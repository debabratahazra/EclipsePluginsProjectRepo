/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.model.Widget;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Widget Transformer</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getWidgetTransformer()
 * @model interface="true" abstract="true"
 * @generated NOT
 */
public interface WidgetTransformer /*extends EObject*/ {
	
	/**
	 * Returns true if the WidgetTransformer is designed to transform the specified WidgetType.
	 * 
	 * @param widget The Widget to test
	 * @return boolean True if the WidgetTransformer is designed to transform the specified WidgetType
	 */
	public boolean isTransformer(Widget widget);
	
	/**
	 * Transforms the Widget and all its children.
	 * 
	 * @param context The WidgetTransformerContext
	 * @param widget The Widget to transform
	 * @exception CoreException
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException;
	
	/**
	 * Gets the Xml element which represents the parent Element to which children will be attached.
	 * Note that this does not return all the XML that this transformer will generate. It is essentially
	 * used to help in the content-assist and auto-completion facilities.
	 *  
	 * @param context The WidgetTransformerContext
	 * @param widget The Widget to get the Element for
	 * @return Element The Element
	 */
	public Element getParentElement(WidgetTransformerContext context, Widget widget);

} // WidgetTransformer