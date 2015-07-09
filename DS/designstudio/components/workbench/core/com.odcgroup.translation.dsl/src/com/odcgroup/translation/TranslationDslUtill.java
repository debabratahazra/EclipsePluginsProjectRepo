package com.odcgroup.translation;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.translation.translationDsl.LocalTranslation;

public class TranslationDslUtill {
	
	/**
	 * @param translationList
	 * @return
	 */
	public static String getTranslationError(LocalTranslation translation) {
		StringBuffer message = new StringBuffer();
		message
			.append("Translation [")
			.append(translation.getText().replaceAll("\r","").replaceAll("\n",""))
			.append("]")
			.append(" cannot contain a Carriage Return ('\\r') or a Line Feed ('\\n').");
		return message.toString();
	}
	
	/**
     * check if the string contain CR or LF characters.
     * @param transaltionString
     */
	public static boolean isValidTranslation(LocalTranslation translation) {
		String text = translation.getText();
		if (StringUtils.isNotBlank(text)) {
			return !(text.contains("\r") ||  text.contains("\n"));
		}
		return true;
	}
}
