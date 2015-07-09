package com.odcgroup.page.transformmodel.impl;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;

/**
 * The DefaultWidgetTransformer transforms the Widget to an Xml Element having
 * the same name but in lowercase. It uses the default Namespace ot the TransformModel.
 * 
 * @author Gary Hayes
 */
public class DefaultWidgetTransformerImpl extends AbstractWidgetTransformerImpl {
	
	/**
	 * Creates a new DefaultWidgetTransformer.
	 */
	public DefaultWidgetTransformerImpl() {;
	}
	
	/**
	 * Returns true if the WidgetTransformer is designed to transform the specified WidgetType.
	 * 
	 * @param widget The Widget to test
	 * @return boolean True if the WidgetTransformer is designed to transform the specified WidgetType
	 */
	public boolean isTransformer(Widget widget) {
		// This can be used for all transformations
		return true;
	}	
	
	/**
	 * Pre-transforms the Widget. This is an ideal place to create the 
	 * Element / Attr and update the WidgetTransformerContext ready
	 * for the next child.
	 * 
	 * @see com.odcgroup.page.transformmodel.impl.AbstractWidgetTransformerImpl#preTransform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	protected void preTransform(WidgetTransformerContext context, Widget widget) throws CoreException {
		String namespace = context.getTransformModel().getDefaultNamespace().getUri();
		String name = widget.getTypeName().toLowerCase();
		Element element = createElement(context, namespace, name);	
		
	    if (context.getParentElement() != null) {
	        context.getParentElement().appendChild(element);
	    }
		
		context.setParentElement(element);
	}
	
	/**
	 * Post-transforms the Widget. This is an ideal place to update the
	 * WidgetTransformerContext ready for the next-sibling.
	 * 
	 * @see com.odcgroup.page.transformmodel.impl.AbstractWidgetTransformerImpl#postTransform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	protected void postTransform(WidgetTransformerContext context, Widget widget) throws CoreException {
		context.setParentElement((Element) context.getParentElement().getParentNode());
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
		String uri = context.getTransformModel().getDefaultNamespace().getUri();
		String name = widget.getTypeName().toLowerCase();
		return createElement(context, uri, name);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.transformmodel.AbstractWidgetTransformer#getWidgetType()
	 */
	@Override
	public WidgetType getWidgetType() {
		return null;
	}
}