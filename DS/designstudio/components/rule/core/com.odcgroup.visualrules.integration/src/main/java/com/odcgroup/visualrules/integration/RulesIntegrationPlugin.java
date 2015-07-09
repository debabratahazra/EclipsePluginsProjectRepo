package com.odcgroup.visualrules.integration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.generation.rules.RuleCategory;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.visualrules.integration.api.DomainNotFoundException;
import com.odcgroup.visualrules.integration.api.EntityNotFoundException;
import com.odcgroup.visualrules.integration.api.RuleModelFacade;
import com.odcgroup.visualrules.integration.api.RuleModelFacadeFactory;
import com.odcgroup.visualrules.integration.api.RuleModelNotFoundException;
import com.odcgroup.visualrules.integration.generation.VisualRulesCodeGenerator;
import com.odcgroup.visualrules.integration.template.Template;
import com.odcgroup.workbench.core.AbstractActivator;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.helper.StringHelper;
import com.odcgroup.workbench.core.logging.LoggingConstants;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.generation.properties.PropertyHelper;
import com.odcgroup.workbench.migration.MigrationCore;

import de.visualrules.integration.ICoreRuleIntegration;
import de.visualrules.integration.IDataModelIntegration;
import de.visualrules.integration.IntegrationCore;
import de.visualrules.integration.IntegrationException;
import de.visualrules.integration.model.Parameter;

/**
 * The activator class controls the plug-in life cycle
 */
public class RulesIntegrationPlugin extends AbstractActivator {

	public static final String RULE_FOLDER = "rule/rules";

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.visualrules.integration";

	public static final String TEMPLATE_EXTENSION_ID = "com.odcgroup.visualrules.integration.template";
	
	public static final String DOMAIN_PKG_NAME = "internal";
	
	public static final String RULE_MARKER_ID = PLUGIN_ID + ".ruleProblem";
		
	// marker used for SLF4J logging to identify the plugin
    public static final Marker LOG_MARKER = createBundleMarker();

	/**
	 * @return
	 */
	public static RulesIntegrationPlugin getDefault() {
		return (RulesIntegrationPlugin) getDefault(RulesIntegrationPlugin.class);
	}

    private static final Marker createBundleMarker() {
    	Marker bundleMarker = MarkerFactory.getMarker(PLUGIN_ID);
    	bundleMarker.add(MarkerFactory.getMarker(LoggingConstants.IS_BUNDLE_MARKER));
    	return bundleMarker;
    }

	static public boolean isRulesEnabled(IProject project) { 
		return PropertyHelper.isCodeCartridgeEnabled(project, VisualRulesCodeGenerator.CARTRIDGE_ID);
	}

	static public void createRule(IProject project, String qualifiedName, String desc) throws CoreException {
		try {
			ICoreRuleIntegration vr = IntegrationCore.getCoreRuleIntegration(project);
			vr.createRule(getVRBasePath(project) + "/" + qualifiedName,
					desc,
					null,
					null,
					false);
		} catch (IntegrationException e) {
			IStatus status = new Status(IStatus.ERROR, RulesIntegrationPlugin.PLUGIN_ID,  
					"Could not create rule '" + qualifiedName + "'", e);
			throw new CoreException(status);
		}		
	}

	static public void createRuleFromTemplate(IProject project, String qualifiedName, String desc, String template, Parameter[] params) 
		throws CoreException {
		try {
			ICoreRuleIntegration vr = IntegrationCore.getCoreRuleIntegration(project);
			vr.createRule(getVRBasePath(project) + "/" + qualifiedName,
					desc,
					params,
					"vrpath:/core/" + template, 
					false);
		} catch (IntegrationException e) {
			IStatus status = new Status(IStatus.ERROR, RulesIntegrationPlugin.PLUGIN_ID,  
					"Could not create rule '" + qualifiedName + "'", e);
			throw new CoreException(status);
		}		
	}
	
	static public void deleteRule(IProject project, String qualifiedName) throws CoreException {
		try {
			ICoreRuleIntegration vr = IntegrationCore.getCoreRuleIntegration(project);
			vr.deleteRule(getVRBasePath(project) + "/" + qualifiedName);
		} catch (IntegrationException e) {
			IStatus status = new Status(IStatus.ERROR, RulesIntegrationPlugin.PLUGIN_ID,  
					"Could not delete rule '" + qualifiedName + "'", e);
			throw new CoreException(status);
		}				
	}

	public static Template[] getRegisteredTemplates() {
		ArrayList<Template> templates = new ArrayList<Template>();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(TEMPLATE_EXTENSION_ID);
		if (point == null) return new Template[0];
		IConfigurationElement[] configElements = point.getConfigurationElements();
    	
		// iterate over all defined templates
		for(IConfigurationElement configElement : configElements) {
			Template template = new Template();
            try {
            	// if a configuration class is specified, use this one instead of the default
            	template = (Template) configElement.createExecutableExtension("configClass");
            } catch (CoreException e) {
            	// no error if there is no class defined
            }
            template.setName(configElement.getAttribute("name"));
            template.setQualifiedTemplateName(configElement.getAttribute("qualifiedTemplateName"));
            template.setType(configElement.getAttribute("type"));
			templates.add(template);
		}
		return templates.toArray(new Template[0]);
	}

	public static Template getTemplate(RuleCategory category) {
		for(Template template : getRegisteredTemplates()) {
			if(template.getType().equals(category.toString())) {
				return template;
			}
		}
		return null;
	}
	
	public static String getVRBasePath(IProject project) {
		IFile rulesFile = RulesIntegrationUtils.getRulesFile(project);
		if(rulesFile==null) return null;
		String ruleName = StringHelper.withoutExtension(rulesFile.getName());
		return "vrpath:/" + ruleName;
	}
	
	public static boolean packageExists(IProject project, String vrpath) {
		try {
			IDataModelIntegration integration = IntegrationCore.getDataModelIntegration(project);
			integration.getPackage(vrpath);
			return true;
		} catch(IntegrationException e) {
			return false;
		}
	}
	
	public static boolean ruleExists(MdfEntity entity, RuleCategory category) {
		Resource res = ((EObject) entity).eResource();
		IOfsProject ofsProject = OfsResourceHelper.getOfsProject(res);
		if(ofsProject==null) return false;
		IProject project = ofsProject.getProject();
		if(!RulesIntegrationPlugin.isRulesEnabled(project)) return false;
		
		try {
			RuleModelFacade ruleApi = RuleModelFacadeFactory.getInstance(project);
			try {
				String[] rules = ruleApi.getRulesForEntity(project, entity.getName(), entity.getParentDomain().getName(), category);
				if(rules.length>0) return true;
			} catch (DomainNotFoundException e) {
				return false;
			} catch (EntityNotFoundException e) {
				return false;
			}
		} catch (RuleModelNotFoundException e) {
			return false;
		}
		return false;
	}
	
	public static String[] getOutdatedRules(IProject project) {
		Set<String> outdatedRules = new HashSet<String>();
		try {
			RuleModelFacade ruleApi = RuleModelFacadeFactory.getInstance(project);
    		IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
    		DomainRepository repository = DomainRepository.getInstance(ofsProject);
			String[] ruleDomains = ruleApi.getDomains(project);
			for(String domain : ruleDomains) {
				MdfDomain mdfDomain = repository.getDomain(domain);
				if(mdfDomain==null) {
					CollectionUtils.addAll(outdatedRules, ruleApi.getRulePathsForDomain(project, domain));
				} else {
					String[] entities = ruleApi.getEntitiesForDomain(project, domain);
					if(entities.length>0) {
						for(String entity : entities) {
							MdfEntity mdfEntity = mdfDomain.getEntity(entity);
							if(mdfEntity==null) {
								CollectionUtils.addAll(outdatedRules, ruleApi.getRulePathsForEntity(project, entity, domain));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			RulesIntegrationPlugin.getDefault().logWarning(
					"Error while checking outdated rules for project " + project, e);
		}
		return outdatedRules.toArray(new String[outdatedRules.size()]);
	}

	static public boolean projectHasConfigurationErrors(IProject project) {
		try {
			IMarker[] markers = project.findMarkers(OfsCore.MARKER_ID, false, IResource.DEPTH_ZERO);
			if(hasError(markers)) return true;
			markers = project.findMarkers(MigrationCore.MARKER_ID, true, IResource.DEPTH_ZERO);
			if(hasError(markers)) return true;
			IFile pom = project.getFile("pom.xml");
			if(pom.exists()) {
				markers = pom.findMarkers("org.eclipse.core.resources.problemmarker", true, IResource.DEPTH_ZERO);
				if(hasError(markers)) return true;
			}
		} catch (CoreException e) {
			getDefault().logError("Error while reading markers", e);
		}
		return false;		
	}
	
	static private boolean hasError(IMarker[] markers) {
		for (IMarker marker: markers) {
			if (marker.getAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO) == IMarker.SEVERITY_ERROR) {
				return true;
			}
		}
		return false;
	}
}
