package com.odcgroup.t24.server.external.ui.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.transform.TransformerException;

import org.jdom.JDOMException;
import org.junit.Assert;

import com.google.common.base.Charsets;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class EditDeployedXmlTest extends AbstractDSUnitTest {
	
	private static final String XML_SAMPLE = "FUNDS.TRANSFER,MT103.HP.version.xml";

	public void testNoEmptyXmlNs_ds5575() throws URISyntaxException, IOException, JDOMException, TransformerException {
		// Given
		String originalXml = readFileFromClasspath("/" + XML_SAMPLE);
		Assert.assertFalse("The file shouldn't contains xmlns=\"\"", originalXml.contains("xmlns=\"\""));

		// When
		final ByteArrayInputStream is = new ByteArrayInputStream(originalXml.getBytes(Charsets.UTF_8));
		String wrappedMessage = new EditDeployedXml().wrapMessage(is, "someT24User", "someT24Password", "someBranch");
		
		// Then
		Assert.assertFalse("The file shouldn't contains xmlns=\"\"", wrappedMessage.contains("xmlns=\"\""));
	}
	
}
