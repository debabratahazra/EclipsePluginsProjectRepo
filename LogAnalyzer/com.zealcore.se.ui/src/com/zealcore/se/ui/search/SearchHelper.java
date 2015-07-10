package com.zealcore.se.ui.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.zealcore.se.core.SearchCriteria;
import com.zealcore.se.core.model.IArtifactID;
import com.zealcore.se.core.model.LogFile;

/**
 * Helper class for SearchAdapter. Supports operands and simple syntax checking.
 * 
 * @author stch
 * @version 1.0
 * @since 1.0
 */
class SearchHelper {

	private static final String UNHANDLED_CRITERIA_TYPE = "Unhandled criteria type: ";

	private static final int TWO = 2;

	private static final Pattern LONG_PATTERN = Pattern
			.compile("(-?[0-9]{1,})");

	private static final Pattern BOOLEAN_PATTERN = Pattern
			.compile("(\\=)?(-?(?i)(true)|(false){1,})");

	private static final Pattern FLOAT_PATTERN = Pattern
			.compile("(-?[0-9]{1,})(\\.[0-9]{1,})?");

	public static enum Type {
		INT, LONG, FLOAT, ARTIFACT, LOGFILE, UNKNOWN, BOOLEAN, SHORT
	}

	static boolean validateLong(final SearchString searchString) {
		boolean validText1 = false;
		boolean validText2 = false;
		Matcher m = null;

		if (searchString.getText1().trim().equals("")) {
			validText1 = true;
		} else {
			m = LONG_PATTERN.matcher(searchString.getText1());
			if (m.matches()) {
				try {
					Long.valueOf(m.group());
					validText1 = true;
				} catch (NumberFormatException ex) {
					validText1 = false;
				}
			}
		}

		if (searchString.getText2().trim().equals("")) {
			validText2 = true;
		} else {
			m = LONG_PATTERN.matcher(searchString.getText2());
			if (m.matches()) {
				try {
					Long.valueOf(m.group());
					validText2 = true;
				} catch (NumberFormatException ex) {
					validText2 = false;
				}
			}
		}

		return (validText1 && validText2);
	}

	static boolean validateInteger(final SearchString searchString) {
		boolean validText1 = false;
		boolean validText2 = false;
		Matcher m = null;

		if (searchString.getText1().trim().equals("")) {
			validText1 = true;
		} else {
			m = LONG_PATTERN.matcher(searchString.getText1());
			if (m.matches()) {
				try {
					Long.valueOf(m.group());
					validText1 = true;
				} catch (NumberFormatException ex) {
					validText1 = false;
				}
			}
		}

		if (searchString.getText2().trim().equals("")) {
			validText2 = true;
		} else {
			m = LONG_PATTERN.matcher(searchString.getText2());
			if (m.matches()) {
				try {
					Long.valueOf(m.group());
					validText2 = true;
				} catch (NumberFormatException ex) {
					validText2 = false;
				}
			}
		}

		return (validText1 && validText2);
	}

	static boolean validateFloat(final SearchString searchString) {

		boolean matches1 = false;
		if (searchString.getText1().trim().equals("")) {
			matches1 = true;
		} else {
			matches1 = FLOAT_PATTERN.matcher(searchString.getText1()).matches();
			if (matches1) {
				try {
					Float.valueOf(parseFloat(searchString.getText1())[0]);
				} catch (NumberFormatException ex) {
					return false;
				}
			}
		}

		boolean matches2 = false;
		if (searchString.getText2().trim().equals("")) {
			matches2 = true;
		} else {
			matches2 = FLOAT_PATTERN.matcher(searchString.getText2()).matches();
			if (matches2) {
				try {
					Float.valueOf(parseFloat(searchString.getText2())[0]);
				} catch (NumberFormatException ex) {
					return false;
				}
			}
		}

		return (matches1 && matches2);
	}

	/**
	 * Parses and assigns the String input to the criteria. The method will
	 * throw IllegalArgumentExecption if the input cannot be parsed properly.
	 * 
	 * @see SearchHelper#validateLong(SearchCriteria, String)
	 * @param criteria
	 *            the criteria to set input
	 * @param input
	 *            the input to parse and assign
	 */
	void set(final SearchCriteria criteria, SearchString searchString) {

		switch (SearchHelper.getCriteriaType(criteria)) {
		case SHORT:
		case LONG:
		case INT:
			setLong(criteria, searchString);
			break;
		case FLOAT:
			setFloat(criteria, searchString);
			break;
		case ARTIFACT:
			setArtifact(criteria, searchString);
			break;
		case LOGFILE:
			setArtifact(criteria, searchString);
			break;
		case BOOLEAN:
			setBoolean(criteria, searchString);
			break;
		case UNKNOWN:
		default:
			throw new IllegalArgumentException(
					SearchHelper.UNHANDLED_CRITERIA_TYPE
							+ criteria.getAttributeClassType());

		}
	}

	private void setBoolean(final SearchCriteria criteria,
			SearchString searchString) {
		if ("true".equalsIgnoreCase(searchString.getText1())) {
			criteria.setOperand1(Boolean.valueOf(searchString.getText1()));
		} else if ("false".equalsIgnoreCase(searchString.getText1())) {
			criteria.setOperand1(Boolean.valueOf(searchString.getText1()));
		} else {
			criteria.setOperand1("");
		}

	}

	private void setFloat(final SearchCriteria criteria,
			SearchString searchString) {

		if (!validateFloat(searchString)) {
			throw new IllegalArgumentException(searchString.getText1()
					+ " is not valid for type Float");
		} else {
			final String[] parts1 = parseFloat(searchString.getText1());
			if (parts1 != null) {
				criteria.setOperand1(Float.parseFloat(parts1[0]));
			} else {
				criteria.setOperand1("");
			}
		}

		if (searchString.getText2() != null) {
			if (!validateFloat(searchString)) {
				throw new IllegalArgumentException(searchString.getText2()
						+ " is not valid for type Long");
			} else {
				final String[] parts2 = parseFloat(searchString.getText2());
				if (parts2 != null) {
					criteria.setOperand2(Float.parseFloat(parts2[0]));
				} else {
					criteria.setOperand2("");
				}
			}
		}
	}

	static Type getCriteriaType(final SearchCriteria criteria) {
		final Class<?> type = criteria.getAttributeClassType();
		// If either long/Long or use long
		if (type == Long.class || type == long.class) {
			return Type.LONG;

		} else if (type == Integer.class || type == int.class
				|| type == Short.class || type == short.class) {
			return Type.INT;
		} else if (type == Float.class || type == float.class) {
			return Type.FLOAT;
		} else if (type == Double.class || type == double.class) {
			return Type.FLOAT;
		} else if (type == Short.class || type == short.class) {
			return Type.SHORT;

		} else if (type == boolean.class || type == Boolean.class) {
			return Type.BOOLEAN;
		} else if (IArtifactID.class.isAssignableFrom(criteria

		.getAttributeClassType())) {
			return Type.ARTIFACT;

		} else if (criteria.getAttributeClassType() == String.class) {
			// TODO Is this really correct?
			return Type.ARTIFACT;

		} else if (LogFile.class.isAssignableFrom(criteria
				.getAttributeClassType())) {
			// TODO Is this really correct?
			return Type.ARTIFACT;
		} else {
			throw new IllegalArgumentException(
					SearchHelper.UNHANDLED_CRITERIA_TYPE
							+ criteria.getAttributeClassType());
		}

	}

	void setArtifact(final SearchCriteria criteria, SearchString searchString) {
		criteria.setOperator1(" LIKE ");
		criteria.setOperand1(searchString.getText1());
		criteria.setOperand2(searchString.getText2());
		criteria.setWildcard(searchString.isChecked());

	}

	void setLong(final SearchCriteria criteria, SearchString searchString) {
		if (!validateLong(searchString)) {
			throw new IllegalArgumentException(searchString.getText1()
					+ " is not valid for type Long");
		} else {
			final String[] parts1 = parseLong(searchString.getText1());
			if (parts1 != null) {
				criteria.setOperand1(Long.parseLong(parts1[0]));
			} else {
				criteria.setOperand1("");
			}
		}

		if (searchString.getText2() != null) {
			if (!validateLong(searchString)) {
				throw new IllegalArgumentException(searchString.getText2()
						+ " is not valid for type Long");
			} else {
				final String[] parts2 = parseLong(searchString.getText2());
				if (parts2 != null) {
					criteria.setOperand2(Long.parseLong(parts2[0]));
				} else {
					criteria.setOperand2("");
				}
			}
		}
	}

	void setInteger(final SearchCriteria criteria, SearchString searchString) {
		if (!validateLong(searchString)) {
			throw new IllegalArgumentException(searchString.getText1()
					+ " is not valid for type Long");
		} else {

			final String[] parts1 = parseLong(searchString.getText1());

			if (parts1 != null) {
				criteria.setOperand1(Long.parseLong(parts1[0]));
			} else {
				criteria.setOperand1("");
			}
		}

		if (searchString.getText2() != null) {
			if (!validateLong(searchString)) {
				throw new IllegalArgumentException(searchString.getText2()
						+ " is not valid for type Long");
			} else {
				final String[] parts2 = parseLong(searchString.getText2());
				if (parts2 != null) {
					criteria.setOperand2(Long.parseLong(parts2[0]));
				} else {
					criteria.setOperand2("");
				}
			}
		}
	}

	/**
	 * Parses the string input to two parts containing the operator in the first
	 * and the operand in the second For instance ">=10" becomes {">=","10"}. If
	 * no operator is set, it will default to "="
	 * 
	 * @param searchString
	 *            the input to parse
	 * @return String array with {operator, operand}
	 */
	static String[] parseLong(final String input) {
		final String[] parts = new String[SearchHelper.TWO];
		try {
			if (input != null && !input.trim().equals("")) {
				final Matcher m = LONG_PATTERN.matcher(input.trim());
				if (m.matches()) {
					if (m.group(1) != null) {
						parts[0] = m.group(1);
					} else {
						parts[0] = "";
					}
					return parts;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	static String[] parseFloat(final String input) {
		final String[] parts = new String[SearchHelper.TWO];
		try {
			if (input != null && !input.trim().equals("")) {
				final Matcher m = FLOAT_PATTERN.matcher(input.trim());
				if (m.matches()) {
					if (m.group(1) != null) {
						parts[0] = m.group(1);
					} else {
						parts[0] = "";
					}
					if (m.group(TWO) != null) {
						parts[0] += m.group(TWO);
					}
					return parts;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Parses the string input to two parts containing the operator in the first
	 * and the operand in the second For instance ">=10" becomes {">=","10"}. If
	 * no operator is set, it will default to "="
	 * 
	 * @param input
	 *            the input to parse
	 * @return String array with {operator, operand}
	 */
	static String[] parseBoolean(final String input) {

		final Matcher m = BOOLEAN_PATTERN.matcher(input.trim());
		m.find();

		final String[] parts = new String[SearchHelper.TWO];
		if (m.group(1) != null) {
			parts[0] = m.group(1);
		} else {
			parts[0] = "";
		}
		return parts;
	}

	/**
	 * Checks if the input value is ok to put into the SearchCriteria using the
	 * {@link SearchHelper}{@link #set(SearchCriteria, String)}.
	 * 
	 * @param criteria
	 *            the criteria
	 * @param searchString
	 *            the value
	 * 
	 * @return true, if value is ok
	 */
	static boolean isOk(final SearchCriteria criteria,
			final SearchString searchString) {
		if ((searchString.getText1() == null || searchString.getText1().trim()
				.equals(""))
				&& (searchString.getText2() == null || searchString.getText2()
						.trim().equals(""))) {
			return true;
		}

		switch (SearchHelper.getCriteriaType(criteria)) {
		case SHORT:
		case LONG:
			return validateLong(searchString);
		case INT:
			return validateInteger(searchString);
		case FLOAT:
			return validateFloat(searchString);
		case ARTIFACT:
			return SearchHelper.checkValidRegExp(searchString);
		case LOGFILE:
			return SearchHelper.checkValidRegExp(searchString);
		case BOOLEAN:
			return SearchHelper.validateBoolean(searchString);
		case UNKNOWN:
		default:
			throw new IllegalArgumentException("Unknown criteria type: "
					+ criteria.getAttributeClassType());

		}
	}

	private static boolean validateBoolean(final SearchString searchString) {
		final Matcher m = BOOLEAN_PATTERN.matcher((CharSequence) searchString
				.getText1());
		boolean matches = m.matches();
		if (matches) {
			try {
				Boolean.valueOf(parseBoolean(searchString.getText1())[0]);
			} catch (NumberFormatException ex) {
				return false;
			}
		}
		return matches;
	}

	private static boolean checkValidRegExp(final SearchString searchString) {

		boolean validate1 = false;
		boolean validate2 = false;

		try {
			if (searchString.getText1() != null
					&& !searchString.getText1().trim().equals("")) {
				Pattern.compile(searchString.getText1());
				validate1 = true;
			}
			if (searchString.getText2() != null
					&& !searchString.getText2().trim().equals("")) {
				Pattern.compile(searchString.getText2());
				validate2 = true;
			}
		} catch (final PatternSyntaxException ex) {
			return false;
		}
		return (validate1 || validate2);
	}

}
