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
		USERS.put(user.getUserId(), user);
		LOGGER.debug(user.toString());
	}

	public static User findUserById(String userId) {
		return USERS.get(userId);
	}

	public static Collection<User> findAll() {
		return USERS.values();
	}
}
