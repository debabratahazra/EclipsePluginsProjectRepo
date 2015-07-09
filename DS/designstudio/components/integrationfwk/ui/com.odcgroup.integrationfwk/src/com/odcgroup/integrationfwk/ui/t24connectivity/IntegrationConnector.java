package com.odcgroup.integrationfwk.ui.t24connectivity;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.core.runtime.IProgressMonitor;

import com.jbase.jremote.DefaultJConnectionFactory;
import com.jbase.jremote.JConnection;
import com.jbase.jremote.JRemoteException;
import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;

/**
 * singleton class, that creates connection to jbase_agent.
 * 
 * @author eswaranmuthu
 * 
 */
public class IntegrationConnector {
	private static String connectedHost;
	private DefaultJConnectionFactory jConnectionFactory;
	private JAgentConnection jagentConnection;
	static private IntegrationConnector integrationConnector;
	static private ConcurrentMap<String, JAgentConnection> connections = new ConcurrentHashMap<String, JAgentConnection>();

	private static boolean checkPortIp(ConfigEntity configEntity,
			JAgentConnection jagentConnection) throws JRemoteException {
		if (jagentConnection == null) {
			return false;
		}
		if (configEntity.getHostName().equals(jagentConnection.getHost())
				&& configEntity.getPortNumber() == jagentConnection.getPort()
				&& jagentConnection.getjConnection(false) != null) {
			return true;
		} else {
			disconnectJagent(jagentConnection);
			return false;
		}
	}

	private static boolean disconnectJagent(JAgentConnection jagentConnection) {
		try {
			JConnection jc = jagentConnection.getjConnection(false);
			jc.close();
			jagentConnection.setjConnection(null);
			jagentConnection = null;
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * factory method that instantiates object of this class.
	 * 
	 * @param configEntity
	 *            connection details required to establish the connection.
	 * @return
	 * @throws JRemoteException
	 */

	static public IntegrationConnector getIntegrationConnector(
			ConfigEntity configEntity, String projectName)
			throws JRemoteException {
		if (integrationConnector != null) {
			if (!(checkPortIp(configEntity, connections.get(projectName)))) {
				integrationConnector.setConnection(configEntity, projectName);
			}
			return integrationConnector;
		} else {
			integrationConnector = new IntegrationConnector();
			integrationConnector.setConnection(configEntity, projectName);
			return integrationConnector;
		}
	}

	/**
	 * closes the connection of jConnection.
	 * 
	 * @return
	 */
	public boolean disconnectJagent(String projectName) {
		JAgentConnection jagent = connections.get(projectName);
		return disconnectJagent(jagent);
	}

	public JConnection getConnection(String projectName,
			boolean fromBackGroundThread) {
		return connections.get(projectName)
				.getjConnection(fromBackGroundThread);
	}

	/**
	 * method that establishes connection to jbase_agent.
	 * 
	 * @param configEntity
	 * @throws JRemoteException
	 */
	private void setConnection(ConfigEntity configEntity, String projectName) {
		jConnectionFactory = new DefaultJConnectionFactory();
		jConnectionFactory.setHost(configEntity.getHostName());
		connectedHost = configEntity.getHostName();
		jConnectionFactory.setPort(configEntity.getPortNumber());
		try {
			Properties properties = new Properties();
			properties.put("env.OFS_SOURCE", configEntity.getOfsSource());
			JConnection jConnection = jConnectionFactory.getConnection("", "",
					properties);
			jConnection.call("JF.INITIALISE.CONNECTION", null);
			jagentConnection = new JAgentConnection(configEntity.getHostName(),
					configEntity.getPortNumber(), configEntity.getOfsSource(),
					jConnection);
			connections.put(projectName, jagentConnection);
		} catch (JRemoteException e) {
			disconnectJagent(projectName);
			TWSConsumerLog
					.logError(
							"Error connecting to T24 check that jbase_agent is runnning, verify host, port and OFS SOURCE settings",
							e);
		}
	}

	// new threads will span to fetch master data.
	public void startBackgroundThread(TWSConsumerProject twsProject,
			ConfigEntity configEntity, IProgressMonitor monitor) {
		new PopulateT24CacheViaJagent(twsProject, configEntity, monitor)
				.start();
	}
}