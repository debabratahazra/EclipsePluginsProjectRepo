package com.odcgroup.t24.server.external.model.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.t24.server.external.model.ApplicationDetail;
import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.util.ServerPropertiesHelper;
import com.odcgroup.t24.server.external.util.T24AgentConnection;
import com.odcgroup.t24.server.external.util.T24AgentConnection.T24Response;
import com.odcgroup.t24.server.external.util.T24AgentConnectionHelper;

class ApplicationLoader extends ExternalLoader {

	private static final String GET_APPLICATION_DETAILS = "T24CatalogServiceImpl.getDomainObjects";
	private static final String GET_APPLICATION_DETAILS_2 = "T24CatalogServiceImpl.getDomainObjects2";
	private static final String GET_APPLICATION_AS_XML= "T24CatalogServiceImpl.getApplicationAsXml";
	public ApplicationLoader(Properties properties) {
		super(properties);
	}

	@SuppressWarnings("unchecked")
	@Override
	public  <T extends IExternalObject> List<T> getObjectDetails() throws T24ServerException {
		T24AgentConnection connection = getConnection();
		if (connection == null) {
			throw new T24ServerException("There is no connection with the T24 server.");
		}
		
		List<ApplicationDetail> applications = new ArrayList<ApplicationDetail>();
		Integer catalogVersion = ServerPropertiesHelper.getT24CatalogServiceVersion(properties);
	    T24Response response = null;
		StringTokenizer appDescriptionST = null;
		if( catalogVersion != null && catalogVersion.intValue() == 1){
			 response = connection.call(GET_APPLICATION_DETAILS, null, null, null, null);
		}else if( catalogVersion != null && catalogVersion.intValue() == 2 ){
			 response = connection.call(GET_APPLICATION_DETAILS_2, null, null, null, null,null); 
			 String appDescription = response.getValue(3);
			 appDescription = appDescription.replace(RESPONSE_SEPARATOR,
						RESPONSE_SEPARATOR+" ").trim();
			 appDescriptionST= new StringTokenizer(appDescription, RESPONSE_SEPARATOR);
		} else {
			throw new T24ServerException("Catalog Service version is not proper, valid values 1 or 2");
		}
		T24AgentConnectionHelper.checkForValidLicense(response.getParameters());
		String productName = response.getValue(0);
		String compName = response.getValue(1);
		String appName = response.getValue(2);
		
		StringTokenizer productST = new StringTokenizer(productName, RESPONSE_SEPARATOR);
		StringTokenizer compST = new StringTokenizer(compName, RESPONSE_SEPARATOR);
		StringTokenizer appST = new StringTokenizer(appName, RESPONSE_SEPARATOR);
		while (productST.hasMoreElements()) {
			String appDescriptionStr = ""; 
			try{
				if(appDescriptionST !=null){
				    appDescriptionStr = appDescriptionST.nextElement().toString();
				    if(!StringUtils.isWhitespace(appDescriptionStr)){
				    	appDescriptionStr = appDescriptionStr.trim();
				    }
				}
			}catch(NoSuchElementException e){
				
			}
			ApplicationDetail applicationDetail = new ApplicationDetail(
					appST.nextElement().toString(),
					compST.nextElement().toString(), 
					productST.nextElement().toString(),appDescriptionStr); 
			applications.add(applicationDetail);
		}
		return (List<T>)applications;
	}

	@Override
	public  <T extends IExternalObject> String getObjectAsXML(T detail) throws T24ServerException {
		T24AgentConnection connection = getConnection();
		if (connection == null) {
			throw new T24ServerException("There is no connection with the T24 server."); //$NON-NLS-1$
		}

		String applicationName = detail.getName();
		T24Response response = connection.call(GET_APPLICATION_AS_XML, applicationName, null, null); 
		return response.getValue(1);
	}

}
