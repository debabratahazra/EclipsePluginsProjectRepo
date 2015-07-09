package com.temenos.t24.tools.eclipse.basic.protocols;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.temenos.t24.tools.eclipse.basic.testutilities.MiscUtilities;
import com.temenos.t24.tools.eclipse.basic.testutilities.T24MockConstants;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

public class SaveFileTest extends MockAbstractProtocol {
    private String username  = T24MockConstants.username;
    private String password  = T24MockConstants.password;
    private String directory = T24MockConstants.directory; 
    private String channel   = T24MockConstants.channel;
    
    private String basicFilenameNoPrefix = "ECLIPSE.TEST1";
    private String basicFileContents = "";
    
    @Before
    public void before() {
        MiscUtilities mu = new MiscUtilities();
        basicFileContents = mu.getFileContents("testfiles/ECLIPSE.TEST1.BASIC");        
    }
    
    @Test
	public void testSaveFileSuccess(){
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.SAVE_SUCCESS;
        
        /** SignOn and get token */
        Response response = sessionMgr.signOn(username, password, channel);
        
        /** Invoke save remote file */
        response = sessionMgr.saveFile(basicFilenameNoPrefix, basicFileContents, directory);
        
        assertTrue(response.getPassed());
        String responseXml = (String)response.getObject();
        assertTrue("TRUE".equals(XmlUtil.getText(responseXml, "//saved")));
 
    }
    
    @Test
	public void testSaveFileFail(){
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.SAVE_FAIL1;
        
        /** SignOn and get token */
        Response response = sessionMgr.signOn(username, password, channel);
        
        /** Invoke save remote file */
        response = sessionMgr.saveFile(basicFilenameNoPrefix, basicFileContents, directory);
        
        assertFalse(response.getPassed());
 
    }    
}
