package webserver.controller;

import java.util.Map;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class CreateUserController extends Controller {
	@Override
	public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
		try {
			User user = createUser(httpRequest.getRequestParameters());
			DataBase.addUser(user);
			httpResponse.redirect("/index.html");
		} catch (IllegalArgumentException exception) {
			httpResponse.redirect("/user/form.html");
		}
	}

	private static User createUser(Map<String, String> userInfo) {
		return new User(userInfo.get("userId"), userInfo.get("password"), userInfo.get("name"), userInfo.get("email"));
	}
}
