package com.odcgroup.server.preferences;

import org.osgi.service.prefs.Preferences;

public class ServerPreferences {
	
	public static final String CONSOLE_BASE = "preference.console."; //$NON-NLS-1$
	
	public static final String CONSOLE_AUTOSHOW_TYPE_NAME = "autoshow"; //$NON-NLS-1$
	public static final String CONSOLE_FONT_NAME = "font"; //$NON-NLS-1$
	public static final String CONSOLE_WRAP_ENABLED_NAME = "wrapEnabled"; //$NON-NLS-1$
	public static final String CONSOLE_WRAP_WIDTH_NAME = "wrapWidth"; //$NON-NLS-1$
	public static final String CONSOLE_LIMIT_ENABLED_NAME = "limitEnabled"; //$NON-NLS-1$
	public static final String CONSOLE_LIMIT_VALUE_NAME = "limitRange"; //$NON-NLS-1$
	
	public static final String CONSOLE_DEBUG_COLOR_NAME = "debug"; //$NON-NLS-1$
	public static final String CONSOLE_ERROR_COLOR_NAME = "error"; //$NON-NLS-1$
	public static final String CONSOLE_INFO_COLOR_NAME = "info"; //$NON-NLS-1$
	public static final String CONSOLE_WARNING_COLOR_NAME = "warning"; //$NON-NLS-1$
	
	public static final int CONSOLE_AUTOSHOW_TYPE_INFO    = 0x1;
	public static final int CONSOLE_AUTOSHOW_TYPE_DEBUG   = 0x2;
	public static final int CONSOLE_AUTOSHOW_TYPE_WARNING = 0x4;
	public static final int CONSOLE_AUTOSHOW_TYPE_ERROR   = 0x8;
	public static final int CONSOLE_AUTOSHOW_TYPE_DEFAULT = 
			CONSOLE_AUTOSHOW_TYPE_INFO | CONSOLE_AUTOSHOW_TYPE_DEBUG | CONSOLE_AUTOSHOW_TYPE_WARNING | CONSOLE_AUTOSHOW_TYPE_ERROR;
	
	public static final boolean CONSOLE_WRAP_ENABLED_DEFAULT = false;
	public static final int CONSOLE_WRAP_WIDTH_DEFAULT = 80;
	public static final boolean CONSOLE_LIMIT_ENABLED_DEFAULT = true;
	public static final int CONSOLE_LIMIT_VALUE_DEFAULT = 5000000;
	
	public static final RGBColor CONSOLE_DEBUG_COLOR_DEFAULT = new RGBColor(0, 0, 255);
	public static final RGBColor CONSOLE_ERROR_COLOR_DEFAULT = new RGBColor(255, 0, 0);
	public static final RGBColor CONSOLE_INFO_COLOR_DEFAULT = new RGBColor(0, 0, 0);
	public static final RGBColor CONSOLE_WARNING_COLOR_DEFAULT = new RGBColor(0, 200, 125);

	public static void setDefaultValues(Preferences node) {
		ServerPreferences.setDefaultConsoleValues(node);
	}

	private static void setDefaultConsoleValues(final Preferences node) {
		node.put(ServerPreferences.fullConsoleName(CONSOLE_DEBUG_COLOR_NAME), CONSOLE_DEBUG_COLOR_DEFAULT.asString());
		node.put(ServerPreferences.fullConsoleName(CONSOLE_ERROR_COLOR_NAME), CONSOLE_ERROR_COLOR_DEFAULT.asString());
		node.put(ServerPreferences.fullConsoleName(CONSOLE_INFO_COLOR_NAME), CONSOLE_INFO_COLOR_DEFAULT.asString());
		node.put(ServerPreferences.fullConsoleName(CONSOLE_WARNING_COLOR_NAME), CONSOLE_WARNING_COLOR_DEFAULT.asString());
		node.put(ServerPreferences.fullConsoleName(CONSOLE_AUTOSHOW_TYPE_NAME), CONSOLE_AUTOSHOW_TYPE_DEFAULT+"");
		node.put(ServerPreferences.fullConsoleName(CONSOLE_WRAP_ENABLED_NAME), CONSOLE_WRAP_ENABLED_DEFAULT+"");
		node.put(ServerPreferences.fullConsoleName(CONSOLE_LIMIT_ENABLED_NAME), CONSOLE_LIMIT_ENABLED_DEFAULT+"");
		node.put(ServerPreferences.fullConsoleName(CONSOLE_WRAP_WIDTH_NAME), CONSOLE_WRAP_WIDTH_DEFAULT+"");
		node.put(ServerPreferences.fullConsoleName(CONSOLE_LIMIT_VALUE_NAME), CONSOLE_LIMIT_VALUE_DEFAULT+"");
	}
	
	public static void resetToDefaultConsoleValues(Preferences node) {
		setDefaultConsoleValues(node);
	}
	
	public static String fullConsoleName(String shortName) {
		return ServerPreferences.CONSOLE_BASE + shortName;
	}
	
}
