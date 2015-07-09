/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Namespace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.Namespace#getUri <em>Uri</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.Namespace#getPrefix <em>Prefix</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.Namespace#getOrigin <em>Origin</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getNamespace()
 * @model
 * @generated
 */
public interface Namespace extends EObject {
	
	/**
	 * Constant who define that the namespace is defined by default
	 * @generated NOT 
	 * */
	public static final String DEFAULT_NAMESPACES = "metaModel";
	/**
	 * Constant who define that the namespace is defined by the user
	 * @generated NOT 
	 * */
	public static final String USER_DEFINED_NAMESPACES ="userDefined";
	/**
	 * Returns the value of the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uri</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uri</em>' attribute.
	 * @see #setUri(String)
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getNamespace_Uri()
	 * @model
	 * @generated
	 */
	String getUri();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.Namespace#getUri <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uri</em>' attribute.
	 * @see #getUri()
	 * @generated
	 */
	void setUri(String value);

	/**
	 * Returns the value of the '<em><b>Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Prefix</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Prefix</em>' attribute.
	 * @see #setPrefix(String)
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getNamespace_Prefix()
	 * @model
	 * @generated
	 */
	String getPrefix();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.Namespace#getPrefix <em>Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Prefix</em>' attribute.
	 * @see #getPrefix()
	 * @generated
	 */
	void setPrefix(String value);

	/**
	 * Returns the value of the '<em><b>Origin</b></em>' attribute.
	 * The default value is <code>"metaModel"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Origin</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Origin</em>' attribute.
	 * @see #setOrigin(String)
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getNamespace_Origin()
	 * @model default="metaModel"
	 * @generated
	 */
	String getOrigin();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.Namespace#getOrigin <em>Origin</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Origin</em>' attribute.
	 * @see #getOrigin()
	 * @generated
	 */
	void setOrigin(String value);

} // Namespace