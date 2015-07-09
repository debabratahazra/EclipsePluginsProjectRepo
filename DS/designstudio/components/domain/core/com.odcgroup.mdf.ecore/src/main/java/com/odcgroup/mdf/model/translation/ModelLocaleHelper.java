package com.odcgroup.mdf.model.translation;

public class ModelLocaleHelper {
	
	static ModelLocaleHelper TA_LOCALE_HELPER = new ModelLocaleHelper();
	
	private static final String[][] TA_JAVA_LOCALE_EXCEPTIONS = {
			{ "he", "iw" },
			{ "nb", "vls" },
			{ "tw", "zh_TW" },
			{ "zh", "zh_CN" }
	};

	public static ModelLocaleHelper getInstance() {
		return TA_LOCALE_HELPER;
	}
	
	public String convertToJavaLocale(String taLocale) {
		for (String[] localeException: TA_JAVA_LOCALE_EXCEPTIONS) {
			if (localeException[0].equals(taLocale)) {
				return localeException[1];
			}
		}
		return taLocale;
	}
	
	public String convertToTALocale(String javaLocale) {
		for (String[] localeException: TA_JAVA_LOCALE_EXCEPTIONS) {
			if (localeException[1].equals(javaLocale)) {
				return localeException[0];
			}
		}
		return javaLocale;
	}
	
}
