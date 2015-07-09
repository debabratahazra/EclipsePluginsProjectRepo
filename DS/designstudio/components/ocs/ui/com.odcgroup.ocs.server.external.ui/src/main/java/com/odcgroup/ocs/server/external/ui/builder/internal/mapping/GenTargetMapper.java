package com.odcgroup.ocs.server.external.ui.builder.internal.mapping;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.ocs.server.ServerCore;
import com.odcgroup.ocs.server.external.builder.internal.delta.SourceFolderEnum;
import com.odcgroup.ocs.server.external.builder.internal.mapping.ConfigMappingInfo;
import com.odcgroup.ocs.server.external.builder.internal.mapping.CustomizableConfigMappingInfo;
import com.odcgroup.ocs.server.external.builder.internal.mapping.MappingInfo;
import com.odcgroup.ocs.server.external.builder.internal.mapping.PrepareDeploymentHelper;
import com.odcgroup.ocs.server.external.builder.internal.mapping.SimpleMappingInfo;
import com.odcgroup.ocs.server.external.builder.internal.mapping.TranslationOnlyMappingInfo;
import com.odcgroup.ocs.server.external.builder.internal.mapping.WuiBlockMappingInfo;
import com.odcgroup.ocs.server.external.model.IExternalServer;
import com.odcgroup.ocs.server.external.ui.OCSServerUIExternalCore;
import com.odcgroup.ocs.server.external.ui.builder.DeployConsole;

/**
 * Find the corresponding target file in the server for DS generated artifacts.
 * @author yan
 */
public class GenTargetMapper extends AbstractTargetMapper {

	/**
	 * 
	 */
	private static final String TARGET_FOLDER = "target/classes";

	private Map<SourceFolderEnum, MappingInfo> targetMapping = new HashMap<SourceFolderEnum, MappingInfo>();

	// Project name
	private IProject project;

	/**
	 * Initialize the mappings used to find the target file/folder in the
	 * server.
	 */
	public GenTargetMapper(IExternalServer classicalServer, IProject project) {
		super(classicalServer);
		this.project = project;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.ocs.server.builder.internal.mapping.TargetMapper#configure(java.util.Map)
	 */
	public void configure(Map<String, String> builderConfig) {
		super.configure(builderConfig);
		targetMapping.put(SourceFolderEnum.API_DOMAIN,           buildSimpleMappingInfo(SourceFolderEnum.API_DOMAIN, builderConfig));
		targetMapping.put(SourceFolderEnum.IMPL_DOMAIN,          buildSimpleMappingInfo(SourceFolderEnum.IMPL_DOMAIN, builderConfig));
		targetMapping.put(SourceFolderEnum.IMPL_DOMAIN_SPECIFIC, buildSimpleMappingInfo(SourceFolderEnum.IMPL_DOMAIN_SPECIFIC, builderConfig));
		targetMapping.put(SourceFolderEnum.API_RULES,            buildSimpleMappingInfo(SourceFolderEnum.API_RULES, builderConfig));
		targetMapping.put(SourceFolderEnum.IMPL_RULES,           buildSimpleMappingInfo(SourceFolderEnum.IMPL_RULES, builderConfig));
		targetMapping.put(SourceFolderEnum.EJB_DOMAIN,           buildSimpleMappingInfo(SourceFolderEnum.EJB_DOMAIN, builderConfig));

		if (!project.getFile("src/wui_block/BLOCK-INF/block.xml").exists()) {
			targetMapping.put(SourceFolderEnum.WUI_BLOCK,        new TranslationOnlyMappingInfo(getServerFolder(SourceFolderEnum.WUI_BLOCK, builderConfig)));
		} else {
			targetMapping.put(SourceFolderEnum.WUI_BLOCK,        new WuiBlockMappingInfo(getServerFolder(SourceFolderEnum.WUI_BLOCK, builderConfig)));
		}
		targetMapping.put(SourceFolderEnum.WUI_BLOCK_SPECIFIC,   new WuiBlockMappingInfo(getServerFolder(SourceFolderEnum.WUI_BLOCK_SPECIFIC, builderConfig)));

		targetMapping.put(SourceFolderEnum.WUI_BLOCK_IMPORT,     new ConfigMappingInfo(getServerFolder(SourceFolderEnum.WUI_BLOCK_IMPORT, builderConfig)));
		targetMapping.put(SourceFolderEnum.CONFIG_SPECIFIC,      new CustomizableConfigMappingInfo(getServerFolder(SourceFolderEnum.CONFIG_SPECIFIC, builderConfig)));
	}

	private SimpleMappingInfo buildSimpleMappingInfo(SourceFolderEnum sourceFolder, Map<String, String> builderConfig) {
		String serverFolder = getServerFolder(sourceFolder, builderConfig);
		return new SimpleMappingInfo(serverFolder, sourceFolder.getGenSuffix());
	}

	public String getServerFolder(SourceFolderEnum sourceFolder, Map<String, String> config) {
		if (config.containsKey(sourceFolder.getServerFolderConfigKey())) {
			return config.get(sourceFolder.getServerFolderConfigKey());
		} else {
			return sourceFolder.getDefaultServerFolder();
		}
	}

	/**
	 * Return the target file for a specific source (file/folder)
	 * @throws CoreException
	 */
	public File getTarget(String source) throws CoreException {
		DeployConsole deployConsole = OCSServerUIExternalCore.getDefault().getDeployBuilderConsole();

		SourceFolderEnum sourceFolder = getSourceFolder(source);
		MappingInfo mapping = targetMapping.get(sourceFolder);
		if (sourceFolder == null || mapping == null) {
			deployConsole.printDebug("no mapping available for this source folder: sourceFolder=" + sourceFolder+ ", source=" + source);
			return null;
		} else {
			if (!StringUtils.startsWith(source, TARGET_FOLDER)) {
				deployConsole.printError("Only files in " + TARGET_FOLDER + " can be deployed. This is not the case for " + source);
				return null;
			}
			File target = mapping.getTarget(getDestination(), project.getName(), StringUtils.removeStart(source, TARGET_FOLDER));
			if (target == null) {
				deployConsole.printDebug("mapper found, but no mapping available for this source: " + source);
				return null;
			} else {
				return target;
			}
		}
	}
	
	/**
	 * Return the source folder enum which represent the source path of the specified path.
	 * If no source folder holds the path, return OTHER
	 */
	public SourceFolderEnum getSourceFolder(String path) {
		String pathInSrc = path;
		
		if (!path.startsWith(TARGET_FOLDER)) {
			return null;
		}
		pathInSrc = StringUtils.removeStart(pathInSrc, TARGET_FOLDER);
		if (pathInSrc.endsWith(".class")) {
			pathInSrc = StringUtils.removeEnd(pathInSrc, ".class")+".java";
		}
		
		for (SourceFolderEnum sourceFolder : SourceFolderEnum.values()) {
			if (project.getFile(sourceFolder.getWorkspaceFolder() + pathInSrc).exists()) {
				return sourceFolder;
			}
		}
		return null;
	}



	/* (non-Javadoc)
	 * @see com.odcgroup.ocs.server.builder.internal.mapping.TargetMapper#needsPrepareDeploymentDestinations()
	 */
	public boolean needsPrepareDeploymentDestinations() throws CoreException {
		// Gather required destinations (set as may need several time the same destination)
		Set<String> requiredDestinations = getRequiredDestinations();

		for (String destination : requiredDestinations) {
			if (PrepareDeploymentHelper.needsPrepareDeployment(destination)) {
				return true;
			}
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.ocs.server.builder.internal.mapping.TargetMapper#unjarDestinations()
	 */
	public IStatus prepareDeploymentDestinations() throws CoreException {
		List<IStatus> allErrorStatus = new LinkedList<IStatus>();

		// 1. Gather required destinations (set as may need several time the same destination)
		Set<String> requiredDestinations = getRequiredDestinations();

		// 2. Backup jars (if necessary)
		for (String destination : requiredDestinations) {
			IStatus status = PrepareDeploymentHelper.backupJar(destination);
			if (status != Status.OK_STATUS) {
				allErrorStatus.add(status);
			}
		}
		if (allErrorStatus.size() == 0) {
			return Status.OK_STATUS;
		} else {
			return new MultiStatus(
					ServerCore.PLUGIN_ID,
					IStatus.ERROR,
					allErrorStatus.toArray(new IStatus[allErrorStatus.size()]),
					"Unable to prepare the deployment of " + project.getName(),
					null);
		}
	}


		@Override
		public IStatus undeployDestinations() throws CoreException {
			List<IStatus> allErrorStatus = new LinkedList<IStatus>();
			Set<String> requiredDestinations = getRequiredDestinations();

			for (String destination : requiredDestinations) {
				IStatus status = PrepareDeploymentHelper.restoreBackupJar(new File(destination+".original"));
				OCSServerUIExternalCore.getDefault().getDeployBuilderConsole().printInfo("Undeploying: " + destination);
				if (status != Status.OK_STATUS) {
					OCSServerUIExternalCore.getDefault().getDeployBuilderConsole().printError("Undeploying failure: " + status.getMessage());
					allErrorStatus.add(status);
				}
			}

			if (allErrorStatus.size() == 0) {
				
				return Status.OK_STATUS;
			} else {
				return new MultiStatus(
						ServerCore.PLUGIN_ID,
						IStatus.ERROR,
						allErrorStatus.toArray(new IStatus[allErrorStatus.size()]),
						"Unable to undeploy " + project.getName() + " at that time.\n\n" +
						"Please add and remove the project again when the External Server is stopped.",
						null);
			}
		}

	/**
	 * Gather required destinations (use a Set as it may have several times the same destination)
	 * @return set of destinations
	 * @throws CoreException
	 */
	private Set<String> getRequiredDestinations() throws CoreException {
		Set<String> requiredDesinations = new HashSet<String>();
		for (SourceFolderEnum src : SourceFolderEnum.values()) {
			StringBuilder destinationStringBuilder = new StringBuilder();
			destinationStringBuilder.append(getDestination());
			destinationStringBuilder.append(File.separator);
			destinationStringBuilder.append(src.getDefaultServerFolder());
			destinationStringBuilder.append(File.separator);
			destinationStringBuilder.append(project.getName());
			destinationStringBuilder.append(src.getGenSuffix());
			destinationStringBuilder.append(".jar");
			String destination = destinationStringBuilder.toString();
			requiredDesinations.add(destination);
		}
		return requiredDesinations;
	}




}
