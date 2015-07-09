package com.odcgroup.visualrules.integration.ui.filter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.odcgroup.visualrules.integration.RulesIntegrationUtils;
import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.helper.StringHelper;

/** This class is a filter for the Project Explorer and the OFS Navigator to hide
 *  Visual Rules internal resources.
 *  
 * @author Kai Kreuzer
 *
 */
public class OfsVRResourceFilter extends ViewerFilter {

	public OfsVRResourceFilter() {
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		
		// Filter the rule model folder
		if(element instanceof IAdaptable) {
			Object adapter = ((IAdaptable) element).getAdapter(IOfsModelFolder.class);
			if(adapter instanceof IOfsModelFolder) {
				IOfsModelFolder folder = (IOfsModelFolder) adapter;
				if(folder.getName().equals("rule")) {
					return false;
				}
			}
		}
		
		// filter the rule model file itself
		if(element instanceof IFile) {
			IFile file = (IFile) element;
			if(OfsCore.isOfsProject(file.getProject())) {
				if(element.equals(RulesIntegrationUtils.getRulesFile(file.getProject()))) {
					return false;
				}
			}
		}

		// filter the rule model directory
		if(element instanceof IFolder) {
			IFolder folder = (IFolder) element;
			if(OfsCore.isOfsProject(folder.getProject())) {
				IFile ruleFile = RulesIntegrationUtils.getRulesFile(folder.getProject());
				if(ruleFile!=null) {
					if(StringHelper.withoutExtension(ruleFile.getName()).equals(folder.getName())) {
						return false;
					}
				}
			}
		}

		return true;
	}

}
