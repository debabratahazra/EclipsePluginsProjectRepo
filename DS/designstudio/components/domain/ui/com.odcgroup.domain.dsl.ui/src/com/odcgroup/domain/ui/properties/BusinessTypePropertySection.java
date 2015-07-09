package com.odcgroup.domain.ui.properties;

import org.eclipse.emf.ecore.EReference;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.workbench.editors.properties.widgets.BrowsePropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.GroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

/**
 * @author ssreekanth
 *
 */
public class BusinessTypePropertySection extends AbstractDSLPropertySection {

	public BusinessTypePropertySection() {
		
	}

	@Override
	public void configureProperties() {
		// TODO Auto-generated method stub
		SimpleGroupWidget group = new SimpleGroupWidget(null);

		// name attribute
		SimpleTextWidget domainName = new SimpleTextWidget(
				MdfPackage.eINSTANCE.getMdfModelElement_Name(), "Name:");
		domainName.setFillHorizontal(true);
		group.setFillBoth(false);
		group.addPropertyFeature(domainName);

		EReference attribute = MdfPackage.eINSTANCE.getMdfBusinessType_Type();
		BrowsePropertyWidget browse = new BrowsePropertyWidget(attribute, "Base Type:", "") {
			@Override
			public void initPropertyControls() {
				super.initPropertyControls();
				if (getElement() != null) {
					Object data = getElement().eGet(getStructuralFeature());
					if(data instanceof MdfPrimitive) {
						textControl.setText(((MdfPrimitive)data).getQualifiedName().toString());
					}
				}
			}
		};
		browse.setFillHorizontal(true);
		group.addPropertyFeature(browse);
		
		GroupWidget baseGroup = new GroupWidget(true, 2);
		baseGroup.setFillBoth(true);

		SimpleGroupWidget group1 = new SimpleGroupWidget("Constraints on Decimal");
		group1.setFillBoth(true);
		
		SimpleGroupWidget group2 = new SimpleGroupWidget("Constraints on Decimal");
		group2.setFillBoth(true);
		
		baseGroup.addPropertyFeature(group1);
		baseGroup.addPropertyFeature(group1);
		
		this.addPropertyFeature(group);
		this.addPropertyFeature(baseGroup);

	}
	

}
