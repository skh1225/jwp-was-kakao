package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import webserver.request.HttpRequest;
import webserver.request.Method;
import webserver.response.HttpResponse;

public abstract class Controller {

	public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
		Method method = httpRequest.getMethod();
		if (method == Method.GET) {
			doGet(httpRequest, httpResponse);
			return;
		}
		doPost(httpRequest, httpResponse);
	}

	public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
		throw new IllegalStateException("GET 메소드는 지원하지 않습니다.");
	}

	public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
		throw new IllegalStateException("POST 메소드는 지원하지 않습니다.");
	}
}
