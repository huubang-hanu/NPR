package com.hanu.chat.util;

public class FileCustom {
	private String id;
	private String name;
	private byte[] data;
	
	public FileCustom(String id, String name, byte[] data) {
		this.id = id;
		this.name = name;
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	
	
	
	
}
