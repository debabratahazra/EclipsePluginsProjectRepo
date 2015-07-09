package com.odcgroup.pageflow.validation.tests.properties;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.validation.internal.PageflowPropertiesConflictDetector;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class PageflowAmbiguousPropertiesTest extends  AbstractDSUnitTest  { 
	
	private static final String PAGEFLOW_MODEL_A = "ds5402/PageflowA.pageflow";
	private static final String PAGEFLOW_MODEL_B1 = "ds5402/PageflowB1.pageflow";
	private static final String PAGEFLOW_MODEL_B2 = "ds5402/PageflowB2.pageflow";
	private static final String PAGEFLOW_MODEL_C1 = "ds5402/PageflowC1.pageflow";
	private static final String PAGEFLOW_MODEL_C2 = "ds5402/PageflowC2.pageflow";
	
	private static final String PAGEFLOW_MODEL_D = "ds5402/PageflowD.pageflow";
	private static final String PAGEFLOW_MODEL_D1 = "ds5402/PageflowD1.pageflow";
	private static final String PAGEFLOW_MODEL_D2 = "ds5402/PageflowD2.pageflow";
	
	private static final String PAGEFLOW_MODEL_E = "ds5402/PageflowE.pageflow";
	private static final String PAGEFLOW_MODEL_E1 = "ds5402/PageflowE1.pageflow";
	private static final String PAGEFLOW_MODEL_E2 = "ds5402/PageflowE2.pageflow";
	
	private static final String PAGEFLOW_MODEL_F = "ds5402/PageflowF.pageflow";
	private static final String PAGEFLOW_MODEL_F1 = "ds5402/PageflowF1.pageflow";
	private static final String PAGEFLOW_MODEL_F2 = "ds5402/PageflowF2.pageflow";


    @Before
    public void setUp() throws CoreException {
    	createModelsProject();
    }

    @After
    public void tearDown() throws CoreException {
    	deleteModelsProjects();
    }
    
    @Test
    public void testPageflowPropertiesWithConflictsInMultipleNestedPageflows() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException {
    	// load required models
		copyResourceInModelsProject(PAGEFLOWS_LOCATION+"/"+PAGEFLOW_MODEL_A);
		copyResourceInModelsProject(PAGEFLOWS_LOCATION+"/"+PAGEFLOW_MODEL_B1);
		copyResourceInModelsProject(PAGEFLOWS_LOCATION+"/"+PAGEFLOW_MODEL_B2);
		copyResourceInModelsProject(PAGEFLOWS_LOCATION+"/"+PAGEFLOW_MODEL_C1);
		copyResourceInModelsProject(PAGEFLOWS_LOCATION+"/"+PAGEFLOW_MODEL_C2);

		// retrieve pageflowA
		URI modelUri = URI.createURI("resource:///"+PAGEFLOW_MODEL_A);
		IOfsModelResource resource = getOfsProject().getOfsModelResource(modelUri);
		Pageflow pageflow = (Pageflow)resource.getEMFModel().iterator().next();

		// perform the validation
		PageflowPropertiesConflictDetector detector = new PageflowPropertiesConflictDetector(pageflow);
		Assert.assertTrue(detector.hasConflicts());

		StringBuilder expectedErrorMessage = new StringBuilder();
		//expectedErrorMessage.append("Pageflow model name<<PageflowA>> contains ambiguous declaration of properties in some nested pageflows:\n");
		expectedErrorMessage.append("The property [A] is declared with different values in nested pageflows:");
		expectedErrorMessage.append("\n\tValue=[2] in subpageflow [PageflowA/PageflowB2/PageflowC2]");
		expectedErrorMessage.append("\n\tValue=[1] in subpageflow [PageflowA/PageflowB2/PageflowC1]");
		expectedErrorMessage.append("\n\tValue=[0] in subpageflow [PageflowA/PageflowB1]\n");
		expectedErrorMessage.append("The property [B] is declared with different values in nested pageflows:");
		expectedErrorMessage.append("\n\tValue=[2] in subpageflow [PageflowA/PageflowB1]");
		expectedErrorMessage.append("\n\tValue=[1] in subpageflow [PageflowA/PageflowB2/PageflowC1]\n");
		String message = detector.getErrorMessage();
		Assert.assertEquals(expectedErrorMessage.toString(), message);
    }
    
    @Test
    public void testPageflowPropertiesWithoutConflictsInNested() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException {
    	// load required models
		copyResourceInModelsProject(PAGEFLOWS_LOCATION+"/"+PAGEFLOW_MODEL_D);
		copyResourceInModelsProject(PAGEFLOWS_LOCATION+"/"+PAGEFLOW_MODEL_D1);
		copyResourceInModelsProject(PAGEFLOWS_LOCATION+"/"+PAGEFLOW_MODEL_D2);

		// retrieve pageflowA
		URI modelUri = URI.createURI("resource:///"+PAGEFLOW_MODEL_D);
		IOfsModelResource resource = getOfsProject().getOfsModelResource(modelUri);
		Pageflow pageflow = (Pageflow)resource.getEMFModel().iterator().next();

		// perform the validation
		PageflowPropertiesConflictDetector detector = new PageflowPropertiesConflictDetector(pageflow);
		Assert.assertFalse(detector.hasConflicts());
		String expectedErrorMessage = "";
		Assert.assertEquals(expectedErrorMessage, detector.getErrorMessage());
    }

    @Test
    public void testPageflowPropertiesWithConflictsInNested() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException {
    	// load required models
		copyResourceInModelsProject(PAGEFLOWS_LOCATION+"/"+PAGEFLOW_MODEL_E);
		copyResourceInModelsProject(PAGEFLOWS_LOCATION+"/"+PAGEFLOW_MODEL_E1);
		copyResourceInModelsProject(PAGEFLOWS_LOCATION+"/"+PAGEFLOW_MODEL_E2);

		// retrieve pageflowA
		URI modelUri = URI.createURI("resource:///"+PAGEFLOW_MODEL_E);
		IOfsModelResource resource = getOfsProject().getOfsModelResource(modelUri);
		Pageflow pageflow = (Pageflow)resource.getEMFModel().iterator().next();

		// perform the validation
		PageflowPropertiesConflictDetector detector = new PageflowPropertiesConflictDetector(pageflow);
		detector.hasConflicts();
		Assert.assertTrue(detector.hasConflicts());
		
		StringBuilder expectedErrorMessage = new StringBuilder();
		//expectedErrorMessage.append("Pageflow model name<<PageflowE>> contains ambiguous declaration of properties in some nested pageflows:\n");
		expectedErrorMessage.append("The property [A] is declared with different values in nested pageflows:");
		expectedErrorMessage.append("\n\tValue=[two] in subpageflow [PageflowE/PageflowE2]");
		expectedErrorMessage.append("\n\tValue=[one] in subpageflow [PageflowE/PageflowE1]\n");
		String message = detector.getErrorMessage();
		Assert.assertEquals(expectedErrorMessage.toString(), message);
    }

    @Test
    public void testPageflowPropertiesWithoutConflictsInParentAndNested() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException {
    	// load required models
		copyResourceInModelsProject(PAGEFLOWS_LOCATION+"/"+PAGEFLOW_MODEL_F);
		copyResourceInModelsProject(PAGEFLOWS_LOCATION+"/"+PAGEFLOW_MODEL_F1);
		copyResourceInModelsProject(PAGEFLOWS_LOCATION+"/"+PAGEFLOW_MODEL_F2);

		// retrieve pageflowA
		URI modelUri = URI.createURI("resource:///"+PAGEFLOW_MODEL_F);
		IOfsModelResource resource = getOfsProject().getOfsModelResource(modelUri);
		Pageflow pageflow = (Pageflow)resource.getEMFModel().iterator().next();

		// perform the validation
		PageflowPropertiesConflictDetector detector = new PageflowPropertiesConflictDetector(pageflow);
		Assert.assertFalse(detector.hasConflicts());
		String expectedErrorMessage = "";
		Assert.assertEquals(expectedErrorMessage, detector.getErrorMessage());
    }

}
