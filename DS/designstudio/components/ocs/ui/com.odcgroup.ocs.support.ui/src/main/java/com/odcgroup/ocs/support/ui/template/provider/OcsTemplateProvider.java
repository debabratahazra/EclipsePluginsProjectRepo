package com.odcgroup.ocs.support.ui.template.provider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.embedder.IMaven;

import com.odcgroup.ocs.support.installer.OcsBinariesExtractionFacade;
import com.odcgroup.workbench.core.targetplatform.TargetPlatform;
import com.odcgroup.workbench.core.template.ITemplateProvider2;

public class OcsTemplateProvider implements ITemplateProvider2 {
	private static final String TEMPLATE_LOCATION = "com/odcgroup/ocs/custo/template";

	/**
	 * Gather all the template found in TEMPLATE_LOCATION<br>
	 * The files found have the following pattern<br>
	 * {@code MAVEN_LOCAL_REPO/TEMPLATE_LOCATION/<template-name>/<version>/<template-name>-<version>.jar}
	 */
	public List<File> getAvailableTemplates() {
		List<File> availableTemplates = new ArrayList<File>();
		File templateLocation = new File(OcsBinariesExtractionFacade.instance().getOcsBinariesRepositoryFolder(), TEMPLATE_LOCATION);
		if (templateLocation.exists()) {
			for (File templateFolder : templateLocation.listFiles()) {
				if (templateFolder.isDirectory()) {
					File template = new File(templateFolder, 
							"" + 
							TargetPlatform.getTechnicalVersion() + 
							"/" + 
							templateFolder.getName()+ 
							"-" + 
							TargetPlatform.getTechnicalVersion() + 
							".jar");
					if (template.exists()) {
						availableTemplates.add(template);
					}
				}
			}
		}
		return availableTemplates;
	}

	@Override
	public File resolveArtifact(String groupId, String artifactId,
			String version, String classifier, String type) {
		IMaven maven = MavenPlugin.getMaven();
		try {
			String nonSnapshotPath = maven.getArtifactPath(maven.getLocalRepository(),
					groupId, artifactId, version, type, classifier);
			File nonSnapshotFile = new File(maven.getLocalRepository().getBasedir(), nonSnapshotPath);
			if (nonSnapshotFile.exists()) {
				return nonSnapshotFile;
			}
		} catch (CoreException e) {
			// Ignore
		}
		try {
			String snapshotPath = maven.getArtifactPath(maven.getLocalRepository(),
					groupId, artifactId, version + "-SNAPSHOT", type, classifier);
			File snapshotFile = new File(maven.getLocalRepository().getBasedir(), snapshotPath);
			if (snapshotFile.exists()) {
				return snapshotFile;
			}
		} catch (CoreException e) {
			// Ignore
		}
		return null;

	}
	
	
}
