package com.odcgroup.ocs.server.embedded.model;

import com.odcgroup.server.model.IDSServer;

public interface IEmbeddedServer extends IDSServer {

	public String getVmArguments();
	
	public String getStarterClass();

}
