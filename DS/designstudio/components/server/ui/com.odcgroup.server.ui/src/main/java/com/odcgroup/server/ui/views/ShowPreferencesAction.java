package com.odcgroup.server.ui.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class ShowPreferencesAction extends Action {
	
	private String preferencesId;
	
	public ShowPreferencesAction(String preferencesId) {
		this.preferencesId = preferencesId;
	}

	public void run() {
		PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
				preferencesId, null, null);
		dialog.open();
	}	

}
