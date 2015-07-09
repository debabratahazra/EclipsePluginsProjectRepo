package com.odcgroup.t24.application.importer.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.t24.applicationimport.ApplicationsImport;
import com.odcgroup.t24.applicationimport.ApplicationsImportProblemsException;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.server.external.model.ApplicationDetail;
import com.odcgroup.translation.core.TranslationException;

public class ImportApplications implements IImportingStep<ApplicationInfo> {
	
	@Inject
	private Provider<ApplicationsImport> applicationsImportProvider;
	
	
	private IFolder folder;
	private ResourceSet resourceSet;
	private IImportModelReport report;
	private Map<MdfDomain, List<ApplicationInfo>> domainModelsMap;
	
	public void init(IImportModelReport report, IFolder folder, ResourceSet resourceSet,
			Map<MdfDomain, List<ApplicationInfo>> domainModelsMap ) {
		this.report = report;
		this.folder = folder;
		this.resourceSet = resourceSet;
		this.domainModelsMap = domainModelsMap;
		
	}

	private String getMessage(ApplicationInfo model) {
		StringBuffer b = new StringBuffer(64);
		b.append(model.getModelType());
		b.append(": "); //$NON-NLS-1$
		b.append(model.getDescription());
		b.append(": "); //$NON-NLS-1$
		b.append("Creating Domain"); //$NON-NLS-1$
		return b.toString();
	}
	
	/**
	 * map MdfDoamin to ApplicationInfo model
	 */
	private void mapDomainToApplicationInfoModel(ApplicationInfo model, MdfDomain domain) {
		List<ApplicationInfo> models = domainModelsMap.get(domain); 
		if (models == null) {
			models = new ArrayList<ApplicationInfo>();
			domainModelsMap.put(domain, models);
		}
		models.add(model);
	}
	
	@Override
	public boolean execute(ApplicationInfo model, IProgressMonitor monitor) {
		
		boolean success = true;

		Set<MdfDomain> readDomains = new HashSet<MdfDomain>();
		
		if (monitor != null) {
			monitor.subTask(getMessage(model));
		}
		
		try {
			// read the xml file and get the domain.
			applicationsImportProvider.get().importApplications(model.getXmlString(), folder, resourceSet, readDomains,domainModelsMap.keySet());
			if (!readDomains.isEmpty()) {
				// set the domain as ObjectModel
				MdfDomain domain = (MdfDomain) readDomains.toArray()[0];
				setDomainDescriptionAsAnnotation(model,domain);
				mapDomainToApplicationInfoModel(model, domain);
			}
		} catch (ApplicationsImportProblemsException ex) {
			report.error(model, ex.getMessage());
			success = false;
		} catch (InterruptedException e) {
			report.error(e);
			success = false;
		}

		return success;
	}

	/**
	 * @param model
	 * @param domain
	 */
	private void setDomainDescriptionAsAnnotation(ApplicationInfo model, MdfDomain domain) {
		try{
			 ApplicationDetail details = (ApplicationDetail)model.externalObject();
			 String applicationName = details.getName();
			 applicationName = applicationName.replace('.', '_');
			 MdfClass klass = domain.getClass(applicationName);
			 String discriptionValue =  StringUtils.isEmpty(model.getApplicationDescription())? "" :  model.getApplicationDescription(); 
		     T24Aspect.setDescription(klass, discriptionValue,folder.getProject());
		}catch(TranslationException e){
			report.error(model, e.getMessage());
		}
		
	}

}
