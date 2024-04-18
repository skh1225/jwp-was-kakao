package webserver.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.RequestHandler;

public class HttpResponse {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponse.class);

	private final DataOutputStream dataOutputStream;

	public HttpResponse(DataOutputStream dataOutputStream) {
		this.dataOutputStream = dataOutputStream;
	}

	public void response(StatusLine statusLine, ResponseHeader responseHeader, byte[] responseBody) {
		try {
			writeStatus(statusLine);
			writeHeader(responseHeader);
			writeBody(responseBody);
			writeEnd();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public void writeStatus(StatusLine statusLine) {
		try {
			dataOutputStream.writeBytes(String.format("%s \r\n", statusLine));
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public void writeHeader(ResponseHeader responseHeader) throws IOException {
		for (Map.Entry<String, String> header : responseHeader.getHeaders().entrySet()) {
			dataOutputStream.writeBytes(String.format("%s: %s\r\n", header.getKey(), header.getValue()));
		}
	}

	public void writeBody(byte[] responseBody) throws IOException {
		dataOutputStream.writeBytes("\r\n");
		dataOutputStream.write(responseBody, 0, responseBody.length);
	}

	public void writeEnd() throws IOException {
		dataOutputStream.flush();
	}
}
