package com.odcgroup.page.transformmodel;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.properties.editable.EditableConstants;
import com.odcgroup.page.model.properties.editable.EditableIsBasedOnCondition;
import com.odcgroup.page.model.properties.enabled.EnabledConstants;
import com.odcgroup.page.model.properties.enabled.EnabledIsBaseOnCondition;

/**
 * This property transformer ignore its value but determines if the declaration
 * of the conditionVal is required. It is required when at least one advanced 
 * condition is used in the module (directly or by fragment inclusion). 
 *
 * @author yan
 * @since 2.4
 */
public class EnabledIsBasedOnAdvanceDeclarationPropertyTransformer extends BasePropertyTransformer {

	/**
	 * Declaration statement of the variable used with advanced condition 
	 */
	private static final String CONDITION_VAL_DECLARATION = "boolean " + EnabledPropertyTransformer.CONDITION_VAL +";";

	/**
	 * @param type
	 */
	public EnabledIsBasedOnAdvanceDeclarationPropertyTransformer(PropertyType type) {
		super(type);
	}

	/**
	 * @see com.odcgroup.page.transformmodel.PropertyTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Property)
	 */
	public void transform(WidgetTransformerContext context, Property property) throws CoreException {
        if (hasAvancedProperty(property.getWidget())) {
        	Element xspLogicDeclaration = createElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_LOGIC);
        	xspLogicDeclaration.setTextContent(CONDITION_VAL_DECLARATION);
        	context.getParentElement().appendChild(xspLogicDeclaration);
        }
	}

    /**
	 * @param widget
	 * @return true if a widget in the hierarchy has an advanced condition defined
	 */
	private boolean hasAvancedProperty(Widget widget) {
		if (EnabledIsBaseOnCondition.ADVANCED_VALUE.equals(widget.getPropertyValue(EnabledConstants.ENABLED_IS_BASE_ON_PROPERTY_NAME))
				|| EditableIsBasedOnCondition.ADVANCED_VALUE.equals(widget.getPropertyValue(EditableConstants.EDITABLE_IS_BASE_ON_PROPERTY_NAME))) {
			return true;
		}
		EList<Widget> children = widget.getContents();
		for (Widget child : children) {
			if (hasAvancedProperty(child)) {
				return true;
			}
			
			// Process fragment inclusion
			Property include = child.findProperty(TransformerConstants.INCLUDE_SRC_PROPERTY_NAME);
			if (include != null) {
				Model includedModel = include.getModel();
				if (includedModel != null) {
					Widget includedWidget = includedModel.getWidget();
					if (includedWidget != null) {
						if (WidgetTypeConstants.FRAGMENT.equals(includedWidget.getTypeName())) {
							if (hasAvancedProperty(includedWidget)) {
								return true;
							}
						}
					} else {
						URI uri = EcoreUtil.getURI(includedModel);
						String message = "The included model "+uri.toString()+" cannot be loaded.";
						IStatus status = new Status(IStatus.ERROR, PageTransformModelActivator.PLUGIN_ID, IStatus.ERROR, message, null);
						PageTransformModelActivator.getDefault().getLog().log(status); 					
					}
				}
			}
		}
		return false;
	}


}
