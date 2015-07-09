package com.odcgroup.ocs.server.external.ui.builder;


import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.odcgroup.ocs.server.ServerCore;
import com.odcgroup.server.ui.internal.CommonConsole;

/**
 * Access to the deploy console
 * @author yan
 */
public class DeployConsole extends CommonConsole {

    private boolean displayDebug = false;
    
    private boolean displayInfoDetail = true;
    
    protected String getScopeQualifier() {
    	return ServerCore.PLUGIN_ID;	
    }

	/* (non-Javadoc)
	 * @see com.odcgroup.ocs.server.internal.CommonConsole#consoleName()
	 */
	protected String consoleName() {
		return "Deployment Console";
	}
	
	public void printInfoDetail(String message) {
		if (displayInfoDetail) {
			getInfoConsoleStream().println(getTime() + " [INFO]: " + message);
		}
	}
	
	public void printDebug(String message) {
		if (displayDebug) {
			getDebugConsoleStream().println(getTime() + " [DEBUG]: " + message);
		}
	}
	
	public void setDisplayDebug(boolean displayDebug) {
		this.displayDebug = displayDebug;
	}

	public void setDisplayInfoDetail(boolean displayInfoDetail) {
		this.displayInfoDetail = displayInfoDetail;
	}

	@Override
	protected IPreferenceStore getPreferenceStore() {
		return new ScopedPreferenceStore(InstanceScope.INSTANCE, getScopeQualifier());
	}
}
