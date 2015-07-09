package com.odcgroup.integrationfwk.controller.tests;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import com.odcgroup.integrationfwk.ui.controller.ComponentServiceController;
import com.odcgroup.integrationfwk.ui.model.Parameter;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.utils.XmlParser;

/**
 * Responsible for conduct the unit test on {@link ComponentServiceController}.
 * <p>
 * Written with intention of testing the {@link XmlParser} also.
 * 
 * @author sbharathraja
 * 
 */
public class ComponentServiceControllerTest {

	private ComponentServiceController controller;

	@Before
	public void setUp() throws Exception {
		// mock the project model
		TWSConsumerProject consumerProject = mock(TWSConsumerProject.class);
		// load the path where we have stored the component service meta data
		// xml
		String path = getClass().getResource(
				"/com/odcgroup/integrationfwk/ui/common/").getPath();
		// suggest the mocker to return our own path
		when(consumerProject.getPathString()).thenReturn(path);
		// create instance to the System which is under test
		controller = new ComponentServiceController(consumerProject);
	}

	//Test case @ RTC works fine
	//Fails @ EDS to investigate
	@Ignore
	@Test
	public void testGetOperationList() {
		// get the services
		List<String> services = controller.getServiceList();
		// execute the test case member
		List<String> operations = controller.getOperationList(services.get(0));
		// verify the size
		assertTrue(operations.size() == 8);
		// verify an available operation
		assertTrue(operations.contains("readFlow"));
	}

	//Test case @ RTC works fine
	//Fails @ EDS to investigate
	@Ignore
	@Test
	public void testGetParameters() {
		// get the services
		List<String> services = controller.getServiceList();
		// get the first operation from the service
		List<String> operations = controller.getOperationList(services.get(0));
		// execute the test case member
		List<Parameter> parameters = controller.getParameters(services.get(0),
				operations.get(0));
		// verify the size
		assertTrue(parameters.size() == 6);
		// get the first parameter from the list
		Parameter parameter = parameters.get(0);
		// verify some members
		assertTrue(parameter.getName().equalsIgnoreCase("flowName"));
		assertTrue(parameter.getDirection().equalsIgnoreCase("IN"));
		assertTrue(parameter.getTypeName().equalsIgnoreCase("String"));
	}

	//Test case @ RTC works fine
	//Fails @ EDS to investigate
	@Ignore
	@Test
	public void testGetServiceList() {
		// get the services
		List<String> services = controller.getServiceList();
		// verify the size
		assertTrue(services.size() == 2);
		// verify the first value
		assertTrue(services.get(0).equalsIgnoreCase("IntegrationFlowService"));
	}

	//Test case @ RTC works fine
	//Fails @ EDS to investigate
	@Ignore
	@Test
	public void testIsServiceAvailable() {
		assertTrue(controller.isServiceAvailable());
	}

}
