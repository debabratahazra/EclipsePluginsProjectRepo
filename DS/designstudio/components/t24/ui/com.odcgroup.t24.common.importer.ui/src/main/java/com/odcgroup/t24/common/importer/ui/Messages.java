package com.odcgroup.t24.common.importer.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.odcgroup.t24.common.importer.ui.messages"; //$NON-NLS-1$
	public static String FolderSelectionPage_description;
	public static String FolderSelectionPage_message;
	public static String FolderSelectionPage_title;
	public static String ServerSelectionGroup_columnServer;
	public static String ServerSelectionGroup_message;
	public static String ServerSelectionPage_description;
	public static String ServerSelectionPage_title;
	public static String ModelImportAction_name;
	public static String ModelImportReportDialog_title;
	public static String ModelImportWizard_cancelled;
	public static String ModelImportWizard_cancelMonitor;
	public static String ModelImportWizard_error;
	public static String ModelImportWizard_startMonitor;
	public static String ModelImportWizard_title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
