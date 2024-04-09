package webserver.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

import webserver.HttpRequest;
import webserver.HttpResponse;

public class GetRequestController {
	public static void run(HttpRequest httpRequest, DataOutputStream dos, Logger logger) throws IOException, URISyntaxException {
		if (httpRequest.getPath().isEmpty() || httpRequest.getPath().equals("/")) {
			HttpResponse.http302Response(dos, "/index.html");
			return;
		}

		if (httpRequest.isFile()) {
			byte[] body = httpRequest.getBody();
			HttpResponse.response(dos, body, HttpStatus.OK.toString(), httpRequest.getContentType());
			return;
		}

		byte[] body = httpRequest.getBody();
		HttpResponse.response(dos, body, HttpStatus.BAD_REQUEST.toString(), httpRequest.getContentType());
	}
}
