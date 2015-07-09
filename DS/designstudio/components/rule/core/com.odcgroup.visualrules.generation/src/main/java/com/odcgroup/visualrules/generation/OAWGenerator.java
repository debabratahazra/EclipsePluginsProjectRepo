package com.odcgroup.visualrules.generation;


import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.mwe.core.WorkflowRunner;
import org.eclipse.emf.mwe.core.monitor.NullProgressMonitor;

import com.odcgroup.mdf.ecore.ECoreModelFactory;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.generation.Activator;
import com.odcgroup.mdf.generation.rules.RuleCategory;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.visualrules.generation.rulesbinding.MdfBinding;
import com.odcgroup.visualrules.generation.rulesbinding.Rule;
import com.odcgroup.visualrules.generation.rulesbinding.RulesBindingFactory;
import com.odcgroup.visualrules.integration.api.DomainNotFoundException;
import com.odcgroup.visualrules.integration.api.EntityNotFoundException;
import com.odcgroup.visualrules.integration.api.RuleModelFacade;
import com.odcgroup.visualrules.integration.api.RuleModelFacadeFactory;
import com.odcgroup.visualrules.integration.api.RuleModelNotFoundException;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.generation.cartridge.AbstractCodeGenerator;
import com.odcgroup.workbench.generation.cartridge.DetailedMessageCaptureExceptionHandler;

public class OAWGenerator extends AbstractCodeGenerator {

	private final String workflow;

	public OAWGenerator(String workflow) {
		this.workflow = workflow;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void doRun(IProject project, IContainer outputContainer, Collection<IOfsModelResource> modelResources,
			List<IStatus> nonOkStatuses) {
		Collection<IOfsModelResource> domainResources = 
			OfsResourceHelper.filter(modelResources, new String[]{"domain", "mml"});
		
		HashSet<MdfDomain> domains = new HashSet<MdfDomain>();
        for (IOfsModelResource domainResource : domainResources) {
			try {
	        	EList<EObject> model = domainResource.getEMFModel();
	        	if(model.size()==1 && (model.get(0) instanceof MdfDomain))
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

    	if (!domains.isEmpty()) {
    		// Get MDF lookup object
    		IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
    		DomainRepository repository = DomainRepository.getInstance(ofsProject);

    		// Map of bindings MDFEntity-Rules, indexed by domainName:entityName
    		Map<String, MdfBinding> bindingsMap = new HashMap<String, MdfBinding>();

    		// Get RuleModel API
    		RuleModelFacade ruleModelFacade;
    		try {
    			ruleModelFacade = RuleModelFacadeFactory.getInstance(project);

    			// Fill bindings for validation rules
    			fillBindings(project, ruleModelFacade, repository, bindingsMap);

    			if (!bindingsMap.isEmpty()) {
    				Map<String, String> parameters = new HashMap<String, String>();
    				File srcOutlet = outputContainer.getLocation().toFile();
    				File resOutlet = outputContainer.getLocation().toFile();
    				parameters.put("ruleModelName", ruleModelFacade
    						.getRuleModelName());
    		        parameters.put("projectName", project.getName());
    				parameters.put("outlet", srcOutlet.getAbsolutePath());
    				parameters.put("outlet.resources", resOutlet.getAbsolutePath());
    				Map<String, Collection<? extends Object>> slotMap = new HashMap<String, Collection<? extends Object>>();
    				slotMap.put("modelSlot", bindingsMap.values());
    				WorkflowRunner runner = new WorkflowRunner();
    				boolean success = runner.run(workflow,
    						new NullProgressMonitor(), parameters, slotMap);
    				if (!success) {
    		        	// Try to get a detailed message from the generator
    					MultiStatus multiStatus = DetailedMessageCaptureExceptionHandler.retreiveDetailedMessageStatus();
    					if (multiStatus != null) {
    						throw new CoreException(multiStatus);
    					}
    					// If no details available, create a generic error message
    					newCoreException(new RuntimeException(
    							"Error in OAW cartridge, please check the log file!"));
    				}
    			}
    		} catch (RuleModelNotFoundException e) {
    			String errorMsg = "Rule model not found.";
    			newCoreException(e, nonOkStatuses, errorMsg);
    		} catch (CoreException e) {
    			newCoreException(e, nonOkStatuses, "");
			}
    	}
	}

	protected void fillBindings(IProject project,
			RuleModelFacade ruleModelFacade, DomainRepository domainRepository, Map<String, MdfBinding> bindingsMap)
			throws CoreException {
		RulesBindingFactory rulesBindingFactory = RulesBindingFactory.eINSTANCE;
		try {
			// Get domains from the RuleModel
			String[] domainNames = ruleModelFacade.getDomains(project);
			for (int i = 0; i < domainNames.length; i++) {
				String domainName = domainNames[i];

				// Get the MdF domain corresponding to the domain name
				MdfDomain mdfDomain = domainRepository.getDomain(domainName);
				if (mdfDomain != null) {
					// Get entities for the MDF domain from the RuleModel
					String[] entityNames = ruleModelFacade
							.getEntitiesForDomain(project, domainName);
					for (int j = 0; j < entityNames.length; j++) {
						String entityName = entityNames[j];

						// Get the MdF entity corresponding to subPackage name
						MdfEntity entity = null;
						// Try first in bindings map if we already have it
						MdfBinding mdfBinding = bindingsMap.get(domainName
								+ ":" + entityName);
						if (mdfBinding == null) {
							// Not already in binding map so lookup it in MDF
							// domain
							entity = mdfDomain.getEntity(entityName);
						} else {
							entity = mdfBinding.getEntity();
						}
						if (entity != null) {
							// Get all rules in entity package
							for (RuleCategory ruleCategory : RuleCategory
									.values()) {
								String[] rules = ruleModelFacade
										.getRulesForEntity(project,
												entityName, domainName, ruleCategory);
								// Iterate on rules and add them to bindings
								if (rules.length > 0) {
									if (mdfBinding == null) {
										// Create new binding with entity
										mdfBinding = rulesBindingFactory
												.createMdfBinding();
										mdfBinding.setEntity(entity);
										bindingsMap.put(domainName + ":"
												+ entityName, mdfBinding);
									}
									// Add all rule names to binding with
									// appropriate type according to rule
									// category
									for (int k = 0; k < rules.length; k++) {
										String ruleName = rules[k];
										mdfBinding.getRules().add(
												createRule(ruleCategory,
														ruleName));
									}
								} // else no rules found in entity package
							}
						} // else entity package does not correspond to MDF
						// entity
					}
				} // else no MDF domain found
			}
		} catch (DomainNotFoundException e) {
			throw newCoreException(e);
		} catch (EntityNotFoundException e) {
			throw newCoreException(e);
		} catch (UnsupportedRuleCategoryException e) {
			throw newCoreException(e);
		}
	}

	protected boolean isModelFile(IResource resource) {
		return ECoreModelFactory.INSTANCE.isModelFile(resource.getName());
	}

	protected CoreException newCoreException(Throwable t) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
				0, t.toString(), t));
	}

	protected Rule createRule(RuleCategory ruleCategory, String ruleName)
			throws UnsupportedRuleCategoryException {
		Rule rule = null;
		RulesBindingFactory factory = RulesBindingFactory.eINSTANCE;
		switch (ruleCategory) {
		case VALIDATION:
			rule = factory.createValidationRule();
			break;
		case COMPLETION:
			rule = factory.createCompletionRule();
			break;
		default:
			throw new UnsupportedRuleCategoryException();
		}
		rule.setName(ruleName);
		return rule;
	}
}