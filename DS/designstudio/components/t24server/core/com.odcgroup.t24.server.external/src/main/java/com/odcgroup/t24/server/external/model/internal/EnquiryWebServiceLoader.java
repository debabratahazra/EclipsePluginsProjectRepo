package com.odcgroup.t24.server.external.model.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.odcgroup.t24.server.external.model.EnquiryDetail;
import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.util.T24WebServiceConnectionHelper;
import com.temenos.services.catalog.data.response.xsd.GetEnquiryAsXmlResponse;
import com.temenos.services.catalog.data.response.xsd.GetEnquiryObjectsResponse;
import com.temenos.services.catalog.data.xsd.MetaDataXML;

/**
 * @author ssreekanth
 *
 */
class EnquiryWebServiceLoader extends AbstractCatalogServiceWebServiceLoader {
	
	public EnquiryWebServiceLoader(Properties properties) {
		super(properties);
	}

	@SuppressWarnings("unchecked")
	@Override
	public  <T extends IExternalObject> List<T> getObjectDetails() throws T24ServerException {
		if (catalogPort == null) {
			throw new T24ServerException("There is no connection with the T24 server.");
		}
		
		GetEnquiryObjectsResponse response = catalogPort.getEnquiryObjects(userDetails, null, null, null);
		
		// Check Response Details First
		T24WebServiceConnectionHelper.checkResponseDetails(response.getResponseDetails().getValue());
		
		// Continue
		String[] enquiryResponse = response.getEnquiryObjects().get(0).getValue().getValue().split(RESPONSE_SEPARATOR);
		String[] componentResponse = response.getComponentDetails().get(0).getValue().getValue().split(RESPONSE_SEPARATOR);
		String[] moduleResponse = response.getModuleDetails().get(0).getValue().getValue().split(RESPONSE_SEPARATOR);
		List<EnquiryDetail> enquiries = new ArrayList<EnquiryDetail>();
		int index = 0;
		while (index < moduleResponse.length) {
			String enquiryName = enquiryResponse[index];
			String component = componentResponse[index];
			String module = moduleResponse[index];
			EnquiryDetail enquiryDetail = new EnquiryDetail(enquiryName, component, module,"");
			enquiries.add(enquiryDetail);
			index++;
		}

		return (List<T>) enquiries;
	}

	@Override
	public <T extends IExternalObject> String getObjectAsXML(T detail) throws T24ServerException {
		checkConnection();

		try {
			String enquiryName = detail.getName();
			GetEnquiryAsXmlResponse response = catalogPort.getEnquiryAsXml(userDetails, enquiryName);

			// Check Response Details First
			T24WebServiceConnectionHelper.checkResponseDetails(response.getResponseDetails().getValue());
			
			// Continue
			MetaDataXML metaXml = response.getXml().getValue();
			return metaXml.getValue().getValue();
		} catch (Exception ex) {
			throw new T24ServerException(ex.getMessage() + "\nThe XML stream cannot be downloaded from the server", ex);
		}
		
	}

}
