package com.odcgroup.process.diagram.custom.properties.sections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import com.odcgroup.process.diagram.custom.dialogs.PageflowSelectionDialogCreator;
import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.BrowsePropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.GroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;
import com.odcgroup.workbench.editors.properties.widgets.TablePropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.ToggleGroupWidget;

/**
 *
 * @author pkk
 *
 */
public class UserTaskPageflowRulePropertySection extends AbstractPropertiesSection {
	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection#configureProperties()
	 */
	protected void configureProperties() {
		
		final EReference pageflow = ProcessPackage.eINSTANCE.getUserTask_Pageflow();
		final EReference rule = ProcessPackage.eINSTANCE.getUserTask_Rule();
		
		ToggleGroupWidget toggleWidget = new ToggleGroupWidget("Specify the Task in DS", null) {
			
			public String getConfirmToggleMessage(EObject element) {
				StringBuilder sb = new StringBuilder();
				sb.append("Are you sure you want to specify the task by");
				int index = getSelectionIndex();
				if (element.eGet(pageflow) == null && index == 0) {
					sb.append(" a Pageflow? \n\n All of the rule information will be lost!!!"); 
				} else if (element.eGet(rule) == null && index == 1) {
					sb.append(" a Rule? \n\n All of the pageflow information will be lost!!!");					
				} else {
					return null;
				}
				return sb.toString();
			}
			
			protected int getSelection(EObject element) {
				Object obj = element.eGet(rule);
				if (obj == null) {
					return 0;
				} else {
					return 1;
				}
			}
			
		};
		
		// pageflow group
		toggleWidget.addReferenceWidget(createPageflowGroup(pageflow));	
		// rule group
		toggleWidget.addReferenceWidget(createRuleGroup(rule));	
		
		
		this.addPropertyFeature(toggleWidget);
		
		
	}
	
	/**
	 * @param rule
	 * @return
	 */
	private GroupWidget createRuleGroup(EReference rule) {
		
		GroupWidget ruleGroup = new GroupWidget(rule, "Rule", false, 1);
		
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
	
	/**
	 * @param pageflow
	 * @return
	 */
	private GroupWidget createPageflowGroup(EReference pageflow) {
		
		GroupWidget pageflowGroup = new GroupWidget(pageflow, "Pageflow", false, 1);	
		
		GroupWidget browseGroup = new GroupWidget(false, 2);
		browseGroup.setFillBoth(false);
		BrowsePropertyWidget pageflowUri = new BrowsePropertyWidget(ProcessPackage.eINSTANCE.getPageflow_URI(),	"Pageflow");
		pageflowUri.setSelectionDialogCreator(new PageflowSelectionDialogCreator());
		browseGroup.addPropertyFeature(pageflowUri);
		
		GroupWidget otherGroup = new GroupWidget(false, 2);
		
		SimpleGroupWidget propertyGroup = new SimpleGroupWidget("Properties");
		TablePropertyWidget propertyList = new TablePropertyWidget(ProcessPackage.eINSTANCE.getPageflow_Property());
		propertyGroup.addPropertyFeature(propertyList);
		otherGroup.addPropertyFeature(propertyGroup);
		
		
		SimpleGroupWidget descGroup = new SimpleGroupWidget("Description");
		SimpleTextWidget servDesc = new SimpleTextWidget(ProcessPackage.eINSTANCE.getPageflow_Description(),	null);
		servDesc.setMultiline(true);
		descGroup.setFillBoth(true);
		descGroup.addPropertyFeature(servDesc);
		
		otherGroup.addPropertyFeature(descGroup);
		
		pageflowGroup.addPropertyFeature(browseGroup);
		pageflowGroup.addPropertyFeature(otherGroup);
		
		return pageflowGroup;
	}

}
