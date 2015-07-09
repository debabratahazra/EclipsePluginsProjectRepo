package com.odcgroup.ocs.server.external.model;

import com.odcgroup.server.model.IDSServer;

/**
 * Interface of the external server managed by the ServerView
 */
public interface IExternalServer extends IDSServer {

	public String getInstallationDirectory();

	public String getStartScript();

	public String getStopScript();

	public String getReloadMessagesTouchFile();

	public boolean isDisplayAllDeployedFiles();

	public boolean startScriptExists();


}
