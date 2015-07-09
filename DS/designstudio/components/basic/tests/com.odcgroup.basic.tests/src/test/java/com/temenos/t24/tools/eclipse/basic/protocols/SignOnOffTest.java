package com.temenos.t24.tools.eclipse.basic.protocols;

import org.junit.Assert;
import org.junit.Test;

import com.temenos.t24.tools.eclipse.basic.testutilities.T24MockConstants;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

public class SignOnOffTest extends MockAbstractProtocol {

    private String username = T24MockConstants.username;
    private String password = T24MockConstants.password;
    private String channel = T24MockConstants.channel;

    @Test
	public void testStandardSignOnSuccess() {
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.SIGNON_SUCCESS;
        Response res = sessionMgr.signOn(username, password, channel);
        String resText = ((String) res.getObject());
        Assert.assertTrue(res.getPassed());
        //System.out.println("Command Passed: "+resText);
        String responseType = XmlUtil.getText(resText, "//responseType");
        Assert.assertTrue("XML.FRAMES".equals(responseType) || "".equals(responseType));
    }

    @Test
	public void testStandardSignOnFail() {
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.SIGNON_FAIL;
        Response res = sessionMgr.signOn(username, password, channel);
        Assert.assertFalse(res.getPassed());
        Assert.assertFalse("".equals(res.getRespMessage()));
    }

    @Test
	public void testSignOffAction() {
        Response res = sessionMgr.signOff();
        String resText = ((String) res.getObject());
        if (res.getPassed()) {
            //System.out.println("Command Passed: "+resText);
            String responseType = XmlUtil.getText(resText, "//responseType");
            Assert.assertTrue("XML.LOGOFF".equals(responseType) || "".equals(responseType));
        } else {
            // SignOn Action didn't pass
            System.out.println("Command Failed: " + resText + " - " + res.getRespMessage());
            Assert.assertTrue(false);
        }
    }

    /**
     * 1.) A new user has been set up in T24
     * 2.) The user attempts to do a normal sign on through the IDE
     * 3.) The response indicates that the password needs to be repeated
     */
    @Test
	public void testNewUserSignOn() {
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.NEW_USER1;
        Response res = sessionMgr.signOn(username, password, channel);
        String resText = ((String) res.getObject());
        Assert.assertTrue(res.getPassed());
        // System.out.println("Command Passed: "+resText);
        String responseType = XmlUtil.getText(resText, "//responseType");
        Assert.assertTrue("XML.REPEAT.PASSWORD".equals(responseType));
    }

    /**
     * User send repeated password, the answer is OK
     */
    @Test
	public void testNewUserPswdRepeatOk() {
        /** Repeat password - successful response */
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.NEW_USER2;
        Response res = sessionMgr.repeatPasswordNewUser(username, "NewPswd", "NewPswd", channel);
        String resText = ((String) res.getObject());
        Assert.assertTrue(res.getPassed());
        String responseType = XmlUtil.getText(resText, "//responseType");
        Assert.assertTrue("XML.FRAMES".equals(responseType));
    }

    /**
     * User send repeated password, the answer FAIL
     */
    @Test
	public void testNewUserPswdRepeatFail() {
        /** Repeat password - successful response */
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.NEW_USER3;
        Response res = sessionMgr.repeatPasswordNewUser(username, "NewPswd", "NewPswd", channel);
        Assert.assertFalse(res.getPassed());
        Assert.assertTrue(res.getRespMessage().indexOf("SECURITY VIOLATION") >= 0);
    }

    /**
     * User attempts to sign on, but his/her password has expired. 
     */
    @Test
	public void testExpirePswdSignOn() {
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.PSWD_EXPIRED1;
        /** Sign On */
        Response res = sessionMgr.signOn(username, password, channel);
        String resText = ((String) res.getObject());
        Assert.assertTrue(res.getPassed());
        String responseType = XmlUtil.getText(resText, "//responseType");
        Assert.assertTrue("XML.EXPIRED.PASSWORD".equals(responseType));
        /** The server will not provide a session token in this scenario */
        Assert.assertTrue("".equals(res.getSessionToken()));
    }

    /**
     * User send repeated password, the answer is OK
     */
    @Test
	public void testExpirePswdRepeatOk() {
        /** Repeat password - successful response */
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.PSWD_EXPIRED2;
        Response res = sessionMgr.repeatPasswordExpired(username, "NewPswd", "NewPswd", channel);
        String resText = ((String) res.getObject());
        if (res.getPassed()) {
            //System.out.println("Command Passed: "+resText);
            String responseType = XmlUtil.getText(resText, "//responseType");
            Assert.assertTrue("XML.FRAMES".equals(responseType));
        } else {
            // SignOn Action didn't pass
            System.out.println("Command Failed: " + resText + " - " + res.getRespMessage());
            Assert.fail();
        }
    }

    /**
     * User send repeated password, the answer is a Fail
     */
    @Test
	public void testExpirePswdRepeatFail() {
        MockT24HttpProtocolManagerImpl.testId = MockT24HttpProtocolManagerImpl.TEST_ID.PSWD_EXPIRED3;
        String errorMessage = "INVALID PASSWORD REPETITION";
        Response res = sessionMgr.repeatPasswordExpired(username, "NewPswd", "NewPswd2", channel);
        String resText = ((String) res.getObject());
        /** We are expecting a failure. */
        if (!res.getPassed()) {
            //System.out.println("Command Passed: "+resText);
            Assert.assertTrue(errorMessage.equals(res.getRespMessage()));
        } else {
            // SignOn Action didn't pass
            System.out.println("Command Failed: " + resText + " - " + res.getRespMessage());
            Assert.assertTrue(false);
        }
    }
}
