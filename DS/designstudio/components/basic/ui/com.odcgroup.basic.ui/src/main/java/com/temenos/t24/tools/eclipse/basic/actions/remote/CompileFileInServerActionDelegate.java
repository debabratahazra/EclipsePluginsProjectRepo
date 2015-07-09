package com.temenos.t24.tools.eclipse.basic.actions.remote;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.dialogs.remote.RemoteFileCompileDialog;
import com.temenos.t24.tools.eclipse.basic.dialogs.remote.RemoteOperationDialog;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicMultiPageEditor;
import com.temenos.t24.tools.eclipse.basic.editors.remote.RemoteFileEditorUtil;
import com.temenos.t24.tools.eclipse.basic.editors.remote.T24RemoteFileEditor;
import com.temenos.t24.tools.eclipse.basic.jremote.MonitoredCompileAction;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.IT24FTPClient;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsDialogHelper;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitePlatform;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtilFactory;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;
import com.temenos.t24.tools.eclipse.basic.views.IT24View;
import com.temenos.t24.tools.eclipse.basic.views.tasks.TasksView;

/**
 * Action to initiate the compilation of a module in the remote server. Based on
 * Connection type selected in the RemoteSite either JCA or HTTP will be used.
 * It is run in two threads; one is a progress monitor that provides a visual
 * indication that work in being done, and another is the compilation thread.
 * 
 * @author yasar arafath
 */
public class CompileFileInServerActionDelegate implements IWorkbenchWindowActionDelegate {

    /** IFile points to physical file holding the basicFile */
    private IFile iFile = null;
    private String basicFilenameNoPrefix = "";
    private StringUtil su = new StringUtil();
    private Object obj = null;
    private String remotePath = "";
    private RemoteSite remoteSite = null;

    public void init(IWorkbenchWindow window) {
    }

    /**
     * Method invoked by the framework. The main logic of the action goes here.
     */
    public void run(IAction action) {
        T24RemoteFileEditor currentEditor = RemoteFileEditorUtil.getCurrentEditor();
        boolean isLocalFile = currentEditor.isLocal();
        if (isLocalFile && !getUserInput(iFile)) {
            return;
        }
        if (!isLocalFile) {
            remoteSite = currentEditor.getRemoteSite();
            remotePath = currentEditor.getServerDirectory();
            basicFilenameNoPrefix = currentEditor.getBasicFilenameNoPrefix();
        }
        boolean saved = false;
        if (currentEditor != null && currentEditor.isDirty()) {
            currentEditor.doSave();
        }
        saved = RemoteOperationsManager.getInstance().saveFile(remoteSite, remotePath, basicFilenameNoPrefix, iFile);
        boolean compiled = false;
        if (saved) {
            compiled = compileThroughJCA(remoteSite, remotePath, iFile, basicFilenameNoPrefix);
            if (compiled) {
                retrieveFile();
            }
        } else {
            RemoteOperationsLog.error("Unable to save file " + basicFilenameNoPrefix + " in server");
        }
    }

    private boolean getUserInput(IFile file) {
        basicFilenameNoPrefix = StringUtil.removeBasicExtension(file.getName());
        RemoteOperationDialog dialog = new RemoteFileCompileDialog(EclipseUtil.getActiveWindow().getShell(), basicFilenameNoPrefix);
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
            String serverDir = dialog.getServerDir();
            String siteName = dialog.getSiteName();
            remoteSite = RemoteSitesManager.getInstance().getRemoteSiteFromName(siteName);
            RemoteOperationsDialogHelper.updateDefaultServerDirectory(serverDir);
            if (serverDir.length() > 0) {
                remotePath = RemoteFileEditorUtil.calculateRemotePath(serverDir, remoteSite);
            } else {
                remotePath = dialog.getCurrentLocation();
            }
            return true;
        }
    }

    private void retrieveFile() {
        closeCurrentEditor();
        IT24FTPClient ftpClient = null;
        ftpClient = RemoteSitesManager.getInstance().getFTPClient(remoteSite.getSiteName());
        if (!RemoteOperationsManager.getInstance().loadFile(ftpClient, remoteSite, remotePath, basicFilenameNoPrefix, iFile.getLocation().toOSString())) {
            RemoteOperationsLog.error("Unable to open file" + basicFilenameNoPrefix);
        }
    }

    private void closeCurrentEditor() {
        T24BasicMultiPageEditor editor = EditorDocumentUtil.getActiveMultiPageEditor();
        IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        activePage.closeEditor(editor, false);
    }

    public boolean compileThroughJCA(RemoteSite remoteSite, String remotePath, IFile file, String fileName) {
        MonitoredCompileAction compileAction = new MonitoredCompileAction();
        Response response = compileAction.execute(remoteSite, getRemotePath(), fileName);
        return analyseCompilationResults(response, remotePath, file);
    }
    
    private String getRemotePath(){
        
        if(RemoteSitePlatform.NT.equals(remoteSite.getPlatform())){
           return  StringUtil.getFolderFromServerDirectory(remotePath);
        }else{
            return remotePath;
        }     
    }

    /**
     * Takes different actions based on whether the compilation passed or
     * failed.
     * 
     */
    private boolean analyseCompilationResults(Response response, String serverDir, IFile iFile) {
        ProtocolUtil pu = new ProtocolUtil();
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        String fullPath = iFile.getLocation().removeLastSegments(1).toOSString();
        String basicFilenameNoPrefix = StringUtil.removeBasicExtension(iFile.getName());
        // ANALYSE COMPILATION RESPONSE FROM SERVER.
        if (response.getPassed()) {
            // COMPILATION SUCCESSFUL
            /*
             * Typical outcome from a compilation looks like: <compileout> Some
             * messages regarding compilation settings <compileoutput> <errors>
             * ... errors details ... </errors> <warnings> ... warning details
             * ... </warnings> <RatingDetails> ... rating details ...
             * </RatingDetails> </compileoutput> </compileout>
             */
            // XML message with all results from Server
            String compileoutXmlResult = "";
            String channelName = "";
            compileoutXmlResult = (String) response.getObject();
            boolean getNewRating = true;
            String oldRating = pu.getCompilationRating(compileoutXmlResult, !getNewRating);
            // Store xml result in a property.
            // The structure of the property is like:
            // property =
            // local_workspace<<NR>>file_name<<NR>>compilation_xml_result
            if ("".equals(compileoutXmlResult)) {
                // No compilation result was received. So just fill in the
                // result with an empty xml response.
                compileoutXmlResult = "<compileoutput></compileoutput>";
            }
            String propertyValue = "true" + "<<NR>>" + fullPath + "<<NR>>" + basicFilenameNoPrefix + "<<NR>>" + compileoutXmlResult;
            mu.updateProperty("t24.remote.compile.xml.response", propertyValue);
            // Format the xml response into a displayable string
            String displayResults = pu.processCompileOutcome(compileoutXmlResult, oldRating);
            // SHOW RESULTS IN CONSOLE
            RemoteOperationsLog.info("(" + response.getActionTimeMillis() + "ms) - " + "Program " + basicFilenameNoPrefix
                    + " was sent for compilation to dir: " + serverDir + channelName + "\n" + "Compilation results:\n"
                    + displayResults);
            // SHOW RESULTS IN A TASK VIEW
            // this shows a table with all the errors and warnings (if any)
            showWarningsErrorsInView();
            return true;
        } else {
            RemoteOperationsLog.error("File " + basicFilenameNoPrefix + " failed to compile. " + response.getRespMessage());
            return false;
        }
    }

    /**
     * Opens a separate view and displays the Warnings and Errors (if any)
     * inside it.
     */
    private void showWarningsErrorsInView() {
        try {
            IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            if (page == null) {
                return;
            }
            // Check whether the view is already active. It if is active then
            // just refresh it,
            // otherwise, create it from scratch.
            IT24View view = (IT24View) page.findView(TasksView.VIEW_ID);
            if (view != null) {
                view.refresh();
            } else {
                TasksView tasksView = new TasksView();
                tasksView = (TasksView) page.showView(tasksView.getViewID());
                tasksView.refresh();
            }
        } catch (PartInitException e) {
            RemoteOperationsLog.error("Failed trying opening " + TasksView.VIEW_ID + ". " + e.getMessage());
        }
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
        String basicFilename = "";
        iFile = null;
        if (selection != null & (selection instanceof TreeSelection)) {
            obj = (Object) ((TreeSelection) selection).getFirstElement();
            if ((obj instanceof IFile)) {
                iFile = (IFile) obj;
            }
        } else if (selection != null && (selection instanceof IStructuredSelection)) {
            obj = (Object) ((IStructuredSelection) selection).getFirstElement();
            if ((obj instanceof IFile)) {
                iFile = (IFile) obj;
            }
        } else if (selection != null & (selection instanceof TextSelection)) { 
            T24RemoteFileEditor editor = RemoteFileEditorUtil.getCurrentEditor();
            if(editor!=null){
            iFile = editor.getIFile();
            }
        }
        if (iFile != null) {
            basicFilename = iFile.getName();
        }
        action
                .setEnabled(!selection.isEmpty() && RemoteFileEditorUtil.isFileInActiveEditor(iFile)
                        && su.isBasicFile(basicFilename));
    }

    public void dispose() {
    }
}
