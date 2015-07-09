package com.odcgroup.integrationfwk.ui.t24connectivity;

import java.util.Properties;

import com.jbase.jremote.DefaultJConnectionFactory;
import com.jbase.jremote.JConnection;
import com.jbase.jremote.JRemoteException;
import com.odcgroup.integrationfwk.ui.TWSConsumerLog;

public class JAgentConnection {

	private String host;
	private int port;
	private JConnection jConnection;
	private String ofsSource;

	public JAgentConnection(String host, int port, String ofsSource,
			JConnection jConnection) {

		this.host = host;
		this.port = port;
		this.jConnection = jConnection;
		this.ofsSource = ofsSource;
	}

	public String getHost() {
		return host;
	}

	private JConnection getjConnection() {
		DefaultJConnectionFactory jConnectionFactory = new DefaultJConnectionFactory();
		jConnectionFactory.setHost(host);
		jConnectionFactory.setPort(port);
		try {
			Properties properties = new Properties();
			properties.put("env.OFS_SOURCE", ofsSource);
			JConnection jConnection = jConnectionFactory.getConnection("", "",
					properties);
			jConnection.call("JF.INITIALISE.CONNECTION", null);
			return jConnection;
		} catch (JRemoteException e) {
			TWSConsumerLog
					.logError(
							"Error connecting to T24 check that jbase_agent is runnning, verify host, port and OFS SOURCE settings",
							e);
		}
		return jConnection;
	}

	public JConnection getjConnection(boolean fromBackGroundThread) {
		if (fromBackGroundThread || jConnection == null) {
			return getjConnection();
		} else {
			return jConnection;
		}
	}

	public String getOfsSource() {
		return ofsSource;
	}

	public int getPort() {
		return port;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setjConnection(JConnection jConnection) {
		this.jConnection = jConnection;
	}

	public void setOfsSource(String ofsSource) {
		this.ofsSource = ofsSource;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
