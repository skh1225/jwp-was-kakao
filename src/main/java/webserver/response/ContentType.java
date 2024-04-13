package webserver.response;

public enum ContentType {

	HTML("html", "text/html"),
	CSS("css", "text/css"),
	ICO("ico", "image/vnd.microsoft.icon"),
	JS("js", "application/x-javascript"),
	WOFF("woff", "font/woff"),
	TTF("ttf", "font/ttf");

	private String type;
	private String contentType;

	ContentType(String type, String contentType) {
		this.type = type;
		this.contentType = contentType;
	}

	public String getType() {
		return type;
	}

	public String getContentType() {
		return contentType;
	}

	@Override
	public String toString() {
		return contentType + ";charset=utf-8";
	}
}
