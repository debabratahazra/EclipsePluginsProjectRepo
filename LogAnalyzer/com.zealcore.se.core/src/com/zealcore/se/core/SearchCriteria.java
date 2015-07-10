/*
 * 
 */
package com.zealcore.se.core;

import java.lang.reflect.Method;

import org.eclipse.ui.IMemento;

import com.zealcore.se.core.ifw.assertions.internal.SearchMatcher;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.IProperty;
import com.zealcore.se.core.model.IType;

/**
 * SearchCriteria is the class for all search criteria contexts. There is one
 * search criteria for each searchable attribute in a class. This means that if
 * you would like to search for a task with a given name and a given id, you
 * will need two search critierias: one for the name and one for the id.
 * 
 * A search critiera consists of an attribute and a function. The attribute is
 * specified with botha name and type. E.g. String taskName, where String is the
 * type and taskName is the name of the attribute.
 * 
 * The conditions to search for, which is specified by a function consisting of
 * an operand and a binary operator. In current version, following binary
 * operators are supported: =, >=, <=, <>, !=, like
 * 
 * 
 * @see SearchAdapter
 * 
 * @author pasa
 * @version 1.0
 * @since 1.0
 * 
 */
public class SearchCriteria {
	static final String TAG_PROPERTY_ID = "attribute";

	static final String OPERATOR1 = "operator1";

	static final String OPERATOR2 = "operator2";

	static final String OPERAND1 = "operand1";

	static final String OPERAND2 = "operand2";

	static final String OPERAND_TYPE = "operand-type";

	static final String WILDCARD = "wildcard";

	static final String GREATER_THAN_EQUAL_TO_OPERAND = ">=";

	static final String LESS_THAN_OPERAND = "<";

	/**
	 * The property this criteria will use on objects to match operand and
	 * operator
	 */
	private IProperty property;

	/**
	 * The value to compare with. Supported types are the primitive CLASSES and
	 * String.
	 */
	private Object operand1;

	private Object operand2;

	private boolean wildcard;

	/*
	 * This string contains the binary comparison operator. Following binary
	 * operators are supported: >=, <
	 */
	private String binOperator1;

	private final String binOperator2 = LESS_THAN_OPERAND;

	private SearchCriteria(final IType parentType,
			final IMemento criteriaMemento) {

		// FIXME There are a number of possible errors
		// That callees could recover from (the actual SearchAdapter may be
		// lost, but the whole application do not have to crash just because 1
		// search criteria is errorneous)
		//
		final String propertyName = criteriaMemento
				.getString(SearchCriteria.TAG_PROPERTY_ID);
		for (final IProperty prop : parentType.getProperties()) {
			if (prop.getName().equals(propertyName)) {
				this.property = prop;
			}
		}

		if (this.property == null) {
			throw new IllegalStateException("Unable to find property "
					+ propertyName + " in " + parentType.getName() + ":"
					+ parentType.getId()
					+ " when reconstructing search-criteria");
		}

		this.binOperator1 = criteriaMemento.getString(SearchCriteria.OPERATOR1);

		final String strOperandType = criteriaMemento
				.getString(SearchCriteria.OPERAND_TYPE);
		final String strOperandValue1 = criteriaMemento
				.getString(SearchCriteria.OPERAND1);

		String strOperandValue2 = "";

		if (strOperandType.equals("java.lang.Long")
				|| strOperandType.equals("java.lang.Integer")
				|| strOperandType.equals("java.lang.Float")) {
			strOperandValue2 = criteriaMemento
					.getString(SearchCriteria.OPERAND2);
		}

		this.wildcard = criteriaMemento.getBoolean(SearchCriteria.WILDCARD);
		Class<?> clazz;
		try {
			clazz = Class.forName(strOperandType);
		} catch (final ClassNotFoundException noSuchClass) {
			// FIXME The user of this constructor must be notified
			// of this exception so that it can take proper measurements
			throw new RuntimeException(noSuchClass);
		}
		if (clazz.equals(String.class)) {
			this.operand1 = strOperandValue1;
			this.operand2 = strOperandValue2;
		} else {
			try {
				final Method method = clazz.getMethod("valueOf", String.class);
				if (strOperandValue1 != null && !strOperandValue1.equals("")) {
					this.operand1 = method.invoke(null, strOperandValue1);
				}

				if (strOperandValue2 != null && !strOperandValue2.equals("")) {
					this.operand2 = method.invoke(null, strOperandValue2);
				}
				// FIXME The user of this constructor must be notified
				// of these exception so that it can take proper measurements
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
		}

	}

	public SearchCriteria(final IProperty property) {
		this.property = property;
	}

	/**
	 * Returns the attribute attributeName
	 * 
	 * @return a string containing the attributeName of the search attribute
	 */
	public String getAttributeName() {
		return this.property.getName();
	}

	/**
	 * Returns the class of the attribute.
	 * 
	 * @return the attribute class type
	 */
	@SuppressWarnings("unchecked")
	public Class getAttributeClassType() {
		return this.property.getReturnType();
	}

	/**
	 * Returns the input argument of the search comparitor (operator)
	 * 
	 * @return the object reference of the search operand.
	 */
	public Object getOperand1() {
		return this.operand1;
	}

	public Object getOperand2() {
		return this.operand2;

	}

	/**
	 * Sets the input argument to the search comparator (operator). Supported
	 * types are primitives or Strings.
	 * 
	 * @param operand
	 *            - the operand to be used in the search criteria
	 */
	public void setOperand1(final Object operand1) {
		this.operand1 = operand1;
	}

	public void setOperand2(final Object operand2) {
		this.operand2 = operand2;
	}

	/**
	 * Returns the binary operator of this search critieria.
	 * 
	 * @return a string representation of the binary operator used in current
	 *         search critieria.
	 */
	public String getBinOperator1() {
		return this.binOperator1;
	}

	public String getBinOperator2() {
		return this.binOperator2;
	}

	/**
	 * Sets the binary operator of this search critieria.
	 * 
	 * @param binOperator
	 *            - a string representation of the binary operator the be used
	 *            in this search criteria. Supported operators are supported: =,
	 *            >=, <
	 */
	public void setOperator1(final String binOperator1) {
		this.binOperator1 = binOperator1;
	}

	/**
	 * toString method for SearchCriteria
	 */
	@Override
	public String toString() {
		if (getOperand2() == null || getBinOperator2() == null
				|| getOperand2().equals("") || getBinOperator2().equals("")) {
			return this.getClass().getSimpleName() + "[AttributeName="
					+ getAttributeName() + ",BinOperator" + getBinOperator1()
					+ ",Operand" + getOperand1() + ",AttributeClass"
					+ getAttributeClassType() + ']';
		} else {
			return this.getClass().getSimpleName() + "[AttributeName="
					+ getAttributeName() + ",BinOperator1" + getBinOperator1()
					+ ",BinOperator2" + getBinOperator2() + ",Operand1"
					+ getOperand1() + ",Operand2" + getOperand2()
					+ ",AttributeClass" + getAttributeClassType() + ']';
		}
	}

	void saveState(final IMemento criteriaMemento) {
		criteriaMemento.putString(SearchCriteria.TAG_PROPERTY_ID, this.property
				.getName());

		criteriaMemento.putString(SearchCriteria.OPERAND1, this.operand1
				.toString());

		if (this.operand1 != null && !"".equals(this.operand1)) {
			criteriaMemento.putString(SearchCriteria.OPERAND_TYPE,
					this.operand1.getClass().getName());
			if (this.binOperator1 == null
					&& (criteriaMemento.getString(SearchCriteria.OPERAND_TYPE)
							.equals("java.lang.Long")
							|| criteriaMemento.getString(
									SearchCriteria.OPERAND_TYPE).equals(
									"java.lang.Integer") || criteriaMemento
							.getString(SearchCriteria.OPERAND_TYPE).equals(
									"java.lang.Float"))) {
				criteriaMemento.putString(SearchCriteria.OPERATOR1, GREATER_THAN_EQUAL_TO_OPERAND);
						
			} else {
				criteriaMemento.putString(SearchCriteria.OPERATOR1, this.binOperator1);
						
			}

		}
		if (this.operand2 != null && !"".equals(this.operand2)) {
			criteriaMemento.putString(SearchCriteria.OPERAND_TYPE,
					this.operand2.getClass().getName());
			criteriaMemento.putString(SearchCriteria.OPERAND2, this.operand2
					.toString());
		}

		else {
			criteriaMemento.putString(SearchCriteria.OPERAND2, "");
		}

		if (criteriaMemento.getString(SearchCriteria.OPERAND_TYPE).equals(
				"java.lang.Long")
				|| criteriaMemento.getString(SearchCriteria.OPERAND_TYPE)
						.equals("java.lang.Integer")) {
			criteriaMemento.putString(SearchCriteria.OPERATOR2,
					this.binOperator2);
		}

		criteriaMemento.putString(SearchCriteria.WILDCARD, String
				.valueOf(this.wildcard));

	}

	public String getDescription() {
		return this.property.getDescription() == null ? "" : this.property
				.getDescription();
	}

	public String getName() {
		return this.property.getName();
	}

	public static SearchCriteria valueOf(final IType type,
			final IMemento memento) {
		return new SearchCriteria(type, memento);
	}

	private IProperty getProperty() {
		return this.property;
	}

	boolean matches(final IObject x) {
		if ((getOperand1() == null || getOperand1().equals(""))
				&& (getOperand2() == null || getOperand2().equals(""))) {
			return true;
		}
		final Object value = getProperty().getValue(x);
		if (value == null) {
			return false;
		}

		if (value instanceof Number) {
			boolean both = true;
			Number eventPropertyValue = (Number) value;
			Number operand1 = null;
			if (getOperand1() != null && !getOperand1().equals("")) {
				operand1 = (Number) getOperand1();
			}
			Number operand2 = null;
			if (getOperand2() != null && !getOperand2().equals("")) {
				operand2 = (Number) getOperand2();
			}

			if ((operand1 == null) || (operand2 == null)) {
				both = false;
			}
			if (both) {
				return SearchMatcher.match(eventPropertyValue, operand1,
						operand2, wildcard);

			} else {
				if (operand1 == null) {
					return SearchMatcher.match(eventPropertyValue, operand2,
							LESS_THAN_OPERAND, wildcard);
				} else if (operand2 == null) {
					return SearchMatcher.match(eventPropertyValue, operand1,
							GREATER_THAN_EQUAL_TO_OPERAND, wildcard);
				}
			}
		}
		return SearchMatcher.match(getOperand1().toString(), value.toString(),
				wildcard);
	}

	public void setWildcard(Boolean wildcard) {
		this.wildcard = wildcard;
	}

	public Boolean getWildcard() {
		return this.wildcard;
	}
}
