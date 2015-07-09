package com.odcgroup.iris.importer.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.odcgroup.iris.importer.ui.messages"; //$NON-NLS-1$
	public static String IRISModel_name;
	public static String IRISImportAction_name;
	public static String IRISImportReportDialog_title;
	public static String IRISImportWizard_cancelled;
	public static String IRISImportWizard_cancelMonitor;
	public static String IRISImportWizard_error;
	public static String IRISImportWizard_startMonitor;
	public static String IRISImportWizard_title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
