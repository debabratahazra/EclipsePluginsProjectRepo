package com.odcgroup.t24.version.editor.ui.providers;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.part.FileEditorInput;

import com.odcgroup.t24.version.editor.utils.VersionUtils;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.ui.helper.OfsEditorUtil;

public class OpenVersionModelAction extends BaseSelectionListenerAction {
    
	@Override
	public void run() {
		IOfsModelResource resource = (IOfsModelResource) getStructuredSelection().getFirstElement();
		IEditorPart part = OfsEditorUtil.window.getActivePage().findEditor(new FileEditorInput(OfsResourceHelper.getFile(resource)));
		if(part == null && VersionUtils.isMoreVersionEditorOpen()) {
			return;
		}
		if(part == null && OfsEditorUtil.isCorrectVersion(resource)) {
			OfsEditorUtil.openEditor(resource);
		}

	}

	protected OpenVersionModelAction() {
		super("Open Version Model");
		
	}


}
