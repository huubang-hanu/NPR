package com.hanu.chat.util;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable{
	private String username;
	private String password;
	private boolean isOnline;
	
	
	public User(String username, String password, boolean isOnline) {
		super();
		this.username = username;
		this.password = password;
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

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	@Override
	public int hashCode() {
		return Objects.hash(isOnline, password, username);
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
		return Objects.equals(username, other.username);
	}
	
	@Override
	public String toString() {
		
		return username;
	}
}
