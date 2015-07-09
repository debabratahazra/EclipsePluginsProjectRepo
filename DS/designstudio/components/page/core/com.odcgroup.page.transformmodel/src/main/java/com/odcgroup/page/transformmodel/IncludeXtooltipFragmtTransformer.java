package com.odcgroup.page.transformmodel;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;

public class IncludeXtooltipFragmtTransformer extends BasePropertyTransformer {

	
	public IncludeXtooltipFragmtTransformer(PropertyType type) {
		super(type);
		
	}

	@SuppressWarnings("unchecked")
	public void transform(WidgetTransformerContext context, Property property)
			throws CoreException {	
		Model model = property.getModel();
		// check if the included model really exists
		if (model != null) {
			// check if the included model really exists
			if (model.eResource() == null) {
				Widget root = property.getWidget().getRootWidget();
				String modelName = root.eResource().getURI().toString();
				String resName = "";
				EObject eObj = (EObject) model;
				if (eObj.eIsProxy()) {
					InternalEObject proxy = (InternalEObject) eObj;
					resName = proxy.eProxyURI().trimFragment().toString();
				}
				String message = "The model ["
						+ modelName
						+ "] includes a not found resource ["
						+ resName
						+ "]. Please check if it needs migration or if it has been deleted.";
				IStatus status = new Status(IStatus.ERROR,
						PageTransformModelActivator.PLUGIN_ID, IStatus.ERROR,
						message, null);
				PageTransformModelActivator.getDefault().getLog().log(status);
				// included model not found
				throw new CoreException(status);
			}
			Widget includedWidget = (Widget) model.getWidget();
			Element parentElement = context.getParentElement();
			if (includedWidget != null) {
				TransformUtils xmlt = new TransformUtils();
				context.getParentWidgets().add(property.getWidget());
				Element element = xmlt.transformWidgetToElement(
						context.getProject(), includedWidget,
						context.getDocument(), context.getParentWidgets());

				Widget parentWidget = property.getWidget();
				String value = parentWidget
						.getPropertyValue(PropertyTypeConstants.XTOOLTIP_HOLDTOOLTIPWINDOW_PROPERTY);
				if (!value.equals("false")) {
					element.setAttribute("sticky", value);
				}
				parentElement.appendChild(element);
			}

		}
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private boolean isWidgetContainedInTable(Widget widget) {
		Widget parent = widget.getParent();
		if (parent != null){			
			if( parent.getTypeName().equals(WidgetTypeConstants.TABLE_COLUMN)) {
				return true;
			} else {
				isWidgetContainedInTable(parent);
			}
		}
		return false;
	}

	public boolean isTransformer(Property property) {
		return getPropertyType().equals(property.getType());
	}	
	
}
