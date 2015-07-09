package com.odcgroup.process.diagram.custom.properties.sections;

import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.GroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;
import com.odcgroup.workbench.editors.properties.widgets.TablePropertyWidget;

public class ServiceTaskServiceDefSection extends AbstractPropertiesSection {

	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {	
		
		GroupWidget serviceGroup = new GroupWidget(ProcessPackage.eINSTANCE.getServiceTask_Service(), null, false, 1);	
		
		GroupWidget nameGroup = new GroupWidget(false, 2);
		nameGroup.setFillBoth(false);
		SimpleTextWidget serviceName = new SimpleTextWidget(ProcessPackage.eINSTANCE.getService_Name(),	"Service Name");
		nameGroup.addPropertyFeature(serviceName);
		serviceGroup.addPropertyFeature(nameGroup);
		
		GroupWidget otherGroup = new GroupWidget(false, 2);
		
		SimpleGroupWidget propertyGroup = new SimpleGroupWidget("Properties");
		TablePropertyWidget propertyList = new TablePropertyWidget(ProcessPackage.eINSTANCE.getService_Property());
		propertyGroup.addPropertyFeature(propertyList);
		otherGroup.addPropertyFeature(propertyGroup);
		
		
		SimpleGroupWidget descGroup = new SimpleGroupWidget("Description");
		SimpleTextWidget servDesc = new SimpleTextWidget(ProcessPackage.eINSTANCE.getService_Description(),	null);
		servDesc.setMultiline(true);
		descGroup.addPropertyFeature(servDesc);
		
		otherGroup.addPropertyFeature(descGroup);		
		serviceGroup.addPropertyFeature(otherGroup);
		
		addPropertyFeature(serviceGroup);		
	}	

}
