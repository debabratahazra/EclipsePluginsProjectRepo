package com.odcgroup.workbench.core.tests;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.workbench.core.helper.StringHelper;


public class StringHelperTest {

	@Test
    public void testToPlural() {
		Assert.assertNull(StringHelper.toPlural(null));
        Assert.assertEquals("Processes", StringHelper.toPlural("Process"));
        Assert.assertEquals("Pageflows", StringHelper.toPlural("Pageflow"));
    }

	@Test
    public void testWithoutExtension() {
		Assert.assertNull(StringHelper.withoutExtension(null));
        Assert.assertEquals("file", StringHelper.withoutExtension("file.ext"));
        Assert.assertEquals("file", StringHelper.withoutExtension("file"));
        Assert.assertEquals("file.ext", StringHelper.withoutExtension("file.ext.ext2"));
    }

	@Test
    public void testToFirstUpper() {
		Assert.assertNull(StringHelper.toFirstUpper(null));
        Assert.assertEquals("Pageflow", StringHelper.toFirstUpper("pageflow"));
    }

	@Test
    public void testToXMLString() {
		Assert.assertNull(StringHelper.toXMLString(null));
        Assert.assertEquals("&lt;xml/&gt;", StringHelper.toXMLString("<xml/>"));
        Assert.assertEquals("a&amp;o", StringHelper.toXMLString("a&o"));
        Assert.assertEquals("&quot;Hello World&quot;",
                StringHelper.toXMLString("\"Hello World\""));
        Assert.assertEquals("&apos;Hello World&apos;",
                StringHelper.toXMLString("'Hello World'"));
    }

}
