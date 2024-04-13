package webserver.request;

import java.util.Arrays;
import java.util.Objects;

import webserver.response.ContentType;

public class Path {
	private static final String PATH_DELIMETER = "/";
	private static final String FILE_TYPE_DELIMETER = "\\.";

	private final String path;

	public Path(String path) {
		this.path = validatePath(path);
	}

	public String validatePath(String path) {
		if (path.length() > 1 && path.endsWith(PATH_DELIMETER)) {
			throw new IllegalArgumentException("path 는 슬래시로 끝나면 안됩니다.");
		}

		if (!path.startsWith(PATH_DELIMETER)) {
			return "/" + path;
		}
		return path;
	}

	public ContentType getContentType() {
		String[] splitedPath = path.split(FILE_TYPE_DELIMETER);
		return Arrays.stream(ContentType.values())
			.filter(contentType -> contentType.getType().equals(splitedPath[splitedPath.length - 1]))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("지원하지 않는 Content-Type 입니다."));
	}

	public boolean isTemplate() {
		return path.endsWith(".html") || path.endsWith(".ico");
	}

	public boolean isStatic() {
		return path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".ttf") || path.endsWith(".woff");
	}

	public String getFilePath() {
		if (isTemplate()) {
			return "./templates" + path;
		}

		if (isStatic()) {
			return "./static" + path;
		}

		throw new IllegalArgumentException("해당 Path는 파일 경로가 아닙니다.");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Path path1 = (Path)o;
		return Objects.equals(path, path1.path);
	}

	@Override
	public int hashCode() {
		return Objects.hash(path);
	}

	@Override
	public String toString() {
		return path;
	}
}
