package com.hanu.chat.util;

import java.io.Serializable;

public class Packet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tag;
	private String content;
	private String sender;
	private String receiver;
	
	/**
	 * 
	 * @param tag
	 * @param content
	 * @param sender
	 * @param receiver
	 */
	public Packet(String tag, String content, String sender, String receiver) {
		this.tag = tag;
		this.content = content;
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
	
	
}
