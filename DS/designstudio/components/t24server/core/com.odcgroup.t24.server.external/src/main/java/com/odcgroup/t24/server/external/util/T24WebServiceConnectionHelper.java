package com.odcgroup.t24.server.external.util;

import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.temenos.soa.services.data.xsd.Response;
import com.temenos.soa.services.data.xsd.ResponseDetails;

public class T24WebServiceConnectionHelper {
	
	public static void checkResponseDetails(ResponseDetails responseDetails) throws T24ServerException {
		if (!responseDetails.getReturnCode().getValue().equals("SUCCESS") ) {
			String responseDetailString = getResponseDetailsAsString(responseDetails) ;
			LicenseValidator.checkValidLicense(responseDetailString);
			throw new T24ServerException(responseDetailString);
		}
	}

	private static String getResponseDetailsAsString(ResponseDetails responseDetails) {
		StringBuilder sb = new StringBuilder("Subroutine: ").append(responseDetails.getServiceName().getValue()).append("\n");
		sb.append("Return Code: ").append(responseDetails.getReturnCode().getValue()).append("\n");
		sb.append("Response size: ").append(responseDetails.getResponses().size()).append("\n");
		if (responseDetails.getResponses().size() > 0) {
			int i = 1;
			for (Response res : responseDetails.getResponses()) {
				sb.append("Response ").append(i).append(" ->");
				sb.append("Response Code: ").append(res.getResponseCode().getValue()).append(",");
				sb.append("Response Type: ").append(res.getResponseType().getValue()).append(",");
				sb.append("Response Text: ").append(res.getResponseText().getValue()).append(",");
				sb.append("Response Info: ").append(res.getResponseInfo().getValue()).append(",").append("\n");
				i++;
			}
		}
		return sb.toString();
	}


}
