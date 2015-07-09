package com.odcgroup.integrationfwk.ui.t24connectivity;

import com.jbase.jremote.JConnection;
import com.jbase.jremote.JRemoteException;
import com.jbase.jremote.JSubroutineNotFoundException;
import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.utils.LogConsole;

public abstract class IntegrationService {

	protected IntegrationConnector integrationConnector;
	protected JConnection jConnection;

	public IntegrationService(ConfigEntity configEntity, String projectName) {
		try {
			integrationConnector = IntegrationConnector
					.getIntegrationConnector(configEntity, projectName);
		} catch (JRemoteException e) {
			closeJConnection(projectName);
			TWSConsumerLog
					.logError(
							"Error connecting to T24 check that jbase_agent is runnning, verify host, port and OFS SOURCE settings",
							e);
		}
	}

	/**
	 * Constructs the Integration Service with the given connector.
	 * 
	 * @param integrationConnector
	 */
	protected IntegrationService(IntegrationConnector integrationConnector) {
		super();
		this.integrationConnector = integrationConnector;
	}

	protected void closeJConnection(String projectName) {
		integrationConnector.disconnectJagent(projectName);
	}

	abstract protected JSubroutineParameters constructjSubroutineRequest();

	protected JConnection getJConnection(String projectName,
			boolean fromBackGroundThread) {
		return integrationConnector.getConnection(projectName,
				fromBackGroundThread);
	}

	/**
	 * calls the routine with given argument and get response from T24.
	 * 
	 * @param jSubroutineRequest
	 *            input argument which is required to invoke the routine.
	 * @param landscapeService
	 *            name of a routine which is being invoked
	 * @return
	 */
	protected JSubroutineParameters getT24Response(
			JSubroutineParameters jSubroutineRequest, String landscapeService,
			String projectName, boolean fromBackGroundThread) {
		try {
			JSubroutineParameters jSubroutineResponse = getJConnection(
					projectName, fromBackGroundThread).call(landscapeService,
					jSubroutineRequest);
			return jSubroutineResponse;
		} catch (JSubroutineNotFoundException e) {
			TWSConsumerLog.logError("Subroutine \"" + landscapeService
					+ "\" not found ", e);
			LogConsole.getSoaConsole().logMessage(
					"Subroutine \"" + landscapeService + "\" not found ");
			return null;
		} catch (JRemoteException e) {
			TWSConsumerLog
					.logError(
							"Error connecting to T24 check that jbase_agent is runnning, verify host, port and OFS SOURCE settings",
							e);
			LogConsole
					.getSoaConsole()
					.logMessage(
							"Error connecting to T24 check that jbase_agent is runnning, verify host, port and OFS SOURCE settings");
			closeJConnection(projectName);
			return null;
		} catch (NullPointerException e) {
			TWSConsumerLog
					.logError(
							"Error connecting to T24 check that jbase_agent is runnning, verify host, port and OFS SOURCE settings",
							e);
			LogConsole
					.getSoaConsole()
					.logMessage(
							"Error connecting to T24 check that jbase_agent is runnning, verify host, port and OFS SOURCE settings");
			closeJConnection(projectName);
			return null;
		}
	}
}
