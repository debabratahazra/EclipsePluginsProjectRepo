package com.temenos.t24.tools.eclipse.basic.actions.remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.temenos.t24.tools.eclipse.basic.actions.ActionsUtils;
import com.temenos.t24.tools.eclipse.basic.dialogs.remote.RemoteProjectCompileDialog;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicEditor;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicMultiPageEditor;
import com.temenos.t24.tools.eclipse.basic.jremote.MonitoredCompileAction;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

/**
 * Action to initiate the compilation of a Project(s) in the remote server.
 * 
 * @author yasar
 */
public class CompileProjectInServerActionDelegate implements IWorkbenchWindowActionDelegate {

    // The currently selected object in the active view
    private ISelection selection = null;
    private Object obj = null;
    private ArrayList<IFile> ifileList = null;
    private String remotePath = "";
    private RemoteSite remoteSite = null;
    private Map<IFile, Boolean> basicFileSelectedMap = new HashMap<IFile, Boolean>();

    public void init(IWorkbenchWindow window) {
    }

    /**
     * Method invoked by the framework. The main logic of the action goes here.
     */
    public void run(IAction action) {
        if (selection instanceof IStructuredSelection) {
            Object[] objs = ((IStructuredSelection) selection).toArray();
            buildFileList(objs);
        } else if (selection != null && (selection instanceof TreeSelection)) {
            Object[] objs = ((TreeSelection) selection).toArray();
            buildFileList(objs);
        }
        if (ifileList.size() == 0) {
            ActionsUtils.launchMessageDialogPopup("Error Dialog", "No files present in the project", MessageDialog.ERROR);
            return;
        }
        if (!getUserInput()) {
            return;
        }
        compileFiles();
    }

    /**
     * Prepares basic file list for the selected project(s)
     * 
     * @param objs
     */
    private void buildFileList(Object[] objs) {
        Object obj = null;
        ifileList = new ArrayList<IFile>();
        ifileList.clear();
        int ObjectArraySize = objs.length;
        for (int i = 0; i < ObjectArraySize; i++) {
            obj = objs[i];
            String selectedProjectFullPath = "";
            IProject proj = (IProject) obj;
            selectedProjectFullPath = proj.getLocation().toOSString();
            buildFileListForProjectPath(selectedProjectFullPath);
        }
    }

    /**
     * Adds the files present in a project to the IFileList
     */
    private void buildFileListForProjectPath(String projectFullPath) {
        ArrayList<String> basicFilesListInProject = EditorDocumentUtil.getBasicFilesInCurProject(projectFullPath);
        for (int j = 0; j < basicFilesListInProject.size(); j++) {
            IFile iFile = EditorDocumentUtil.getIFile(projectFullPath, basicFilesListInProject.get(j));
            if (!ifileList.contains(iFile)) {
                ifileList.add(iFile);
            }
        }
    }

    /**
     * Checks if compilation can be done or not
     * 
     * @return true if compilation can be done and false otherwise
     */
    private boolean getUserInput() {
        RemoteProjectCompileDialog dialog = new RemoteProjectCompileDialog(EclipseUtil.getActiveWindow().getShell(), ifileList);
        /*
         * dialog.open() opens this window, creating it first if it has not yet
         * been created. This method waits (blocks) until the window is closed
         * by the end user, and then it returns the window's return code. A
         * window's return codes are window-specific, although two standard
         * return codes are predefined: OK and CANCEL.
         */
        if (dialog.open() != InputDialog.OK) {
            // The user clicked something else other than OK. The execution
            // is finished.
            return false;
        } else {
            remotePath = dialog.getCurrentLocation();
            String siteName = dialog.getSiteName();
            remoteSite = RemoteSitesManager.getInstance().getRemoteSiteFromName(siteName);
            basicFileSelectedMap = dialog.getFilesSelected();
            return true;
        }
    }

    /**
     * Actual compilation process goes here
     */
    public void compileFiles() {
        List<String> failedFiles = new ArrayList<String>();
        boolean atLeastOneFailure = false;
        try {
            // Iterate through all the files that will be compiled
            Set<IFile> files = basicFileSelectedMap.keySet();
            for (final IFile file : files) {
                Boolean selected = (Boolean) basicFileSelectedMap.get(file);
                if (selected) {
                    String basicFilenameNoPrefix = "";
                    basicFilenameNoPrefix = StringUtil.removeBasicExtension(file.getName());
                    boolean saved = false;
                    boolean compiled = false;
                    saveLocal(file);
                    saved = RemoteOperationsManager.getInstance().saveFile(remoteSite, remotePath, basicFilenameNoPrefix, file);
                    if (saved) {
                        compiled = compileThroughJca(basicFilenameNoPrefix);
                        if (compiled) {
                            RemoteOperationsLog.info("File " + basicFilenameNoPrefix + " compiled successfully in server");
                        } else {
                            atLeastOneFailure = true;
                            failedFiles.add(file.getName());
                        }
                    } else {
                        RemoteOperationsLog.error("Unable to save file " + basicFilenameNoPrefix + " in server");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * Launch a popup dialog if there was at least one failure, to alert
         * user.
         */
        if (atLeastOneFailure) {
            StringBuffer sb = new StringBuffer();
            sb.append("The following files failed to compile: \n");
            for (String failedName : failedFiles) {
                sb.append(failedName + "\n");
            }
            ActionsUtils.launchMessageDialogPopup("Compile Project Error Dialog", sb.toString(), MessageDialog.ERROR);
        }
    }

    /**
     * Saves the file contents locally if the file is modified
     * 
     * @param file
     */
    private void saveLocal(IFile file) {
        T24BasicEditor editor = getFileEditor(file);
        if (editor != null && editor.isDirty()) {
            editor.doSave(null);
        }
    }

    /**
     * Checks whether Compilation succeeds or not
     * 
     * @param fileName
     * @return true if compilation success else false
     */
    public boolean compileThroughJca(String fileName) {
        MonitoredCompileAction compileAction = new MonitoredCompileAction();
        Response response = compileAction.execute(remoteSite, remotePath, fileName);
        return analyseCompilationResults(response);
    }

    private boolean analyseCompilationResults(Response response) {
        String responseContents = "";
        boolean compiledOk = true;
        responseContents = response.getRespMessage();
        if (response.getPassed()) {
            if (isCompilationError(responseContents)) {
                compiledOk = false;
            }
        } else {
            compiledOk = false;
        }
        return compiledOk;
    }

    /**
     * Returns whether compilation is successful
     * 
     * @param compileOutput
     * @return true if there is any error. false otherwise.
     */
    private boolean isCompilationError(String compileOutput) {
        // The compilation result has error tags which means compilation failed.
        return (compileOutput.contains("&lt;error&gt;") || compileOutput.contains("<error>"));
    }

    /**
     * Returns the corresponding editor of the file passed
     * 
     * @param file
     * @return editor
     */
    private T24BasicEditor getFileEditor(IFile file) {
        T24BasicMultiPageEditor multiPageEditor = EditorDocumentUtil.findOpenMultipageEditor(file);
        T24BasicEditor editor = null;
        if (multiPageEditor != null) {
            editor = multiPageEditor.getSourceEditor();
        }
        return editor;
    }

    /**
     * Notifies this action delegate that the selection in the workbench has
     * changed.
     * 
     * @param action the action proxy that handles presentation portion of the
     *            action
     * @param selection the current selection, or <code>null</code> if there
     *            is no selection.
     * @see IActionDelegate#selectionChanged(IAction, ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
        Object[] objs = null;
        if (selection != null & (selection instanceof TreeSelection)) {
            objs = ((TreeSelection) selection).toArray();
            action.setEnabled(isAllSelectedObjectsIsProject(objs));
        } else if (selection != null && (selection instanceof IStructuredSelection)) {
            objs = ((IStructuredSelection) selection).toArray();
            action.setEnabled(isAllSelectedObjectsIsProject(objs));
        }
    }

    /**
     * Checks whether all the selected items is of Project type
     * 
     * @param objs
     * @return true if all the selected items is of Project type else false.
     */
    private boolean isAllSelectedObjectsIsProject(Object[] objs) {
        int ObjectArraySize = objs.length;
        if (ObjectArraySize == 0) {
            return false;
        }
        for (int i = 0; i < ObjectArraySize; i++) {
            obj = objs[i];
            if (!(obj instanceof IProject)) {
                return false;
            }
        }
        return true;
    }

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
    }

    public void dispose() {
    }
}
