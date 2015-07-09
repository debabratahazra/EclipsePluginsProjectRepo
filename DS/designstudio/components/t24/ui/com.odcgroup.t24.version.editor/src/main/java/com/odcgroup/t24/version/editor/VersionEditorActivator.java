package com.odcgroup.t24.version.editor;

import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.ui.AbstractUIActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class VersionEditorActivator extends AbstractUIActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.t24.version.editor"; //$NON-NLS-1$
    private static final String BUNDLE_NAME = "com.odcgroup.t24.version.editor.messages"; //$NON-NLS-1$
    /** The context id */
	public static final String CONTEXT_ID = "com.odcgroup.t24.version.model.ui.context";

	
	/**
	 * The constructor
	 */
	public VersionEditorActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static VersionEditorActivator getDefault() {
		return (VersionEditorActivator) getDefault(VersionEditorActivator.class);
	}
	
	public static synchronized Image getImage(String key, boolean keyIncludeExtn, String relativePath) {
		ImageRegistry registry = getDefault().getImageRegistry();
		Image image = registry.get(key);

		if (image == null) {
			registry.put(key, getImageDescriptor(key, keyIncludeExtn, relativePath));
			image = registry.get(key);
		}

		return image;
	}

	public static synchronized Image getImage(String key, boolean keyIncludeExtn) {
		return getImage(key, keyIncludeExtn, null);
	}

	public static synchronized Image getImage(String key) {
		return getImage(key, false, null);
	}

	public static ImageDescriptor getImageDescriptor(String key) {
		return getImageDescriptor(key, false, null);
	}

	public static ImageDescriptor getImageDescriptor(String key,
			boolean keyIncludesExtn, String relativePath) {
		if (!keyIncludesExtn) {
			key = key + ".gif";
		}
		String path = (StringUtils.isEmpty(relativePath)) ? "" : relativePath;
		URL url = getDefault().getBundle().getEntry("icons/"+path + key);
		return ImageDescriptor.createFromURL(url);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.ui.AbstractUIActivator#getResourceBundleName()
	 */
	protected String getResourceBundleName() {
		return BUNDLE_NAME;
	}
}
