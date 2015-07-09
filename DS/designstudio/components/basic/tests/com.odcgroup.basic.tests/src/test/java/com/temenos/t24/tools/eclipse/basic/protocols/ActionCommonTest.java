package com.temenos.t24.tools.eclipse.basic.protocols;

import org.junit.Assert;
import org.junit.Test;

import com.temenos.t24.tools.eclipse.basic.protocols.actions.ActionCommon;

public class ActionCommonTest {
    
    @Test
	public void testGetErrorMsg(){
        Assert.assertTrue("".equals(ActionCommon.getMsgError(null)));
        Assert.assertTrue("SECURITY.VIOLATION".equals(ActionCommon.getMsgError("SECURITY.VIOLATION")));
        Assert.assertTrue("OF.RTN.SECURITY.VIOLATION".equals(ActionCommon.getMsgError("OF.RTN.SECURITY.VIOLATION")));        
    }
    
    @Test
	public void testIsRawResponseOK(){
        Assert.assertFalse(ActionCommon.isRawResponseOK(null));
        Assert.assertFalse(ActionCommon.isRawResponseOK("null"));
        Assert.assertFalse(ActionCommon.isRawResponseOK("OFSERROR_TIMEOUT"));
        Assert.assertFalse(ActionCommon.isRawResponseOK("OFSERROR_PROCESS"));
        Assert.assertTrue(ActionCommon.isRawResponseOK("<xml>other</xml>"));
    }
}
