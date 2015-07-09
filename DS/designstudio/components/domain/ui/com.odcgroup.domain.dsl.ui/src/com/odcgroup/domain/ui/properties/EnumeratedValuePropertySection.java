package com.odcgroup.domain.ui.properties;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

public class EnumeratedValuePropertySection extends
AbstractDSLPropertySection {

	public EnumeratedValuePropertySection() {

	}

	@Override
	protected void configureProperties() {
		SimpleGroupWidget group = new SimpleGroupWidget(null);
		group.setFillBoth(false);
		
		// name attribute
		SimpleTextWidget enumValName = new SimpleTextWidget(
				MdfPackage.eINSTANCE.getMdfModelElement_Name(), "Name:");
		enumValName.setFillHorizontal(true);
		
		SimpleTextWidget enumVal = new SimpleTextWidget(
				MdfPackage.eINSTANCE.getMdfEnumValue_Value(), "Value:");
		enumVal.setFillHorizontal(true);
		
		
		
		group.addPropertyFeature(enumValName);
		group.addPropertyFeature(enumVal);
		
		this.addPropertyFeature(group);
	}

}
