/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel;


/**
 * <!-- begin-user-doc -->
 * The element property transformer transforms a Property into an element using the
 * specified namespace and element name. If no namespace
 * is selected the defaultNamespace from the TransformModel is used.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.ElementPropertyTransformer#getElementName <em>Element Name</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.ElementPropertyTransformer#getNamespace <em>Namespace</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getElementPropertyTransformer()
 * @model
 * @generated
 */
public interface ElementPropertyTransformer extends SimplePropertyTransformer {
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
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getElementPropertyTransformer_ElementName()
	 * @model
	 * @generated
	 */
	String getElementName();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.ElementPropertyTransformer#getElementName <em>Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element Name</em>' attribute.
	 * @see #getElementName()
	 * @generated
	 */
	void setElementName(String value);

	/**
	 * Returns the value of the '<em><b>Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Namespace</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Namespace</em>' reference.
	 * @see #setNamespace(Namespace)
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getElementPropertyTransformer_Namespace()
	 * @model
	 * @generated
	 */
	Namespace getNamespace();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.ElementPropertyTransformer#getNamespace <em>Namespace</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Namespace</em>' reference.
	 * @see #getNamespace()
	 * @generated
	 */
	void setNamespace(Namespace value);

} // ElementPropertyTransformer