/*
 * 
 */
package com.zealcore.se.ui;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.UIManager;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.ifw.assertions.IAssertionResult;
import com.zealcore.se.core.ifw.assertions.IAssertionSetResult;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.SEProperty;
import com.zealcore.se.core.services.IAssertionReportService;
import com.zealcore.se.core.services.IMementoService;
import com.zealcore.se.core.services.IServiceProvider;
import com.zealcore.se.ui.core.CaseFileManager;
import com.zealcore.se.ui.core.IEventColorProvider;
import com.zealcore.se.ui.core.internal.EventColorProvider;
import com.zealcore.se.ui.editors.ILogSessionWrapper;
import com.zealcore.se.ui.util.PropertySourceAdapterFactory;
import com.zealcore.se.ui.views.AssertionResultView;

/**
 * The activator class controls the plug-in life cycle
 */
public class SeUiPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.zealcore.se.ui";

    public static final String NEWLINE = System.getProperty("line.separator");

    // The shared instance
    private static SeUiPlugin plugin;

    /**
     * The constructor
     */
    public SeUiPlugin() {
        SeUiPlugin.plugin = this;
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

        final IAdapterManager adapterManager = Platform.getAdapterManager();

        registerAdapters(adapterManager);
        reigsterServices(SeCorePlugin.getDefault());

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        final IAssertionReportService reportService = SeCorePlugin.getDefault()
                .getService(IAssertionReportService.class);
        reportService.addAssertionReportListener(AssertionResultView
                .getReportListener());
    }

    private void reigsterServices(final IServiceProvider provider) {
        final IMementoService mementoService = provider
                .getService(IMementoService.class);
        final IPath stateLocation = SeCorePlugin.getDefault()
                .getStateLocation();
        final IPath path = stateLocation.append("eventColorProvider.xml");
        provider.registerService(IEventColorProvider.class,
                new EventColorProvider(mementoService, path));
    }

    /**
     * Registers adapters this plugin uses.
     * 
     * @param adapterManager
     *            the adapter manager
     */
    void registerAdapters(final IAdapterManager adapterManager) {

        adapterManager.registerAdapters(
        		PropertySourceAdapterFactory.getInstance(), ITimeCluster.class);
				
        adapterManager.registerAdapters(
        		PropertySourceAdapterFactory.getInstance(), SEProperty.class);
        adapterManager.registerAdapters(PropertySourceAdapterFactory
                .getInstance(), IObject.class);

        adapterManager.registerAdapters(
        		PropertySourceAdapterFactory.getInstance(), IAssertionSetResult.class);

        adapterManager.registerAdapters(
        		PropertySourceAdapterFactory.getInstance(), IAssertionResult.class);
        
        adapterManager.registerAdapters(
        		PropertySourceAdapterFactory.getInstance(), ILogMark.class);        

        adapterManager.registerAdapters(CaseFileManager.getInstance(),
                IFile.class);

        adapterManager.registerAdapters(CaseFileManager.getInstance(),
                IFolder.class);

        adapterManager.registerAdapters(CaseFileManager.getInstance(),
                IProject.class);

        adapterManager.registerAdapters(CaseFileManager.getInstance(),
                ILogSessionWrapper.class);

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
        SeUiPlugin.plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static SeUiPlugin getDefault() {
        return SeUiPlugin.plugin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initializeImageRegistry(final ImageRegistry reg) {
        super.initializeImageRegistry(reg);
        IconManager.getInstance().initializeImageRegistry(reg);
    }

    /**
     * Log a information message, not an error.
     * 
     * @param message
     *            the message to log
     */
    public static void logInfo(final String message) {

        SeUiPlugin
                .getDefault()
                .getLog()
                .log(SeUiPlugin.createStatus(IStatus.INFO, IStatus.OK, message,
                        null));
    }

    /**
     * Log an exception without a message.
     * 
     * @param exception
     */
    public static void logError(final Throwable exception) {
        String message = "";
        if (exception != null) {
            message = exception.getMessage();
        }
        SeUiPlugin
                .getDefault()
                .getLog()
                .log(SeUiPlugin.createStatus(IStatus.ERROR, IStatus.OK,
                        message, exception));
    }

    /**
     * Logs and error to the ErrorLog through a status message.
     * 
     * @param message
     *            the message to send.
     * @param exception
     *            the exception caused the error.
     */
    public static void logError(final String message, final Throwable exception) {
        SeUiPlugin
                .getDefault()
                .getLog()
                .log(SeUiPlugin.createStatus(IStatus.ERROR, IStatus.OK,
                        message, exception));
    }

    public static void log(final int severity, final int code,
            final String message) {
        SeUiPlugin.getDefault().getLog()
                .log(SeUiPlugin.createStatus(severity, code, message, null));
    }

    /**
     * Returns a new IStatus representing the specified severity, code, message
     * and exception values.
     * 
     * @param code
     *            the code
     * @param exception
     *            the exception
     * @param message
     *            the message
     * @param severity
     *            the severity
     * 
     * @return the status
     */
    private static MultiStatus createStatus(final int severity, final int code,
            final String message, final Throwable exception) {
        final MultiStatus status = new MultiStatus(SeUiPlugin.PLUGIN_ID,
                severity, message, exception);
        status.add(new Status(severity, SeUiPlugin.PLUGIN_ID, code,
                "Plug-in Vendor: "
                        + SeUiPlugin.plugin.getBundle().getHeaders()
                                .get("Bundle-Vendor").toString(), null));
        status.add(new Status(severity, SeUiPlugin.PLUGIN_ID, code,
                SeUiPlugin.NEWLINE
                        + "Plug-in Name: "
                        + SeUiPlugin.plugin.getBundle().getHeaders()
                                .get("Bundle-Name").toString(), null));
        status.add(new Status(severity, SeUiPlugin.PLUGIN_ID, code,
                SeUiPlugin.NEWLINE
                        + "Plug-in Version: "
                        + SeUiPlugin.plugin.getBundle().getHeaders()
                                .get("Bundle-Version").toString(), null));
        return status;
    }

    public IServiceProvider getServiceProvider() {
        return SeCorePlugin.getDefault();
    }

    public static void reportError(final String title, final Throwable e) {
        String message = e.getMessage();
        final Throwable cause = e.getCause();
        if (cause != null) {
            message += SeUiPlugin.NEWLINE + SeUiPlugin.NEWLINE + "Reason:"
                    + SeUiPlugin.NEWLINE;
            message += cause.getMessage();
        } else if (message == null) {
            message = e.toString();
        }
        message += SeUiPlugin.NEWLINE + SeUiPlugin.NEWLINE
                + "See error log for more details";
        final String text = message;
        Display.getDefault().syncExec(new Runnable() {

            public void run() {
                MessageDialog.openError(null, title, text);
            }
        });
    }

    public static void reportUnhandledRuntimeException(final Class<?> clazz,
            final Throwable e, final boolean doLog) {
        final int severity = IStatus.ERROR;
        final int code = 0;
        String message = e.getMessage();
        if (message == null) {
            message = e.toString();
        }
        final Throwable cause = e.getCause() == null ? e : e.getCause();
        final MultiStatus status = SeUiPlugin.createStatus(severity, code,
                message, cause);
        final StringBuilder stackTrace = new StringBuilder(SeUiPlugin.NEWLINE
                + SeUiPlugin.NEWLINE + "Stacktrace:" + SeUiPlugin.NEWLINE);
        final StringWriter str = new StringWriter();
        final PrintWriter pw = new PrintWriter(str);
        cause.printStackTrace(pw);
        stackTrace.append(str.toString());

        if (doLog) {
            // log without stacktrace, it will be included automatically anyway
            SeUiPlugin.getDefault().getLog().log(status);
        }
        // new Status()
        status.add(new Status(severity, SeUiPlugin.PLUGIN_ID, code, stackTrace
                .toString(), null));

        final Shell activeShell = Display.getCurrent() == null ? null : Display
                .getCurrent().getActiveShell();

        ErrorDialog.openError(activeShell, "Internal error in plugin: "
                + SeUiPlugin.PLUGIN_ID, "Unhandled exception caught in class "
                + clazz.getName(), status);
    }

}
