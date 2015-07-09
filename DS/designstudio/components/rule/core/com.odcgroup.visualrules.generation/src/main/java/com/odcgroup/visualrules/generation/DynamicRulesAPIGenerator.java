package com.odcgroup.visualrules.generation;

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
import org.eclipse.emf.mwe.core.WorkflowRunner;
import org.eclipse.emf.mwe.core.monitor.NullProgressMonitor;
import org.eclipse.emf.mwe.core.resources.CachingResourceLoaderImpl;
import org.eclipse.emf.mwe.core.resources.OsgiResourceLoader;
import org.eclipse.emf.mwe.core.resources.ResourceLoader;
import org.eclipse.emf.mwe.core.resources.ResourceLoaderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.visualrules.integration.api.RuleModelFacade;
import com.odcgroup.visualrules.integration.api.RuleModelFacadeFactory;
import com.odcgroup.visualrules.integration.api.RuleModelNotFoundException;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.generation.cartridge.AbstractCodeGenerator;
import com.odcgroup.workbench.generation.cartridge.DetailedMessageCaptureExceptionHandler;


public class DynamicRulesAPIGenerator extends AbstractCodeGenerator {

	private static final Logger logger = LoggerFactory.getLogger(DynamicRulesAPIGenerator.class);
	
    private static final String WORKFLOW_NAME = "com/odcgroup/visualrules/generation/rules/dynamic_rules.oaw";

    @Override
	public void doRun(IProject project, IContainer outputContainer, Collection<IOfsModelResource> modelResources,
			List<IStatus> nonOkStatuses) {
		Collection<IOfsModelResource> domainResources = OfsResourceHelper
				.filter(modelResources, new String[] { "domain", "mml" });

		HashSet<MdfDomain> domains = new HashSet<MdfDomain>();
		for (IOfsModelResource domainResource : domainResources) {
			try {
				EList<EObject> model = domainResource.getEMFModel();
				if (model.size() == 1 && (model.get(0) instanceof MdfDomain))
					domains.add((MdfDomain) model.get(0));
			} catch (IOException e) {
				String errorMsg = "Error while code generating in " + getClass().getName() + " for domain '"
						+ domainResource + "'";
				newCoreException(e, nonOkStatuses, errorMsg);
			} catch (InvalidMetamodelVersionException e) {
				String errorMsg = "Error while code generating in " + getClass().getName() + " for domain '"
						+ domainResource + "'";
				newCoreException(e, nonOkStatuses, errorMsg);
			}
		}
		if (!domains.isEmpty())
			try {
				doInternalRun(project, outputContainer.getLocation()
						.toOSString(), domains);
			} catch (CoreException e) {
				String errorMsg = "Error while code generating in " + getClass().getName();
				newCoreException(e, nonOkStatuses, errorMsg);
			}
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public void doInternalRun(IProject project, String outputFolder,
			Set<MdfDomain> domains) throws CoreException {

		// DS-1985: solution for Eclipse bug
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=278753

		ResourceLoader defaultResourceLoader = ResourceLoaderFactory.getCurrentThreadResourceLoader();

		CachingResourceLoaderImpl crl = new CachingResourceLoaderImpl(
				new OsgiResourceLoader(VisualRuleGenCore.PLUGIN_ID,
						this.getClass().getClassLoader()));
		ResourceLoaderFactory.setCurrentThreadResourceLoader(crl);

		RuleModelFacade ruleModelFacade;
		try {
			ruleModelFacade = RuleModelFacadeFactory.getInstance(project);
		} catch (RuleModelNotFoundException e) {
			logger.warn("Project '{}' has the 'VisualRules Dynamic API' code generation cartridge enabled, " +
					"although no rule model can be found.", project.getName());
			// We do not need to generate anything for a project without a rule model
			return;
		}

		WorkflowRunner runner = new WorkflowRunner();

		Map<String, List> slotMap = new HashMap<String, List>();
		List<MdfDomain> slot = new ArrayList<MdfDomain>();
		Map<String, String> parameters = new HashMap<String, String>();

		slot.addAll(domains);
		slotMap.put("modelSlot", slot);
		parameters.put("projectName", project.getName());
		parameters.put("ruleModelName", ruleModelFacade.getRuleModelName());

		parameters.put("outlet", outputFolder);
		parameters.put("outlet.resources", outputFolder);

		boolean success = runner.run(WORKFLOW_NAME, new NullProgressMonitor(),
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

}
