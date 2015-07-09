package com.odcgroup.workbench.core;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractActivator extends Plugin {
	
	// The shared instances
	final protected static Map<Class,AbstractActivator> plugins = new HashMap<Class,AbstractActivator>();

    final protected static Map<Class,ResourceBundle> resourceBundles = new HashMap<Class,ResourceBundle>();
    
	/**
	 * The cached debug options (for optimization).
	 */
	private static final ConcurrentMap<String, Boolean> cachedOptions = new ConcurrentHashMap<String, Boolean>();

	/**
	 * The constructor
	 */
	public AbstractActivator() {
		plugins.put(this.getClass(), this);
		
	}
	
	/**
	 * Retrieves a Boolean value indicating whether tracing is enabled for the
	 * specified debug option.
	 * 
	 * @return Whether tracing is enabled for the debug option of the plug-in.
	 * @param option The debug option for which to determine trace enablement.
	 * 
	 */
	protected static boolean isTraceOptionEnabled(String option) {
		Boolean value = cachedOptions.get(option);
		if (null == value) {
			Boolean newValue = Boolean.valueOf(
					org.eclipse.core.runtime.Platform.getDebugOption(option));
			value = cachedOptions.putIfAbsent(option, newValue);
			if (value == null) {
				value = newValue;
			}
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		ClassLoader cl = this.getClass().getClassLoader();
		if(getResourceBundleName()!=null) {
			resourceBundles.put(this.getClass(), 
					ResourceBundle.getBundle( getResourceBundleName(), Locale.getDefault(), cl));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugins.remove(this.getClass());
		resourceBundles.remove(this.getClass());
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static AbstractActivator getDefault(Class clazz) {
		AbstractActivator plugin = plugins.get(clazz);
		if(plugin==null) {
			try {
				plugin = (AbstractActivator) clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			plugins.put(clazz.getClass(), plugin);
		}
		return plugin;
	}
	
	/**
	 * Retrieves the name of the message resource bundle for this plugin.
	 * This method has to be implemented by all subclasses of this abstract activator that
	 * want to provide resource texts.
	 * When calling the getString() method, this resource bundle is the primary source
	 * for getting the message texts.
	 * 
	 * @return the name of the message resource bundle for this plugin
	 */
	protected String getResourceBundleName() {
		return null;
	}

	/**
	 * This method looks up the localized message text for a given key. The message text
	 * is first searched in the plugin specific resource bundle that is defined by the
	 * getResourceBundleName() method. If it cannot be resolved, the plugin.properties file
	 * is checked. If still no localized message could be found, the key surrounded by "!"
	 * is returned.
	 * @param key the key of the message text
	 * @return the message text for the current locale
	 */
	public String getString( String key ) {
		if(key.startsWith("%")) {
			key = key.substring(1);
		}
		try {
	      return resourceBundles.get(this.getClass()).getString( key );
	    } catch( Exception e ) {
	    	try {
	    		// add the prefix "%" to the key as this is required for a valid key
	    		return Platform.getResourceString(getDefault(this.getClass()).getBundle(), "%" + key);
	    	} catch(MissingResourceException ex) {
		    	return '!' + key + '!';
	    	}
	    }
	}

	/**
     * Returns a localized formatted message.  This method conveniently wraps
     * a call to a MessageFormat object.
     *
     * @param key the key of the message to be returned
     * @param arg an argument to be inserted into the message
     */
	public String getFormattedString(String key, Object arg) { 
        String format = getString(key);
        if (arg == null) 
            arg = ""; //$NON-NLS-1$ 
        return MessageFormat.format(format, new Object[] { arg }); 
    }
	
	/**
     * Returns a localized formatted message.  This method conveniently wraps
     * a call to a MessageFormat object.
     *
     * @param key the key of the message to be returned
     * @param args an array of string arguments to be inserted into the message
     */
    public String getFormattedString (String key, String[] args) { 
    	String message = getString(key);
        return MessageFormat.format(message, (Object[]) args); 
    }
    
    public void logError(String msg, Throwable exception) {
    	log(IStatus.ERROR, msg, exception);
    }

    public void logWarning(String msg, Throwable exception) {
    	log(IStatus.WARNING, msg, exception);
    }

    public void logInfo(String msg, Throwable exception) {
    	log(IStatus.INFO, msg, exception);
    }

    protected void log(int severity, String msg, Throwable exception) {
    	IStatus status = new Status(
    			severity, getDefault(this.getClass()).getBundle().getSymbolicName(), IStatus.OK, msg, exception);
    	getDefault(this.getClass()).getLog().log(status);    	
    }
}
