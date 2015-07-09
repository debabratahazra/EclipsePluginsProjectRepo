/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel;



/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.PropertyType#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.PropertyType#getDataType <em>Data Type</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.PropertyType#getLibrary <em>Library</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.PropertyType#getCategory <em>Category</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.PropertyType#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.PropertyType#getSourceAdapter <em>Source Adapter</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.PropertyType#getSubCategory <em>Sub Category</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.PropertyType#isVisibleInDomain <em>Visible In Domain</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getPropertyType()
 * @model
 * @generated
 */
public interface PropertyType extends NamedType {
	/**
	 * Returns the value of the '<em><b>Default Value</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Value</em>' attribute.
	 * @see #setDefaultValue(String)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getPropertyType_DefaultValue()
	 * @model default=""
	 * @generated
	 */
	String getDefaultValue();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.PropertyType#getDefaultValue <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Value</em>' attribute.
	 * @see #getDefaultValue()
	 * @generated
	 */
	void setDefaultValue(String value);

	/**
	 * Returns the value of the '<em><b>Data Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Type</em>' reference.
	 * @see #setDataType(DataType)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getPropertyType_DataType()
	 * @model required="true"
	 * @generated
	 */
	DataType getDataType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.PropertyType#getDataType <em>Data Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Type</em>' reference.
	 * @see #getDataType()
	 * @generated
	 */
	void setDataType(DataType value);

	/**
	 * Returns the value of the '<em><b>Library</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.odcgroup.page.metamodel.WidgetLibrary#getPropertyTypes <em>Property Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Library</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Library</em>' container reference.
	 * @see #setLibrary(WidgetLibrary)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getPropertyType_Library()
	 * @see com.odcgroup.page.metamodel.WidgetLibrary#getPropertyTypes
	 * @model opposite="propertyTypes" transient="false"
	 * @generated
	 */
	WidgetLibrary getLibrary();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.PropertyType#getLibrary <em>Library</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Library</em>' container reference.
	 * @see #getLibrary()
	 * @generated
	 */
	void setLibrary(WidgetLibrary value);

	/**
	 * Returns the value of the '<em><b>Category</b></em>' attribute.
	 * The default value is <code>"None"</code>.
	 * The literals are from the enumeration {@link com.odcgroup.page.metamodel.PropertyCategory}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Category</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Category</em>' attribute.
	 * @see com.odcgroup.page.metamodel.PropertyCategory
	 * @see #setCategory(PropertyCategory)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getPropertyType_Category()
	 * @model default="None"
	 * @generated
	 */
	PropertyCategory getCategory();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.PropertyType#getCategory <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Category</em>' attribute.
	 * @see com.odcgroup.page.metamodel.PropertyCategory
	 * @see #getCategory()
	 * @generated
	 */
	void setCategory(PropertyCategory value);

	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getPropertyType_DisplayName()
	 * @model
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.PropertyType#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

    /**
	 * Returns the value of the '<em><b>Source Adapter</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source Adapter</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Adapter</em>' attribute.
	 * @see #setSourceAdapter(String)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getPropertyType_SourceAdapter()
	 * @model
	 * @generated
	 */
    String getSourceAdapter();

    /**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.PropertyType#getSourceAdapter <em>Source Adapter</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Adapter</em>' attribute.
	 * @see #getSourceAdapter()
	 * @generated
	 */
    void setSourceAdapter(String value);

				/**
	 * Returns the value of the '<em><b>Sub Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Category</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Category</em>' attribute.
	 * @see #setSubCategory(String)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getPropertyType_SubCategory()
	 * @model
	 * @generated
	 */
	String getSubCategory();

				/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.PropertyType#getSubCategory <em>Sub Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sub Category</em>' attribute.
	 * @see #getSubCategory()
	 * @generated
	 */
	void setSubCategory(String value);

				/**
	 * Returns the value of the '<em><b>Visible In Domain</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visible In Domain</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Visible In Domain</em>' attribute.
	 * @see #setVisibleInDomain(boolean)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getPropertyType_VisibleInDomain()
	 * @model default="false"
	 * @generated
	 */
	boolean isVisibleInDomain();

				/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.PropertyType#isVisibleInDomain <em>Visible In Domain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visible In Domain</em>' attribute.
	 * @see #isVisibleInDomain()
	 * @generated
	 */
	void setVisibleInDomain(boolean value);

} // PropertyType