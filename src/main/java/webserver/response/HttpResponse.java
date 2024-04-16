package webserver.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import webserver.RequestHandler;

public class HttpResponse {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponse.class);

	private final DataOutputStream dataOutputStream;

	public HttpResponse(DataOutputStream dataOutputStream) {
		this.dataOutputStream = dataOutputStream;
	}

	public void response(ResponseHeader responseHeader, byte[] body) {
		try {
			dataOutputStream.writeBytes(String.format("HTTP/1.1 %s \r\n", responseHeader.getHttpStatus()));

			for (Map.Entry<String, String> header : responseHeader.getHeaders().entrySet()) {
				dataOutputStream.writeBytes(String.format("%s: %s\r\n", header.getKey(), header.getValue()));
			}

			dataOutputStream.writeBytes("\r\n");
			dataOutputStream.write(body, 0, body.length);
			dataOutputStream.flush();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}
}
