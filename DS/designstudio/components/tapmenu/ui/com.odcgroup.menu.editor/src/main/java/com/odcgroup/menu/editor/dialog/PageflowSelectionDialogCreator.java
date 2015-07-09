package com.odcgroup.menu.editor.dialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelResourceLookup;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialog;
import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialogCreator;

/**
 * @author pkk
 *
 */
public class PageflowSelectionDialogCreator implements	IPropertySelectionDialogCreator {
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialogCreator#createDialog(org.eclipse.swt.widgets.Shell, org.eclipse.emf.ecore.EObject)
	 */
	public IPropertySelectionDialog createDialog(Shell shell, EObject element) {
		IOfsProject ofsProject = OfsResourceHelper.getOfsProject(element.eResource());
		String[] extensions = {"pageflow"};
		ModelResourceLookup lookup = new ModelResourceLookup(ofsProject, extensions);
		Set<IOfsModelResource> set = lookup.getAllOfsModelResources();
		List<EObject> list = new ArrayList<EObject>();
		for (IOfsModelResource res : set) {
			EObject obj = getModel(res);
			if (obj != null) {
				list.add(obj);
			}
		}
		
		PageflowSelectionDialog dialog = PageflowSelectionDialog.createDialog(shell, list.toArray());
		return dialog;
	}
	
	/**
	 * @param res
	 * @return
	 */
	private EObject getModel(IOfsModelResource res) {
		try {
			List<EObject> list = res.getEMFModel();			
			if (!list.isEmpty()) {
				return list.get(0);
			}
		} catch (IOException e) {
		} catch (InvalidMetamodelVersionException e) {
		}
		return null;
	}

}
