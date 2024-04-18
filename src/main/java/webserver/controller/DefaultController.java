package webserver.controller;

import webserver.request.HttpRequest;
import webserver.request.Protocol;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.ResponseHeader;
import webserver.response.StatusLine;

public class DefaultController extends Controller {

	@Override
	public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
		ResponseHeader responseHeader = ResponseHeader.create302Header("/index.html");
		httpResponse.response(new StatusLine(Protocol.HTTP_1_1, HttpStatus.FOUND), responseHeader, new byte[0]);
	}
}
