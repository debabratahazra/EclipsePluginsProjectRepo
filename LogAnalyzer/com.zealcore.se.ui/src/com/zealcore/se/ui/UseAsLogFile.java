/*
 * 
 */
package com.zealcore.se.ui;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import com.zealcore.se.core.ImportException;
import com.zealcore.se.core.ImportExceptionList;
import com.zealcore.se.core.ImportOperationCancelledException;
import com.zealcore.se.core.ifw.IFWFacade;
import com.zealcore.se.core.ifw.ILogAdapter;
import com.zealcore.se.core.ifw.ImportContext;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.ifw.Reason;
import com.zealcore.se.ui.actions.AbstractObjectDelegate;
import com.zealcore.se.ui.core.CaseFileManager;
import com.zealcore.se.ui.internal.DirectoryLogSession;
import com.zealcore.se.ui.internal.ILogFileSuspect;
import com.zealcore.se.ui.internal.LogSessionConfig;
import com.zealcore.se.ui.jobs.LAAbstractActionJob;
import com.zealcore.se.ui.jobs.LAActionJobRunner;

public class UseAsLogFile extends AbstractObjectDelegate {

    private static final String IMPORT_FAILED_PARENT_IS_NOT_A_LOGSET = "Import failed. Parent is not a logset";

    private static final String FAILED_TO_IMPORT_FILE = "Failed to import file: ";

    private static final String PARENT_IS_NOT_A_LOGSET = ", Parent is not a logset";

    private static final String E_IMPORT_FAILED = "Import failed";

    private static final String WARNING = "Warning: Could not find an importer for ";

    private static final String EOL = "\n";

    private IFile file;

    public UseAsLogFile() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void runSafe(final IAction action) {
        if (this.guardFail()) {
            return;
        }
        if (Logset.isDisabled()) {

            ErrorDialog.openError(new Shell(), Messages.LICENSE_ERROR,
                    Messages.IMPORT_ERROR,
                    new Status(IStatus.ERROR, SeUiPlugin.PLUGIN_ID,
                            IStatus.ERROR, Messages.LICENSE_EXCEPTION + ".",
                            Logset.getException()));
            return;
        }
        
        final IStructuredSelection struct = (IStructuredSelection) this
                .getSelection();
        if (!(struct.getFirstElement() instanceof ILogFileSuspect)) {
            return;
        }
        final ILogFileSuspect suspect = (ILogFileSuspect) struct
                .getFirstElement();

        handleImport(suspect.getSuspect());
    }

    public void handleImport(final IFile suspect) {
        file = suspect;

        try {
            Logset logset = CaseFileManager.getLogset(file);

            if (logset != null) {
                long syncKey = getLogsetLock(logset);
                if (syncKey != -1) {

                    if (logset.contains(file)) {
                        // deImportFile(shell, file);
                        LAAbstractActionJob deImporterJob = new LADeImporterJob(
                                file, syncKey);
                        LAActionJobRunner.getInstance().runSingleAction(
                                deImporterJob, true, null);

                    } else {
                        LAAbstractActionJob importerJob = new LAImporterJob(
                                file, syncKey);
                        LAActionJobRunner.getInstance().runSingleAction(
                                importerJob, true, null);
                    }
                }

            } else {
                IllegalStateException e = new IllegalStateException(
                        FAILED_TO_IMPORT_FILE + file + PARENT_IS_NOT_A_LOGSET);
                SeUiPlugin.reportError(IMPORT_FAILED_PARENT_IS_NOT_A_LOGSET, e);
                SeUiPlugin.logError(e.getCause());
            }

        } catch (final ImportException e) {
            reportException(e);
        } catch (final ImportExceptionList e) {
        	if (e.getExceptions() != null) {
	            for (ImportException ie : e.getExceptions()) {
	                reportException(ie);
	            }
        	}
        }
    }

    public void handleLostFiles(Logset logset) {
        if (logset != null) {
            long syncKey = getLogsetLock(logset);
            if (syncKey != -1) {
                LAAbstractActionJob lostFilesJob = new LALostFilesJob(logset,
                        syncKey);
                LAActionJobRunner.getInstance().runSingleAction(lostFilesJob,
                        true, null);
            }
        }
    }

    public void handleImport(Collection<IFile> files, boolean rename) {
        if ((files == null) || (files.size() <= 0)) {
            return;
        }
        try {
            Logset logset = CaseFileManager.getLogset(files.iterator().next());

            if (logset != null) {
                long syncKey = getLogsetLock(logset);
                if (syncKey != -1) {
                    Collection<ILogAdapter> logAdapters = new ArrayList<ILogAdapter>();
                    boolean canImport = true;
                    for (Object element : files) {
                        IFile file = (IFile) element;
                        if ((IFWFacade.findLogAdapter(file,
                                logset.getImporterVersionType())).size() == 0) {
                            canImport = false;
                            break;
                        }
                    }
                    // Import logs not possible with current version of importer
                    // version
                    // Check all logs including already imported can import
                    // changing the importer version.
                    if (!canImport) {
                        int impVersion = getOtherImporterVersion(logset
                                .getImporterVersionType());
                        canImport = true;
                        for (Object element : files) {
                            IFile file = (IFile) element;
                            if ((IFWFacade.findLogAdapter(file, impVersion))
                                    .size() == 0) {
                                canImport = false;
                                break;
                            }
                        }
                        for (final ILogAdapter adapter : logset.getLogs()) {
                            if ((IFWFacade.findLogAdapter(adapter.getFile(),
                                    impVersion)).size() == 0) {
                                canImport = false;
                                break;
                            }
                        }
                        if (!canImport) {
                            for (Object element : files) {
                                IFile iFile = (IFile) element;
                                Collection<ILogAdapter> importers = IFWFacade
                                        .findLogAdapter(iFile,
                                                logset.getImporterVersionType());
                                if (importers.size() > 0) {
                                    logAdapters.add(importers
                                            .iterator()
                                            .next()
                                            .setContext(
                                                    ImportContext.valueOf(
                                                            logset, iFile)));
                                }
                            }
                        } else {
                            logset.setImporterVersionType(impVersion);
                            for (Object element : files) {
                                IFile iFile = (IFile) element;
                                Collection<ILogAdapter> importers = IFWFacade
                                        .findLogAdapter(iFile,
                                                logset.getImporterVersionType());
                                if (importers.size() > 0) {
                                    logAdapters.add(importers
                                            .iterator()
                                            .next()
                                            .setContext(
                                                    ImportContext.valueOf(
                                                            logset, iFile)));
                                }
                            }
                            for (final ILogAdapter adapter : logset.getLogs()) {
                                Collection<ILogAdapter> importers = IFWFacade
                                        .findLogAdapter(adapter.getFile(),
                                                logset.getImporterVersionType());
                                if (importers.size() > 0) {
                                    logAdapters
                                            .add(importers
                                                    .iterator()
                                                    .next()
                                                    .setContext(
                                                            ImportContext
                                                                    .valueOf(
                                                                            logset,
                                                                            adapter.getFile())));
                                }
                            }

                        }
                    } else {
                        for (Object element : files) {
                            IFile iFile = (IFile) element;
							Collection<ILogAdapter> importers =  IFWFacade.findLogAdapter(iFile,
									logset.getImporterVersionType());
							if (importers.size() > 0) {
								logAdapters.add(importers.iterator().next().setContext(ImportContext.valueOf(logset,
										iFile)));
							}
						}
					}
                    if (logAdapters.size() <= 0) {
                        return;
                    }
                    LAAbstractActionJob importerJob = new LAImporterJob(null,
                            logAdapters, rename, syncKey);
                    LAActionJobRunner.getInstance().runSingleAction(
                            importerJob, true, null);
                }

            } else {
                IllegalStateException e = new IllegalStateException(
                        FAILED_TO_IMPORT_FILE + file + PARENT_IS_NOT_A_LOGSET);
                SeUiPlugin.reportError(IMPORT_FAILED_PARENT_IS_NOT_A_LOGSET, e);
                SeUiPlugin.logError(e.getCause());
            }

        } catch (final ImportException e) {
            reportException(e);
        } catch (final ImportExceptionList e) {
        	if (e.getExceptions() != null) {
	            for (ImportException ie : e.getExceptions()) {
	                reportException(ie);
	            }
        	}
        }
    }

    private int getOtherImporterVersion(int version) {
        if (version == IFWFacade.IEXTENDEDIMPORTER_VERSION) {
            return IFWFacade.IIMPORTER_VERSION;
        } else {
            return IFWFacade.IEXTENDEDIMPORTER_VERSION;
        }
    }

    private long getLogsetLock(Logset logset) {
        // TODO Check if the logset has been locked for import activity
        long syncKey = -1;
        if (logset != null) {
            syncKey = logset.getLock();
        }
        return syncKey;
    }

    private void reportException(final ImportException ie) {
        SeUiPlugin.reportError(UseAsLogFile.E_IMPORT_FAILED, ie);
        if (ie.equals(ie.getCause())) {
            SeUiPlugin.logError(ie.getCause());
        } else {
            SeUiPlugin.logError(ie);
        }
        Logset logset = CaseFileManager.getLogset(file);
        if (logset != null) {
            try {
                logset.removeLog(file);
            } catch (ImportOperationCancelledException e) {
            	if (e.getExceptions() != null) {
	                for (ImportException exception : e.getExceptions()
	                        .getExceptions()) {
	                    reportException(exception);
	                }
            	}
            }
        }
    }

    @Override
    protected void handleException(final Throwable e) {
        SeUiPlugin.reportUnhandledRuntimeException(this.getClass(), e, true);
    }

    /**
     * De imports the file.
     * 
     * 
     * @param file
     *            the log file
     */
    public static void deImportFile(final IFile file) {
        Logset logset = CaseFileManager.getLogset(file);
        if (logset != null) {
            try {
                logset.removeLog(file);
            } catch (ImportOperationCancelledException e) {
            	if (e.getExceptions() != null) {
	                for (ImportException exception : e.getExceptions()
	                        .getExceptions()) {
	                    SeUiPlugin.reportError(FAILED_TO_IMPORT_FILE, exception);
	                }
            	}
            }
        }

        IPath location = file.getLocation();
        File binFile = new File(location.toOSString() + Logset.BIN_EXT);
        if (binFile != null && binFile.canRead()) {
            if (binFile.delete()) {
                IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
                try {
                    root.refreshLocal(IResource.DEPTH_INFINITE, null);
                } catch (CoreException e) {}
            }
        }

        refreshWorkspace();
    }

    /**
     * Imports the file.
     * 
     * 
     * @param file
     *            the suspect
     */
    public static void importFile(final IFile file) {

        String message = "";
        ILogAdapter importer = null;

        final Collection<ILogAdapter> importers = UseAsLogFile
                .findLogAdaptersFor(file);

        if (importers.size() == 0) {
            Logset logset = CaseFileManager.getLogset(file);
            if (logset != null) {
                for (ILogAdapter lAdapter : IFWFacade.getLogAdapters(logset
                        .getImporterVersionType())) {
                    if (lAdapter.canRead(file)) {
                        importers.add(lAdapter);
                    }
                }

                // Now reset the importerVersionType and check if IImporter
                // version is supported

                // importer = getExtendedLAAdapter(file, importers, true);
            }

        } else if (importers.size() == 1) {
            importer = importers.iterator().next();
        } else {
            message += "Please select an importer for the file "
                    + file.getName();
            importer = displayImporterDialog(importers, message, file);
        }

        if (importer != null) {
            Logset logset = CaseFileManager.getLogset(file);
            if (logset != null) {
                try {
                    logset.addLog(importer.setContext(ImportContext.valueOf(
                            logset, file)));
                } catch (ImportOperationCancelledException e) {
                	if (e.getExceptions() != null) {
	                    for (ImportException exception : e.getExceptions()
	                            .getExceptions()) {
	                        SeUiPlugin
	                                .reportError(FAILED_TO_IMPORT_FILE, exception);
	                    }
                	}
                }
            } else {
                IllegalStateException e = new IllegalStateException(
                        FAILED_TO_IMPORT_FILE + file + PARENT_IS_NOT_A_LOGSET);
                SeUiPlugin.reportError(IMPORT_FAILED_PARENT_IS_NOT_A_LOGSET, e);
                SeUiPlugin.logError(e.getCause());
            }
        }

        refreshWorkspace();
    }

    private static ILogAdapter displayImporterDialog(
            final Collection<ILogAdapter> importers, final String message,
            final IFile file) {

        ImportDialogRunnable dRunnable = new UseAsLogFile.ImportDialogRunnable(
                importers, message);

        Display.getDefault().syncExec(dRunnable);

        return dRunnable.getLogAdapter();
    }

    static class ImportDialogRunnable implements Runnable {

        ILogAdapter lAdapter;

        Collection<ILogAdapter> importers;

        String message;

        public ImportDialogRunnable(Collection<ILogAdapter> importers,
                String msg) {
            this.importers = importers;
            message = msg;
        }

        public void run() {
            final Shell shell = PlatformUI.getWorkbench().getDisplay()
                    .getActiveShell();
            final ElementListSelectionDialog selectImporterDialog = new ElementListSelectionDialog(
                    shell, new LabelProvider());
            selectImporterDialog.setElements(importers.toArray());
            selectImporterDialog.setAllowDuplicates(false);
            selectImporterDialog.setMultipleSelection(false);
            selectImporterDialog.setHelpAvailable(false);
            selectImporterDialog.setMessage(message);

            if (importers.size() == 0) {
                MessageDialog.openInformation(shell, "Warning", message);
            }
            selectImporterDialog.setTitle("Select importer");
            if (selectImporterDialog.open() == Window.OK) {
                lAdapter = (ILogAdapter) selectImporterDialog.getFirstResult();
            } else {
                lAdapter = null;
            }
        }

        ILogAdapter getLogAdapter() {
            return lAdapter;

        }
    }

    /**
     * if the file does not exist, an empty collection is returned
     * 
     * @param file
     * @return -
     */
    @SuppressWarnings("unchecked")
    public static Collection<ILogAdapter> findLogAdaptersFor(final IFile file) {
        if (!file.exists()) {
            return Collections.EMPTY_LIST;
        }
        if (UseAsLogFile.isLogSetConfigFile(file)
                || (file.getName()
                        .equalsIgnoreCase(DirectoryLogSession.BIN_FILE))) {
            return Collections.EMPTY_LIST;
        }

        Logset logset = CaseFileManager.getLogset(file);
        if (logset != null) {
            return IFWFacade.findLogAdapter(file,
                    logset.getImporterVersionType());
        } else {
            return Collections.emptyList();
        }
    }

    private static boolean isLogSetConfigFile(final IFile file) {
        return file.getName().equalsIgnoreCase(DirectoryLogSession.CONFIG_FILE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectionChanged(final IAction action,
            final ISelection selection) {
        super.selectionChanged(action, selection);
        if (this.guardFail()) {
            return;
        }
        final IStructuredSelection struct = (IStructuredSelection) selection;
        if (struct.getFirstElement() instanceof ILogFileSuspect) {
            final ILogFileSuspect suspect = (ILogFileSuspect) struct
                    .getFirstElement();
            if (IFWFacade.isImported(suspect.getSuspect())) {
                action.setText("Deconfigure Log File");
            } else {
                action.setText("Use as Log File");
            }
            Logset logset = CaseFileManager.getLogset(suspect.getSuspect());
            if (logset.isLocked()) {
                action.setEnabled(false);
            } else {
                action.setEnabled(true);
            }
        }
    }

    /**
     * It returns a LAAdapter if current ExtendedLAAdapter doesn't satisfy given
     * importer selection conditions
     * 
     * @param shell
     * @param file
     * @param importers
     * @param searchAll
     * @return
     */
    public static ILogAdapter getExtendedLAAdapter(final IFile file,
            final Collection<ILogAdapter> importers, final boolean searchAll) {

        ILogAdapter importer = null;
        String message;
        DirectoryLogSession logSession = null;

        if (importers.size() == 0) {

            if (DirectoryLogSession.valueOf(file.getParent()) instanceof DirectoryLogSession) {
                logSession = (DirectoryLogSession) DirectoryLogSession
                        .valueOf(file.getParent());
            }

            // Now reset the importerVersionType and check if IImporter
            // version is supported

            if ((logSession.getImporterVersionType() == LogSessionConfig.IEXTENDEDIMPORTER_VERSION)
                    && logSession != null) {
                logSession
                        .setImporterVersionType(LogSessionConfig.IIMPORTER_VERSION);
                importers.addAll(UseAsLogFile.findLogAdaptersFor(file));

                if (importers.size() == 0) {

                    importer = getLAAdapter(file, importers, true);
                    if (importers.size() != 0) {
                        // LogSessionConfig should update the
                        // importerVersionType in .logset and reImport the
                        // logset with new version type

                        performImporterVersionConflictsResolution(file);
                    } else {
                        // Display a message informing about a failure in
                        // finding an importer for given version
                        // Also reset the importerVersionType to
                        // IExtendedImporter
                        // message = WARNING + file.getName() + EOL;
                        logSession
                                .setImporterVersionType(LogSessionConfig.IEXTENDEDIMPORTER_VERSION);
                        message = new StringBuffer(
                                "No importers available for '")
                                .append(file.getName())
                                .append("' for the supported IExtendedImporter, IImporter versions.Refer the documentation for more.")
                                .toString();

                        importer = displayImporterDialog(importers, message,
                                file);

                    }

                } else {
                    // LogSessionConfig should update the
                    // importerVersionType in .logset and reImport the logset
                    // with new version type
                    performImporterVersionConflictsResolution(file);
                }
            } else {
                // If current importerVersionType is normal IImporter.
                // message = WARNING + file.getName() + EOL;
                message = new StringBuffer("No importers available for '")
                        .append(file.getName())
                        .append("' for the supported IImporter version.Refer the documentation for more.")
                        .toString();

                importer = displayImporterDialog(importers, message, file);
            }
        } else if (importers.size() == 1) {
            importer = importers.iterator().next();
        } else {
            // message = WARNING + file.getName() + EOL;
            message = new StringBuffer("No importers available for '")
                    .append(file.getName())
                    .append("' for the supported IExtendedImporter version.Refer the documentation for more.")
                    .toString();

            importer = displayImporterDialog(importers, message, file);

        }
        return importer;
    }

    private static ILogAdapter getLAAdapter(final IFile file,
            final Collection<ILogAdapter> importers, final boolean searchAll) {

        ILogAdapter importer = null;
        String message;

        if (importers.size() == 0) {

            if (searchAll) {
                Logset logset = CaseFileManager.getLogset(file);
                if (logset != null) {
                    for (ILogAdapter lAdapter : IFWFacade.getLogAdapters(logset
                            .getImporterVersionType())) {
                        if (lAdapter.canRead(file)) {
                            importers.add(lAdapter);
                        }
                    }
                }
            }
        } else if (importers.size() == 1) {
            importer = importers.iterator().next();
        } else {
            message = WARNING + file.getName() + EOL;
            importer = displayImporterDialog(importers, message, file);

        }
        return importer;
    }

    /**
     * It resolves importerVersionType conflicts by auto deconfigure and
     * reconfigure of the constituent logfiles in the logset.
     * 
     * @param shell
     * @param file
     */
    private static void performImporterVersionConflictsResolution(
            final IFile file) {
        if (file == null || !file.exists()) {
            return;
        }

        Logset logset = CaseFileManager.getLogset(file);
        if (logset != null) {
            List<IFile> logFilesList = new ArrayList<IFile>();
            DirectoryLogSession logSession = (DirectoryLogSession) DirectoryLogSession
                    .valueOf(file.getParent());

            // Set importerVersionType to IExtendedImporterVersion
            logSession
                    .setImporterVersionType(LogSessionConfig.IEXTENDEDIMPORTER_VERSION);
            logSession.writeConfig();

            for (ILogAdapter lAdapter : logset.getLogs()) {
                IFile logFile = lAdapter.getFile();
                logFilesList.add(logFile);
            }

            // Now de-Configure the logfiles.
            // for (IFile lFile : logFilesList) {
            // deImportFile(shell, lFile);
            // }

            logset.invalidateLogset();

            if (CaseFileManager.getLogset(file).getLogs() == null
                    || CaseFileManager.getLogset(file).getLogs().size() == 0) {

                // Set importerVersionType to IImporterVersion
                logSession
                        .setImporterVersionType(LogSessionConfig.IIMPORTER_VERSION);
                logSession.writeConfig();
                // logSession
                // .notifyVersionUpdateListeners(LogSessionConfig.IIMPORTER_VERSION);

                for (IFile lFile : logFilesList) {
                    importFile(lFile);
                }
                importFile(file);
            }
        }
    }

    class LAImporterJob extends LAAbstractActionJob {
        Logset iLogset;

        IFile importedFile;

        long syncKey;

        Collection<ILogAdapter> removeLogs;

        Collection<ILogAdapter> addLogs;

        boolean rename;

        public LAImporterJob(IFile iFile, long syncKey) {
            importedFile = iFile;
            iLogset = CaseFileManager.getLogset(importedFile);
            this.syncKey = syncKey;
        }

		public LAImporterJob(Collection<ILogAdapter> removeLogs, Collection<ILogAdapter> addLogs, boolean rename, long syncKey) {
			this.removeLogs = removeLogs;
			this.addLogs = addLogs;
			iLogset = CaseFileManager.getLogset(this.addLogs.iterator().next().getFile());
			this.rename = rename;
			this.syncKey = syncKey;
		}

        public String getJobDescription() {
            return "Importing log files";
        }

        public String getJobName() {
            return "LogAnalyzer Import Operation";
        }

        public int getTotalTime() {
            // TODO Return the number of sub-tasks which constitute the
            // cumulative time required for the whole import activity
            return 0;
        }

        public void run(IProgressMonitor monitor)
                throws InvocationTargetException, InterruptedException {
            iLogset.setProgressMonitor(monitor);
            if (importedFile != null) {
                if (iLogset.getLogs().size() == 0
                        && iLogset.getImporterVersionType() == LogSessionConfig.IIMPORTER_VERSION) {

                    DirectoryLogSession logSession = (DirectoryLogSession) DirectoryLogSession
                            .valueOf(importedFile.getParent());

                    // Reset importerVersionType to IExtendedImporterVersion
                    logSession
                            .setImporterVersionType(LogSessionConfig.IEXTENDEDIMPORTER_VERSION);
                    logSession.writeConfig();

                }
                importFile(importedFile);
            } else {
                try {
                    iLogset.addAndRemoveLogs(removeLogs, addLogs, rename);
                } catch (ImportOperationCancelledException e) {
                	if (e.getExceptions() != null) {
	                    for (ImportException exception : e.getExceptions()
	                            .getExceptions()) {
	                        SeUiPlugin
	                                .reportError(FAILED_TO_IMPORT_FILE, exception);
	                    }
                	}
                }
				DirectoryLogSession logSession = (DirectoryLogSession) DirectoryLogSession
				.valueOf(addLogs.iterator().next().getFile().getParent());
                                

                // Reset importerVersionType to IExtendedImporterVersion
                logSession.setImporterVersionType(iLogset
                        .getImporterVersionType());
                logSession.writeConfig();
            }
        }

        public void notifyJobDone() {
            // Release the lock acquired..
            if (iLogset != null) {
                if (iLogset.getLogs().isEmpty()) {
                    iLogset.invalidateLogset();
                }
                iLogset.releaseLock(this.syncKey);
                iLogset.notifyListeners(Reason.FILE_CHANGED);
            }
        }

        public void notifyJobStart() {
            // Lockt the logset resource
            if (iLogset != null) {
                // nothing is done
            }
        }
    }

    class LADeImporterJob extends LAAbstractActionJob {
        Logset iLogset;

        IFile importedFile;

        long syncKey;

        public LADeImporterJob(IFile iFile, long syncKey) {
            importedFile = iFile;
            iLogset = CaseFileManager.getLogset(importedFile);
            this.syncKey = syncKey;
        }

        public String getJobDescription() {
            return "DeImporting log files";
        }

        public String getJobName() {
            return "LogAnalyzer deimport Activity";
        }

        public int getTotalTime() {
            // TODO Return the number of sub-tasks which constitute the
            // cumulative time required for the whole import activity
            return 0;
        }

        public void run(IProgressMonitor monitor)
                throws InvocationTargetException, InterruptedException {

            iLogset.setProgressMonitor(monitor);

            deImportFile(importedFile);
        }

        public void notifyJobDone() {
            // Release the lock acquired..
            if (iLogset != null) {
                if (iLogset.getLogs().isEmpty()) {
                    iLogset.invalidateLogset();
                }
                iLogset.releaseLock(this.syncKey);
                iLogset.notifyListeners(Reason.FILE_REMOVED);
            }
        }

        public void notifyJobStart() {
            // Lockt the logset resource
            if (iLogset != null) {
                // Nothing is done
            }
        }
    }

    class LALostFilesJob extends LAAbstractActionJob {
        Logset iLogset;

        long syncKey;

        public LALostFilesJob(Logset logset, long syncKey) {
            iLogset = logset;
            this.syncKey = syncKey;
        }

        public String getJobDescription() {
            return "Removing deleted log files";
        }

        public String getJobName() {
            return "LogAnalyzer deleting log files Activity";
        }

        public int getTotalTime() {
            // TODO Return the number of sub-tasks which constitute the
            // cumulative time required for the whole import activity
            return 0;
        }

        public void run(IProgressMonitor monitor)
                throws InvocationTargetException, InterruptedException {

            iLogset.setProgressMonitor(monitor);

            iLogset.removeLogs();
        }

        public void notifyJobDone() {
            // Release the lock acquired..
            if (iLogset != null) {
                if (iLogset.getLogs().isEmpty()) {
                    iLogset.invalidateLogset();
                }
                iLogset.releaseLock(this.syncKey);
                iLogset.notifyListeners(Reason.FILE_REMOVED);
            }
        }

        public void notifyJobStart() {
            // Lockt the logset resource
            if (iLogset != null) {
                // Nothing is done
            }
        }
    }

    static void refreshWorkspace() {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        try {
            root.refreshLocal(IResource.DEPTH_INFINITE, null);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

}
