package com.odcgroup.visualrules.integration;


import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationFactory;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.helper.StringHelper;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelResourceSet;

abstract public class RulesIntegrationUtils {

	private static final Logger logger = LoggerFactory.getLogger(RulesIntegrationUtils.class);

	/**
	 * This method recursively removes read-only flags on files and folders
	 * 
	 * @param resource a file or folder to remove the read-only flag (incl. all children)
	 * 
	 * @throws CoreException if a resource cannot be modified
	 */
	static public void removeReadOnlyFlags(IResource resource) throws CoreException {
		if(resource.getResourceAttributes().isReadOnly()) {
			ResourceAttributes attributes = resource.getResourceAttributes();
			attributes.setReadOnly(false);
			resource.setResourceAttributes(attributes);
		}
		if(resource instanceof IFolder)
		for(IResource res : ((IFolder)resource).members()) {
			removeReadOnlyFlags(res);
		}
	}

	static public IFile getRulesFile(IProject project) { 
		try {
			IFolder ruleFolder = project.getFolder(RulesIntegrationPlugin.RULE_FOLDER);
			for(IResource resource : ruleFolder.members()) {
				if(resource.getName().toLowerCase().startsWith("rules") && 
				   resource.getName().toLowerCase().endsWith(".vrmodel")) {
					return (IFile) resource;
				}
			}
		} catch (CoreException e) {
			// Ignore if the file is not found and return null
			return null;
		}
		return null;
	}

	static public IFile getRulesTranslationFile(IProject project) { 
		try {
			IFolder ruleFolder = project.getFolder(RulesIntegrationPlugin.RULE_FOLDER);
			for(IResource resource : ruleFolder.members()) {
				if(resource.getName().toLowerCase().startsWith("rules") && 
				   resource.getName().toLowerCase().endsWith(".rule")) {
					return (IFile) resource;
				}
			}
		} catch (CoreException e) {
			// Ignore if the file is not found and return null
			return null;
		}
		return null;
	}

	static public URI getRulesTranslationModelURI(IProject project) { 
		try {
			IFolder ruleFolder = project.getFolder(RulesIntegrationPlugin.RULE_FOLDER);
			for(IResource resource : ruleFolder.members()) {
				if(resource.getName().toLowerCase().startsWith("rules") && 
				   resource.getName().toLowerCase().endsWith(".vrmodel")) {
				   return URI.createURI("resource:///rules/" + StringHelper.withoutExtension(resource.getName()) + ".rule");
				}
			}
		} catch (CoreException e) {
			// Ignore if the file is not found and return null
			return null;
		}
		return null;
	}
	
	static public RuleTranslationRepo getRulesTranslationModel(IProject project) { 
		URI uri = RulesIntegrationUtils.getRulesTranslationModelURI(project);
		if(uri==null) 
			return null;
		try {
			IOfsModelResource res = OfsCore.getOfsProject(project).getOfsModelResource(uri);
			return (RuleTranslationRepo) res.getEMFModel().get(0);
		} catch (ModelNotFoundException e) {
			return createRuleTranslationRepo(project, uri);
		} catch (IOException e) {
			logger.error("Error loading rule translation model '{}'", uri.path(), e);
		} catch (InvalidMetamodelVersionException e) {
			logger.error("Rule translation model '{}' has an invalid metamodel version", uri.path(), e);
		}
		return null;
	}

	private static RuleTranslationRepo createRuleTranslationRepo(IProject project, URI uri) {
		IFolder ruleFolder = project.getFolder("rule");
		IFile translationFile = ruleFolder.getFile(uri.path());
		ModelResourceSet rs = OfsCore.getOfsProject(project).getModelResourceSet();
		rs.createOfsModelResource(uri, translationFile, IOfsProject.SCOPE_PROJECT);
		try {
			Resource res = rs.getResource(uri, false); // NOT createResource(uri)
			RuleTranslationRepo repo = RuletranslationFactory.eINSTANCE.createRuleTranslationRepo();
			res.getContents().add(repo);
			res.save(null);
			return repo;
		} catch (IOException e) {
			logger.error("Error loading rule translation model '{}'", uri.path(), e);
		}
		return null;
	}
}
