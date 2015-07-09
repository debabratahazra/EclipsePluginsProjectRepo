package com.odcgroup.page.values;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.dsl.values.DSLValueConverterService;

public class PageValueConverterService extends DSLValueConverterService {

	private static final Logger logger = LoggerFactory.getLogger(PageValueConverterService.class);
	
	@ValueConverter(rule = "ID")
	public IValueConverter<String> ID() {
		return new IValueConverter<String>() {

			@Override
			public String toValue(String string, INode node) throws ValueConverterException {
				return (string != null && string.startsWith("^"))
					? string.substring(1)
				    : string;
			}

			@Override
			public String toString(String value) throws ValueConverterException {
				return (isKeyword(value)) 
						? "^" + value 
						: value;
			}
			
		};
	}

//	@ValueConverter(rule = "String_Value")
//	public IValueConverter<String> String_Value() {
//		return new IValueConverter<String>() {
//			@Override
//			public String toValue(String string, INode node) throws ValueConverterException {
//				if (string==null) return null;
//				if (string.startsWith("\"") && string.endsWith("\"")) {
//					return Strings.convertFromJavaString(string.substring(1, string.length() - 1), true);
//				} else {
//					if(string.startsWith("^")) return string.substring(1);
//					else return string;
//				}
//			}
//
//			@Override
//			public String toString(String value) throws ValueConverterException {
//				if (value == null) return null;
//				if(value.isEmpty() || isKeyword(value)) {
//					value = Strings.convertToJavaString(value, false);
//					return "\"" + value + "\"";
//				} else {
//					return value;
//				}
//			}
//			
//		};
//	}
	
	@ValueConverter(rule = "LibraryName")
	public IValueConverter<String> LibraryName() {
		return new IValueConverter<String>() {

			@Override
			public String toValue(String string, INode node)
					throws ValueConverterException {
				return string;
			}

			@Override
			public String toString(String value) throws ValueConverterException {
				if ("xgui".equals(value))
					return null;
				else
					return value;
			}

		};
	}

	@ValueConverter(rule = "Timestamp")
	public IValueConverter<java.util.Date> TimestampValue() {
		return new AbstractNullSafeConverter<Date>() {

			@Override
			protected String internalToString(Date value) {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssZ");
				fmt.setTimeZone(TimeZone.getTimeZone("UTC"));
				return '"' + fmt.format(value) + '"';
			}

			@Override
			protected Date internalToValue(String string, INode node)
					throws ValueConverterException {
				string = string.substring(1, string.length() - 1);

				try {
					SimpleDateFormat fmt = new SimpleDateFormat(
							"yyyyMMddHHmmssZ");
					fmt.setTimeZone(TimeZone.getTimeZone("UTC"));
					return fmt.parse(string);
				} catch (ParseException e) {
					logger.error("Cannot convert string '" + string + "' to Date", e);
					throw new ValueConverterException(
							"Not in valid format: Use 'yyyyMMddHHmmssZ' Parse error:"
									+ e.getMessage(), node, null);
				}
			}
		};
	}

	private final String[] keywords = 
			new String[] { "modifiedBy", "modifiedDate", "metamodelVersion", "labels",
			"toolTips", "ro", "Snippet", "A", "S", "ud" };
	
	@Override
	protected boolean isKeyword(String value) {
		return ArrayUtils.contains(keywords, value);
	}

}
