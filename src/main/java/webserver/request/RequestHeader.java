package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import webserver.session.SessionManager;

public class RequestHeader {

	private static final String KEY_VALUE_DELIMETER = ":";
	private static final int KEY_LOCATION = 0;
	private static final int VALUE_LOCATION = 1;
	private static final String CONTENT_LENGTH = "Content-Length";
	private static final String COOKIE = "Cookie";

	private Map<String, String> headers;

	private RequestHeader(Map<String, String> headers) {
		this.headers = headers;
	}

	public static RequestHeader from(BufferedReader bufferedReader) throws IOException {
		Map<String, String> headers = new HashMap<>();
		String readLine = bufferedReader.readLine();
		while (!"".equals(readLine)) {
			String[] lines = readLine.split(KEY_VALUE_DELIMETER);
			headers.put(lines[KEY_LOCATION].trim(), lines[VALUE_LOCATION].trim());
			readLine = bufferedReader.readLine();
			if (readLine == null) {
				break;
			}
		}

		return new RequestHeader(headers);
	}

	public int getContentLength() {
		if (headers.get(CONTENT_LENGTH) != null) {
			return Integer.parseInt(headers.get(CONTENT_LENGTH));
		}
		return 0;
	}

	public String getJsessionId() {
		return HttpCookie.from(headers.get(COOKIE)).getJsessionid();
	}

	public boolean jsessionIdExists() {
		if (!headers.containsKey(COOKIE)) {
			return false;
		}
		return HttpCookie.from(headers.get(COOKIE)).jesessionIdExists();
	}

	public boolean isLogined() {
		if (!headers.containsKey(COOKIE)) {
			return false;
		}
		return HttpCookie.from(headers.get(COOKIE)).isLogined();
	}
}
