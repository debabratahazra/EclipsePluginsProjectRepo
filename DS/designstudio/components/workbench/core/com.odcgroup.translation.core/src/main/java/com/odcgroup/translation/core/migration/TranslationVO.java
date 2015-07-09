package com.odcgroup.translation.core.migration;

import org.apache.commons.lang.StringUtils;

/**
 * This VO contains all the data used to process a translation, as well
 * as a error status field to hold problem description when the vo 
 * couldn't be processed.
 * @author yan
 */
public class TranslationVO implements Comparable<TranslationVO> {
	public String key;
	public String language;
	public String text;
	public String errorStatus = "";
	
	public TranslationVO(String key, String language, String text) {
		if (StringUtils.isEmpty(text)) {
			throw new IllegalArgumentException("Argument text cannot be empty");
		}
		if (StringUtils.isEmpty(key)) {
			throw new IllegalArgumentException("Argument key cannot be empty");
		}
		if (StringUtils.isEmpty(language)) {
			throw new IllegalArgumentException("Argument language cannot be empty");
		}
		this.key = key;
		this.language = language;
		this.text = text;
	}

	public TranslationVO(String key, String language, String text, String errorStatus) {
		this(key, language, text);
		if (StringUtils.isEmpty(errorStatus)) {
			throw new IllegalArgumentException("Argument errorStatus cannot be empty");
		}
		this.errorStatus = errorStatus;
	}
	
	public int compareTo(TranslationVO other) {
		return key.compareTo(other.key);
	}
}

