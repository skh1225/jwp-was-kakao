package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.ResponseHeader;

public class DefaultController extends Controller {

	@Override
	public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
		ResponseHeader responseHeader = ResponseHeader.create302Header("/index.html");
		httpResponse.response(HttpStatus.FOUND, responseHeader, new byte[0]);
	}
}
