package com.odcgroup.t24.server.external.model.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.model.LocalRefApplicationDetail;
import com.odcgroup.t24.server.external.util.T24WebServiceConnectionHelper;
import com.temenos.services.catalog.data.response.xsd.GetLocalRefTableAsXmlResponse;
import com.temenos.services.catalog.data.response.xsd.GetLocalRefTableObjectsResponse;
import com.temenos.services.catalog.data.xsd.MetaDataXML;

/**
 * TODO: Document me!
 *
 * @author atripod
 *
 */
public class LocalRefApplicationWebServiceLoader  extends AbstractCatalogServiceWebServiceLoader {

	public LocalRefApplicationWebServiceLoader(Properties properties) {
		super(properties);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IExternalObject> List<T> getObjectDetails() throws T24ServerException {
		if (catalogPort == null) {
			throw new T24ServerException("There is no connection with the T24 server.");
		}
		
		GetLocalRefTableObjectsResponse response = catalogPort.getLocalRefTableObjects(userDetails);
		
		// Check Response Details First
		T24WebServiceConnectionHelper.checkResponseDetails(response.getResponseDetails().getValue());
		
		List<LocalRefApplicationDetail> localRefApps = new ArrayList<LocalRefApplicationDetail>();
		
		//extract data
		String tableLists = response.getLocalRefTableList().get(0);
		StringTokenizer strTokenTableList = new StringTokenizer(tableLists, "#");
		List<String> tableList = new ArrayList<String>(strTokenTableList.countTokens());
		while (strTokenTableList.hasMoreElements()) {
			tableList.add((String)strTokenTableList.nextElement());
		}
		
		for (String table : tableList) {
			LocalRefApplicationDetail localRefDetail = new LocalRefApplicationDetail(table);
			localRefApps.add(localRefDetail);
		}
		

		return (List<T>) localRefApps;
	}

	@Override
	public <T extends IExternalObject> String getObjectAsXML(T detail) throws T24ServerException {
		checkConnection();

		try {
			String name = detail.getName();
			GetLocalRefTableAsXmlResponse response = catalogPort.getLocalRefTableAsXml(userDetails, name);

			// Check Response Details First
			T24WebServiceConnectionHelper.checkResponseDetails(response.getResponseDetails().getValue());
			
			// Continue
			MetaDataXML metaXml = response.getLocalRefTableXml().getValue();
			return metaXml.getValue().getValue();
		} catch (Exception ex) {
			throw new T24ServerException(ex.getMessage() + "\nThe XML stream cannot be downloaded from the server", ex);
		}
	}

}
