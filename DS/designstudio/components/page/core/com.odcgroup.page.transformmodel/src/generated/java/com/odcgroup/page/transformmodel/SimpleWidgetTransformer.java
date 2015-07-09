/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel;

import com.odcgroup.page.metamodel.WidgetType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Widget Transformer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.SimpleWidgetTransformer#getWidgetType <em>Widget Type</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.SimpleWidgetTransformer#getNamespace <em>Namespace</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getSimpleWidgetTransformer()
 * @model abstract="true"
 * @generated
 */
public interface SimpleWidgetTransformer extends AbstractWidgetTransformer {
	/**
	 * Returns the value of the '<em><b>Widget Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Widget Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Widget Type</em>' reference.
	 * @see #setWidgetType(WidgetType)
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getSimpleWidgetTransformer_WidgetType()
	 * @model
	 * @generated
	 */
	WidgetType getWidgetType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.SimpleWidgetTransformer#getWidgetType <em>Widget Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Widget Type</em>' reference.
	 * @see #getWidgetType()
	 * @generated
	 */
	void setWidgetType(WidgetType value);

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
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getSimpleWidgetTransformer_Namespace()
	 * @model
	 * @generated
	 */
	Namespace getNamespace();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.SimpleWidgetTransformer#getNamespace <em>Namespace</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Namespace</em>' reference.
	 * @see #getNamespace()
	 * @generated
	 */
	void setNamespace(Namespace value);

} // SimpleWidgetTransformer