package com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Shell;

import com.odcgroup.pageflow.editor.diagram.custom.dialog.TransitionActionDefinitionDialog;
import com.odcgroup.pageflow.editor.diagram.custom.properties.section.PageflowBasicPropertySection;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowFactory;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.util.ListReferenceColumn;
import com.odcgroup.workbench.editors.properties.util.SectionListReferencePropertyHelper;
import com.odcgroup.workbench.editors.ui.dialog.AbstractTitleAreaDialog;

public class TransitionPropertySection extends PageflowBasicPropertySection {


	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {
		//DS-1562
		SectionListReferencePropertyHelper transitionActions = new SectionListReferencePropertyHelper(Messages.TransitionActionDefDialogTitle, PageflowPackage.eINSTANCE.getTransition_Actions(),null, PageflowFactory.eINSTANCE, PageflowPackage.eINSTANCE.getTransitionAction(),true, false);
		List<ListReferenceColumn> columns = new ArrayList<ListReferenceColumn>();
		columns.add(new ListReferenceColumn(Messages.TransitionPropertySectionNameLabel, PageflowPackage.eINSTANCE.getAction_Name().getName(), 25, 150));
		//columns.add(new ListReferenceColumn(Messages.TransitionPropertySectionDescLabel, PageflowPackage.eINSTANCE.getAction_Desc().getName(), 20));
		columns.add(new ListReferenceColumn(Messages.TransitionPropertySectionURILabel, PageflowPackage.eINSTANCE.getAction_Uri().getName(), 65, 400));
		columns.add(new ListReferenceColumn(Messages.TransitionPropertySectionTypeLabel, PageflowPackage.eINSTANCE.getTransitionAction_ProblemManagement().getName(), 10, 80));
		transitionActions.setColumns(columns);
		transitionActions.setMultiSelect(true);
		transitionActions.addCopyAction();
		addListReference(transitionActions);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.AbstractBasicPropertiesSection#getPopuopDialog(org.eclipse.swt.widgets.Shell)
	 */
	public AbstractTitleAreaDialog getPopuopDialog(Shell shell) {
		return new TransitionActionDefinitionDialog(shell);
	}

}
