package com.odcgroup.menu.editor;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * @author pkk
 *
 */
public class MenuActivator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "com.odcgroup.menu.editor";
	

	/** The context id */
	public static final String CONTEXT_ID = "com.odcgroup.menu.editor.context";

	private static MenuActivator INSTANCE;

	/* (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		INSTANCE = this;
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		INSTANCE = null;
		super.stop(context);
	}

	/**
	 * @return
	 */
	public static MenuActivator getInstance() {
		return INSTANCE;
	}

	/**
	 * @return
	 */
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	/**
	 * @param key
	 * @return
	 */
	public static ImageDescriptor getImageDescriptor(String key) {
		return getImageDescriptor(key, false);
	}

	/**
	 * @param key
	 * @param keyIncludesExtn
	 * @return
	 */
	public static ImageDescriptor getImageDescriptor(String key,
			boolean keyIncludesExtn) {
		if (!keyIncludesExtn) {
			key = key + ".gif";
		}
		return imageDescriptorFromPlugin(PLUGIN_ID, "icons/" + key);
	}
	
	
}
