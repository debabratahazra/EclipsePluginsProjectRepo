package com.odcgroup.page.ui.navigator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.wst.sse.core.utils.StringUtils;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.ui.navigator.OfsLinkHelper;

/**
 * DS-3858 : This class avoid to activate the editor.
 * 
 * @author atr
 */
public class PageLinkHelper extends OfsLinkHelper {
	
	private boolean isDragActive = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ui.navigator.ILinkHelper#activateEditor(org.eclipse.ui.
	 * IWorkbenchPage, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void activateEditor(IWorkbenchPage aPage, IStructuredSelection aSelection) {
		
		//System.out.println("activateEditor: "+isDragActive);

		if (aSelection == null || aSelection.isEmpty()) {
			return;
		}

		Object obj = aSelection.getFirstElement();
		if (obj instanceof IOfsModelResource) {
			IOfsModelResource ofsResource = (IOfsModelResource) obj;
			URI uri = ofsResource.getURI();
			String extension = uri.fileExtension();
			if (extension != null && StringUtils.contains(PageConstants.PAGE_DESIGNER_FILE_EXTENSIONS, extension, true)) {
				// check if an editor is alread open for the given uri.
				String filename = uri.lastSegment();
				String id = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(filename).getId();
				for (IEditorReference editor : aPage.getEditorReferences()) {
					if (editor.getId().equals(id)) {
						try {
							IEditorInput eInput = editor.getEditorInput();
							if (eInput instanceof FileEditorInput) {
								FileEditorInput fInput = (FileEditorInput) eInput;
								if (filename.equals(fInput.getName())) {
									if (isDragActive) {
										// an editor is already open, simply return
										// to avoid the activation of this editor.
										isDragActive = false;
										return;
									}
								}
							}
						} catch (PartInitException ex) {
							// silently ignore
						}
					}
				}

			}
		}

		// editor not found
		super.activateEditor(aPage, aSelection);
		

	}

	public PageLinkHelper() {

		// drag-detector
		PlatformUI.getWorkbench().getDisplay().addFilter(SWT.DragDetect, new Listener() {
			@Override
			public void handleEvent(Event event) {
				isDragActive = false;
				if (event.widget instanceof Tree) {
					Tree tree = (Tree)event.widget;
				    int count = tree.getSelectionCount();
				    for (TreeItem treeItem : tree.getSelection()) {
				    	Object data = treeItem.getData();
				    	if (data instanceof IOfsModelResource) {
				    		// consider only page resources
							IOfsModelResource ofsResource = (IOfsModelResource) data;
							URI uri = ofsResource.getURI();
							String extension = uri.fileExtension();
							if (StringUtils.contains(PageConstants.PAGE_DESIGNER_FILE_EXTENSIONS, extension, true)) {
								count--;
								//System.out.println("drag-detect: count="+count);
							}
				    	}
				    }
				    isDragActive = count == 0;
				}
				//System.out.println("drag-detect: "+isDragActive);
			}
		});
	}
}
