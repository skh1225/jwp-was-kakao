package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import utils.FileIoUtils;
import utils.IOUtils;

public class HttpRequest {

	private final RequestLine requestLine;
	private final RequestHeader requestHeader;
	private final RequestParameters requestParameters;
	private final RequestParameters requestBody;

	public HttpRequest(BufferedReader bufferedReader) throws IOException {
		String firstLine = bufferedReader.readLine();
		this.requestLine = RequestLine.from(firstLine);
		this.requestHeader = RequestHeader.from(bufferedReader);
		this.requestParameters = this.requestLine.getRequestParameters();
		this.requestBody = new RequestParameters(new HashMap<>());

		if (this.requestHeader.getContentLength() > 0) {
			this.requestBody.add(RequestParameters.extractParameters(
				IOUtils.readData(bufferedReader, this.requestHeader.getContentLength())));
		}
	}

	public byte[] createResponseBody() throws IOException, URISyntaxException {
		return FileIoUtils.loadFileFromClasspath(requestLine.getPath().getFilePath());
	}

	public Method getMethod() {
		return requestLine.getMethod();
	}

	public RequestParameters getRequestParameters() {
		return requestParameters;
	}

	public RequestParameters getRequestBody() {
		return requestBody;
	}

	public RequestHeader getRequestHeader() {
		return requestHeader;
	}

	public RequestLine getRequestLine() {
		return requestLine;
	}
}