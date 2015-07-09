package com.temenos.t24.tools.eclipse.basic.protocols;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.temenos.t24.tools.eclipse.basic.testutilities.T24MockConstants;

public class GetServerFilesTest extends MockAbstractProtocol {

    private String username = T24MockConstants.username;
    private String password = T24MockConstants.password;
    private String channel = T24MockConstants.channel;

    @SuppressWarnings("unchecked")
	@Test
	public void testGetChannelsSuccess() {
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.GET_SERVER_FILES_SUCCESS;
        /** SignOn and get token */
        Response response = sessionMgr.signOn(username, password, channel);
        response = sessionMgr.getServerFiles("GLOBUS.BP", "LK", "ACC...");
        assertTrue(response.getPassed());
        ArrayList<String> files = (ArrayList<String>) response.getObject();
        //System.out.println("Response: "+files.toString());
        assertEquals(files.get(0), "ACCOUNT.1");
        assertEquals(files.get(1), "ACCOUNT.2");
        assertEquals(files.get(2), "ACCOUNT.3");
    }
}
