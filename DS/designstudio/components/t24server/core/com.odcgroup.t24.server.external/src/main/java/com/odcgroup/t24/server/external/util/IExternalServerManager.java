package com.odcgroup.t24.server.external.util;

import java.util.List;

import com.odcgroup.server.util.IServerManager;
import com.odcgroup.t24.server.external.model.IExternalServer;

public interface IExternalServerManager extends IServerManager {
	
	public List<IExternalServer> getExternalServers();
	
}
