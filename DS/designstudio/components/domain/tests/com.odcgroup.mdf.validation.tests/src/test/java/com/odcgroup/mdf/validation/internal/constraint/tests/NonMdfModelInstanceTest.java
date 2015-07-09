package com.odcgroup.mdf.validation.internal.constraint.tests;

import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelResourceSet;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class NonMdfModelInstanceTest  extends AbstractDSUnitTest {

	IFile file;
	Resource resource = null;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		createModelsProject();
		file = getProject().getFile("domain/test/DomainXtextResourceTest.domain");

		MdfDomainImpl domain = (MdfDomainImpl) MdfFactory.eINSTANCE.createMdfDomain();
		domain.setMetamodelVersion(OfsCore.getVersionNumber());
		MdfClass clazz = MdfFactory.eINSTANCE.createMdfClass();
		MdfAttributeImpl attr1 = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute();
		attr1.setName("a_attribute");
		MdfAttributeImpl attr2 = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute();
		attr2.setName("C_attribute");
		MdfAttributeImpl attr3 = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute();
		attr3.setName("e_attribute");
		domain.setName("NonMdfModelInstanceTest"); // needed because else name-based hashCode() fails, see issue DS-5608 for upcoming proper solution
		
		clazz.getProperties().addAll(Arrays.asList(new MdfProperty[] { attr1, attr2}));
		domain.getClasses().add(clazz);
		
		ModelResourceSet resourceSet = getOfsProject().getModelResourceSet();
		Resource resource = getOfsProject().getModelResourceSet().getResource(ModelURIConverter.createModelURI((IResource)file), false);

		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("domain", resource);

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
	public void testCollapseAll(){
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(file), 
					"com.odcgroup.domain.Domain");
			IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			editor.getEditorSite().getSelectionProvider().setSelection(new StructuredSelection("WorkItOut"));
			Assert.assertTrue("No Exception Encountered", (editor.getEditorSite().getSelectionProvider().getSelection()!=null));
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(editor, true);
		} catch (PartInitException e) {
			Assert.assertFalse("An Exception Encountered", (e!=null));
		}
	}

}
