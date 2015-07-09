package com.odcgroup.t24.server.external.model.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.odcgroup.t24.server.external.model.ApplicationDetail;
import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.util.T24WebServiceConnectionHelper;
import com.temenos.services.catalog.data.response.xsd.GetApplicationAsXmlResponse;
import com.temenos.services.catalog.data.response.xsd.GetDomainObjectsResponse;
import com.temenos.services.catalog.data.xsd.MetaDataXML;


/**
 * @author ssreekanth
 *
 */
class ApplicationWebServiceLoader extends AbstractCatalogServiceWebServiceLoader {
	
	public ApplicationWebServiceLoader (Properties props) {
		super(props);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends IExternalObject> List<T> getObjectDetails() throws T24ServerException {
		if (catalogPort == null) {
			throw new T24ServerException("Catalog client is not instantiated, Aborting Request!");
		}

		GetDomainObjectsResponse response = catalogPort.getDomainObjects(userDetails, null, null);
		
		// Check Response Details First
		T24WebServiceConnectionHelper.checkResponseDetails(response.getResponseDetails().getValue());
		
		// Continue
		String[] domainObjects = response.getDomainObjects().get(0).getValue().getValue().split(RESPONSE_SEPARATOR);
		String[] components = response.getComponentDetails().get(0).getValue().getValue().split(RESPONSE_SEPARATOR);
		String[] modules = response.getModuleDetails().get(0).getValue().getValue().split(RESPONSE_SEPARATOR);
	
		List<ApplicationDetail> applications = new ArrayList<ApplicationDetail>();
		int index = 0;
		while (index < domainObjects.length) {
			String application = domainObjects[index];
			String component = components[index];
			String module = modules[index];
			ApplicationDetail appDetail = new ApplicationDetail(application, component, module,"");
			applications.add(appDetail);
			index++;
		}
		return (List<T>)applications;
	}

	@Override
	public <T extends IExternalObject> String getObjectAsXML(T detail) throws T24ServerException {
		checkConnection();
		
		try {
			String applicationName = detail.getName();
			GetApplicationAsXmlResponse response = catalogPort.getApplicationAsXml(userDetails, applicationName);

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
