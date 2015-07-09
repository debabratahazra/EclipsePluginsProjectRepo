package com.odcgroup.domain.ui.properties;

import org.eclipse.emf.ecore.EReference;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.workbench.editors.properties.widgets.BrowsePropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.CheckboxPropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

public class ClassPropertySection extends AbstractDSLPropertySection {

	public ClassPropertySection() {}

	@Override
	protected void configureProperties() {
		// TODO Auto-generated method stub
		SimpleGroupWidget group = new SimpleGroupWidget(null);

		// name attribute
		SimpleTextWidget className = new SimpleTextWidget(
				MdfPackage.eINSTANCE.getMdfModelElement_Name(), "Name:");
		className.setFillHorizontal(true);
		group.setFillBoth(false);
		

		EReference reference = MdfPackage.eINSTANCE.getMdfClass_BaseClass();
		BrowsePropertyWidget browse = new BrowsePropertyWidget(reference, "Superclass", "") {
			@Override
			public void initPropertyControls() {
				if (getElement() != null) {
					Object data = getElement();
					if(data instanceof MdfClass) {
						MdfClass clazz = (MdfClass)data;
						if(clazz.getBaseClass() != null) {
							textControl.setText(clazz.getBaseClass().getName());
						} else {
							textControl.setText("");
						}
					}
				}
			}
		};
		browse.setFillHorizontal(true);
		
		SimpleGroupWidget group1 = new SimpleGroupWidget("Modifiers");
		CheckboxPropertyWidget checkBox = new CheckboxPropertyWidget(true, MdfPackage.eINSTANCE.getMdfClass_Abstract(), "Abstract", true);
		group1.addPropertyFeature(checkBox);
		group1.setFillBoth(false);
		
		group.addPropertyFeature(className);
		group.addPropertyFeature(browse);
			
		this.addPropertyFeature(group);
		this.addPropertyFeature(group1);
	}
	
	@Override
	protected void initiateControls() {
		super.initiateControls();
	}

}
