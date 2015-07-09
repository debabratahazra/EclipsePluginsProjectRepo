package com.odcgroup.pageflow.generation;

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

import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.generation.cartridge.CodeGenerator;
import com.odcgroup.workbench.generation.cartridge.DetailedMessageCaptureExceptionHandler;

/**
 * 
 * @author nba
 * 
 */
public class PageflowGenerator implements CodeGenerator {
	
	private static Logger logger = LoggerFactory.getLogger(PageflowGenerator.class);

	@Override
	public void run(IProgressMonitor monitor, final IProject project, Collection<IOfsModelResource> modelResources,
			IFolder outputFolder, List<IStatus> nonOkStatuses) {
		final Map<String, List<Pageflow>> resourcesMap = new HashMap<String, List<Pageflow>>();
		final Map<String, String> packageMap = new HashMap<String, String>();

		Collection<IOfsModelResource> pageflowResources = 
			OfsResourceHelper.filter(modelResources, new String[]{"pageflow"});

		for(IOfsModelResource modelResource : pageflowResources) {
			try {
				Pageflow p = (modelResource.getEMFModel().get(0) instanceof Pageflow)?
							(Pageflow) modelResource.getEMFModel().get(0) :
							(Pageflow) modelResource.getEMFModel().get(1);
							
							
				p = new PageflowMerger(p).merge();
							
							
				String fileName = p.getFileName();
				// modified by MKA for issue OFSFMK-669 and OFSFMK-676
				if (fileName == null || fileName.length()==0) {
					// ignore this pageflow - see OFSFMK-773
					continue;
				}
				if (resourcesMap.containsKey(fileName)) {
					resourcesMap.get(fileName).add(p);
				} else {
					List<Pageflow> pageflowModelList = new ArrayList<Pageflow>();
					pageflowModelList.add(p);
					resourcesMap.put(fileName, pageflowModelList);
				}
				if (!packageMap.containsKey(fileName)){
					packageMap.put(fileName, getPackage(modelResource.getURI().toString(), project.getName()));
				}
			} catch (IOException e) {
				String errorMsg = "Error while code generating in " + getClass().getName() + " for resource '"
						+ modelResource + "'";
				newCoreException(e, nonOkStatuses, errorMsg);
			} catch (InvalidMetamodelVersionException e) {
				String errorMsg = "Error while code generating in " + getClass().getName() + " for resource '"
						+ modelResource + "'";
				newCoreException(e, nonOkStatuses, errorMsg);
			}						
		}
		for (String resource : resourcesMap.keySet()) {
			Map<String, List<Pageflow>> slotMap = new HashMap<String, List<Pageflow>>();
			Map<String, String> properties = new HashMap<String, String>();
			slotMap.put("pageflowModelSlot", resourcesMap.get(resource));
			properties.put("projectName", project.getName());
			properties.put("systemUser", System.getProperty("user.name"));
			properties.put("pageflowModelSlot", "pageflowModelSlot");
			properties.put("pageflowFileName", resource);
			properties.put("packageName", packageMap.get(resource));
			properties.put("outlet", outputFolder.getLocation().toOSString());
			try {
				doRun(properties, slotMap);
			} catch (CoreException e) {
				String errorMsg = "Error while executing the OAW workflow in " + getClass().getName()
						+ " for resource '" + resource + "'";
				newCoreException(e, nonOkStatuses, errorMsg);
			}
		}

	}

	/**
	 * @param e
	 * @param nonOkStatuses
	 * @param string
	 */
	private void newCoreException(Exception e, List<IStatus> nonOkStatuses, String errorMsg) {
		logger.error(errorMsg, e);
		final IStatus errorStatus = new Status(IStatus.ERROR, PageflowGenerationCore.PLUGIN_ID, 0,	errorMsg + ": " + e.getMessage(), e);
		nonOkStatuses.add(errorStatus);
	}

	/**
	 * Instantiate and execute the OAW workflow
	 * 
	 * @param properties
	 * @param slotMap
	 * @throws CoreException
	 */
	protected void doRun(Map<String, String> properties, Map<String, List<Pageflow>> slotMap) throws CoreException {
		WorkflowRunner runner = new WorkflowRunner();

		ClassLoader oldCl = Thread.currentThread().getContextClassLoader();
		try {
			ClassLoader bundleCl = PageflowGenerationCore.getDefault().getClass().getClassLoader();
			Thread.currentThread().setContextClassLoader(bundleCl);
			boolean success = runner.run("com/odcgroup/pageflow/generation/ocs/workflow.oaw",
					new NullProgressMonitor(), properties, slotMap);
			if (!success) {
	        	// Try to get a detailed message from the generator
				MultiStatus multiStatus = DetailedMessageCaptureExceptionHandler.retreiveDetailedMessageStatus();
				if (multiStatus != null) {
					throw new CoreException(multiStatus);
				}
				// If no details available, create a generic error message
				newCoreException(new RuntimeException("Workflow ended unsuccessfully - please see log file for details"));
			}
		} catch (RuntimeException e) {
			IStatus status = new Status(Status.ERROR, PageflowGenerationCore.PLUGIN_ID,
					Status.OK,
					"Unexpected runtime error in pageflow cartridge", e);
			throw new CoreException(status);
		} finally {
			Thread.currentThread().setContextClassLoader(oldCl);			
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
		throw new CoreException(new Status(IStatus.ERROR, PageflowGenerationCore.PLUGIN_ID,
				0, t.toString(), t));
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
