package com.odcgroup.t24.application.localrefapplication.importer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.google.inject.Inject;
import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.t24.application.localrefapplication.ILocalRefApplicationFilter;
import com.odcgroup.t24.application.localrefapplication.ILocalRefApplicationImporter;
import com.odcgroup.t24.application.localrefapplication.ILocalRefApplicationSelector;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.internal.T24ModelImportReport;
import com.odcgroup.t24.common.importer.internal.T24ModelImporter;
import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.LocalRefApplicationDetail;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public class LocalRefApplicationImporter extends T24ModelImporter implements
		ILocalRefApplicationImporter, ILocalRefApplicationSelector {
	
	//private static final Logger logger = LoggerFactory.getLogger(LocalRefApplicationImporter.class);
	
	private List<LocalRefApplicationDetail> allLocalRefApplications;
	private Set<LocalRefApplicationDetail> selectedLocalRefApplications = new HashSet<LocalRefApplicationDetail>();
	
	private IExternalServer extServer;
	
	private LocalRefApplicationFilter filter = new LocalRefApplicationFilter();  
	
	private IImportModelReport report;
	
	@Inject
	private LocalRefApplicationImportOperation operation;

	@Override
	public void importModels(IProgressMonitor monitor)
			throws T24ServerException {
		IFolder folder = getContainer().getParent().getFolder(new Path(LocalRefApplicationImportOperation.DESTINATION_FOLDER));
		report = new T24ModelImportReport(selectedLocalRefApplications.size(), folder);
		
		IExternalLoader loader = null;
		try {
			loader = getServer().getExternalLoader(LocalRefApplicationDetail.class);
			loader.open();
	 		operation.setContainer(getContainer());
	 		operation.setExternalLoader(loader);
	 		operation.setInDebug(getServer().getState() == IDSServerStates.STATE_ACTIVE_IN_DEBUG);
	 		operation.setModels(selectedLocalRefApplications);
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
	public IImportModelReport getImportReport() {
		return report;
	}

	@Override
	public int level() {
		return 0;
	}

	@Override
	public int minSelectedLevel() {
		if(selectedLocalRefApplications.isEmpty()){
			return 0;
		}
		return 2; // Project level selection
	}

	@Override
	public String getModelType() {
		return "domain";
	}

	@Override
	public ILocalRefApplicationFilter getFilter() {
		return filter;
	}

	@Override
	public List<LocalRefApplicationDetail> getFilteredModels()
			throws T24ServerException {
		loadAllLocalRefs();
		List<LocalRefApplicationDetail> models = new ArrayList<LocalRefApplicationDetail>();
		for (LocalRefApplicationDetail localRef : allLocalRefApplications) {
			if (filter.accept(localRef)) {
				models.add(localRef);
			}
		}
		return models;
	}
	
	/**
	 * Ensure all localRefs has been loaded from the current server.
	 */
	private void loadAllLocalRefs() throws T24ServerException {
		if (extServer!=null && !extServer.equals(getServer())) {
			allLocalRefApplications = null; 
		}
		if (allLocalRefApplications == null) {
			extServer = getServer(); 
			// reload all localRefs info from the current server
			allLocalRefApplications = new ArrayList<LocalRefApplicationDetail>();
			if (isServerSet()) {
				IExternalLoader loader = getServer().getExternalLoader(LocalRefApplicationDetail.class);
				if (loader != null) {
					try {
						loader.open();
						allLocalRefApplications = loader.getObjectDetails();
					} finally {
						loader.close();
					}
				}
			} 
		}
	}

	@Override
	public Set<LocalRefApplicationDetail> getSelectedModels() {
		return selectedLocalRefApplications;
	}

	@Override
	public ILocalRefApplicationSelector getApplicationSelector() {
		return this;
	}

}
