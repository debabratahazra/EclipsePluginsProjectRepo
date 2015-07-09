package com.temenos.t24.tools.eclipse.basic.protocols;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.temenos.t24.tools.eclipse.basic.testutilities.T24MockConstants;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

public class RetrieveFileTest extends MockAbstractProtocol {
    private String username  = T24MockConstants.username;
    private String password  = T24MockConstants.password;
    private String serverDir = T24MockConstants.directory; 
    private String channel   = T24MockConstants.channel;
   
    @Test
	public void testRetrieveFileOk(){
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.RETRIEVE_FILE_SUCCESS; 
        
        /** SignOn */
        Response res = sessionMgr.signOn(username, password, channel);
        assertTrue(res.getPassed());

        /** Invoke retrieve file */
        String basicFilename = "ANY.NAME"; 
        res = sessionMgr.retrieveFile(basicFilename, serverDir);
        String responseXml = ((String) res.getObject());
        
        assertTrue(res.getPassed());
        String basicFileContents = XmlUtil.getText(responseXml, "//contents");
        basicFileContents = (new ProtocolUtil()).transformInsolated_CR_LF_IntoWinNewLines(basicFileContents);
        assertTrue("LIN 1\r\nLIN 2\r\nLIN 3\r\nLIN 4".equals(basicFileContents));
    }
    
    @Test
	public void testRetrieveFileFail(){
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.RETRIEVE_FILE_FAIL; 
        
        /** SignOn */
        Response res = sessionMgr.signOn(username, password, channel);
        assertTrue(res.getPassed());

        /** Invoke retrieve file - fails because it's locked */
        String basicFilename = "ANY.NAME"; 
        res = sessionMgr.retrieveFile(basicFilename, serverDir);
        String respMsg = res.getRespMessage();
        assertTrue(respMsg.indexOf(ProtocolConstants.MESSAGE_FILE_LOCKED) >= 0);
        assertFalse(res.getPassed());
    }    
    
    @Test
	public void testRetrieveFileReadOnlyOk() {
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.RETRIEVE_FILE_READONLY_SUCCESS; 
        
        /** SignOn */
        Response res = sessionMgr.signOn(username, password, channel);
        assertTrue(res.getPassed());

        /** Invoke retrieve file read only */
        String basicFilename = "ANY.NAME"; 
        res = sessionMgr.retrieveReadOnlyFile(basicFilename, serverDir);
        String responseXml = ((String) res.getObject());
        
        assertTrue(res.getPassed());
        String basicFileContents = XmlUtil.getText(responseXml, "//contents");
        basicFileContents = (new ProtocolUtil()).transformInsolated_CR_LF_IntoWinNewLines(basicFileContents);
        assertTrue("LIN 1\r\nLIN 2\r\nLIN 3\r\nLIN 4".equals(basicFileContents));
    }
}
