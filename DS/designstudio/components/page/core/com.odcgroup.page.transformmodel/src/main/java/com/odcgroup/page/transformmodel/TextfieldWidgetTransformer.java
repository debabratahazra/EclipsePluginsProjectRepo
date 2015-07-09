package com.odcgroup.page.transformmodel;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.mdf.ext.constraints.ConstraintsAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.filter.TextfieldPropertyFilter;
import com.odcgroup.page.transformmodel.util.DomainObjectUtils;
import com.odcgroup.page.transformmodel.util.SignificantFigure;
import com.odcgroup.page.transformmodel.util.TransformUtils;

public class TextfieldWidgetTransformer extends BaseWidgetTransformer {
	
	private Element textfield = null;

	public TextfieldWidgetTransformer(WidgetType type) {
		super(type);
	}

	@Override
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		textfield = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_TEXTFIELD_ELEMENT);	
		TransformUtils.appendChild(context, textfield);

		// Set the parent so that the Attributes are set on the correct element
		Element oldParent = context.setParentElement(textfield);	

		transformTranslations(context, widget);
		transformProperties(context, widget);
		transformEvents(context, widget);
		transformChildren(context, widget);

		context.setParentElement(oldParent);
	}

	@Override
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {
		return textfield;
	}

	/**
	 * Transforms the Properties of the Widget.
	 *
	 * @param context The WidgetTransformerContext
	 * @param widget The Widget whose properties are to be transformed
	 * @exception CoreException
	 */
	protected void transformProperties(WidgetTransformerContext context, Widget widget) throws CoreException  {

		boolean significantFigurePresent = false;
		for (Property property : widget.getProperties()) {
			PropertyTransformer propertyTransformer = null;			
			if (!(property.getTypeName().equals(PropertyTypeConstants.PRECISION)) 
					&& !(property.getTypeName().equals(PropertyTypeConstants.SCALE))) {
				propertyTransformer = context.getTransformModel().findPropertyTransformer(property);
				propertyTransformer.transform(context, property);

			} 
		}	
		Property scalepro = widget.findProperty(PropertyTypeConstants.SCALE);
		Property precpro = widget.findProperty(PropertyTypeConstants.PRECISION);
	    //#DS-4558-if scale and precision both exist then significantFigurePresentis ture
		if(scalepro!=null&&precpro!=null){
			significantFigurePresent=true;
		}
		if (significantFigurePresent) {			
			String scale = null;
			String precision = null;
			scale = scalepro.getValue();
			precision = precpro.getValue();
			if (StringUtils.isEmpty(precision) || StringUtils.isEmpty(scale)) {
				MdfAnnotation constraints = getDatasetPropertyConstraints(context, widget);
				String suff = TextfieldPropertyFilter.BUSINESS_TYPE;		
				if (constraints != null) {
					if (StringUtils.isEmpty(scale)) {
						MdfAnnotationProperty annotProperty=constraints.getProperty(ConstraintsAspect.SCALE);
						//if constraints is not null and checking for scale exist.
						if(annotProperty!=null){
							scale = annotProperty.getValue()+ " "+suff;
						}
					}
					if (StringUtils.isEmpty(precision)) {
						MdfAnnotationProperty annotProperty=constraints.getProperty(ConstraintsAspect.PRECISION);
						//if constraints is not null and checking for scale exist.
						 if(annotProperty!=null){
							precision = annotProperty.getValue()+ " "+suff;
						}
					}
				}

			}
			SignificantFiguresPropertyTransformer significantFiguresPropertyTransformer = new SignificantFiguresPropertyTransformer();
			SignificantFigure significantFigure = new SignificantFigure(scale, precision);				
			significantFiguresPropertyTransformer.transform(context, significantFigure );
		}

	}

	/**
	 * @param widget
	 * @return
	 */
	private MdfAnnotation getDatasetPropertyConstraints(WidgetTransformerContext context, Widget widget) {
		
		MdfAnnotation annotation = null;		
		MdfModelElement mdfElement = getMdfModelElement(context, widget);
		if (mdfElement != null && mdfElement instanceof MdfDatasetProperty) {
			MdfEntity entity = ((MdfDatasetProperty)mdfElement).getType();
			if (entity instanceof MdfBusinessType) {
				annotation = ConstraintsAspect.getConstraints((MdfBusinessType)entity);
			}
		}
		
//			String dAttr = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
//			if (!StringUtils.isEmpty(dAttr)) {
//				MdfDataset ds = DomainObjectUtils.getDataset(widget);
//				if (ds != null) {
//					MdfDatasetProperty prop = ds.getProperty(dAttr);
//					if (prop != null) {
//						MdfEntity entity = prop.getType();
//						if (entity instanceof MdfBusinessType) {
//							annotation = ConstraintsAspect.getConstraints((MdfBusinessType)entity);
//						}
//					}
//				}
//			}

		return annotation;
	}
}
