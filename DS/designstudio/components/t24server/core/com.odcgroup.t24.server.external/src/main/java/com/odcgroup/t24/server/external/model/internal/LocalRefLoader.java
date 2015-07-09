package com.odcgroup.t24.server.external.model.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.model.LocalRefDetail;
import com.odcgroup.t24.server.external.util.T24AgentConnection;
import com.odcgroup.t24.server.external.util.T24AgentConnection.T24Response;

class LocalRefLoader extends ExternalLoader {

	private static final String GET_LOCALREF_TABLES = "T24CatalogServiceImpl.getLocalTableObjects";
	private static final String GET_LOCALREF_AS_XML = "T24CatalogServiceImpl.getLocalTableAsXml";

	public LocalRefLoader(Properties properties) {
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

		List<LocalRefDetail> localRefs = new ArrayList<LocalRefDetail>();

		T24Response response = connection.call(GET_LOCALREF_TABLES, null, null);
		String localRefName = response.getValue(0);

		StringTokenizer localRefNameST = new StringTokenizer(localRefName,
				RESPONSE_SEPARATOR);
		while (localRefNameST.hasMoreElements()) {
			String elementName = localRefNameST.nextElement().toString();
			LocalRefDetail localRefDetail = new LocalRefDetail(elementName);
			localRefs.add(localRefDetail);
		}
		return (List<T>) localRefs;
	}

	@Override
	public <T extends IExternalObject> String getObjectAsXML(T detail)
			throws T24ServerException {
		T24AgentConnection connection = getConnection();
		if (connection == null) {throw new T24ServerException(
					"There is no connection with the T24 server."); //$NON-NLS-1$
		}

		String applicationName = detail.getName();
		T24Response response = connection.call(GET_LOCALREF_AS_XML, applicationName, null, null);
		return response.getValue(1);
	}

}
