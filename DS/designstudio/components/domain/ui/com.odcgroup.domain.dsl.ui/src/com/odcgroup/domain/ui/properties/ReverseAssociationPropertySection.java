package com.odcgroup.domain.ui.properties;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

public class ReverseAssociationPropertySection extends
AbstractDSLPropertySection {

	public ReverseAssociationPropertySection() {
	}

	@Override
	protected void configureProperties() {
		SimpleGroupWidget group = new SimpleGroupWidget(null);
		group.setFillBoth(false);

		// name attribute
		SimpleTextWidget assocName = new SimpleTextWidget(
				MdfPackage.eINSTANCE.getMdfModelElement_Name(), "Name:");
		assocName.setFillHorizontal(true);
		
		group.addPropertyFeature(assocName);
		this.addPropertyFeature(group);
	}

}
