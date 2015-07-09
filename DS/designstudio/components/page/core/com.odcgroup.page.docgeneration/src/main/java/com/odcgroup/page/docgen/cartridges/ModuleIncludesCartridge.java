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
import com.odcgroup.page.docgen.cartridges.mapper.ModuleModel2ModuleDocumentation;
import com.odcgroup.page.docgen.constants.PageDesignerDocumentGenerationConstants;
import com.odcgroup.page.docgen.utils.GenerationUtils;
import com.odcgroup.page.model.Model;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

public class ModuleIncludesCartridge implements DocGenerator {

	public ModuleIncludesCartridge() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(IProject project,
			Collection<IOfsModelResource> modelResources, IFolder outputFolder,
			IProgressMonitor monitor) throws CoreException,
			InterruptedException {

		Collection<IOfsModelResource> moduleResources = OfsResourceHelper.filter(
				modelResources, new String[] { "module" });
		moduleGeneration(project, moduleResources);
		
		Collection<IOfsModelResource> fragmentResources = OfsResourceHelper.filter(
				modelResources, new String[] { "fragment" });
		fragmentGeneration(project, fragmentResources);
		
		monitor.worked(35);
	}

	/**
	 * Document Generation for Module
	 * @param project
	 * @param moduleResources
	 */
	private void moduleGeneration(IProject project,
			Collection<IOfsModelResource> moduleResources) {
		IFolder outputFolder;
		outputFolder=null;
		for (IOfsModelResource res : moduleResources) {
			String result;
			ModuleModel2ModuleDocumentation mm2pd = new ModuleModel2ModuleDocumentation();
			try{
			EObject resrceObj = res.getEMFModel()
					.get(0);
			Model resrceModel = (Model) resrceObj;
			String moduleFileName = res.getURI().path().substring(1, res.getURI().path().lastIndexOf("."));
			String standardPagePath = project.getName() + PageDesignerDocumentGenerationConstants.DOCUMENTATION_MODULES_PATH;
			String defaultPath =  GenerationUtils.getOutputPathFromPreference(project) + standardPagePath;  
			String moduleFolderPath = (outputFolder!=null ? (outputFolder + "\\" + standardPagePath) : defaultPath) + "\\" + moduleFileName;

			try {
				File file = new File(moduleFolderPath);
				if (!file.exists()) {
					file.mkdirs();
				}

				CharSequence moduleIncludes = mm2pd.moduleIncludes(resrceModel,res.getOfsProject());
				if (moduleIncludes!=null && moduleIncludes.length() != 0) {
					result = moduleIncludes.toString();
					BufferedWriter out = new BufferedWriter(
							new FileWriter(
									moduleFolderPath
											+ PageDesignerDocumentGenerationConstants.MODULE_INCLUDE_HTML));
					out.write(result);
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally{
				try{
					//read content from folder
					List<String> fileList = new ArrayList<String>();
					GenerationUtils.readAllFiles(moduleFolderPath,fileList);
					GenerationUtils.moduleFilenameSorter(fileList);
					//print to file
					result = (mm2pd.moduleIndex(moduleFolderPath ,fileList)).toString();
					BufferedWriter out = new BufferedWriter(new FileWriter(moduleFolderPath + PageDesignerDocumentGenerationConstants.INDEX_HTML_FILE));
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
	}
	
	/**
	 * Document Generation for Fragment
	 * @param project
	 * @param fragmentResources
	 */
	private void fragmentGeneration(IProject project,
			Collection<IOfsModelResource> fragmentResources) {
		IFolder outputFolder;
		outputFolder=null;
		for (IOfsModelResource res : fragmentResources) {
			String result;
			ModuleModel2ModuleDocumentation mm2pd = new ModuleModel2ModuleDocumentation();
			try{
			EObject resrceObj = res.getEMFModel()
					.get(0);
			Model resrceModel = (Model) resrceObj;
			String fragmentFileName = res.getURI().path().substring(1, res.getURI().path().lastIndexOf("."));
			String standardPagePath = project.getName() + PageDesignerDocumentGenerationConstants.DOCUMENTATION_FRAGMENT_PATH;
			String defaultPath =  GenerationUtils.getOutputPathFromPreference(project) + standardPagePath;  
			String fragmentFolderPath = (outputFolder!=null ? (outputFolder + "\\" + standardPagePath) : defaultPath) + "\\" + fragmentFileName;

			try {
				File file = new File(fragmentFolderPath);
				if (!file.exists()) {
					file.mkdirs();
				}

				CharSequence fragmentIncludes = mm2pd.fragmentIncludes(resrceModel,res.getOfsProject());
				if (fragmentIncludes!=null && (fragmentIncludes.length() != 0)) {
					result = fragmentIncludes.toString();
					BufferedWriter out = new BufferedWriter(
							new FileWriter(
									fragmentFolderPath
											+ PageDesignerDocumentGenerationConstants.FRAGMENT_INCLUDE_HTML));
					out.write(result);
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally{
				try{
					//read content from folder
					List<String> fileList = new ArrayList<String>();
					GenerationUtils.readAllFiles(fragmentFolderPath,fileList);
					GenerationUtils.fragmentFilenameSorter(fileList);
					//print to file
					result = (mm2pd.moduleIndex(fragmentFolderPath ,fileList)).toString();
					BufferedWriter out = new BufferedWriter(new FileWriter(fragmentFolderPath + PageDesignerDocumentGenerationConstants.INDEX_HTML_FILE));
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
	}


	@Override
	public boolean isValidForProject(IProject project) {
		return true;
	}

}
