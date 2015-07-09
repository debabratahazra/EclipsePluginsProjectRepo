package com.odcgroup.visualrules.integration.init;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.visualrules.integration.RulesIntegrationUtils;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.helper.StringHelper;
import com.odcgroup.workbench.core.init.DefaultModelProjectInitializer;
import com.odcgroup.workbench.core.repository.maven.MavenDependencyHelper;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.cartridge.CodeCartridge;
import com.odcgroup.workbench.generation.cartridge.CodeGenCategory;
import com.odcgroup.workbench.generation.properties.PropertyHelper;

import de.visualrules.integration.IJavaIntegration;
import de.visualrules.integration.IntegrationCore;
import de.visualrules.integration.IntegrationException;
import de.visualrules.integration.ProjectNotManagedException;
import de.visualrules.integration.model.IntegrationFactory;
import de.visualrules.integration.model.JavaRuleModelEnvironmentConfiguration;
import de.visualrules.integration.model.JavaRulePackageEnvironmentConfiguration;
import de.visualrules.model.access.IllegalMetaModelVersionException;
import de.visualrules.model.core.resource.ResourceNotManagedException;

public class RulesInitializer extends DefaultModelProjectInitializer {

	private static final String RULE_CORE_MODELS_GROUP_ID = "com.odcgroup.thirdparty.visualrules";
	private static final String RULE_CORE_MODELS_ARTIFACT_ID = "rule-core-models";
	private static final String RULE_CORE_MODELS_VERSION = "7.0.0";
	private static final String RULE_CORE_MODELS_TYPE = "jar";

	private static final Logger logger = LoggerFactory.getLogger(RulesInitializer.class);
	
	public static final String RULES_NATURE = "de.visualrules.core.visualrulesNature";
	public final static String VR_VALIDATION_BUILDER_ID = "de.visualrules.validation.builder";
	public final static String DATASYNC_BUILDER_ID = "com.odcgroup.visualrules.integration.OfsVRBuilder";
	public final static String VR_ARTIFACT_MARKER_ID = "de.visualrules.artifact.eclipse.problem";

	public RulesInitializer() {
		super("rule");
	}
   
	@SuppressWarnings("unchecked")
	public void updateConfiguration(final IProject project, IProgressMonitor monitor) throws CoreException {

		OfsCore.addNature(project, RULES_NATURE);

		if (!isRuleValidationBuilderDisabled(project)) {
			// remove it so that it is added again with the right
			// settings
			OfsCore.removeProjectBuilder(project, VR_VALIDATION_BUILDER_ID);
		}
		Map args = new HashMap();
		args.put("disable", "true");
		OfsCore.addProjectBuilder(project, VR_VALIDATION_BUILDER_ID, args);

		// DS-1419
		boolean active = isVRCartridgeActivated(project);

		// DS-3330: we do not need the dummy file anymore
		IFile dummyFile = project.getFolder("rule/rules").getFile("dummy.rule");
		if(dummyFile.exists()) dummyFile.delete(false, monitor);

		if(!active) {
			OfsCore.removeProjectBuilder(project, DATASYNC_BUILDER_ID);
			project.deleteMarkers(VR_ARTIFACT_MARKER_ID, true, IResource.DEPTH_ONE);
			project.deleteMarkers(OfsCore.MARKER_ID, false, IResource.DEPTH_INFINITE);
			IFile ruleFile = RulesIntegrationUtils.getRulesFile(project);
			if(ruleFile!=null && ruleFile.exists()) ruleFile.delete(false, monitor);
			return;
		}
		// END DS-1419
		
		initializeSourceFolder(project);

		OfsCore.addProjectBuilder(project, DATASYNC_BUILDER_ID);

		Model pom = getPom(project);
		if (pom != null) {
			if (!hasRuleCoreModelsDependency(pom)) {
				try {
					addRuleCoreModelsDependency(project);
				} catch (Exception e) {
					logger.error("Unable to update the pom.xml of the " + project.getName() + " project", e);
					return;
				}

				
			}
		}
		
		ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				// touch the pom.xml as this will trigger the VR dependency management
				IFile pomFile = project.getFile("pom.xml");
				if(pomFile.exists()) {
					pomFile.touch(null);
				}
			}}, monitor);				
		if(project.hasNature(GenerationCore.TECHNICAL_NATURE_ID)) {
			initCodeGenerationSettings(project);
		}
		try {
			IntegrationCore.getDataModelIntegration(project).save(RulesIntegrationPlugin.getVRBasePath(project));
		} catch (ProjectNotManagedException e) {
			logger.warn("Cannot save changes to rule model: {}", e.getMessage());
		} catch (IntegrationException e) {
			// if the rule model is not migrated, we might see this exception, but in this case, we can simply ignore it
		}
	}

	public IStatus checkConfiguration(IProject project) {
		assert project!=null;
		
		MultiStatus status = new MultiStatus(OfsCore.PLUGIN_ID, IStatus.OK, "Errors in configuration", null);

		try {
			if (!project.hasNature(RULES_NATURE)) {
				status.add(new Status(IStatus.ERROR, OfsCore.PLUGIN_ID,
						"Rules nature is not configured for this project."));
			}
		} catch (CoreException e) {
			// ignore if nature cannot be determined
		}

		if (!OfsCore.hasProjectBuilder(project, VR_VALIDATION_BUILDER_ID)) {
			status
					.add(new Status(IStatus.ERROR, OfsCore.PLUGIN_ID,
							"Rules validation builder is not configured for this project."));
		} else if (!isRuleValidationBuilderDisabled(project)) {
			status
					.add(new Status(IStatus.ERROR, OfsCore.PLUGIN_ID,
							"Rules validation builder is not disabled for this project."));
		}

		// check if VR cartridge is activated
		boolean active = isVRCartridgeActivated(project);
		if(!active) {
			if(OfsCore.hasProjectBuilder(project, DATASYNC_BUILDER_ID)) {
				status.add(
						new Status(IStatus.ERROR, RulesIntegrationPlugin.PLUGIN_ID, 
								"Rules data type synchronization builder is active although project does not use the rule code generator."));
			}

			return status;
		}

		if(!OfsCore.hasProjectBuilder(project, DATASYNC_BUILDER_ID)) {
			status.add(
					new Status(IStatus.ERROR, RulesIntegrationPlugin.PLUGIN_ID, 
							"Data type synchronization for rules is not set up correctly."));
		}

		if(!project.getFolder("rule/rules").exists()) {
			status.add(
					new Status(IStatus.ERROR, RulesIntegrationPlugin.PLUGIN_ID, 
							"Rules folder 'rules' does not exist."));
		} else {
			IFile rulesFile = RulesIntegrationUtils.getRulesFile(project);
			if(rulesFile==null || !rulesFile.exists()) {
				status.add(
						new Status(IStatus.ERROR, RulesIntegrationPlugin.PLUGIN_ID, 
								"VisualRules model file does not exist."));
			}

			// DS-3330: we do not need the dummy file anymore
			IFile dummyFile = project.getFolder("rule/rules").getFile("dummy.rule");
			if(dummyFile.exists()) {
				status.add(
						new Status(IStatus.ERROR, RulesIntegrationPlugin.PLUGIN_ID, 
								"VisualRules dummy rule file still exists and need to be removed."));
			}

		}
		
		Model pom = getPom(project);
		if (pom != null) {
			try {
				if (!hasRuleCoreModelsDependency(pom)) {
					status.add(
							new Status(IStatus.ERROR, RulesIntegrationPlugin.PLUGIN_ID, 
									"The rule-core-models dependency is missing."));
				}
			} catch (Exception e) {
				// Corrupted model project, ignore it
			}
		}
		
		return status;
	}

	private boolean hasRuleCoreModelsDependency(Model pom) {
		boolean ruleCoreModelsFound = false;
		for (Dependency dependency: pom.getDependencies()) {
			if (RULE_CORE_MODELS_GROUP_ID.equals(dependency.getGroupId()) &&
					RULE_CORE_MODELS_ARTIFACT_ID.equals(dependency.getArtifactId()) &&
					RULE_CORE_MODELS_TYPE.equals(dependency.getType())) {
				ruleCoreModelsFound = true;
				break;
			}
		}
		return ruleCoreModelsFound;
	}
	
	private void addRuleCoreModelsDependency(IProject project) throws JDOMException, IOException, CoreException {
		IFile pomFile = project.getFile("pom.xml");
		Document pomDocument = MavenDependencyHelper.parsePom(pomFile);
		MavenDependencyHelper.addDependency(pomDocument, RULE_CORE_MODELS_GROUP_ID, RULE_CORE_MODELS_ARTIFACT_ID, RULE_CORE_MODELS_VERSION);
		MavenDependencyHelper.updatePom(pomFile, pomDocument);
	}

	private Model getPom(IProject project) {
		Model model = null;
		
		IFile pom = project.getFile(new Path("pom.xml"));
		if (pom.exists()) {
			InputStream is = null;
			try {
				MavenXpp3Reader reader = new MavenXpp3Reader();
				is = pom.getContents();
				model = reader.read(is);
			} catch (Exception e) {
				// Consider the model project deeply broken, ignore the pom
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
		return model;
	}

	private boolean isVRCartridgeActivated(IProject project) {
		boolean active = false;
		for(CodeCartridge cartridge : PropertyHelper.getSelectedCodeCartridges(project)) {
			if(cartridge.getId().equals("visualrules.generation.m2c")) active = true;
		}
		return active;
	}

	private void initializeSourceFolder(final IProject project) {
		try {
			IFolder ruleFolder = project.getFolder("rule");
			if(!ruleFolder.exists()) OfsCore.createFolder(ruleFolder);
			IFolder rulesFolder = ruleFolder.getFolder("rules");
			if(!rulesFolder.exists()) OfsCore.createFolder(rulesFolder);

			if(RulesIntegrationUtils.getRulesTranslationFile(project)==null) {
				// the first model access will create this file
				RulesIntegrationUtils.getRulesTranslationModel(project);
			}
			
			IFile file = RulesIntegrationUtils.getRulesFile(project);
			if(file==null) {
				String ruleName = "rules_" + (rulesFolder.hashCode() & Integer.MAX_VALUE);
				file = rulesFolder.getFile(ruleName + ".vrmodel");
			}
			if(!file.exists()) {
				final IFile finalFile = file; 
				
				ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
					public void run(IProgressMonitor monitor) throws CoreException {
						try {
							// we need to execute this in an extra workspace runnable in order to make the VR cloud resolver
							// aware of this new rule model
							IntegrationCore.getCoreRuleIntegration(project).createRuleModel(finalFile.getParent(), StringHelper.withoutExtension(finalFile.getName()), null);
						} catch (ProjectNotManagedException e) {
							OfsCore.getDefault().logError("Could not initialize rule folder for project " + project.getName(), e);
						} catch (IntegrationException e) {
							OfsCore.getDefault().logError("Could not initialize rule folder for project " + project.getName(), e);
						}
					}
				}, rulesFolder, IWorkspace.AVOID_UPDATE, null);
				ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
					public void run(IProgressMonitor monitor) throws CoreException {
						try {
							// we need to execute this as well in a runnable in order to avoid resource updates inbetween,
							// which would cause deadlocks
							IntegrationCore.getDataModelIntegration(project).addPackageReuse(
									RulesIntegrationPlugin.getVRBasePath(project), "vrpath:/core");
							IntegrationCore.getDataModelIntegration(project).save(RulesIntegrationPlugin.getVRBasePath(project));
						} catch (ProjectNotManagedException e) {
							OfsCore.getDefault().logError("Could not initialize rule folder for project " + project.getName(), e);
						} catch (IntegrationException e) {
							// ignore, as this case only happens when a project has just been created; and normal projects do not
							// have rules enabled by default and custo projects will overwrite the rule model anyhow.
						}
					}
				}, rulesFolder, IWorkspace.AVOID_UPDATE, null);
			}
			ruleFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			OfsCore.getDefault().logError("Could not initialize rule folder for project " + project.getName(), e);
		}
	}
	
	private void initCodeGenerationSettings(IProject project) {
		// get the output container
        IContainer outputContainer = 
        	GenerationCore.getJavaSourceFolder(project, CodeGenCategory.IMPL_R);

        JavaRuleModelEnvironmentConfiguration iModelConf = 
	    	  IntegrationFactory.eINSTANCE.createJavaRuleModelEnvironmentConfiguration();
	      iModelConf.setGenerateStatisticsCode(false);
	      iModelConf.setInternalPackageName("internal");
	      iModelConf.setJavaVersion("1.6");
	      iModelConf.setPackagePrefix("com.odcgroup");
	      iModelConf.setTargetDirectory(outputContainer.getFullPath().toString());
	      iModelConf.setForceFullyQualifiedMethodNames(true);

	      JavaRulePackageEnvironmentConfiguration iPackageConf =
	    	  IntegrationFactory.eINSTANCE.createJavaRulePackageEnvironmentConfiguration();
	      iPackageConf.setPackageAlias("rules");
	      iPackageConf.setUseReferences(true);

	      IJavaIntegration javaIntegration = IntegrationCore.getJavaIntegration();
	      IFile ruleFile = RulesIntegrationUtils.getRulesFile(project);

	      try {
			javaIntegration.configureCodeGenerator(ruleFile, iModelConf);
			javaIntegration.configureCodeGenerator(ruleFile, iPackageConf);
		} catch (IntegrationException e) {
			if((e.getCause() instanceof ResourceNotManagedException)) {
				// Project might not yet been recognized by VR, so we ignore if the creation of the internal
				// package fails at this point - this can still be done at a later point
			} else if(!(e.getCause() instanceof IllegalMetaModelVersionException)) {
				RulesIntegrationPlugin.getDefault().
					logError("Could not configure code generation settings for rule file " + ruleFile.getName(), e);
			}
		}
	}
	
	private boolean isRuleValidationBuilderDisabled(IProject project) {
		try {
			for (ICommand command : project.getDescription().getBuildSpec()) {
				if (command.getBuilderName().equals(VR_VALIDATION_BUILDER_ID)) {
					String disabled = (String) command.getArguments().get(
							"disable");
					if ("true".equalsIgnoreCase(disabled))
						return true;
				}
			}
		} catch (CoreException e) {
			return false;
		}
		return false;
	}

}
