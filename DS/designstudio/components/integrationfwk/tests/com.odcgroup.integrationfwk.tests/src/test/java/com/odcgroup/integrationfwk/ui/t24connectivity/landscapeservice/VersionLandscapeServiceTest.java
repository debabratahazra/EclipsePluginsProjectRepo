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
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.VersionLandscapeService;

/**
 * Responsible for running unit tests on {@link VersionLandscapeService}
 * 
 * @author sbharathraja
 * 
 */
public class VersionLandscapeServiceTest {

	/** instance of class which is under test */
	private VersionLandscapeService service;

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
		responseArray.insert("AA.PERIODIC.ATTRIBUTE,AA.AUDIT", 1);
		responseArray.insert("AA.PERIODIC.ATTRIBUTE.CLASS,AA.AUDIT", 2);
		responseArray.insert("AA.PRD.CAT.AC.GENERAL.CHARGE,SEAT.ZERO", 3);
		responseArray.insert("AA.PRD.CAT.ACTIVITY.MAPPING,SEAT", 4);
		responseArray.insert("AA.PRD.CAT.AZ.DEPOSIT,SEAT.ZERO", 5);

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
		service = new VersionLandscapeService(connector);
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
	public void testGetResponseString() {
		// execute the test case member
		List<String> responseList = service.getResponse("projectName");
		// verify the execution
		assertNotNull(responseList);
		assertTrue(responseList.size() == 5);
		String response = responseList.get(0);
		assertTrue(response.equals("AA.PERIODIC.ATTRIBUTE,AA.AUDIT"));
		response = responseList.get(1);
		assertTrue(response.equals("AA.PERIODIC.ATTRIBUTE.CLASS,AA.AUDIT"));
		response = responseList.get(2);
		assertTrue(response.equals("AA.PRD.CAT.AC.GENERAL.CHARGE,SEAT.ZERO"));
		response = responseList.get(3);
		assertTrue(response.equals("AA.PRD.CAT.ACTIVITY.MAPPING,SEAT"));
		response = responseList.get(4);
		assertTrue(response.equals("AA.PRD.CAT.AZ.DEPOSIT,SEAT.ZERO"));
	}

	@Test
	public void testGetResponseStringBoolean() {
		// execute the test case member
		List<String> responseList = service.getResponse("projectName", false);
		// verify the execution
		assertNotNull(responseList);
		assertTrue(responseList.size() == 5);
		String response = responseList.get(0);
		assertTrue(response.equals("AA.PERIODIC.ATTRIBUTE,AA.AUDIT"));
		response = responseList.get(1);
		assertTrue(response.equals("AA.PERIODIC.ATTRIBUTE.CLASS,AA.AUDIT"));
		response = responseList.get(2);
		assertTrue(response.equals("AA.PRD.CAT.AC.GENERAL.CHARGE,SEAT.ZERO"));
		response = responseList.get(3);
		assertTrue(response.equals("AA.PRD.CAT.ACTIVITY.MAPPING,SEAT"));
		response = responseList.get(4);
		assertTrue(response.equals("AA.PRD.CAT.AZ.DEPOSIT,SEAT.ZERO"));
	}

	@Test
	public void testGetVersionList() {
		// execute the test case member
		List<String> versionList = service
				.getVersionList(getMockSubroutineCallResponse());
		// verify the execution
		assertNotNull(versionList);
		assertTrue(versionList.size() == 5);
		String version = versionList.get(0);
		assertTrue(version.equals("AA.PERIODIC.ATTRIBUTE,AA.AUDIT"));
		version = versionList.get(1);
		assertTrue(version.equals("AA.PERIODIC.ATTRIBUTE.CLASS,AA.AUDIT"));
		version = versionList.get(2);
		assertTrue(version.equals("AA.PRD.CAT.AC.GENERAL.CHARGE,SEAT.ZERO"));
		version = versionList.get(3);
		assertTrue(version.equals("AA.PRD.CAT.ACTIVITY.MAPPING,SEAT"));
		version = versionList.get(4);
		assertTrue(version.equals("AA.PRD.CAT.AZ.DEPOSIT,SEAT.ZERO"));
	}

}
