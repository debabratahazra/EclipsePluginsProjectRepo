package com.odcgroup.process.diagram.custom.properties.sections.legacy;

import org.eclipse.emf.ecore.EReference;

import com.odcgroup.process.diagram.custom.properties.ProcessBasicPropertySection;
import com.odcgroup.process.model.ProcessFactory;
import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.process.model.ScriptingLanguage;
import com.odcgroup.workbench.editors.properties.choice.ChoiceGroup;
import com.odcgroup.workbench.editors.properties.choice.ReferenceAttributesItem;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;
import com.odcgroup.workbench.editors.properties.util.SectionListReferencePropertyHelper;
import com.odcgroup.workbench.editors.properties.util.SectionMultiplePropertyRowHelper;

public class ComplexGatewayScriptSection extends ProcessBasicPropertySection {	

	protected EReference reference = ProcessPackage.eINSTANCE.getComplexGateway_Script();
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {		
		ChoiceGroup group = new ChoiceGroup("Specify the Condition");
		
		ReferenceAttributesItem rule = new ReferenceAttributesItem("By Calling a Rule", ProcessPackage.eINSTANCE.getComplexGateway_Rule());
		SectionAttributePropertyHelper ruleName = new SectionAttributePropertyHelper("Rule Name", ProcessPackage.eINSTANCE.getRule_Name(), ProcessPackage.eINSTANCE.getComplexGateway_Rule());		
		rule.addStructuralfeature(ruleName);
		
		SectionMultiplePropertyRowHelper rmultiFeature = new SectionMultiplePropertyRowHelper("ruleProperty.description");
		SectionListReferencePropertyHelper rproperties = new SectionListReferencePropertyHelper(null, ProcessPackage.eINSTANCE.getRule_Property(), ProcessPackage.eINSTANCE.getComplexGateway_Rule(), ProcessFactory.eINSTANCE, ProcessPackage.eINSTANCE.getProperty());
		rmultiFeature.addFeature("Properties", rproperties);
		SectionAttributePropertyHelper rdesc = new SectionAttributePropertyHelper("Description", ProcessPackage.eINSTANCE.getRule_Description(), ProcessPackage.eINSTANCE.getComplexGateway_Rule(), true);
		rmultiFeature.addFeature("Description", rdesc);		
		rule.addStructuralfeature(rmultiFeature);
		
		group.addChoiceItem(rule, true);

		ReferenceAttributesItem script = new ReferenceAttributesItem("By Coding a Script", reference);
		SectionAttributePropertyHelper scriptLanguage = new SectionAttributePropertyHelper("Script Language", ProcessPackage.eINSTANCE.getScript_Language(), reference, ScriptingLanguage.VALUES);		
		script.addStructuralfeature(scriptLanguage);
		SectionAttributePropertyHelper scriptVal = new SectionAttributePropertyHelper("Script", ProcessPackage.eINSTANCE.getScript_Value(), reference, true);		
		script.addStructuralfeature(scriptVal);
		group.addChoiceItem(script);

		ReferenceAttributesItem service = new ReferenceAttributesItem("By Calling a Service", ProcessPackage.eINSTANCE.getComplexGateway_Service());
		
		SectionAttributePropertyHelper serviceName = new SectionAttributePropertyHelper("Service Name", ProcessPackage.eINSTANCE.getService_Name(), ProcessPackage.eINSTANCE.getComplexGateway_Service());		
		service.addStructuralfeature(serviceName);
		
		SectionMultiplePropertyRowHelper multiFeature = new SectionMultiplePropertyRowHelper("serviceProperty.description");
		SectionListReferencePropertyHelper properties = new SectionListReferencePropertyHelper(null, ProcessPackage.eINSTANCE.getService_Property(), ProcessPackage.eINSTANCE.getComplexGateway_Service(), ProcessFactory.eINSTANCE, ProcessPackage.eINSTANCE.getProperty());
		multiFeature.addFeature("Properties", properties);
		SectionAttributePropertyHelper sdesc = new SectionAttributePropertyHelper("Description", ProcessPackage.eINSTANCE.getService_Description(), ProcessPackage.eINSTANCE.getComplexGateway_Service(), true);
		multiFeature.addFeature("Description", sdesc);
		
		service.addStructuralfeature(multiFeature);
		group.addChoiceItem(service);
		
		setChoiceGroup(group);
	}	
	
	

}
