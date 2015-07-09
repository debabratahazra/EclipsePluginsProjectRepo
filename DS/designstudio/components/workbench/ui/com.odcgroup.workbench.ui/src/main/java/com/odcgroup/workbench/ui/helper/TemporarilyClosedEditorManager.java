package com.odcgroup.workbench.ui.helper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.odcgroup.workbench.ui.OfsUICore;

/**
 * This class closes the open editors and allow to reopen them after
 * a certain task has been done.
 * @author yan
 */
public class TemporarilyClosedEditorManager {
	
	private List<IFile> closedFiles = new ArrayList<IFile>();

	private String[] extensions;
	
	/**
	 * Constructor (to close all type of editor)
	 */
	public TemporarilyClosedEditorManager() {
	}
	
	/**
	 * Constructor
	 * @param extensions editor with these extensions will closed
	 */
	public TemporarilyClosedEditorManager(String... extensions) {
		this.extensions = extensions;
	}
	
	/**
	 * Apply the logic to select the files to be closed (by
	 * default based on extensions.
	 * @param file
	 * @return {@code true} if the file opened in the editor should be closed,
	 * {@code false} otherwise.
	 */
	protected boolean acceptFile(IFile file) {
		if (extensions == null) {
			return true;
		}
		
		for (String extension : extensions) {
			if (file.getName().endsWith("." + extension)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Close all open editor that match the criteria defined in acceptFile 
	 */
	public void closeEditors() {
		closedFiles.clear();
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorReference[] references = page.getEditorReferences();
		for (IEditorReference ref : references) {
			IEditorPart editor = ref.getEditor(false);
			if (editor != null && editor.getEditorSite() != null) {
				IEditorInput input = editor.getEditorInput();
				if (input instanceof FileEditorInput) {
					FileEditorInput fei = (FileEditorInput)input;
					if (acceptFile(fei.getFile())) {
						closedFiles.add(0,fei.getFile());
						page.closeEditor(editor, editor.isDirty());
					}
				}
			}
		}
	}
	
	/**
	 * Restore (reopen) all closed editor
	 */
	public void restoreEditors() {
		// reopen files that has been closed
		for (IFile file : closedFiles) {
	        IWorkbenchPage page = OfsUICore.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
	        if (page != null) {
	            try {
	        		String id = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName()).getId();
	        		page.openEditor(new FileEditorInput(file), id, true);
				} catch (PartInitException ex) {
					ex.printStackTrace();
				}
	        }
		}
		closedFiles.clear();
	}


}
