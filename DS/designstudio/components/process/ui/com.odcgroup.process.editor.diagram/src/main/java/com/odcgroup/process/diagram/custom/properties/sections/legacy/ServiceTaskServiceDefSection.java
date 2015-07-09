package com.odcgroup.process.diagram.custom.properties.sections.legacy;

import com.odcgroup.process.diagram.custom.properties.ProcessBasicPropertySection;
import com.odcgroup.process.model.ProcessFactory;
import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;
import com.odcgroup.workbench.editors.properties.util.SectionListReferencePropertyHelper;
import com.odcgroup.workbench.editors.properties.util.SectionMultiplePropertyRowHelper;

public class ServiceTaskServiceDefSection extends ProcessBasicPropertySection {

	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {	
		addAttribute(new SectionAttributePropertyHelper("Service Name", ProcessPackage.eINSTANCE.getService_Name(), ProcessPackage.eINSTANCE.getServiceTask_Service()));		
		
		SectionMultiplePropertyRowHelper multiFeature = new SectionMultiplePropertyRowHelper("serviceProperty.description");
		SectionListReferencePropertyHelper properties = new SectionListReferencePropertyHelper(null, ProcessPackage.eINSTANCE.getService_Property(), ProcessPackage.eINSTANCE.getServiceTask_Service(), ProcessFactory.eINSTANCE, ProcessPackage.eINSTANCE.getProperty());
		multiFeature.addFeature("Properties", properties);
		SectionAttributePropertyHelper sdesc = new SectionAttributePropertyHelper("Description", ProcessPackage.eINSTANCE.getService_Description(), ProcessPackage.eINSTANCE.getServiceTask_Service(), true);
		multiFeature.addFeature("Description", sdesc);
		addAttribute(multiFeature);	
	}	

}
