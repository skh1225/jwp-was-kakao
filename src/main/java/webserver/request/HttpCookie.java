package webserver.request;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.controller.LoginUserController;

public class HttpCookie {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpCookie.class);
	private static final String COOKIE_DELIMETER = "; ";
	private static final String KEY_VALUE_DELIMETER = "=";
	private static final String JSESSIONID = "JSESSIONID";
	private static final String LOGINED = "logined";

	private final Map<String, String> cookies;

	public HttpCookie(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	public static HttpCookie from(String parameters) {
		Map<String, String> cookies = new HashMap<>();

		for (String parameter : parameters.split(COOKIE_DELIMETER)) {
			String[] keyAndValue = parameter.split(KEY_VALUE_DELIMETER);
			cookies.put(keyAndValue[0], keyAndValue[1]);
		}

		return new HttpCookie(cookies);
	}

	public boolean jesessionIdExists() {
		return cookies.get(JSESSIONID) != null;
	}

	public String getJsessionid() {
		return cookies.get(JSESSIONID);
	}

	public boolean isLogined() {
		LOGGER.debug(cookies.get(LOGINED));
		if (cookies.get(LOGINED) == null) {
			return false;
		}
		return cookies.get(LOGINED).equals("true");
	}
}
