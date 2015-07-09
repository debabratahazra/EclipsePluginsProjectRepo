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
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.CommonVariablesLandscapeService;

/**
 * Responsible for running unit tests on {@link CommonVariablesLandscapeService}
 * 
 * @author sbharathraja
 * 
 */
public class CommonVariablesLandscapeServiceTest {

	/** instance of class which is under test */
	private CommonVariablesLandscapeService service;

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
		// not sure what is common variables in T24. Hence testing with some
		// dummy strings.
		responseArray.insert("FOO.BAR", 1);
		responseArray.insert("FOO.BAR,NEW", 2);
		responseArray.insert("FOOBAR.FOO", 3);
		responseArray.insert("FOOBAR.FOO,NEW", 4);
		responseArray.insert("BARFOO.BAR", 5);

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
		service = new CommonVariablesLandscapeService(connector);
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
		List<String> commonvariablesList = service.getResponse("projectName");
		// verify the execution
		assertNotNull(commonvariablesList);
		assertTrue(commonvariablesList.size() == 5);
		String commonVariable = commonvariablesList.get(0);
		assertTrue(commonVariable.equals("FOO.BAR"));
		commonVariable = commonvariablesList.get(1);
		assertTrue(commonVariable.equals("FOO.BAR,NEW"));
		commonVariable = commonvariablesList.get(2);
		assertTrue(commonVariable.equals("FOOBAR.FOO"));
		commonVariable = commonvariablesList.get(3);
		assertTrue(commonVariable.equals("FOOBAR.FOO,NEW"));
		commonVariable = commonvariablesList.get(4);
		assertTrue(commonVariable.equals("BARFOO.BAR"));
	}

	@Test
	public void testGetResponseStringBoolean() {
		// execute the test case member
		List<String> commonvariablesList = service.getResponse("projectName",
				false);
		// verify the execution
		assertNotNull(commonvariablesList);
		assertTrue(commonvariablesList.size() == 5);
		String commonVariable = commonvariablesList.get(0);
		assertTrue(commonVariable.equals("FOO.BAR"));
		commonVariable = commonvariablesList.get(1);
		assertTrue(commonVariable.equals("FOO.BAR,NEW"));
		commonVariable = commonvariablesList.get(2);
		assertTrue(commonVariable.equals("FOOBAR.FOO"));
		commonVariable = commonvariablesList.get(3);
		assertTrue(commonVariable.equals("FOOBAR.FOO,NEW"));
		commonVariable = commonvariablesList.get(4);
		assertTrue(commonVariable.equals("BARFOO.BAR"));
	}

}
