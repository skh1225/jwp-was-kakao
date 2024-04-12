package webserver;

import java.util.HashMap;
import java.util.Map;

import webserver.controller.Controller;
import webserver.controller.CreateUserController;
import webserver.controller.DefaultController;
import webserver.controller.ResourceController;
import webserver.request.Path;

public class RequestMappingHandler {

	private static final String CREATE_USER_URL = "/user/create";
	private static final Map<String, Controller> CONTROLLER_MAPPER = new HashMap<>();

	static {
		CONTROLLER_MAPPER.put(CREATE_USER_URL, new CreateUserController());
	}

	public static Controller getController(Path path) {
		if (path.isStatic() || path.isTemplate()) {
			return new ResourceController();
		}

		return CONTROLLER_MAPPER.getOrDefault(path.toString(), new DefaultController());
	}
}
