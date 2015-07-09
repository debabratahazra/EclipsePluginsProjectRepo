package com.odcgroup.mdf.validation;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.workbench.core.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class MdfValidationCore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.mdf.validation"; 

    public static final IStatus STATUS_OK = Status.OK_STATUS;

	@Override
	public void start(org.osgi.framework.BundleContext context) throws Exception {
		super.start(context);
	}
    
	/**
	 * @return
	 */
	public static MdfValidationCore getDefault() {
		return (MdfValidationCore) getDefault(MdfValidationCore.class);
	}

    public static IStatus newStatus(String message, int severity) {
        return new Status(severity, PLUGIN_ID, -1, message, null);
    }

    public static IStatus newStatus(String message, Throwable t) {
        return new Status(IStatus.ERROR, PLUGIN_ID, -1, message, t);
    }

    public static IStatus newStatus(Throwable t) {
        return newStatus(t.toString(), t);
    }

}
