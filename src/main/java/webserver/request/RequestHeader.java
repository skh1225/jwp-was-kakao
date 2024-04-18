package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {

	private static final String KEY_VALUE_DELIMETER = ":";
	private static final int KEY_LOCATION = 0;
	private static final int VALUE_LOCATION = 1;
	private static final String CONTENT_LENGTH = "Content-Length";
	private static final String COOKIE = "Cookie";

	private Map<String, String> headers;
	private HttpCookie httpCookie;

	private RequestHeader(Map<String, String> headers, HttpCookie httpCookie) {
		this.headers = headers;
		this.httpCookie = httpCookie;
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
		if (headers.containsKey(COOKIE)) {
			return new RequestHeader(headers, HttpCookie.from(headers.get(COOKIE)));
		}
		return new RequestHeader(headers, new HttpCookie(new HashMap<>()));
	}

	public int getContentLength() {
		if (headers.get(CONTENT_LENGTH) != null) {
			return Integer.parseInt(headers.get(CONTENT_LENGTH));
		}
		return 0;
	}

	public String getJsessionId() {
		return httpCookie.getJsessionid();
	}

	public boolean jsessionIdExists() {
		return httpCookie.jesessionIdExists();
	}

	public boolean isLogined() {
		return httpCookie.isLogined();
	}
}
