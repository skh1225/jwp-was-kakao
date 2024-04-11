package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.controller.GetRequestController;
import webserver.controller.PostRequestController;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	private final Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			// TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
			DataOutputStream dos = new DataOutputStream(out);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			HttpRequest httpRequest = HttpRequest.from(br);

			if (httpRequest.getMethod().equals("GET")) {
				GetRequestController.run(httpRequest, dos, logger);
			}

			if (httpRequest.getMethod().equals("POST")) {
				PostRequestController.run(httpRequest, dos, logger);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}