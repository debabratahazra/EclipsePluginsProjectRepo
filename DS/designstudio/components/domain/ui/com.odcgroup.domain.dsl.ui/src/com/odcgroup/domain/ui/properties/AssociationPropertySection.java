package com.odcgroup.domain.ui.properties;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.swt.widgets.Button;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.workbench.editors.properties.widgets.BrowsePropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.CheckboxPropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.GroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.RadioGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

public class AssociationPropertySection extends AbstractDSLPropertySection {

	public AssociationPropertySection() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void configureProperties() {
		// TODO Auto-generated method stub
		SimpleGroupWidget group = new SimpleGroupWidget(null);
		group.setFillBoth(false);

		// name attribute
		SimpleTextWidget assocName = new SimpleTextWidget(
				MdfPackage.eINSTANCE.getMdfModelElement_Name(), "Name:");
		assocName.setFillHorizontal(true);

		EReference attribute = MdfPackage.eINSTANCE.getMdfAssociation_Type();
		BrowsePropertyWidget browse = new BrowsePropertyWidget(attribute,
				"Type:", "") {
			@Override
			public void initPropertyControls() {
				if (getElement() != null) {
					Object data = getElement().eGet(getStructuralFeature());			
					if (data != null && data instanceof MdfClass) {
						textControl.setText(((MdfClass)data).getName());
					} else if (data == null) {
						textControl.setText("");
					}
				}		
			}
		};
		browse.setFillHorizontal(true);

		GroupWidget widGrp = new GroupWidget(true, 3);
		widGrp.setEqualColumns(true);

		widGrp.setFillBoth(true);

		SimpleGroupWidget group1 = new SimpleGroupWidget("Modifiers");
		CheckboxPropertyWidget checkBox1 = new CheckboxPropertyWidget(true,
				MdfPackage.eINSTANCE.getMdfAssociation_Type(), "Business Key",
				false) {
			@Override
			public void initPropertyControls() {
				Object data = getElement();
				if (data != null && data instanceof MdfClass) {
					MdfClass clazz = (MdfClass) data;
					if (clazz.isPrimitiveType()) {
						checkBox.setSelection(true);
					} else {
						checkBox.setSelection(false);
					}
				} else {
					checkBox.setSelection(false);
				}
			}
		};
		group1.setFillBoth(false);

		CheckboxPropertyWidget checkBox2 = new CheckboxPropertyWidget(true,
				MdfPackage.eINSTANCE.getMdfAssociation_Type(), "Required",
				false) {
			@Override
			public void initPropertyControls() {
				Object data = getElement();
				if (data != null && data instanceof MdfClass) {
					MdfClass clazz = (MdfClass) data;
					if (clazz.isPrimitiveType()) {
						checkBox.setSelection(true);
					} else {
						checkBox.setSelection(false);
					}
				} else {
					checkBox.setSelection(false);
				}
			}
		};

		RadioGroupWidget radio1 = new RadioGroupWidget(
				MdfPackage.eINSTANCE.getMdfAssociation_Containment(),
				"Containment", true, 1, true) {
			protected void initPropertyControls() {
				Object data = getElement().eGet(getStructuralFeature());
				if (data != null)
					if (data instanceof MdfContainment)
						if (((MdfContainment) data).getLiteral().equals(
								"byReference"))
							for (Button btn : radioButtons)
								if (btn.getText().equals("By Reference"))
									btn.setSelection(true);
								else
									btn.setSelection(false);
						else if (((MdfContainment) data).getLiteral().equals(
								"byValue"))
							for (Button btn : radioButtons)
								if (btn.getText().equals("By Value"))
									btn.setSelection(true);
								else
									btn.setSelection(false);
			}
		};
		radio1.addRadio("By Value", true);
		radio1.addRadio("By Reference", true);

		RadioGroupWidget radio2 = new RadioGroupWidget(
				MdfPackage.eINSTANCE.getMdfProperty_Multiplicity(),
				"Multiplicity", true, 1, true){
			protected void initPropertyControls() {
				Object data = getElement().eGet(getStructuralFeature());
				if (data != null) {
					if (data instanceof MdfMultiplicity){
						if (((MdfMultiplicity) data).getLiteral().equals(
								"one")){
							for (Button btn : radioButtons){
								if (btn.getText().equals("One"))
									btn.setSelection(true);
								else
									btn.setSelection(false);
							}
						} else if (((MdfMultiplicity) data).getLiteral().equals(
								"many")) {
							for (Button btn : radioButtons) {
								if (btn.getText().equals("Many"))
									btn.setSelection(true);
								else
									btn.setSelection(false);
							}
						}
					}
				}
			}
		};
		radio2.addRadio("One", true);
		radio2.addRadio("Many", true);

		group1.addPropertyFeature(checkBox1);
		group1.addPropertyFeature(checkBox2);

		widGrp.addPropertyFeature(group1);
		widGrp.addPropertyFeature(radio1);
		widGrp.addPropertyFeature(radio2);

		group.addPropertyFeature(assocName);
		group.addPropertyFeature(browse);

		this.addPropertyFeature(group);
		this.addPropertyFeature(widGrp);
	}

}
