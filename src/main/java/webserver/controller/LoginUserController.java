package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import webserver.WebApplicationServer;
import webserver.request.HttpRequest;
import webserver.request.RequestParameters;
import webserver.response.HttpResponse;
import webserver.response.ResponseHeader;
import webserver.session.Session;
import webserver.session.SessionManager;

public class LoginUserController extends Controller {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginUserController.class);
	@Override
	public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
		try {
			Map<String, String> parameters = httpRequest.getRequestParameters().add(httpRequest.getRequestBody().getParameters()).getParameters();
			String userId = parameters.getOrDefault("userId", "");
			String password = parameters.getOrDefault("password", "");
			User user = DataBase.findUserById(userId);

			if (user.getPassword().equals(password)) {
				ResponseHeader responseHeader = ResponseHeader.create302Header("/index.html");
				responseHeader.addHeader("Set-Cookie", "logined=true; Path=/");
				httpResponse.response(responseHeader, new byte[0]);
				Session session = new Session(httpRequest.getRequestHeader().getJsessionId());
				session.setAttribute("user", user);
				SessionManager.add(session);
				return;
			}
			ResponseHeader responseHeader = ResponseHeader.create302Header("/user/login_failed.html");
			responseHeader.addHeader("Set-Cookie", "logined=false; Path=/");
			httpResponse.response(responseHeader, new byte[0]);
		} catch (NullPointerException exception) {
			ResponseHeader responseHeader = ResponseHeader.create302Header("/user/login_failed.html");
			responseHeader.addHeader("Set-Cookie", "logined=false; Path=/");
			httpResponse.response(responseHeader, new byte[0]);
		}
	}

	@Override
	public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
		try {
			if (SessionManager.findSession(httpRequest.getRequestHeader().getJsessionId()).getAttribute("user") != null) {
				ResponseHeader responseHeader = ResponseHeader.create302Header("/index.html");
				httpResponse.response(responseHeader, new byte[0]);
			}

			byte[] body = httpRequest.createResponseBody();
			ResponseHeader responseHeader = ResponseHeader.create200Header(httpRequest.getRequestLine().getPath().getContentType(), body.length);

			if (!httpRequest.getRequestHeader().jsessionIdExists()) {
				responseHeader.addHeader("Set-Cookie", String.format("JSESSIONID=%s; Path=/", UUID.randomUUID()));
			}

			httpResponse.response(responseHeader, body);
		} catch (Exception exception) {
			byte[] body = httpRequest.createResponseBody();
			ResponseHeader responseHeader = ResponseHeader.create400Header();
			httpResponse.response(responseHeader, body);
		}
	}
}
