package com.odcgroup.process.generation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.mwe.core.WorkflowRunner;
import org.eclipse.emf.mwe.core.monitor.NullProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.process.model.Activator;
import com.odcgroup.process.model.Process;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.generation.cartridge.CodeGenerator;
import com.odcgroup.workbench.generation.cartridge.DetailedMessageCaptureExceptionHandler;

public class WorkflowCodeGenerator implements CodeGenerator {
	
	private static Logger logger = LoggerFactory.getLogger(WorkflowCodeGenerator.class);

	public WorkflowCodeGenerator() {}

	public void run(IProgressMonitor monitor, final IProject project, Collection<IOfsModelResource> modelResources,
			IFolder outputFolder, List<IStatus> nonOkStatuses) {
		final Map<String, List<Process>> resourcesMap = new HashMap<String, List<Process>>();
		final Map<String, String> packageMap = new HashMap<String, String>();
		
		Collection<IOfsModelResource> workflowResources = 
			OfsResourceHelper.filter(modelResources, new String[]{"workflow"});
		
		for(IOfsModelResource modelResource : workflowResources) {
			try {
				Process p = (modelResource.getEMFModel().get(0) instanceof Process)?
						(Process) modelResource.getEMFModel().get(0) :
						(Process) modelResource.getEMFModel().get(1);
				String fileName = p.getFilename();
				if (fileName == null || fileName.length()==0) {
					fileName= project.getName() + "-" + p.getName();
				}
				if (resourcesMap.containsKey(fileName)) {
					resourcesMap.get(fileName).add(p);
				} else {
					List<Process> processList = new ArrayList<Process>();
					processList.add(p);
					resourcesMap.put(fileName, processList);
				}
				if (!packageMap.containsKey(fileName)){
					packageMap.put(fileName, getPackage(modelResource.getURI().toString(), project.getName()));
				}			
			} catch (IOException e) {
				String errorMsg = "Error while code generation in " + getClass().getName() + " for resource '"
						+ modelResource.getName() + "'";
				newCoreException(e, nonOkStatuses, errorMsg);
			} catch (InvalidMetamodelVersionException e) {
				String errorMsg = "Error while code generation in " + getClass().getName() + " for resource '"
						+ modelResource.getName() + "'";
				newCoreException(e, nonOkStatuses, errorMsg);
			}
		}

		for (String resource : resourcesMap.keySet()) {			
			Map<String, List<Process>> slotMap = new HashMap<String, List<Process>>();
			Map<String, String> properties = new HashMap<String, String>();
			slotMap.put("modelSlot", resourcesMap.get(resource));
			properties.put("projectName", project.getName());
			properties.put("packageName", packageMap.get(resource));
			properties.put("workflowFilePrefix", resource);
			properties.put("outlet", outputFolder.getLocation().toOSString());
			try {
				doRun(properties, slotMap);
			} catch (CoreException e) {
				String errorMsg = "Error while code generation in " + getClass().getName();
				newCoreException(e, nonOkStatuses, errorMsg);
			}
		}
		
	}

	/**
	 * Instantiate and execute the OAW workflow
	 * 
	 * @param properties
	 * @param slotMap
	 * @throws CoreException
	 */
	protected void doRun(Map<String, String> properties, Map<String, List<Process>> slotMap) throws CoreException {
		WorkflowRunner runner = new WorkflowRunner();

		try {
			boolean success = runner.run(
					"com/odcgroup/process/generation/ocs/workflow.oaw",
					new NullProgressMonitor(), properties, slotMap);
			if (!success) {
	        	// Try to get a detailed message from the generator
				MultiStatus multiStatus = DetailedMessageCaptureExceptionHandler.retreiveDetailedMessageStatus();
				if (multiStatus != null) {
					throw new CoreException(multiStatus);
				}
				// If no details available, create a generic error message
				newCoreException(new RuntimeException("Error in OAW cartridge, please check the log file!"));
			}
		} catch (RuntimeException e) {
			IStatus status = new Status(Status.ERROR, Activator.PLUGIN_ID,
					Status.OK,
					"Unexpected runtime error in pageflow cartridge", e);
			throw new CoreException(status);
		}
	}

	/**
	 * Exception Wrapper
	 * 
	 * @param t
	 * @return CoreException
	 * @throws CoreException
	 */
	protected CoreException newCoreException(Throwable t) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
				0, t.toString(), t));
	}
	
	protected void newCoreException(Exception e, List<IStatus> nonOkStatuses, String errorMsg) {
		logger.error(errorMsg, e);
		final IStatus errorStatus = new Status(IStatus.ERROR, ProcessGenerationCore.PLUGIN_ID, 0,	errorMsg + ": " + e.getMessage(), e);
		nonOkStatuses.add(errorStatus);
	}
	
	/**
	 * @param resourceURL
	 * @return
	 */
	private static String getPackage(String resourceURL, String projectName){
		String RESOURCE_URL_PREFIX = "resource:/";
		int index = resourceURL.lastIndexOf("/");
		return resourceURL.substring(RESOURCE_URL_PREFIX.length(), index);
	}
}
