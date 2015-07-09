package com.odcgroup.mdf.generation.tests;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.junit.After;
import org.junit.Before;

import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

abstract public class MdfGenerationTestCase extends AbstractDSUnitTest {

	private IProject project;

	private static final String OCSOUTPUT_DIR = "output";
	
	@Before
    public void setUp() throws CoreException {
    	project = createModelsProject().getProject();
        project.getFolder(OCSOUTPUT_DIR).create(true, true, null);
    }

    @After
    public void tearDown() throws CoreException {
    	deleteModelsProjects();
    }
    
    protected IPath getOutputLocation() {
    	return project.getFolder(OCSOUTPUT_DIR).getLocation();
    }
    
}
