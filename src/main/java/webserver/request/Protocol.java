package webserver.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Protocol {
	private static final String PROTOCOL_DELIMETER = "/";
	private static final int PROTOCOL_LOCATION = 0;
	private static final int VERSION_LOCATION = 1;

	private final String protocol;
	private final String version;

	private static final Map<String, Protocol> PROTOCOLS = new HashMap<>();

	static {
		PROTOCOLS.put("HTTP/0.9", new Protocol("HTTP", "0.9"));
		PROTOCOLS.put("HTTP/1.0", new Protocol("HTTP", "1.0"));
		PROTOCOLS.put("HTTP/1.1", new Protocol("HTTP", "1.1"));
		PROTOCOLS.put("HTTP/2", new Protocol("HTTP", "2"));
		PROTOCOLS.put("HTTP/3", new Protocol("HTTP", "3"));
	}

	private Protocol(String protocol, String version) {
		this.protocol = protocol;
		this.version = version;
	}

	public static Protocol from(String protocol) {
		Protocol cachedProtocol = PROTOCOLS.get(protocol);

		if (cachedProtocol == null) {
			throw new IllegalArgumentException("유효하지 않은 프로토콜입니다.");
		}

		return cachedProtocol;
	}


	public String getProtocol() {
		return protocol;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Protocol protocol1 = (Protocol)o;
		return Objects.equals(protocol, protocol1.protocol) && Objects.equals(version,
			protocol1.version);
	}

	@Override
	public int hashCode() {
		return Objects.hash(protocol, version);
	}
}
