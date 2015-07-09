/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generic Property Transformer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.GenericPropertyTransformer#getImplementationClass <em>Implementation Class</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getGenericPropertyTransformer()
 * @model
 * @generated
 */
public interface GenericPropertyTransformer extends SimplePropertyTransformer {
	/**
	 * Returns the value of the '<em><b>Implementation Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implementation Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implementation Class</em>' attribute.
	 * @see #setImplementationClass(String)
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getGenericPropertyTransformer_ImplementationClass()
	 * @model
	 * @generated
	 */
	String getImplementationClass();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.GenericPropertyTransformer#getImplementationClass <em>Implementation Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implementation Class</em>' attribute.
	 * @see #getImplementationClass()
	 * @generated
	 */
	void setImplementationClass(String value);

} // GenericPropertyTransformer