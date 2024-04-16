package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.ResponseHeader;

public class ResourceController extends Controller {

	@Override
	public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
		if (httpRequest.getRequestLine().isPathHome()) {
			ResponseHeader responseHeader = ResponseHeader.create302Header("/index.html");
			httpResponse.response(responseHeader, new byte[0]);
			return;
		}
		try {
			byte[] body = httpRequest.createResponseBody();
			ResponseHeader responseHeader = ResponseHeader.create200Header(httpRequest.getRequestLine().getPath().getContentType(), body.length);

			httpResponse.response(responseHeader, body);
		} catch (Exception exception) {
			byte[] body = httpRequest.createResponseBody();
			ResponseHeader responseHeader = ResponseHeader.create400Header();
			httpResponse.response(responseHeader, body);
		}
	}
}
