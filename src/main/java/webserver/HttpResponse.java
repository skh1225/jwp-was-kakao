package webserver;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);

	public static void response(DataOutputStream dos, byte[] body, String httpStatus, String contentType) {
		try {
			dos.writeBytes(String.format("HTTP/1.1 %s \r\n", httpStatus));
			dos.writeBytes(String.format("Content-Type: %s\r\n", contentType));
			dos.writeBytes(String.format("Content-Length: %d\r\n", body.length));
			dos.writeBytes("\r\n");
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}
}
