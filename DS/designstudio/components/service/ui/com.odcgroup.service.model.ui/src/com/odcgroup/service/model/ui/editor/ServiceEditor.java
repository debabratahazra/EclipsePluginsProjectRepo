package com.odcgroup.service.model.ui.editor;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.xtext.ui.editor.XtextEditor;

public class ServiceEditor extends XtextEditor {
	
	private static final String CONTEXT_ID = "com.odcgroup.service.model.ui.context";
	
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		
		super.init(site, input);
		
		IContextService contextService = (IContextService) getSite().getService(IContextService.class);
		if(contextService != null) {
			contextService.activateContext(CONTEXT_ID);
		}
	}
}
