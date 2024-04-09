package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.FileIoUtils;

public class HttpRequest {

	private final String method;
	private final String path;
	private final Map<String, String> queryParameters;

	public HttpRequest(String method, String path, Map<String, String> queryParameters) {
		this.method = method;
		this.path = path;
		this.queryParameters = queryParameters;
	}

	public static HttpRequest from(BufferedReader br) throws IOException {
		List<String> header = getRequestHeader(br);

		String method = header.get(0).split(" ")[0];
		String[] requestUrl = header.get(0).split(" ")[1].split("\\?");

		if (requestUrl.length > 1) {
			Map<String, String> queryParameters = extractQueryParameters(requestUrl);

			return new HttpRequest(method, requestUrl[0], queryParameters);
		}

		return new HttpRequest(method, requestUrl[0], new HashMap<>());
	}

	public static Map<String, String> extractQueryParameters(String[] requestUrl) {
		Map<String, String> queryParameters = new HashMap<>();

		for (String queryParameter : requestUrl[1].split("&")) {
			String[] splitedQueryParameter = queryParameter.split("=");
			queryParameters.put(splitedQueryParameter[0], splitedQueryParameter[1]);
		}
		return queryParameters;
	}

	private static List<String> getRequestHeader(BufferedReader br) throws IOException {
		List<String> header = new ArrayList<>();

		String line = br.readLine();
		while (!"".equals(line) && line != null) {
			header.add(line);
			line = br.readLine();
		}

		return header;
	}

	public String getFileType() {
		return path.split("\\.")[path.split("\\.").length - 1];
	}

	public String getPath() {
		if (getFileType().equals("css")) {
			return "./static" + path;
		}
		if (getFileType().equals("html") || getFileType().equals("ico")) {
			return "./templates" + path;
		}
		return path;
	}

	public byte[] getBody() throws IOException, URISyntaxException {
		if (getFileType().equals("html") || getFileType().equals("css") || getFileType().equals("ico")) {
			return FileIoUtils.loadFileFromClasspath(getPath());
 		}

		return new byte[0];
	}

	public String getContentType() {
		if (getFileType().equals("html")) {
			return "text/html;charset=utf-8";
		}

		return "text/css";
	}

	public Map<String, String> getQueryParameters() {
		return queryParameters;
	}
}
