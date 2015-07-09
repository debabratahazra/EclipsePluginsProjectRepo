package com.temenos.t24.tools.eclipse.basic.doc;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.springframework.context.ApplicationContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class BasicDocCore extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.basic.doc.ui"; //$NON-NLS-1$

	// The shared instance
	private static BasicDocCore plugin;
	
	// Resource bundle.
    private ResourceBundle resourceBundle;
	
	 // Spring context
    private static ApplicationContext springApplicationContext = null;
    
    
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
     * Returns the plugin's resource bundle
     */
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public static ApplicationContext getSpringApplicationContext() {
        return springApplicationContext;
    }

    public static void setSpringApplicationContext(ApplicationContext springApplicationContext) {
        BasicDocCore.springApplicationContext = springApplicationContext;
    }
    
	/**
	 * The constructor
	 */
	public BasicDocCore() {
		super();
        plugin = this;
        try {
            // Resource bundle for T24Basic plug-in. Used only for Content
            // assist.
            resourceBundle = ResourceBundle.getBundle("com.temenos.t24.tools.eclipse.basic.utils.T24BasicResourceBundle");
        } catch (MissingResourceException x) {
            resourceBundle = null;
        }
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static BasicDocCore getDefault() {
		return plugin;
	}
}
