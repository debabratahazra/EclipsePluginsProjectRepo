package com.odcgroup.pageflow.editor.diagram.custom.properties.section;

import org.eclipse.emf.ecore.EReference;

import com.odcgroup.pageflow.editor.diagram.custom.dialog.TransitionActionDefinitionDialogCreator;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.gmf.widgets.TablePropertyWithActionsWidget;
import com.odcgroup.workbench.editors.properties.item.PropertyTableColumn;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;

public class TransitionPropertySection extends AbstractPropertiesSection {


	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {
		//DS-1562
		final PageflowPackage pflowPckg = PageflowPackage.eINSTANCE;
		
		EReference ref = pflowPckg.getTransition_Actions();

		SimpleGroupWidget group = new SimpleGroupWidget(null);			
		
		TablePropertyWithActionsWidget tProperty = new TablePropertyWithActionsWidget(ref);
		tProperty.addTableColumn(new PropertyTableColumn(Messages.TransitionPropertySectionNameLabel, pflowPckg.getAction_Name(), 3));
		tProperty.addTableColumn(new PropertyTableColumn(Messages.TransitionPropertySectionURILabel, pflowPckg.getAction_Uri(), 5));
		tProperty.addTableColumn(new PropertyTableColumn(Messages.TransitionPropertySectionTypeLabel, pflowPckg.getTransitionAction_ProblemManagement(), 2));
		
		tProperty.setSortable(true);
		tProperty.setDialogCreator(new TransitionActionDefinitionDialogCreator());
		
		group.addPropertyFeature(tProperty);
		addPropertyFeature(group);
	}

}
