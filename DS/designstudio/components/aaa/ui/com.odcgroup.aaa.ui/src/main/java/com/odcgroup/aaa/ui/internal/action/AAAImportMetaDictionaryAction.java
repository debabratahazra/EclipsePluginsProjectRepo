package com.odcgroup.aaa.ui.internal.action;

import org.eclipse.ui.IImportWizard;

import com.odcgroup.aaa.ui.AAAUIPlugin;
import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.aaa.ui.internal.wizard.AAAImportMetaDictionaryWizard;
import com.odcgroup.workbench.ui.OfsUICore;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class AAAImportMetaDictionaryAction extends AAAAction {

	/*
	 * @see com.odcgroup.mdf.aaa.integration.action.AAAAction#createImportWizard()
	 */
	protected IImportWizard createImportWizard() {
		return new AAAImportMetaDictionaryWizard();
	}
	
	public AAAImportMetaDictionaryAction() {
		setText(Messages.getString("aaa.metadictimport.menuitem.title"));
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(AAAUIPlugin.PLUGIN_ID, "/icons/aaametadict.png"));
	}
	
}
