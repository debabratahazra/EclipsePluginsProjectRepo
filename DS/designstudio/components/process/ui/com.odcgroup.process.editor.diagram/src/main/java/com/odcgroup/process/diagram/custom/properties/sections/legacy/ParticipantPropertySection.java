package com.odcgroup.process.diagram.custom.properties.sections.legacy;

import com.odcgroup.process.diagram.custom.properties.ProcessBasicPropertySection;
import com.odcgroup.process.model.ProcessFactory;
import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.workbench.editors.properties.choice.ChoiceGroup;
import com.odcgroup.workbench.editors.properties.choice.ReferenceAttributesItem;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;
import com.odcgroup.workbench.editors.properties.util.SectionListReferencePropertyHelper;
import com.odcgroup.workbench.editors.properties.util.SectionMultiplePropertyRowHelper;

public class ParticipantPropertySection extends ProcessBasicPropertySection {

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {		
		ChoiceGroup group = new ChoiceGroup("Specify Participants");
		ReferenceAttributesItem participants = new ReferenceAttributesItem("By Giving Specific Names", ProcessPackage.eINSTANCE.getPool_Assignee());
		SectionListReferencePropertyHelper assignees = new SectionListReferencePropertyHelper(null, ProcessPackage.eINSTANCE.getPool_Assignee(), null, ProcessFactory.eINSTANCE, ProcessPackage.eINSTANCE.getAssignee());
		participants.addStructuralfeature(assignees);
		group.addChoiceItem(participants, true);
		
		ReferenceAttributesItem service = new ReferenceAttributesItem("By Calling a Service", ProcessPackage.eINSTANCE.getPool_AssigneeByService());
		SectionAttributePropertyHelper serviceName = new SectionAttributePropertyHelper("Service Name", ProcessPackage.eINSTANCE.getService_Name(), ProcessPackage.eINSTANCE.getPool_AssigneeByService());		
		service.addStructuralfeature(serviceName);
		SectionMultiplePropertyRowHelper multiFeature = new SectionMultiplePropertyRowHelper("serviceProperty.description");
		SectionListReferencePropertyHelper properties = new SectionListReferencePropertyHelper(null, ProcessPackage.eINSTANCE.getService_Property(), ProcessPackage.eINSTANCE.getPool_AssigneeByService(), ProcessFactory.eINSTANCE, ProcessPackage.eINSTANCE.getProperty());
		multiFeature.addFeature("Properties", properties);
		SectionAttributePropertyHelper sdesc = new SectionAttributePropertyHelper("Description", ProcessPackage.eINSTANCE.getService_Description(), ProcessPackage.eINSTANCE.getPool_AssigneeByService(), true);
		multiFeature.addFeature("Description", sdesc);
		service.addStructuralfeature(multiFeature);	
		
		group.addChoiceItem(service);
		setChoiceGroup(group);
	}

}
