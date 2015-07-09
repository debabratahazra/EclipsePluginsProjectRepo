package com.odcgroup.ds.t24.packager.generator;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;

public enum PackageTypeEnum {

	TAFC("tar", "windows-1252", "\u00fd", "\u00fc"), 
	TAFJ("jar", "UTF-8", "\uf8fd", "\uf8fc");

	private final String extension;
	private final String encoding;
	private final String fieldSeparator;
	private final String subFieldSeparator;

	private PackageTypeEnum(String extension, String encoding, String fieldSeparator, String subFieldSeparator) {
		this.extension = extension;
		this.encoding = encoding;
		this.fieldSeparator = fieldSeparator;
		this.subFieldSeparator = subFieldSeparator;
	}

	public static PackageTypeEnum parse(String stringToParse) {
		for (PackageTypeEnum type : values()) {
			if (type.name().equalsIgnoreCase(stringToParse)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Illegal package type: " + stringToParse + ". " +
				"Legal values: " + StringUtils.join(values(), ", ") + ".");
	}
	
	public String getExtension() {
		return extension;
	}

	public String getEncoding() {
		return encoding;
	}

	public String getFieldSeparator() {
		return fieldSeparator;
	}

	public String getSubFieldSeparator() {
		return subFieldSeparator;
	}
	
	public String getFieldSeparatorInAnotherEncoding(String anotherEncoding) {
		try {
			byte[] bytes = getFieldSeparator().getBytes(encoding);
			return new String(bytes, anotherEncoding);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
