package com.odcgroup.t24.application.importer.internal;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.ApplicationImportUtils;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.workbench.core.helper.FeatureSwitches;

public class SaveApplications implements IImportingStep<ApplicationInfo> {

	@Inject
	private Provider<SaveBusinessTypes> saveBusinessTypesProvider;

	private IFolder targetFolder;
	private ResourceSet resourceSet;
	private IImportModelReport report;
	private IFolder businessTypesFolder;
	private Map<MdfDomain, List<ApplicationInfo>> domainModelsMap;

	public void init(IImportModelReport report, IFolder folder, IFolder businessTypesFolder,
			ResourceSet resourceSet, Map<MdfDomain, List<ApplicationInfo>> domainModelsMap) {
		this.report = report;
		this.targetFolder = folder;
		this.resourceSet = resourceSet;
		this.domainModelsMap = domainModelsMap;
		this.businessTypesFolder = businessTypesFolder;
	}

	private String getMessage() {
		return "Saving domains"; //$NON-NLS-1$
	}

	@Override
	public boolean execute(ApplicationInfo model, IProgressMonitor monitor) {
		boolean success = true;
		if (monitor != null) {
			monitor.subTask(getMessage());
		}
		if (domainModelsMap != null) {
			SaveBusinessTypes saveBusinessTypes = saveBusinessTypesProvider.get();
			saveBusinessTypes.setInput(report, businessTypesFolder, resourceSet);
			saveBusinessTypeDomain(saveBusinessTypes, monitor);
			for (MdfDomain domain : domainModelsMap.keySet()) {
				IFolder folder = targetFolder.getProject().getFolder(targetFolder.getProjectRelativePath());
				try {
					if (!FeatureSwitches.primitiveToBTReplaceForIrisDisabled.get()) {
						replacePrimitiveAttributeToBusinessType(domain, saveBusinessTypes);
					}
					final String name = domain.getName();
					if (name == null || name.trim().isEmpty()) {
						for (ApplicationInfo application : domainModelsMap.get(domain)) {
							report.error(application, "Found a weird domain with no name: " + domain.toString());
						}
						continue;
					}
					IFile file = folder.getFile(name + ".domain");
					if (monitor != null) {
						monitor.subTask("Saving " + file.getFullPath().toString());
					}
					URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
					Resource resource = resourceSet.getResource(uri, false);
					if (resource == null) {
						resource = resourceSet.createResource(uri);
					}
					resource.getContents().add((EObject) domain);
					resource.save(null);
				} catch (Exception ex) {
					for (ApplicationInfo application : domainModelsMap.get(domain)) {
						report.error(application, ex);
					}
					continue;
				}
			}
		}
		return success;
	}

	/**
	 * This method gets set of T24 business type names from domain classes. From
	 * this set of data we create and save BusinessTypes.domain
	 * 
	 * @param saveBusinessTypes
	 */
	private void saveBusinessTypeDomain(SaveBusinessTypes saveBusinessTypes, IProgressMonitor monitor) {
		Set<String> businessTypes = new HashSet<String>();
		if (monitor != null) {
			monitor.subTask("Saving BusinessTypes.domain");
		}
		for (MdfDomain domain : domainModelsMap.keySet()) {
			for (Object obj : domain.getClasses()) {
				if (obj instanceof MdfClass) {
					MdfClass mdfClass = (MdfClass) obj;
					for (Object propertyObj : mdfClass.getProperties(false)) {
						if (propertyObj instanceof MdfProperty) {
							MdfProperty property = (MdfProperty) propertyObj;
							businessTypes.add(T24Aspect.getBusinessType(property));
						}
					}
				}
			}
		}
		saveBusinessTypes.updateBusinessTypesDomain(businessTypes);
	}

	/**
	 * This method references class attributes of primitive type to values in
	 * BusinessTypes.domain and removes "businessType" annotation from the
	 * annotation property list.
	 * 
	 * @param domain
	 * @param saveBusinessTypes
	 */
	private void replacePrimitiveAttributeToBusinessType(MdfDomain domain, SaveBusinessTypes saveBusinessTypes) {
		MdfDomainImpl businessTypesDomain = saveBusinessTypes.getBusinessTypesDomain();
		if (businessTypesDomain != null) {
			for (Object obj : domain.getClasses()) {
				if (obj instanceof MdfClass) {
					MdfClass mdfClass = (MdfClass) obj;
					for (Object propertyObj : mdfClass.getProperties(false)) {
						if (propertyObj instanceof MdfAttribute) {
							MdfAttributeImpl attribute = (MdfAttributeImpl) propertyObj;
							if (attribute.getType() != null && attribute.getType() instanceof MdfPrimitive) {
								String businessType = ApplicationImportUtils.getBusinessTypeName(T24Aspect
										.getBusinessType(attribute));
								if (businessType != null) {
									attribute.setType(businessTypesDomain.getBusinessType(businessType));
									MdfAnnotation annotation = attribute.getAnnotation(
											T24Aspect.T24_ANNOTATIONS_NAMESPACE_URI, T24Aspect.ANNOTATION);
									if (annotation != null && annotation.getProperties() != null)
										annotation.getProperties().remove(
												annotation.getProperty(T24Aspect.BUSINESS_TYPE));
								}
							}
						}
					}
				}
			}
		}
	}

}
