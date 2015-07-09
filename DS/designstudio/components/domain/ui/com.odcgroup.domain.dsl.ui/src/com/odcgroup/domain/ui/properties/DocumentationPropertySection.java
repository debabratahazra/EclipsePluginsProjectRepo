package com.odcgroup.domain.ui.properties;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

public class DocumentationPropertySection extends AbstractDSLPropertySection {

	public DocumentationPropertySection() {
	}

	@Override
	protected void configureProperties() {
		
		SimpleTextWidget text = new SimpleTextWidget(MdfPackage.eINSTANCE.getMdfModelElement_Documentation(), null);
		text.setFillHorizontal(true);
		text.setMultiline(true);
		
		this.addPropertyFeature(text);
	}
}
