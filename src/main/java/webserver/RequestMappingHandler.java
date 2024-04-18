package webserver;

import java.util.HashMap;
import java.util.Map;

import webserver.controller.Controller;
import webserver.controller.CreateUserController;
import webserver.controller.DefaultController;
import webserver.controller.ListUserController;
import webserver.controller.LoginUserController;
import webserver.controller.ResourceController;
import webserver.request.Path;

public class RequestMappingHandler {

	private static final String CREATE_USER_URL = "/user/create";
	private static final String LOGIN_USER_URL = "/user/login";
	private static final String LOGIN_TEMPLATES_URL = "/user/login.html";
	private static final String LIST_TEMPLATES_URL = "/user/list.html";
	private static final Map<String, Controller> CONTROLLER_MAPPER = new HashMap<>();

	static {
		CONTROLLER_MAPPER.put(CREATE_USER_URL, new CreateUserController());
		CONTROLLER_MAPPER.put(LOGIN_USER_URL, new LoginUserController());
		CONTROLLER_MAPPER.put(LOGIN_TEMPLATES_URL, new LoginUserController());
		CONTROLLER_MAPPER.put(LIST_TEMPLATES_URL, new ListUserController());
	}

	public static Controller getController(Path path) {
		if (path.isStatic() || path.isTemplate() && !path.equals(new Path(LOGIN_TEMPLATES_URL)) && !path.equals(
			new Path(LIST_TEMPLATES_URL))) {
			return new ResourceController();
		}

		return CONTROLLER_MAPPER.getOrDefault(path.toString(), new DefaultController());
	}
}
