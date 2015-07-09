package com.odcgroup.integrationfwk.services.connectivity.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import integrationlandscapeservicews.IntegrationLandscapeServiceWS;
import integrationlandscapeservicews.IntegrationLandscapeServiceWSPortType;
import integrationlandscapeservicews.ObjectFactory;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.junit.Before;
import org.junit.Test;

import com.odcgroup.integrationfwk.ui.common.GetFieldsServiceResponse;
import com.odcgroup.integrationfwk.ui.common.ServiceResponses;
import com.odcgroup.integrationfwk.ui.model.Field;
import com.odcgroup.integrationfwk.ui.services.connectivity.LandscapeServices;
import com.temenos.services.integrationlandscape.data.response.xsd.GetApplicationsResponse;
import com.temenos.services.integrationlandscape.data.response.xsd.GetApplicationsVersionsResponse;
import com.temenos.services.integrationlandscape.data.response.xsd.GetFlowFieldsResponse;
import com.temenos.services.integrationlandscape.data.response.xsd.GetOverrideCodesResponse;
import com.temenos.services.integrationlandscape.data.response.xsd.GetTsaServiceJobsResponse;
import com.temenos.services.integrationlandscape.data.response.xsd.GetVersionExitPointsResponse;
import com.temenos.services.integrationlandscape.data.response.xsd.GetVersionsResponse;
import com.temenos.services.integrationlandscape.data.xsd.ApplicationField;
import com.temenos.services.integrationlandscape.data.xsd.VersionExitPoint;
import com.temenos.soa.services.data.xsd.ResponseDetails;
import com.temenos.soa.services.data.xsd.T24UserDetails;

/**
 * Responsible for conducting unit tests on {@link LandscapeServices}
 * 
 * @author sbharathraja
 * 
 */
public class LandscapeServicesTest {

    /** instance of class which is under test */
    private LandscapeServices successService;
    private LandscapeServices failureService;

    /**
     * Helps to get the service proxy instance.
     *
     * @return
     */
    private IntegrationLandscapeServiceWSPortType getServiceProxy(
	    boolean isSuccess) {
	// mock the proxy.
	IntegrationLandscapeServiceWSPortType proxy = mock(IntegrationLandscapeServiceWSPortType.class);
	String successIndicator = null;
	if (isSuccess) {
	    successIndicator = "SUCCESS";
	} else {
	    successIndicator = "FAILURE";
	}

	ObjectFactory objectFactory = new ObjectFactory();

	GetApplicationsResponse applicationResponse = mock(GetApplicationsResponse.class);
	List<String> applications = new ArrayList<String>();
	applications.add("FOO");
	applications.add("BAR");
	applications.add("FUNDS.TRANSFER");

	JAXBElement<ResponseDetails> response = mock(JAXBElement.class);
	ResponseDetails res = mock(ResponseDetails.class);

	JAXBElement<String> ret = mock(JAXBElement.class);
	// response.setReturnCode(value)
	// response.setReturnCode("SUCCESS");
	// applicationResponse.setResponseDetails(response);
	when(applicationResponse.getResponseDetails()).thenReturn(response);
	when(response.getValue()).thenReturn(res);
	when(res.getReturnCode()).thenReturn(ret);
	when(ret.getValue()).thenReturn(successIndicator);
	when(applicationResponse.getApplicationNames())
		.thenReturn(applications);
	when(proxy.getApplications(any(T24UserDetails.class))).thenReturn(
		applicationResponse);

	GetFlowFieldsResponse applicationFieldsResponse = mock(GetFlowFieldsResponse.class);
	List<ApplicationField> applicationFields = new ArrayList<ApplicationField>();
	ApplicationField applicationField = new ApplicationField();
	applicationField.setName(objectFactory
		.createGetApplicationFieldsApplicationName("@ID"));
	applicationField.setDataType(objectFactory
		.createGetApplicationFieldsApplicationName("String"));
	applicationFields.add(applicationField);
	applicationField = new ApplicationField();
	applicationField.setName(objectFactory
		.createGetApplicationFieldsApplicationName("FIELD.NAME"));
	applicationField.setDataType(objectFactory
		.createGetApplicationFieldsApplicationName("String"));
	applicationFields.add(applicationField);
	applicationField = new ApplicationField();
	applicationField.setName(objectFactory
		.createGetApplicationFieldsApplicationName("DEBIT.ACCT.NO"));
	applicationField.setDataType(objectFactory
		.createGetApplicationFieldsApplicationName("Decimal"));
	applicationFields.add(applicationField);
	applicationField = new ApplicationField();

	applicationField
		.setName(objectFactory
			.createGetApplicationFieldsApplicationName("AA.ARRANGEMENT.ACTIVITY:DEBIT.ACCT.NO"));
	applicationField.setDataType(objectFactory
		.createGetApplicationFieldsApplicationName("Decimal"));
	applicationFields.add(applicationField);
	applicationField = new ApplicationField();
	applicationField
		.setName(objectFactory
			.createGetApplicationFieldsApplicationName("AA.ARRANGEMENT.ACTIVITY:DEBIT.ACCT.DATE"));
	applicationField.setDataType(objectFactory
		.createGetApplicationFieldsApplicationName("Date"));
	applicationFields.add(applicationField);
	when(applicationFieldsResponse.getResponseDetails()).thenReturn(
		response);
	when(response.getValue()).thenReturn(res);
	when(res.getReturnCode()).thenReturn(ret);
	when(ret.getValue()).thenReturn(successIndicator);
	when(applicationFieldsResponse.getApplicationFields()).thenReturn(
		applicationFields);
	when(proxy.getFlowFields(any(T24UserDetails.class), eq("FOO")))
		.thenReturn(applicationFieldsResponse);

	GetApplicationsVersionsResponse applicationsVersionsResponse = mock(GetApplicationsVersionsResponse.class);
	List<String> applicationsVersions = new ArrayList<String>();
	applicationsVersions.add("FOO");
	applicationsVersions.add("FOO.BAR");
	applicationsVersions.add("FOO.BAR,NEW");
	when(applicationsVersionsResponse.getResponseDetails()).thenReturn(
		response);
	when(response.getValue()).thenReturn(res);
	when(res.getReturnCode()).thenReturn(ret);
	when(ret.getValue()).thenReturn(successIndicator);
	when(applicationsVersionsResponse.getBaseEventNames()).thenReturn(
		applicationsVersions);
	when(proxy.getApplicationsVersions(any(T24UserDetails.class)))
		.thenReturn(applicationsVersionsResponse);

	GetVersionExitPointsResponse exitPointsResponse = mock(GetVersionExitPointsResponse.class);
	List<VersionExitPoint> versionExitPoints = new ArrayList<VersionExitPoint>();
	VersionExitPoint versionExitPoint = new VersionExitPoint();
	versionExitPoint.setName(objectFactory
		.createGetVersionSchemasVersionName("INPUT.ROUTINE"));
	versionExitPoints.add(versionExitPoint);
	versionExitPoint = new VersionExitPoint();
	versionExitPoint.setName(objectFactory
		.createGetVersionSchemasVersionName("AUTH.ROUTINE"));
	versionExitPoints.add(versionExitPoint);
	when(exitPointsResponse.getResponseDetails()).thenReturn(response);
	when(response.getValue()).thenReturn(res);
	when(res.getReturnCode()).thenReturn(ret);
	when(ret.getValue()).thenReturn(successIndicator);
	when(exitPointsResponse.getVersionExitPoints()).thenReturn(
		versionExitPoints);
	when(proxy.getVersionExitPoints(any(T24UserDetails.class))).thenReturn(
		exitPointsResponse);

	GetOverrideCodesResponse overrideCodeResponse = mock(GetOverrideCodesResponse.class);
	List<String> overrideCodes = new ArrayList<String>();
	overrideCodes.add("BAR");
	overrideCodes.add("FOO");
	overrideCodes.add("BAR.FOO");
	when(overrideCodeResponse.getResponseDetails()).thenReturn(response);
	when(response.getValue()).thenReturn(res);
	when(res.getReturnCode()).thenReturn(ret);
	when(ret.getValue()).thenReturn(successIndicator);
	when(overrideCodeResponse.getOverrideCodes()).thenReturn(overrideCodes);
	when(proxy.getOverrideCodes(any(T24UserDetails.class))).thenReturn(
		overrideCodeResponse);

	GetTsaServiceJobsResponse tsaServiceJobsResponse = mock(GetTsaServiceJobsResponse.class);
	List<String> tsaServiceJobs = new ArrayList<String>();
	tsaServiceJobs.add("FOO.FOO");
	tsaServiceJobs.add("BAR.BAR");
	when(tsaServiceJobsResponse.getResponseDetails()).thenReturn(response);
	when(response.getValue()).thenReturn(res);
	when(res.getReturnCode()).thenReturn(ret);
	when(ret.getValue()).thenReturn(successIndicator);
	when(tsaServiceJobsResponse.getTsaServiceJobs()).thenReturn(
		tsaServiceJobs);
	when(proxy.getTsaServiceJobs(any(T24UserDetails.class))).thenReturn(
		tsaServiceJobsResponse);

	GetVersionsResponse versionsResponse = mock(GetVersionsResponse.class);
	List<String> versionNames = new ArrayList<String>();
	versionNames.add("FOO,BAR");
	versionNames.add("FOO.BAR,NEW");
	when(versionsResponse.getResponseDetails()).thenReturn(response);
	when(response.getValue()).thenReturn(res);
	when(res.getReturnCode()).thenReturn(ret);
	when(ret.getValue()).thenReturn(successIndicator);
	when(versionsResponse.getVersionNames()).thenReturn(versionNames);
	when(proxy.getVersions(any(T24UserDetails.class))).thenReturn(
		versionsResponse);

	return proxy;
    }

    @Before
    public void setUp() throws Exception {
	// mock the serviceWS instance for success
	IntegrationLandscapeServiceWS landscapeServiceWS = mock(IntegrationLandscapeServiceWS.class);
	IntegrationLandscapeServiceWSPortType proxyForSuccess = getServiceProxy(true);

	when(
		landscapeServiceWS
			.getIntegrationLandscapeServiceWSHttpSoap11Endpoint())
		.thenReturn(proxyForSuccess);

	successService = new LandscapeServices(landscapeServiceWS, "SUSER1",
		"123456");

	mock(IntegrationLandscapeServiceWS.class);

	IntegrationLandscapeServiceWSPortType proxyForFailure = getServiceProxy(false);

	when(
		landscapeServiceWS
			.getIntegrationLandscapeServiceWSHttpSoap11Endpoint())
		.thenReturn(proxyForFailure);

	failureService = new LandscapeServices(landscapeServiceWS, "SUSER1",
		"123456");

    }

    @Test
    public void testGetApplicationFields() {
	// execute the test case

	GetFieldsServiceResponse response = successService
		.getApplicationFields("FOO");
	List<Field> applicationFields = response.getResponse();
	// verify the test case
	assertTrue(applicationFields.size() == 5);
	assertTrue(applicationFields.get(0).getFieldName().equals("@ID"));
	assertTrue(applicationFields.get(0).getFieldType().equals("String"));
	assertTrue(applicationFields.get(1).getFieldName().equals("FIELD.NAME"));
	assertTrue(applicationFields.get(1).getFieldType().equals("String"));
	assertTrue(applicationFields.get(2).getFieldName()
		.equals("DEBIT.ACCT.NO"));
	assertTrue(applicationFields.get(2).getFieldType().equals("Decimal"));
	assertTrue(applicationFields.get(3).getFieldName()
		.equals("AA.ARRANGEMENT.ACTIVITY:DEBIT.ACCT.NO"));
	assertTrue(applicationFields.get(3).getFieldType().equals("Decimal"));
	assertTrue(applicationFields.get(4).getFieldName()
		.equals("AA.ARRANGEMENT.ACTIVITY:DEBIT.ACCT.DATE"));
	assertTrue(applicationFields.get(4).getFieldType().equals("Date"));
    }

    @Test
    public void testGetApplications() {
	// execute the test case

	ServiceResponses response = successService.getApplications();
	List<String> applications = response.getResponse();

	// verify the execution
	assertTrue(applications.size() == 3);
	assertTrue(applications.get(0).equals("FOO"));
	assertTrue(applications.get(1).equals("BAR"));
	assertTrue(applications.get(2).equals("FUNDS.TRANSFER"));
    }

    @Test
    public void testGetApplicationsFailure() {
	ServiceResponses response = failureService.getApplications();

	assertFalse(response.isStatus());
    }

    @Test
    public void testGetApplicationsVersionFailure() {
	ServiceResponses response = failureService.getApplicationsVersions();

	assertFalse(response.isStatus());
    }

    @Test
    public void testGetApplicationsVersions() {
	// execute the test case
	ServiceResponses response = successService.getApplicationsVersions();
	List<String> applicationsVersions = response.getResponse();

	// verify the execution
	assertTrue(applicationsVersions.size() == 3);
	assertTrue(applicationsVersions.get(0).equals("FOO"));
	assertTrue(applicationsVersions.get(1).equals("FOO.BAR"));
	assertTrue(applicationsVersions.get(2).equals("FOO.BAR,NEW"));
    }

    @Test
    public void testGetExitPoints() {
	// execute the test case
	ServiceResponses response = successService.getExitPoints();
	List<String> exitPoints = response.getResponse();
	// verify the execution
	assertTrue(exitPoints.size() == 2);
	assertTrue(exitPoints.get(0).equals("INPUT.ROUTINE"));
	assertTrue(exitPoints.get(1).equals("AUTH.ROUTINE"));
    }

    @Test
    public void testGetExitPointsFailure() {
	ServiceResponses response = failureService.getExitPoints();

	assertFalse(response.isStatus());
    }

    @Test
    public void testGetOverrideCodes() {
	// execute the test case
	ServiceResponses response = successService.getOverrideCodes();
	List<String> overrideCodes = response.getResponse();
	// verify the execution
	assertTrue(overrideCodes.size() == 3);
	assertTrue(overrideCodes.get(0).equals("BAR"));
	assertTrue(overrideCodes.get(1).equals("FOO"));
	assertTrue(overrideCodes.get(2).equals("BAR.FOO"));
    }

    @Test
    public void testGetOverrideCodesFailure() {
	ServiceResponses response = failureService.getOverrideCodes();

	assertFalse(response.isStatus());
    }

    @Test
    public void testGetTsaServiceFailure() {
	ServiceResponses response = failureService.getTsaServices();

	assertFalse(response.isStatus());
    }

    @Test
    public void testGetTsaServices() {
	// execute the test case
	ServiceResponses response = successService.getTsaServices();
	List<String> tsaServiceJobs = response.getResponse();
	// verify the execution
	assertTrue(tsaServiceJobs.size() == 2);
	assertTrue(tsaServiceJobs.get(0).equals("FOO.FOO"));
	assertTrue(tsaServiceJobs.get(1).equals("BAR.BAR"));
    }

    @Test
    public void testGetVersions() {
	// execute the test case member
	ServiceResponses response = successService.getVersions();
	List<String> versions = response.getResponse();
	// verify the execution
	assertTrue(versions.size() == 2);
	assertTrue(versions.get(0).equals("FOO,BAR"));
	assertTrue(versions.get(1).equals("FOO.BAR,NEW"));
    }

    @Test
    public void testGetVersionsFailure() {
	ServiceResponses response = failureService.getVersions();

	assertFalse(response.isStatus());
    }

}
