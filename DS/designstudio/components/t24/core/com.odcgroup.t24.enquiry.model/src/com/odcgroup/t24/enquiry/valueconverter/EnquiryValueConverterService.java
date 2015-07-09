package com.odcgroup.t24.enquiry.valueconverter;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class EnquiryValueConverterService extends DefaultTerminalConverters {

	@Inject
	private BooleanValueConverter booleanValueConverter;

	@ValueConverter(rule = "Boolean")
	public IValueConverter<Boolean> getBooleanConverter() {
		return booleanValueConverter;
	}

	@ValueConverter(rule = "BooleanObject")
	public IValueConverter<Boolean> getBooleanObjectConverter() {
		return booleanValueConverter;
	}

}
