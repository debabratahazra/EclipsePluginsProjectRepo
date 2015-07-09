package com.odcgroup.menu.editor.ui;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.odcgroup.menu.model.MenuItem;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.ui.helper.OfsEditorUtil;

public class MenuItemDoubleClickListener implements IDoubleClickListener {

	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
	 */
	public void doubleClick(DoubleClickEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		MenuItem item  = (MenuItem) selection.getFirstElement();
		String url = item.getPageflow();
		if (url == null || url.trim().equals("")) {
			return;
		}
		if (url.startsWith("resource:") && (url.endsWith(".pageflow"))){
			IOfsProject ofsProject = OfsResourceHelper.getOfsProject(item.eResource());
			try {
				IOfsModelResource mres = ofsProject.getOfsModelResource(URI.createURI(url));
				OfsEditorUtil.openEditor(mres);
			} catch (ModelNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
}
