package com.hanu.chat.util;

import java.io.Serializable;

public class Packet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tag;
	private String content;
	private String[] onlineUsers;
	private String sender;
	private String receiver;
	private byte[] fileContent;
	
	public Packet(String tag, String content, String sender, String receiver) {
		this.tag = tag;
		this.content = content;
		this.sender = sender;
		this.receiver = receiver;
	}
	
	

	public Packet(String tag, String content, String sender, String receiver, byte[] fileContent) {
		super();
		this.tag = tag;
		this.content = content;
		this.sender = sender;
		this.receiver = receiver;
		this.fileContent = fileContent;
	}



	public Packet(String tag, String content, String[] onlineUsers, String sender, String receiver) {
		this.tag = tag;
		this.content = content;
		this.onlineUsers = onlineUsers;
		this.sender = sender;
		this.receiver = receiver;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getOnlineUsers() {
		return onlineUsers;
	}

	public void setOnlineUsers(String[] onlineUsers) {
		this.onlineUsers = onlineUsers;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}



	public byte[] getFileContent() {
		return fileContent;
	}



	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	
	
	
}
