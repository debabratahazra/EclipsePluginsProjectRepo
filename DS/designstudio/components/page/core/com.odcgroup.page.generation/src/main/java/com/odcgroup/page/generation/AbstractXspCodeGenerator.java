package com.odcgroup.page.generation;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.generation.cartridge.CodeGenerator;

/**
 * Implementation of the <code>CodeGenerator</code> interface to allow integration with the workbench code generation
 * process.
 * 
 * @author fch
 * @author Gary Hayes
 */
abstract public class AbstractXspCodeGenerator implements CodeGenerator {
	
	private static Logger logger = LoggerFactory.getLogger(AbstractXspCodeGenerator.class);

	/**
	 * 
	 */
	private static final String OAMS_PROFILES = "/oams-profiles";
	
	/**
	 * @param rootWidget
	 * @return {@code true} if the generation must be done for this root widget
	 */
	protected boolean acceptGeneration(Widget rootWidget) {
		return true;
	}

	
	/**
	 * Execute the code generation.
	 * 
	 * @param project
	 * @param modelResources
	 * @param outputFolder
	 * @throws CoreException
	 */
	public void run(IProgressMonitor monitor, IProject project, Collection<IOfsModelResource> modelResources,
			IFolder outputFolder, List<IStatus> nonOkStatuses) {
		Assert.isNotNull(outputFolder, "output folder must be not be null");
		
		//PageGenerationCore.getDefault().logInfo("Start XSP Generator", null);
		long start = System.currentTimeMillis();

		String baseOutputFilePath = outputFolder.getLocation().toOSString();
		String extension = getHandledFileExtension();
		
		Collection<IOfsModelResource> xspResources = OfsResourceHelper.filter(modelResources, new String[]{extension});

		for (IOfsModelResource modelResource : xspResources) {
			
			// Alter the file extension
	        String rn = modelResource.getURI().lastSegment();
	        String outputFileName = rn.substring(0, rn.indexOf('.')) + "." + PageConstants.XSP_FILE_EXTENSION;			

	        //String projectName = project.getName();
			String outputFilePath = baseOutputFilePath + OAMS_PROFILES + getOutputFilePath(modelResource);
			
			File outputFile = new File(outputFilePath + "/" + outputFileName);
			
			try {
				Model model = (Model)modelResource.getEMFModel().get(0);
				Widget widget = model.getWidget(); // root widget
				if (acceptGeneration(widget)) {
					FileUtils.writeStringToFile(outputFile, TransformUtils.transformWidgetToXmlString(modelResource.getOfsProject().getProject(), widget));
				}
			} catch (IOException e) {
				String errorMsg = "Error generating XML code for widget in " + getClass().getName() + " for resource '"
						+ modelResource.getName() + "'";
				newCoreException(e, nonOkStatuses, errorMsg);
			} catch (InvalidMetamodelVersionException e) {
				String errorMsg = "Error generating XML code for widget in " + getClass().getName() + " for resource '"
						+ modelResource.getName() + "'";
				newCoreException(e, nonOkStatuses, errorMsg);
			}
		}

		long stop = System.currentTimeMillis();
		long duration = stop-start;
		PageGenerationCore.getDefault().logInfo("XSP Generator generates: " + xspResources.size() + " " + extension + "s in " + duration + " ms", null);
	}
	
	/**
	 * Gets the files extension handled by this Code Generator.
	 * 
	 * @return String The file extension handled by this Code Generator
	 */
	abstract protected String getHandledFileExtension();
	
	/**
	 * Gets the output file path relative to the project.
	 * 
	 * @param resource The input resource
	 * @return String The output file path
	 */
	abstract protected String getOutputFilePath(IOfsModelResource resource);
	
	protected void newCoreException(Exception e, List<IStatus> nonOkStatuses, String errorMsg) {
		logger.error(errorMsg, e);
		final IStatus errorStatus = new Status(IStatus.ERROR, PageGenerationCore.PLUGIN_ID, 0,	errorMsg + ": " + e.getMessage(), e);
		nonOkStatuses.add(errorStatus);
	}

}
