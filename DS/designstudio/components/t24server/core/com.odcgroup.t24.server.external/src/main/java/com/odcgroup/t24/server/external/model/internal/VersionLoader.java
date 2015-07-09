package com.odcgroup.t24.server.external.model.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.model.VersionDetail;
import com.odcgroup.t24.server.external.util.ServerPropertiesHelper;
import com.odcgroup.t24.server.external.util.T24AgentConnection;
import com.odcgroup.t24.server.external.util.T24AgentConnection.T24Response;
import com.odcgroup.t24.server.external.util.T24AgentConnectionHelper;

class VersionLoader extends ExternalLoader  {

	private static final String GET_VERSION_DETAILS = "T24CatalogServiceImpl.getVersionObjects";
	private static final String GET_VERSION_DETAILS_2 = "T24CatalogServiceImpl.getVersionObjects2";
	private static final String GET_VERSION_AS_XML= "T24CatalogServiceImpl.getVersionAsXml";
	public VersionLoader(Properties props) {
		super(props);
	}

	@SuppressWarnings("unchecked")
	@Override
	public  <T extends IExternalObject> List<T> getObjectDetails() throws T24ServerException {
		
		T24AgentConnection connection = getConnection();
		if (connection == null) {
			throw new T24ServerException("There is no connection with the T24 server.");
		}
		
	    Integer catalogVersion = ServerPropertiesHelper.getT24CatalogServiceVersion(properties);
		T24Response response = null;
		StringTokenizer descriptionST = null;
		if( catalogVersion != null && catalogVersion.intValue() == 1){
		    response = connection.call(GET_VERSION_DETAILS, null, null, null, null, null); 
		}else if( catalogVersion != null && catalogVersion.intValue() == 2){
			 response = connection.call(GET_VERSION_DETAILS_2, null, null, null, null, null,null); 
			 String descriptionResponse = response.getValue(4);
			 descriptionResponse = descriptionResponse.replace(RESPONSE_SEPARATOR,
						RESPONSE_SEPARATOR+" ").trim();
			 descriptionST= new StringTokenizer(descriptionResponse, RESPONSE_SEPARATOR);
		} else {
			throw new T24ServerException("Catalog Service version is not proper, valid values 1 or 2.");
		}
		
		T24AgentConnectionHelper.checkForValidLicense(response.getParameters());
		String moduleResponse = response.getValue(0);
		String componentResponse = response.getValue(1);
		String versionResponse = response.getValue(3);
		StringTokenizer moduleST = new StringTokenizer(moduleResponse, RESPONSE_SEPARATOR);
		StringTokenizer compST = new StringTokenizer(componentResponse, RESPONSE_SEPARATOR);
		StringTokenizer verST = new StringTokenizer(versionResponse, RESPONSE_SEPARATOR);
		
		List<VersionDetail> versions = new ArrayList<VersionDetail>();
		while (moduleST.hasMoreElements()) {
			
			String ver = verST.nextElement().toString();
			
			String component = "";
			try {
				component = compST.nextElement().toString();
			} catch (NoSuchElementException ex) {
				// ignore;
			}
			String module = "";
			try {
				module = moduleST.nextElement().toString();
			} catch (NoSuchElementException ex) {
				// ignore;
			}
			String description = "";
			try {
				if(descriptionST !=null){
				  description = descriptionST.nextElement().toString();
				  if(!StringUtils.isWhitespace(description)){
				    	description = description.trim();
				    }
				}
			} catch (NoSuchElementException ex) {
				// ignore;
			}
			String application;
			int pos = ver.indexOf(',');
			if (pos > -1) {
				application = ver.substring(0, pos);
			} else {
				application = ver;
			}
			String versionShortName = ver.substring(pos+1);
			
			VersionDetail versionDetail = new VersionDetail(versionShortName, component, module, application,description);
			versions.add(versionDetail);
		}
		
		return (List<T>)versions;
	}

	@Override
	public  <T extends IExternalObject> String getObjectAsXML(T detail) throws T24ServerException {
		T24AgentConnection connection = getConnection();
		if (connection == null) {
			throw new T24ServerException("There is no connection with the T24 server.");
		}
		
		VersionDetail versionDetail = (VersionDetail) detail;
		String versionName = versionDetail.getApplication()+","+versionDetail.getName();
		T24Response response = connection.call(GET_VERSION_AS_XML, versionName, null, null); 
		return response.getValue(1);
	}
	
}
