package com.odcgroup.t24.aa.product.importer.internal;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.odcgroup.domain.annotations.JavaAspectDS;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.util.MdfEcoreUtil;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.server.external.model.internal.AAProductsLoader;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.xtext.XtextResourceSetProviderDS;

/**
 * TODO: Document me!
 * 
 * @author hdebabrata
 * 
 */
public class SaveAAProducts implements IImportingStep<AAProductsInfo> {

	private IImportModelReport report;
	private AAProductsInfo model = null;
	private MdfDomainImpl productLineDomain = null;
	private IFolder descFolder = null;

	/**
	 * Create a new .domain file w.r.t. XML file
	 * 
	 * @param domainName
	 * @param namespace
	 * @return
	 */
	private MdfDomainImpl createDomain(String domainName, String namespace) {
		MdfDomainImpl domain = null;
		try {
			domain = (MdfDomainImpl) MdfFactory.eINSTANCE.createMdfDomain();
			domain.setMetamodelVersion(OfsCore.getVersionNumber());
			domain.setName(domainName);
			domain.setNamespace("http://www.odcgroup.com/" + namespace);
			JavaAspectDS.setPackage(domain, AAProductsImportOperation.DESTINATION_FOLDER);
			XtextResourceSet rs = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, descFolder.getProject());
			IFile file = descFolder.getFile("/" + AAProductsImportOperation.DOMAIN_FILENAME);
			Resource resource = rs.createResource(URI.createPlatformResourceURI(file.getFullPath().toString(), true));			
			resource.getContents().add(domain);
			resource.save(null);
		} catch (IOException e) {
			report.error(model, e);
		}
		return domain;
	}

	private String getMessage() {
		StringBuffer b = new StringBuffer();
		b.append("Saving...");
		return b.toString();
	}

	public void setInput(IImportModelReport report, IFolder folder) {
		this.report = report;
		if (!folder.exists()) {
			try {
				folder.create(true, true, null);
			} catch (CoreException e) {
			}
		}
		this.descFolder = folder;
		clearExistingDomains();
	}

	@Override
	public boolean execute(AAProductsInfo model, IProgressMonitor monitor) {

		boolean success = false;
		this.model = model;
		if (monitor != null) {
			monitor.subTask(getMessage());
		}
		success = update();
		return success;
	}

	@SuppressWarnings("unchecked")
	private boolean update() {
		if(productLineDomain == null) {
			productLineDomain = createDomain("ProductLine", "product-line");
		}
		String className = model.getModelName();
		if (StringUtils.isNotBlank(className) && productLineDomain.getClass(className) == null) {
			MdfClassImpl productLineClass = (MdfClassImpl) MdfFactory.eINSTANCE.createMdfClass();
			productLineClass.setName(className);

			String data = model.getXmlString();
			StringTokenizer strAppComp = new StringTokenizer(data, AAProductsLoader.getSeparator());
			String appList = (String) strAppComp.nextElement();
			String compList = (String) strAppComp.nextElement();
			StringTokenizer strTokenApplicationList = new StringTokenizer(appList, "#");
			StringTokenizer strTokenComponentList = new StringTokenizer(compList, "#");
			while (strTokenApplicationList.hasMoreElements()) {
				String applicationName = ((String) strTokenApplicationList.nextElement()).replaceAll("\\.", "_");
				String componentName = ((String) strTokenComponentList.nextElement()).replaceAll("\\.", "_");

				MdfAssociation mdfAssociation = MdfFactory.eINSTANCE.createMdfAssociation();
				MdfAssociationImpl mdfAssociationImpl = ((MdfAssociationImpl) mdfAssociation);
				mdfAssociationImpl.setMultiplicity(MdfMultiplicity.MANY);
				mdfAssociationImpl.setContainment(MdfContainment.BY_VALUE);

				MdfClassImpl mdfClassProxy = MdfEcoreUtil.getMdfClassProxy(MdfNameFactory.createMdfName(componentName
						+ ":" + applicationName));
				mdfAssociationImpl.setType(mdfClassProxy);
				mdfAssociationImpl.setName(applicationName);
				mdfAssociationImpl.getType();
				productLineClass.getProperties().add(mdfAssociationImpl);
			}
			productLineDomain.getClasses().add(productLineClass);
			T24Aspect.setProductLines(productLineDomain, true);
			saveDomain(productLineDomain);
		}
		return true;
	}

	
	/**
	 * Save *.domain file.
	 * @param domain
	 */
	private void saveDomain(MdfDomainImpl domain) {
		try {
			Resource resource = domain.eResource();
			if (resource != null) {
				resource.save(null);
			}
		} catch (IOException e) {
			report.error(model, e);
		}
	}

	/**
	 * Remove *.domain file before import.
	 */
	private void clearExistingDomains() {
		try {
			descFolder.getProject().refreshLocal(IProject.DEPTH_INFINITE, null);
			IFile iFile = descFolder.getFile(AAProductsImportOperation.DOMAIN_FILENAME);
			if(iFile.exists()) {
				iFile.delete(true, null);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
}
