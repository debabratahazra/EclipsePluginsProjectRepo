package com.odcgroup.page.model.widgets.table;

/**
 * The interface ITableKeepFilter provides services to ease the management of a
 * KeepFilter in a Table Widget. In practice it will be used to implements
 * specific UI & related commands, and also to implements specific PSM
 * transformers.
 * <p>
 * 
 * @author atr
 * @since DS 1.40.0
 */
public interface ITableKeepFilter extends ITableFeature, IWidgetAdapter {

	/**
	 * Changes the column name
	 * 
	 * @param name
	 *            the new column name
	 */
	void setColumnName(String name);

	/**
	 * Changes the operator
	 * 
	 * @param operator
	 *            the new operator
	 */
	void setOperator(String operator);

	/**
	 * @return the operator
	 */
	String getOperator();

	/**
	 * Changes the value
	 * 
	 * @param value
	 *            the new value
	 */
	void setOperand(String value);

	/**
	 * @return the value
	 */
	String getOperand();

}
