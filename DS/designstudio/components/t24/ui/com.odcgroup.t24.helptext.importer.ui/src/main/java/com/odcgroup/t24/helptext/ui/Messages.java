package com.odcgroup.t24.helptext.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.odcgroup.t24.applicationimport.ui.messages"; //$NON-NLS-1$
	public static String FolderSelectionPage_description;
	public static String FolderSelectionPage_additional_desc;
	public static String FolderSelectionPage_message;
	public static String FolderSelectionPage_title;
	public static String FolderSelectionPage_browse;
	public static String HelptextImportTargetDialog_title;
	public static String HelptextImportWizard_title;
	public static String HelptextSelectionGroup_previewGroupLabel;
	public static String HelptextSelectionGroup_SelectAll;
	public static String HelptextSelectionGroup_UnselectAll;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
