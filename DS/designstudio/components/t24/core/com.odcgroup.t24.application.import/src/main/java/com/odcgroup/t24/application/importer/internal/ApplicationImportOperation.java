package com.odcgroup.t24.application.importer.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.google.inject.Injector;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.t24.applicationimport.ApplicationImportUtils;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportOperation;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.internal.LoadXmlFromServer;
import com.odcgroup.t24.common.importer.internal.T24ModelImportOperation;
import com.odcgroup.t24.server.external.model.ApplicationDetail;
import com.odcgroup.t24.server.external.model.IExternalLoader;

public class ApplicationImportOperation extends T24ModelImportOperation<ApplicationInfo, ApplicationDetail> implements IImportOperation {

	public static final String BUSINESSTYPES = "com.temenos.t24.datamodel.businesstypes";
	
	// map the domain to ApplicationInfo model
	private Map<MdfDomain, List<ApplicationInfo>> domainModelsMap = new HashMap<MdfDomain, List<ApplicationInfo>>();
	
	private IExternalLoader loader;

	private final Injector injector;
	private ResourceSet resourceSet;

	public ApplicationImportOperation(Injector injector) {
		this.injector = injector;
	}

	protected IFolder getFolder() {
		return getContainer().getFolder(new Path("/"));
	}
	
	protected IFolder getBusinessTypesFolder() {
		return getContainer().getProject().getFolder(new Path("/domain" + ApplicationImportUtils.BUSINESS_TYPES_FOLDER));
	}
	
	@Override
	protected String getImportMessage() {
		return "Processing Applications Import";
	}
	
	@Override
	protected ApplicationInfo makeModelInfoFromDetail(ApplicationDetail detail) {
		return new ApplicationInfo(detail);
	}
	
	@Override 
	protected IImportingStep<ApplicationInfo> getFinalStep(IImportModelReport report) {
		SaveApplications saveApplications = new SaveApplications();
		saveApplications.init(report, getFolder(), getBusinessTypesFolder(), resourceSet, domainModelsMap);
		// This is ugly - we should convert this to real proper DI (once when we're not fighting fires left, right and center..)	                 saveApplications.init(report, getFolder(), getBusinessTypesFolder(), resourceSet, domainModelsMap);
        injector.injectMembers(saveApplications);
		return saveApplications;
	}
	
	@Override
	protected List<IImportingStep<ApplicationInfo>> getImportingSteps(IImportModelReport report) {
		List<IImportingStep<ApplicationInfo>> steps = new ArrayList<IImportingStep<ApplicationInfo>>();
		IFolder folder = getInDebug() ? getFolder() : null; 
		steps.add(new LoadXmlFromServer<ApplicationInfo>(report, this.loader, folder));
		steps.add(new ModifyXml<ApplicationInfo>(report));
		ImportApplications importApplications = new ImportApplications();
		importApplications.init(report, getFolder(), resourceSet, domainModelsMap);
		// This is ugly - we should convert this to real proper DI (once when we're not fighting fires left, right and center..)	                 saveApplications.init(report, getFolder(), getBusinessTypesFolder(), resourceSet, domainModelsMap);
        injector.injectMembers(importApplications);
		steps.add(importApplications);
		return steps;
	}
	
	public void setExternalLoader(IExternalLoader loader) {
		this.loader = loader;
	}
	
	public void setResourceSet(ResourceSet resourceSet) {
		this.resourceSet = resourceSet;
	}
	
}
