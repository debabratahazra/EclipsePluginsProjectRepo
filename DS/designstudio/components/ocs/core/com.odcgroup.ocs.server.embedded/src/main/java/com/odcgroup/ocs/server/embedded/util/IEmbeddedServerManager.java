package com.odcgroup.ocs.server.embedded.util;

import java.util.List;

import com.odcgroup.ocs.server.embedded.model.IEmbeddedServer;
import com.odcgroup.server.util.IServerManager;

public interface IEmbeddedServerManager extends IServerManager {
	
	public List<IEmbeddedServer> getEmbeddedServers();

}
