package com.odcgroup.edge.t24ui.model.ui.wizard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;

import com.odcgroup.edge.t24ui.model.ui.action.NewCOSUtil;
import com.odcgroup.workbench.ui.wizards.AbstractNewModelResourceCreationPage;
import com.odcgroup.workbench.ui.wizards.AbstractNewModelResourceCreationWizard;

public class T24CompositeScreenWizard extends AbstractNewModelResourceCreationWizard {
	
	private T24CompositeScreenWizardPage page;
	private IPath containerFullPath;
	private static final String XTextNatureId = "org.eclipse.xtext.ui.shared.xtextNature";

	public T24CompositeScreenWizard() {
	}
	
	@Override
	public void addPages() {
		super.addPages();
		if (page == null) {
			if (containerFullPath == null) {
				containerFullPath = getSelectionFullPath();
			}
			page = new T24CompositeScreenWizardPage("COS", getWorkbench(), containerFullPath, "eson");
		}
		addPage(page);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
	}

	@Override
	public boolean performFinish() {
		if(canFinish()){
			try{
			final IPath selectionFullPath = getSelectionFullPath();
			final String cosName = page.getFileName().replace(".eson", "");
			final String cosDomain = deduceCompositeScreenDomain(selectionFullPath.toFile(), cosName);
			final IFile file = NewCOSUtil.createNewFile(cosName + ".eson", selectionFullPath);
			StringBuffer content = esonFileContent(cosName, cosDomain);
			writeToTextEditor(file, content);
			addXtextProjectNature(file);
			// open editor
			NewCOSUtil.openEditor(file);
			} catch (Exception e) {
				Logger.getLogger(" Create / Write to file failed " + e);
			}
		}
		return true;
	}
	
	private void addXtextProjectNature(IFile iFile) {
		try {
			//Adding XText nature in Project
			IProject project = iFile.getProject();
			IProjectDescription desc = project.getDescription();
			String[] oldNatureIds = desc.getNatureIds();
			for (String nature : oldNatureIds) {
				if(nature.equals(XTextNatureId))
					return; // Ignore to add xtext nature
			}
			String [] natureIds = new String[oldNatureIds.length + 1];
			System.arraycopy(oldNatureIds, 0, natureIds, 0, oldNatureIds.length);
			natureIds[oldNatureIds.length] = XTextNatureId;
			desc.setNatureIds(natureIds);
			project.setDescription(desc, null);
		} catch (Exception e) {
		}
	}

	@Override
	public String getWindowTitle() {
		return "COS";
	}
	
	private static String deduceCompositeScreenDomain(File dir, String cosName) {
		final String fullPath = dir.toString().replace('\\', '/');
		final String toFind = "/eson/COS/";
		final int i = fullPath.indexOf(toFind);
		if (i >= 0){
			final String subPath = fullPath.substring(i + toFind.length());
			return subPath.replace('/', '.') + '.' + cosName;
		}else{
			/*
			 * No domain, return the cos name
			 */
			return cosName;
		}
	}
	
	private StringBuffer esonFileContent(String cosName, String cosDomain) {
		StringBuffer content = new StringBuffer("");

		content.append("use t24ui.* \n");
		content.append("CompositeScreen " + cosName + "{\n");
		content.append("\tdomain: \"" + cosDomain + "\"\n");
		content.append("\tpattern:Rows\n");
		content.append("\tpanels: [\n");
		content.append("\t\tSingleComponentPanel default {\n");
		content.append("\t\t\thostableContentAll: false\n");
		content.append("\t\t\thostableBespokeCOSContentAll: false\n");
		content.append("\t\t\thostableCOSContentAll: false\n");
		content.append("\t\t\thostableEnquiryContentAll: false\n");
		content.append("\t\t\thostableScreenContentAll: false\n");
		content.append("\t\t\theight: \"auto\"\n");
		content.append("\t\t\twidth: \"auto\"\n");
		content.append("\t\t\thorizontalOverflowOption: :HIDE_OVERFLOW\n");
		content.append("\t\t\tverticalOverflowOption: :HIDE_OVERFLOW\n");
		content.append("\t\t}\n");
		content.append("\t]\n");
		content.append("}\n");

		return content;

	}
	
	private void writeToTextEditor(final IFile file, StringBuffer content) throws PartInitException {
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(file.getLocation().toOSString());
			fout.write(content.toString().getBytes());
			fout.flush();
			fout.close();

		} catch (IOException e) {
			Logger.getLogger(" Writing to file failed " + e);
		} finally {
			try {
				fout.close();
			} catch (IOException e) {
				Logger.getLogger(" Close file failed " + e);
			}
		}

	}

}

class T24CompositeScreenWizardPage extends AbstractNewModelResourceCreationPage {

	protected T24CompositeScreenWizardPage(String pageName, IWorkbench workbench, IPath containerFullPath, String model) {
		super(pageName, workbench, containerFullPath, model);
	}

	@Override
	public boolean doFinish(IProgressMonitor monitor) {
		return true;
	}
}
