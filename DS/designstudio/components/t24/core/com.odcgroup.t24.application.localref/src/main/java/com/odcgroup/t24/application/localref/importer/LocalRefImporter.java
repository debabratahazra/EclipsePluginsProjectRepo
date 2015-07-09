package com.odcgroup.t24.application.localref.importer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.google.inject.Inject;
import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.t24.application.localref.ILocalRefFilter;
import com.odcgroup.t24.application.localref.ILocalRefImporter;
import com.odcgroup.t24.application.localref.ILocalRefSelector;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.internal.T24ModelImportReport;
import com.odcgroup.t24.common.importer.internal.T24ModelImporter;
import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.LocalRefDetail;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

/**
 * @author ssreekanth
 *
 */
public class LocalRefImporter extends T24ModelImporter implements ILocalRefImporter, ILocalRefSelector {
	
	private List<LocalRefDetail> allLocalRefs;
	private Set<LocalRefDetail> selectedLocalRefs = new HashSet<LocalRefDetail>();
	
	private LocalRefFilter filter = new LocalRefFilter();  
	
	private IImportModelReport report;
	private IExternalServer server1;
	
	@Inject
	private LocalRefImportOperation operation;
	
	/**
	 * Ensure all localRefs has been loaded from the current server.
	 */
	private void loadAllLocalRefs() throws T24ServerException {
		if (server1!=null && !server1.equals(getServer())) {
			allLocalRefs = null; 
		}
		if (allLocalRefs == null) {
			server1 = getServer(); 
			// reload all localRefs info from the current server
			allLocalRefs = new ArrayList<LocalRefDetail>();
			if (isServerSet()) {
				IExternalLoader loader = getServer().getExternalLoader(LocalRefDetail.class);
				if (loader != null) {
					try {
						loader.open();
						allLocalRefs = loader.getObjectDetails();
					} finally {
						loader.close();
					}
				}
			} 
		}
	}

	@Override
	public final ILocalRefFilter getFilter() {
		return filter;
	}

	@Override
	public List<LocalRefDetail> getFilteredModels() throws T24ServerException {
		loadAllLocalRefs();
		List<LocalRefDetail> models = new ArrayList<LocalRefDetail>();
		for (LocalRefDetail localRef : allLocalRefs) {
			if (filter.accept(localRef)) {
				models.add(localRef);
			}
		}
		return models;
	}
	
	@Override
	public final ILocalRefSelector getLocalRefSelector() {
		return this;
	}

	@Override
	public final Set<LocalRefDetail> getSelectedModels() {
		return selectedLocalRefs;
	}
	
	@Override
	public int level() {
		return 0;
	}
	
	@Override
	public int minSelectedLevel() {
		if(selectedLocalRefs.isEmpty()){
			return 0;
		}
		return 2; // Project level selection
	}

	@Override
	public String getModelType() {
		return "domain";
	}
	
	@Override
	public void importModels(IProgressMonitor monitor) throws T24ServerException {
		IFolder folder = getContainer().getParent().getFolder(new Path(LocalRefImportOperation.DESTINATION_FOLDER));
		report = new T24ModelImportReport(selectedLocalRefs.size(), folder);
		IExternalLoader loader = null;
		try {
			loader = getServer().getExternalLoader(LocalRefDetail.class);
			loader.open();
	 		operation.setContainer(getContainer()); 		
	 		operation.setExternalLoader(loader);
	 		operation.setInDebug(getServer().getState() == IDSServerStates.STATE_ACTIVE_IN_DEBUG);
	 		operation.setModels(selectedLocalRefs);
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



}
