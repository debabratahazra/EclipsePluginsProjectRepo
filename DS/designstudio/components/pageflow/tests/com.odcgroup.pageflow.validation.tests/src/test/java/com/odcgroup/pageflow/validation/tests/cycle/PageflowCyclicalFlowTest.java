package com.odcgroup.pageflow.validation.tests.cycle;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.pageflow.model.DecisionState;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.pageflow.validation.internal.PageflowStronglyConnectedComponentsDetector;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * Check the presence of bad cycles in pageflow.
 */
public class PageflowCyclicalFlowTest extends  AbstractDSUnitTest  { 
	
	private static final String PAGEFLOW_MODEL = "ds5427/Pageflow5427.pageflow";

    @Before
    public void setUp() throws CoreException {
    	createModelsProject();
		copyResourceInModelsProject(PAGEFLOWS_LOCATION+"/"+PAGEFLOW_MODEL);
    }

    @After
    public void tearDown() throws CoreException {
    	deleteModelsProjects();
    }
    
    @Test
    public void testPageflowStronglyConnectedComponentsDetector() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException {
    
		URI modelUri = URI.createURI("resource:///"+PAGEFLOW_MODEL);
		IOfsModelResource resource = getOfsProject().getOfsModelResource(modelUri);
		Pageflow pageflow = (Pageflow)resource.getEMFModel().iterator().next();
    	PageflowStronglyConnectedComponentsDetector sccDetector = new PageflowStronglyConnectedComponentsDetector(pageflow);
    	List<List<State>> scc = sccDetector.getStronglyConnectedComponents();
		Assert.assertEquals("Wrong number of strongly connected component", 2, scc.size());

		Set<String> names = new HashSet<String>();
		
		// check first cycle.
		List<State> cycle = scc.get(0);
		Assert.assertEquals("Wrong number of states", 4, cycle.size());
		for (State state : cycle) {
			Assert.assertTrue("Decision State expected", state instanceof DecisionState);
			names.add(state.getDisplayName());
		}
		Assert.assertTrue("Decision State DS3 expected in cycle 1", names.contains("DS3"));
		Assert.assertTrue("Decision State DS4 expected in cycle 1", names.contains("DS4"));
		Assert.assertTrue("Decision State DS5 expected in cycle 1", names.contains("DS5"));
		Assert.assertTrue("Decision State DS6 expected in cycle 1", names.contains("DS6"));

		names.clear();
		cycle = scc.get(1);
		Assert.assertEquals("Wrong number of state", 2, cycle.size());
		for (State state : cycle) {
			Assert.assertTrue("Decision State expected", state instanceof DecisionState);
			names.add(state.getDisplayName());
		}
		Assert.assertTrue("Decision State DS1 expected in cycle 2", names.contains("DS1"));
		Assert.assertTrue("Decision State DS2 expected in cycle 2", names.contains("DS2"));
		
    }
    
}
