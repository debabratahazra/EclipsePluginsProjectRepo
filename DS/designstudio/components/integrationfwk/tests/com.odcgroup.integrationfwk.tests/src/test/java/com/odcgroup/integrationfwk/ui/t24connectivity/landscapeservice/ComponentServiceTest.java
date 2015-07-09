package com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationConnector;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.ComponentService;

/**
 * Responsible for running unit tests on {@link ComponentService}
 * 
 * @author sbharathraja
 * 
 */
public class ComponentServiceTest {

	/** instance of class which is under test */
	private ComponentService service;

	@Before
	public void setUp() throws Exception {
		// mock the integration connector
		IntegrationConnector connector = mock(IntegrationConnector.class);
		// suggest the mocker to return our own values
		service = new ComponentService(connector);
	}

	@Test
	public void testConstructjSubroutineRequest() {
		// execute the test case member
		JSubroutineParameters request = service.constructjSubroutineRequest();
		// verify the execution
		assertNotNull(request);
		assertTrue(request.size() == 2);
		assertTrue(request.get(0).toString().equals(""));
		assertTrue(request.get(1).toString().equals(""));
	}

	// TODO: Bharath - the core class needs to be refactored...
	// @Test
	// public void testGetComponentServiceStringString() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testGetComponentServiceStringStringBoolean() {
	// fail("Not yet implemented");
	// }

	@Test
	public void testGetResponse() {
		// execute the test case
		List<String> response = service.getResponse("projectName");
		// verify the execution
		assertNotNull(response);
		assertTrue(response.isEmpty());
	}

}
