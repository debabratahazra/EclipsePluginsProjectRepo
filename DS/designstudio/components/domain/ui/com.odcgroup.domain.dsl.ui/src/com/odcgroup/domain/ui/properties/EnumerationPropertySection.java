package com.odcgroup.domain.ui.properties;

import org.eclipse.emf.ecore.EReference;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.workbench.editors.properties.widgets.BrowsePropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.CheckboxPropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

public class EnumerationPropertySection extends AbstractDSLPropertySection {

	public EnumerationPropertySection() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void configureProperties() {// TODO Auto-generated method stub
		SimpleGroupWidget group = new SimpleGroupWidget(null);

		// name attribute
		SimpleTextWidget domainName = new SimpleTextWidget(
				MdfPackage.eINSTANCE.getMdfModelElement_Name(), "Name:");
		domainName.setFillHorizontal(true);
		group.setFillBoth(false);
		

		EReference reference = MdfPackage.eINSTANCE.getMdfEnumeration_Type();
		BrowsePropertyWidget browse = new BrowsePropertyWidget(reference,
				"Type:", "") {
			@Override
			public void initPropertyControls() {
				super.initPropertyControls();
				if (getElement() != null) {
					Object data = getElement().eGet(getStructuralFeature());
					if (data instanceof MdfPrimitive) {
						textControl.setText(((MdfPrimitive) data)
								.getQualifiedName().toString());
					}
				}
			}
		};
		browse.setFillHorizontal(true);
		
		SimpleGroupWidget widgetGroup = new SimpleGroupWidget(null);
		CheckboxPropertyWidget widget = new CheckboxPropertyWidget(true, MdfPackage.eINSTANCE.getMdfEnumeration_AcceptNullValue(), "Allow NULL Value", true);
		widgetGroup.setFillBoth(false);
		
		widgetGroup.addPropertyFeature(widget);
		
		group.addPropertyFeature(domainName);
		group.addPropertyFeature(browse);
		
		this.addPropertyFeature(group);
		this.addPropertyFeature(widgetGroup);
	}

}
