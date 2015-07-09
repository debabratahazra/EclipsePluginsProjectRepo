package com.temenos.ds.embedded.server.edge;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import junit.framework.Assert;

import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Test;

import com.acquire.util.StringUtils;

/**
 * @author yandenmatten
 */
public class EdgeEmbeddedServerTest {

	private static final URL SERVER_PROPERTIES_RESOURCE = EdgeEmbeddedServerTest.class.getResource("/server.properties");
	private static final URL REALM_PROPERTIES_RESOURCE = EdgeEmbeddedServerTest.class.getResource("/realm.properties");

	@Test
	public void testServerPropertiesReading() {
		// Given
		File file = getServerPropertiesFile();
		
		// When
		Properties serverProperties = EdgeEmbeddedServer.readServerProperties(file.getAbsolutePath());
		
		// Then
		Assert.assertEquals("Unable to read port in server properties file", 8082, EdgeEmbeddedServer.getPort(serverProperties));
		Assert.assertEquals("Unable to read war file name in server properties file", "hothouse-edge", EdgeEmbeddedServer.getWarFileName(serverProperties));
		Assert.assertEquals("Unable to read context path in server properties file", "/DashBoardDynamic", EdgeEmbeddedServer.getContextPath(serverProperties));
	}

	@Test 
	public void testSystemPropertiesOverloading() {
		// Given
		File file = getServerPropertiesFile();
		Properties serverProperties = EdgeEmbeddedServer.readServerProperties(file.getAbsolutePath());
		Properties savedForCleanup = buildRestoreSystemProperties(serverProperties);
		
		try {
			// When
			EdgeEmbeddedServer.configureSystemProperties(serverProperties);
			
			// Then
			Assert.assertEquals("The system property is not overridden", "some_hothouse_url", serverProperties.getProperty("override.hothouse.url"));
			Assert.assertEquals("The system property is not overridden", "some_solr_url", serverProperties.getProperty("override.solr.url"));
			Assert.assertEquals("The system property is not overridden", "some_browser_url", serverProperties.getProperty("override.browser.url"));
			Assert.assertEquals("The system property is not overridden", "some_IRIS_USER", serverProperties.getProperty("override.IRIS_USER"));
			Assert.assertEquals("The system property is not overridden", "some_IRIS_PASSWORD", serverProperties.getProperty("override.IRIS_PASSWORD"));
		} finally {
			restoreSystemProperties(savedForCleanup);
		}
	}

	private Properties buildRestoreSystemProperties(Properties serverProperties) {
		Properties savedCurrentSystemProperties = new Properties();
		for (Map.Entry<Object, Object> entry: serverProperties.entrySet()) {
			String key = entry.getKey().toString();
			String value = System.getProperty(key, "");
			savedCurrentSystemProperties.put(key, value);
		}
		return savedCurrentSystemProperties;
	}
	
	private void restoreSystemProperties(Properties savedForCleanup) {
		for (Map.Entry<Object, Object> entry : savedForCleanup.entrySet()) {
			System.out.println("Restoring " + entry.toString());
			System.setProperty(
					StringUtils.toString(entry.getKey()), 
					StringUtils.toString(entry.getValue()));
		}
	}
	
	private static File getServerPropertiesFile() {
		try {
			return new File(SERVER_PROPERTIES_RESOURCE.toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetMandatoryServerProperties() {
		// Given
		Properties serverProperties = new Properties();
		serverProperties.put("mandatory", "somevalue");
		
		// When
		Assert.assertEquals("Mandatory server properties not found", "somevalue", EdgeEmbeddedServer.getMandatoryServerProperties(serverProperties, "mandatory"));
		EdgeEmbeddedServer.getMandatoryServerProperties(serverProperties, "mandatory-missing");
	}
	
	@Test
	public void testGetOptionalServerProperties() {
		// Given
		Properties serverProperties = new Properties();
		serverProperties.put("optional", "somevalue");
		
		// When
		Assert.assertEquals("Incorrect optional server properties", "somevalue", EdgeEmbeddedServer.getOptionalServerProperties(serverProperties, "optional"));
		Assert.assertEquals("Incorrect missing optional server properties", "", EdgeEmbeddedServer.getOptionalServerProperties(serverProperties, "optional-missing"));
	}
	
	@Test
	public void testSecurityConfiguration() {
		// Given
		Server server = new Server();
		
		// When
		String realmProperties = getRealmPropertiesFile().getAbsolutePath();
		new EdgeEmbeddedServer().configureSecurity(server, realmProperties);
		
		// Then
		HashLoginService bean = server.getBean(HashLoginService.class);
		Assert.assertNotNull("No login server bean configured", bean);
		Assert.assertEquals(bean.getName(), "T24");
		Assert.assertEquals(bean.getConfig(), realmProperties);
	}
	
	private static File getRealmPropertiesFile() {
		try {
			return new File(REALM_PROPERTIES_RESOURCE.toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void testServerStartedProperly() {
		// Given
		class MockServer extends Server {
			boolean running = true; // defaults to a properly started server
			boolean started = true; // defaults to a properly started server
			boolean failed = false; // defaults to a properly started server
			
			@Override
			public boolean isRunning() {
				return running;
			}

			@Override
			public boolean isStarted() {
				return started;
			}

			@Override
			public boolean isFailed() {
				return failed;
			}
		};
		
		// When
		MockServer serverFailed = new MockServer();
		serverFailed.failed = true;
		MockServer serverNotRunning = new MockServer();
		serverNotRunning.running = false;
		MockServer serverNotStarted = new MockServer();
		serverNotStarted.started = false;
		MockServer serverStarted = new MockServer();
		
		// Then
		EdgeEmbeddedServer edgeEmbeddedServer = new EdgeEmbeddedServer();
		Assert.assertFalse("Server should be seen as not started properly", 
				edgeEmbeddedServer.serverStartedProperly(serverFailed));
		Assert.assertFalse("Server should be seen as not started properly", 
				edgeEmbeddedServer.serverStartedProperly(serverNotRunning));
		Assert.assertFalse("Server should be seen as not started properly", 
				edgeEmbeddedServer.serverStartedProperly(serverNotStarted));
		Assert.assertTrue("Server should be seen as started properly", 
				edgeEmbeddedServer.serverStartedProperly(serverStarted));
		
		// Tests test validity check (i.e. verify if tested failing condition is
		// gone, then the result is different)
		serverFailed.failed = false;
		Assert.assertTrue("Server should be seen as not started properly", 
				edgeEmbeddedServer.serverStartedProperly(serverFailed));
		serverNotRunning.running = true;
		Assert.assertTrue("Server should be seen as not started properly", 
				edgeEmbeddedServer.serverStartedProperly(serverNotRunning));
		serverNotStarted.started = true;
		Assert.assertTrue("Server should be seen as not started properly", 
				edgeEmbeddedServer.serverStartedProperly(serverNotStarted));
	}
	
	@Test
	public void testWebAppContextStartedProperly() {
		// Given
		class MockWebAppContext extends WebAppContext {
			boolean available = true;
			boolean running = true;
			boolean started = true;
			boolean failed = false;
			
			@Override
			public boolean isAvailable() {
				return available;
			}

			@Override
			public boolean isRunning() {
				return running;
			}

			@Override
			public boolean isStarted() {
				return started;
			}

			@Override
			public boolean isFailed() {
				return failed;
			}
		}
		
		// When
		MockWebAppContext webAppNotAvailable = new MockWebAppContext();
		webAppNotAvailable.available = false;
		MockWebAppContext webAppNotRunning = new MockWebAppContext();
		webAppNotRunning.running = false;
		MockWebAppContext webAppNotStarted = new MockWebAppContext();
		webAppNotStarted.started = false;
		MockWebAppContext webAppFailed = new MockWebAppContext();
		webAppFailed.failed = true;
		MockWebAppContext webAppStartedProperly = new MockWebAppContext();
		
		// Then
		EdgeEmbeddedServer edgeEmbeddedServer = new EdgeEmbeddedServer();
		Assert.assertFalse("WebApp shouldn't be seen as started properly", edgeEmbeddedServer.webappStartedProperly(webAppNotAvailable));
		Assert.assertFalse("WebApp shouldn't be seen as started properly", edgeEmbeddedServer.webappStartedProperly(webAppNotRunning));
		Assert.assertFalse("WebApp shouldn't be seen as started properly", edgeEmbeddedServer.webappStartedProperly(webAppNotStarted));
		Assert.assertFalse("WebApp shouldn't be seen as started properly", edgeEmbeddedServer.webappStartedProperly(webAppFailed));
		Assert.assertTrue("WebApp shouldn't be seen as started properly", edgeEmbeddedServer.webappStartedProperly(webAppStartedProperly));

		// Tests test validity check (i.e. verify if tested failing condition is
		// gone, then the result is different)
		webAppNotAvailable.available = true;
		Assert.assertTrue("WebApp should be seen as started properly", edgeEmbeddedServer.webappStartedProperly(webAppNotAvailable));
		webAppNotRunning.running = true;
		Assert.assertTrue("WebApp should be seen as started properly", edgeEmbeddedServer.webappStartedProperly(webAppNotRunning));
		webAppNotStarted.started = true;
		Assert.assertTrue("WebApp should be seen as started properly", edgeEmbeddedServer.webappStartedProperly(webAppNotStarted));
		webAppFailed.failed = false;
		Assert.assertTrue("WebApp should be seen as started properly", edgeEmbeddedServer.webappStartedProperly(webAppFailed));
	}

}
