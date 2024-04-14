package webserver.controller;

import java.util.Map;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.request.RequestParameters;
import webserver.response.HttpResponse;

public class CreateUserController extends Controller {
	@Override
	public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
		try {
			RequestParameters parameters = httpRequest.getRequestParameters().add(httpRequest.getRequestBody().getParameters());
			User user = createUser(parameters.getParameters());
			validateDuplicateUser(user.getUserId());
			DataBase.addUser(user);
			httpResponse.redirect("/index.html");
		} catch (IllegalArgumentException exception) {
			httpResponse.redirect("/user/form.html");
		}
	}

	private User createUser(Map<String, String> userInfo) {
		return new User(userInfo.get("userId"), userInfo.get("password"), userInfo.get("name"), userInfo.get("email"));
	}

	private static void validateDuplicateUser(String userId) {
		if (DataBase.findUserById(userId) != null) {
			throw new IllegalArgumentException("기존에 존재하는 user ID 입니다.");
		}
	}
}
