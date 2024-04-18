package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.request.Protocol;
import webserver.response.ContentType;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.ResponseHeader;
import webserver.response.StatusLine;

public class ListUserController extends Controller {
	private static final Logger LOGGER = LoggerFactory.getLogger(webserver.controller.LoginUserController.class);
	private static final Handlebars HANDLEBARS;

	static {
		TemplateLoader loader = new ClassPathTemplateLoader();
		loader.setPrefix("/templates");
		loader.setSuffix(".html");

		HANDLEBARS = new Handlebars(loader);
	}

	@Override
	public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
		if (httpRequest.getRequestHeader().isLogined()) {
			LOGGER.debug("로그인이 확인되었습니다.");
			byte[] body = getUsersTemplates();
			ResponseHeader responseHeader = ResponseHeader.create200Header(httpRequest.getRequestLine().getPath().getContentType(), body.length);

			httpResponse.response(new StatusLine(Protocol.HTTP_1_1, HttpStatus.OK), responseHeader, body);
			return;
		}
		LOGGER.debug("로그인을 해주세요.");
		ResponseHeader responseHeader = ResponseHeader.create302Header("/user/login.html");
		httpResponse.response(new StatusLine(Protocol.HTTP_1_1, HttpStatus.FOUND), responseHeader, new byte[0]);
	}

	private byte[] getUsersTemplates() throws IOException {
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("users", DataBase.findAll());


		Template template = HANDLEBARS.compile("/user/list");
		return template.apply(parameterMap).getBytes();
	}
}
