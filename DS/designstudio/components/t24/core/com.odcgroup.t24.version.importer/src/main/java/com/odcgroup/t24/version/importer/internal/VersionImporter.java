package com.odcgroup.t24.version.importer.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.internal.T24ModelImportReport;
import com.odcgroup.t24.common.importer.internal.T24ModelImporter;
import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.VersionDetail;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.t24.version.importer.IVersionFilter;
import com.odcgroup.t24.version.importer.IVersionImporter;
import com.odcgroup.t24.version.importer.IVersionSelector;

public class VersionImporter extends T24ModelImporter implements IVersionImporter, IVersionSelector {
	private static final Logger logger = LoggerFactory.getLogger(VersionImporter.class);
	
	private List<VersionDetail> allVersions;

	private Set<VersionDetail> selectedVersions = new HashSet<VersionDetail>();
	
	private Set<String> applications;
	private Set<String> components;
	private Set<String> modules;
	private IExternalServer server1;
	private Set<String> descriptions;
	
	private VersionFilter filter = new VersionFilter();  
	
	private IImportModelReport report;
	
	private @Inject VersionImportOperation operation;
	
	/**
	 * Ensure all version has been loaded from the current server.
	 */
	private void loadAllVersions() throws T24ServerException {
		if (server1!=null && !server1.equals(getServer())) {
			allVersions = null; 
		}
		if (allVersions == null) {
			server1 = getServer(); 
			modules = new HashSet<String>();
			components = new HashSet<String>();
			applications = new HashSet<String>();
			descriptions = new HashSet<String>();

			// reload all versions info from the current server
			allVersions = new ArrayList<VersionDetail>();
			if (isServerSet()) {
				IExternalLoader loader = getServer().getExternalLoader(VersionDetail.class);
				if (loader != null) {
					try {
						loader.open();
						allVersions = loader.getObjectDetails();
					} finally {
						loader.close();
					}
				}
			} 
			
			// extract modules, components, applications
			for (VersionDetail version : allVersions) {
				modules.add(version.getModule());
				components.add(version.getComponent());
				applications.add(version.getApplication());
				descriptions.add(version.getDescription());
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
			// ignore
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
			// ignore
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
			// ignore
		}
		return result;
	}

	@Override
	public final IVersionFilter getFilter() {
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
	public final IVersionSelector getVersionSelector() {
		return this;
	}

	@Override
	public final Set<VersionDetail> getSelectedModels() {
		return selectedVersions;
	}
	
	@Override
	public int level() {
		// sub-packages will not be displayed in the wizard
		return -1;  
	}
	
	@Override
	public int minSelectedLevel() {
		return 0;
	}

	@Override
	public String getModelType() {
		return "version";
	}

	@Override
	public void importModels(IProgressMonitor monitor) throws T24ServerException {
		report = new T24ModelImportReport(selectedVersions.size(), (IFolder)getContainer());
		IExternalLoader loader = null;
		try {
			loader = getServer().getExternalLoader(VersionDetail.class);
			loader.open();
	 		operation.setContainer(getContainer());
	 		operation.setExternalLoader(loader);
	 		operation.setInDebug(getServer().getState() == IDSServerStates.STATE_ACTIVE_IN_DEBUG);
	 		operation.setModels(selectedVersions);
	 		operation.importModels(report, monitor); 
		} catch (T24ServerException ex) {
			report.error(ex);
		} finally {
			if (loader != null) {
				loader.close();
			}
		}
	}

	@Override
	public final IImportModelReport getImportReport() {
		return report; 
	}

	@Override
	public List<String> getDescriptions() {
		List<String> result = new ArrayList<String>();
		try {
			loadAllVersions();
			result = new ArrayList<String>(descriptions); 
		} catch (T24ServerException ex) {
			logger.warn("loadAllVersions() failed", ex);
			// ignore
		}
		return result;
	}

}
