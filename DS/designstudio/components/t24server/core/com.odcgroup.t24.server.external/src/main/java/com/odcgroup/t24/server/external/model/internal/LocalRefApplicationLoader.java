package com.odcgroup.t24.server.external.model.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.model.LocalRefApplicationDetail;
import com.odcgroup.t24.server.external.util.T24AgentConnection;
import com.odcgroup.t24.server.external.util.T24AgentConnection.T24Response;
import com.odcgroup.t24.server.external.util.T24AgentConnectionHelper;

/**
 * 
 * TODO: Document me!
 * @author hdebabrata
 *
 */
public class LocalRefApplicationLoader extends ExternalLoader {
	
	private static final String GET_LOCALREF_APPLICATION_TABLE_AS_XML = "T24CatalogServiceImpl.getLocalRefTableAsXml";
	private static final String GET_LOCALREF_APPLICATION_TABLE_LIST = "T24CatalogServiceImpl.getLocalRefTableObjects";
	
	protected LocalRefApplicationLoader(Properties properties) {
		super(properties);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IExternalObject> List<T> getObjectDetails()
			throws T24ServerException {
		
		T24AgentConnection connection = getConnection();
		if (connection == null) {
			throw new T24ServerException(
					"There is no connection with the T24 server.");
		}

		List<LocalRefApplicationDetail> localRefApps = new ArrayList<LocalRefApplicationDetail>();

		T24Response responseTableList = connection.call(GET_LOCALREF_APPLICATION_TABLE_LIST, null, null);
		String tableLists = responseTableList.getValue(0);
		T24AgentConnectionHelper.checkForValidLicense(responseTableList.getParameters());
		StringTokenizer strTokenTableList = new StringTokenizer(tableLists, "#");
		List<String> tableList = new ArrayList<String>(strTokenTableList.countTokens());
		while (strTokenTableList.hasMoreElements()) {
			tableList.add((String)strTokenTableList.nextElement());
		}
		
		for (Iterator<String> iterator = tableList.iterator(); iterator.hasNext();) {
			String table = (String) iterator.next();
			
			T24Response response = connection.call(GET_LOCALREF_APPLICATION_TABLE_AS_XML, table, null,null);
			String tableName = response.getValue(0);
			StringTokenizer localRefNameST = new StringTokenizer(tableName,
					RESPONSE_SEPARATOR);
			while (localRefNameST.hasMoreElements()) {
				String elementName = localRefNameST.nextElement().toString();
				LocalRefApplicationDetail localRefDetail = new LocalRefApplicationDetail(elementName);
				localRefApps.add(localRefDetail);
			}
		}
		
		return (List<T>) localRefApps;
	}
	

	@Override
	public <T extends IExternalObject> String getObjectAsXML(T detail)
			throws T24ServerException {

		T24AgentConnection connection = getConnection();
		if (connection == null) {throw new T24ServerException(
					"There is no connection with the T24 server."); //$NON-NLS-1$
		}

		String LocalRefApplicationName = detail.getName();
		T24Response response = connection.call(GET_LOCALREF_APPLICATION_TABLE_AS_XML, LocalRefApplicationName, null, null);
		return response.getValue(1);
	}
}
