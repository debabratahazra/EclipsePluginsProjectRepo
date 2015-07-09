package com.odcgroup.t24.server.external.model.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import com.odcgroup.t24.server.external.model.AAProductsDetails;
import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.util.T24AgentConnection;
import com.odcgroup.t24.server.external.util.T24AgentConnection.T24Response;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public class AAProductsLoader extends ExternalLoader {
	
	private static final String SEPARATOR = "&%@";
	
	/**
	 * @param properties
	 */
	protected AAProductsLoader(Properties properties) {
		super(properties);
	}

	private static final String GET_AA_PRODUCTS_LINE = "T24CatalogServiceImpl.getAAProductLine";
	private static final String GET_AA_PRODUCTS_LINE_PROPERTY= "T24CatalogServiceImpl.getAAProductLinePropertyClass";
	

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IExternalObject> List<T> getObjectDetails() throws T24ServerException {

		T24AgentConnection connection = getConnection();
		if (connection == null) {
			throw new T24ServerException("There is no connection with the T24 server.");
		}

		List<AAProductsDetails> aaProductDetails = new ArrayList<AAProductsDetails>();
		T24Response responseTableList = connection.call(GET_AA_PRODUCTS_LINE, null, null);
		String productLists = responseTableList.getValue(0);
		StringTokenizer strTokenProductList = new StringTokenizer(productLists, "#");
		while (strTokenProductList.hasMoreElements()) {
			T24Response response = connection.call(GET_AA_PRODUCTS_LINE_PROPERTY, (String) strTokenProductList.nextElement(), null, null, null);
			if(response.getValue(1).isEmpty() || response.getValue(2).isEmpty()){
				continue;
			}
			String properties = response.getValue(1);
			StringTokenizer strTokenProperties = new StringTokenizer(properties, "#");
			AAProductsDetails aaProductDetail = new AAProductsDetails(response.getValue(0), strTokenProperties.countTokens());
			aaProductDetails.add(aaProductDetail);
		}

		return (List<T>) aaProductDetails;
	}

	@Override
	public <T extends IExternalObject> String getObjectAsXML(T detail) throws T24ServerException {
		
		T24AgentConnection connection = getConnection();
		if (connection == null) {throw new T24ServerException(
					"There is no connection with the T24 server."); //$NON-NLS-1$
		}

		String aaProductsName = detail.getName();
		T24Response response = connection.call(GET_AA_PRODUCTS_LINE_PROPERTY, aaProductsName, null, null, null);
		return response.getValue(1) + SEPARATOR + response.getValue(2);
	}

	/**
	 * @return the separator
	 */
	public static String getSeparator() {
		return SEPARATOR;
	}

}
