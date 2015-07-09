package com.odcgroup.page.transformmodel.snippet;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import com.odcgroup.page.transformmodel.Namespace;
import com.odcgroup.page.transformmodel.TransformModel;
import com.odcgroup.page.transformmodel.TransformerConstants;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;

/**
 * base snippet transformer
 * 
 * @author pkk
 *
 */
public class BaseSnippetTransformer {
	
	/** */
	protected static final String PARAM_PREFIX = SnippetTransformerConstants.PARAM_PREFIX;
	/** */
	private Element functioneElement = null;
	
	/**
	 * @return the functioneElement
	 */
	public Element getFunctioneElement() {
		return functioneElement;
	}

	/**
	 * @param functionElement
	 */
	public BaseSnippetTransformer(Element functionElement) {
		this.functioneElement = functionElement;
	}
	
	/**
     * @param context
     * @param name
     * @param value
     */
    protected void transformSnippetParameter(WidgetTransformerContext context, String name, String value) {
    	if (StringUtils.isEmpty(value)) {
    		return;
    	}
    	TransformModel tm = context.getTransformModel();
        Namespace ns = tm.getDefaultNamespace();

        // Create the <xgui:param name="f.nature.eq"> element
        Element element = context.getDocument().createElementNS(ns.getUri(),
                TransformerConstants.EVENT_USER_PARAMETER);
        element.setPrefix(ns.getPrefix());	
         
        Attr nameAttrib = context.getDocument().createAttribute(TransformerConstants.NAME_ATTRIBUTE_NAME);
        nameAttrib.setValue(name);	
		element.setAttributeNode(nameAttrib);
		
		element.setTextContent(value);
		
		if (functioneElement == null) {
			context.getParentElement().appendChild(element);
		} else {
			functioneElement.appendChild(element);
		}
    }

	/**
     * @param context
     * @param name
     * @param value
     */
    protected void transformSnippetParameter(WidgetTransformerContext context, String name) {
    	TransformModel tm = context.getTransformModel();
        Namespace ns = tm.getDefaultNamespace();

        // Create the <xgui:param name="f.nature.eq"> element
        Element element = context.getDocument().createElementNS(ns.getUri(),
                TransformerConstants.EVENT_USER_PARAMETER);
        element.setPrefix(ns.getPrefix());	
         
        Attr nameAttrib = context.getDocument().createAttribute(TransformerConstants.NAME_ATTRIBUTE_NAME);
        nameAttrib.setValue(name);	
		element.setAttributeNode(nameAttrib);
		
		if (functioneElement == null) {
			context.getParentElement().appendChild(element);
		} else {
			functioneElement.appendChild(element);
		}
    }

}
