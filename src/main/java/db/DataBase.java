package db;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import model.User;
import webserver.RequestHandler;

public class DataBase {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
	private static final Map<String, User> USERS = Maps.newHashMap();

	public static void addUser(User user) {
		validateUserInfo(user.getUserId());
		USERS.put(user.getUserId(), user);
		LOGGER.debug(user.toString());
	}

	public static User findUserById(String userId) {
		return USERS.get(userId);
	}

	public static Collection<User> findAll() {
		return USERS.values();
	}

	private static void validateUserInfo(String userId) {
		if (findUserById(userId) != null) {
			throw new IllegalArgumentException("기존에 존재하는 user ID 입니다.");
		}
	}
}
