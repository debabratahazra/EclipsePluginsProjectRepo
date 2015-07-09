/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel.impl;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EClass;
import org.w3c.dom.Element;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.ChildrenWidgetTransformer;
import com.odcgroup.page.transformmodel.TransformModelPackage;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Children Widget Transformer</b></em>'.
 * This WidgetTransformer transforms only its children.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class ChildrenWidgetTransformerImpl extends SimpleWidgetTransformerImpl implements ChildrenWidgetTransformer {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChildrenWidgetTransformerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformModelPackage.Literals.CHILDREN_WIDGET_TRANSFORMER;
	}
		
	/**
	 * @see com.odcgroup.page.transformmodel.impl.AbstractWidgetTransformerImpl#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		transformChildren(context, widget);
	}	
	
	/**
	 * Gets the Xml element which represents the parent Element to which children will be attached.
	 * Note that this does not return all the XML that this transformer will generate. It is essentially
	 * used to help in the content-assist and auto-completion facilities.
	 *  
	 * @param context The WidgetTransformerContext
	 * @param widget The Widget to get the Element for
	 * @return Element The Element
	 */
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {	
		// Include Widgets cannot include Code Widgets
		return null;
	}	

} //ChildrenWidgetTransformerImpl
