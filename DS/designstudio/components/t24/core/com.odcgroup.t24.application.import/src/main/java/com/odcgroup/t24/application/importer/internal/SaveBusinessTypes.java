package com.odcgroup.t24.application.importer.internal;

import java.io.IOException;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;

import com.google.inject.Inject;
import com.odcgroup.domain.annotations.JavaAspectDS;
import com.odcgroup.mdf.ecore.MdfBusinessTypeImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.t24.applicationimport.ApplicationImportUtils;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.workbench.core.OfsCore;

/**
 * Class to save T24 Business Types while importing Applications
 * 
 * @author vramya
 * @author kosyakov (for Michael Vorburger) - partial rewrite to be independant of DomainRepository/IOfsProject, @see http://rd.oams.com/browse/DS-7424
 */
public class SaveBusinessTypes {
	
	

	private IFolder folder;

	private ResourceSet resourceSet;
	
	private IImportModelReport report;
	
	private MdfDomainImpl businessTypesDomain;
	 @Inject	         
     private ResourceDescriptionsProvider resourceDescriptionsProvider;
	public void setInput(IImportModelReport report, IFolder folder, ResourceSet resourceSet) {
		this.report = report;
		this.resourceSet = resourceSet;
		this.folder = folder;
		updateDomainsFromRepo(resourceSet);
	}

	private void updateDomainsFromRepo(ResourceSet resourceSet) {
		businessTypesDomain = getBusinessTypesDomain();
		if (businessTypesDomain == null) {
			if (!folder.exists()) {
				try {
					folder.create(true, true, null);
				} catch (CoreException e) {
				}
			}
			businessTypesDomain = createDomain(ApplicationImportUtils.BUSINESSTYPES_DOMAIN, ApplicationImportUtils.BUSINESSTYPES_DOMAINS_NAMESPACE);
			addDefaultBusinessTypes();
			saveDomain(businessTypesDomain);
		}
	}

	public MdfDomainImpl getBusinessTypesDomain() {
			IResourceDescriptions index = resourceDescriptionsProvider.getResourceDescriptions(resourceSet);
			Iterable<IEObjectDescription> objectsDescriptors = ApplicationImportUtils.getAllDomainsinAProject(folder
					.getProject().getName(),index);
			return getDomain(objectsDescriptors, ApplicationImportUtils.BUSINESSTYPES_DOMAIN);
		
		
	}

	protected MdfDomainImpl getDomain(Iterable<IEObjectDescription> objectsDescriptors, String domainName) {
		for (IEObjectDescription objectDescription : objectsDescriptors) {
			QualifiedName qName = objectDescription.getName();
			if (qName!=null && qName.getSegmentCount() == 1 && qName.getFirstSegment().equals(domainName)) {
				EObject object = resourceSet.getEObject(objectDescription.getEObjectURI(), true);
				if (object != null) {
					return (MdfDomainImpl) object;
				}
			}
		}
		return null;
	}

	private MdfDomainImpl createDomain(String domainName, String nameSpace) {
		MdfDomainImpl domain = null;
		try {
			domain = (MdfDomainImpl) MdfFactory.eINSTANCE.createMdfDomain();
			domain.setMetamodelVersion(OfsCore.getVersionNumber());
			domain.setName(domainName);
			domain.setNamespace(ApplicationImportUtils.BUSINESS_TYPES_NAMESPACE_URL + nameSpace);
			JavaAspectDS.setPackage(domain, ApplicationImportOperation.BUSINESSTYPES);
			T24Aspect.setBusinessTypes(domain, true);
			IFile file = folder.getFile(ApplicationImportUtils.BUSINESSTYPES_FILE_NAME + "." + ApplicationImportUtils.DOMAIN);
			URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			Resource resource = resourceSet.createResource(uri);
			resource.getContents().add(domain);
			resource.save(null);
		} catch (Exception e) {
			report.error(e);
		}
		return domain;
	}

	@SuppressWarnings("unchecked")
	private void addDefaultBusinessTypes() {
		for (String bt : ApplicationImportUtils.BUSSINESS_TYPES) {
			MdfBusinessTypeImpl btImpl = (MdfBusinessTypeImpl) MdfFactory.eINSTANCE.createMdfBusinessType();
			btImpl.setName(bt);
			btImpl.setType(PrimitivesDomain.STRING);
			businessTypesDomain.getBusinessTypes().add(btImpl);
		}
	}

	private void saveDomain(MdfDomainImpl domain) {
		try {
			Resource resource = domain.eResource();
			if (resource != null) {
				resource.save(null);
			}
		} catch (IOException e) {
			report.error(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected void updateBusinessTypesDomain(Set<String> businessTypes) {
		for (String bt : businessTypes) {
			if(bt == null) continue;
			MdfBusinessTypeImpl btImpl = (MdfBusinessTypeImpl) MdfFactory.eINSTANCE.createMdfBusinessType();
			String businessType = ApplicationImportUtils.getBusinessTypeName(bt);
			if (businessType != null) {
				if (StringUtils.isBlank(businessType) || businessType.equals("0")) {
					if (businessTypesDomain.getBusinessType(ApplicationImportUtils.CHARTYPE_NUMERIC) == null) {
						btImpl.setName(ApplicationImportUtils.CHARTYPE_NUMERIC);
						btImpl.setType(PrimitivesDomain.STRING);
						businessTypesDomain.getBusinessTypes().add(btImpl);
						saveDomain(businessTypesDomain);
					}
				} else if (businessTypesDomain.getBusinessType(businessType) == null) {
					btImpl.setName(businessType);
					btImpl.setType(PrimitivesDomain.STRING);
					T24Aspect.setT24Name(btImpl, bt);
					businessTypesDomain.getBusinessTypes().add(btImpl);
					saveDomain(businessTypesDomain);
				}
			}
		}
	}

}
