package com.odcgroup.t24.application.localrefapplication.importer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.odcgroup.domain.annotations.JavaAspectDS;
import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.MdfPropertyImpl;
import com.odcgroup.mdf.ecore.util.MdfNameURIUtil;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.application.localref.importer.LocalRefUtils;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.localReferenceApplication.LocalField;
import com.odcgroup.t24.localReferenceApplication.LocalReferenceApplication;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.xtext.XtextResourceSetProviderDS;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public class SaveLocalRefApplication implements IImportingStep<LocalRefApplicationInfo> {
	
	private IImportModelReport report;
	private LocalRefApplicationInfo model = null;
	private MdfDomainImpl localRefApplicationDomain = null;
	private MdfDomainImpl fieldsDomain = null;
	private IFolder folder = null;

	@Override
	public boolean execute(LocalRefApplicationInfo model,
			IProgressMonitor monitor) {
		boolean success = false;
		this.model  = model;
		if (monitor != null) {
			monitor.subTask(getMessage());
		}
		
		LocalReferenceApplication localReferenceApplication = (LocalReferenceApplication) model.getModel();
		if(localReferenceApplication!=null){
			success = update(localReferenceApplication);
		}
		
		return success;
	}
	
	/**
	 * @param model2 
	 * @param localReferenceApplication
	 */
	@SuppressWarnings("unchecked")
	private boolean update(LocalReferenceApplication localRefApplication) {
		if (fieldsDomain == null) {
			fieldsDomain = (MdfDomainImpl) DomainRepositoryUtil.getMdfDomain(
					QualifiedName.create(LocalRefUtils.LOCAL_FIELD_LOCALFIELDSDEFINITION_DOMAIN), folder.getProject());
		}
		if (fieldsDomain == null) {
			// LocalFieldsDefinition.domain file not found in current project.
			report.error(model, "LocalFieldsDefinition.domain file not found.");
			return false; 
		}

		// Create Class in X_*.domain file.
		MdfClassImpl localRefClass = (MdfClassImpl) MdfFactory.eINSTANCE.createMdfClass();

		// Set Base Class
		localRefClass.setName(model.getXMLFilename());
		MdfClass mdfBaseClass = localRefApplication.getForApplication();
		URI mdfNameURI = MdfNameURIUtil.createURI(mdfBaseClass.getQualifiedName());
		((InternalEObject) mdfBaseClass).eSetProxyURI(mdfNameURI);
		localRefClass.setBaseClass(mdfBaseClass);

		Map<String, List<String>> grCollection = new HashMap<String, List<String>>(); // GroupName - LocalRefID

		MdfClassImpl localFieldsClass = (MdfClassImpl) fieldsDomain.getClass(LocalRefUtils.LOCAL_FIELDS_CLASS);

		EObjectContainmentEList<LocalField> localFieldList = (EObjectContainmentEList<LocalField>) localRefApplication
				.getLocalField();
		Map<String, Integer> index = new HashMap<String, Integer>(localFieldList.size()); // LocalRefID - Position
		int position = -1;
		for (Iterator<LocalField> iterator = localFieldList.iterator(); iterator.hasNext();) {

			LocalField localField = (LocalField) iterator.next();
			MdfProperty property = localFieldsClass.getProperty(localField.getLocalRefID());
			if(property == null){
				//Property not found from "LocalFieldsDefinition.domain" file. Check it.
				report.error(model, "Attribute - " + localField.getLocalRefID() +" is not found in LocalFieldsDefinition.domain file.");
				continue;
			}
			EObject obj  = EcoreUtil.copy((EObject)property);
			if (localField.getGroupName().equals("No")) {
				// Add elements direct to the subclass.
				localRefClass.getProperties().add(obj);
			} else {
				// Create separate Class/Group
				if (grCollection.containsKey(localField.getGroupName())) {
					ArrayList<String> value = (ArrayList<String>) grCollection.get(localField.getGroupName());
					value.add(localField.getLocalRefID());
					grCollection.put(localField.getGroupName(), value);
				} else {
					ArrayList<String> id = new ArrayList<String>();
					id.add(localField.getLocalRefID());
					grCollection.put(localField.getGroupName(), id);
				}
			}
			index.put(localField.getLocalRefID(), ++position);
		}
		
		if(report.hasErrors()){
			return false;
		}
		
		localRefApplicationDomain = createDomain(model.getXMLFilename(), model.getXMLFilename());

		// Adding elements/properties for each Group/Class
		for (Map.Entry<String, List<String>> entry : grCollection.entrySet()) {

			MdfClassImpl localRefClassGr = (MdfClassImpl) MdfFactory.eINSTANCE.createMdfClass();
			localRefClassGr.setName("Group_" + entry.getKey().toString().replace(".", "_"));
			
			// Adding association to main class.
			MdfAssociation mdfAssociation = MdfFactory.eINSTANCE.createMdfAssociation();
            MdfAssociationImpl mdfAssociationImpl = ((MdfAssociationImpl) mdfAssociation);
            mdfAssociationImpl.setMultiplicity(MdfMultiplicity.MANY);
            mdfAssociationImpl.setContainment(MdfContainment.BY_VALUE);
            mdfAssociationImpl.setType(localRefClassGr);
            mdfAssociationImpl.setName("GroupSample_" + entry.getKey().toString().replace(".", "_"));
            localRefClass.getProperties().add(index.get(entry.getKey().toString()), mdfAssociationImpl);

			List<String> localredIDs = entry.getValue();
			for (Iterator<String> iterator = localredIDs.iterator(); iterator.hasNext();) {
				String localRefID = (String) iterator.next();
				MdfProperty prop = localFieldsClass.getProperty(localRefID);
				if (prop != null) {
					EObject eObj = EcoreUtil.copy((EObject) prop);
					localRefClassGr.getProperties().add(eObj);
				}
			}
			setT24NameForProperties(localRefClassGr);
			localRefApplicationDomain.getClasses().add(localRefClassGr);
			mdfAssociation = null;
			localRefClassGr = null;
		}

		setT24NameForProperties(localRefClass);
		localRefApplicationDomain.getClasses().add(localRefClass);
		T24Aspect.setLocalRefApplications(localRefApplicationDomain, true);
		saveDomain(localRefApplicationDomain);

		return true;
	}

	/**
	 * @param localRefClass
	 */
	private void setT24NameForProperties(MdfClassImpl localRefClass) {
		for(Object object : localRefClass.getProperties()){
			if(object instanceof MdfProperty) {
				MdfPropertyImpl property = (MdfPropertyImpl) object;
				T24Aspect.setT24Name(property, property.getName());
				String attrName = property.getName().replace(".", "_");
				property.setName(attrName);
			}
		}
	}
	
	/**
	 * Save *.domain file.
	 * @param domain
	 */
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
	
	/**
	 * Create a new .domain file w.r.t. XML file
	 * @param domainName
	 * @param namespace
	 * @return
	 */
	private MdfDomainImpl createDomain(String domainName, String namespace) {
		MdfDomainImpl domain = null;
		try {
			domain = (MdfDomainImpl) MdfFactory.eINSTANCE.createMdfDomain();
			domain.setMetamodelVersion(OfsCore.getCurrentMetaModelVersion("domain"));
			domain.setName(domainName);
			domain.setNamespace("http://www.odcgroup.com/" + namespace);
			JavaAspectDS.setPackage(domain, LocalRefUtils.LOCAL_REF_APPLICATION_PACKAGE + "." + domainName.toLowerCase());
			XtextResourceSet rs = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, folder.getProject());
			IFile file = folder.getFile("/" + domainName + ".domain");
			URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			Resource resource = rs.createResource(uri);
			resource.getContents().add(domain);
			resource.save(null);
		} catch (IOException e) {			
			report.error(model, e);
		}
		return domain;
	}
	
	private String getMessage() {
		StringBuffer b = new StringBuffer();
		b.append("Saving LocalRefApplication domain"); //$NON-NLS-1$
		return b.toString();
	}

	public void setInput(IImportModelReport report, IFolder folder) {
		this.report = report;
		if(!folder.exists()) {
			try {
				folder.create(true, true, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		this.folder = folder;
	}
}
