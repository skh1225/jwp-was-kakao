package webserver.response;

public enum HttpStatus {
	OK("200", "OK"),
	FOUND("302", "FOUND"),
	NOT_FOUND("404", "NOT FOUND");

	private final String value;
	private final String responsePhrase;

	HttpStatus(String value, String responsePhrase) {
		this.value = value;
		this.responsePhrase = responsePhrase;
	}

	@Override
	public String toString() {
		return this.value + " " + this.responsePhrase;
	}
}
