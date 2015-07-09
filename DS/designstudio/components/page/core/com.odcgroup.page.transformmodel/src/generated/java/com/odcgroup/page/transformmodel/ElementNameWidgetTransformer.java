/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel;


/**
 * <!-- begin-user-doc -->
 * Transforms the Widget to the specified element name and namespace. If no namespace
 * is selected the defaultNamespace from the TransformModel is used.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.ElementNameWidgetTransformer#getElementName <em>Element Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getElementNameWidgetTransformer()
 * @model
 * @generated
 */
public interface ElementNameWidgetTransformer extends SimpleWidgetTransformer {
	/**
	 * Returns the value of the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element Name</em>' attribute.
	 * @see #setElementName(String)
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getElementNameWidgetTransformer_ElementName()
	 * @model
	 * @generated
	 */
	String getElementName();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.ElementNameWidgetTransformer#getElementName <em>Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element Name</em>' attribute.
	 * @see #getElementName()
	 * @generated
	 */
	void setElementName(String value);

} // ElementNameWidgetTransformer