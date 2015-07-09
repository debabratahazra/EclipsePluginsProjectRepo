package com.odcgroup.ocs.server.embedded.model;

import com.odcgroup.workbench.core.AbstractActivator;

public class OcsServerEmbeddedCore extends AbstractActivator {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.ocs.server.embedded";  //$NON-NLS-1$
	
	public static OcsServerEmbeddedCore getDefault() {
		return (OcsServerEmbeddedCore) getDefault(OcsServerEmbeddedCore.class);
	}

}
