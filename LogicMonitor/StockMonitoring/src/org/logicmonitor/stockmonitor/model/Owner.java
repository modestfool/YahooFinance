package org.logicmonitor.stockmonitor.model;

public class Owner {

	String username;
	String password;
	
	public Owner(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public Owner() {
		super();
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

	@Override
	public String toString() {
		return "User [username=" + username + "]";
	}
}
