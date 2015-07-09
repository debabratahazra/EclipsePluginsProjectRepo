package com.odcgroup.t24.enquiry.model.ui.editor;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.xtext.ui.editor.XtextEditor;


public class EnquiryEditor extends XtextEditor {

	@Override
	protected void initializeKeyBindingScopes() {
		// TODO Auto-generated method stub
		super.initializeKeyBindingScopes();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		IContextService contextService = (IContextService) getSite().getService(IContextService.class);
		if (contextService != null) {
			contextService.activateContext("com.odcgroup.t24.enquiry.model.ui.context");
		}
	}

}
