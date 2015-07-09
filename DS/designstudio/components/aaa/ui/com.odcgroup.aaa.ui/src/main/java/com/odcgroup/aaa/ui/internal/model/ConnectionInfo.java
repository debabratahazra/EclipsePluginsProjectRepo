package com.odcgroup.aaa.ui.internal.model;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class ConnectionInfo {

	private String server;
	private String database;
	private String port;
	private String charset;
	private String username;
	private String password;

	public final String getServer() {
		return server;
	}

	public final String getDatabase() {
		return database;
	}

	public final String getPort() {
		return port;
	}

	public final String getCharset() {
		return charset;
	}

	public final String getUsername() {
		return username;
	}

	public final String getPassword() {
		return password;
	}
	
	public int hashCode() {
		return this.server.hashCode()  
			 + this.database.hashCode()
		     + this.port.hashCode()
		     + this.charset.hashCode()
		     + this.username.hashCode()
		     + this.password.hashCode();
	}
	
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof ConnectionInfo) {
			ConnectionInfo info = (ConnectionInfo)other;
			return this.server.equals(info.server) 
				&& this.database.equals(info.database)
				&& this.port.equals(info.port)
				&& this.charset.equals(info.charset)
				&& this.username.equals(info.username)
				&& this.password.equals(info.password);
		}
		return false;
	}

	public ConnectionInfo(String server, String database, String port, String charset, String username, String password) {
		this.server = server.trim();
		this.database = database.trim();
		this.port = port.trim();
		this.charset = charset.trim();
		this.username = username;
		this.password = password;
	}

}
