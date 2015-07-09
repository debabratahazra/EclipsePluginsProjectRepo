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
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.ApplicationsVersionsLandscapeService;

/**
 * Responsible for running unit tests on
 * {@link ApplicationsVersionsLandscapeService}
 * 
 * @author sbharathraja
 * 
 */
public class ApplicationsVersionsLandscapeServiceTest {

	/** instance of class which is under test */
	private ApplicationsVersionsLandscapeService service;

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
		responseArray.insert("AA.ACCRUAL.FREQUENCY,AR.ACCOUNT", 1);
		responseArray.insert("DE.FORMAT.BGC,NEW", 2);
		responseArray.insert("AA.ARRANGEMENT.ACTIVITY", 3);

		// adding a response array which holds the real data.
		response.add(responseArray);
		return response;
	}

	@Before
	public void setUp() throws Exception {
		// mock the integration connector
		IntegrationConnector connector = mock(IntegrationConnector.class);
		// get the mocked jconnection
		JConnection jConnection = getMockJConnection();
		// suggest the mocker to return mocked values for some method calls.
		when(connector.getConnection(anyString(), anyBoolean())).thenReturn(
				jConnection);
		// suggest the mocker to return our own values
		service = new ApplicationsVersionsLandscapeService(connector);
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

	@Test
	public void testGetResponseString() {
		// execute the test case member
		List<String> versionList = service.getResponse("projectName");
		// verify the execution
		assertNotNull(versionList);
		assertTrue(versionList.size() == 2);
		String version = versionList.get(0);
		assertTrue(version.equals("AA.ACCRUAL.FREQUENCY,AR.ACCOUNT"));
		version = versionList.get(1);
		assertTrue(version.equals("DE.FORMAT.BGC,NEW"));

	}

	@Test
	public void testGetResponseStringBoolean() {
		// execute the test case member
		List<String> versionList = service.getResponse("projectName", false);
		// verify the execution
		assertNotNull(versionList);
		assertTrue(versionList.size() == 2);
		String version = versionList.get(0);
		assertTrue(version.equals("AA.ACCRUAL.FREQUENCY,AR.ACCOUNT"));
		version = versionList.get(1);
		assertTrue(version.equals("DE.FORMAT.BGC,NEW"));

	}

}
