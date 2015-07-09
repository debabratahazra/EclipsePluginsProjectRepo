package com.odcgroup.pageflow.docgen.cartridges;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.osgi.framework.Bundle;

import com.odcgroup.documentation.generation.DocGenerator;
import com.odcgroup.pageflow.docgen.PageflowDocGenerationCore;
import com.odcgroup.pageflow.docgen.cartridges.mapper.PageflowDocumentation;
import com.odcgroup.pageflow.docgen.constants.PageflowDocgenConstants;
import com.odcgroup.pageflow.docgen.utils.GenerationUtils;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

public class PageflowTransitionsCartridge implements DocGenerator {

	public PageflowTransitionsCartridge() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(IProject project,
			Collection<IOfsModelResource> modelResources, IFolder outputFolder,
			IProgressMonitor monitor) throws CoreException,
			InterruptedException {

		Collection<IOfsModelResource> pageResources = OfsResourceHelper.filter(
				modelResources, new String[] { "pageflow" });
		outputFolder=null;
		for (IOfsModelResource res : pageResources) {
			String result;
			PageflowDocumentation pf2d = new PageflowDocumentation();
			try{
			EObject resrceObj = res.getEMFModel()
					.get(0);
			Pageflow resrceModel = (Pageflow) resrceObj;
			String pageflowFileName = res.getURI().path().substring(1, res.getURI().path().lastIndexOf("."));
			String standardPagePath = project.getName() + PageflowDocgenConstants.DOCUMENTATION_PAGEFLOWS_PATH;
			String defaultPath =  GenerationUtils.getOutputPathFromPreference(project) + standardPagePath;  
			String pageflowFolderPath = (outputFolder!=null ? (outputFolder + "\\" + standardPagePath) : defaultPath) + "\\" + pageflowFileName;

			try {
				File file = new File(pageflowFolderPath);
				if (!file.exists()) {
					file.mkdirs();
				}

				Bundle bundle = Platform.getBundle(PageflowDocGenerationCore.PLUGIN_ID);
				Path path = new Path("icons");
				URL fileURL = FileLocator.find(bundle, path, null);
				String bundleLoc = FileLocator.resolve(fileURL).getPath().substring(1);
				
				File source = new File(bundleLoc);
				File desc = new File(pageflowFolderPath);
				try {
				    FileUtils.copyDirectory(source, desc);
				} catch (IOException e) {
				    e.printStackTrace();
				}
				
				CharSequence pageflowTransitions = pf2d.pageflowTransitions(resrceModel);
				result = (pageflowTransitions != null && pageflowTransitions
						.length() != 0) ? pageflowTransitions.toString()
						: "";
				BufferedWriter out = new BufferedWriter(
						new FileWriter(pageflowFolderPath + PageflowDocgenConstants.PAGEFLOW_TRANSITIONS_HTML_FILE));
				out.write(result);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally{
				try{
					//read content from folder
					List<String> fileList = new ArrayList<String>();
					GenerationUtils.readAllFiles(pageflowFolderPath,fileList);
					GenerationUtils.pageflowFilenameSorter(fileList);
					//print to file
					result = (pf2d.pageflowIndex(pageflowFolderPath ,fileList)).toString();
					BufferedWriter out = new BufferedWriter(new FileWriter(pageflowFolderPath + PageflowDocgenConstants.PAGEFLOW_INDEX_HTML_FILE));
					out.write(result);
					out.close();
					fileList.clear();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			catch (InvalidMetamodelVersionException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		monitor.worked(5);
	}

	@Override
	public boolean isValidForProject(IProject project) {
		// TODO Auto-generated method stub
		return true;
	}

}
