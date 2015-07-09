package com.odcgroup.aaa.ui.internal.action;

import org.eclipse.ui.IImportWizard;

import com.odcgroup.aaa.ui.AAAUIPlugin;
import com.odcgroup.aaa.ui.internal.wizard.AAASynchronizeImportWizard;
import com.odcgroup.workbench.ui.OfsUICore;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class AAASynchronizeAction extends AAAAction {
	
	/*
	 * @see com.odcgroup.mdf.aaa.integration.action.AAAAction#createImportWizard()
	 */
	protected IImportWizard createImportWizard() {
		return new AAASynchronizeImportWizard();
	}
	
	/**
	 * 
	 */
	public AAASynchronizeAction() {
		setText("Synchronize with Triple'A...");
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(AAAUIPlugin.PLUGIN_ID, "/icons/aaaformats.png"));
	}
	
}
