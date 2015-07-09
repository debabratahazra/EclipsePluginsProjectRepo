package com.odcgroup.workbench.el.valueconverter;

import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;

import ch.vorburger.el.valueconverter.ELValueConverterService;

import com.google.inject.Singleton;

@Singleton
public class DSELValueConverterService extends ELValueConverterService {
	
	@ValueConverter(rule = "OpEquality")
	public IValueConverter<String> getOpEqualityConverter() {
		return new DSELKeywordBasedValueConverter();
	}

	@ValueConverter(rule = "OpCompare")
	public IValueConverter<String> getOpCompareConverter() {
		return new DSELKeywordBasedValueConverter();
	}

	@ValueConverter(rule = "OpUnary")
	public IValueConverter<String> getOpUnaryConverter() {
		return new DSELKeywordBasedValueConverter();
	}

	@ValueConverter(rule = "OpOr")
	public IValueConverter<String> getOpOrConverter() {
		return new DSELKeywordBasedValueConverter();
	}

	@ValueConverter(rule = "OpAnd")
	public IValueConverter<String> getOpAndConverter() {
		return new DSELKeywordBasedValueConverter();
	}	
}
