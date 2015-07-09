package com.odcgroup.page.ui;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.ui.AbstractUIActivator;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author Gary Hayes
 */
public class PageUIPlugin extends AbstractUIActivator {

	/** The plug-in ID */
	public static final String PLUGIN_ID = "com.odcgroup.page.ui";
	
	/** The context id */
	public static final String CONTEXT_ID = "com.odcgroup.page.ui.context";

	/** help keys for widgets */
	private static final String CONFIGURATION_FILE = "src/main/resources/help_widget_properties.properties";

	/** The shared instance */
	private static PageUIPlugin plugin;
	
	/**Color Registry */
	private  ColorRegistry colorRegistry;
	private Properties helpKeys = null;
	
	/**
	 * The constructor
	 */
	public PageUIPlugin() {
		plugin = this;
		colorRegistry = new ColorRegistry();
	}

	/**
	 * Called when the Plugin is started.
	 * 
	 * @param context
	 *            The BundleContext
	 * @throws Exception
	 *             Thrown if an error occurs
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}	

	/**
	 * Called when the Plugin is stopped.
	 * 
	 * @param context
	 *            The BundleContext
	 * @throws Exception
	 *             Thrown if an error occurs
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
	public static PageUIPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	/**
	 * Get the image from the ImageRegistry.
	 * @param key
	 * @return Image
	 */
	public static synchronized Image getImage(String key) {
	        ImageRegistry registry = plugin.getImageRegistry();
	        Image image = registry.get(key);
	        if (image == null) {
	            registry.put(key, getImageDescriptor(key));
	            image = registry.get(key);
	        }

	        return image;
	    }
	/**
	 * @return the help keys for widgets
	 */
	public Properties getHelpKeys() {
		if (helpKeys == null) {
			helpKeys = new Properties();
			Bundle bundle = PageUIPlugin.getDefault().getBundle();
			URL entry = bundle.getEntry(CONFIGURATION_FILE);
			try {
				helpKeys.load(entry.openStream());
			} catch (IOException ex) {
				throw new RuntimeException("Unable to load " + CONFIGURATION_FILE,
						ex);
			}
		}
		return helpKeys;
	}
        /**
         * Get the Color for the given symbolicName;
         * @param colorSymbolicName
         * @return
         */
	public static Color getColor(String colorSymbolicName) {
	    return getDefault().colorRegistry.get(colorSymbolicName);
	}
	/**
	 * set the Color to the given Symbolic name.
	 * @param colorSymbolicName
	 * @param colorData
	 */
	public static void setColorInRegistry(String colorSymbolicName , RGB colorData) {
	    if(!getDefault().colorRegistry.getKeySet().contains(colorSymbolicName)){
		getDefault().colorRegistry.put(colorSymbolicName, colorData);
	    }
	}
}
