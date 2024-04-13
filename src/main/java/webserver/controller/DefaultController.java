package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class DefaultController extends Controller {

	@Override
	public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
		httpResponse.redirect("/index.html");
	}
}
