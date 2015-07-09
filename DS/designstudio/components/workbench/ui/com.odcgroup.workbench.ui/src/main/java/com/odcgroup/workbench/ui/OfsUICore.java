package com.odcgroup.workbench.ui;

import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IStartup;

import com.odcgroup.workbench.ui.internal.markers.ProjectResourceListener;


public class OfsUICore extends AbstractUIActivator implements IStartup {
	
	// the messages resource bundle
    private static final String BUNDLE_NAME = "com.odcgroup.workbench.ui.messages"; //$NON-NLS-1$

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.workbench.ui";

    public static final String JAVA_MARKER_ID  = PLUGIN_ID + ".javaProblem"; //$NON-NLS-1$

	final static public String ID_NAVIGATOR = "com.odcgroup.workbench.ui.navigator";
	
	public static final String MODEL_IMAGE_EXTENSION_ID = "com.odcgroup.workbench.ui.modelImage";
	
	public static final String MODEL_NAME = "modelName";
	
	public static final String ICON_PATH = "path";

	// DS-1596: register resource listener for .project changes
	private IResourceChangeListener resourceChangeListener = new ProjectResourceListener();

	@Override
	protected String getResourceBundleName() {
		return BUNDLE_NAME;
	}
	
	public static OfsUICore getDefault() {
		return (OfsUICore) getDefault(OfsUICore.class);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IStartup#earlyStartup()
	 */
	public void earlyStartup() {
        ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener);
	}

}
