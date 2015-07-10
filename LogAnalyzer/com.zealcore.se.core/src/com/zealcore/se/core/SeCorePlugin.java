/*
 * 
 */
package com.zealcore.se.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.MatchResult;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.UIJob;
import org.osgi.framework.BundleContext;

import com.zealcore.plugin.control.LicenseException;
import com.zealcore.plugin.control.LicenseManager;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.dl.TypeRegistry;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.services.IAssertionReportService;
import com.zealcore.se.core.services.IMementoService;
import com.zealcore.se.core.services.IServiceProvider;
import com.zealcore.se.core.services.internal.AssertionReportService;
import com.zealcore.se.core.services.internal.MementoService;

/**
 * The activator class controls the plug-in life cycle
 */
public class SeCorePlugin extends AbstractUIPlugin implements IServiceProvider {
    public static final String NEWLINE = System.getProperty("line.separator");

    private static final int MS_TO_CHECK_LICENSE = 300000;

    // The plug-in ID
    public static final String PLUGIN_ID = "com.zealcore.se.core";

    // The shared instance
    private static SeCorePlugin plugin;

    private final Map<Class<?>, Object> services = new HashMap<Class<?>, Object>();

    private LicenseManager lm;
    
    private static Exception exception;

    /**
     * The constructor
     */
    public SeCorePlugin() {
        SeCorePlugin.plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(final BundleContext context) throws Exception {
        String javaVersion = System.getProperty("java.version");

        Scanner s = new Scanner(javaVersion);
        s.findInLine("(\\d)\\.(\\d),*");

        MatchResult result = s.match();
        if (Integer.parseInt(result.group(1)) >= 1
                && Integer.parseInt(result.group(2)) < 5) {
            MessageDialog
                    .openError(
                            new Shell(),
                            "Java Runtime Environment Version Error",
                            "Your JRE is too old. Your version is "
                                    + javaVersion
                                    + " and 5.0 (1.5) or later is required.\n"
                                    + "Download from: http://www.java.com/en/download/manual.jsp");
        } else {
            super.start(context);
            checkLicense();
            createServices();
            final UIJob job = new UIJob("Checking License") {
                @Override
                public IStatus runInUIThread(final IProgressMonitor monitor) {
                    schedule(SeCorePlugin.MS_TO_CHECK_LICENSE);
                    checkLicense();
                    return Status.OK_STATUS;
                }
            };
            job.schedule(SeCorePlugin.MS_TO_CHECK_LICENSE);
        }
    }

    private void checkLicense() {
        try {
            lm = LicenseManager.getInstance();
            long cookie = (long) (Math.random() * Long.MAX_VALUE);
            long r = lm.registerPlugin(PLUGIN_ID, "1.000", cookie);
            long t = System.currentTimeMillis() / 10000;
            if (r != (cookie ^ t)) {
                if (r != (cookie ^ (t - 1))) {
                    throw new LicenseException("Incorrect response value");
                }
            }
        } catch (LicenseException e) {
            exception = e;
            disableFunctionality();
            String message = Messages.LICENSE_EXCEPTION + " (" + PLUGIN_ID
                    + " plugin). Certain features will be turned off.";
            ErrorDialog
                    .openError(new Shell(), Messages.LICENSE_ERROR, message,
                            new Status(IStatus.ERROR, SeCorePlugin.PLUGIN_ID,
                                    IStatus.ERROR, Messages.LICENSE_EXCEPTION
                                            + ".", e));
        }
    }

    public static void disableFunctionality() {
        Logset.setDisabled(true);
    }

    /**
     * Creates the runtime services.
     */
    private void createServices() {
        final MementoService mem = new MementoService();
        registerService(IMementoService.class, mem);
        services.put(IAssertionReportService.class,
                new AssertionReportService());

        services.put(ITypeRegistry.class, TypeRegistry.getInstance());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(final BundleContext context) throws Exception {
        SeCorePlugin.plugin = null;
        if (lm != null) {
            lm.unregisterPlugin(PLUGIN_ID);
        }
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static SeCorePlugin getDefault() {
        return SeCorePlugin.plugin;
    }

    /**
     * {@inheritDoc}
     */
    public <T> T getService(final Class<T> serviceType) {
        final Object service = services.get(serviceType);
        if (service != null && serviceType.isInstance(service)) {
            return serviceType.cast(service);
        }
        throw new IllegalStateException("Unknown Service: "
                + serviceType.getName());
    }

    public <T> void registerService(final Class<T> type, final T instance) {
        if (!type.isInstance(instance)) {
            throw new IllegalArgumentException("Cannot register service "
                    + instance + " as a " + type + " because " + instance
                    + " is not an instance of " + type);
        }
        services.put(type, instance);
    }

    /**
     * Log an exception without a message.
     * 
     * @param exception
     */
    public static void logError(final Throwable exception) {
        final SeCorePlugin instance = SeCorePlugin.getDefault();
        if (instance != null) {
            instance.getLog().log(
                    SeCorePlugin.createStatus(IStatus.ERROR, IStatus.OK,
                            exception.getMessage(), exception));
        } else {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Log an exception with a message.
     * 
     * @param message
     * @param exception
     */
    public static void logError(final String message, final Throwable exception) {
        SeCorePlugin.getDefault().getLog().log(
                SeCorePlugin.createStatus(IStatus.ERROR, IStatus.OK, message,
                        exception));
    }

    /**
     * Returns a new IStatus representing the specified severity, code, message
     * and exception values.
     * 
     * @param code
     *                the code
     * @param exception
     *                the exception
     * @param message
     *                the message
     * @param severity
     *                the severity
     * 
     * @return the status
     */
    private static IStatus createStatus(final int severity, final int code,
            final String message, final Throwable exception) {
        return new Status(severity, SeCorePlugin.PLUGIN_ID, code, message,
                exception);
    }

    /**
     * Returns a new IStatus representing the specified severity, code, message
     * and exception values.
     * 
     * @param code
     *                the code
     * @param exception
     *                the exception
     * @param message
     *                the message
     * @param severity
     *                the severity
     * 
     * @return the status
     */
    private static MultiStatus createMultiStatus(final int severity,
            final int code, final String message, final Throwable exception) {
        final MultiStatus status = new MultiStatus(SeCorePlugin.PLUGIN_ID,
                severity, message, exception);
        status.add(new Status(severity, SeCorePlugin.PLUGIN_ID, code,
                "Plug-in Vendor: "
                        + SeCorePlugin.plugin.getBundle().getHeaders().get(
                                "Bundle-Vendor").toString(), null));
        status.add(new Status(severity, SeCorePlugin.PLUGIN_ID, code,
                SeCorePlugin.NEWLINE
                        + "Plug-in Name: "
                        + SeCorePlugin.plugin.getBundle().getHeaders().get(
                                "Bundle-Name").toString(), null));
        status.add(new Status(severity, SeCorePlugin.PLUGIN_ID, code,
                SeCorePlugin.NEWLINE
                        + "Plug-in Version: "
                        + SeCorePlugin.plugin.getBundle().getHeaders().get(
                                "Bundle-Version").toString(), null));
        return status;
    }

    public static void reportUnhandledRuntimeException(final Class<?> clazz,
            final Throwable e) {
        String message = e.getMessage();
        if (message == null) {
            message = e.toString();
        }
        final Throwable cause = e.getCause() == null ? e : e.getCause();
        final MultiStatus status = SeCorePlugin.createMultiStatus(
                IStatus.ERROR, 0, message, cause);
        SeCorePlugin.getDefault().getLog().log(status);
    }

    public static Exception getException() {
        return exception;
    }
}
