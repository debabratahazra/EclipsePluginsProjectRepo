package com.odcgroup.workbench.dsl.values;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.CharUtils;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractDeclarativeValueConverterService;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DSLValueConverterService extends AbstractDeclarativeValueConverterService {

	private static final Logger logger = LoggerFactory.getLogger(DSLValueConverterService.class);
	
	private final char[] idAllowedChars = new char[] {'_', '-', '.'};

	@ValueConverter(rule = "ID")
	public IValueConverter<String> ID() {
		return new IValueConverter<String>() {

			@Override
			public String toValue(String string, INode node) throws ValueConverterException {
				if(string != null && string.startsWith("^")) return string.substring(1);
				else return string;
			}

			@Override
			public String toString(String value) throws ValueConverterException {
				if(hasInvalidCharacters(value)) {
					logger.error("Cannot convert value '" + value + "' to String: ID contains invalid characters");
					throw new ValueConverterException("ID contains invalid characters!", null, null);
				}
				if(isKeyword(value)) return "^" + value;
				else return value;
			}
			
			protected boolean hasInvalidCharacters(String value) {
				if (value == null)
					return false;
				for (int i = 0; i < value.length(); i++) {
					char c = value.charAt(i);
					if (CharUtils.isAsciiAlphanumeric(c)) continue;
					if (ArrayUtils.contains(idAllowedChars, c))	continue;
					
					return true;
				}
				return false;
			}
		};
	}

	private final char[] stringAllowedChars = 
			new char[] {'_', '-', '&', '.', '/', '%', '\u00e9', '\u00e8', '\u00e0', '\u00e4', '\u00f6', '\u00fc', '\u00c4', '\u00d6', '\u00dc', '\u00c9', '\u00c8', '\u00c0', '\u00e7', '\u00df'};

	@ValueConverter(rule = "String_Value")
	public IValueConverter<String> String_Value() {
		return new IValueConverter<String>() {

			@Override
			public String toValue(String string, INode node) throws ValueConverterException {
				if(string==null) return null;
				if(string.startsWith("\"") && string.endsWith("\"")) {
					return Strings.convertFromJavaString(string.substring(1, string.length() - 1), true);
				} else {
					if(string.startsWith("^")) return string.substring(1);
					else return string;
				}
			}

			@Override
			public String toString(String value) throws ValueConverterException {
				if(value==null) return null;
				if(value.isEmpty() || hasInvalidCharacters(value) || isKeyword(value)) {
					value = Strings.convertToJavaString(value, false);
					return "\"" + value + "\"";
				} else {
					return value;
				}
			}
			
			protected boolean hasInvalidCharacters(String value) {
				if (value == null)
					return false;
				for (int i = 0; i < value.length(); i++) {
					char c = value.charAt(i);
					if (CharUtils.isAsciiAlphanumeric(c)) continue;
					if (ArrayUtils.contains(stringAllowedChars, c))	continue;
					
					return true;
				}
				return false;
			}

		};
	}
	
	protected boolean isKeyword(String value) {
		return false;
	}
}
