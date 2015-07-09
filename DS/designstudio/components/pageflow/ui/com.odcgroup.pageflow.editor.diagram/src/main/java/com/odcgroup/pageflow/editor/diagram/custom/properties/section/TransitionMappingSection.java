package com.odcgroup.pageflow.editor.diagram.custom.properties.section;

import org.eclipse.swt.widgets.Shell;

import com.odcgroup.pageflow.editor.diagram.custom.dialog.TransitionMappingDefinitionDialog;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowFactory;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.AbstractListReferenceSection;
import com.odcgroup.workbench.editors.properties.util.SectionListReferencePropertyHelper;
import com.odcgroup.workbench.editors.ui.dialog.AbstractTitleAreaDialog;

public class TransitionMappingSection extends AbstractListReferenceSection {
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.propeties.AbstractListReferenceSection#getPopuopDialog()
	 */
	public AbstractTitleAreaDialog getPopuopDialog(Shell shell) {
		return new TransitionMappingDefinitionDialog(shell);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.propeties.AbstractListReferenceSection#getReferenceUtil()
	 */
	protected SectionListReferencePropertyHelper getReferenceUtil() {
		return new SectionListReferencePropertyHelper(Messages.TransitionMappingSectionMappingLabel, PageflowPackage.eINSTANCE.getSubPageflowState_TransitionMappings(),null, PageflowFactory.eINSTANCE,PageflowPackage.eINSTANCE.getTransitionMapping());
	}

}
