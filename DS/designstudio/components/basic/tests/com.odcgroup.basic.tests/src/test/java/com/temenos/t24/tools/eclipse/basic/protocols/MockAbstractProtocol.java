package com.temenos.t24.tools.eclipse.basic.protocols;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.editor.MockPreferenceStore;

public abstract class MockAbstractProtocol {

    protected static MockPreferenceStore store = new MockPreferenceStore();
    protected static RemoteSessionManager sessionMgr;
    protected static ClassPathXmlApplicationContext springContext;
    static {
        /** Populate local mock store with test data */
        store.setValue(PluginConstants.T24_REMOTE_HOSTNAME, "localhost");
        store.setValue(PluginConstants.T24_REMOTE_PORT, "8085");
        store.setValue(PluginConstants.T24_REMOTE_SERVER_DIR, "BP");
        store.setValue(PluginConstants.T24_REMOTE_TIMEOUT_MILLIS, "15000");
        store.setValue(PluginConstants.T24_LOCAL_USERNAME, System.getProperty("user.name"));
        store.setValue(PluginConstants.T24_LOCAL_EMAIL, System.getProperty("user.name") + "@temenos.com");
        store.setValue(PluginConstants.T24_LOCAL_TELEPHONE, "3794");
        /** Load spring configuration files, and get sessionMgr instance. */
        try {
            String[] locations = { "spring/test.protocol.spring.xml", "spring/test.plugin.spring.xml" };
            springContext = new ClassPathXmlApplicationContext(locations);
            T24BasicPlugin.setSpringApplicationContext(springContext);
            sessionMgr = RemoteSessionManager.getInstance();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
