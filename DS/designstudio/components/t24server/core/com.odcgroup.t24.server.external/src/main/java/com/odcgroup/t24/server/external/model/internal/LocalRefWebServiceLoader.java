package com.odcgroup.t24.server.external.model.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.model.LocalRefDetail;
import com.odcgroup.t24.server.external.util.T24WebServiceConnectionHelper;
import com.temenos.services.catalog.data.response.xsd.GetLocalTableAsXmlResponse;
import com.temenos.services.catalog.data.response.xsd.GetLocalTableObjectsResponse;
import com.temenos.services.catalog.data.xsd.MetaDataXML;

public class LocalRefWebServiceLoader extends AbstractCatalogServiceWebServiceLoader {

	public LocalRefWebServiceLoader(Properties properties) {
		super(properties);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IExternalObject> List<T> getObjectDetails() throws T24ServerException {
		if (catalogPort == null) {
			throw new T24ServerException("There is no connection with the T24 server.");
		}
		
		GetLocalTableObjectsResponse response = catalogPort.getLocalTableObjects(userDetails);
		
		// Check Response Details First
		T24WebServiceConnectionHelper.checkResponseDetails(response.getResponseDetails().getValue());
		
		// extract names
		List<LocalRefDetail> localRefs = new ArrayList<LocalRefDetail>();
		List<String> names = response.getLocalTableList();
		if (names != null && names.size() > 0) {
			StringTokenizer localRefNameST = new StringTokenizer(names.get(0), RESPONSE_SEPARATOR);
			while (localRefNameST.hasMoreElements()) {
				String elementName = localRefNameST.nextElement().toString();
				LocalRefDetail localRefDetail = new LocalRefDetail(elementName);
				localRefs.add(localRefDetail);
			}
		}
		return (List<T>) localRefs;

	}

	@Override
	public <T extends IExternalObject> String getObjectAsXML(T detail) throws T24ServerException {
		checkConnection();

		try {
			String name = detail.getName();
			GetLocalTableAsXmlResponse response = catalogPort.getLocalTableAsXml(userDetails, name);

			// Check Response Details First
			T24WebServiceConnectionHelper.checkResponseDetails(response.getResponseDetails().getValue());
			
			// Continue
			MetaDataXML metaXml = response.getLocalTableXml().getValue();
			return metaXml.getValue().getValue();
		} catch (Exception ex) {
			throw new T24ServerException(ex.getMessage() + "\nThe XML stream cannot be downloaded from the server", ex);
		}
		
	}

}
