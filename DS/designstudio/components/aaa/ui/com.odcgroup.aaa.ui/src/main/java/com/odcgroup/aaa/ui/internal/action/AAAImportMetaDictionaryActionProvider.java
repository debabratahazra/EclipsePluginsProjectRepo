package com.odcgroup.aaa.ui.internal.action;

import org.eclipse.jface.action.Action;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class AAAImportMetaDictionaryActionProvider extends AAAActionProvider {

	/* 
	 * @see com.odcgroup.mdf.aaa.integration.action.AAAImportActionProvider#createImportAction()
	 */
	protected Action createImportAction() {
		return new AAAImportMetaDictionaryAction();
	}

	/*
	 * @see com.odcgroup.mdf.aaa.integration.action.AAAActionProvider#getGroupName()
	 */
	protected String getGroupName() {
		return "group.port";
	}
	
}
