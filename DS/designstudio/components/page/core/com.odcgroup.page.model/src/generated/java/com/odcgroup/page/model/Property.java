/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.model;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.page.metamodel.PropertyType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.model.Property#getValue <em>Value</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Property#isReadonly <em>Readonly</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Property#getTypeName <em>Type Name</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Property#getLibraryName <em>Library Name</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Property#getWidget <em>Widget</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Property#getModel <em>Model</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.model.ModelPackage#getProperty()
 * @model
 * @generated
 */
public interface Property extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.odcgroup.page.model.ModelPackage#getProperty_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Gets the value of the Property as an int, or raises the exception 
	 * <code>NumberFormatException</code> whenever the property value 
	 * as no integer representation.
	 * 
	 * @return int The integer value of the Property, or raises an exception
	 *             if it cannot be converted to an int. 
	 * 
	 * @exception java.lang.NumberFormatException
	 * @generated NOT
	 */
    int getIntValue();
    
	/**
	 * Gets the value of the Property as an long, or raises the exception 
	 * <code>NumberFormatException</code> whenever the property value 
	 * as no integer representation.
	 * 
	 * @return long The long value of the Property, or raises an exception
	 *             if it cannot be converted to an long. 
	 * 
	 * @exception java.lang.NumberFormatException
	 * @generated NOT
	 */
    long getLongValue();    

	/**
	 * Gets the value of the Property as a boolean.<p>
	 * 
	 * It returns <code>true</code> if the internal property value is equals to 
	 * "true" (case insensitive), otherwise it returns <code>false</code>.
	 * 
	 * @return boolean The boolean value of the Property 
	 * 
	 * @generated NOT
	 */
    boolean getBooleanValue();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.model.Property#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);
	
	/**
	 * Sets the value of the '{@link com.odcgroup.page.model.Property#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * The value is stored as a string
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @see #getIntValue()
	 * @generated NOT
	 */
	void setValue(int value);

	/**
	 * Sets the value of the '{@link com.odcgroup.page.model.Property#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * The value is stored as a string
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @see #getBooleanValue()
	 * @generated NOT
	 */
	void setValue(boolean value);

	/**
	 * Returns the value of the '<em><b>Readonly</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Readonly</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Readonly</em>' attribute.
	 * @see #setReadonly(boolean)
	 * @see com.odcgroup.page.model.ModelPackage#getProperty_Readonly()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isReadonly();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.model.Property#isReadonly <em>Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Readonly</em>' attribute.
	 * @see #isReadonly()
	 * @generated
	 */
	void setReadonly(boolean value);

	/**
	 * Returns the value of the '<em><b>Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Name</em>' attribute.
	 * @see #setTypeName(String)
	 * @see com.odcgroup.page.model.ModelPackage#getProperty_TypeName()
	 * @model
	 * @generated
	 */
	String getTypeName();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.model.Property#getTypeName <em>Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Name</em>' attribute.
	 * @see #getTypeName()
	 * @generated
	 */
	void setTypeName(String value);

	/**
	 * Returns the value of the '<em><b>Library Name</b></em>' attribute.
	 * The default value is <code>"xgui"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Library Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Library Name</em>' attribute.
	 * @see #setLibraryName(String)
	 * @see com.odcgroup.page.model.ModelPackage#getProperty_LibraryName()
	 * @model default="xgui"
	 * @generated
	 */
	String getLibraryName();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.model.Property#getLibraryName <em>Library Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Library Name</em>' attribute.
	 * @see #getLibraryName()
	 * @generated
	 */
	void setLibraryName(String value);

	/**
	 * Returns the value of the '<em><b>Widget</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.odcgroup.page.model.Widget#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Widget</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Widget</em>' container reference.
	 * @see #setWidget(Widget)
	 * @see com.odcgroup.page.model.ModelPackage#getProperty_Widget()
	 * @see com.odcgroup.page.model.Widget#getProperties
	 * @model opposite="properties" transient="false"
	 * @generated
	 */
	Widget getWidget();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.model.Property#getWidget <em>Widget</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Widget</em>' container reference.
	 * @see #getWidget()
	 * @generated
	 */
	void setWidget(Widget value);

	/**
	 * Returns the value of the '<em><b>Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model</em>' reference.
	 * @see #setModel(Model)
	 * @see com.odcgroup.page.model.ModelPackage#getProperty_Model()
	 * @model
	 * @generated
	 */
	Model getModel();
	
	/**
	 * @return the URI of the referenced model
	 * @generated NOT
	 */
	URI getModelURI();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.model.Property#getModel <em>Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model</em>' reference.
	 * @see #getModel()
	 * @generated
	 */
	void setModel(Model value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void resetValue();

	/**
	 * <!-- begin-user-doc -->
	 * @return boolean
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isValueSet();

	/**
	 * Returns true if the Property's current value is equal to its default value.
	 * 
	 * @return boolean True if the Property's current value is equal to its default value
	 */
	public boolean isDefaultValue();
	
	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getEditorName();

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getValidatorName();

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getConverterName();	
	
	/**
	 * Gets the PropertyType containing the meta-information about this Property.
	 * 
	 * @return PropertyType The PropertyType containing the meta-information about this Property
	 */
	public PropertyType getType();
	
	/**
	 * Gets the converted value of the Property.
	 * 
	 * @return Object The value of the Property converted using the
	 * correct Converter for the DataType
	 */
	public Object getConvertedValue();
	
} // Property