package com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jbase.jremote.JConnection;
import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JRemoteException;
import com.jbase.jremote.JSubroutineCoreDumpException;
import com.jbase.jremote.JSubroutineNotFoundException;
import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationConnector;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.TSALandscapeService;

/**
 * Responsible for running unit tests on {@link TSALandscapeService}
 * 
 * @author sbharathraja
 * 
 */
public class TSALandscapeServiceTest {

	/** instance of class which is under test */
	private TSALandscapeService service;

	/**
	 * Helps to mock the jconnection object.
	 * 
	 * @return mocked jconnection object.
	 * @throws JRemoteException
	 * @throws JSubroutineCoreDumpException
	 * @throws JSubroutineNotFoundException
	 */
	private JConnection getMockJConnection()
			throws JSubroutineNotFoundException, JSubroutineCoreDumpException,
			JRemoteException {
		// mock the jconnection
		JConnection jConnection = mock(JConnection.class);

		// suggest the jconnection mocker to return dummy response
		when(jConnection.call(anyString(), any(JSubroutineParameters.class)))
				.thenReturn(getMockSubroutineCallResponse());

		return jConnection;
	}

	/**
	 * Helps to create the mock subroutine call response.
	 * 
	 * @return
	 */
	private JSubroutineParameters getMockSubroutineCallResponse() {
		// create a dummy response
		JSubroutineParameters response = new JSubroutineParameters();
		// create a dummy dyn array for response status, and insert value
		JDynArray responseArray = new JDynArray();
		responseArray.insert("3MONTHLY.ARCHIVE.FIELDS", 1);
		responseArray.insert("EOD.REVAL.REP", 2);
		responseArray.insert("EOD.UPD.AC.PENDING", 3);
		responseArray.insert("EVENT", 4);
		responseArray.insert("START.DAY.UPDATE", 5);

		// adding a response array which holds the real data.
		response.add(responseArray);
		return response;
	}

	@Before
	public void setUp() throws Exception {
		// mock the integration
		IntegrationConnector connector = mock(IntegrationConnector.class);
		// get the mocked jconnection
		JConnection jConnection = getMockJConnection();
		// suggest the mocker to return mocked values for some method calls.
		when(connector.getConnection(anyString(), anyBoolean())).thenReturn(
				jConnection);

		// initialize the class which is under test
		service = new TSALandscapeService(connector);
	}

	@Test
	public void testConstructjSubroutineRequest() {
		// execute the test case member
		JSubroutineParameters request = service.constructjSubroutineRequest();
		// verify the execution.
		assertNotNull(request);
		assertTrue(request.size() == 1);
		assertTrue(request.get(0).toString().equals(""));
	}

	@Test
	public void testGetResponse() {
		// execute the test case member
		List<String> response = service.getResponse("projectName");
		// verify the execution
		assertNotNull(response);
		assertTrue(response.size() == 5);
		String value = response.get(0);
		assertTrue(value.equals("3MONTHLY.ARCHIVE.FIELDS"));
		value = response.get(1);
		assertTrue(value.equals("EOD.REVAL.REP"));
		value = response.get(2);
		assertTrue(value.equals("EOD.UPD.AC.PENDING"));
		value = response.get(3);
		assertTrue(value.equals("EVENT"));
		value = response.get(4);
		assertTrue(value.equals("START.DAY.UPDATE"));
	}

	@Test
	public void testGetTsaServicesList() {
		// execute the test case member
		List<String> response = service
				.getTsaServicesList("projectName", false);
		// verify the execution
		assertNotNull(response);
		assertTrue(response.size() == 5);
		String tsaService = response.get(0);
		assertTrue(tsaService.equals("3MONTHLY.ARCHIVE.FIELDS"));
		tsaService = response.get(1);
		assertTrue(tsaService.equals("EOD.REVAL.REP"));
		tsaService = response.get(2);
		assertTrue(tsaService.equals("EOD.UPD.AC.PENDING"));
		tsaService = response.get(3);
		assertTrue(tsaService.equals("EVENT"));
		tsaService = response.get(4);
		assertTrue(tsaService.equals("START.DAY.UPDATE"));
	}

}
