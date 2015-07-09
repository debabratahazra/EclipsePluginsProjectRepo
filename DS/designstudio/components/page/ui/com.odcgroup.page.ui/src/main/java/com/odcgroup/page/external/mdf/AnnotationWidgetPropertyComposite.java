package com.odcgroup.page.external.mdf;

import java.util.HashSet;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.annotations.AnnotationPropertyComposite;
import com.odcgroup.mdf.ext.gui.GUIAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;

/**
 * Extends the base class with support for display name
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class AnnotationWidgetPropertyComposite extends AnnotationPropertyComposite {

	/** */
	private List<PropertyType> propertyTypes = null;	
	
	/**
	 * @param name
	 * @return compatible name with page-designer property type name
	 */
	private String convertAnnotatioName(String name) {
		if (name.equals(GUIAspect.DISPLAY_FORMAT)) {
			return PropertyTypeConstants.FORMAT;
		}
		return name;
	}
	
	/**
	 * @see com.odcgroup.mdf.editor.ui.dialogs.annotations.AnnotationPropertyComposite#getDisplayName(com.odcgroup.mdf.metamodel.MdfAnnotationProperty)
	 */
	protected String getDisplayName(MdfAnnotationProperty property) {
		String annotationName = convertAnnotatioName(property.getName());
		for (PropertyType type : propertyTypes) {
			if (type.getName().equals(annotationName)) {
				return type.getDisplayName();
			}
		}
		return annotationName;
	}
	
	/**
	 * @param widgetType
	 */
	public void setWidgetType(WidgetType widgetType) {
        propertyTypes = AnnotationWidgetPropertyHelper.fetchDerivablePropertyTypes(widgetType, new HashSet<String>());
	}

	/**
	 * @param parent
	 * @param factory
	 */
	public AnnotationWidgetPropertyComposite(Composite parent, WidgetFactory factory) {
		super(parent, factory);
	}

	/**
	 * @param parent
	 * @param factory
	 * @param data
	 */
	public AnnotationWidgetPropertyComposite(Composite parent, WidgetFactory factory, boolean data) {
		super(parent, factory, data);
	}
	
}
