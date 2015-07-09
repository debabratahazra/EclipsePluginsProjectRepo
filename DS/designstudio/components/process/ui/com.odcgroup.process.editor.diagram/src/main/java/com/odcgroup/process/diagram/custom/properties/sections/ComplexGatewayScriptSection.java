package com.odcgroup.process.diagram.custom.properties.sections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.process.model.ScriptingLanguage;
import com.odcgroup.workbench.editors.properties.item.AbstractPropertyReferenceWidget;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.ComboPropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.GroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;
import com.odcgroup.workbench.editors.properties.widgets.TablePropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.ToggleGroupWidget;

public class ComplexGatewayScriptSection extends AbstractPropertiesSection {	

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {			

		final EReference scriptRef = ProcessPackage.eINSTANCE.getComplexGateway_Script();		
		final EReference ruleRef = ProcessPackage.eINSTANCE.getComplexGateway_Rule();
		final EReference serviceRef = ProcessPackage.eINSTANCE.getComplexGateway_Service();
		
		// toggle widget to toggle between rule/script/service
		ToggleGroupWidget toggleWidget = new ToggleGroupWidget("Specify the Condition", null) {
			@Override
			public String getConfirmToggleMessage(EObject element) {
				StringBuilder sb = new StringBuilder();
				sb.append("Are you sure you want to specify condition by");
				int index = getSelectionIndex();
				if (element.eGet(ruleRef)  == null && index == 0) {
					sb.append(" calling a Rule?"); 
				} else if (element.eGet(scriptRef) == null && index == 1) {
					sb.append(" coding a Script?"); 				
				} else if (element.eGet(serviceRef) == null && index == 2) {
					sb.append(" calling a Service?"); 				
				} else {
					return null;
				}
				return sb.toString();
			}

			@Override
			protected int getSelection(EObject element) {
				if (element.eGet(ruleRef)  != null) {
					return 0;
				} else if (element.eGet(serviceRef) != null) {
					return 2;
				} else if (element.eGet(scriptRef) != null) {
					return 1;
				}
				return 0;
			}
			
		};

		
		// rule	
		toggleWidget.addReferenceWidget(createRuleGroup(ruleRef), true);	
		
		// script
		toggleWidget.addReferenceWidget(createScriptGroup(scriptRef));
		
		// service
		toggleWidget.addReferenceWidget(createServiceGroup(serviceRef));		
		

		addPropertyFeature(toggleWidget);	
	}	
	
	/**
	 * @param scriptRef
	 * @return
	 */
	private AbstractPropertyReferenceWidget createScriptGroup(EReference scriptRef) {

		GroupWidget serviceGroup = new GroupWidget(scriptRef, "By Coding a Script", false, 1);	
		
		GroupWidget nameGroup = new GroupWidget(false, 2);
		ComboPropertyWidget scriptLanguage = new ComboPropertyWidget(ProcessPackage.eINSTANCE.getScript_Language(), "Script Language") {
			
			public String getItemDisplayText(Object element) {
				return ((ScriptingLanguage) element).getName();
			}
			
			public Object[] getComboItems(Object element) {
				return ScriptingLanguage.VALUES.toArray();
			}
		};	
		nameGroup.addPropertyFeature(scriptLanguage);
		
		SimpleTextWidget servDesc = new SimpleTextWidget(ProcessPackage.eINSTANCE.getScript_Value(), "Script");
		servDesc.setMultiline(true);
		nameGroup.addPropertyFeature(servDesc);	

		serviceGroup.addPropertyFeature(nameGroup);	
		return serviceGroup;		
	}
	
	/**
	 * @param serviceRef
	 * @return
	 */
	private AbstractPropertyReferenceWidget createServiceGroup(EReference serviceRef) {

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
	
	/**
	 * @param rule
	 * @return
	 */
	private GroupWidget createRuleGroup(EReference rule) {
		
		GroupWidget ruleGroup = new GroupWidget(rule, "By calling a Rule", false, 1);
		
		GroupWidget ruleNameGroup = new GroupWidget(false, 2);
		ruleNameGroup.setFillBoth(false);
		SimpleTextWidget ruleName = new SimpleTextWidget(ProcessPackage.eINSTANCE.getRule_Name(), "Rule");
		ruleNameGroup.addPropertyFeature(ruleName);
		
		GroupWidget otherGroup = new GroupWidget(false, 2);
		
		SimpleGroupWidget propertyGroup = new SimpleGroupWidget("Properties");
		propertyGroup.setFillBoth(true);
		TablePropertyWidget propertyList = new TablePropertyWidget(ProcessPackage.eINSTANCE.getRule_Property());
		propertyGroup.addPropertyFeature(propertyList);
		otherGroup.addPropertyFeature(propertyGroup);
		
		
		SimpleGroupWidget descGroup = new SimpleGroupWidget("Description");
		SimpleTextWidget servDesc = new SimpleTextWidget(ProcessPackage.eINSTANCE.getRule_Description(),	null);
		servDesc.setMultiline(true);
		descGroup.addPropertyFeature(servDesc);
		
		otherGroup.addPropertyFeature(descGroup);
		
		ruleGroup.addPropertyFeature(ruleNameGroup);
		ruleGroup.addPropertyFeature(otherGroup);
		
		return ruleGroup;
	}
	
	

}
