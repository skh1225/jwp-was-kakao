package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.crypto.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import db.DataBase;
import model.User;
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

			HttpRequest httpRequest = HttpRequest.from(br);

			byte[] body = httpRequest.getBody();
			if (httpRequest.getMethod().equals("GET") && httpRequest.isFile()) {
				HttpResponse.response(dos, body, HttpStatus.OK.toString(), httpRequest.getContentType());
			}

			if (httpRequest.getMethod().equals("POST") && httpRequest.getPath().equals("/user/create")) {
				User user = createUser(httpRequest.getQueryParameters());
				DataBase.addUser(user);
				logger.info(DataBase.findAll().toString());
				HttpResponse.response(dos, body, HttpStatus.OK.toString(), httpRequest.getContentType());
			}

			HttpResponse.response(dos, body, HttpStatus.BAD_REQUEST.toString(), httpRequest.getContentType());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private User createUser(Map<String, String> userInfo) {
		return new User(userInfo.get("userId"), userInfo.get("password"), userInfo.get("name"), userInfo.get("email"));
	}
}