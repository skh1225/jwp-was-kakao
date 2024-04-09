package webserver.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import db.DataBase;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

public class PostRequestController {
	public static void run(HttpRequest httpRequest, DataOutputStream dos, Logger logger) throws IOException, URISyntaxException {
		if (httpRequest.getPath().equals("/user/create")) {
			createUserAndResponse(httpRequest, dos, logger);
			return;
		}

		byte[] body = httpRequest.getBody();
		HttpResponse.response(dos, body, HttpStatus.BAD_REQUEST.toString(), httpRequest.getContentType());
	}

	private static void createUserAndResponse(HttpRequest httpRequest, DataOutputStream dos, Logger logger) {
		try {
			User user = createUser(httpRequest.getQueryParameters());
			DataBase.addUser(user);
			logger.info(DataBase.findAll().toString());
			HttpResponse.http302Response(dos, "/index.html");
		} catch (IllegalArgumentException exception) {
			logger.error(exception.toString());
			HttpResponse.http302Response(dos, "/user/form.html");
		}
	}

	private static User createUser(Map<String, String> userInfo) {
		return new User(userInfo.get("userId"), userInfo.get("password"), userInfo.get("name"), userInfo.get("email"));
	}
}
