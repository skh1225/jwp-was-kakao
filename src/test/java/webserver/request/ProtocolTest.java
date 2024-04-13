package webserver.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ProtocolTest {
	@Test
	void Protocol과_버전을_구분할_수_있다() {
		Protocol protocol = Protocol.from("HTTP/1.1");

		assertAll(
			() -> assertThat(protocol.getProtocol()).isEqualTo("HTTP"),
			() -> assertThat(protocol.getVersion()).isEqualTo("1.1")
		);

	}
}
