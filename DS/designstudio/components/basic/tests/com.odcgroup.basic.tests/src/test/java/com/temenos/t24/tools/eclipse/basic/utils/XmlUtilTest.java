package com.temenos.t24.tools.eclipse.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;
import org.junit.Test;

public class XmlUtilTest {

    protected final Logger log = LoggerFactory.getLogger(StringUtilTest.class);

    @Test
	public void testGetText() {
        String text = "<user><name>pepe</name><dir/></user>";
        String xpath = "//name";
        Assert.assertTrue("pepe".equals(XmlUtil.getText(text, xpath)));
        xpath = "//user/name";
        Assert.assertTrue("pepe".equals(XmlUtil.getText(text, xpath)));
        xpath = "//user/dir";
        Assert.assertTrue("".equals(XmlUtil.getText(text, xpath)));
        xpath = "//user/not_exist";
        Assert.assertTrue(null == XmlUtil.getText(text, xpath));
        xpath = "";
        Assert.assertTrue(null == XmlUtil.getText(text, xpath));
        text = "";
        Assert.assertTrue(null == XmlUtil.getText(text, xpath));
    }

    @Test
	public void testGetSafeText() {
        String text = "<user><name>pepe</name><dir/></user>";
        String xpath = "//user/not_exist";
        Assert.assertTrue("".equals(XmlUtil.getSafeText(text, xpath)));
        xpath = "";
        Assert.assertTrue("".equals(XmlUtil.getSafeText(text, xpath)));
        text = "";
        Assert.assertTrue("".equals(XmlUtil.getSafeText(text, xpath)));
        text = null;
        xpath = null;
        Assert.assertTrue("".equals(XmlUtil.getSafeText(text, xpath)));
    }
}
