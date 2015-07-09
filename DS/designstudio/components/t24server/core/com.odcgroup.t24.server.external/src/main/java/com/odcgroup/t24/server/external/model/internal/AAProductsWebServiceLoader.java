package com.odcgroup.t24.server.external.model.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import com.odcgroup.t24.server.external.model.AAProductsDetails;
import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.util.T24WebServiceConnectionHelper;
import com.temenos.services.catalog.data.response.xsd.GetAAProductLinePropertyClassResponse;
import com.temenos.services.catalog.data.response.xsd.GetAAProductLineResponse;
import com.temenos.services.catalog.data.xsd.ReturnList;

class AAProductsWebServiceLoader extends AbstractCatalogServiceWebServiceLoader {

	private static final String SEPARATOR = "&%@";
	
	public AAProductsWebServiceLoader(Properties properties) {
		super(properties);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IExternalObject> List<T> getObjectDetails()
			throws T24ServerException {
		if (catalogPort == null) {
			throw new T24ServerException("There is no connection with the T24 server.");
		}
		
		GetAAProductLineResponse response = catalogPort.getAAProductLine(userDetails);
		
		// Check Response Details First
		T24WebServiceConnectionHelper.checkResponseDetails(response.getResponseDetails().getValue());
		
		// Continue
		String[] productResponse = response.getProductLineObjects().get(0).getValue().getValue().split(RESPONSE_SEPARATOR);

		List<AAProductsDetails> products = new ArrayList<AAProductsDetails>();
		int index = 0;
		while (index < productResponse.length) {
			String product = productResponse[index];
			GetAAProductLinePropertyClassResponse response2 = catalogPort.getAAProductLinePropertyClass(userDetails, product);
			try {
				T24WebServiceConnectionHelper.checkResponseDetails(response2.getResponseDetails().getValue());
				List<ReturnList> returnList = response2.getPropertyClassApplications();
				if (returnList != null && returnList.size() > 0) {
					String properties = returnList.get(0).getValue().getValue();
					StringTokenizer strTokenProperties = new StringTokenizer(properties, "#");
					AAProductsDetails aaProductDetail = new AAProductsDetails(product, strTokenProperties.countTokens());
					products.add(aaProductDetail);
				}
			} catch (T24ServerException ex) {
				// ignore this entries
				ex.printStackTrace();
			}

			index++;
		}

		return (List<T>) products;

	}

	public <T extends IExternalObject> String getObjectAsXML(T detail)
			throws T24ServerException {
		checkConnection();

		AAProductsDetails productDetail = (AAProductsDetails) detail;
		String productName = productDetail.getName();
		GetAAProductLinePropertyClassResponse response = catalogPort.getAAProductLinePropertyClass(userDetails, productName);
		
		// Check Response Details First
		T24WebServiceConnectionHelper.checkResponseDetails(response.getResponseDetails().getValue());
		
		String AppList = response.getPropertyClassApplications().get(0).getValue().getValue();
		
		String CompList = response.getPropertyclassModule().get(0).getValue().getValue();
		
		return AppList + SEPARATOR + CompList;
		
	}

}
