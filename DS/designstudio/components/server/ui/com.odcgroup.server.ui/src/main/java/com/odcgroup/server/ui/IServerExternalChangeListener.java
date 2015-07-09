package com.odcgroup.server.ui;

import com.odcgroup.server.model.IDSServer;

public interface IServerExternalChangeListener {
	
	public void serverAddedExternally(IDSServer server);

	public void serverRemovedExternally(IDSServer server);

}
