package com.odcgroup.workbench.ui.internal.navigator;

import java.util.Iterator;

import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.OpenFileAction;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.ui.helper.OfsEditorUtil;

public class OpenModelFileAction extends OpenFileAction {

	public OpenModelFileAction(IWorkbenchPage page) {
		super(page);
	}

	@Override
	public void run() {
		Iterator itr = getSelectedResources().iterator();
		while (itr.hasNext()) {
			IResource resource = (IResource) itr.next();
			IOfsModelResource modelResource = (IOfsModelResource) resource.getAdapter(IOfsModelResource.class);
			if(modelResource!=null) {
				if(!OfsEditorUtil.isCorrectVersion(modelResource)) return;
			}
		}
		super.run();
	}
}
