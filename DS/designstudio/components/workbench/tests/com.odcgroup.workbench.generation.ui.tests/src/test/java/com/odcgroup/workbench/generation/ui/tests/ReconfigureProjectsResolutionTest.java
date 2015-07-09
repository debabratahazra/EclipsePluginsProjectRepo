package com.odcgroup.workbench.generation.ui.tests;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IMarkerResolution;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.init.CodeGenInitializer;
import com.odcgroup.workbench.generation.ui.internal.marker.CodeGenMarkerResolutionGenerator;
import com.odcgroup.workbench.generation.ui.internal.marker.ReconfigureProjectsResolution;
import com.odcgroup.workbench.ui.internal.markers.OfsMarkerResolution;
import com.odcgroup.workbench.ui.tests.util.AbstractDSUIUnitTest;

@SuppressWarnings("restriction")
public class ReconfigureProjectsResolutionTest extends AbstractDSUIUnitTest {


	private static final String DS4051_MODELS_GEN = "ds4051-models-gen";
	private static final String DS4051_MODELS = "ds4051-models";
	private static final String DS6446_MODELS = "ds6446-models";
	private static final String PMS_MODELS = "pms-models";

	/**
	 * @param name
	 */
	public ReconfigureProjectsResolutionTest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		createNamedModelsProject(DS4051_MODELS);
		createNamedModelsProject(DS6446_MODELS);
		createNamedModelsProject(PMS_MODELS);
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProject(DS4051_MODELS);
		deleteModelsProject(DS6446_MODELS);
		deleteModelsProject(PMS_MODELS);
	}
	
	@Test
	public void testReconfigureProjectsCallsUICodeGenInitializer_DS4051() throws Exception {
	 	IProject project = getProject(DS4051_MODELS);
	 	project.build(IncrementalProjectBuilder.FULL_BUILD, null);
	 	IMarker[] markers = project.findMarkers("com.odcgroup.workbench.core.OfsProblem", true, IResource.DEPTH_ONE);
 		if(markers.length > 0) { 
 			new OfsMarkerResolution().run(markers[0]);
 		}
	 	GenerationCore.toggleNature(project);
	 	ResourcesPlugin.getWorkspace().getRoot().getProject(DS4051_MODELS_GEN).delete(true, null);
	 	
	 	project.build(IncrementalProjectBuilder.FULL_BUILD, null);
	 	
	 	// Try to stabilize the unit test
	 	Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_BUILD, null);
	 	
		markers = project.findMarkers("com.odcgroup.workbench.generation.projectProblem", true, IResource.DEPTH_ONE);
		Assert.assertEquals(1, markers.length);

		new OfsMarkerResolution().run(markers[0]);
        new ReconfigureProjectsResolution().run(markers[0]);
        project.build(IncrementalProjectBuilder.FULL_BUILD, null);
        int maxSeverity = project.findMaxProblemSeverity("com.odcgroup.workbench.core.OfsProblem", true, IResource.DEPTH_INFINITE);
        Assert.assertNotSame(IMarker.SEVERITY_ERROR, maxSeverity);
	}

	@Test
	public void testRemovedtheSketchingDS5036() throws Exception {
	    IProject project = getProject(DS4051_MODELS);
	    project.build(IncrementalProjectBuilder.FULL_BUILD, null);
	    IMarker[] markers = project.findMarkers("com.odcgroup.workbench.core.OfsProblem", true, IResource.DEPTH_ONE);
	    if(markers.length>0) {
		    CodeGenMarkerResolutionGenerator reGenerator=new CodeGenMarkerResolutionGenerator();
		    IMarkerResolution[] resoulution= reGenerator.getResolutions(markers[0]);
		    Assert.assertEquals(2, resoulution.length);
		    Assert.assertNotSame("Change project type to 'Sketching'",resoulution[0].getLabel());
		    Assert.assertNotSame("Change project type to 'Sketching'",resoulution[1].getLabel());
	    }
	}

	@Test
	public void testGenPomExistInOCSRepoFolderDS6446() throws Exception {
	    IProject project = getProject(DS6446_MODELS);
	    project.build(IncrementalProjectBuilder.FULL_BUILD, null);
	    IMarker[] markers = project.findMarkers("com.odcgroup.workbench.core.OfsProblem", true, IResource.DEPTH_ONE);
	    if(markers.length>0) {
	   
		    CodeGenMarkerResolutionGenerator reGenerator=new CodeGenMarkerResolutionGenerator();
		    IMarkerResolution[] resoulution= reGenerator.getResolutions(markers[0]);
		    Assert.assertSame("Create and update code generation java projects",resoulution[0].getLabel());
		    CodeGenInitializer codeGeninitializer = new CodeGenInitializer();
		    boolean isExist = codeGeninitializer.isGenPomExistInRepoFolder(project); 
		    Assert.assertTrue(isExist);
		    
		    IProject pms_project = getProject(PMS_MODELS);
		    project.build(IncrementalProjectBuilder.FULL_BUILD, null);
		    IMarker[] pms_markers = project.findMarkers("com.odcgroup.workbench.core.OfsProblem", true, IResource.DEPTH_ONE);
		    IMarkerResolution[] pms_resoulution= reGenerator.getResolutions(pms_markers[0]);
		    Assert.assertSame("Create and update code generation java projects",pms_resoulution[0].getLabel());
		    Assert.assertFalse(codeGeninitializer.isGenPomExistInRepoFolder(pms_project));
		}
	}

	
}
