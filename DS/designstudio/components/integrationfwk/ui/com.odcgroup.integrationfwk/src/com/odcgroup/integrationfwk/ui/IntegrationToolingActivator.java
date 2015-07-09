package com.odcgroup.integrationfwk.ui;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

/**
 * The activator class controls the plug-in life cycle
 */
public class IntegrationToolingActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.integrationfwk.ui";
	public static final String IMG_FORM_BG = "form_banner.gif"; //$NON-NLS-1$
	private static Bundle bundle;
	/** instance of {@link IResourceChangeListener} */
	private static IResourceChangeListener resourceListener;

	public static String getEventString(String key) {
		return getResourceString(key);
	}

	public static String getPluginVersion() {
		return plugin.getBundle().getHeaders().get(Constants.BUNDLE_VERSION).toString();
	}

	/**
	 * Returns the string from the plugin's resource bundle, or 'key' if not
	 * found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle rsbundle = IntegrationToolingActivator.getDefault().getResourceBundle();
		try {
			return (rsbundle != null) ? rsbundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	public static String getResourceString(String key, Object o) {
		return getResourceString(key, new Object[] { o });
	}

	// Resource bundle.
	private ResourceBundle resourceBundle;

	// The shared instance
	private static IntegrationToolingActivator plugin;

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static IntegrationToolingActivator getDefault() {
		return plugin;
	}

	public static Image getImage(String path) {
		ImageDescriptor imageDescriptor = getImageDescriptor(path);
		Image image = imageDescriptor.createImage();
		return image;
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

	public static String getPluginName() {
		return plugin.getBundle().getSymbolicName();
	}

	/**
	 * The constructor
	 */
	public IntegrationToolingActivator() {
		plugin = this;
	}

	/*
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		try {
			if (resourceBundle == null) {
				resourceBundle = Platform.getResourceBundle(getBundle());
			}

		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
		return resourceBundle;
	}

	/**
	 * This gets the string resource and does one substitution.
	 */
	public String getString(String key, Object s1) {
		return MessageFormat.format(Platform.getResourceBundle(getBundle()).getString(key), new Object[] { s1 });
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		bundle = context.getBundle();
		if (resourceListener == null) {
			resourceListener = new ConsumerPluginRefactoringListener();
		}
		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceListener,
				IResourceChangeEvent.PRE_CLOSE | IResourceChangeEvent.PRE_DELETE | IResourceChangeEvent.POST_CHANGE);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		if (resourceListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceListener);
		}
	}

	/*
	 * private static final String PRODUCT_CUSTOMIZATION_PROVIDER_PLUGIN_ID =
	 * "org.eclipse.wst.xsd.ui.productCustomizationProviderPluginId";
	 * //$NON-NLS-1$ private static final String
	 * PRODUCT_CUSTOMIZATION_PROVIDER_CLASS_NAME =
	 * "org.eclipse.wst.xsd.ui.productCustomizationProviderClassName";
	 * //$NON-NLS-1$
	 * 
	 * private static ProductCustomizationProvider productCustomizationProvider;
	 * private static boolean productCustomizationProviderInitialized = false;
	 * 
	 * public ProductCustomizationProvider getProductCustomizationProvider() {
	 * if (!productCustomizationProviderInitialized) {
	 * productCustomizationProviderInitialized = true; String pluginName =
	 * getPreferenceStore().getString(
	 * PRODUCT_CUSTOMIZATION_PROVIDER_PLUGIN_ID); String className =
	 * getPreferenceStore().getString(
	 * PRODUCT_CUSTOMIZATION_PROVIDER_CLASS_NAME); if (pluginName != null &&
	 * pluginName.length() > 0 && className != null && className.length() > 0) {
	 * try { Bundle bundle = Platform.getBundle(pluginName); Class clazz =
	 * bundle.loadClass(className); productCustomizationProvider =
	 * (ProductCustomizationProvider) clazz .newInstance(); } catch (Exception
	 * e) { } } } return productCustomizationProvider; }
	 */
}
