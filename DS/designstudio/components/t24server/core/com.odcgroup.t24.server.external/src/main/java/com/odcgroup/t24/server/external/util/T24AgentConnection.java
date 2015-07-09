package com.odcgroup.t24.server.external.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbase.jremote.JConnection;
import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JRemoteException;
import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

public class T24AgentConnection implements IT24Connection {

	static private final Logger logger = LoggerFactory.getLogger(T24AgentConnection.class);

	private JConnection connection;
	
	public interface T24Response {
		String getValue(int i);
		JSubroutineParameters getParameters();
	}
	
	public T24AgentConnection(JConnection connection) {
		this.connection = connection; 
	}
	
	/*default*/ JConnection getJConnection() {
		return connection;
	}
	
	public void close() throws T24ServerException {
		try {
			connection.close();
		} catch (JRemoteException e) {
			throw new T24ServerException("Unable to close the connection", e);
		}
	}
	
	public T24Response call(String operation, String... params) throws T24ServerException {
		JSubroutineParameters jParams = new JSubroutineParameters();
		for (String param: params) {
			if (param != null) {
				jParams.add(new JDynArray(param));
			} else {
				jParams.add(new JDynArray());
			}
		}
		
		try {
			final JSubroutineParameters response = connection.call(operation, jParams);
			return new T24Response() {
				public String getValue(int i) {
					return response.get(i).get(1);
				}
				public JSubroutineParameters getParameters(){
					return response;
				}
			};
		} catch (JRemoteException e) {
			final String msg = "Unable to call " + operation;
			logger.error(msg, e);
			throw new T24ServerException(msg, e);
		} 
		
	}

	@Override
	public void sendOfsMessage(String message) throws T24ServerException {
		String result = T24ServerDeployProtocolFacade.sendOfsMessage(this, message);
		if (!result.isEmpty()) {
			throw new T24ServerException(result);
		}
	}
	
	
}
