package com.odcgroup.mdf.migration.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.parsetree.reconstr.XtextSerializationException;
import org.eclipse.xtext.resource.SaveOptions;

import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelResourceSet;
import com.odcgroup.workbench.core.resources.OfsModelResource;
import com.odcgroup.workbench.migration.AbstractModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

public class XMI2DSLMigration extends AbstractModelMigration {

	public XMI2DSLMigration() {
	}

	@Override
	public void migrate(IOfsProject ofsProject,
			IOfsModelResource modelResource, IProgressMonitor monitor)
			throws MigrationException {
		internalMigrate(modelResource, monitor);
	}

	@Override
	public InputStream migrate(IOfsProject ofsProject, InputStream is,
			URI modelURI, IProgressMonitor monitor) throws MigrationException {
		try {
			IFile file = (IFile) ofsProject.getOfsModelResource(modelURI).getResource();
			file.setContents(is, IFile.FORCE|IFile.KEEP_HISTORY, monitor);
			IOfsModelResource modelResource = ofsProject.getOfsModelResource(modelURI);
			internalMigrate(modelResource, monitor);
		} catch (CoreException e) {
			throw new MigrationException("Error while converting file '" + modelURI.lastSegment() + "'", e);
		} catch (ModelNotFoundException e) {
			throw new MigrationException("Error while converting file '" + modelURI.lastSegment() + "'", e);
		}
		return null;
	}

	
	private void internalMigrate(IOfsModelResource modelResource, IProgressMonitor monitor) throws MigrationException {
		URI dslURI = modelResource.getURI().trimFileExtension().appendFileExtension("domain");
		try {
			
			java.net.URI uri = modelResource.getResource().getLocation().removeFileExtension().addFileExtension("domain").toFile().toURI();
			IFile dslFile = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(uri)[0];
			
			ModelResourceSet resourceSet = modelResource.getOfsProject().getModelResourceSet();

			// make sure that all domains are already resolved
			DomainRepository.getInstance(resourceSet).getDomains();

			resourceSet.createOfsModelResource(dslFile, IOfsProject.SCOPE_PROJECT);
			Resource dslResource = resourceSet.getResource(dslURI, false);
			while(dslResource.getResourceSet()==null) {
				dslResource = resourceSet.getResource(dslURI, false);
			}
			((OfsModelResource)modelResource).setMigrationCheckActivated(false);
			processReverseAssociations((MdfDomainImpl) modelResource.getEMFModel().get(0));
			MdfDomainImpl domain = (MdfDomainImpl) EcoreUtil.copy(modelResource.getEMFModel().get(0));
			domain.setMetamodelVersion("4.0.0.20110117");
			dslResource.getContents().add(domain);
			monitor.subTask("Saving file '" + dslURI.lastSegment() + "'");
			Map<Object, Object> options = SaveOptions.newBuilder().noValidation().getOptions().toOptionsMap();
			dslResource.save(options);
			resourceSet.removeResource(modelResource.getURI(), IOfsProject.SCOPE_PROJECT);
			modelResource.getResource().delete(true, monitor);
		} catch (IOException e) {
			throw new MigrationException("Error while converting file '" + modelResource.getName() + "'", e);
		} catch(XtextSerializationException e) {
			throw new MigrationException("Error while converting file '" + modelResource.getName() + "'", e);
		} catch (InvalidMetamodelVersionException e) {
			throw new MigrationException("Error while converting file '" + modelResource.getName() + "'", e);
		} catch (CoreException e) {
			throw new MigrationException("Error while deleting file '" + modelResource.getName() + "'", e);
		} finally {
			((OfsModelResource)modelResource).setMigrationCheckActivated(true);
		}
	}
	
	private void processReverseAssociations(MdfDomainImpl domain) {
		Map<MdfReverseAssociation, MdfClass> toDelete = new HashMap<MdfReverseAssociation, MdfClass>();
		TreeIterator<EObject> contents = domain.eAllContents();
		while(contents.hasNext()) {
			EObject eObj = contents.next();
			if(eObj instanceof MdfReverseAssociation) {
				MdfReverseAssociation revAssoc = (MdfReverseAssociation) eObj;
				EObject container = ((EObject)revAssoc).eContainer();
				if(container instanceof MdfClass) {
					MdfClass parentClass = (MdfClass) container;
					toDelete.put(revAssoc, parentClass);
				}
			}
		}
		for(Entry<MdfReverseAssociation, MdfClass> entry : toDelete.entrySet()) {
			entry.getValue().getProperties().remove(entry.getKey());
		}
	}

}
