package com.odcgroup.mdf.generation.tests;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.mdf.ecore.annotation.EcorePlusAspect;
import com.odcgroup.mdf.generation.EcoreGenerator;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class EcoreGeneratorTestCase extends AbstractDSUnitTest {
	
	private static final String OUTPUT_FOLDER = "output";
	private static final String DS4315_MODEL_FILE = "ds4315/DS4315Domain.domain";
	private static final String DS5128_MODEL_FILE = "ds5128/DS5128Domain.domain";
	
	protected ResourceSet rs = new ResourceSetImpl();

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject(DOMAIN_LOCATION + "/" + DS4315_MODEL_FILE);
		copyResourceInModelsProject(DOMAIN_LOCATION + "/" + DS5128_MODEL_FILE);
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	/**
	 * DS-4315
	 * @throws ModelNotFoundException
	 * @throws CoreException
	 */
	@Test
	public void testEcoreGenerator_DS4315() throws ModelNotFoundException, CoreException, IOException {
		
		IOfsProject ofsProject = getOfsProject();
		EcoreGenerator generator = getGeneratorInstance();
		IOfsModelResource mres = getModelResource(ofsProject, DS4315_MODEL_FILE);
		assertNotNull(mres);
		Set<IOfsModelResource> resources = Collections.singleton(mres);
		IFolder outputFolder = ofsProject.getProject().getFolder(OUTPUT_FOLDER);
		outputFolder.create(true, true, null);
		List<IStatus> nonOkStatuses = new ArrayList<IStatus>();
		generator.doRun(getProject(), outputFolder, resources, nonOkStatuses);
		getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
		IFile generatedFile = outputFolder.getFile("META-INF/models/DS4315Domain." + generator.getResourceFileExtension());
		Assert.assertTrue(generatedFile.exists());
		
		EPackage ecoreModel = loadModel(generatedFile);
		
		Assert.assertNotNull(ecoreModel);
		Assert.assertEquals(3, ecoreModel.getEClassifiers().size());

		EClassifier myEnum = ecoreModel.getEClassifier("MyEnumeration");
		Assert.assertNotNull(myEnum);
		Assert.assertEquals("EString", EcorePlusAspect.EnumBaseType.get((EEnum) myEnum));

		EClass myDataset = (EClass) ecoreModel.getEClassifier("MyDataset");
		Assert.assertNotNull(myDataset);
		Assert.assertEquals(3, myDataset.getEAttributes().size());
		
		EAttribute eAttr = (EAttribute) myDataset.getEStructuralFeature("enumAttribute");
		Assert.assertEquals("MyEnumeration", eAttr.getEType().getName());

		eAttr = (EAttribute) myDataset.getEStructuralFeature("btAttribute");
		Assert.assertEquals("MyBusinessType", eAttr.getEType().getName());
		Assert.assertTrue(EcorePlusAspect.BusinessKey.get(eAttr));

		eAttr = (EAttribute) myDataset.getEStructuralFeature("stringAttribute");
		Assert.assertEquals("EString", eAttr.getEType().getName());
		Assert.assertTrue(eAttr.isID());
		
		EReference eRef = (EReference) myDataset.getEStructuralFeature("classAssociation");
		Assert.assertEquals("MyDataset", eRef.getEType().getName());

	}

	/**
	 * DS-5128
	 * @throws ModelNotFoundException
	 * @throws CoreException
	 */
	@Test
	public void testEnumTypes_DS5128() throws ModelNotFoundException, CoreException, IOException {
		
		IOfsProject ofsProject = getOfsProject();
		EcoreGenerator generator = getGeneratorInstance();
		IOfsModelResource mres = getModelResource(ofsProject, DS5128_MODEL_FILE);
		assertNotNull(mres);
		Set<IOfsModelResource> resources = Collections.singleton(mres);
		IFolder outputFolder = ofsProject.getProject().getFolder(OUTPUT_FOLDER);
		outputFolder.create(true, true, null);
		List<IStatus> nonOkStatuses = new ArrayList<IStatus>();
		generator.doRun(getProject(), outputFolder, resources, nonOkStatuses);
		getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
		
		IFile generatedFile = outputFolder.getFile("META-INF/models/DS5128Domain." + generator.getResourceFileExtension());
		Assert.assertTrue(generatedFile.exists());		
	}
	
	protected EcoreGenerator getGeneratorInstance() {
		return new EcoreGenerator();
	}

	private EPackage loadModel(URI uri) {
		Resource resource = rs.getResource(uri, true);
		if(resource!=null && resource.getContents().size()>0) {
			return (EPackage) resource.getContents().get(0);
		}
		throw new RuntimeException("Could not load model " + uri.toString());
	}
	
	private EPackage loadModel(IFile file) {
		URI uri = URI.createFileURI(file.getFullPath().toString());
		return loadModel(uri);
	}

}
