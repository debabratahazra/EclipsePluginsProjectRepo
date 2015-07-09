
package com.odcgroup.t24.application.localref.importer;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.odcgroup.domain.annotations.JavaAspectDS;
import com.odcgroup.domain.annotations.MdfAnnotationsUtil;
import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfBusinessTypeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfEnumValueImpl;
import com.odcgroup.mdf.ecore.MdfEnumerationImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.MdfPropertyImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ecore.util.MdfNameURIUtil;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.mdf.model.translation.MdfTranslation;
import com.odcgroup.t24.application.internal.localref.LocalRef;
import com.odcgroup.t24.application.internal.localref.VettingTable;
import com.odcgroup.t24.applicationimport.ApplicationImportUtils;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.Translations;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.xtext.XtextResourceSetProviderDS;

/**
 * @author ssreekanth
 *
 */
public class SaveLocalRefs implements IImportingStep<LocalRefInfo> {

	private IImportModelReport report;
	private MdfDomainImpl enumDomain = null;
	private MdfDomainImpl businessTypesDomain = null;
	private MdfDomainImpl fieldsDomain = null;
	private LocalRefInfo model = null;
	private IFolder folder = null;
	
	private String getMessage() {
		StringBuffer b = new StringBuffer();
		b.append("Saving LocalFieldsDefinition domain"); //$NON-NLS-1$
		return b.toString();
	}
	
	@SuppressWarnings("unchecked")
	private void addDefaultBusinessTypes() {
		for(String bt : ApplicationImportUtils.BUSSINESS_TYPES) {
			MdfBusinessTypeImpl btImpl = (MdfBusinessTypeImpl)MdfFactory.eINSTANCE.createMdfBusinessType();
			btImpl.setName(bt);
			btImpl.setType(PrimitivesDomain.STRING);
			businessTypesDomain.getBusinessTypes().add(btImpl);
		}
	}

	private void saveDomain(MdfDomainImpl domain) {
		try {			
			Resource resource = domain.eResource();	
			if(resource != null) {
				resource.save(null);	
			}
		} catch (IOException e) {			
			report.error(model, e);
		}
	}
	

	private MdfDomainImpl createDomain(String domainName, String nameSpace, String packageName) {
		MdfDomainImpl domain = null;
		IFile file = null;
		try {
			domain = (MdfDomainImpl) MdfFactory.eINSTANCE.createMdfDomain();
			domain.setMetamodelVersion(OfsCore.getVersionNumber());
			domain.setName(domainName);
			domain.setNamespace("http://www.odcgroup.com/" + nameSpace);
			JavaAspectDS.setPackage(domain, LocalRefUtils.LOCAL_FIELD_PACKAGE_NAME);
			if(ApplicationImportUtils.BUSINESSTYPES_DOMAIN.equalsIgnoreCase(domainName)) {
				file = folder.getParent().getFile(
						new Path(ApplicationImportUtils.BUSINESS_TYPES_FOLDER
								+ ApplicationImportUtils.BUSINESSTYPES_FILE_NAME + ".domain"));
			} else {
				file = folder.getParent().getFile(new Path(LocalRefUtils.LOCAL_FIELD_PACKAGE + "/" + domainName + ".domain"));
			}
		
			XtextResourceSet rs = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, folder.getProject());
			URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			Resource resource = rs.createResource(uri);
			resource.getContents().add(domain);
			resource.save(null);
		} catch (IOException e) {			
			report.error(model, e);
		}
		return domain;
	}
	
	@SuppressWarnings("unchecked")
	private void updateEnumDomain(LocalRef ref) {
		EList<VettingTable> vetTable = ref.getVettingTable();
		MdfEnumerationImpl mdfEnum = (MdfEnumerationImpl)MdfFactory.eINSTANCE.createMdfEnumeration();
		String enumName = "LocalField_" + ref.getLocalRefID();
		if(enumName.contains(".")) {
			enumName = enumName.replace(".", "_");
		}
		mdfEnum.setName(enumName);
		
		if(ref.getCharType() != null && businessTypesDomain != null) {
			mdfEnum.setType(businessTypesDomain.getBusinessType(ref.getCharType()));
		} else {
			mdfEnum.setType(PrimitivesDomain.STRING);
		}
		
		for (VettingTable vettingTable : vetTable) {
			if(vettingTable != null) {
				EList<Translations> remarks = vettingTable.getRemarks();
				if(remarks !=null && remarks.size() > 0) {
					String name = vettingTable.getVettingTable();
					String value = name;

					MdfEnumValueImpl enumValue = (MdfEnumValueImpl)MdfFactory.eINSTANCE.createMdfEnumValue();
					enumValue.setName(name);
					enumValue.setValue(value);
					
					for(Translations remark : remarks) {						
						LocalTranslations localTrans = (LocalTranslations)remark;
						EList<LocalTranslation> translations = localTrans.getTranslations();
						for (LocalTranslation localTranslation : translations) {							
							MdfAnnotationsUtil.setAnnotationProperty(enumValue, MdfTranslation.NAMESPACE_URI, MdfTranslation.TRANSLATION_LABEL, localTranslation.getLocale(), localTranslation.getText(), false);
						}
					}
					
					if(enumDomain.getEnumeration(enumName) == null) {
						enumDomain.getEnumerations().add(mdfEnum);
					}
					mdfEnum.getValues().add(enumValue);
				}
			}
		}
		saveDomain(enumDomain);
	}
	
	@SuppressWarnings("unchecked")
	private void updateFieldsDomain(LocalRef ref) {
		MdfClassImpl localFieldsClass = (MdfClassImpl)fieldsDomain.getClass(LocalRefUtils.LOCAL_FIELDS_CLASS);
		if(ref.getApplicationVET() != null) {
			MdfAssociationImpl association = (MdfAssociationImpl)MdfFactory.eINSTANCE.createMdfAssociation();
			association.setName(ref.getLocalRefID());
			MdfClassImpl applicationVetClass = (MdfClassImpl)ref.getApplicationVET();
			applicationVetClass = replaceNameSchemeToReference(applicationVetClass);
			association.setType(applicationVetClass);
			T24Aspect.setApplicationEnrich(association,
					ApplicationImportUtils.replaceSpecialCharByUnderscores(ref.getApplicationEnrich()));
			updateAnnotations(association, ref);
			localFieldsClass.getProperties().add(association);
		} else {
			MdfAttributeImpl attribute = (MdfAttributeImpl)MdfFactory.eINSTANCE.createMdfAttribute();
			attribute.setName(ref.getLocalRefID());
			String enumName = "LocalField_" + ref.getLocalRefID().toString();
			if(enumName.contains(".")) {
				enumName = enumName.replace(".", "_");
			}
			MdfEnumeration enumVal = enumDomain.getEnumeration(enumName); 
			if(enumVal != null) {
				URI uri =MdfNameURIUtil.createURI(enumVal.getQualifiedName());
				((MinimalEObjectImpl)enumVal).eSetProxyURI(uri);
				attribute.setType(enumVal);
			}
			else {
				if(ref.getCharType() == null) {
					attribute.setType(businessTypesDomain.getBusinessType("NUMERIC"));
				}
				else {
					attribute.setType(businessTypesDomain.getBusinessType(ref.getCharType()));
				}
			}
			
			attribute.setRequired(ref.isMandatory());
			
			updateAnnotations(attribute, ref);
			
			localFieldsClass.getProperties().add(attribute);
			
		}
		saveDomain(fieldsDomain);
	}
	


	private MdfClassImpl replaceNameSchemeToReference(MdfClassImpl applicationVetClass) {
		URI uri =applicationVetClass.eProxyURI();
		if(uri.scheme().equals("name")){
			String refString = uri.path().substring(1);
			MdfName qname = MdfNameFactory.createMdfName(refString);
			URI mdfNameURI = MdfNameURIUtil.createURI(qname);
			applicationVetClass.eSetProxyURI(mdfNameURI);
		}
	  return applicationVetClass;
		
	}

	private void updateAnnotations(MdfPropertyImpl element, LocalRef ref) {
		String description = ref.getDescription();
		if(description != null) {
			element.setDocumentation(description);
		}
		
		EList<Translations> shortNames = ref.getShortName();
		for (Translations translations : shortNames) {
			LocalTranslations shortName = (LocalTranslations)(translations);
			if(shortName != null) {
				EList<LocalTranslation> trans = shortName.getTranslations();
				for (LocalTranslation localTranslation : trans) {
					MdfAnnotationsUtil.setAnnotationProperty(element, MdfTranslation.NAMESPACE_URI, MdfTranslation.TRANSLATION_LABEL, localTranslation.getLocale(), localTranslation.getText(), false);	
				}
			}	
		}		
		
		T24Aspect.setMaxChars(element, ref.getMaximumChars());
		T24Aspect.setMinChars(element, ref.getMinimumChars());
		T24Aspect.setOverridePossible(element, ref.isOverridePossible());
		T24Aspect.setDefaultPossible(element, ref.isDefaultPossible());
		T24Aspect.setNoInputChange(element, ref.getNoInputChange().toString());
		if(ref.getVirtualTable() != null) {
			T24Aspect.setVirtualTable(element, ref.getVirtualTable());
		}
	}
	@Override
	public boolean execute(LocalRefInfo model, IProgressMonitor monitor) {
		boolean success = true;
		this.model  = model;
		if (monitor != null) {
			monitor.subTask(getMessage());
		}
		LocalRef ref = (LocalRef) model.getModel();
		if(ref!=null){
			updateBusinessTypesDomain(ref.getCharType());
			updateEnumDomain(ref);
			updateFieldsDomain(ref);
		}
		return success;
	}
	
	@SuppressWarnings("unchecked")
	private void updateBusinessTypesDomain(String charType) {
		MdfBusinessTypeImpl btImpl = (MdfBusinessTypeImpl)MdfFactory.eINSTANCE.createMdfBusinessType();
		String busnessType = ApplicationImportUtils.getBusinessTypeName(charType);
		if(StringUtils.isBlank(busnessType)) {
			if(businessTypesDomain.getBusinessType("NUMERIC") == null) {
				btImpl.setName("NUMERIC");
				btImpl.setType(PrimitivesDomain.STRING);
				businessTypesDomain.getBusinessTypes().add(btImpl);
				saveDomain(businessTypesDomain);
			}
		}
		else if(businessTypesDomain.getBusinessType(busnessType) == null) {			
			btImpl.setName(busnessType);
			btImpl.setType(PrimitivesDomain.STRING);
			T24Aspect.setT24Name(btImpl, charType);
			businessTypesDomain.getBusinessTypes().add(btImpl);
			saveDomain(businessTypesDomain);	
		}
	}

	public void setInput(IImportModelReport report, IFolder folder) {
		this.report = report;
		this.folder = folder;
		updateDomainsFromRepo();
		clearExistingDomains();
	}
	
	@SuppressWarnings("unchecked")
	private void updateDomainsFromRepo() {
		IProject project = folder.getProject();
		businessTypesDomain = (MdfDomainImpl) DomainRepositoryUtil.getMdfDomain(
				QualifiedName.create(ApplicationImportUtils.BUSINESSTYPES_DOMAIN), project);
		if (businessTypesDomain == null) {
			businessTypesDomain = createDomain(ApplicationImportUtils.BUSINESSTYPES_DOMAIN,
					ApplicationImportUtils.BUSINESSTYPES_DOMAINS_NAMESPACE,	ApplicationImportUtils.BUSINESSTYPES_PACKAGE);
			addDefaultBusinessTypes();
			T24Aspect.setBusinessTypes(businessTypesDomain, true);
			saveDomain(businessTypesDomain);
		}

		enumDomain = (MdfDomainImpl) DomainRepositoryUtil.getMdfDomain(QualifiedName.create(LocalRefUtils.LOCAL_FIELD_ENUMERATIONS_DOMAIN), project);
		if (enumDomain == null) {
			createFolder();
			enumDomain = createDomain(LocalRefUtils.LOCAL_FIELD_ENUMERATIONS_DOMAIN,
					LocalRefUtils.LOCAL_FIELD_ENUMERATIONS_DOMAINS_NAMESPACE, LocalRefUtils.LOCAL_FIELD_PACKAGE);
			T24Aspect.setLocalFields(enumDomain, true);
			saveDomain(enumDomain);
		}

		fieldsDomain = (MdfDomainImpl) DomainRepositoryUtil.getMdfDomain(QualifiedName.create(LocalRefUtils.LOCAL_FIELD_LOCALFIELDSDEFINITION_DOMAIN), project);
		if (fieldsDomain == null) {
			createFolder();
			fieldsDomain = createDomain(LocalRefUtils.LOCAL_FIELD_LOCALFIELDSDEFINITION_DOMAIN,
					LocalRefUtils.LOCAL_FIELD_LOCALFIELDSDEFINITION_NAMESPACE, LocalRefUtils.LOCAL_FIELD_PACKAGE);
			MdfClassImpl localFieldsClass = (MdfClassImpl) MdfFactory.eINSTANCE.createMdfClass();
			localFieldsClass.setName(LocalRefUtils.LOCAL_FIELDS_CLASS);
			fieldsDomain.getClasses().add(localFieldsClass);
			T24Aspect.setLocalFields(fieldsDomain, true);
			saveDomain(fieldsDomain);
		}
	}
	
	private void createFolder() {
		if(!folder.exists()) {
			try {
				folder.create(true, true, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void clearExistingDomains() {
		if(fieldsDomain != null) {
			MdfClass klass = fieldsDomain.getClass(LocalRefUtils.LOCAL_FIELDS_CLASS);
			if(klass != null) {
				fieldsDomain.getClasses().remove(klass);
				klass.getProperties().clear();
				fieldsDomain.getClasses().add((MdfClassImpl)klass);
				saveDomain(fieldsDomain);
			}
		}
		
		if(enumDomain != null) {
			enumDomain.getEnumerations().clear();
			saveDomain(enumDomain);
		}
		
		try {
			folder.getProject().refreshLocal(IProject.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
}
