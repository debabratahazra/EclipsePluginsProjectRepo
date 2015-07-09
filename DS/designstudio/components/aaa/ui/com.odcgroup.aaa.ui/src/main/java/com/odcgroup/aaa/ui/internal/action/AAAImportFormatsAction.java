package com.odcgroup.aaa.ui.internal.action;

import org.eclipse.ui.IImportWizard;

import com.odcgroup.aaa.ui.AAAUIPlugin;
import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.aaa.ui.internal.wizard.AAAImportFormatsWizard;
import com.odcgroup.workbench.ui.OfsUICore;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class AAAImportFormatsAction extends AAAAction {
	
	/*
	 * @see com.odcgroup.mdf.aaa.integration.action.AAAAction#createImportWizard()
	 */
	protected IImportWizard createImportWizard() {
		return new AAAImportFormatsWizard();
	}
	
	/**
	 * 
	 */
	public AAAImportFormatsAction() {
		setText(Messages.getString("aaa.action.formats.name"));
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(AAAUIPlugin.PLUGIN_ID, "/icons/aaaformat.png"));
	}
	
}
