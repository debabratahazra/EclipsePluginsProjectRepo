package com.temenos.t24.tools.eclipse.basic.t24unit;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;

import com.temenos.t24.tools.eclipse.basic.actions.remote.T24UnitTestActionDelegate;
import com.temenos.t24.tools.eclipse.basic.dialogs.remote.RemoteFileExecuteDialog;
import com.temenos.t24.tools.eclipse.basic.editors.remote.RemoteFileEditorUtil;
import com.temenos.t24.tools.eclipse.basic.editors.remote.T24RemoteFileEditor;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

/**
 * This delegate is responsible for interpreting the selection or active editor
 * (if it applies) and launching an application.
 */
public class T24UnitShortcut implements ILaunchShortcut {

    private ArrayList<IFile> ifileList = null;
    private ArrayList<TestFile> remoteFileList = null;
    private T24UnitTestActionDelegate tUnitTestActionDelegate = new T24UnitTestActionDelegate();
    private String remotePath = "";
    private RemoteSite remoteSite = null;
    private TestFile remoteFileTest = null;

    /** If the selection is of IFile or IProject type this method will be called */
    public void launch(ISelection selection, String mode) {
        if (!getUserInput()) {
            return;
        }
        ifileList = new ArrayList<IFile>();
        ifileList.clear();
        remoteFileList = new ArrayList<TestFile>();
        if (selection instanceof IStructuredSelection) {
            Object[] objs = ((IStructuredSelection) selection).toArray();
            Object obj = null;
            int ObjectArraySize = objs.length;
            for (int i = 0; i < ObjectArraySize; i++) {
                obj = objs[i];
                if ((obj instanceof IFile)) {
                    IFile iFile = (IFile) obj;
                    if (!ifileList.contains(iFile)) {
                        ifileList.add(iFile);
                        remoteFileTest = new TestFile(iFile, StringUtil.removeBasicExtension(iFile.getName()), remoteSite,
                                remotePath, true);
                        remoteFileList.add(remoteFileTest);
                    }
                } else {
                    String selectedProjectFullPath = "";
                    if (obj instanceof IProject) {
                        IProject proj = (IProject) obj;
                        selectedProjectFullPath = proj.getLocation().toOSString();
                    } else {
                        IFolder folder = (IFolder) obj;
                        selectedProjectFullPath = folder.getLocation().toOSString();
                    }
                    buildIFileList(selectedProjectFullPath);
                }
            }
            tUnitTestActionDelegate.run(remoteFileList);
        }
    }

    /** If the selection is made from the editor this method will be called */
    public void launch(IEditorPart editor, String mode) {
        T24RemoteFileEditor currentEditor = RemoteFileEditorUtil.getCurrentEditor();
        boolean isLocalFile = currentEditor.isLocal();
        if (isLocalFile && !getUserInput()) {
            return;
        }
        if (currentEditor != null && currentEditor.isDirty()) {
            currentEditor.doSave();
        }
        IFile iFile = RemoteFileEditorUtil.getCurrentEditor().getIFile();
        ifileList = new ArrayList<IFile>();
        remoteFileList = new ArrayList<TestFile>();
        ifileList.clear();
        ifileList.add(iFile);
        /** If the editor is local show Execute Dialog else won't */
        if (isLocalFile) {
            remoteFileTest = new TestFile(iFile, StringUtil.removeBasicExtension(iFile.getName()), remoteSite, remotePath, true);
        } else {
            remoteFileTest = new TestFile(iFile, StringUtil.removeBasicExtension(iFile.getName()), currentEditor.getRemoteSite(),
                    currentEditor.getServerDirectory(), false);
        }
        remoteFileList.add(remoteFileTest);
        tUnitTestActionDelegate.run(remoteFileList);
    }

    /**
     * Adds the T24Unit Test files present in the current project to the
     * IFileList
     */
    private void buildIFileList(String projectFullPath) {
        ArrayList<String> t24UnitTestFilesInProject = EditorDocumentUtil.getTUnitTestFilesInCurProject(projectFullPath);
        for (int j = 0; j < t24UnitTestFilesInProject.size(); j++) {
            IFile iFile = EditorDocumentUtil.getIFile(projectFullPath, t24UnitTestFilesInProject.get(j));
            if (!ifileList.contains(iFile)) {
                ifileList.add(iFile);
                remoteFileTest = new TestFile(iFile, StringUtil.removeBasicExtension(iFile.getName()), remoteSite, remotePath, true);
                remoteFileList.add(remoteFileTest);
            }
        }
    }

    private boolean getUserInput() {
        RemoteFileExecuteDialog dialog = new RemoteFileExecuteDialog(EclipseUtil.getActiveWindow().getShell());
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
            return true;
        }
    }
}
