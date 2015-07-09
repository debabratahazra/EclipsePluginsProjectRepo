package com.odcgroup.t24.server.iris.model;

import com.odcgroup.server.model.IDSServer;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public interface IIrisEmbeddedServer extends IDSServer {

	public String getProgArguments(IIrisEmbeddedServer irisServer);
	
	public String getVmArguments();

	public String getStarterClass();

}
