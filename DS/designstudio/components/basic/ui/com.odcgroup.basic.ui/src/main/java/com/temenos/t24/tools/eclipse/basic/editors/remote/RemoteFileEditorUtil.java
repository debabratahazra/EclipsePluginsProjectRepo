package com.temenos.t24.tools.eclipse.basic.editors.remote;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.part.FileEditorInput;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicEditor;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicMultiPageEditor;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.IRemoteFile;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

/**
 * This class is used for generic {@link T24RemoteFileEditor} operations like
 * opening a file, performing a save etc.,
 * 
 * @author ssethupathi
 * 
 */
public class RemoteFileEditorUtil {

    /**
     * Opens the {@link IRemoteFile} retrieved from the {@link RemoteSite} into
     * the editor.
     * 
     * @param remoteSite {@link RemoteSite}
     * @param remotePath Remote path of the file
     * @param localPath local path inside the project
     * @return string identifying the problem
     */
    public synchronized static String openFileWithEditor(RemoteSite remoteSite, String remotePath, String localPath) {
        long fileSize = getFileSize(localPath);
        if (fileSize > PluginConstants.MAX_FILE_SIZE) {
            return "File is too big to open.File Size " + fileSize + " bytes.";
        }
        IFile file = getIFile(localPath);
        if (file.isAccessible()) {
            IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            if (window == null) {
                // TODO
            }
            IWorkbenchPage page = window.getActivePage();
            String fileName = file.getName();
            if (page != null) {
                IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(fileName);
                if (desc == null) {
                    desc = PlatformUI.getWorkbench().getEditorRegistry().findEditor(EditorsUI.DEFAULT_TEXT_EDITOR_ID);
                }
                IEditorPart editor = null;
                try {
                    editor = page.openEditor(new FileEditorInput(file), desc.getId());
                    editor.setFocus();
                } catch (PartInitException e) {
                    e.printStackTrace();
                }
                T24BasicMultiPageEditor multipageEditor = null;
                if (editor instanceof T24BasicMultiPageEditor) {
                    multipageEditor = (T24BasicMultiPageEditor) editor;
                }
                T24BasicEditor activeEditor = (T24BasicEditor) EditorDocumentUtil.getActiveEditor();
                if (!(activeEditor instanceof T24RemoteFileEditor)) {
                    return "";
                }
                ((T24RemoteFileEditor) activeEditor).setIFile(file);
                ((T24RemoteFileEditor) activeEditor).setLocal(false);
                ((T24RemoteFileEditor) activeEditor).setServerDirectory(remotePath);
                ((T24RemoteFileEditor) activeEditor).setBasicFilenameNoPrefix(StringUtil.removeBasicExtension(fileName));
                ((T24RemoteFileEditor) activeEditor).setRemoteSite(remoteSite);
                multipageEditor.updateTitleImage();
            }
        } else {
            // TODO
        }
        return "";
    }

    /**
     * Creates and returns the {@link IFile} object from the passed in local
     * file path.
     * 
     * @param filePath local path
     * @return {@link IFile} file
     */
    public synchronized static IFile getIFile(String filePath) {
        IFile iFile = null;
        try {
            IWorkspace workspace = ResourcesPlugin.getWorkspace();
            IWorkspaceRoot root = workspace.getRoot();
            IPath path = new Path(filePath);
            iFile = root.getFileForLocation(path);
            // Refresh one level the iFile, in case it has just been added.
            iFile.refreshLocal(IResource.DEPTH_ONE, null);
        } catch (CoreException e) {
            e.printStackTrace();
        }
        return iFile;
    }

    /**
     * Saves the file associated with the passed-in editor to the
     * {@link RemoteSite}.
     * 
     * @param editor
     * @return true if saved and false otherwise.
     */
    public synchronized static boolean saveEditorContents(T24RemoteFileEditor editor) {
        if (editor == null) {
            return false;
        }
        return RemoteOperationsManager.getInstance().saveFile(editor.getRemoteSite(), editor.getServerDirectory(),
                editor.getBasicFilenameNoPrefix(), editor.getIFile());
    }

    /**
     * Returns the remote path of the file in the current editor.
     * 
     * @return remote path
     */
    public synchronized static String getCurrentRemotePath() {
        T24RemoteFileEditor editor = getCurrentEditor();
        if (editor != null) {
            return editor.getServerDirectory();
        }
        return "";
    }

    /**
     * Returns the {@link RemoteSite} of the file in the current editor.
     * 
     * @return remote Site
     */
    public synchronized static RemoteSite getCurrentRemoteSite() {
        T24RemoteFileEditor editor = getCurrentEditor();
        if (editor != null) {
            return editor.getRemoteSite();
        }
        return null;
    }

    /**
     * Returns the current active {@link T24RemoteFileEditor}
     * 
     * @return remote file editor
     */
    public synchronized static T24RemoteFileEditor getCurrentEditor() {
        T24BasicEditor editor = EditorDocumentUtil.getActiveEditor();
        if (editor!=null && editor instanceof T24RemoteFileEditor) {
            return (T24RemoteFileEditor) editor;
        }
        return null;
    }

    /**
     * Converts the passed-in editor to a local editor by removing
     * {@link RemoteSite} related information.
     * 
     * @param editor
     * @return local editor
     */
    public synchronized static T24RemoteFileEditor convertAsLocalEditor(T24RemoteFileEditor editor) {
        return convertEditor(editor, null, "", true, "");
    }

    /**
     * Converts the editor by changing the passed-in {@link RemoteSite}
     * information.
     * 
     * @param editor
     * @param remoteSite
     * @param serverDir
     * @param isLocal
     * @param fileName
     * @return converted editor
     */
    public synchronized static T24RemoteFileEditor convertEditor(T24RemoteFileEditor editor, RemoteSite remoteSite,
            String serverDir, boolean isLocal, String fileName) {
        if (editor == null) {
            return null;
        }
        editor.setRemoteSite(remoteSite);
        editor.setServerDirectory(serverDir);
        editor.setLocal(isLocal);
        editor.setBasicFilenameNoPrefix(fileName);
        return editor;
    }

    /**
     * Updates the editor title items such as image and tooltip. Called when an
     * editor is converted.
     */
    public synchronized static void updateEditorTitle() {
        T24BasicMultiPageEditor multipageEditor = EditorDocumentUtil.getActiveMultiPageEditor();
        if (multipageEditor != null) {
            multipageEditor.updateTitleImage();
        }
    }

    public synchronized static boolean isFileInActiveEditor(IFile file) {
        T24BasicMultiPageEditor editorFromFile = EditorDocumentUtil.findOpenMultipageEditor(file);
        T24BasicMultiPageEditor activeEditor = EditorDocumentUtil.getActiveMultiPageEditor();
        if (editorFromFile == null || activeEditor == null) {
            return false;
        }
        return editorFromFile.equals(activeEditor);
    }

    /**
     * Checks whether the passed-in document is complete or not in line with the
     * T24 source file which needs a carriage return at the end of the code.
     * 
     * @param document
     * @return true if the last line ended properly false otherwise.
     */
    public synchronized static boolean isDocumentComplete(IDocument document) {
        int lastLineNumber = document.getNumberOfLines() - 1;
        try {
            int lastLineOffset = document.getLineOffset(lastLineNumber);
            int lastLineLength = document.getLineLength(lastLineNumber);
            String lastLine = document.get(lastLineOffset, lastLineLength);
            if (StringUtil.isEmpty(lastLine) || document.getLineDelimiter(lastLineNumber) != null) {
                return true;
            }
        } catch (BadLocationException e) {
            return false;
        }
        return false;
    }

    private synchronized static long getFileSize(String localPath) {
        File file = new File(localPath);
        long fileSize = file.length();
        return fileSize;
    }
    /**
     * Adds carriage return character at the last line of the source code. 
     * @param editor
     */
    public synchronized static void completeDocument(T24BasicEditor editor) {
        // T24 (JED, Compiler) requires the last line of the source code ends
        // with a carriage return. When not available, we add it before saving.
        IDocument document = EditorDocumentUtil.getDocument(editor);
        if (!RemoteFileEditorUtil.isDocumentComplete(document)) {
            int offset = document.getLength();
            String content = document.get();
            try {
                document.replace(0, offset, content + "\r\n");
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }
    

    public synchronized static String calculateRemotePath(String serverDir, RemoteSite site) {
          
        String homeDirectory = RemoteOperationsManager.getInstance().getHomeDirectory(site);
         if(homeDirectory==null){
         return null;  
        }
        String remotePath = "";
        RemoteOperationsManager remoteOperationsManager = RemoteOperationsManager.getInstance();
        if (serverDir.equals(PluginConstants.SERVER_PRIMARY_SOURCE)) {
            remotePath = remoteOperationsManager.getParentDirectory(site, homeDirectory)+ "/" + PluginConstants.SERVER_PRIMARY_SOURCE;
        } else if(serverDir.equals(PluginConstants.SERVER_PRIMARY_SOURCE_ALTERNATE)){
            remotePath = remoteOperationsManager.getParentDirectory(site, homeDirectory)+ "/" + PluginConstants.SERVER_PRIMARY_SOURCE_ALTERNATE;
        }  else {
            String remoteSiteSeparator = homeDirectory.contains("/") ? "/" : "\\";
            remotePath = homeDirectory + remoteSiteSeparator + serverDir;
        }
        return remotePath;
    }
}
