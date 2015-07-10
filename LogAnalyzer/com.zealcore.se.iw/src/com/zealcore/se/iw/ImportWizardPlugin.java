package com.zealcore.se.iw;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.zealcore.se.core.SeCorePlugin;

/**
 * The activator class controls the plug-in life cycle
 */
public class ImportWizardPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.zealcore.se.iw";

    // The shared instance
    private static ImportWizardPlugin plugin;

    /**
     * The constructor
     */
    public ImportWizardPlugin() {
        ImportWizardPlugin.plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void start(final BundleContext context) throws Exception {
        super.start(context);

        final GenericImportRegistry registry = new GenericImportRegistry();
        registry.restore();
        final SeCorePlugin serviceProvider = SeCorePlugin.getDefault();
        serviceProvider.registerService(GenericImportRegistry.class, registry);

        GenericTextExtendedImporter.setServiceProvider(serviceProvider);
        GenericTextImporter.setServiceProvider(serviceProvider);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void stop(final BundleContext context) throws Exception {
        ImportWizardPlugin.plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static ImportWizardPlugin getDefault() {
        return ImportWizardPlugin.plugin;
    }

    public static void logError(final Throwable e) {
        if (ImportWizardPlugin.plugin == null) {
            e.printStackTrace();
        }
        ImportWizardPlugin
                .getDefault()
                .getLog()
                .log(ImportWizardPlugin.createStatus(IStatus.ERROR, IStatus.OK,
                        "Unexpected Exception", e));
    }

    private static IStatus createStatus(final int severity, final int code,
            final String message, final Throwable exception) {
        return new Status(severity, ImportWizardPlugin.PLUGIN_ID, code,
                message, exception);
    }

    public static IWorkbenchHelpSystem getHelpSystem() {
        return PlatformUI.getWorkbench().getHelpSystem();
    }
}
