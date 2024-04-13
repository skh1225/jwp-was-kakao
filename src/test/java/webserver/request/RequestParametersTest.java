package webserver.request;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class RequestParametersTest {
	@Test
	void RequestParameter를_파싱할_수_있다() {
		String queryString = "userId=cu&password=password&name=%EC%9D%B4%EB%8F%99%EA%B7%9C&email=brainbackdoor%40gmail.com";
		Map<String, String> expectedParameters = new HashMap<>();
		expectedParameters.put("password", "password");
		expectedParameters.put("name", "이동규");
		expectedParameters.put("userId", "cu");
		expectedParameters.put("email", "brainbackdoor@gmail.com");

		Map<String, String> parameters = RequestParameters.from(queryString).getParameters();
		assertThat(parameters).isEqualTo(expectedParameters);
	}
}
