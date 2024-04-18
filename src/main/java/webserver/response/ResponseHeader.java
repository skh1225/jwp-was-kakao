package webserver.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
	private final Map<String, String> headers;

	public ResponseHeader(Map<String, String> headers) {
		this.headers = headers;
	}

	public static ResponseHeader create200Header(ContentType contentType, int contentLength) {
		Map<String, String> header = new HashMap<>();
		header.put("Content-Type", contentType.toString());
		header.put("Content-Length", Integer.toString(contentLength));
		return new ResponseHeader(header);
	}

	public static ResponseHeader create302Header(String location) {
		Map<String, String> header = new HashMap<>();
		header.put("Location", location);
		return new ResponseHeader(header);
	}

	public void addHeader(String key, String value) {
		this.headers.put(key, value);
	}

	public Map<String, String> getHeaders() {
		return headers;
	}
}
