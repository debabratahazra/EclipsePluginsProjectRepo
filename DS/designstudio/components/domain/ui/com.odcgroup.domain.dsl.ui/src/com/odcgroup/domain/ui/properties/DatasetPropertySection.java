package com.odcgroup.domain.ui.properties;

import org.eclipse.emf.ecore.EReference;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.workbench.editors.properties.widgets.BrowsePropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.CheckboxPropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

public class DatasetPropertySection extends AbstractDSLPropertySection {

	public DatasetPropertySection() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void configureProperties() {
		// TODO Auto-generated method stub
		SimpleGroupWidget group = new SimpleGroupWidget(null);

		// name attribute
		SimpleTextWidget name = new SimpleTextWidget(
				MdfPackage.eINSTANCE.getMdfModelElement_Name(), "Name:");
		name.setFillHorizontal(true);
		group.setFillBoth(false);

		EReference reference = MdfPackage.eINSTANCE.getMdfDataset_BaseClass();
		BrowsePropertyWidget browse = new BrowsePropertyWidget(reference, "Based on class:", ""){
			@Override
			public void initPropertyControls() {
				if (getElement() != null) {
					Object data = getElement().eGet(getStructuralFeature());		
					if(data != null && data instanceof MdfClass) {
						textControl.setText(((MdfClass)data).getName());
					} else {
						textControl.setText("");
					}
				}	
			}
		};
		browse.setFillHorizontal(true);
		
		SimpleGroupWidget group1 = new SimpleGroupWidget("");
		CheckboxPropertyWidget wid1 = new CheckboxPropertyWidget(true, MdfPackage.eINSTANCE.getMdfDataset_Linked(), "Default linked dataset", true);
		CheckboxPropertyWidget wid2 = new CheckboxPropertyWidget(true, MdfPackage.eINSTANCE.getMdfDataset_Sync(), "Synchronized with base class", true);
		group1.setFillBoth(false);
		
		group.addPropertyFeature(name);
		group.addPropertyFeature(browse);
		group1.addPropertyFeature(wid1);
		group1.addPropertyFeature(wid2);
		
		
		this.addPropertyFeature(group);
		this.addPropertyFeature(group1);
	}

}
