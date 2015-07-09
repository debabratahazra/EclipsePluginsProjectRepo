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
import com.odcgroup.integrationfwk.ui.model.Field;
import com.odcgroup.integrationfwk.ui.model.Fields;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationConnector;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.ApplicationFieldsLandscapeService;

/**
 * Class which is responsible for running unit tests on
 * {@link ApplicationFieldsLandscapeService}
 * 
 * @author sbharathraja
 * 
 */
public class ApplicationFieldsLandscapeServiceTest {

	/** instance of class which is under test */
	private ApplicationFieldsLandscapeService service;

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
		responseArray.insert("@ID", 1, 1);
		responseArray.insert("string", 1, 2);
		responseArray.insert("FUNDS.TRANSFER", 1, 3);

		responseArray.insert("CO.CODE", 2, 1);
		responseArray.insert("string", 2, 2);
		responseArray.insert("FUNDS.TRANSFER", 2, 3);

		responseArray.insert("ACCOUNTING.DATE", 3, 1);
		responseArray.insert("date", 3, 2);
		responseArray.insert("FUNDS.TRANSFER", 3, 3);

		responseArray.insert("AA.ARRANGEMENT.ACTIVITY:@ID", 4, 1);
		responseArray.insert("string", 4, 2);
		responseArray.insert("AA.ARRANGEMENT.ACTIVITY", 4, 3);

		responseArray.insert("AA.ARRANGEMENT.ACTIVITY:EFFECTIVE.DATE", 5, 1);
		responseArray.insert("date", 5, 2);
		responseArray.insert("AA.ARRANGEMENT.ACTIVITY", 5, 3);

		// adding an empty array to the first position of the response
		response.add(new JDynArray(""));
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
		service = new ApplicationFieldsLandscapeService(connector);
	}

	@Test
	public void testConstructjSubroutineRequest() {
		// execute the test case member
		JSubroutineParameters request = service.constructjSubroutineRequest();
		// verify the execution.
		assertNotNull(request);
		assertTrue(request.size() == 2);
		assertTrue(request.get(0).toString().equals(""));
		assertTrue(request.get(1).toString().equals(""));
	}

	@Test
	public void testConstructjSubroutineRequestString() {
		// execute the test case member
		JSubroutineParameters request = service
				.constructjSubroutineRequest("applicationName");
		// verify the execution.
		assertNotNull(request);
		assertTrue(request.size() == 2);
		assertTrue(request.get(0).toString().equals("<1>applicationName"));
		assertTrue(request.get(1).toString().equals(""));
	}

	@Test
	public void testGetApplicationFieldsList() {
		// execute the test case member
		Fields fields = service
				.getApplicationFieldsList(getMockSubroutineCallResponse());
		// verify the execution.
		assertNotNull(fields);
		List<Field> fieldList = fields.getInputFields();
		assertNotNull(fieldList);
		assertTrue(fieldList.size() == 5);
		Field field = fieldList.get(0);
		assertNotNull(field);
		// display name should be empty here as it will be generated only when
		// the user adding it into enrichment.
		assertTrue(field.getDisplayName().equals(""));
		assertTrue(field.getFieldName().equals("@ID"));
		assertTrue(field.getFieldType().equals("string"));

		field = fieldList.get(1);
		assertNotNull(field);
		assertTrue(field.getDisplayName().equals(""));
		assertTrue(field.getFieldName().equals("CO.CODE"));
		assertTrue(field.getFieldType().equals("string"));

		field = fieldList.get(2);
		assertNotNull(field);
		assertTrue(field.getDisplayName().equals(""));
		assertTrue(field.getFieldName().equals("ACCOUNTING.DATE"));
		assertTrue(field.getFieldType().equals("date"));

		field = fieldList.get(3);
		assertNotNull(field);
		assertTrue(field.getDisplayName().equals(""));
		assertTrue(field.getFieldName().equals("AA.ARRANGEMENT.ACTIVITY:@ID"));
		assertTrue(field.getFieldType().equals("string"));

		field = fieldList.get(4);
		assertNotNull(field);
		assertTrue(field.getDisplayName().equals(""));
		assertTrue(field.getFieldName().equals(
				"AA.ARRANGEMENT.ACTIVITY:EFFECTIVE.DATE"));
		assertTrue(field.getFieldType().equals("date"));
	}

	@Test
	public void testGetFieldsStringString() {
		// execute the test case member
		Fields fields = service.getFields("applicationName", "projectName");
		// verify the execution.
		assertNotNull(fields);
		List<Field> fieldList = fields.getInputFields();
		assertNotNull(fieldList);
		assertTrue(fieldList.size() == 5);
		Field field = fieldList.get(0);
		assertNotNull(field);
		// display name should be empty here as it will be generated only when
		// the user adding it into enrichment.
		assertTrue(field.getDisplayName().equals(""));
		assertTrue(field.getFieldName().equals("@ID"));
		assertTrue(field.getFieldType().equals("string"));

		field = fieldList.get(1);
		assertNotNull(field);
		assertTrue(field.getDisplayName().equals(""));
		assertTrue(field.getFieldName().equals("CO.CODE"));
		assertTrue(field.getFieldType().equals("string"));

		field = fieldList.get(2);
		assertNotNull(field);
		assertTrue(field.getDisplayName().equals(""));
		assertTrue(field.getFieldName().equals("ACCOUNTING.DATE"));
		assertTrue(field.getFieldType().equals("date"));

		field = fieldList.get(3);
		assertNotNull(field);
		assertTrue(field.getDisplayName().equals(""));
		assertTrue(field.getFieldName().equals("AA.ARRANGEMENT.ACTIVITY:@ID"));
		assertTrue(field.getFieldType().equals("string"));

		field = fieldList.get(4);
		assertNotNull(field);
		assertTrue(field.getDisplayName().equals(""));
		assertTrue(field.getFieldName().equals(
				"AA.ARRANGEMENT.ACTIVITY:EFFECTIVE.DATE"));
		assertTrue(field.getFieldType().equals("date"));
	}

	@Test
	public void testGetFieldsStringStringBoolean() {
		// execute the test case member
		Fields fields = service.getFields("applicationName", "projectName",
				false);
		// verify the execution.
		assertNotNull(fields);
		List<Field> fieldList = fields.getInputFields();
		assertNotNull(fieldList);
		assertTrue(fieldList.size() == 5);
		Field field = fieldList.get(0);
		assertNotNull(field);
		// display name should be empty here as it will be generated only when
		// the user adding it into enrichment.
		assertTrue(field.getDisplayName().equals(""));
		assertTrue(field.getFieldName().equals("@ID"));
		assertTrue(field.getFieldType().equals("string"));

		field = fieldList.get(1);
		assertNotNull(field);
		assertTrue(field.getDisplayName().equals(""));
		assertTrue(field.getFieldName().equals("CO.CODE"));
		assertTrue(field.getFieldType().equals("string"));

		field = fieldList.get(2);
		assertNotNull(field);
		assertTrue(field.getDisplayName().equals(""));
		assertTrue(field.getFieldName().equals("ACCOUNTING.DATE"));
		assertTrue(field.getFieldType().equals("date"));

		field = fieldList.get(3);
		assertNotNull(field);
		assertTrue(field.getDisplayName().equals(""));
		assertTrue(field.getFieldName().equals("AA.ARRANGEMENT.ACTIVITY:@ID"));
		assertTrue(field.getFieldType().equals("string"));

		field = fieldList.get(4);
		assertNotNull(field);
		assertTrue(field.getDisplayName().equals(""));
		assertTrue(field.getFieldName().equals(
				"AA.ARRANGEMENT.ACTIVITY:EFFECTIVE.DATE"));
		assertTrue(field.getFieldType().equals("date"));
	}

	@Test
	public void testGetResponse() {
		// execute and verify the test case member
		List<String> response = service.getResponse("projectName");
		assertNotNull(response);
		assertTrue(response.isEmpty());
	}

}
