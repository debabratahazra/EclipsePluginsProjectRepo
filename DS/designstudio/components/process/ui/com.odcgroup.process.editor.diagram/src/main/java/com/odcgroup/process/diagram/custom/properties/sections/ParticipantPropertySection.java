package com.odcgroup.process.diagram.custom.properties.sections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.workbench.editors.properties.item.AbstractPropertyReferenceWidget;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.GroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;
import com.odcgroup.workbench.editors.properties.widgets.TablePropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.ToggleGroupWidget;

public class ParticipantPropertySection extends AbstractPropertiesSection {

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection#configureProperties()
	 */
	protected void configureProperties() {	
		
		final EReference participantRef = ProcessPackage.eINSTANCE.getPool_Assignee();
		final EReference serviceRef = ProcessPackage.eINSTANCE.getPool_AssigneeByService();
		
		ToggleGroupWidget toggleWidget = new ToggleGroupWidget("Specify Participants", null) {
			@Override
			public String getConfirmToggleMessage(EObject element) {
				int index = getSelectionIndex();
				StringBuilder sb = new StringBuilder();
				sb.append("Are you sure you want to specify participants by");
				if (index == 0) {
					sb.append(" giving specific names? \n\n All of the service information will be lost!!!");	
				} else  {
					sb.append(" calling a Service? \n\n All the specified participant names will be lost!!!"); 				
				} 
				return sb.toString();
			}

			@Override
			protected int getSelection(EObject element) {
				Object obj = element.eGet(serviceRef);
				if (obj == null) {
					return 0;
				} else {
					return 1;
				}
			}
			
		};

		
		// participants list	
		TablePropertyWidget assigneeList = new TablePropertyWidget(participantRef, "By Giving Specific Names", true);
		toggleWidget.addReferenceWidget(assigneeList, true);	
		
		// assignee service
		AbstractPropertyReferenceWidget serviceGroup = getServiceGroup(serviceRef);		
		toggleWidget.addReferenceWidget(serviceGroup);		

		addPropertyFeature(toggleWidget);
	}
	
	/**
	 * @param serviceRef
	 * @return
	 */
	private AbstractPropertyReferenceWidget getServiceGroup(EReference serviceRef) {

		GroupWidget serviceGroup = new GroupWidget(serviceRef, "By Calling a Service", false, 1);	
		
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
		return serviceGroup;
	}
	

}
