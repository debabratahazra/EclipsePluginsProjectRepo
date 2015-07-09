package com.temenos.t24.tools.eclipse.basic.actions.remote;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.google.common.io.Closeables;
import com.temenos.t24.tools.eclipse.basic.dialogs.remote.RemoteFileSaveAsDialog;
import com.temenos.t24.tools.eclipse.basic.dialogs.remote.RemoteOperationDialog;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicMultiPageEditor;
import com.temenos.t24.tools.eclipse.basic.editors.remote.RemoteFileEditorUtil;
import com.temenos.t24.tools.eclipse.basic.editors.remote.T24RemoteFileEditor;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsDialogHelper;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

/**
 * Class delegates the save file to server action.
 * 
 * @author ssethupathi
 * 
 */
public class SaveAsRemoteActionDelegate implements
		IWorkbenchWindowActionDelegate {

	private ISelection selection;
	private IWorkbenchWindow window;
	private IFile file;
	private T24RemoteFileEditor editor;
	private boolean saved = false;
	private boolean isCancelled = false;

	/**
	 * {@inheritDoc}
	 */
	public void dispose() {
	}

	/**
	 * {@inheritDoc}
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	/**
	 * {@inheritDoc}
	 */
	public void run(IAction action) {
		isCancelled = false;
		T24BasicMultiPageEditor multipageEditor = EditorDocumentUtil
				.getActiveMultiPageEditor();
		if (multipageEditor == null) {
			return;
		}
		editor = multipageEditor.getSourceEditor();
		saveEditor(editor);
		if (!isCancelled) {
			informUser();
		}
	}

	/**
	 * Performs the Save As operation on the editor passed in.
	 * 
	 * @param currentEditor
	 */
	public void saveEditor(T24RemoteFileEditor currentEditor) {
		this.editor = currentEditor;
		file = editor.getIFile();
		String fileName = StringUtil.removeBasicExtension(file.getName());
		RemoteSite remoteSite = editor.getRemoteSite();
		String remotePath = editor.getServerDirectory();
		FileAttributes oldAttr = new FileAttributes(fileName, remoteSite,
				remotePath);
		FileAttributes newAttr = getUserInput(oldAttr);
		if (newAttr == null) {
			isCancelled = true;
			return;
		}
		if (fileName.equals(newAttr.getFileName())) {
			if (editor.isDirty()) {
				editor.doSave();
			}
			saved = performSave(newAttr);
			if (saved) {
				editor = RemoteFileEditorUtil.convertEditor(editor, newAttr
						.getRemoteSite(), newAttr.getRemoteDir(), false,
						newAttr.getFileName());
				RemoteFileEditorUtil.updateEditorTitle();
			} else {
				editor = RemoteFileEditorUtil.convertEditor(editor, oldAttr
						.getRemoteSite(), oldAttr.getRemoteDir(), false,
						oldAttr.getFileName());
			}
		} else {
			handleNameChange(newAttr);
		}
	}

	/**
	 * Handles the change in name while saving a file to a {@link RemoteSite}.
	 * 
	 * @param newAttr
	 */
	private void handleNameChange(FileAttributes newAttr) {
	    InputStream is = null;
	    try {
            IPath newPath = new Path(file.getParent().getFullPath().toOSString() + "/" + newAttr.getFileName() + ".b");
            IFile newFile = ResourcesPlugin.getWorkspace().getRoot().getFile(newPath);
            RemoteFileEditorUtil.completeDocument(editor);
            String editorContents = EditorDocumentUtil.getEditorContents(editor);
            if (editorContents == null) {
                return;
            }
            is = new ByteArrayInputStream(editorContents.getBytes());
            try {
                newFile.create(is, IResource.NONE, null);
            } catch (CoreException e) {
                e.printStackTrace();
            }
            editor.close(false);
            file = newFile;
            saved = performSave(newAttr);
            if (saved) {
                RemoteSite remoteSite = newAttr.getRemoteSite();
                String remoteDir = newAttr.getRemoteDir();
                T24BasicMultiPageEditor multipageEditor = EditorDocumentUtil.getActiveMultiPageEditor();
                IWorkbenchPage activePage = getActivePage();
                if (activePage != null) {
                    activePage.closeEditor(multipageEditor, false);
                }
                RemoteFileEditorUtil.openFileWithEditor(remoteSite, remoteDir, newFile.getLocation().toOSString());
                multipageEditor = EditorDocumentUtil.getActiveMultiPageEditor();
                editor = multipageEditor.getSourceEditor();
            }
        } finally {
            Closeables.closeQuietly(is);
        }
	}

	private boolean performSave(FileAttributes attr) {
		return RemoteOperationsManager.getInstance().saveFile(
				attr.getRemoteSite(), attr.getRemoteDir(), attr.getFileName(),
				file);
	}

	private FileAttributes getUserInput(FileAttributes currentAttr) {
		RemoteOperationDialog saveDialog = new RemoteFileSaveAsDialog(
				getShell(), currentAttr.getFileName(), currentAttr
						.getRemoteSite(), currentAttr.getRemoteDir());
		if (saveDialog.open() != InputDialog.OK) {
			return null;
		}
		String fileName = saveDialog.getFileName();
		String serverDir = saveDialog.getServerDir();
		String remoteDir = "";
		RemoteSite remoteSite = RemoteSitesManager.getInstance()
				.getRemoteSiteFromName(saveDialog.getSiteName());
		RemoteOperationsDialogHelper.updateDefaultServerDirectory(serverDir);
		if (serverDir.length() > 0) {
			remoteDir = RemoteFileEditorUtil.calculateRemotePath(serverDir,
					remoteSite);
		} else {
			remoteDir = saveDialog.getCurrentLocation();
		}
		FileAttributes attr = new FileAttributes(fileName, remoteSite,
				remoteDir);
		return attr;
	}

	/**
	 * {@inheritDoc}
	 */
	public void selectionChanged(IAction action, ISelection selection1) {
		this.selection = selection1;
		action.setEnabled(false);
		if (selection instanceof IFile) {
			Object obj = (Object) ((IStructuredSelection) selection)
					.getFirstElement();
			IFile file = (IFile) obj;
			if (RemoteFileEditorUtil.isFileInActiveEditor(file)) {
				action.setEnabled(true);
			}
			return;
		}
		if (EditorDocumentUtil.getActiveEditor() != null) {
			action.setEnabled(true);
			return;
		}
	}

	private Shell getShell() {
		if (window != null) {
			return window.getShell();
		}
		return null;
	}

	private IWorkbenchPage getActivePage() {
		if (window == null) {
			window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		}
		return window.getActivePage();
	}

	private void informUser() {
		String fileName = StringUtil.removeBasicExtension(file.getName());
		String remotePath = editor.getServerDirectory();
		if (saved) {
			RemoteOperationsLog.info("File " + fileName + " saved to "
					+ remotePath);
		} else {
			RemoteOperationsLog.error("Unable to save file " + fileName);
		}
	}

	private class FileAttributes {

		private String fileName;
		private RemoteSite remoteSite;
		private String remoteDir;

		public FileAttributes(String fileName, RemoteSite remoteSite,
				String remoteDir) {
			this.fileName = fileName;
			this.remoteDir = remoteDir;
			this.remoteSite = remoteSite;
		}

		public String getFileName() {
			return fileName;
		}

		public RemoteSite getRemoteSite() {
			return remoteSite;
		}

		public String getRemoteDir() {
			return remoteDir;
		}
	}
}
