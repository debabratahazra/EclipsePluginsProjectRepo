package com.temenos.t24.tools.eclipse.basic.protocols;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.testutilities.T24MockConstants;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

public class FileLockTest extends MockAbstractProtocol {

    private String username = T24MockConstants.username;
    private String password = T24MockConstants.password;
    private String channel = T24MockConstants.channel;
    private String localUsername = "";
    private String localEmail = "";
    private String localTelephone = "";


    @Before
    public void before() {
        reloadLocalUserDetails();
    }

    @Test
	public void testCheckLockOk() {
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.CHECK_LOCK_SUCCESS;
        /** SignOn **/
        Response res = sessionMgr.signOn(username, password, channel);
        assertTrue(res.getPassed());
        /** Get local file contents and Invoke save remote file */
        String basicFilename = "ECLIPSE.TEST1";
        res = sessionMgr.checkLock(basicFilename);
        assertTrue(res.getPassed());
        /** The files is not locked */
        assertTrue("FALSE".equals(XmlUtil.getText((String) res.getObject(), "//locked")));
    }

    @Test
	public void testLockFileOk() {
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.LOCK_SUCCESS;
        /** SignOn */
        Response res = sessionMgr.signOn(username, password, channel);
        /** Check lock */
        String filename = "ECLIPSE.TEST1";
        res = sessionMgr.lockFile(filename, localUsername, localEmail, localTelephone);
        assertTrue(res.getPassed());
        /** File locked */
        assertTrue("TRUE".equals(XmlUtil.getText((String) res.getObject(), "//locked")));
    }

    @Test
	public void testUnlockFileOK() {
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.UNLOCK_SUCCESS;
        /** SignOn */
        Response res = sessionMgr.signOn(username, password, channel);
        /** Unlock */
        String filename = "ECLIPSE.TEST1";
        res = sessionMgr.unlockFile(filename);
        /** File locked */
        assertTrue("FALSE".equals(XmlUtil.getText((String) res.getObject(), "//locked")));
    }

    @Test
	public void testGetLocks() {
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.GET_LOCKS_SUCCESS;
        /** SignOn */
        Response res = sessionMgr.signOn(username, password, channel);
        /** Get locks */
        res = sessionMgr.getLocks(localUsername);
        assertTrue(res.getPassed());
        String responseXmlContents = (String) res.getObject();
        String lockList = XmlUtil.getText(responseXmlContents, "//locklist");
        assertEquals(lockList, "TEST1:TEST2:TEST3");
    }

    /** Loads values from plug-in store */
    private void reloadLocalUserDetails() {
        localUsername = store.getString(PluginConstants.T24_LOCAL_USERNAME);
        localEmail = store.getString(PluginConstants.T24_LOCAL_EMAIL);
        localTelephone = store.getString(PluginConstants.T24_LOCAL_TELEPHONE);
    }
}
