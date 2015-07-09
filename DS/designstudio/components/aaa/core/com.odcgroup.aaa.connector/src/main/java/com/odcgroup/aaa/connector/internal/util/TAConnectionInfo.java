package com.odcgroup.aaa.connector.internal.util;

/**
 * A simple value holder for "connection details" to a T'A database.
 * 
 * Introduced to avoid having to "expose" OpenJPA-specific property names,
 * with a too-loosely typed generic Properties contract.
 * 
 * This is intentionally, a higher-level abstraction than e.g. a JDBC
 * ConnectionURL, e.g. it's "presumes" Sybase and the driver name etc.
 * 
 * @author Michael Vorburger (MVO)
 */
public class TAConnectionInfo {

    private String hostname;
    private int port;
    private String charset;
    private String dbName;
    private String username;
    private String password;
    
    public String getHostname() {
        return hostname;
    }
    
    public TAConnectionInfo setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }
    
    public final String getCharset() {
    	return charset;
    }
    
    public TAConnectionInfo setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public int getPort() {
        return port;
    }
    
    public TAConnectionInfo setPort(int port) {
        this.port = port;
        return this;
    }
    
    public String getDBName() {
        return dbName;
    }

    
    public void setDBName(String dbName) {
        this.dbName = dbName;
    }

    public String getUsername() {
        return username;
    }
    
    public TAConnectionInfo setUsername(String username) {
        this.username = username;
        return this;
    }
    
    public TAConnectionInfo setPassword(String password) {
        this.password = password;
        return this;
    }

    /* For better security, the getter for password is package-local only and not public, intentionally! */
    String getPassword() {
        return password;
    }
}
