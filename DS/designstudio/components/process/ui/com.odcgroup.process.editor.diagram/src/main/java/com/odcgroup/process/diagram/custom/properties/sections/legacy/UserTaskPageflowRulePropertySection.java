package com.odcgroup.process.diagram.custom.properties.sections.legacy;

import com.odcgroup.pageflow.model.PageflowFactory;
import com.odcgroup.pageflow.model.PageflowModelLookup;
import com.odcgroup.process.diagram.custom.properties.ProcessBasicPropertySection;
import com.odcgroup.process.model.ProcessFactory;
import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.workbench.editors.properties.choice.ChoiceGroup;
import com.odcgroup.workbench.editors.properties.choice.ReferenceAttributesItem;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;
import com.odcgroup.workbench.editors.properties.util.SectionBrowsePropertyHelper;
import com.odcgroup.workbench.editors.properties.util.SectionListReferencePropertyHelper;
import com.odcgroup.workbench.editors.properties.util.SectionMultiplePropertyRowHelper;
import com.odcgroup.workbench.ui.navigator.pageflow.PageflowAdapterFactoryProvider;

public class UserTaskPageflowRulePropertySection extends ProcessBasicPropertySection {

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {		
		ChoiceGroup group = new ChoiceGroup("Specify the Task in DS");

		ReferenceAttributesItem pageflow = new ReferenceAttributesItem("Pageflow", 
				ProcessPackage.eINSTANCE.getUserTask_Pageflow());
		
		SectionBrowsePropertyHelper pageflowUri = new SectionBrowsePropertyHelper("Pageflow",
				ProcessPackage.eINSTANCE.getPageflow_URI(), ProcessPackage.eINSTANCE.getUserTask_Pageflow(), 
				PageflowAdapterFactoryProvider.getAdapterFactory(), PageflowFactory.eINSTANCE.getPageflowPackage().getPageflow(), 
				PageflowModelLookup.EXTENSIONS );	
		pageflow.addStructuralfeature(pageflowUri);
		
		SectionMultiplePropertyRowHelper multiPageflowFeature = new SectionMultiplePropertyRowHelper("pageflowProperty.description");
		SectionListReferencePropertyHelper pageflowProperties = new SectionListReferencePropertyHelper(null, 
				ProcessPackage.eINSTANCE.getPageflow_Property(), 
				ProcessPackage.eINSTANCE.getUserTask_Pageflow(),
				ProcessFactory.eINSTANCE, ProcessPackage.eINSTANCE.getProperty());
		multiPageflowFeature.addFeature("Properties", pageflowProperties);
		SectionAttributePropertyHelper pdesc = new SectionAttributePropertyHelper("Description", 
				ProcessPackage.eINSTANCE.getPageflow_Description(), 
				ProcessPackage.eINSTANCE.getUserTask_Pageflow(), true);
		multiPageflowFeature.addFeature("Description", pdesc);		
		pageflow.addStructuralfeature(multiPageflowFeature);
		
		group.addChoiceItem(pageflow, true);
		
		ReferenceAttributesItem service = new ReferenceAttributesItem("Rule", 
				ProcessPackage.eINSTANCE.getUserTask_Rule());
		SectionAttributePropertyHelper serviceName = new SectionAttributePropertyHelper("Rule", 
				ProcessPackage.eINSTANCE.getRule_Name(), 
				ProcessPackage.eINSTANCE.getUserTask_Rule());		
		service.addStructuralfeature(serviceName);
		
		SectionMultiplePropertyRowHelper multiFeature = new SectionMultiplePropertyRowHelper("ruleProperty.description");
		SectionListReferencePropertyHelper properties = new SectionListReferencePropertyHelper(null, 
				ProcessPackage.eINSTANCE.getRule_Property(), 
				ProcessPackage.eINSTANCE.getUserTask_Rule(), 
				ProcessFactory.eINSTANCE, ProcessPackage.eINSTANCE.getProperty());
		multiFeature.addFeature("Properties", properties);
		SectionAttributePropertyHelper sdesc = new SectionAttributePropertyHelper("Description", 
				ProcessPackage.eINSTANCE.getRule_Description(), 
				ProcessPackage.eINSTANCE.getUserTask_Rule(), true);
		multiFeature.addFeature("Description", sdesc);		
		service.addStructuralfeature(multiFeature);
		
		group.addChoiceItem(service);
		
		setChoiceGroup(group);
	}

}
