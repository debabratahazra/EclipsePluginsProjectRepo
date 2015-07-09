package com.odcgroup.t24.mdf.editor.ui.actions;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.ui.editors.DomainModelEditor;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.mdf.editor.ui.dialogs.LocalRefSelectionDialog;
import com.odcgroup.workbench.core.IOfsProject;

public class T24DomainNewAttributeAction extends BaseSelectionListenerAction {

	private DomainModelEditor editor = null;
	private IOfsProject project = null;
	NewAttributeCommand command = null;
	private MdfClass owner;
	private EditingDomain domain = null;

	public T24DomainNewAttributeAction(IWorkbenchPart workbenchPart, ISelection selection, IOfsProject project, EditingDomain domain) {
		super("Attribute");
		this.editor = (DomainModelEditor) workbenchPart;
		setText("Attribute");
		this.project  = project;
		setImageDescriptor(MdfPlugin.getImageDescriptor(MdfCore.NEW_ATTR));
		if (selection instanceof IStructuredSelection) {
			updateSelection((IStructuredSelection) selection);
		}
		this.domain  = domain;
		this.command = new NewAttributeCommand(this.owner, this.project, this.editor);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org
	 * .eclipse.jface.viewers.IStructuredSelection)
	 */
	public boolean updateSelection(IStructuredSelection selection) {
		if (selection.getFirstElement() instanceof MdfClass) {
			this.owner = (MdfClass) selection.getFirstElement();
			if (owner != null) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void run() {
		LocalRefSelectionDialog dialog = new LocalRefSelectionDialog(editor.getSite().getShell(), this.project, this.command, this.domain);
		if(dialog.getLocalRefs() != null && dialog.getLocalRefs().size() > 0)
			dialog.open();
		else
			MessageDialog.openInformation(editor.getSite().getShell(), "LocalField Selection", "There are no LocalFields available in the project, please introspect the same from T24 Server before creating a new attribute");
	}
}
