package com.odcgroup.integrationfwk.ui.services.connectivity;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import catalogservicews.CatalogServiceWS;
import catalogservicews.CatalogServiceWSPortType;

import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.utils.FileUtil;
import com.temenos.services.catalog.data.response.xsd.GetServicesResponse;
import com.temenos.soa.services.data.xsd.ObjectFactory;
import com.temenos.soa.services.data.xsd.T24UserDetails;

/**
 * Responsible for providing the component services operation in web-services
 * type of connection.
 * 
 * @author sbharathraja
 * 
 */
public class ComponentServices {

    /** component service port proxy */
    private CatalogServiceWSPortType serviceProxy;
    /** T24 user details instance */
    private T24UserDetails t24UserDetails;
    /** component service metadata file name. */
    private final String COMPONENT_SERVICE_FILE = "componentService.xml";

    /**
     * constructs the {@link ComponentServices} using the given details.
     *
     * @param componentServiceUrl
     * @param userName
     * @param password
     */
    public ComponentServices(String componentServiceUrl, String userName,
	    String password) {
	initService(componentServiceUrl);
	initT24UserDetails(userName, password);
    }

    // TODO: Bharath - Component Framework team would change the behavior of
    // getServices() method. Keep an eye on those changes and redesign this
    // based on the need.
    /**
     * Helps to get the component services from the service repository as xml
     * and store it in given path.
     *
     * @param filePath
     */
    public boolean getComponentServices(String filePath) {
	// happening only in exception scenario
	if (serviceProxy == null || t24UserDetails == null) {
	    return false;
	}
	GetServicesResponse response = serviceProxy.getServices(t24UserDetails);
	if (response.getResponseDetails().getValue().getReturnCode()
		.equals("FAILURE")) {
	    return false;

	} else {
	    String serviceXml = response.getMetaDataXML().getValue().getValue()
		    .getValue();
	    // xml could be empty if in case of any exception occurs while
	    // trying to
	    // retrieve the metadata!
	    if (serviceXml == null || serviceXml.length() == 0) {
		return false;
	    }
	    FileUtil.doXmlFileWrite(serviceXml, COMPONENT_SERVICE_FILE,
		    filePath);
	    return true;
	}
    }

    /**
     * Helps to initialize the component service proxy using given url.
     *
     * @param url
     *            - component service url.
     */
    private void initService(String url) {
	URL wsdlUrl = null;
	try {
	    wsdlUrl = new URL(url);
	    CatalogServiceWS catalogServiceWs = new CatalogServiceWS(wsdlUrl,
		    new QName("http://CatalogServiceWS", "CatalogServiceWS"));
	    serviceProxy = catalogServiceWs
		    .getCatalogServiceWSHttpSoap11Endpoint();
	} catch (MalformedURLException e) {
	    TWSConsumerLog
		    .logError("Catalog Service Url is not well formed", e);
	}
    }

    /**
     * Helps to initialize the T24 user details using given details.
     *
     * @param userName
     *            - T24 sign-on user name.
     * @param password
     *            - T24 sign-on password.
     */
    private void initT24UserDetails(String userName, String password) {
	ObjectFactory factory = new ObjectFactory();
	t24UserDetails = new com.temenos.soa.services.data.xsd.T24UserDetails();
	t24UserDetails.setUser(factory.createT24UserDetailsUser(userName));
	t24UserDetails.setPassword(factory
		.createT24UserDetailsPassword(password));
    }

}
