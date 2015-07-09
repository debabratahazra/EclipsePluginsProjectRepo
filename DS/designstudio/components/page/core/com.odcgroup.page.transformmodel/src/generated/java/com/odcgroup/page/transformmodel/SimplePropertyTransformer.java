/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel;

import com.odcgroup.page.metamodel.PropertyType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Property Transformer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.SimplePropertyTransformer#getPropertyType <em>Property Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getSimplePropertyTransformer()
 * @model abstract="true"
 * @generated
 */
public interface SimplePropertyTransformer extends AbstractPropertyTransformer {
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
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getSimplePropertyTransformer_PropertyType()
	 * @model
	 * @generated
	 */
	PropertyType getPropertyType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.SimplePropertyTransformer#getPropertyType <em>Property Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Type</em>' reference.
	 * @see #getPropertyType()
	 * @generated
	 */
	void setPropertyType(PropertyType value);

} // SimplePropertyTransformer