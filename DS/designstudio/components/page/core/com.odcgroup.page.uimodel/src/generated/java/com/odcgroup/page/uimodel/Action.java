/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel;

import com.odcgroup.page.metamodel.NamedType;
import com.odcgroup.page.metamodel.PropertyType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.Action#getProvider <em>Provider</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.Action#getProperty <em>Property</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.Action#getMode <em>Mode</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.Action#getId <em>Id</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.Action#getPropertyValue <em>Property Value</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.Action#getTemplateName <em>Template Name</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.Action#getDelegate <em>Delegate</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.Action#getAccelerator <em>Accelerator</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.Action#getPropertyType <em>Property Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.uimodel.UIModelPackage#getAction()
 * @model
 * @generated
 */
public interface Action extends NamedType {
	/**
	 * Returns the value of the '<em><b>Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provider</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider</em>' attribute.
	 * @see #setProvider(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getAction_Provider()
	 * @model
	 * @generated
	 */
	String getProvider();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.Action#getProvider <em>Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provider</em>' attribute.
	 * @see #getProvider()
	 * @generated
	 */
	void setProvider(String value);

	/**
	 * Returns the value of the '<em><b>Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' reference.
	 * @see #setProperty(PropertyType)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getAction_Property()
	 * @model
	 * @generated
	 */
	PropertyType getProperty();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.Action#getProperty <em>Property</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property</em>' reference.
	 * @see #getProperty()
	 * @generated
	 */
	void setProperty(PropertyType value);

	/**
	 * Returns the value of the '<em><b>Mode</b></em>' attribute.
	 * The default value is <code>"DesignMode"</code>.
	 * The literals are from the enumeration {@link com.odcgroup.page.uimodel.EditorMode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mode</em>' attribute.
	 * @see com.odcgroup.page.uimodel.EditorMode
	 * @see #setMode(EditorMode)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getAction_Mode()
	 * @model default="DesignMode"
	 * @generated
	 */
	EditorMode getMode();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.Action#getMode <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mode</em>' attribute.
	 * @see com.odcgroup.page.uimodel.EditorMode
	 * @see #getMode()
	 * @generated
	 */
	void setMode(EditorMode value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getAction_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.Action#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Property Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Value</em>' attribute.
	 * @see #setPropertyValue(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getAction_PropertyValue()
	 * @model
	 * @generated
	 */
	String getPropertyValue();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.Action#getPropertyValue <em>Property Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Value</em>' attribute.
	 * @see #getPropertyValue()
	 * @generated
	 */
	void setPropertyValue(String value);

	/**
	 * Returns the value of the '<em><b>Template Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Template Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Template Name</em>' attribute.
	 * @see #setTemplateName(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getAction_TemplateName()
	 * @model
	 * @generated
	 */
	String getTemplateName();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.Action#getTemplateName <em>Template Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Template Name</em>' attribute.
	 * @see #getTemplateName()
	 * @generated
	 */
	void setTemplateName(String value);

	/**
	 * Returns the value of the '<em><b>Delegate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delegate</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delegate</em>' attribute.
	 * @see #setDelegate(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getAction_Delegate()
	 * @model
	 * @generated
	 */
	String getDelegate();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.Action#getDelegate <em>Delegate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delegate</em>' attribute.
	 * @see #getDelegate()
	 * @generated
	 */
	void setDelegate(String value);

	/**
	 * Returns the value of the '<em><b>Accelerator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Accelerator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Accelerator</em>' attribute.
	 * @see #setAccelerator(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getAction_Accelerator()
	 * @model
	 * @generated
	 */
	String getAccelerator();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.Action#getAccelerator <em>Accelerator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Accelerator</em>' attribute.
	 * @see #getAccelerator()
	 * @generated
	 */
	void setAccelerator(String value);

	/**
	 * Returns the value of the '<em><b>Property Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Type</em>' reference.
	 * @see #setPropertyType(PropertyType)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getAction_PropertyType()
	 * @model
	 * @generated
	 */
	PropertyType getPropertyType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.Action#getPropertyType <em>Property Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Type</em>' reference.
	 * @see #getPropertyType()
	 * @generated
	 */
	void setPropertyType(PropertyType value);

} // Action
