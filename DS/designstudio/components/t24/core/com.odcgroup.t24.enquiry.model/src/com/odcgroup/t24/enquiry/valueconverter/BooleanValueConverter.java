package com.odcgroup.t24.enquiry.valueconverter;

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

public class BooleanValueConverter extends AbstractNullSafeConverter<Boolean> {
	// NOTE: DS-8734 Must extend AbstractNullSafeConverter (IS-A AbstractValueConverter)
	// instead of AbstractLexerBasedConverter, because Boolean & BooleanObject
	// are data type rules and not terminal.

	@Override
	protected Boolean internalToValue(String string, INode node) throws ValueConverterException {
		if (Strings.isEmpty(string))
			throw new ValueConverterException("Couldn't convert empty string to boolean.", node, null);

		if ("true".equals(string.toLowerCase()) || "yes".equals(string.toLowerCase())) {
			return true;
		}

		if ("false".equals(string.toLowerCase()) || "no".equals(string.toLowerCase())) {
			return false;
		}

		throw new ValueConverterException("Couldn't convert '" + string + "' to boolean.", node, null);
	}

	@Override
	protected String internalToString(Boolean value) {
		return value.toString();
	}

}
