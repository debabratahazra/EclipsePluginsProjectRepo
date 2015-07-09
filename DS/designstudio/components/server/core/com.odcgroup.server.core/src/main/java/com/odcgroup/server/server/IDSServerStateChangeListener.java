package com.odcgroup.server.server;

import com.odcgroup.server.model.IDSServer;

/**
 * @author yan
 */
public interface IDSServerStateChangeListener {

	public void serverStateChanged(IDSServer server);
}
