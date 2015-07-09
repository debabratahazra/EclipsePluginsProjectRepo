package com.odcgroup.cdm.generation;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.cdm.generation.util.CdmConstants;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.generation.cartridge.CodeGenerator;

/**
 * Base code generator for CDM.
 * 
 * @author Gary Hayes
 */
abstract public class AbstractCdmCodeGenerator implements CodeGenerator {
	
	private static Logger logger = LoggerFactory.getLogger(AbstractCdmCodeGenerator.class);
		
	/**
	 * Called by the framework to run the code generation.
	 * 
	 * @param project
	 *            The project
	 * @param modelResources
	 *            The set of resources to be generated
	 * @param outputFolder
	 *            The output container
     * @throws CoreException Thrown if an error occurs
	 * @throws InvalidMetamodelVersionException 
	 */
	public void run(IProgressMonitor monitor, IProject project, Collection<IOfsModelResource> modelResources,
			IFolder outputFolder, List<IStatus> nonOkStatuses) {

		try {

			Assert.isNotNull(outputFolder, "output folder must be not be null");

			// Make the directories if they do not exist
			String outputFilePath = outputFolder.getLocation().toOSString();
			File f = new File(outputFilePath);
			f.mkdirs();

			Collection<IOfsModelResource> domainResources = OfsResourceHelper.filter(modelResources, new String[] {
					CdmConstants.DOMAIN_FILE_EXTENSION, CdmConstants.MML_FILE_EXTENSION });

			for (IOfsModelResource resource : domainResources) {
				if (resource.getEMFModel().get(0) instanceof MdfDomain) {
					MdfDomain domain = (MdfDomain) resource.getEMFModel().get(0);
					if (!isCDMDomain(domain.getName())) {
						// This is not for CDM
						continue;
					}

					generateFiles(domain, outputFilePath);
				}
			}

		} catch (IOException ioe) {
			String error = "Unable to generate the Cdm Enumerations File.";
			newCoreException(ioe, nonOkStatuses, error);
		} catch (InvalidMetamodelVersionException e) {
			String error = "Unable to generate the Cdm Enumerations File.";
			newCoreException(e, nonOkStatuses, error);
		}
	}
	
	/**
	 * check for valid cdm domains
	 * 
	 * @param domain
	 * @return
	 */
	private boolean isCDMDomain(String domain) {
		for (String domainName : CdmConstants.VALID_CDM_DOMAINS) {
			if (domainName.equals(domain)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Generates the files.
	 * 
	 * @param domain The Mdf Domain
	 * @param outputFilePath The output file path
	 */
	abstract protected void generateFiles(MdfDomain domain, String outputFilePath);
	
	protected void newCoreException(Exception e, List<IStatus> nonOkStatuses, String errorMsg) {
		logger.error(errorMsg, e);
		final IStatus errorStatus = new Status(IStatus.ERROR, CdmGenerationCore.PLUGIN_ID, 0,	errorMsg + ": " + e.getMessage(), e);
		nonOkStatuses.add(errorStatus);
	}
}
