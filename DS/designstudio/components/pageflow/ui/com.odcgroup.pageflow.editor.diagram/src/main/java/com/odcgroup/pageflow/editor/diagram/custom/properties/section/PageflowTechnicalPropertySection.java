package com.odcgroup.pageflow.editor.diagram.custom.properties.section;

import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.ReferencedTextWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

public class PageflowTechnicalPropertySection extends AbstractPropertiesSection {

	private static final String errorViewToolTip = Messages.PropertiesActionURIToolTip;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.odcgroup.workbench.gmf.editors.properties.AbstractPropertiesSection
	 * #configureProperties()
	 */
	protected void configureProperties() {
		
		setUseThreeFourthLayout(true);
		SimpleGroupWidget group = new SimpleGroupWidget(null);	
		
		// create errorUrl text property control
		ReferencedTextWidget errorUrl = new ReferencedTextWidget(
				PageflowPackage.eINSTANCE.getPageflow_ErrorView(),
				PageflowPackage.eINSTANCE.getView_Url(),
				Messages.PageflowTechnicalPropertySectionErrorURILabel,
				errorViewToolTip);
		errorUrl.setFillHorizontal(true);
		group.addPropertyFeature(errorUrl);
		
		// create targetfile text property control
		SimpleTextWidget targetFile = new SimpleTextWidget(PageflowPackage.eINSTANCE.getPageflow_FileName(),
				Messages.PageflowTechnicalPropertySectionFileNameLabel);
		targetFile.setFillHorizontal(true);
		group.addPropertyFeature(targetFile);

		// create techDesc text property control
		SimpleTextWidget pageflowTechDesc = new SimpleTextWidget(PageflowPackage.eINSTANCE.getPageflow_TechDesc(),
				Messages.TechnicalPropertySectionDescLabel);
		pageflowTechDesc.setMultiline(true);
		group.addPropertyFeature(pageflowTechDesc);
		
		this.addPropertyFeature(group);		
	}

}
