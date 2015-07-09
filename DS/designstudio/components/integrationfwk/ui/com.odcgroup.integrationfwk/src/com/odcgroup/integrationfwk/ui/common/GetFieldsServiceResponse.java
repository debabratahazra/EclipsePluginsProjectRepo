package com.odcgroup.integrationfwk.ui.common;

import java.util.List;

import com.odcgroup.integrationfwk.ui.model.Field;

//This class represents the field response from any Landscape /Flow service.
//This class returns response of Field type while
//ServiceRespose which is generic  returns responses of string type.
//This contains the status indicator indicating if the service call was success or failure
//and if success contains the return response data.

public class GetFieldsServiceResponse {

    private final boolean status;
    private final List<Field> response;
    private final String errorMessage;

    public GetFieldsServiceResponse(boolean indicator, List<Field> response,
	    String error) {
	status = indicator;
	this.response = response;
	errorMessage = error;
    }

    public String getErrorMessage() {
	return errorMessage;
    }

    public List<Field> getResponse() {
	return response;
    }

    public boolean isStatus() {
	return status;
    }
}
