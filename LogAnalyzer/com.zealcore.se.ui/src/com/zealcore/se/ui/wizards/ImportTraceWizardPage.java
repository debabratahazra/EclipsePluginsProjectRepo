package com.zealcore.se.ui.wizards;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.ide.undo.CreateFileOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;

public class ImportTraceWizardPage extends LTTngFileSystemResourceImportPage {

	private String path;

	public ImportTraceWizardPage(IWorkbench workbench,
			IStructuredSelection selection) {
		super(workbench, selection);
		Object element = ((IStructuredSelection) workbench
				.getActiveWorkbenchWindow().getSelectionService()
				.getSelection()).getFirstElement();
		if (element instanceof IFolder) {
			IFolder f = (IFolder) element;
			path = f.getFullPath().toString();
		}

	}

	public IFile createNewFile(String contents) {

		IPath filePath = new Path(path + "/" + getContainerFieldValue()
				+ LTTNG_FILE_EXT);
		final IFile newFileHandle = createFileHandle(filePath);
		final java.io.InputStream initialContents = null;

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) {
				CreateFileOperation op = new CreateFileOperation(newFileHandle,
						null, initialContents, "New file");
				try {
					op.execute(monitor,
							WorkspaceUndoUtil.getUIInfoAdapter(getShell()));
				} catch (final ExecutionException e) {
				}
			}
		};
		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return null;
		} catch (InvocationTargetException e) {
			// Execution Exceptions are handled above but we may still get
			// unexpected runtime errors.

			return null;
		}

		IFile newFile = newFileHandle;

		try {
			PrintWriter out = new PrintWriter(new FileOutputStream(newFile
					.getLocation().toOSString()));
			out.print("<LTTngTraceInfo>");
			out.print("<Folder>");
			out.print(contents);
			out.print("</Folder>");
			out.print("</LTTngTraceInfo>");
			out.close();
		} catch (FileNotFoundException e) {
		}
		return newFile;
	}

	protected IFile createFileHandle(IPath filePath) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(filePath);
	}

	public boolean finish() {
		if (super.finish()) {
			createNewFile(tracesFolder);
			return true;
		}
		return false;

	}

	public boolean isTraceSelected() {
		if (getSelectionGroup().getCheckedElementCount() <= 0) {
			return false;
		}
		if (!validateTraceFileName()) {
			return false;
		}
		return true;
	}
}
