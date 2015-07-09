package com.odcgroup.integrationfwk.ui.utils;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.integrationfwk.ui.TWSConsumerPluginException;
import com.odcgroup.integrationfwk.ui.model.Operation;
import com.odcgroup.integrationfwk.ui.model.Parameter;
import com.odcgroup.integrationfwk.ui.model.Service;

public class XmlParser {
	// TODO remove below given hard coded path. get the Xml from T24 and save it
	// in new Integration project, replace below given path with that or get xml
	// from T24 and pass them to dom parser.
	private static String SERVICE = "service";
	private static String OPERATION = "operation";
	private static String PARAMETER = "parameter";
	private static String ATTRIBUTE_NAME = "name";
	private static String FLOW_SUPPORTABLE = "integrationFlowSupportable";
	private static String IS_PRIMITIVE = "isPrimitive";
	private static String IS_COLLECTION = "isCollection";
	private static String IS_MANDATORY = "isMandatory";
	private static String DIRECTION = "direction";

	public static List<Service> getServices(String filePath) {
		try {
			List<Service> services = new ArrayList<Service>();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(filePath);
			NodeList list = (doc).getElementsByTagName(SERVICE);
			for (int index = 0; index < list.getLength(); index++) {
				Node nNode = list.item(index);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Service service = new Service();
					Element eElement = (Element) nNode;
					service.setComponentService(eElement
							.getAttribute(ATTRIBUTE_NAME));
					NodeList operations = eElement
							.getElementsByTagName(OPERATION);
					for (int index1 = 0; index1 < operations.getLength(); index1++) {
						Operation operation = new Operation();
						Node operationNode = operations.item(index1);
						eElement = (Element) operationNode;
						if (!Boolean.parseBoolean(eElement.getAttribute(FLOW_SUPPORTABLE))) {
							continue;
						}
						operation.setOperation(eElement
								.getAttribute(ATTRIBUTE_NAME));
						NodeList parameters = eElement
								.getElementsByTagName(PARAMETER);
						for (int paramList = 0; paramList < parameters
								.getLength(); paramList++) {
							Parameter parameter = new Parameter();
							Node paramNode = parameters.item(paramList);
							eElement = (Element) paramNode;
							parameter.setName(eElement
									.getAttribute(ATTRIBUTE_NAME));
							parameter.setTypeName(eElement
									.getAttribute("typeName"));
							parameter.setIsPrimitive(eElement
									.getAttribute(IS_PRIMITIVE));
							parameter.setIsCollection(eElement
									.getAttribute(IS_COLLECTION));
							parameter.setIsMandatory(eElement
									.getAttribute(IS_MANDATORY));
							parameter.setDirection(eElement
									.getAttribute(DIRECTION));
							operation.addParameter(parameter);
						}
						service.addOperations(operation);
					}
					if (service.getOperations().size() > 0) {
						services.add(service);
					}
				}
			}
			return services;
		} catch (Exception e) {
			new TWSConsumerPluginException("Error occurred parsing schema.", e);
			return new ArrayList<Service>();
		}
	}
}
