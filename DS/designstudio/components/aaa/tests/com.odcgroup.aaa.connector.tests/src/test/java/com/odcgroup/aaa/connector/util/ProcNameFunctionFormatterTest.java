package com.odcgroup.aaa.connector.util;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.aaa.connector.internal.util.ProcNameFunctionFormatter;


/**
 * Test for ProcNameFunctionFormatter.
 * 
 * @author Ramya
 */
public class ProcNameFunctionFormatterTest {
    @Test
	public void testGetProcFileNameFunction() {
        Assert.assertEquals("_Proc__File_Name_is_fine", ProcNameFunctionFormatter.getProcFileNameFunction("#Proc:\\File<Name>is*fine"));
    }
}
