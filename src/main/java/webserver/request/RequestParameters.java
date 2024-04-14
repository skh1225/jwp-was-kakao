package webserver.request;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestParameters {

	private static final String PARAMETER_DELIMETER = "&";
	private static final String KEY_VALUE_DELIMETER = "=";
	private static final int KEY_LOCATION = 0;
	private static final int VALUE_LOCATION = 1;

	private final Map<String, String> parameters;

	public RequestParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}


	public static RequestParameters from(String queryParameters) {
		RequestParameters requestParameters = new RequestParameters(new HashMap<>());
		requestParameters.add(extractParameters(queryParameters));

		return requestParameters;
	}

	public RequestParameters add(Map<String, String> queryParameters) {
		parameters.putAll(queryParameters);

		return new RequestParameters(parameters);
	}

	public static Map<String, String> extractParameters(String queryParameters) {
		String decodedQueryParameters = URLDecoder.decode(queryParameters, StandardCharsets.UTF_8);
		Map<String, String> parameters = new HashMap<>();

		for (String queryParameter : decodedQueryParameters.split(PARAMETER_DELIMETER)) {
			String[] keyAndValue = queryParameter.split(KEY_VALUE_DELIMETER);
			parameters.put(keyAndValue[KEY_LOCATION], keyAndValue[VALUE_LOCATION]);
		}

		return parameters;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		RequestParameters that = (RequestParameters)o;
		return Objects.equals(parameters, that.parameters);
	}

	@Override
	public int hashCode() {
		return Objects.hash(parameters);
	}
}
