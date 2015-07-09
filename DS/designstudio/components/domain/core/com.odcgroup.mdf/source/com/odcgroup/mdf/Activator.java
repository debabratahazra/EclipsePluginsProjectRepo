package com.odcgroup.mdf;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;


public class Activator extends Plugin {

    public static Activator INSTANCE;

    public Activator() {
    }

    /**
     * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        INSTANCE = this;
    }

    /**
     * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
        INSTANCE = this;
        super.stop(context);
    }

}
