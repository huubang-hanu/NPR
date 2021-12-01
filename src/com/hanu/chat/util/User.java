package com.hanu.chat.util;

import java.util.Objects;

public class User {
	private String username;
	private String password;
	private String email;
	private boolean isOnline;
	
	
	public User(String username, String password, String email, boolean isOnline) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.isOnline = isOnline;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, isOnline, password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && isOnline == other.isOnline
				&& Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}
	
	
	
	
	
}
