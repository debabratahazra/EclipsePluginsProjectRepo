package com.cdt.keil.debug.ui.breakpoints;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.ui.texteditor.ITextEditor;

public class SDKLineBreakpointAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adaptableObject instanceof ITextEditor) {
			ITextEditor editorPart = (ITextEditor) adaptableObject;
			IResource resource = (IResource) editorPart.getEditorInput()
											.getAdapter(IResource.class);
			if (resource != null) {
				String extension = resource.getFileExtension();
				if (extension != null ) { 
					return new SDKLineBreakpointAdapter();
				}
			}			
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Class[] getAdapterList() {
		return new Class[]{
				IToggleBreakpointsTarget.class
		};
	}
}
