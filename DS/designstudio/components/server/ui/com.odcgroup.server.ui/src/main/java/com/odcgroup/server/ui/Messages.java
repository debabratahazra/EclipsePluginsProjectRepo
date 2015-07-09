package com.odcgroup.server.ui;

import org.eclipse.osgi.util.NLS;


public class Messages  extends NLS {

	protected static final String BUNDLE_NAME = "com.odcgroup.server.ui.messages";	 //$NON-NLS-1$

	static {
		//load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
	
	public static String ConsolePreferencePage_textDebugMessage;
	public static String ConsolePreferencePage_textErrorMessage;
	public static String ConsolePreferencePage_textInfoMessage;
	public static String ConsolePreferencePage_textWarningMessage;

	public static String ConsolePreferencePage_textColorsGroup;
	public static String ConsolePreferencePage_textLimitEnabled;
	public static String ConsolePreferencePage_textLimitValue;
	public static String ConsolePreferencePage_textShowInfo;
	public static String ConsolePreferencePage_textShowDebug;
	public static String ConsolePreferencePage_textShowWarning;
	public static String ConsolePreferencePage_textShowError;
	public static String ConsolePreferencePage_textShowOnGroup;
	public static String ConsolePreferencePage_textWrapEnabled;
	public static String ConsolePreferencePage_textWrapWidth;

}
