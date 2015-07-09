package com.temenos.t24.tools.eclipse.basic;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.ui.ServerUICore;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.t24.server.external.util.ServerPropertiesHelper;
import com.temenos.t24.tools.eclipse.basic.help.HelpCache;
import com.temenos.t24.tools.eclipse.basic.jremote.RemoteConnectionType;
import com.temenos.t24.tools.eclipse.basic.protocols.RemoteSessionManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.Protocol;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteConnectionException;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitePlatform;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtilFactory;

/**
 * The main plugin class to be used in the desktop.
 */
public class T24BasicPlugin extends AbstractUIPlugin {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(T24BasicPlugin.class);
    // The plug-in ID
    public static final String ID = "com.temenos.t24.tools.eclipse.basic.plugin";
    private static final String WINDOWS = "windows";
    private static final String LINUX = "linux";
    // The shared instance.
    private static T24BasicPlugin plugin;
    // The configuration preferences
    private IEclipsePreferences configPrefs;
    // Resource bundle.
    private ResourceBundle resourceBundle;
    // Spring context
    private static ClassPathXmlApplicationContext springApplicationContext = null;
    
    public static String IMG_SAMPLE = "sample";
    
    private IDocViewProvider provider = null;
    private String EXTENSION_ID = "com.odcgroup.basic.ui.docViewProvider";

    /**
     * Static method for building spring beans.
     * 
     * @param bean - bean name to be injected by Spring.
     * @return
     */
    public static Object getBean(String bean) {
        return springApplicationContext.getBean(bean);
    }

    /**
     * The constructor.
     */
    public T24BasicPlugin() {
        super();
        plugin = this;
        try {
            // Resource bundle for T24Basic plug-in. Used only for Content
            // assist.
            resourceBundle = ResourceBundle.getBundle("com.temenos.t24.tools.eclipse.basic.utils.T24BasicResourceBundle");
        } catch (MissingResourceException x) {
            resourceBundle = null;
            LOGGER.error("MissingResourceException from ResourceBundle.getBundle(\"com.temenos.t24.tools.eclipse.basic.utils.T24BasicResourceBundle\")", x);
        }
        getExtensions();
    }
    
    public RemoteSite finish(ExternalT24Server server) throws T24ServerException {
        boolean isDefault = true;
        RemoteSite newSite = null;
        
        Properties properties = null;
        try {
            properties = server.readPropertiesFile();
        } catch (IOException e) {
            // If no server properties are found, 
            throw new T24ServerException("Unable to read the server properties", e);
        }
        String host = ServerPropertiesHelper.getHost(properties);
        String username = ServerPropertiesHelper.getUsername(properties);
        String password = ServerPropertiesHelper.getPassword(properties);
        String portNumber = ServerPropertiesHelper.getAgentPort(properties);
        String homeDirectory = null;
        //update the below with the proper details
        Protocol protocolType = Protocol.SFTP;
        String protocol = properties.getProperty("protocol");
        if (protocol != null) {
            if (protocol.equals(Protocol.SFTP.toString())) {
                protocolType = Protocol.SFTP;
            } else if (protocol.equals(Protocol.FTP.toString())){
                protocolType = Protocol.FTP;
            }else {
                protocolType = Protocol.LOCAL;
                homeDirectory = ServerPropertiesHelper.getTafcHome(properties);
            }
        }
        
        RemoteSitePlatform ostype = RemoteSitePlatform.UNIX;
        String osType = ServerPropertiesHelper.getPropertyOSType(properties);
        if (osType != null) {
            if (osType.equals(WINDOWS)) {
                ostype = RemoteSitePlatform.NT;
            } else if (osType.equals(LINUX)) {
                ostype = RemoteSitePlatform.LINUX;
            }
        }
        
         try{
             RemoteSite defaultSite = RemoteSitesManager.getInstance().getDefaultSite();
             server.getState();
             if(defaultSite==null){
                 isDefault = true;
             }
            newSite = RemoteSitesManager.getInstance().editSite(server.getServerProject().getName(), host, username, password, ostype,
                     isDefault, portNumber, RemoteConnectionType.JCA, protocolType, homeDirectory );
        }catch (RemoteConnectionException remoteException){
            LOGGER.error("Remote Site Error" + remoteException.getMessage());
        }
        return newSite;
    }
    
    private void getExtensions() {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point = registry.getExtensionPoint(EXTENSION_ID);
        IExtension[] extensions = point.getExtensions();
        for (int i = 0; i < extensions.length; i++) {
            IConfigurationElement[] elements =
                extensions[i].getConfigurationElements();
            for (int j = 0; j < elements.length; j++) {
                try {
                    provider = (IDocViewProvider) elements[j].createExecutableExtension("class");                
                } catch (CoreException e) {
                    LOGGER.error("CoreException when reading extension point", e);
                }
            }
        }        
    }
    
    public IDocViewProvider getProvider() {
        return provider;
    }

    /**
     * This method is called upon plug-in activation. So it's the first method
     * to run, and therefore all initialisation methods, actions, loadings, etc
     * are done in here.
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        startSetUpTasks();
    }

    /**
     * @throws T24ServerException 
     * 
     */
    public void updateServer(String serverName) throws T24ServerException {
        List<IDSServer> servers = ServerUICore.getDefault().getContributions().getServers();
        for (IDSServer idsServer : servers) {
            if(idsServer.getServerProject().getName().equalsIgnoreCase(serverName))
                try {
                    finish((ExternalT24Server)idsServer);
                } catch (T24ServerException e) {
                    throw new T24ServerException("Unable to read the server properties", e);
                }
        }
    }

    /**
     * This method is called when the plug-in is stopped
     */
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        // Include here all the elements of the plug-in that need to be
        // stopped or disposed.
        if (configPrefs != null) {
            configPrefs.flush();
            configPrefs = null;
        }
        plugin = null;
    }

    /**
     * Returns the shared instance.
     */
    public static T24BasicPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @param path the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin("com.temenos.t24.tools.eclipse.basic", path);
    }

    /**
     * Answer the configuration directory for this plug-in that is shared by all
     * workspaces of this installation.
     */
    public File getConfigDir() {
        Location location = Platform.getConfigurationLocation();
        if (location != null) {
            URL configURL = location.getURL();
            if (configURL != null && configURL.getProtocol().startsWith("file")) {
                return new File(configURL.getFile(), ID);
            }
        }
        // If the configuration directory is read-only,
        // then return an alternate location
        // rather than null or throwing an Exception
        return getStateLocation().toFile();
    }

    public static boolean isEarlyStartupDisabled() {
        String plugins = PlatformUI.getPreferenceStore().getString(
        /*
         * Copy constant out of internal Eclipse interface
         * IPreferenceConstants.PLUGINS_NOT_ACTIVATED_ON_STARTUP so that we are
         * not accessing internal type.
         */
        "PLUGINS_NOT_ACTIVATED_ON_STARTUP");
        return plugins.indexOf(T24BasicPlugin.ID) != -1;
    }

    /**
     * Returns the plugin's resource bundle
     */
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    /**
     * Include in this method all the tasks that need running before the plug-in
     * starts up.
     */
    private void startSetUpTasks() throws IOException {
        // ** ** This MUST be the first task. Others may depend on this one **
        // **
        // Load Spring context
        loadSpringConfigurationFiles();
        // Session Manager singleton to handle http comms with remote T24 server
        /*RemoteSessionManager sessionMgr = */ RemoteSessionManager.getInstance();
        // Load T24 properties from the properties file into a local eclipse xml
        // managed through eclipse's Memento framework
        (MementoUtilFactory.getMementoUtil()).loadGlobalT24EnvironmentProperties();
        // Load Properties from the properties file into a local eclipse xml
        // managed through eclipse's Memento framework
        (HelpCache.getInstance()).loadHelpProperties();
        // Default values for plug-in preferences page
        initializeDefaultEditorPreferences(getPreferenceStore());
        //hide the T24 RTC Tool menu in TAFJ perspective inT24DS.
        hideT24RTCToolMenu();
    }

    /**
     * hide the T24RTCTool menu in T24DS only
     */
    private void hideT24RTCToolMenu() {
        IWorkbenchActivitySupport workbenchActivitySupport = PlatformUI.getWorkbench().getActivitySupport();
        IActivityManager activityManager =  workbenchActivitySupport.getActivityManager();
        Set<Object> enabledActivityIds = new HashSet<Object>(activityManager.getEnabledActivityIds());
        if(null != Platform.getProduct())
        if (!("com.odcgroup.workbench.t24.products.designstudioT24".equals(Platform.getProduct().getId()))){
           enabledActivityIds.add("com.odcgroup.tafj.activity");
           workbenchActivitySupport.setEnabledActivityIds(enabledActivityIds);
        }
        
    }

    private void loadSpringConfigurationFiles() {
        if (springApplicationContext == null) {
            String[] locations = { "/spring/plugin.spring.xml", "/spring/protocol.spring.xml" };
            springApplicationContext = new ClassPathXmlApplicationContext();
            springApplicationContext.setClassLoader(getClass().getClassLoader());
            springApplicationContext.setConfigLocations(locations);
            springApplicationContext.refresh();
        }
    }

    public static ApplicationContext getSpringApplicationContext() {
        return springApplicationContext;
    }

    /**
     * This is used by tests, only.
     */
    public static void setSpringApplicationContext(ClassPathXmlApplicationContext springApplicationContext) {
        T24BasicPlugin.springApplicationContext = springApplicationContext;
    }

    /**
     * Initializes the default values for the Preferences Page.
     * 
     * @param store - store for the values.
     */
    protected void initializeDefaultEditorPreferences(IPreferenceStore store) {
        store.setDefault(PluginConstants.PLUGIN_VERSION, MementoUtilFactory.getMementoUtil().getProperty(
                "t24.basic.editor.properties.ver"));
        store.setDefault(PluginConstants.T24_REMOTE_HOSTNAME, PluginConstants.DEFAULT_REMOTE_HOSTNAME);
        store.setDefault(PluginConstants.T24_REMOTE_PORT, PluginConstants.DEFAULT_REMOTE_PORT);
        store.setDefault(PluginConstants.T24_REMOTE_TIMEOUT_MILLIS, PluginConstants.DEFAULT_TIMEOUT_MILLIS);
        store.setDefault(PluginConstants.T24_REMOTE_SERVER_DIR, PluginConstants.DEFAULT_REMOTE_DIR);
        store.setDefault(PluginConstants.T24_HYPERLINK_DIR, PluginConstants.SERVER_PRIMARY_SOURCE);
        store.setDefault(PluginConstants.T24_LOCAL_DIRECTORY, PluginConstants.DEFAULT_LOCAL_DIRECTORY);
        store.setDefault(PluginConstants.T24_LOCAL_DEFAULT_PROJECT_FULLPATH, PluginConstants.DEFAULT_LOCAL_PROJECT);
        store.setDefault(PluginConstants.T24_DOCUMENT_FULLPATH, PluginConstants.DEFAULT_DOCUMENT_FULLPATH);
        store.setDefault(PluginConstants.T24_SAVE_LOCALLY_BY_DEFAULT_ON, PluginConstants.DEFAULT_SAVEL_LOCALLY_ON);
        store.setDefault(PluginConstants.T24_INDENTATION_SPACES, PluginConstants.DEFAULT_INDENTATION_SPACES);
        store.setDefault(PluginConstants.T24_LOCAL_USERNAME, System.getProperty("user.name"));
        store.setDefault(PluginConstants.T24_LOCAL_EMAIL, PluginConstants.DEFAULT_FILL_IN);
        store.setDefault(PluginConstants.T24_LOCAL_TELEPHONE, PluginConstants.DEFAULT_FILL_IN);
        store.setDefault(PluginConstants.T24UNIT_TEST_COMPILE, PluginConstants.DEFAULT_COMPILE_TEST);
        PreferenceConverter.setDefault(store, PluginConstants.T24_EDITOR_COLOR_BACKGROUND,
                PluginConstants.DEFAULT_EDITOR_COLOR_BACKGROUND);
        PreferenceConverter.setDefault(store, PluginConstants.T24_EDITOR_COLOR_COMMENT,
                PluginConstants.DEFAULT_EDITOR_COLOR_COMMENT);
        PreferenceConverter.setDefault(store, PluginConstants.T24_EDITOR_COLOR_COMMON_VARIABLE,
                PluginConstants.DEFAULT_EDITOR_COLOR_COMMON_VARIABLE);
        PreferenceConverter.setDefault(store, PluginConstants.T24_EDITOR_COLOR_DEFAULT,
                PluginConstants.DEFAULT_EDITOR_COLOR_DEFAULT);
        PreferenceConverter.setDefault(store, PluginConstants.T24_EDITOR_COLOR_KEYWORD,
                PluginConstants.DEFAULT_EDITOR_COLOR_KEYWORD);
        PreferenceConverter.setDefault(store, PluginConstants.T24_EDITOR_COLOR_LITERAL,
                PluginConstants.DEFAULT_EDITOR_COLOR_LITERAL);
        PreferenceConverter.setDefault(store, PluginConstants.T24_EDITOR_COLOR_HYPERLINK,
                PluginConstants.DEFAULT_EDITOR_COLOR_HYPERLINK);
    }

    protected void initializeImageRegistry(ImageRegistry registry) {
        registerImage(registry, IMG_SAMPLE, "sample.gif");
    }
  
    private void registerImage(ImageRegistry registry, String key, String fileName) {
        try {
            IPath path = new Path("icons/" + fileName);
            @SuppressWarnings("deprecation")
            URL url = find(path);
            if (url != null) {
                ImageDescriptor desc = ImageDescriptor.createFromURL(url);
                registry.put(key, desc);
            }
        } catch (Exception e) {
            LOGGER.error("Exception in registerImage", e);
        }
    }
}
