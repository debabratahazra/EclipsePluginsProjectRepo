package com.odcgroup.integrationfwk.ui.common;

import java.util.List;

import com.temenos.soa.services.data.xsd.Response;

//This class represents the response from any Landscape /Flow service.
//This contains the status indicator indicating if the service call was success or failure
//and if success contains the return response data.

public class ServiceResponses {

    private boolean status;
    private List<String> response;
    private List<Response> errorMessage;

    public ServiceResponses(boolean indicator, List<String> response,
	    List<Response> error) {
	status = indicator;
	this.response = response;
	errorMessage = error;
    }

    public List<Response> getErrorMessage() {
	return errorMessage;
    }

    public List<String> getResponse() {
	return response;
    }

    public boolean isStatus() {
	return status;
    }

    public void setErrorMessage(List<Response> errorMessage) {
	this.errorMessage = errorMessage;
    }

    public void setResponse(List<String> response) {
	this.response = response;
    }

    public void setStatus(boolean status) {
	this.status = status;
    }

}
