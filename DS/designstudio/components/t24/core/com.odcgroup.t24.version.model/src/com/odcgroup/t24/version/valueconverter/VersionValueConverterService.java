package com.odcgroup.t24.version.valueconverter;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;

public class VersionValueConverterService extends DefaultTerminalConverters {

	private static final char ESCAPE = '^';
	private static final char SPACE = ' ';
	private static final String AUTO = "AUTO";
	private static final String ESCAPED_AUTO = ESCAPE + AUTO;

	@ValueConverter(rule = "NID")
	public IValueConverter<String> NID() {

		return new IValueConverter<String>() {

			@Override
			public String toValue(String string, INode node) throws ValueConverterException {
				int pos = string.indexOf(ESCAPED_AUTO);
				if (pos != -1) {
					// remove escape character '^'
					return string.substring(0, pos) + string.substring(pos + 1, string.length());
				}
				return string;
			}

			@Override
			public String toString(String value) throws ValueConverterException {
				int pos = value.indexOf(AUTO);
				if (pos == -1) {
					return value;
				}

				// check if AUTO is surrounded by spaces
				if (pos > 0 && value.charAt(pos - 1) != SPACE) {
					// contained in a word, do nothing
					return value;
				}
				if (pos + 4 < value.length() && value.charAt(pos + 4) != SPACE) {
					// contained in a word, do nothing
					return value;
				}
				return new StringBuilder(value).insert(pos, ESCAPE).toString();
			}

		};

	}

}
