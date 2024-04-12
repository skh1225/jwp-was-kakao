package webserver.response;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import webserver.RequestHandler;

public class HttpResponse {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);

	private final DataOutputStream dataOutputStream;

	public HttpResponse(DataOutputStream dataOutputStream) {
		this.dataOutputStream = dataOutputStream;
	}

	public void response(byte[] body, String httpStatus, ContentType contentType) {
		try {
			dataOutputStream.writeBytes(String.format("HTTP/1.1 %s \r\n", httpStatus));
			dataOutputStream.writeBytes(String.format("Content-Type: %s\r\n", contentType.toString()));
			dataOutputStream.writeBytes(String.format("Content-Length: %d\r\n", body.length));
			dataOutputStream.writeBytes("\r\n");
			dataOutputStream.write(body, 0, body.length);
			dataOutputStream.flush();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public void redirect(String location) {
		try {
			dataOutputStream.writeBytes(String.format("HTTP/1.1 %s \r\n", HttpStatus.FOUND));
			dataOutputStream.writeBytes(String.format("Location: %s\r\n", location));
			dataOutputStream.writeBytes("\r\n");
			dataOutputStream.flush();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}
}
