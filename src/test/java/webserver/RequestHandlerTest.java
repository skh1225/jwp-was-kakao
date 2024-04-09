package webserver;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import model.User;

public class RequestHandlerTest {
	@Test
	void 인덱스_html_GET_요청_테스트() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void POST_유저_생성_테스트() {
		RestTemplate restTemplate = new RestTemplate();
		String name = "name";
		String userId = "userId";
		String password = "password";
		String email = "email@email.com";
		User user = new User(userId, password, name, email);

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("name", name);
		parameters.add("userId", userId);
		parameters.add("password", password);
		parameters.add("email", email);

		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/user/create", parameters, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
	}

	@Test
	void 쿼리_파라미터_분리_테스트() {
		Map<String, String> expectedQueryParams = new HashMap<>();
		expectedQueryParams.put("userId", "id");
		expectedQueryParams.put("name", "name");
		expectedQueryParams.put("password", "password");
		expectedQueryParams.put("email", "email@email.com");

		String parameters = "userId=id&name=name&password=password&email=email@email.com";

		Map<String, String> queryParams = HttpRequest.extractQueryParameters(parameters);

		assertThat(queryParams).isEqualTo(expectedQueryParams);
	}

	@ParameterizedTest
	@ValueSource(strings = {"html", "css"})
	void 요청에대한_파일타입_반환_테스트(String fileType) {
		HttpRequest httpRequest = new HttpRequest("GET", String.format("file.%s", fileType), new HashMap<String, String>(), new HashMap<String, String>());

		assertThat(httpRequest.getFileType()).isEqualTo(fileType);
	}
}
