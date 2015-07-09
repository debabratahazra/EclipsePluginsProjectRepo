/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel.impl;

import org.eclipse.emf.ecore.EClass;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.transformmodel.NullPropertyTransformer;
import com.odcgroup.page.transformmodel.TransformModelPackage;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Null Property Transformer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class NullPropertyTransformerImpl extends SimplePropertyTransformerImpl implements NullPropertyTransformer {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NullPropertyTransformerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformModelPackage.Literals.NULL_PROPERTY_TRANSFORMER;
	}

	/**
	 * Transforms the Property. This is a null implementation
	 * 
	 * @param context The WidgetTransformerContext
	 * @param property The Property to transform
	 */
	public void transform(WidgetTransformerContext context, Property property) {
	}	

} //NullPropertyTransformerImpl