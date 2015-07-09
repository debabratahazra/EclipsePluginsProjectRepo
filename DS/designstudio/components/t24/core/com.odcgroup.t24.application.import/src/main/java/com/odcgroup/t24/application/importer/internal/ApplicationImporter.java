package com.odcgroup.t24.application.importer.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;
import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.t24.application.importer.IApplicationFilter;
import com.odcgroup.t24.application.importer.IApplicationImporter;
import com.odcgroup.t24.application.importer.IApplicationSelector;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.internal.T24ModelImportReport;
import com.odcgroup.t24.common.importer.internal.T24ModelImporter;
import com.odcgroup.t24.server.external.model.ApplicationDetail;
import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.workbench.core.xtext.XtextResourceSetProviderDS;

public class ApplicationImporter extends T24ModelImporter implements IApplicationImporter, IApplicationSelector {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationImporter.class);
	
	private List<ApplicationDetail> allApplications;
	private Set<ApplicationDetail> selectedApplications = new HashSet<ApplicationDetail>();
	
	private Set<String> components;
	private Set<String> products;
	private IExternalServer server1;
	private Set<String> description;
	
	private ApplicationFilter filter = new ApplicationFilter();  
	
	private IImportModelReport report;
	
	private ApplicationImportOperation operation;

	private final Injector injector;
	
	public ApplicationImporter(Injector injector) {
		this.injector = injector;
	}

	/**
	 * Ensure all applications has been loaded from the current server.
	 */
	private void loadAllApplications() throws T24ServerException {
		if (server1!=null && !server1.equals(getServer())) {
			allApplications = null; 
		}
		if (allApplications == null) {
			server1 = getServer(); 
			products = new HashSet<String>();
			components = new HashSet<String>();
			description = new HashSet<String>();
			// reload all applications info from the current server
			allApplications = new ArrayList<ApplicationDetail>();
			if (isServerSet()) {
				IExternalLoader loader = getServer().getExternalLoader(ApplicationDetail.class);
				if (loader != null) {
					try {
						loader.open();
						allApplications = loader.getObjectDetails();
					} finally {
						loader.close();
					}
				}
			} 
			
			// extract modules, components, applications
			for (ApplicationDetail application : allApplications) {
				products.add(application.getProduct());
				components.add(application.getComponent());
				description.add(application.getApplicaitonDescription());
			}
		}
	}

	@Override
	public List<String> getProducts() {
		List<String> result = new ArrayList<String>();
		try {
			loadAllApplications();
			result = new ArrayList<String>(products); 
		} catch (T24ServerException ex) {
			logger.warn("loadAllApplications() failed", ex);
			// ignore
		}
		return result;
	}

	@Override
	public List<String> getComponents() {
		List<String> result = new ArrayList<String>();
		try {
			loadAllApplications();
			result = new ArrayList<String>(components); 
		} catch (T24ServerException ex) {
			logger.warn("loadAllApplications() failed", ex);
			// ignore
		}
		return result;
	}

	@Override
	public final IApplicationFilter getFilter() {
		return filter;
	}

	@Override
	public List<ApplicationDetail> getFilteredModels() throws T24ServerException {
		loadAllApplications();
		List<ApplicationDetail> models = new ArrayList<ApplicationDetail>();
		for (ApplicationDetail application : allApplications) {
			if (filter.accept(application)) {
				models.add(application);
			}
		}
		return models;
	}
	
	@Override
	public final IApplicationSelector getApplicationSelector() {
		return this;
	}

	@Override
	public final Set<ApplicationDetail> getSelectedModels() {
		return selectedApplications;
	}
	
	@Override
	public int level() {
		return -1;
	}

	@Override
	public int minSelectedLevel() {
		return 0;
	}

	@Override
	public String getModelType() {
		return "domain";
	}
	
	@Override
	public void importModels(IProgressMonitor monitor) throws T24ServerException {
		IFolder folder = getContainer().getFolder(new Path("/"));
		report = new T24ModelImportReport(selectedApplications.size(), folder);
		
		IExternalLoader loader = null;
		try {
			loader = getServer().getExternalLoader(ApplicationDetail.class);
			loader.open();
			operation = new ApplicationImportOperation(injector);
	 		operation.setContainer(getContainer());
	 		operation.setResourceSet(createResourceSet(operation.getFolder().getProject()));
	 		operation.setExternalLoader(loader);
	 		operation.setInDebug(getServer().getState() == IDSServerStates.STATE_ACTIVE_IN_DEBUG);
	 		operation.setModels(selectedApplications);
	 		operation.importModels(report, monitor);
		} catch (T24ServerException ex) {
			report.error(ex);
		} finally {
			if (loader != null) {
				loader.close();
			}
		}
	}

	private ResourceSet createResourceSet(IProject project) {
		XtextResourceSet resourceSet = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, project);
		resourceSet.getLoadOptions().put(ResourceDescriptionsProvider.LIVE_SCOPE, Boolean.TRUE);
		return resourceSet;
	}

	@Override
	public final IImportModelReport getImportReport() {
		return report;
	}

	@Override
	public List<String> getAppDescriptions() {
		List<String> result = new ArrayList<String>();
		try {
			loadAllApplications();
			result = new ArrayList<String>(description); 
		} catch (T24ServerException ex) {
			logger.warn("loadAllApplications() failed", ex);
			// ignore
		}
		return result;
	}

}
