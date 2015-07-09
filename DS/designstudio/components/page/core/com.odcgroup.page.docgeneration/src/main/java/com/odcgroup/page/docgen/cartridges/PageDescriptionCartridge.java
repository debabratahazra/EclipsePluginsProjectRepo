package com.odcgroup.page.docgen.cartridges;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.documentation.generation.DocGenerator;
import com.odcgroup.page.docgen.cartridges.mapper.PageModel2PageDocumentation;
import com.odcgroup.page.docgen.constants.PageDesignerDocumentGenerationConstants;
import com.odcgroup.page.docgen.utils.GenerationUtils;
import com.odcgroup.page.model.Model;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

public class PageDescriptionCartridge implements DocGenerator {

	public PageDescriptionCartridge() {
	}

	@Override
	public void run(IProject project,
			Collection<IOfsModelResource> modelResources, IFolder outputFolder,
			IProgressMonitor monitor) throws CoreException,
			InterruptedException {

		Collection<IOfsModelResource> pageResources = OfsResourceHelper.filter(
				modelResources, new String[] { "page" });
		outputFolder=null;
		for (IOfsModelResource res : pageResources) {
			String result;
			PageModel2PageDocumentation pm2pd = new PageModel2PageDocumentation();
			try{
			EObject resrceObj = res.getEMFModel()
					.get(0);
			Model resrceModel = (Model) resrceObj;
			String pageFileName = res.getURI().path().substring(1, res.getURI().path().lastIndexOf("."));
			String standardPagePath = project.getName() + PageDesignerDocumentGenerationConstants.DOCUMENTATION_PAGES_PATH;
			String defaultPath =  GenerationUtils.getOutputPathFromPreference(project) + standardPagePath;  
			String pageFolderPath = (outputFolder!=null ? (outputFolder + "\\" + standardPagePath) : defaultPath) + "\\" + pageFileName;

			try {
				File file = new File(pageFolderPath);
				if (!file.exists()) {
					file.mkdirs();
				}

				CharSequence pageDescription = pm2pd.pageDescription(resrceModel);
				result = (pageDescription != null && pageDescription
						.length() != 0) ? pageDescription.toString()
						: "";
				BufferedWriter out = new BufferedWriter(
						new FileWriter(pageFolderPath + PageDesignerDocumentGenerationConstants.PAGE_DESCRIPTION_HTML_FILE));
				out.write(result);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally{
				try{
					//read content from folder
					List<String> fileList = new ArrayList<String>();
					GenerationUtils.readAllFiles(pageFolderPath,fileList);
					GenerationUtils.pageFilenameSorter(fileList);
					//print to file
					result = (pm2pd.pageIndex(pageFolderPath ,fileList)).toString();
					BufferedWriter out = new BufferedWriter(new FileWriter(pageFolderPath + PageDesignerDocumentGenerationConstants.PAGE_INDEX_HTML_FILE));
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
		return true;
	}
}
