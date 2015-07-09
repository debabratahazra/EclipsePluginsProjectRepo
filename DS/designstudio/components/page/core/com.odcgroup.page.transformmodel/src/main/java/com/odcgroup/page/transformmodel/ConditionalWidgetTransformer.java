package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XSPConstants.XSP_NAMESPACE_URI;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.ConditionUtils;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * This transformer is for a ConditionalWidget. It transforms the child
 * ConditionalBodyWidgets.
 * 
 * @author Gary Hayes
 */
public class ConditionalWidgetTransformer extends BaseWidgetTransformer {

	/**
	 * Creates a new ConditionalWidgetTransformer.
	 * 
	 * @param type the widget type
	 */
	public ConditionalWidgetTransformer(WidgetType type) {
		super(type);	
	}
	
	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException{
		boolean first = true;
		boolean last = false;
		List<Widget> children = widget.getContents();
		for (Widget w : children) {
			if (children.indexOf(w) == children.size() - 1) {
				last = true;
			}
			
			transformBody(context, w, first, last);
			
			first = false;
		}
	}	
	
	/**
	 * Transforms the body Widget (type ConditionalBodyWidget).
	 * 
	 * @param context The WidgetTransformerContext
	 * @param widget The body Widget to transform
	 * @param first True if this is the first Condition
	 * @param last True if this is the last Condition
	 * @exception CoreException
	 */
	private void transformBody(WidgetTransformerContext context, Widget widget, boolean first, boolean last) throws CoreException {		
		Element openLogicE = createElement(context, XSP_NAMESPACE_URI, TransformerConstants.LOGIC_ELEMENT_NAME);
		
		boolean udp = isInTableOrMatrix(widget);
		
		String javaCode = ConditionUtils.getConditionAsJavaCode(widget, udp);
		//javaCode = StringEscapeUtils.escapeXml(javaCode);
		
		if (first) {
			// First element starts with 'if (JAVA_CODE) {' otherwise '} else if (JAVA_CODE) {'
			openLogicE.setTextContent("if (" + javaCode + ") {");
		} else {
			openLogicE.setTextContent("} else if (" + javaCode + ") {");
		}
		
		//String boxElementName = getElementName(widget);
		//Element boxE = createElement(context, XGUI_NAMESPACE_URI, boxElementName);

		// Save the old parent. We will restore it later
		Element oldParent = context.getParentElement();
		
		oldParent.appendChild(openLogicE);
		//oldParent.appendChild(boxE);
		transformChildren(context, widget);
		
		if (last) {
			Element closeLogicE = createElement(context, XSP_NAMESPACE_URI, TransformerConstants.LOGIC_ELEMENT_NAME);
			closeLogicE.setTextContent("}");
			oldParent.appendChild(closeLogicE);
		}		
		
		// Reset the parent since this element should not contain any children
		// context.setParentElement(oldParent);
	}
	
	private boolean isInTableOrMatrix(Widget widget) {
		while(widget.getParent()!=null) {
			widget = widget.getParent();
			if(widget.getTypeName().equals(WidgetTypeConstants.TABLE_COLUMN) || widget.getTypeName().equals(WidgetTypeConstants.MATRIX_CELL)) {
				return true;
			}
		}
		return false;
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
		String elementName = getElementName(widget);
		return createElement(context, XGUI_NAMESPACE_URI, elementName);
	}
	
	/**
	 * Gets the name of the Xml Element to create.
	 * 
	 * @param widget The Widget
	 * @return String The name of the Xml element to create
	 */
	private String getElementName(Widget widget) {
		Property p = widget.findProperty(TransformerConstants.BOX_TYPE_PROPERTY_NAME);
		String type = p.getValue();
		return TransformUtils.getBoxElementName(type);
	}	
}
