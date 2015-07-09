package com.odcgroup.iris.importer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.t24.application.importer.IApplicationSelector;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.internal.T24ModelImportReport;
import com.odcgroup.t24.common.importer.internal.T24ModelImporter;
import com.odcgroup.t24.enquiry.importer.IEnquirySelector;
import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.model.InteractionDetail;
import com.odcgroup.t24.server.external.model.internal.IInteractionLoader;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.t24.version.importer.IVersionSelector;

public class IRISModelImporter extends T24ModelImporter implements IIRISImporter {
	
	private static final Logger logger = LoggerFactory.getLogger(IRISModelImporter.class);
	
	private Set<InteractionDetail> entities = null;
	
	private IApplicationSelector applicationSelector = new IRISApplicationSelector() {
		protected IProject getProject() {
			return IRISModelImporter.this.getProject();
		}
	};

	private IEnquirySelector enquirySelector = new IRISEnquirySelector() {
		protected IProject getProject() {
			return IRISModelImporter.this.getProject();
		}
	};
	
	private IVersionSelector versionSelector = new IRISVersionSelector() {
		protected IProject getProject() {
			return IRISModelImporter.this.getProject();
		}
	};

	private IImportModelReport report;
	
	private IRISImportOperation operation = new IRISImportOperation();
	
	private <T extends IExternalObject> void collectEntityNames(Set<T> entities, List<String> names) {
		for (IExternalObject entity : entities) {
			names.add(entity.getName());
		}
	}
	
	
	// Collect all entity names (i.e applications/enquiries T24 names)
	private Set<InteractionDetail> getEntities() {
		if(this.entities != null) {
			// For JUnit test case.
			return this.entities;
		}
		List<String> entityNames = new ArrayList<String>();
		collectEntityNames(getApplicationSelector().getSelectedModels(), entityNames);
		collectEntityNames(getEnquirySelector().getSelectedModels(), entityNames);
		collectEntityNames(getVersionSelector().getSelectedModels(), entityNames);
		
		Set<InteractionDetail> entities = new HashSet<InteractionDetail>();
		
		for (String name : entityNames) {
			InteractionDetail detail = new InteractionDetail(name);
			entities.add(detail);
		}
		
		return entities;
	}
	
	// For JUnit test
	public void setEntities(Set<InteractionDetail> entities) {
		this.entities = entities;
	}


	@Override
	public String getModelType() {
		return "rim";
	}
	
	@Override
	public void importModels(IProgressMonitor monitor) throws T24ServerException {
		
		String modelName = getModelName();
		
		Set<InteractionDetail> entities = getEntities();
		report = new T24ModelImportReport(entities.size(), (IFolder)getContainer());
		
		IExternalLoader loader = null;
		try {
			loader = getServer().getExternalLoader(InteractionDetail.class);
			((IInteractionLoader)loader).setModelName(modelName);
			loader.open();
			operation.setContainer(getContainer());
			operation.setModelName(modelName);
	 		operation.setExternalLoader(loader);
	 		operation.setInDebug(getServer().getState() == IDSServerStates.STATE_ACTIVE_IN_DEBUG);
	 		operation.setModels(entities);
	 		operation.importModels(report, monitor);
		} catch (Exception ex) {
			report.error(ex);
			logger.error(ex.getMessage(), ex);
		} finally {
			if (loader != null) {
				try {
					loader.close();//getContainer(), modelName);
				} catch (Exception ex) {
					report.error(ex);
					logger.error(ex.getMessage(), ex);
				}
			}
		} 	
	}

	@Override
	public IImportModelReport getImportReport() {
		return report;
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
	public final IApplicationSelector getApplicationSelector() {
		return this.applicationSelector;
	}

	@Override
	public final IEnquirySelector getEnquirySelector() {
		return this.enquirySelector;
	}

	@Override
	public boolean isSelectionEmpty() {
		return getApplicationSelector().getSelectedModels().isEmpty()
			&& getEnquirySelector().getSelectedModels().isEmpty()
			&& getVersionSelector().getSelectedModels().isEmpty();
	}


	@Override
	public IVersionSelector getVersionSelector() {
		return this.versionSelector;
	}
}
