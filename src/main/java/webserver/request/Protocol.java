package webserver.request;

import java.util.Objects;

public class Protocol {
	private static final String PROTOCOL_DELIMETER = "/";
	private static final int PROTOCOL_LOCATION = 0;
	private static final int VERSION_LOCATION = 1;

	private final String protocol;
	private final String version;

	public Protocol(String protocol, String version) {
		this.protocol = protocol;
		this.version = version;
	}

	public static Protocol from(String protocol) {
		String[] splitedProtocol = protocol.split(PROTOCOL_DELIMETER);
		return new Protocol(splitedProtocol[PROTOCOL_LOCATION], splitedProtocol[VERSION_LOCATION]);
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
