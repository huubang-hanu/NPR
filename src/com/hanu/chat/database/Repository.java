package com.hanu.chat.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hanu.chat.server.ClientThread;
import com.hanu.chat.util.Tag;
import com.hanu.chat.util.User;

/**
 * This is repo to store online user in server
 * 
 * @author Admin
 *
 */
public class Repository {
	private Map<String, ClientThread> userThreads = new HashMap<String, ClientThread>();
	private static Repository instance;
	private FileIO fileIO;
	private Set<User> users;

	private Repository() {
		fileIO = new FileIO();
		users = (HashSet<User>) fileIO.readDataFromFile(Tag.FILE_NAME);
		if (users == null) {
			users = new HashSet<User>();
			fileIO.saveDataToFile(users, Tag.FILE_NAME);
		}
	}

	/**
	 * Singleton repository
	 */
	public static Repository getRepository() {
		if (instance == null) {
			instance = new Repository();
		}
		return instance;
	}

	/**
	 * Get set of usernames
	 * 
	 * @return
	 */
	public String[] getUsernames() {
		List<String> onlineUsers = new ArrayList<String>();
		
		for (User user : users) {
			if(user.isOnline()) {
				onlineUsers.add(user.getUsername());
			}
		}
		
		return onlineUsers.toArray(String[]::new);
	}

	/**
	 * Get online client threads
	 * 
	 * @return
	 */
	public Collection<ClientThread> getClientThreads() {
		return userThreads.values();
	}


	public void addUser(String username, ClientThread client) {
		this.userThreads.put(username, client);
	}

	public boolean signIn(String username, String password) {
		for (User user : users) {
			if(user.getUsername().equals(username)&&user.getPassword().equals(password)) {
				user.setOnline(true);
				fileIO.saveDataToFile(this.users, Tag.FILE_NAME);
				return true;
			}
		}
		
		return false;
	}
	
	public boolean signUp(String username, String password) {
		if(!isUserExist(username)) {
			User user = new User(username, password, false);
			users.add(user);
			fileIO.saveDataToFile(this.users, Tag.FILE_NAME);
			return true;
		}
		
		return false;
	}
	
	public boolean isUserExist(String username) {
		for (User user : users) {
			if(user.getUsername().equals(username)) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * 
	 * @param username
	 */
	public void removeUser(String username) {
		this.userThreads.remove(username);
		for (User user : users) {
			if(user.getUsername().equals(username)) {
				user.setOnline(false);
			}
		}
		fileIO.saveDataToFile(this.users, Tag.FILE_NAME);
	}

	/**
	 * Find Client Thread by username
	 * 
	 * @param username
	 * @return
	 */
	public ClientThread getByUsername(String username) {
		return this.userThreads.get(username);
	}

	public boolean isOnline(String username) {
		ClientThread client = this.userThreads.get(username);
		if (client == null) {
			return false;
		} else {
			return true;
		}
	}

}
