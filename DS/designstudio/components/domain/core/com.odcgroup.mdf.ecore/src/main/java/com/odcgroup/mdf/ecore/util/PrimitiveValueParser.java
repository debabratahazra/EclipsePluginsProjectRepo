package com.odcgroup.mdf.ecore.util;

import java.text.ParseException;
import java.util.Date;

import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.otf.utils.inet.MalformedUriException;
import com.odcgroup.otf.utils.inet.URI;
import com.odcgroup.otf.utils.lang.ISO8601;

/**
 * Class used to help parsing string values to MDF primitive types.
 * 
 * @author cve
 */
public class PrimitiveValueParser {

	/**
	 * Tells whether a given string value is null for the MDF.
	 * 
	 * @param value
	 *            the value to test.
	 * 
	 * @return <code>true</code> if the given string is either
	 *         <code>null</code> or equals to <code>"null"</code>,
	 *         <code>false</code> otherwise.
	 */
	public static boolean isNull(String value) {
		return (value == null) || "null".equals(value);
	}

	public static Object parse(MdfPrimitive type, String value)
			throws ParseException {
		return privateParse(type, value);
	}

	/**
	 * Parses a string value and convert it to the Java type corresponding to
	 * the given primitive type.
	 * 
	 * @param type
	 *            the MDF primitive type the string needs to be converted to.
	 * @param value
	 *            the string value to convert.
	 * @return the converted value object. May be <code>null</code> if the
	 *         string value is considered null for the MDF (see
	 *         {@link #isNull(String)}).
	 * @throws ParseException
	 * @throws ParseException
	 *             if the string value is invalid for the given MDF primitive
	 *             type.
	 */
	private static Object privateParse(MdfPrimitive type, String value)
			throws ParseException {
		if (isNull(value)) {
			return null;
		}

		try {
			if (type.equals(PrimitivesDomain.STRING)) {
				return value;
			} else if (type.equals(PrimitivesDomain.URI)) {
				return URI.parse(value);
			} else if (type.equals(PrimitivesDomain.BOOLEAN)
					|| type.equals(PrimitivesDomain.BOOLEAN_OBJ)) {
				if (value.equals("true")) {
					return Boolean.TRUE;
				} else if (value.equals("false")) {
					return Boolean.FALSE;
				} else {
					throw new ParseException(
							"Boolean values can only be \"true\" or \"false\"",
							0);
				}
			} else if (type.equals(PrimitivesDomain.BYTE)
					|| type.equals(PrimitivesDomain.BYTE_OBJ)) {
				return Byte.decode(value);
			} else if (type.equals(PrimitivesDomain.CHAR)
					|| type.equals(PrimitivesDomain.CHAR_OBJ)) {
				if (value.length() == 1) {
					return Character.valueOf(value.charAt(0));
				} else {
					throw new ParseException(
							"Character values cannot be more than 1 character long",
							1);
				}
			} else if (type.equals(PrimitivesDomain.DOUBLE)
					|| type.equals(PrimitivesDomain.DOUBLE_OBJ) 
					|| type.equals(PrimitivesDomain.DECIMAL)) {
				if (!"NaN".equalsIgnoreCase(value)) {
					return Double.valueOf(value);
				} else {
					return new Double(Double.NaN);
				}
			} else if (type.equals(PrimitivesDomain.FLOAT)
					|| type.equals(PrimitivesDomain.FLOAT_OBJ)) {
				if (!"NaN".equalsIgnoreCase(value)) {
					return Float.valueOf(value);
				} else {
					return new Float(Float.NaN);
				}
			} else if (type.equals(PrimitivesDomain.INTEGER)
					|| type.equals(PrimitivesDomain.INTEGER_OBJ)) {
				return Integer.valueOf(value);
			} else if (type.equals(PrimitivesDomain.LONG)
					|| type.equals(PrimitivesDomain.LONG_OBJ)) {
				return Long.valueOf(value);
			} else if (type.equals(PrimitivesDomain.SHORT)
					|| type.equals(PrimitivesDomain.SHORT_OBJ)) {
				return Short.valueOf(value);
			} else if (type.equals(PrimitivesDomain.DATE)) {
				if ("today()".equals(value)) {
					return new Date();
				} else {
					return ISO8601.parse(value);
				}
			} else if (type.equals(PrimitivesDomain.DATE_TIME)) {
				if ("now()".equals(value)) {
					return new Date();
				} else {
					return ISO8601.parse(value);
				}
			} else if (type instanceof MdfBusinessType) {
				return privateParse(((MdfBusinessType) type).getType(), value);
			} else {
				throw new ParseException(type.getQualifiedName()
						+ " is an unknown primitive type", 1);
			}

		} catch (NumberFormatException e) {
			throw newParseException(type, value, e);
		} catch (MalformedUriException e) {
			throw newParseException(type, value, e);
		} catch (ParseException e) {
			throw newParseException(type, value, e);
		}
	}

	private static ParseException newParseException(MdfPrimitive type,
			String value, Exception cause) {
		ParseException e = new ParseException(cause.getMessage(), 0);
		e.initCause(cause);
		return e;
	}

}
