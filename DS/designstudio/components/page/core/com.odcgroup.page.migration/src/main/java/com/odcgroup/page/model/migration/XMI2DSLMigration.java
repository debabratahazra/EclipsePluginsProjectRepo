package com.odcgroup.page.model.migration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.parsetree.reconstr.XtextSerializationException;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.XtextResourceFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.odcgroup.page.model.Model;
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
			modelResource.unload();
			internalMigrate(modelResource, monitor);
		} catch (CoreException e) {
			throw new MigrationException("Error while converting file '" + modelURI.lastSegment() + "'", e);
		} catch (ModelNotFoundException e) {
			throw new MigrationException("Error while converting file '" + modelURI.lastSegment() + "'", e);
		}
		return null;
	}

	
	private void internalMigrate(IOfsModelResource modelResource, IProgressMonitor monitor) throws MigrationException {
		try {
			ModelResourceSet resourceSet = modelResource.getOfsProject().getModelResourceSet();

			((OfsModelResource)modelResource).setMigrationCheckActivated(false);

			IFile dslFile = (IFile) modelResource.getResource();
			EObject clonedModel = EcoreUtil.copy(modelResource.getEMFModel().get(0));
			((Model)clonedModel).setMetamodelVersion("4.0.0.20110117");
			URI dslURI = modelResource.getURI();
			
			Injector injector = Guice.createInjector(new com.odcgroup.page.PageRuntimeModule());
			XtextResourceFactory resourceFactory = injector.getInstance(XtextResourceFactory.class);
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("fragment", resourceFactory);
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("module", resourceFactory);
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("page", resourceFactory);

			// now replace the old resource with the new XtextResource in the resource set
			resourceSet.removeResource(dslURI, IOfsProject.SCOPE_PROJECT);
			resourceSet.createOfsModelResource(dslURI, dslFile, IOfsProject.SCOPE_PROJECT);
			Resource dslResource = resourceSet.getResource(dslURI, false);
			dslResource.getContents().add(clonedModel);
			
			monitor.subTask("Saving file '" + dslURI.lastSegment() + "'");
			Map<Object, Object> options = SaveOptions.newBuilder().noValidation().getOptions().toOptionsMap();
			dslResource.save(options);
			dslResource.unload();
		} catch(XtextSerializationException e) {
			throw new MigrationException("Error while converting file '" + modelResource.getName() + "'", e);
		} catch (IOException e) {
			throw new MigrationException("Error while converting file '" + modelResource.getName() + "'", e);
		} catch (InvalidMetamodelVersionException e) {
			throw new MigrationException("Error while converting file '" + modelResource.getName() + "'", e);
		} finally {
			((OfsModelResource)modelResource).setMigrationCheckActivated(true);
		}
	}

}
