package com.odcgroup.domain.ui.properties;

import org.eclipse.emf.ecore.EReference;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.workbench.editors.properties.widgets.BrowsePropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.CheckboxPropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

public class AttributePropertySection extends AbstractDSLPropertySection {

	public AttributePropertySection() {
	}

	@Override
	protected void configureProperties() {
		// TODO Auto-generated method stub
		SimpleGroupWidget group = new SimpleGroupWidget(null);

		// name attribute
		SimpleTextWidget attrName = new SimpleTextWidget(
				MdfPackage.eINSTANCE.getMdfModelElement_Name(), "Name:");
		attrName.setFillHorizontal(true);
		group.setFillBoth(false);
		

		EReference reference = MdfPackage.eINSTANCE.getMdfAttribute_Type();
		BrowsePropertyWidget browse = new BrowsePropertyWidget(reference, "Type:", "") {
			@Override
			public void initPropertyControls() {
				if (getElement() != null) {
					Object data = getElement().eGet(getStructuralFeature());
					if(data instanceof MdfPrimitive) {
						textControl.setText(((MdfPrimitive)data).getQualifiedName().toString());
					}
				}
			}
		};
		browse.setFillHorizontal(true);
		
		
		
		SimpleGroupWidget group1 = new SimpleGroupWidget("Modifiers");
		group1.setFillBoth(false);
		
		CheckboxPropertyWidget checkBox1 = new CheckboxPropertyWidget(true, MdfPackage.eINSTANCE.getMdfAttribute_Type(), "Primary Key", true) {
			@Override
			public void initPropertyControls() {
				if (getElement() != null) {
					Object element = getElement();
					if(element instanceof MdfAttribute) {
						MdfAttribute attr = (MdfAttribute)element;
						if(attr.isPrimaryKey()) {
							checkBox.setSelection(true);	
						}
						else {
							checkBox.setSelection(false);
						}
					}
				}		
			}
		};
		
		CheckboxPropertyWidget checkBox2 = new CheckboxPropertyWidget(true, MdfPackage.eINSTANCE.getMdfAttribute_Type(), "Business Key", true){
			@Override
			public void initPropertyControls() {
				if (getElement() != null) {
					Object element = getElement();
					if(element instanceof MdfAttribute) {
						MdfAttribute attr = (MdfAttribute)element;
						if(attr.isBusinessKey()) {
							checkBox.setSelection(true);	
						}
						else {
							checkBox.setSelection(false);
						}
					}
				}		
			}
		};
		
		CheckboxPropertyWidget checkBox3 = new CheckboxPropertyWidget(true, MdfPackage.eINSTANCE.getMdfAttribute_Type(), "Required", true){
			@Override
			public void initPropertyControls() {
				if (getElement() != null) {
					Object element = getElement();
					if(element instanceof MdfAttribute) {
						MdfAttribute attr = (MdfAttribute)element;
						if(attr.isRequired()) {
							checkBox.setSelection(true);	
						}
						else {
							checkBox.setSelection(false);
						}
					}
				}		
			}
		};
		
		group.addPropertyFeature(attrName);
		group.addPropertyFeature(browse);
		
		group1.addPropertyFeature(checkBox1);
		group1.addPropertyFeature(checkBox2);
		group1.addPropertyFeature(checkBox3);
			
		this.addPropertyFeature(group);
		this.addPropertyFeature(group1);
	}

}
