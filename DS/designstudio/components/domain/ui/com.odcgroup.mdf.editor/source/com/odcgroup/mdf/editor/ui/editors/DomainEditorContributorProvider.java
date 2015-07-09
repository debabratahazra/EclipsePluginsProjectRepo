package com.odcgroup.mdf.editor.ui.editors;

import org.eclipse.ui.IEditorPart;

import com.odcgroup.mdf.editor.ui.actions.DeriveFromBaseClassAction;

public interface DomainEditorContributorProvider {
	public DomainModelActionHandler getActionHandler(DeriveFromBaseClassAction copyBaseClassAction, IEditorPart activeEditorPart);
}
