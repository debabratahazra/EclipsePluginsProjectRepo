package com.odcgroup.page.transformmodel;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.w3c.dom.Attr;

import com.odcgroup.page.metamodel.InclusionType;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.ModuleContainmentUtil;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * Specific-implementation of a PropertyTransformer for the include Tag.
 * 
 * @author Gary Hayes
 */
public class IncludePropertyTransformer extends BasePropertyTransformer {
	
	/**
	 * Constructs a new IncludePropertyTransformer.
	 * 
	 * @param type The PropertyType
	 */
	public IncludePropertyTransformer(PropertyType type) {
		super(type);
	}
	
	/**
	 * @see com.odcgroup.page.transformmodel.BasePropertyTransformer#isTransformer(com.odcgroup.page.model.Property)
	 */
	public boolean isTransformer(Property property) {
		return getPropertyType().equals(property.getType());
	}	
	
	/**
	 * @see com.odcgroup.page.transformmodel.PropertyTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Property)
	 */
	public void transform(WidgetTransformerContext context, Property property) throws CoreException {
		String attributeName = "";
		
		Model model = property.getModel();
		if (model != null && model.eResource() != null) {
			String type = IncludeWidgetTransformer.getInclusionType(model);
			String uri = OfsResourceHelper.getPathFromPlatformURI(model.eResource().getURI());
			String attributeValue = convertToXspValue(uri, type);
			
			// Note the IncludeWidgetTransformer takes care of SOURCE_INCLUDE's.
			// It does not call this Transformer in the case of a SOURCE_INCLUDE
			if (type.equals(InclusionType.CINCLUDE_LITERAL.getName())) {
				attributeName = TransformerConstants.C_INCLUDE_ATTRIBUTE_NAME;
				Widget root = property.getWidget().getRootWidget();
				String includeSrc = getIncludeValueForPageContainingModule(root, model);
				attributeValue = (includeSrc != null) ? includeSrc : attributeValue;
				attributeValue = TransformerConstants.COCOON_PROTOCOL + attributeValue;
			} else if(type.equals(InclusionType.XINCLUDE_LITERAL.getName())) {
				attributeName = TransformerConstants.X_INCLUDE_ATTRIBUTE_NAME;
			} 
			
			Attr a = context.getDocument().createAttribute(attributeName);		 
			a.setValue(attributeValue);	
			context.getParentElement().setAttributeNode(a);
		} else if (model == null) {
			Widget root = property.getWidget().getRootWidget();
			String modelName = root.eResource().getURI().toString();
			String message = "The model ["+modelName+"] contains an empty 'Include' widget. Please, check the model.";
			IStatus status = new Status(IStatus.ERROR, PageTransformModelActivator.PLUGIN_ID, IStatus.ERROR, message, null);
			PageTransformModelActivator.getDefault().getLog().log(status);
			throw new CoreException(status);
		} else if (model.eResource() == null) {
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
			throw new CoreException(status);
		}
	}
	
	/**
	 * DS-2244
	 * 
	 * @param root
	 * @param model
	 * @return string
	 */
	private String getIncludeValueForPageContainingModule(Widget root, Model model) {
		Widget include = model.getWidget();
		String str = null;
		if ((root.getTypeName().equals(WidgetTypeConstants.PAGE) 
				|| root.getTypeName().equals(WidgetTypeConstants.MODULE))
				&& include.getTypeName().equals(WidgetTypeConstants.MODULE)) {
			str = TransformUtils.getModulePath(model.eResource().getURI());
			// remove the file extension
			str = str.substring(0, str.lastIndexOf("."));
			if (ModuleContainmentUtil.canContainModules(include)) {
				str = "c"+str;
			}
		}
		return str;
	}
	

	


	/**
	 * Removes the platform resource protocol and the project name
	 * from the String.
	 * 
	 * @param str The String
	 * @param inclusionType
	 * @return String The transformed String
	 */
	private String convertToXspValue(String str, String inclusionType) {		
		str = TransformUtils.getModelPackage(str);		
		if (inclusionType.equals(InclusionType.CINCLUDE_LITERAL.getName())) {
			// Remove the file extension
			int extensionIndex = str.lastIndexOf(".");
			if (extensionIndex > 0) {
				str = str.substring(0, extensionIndex);
				
			}			
		}
		// Remove the first /
		if (str.length() > 0 && str.charAt(0) == '/') {
			str = str.replaceFirst("/", "");
		}
				
		return str;
	}
	
}
