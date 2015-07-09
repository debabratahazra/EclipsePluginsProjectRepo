package com.odcgroup.ocs.support.internal.repo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.ocs.support.OCSPluginActivator;
import com.odcgroup.ocs.support.installer.OcsBinariesExtractionFacade;
import com.odcgroup.workbench.core.IContainerIdentifier;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.MavenContainerIdentifier;
import com.odcgroup.workbench.core.targetplatform.TargetPlatform;
import com.odcgroup.workbench.generation.init.IStandardProjectProvider;

/**
 * This class parses the file config/standardprojects.txt and provides the content as standard projects.
 * It then resolves the jar files from the embedded OCS repo.
 * 
 * @author kkr
 *
 */
 
public class StandardModelProvider implements IStandardProjectProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(StandardModelProvider.class);

	protected Collection<IContainerIdentifier> containerIds = new ArrayList<IContainerIdentifier>();
	
	public StandardModelProvider() {
		initializeContainerIds();
	}

	@Override
	public Collection<IContainerIdentifier> getContainerIdentifiers() {
		return containerIds;
	}

	@Override
	public File getFile(IContainerIdentifier id) {
		File repoFolder = getRepoFolder();
		String relativePath = id.getGroupId().replace('.', File.separatorChar) + File.separator +
			id.getName() + File.separator + 
			id.getVersion() + File.separator +
			id.getName() + "-" + id.getVersion() + "-standard.jar";
		File standardProjectFile = new File(repoFolder, relativePath);
		if(standardProjectFile.exists()) {
			return standardProjectFile;
		} else {
			logger.warn("Cannot find standard project at '{}'", standardProjectFile.getAbsolutePath());
			return null;
		}
	}

	@Override
	public File getModelsPomFile(IContainerIdentifier id) {
		File repoFolder = getRepoFolder();
		String artifactId = id.getName();
		String relativePath = id.getGroupId().replace('.', File.separatorChar) + File.separator +
			artifactId + File.separator + 
			id.getVersion() + File.separator +
			artifactId + "-" + id.getVersion() + "-pom.xml";
		File pomFile = new File(repoFolder, relativePath);
		if(pomFile.exists()) {
			return pomFile;
		} else {
			logger.warn("Cannot find standard models project pom.xml at '{}'", pomFile.getAbsolutePath());
			return null;
		}
	}

	@Override
	public File getGenPomFile(IContainerIdentifier id) {
		File repoFolder = getRepoFolder();
		String artifactId = id.getName() + "-gen";
		String relativePath = id.getGroupId().replace('.', File.separatorChar) + File.separator +
			artifactId + File.separator + 
			id.getVersion() + File.separator +
			artifactId + "-" + id.getVersion() + "-pom.xml";
		File pomFile = new File(repoFolder, relativePath);
		if(pomFile.exists()) {
			return pomFile;
		} else {
			logger.warn("Cannot find standard gen project pom.xml at '{}'", pomFile.getAbsolutePath());
			return null;
		}
	}

	private File getRepoFolder() {
		File repoFolder;
		String localRepo = OfsCore.getForcedLocalRepository();
		if(localRepo!=null) {
			repoFolder = new File(localRepo);
		} else {
			repoFolder = OcsBinariesExtractionFacade.instance().getOcsBinariesRepositoryFolder();
		}
		return repoFolder;
	}

	private void initializeContainerIds() {
		InputStream is;
		try {
			is = FileLocator.openStream(OCSPluginActivator.getDefault().getBundle(),
					new Path("config/standardprojects.txt"), false);
			List<String> lines = IOUtils.readLines(is);
			for(String line : lines) {
				line = line.trim();
				if(line.isEmpty() || line.startsWith("#")) continue;
				String[] segments = line.split(":");
				if(segments.length==2) {
					IContainerIdentifier id = new MavenContainerIdentifier(segments[0], segments[1], TargetPlatform.getTechnicalVersion());
					containerIds.add(id);
				} else {
					logger.error("standardprojects.txt contains invalid entries: {}" + line);
				}
			}
		} catch (IOException e) {
			logger.error("Exception while reading standardprojects.txt: {}" + e.getMessage());
		}
	}

}
