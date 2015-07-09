package com.temenos.t24.tools.eclipse.basic.protocols;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

public class GetChannelsTest extends MockAbstractProtocol {
   
    @Test
	public void testGetChannelsSuccess(){
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.GET_CHANNELS_SUCCESS;
        
        Response response = sessionMgr.getChannelList();        
        
        assertTrue(response.getPassed());

        // System.out.println("GetChannels Command Passed: ");
        String [] channels = (String[])response.getObject();
        String ch = ""; 
        for(int i=0; i<channels.length; i++) ch = ch+channels[i]+" ";
        // System.out.println("Response: "+ch);
        Assert.assertTrue("DEFAULT browser.1 browser.2 mq.sample ".equals(ch));
    }
    
    @Test
	public void testGetChannelsError(){
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.GET_CHANNELS_FAIL;
        Response response = sessionMgr.getChannelList();
        assertFalse(response.getPassed());
  
    }
}
