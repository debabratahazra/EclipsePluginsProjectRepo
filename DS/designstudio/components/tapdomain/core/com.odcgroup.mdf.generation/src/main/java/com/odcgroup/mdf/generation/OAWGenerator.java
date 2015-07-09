package com.odcgroup.mdf.generation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.mwe.core.WorkflowEngine;
import org.eclipse.emf.mwe.core.monitor.NullProgressMonitor;
import org.eclipse.emf.mwe.core.resources.CachingResourceLoaderImpl;
import org.eclipse.emf.mwe.core.resources.OsgiResourceLoader;
import org.eclipse.emf.mwe.core.resources.ResourceLoader;
import org.eclipse.emf.mwe.core.resources.ResourceLoaderFactory;

import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.generation.cartridge.AbstractCodeGenerator;
import com.odcgroup.workbench.generation.cartridge.DetailedMessageCaptureExceptionHandler;

public class OAWGenerator extends AbstractCodeGenerator {

	private final String workflow;

	public OAWGenerator(String workflow) {
		this.workflow = workflow;
	}

	@Override
	public void doRun(IProject project, IContainer outputContainer, Collection<IOfsModelResource> modelResources,
			List<IStatus> nonOkStatuses) {
		Collection<IOfsModelResource> domainResources = OfsResourceHelper
				.filter(modelResources, new String[] { "domain" });

		HashSet<MdfDomain> domains = new HashSet<MdfDomain>();
		for (IOfsModelResource domainResource : domainResources) {
			try {
				EList<EObject> model = domainResource.getEMFModel();
				if (model.size() > 0 && (model.get(0) instanceof MdfDomain))
					domains.add((MdfDomain) model.get(0));
			} catch (IOException e) {
				String errorMsg = "Error during code generation in " + getClass().getName() + " for domain '"
						+ domainResource.getName() + "'";
				newCoreException(e, nonOkStatuses, errorMsg);
			} catch (InvalidMetamodelVersionException e) {
				String errorMsg = "Error during code generation in " + getClass().getName() + " for domain '"
						+ domainResource.getName() + "'";
				newCoreException(e, nonOkStatuses, errorMsg);
			}
		}
		if (!domains.isEmpty())
			try {
				doInternalRun(project.getName(), outputContainer.getLocation()
						.toOSString(), domains);
			} catch (CoreException e) {
				String errorMsg = "Error during code generation in " + getClass().getName();
				newCoreException(e, nonOkStatuses, errorMsg);
			}
	}

	/**
	 * @param string
	 * @param outputFolder
	 * @param contents
	 */
	public void doInternalRun(String projectName, String outputFolder,
			Set<MdfDomain> domains) throws CoreException {

		// DS-1985: solution for Eclipse bug
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=278753

		ResourceLoader defaultResourceLoader = ResourceLoaderFactory.getCurrentThreadResourceLoader();

		CachingResourceLoaderImpl crl = new CachingResourceLoaderImpl(
				new OsgiResourceLoader(getPluginId(),
						this.getClass().getClassLoader()));
		ResourceLoaderFactory.setCurrentThreadResourceLoader(crl);

		WorkflowEngine engine = new WorkflowEngine();

		Map<String, List> slotMap = new HashMap<String, List>();
		List<MdfDomain> slot = new ArrayList<MdfDomain>();
		Map<String, String> parameters = new HashMap<String, String>();

		slot.addAll(domains);
		slotMap.put("modelSlot", slot);
		parameters.put("projectName", projectName);

		parameters.put("outlet", outputFolder);
		parameters.put("outlet.resources", outputFolder);

		boolean success = engine.run(workflow, new NullProgressMonitor(),
				parameters, slotMap);

		// DS-1985: set the original resource loader again
		ResourceLoaderFactory.setCurrentThreadResourceLoader(defaultResourceLoader);

		if (!success) {
			// Try to get a detailed message from the generator
			MultiStatus multiStatus = DetailedMessageCaptureExceptionHandler
					.retreiveDetailedMessageStatus();
			if (multiStatus != null) {
				throw new CoreException(multiStatus);
			}
			// If no details available, create a generic error message
			newCoreException(
					"Error in OAW cartridge, please check the log file!", null);
		}
	}

	/**
	 * This method must be overridden, if the extending class is not in the same plugin.
	 * We need to pass the plugin id to oAW for resource loading; so this method needs
	 * to return a plugin id of a plugin, which has all required oAW files in its dependencies.
	 * 
	 * @return plugin id for resource loader
	 */
	protected String getPluginId() {
		return Activator.PLUGIN_ID;
	}
}
