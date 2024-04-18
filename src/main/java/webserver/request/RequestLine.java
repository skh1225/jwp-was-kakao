package webserver.request;

import java.util.Arrays;
import java.util.HashMap;

public class RequestLine {
	private static final String FIRST_LINE_DELIMETER = " ";
	private static final String PATH_DELIMETER = "\\?";
	private static final int METHOD_LOCATION = 0;
	private static final int FULL_PATH_LOCATION = 1;
	private static final int PROTOCOL_LOCATION = 2;
	private static final int PATH_LOCATION = 0;
	private static final int PARAMETER_LOCATION = 1;

	private final Method method;
	private final Protocol protocol;
	private final Path path;
	private final RequestParameters requestParameters;

	public RequestLine(Method method, Protocol protocol, Path path, RequestParameters requestParameters) {
		this.method = method;
		this.protocol = protocol;
		this.path = path;
		this.requestParameters = requestParameters;
	}

	public static RequestLine from(String firstLine) {
		String[] splitedFirstLine = firstLine.split(FIRST_LINE_DELIMETER);

		Method method = Arrays.stream(Method.values())
			.filter(m -> m.getMethod().equals(splitedFirstLine[METHOD_LOCATION]))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("지원하지 않는 메소드 입니다."));
		Protocol protocol = Protocol.from(splitedFirstLine[PROTOCOL_LOCATION]);
		String[] pathAndParameters = splitedFirstLine[FULL_PATH_LOCATION].split(PATH_DELIMETER);
		Path path = new Path(pathAndParameters[PATH_LOCATION]);
		RequestParameters requestParameters = new RequestParameters(new HashMap<>());

		if (pathAndParameters.length > 1) {
			requestParameters.add(RequestParameters.extractParameters(pathAndParameters[PARAMETER_LOCATION]));
		}

		return new RequestLine(method, protocol, path, requestParameters);
	}

	public RequestParameters getRequestParameters() {
		return requestParameters;
	}

	public Method getMethod() {
		return method;
	}

	public boolean isPathHome() {
		return path.equals(new Path("/"));
	}

	public Path getPath() {
		return path;
	}

	public Protocol getProtocol() {
		return protocol;
	}
}
