package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import webserver.request.HttpRequest;
import webserver.request.Protocol;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.ResponseHeader;
import webserver.response.StatusLine;

public class ResourceController extends Controller {

	@Override
	public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
		if (httpRequest.getRequestLine().isPathHome()) {
			ResponseHeader responseHeader = ResponseHeader.create302Header("/index.html");
			httpResponse.response(new StatusLine(Protocol.HTTP_1_1, HttpStatus.FOUND), responseHeader, new byte[0]);
			return;
		}
		byte[] body = httpRequest.createResponseBody();
		ResponseHeader responseHeader = ResponseHeader.create200Header(
			httpRequest.getRequestLine().getPath().getContentType(), body.length);

		httpResponse.response(new StatusLine(Protocol.HTTP_1_1, HttpStatus.OK), responseHeader, body);
	}
}
