package com.odcgroup.t24.mdf.editor.ui;

import org.eclipse.ui.IEditorPart;

import com.odcgroup.mdf.editor.ui.actions.DeriveFromBaseClassAction;
import com.odcgroup.mdf.editor.ui.editors.DomainEditorContributorProvider;
import com.odcgroup.mdf.editor.ui.editors.DomainModelActionHandler;

public class T24DomainModelContributorProvider implements
		DomainEditorContributorProvider {

	public T24DomainModelContributorProvider() {
	}

	@Override
	public DomainModelActionHandler getActionHandler(
			DeriveFromBaseClassAction copyBaseClassAction,
			IEditorPart activeEditorPart) {
		return new T24DomainModelActionHandler(copyBaseClassAction, activeEditorPart);
	}

}
