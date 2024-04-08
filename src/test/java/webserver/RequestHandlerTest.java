package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RequestHandlerTest {

	@Test
	void 파일_경로를_파싱할_수_잇다() {
		Socket socket = new Socket();
		RequestHandler requestHandler = new RequestHandler(socket);

		assertThat(requestHandler.parsePath("GET /index.html HTTP/1.1")).isEqualTo("/index.html");
	}

	@Test
	void 인덱스_html_GET_요청_테스트() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
