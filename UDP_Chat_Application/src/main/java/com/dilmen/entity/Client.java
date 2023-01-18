package com.dilmen.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.codec.digest.DigestUtils;

@Entity
public class Client {
	@Id
	@Column(unique = true)
	private String username;
	
	private String password;
	
	private int port;
	
	private boolean online = false;

	public Client() {
		super();
	}
	

	public Client(String username, String password, boolean online) {
		super();
		this.username = username;
		this.password = DigestUtils.sha256Hex(password);
		this.online = online;
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
		this.password = DigestUtils.sha256Hex(password);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}
	
	
	
}
