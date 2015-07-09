package com.odcgroup.page.transformmodel;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.page.metamodel.InclusionType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.ModuleContainmentUtil;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * Specific-implementation of an WidgetTransformer for the include Tag.
 * 
 * @author atr (DS-1296)
 */
public class IncludeWidgetTransformer extends BaseWidgetTransformer {
	
	// DS-5693
	private boolean loadModuleAsynchronously(Widget widget, Property property) {
		boolean asyncMode = false;
		property = widget.findProperty(TransformerConstants.INCLUDE_SRC_PROPERTY_NAME);
		if (ModuleContainmentUtil.includeContainerOrStandaloneModule(property)) {
			Property prop = widget.findProperty(PropertyTypeConstants.INCLUDE_LOADING_MODE);
			if (prop != null) {
				asyncMode = "async".equals(prop.getValue());
			}
		}
		return asyncMode;
	}
	
	/**
	 * // DS-5693
	 * The module is included with a loading model set to 'asynchronous'. Apply here a specific transformation
	 * @param context
	 * @param module
	 * @param includedModel
	 */
	private void transform(WidgetTransformerContext context, Widget module, Model includedModel) {
		
		Namespace ns = context.getTransformModel().findNamespace(XGuiConstants.XGUI_NAMESPACE_URI);
		Element element = context.getDocument().createElementNS(ns.getUri(), "module");
		element.setPrefix(ns.getPrefix());

		TransformUtils.appendChild(context, element);	
		
		// Set the parent so that the Attributes are set on the correct element
		Element oldParent = context.setParentElement(element);
		
		String modulePath = TransformUtils.getModulePath(includedModel.eResource().getURI());
		String value = "/wui/p"+modulePath.substring(0, modulePath.lastIndexOf("."));
		
		Attr src = context.getDocument().createAttribute("src");		 
		src.setValue(value);	
		context.getParentElement().setAttributeNode(src);
		
		// Reset the parent since this element should not contain any children
		context.setParentElement(oldParent);
	}

	/**
	 * Constructor
	 * 
	 * @param type
	 * 			The widget type
	 */
	public IncludeWidgetTransformer(WidgetType type) {
		super(type);
	}

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {	
		
		// DS-5693
		Property property = widget.findProperty(TransformerConstants.INCLUDE_SRC_PROPERTY_NAME);
		if (loadModuleAsynchronously(widget, property)) {
			transform(context, widget, property.getModel());
			return;
		}
		
		// Generate for all others kind of models with 'synchronous' loading mode. 
		
		// check if the included model really exists
		Model model = property.getModel();
		if (model == null) {
			Widget root = property.getWidget().getRootWidget();
			String modelName = root.eResource().getURI().toString();
			String message = "The model ["+modelName+"] contains an empty 'Include' widget. Please, check the model.";
			IStatus status = new Status(IStatus.ERROR, PageTransformModelActivator.PLUGIN_ID, IStatus.ERROR, message, null);
			PageTransformModelActivator.getDefault().getLog().log(status);
			// no model is included
			throw new CoreException(status);
		} 
		
		// check if the included model really exists
		if (model.eResource() == null) {
			Widget root = property.getWidget().getRootWidget();
			String modelName = root.eResource().getURI().toString();
			String resName = "";
			EObject eObj = (EObject)model;
			if (eObj.eIsProxy())  {
				InternalEObject proxy = (InternalEObject)eObj;
				resName = proxy.eProxyURI().trimFragment().toString();
			}
			String message = "The model ["+modelName+"] includes a not found resource ["+resName+"]. Please check if it needs migration or if it has been deleted.";
			IStatus status = new Status(IStatus.ERROR, PageTransformModelActivator.PLUGIN_ID, IStatus.ERROR, message, null);
			PageTransformModelActivator.getDefault().getLog().log(status);
			// included model not found
			throw new CoreException(status);
		}

		String typeOfInclusion = getInclusionType(model);

		// source include (copy)
		if (typeOfInclusion.equals(InclusionType.SOURCE_INCLUDE_LITERAL.getName())) {
			includeSource(context, widget);
		} else {
			// other kind of inclusion
			String elementName = TransformerConstants.INCLUDE_ELEMENT_NAME;
			String uri = "";
			if (typeOfInclusion.equals(InclusionType.CINCLUDE_LITERAL.getName())) {
				uri = TransformerConstants.C_INCLUDE_NAMESPACE_URI;
			} else if  (typeOfInclusion.equals(InclusionType.XINCLUDE_LITERAL.getName())) {
				uri = TransformerConstants.X_INCLUDE_NAMESPACE_URI;
			} 
			
			// the included model is reachable, so generate the inclusion
			Namespace ns = context.getTransformModel().findNamespace(uri);
			Element element = context.getDocument().createElementNS(ns.getUri(), elementName);
			element.setPrefix(ns.getPrefix());
	
			TransformUtils.appendChild(context, element);	
			
			// Set the parent so that the Attributes are set on the correct element
			Element oldParent = context.setParentElement(element);
			
			transformProperties(context, widget);
			
			// Reset the parent since this element should not contain any children
			context.setParentElement(oldParent);
		}

	}

	// DS-3649: Determine the inclusion type automatically from the model type
	static String getInclusionType(Model model) {
		String typeOfInclusion = model.eResource().getURI().fileExtension().equals(PageConstants.FRAGMENT_FILE_EXTENSION) ?
				InclusionType.SOURCE_INCLUDE_LITERAL.getName() :
				InclusionType.CINCLUDE_LITERAL.getName();
		return typeOfInclusion;
	}	

	/**
	 * Includes the source file directly within the XSP page.
	 * 
	 * @param context The WidgetTransformerContext
	 * @param widget The Widget to transform
	 * @exception CoreException
	 */
	@SuppressWarnings("unchecked")
	private void includeSource(WidgetTransformerContext context, Widget widget) throws CoreException {
		
		
		Property include = widget.findProperty(TransformerConstants.INCLUDE_SRC_PROPERTY_NAME);
		Model model = include.getModel();
		if (model != null) {
			
			Widget includedWidget = model.getWidget();
			
			if (includedWidget != null) {
				
				context.getParentWidgets().add(widget);
				TransformUtils xmlt = new TransformUtils();
				Element element = xmlt.transformWidgetToElement(context.getProject(), 
						                         includedWidget, 
						                         context.getDocument(), 
						                         context.getParentWidgets());
				context.getParentWidgets().remove(widget);
				
				Element pe = context.getParentElement();
				if (pe != null) {
					String parentTypeName = widget.getParent().getTypeName(); 
					// TODO REFACTOR this test
					boolean isGridCell = parentTypeName.equals("GridCell") || parentTypeName.equals("TableColumn");
					if (isGridCell && element.getLocalName().equals("hbox")) {
						// begin DS-1821 hack drop root element
						List<Node> nodes = new ArrayList<Node>();
						NodeList list = element.getChildNodes();
						for (int kx=0; kx < list.getLength(); kx++) {
							nodes.add(list.item(kx));
						}
						for (Node child : nodes) {
					    	pe.appendChild(child);
						}
						// end DS-1821 hack drop root element
					} else {
						pe.appendChild(element);
					}
				}	
			} 
		} 
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
}
