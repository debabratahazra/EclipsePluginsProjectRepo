package com.odcgroup.iris.importer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.t24.server.external.model.VersionDetail;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.t24.version.importer.IVersionFilter;
import com.odcgroup.t24.version.importer.IVersionSelector;
import com.odcgroup.t24.version.importer.internal.VersionFilter;
import com.odcgroup.t24.version.repository.IVersionRepository;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;
import com.odcgroup.workbench.core.repository.LanguageRepositoryProvider;

public class IRISVersionSelector implements IVersionSelector {

	private static final Logger logger = LoggerFactory.getLogger(IRISVersionSelector.class);
	
	private List<VersionDetail> allVersions;
	private Set<VersionDetail> selectedVersions = new HashSet<VersionDetail>();
	
	private Set<String> applications;
	private Set<String> components;
	private Set<String> modules;
	
	private VersionFilter filter = new VersionFilter();  
	
	private void loadVersionFromModels(final List<VersionDetail> allVersions) {
		
		final IVersionRepository versionRepository = 
				LanguageRepositoryProvider.getLanguageRepository(IVersionRepository.VERSION_LANGUAGE_NAME);
		
		Iterable<IEObjectDescription> candidates = 
				versionRepository.getExportedObjectsByType(VersionDSLPackage.Literals.VERSION);

		IterableExtensions.<IEObjectDescription>forEach(candidates, 
				new Procedure1<IEObjectDescription>() {
					public void apply(IEObjectDescription p) {
						String screenName = versionRepository.getT24Name(p);
						String component = versionRepository.getT24ComponentName(p); 
						String module = versionRepository.getT24ModuleName(p); 
						String appName = versionRepository.getT24ApplicationName(p); 
						VersionDetail detail = new VersionDetail(screenName, component, module, appName);
						if (!detail.isEmpty()) {
							allVersions.add(detail);
						}
					}
				});

	}
	
	protected IProject getProject() {
		return null;
	}
	
	@Override
	public String getModelName() {
		return "???";
	}
	
	/**
	 * Ensure all versions has been loaded from the current server.
	 */
	private void loadAllVersions() throws T24ServerException {
		if (allVersions == null) {
			modules = new HashSet<String>();
			components = new HashSet<String>();
			applications = new HashSet<String>();

			// reload all version info from the current project
			allVersions = new ArrayList<VersionDetail>();
			loadVersionFromModels(allVersions);
			
			// extract modules, components, applications
			for (VersionDetail version : allVersions) {
				modules.add(version.getModule());
				components.add(version.getComponent());	
				applications.add(version.getApplication());
			}
		}
	}

	@Override
	public List<String> getModules() {
		List<String> result = new ArrayList<String>();
		try {
			loadAllVersions();
			result = new ArrayList<String>(modules); 
		} catch (T24ServerException ex) {
			logger.warn("loadAllVersions() failed", ex);
		}
		return result;
	}

	@Override
	public List<String> getApplications() {
		List<String> result = new ArrayList<String>();
		try {
			loadAllVersions();
			result = new ArrayList<String>(applications); 
		} catch (T24ServerException ex) {
			logger.warn("loadAllVersions() failed", ex);
		}
		return result;
	}

	@Override
	public List<String> getComponents() {
		List<String> result = new ArrayList<String>();
		try {
			loadAllVersions();
			result = new ArrayList<String>(components); 
		} catch (T24ServerException ex) {
			logger.warn("loadAllVersions() failed", ex);
		}
		return result;
	}

	@Override
	public IVersionFilter getFilter() {
		return filter;
	}

	@Override
	public List<VersionDetail> getFilteredModels() throws T24ServerException {
		loadAllVersions();
		List<VersionDetail> models = new ArrayList<VersionDetail>();
		for (VersionDetail version : allVersions) {
			if (filter.accept(version)) {
				models.add(version);
			}
		}
		return models;
	}

	@Override
	public Set<VersionDetail> getSelectedModels() {
		return selectedVersions;
	}

	@Override
	public List<String> getDescriptions() {
		List<String> result = new ArrayList<String>();
		return result;
	}

}
