package com.odcgroup.t24.server.external.model.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.model.VersionDetail;
import com.odcgroup.t24.server.external.util.T24WebServiceConnectionHelper;
import com.temenos.services.catalog.data.response.xsd.GetVersionAsXmlResponse;
import com.temenos.services.catalog.data.response.xsd.GetVersionObjectsResponse;
import com.temenos.services.catalog.data.xsd.MetaDataXML;

class VersionWebServiceLoader extends AbstractCatalogServiceWebServiceLoader {

	public VersionWebServiceLoader(Properties properties) {
		super(properties);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IExternalObject> List<T> getObjectDetails()
			throws T24ServerException {
		if (catalogPort == null) {
			throw new T24ServerException("There is no connection with the T24 server.");
		}
		
		GetVersionObjectsResponse response = catalogPort.getVersionObjects(userDetails, null, null, null);
		
		// Check Response Details First
		T24WebServiceConnectionHelper.checkResponseDetails(response.getResponseDetails().getValue());
		
		// Continue
		String[] versionResponse = response.getVersionObects().get(0).getValue().getValue().split(RESPONSE_SEPARATOR);
		String[] componentResponse = response.getComponentDetails().get(0).getValue().getValue().split(RESPONSE_SEPARATOR);
		String[] moduleResponse = response.getModuleDetails().get(0).getValue().getValue().split(RESPONSE_SEPARATOR);
	

		List<VersionDetail> versions = new ArrayList<VersionDetail>();
		int index = 0;
		while (index < moduleResponse.length) {
			String ver = versionResponse[index];
			String component = componentResponse[index];
			String module = moduleResponse[index];
			int pos = ver.indexOf(',');
			boolean validVersion = ver.contains(",");
			if(!validVersion) {
				throw new T24ServerException("The version "+ver+" should be suffixed with ',' in absense of shortname.");
			}		
			String version = ver.substring(pos + 1);
			String application = ver.substring(0, pos);
          
			VersionDetail versionDetail = new VersionDetail(version, component, module, application,"");
			versions.add(versionDetail);
			index++;
		}

		return (List<T>) versions;

	}

	public <T extends IExternalObject> String getObjectAsXML(T detail) throws T24ServerException {
		checkConnection();

		try {
			VersionDetail versionDetail = (VersionDetail) detail;
			String versionName = versionDetail.getApplication() + "," + versionDetail.getName();
			GetVersionAsXmlResponse response = catalogPort.getVersionAsXml(userDetails, versionName);
	
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
