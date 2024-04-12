package webserver.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PathTest {
	@Test
	void 슬래시로_끝나는_경로에대해서_오류를_던진다() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Path("/abc/"));
	}

	@ParameterizedTest
	@CsvSource({"/index.html, ./templates/index.html", "/favicon.ico, ./templates/favicon.ico", "/css/style.css, ./static/css/style.css"})
	void 경로에대해_적절한_파일경로를_반환한다(String path, String filePath) {

		assertThat(new Path(path).getFilePath()).isEqualTo(filePath);
	}
}
