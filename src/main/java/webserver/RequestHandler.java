package webserver;

import static org.springframework.http.server.PathContainer.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.FileIoUtils;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	private Socket connection;

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

			List<String> header = getRequestHeader(br);

			byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + parsePath(header.get(0)));
			response200Header(dos, body.length);
			responseBody(dos, body);
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private List<String> getRequestHeader(BufferedReader br) throws IOException {
		List<String> header = new ArrayList<>();

		String line = br.readLine();
		while (!"".equals(line) && line != null) {
			header.add(line);
			line = br.readLine();
		}

		return header;
	}


	public String parsePath(String firstLine) {
		String[] result = firstLine.split(" ");
		return result[1];
	}

	private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void responseBody(DataOutputStream dos, byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}