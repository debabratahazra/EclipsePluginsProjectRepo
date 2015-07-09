package com.temenos.t24.tools.eclipse.basic.protocols;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.temenos.t24.tools.eclipse.basic.testutilities.MiscUtilities;
import com.temenos.t24.tools.eclipse.basic.testutilities.T24MockConstants;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtilFactory;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

public class CompileTest extends MockAbstractProtocol {
    private String username  = T24MockConstants.username;
    private String password  = T24MockConstants.password;
    private String directory = T24MockConstants.directory; 
    private String channel   = T24MockConstants.channel;
    
    @Before
    public void setup(){
        // set up the necessary properties
        MementoUtil memUtl = MementoUtilFactory.getMementoUtil();
        memUtl.createProperty("t24.local.user.name", ProtocolConstants.DEFAULT_LOCAL_USERNAME);
    }
    
    @Test
	public void testCompileFileOk(){
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.COMPILE_SUCCESS;
        
        /** SignOn */
        Response response = sessionMgr.signOn(username, password, channel);
        
        /** Invoke save file */
        String filename = "ECLIPSE.TEST1";
        MiscUtilities mu = new MiscUtilities();
        String filecontents = mu.getFileContents("testfiles/ECLIPSE.TEST1.BASIC");
        //System.out.println("Contents: \n"+args[1]);
        
        response = sessionMgr.compileFile(filename, filecontents, directory);
        assertTrue(response.getPassed());
        assertTrue("TRUE".equals(XmlUtil.getText((String)response.getObject(), "//saved")));

    }
}
