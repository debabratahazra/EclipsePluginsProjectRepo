package com.temenos.t24.tools.eclipse.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;
import org.junit.Test;

public class FileUtilTest {

    protected final Logger log = LoggerFactory.getLogger(StringUtilTest.class);

    @Test
	public void testBasicTermination(){
        FileUtil mu = new FileUtil();
        String contents = "hello\r\nhola";
        String filename = "c:\\temp\\test.txt";
        mu.saveToFile(contents, filename, true);
        Assert.assertTrue(contents.equals(mu.getFromFile(filename)));
    }
}
