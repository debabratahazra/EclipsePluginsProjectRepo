package com.odcgroup.page.transformmodel;

import org.eclipse.core.runtime.CoreException;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.transformmodel.util.PropertyUtils;
import com.odcgroup.page.transformmodel.util.SignificantFigure;

public class SignificantFiguresPropertyTransformer extends BasePropertyTransformer {

	public SignificantFiguresPropertyTransformer(PropertyType type) {
		super(type);
	}
	
	public SignificantFiguresPropertyTransformer() {
		super(null);
	}

	public void transform(WidgetTransformerContext context, SignificantFigure significantFigure) throws CoreException {
		
		String parsedScaleValue     = significantFigure.getScale();
		String parsedPrecisionValue = significantFigure.getPrecision();
		
		if(PropertyUtils.isNumericButNotEmpty(parsedScaleValue) &&
		   PropertyUtils.isNumericButNotEmpty(parsedPrecisionValue)) {
			addAttribute(context, PropertyTypeConstants.SCALE,     parsedScaleValue);
			addAttribute(context, PropertyTypeConstants.PRECISION, parsedPrecisionValue);
		}
	}
	
	@Override
	public void transform(WidgetTransformerContext context, Property property) throws CoreException {

		String precisionValue = property.getValue();
		String scaleValue = PropertyUtils.getSiblingPropertyValue(property, PropertyTypeConstants.SCALE);
		
		SignificantFigure significantFigure = new SignificantFigure(scaleValue, precisionValue);
		
		String parsedPrecisionValue = significantFigure.getPrecision();
		
		if(PropertyUtils.isNumericButNotEmpty(parsedPrecisionValue) && PropertyUtils.isNumericButNotEmpty(scaleValue)) {
			addAttribute(context, PropertyTypeConstants.PRECISION, parsedPrecisionValue);
		}
	}
}
