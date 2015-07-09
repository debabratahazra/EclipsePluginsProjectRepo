/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel;

import org.eclipse.core.runtime.CoreException;

import com.odcgroup.page.model.Property;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Transformer</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getPropertyTransformer()
 * @model interface="true" abstract="true"
 * @generated NOT
 */
public interface PropertyTransformer /*extends EObject*/ {
	
	/**
	 * Returns true if the PropertyTransformer is designed to transform the specified PropertyType.
	 * 
	 * @param property The Property to test
	 * @return boolean True if the PropertyTransformer is designed to transform the specified PropertyType
	 */
	public boolean isTransformer(Property property);
	
	/**
	 * Transforms the Property.
	 * 
	 * @param context The WidgetTransformerContext
	 * @param property The Property to transform
	 */
	public void transform(WidgetTransformerContext context, Property property) throws CoreException;
	
} // PropertyTransformer