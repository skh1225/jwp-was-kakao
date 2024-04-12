package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.http.HttpStatus;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class ResourceController extends Controller {

	@Override
	public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
		if (httpRequest.getRequestLine().isPathHome()) {
			httpResponse.redirect("/index.html");
			return;
		}
		try {
			byte[] body = httpRequest.createResponseBody();
			httpResponse.response(body, HttpStatus.OK.toString(),
				httpRequest.getRequestLine().getPath().getContentType());
		} catch (IllegalArgumentException exception) {
			byte[] body = httpRequest.createResponseBody();
			httpResponse.response(body, HttpStatus.BAD_REQUEST.toString(),
				httpRequest.getRequestLine().getPath().getContentType());
		}
	}
}
