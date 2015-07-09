package com.odcgroup.domain.ui.properties;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

public class DomainPropertySection extends AbstractDSLPropertySection {

	public DomainPropertySection() {
	}

	@Override
	protected void configureProperties() {
		SimpleGroupWidget group = new SimpleGroupWidget(null);
		
		// name attribute
		SimpleTextWidget domainName = new SimpleTextWidget(
				MdfPackage.eINSTANCE.getMdfModelElement_Name(), "Name:");
		domainName.setFillHorizontal(true);
		group.setFillBoth(false);
		group.addPropertyFeature(domainName);
		
		//namespace attribute
		SimpleTextWidget domainNameSpace = new SimpleTextWidget(
				MdfPackage.eINSTANCE.getMdfDomain_Namespace(), "Namespace");
		domainNameSpace.setFillHorizontal(true);
		group.setFillBoth(false);
		group.addPropertyFeature(domainNameSpace);
		
		this.addPropertyFeature(group);
	}
}
