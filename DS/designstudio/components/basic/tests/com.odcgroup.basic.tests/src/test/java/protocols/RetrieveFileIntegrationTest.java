package protocols;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.RemoteSessionManager;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

/**
 * In order to connect to server make sure the following items have been setup:
 * - AbstractProtocolIntegration holds the store with remote server items
 * - username, pswrd, serverDir
 */
public class RetrieveFileIntegrationTest extends AbstractProtocolIntegration {
    private static ClassPathXmlApplicationContext springContext;
    private String username  = "INPUTTIDE";
    private String password  = "123456";
    private String serverDir = "BP"; 
    private String channel   = "Browser.1";
    private static RemoteSessionManager sessionMgr;
    
    @BeforeClass
    public static void beforeClass() {
        try{
            String[] locations = {"spring/protocol.spring.xml", "spring/test.plugin.spring.xml"};
            springContext = new ClassPathXmlApplicationContext(locations);
            T24BasicPlugin.setSpringApplicationContext(springContext);
            sessionMgr = RemoteSessionManager.getInstance();
        } catch(Throwable e) {
            e.printStackTrace();
        }
    }    

    @Test
	public void testRetrieveFileOk(){
        
        /** SignOn */ 
        Response res = sessionMgr.signOn(username, password, channel);
        assertTrue(res.getPassed());

        /** Invoke retrieve file */
        String basicFilenameNoPrefix = "Test";  
        res = sessionMgr.retrieveFile(basicFilenameNoPrefix, serverDir);
        String responseXml = ((String) res.getObject());
        String basicModuleContents = XmlUtil.getText(responseXml, "//contents");
        basicModuleContents = (new ProtocolUtil()).transformInsolated_CR_LF_IntoWinNewLines(basicModuleContents);
        //System.out.println(basicModuleContents);
        
        if (res.getPassed()) {
            
            /** Unlock file */
            res = sessionMgr.unlockFile(basicFilenameNoPrefix);
            assertTrue(res.getPassed());
            
        } else {
            /** Action didn't pass */
            System.out.println("RetrieveFile Command Failed: "+res.getRespMessage());
            assertTrue(false);
        }
        
        /** SignOff */
        res = sessionMgr.signOff();
        assertTrue(res.getPassed());
    }
}
