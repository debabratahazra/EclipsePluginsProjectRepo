/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel;


import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.DataType#getValues <em>Values</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.DataType#getEditorName <em>Editor Name</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.DataType#getValidatorName <em>Validator Name</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.DataType#getConverterName <em>Converter Name</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.DataType#isEditable <em>Editable</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getDataType()
 * @model
 * @generated
 */
public interface DataType extends NamedType {
	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.DataValue}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getDataType_Values()
	 * @model containment="true"
	 * @generated
	 */
	EList<DataValue> getValues();

	/**
	 * Returns the value of the '<em><b>Editor Name</b></em>' attribute.
	 * The default value is <code>"com.odcgroup.page.ui.properties.PropertyTextEditor"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Editor Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Editor Name</em>' attribute.
	 * @see #setEditorName(String)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getDataType_EditorName()
	 * @model default="com.odcgroup.page.ui.properties.PropertyTextEditor"
	 * @generated
	 */
	String getEditorName();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.DataType#getEditorName <em>Editor Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Editor Name</em>' attribute.
	 * @see #getEditorName()
	 * @generated
	 */
	void setEditorName(String value);

	/**
	 * Returns the value of the '<em><b>Validator Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Validator Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Validator Name</em>' attribute.
	 * @see #setValidatorName(String)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getDataType_ValidatorName()
	 * @model
	 * @generated
	 */
	String getValidatorName();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.DataType#getValidatorName <em>Validator Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validator Name</em>' attribute.
	 * @see #getValidatorName()
	 * @generated
	 */
	void setValidatorName(String value);

	/**
	 * Returns the value of the '<em><b>Converter Name</b></em>' attribute.
	 * The default value is <code>"com.odcgroup.page.model.converter.StringValueConverter"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Converter Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Converter Name</em>' attribute.
	 * @see #setConverterName(String)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getDataType_ConverterName()
	 * @model default="com.odcgroup.page.model.converter.StringValueConverter"
	 * @generated
	 */
	String getConverterName();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.DataType#getConverterName <em>Converter Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Converter Name</em>' attribute.
	 * @see #getConverterName()
	 * @generated
	 */
	void setConverterName(String value);
	
	/**
	 * Returns the value of the '<em><b>Editable</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * This properties is usefull for enumeration type and combo. If the DataType
	 * instance is editable then An Editable Combo should be generated. For single valued
	 * DataType the Editable flag is set to true by default.
	 * <p>
	 * If the meaning of the '<em>Editable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Editable</em>' attribute.
	 * @see #setEditable(boolean)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getDataType_Editable()
	 * @model default="true" required="true"
	 * @generated
	 */
	boolean isEditable();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.DataType#isEditable <em>Editable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Editable</em>' attribute.
	 * @see #isEditable()
	 * @generated
	 */
	void setEditable(boolean value);

	/**
	 * Finds the DataValue given the value.
	 * 
	 * @param value The value to find
	 * @return DataValue The DataValue or null if no value could be found
	 */
	public DataValue findDataValue(String value);

} // DataType