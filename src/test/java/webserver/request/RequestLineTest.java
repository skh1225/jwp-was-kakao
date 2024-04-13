package webserver.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class RequestLineTest {
	@Test
	void 적절한_RequestLine을_생성할_수_있다() {
		String firstLine = "GET /user/create?userId=cu&password=password&name=%EC%9D%B4%EB%8F%99%EA%B7%9C&email=brainbackdoor%40gmail.com HTTP/1.1";
		RequestLine requestLine = RequestLine.from(firstLine);

		Map<String, String> expectedParameters = new HashMap<>();
		expectedParameters.put("password", "password");
		expectedParameters.put("name", "이동규");
		expectedParameters.put("userId", "cu");
		expectedParameters.put("email", "brainbackdoor@gmail.com");

		assertAll(
			() -> assertThat(requestLine.getMethod()).isEqualTo(Method.GET),
			() -> assertThat(requestLine.getPath()).isEqualTo(new Path("/user/create")),
			() -> assertThat(requestLine.getRequestParameters()).isEqualTo(new RequestParameters(expectedParameters)),
			() -> assertThat(requestLine.getProtocol()).isEqualTo(Protocol.from("HTTP/1.1"))
		);
	}
}
