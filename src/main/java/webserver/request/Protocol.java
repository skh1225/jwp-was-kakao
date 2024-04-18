package webserver.request;

import java.util.Arrays;

public enum Protocol {
	HTTP_0_9("HTTP", "0.9"),
	HTTP_1_0("HTTP", "1.0"),
	HTTP_1_1("HTTP", "1.1"),
	HTTP_2("HTTP", "2"),
	HTTP_3("HTTP", "3");

	private final String protocol;
	private final String version;

	Protocol(String protocol, String version) {
		this.protocol = protocol;
		this.version = version;
	}

	public static Protocol from(String protocol) {
		String[] splitedProtocol = protocol.split("/");
		return Arrays.stream(Protocol.values())
			.filter(p -> p.getProtocol().equals(splitedProtocol[0]))
			.filter(p -> p.getVersion().equals(splitedProtocol[1]))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 프로토콜 입니다."));
	}

	public String getProtocol() {
		return protocol;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return this.protocol + "/" + this.version;
	}
}
