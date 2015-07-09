package com.odcgroup.t24.server.external.model.internal;

import java.util.Properties;

import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.util.ServerPropertiesHelper;
import com.odcgroup.t24.server.external.util.T24AgentConnection;
import com.odcgroup.t24.server.external.util.T24AgentConnectionHelper;

abstract class ExternalLoader implements IExternalLoader {
	
	protected static final String RESPONSE_SEPARATOR = "#";
	protected static final String DESCRIPTION_RESPONSE_SEPARATOR = "# #";

	private T24AgentConnection connection;
	protected Properties properties;
	
	protected ExternalLoader(Properties properties) {
		this.properties = properties;
	}

	protected final T24AgentConnection getConnection() {
		return this.connection;
	}
	
	
	protected final String getHost() { 
		return ServerPropertiesHelper.getHost(properties);		
	}
	
	protected final String getAgentPort() { 
		return ServerPropertiesHelper.getAgentPort(properties);		
	}
	
	protected final String getUsername() { 
		return ServerPropertiesHelper.getUsername(properties);		
	}

	protected final String getPassword() { 
		return ServerPropertiesHelper.getPassword(properties);		
	}

	/**
	 * Open a connection with the external server
	 * @throws T24ServerException
	 */
	public void open() throws T24ServerException {
		String host = ServerPropertiesHelper.getHost(properties);
		String port = ServerPropertiesHelper.getAgentPort(properties);
		String user = ServerPropertiesHelper.getUsername(properties);
		String pswd = ServerPropertiesHelper.getPassword(properties);
		
		try {
			if (connection != null) {
				close();
			}
			connection = T24AgentConnectionHelper.getT24AgentConnection(host, port, user, pswd);
		} catch (NumberFormatException ex) {
			throw new T24ServerException("Invalid port number ["+port+"]", ex);
		}
	}

	/**
	 * Close a connection with the external server
	 */
	public void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (T24ServerException e) {
				// silently ignore
				// TODO maybe log the error
			}
			connection = null;
		}	
	}
	
}
