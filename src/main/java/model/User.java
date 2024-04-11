package model;

import java.util.Objects;

import db.DataBase;

public class User {
	private final String userId;
	private final String password;
	private final String name;
	private final String email;

	public User(String userId, String password, String name, String email) {
		valdateUserInfo(userId);
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void valdateUserInfo(String userId) {
		if (DataBase.findUserById(userId) != null) {
			throw new IllegalArgumentException("기존에 존재하는 user ID 입니다.");
		}
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User)o;
		return Objects.equals(userId, user.userId) && Objects.equals(password, user.password)
			&& Objects.equals(name, user.name) && Objects.equals(email, user.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, password, name, email);
	}
}
