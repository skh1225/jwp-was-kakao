package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.ResponseHeader;

public class ResourceController extends Controller {

	@Override
	public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
		if (httpRequest.getRequestLine().isPathHome()) {
			ResponseHeader responseHeader = ResponseHeader.create302Header("/index.html");
			httpResponse.response(HttpStatus.FOUND, responseHeader, new byte[0]);
			return;
		}
		byte[] body = httpRequest.createResponseBody();
		ResponseHeader responseHeader = ResponseHeader.create200Header(httpRequest.getRequestLine().getPath().getContentType(), body.length);

		httpResponse.response(HttpStatus.OK, responseHeader, body);
	}
}
