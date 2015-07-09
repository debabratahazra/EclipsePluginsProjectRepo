package com.temenos.ds.t24.data.rps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.Test;

import com.temenos.ds.t24.data.rps.connection.T24ConnectionParameters;
import com.temenos.ds.t24.data.rps.connection.T24JAgentConnectionParameters;
import com.temenos.ds.t24.data.rps.connection.T24WSConnectionParameters;
import com.temenos.ds.t24.data.rps.connection.properties.T24ConnectionParametersFactory;
import com.temenos.ds.t24.data.rps.connection.properties.T24ConnectionProperties;

/**
 * Unit test for T24ConnectionPropertiesFactory.
 *
 * @author Michael Vorburger
 */
public class T24ConnectionPropertiesFactoryTest {

	@Test
	public void testDefaultProtocol() {
		Properties props = new Properties();
		T24ConnectionProperties t24props = T24ConnectionProperties.from(props);
		assertEquals(T24ConnectionProperties.T24ConnectionProtocol.jAgent, t24props.protocol);
	}

	@Test
	public void testDefaultOfsSource() {
		Properties props = new Properties();
		T24ConnectionProperties t24props = T24ConnectionProperties.from(props);
		assertEquals("IRISPA", t24props.ofsSource);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testMissingProperties() {
		Properties props = new Properties();
		T24ConnectionProperties t24props = T24ConnectionProperties.from(props);
		T24ConnectionParametersFactory.from(t24props);
	}

	@Test
	public void testT24ConnectionPropertiesJAgent() {
		Properties props = new Properties();
		props.setProperty("host", "somehost.corp.com");
		props.setProperty("agent.port", "21001");
		props.setProperty("ofs.source", "LALALA");

		T24ConnectionProperties t24props = T24ConnectionProperties.from(props);
		T24ConnectionParameters params = T24ConnectionParametersFactory.from(t24props);
		assertTrue(params instanceof T24JAgentConnectionParameters);
		T24JAgentConnectionParameters jParams = (T24JAgentConnectionParameters) params;
		assertEquals("somehost.corp.com", jParams.jAgentHostname);
		assertEquals(21001, jParams.jAgentPort);
		assertEquals("LALALA", jParams.ofsSource);
	}

	@Test
	public void testT24ConnectionPropertiesWS_hostPort() {
		Properties props = new Properties();
		props.setProperty("protocol", "ws");
		props.setProperty("host", "somehost.corp.com");
		props.setProperty("ws.port", "14124");

		T24ConnectionProperties t24props = T24ConnectionProperties.from(props);
		T24ConnectionParameters params = T24ConnectionParametersFactory.from(t24props);
		assertTrue(params instanceof T24WSConnectionParameters);
		T24WSConnectionParameters wsParams = (T24WSConnectionParameters) params;
		assertEquals("http://somehost.corp.com:14124/axis2/services/ResourceProviderServiceWS", wsParams.wsURL);
	}

	@Test
	public void testT24ConnectionPropertiesWS_URL() {
		Properties props = new Properties();
		props.setProperty("protocol", "ws");
		props.setProperty("ws.url", "http://bank.com:1289/ws/ResourceProviderServiceWS");

		T24ConnectionProperties t24props = T24ConnectionProperties.from(props);
		T24ConnectionParameters params = T24ConnectionParametersFactory.from(t24props);
		assertTrue(params instanceof T24WSConnectionParameters);
		T24WSConnectionParameters wsParams = (T24WSConnectionParameters) params;
		assertEquals("http://bank.com:1289/ws/ResourceProviderServiceWS", wsParams.wsURL);
	}

	@Test
	public void testReadPropertiesFromClasspath() throws Exception {
		T24ConnectionProperties t24props = T24ConnectionProperties.fromClasspath("/tafc-T24ConnectionPropertiesFactoryTest.properties");
		T24ConnectionParameters params = T24ConnectionParametersFactory.from(t24props);
		T24JAgentConnectionParameters jParams = (T24JAgentConnectionParameters) params;
		assertEquals("host.corp.com", jParams.jAgentHostname);
		assertEquals(12345, jParams.jAgentPort);
	}

}
