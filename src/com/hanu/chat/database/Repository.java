package com.hanu.chat.database;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.hanu.chat.server.ClientThread;
import com.hanu.chat.util.Tag;

/**
 * This is repo to store online user in server
 * 
 * @author Admin
 *
 */
public class Repository {
	private Map<String, ClientThread> users = new HashMap<String, ClientThread>();
	private static Repository instance;
	private FileIO fileIO;
	private Set<String> usernames;

	private Repository() {
		fileIO = new FileIO();
		usernames = (HashSet<String>) fileIO.readDataFromFile(Tag.FILE_NAME);
		if (usernames == null) {
			usernames = new HashSet<String>();
			fileIO.saveDataToFile(usernames, Tag.FILE_NAME);
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
	 * Get set of username
	 * 
	 * @return
	 */
	public String[] getUsernames() {
		return this.usernames.toArray(String[]::new);
	}

	/**
	 * Get online client threads
	 * 
	 * @return
	 */
	public Collection<ClientThread> getClients() {
		return users.values();
	}

	/**
	 * Sign up new user
	 * 
	 * @param username
	 * @param client
	 */
	public void addUser(String username, ClientThread client) {
		this.users.put(username, client);
		usernames.add(username);
		fileIO.saveDataToFile(usernames, Tag.FILE_NAME);
	}

	/**
	 * 
	 * @param username
	 */
	public void removeUser(String username) {
		this.users.remove(username);
		usernames.remove(username);
		fileIO.saveDataToFile(this.usernames, Tag.FILE_NAME);
	}

	/**
	 * Find Client Thread by username
	 * 
	 * @param username
	 * @return
	 */
	public ClientThread getByUsername(String username) {
		return this.users.get(username);
	}

	public boolean isExist(String username) {
		ClientThread client = this.users.get(username);
		if (client == null) {
			return false;
		} else {
			return true;
		}
	}

}
