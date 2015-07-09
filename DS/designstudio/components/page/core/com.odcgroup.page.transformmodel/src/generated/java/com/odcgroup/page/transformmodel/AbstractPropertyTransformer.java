/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel;

import org.eclipse.emf.ecore.EObject;

import com.odcgroup.page.metamodel.PropertyType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Property Transformer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.AbstractPropertyTransformer#getModel <em>Model</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getAbstractPropertyTransformer()
 * @model abstract="true"
 * @generated NOT
 */
public interface AbstractPropertyTransformer extends PropertyTransformer, EObject {
	/**
	 * Returns the value of the '<em><b>Model</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.odcgroup.page.transformmodel.TransformModel#getPropertyTransformers <em>Property Transformers</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model</em>' container reference.
	 * @see #setModel(TransformModel)
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getAbstractPropertyTransformer_Model()
	 * @see com.odcgroup.page.transformmodel.TransformModel#getPropertyTransformers
	 * @model opposite="propertyTransformers"
	 * @generated
	 */
	TransformModel getModel();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.AbstractPropertyTransformer#getModel <em>Model</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model</em>' container reference.
	 * @see #getModel()
	 * @generated
	 */
	void setModel(TransformModel value);
	
	/**
	 * @return
	 */
	public PropertyType getPropertyType();

} // AbstractPropertyTransformer