package com.odcgroup.domain.resource;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.resource.XtextResourceFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfEnumValueImpl;
import com.odcgroup.mdf.ecore.MdfEnumerationImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelResourceSet;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.ui.tests.util.AbstractDSUIUnitTest;

public class DomainEscapeSplCharTest extends AbstractDSUIUnitTest {

	IFile file;
	Resource resource = null;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		createModelsProject();
		file = getProject().getFile("domain/ds5236/DS-5236.domain");
	
		MdfDomainImpl domain = (MdfDomainImpl) MdfFactory.eINSTANCE.createMdfDomain();
		domain.setMetamodelVersion(OfsCore.getVersionNumber());
		domain.setName("DomainEscapeSplCharTest"); // needed because else name-based hashCode() fails, see issue DS-5608 for upcoming proper solution
		
		MdfEnumerationImpl enumImpl = (MdfEnumerationImpl) MdfFactory.eINSTANCE.createMdfEnumeration();
		MdfEnumValueImpl enumVal =  (MdfEnumValueImpl)MdfFactory.eINSTANCE.createMdfEnumValue();
		enumVal.setValue("%");
		
		enumImpl.getValues().add(enumVal);
		
		ModelResourceSet resourceSet = getOfsProject().getModelResourceSet();
		Injector injector = Guice.createInjector(new com.odcgroup.domain.DomainRuntimeModule());
		XtextResourceFactory resourceFactory = injector.getInstance(XtextResourceFactory.class);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("domain", resourceFactory);
	
		resourceSet.createOfsModelResource(file, IOfsProject.SCOPE_PROJECT);
		resource = resourceSet.getResource(ModelURIConverter.createModelURI((IResource)file), false);
		resource.getContents().add(domain);
		resource.save(null);
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@Test
	public void testEnumVal() throws PartInitException {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(file), 
				"com.odcgroup.domain.Domain");
	}

}
