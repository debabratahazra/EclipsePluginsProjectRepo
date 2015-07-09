package com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationConnector;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.CreateDataLibraryService;

/**
 * Responsible for running unit tests on {@link CreateDataLibraryService}
 * 
 * @author sbharathraja
 * 
 */
public class CreateDataLibraryServiceTest {

	/** instance of class which is under test */
	private CreateDataLibraryService service;

	@Before
	public void setUp() throws Exception {
		// mock the integration connector
		IntegrationConnector connector = mock(IntegrationConnector.class);
		// suggest the mocker to return our own values
		service = new CreateDataLibraryService(connector);
	}

	@Test
	public void testConstructjSubroutineRequest() {
		// execute the test case member
		JSubroutineParameters request = service.constructjSubroutineRequest();
		// verify the execution
		assertNotNull(request);
		assertTrue(request.size() == 1);
		assertTrue(request.get(0).toString().equals(""));
	}

	// TODO: Bharath - core classe needs to be refactored.
	// @Test
	// public void testGetResponse() {
	// fail("Not yet implemented");
	// }

}
