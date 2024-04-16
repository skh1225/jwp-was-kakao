package webserver.response;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;

public class ResponseHeader {
	private final HttpStatus httpStatus;
	private final Map<String, String> headers;

	public ResponseHeader(HttpStatus httpStatus, Map<String, String> headers) {
		this.httpStatus = httpStatus;
		this.headers = headers;
	}

	public static ResponseHeader create200Header(ContentType contentType, int contentLength) {
		Map<String, String> header = new HashMap<>();
		header.put("Content-Type", contentType.toString());
		header.put("Content-Length", Integer.toString(contentLength));
		return new ResponseHeader(HttpStatus.OK, header);
	}

	public static ResponseHeader create302Header(String location) {
		Map<String, String> header = new HashMap<>();
		header.put("Location", location);
		return new ResponseHeader(HttpStatus.FOUND, header);
	}

	public static ResponseHeader create400Header() {
		Map<String, String> header = new HashMap<>();
		return new ResponseHeader(HttpStatus.BAD_REQUEST, header);
	}

	public void addHeader(String key, String value) {
		this.headers.put(key, value);
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}
}
