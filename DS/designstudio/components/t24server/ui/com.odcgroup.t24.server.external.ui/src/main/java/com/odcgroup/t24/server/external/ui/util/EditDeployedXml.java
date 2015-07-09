package com.odcgroup.t24.server.external.ui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.t24.server.external.ui.T24ServerUIExternalCore;
import com.odcgroup.t24.server.external.ui.action.PostDeployAction;
import com.odcgroup.t24.server.external.ui.builder.T24DeployConsole;
import com.odcgroup.t24.server.external.util.IT24Connection;
import com.odcgroup.t24.server.external.util.ServerPropertiesHelper;
import com.odcgroup.t24.server.external.util.ServerPropertiesHelper.T24ConnectionProtocol;
import com.odcgroup.t24.server.external.util.T24AgentConnectionHelper;
import com.odcgroup.t24.server.external.util.T24DesignStudioInstallerService;

public class EditDeployedXml {

	private static final String SERVER_PROPERTIES_MESSAGE = "Unable to read the server properties.";
 	private static final String UNABLE_TO_CONNECT_TO_SERVER_MESSAGE = "Unable to connect to the T24 server.";
 	private static final String UNABLE_TO_WRAP_MESSAGE = "Unable to wrap {0}.";
	private static final String UNABLE_TO_DEPLOY_MESSAGE = "Unable to deploy {0} to {1}.";
	private static final String SUCCESS_MESSAGE = "{0} successfully modified on {2} in {1} ms.";

	/**
	 * Wrap the xml file with the specified header and send it to the server
	 * @param rootFolder 
	 * @throws T24ServerException 
	 */
	public void processGenXml(File rootFolder, List<String> t24XmlFiles) {
		T24DeployConsole deployConsole = T24ServerUIExternalCore.getDefault().getDeployBuilderConsole();
		IExternalServer externalServer = T24ServerUIExternalCore.getDefault().getExternalServer();
		if (externalServer == null)
			return; // No T24 external server found
		
		// Read the properties
		Properties serverProperties;
		try {
			serverProperties = externalServer.readPropertiesFile();
		} catch (IOException e) {
			deployConsole.printError(SERVER_PROPERTIES_MESSAGE, e);
			warnUserOfErrors("The main reason is: " + SERVER_PROPERTIES_MESSAGE);
			return;
		}
		
		IT24Connection connection = null;
		try {
			// Create connection
			try {
				connection = connect(serverProperties);
			} catch (T24ServerException e) {
				deployConsole.printError(UNABLE_TO_CONNECT_TO_SERVER_MESSAGE, e);
				warnUserOfErrors(UNABLE_TO_CONNECT_TO_SERVER_MESSAGE + "\nReason: " + e.getMessage());
				return;
			}
			
			int nbFailedWrapping = 0;
			int nbFailedDeploy = 0;
			String firstFailedWrappingErrorMessage = "";
			String firstFailedDeployedErrorMessage = "";
			
			for (String t24XmlFile: t24XmlFiles) {
				// Prepare the message
				String deployableXml;
				try {
					T24ConnectionProtocol protocol = ServerPropertiesHelper.getProtocol(serverProperties);
					if (protocol == T24ConnectionProtocol.WS) {
						deployableXml = wrapMessage(new FileInputStream(new File(rootFolder, t24XmlFile)),
								ServerPropertiesHelper.getUsername(serverProperties),
								ServerPropertiesHelper.getPassword(serverProperties),
								ServerPropertiesHelper.getBranch(serverProperties));
					} else {
						deployableXml = wrapMessage(new FileInputStream(new File(rootFolder, t24XmlFile)),
								ServerPropertiesHelper.getT24User(serverProperties),
								ServerPropertiesHelper.getT24Password(serverProperties),
								ServerPropertiesHelper.getBranch(serverProperties));	
					}
				} catch (Exception e) {
					String errorMessage = MessageFormat.format(UNABLE_TO_WRAP_MESSAGE, t24XmlFile);
					deployConsole.printError(errorMessage, e);
					nbFailedWrapping++;
					if (nbFailedWrapping == 1) {
						firstFailedWrappingErrorMessage = errorMessage;
					}
					continue;
				}
				
				// Deploy to T24 server
				String t24ServerName = ServerPropertiesHelper.getUsername(serverProperties);
				try {
					String xmlFileName = new File(t24XmlFile).getName();
					deployToServer(deployableXml, connection, xmlFileName, t24ServerName);
					// post deploy actions only for local-ref applications
					if (xmlFileName.startsWith("X_")) {
						PostDeployAction deployAction = new PostDeployAction();
						deployAction.postDeploy(new File(t24XmlFile));
					}
				} catch (T24ServerException e) {
					String errorMessage = MessageFormat.format(UNABLE_TO_DEPLOY_MESSAGE, t24XmlFile, t24ServerName);
					deployConsole.printError(errorMessage, e);
					nbFailedDeploy++;
					if (nbFailedDeploy == 1) {
						firstFailedDeployedErrorMessage = errorMessage;
					}
					continue;
				}
			}
			if (nbFailedWrapping != 0 || nbFailedDeploy != 0) {
				StringBuffer sb = new StringBuffer();
				if (nbFailedWrapping != 0) {
					if (nbFailedWrapping == 1) {
						sb.append("The deployment was unable to parse the generated message.\n");
						sb.append(firstFailedWrappingErrorMessage);
					} else {
						sb.append("The deployment was unable to parse " + nbFailedWrapping + " generated messages\n");
						sb.append("The first error message is: " + firstFailedWrappingErrorMessage);
					}
					if (nbFailedDeploy != 0) {
						sb.append("\n\n");
					}
				}
				if (nbFailedDeploy != 0) {
					if (nbFailedDeploy == 1) {
						sb.append("The deployment was unable to deploy the message to the T24 server\n");
						sb.append(firstFailedDeployedErrorMessage);
					} else {
						sb.append("The deployment was unable to deploy " + nbFailedDeploy + " generated messages to the T24 server\n");
						sb.append("The first error message is: " + firstFailedDeployedErrorMessage);
					}
				}
				warnUserOfErrors(sb.toString());
			}

		} finally {
			closeQuietly(connection);
		}
	}
	
	private void warnUserOfErrors(final String message) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				String fullMessage = "Some file(s) were not deployed.\n" +
						message + "\n\n" +
						"Check the Deploy Console for more details.\n" +
						"(Tip: run the server in debug mode to get even more details)";
				MessageDialog.openError(Display.getDefault().getActiveShell(), 
						"Deploy to T24 Server Problem", fullMessage);
			}
		});
	}

	private IT24Connection connect(Properties serverProperties) throws T24ServerException {
		T24ConnectionProtocol protocol = ServerPropertiesHelper.getProtocol(serverProperties);
		String host = ServerPropertiesHelper.getHost(serverProperties);
		String port = null;
		if (protocol == T24ConnectionProtocol.WS) {
			port = ServerPropertiesHelper.getWebServicePort(serverProperties);
		} else {
			port = ServerPropertiesHelper.getAgentPort(serverProperties);
		}
		String username = ServerPropertiesHelper.getUsername(serverProperties);
		String password = ServerPropertiesHelper.getPassword(serverProperties); 

		T24DeployConsole deployConsole = T24ServerUIExternalCore.getDefault()
				.getDeployBuilderConsole();
		deployConsole.printDebug("Connecting to the server (host=" + host + ", port=" + port + ", username=" + 
				username + ", protocol=" + protocol + ")");
		
		if (protocol == T24ConnectionProtocol.WS) {
			// Webservice deployment
			return new T24DesignStudioInstallerService(
					host, port, username, password);
		} else {
			// Agent development
			return T24AgentConnectionHelper.getT24AgentConnection(host, port, username, password);
		}
		
	}

	/**
	 * Wrap the xml with a message tag
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public String wrapMessage(InputStream input, String t24User, String t24Password,
			String branch) throws JDOMException, IOException {
		
		SAXBuilder builder = new SAXBuilder();
		try {
			Document doc = (Document) builder.build(input);
			Element rootNode = doc.getRootElement();
	
			// create the message element (the wrapping tag)
			Element installer = new Element("message", Namespace.getNamespace("installer",
					"http://www.odcgroup.com/t24/installer"));
			installer.addNamespaceDeclaration(Namespace.getNamespace("xsi",
					"http://www.w3.org/2001/XMLSchema-instance"));
			installer.setAttribute(new Attribute("uid", t24User));
			installer.setAttribute(new Attribute("pwd", t24Password));
			installer.setAttribute(new Attribute("branch", branch));
			installer.setAttribute(new Attribute("object", getObjectName(rootNode.getName())));
			
			// replace root element
			doc.setRootElement(installer);
			installer.addContent(rootNode);
	
			T24DeployConsole deployBuilderConsole = T24ServerUIExternalCore.getDefault().getDeployBuilderConsole();
			if (deployBuilderConsole.isDisplayDebug()) {
				StringWriter prettyResult = new StringWriter();
				new XMLOutputter(Format.getPrettyFormat()).output(doc, prettyResult);
				deployBuilderConsole.printDebug("Credential used for the message wrapper:" + t24User + "/" + t24Password);
				deployBuilderConsole.printDebug("Message prepared for sending:");
				deployBuilderConsole.printDebug(prettyResult.toString());
			}
			
			// format the result
			StringWriter result = new StringWriter();
			new XMLOutputter(Format.getCompactFormat()).output(doc, result);
			return result.toString();
		} finally {
			IOUtils.closeQuietly(input);
		}

	}

	private String getObjectName(String name) {
		if(name.equals("LocalRef")) {
			return "local_table";
		}
	    if(name.equals("LocalReferenceApplication")){
	       return "local_ref_table";
	    }
		return name;
	}

	/**
	 * Send the message to the server
	 * @param t24ServerName 
	 */
	public void deployToServer(String messageFile, IT24Connection connection, String sourceFileName, String t24ServerName) throws T24ServerException {
		T24DeployConsole deployConsole = T24ServerUIExternalCore.getDefault()
				.getDeployBuilderConsole();
		deployConsole.printDebug("Sending " + sourceFileName + " to the server.");

		long start = System.currentTimeMillis();
		connection.sendOfsMessage(messageFile);
		deployConsole.printInfo(MessageFormat.format(SUCCESS_MESSAGE, sourceFileName, System.currentTimeMillis() - start, t24ServerName));
	}

    public void closeQuietly(IT24Connection connection) {
    	 if (connection!=null) {
			try {
				connection.close();
			} catch (T24ServerException e) {
				//quietly 
			}
		}
    }

}
