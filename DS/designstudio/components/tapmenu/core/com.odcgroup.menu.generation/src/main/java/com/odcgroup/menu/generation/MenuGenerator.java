package com.odcgroup.menu.generation;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.mwe.core.WorkflowEngine;
import org.eclipse.emf.mwe.core.monitor.NullProgressMonitor;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.menu.model.MenuRoot;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.generation.cartridge.CodeGenerator;
import com.odcgroup.workbench.generation.cartridge.DetailedMessageCaptureExceptionHandler;
import com.odcgroup.workbench.generation.cartridge.ng.CodeGenerator2;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.odcgroup.workbench.generation.cartridge.ng.SimplerEclipseResourceFileSystemAccess2;

/**
 * @author pkk
 */
public class MenuGenerator implements CodeGenerator2, CodeGenerator {
	
	private static Logger logger = LoggerFactory.getLogger(MenuGenerator.class);

	private static final String MENU_EXTN = "menu";
	private static final String OAMS_PROFILES = "/oams-profiles";
	private static final String MENU_OUTPUT_FILE = "Menu.xml";

	@Override
	public void run(IProgressMonitor monitor, IProject project, Collection<IOfsModelResource> modelResources,
			IFolder outputFolder, List<IStatus> nonOkStatuses) {
		throw new NotImplementedException();
	}

	/**
	 * Instantiate and execute the OAW workflow
	 * 
	 * @param properties
	 * @param slotMap
	 * @throws CoreException
	 */
	protected void doRun(Map<String, String> properties,
			Map<String, MenuRoot> slotMap) throws CoreException {
		WorkflowEngine engine = new WorkflowEngine();

		ClassLoader oldCl = Thread.currentThread().getContextClassLoader();
		try {
			ClassLoader bundleCl = MenuGenerationCore.getDefault().getClass()
					.getClassLoader();
			Thread.currentThread().setContextClassLoader(bundleCl);
			boolean success = engine.run(
					"com/odcgroup/menu/generation/tap/workflow.mwe",
					new NullProgressMonitor(), properties, slotMap);
			if (!success) {
				// Try to get a detailed message from the generator
				MultiStatus multiStatus = DetailedMessageCaptureExceptionHandler
						.retreiveDetailedMessageStatus();
				if (multiStatus != null) {
					throw new CoreException(multiStatus);
				}
				// If no details available, create a generic error message
				newCoreException(new RuntimeException(
						"Workflow ended unsuccessfully - please see log file for details"));
			}
		} catch (RuntimeException e) {
			IStatus status = new Status(Status.ERROR,
					MenuGenerationCore.PLUGIN_ID, Status.OK,
					"Unexpected runtime error in menu cartridge", e);
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
		throw new CoreException(new Status(IStatus.ERROR, MenuGenerationCore.PLUGIN_ID,
				0, t.toString(), t));
	}
	
	protected void newCoreException(Exception e, List<IStatus> nonOkStatuses, String errorMsg) {
		logger.error(errorMsg, e);
		final IStatus errorStatus = new Status(IStatus.ERROR, MenuGenerationCore.PLUGIN_ID, 0,	errorMsg + ": " + e.getMessage(), e);
		nonOkStatuses.add(errorStatus);
	}

	/**
	 * @param resource
	 * @return
	 */
	protected String getOutputFilePath(URI uri) {
		URI uri2 = uri.trimSegments(1);
		List<String> segments = uri2.segmentsList();
		String pack = OfsResourceHelper.getPathFromPlatformURI(uri2);
		String firstsegment =segments.get(3);
		//append the activity for the profiled menus.
		if (firstsegment != null) {
			pack = pack.replaceFirst(firstsegment, firstsegment+"/activity");
	    }
		return pack;
	}
	
	/**
	 * @param resourceURL
	 * @return
	 */
	private static String getPackage(String resourceURL) {
		String RESOURCE_URL_PREFIX = "platform:/resource/";
		int index = resourceURL.lastIndexOf("/");
		return resourceURL.substring(RESOURCE_URL_PREFIX.length(), index);
	}
	
	private IFolder getOutputFolder(IFileSystemAccess fsa) {
		if (fsa instanceof SimplerEclipseResourceFileSystemAccess2) {
			SimplerEclipseResourceFileSystemAccess2 efsa = (SimplerEclipseResourceFileSystemAccess2) fsa;
			IProject project = efsa.getProject();
			OutputConfiguration outputConfig = efsa.getOutputConfigurations().get(IFileSystemAccess.DEFAULT_OUTPUT);
			return project.getFolder(new Path(outputConfig.getOutputDirectory()));
		}
		return null;
	}

	@Override
	public void doGenerate(URI input, ModelLoader loader, IFileSystemAccess fsa) throws Exception {
		
		if (!input.fileExtension().equals(MENU_EXTN)) {
			// ignore non menu file
			return;
		}
		
		Resource resource = loader.getResource(input);
		EObject eObj = resource.getContents().get(0);
		
		URI uri = resource.getURI();
		String projectName = uri.segment(1);
		
		IFolder outputFolder = getOutputFolder(fsa);
		String baseOutputFilePath = outputFolder.getLocation().toOSString();		
		String outputFilePath = baseOutputFilePath + OAMS_PROFILES + getOutputFilePath(uri);		

		MenuRoot root = (MenuRoot)eObj;
		String packagename = getPackage(uri.toString());

		
		Map<String, MenuRoot> slotMap = new HashMap<String, MenuRoot>();
		Map<String, String> properties = new HashMap<String, String>();
		slotMap.put("menuModelSlot", root);
		properties.put("projectName", projectName);
		properties.put("systemUser", System.getProperty("user.name"));
		properties.put("menuModelSlot", "menuModelSlot");
		properties.put("packageName", packagename);
		properties.put("menuFileName", MENU_OUTPUT_FILE);
		properties.put("outlet", outputFilePath);
		doRun(properties, slotMap);
		
	}

}
