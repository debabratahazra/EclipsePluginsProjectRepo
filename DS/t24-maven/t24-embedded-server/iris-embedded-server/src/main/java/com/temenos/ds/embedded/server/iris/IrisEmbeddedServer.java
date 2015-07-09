package com.temenos.ds.embedded.server.iris;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is class starts a jetty server and find the (first) web app on the 
 * classpath (search for WEB-INF/web.xml). The web app must be provided as a
 * jar (with not libs in WEB-INF/libs).
 * 
 * @author yandenmatten
 */
public class IrisEmbeddedServer {

	private static Logger logger = LoggerFactory.getLogger(IrisEmbeddedServer.class);

	/** Server properties constants */
	private static final String SERVER_WEBAPP_WAR_KEY = "server.webapp.war";
	private static final String SERVER_PORT_KEY = "server.port";
	private static final String SERVER_WEBAPP_CONTEXT_PATH_KEY = "server.webapp.contextPath";

	private static final String OVERRIDE_PREFIX = "override.";

	/** Used to find web.xml in classpath */
	private static final String WEB_INF_WEB_XML = "WEB-INF/web.xml";
	
	// WARNING: this text is parsed by DS to update the status in the server view
	private static final String STARTUP_SUCCESSFUL = "Embedded Server is now up and running at http://localhost:{0,number,#####}{1}/";
	
	// WARNING: this text is parsed by DS to update the status in the server view
	private static final String STARTUP_FAILURE = "Embedded Server could not start at http://localhost:{0,number,#####}{1}/";

	private static final String DS_JETTY_TEMP_DIR_PROPERTY = "ds.jetty.temp.dir";

	/**
	 * Entry point
	 * @param args
	 * @throws Exception
	 */
    public static void main(String[] args) throws Exception {
    	if (args.length != 2)
    		throw new IllegalArgumentException("Incomplete arguments. You must specify the location of the server properties and the realm properties files.");

    	String serverPropertiesArg = args[0];
    	String realmPropertiesArg = args[1];
    	
		Properties serverProperties = readServerProperties(serverPropertiesArg);
    	int port = getPort(serverProperties);
    	String warFileName = getWarFileName(serverProperties);
    	String contextPath = getContextPath(serverProperties);
    	configureSystemProperties(serverProperties);
    	
    	IrisEmbeddedServer irisEmbeddedServer = new IrisEmbeddedServer();
    	irisEmbeddedServer.start(port, warFileName, contextPath, realmPropertiesArg);
    }
    
	/**
	 * @param propertiesFullPath
	 */
	protected static Properties readServerProperties(String propertiesFullPath) {
		File propertiesFile = new File(propertiesFullPath);
		if (!propertiesFile.exists()) {
			throw new IllegalArgumentException("The properties files doesn't exist: " + propertiesFile.getAbsolutePath());
		}
		Properties serverProperties = new Properties();
		try {
			FileInputStream fis = new FileInputStream(propertiesFile);
			try {
				serverProperties.load(fis);
			} finally {
				IOUtils.closeQuietly(fis);
			}
		} catch (IOException e) {
			String message = "Unable to read the iris server properties: " + propertiesFile.getAbsolutePath();
			logger.error(message, e);
			throw new IllegalArgumentException(message, e);
		}
		
		return serverProperties;
	}
	
	/**
	 * @param serverProperties
	 * @return
	 */
	protected static int getPort(Properties serverProperties) {
		return Integer.parseInt(getMandatoryServerProperties(serverProperties, SERVER_PORT_KEY));
	}
	
	/**
	 * @param serverProperties
	 * @return
	 */
	protected static String getWarFileName(Properties serverProperties) {
		String warFileName = getMandatoryServerProperties(serverProperties, SERVER_WEBAPP_WAR_KEY);
		if (StringUtils.endsWith(warFileName, ".jar") || StringUtils.endsWith(warFileName, ".war"))
			throw new IllegalArgumentException("The war file name should have a war or a jar extension: " + warFileName);
		if (StringUtils.containsAny(warFileName, "/\\"))
			throw new IllegalArgumentException("The war file name shouldn't be a fully qualified name: " + warFileName);
		return warFileName;
	}

	/**
	 * @param serverProperties
	 * @return
	 */
	protected static String getContextPath(Properties serverProperties) {
		String contextPath = getMandatoryServerProperties(serverProperties, SERVER_WEBAPP_CONTEXT_PATH_KEY);
		if (!StringUtils.startsWith(contextPath, "/")) {
			contextPath = "/" + contextPath;
		}
		return contextPath;
	}

	/**
	 * @param args
	 */
	protected static void configureSystemProperties(Properties serverProperties) {
		for (Entry<Object, Object> property : serverProperties.entrySet()) {
			String key = ObjectUtils.toString(property.getKey());
			String value = ObjectUtils.toString(property.getValue());
			
			if (StringUtils.startsWith(key, OVERRIDE_PREFIX)) {
				String finalKey = StringUtils.removeStart(key, OVERRIDE_PREFIX);
				logger.debug("Transmitting override properties to Iris: key=" + finalKey + ", value=" + value);
				System.setProperty(finalKey, value);
			} else {
				logger.debug("Ignore non override properties : " + key + " = " + value);
			}
		}
	}

	protected static String getMandatoryServerProperties(Properties serverProperties, String key) {
		String value = serverProperties.getProperty(key);
		if (StringUtils.isEmpty(value))
			throw new IllegalArgumentException("The property " + key + " is mandatory but was not found.");
		return value;
	}

	protected static String getOptionalServerProperties(Properties serverProperties, String key) {
		String value = serverProperties.getProperty(key);
		if (StringUtils.isEmpty(value))
			return "";
		return value;
	}

	/**
	 * @param port
	 * @param warFileName
	 * @param contextPath 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	private void start(int port, String warFileName, String contextPath, String realmProperties) throws InterruptedException, IOException {
		logger.info("**********************************************");
		logger.info("Starting Design Studio IRIS Embeeded server...");
		logger.info("**********************************************");
		logger.debug("port="+port);
		logger.debug("warFileName="+warFileName);
		logger.debug("contextPath="+contextPath);
		logger.debug("realmProperties="+realmProperties);
		
		checkPortIsFree(port);
		
        Server server = new Server(port);
        server.setStopAtShutdown(true);
        
        configureSecurity(server, realmProperties);

        WebAppContext webapp = createWebAppContext(warFileName, contextPath);
        addWebFragments(webapp);
        forceTempDirIfRequired(webapp);
        server.setHandler(webapp);
        
        try {
        	server.start();
        	
            if (serverStartedProperly(server) && webappStartedProperly(webapp)) {
        		// WARNING: this text is parsed by DS to update the status in the server view
        		//CHECKSTYLE:OFF
        		System.out.println("\n" + getSuccessfulStartupMessage(port, webapp) + "\n");
        		//CHECKSTYLE:ON
        		
                server.join();
            } else {
        		// We must (try to) STOP the server, otherwise the forked background thread keeps running, and the JVM won't exit (e.g. in JUnit Tests)
        		logger.debug("Attempting to stop the server");
        		server.stop();
        		
        		throw new IllegalStateException("Web App in Jetty Server does not seem to have started up; CHECK THE LOG!", 
        				webapp.getUnavailableException());
            }
        } catch (Throwable t) {
    		// WARNING: this text is parsed by DS to update the status in the server view
    		//CHECKSTYLE:OFF
    		System.out.println("\n" + getFailedStartupMessage(port, webapp) + "\n");
    		t.printStackTrace();
    		//CHECKSTYLE:ON
        }
        
	}
	
	/**
	 * @param webapp
	 */
	private void forceTempDirIfRequired(WebAppContext webapp) {
		String dsJettyTempDir = System.getProperty(DS_JETTY_TEMP_DIR_PROPERTY);
		if (dsJettyTempDir != null) {
			logger.info("Jetty temp dir forced to : " + dsJettyTempDir);
			webapp.setTempDirectory(new File(dsJettyTempDir));
		} else {
			logger.debug("No temp dir forced as no " + DS_JETTY_TEMP_DIR_PROPERTY + " is defined.");
		}
	}

	/**
	 * @param server
	 */
	protected void configureSecurity(Server server, String realmProperties) {
		File realmPropertiesFiles = new File(realmProperties);
		if (!realmPropertiesFiles.exists()) {
			throw new IllegalArgumentException("The realm properties files doesn't exist: " + realmPropertiesFiles.getAbsolutePath());
		}
		
		HashLoginService loginService = new HashLoginService("T24", realmPropertiesFiles.getAbsolutePath());
		server.addBean(loginService);
	}

	/**
	 * @param webapp
	 * @throws IOException 
	 */
	private void addWebFragments(WebAppContext webapp) throws IOException {
        // Grab *all* META-INF/web-fragment outside WEB-INF/lib to feed jetty
        Enumeration<URL> resourcesOnCP = getClass().getClassLoader().getResources("META-INF/web-fragment.xml");
        List<Resource> webFragmentResources = new ArrayList<Resource>();
        List<Resource> webFragmentXmls = new ArrayList<Resource>();
        
        while (resourcesOnCP.hasMoreElements()) {
        	URL resOnCP = resourcesOnCP.nextElement();
        	String file = resOnCP.toString();
        	logger.info("Web fragment found: " + file);
        	
        	file = StringUtils.removeStart(file, "jar:");
        	file = StringUtils.removeEnd(file, "META-INF/web-fragment.xml");
        	file = StringUtils.removeEnd(file, "!/");
        	
        	webFragmentXmls.add(Resource.newResource(file));
            webapp.getMetaData().addWebInfJar(Resource.newResource(file));

        	Resource resourcesOfWebFragment = Resource.newResource(file + "META-INF/resources/");
        	if (resourcesOfWebFragment.exists()) {
        		logger.info("Resources folder of web fragment found : " + resourcesOfWebFragment);
        		webFragmentResources.add(resourcesOfWebFragment);
        	}
        }

        // Feed Jetty configurators
        webapp.setAttribute(FragmentConfiguration.FRAGMENT_RESOURCES, webFragmentXmls);
        webapp.setAttribute(WebInfConfiguration.RESOURCE_URLS, webFragmentResources);
	}
	
	/**
	 * @param port 
	 * @return
	 * @throws IOException 
	 */
	private void checkPortIsFree(int port) throws IOException {
		ServerSocket serverSocket = null;
		try {
			logger.debug("Checking if port: " + port + " is available");
		    serverSocket = new ServerSocket(port);

		    logger.debug("Port: " + port + " is available");
		} 
		catch (IOException e) {
			throw new IOException("Cannot use the port " + port + " as is already in use.", e);
		} 
		finally {
		    if (serverSocket != null) {
		    	serverSocket.close();
		    }
		}
	}
	
	protected boolean serverStartedProperly(Server server) {
		if (server.isFailed()) {
			logger.error("Server has failed.");
			return false;
		} else if (!server.isRunning()) {
			logger.error("Server is not running.");
			return false;
		} else if (!server.isStarted()) {
			logger.error("Server has not started.");
			return false;
		}
		return true;
	}
	
	protected boolean webappStartedProperly(WebAppContext webapp) {
		if (!webapp.isAvailable()) {
			logger.error("WepAppContext is not available.");
			return false;
		} else if (!webapp.isStarted()) {
			logger.error("WepAppContext is not started.");
			return false;
		} else if (!webapp.isRunning()) {
			logger.error("WepAppContext is not running.");
			return false;
		} else if (webapp.isFailed()) {
			logger.error("WepAppContext has failed.");
			return false;
		}
		return true;
	}

	/**
	 * @param warName
	 * @param contextPath 
	 * @return
	 * @throws IOException 
	 */
	private WebAppContext createWebAppContext(String warName, String contextPath) throws IOException {
        WebAppContext webAppContext = new WebAppContext();
        logger.debug("Context path of the web app: " + contextPath);
        webAppContext.setContextPath(contextPath);
        String warOnClasspath = findWarOnClasspath(warName);
        logger.debug("The war " + warName + " was found on the classpath on " + warOnClasspath);
		webAppContext.setWar(warOnClasspath);
		
		webAppContext.setConfigurations(new Configuration[] {
		        new WebInfConfiguration(),
		        new WebXmlConfiguration(),
		        new MetaInfConfiguration(),
		        new FragmentConfiguration(),
		        new JettyWebXmlConfiguration()
		});
		
		return webAppContext;
	}

	/**
	 * @param warFileName
	 * @return the war on the war found on the classpath
	 * @throws IOException 
	 */
	private String findWarOnClasspath(String warFileName) throws FileNotFoundException, IOException {
        Enumeration<URL> webInfXmlsEnumeration = getClass().getClassLoader().getResources(WEB_INF_WEB_XML);
        while (webInfXmlsEnumeration.hasMoreElements()) {
        	String webXmlOnCP = webInfXmlsEnumeration.nextElement().toExternalForm();
        	if (webXmlOnCP.contains("/" + warFileName)) {
        		webXmlOnCP = StringUtils.removeStart(webXmlOnCP, "jar:");
        		webXmlOnCP = StringUtils.substringBefore(webXmlOnCP, "!");
        		webXmlOnCP = StringUtils.removeStart(webXmlOnCP, "file:");
        		webXmlOnCP = StringUtils.removeEnd(webXmlOnCP, "/" + WEB_INF_WEB_XML);
        		return webXmlOnCP;
        	}
        }
        
		throw new FileNotFoundException("Unable to file the war named " + warFileName);
	}
	
	public String getSuccessfulStartupMessage(int port, WebAppContext webapp) {
		return MessageFormat.format(STARTUP_SUCCESSFUL, port, webapp.getContextPath());
	}
	
	public String getFailedStartupMessage(int port, WebAppContext webapp) {
		return MessageFormat.format(STARTUP_FAILURE, port, webapp.getContextPath());
	}

}
