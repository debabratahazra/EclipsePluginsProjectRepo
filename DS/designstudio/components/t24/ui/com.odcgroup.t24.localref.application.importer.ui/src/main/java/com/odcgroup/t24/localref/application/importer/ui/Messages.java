package com.odcgroup.t24.localref.application.importer.ui;

import org.eclipse.osgi.util.NLS;

/**
 * @author hdebabrata
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.odcgroup.t24.localref.application.importer.ui.messages"; //$NON-NLS-1$
	public static String LocalRefApplication_Model_name;
	public static String LocalRefApplicationImportAction_name;
	public static String LocalRefApplicationImportReportDialog_title;
	public static String LocalRefApplicationImportWizard_cancelled;
	public static String LocalRefApplicationImportWizard_cancelMonitor;
	public static String LocalRefApplicationImportWizard_error;
	public static String LocalRefApplicationImportWizard_startMonitor;
	public static String LocalRefApplicationImportWizard_title;
	public static String LocalRefApplicationSelectionGroup_columnLocalRef;
	public static String LocalRefApplicationSelectionGroup_localRefDefaultName;
	public static String LocalRefApplicationSelectionGroup_previewGroupLabel;
	public static String LocalRefApplicationSelectionGroup_SelectAll;
	public static String LocalRefApplicationSelectionGroup_UnselectAll;
	public static String LocalRefApplicationSelectionPage_choiceAll;
	public static String LocalRefApplicationSelectionPage_description;
	public static String LocalRefApplicationSelectionPage_errorLocalRefName;
	public static String LocalRefApplicationSelectionPage_selectionGroupLabel;
	public static String LocalRefApplicationSelectionPage_title;
	public static String LocalRefApplicationSelectionPage_localRefLabel;
	public static String LocalRefApplicationProjectSelectionPage_title;
	public static String LocalRefApplicationProjectSelectionPage_description;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
