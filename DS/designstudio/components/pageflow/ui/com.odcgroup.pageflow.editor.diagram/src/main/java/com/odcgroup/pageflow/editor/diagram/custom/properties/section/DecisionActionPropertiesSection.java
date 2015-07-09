package com.odcgroup.pageflow.editor.diagram.custom.properties.section;

import com.odcgroup.pageflow.editor.diagram.custom.dialog.DecisionActionSelectionDialogCreator;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.BrowsePropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.CompoundBrowsePropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.GroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;
import com.odcgroup.workbench.editors.properties.widgets.TablePropertyWidget;


public class DecisionActionPropertiesSection extends AbstractPropertiesSection {

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection#configureProperties()
	 */
	@Override
	protected void configureProperties() {
		
		setNumColumns(2);
		
		PageflowPackage eInstance = PageflowPackage.eINSTANCE;
		
		BrowsePropertyWidget actionName = new BrowsePropertyWidget(eInstance.getAction_Name(),
				Messages.TransitionActionNameLabel);
		actionName.setBrowseLabel(Messages.SubPageflowGeneralPropertySectionBrowseBtnText);
		actionName.setBrowseTooltip("Browse For Action");
		actionName.setFillHorizontal(true);
		actionName.setBrowseOnly(false);
		actionName.setSelectionDialogCreator(new DecisionActionSelectionDialogCreator());
		
		CompoundBrowsePropertyWidget browseGroup = new CompoundBrowsePropertyWidget(eInstance.getDecisionState_Action(),
				" "+Messages.TransitionActionDefGroup+" ");
		browseGroup.setBrowseWidget(actionName);
		
		SimpleTextWidget actionUri = new SimpleTextWidget(eInstance.getAction_Uri(), Messages.TransitionActionURILabel);
		actionUri.setFillHorizontal(true);
		browseGroup.addPropertyFeature(actionUri);	
		
		SimpleTextWidget actionDesc = new SimpleTextWidget(eInstance.getAction_Desc(), Messages.TransitionActionDescLabel);
		actionDesc.setMultiline(true);
		browseGroup.addPropertyFeature(actionDesc);
		
		this.addPropertyFeature(browseGroup);
		
		GroupWidget propertyGroup = new GroupWidget(eInstance.getDecisionState_Action(), "Properties");
		TablePropertyWidget tProperty = new TablePropertyWidget(eInstance.getAction_Property());
		propertyGroup.addPropertyFeature(tProperty);
		this.addPropertyFeature(propertyGroup);
		
		
	}
	
}
