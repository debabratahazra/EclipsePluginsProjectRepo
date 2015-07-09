package com.odcgroup.server.ui.preferences;

import static com.odcgroup.server.preferences.ServerPreferences.CONSOLE_AUTOSHOW_TYPE_NAME;
import static com.odcgroup.server.preferences.ServerPreferences.CONSOLE_DEBUG_COLOR_DEFAULT;
import static com.odcgroup.server.preferences.ServerPreferences.CONSOLE_ERROR_COLOR_DEFAULT;
import static com.odcgroup.server.preferences.ServerPreferences.CONSOLE_INFO_COLOR_DEFAULT;
import static com.odcgroup.server.preferences.ServerPreferences.CONSOLE_LIMIT_ENABLED_DEFAULT;
import static com.odcgroup.server.preferences.ServerPreferences.CONSOLE_LIMIT_ENABLED_NAME;
import static com.odcgroup.server.preferences.ServerPreferences.CONSOLE_LIMIT_VALUE_DEFAULT;
import static com.odcgroup.server.preferences.ServerPreferences.CONSOLE_LIMIT_VALUE_NAME;
import static com.odcgroup.server.preferences.ServerPreferences.CONSOLE_WARNING_COLOR_DEFAULT;
import static com.odcgroup.server.preferences.ServerPreferences.CONSOLE_WRAP_ENABLED_DEFAULT;
import static com.odcgroup.server.preferences.ServerPreferences.CONSOLE_WRAP_ENABLED_NAME;
import static com.odcgroup.server.preferences.ServerPreferences.CONSOLE_WRAP_WIDTH_DEFAULT;
import static com.odcgroup.server.preferences.ServerPreferences.CONSOLE_WRAP_WIDTH_NAME;

import org.eclipse.jface.preference.IPreferenceStore;

import com.odcgroup.server.preferences.RGBColor;
import com.odcgroup.server.preferences.ServerPreferences;

public final class ServerUIPreferences {
	
	public static final String CONSOLE_AUTOSHOW_TYPE_NAME = ServerPreferences.CONSOLE_AUTOSHOW_TYPE_NAME;

	public static final String CONSOLE_DEBUG_COLOR_NAME = ServerPreferences.CONSOLE_DEBUG_COLOR_NAME;
	public static final String CONSOLE_ERROR_COLOR_NAME = ServerPreferences.CONSOLE_ERROR_COLOR_NAME;
	public static final String CONSOLE_INFO_COLOR_NAME = ServerPreferences.CONSOLE_INFO_COLOR_NAME;
	public static final String CONSOLE_WARNING_COLOR_NAME = ServerPreferences.CONSOLE_WARNING_COLOR_NAME;
	
	public static final int CONSOLE_AUTOSHOW_TYPE_INFO = ServerPreferences.CONSOLE_AUTOSHOW_TYPE_INFO;
	public static final int CONSOLE_AUTOSHOW_TYPE_DEBUG = ServerPreferences.CONSOLE_AUTOSHOW_TYPE_DEBUG;
	public static final int CONSOLE_AUTOSHOW_TYPE_WARNING = ServerPreferences.CONSOLE_AUTOSHOW_TYPE_WARNING;
	public static final int CONSOLE_AUTOSHOW_TYPE_ERROR = ServerPreferences.CONSOLE_AUTOSHOW_TYPE_ERROR;
	public static final int CONSOLE_AUTOSHOW_TYPE_DEFAULT = ServerPreferences.CONSOLE_AUTOSHOW_TYPE_DEFAULT;
	
	public static void resetToDefaultConsoleValues(IPreferenceStore store) {
		store.setDefault(ServerPreferences.fullConsoleName(CONSOLE_DEBUG_COLOR_NAME), CONSOLE_DEBUG_COLOR_DEFAULT.asString());
		store.setDefault(ServerPreferences.fullConsoleName(CONSOLE_ERROR_COLOR_NAME), CONSOLE_ERROR_COLOR_DEFAULT.asString());
		store.setDefault(ServerPreferences.fullConsoleName(CONSOLE_INFO_COLOR_NAME), CONSOLE_INFO_COLOR_DEFAULT.asString());
		store.setDefault(ServerPreferences.fullConsoleName(CONSOLE_WARNING_COLOR_NAME), CONSOLE_WARNING_COLOR_DEFAULT.asString());
		store.setDefault(ServerPreferences.fullConsoleName(CONSOLE_AUTOSHOW_TYPE_NAME), CONSOLE_AUTOSHOW_TYPE_DEFAULT+"");
		store.setDefault(ServerPreferences.fullConsoleName(CONSOLE_WRAP_ENABLED_NAME), CONSOLE_WRAP_ENABLED_DEFAULT+"");
		store.setDefault(ServerPreferences.fullConsoleName(CONSOLE_LIMIT_ENABLED_NAME), CONSOLE_LIMIT_ENABLED_DEFAULT+"");
		store.setDefault(ServerPreferences.fullConsoleName(CONSOLE_WRAP_WIDTH_NAME), CONSOLE_WRAP_WIDTH_DEFAULT+"");
		store.setDefault(ServerPreferences.fullConsoleName(CONSOLE_LIMIT_VALUE_NAME), CONSOLE_LIMIT_VALUE_DEFAULT+"");
	}
	
	public static RGBColor getDebugColor(IPreferenceStore store, boolean defaultValue) {
		String value = (defaultValue)
				? store.getDefaultString(ServerPreferences.fullConsoleName(CONSOLE_DEBUG_COLOR_NAME))
				: store.getString(ServerPreferences.fullConsoleName(CONSOLE_DEBUG_COLOR_NAME));
		return RGBColor.asRGBColor(value);
	}
	
	public static RGBColor getDebugColor(IPreferenceStore store) {
		return getDebugColor(store, false);
	}
	
	public static void setDebugColor(IPreferenceStore store, RGBColor color) {
		store.setValue(ServerPreferences.fullConsoleName(CONSOLE_DEBUG_COLOR_NAME), color.asString());
	}

	public static RGBColor getErrorColor(IPreferenceStore store, boolean defaultValue) {
		String value = (defaultValue) 
				? store.getDefaultString(ServerPreferences.fullConsoleName(CONSOLE_ERROR_COLOR_NAME))
				: store.getString(ServerPreferences.fullConsoleName(CONSOLE_ERROR_COLOR_NAME));
		return RGBColor.asRGBColor(value);
	}
	
	public static RGBColor getErrorColor(IPreferenceStore store) {
		return getErrorColor(store, false);

	}

	public static void setErrorColor(IPreferenceStore store, RGBColor color) {
		store.setValue(ServerPreferences.fullConsoleName(CONSOLE_ERROR_COLOR_NAME), color.asString());
	}

	public static RGBColor getInfoColor(IPreferenceStore store, boolean defaultValue) {
		String value = (defaultValue)
				? store.getDefaultString(ServerPreferences.fullConsoleName(CONSOLE_INFO_COLOR_NAME))
				: store.getString(ServerPreferences.fullConsoleName(CONSOLE_INFO_COLOR_NAME));
		return RGBColor.asRGBColor(value);
	}

	public static RGBColor getInfoColor(IPreferenceStore store) {
		return getInfoColor(store, false);
	}
	
	public static void setInfoColor(IPreferenceStore store, RGBColor color) {
		store.setValue(ServerPreferences.fullConsoleName(CONSOLE_INFO_COLOR_NAME), color.asString());
	}

	public static RGBColor getWarningColor(IPreferenceStore store, boolean defaultValue) {
		String value = (defaultValue)
				? store.getDefaultString(ServerPreferences.fullConsoleName(CONSOLE_WARNING_COLOR_NAME))
				: store.getString(ServerPreferences.fullConsoleName(CONSOLE_WARNING_COLOR_NAME));
		return RGBColor.asRGBColor(value);
	}
	
	public static RGBColor getWarningColor(IPreferenceStore store) {
		return getWarningColor(store, false);
	}
	
	public static void setWarningColor(IPreferenceStore store, RGBColor color) {
		store.setValue(ServerPreferences.fullConsoleName(CONSOLE_WARNING_COLOR_NAME), color.asString());
	}

	public static boolean isLimitEnabled(IPreferenceStore store, boolean defaultValue) {
		return (defaultValue)
				? store.getDefaultBoolean(ServerPreferences.fullConsoleName(CONSOLE_LIMIT_ENABLED_NAME))
				: store.getBoolean(ServerPreferences.fullConsoleName(CONSOLE_LIMIT_ENABLED_NAME));
	}

	public static boolean isLimitEnabled(IPreferenceStore store) {
		return isLimitEnabled(store, false);
	}

	public static void setLimitEnabled(IPreferenceStore store, boolean value) {
		store.setValue(ServerPreferences.fullConsoleName(CONSOLE_LIMIT_ENABLED_NAME), value);
	}

	public static boolean isWrapEnabled(IPreferenceStore store, boolean defaultValue) {
		return (defaultValue)
				? store.getDefaultBoolean(ServerPreferences.fullConsoleName(CONSOLE_WRAP_ENABLED_NAME))
				: store.getBoolean(ServerPreferences.fullConsoleName(CONSOLE_WRAP_ENABLED_NAME));
	}

	public static boolean isWrapEnabled(IPreferenceStore store) {
		return isWrapEnabled(store, false);
	}

	public static void setWrapEnabled(IPreferenceStore store, boolean value) {
		store.setValue(ServerPreferences.fullConsoleName(CONSOLE_WRAP_ENABLED_NAME), value);
	}

	public static int getAutoShow(IPreferenceStore store, boolean defaultValue) {
		return (defaultValue)
				? store.getDefaultInt(ServerPreferences.fullConsoleName(CONSOLE_AUTOSHOW_TYPE_NAME))
				: store.getInt(ServerPreferences.fullConsoleName(CONSOLE_AUTOSHOW_TYPE_NAME));
	}

	public static int getAutoShow(IPreferenceStore store) {
		return getAutoShow(store, false);
	}

	public static void setAutoShow(IPreferenceStore store, int type) {
		store.setValue(ServerPreferences.fullConsoleName(CONSOLE_AUTOSHOW_TYPE_NAME), type);
	}

	public static int getWrapWidth(IPreferenceStore store, boolean defaultValue) {
		return (defaultValue)
				? store.getDefaultInt(ServerPreferences.fullConsoleName(CONSOLE_WRAP_WIDTH_NAME))
				: store.getInt(ServerPreferences.fullConsoleName(CONSOLE_WRAP_WIDTH_NAME));
	}

	public static int getWrapWidth(IPreferenceStore store) {
		return getWrapWidth(store, false);
	}

	public static void setWrapWidth(IPreferenceStore store, int width) {
		store.setValue(ServerPreferences.fullConsoleName(CONSOLE_WRAP_WIDTH_NAME), width);
	}

	public static int getLimitValue(IPreferenceStore store, boolean defaultValue) {
		return (defaultValue)
				? store.getDefaultInt(ServerPreferences.fullConsoleName(CONSOLE_LIMIT_VALUE_NAME))
				: store.getInt(ServerPreferences.fullConsoleName(CONSOLE_LIMIT_VALUE_NAME));
	}

	public static int getLimitValue(IPreferenceStore store) {
		return getLimitValue(store, false);
	}

	public static void setLimitValue(IPreferenceStore store, int value) {
		store.setValue(ServerPreferences.fullConsoleName(CONSOLE_LIMIT_VALUE_NAME), value);
	}

	public static String fullConsoleName(String shortName) {
		return ServerPreferences.fullConsoleName(shortName);
	}

	
}
