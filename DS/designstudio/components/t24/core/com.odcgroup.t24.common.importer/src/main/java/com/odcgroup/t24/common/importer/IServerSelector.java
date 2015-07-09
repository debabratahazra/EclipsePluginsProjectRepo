package com.odcgroup.t24.common.importer;

import com.odcgroup.t24.server.external.model.IExternalServer;

public interface IServerSelector extends ISelector { 

	/**
	 * @return
	 */
	IExternalServer getServer();

	/**
	 * @param server
	 */
	void setServer(IExternalServer server);
	
	/**
	 * @return
	 */
	boolean isServerSet();

}
