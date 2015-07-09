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
import com.odcgroup.page.transformmodel.NullWidgetTransformer;
import com.odcgroup.page.transformmodel.TransformModelPackage;
import com.odcgroup.page.transformmodel.WidgetTransformer;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;

/**
 * <!-- begin-user-doc -->
 * A Widget Transformer which ignores this Widget and all its child Widgets.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class NullWidgetTransformerImpl extends SimpleWidgetTransformerImpl implements NullWidgetTransformer {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NullWidgetTransformerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformModelPackage.Literals.NULL_WIDGET_TRANSFORMER;
	}

	/**
	 * Transforms the Element and all its children. This is a null implementation
	 * which ignores the Widget AND all its child Widgets.
	 * 
	 * @see com.odcgroup.page.transformmodel.impl.AbstractWidgetTransformerImpl#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
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
		// Return the Element created by the parent since this Widget does not generate any code itself
		Widget parent = widget.getParent();
		WidgetTransformer wt = context.getTransformModel().findWidgetTransformer(parent);
		return wt.getParentElement(context, parent);
	}	

} //NullWidgetTransformerImpl