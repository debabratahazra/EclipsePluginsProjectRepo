package com.odcgroup.t24.localref.ui;

import org.eclipse.osgi.util.NLS;

/**
 * @author ssreekanth
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.odcgroup.t24.localref.ui.messages"; //$NON-NLS-1$
	public static String LocalRefModel_name;
	public static String LocalRefImportAction_name;
	public static String LocalRefImportReportDialog_title;
	public static String LocalRefImportWizard_cancelled;
	public static String LocalRefImportWizard_cancelMonitor;
	public static String LocalRefImportWizard_error;
	public static String LocalRefImportWizard_startMonitor;
	public static String LocalRefImportWizard_title;
	public static String LocalRefSelectionGroup_columnLocalRef;
	public static String LocalRefSelectionGroup_localRefDefaultName;
	public static String LocalRefSelectionGroup_previewGroupLabel;
	public static String LocalRefSelectionGroup_SelectAll;
	public static String LocalRefSelectionGroup_UnselectAll;
	public static String LocalRefSelectionPage_choiceAll;
	public static String LocalRefSelectionPage_description;
	public static String LocalRefSelectionPage_errorLocalRefName;
	public static String LocalRefSelectionPage_selectionGroupLabel;
	public static String LocalRefSelectionPage_title;
	public static String LocalRefSelectionPage_localRefLabel;
	public static String LocalRefProjectSelectionPage_title;
	public static String LocalRefProjectSelectionPage_description;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
