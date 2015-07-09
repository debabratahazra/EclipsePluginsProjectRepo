package com.odcgroup.t24.server.iris.util;

import java.util.List;

import com.odcgroup.server.util.IServerManager;
import com.odcgroup.t24.server.iris.model.IIrisEmbeddedServer;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public interface IIrisServerManager extends IServerManager {
	
	public List<IIrisEmbeddedServer> getIrisServers();

}
