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

import webserver.controller.Controller;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class RequestHandler implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);

	private final Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		LOGGER.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			// TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			DataOutputStream dos = new DataOutputStream(out);

			HttpRequest httpRequest = new HttpRequest(br);
			HttpResponse httpResponse = new HttpResponse(dos);

			Controller controller = RequestMappingHandler.getController(httpRequest.getRequestLine().getPath());
			controller.service(httpRequest, httpResponse);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}