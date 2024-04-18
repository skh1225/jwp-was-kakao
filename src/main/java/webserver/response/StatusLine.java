package webserver.response;

import webserver.request.Protocol;

public class StatusLine {
	private final Protocol protocol;
	private final HttpStatus httpStatus;

	public StatusLine(Protocol protocol, HttpStatus httpStatus) {
		this.protocol = protocol;
		this.httpStatus = httpStatus;
	}

	@Override
	public String toString() {
		return protocol.toString() + " " + httpStatus.toString();
	}
}
