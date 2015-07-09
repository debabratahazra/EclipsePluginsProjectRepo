package protocols;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.protocols.RemoteSessionManager;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

/**
 * In order to connect to server make sure the following items have been setup:
 * - AbstractProtocolIntegration holds the store with remote server items
 * - username, pswrd, serverDir
 */
public class LockFileIntegrationTest extends AbstractProtocolIntegration {
    private static ClassPathXmlApplicationContext springContext;
    private String signOnUsername  = "INPUTTIDE";
    private String signOnPassword  = "123456";
    private String channel         = "Browser.1";
    private static RemoteSessionManager sessionMgr;
    private String localUsername = "";
    private String localEmail = "";
    private String localTelephone = "";
    
    @BeforeClass
    public static void beforeClass() {
        try{
            /** Note we use the REAL protocol.spring.xml, since we want to communicate with 
             * the real server, not a mock server */
            String[] locations = {"spring/protocol.spring.xml", "spring/test.plugin.spring.xml"};
            springContext = new ClassPathXmlApplicationContext(locations);
            T24BasicPlugin.setSpringApplicationContext(springContext);
            sessionMgr = RemoteSessionManager.getInstance();
        } catch(Throwable e) {
            e.printStackTrace();
        }
    }    

    @Test
	public void testGetLocks(){
        
        /** SignOn */ 
        Response res = sessionMgr.signOn(signOnUsername, signOnPassword, channel);
        assertTrue(res.getPassed());

        /** Get Locks */
        reloadLocalUserDetails();
        res = sessionMgr.getLocks(localUsername);
        assertTrue(res.getPassed());
        
        /** Show files locked */
        String responseXmlContents = (String) res.getObject();
        String locklist = XmlUtil.getText(responseXmlContents, "//locklist");
        if(!"".equals(locklist)){
            String[] lockedFiles = locklist.split(":");
            for(int i=0; i<lockedFiles.length; i++){
                System.out.println(lockedFiles[i]);
            }
        }
        
        /** SignOff */
        res = sessionMgr.signOff();
        assertTrue(res.getPassed());
    }    
    
    @Test
	public void testLockFile(){
        
        /** SignOn */ 
        Response res = sessionMgr.signOn(signOnUsername, signOnPassword, channel);
        assertTrue(res.getPassed());

        /** Lock file */
        String basicFilenameNoPrefix = "Test";  
        reloadLocalUserDetails();
        res = sessionMgr.lockFile(basicFilenameNoPrefix, localUsername, localEmail, localTelephone);
        assertTrue("TRUE".equals(XmlUtil.getText((String)res.getObject(), "//locked")));
        assertTrue(res.getPassed());
        
        /** Unlock */
        res = sessionMgr.unlockFile(basicFilenameNoPrefix);
        assertTrue(res.getPassed());
        
        /** SignOff */
        res = sessionMgr.signOff();
        assertTrue(res.getPassed());
    }
    
    /** Loads values from plug-in store */
    private void reloadLocalUserDetails() {
        localUsername  = store.getString(PluginConstants.T24_LOCAL_USERNAME);
        localEmail     = store.getString(PluginConstants.T24_LOCAL_EMAIL);
        localTelephone = store.getString(PluginConstants.T24_LOCAL_TELEPHONE);
    }    
}
