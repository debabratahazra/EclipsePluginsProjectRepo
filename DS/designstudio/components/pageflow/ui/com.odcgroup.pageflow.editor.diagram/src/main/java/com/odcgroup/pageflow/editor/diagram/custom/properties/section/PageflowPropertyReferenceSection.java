package com.odcgroup.pageflow.editor.diagram.custom.properties.section;

import org.eclipse.emf.ecore.EReference;

import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.TablePropertyWidget;

public class PageflowPropertyReferenceSection extends AbstractPropertiesSection {
	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection#configureProperties()
	 */
	protected void configureProperties() {			
		setUseThreeFourthLayout(true);		
		EReference ref = PageflowPackage.eINSTANCE.getPageflow_Property();
		
		SimpleGroupWidget group = new SimpleGroupWidget(null);	
		TablePropertyWidget tProperty = new TablePropertyWidget(ref);
		group.addPropertyFeature(tProperty);
		
		this.addPropertyFeature(group);
	}	

}
