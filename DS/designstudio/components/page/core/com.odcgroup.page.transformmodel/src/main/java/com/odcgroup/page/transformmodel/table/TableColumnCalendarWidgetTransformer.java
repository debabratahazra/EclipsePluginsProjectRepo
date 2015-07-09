package com.odcgroup.page.transformmodel.table;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.WidgetTransformer;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;

/**
 * @author phanikumark
 *
 */
public class TableColumnCalendarWidgetTransformer extends TableColumnEditableItemWidgetTransformer {
	
	private static String[] filterprops = {};
	
	private Property beanNameProp = null;
	private Property beanProp = null;
	
	
	protected void prepareTransformation(WidgetTransformerContext context, Widget widget) {

		Property prop = null;
		
		// add if necessary the property BEAN_PROPERTY
		prop = widget.findProperty(PropertyTypeConstants.BEAN_NAME);
		if (prop == null) {
			prop = ModelFactory.eINSTANCE.createProperty();
			prop.setTypeName(PropertyTypeConstants.BEAN_NAME);
			prop.setLibraryName("xgui");
			widget.getProperties().add(prop);
			prop.setValue(context.getEditableDataset());
			beanNameProp = prop;
		}		

		// add if necessary the property BEAN_PROPERTY
		prop = widget.findProperty(PropertyTypeConstants.BEAN_PROPERTY);
		if (prop == null) {
			prop = ModelFactory.eINSTANCE.createProperty();
			prop.setTypeName(PropertyTypeConstants.BEAN_PROPERTY);
			prop.setLibraryName("xgui");
			widget.getProperties().add(prop);
			prop.setValue(context.getEditableDatasetAttribute());
			beanProp = prop;
		}
	}
	
	protected void endTransformation(WidgetTransformerContext context, Widget widget) {
		if (beanProp != null) {
			widget.getProperties().remove(beanProp);
		}
		if (beanNameProp != null) {
			widget.getProperties().remove(beanNameProp);
		}
	}
		
	@Override
	protected Element performTransformation(WidgetTransformerContext context, Widget widget) throws CoreException {
		try {
			prepareTransformation(context, widget);
			WidgetType wt = widget.getLibrary().findWidgetType(WidgetTypeConstants.CALDATE_FIELD);
			WidgetTransformer transformer = context.getTransformModel().findWidgetTransformer(wt);
			transformer.transform(context, widget);
			return transformer.getParentElement(context, widget);
		} finally {
			endTransformation(context, widget);
		}
	}
	
	/**
	 * @param type
	 */
	public TableColumnCalendarWidgetTransformer(WidgetType type) {
		super(type, filterprops);
	}



}
