package protocols;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.editor.MockPreferenceStore;

public abstract class AbstractProtocolIntegration {
    protected static MockPreferenceStore store = new MockPreferenceStore();    
    static {
        store.setValue(PluginConstants.T24_REMOTE_HOSTNAME, "localhost");
        store.setValue(PluginConstants.T24_REMOTE_PORT, "8085");
        store.setValue(PluginConstants.T24_REMOTE_SERVER_DIR, "BP");
        store.setValue(PluginConstants.T24_REMOTE_TIMEOUT_MILLIS, "15000");
        store.setValue(PluginConstants.T24_LOCAL_USERNAME, System.getProperty("user.name"));
        store.setValue(PluginConstants.T24_LOCAL_EMAIL, System.getProperty("user.name")+"@temenos.com");
        store.setValue(PluginConstants.T24_LOCAL_TELEPHONE, "3794");
    }
    
}
