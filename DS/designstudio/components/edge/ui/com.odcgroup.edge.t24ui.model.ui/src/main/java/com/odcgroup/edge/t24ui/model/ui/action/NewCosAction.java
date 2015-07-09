package com.odcgroup.edge.t24ui.model.ui.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Logger;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.edge.t24ui.model.ui.CosUICore;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.ui.OfsUICore;

/**
 * 
 * 
 * @author RAMAPRIYAMN
 * 
 */
public class NewCosAction extends Action {

	private IStructuredSelection selection;

	public NewCosAction() {
		setText("New Composite Screen...");
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(CosUICore.PLUGIN_ID, "icons/cos.png"));
	}

	/**
	 * @param file
	 * @param content
	 * @throws PartInitException
	 * @throws IOException
	 */
	private void writeToTextEditor(final IFile file, StringBuffer content) throws PartInitException {
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(file.getLocation().toOSString());
			fout.write(content.toString().getBytes());
			fout.flush();
			fout.close();

		} catch (IOException e) {
			Logger.getLogger(" Writing to file failed " + e);
		} finally {
			try {
				fout.close();
			} catch (IOException e) {
				Logger.getLogger(" Close file failed " + e);
			}
		}

	}

	/**
	 * @return
	 */
	private StringBuffer esonFileContent(String cosName, String cosDomain) {
		StringBuffer content = new StringBuffer("");

		content.append("use t24ui.* \n");
		content.append("CompositeScreen " + cosName + "{\n");
		content.append("\tdomain: \"" + cosDomain + "\"\n");
		content.append("\tpattern:Rows\n");
		content.append("\tpanels: [\n");
		content.append("\t\tSingleComponentPanel default {\n");
		content.append("\t\t\thostableContentAll: false\n");
		content.append("\t\t\thostableBespokeCOSContentAll: false\n");
		content.append("\t\t\thostableCOSContentAll: false\n");
		content.append("\t\t\thostableEnquiryContentAll: false\n");
		content.append("\t\t\thostableScreenContentAll: false\n");
		content.append("\t\t\theight: \"auto\"\n");
		content.append("\t\t\twidth: \"auto\"\n");
		content.append("\t\t\thorizontalOverflowOption: :HIDE_OVERFLOW\n");
		content.append("\t\t\tverticalOverflowOption: :HIDE_OVERFLOW\n");
		content.append("\t\t}\n");
		content.append("\t]\n");
		content.append("}\n");

		return content;

	}

	private static String deduceCompositeScreenDomain(File dir, String cosName) {
		final String fullPath = dir.toString().replace('\\', '/');
		final String toFind = "/eson/COS/";
		final int i = fullPath.indexOf(toFind);
		if (i >= 0){
			final String subPath = fullPath.substring(i + toFind.length());
			return subPath.replace('/', '.') + '.' + cosName;
		}else{
			/*
			 * No domain, return the cos name
			 */
			return cosName;
		}
	}

	@Override
	public void run() {
		try {
			InputDialog id = new InputDialog(Display.getCurrent().getActiveShell(), "COS", "Enter COS name", "Sample",
					null);
			id.open();
			if (id.getReturnCode() == 0) {
				final IPath selectionFullPath = getSelectionFullPath();

				final String cosName = id.getValue();
				final String cosDomain = deduceCompositeScreenDomain(selectionFullPath.toFile(), cosName);

				final IFile file = NewCOSUtil.createNewFile(cosName + ".eson", selectionFullPath);

				StringBuffer content = esonFileContent(cosName, cosDomain);

				writeToTextEditor(file, content);
				// open editor
				NewCOSUtil.openEditor(file);
			}
		} catch (Exception e) {
			Logger.getLogger(" Create / Write to file failed " + e);
		}
	}

	/**
	 * @param resource
	 * @param file
	 * @param monitor
	 * @throws CoreException
	 */
	public static void doSave(Resource resource, IFile file, IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Saving model", 2);

		try {
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			resource.save(os, Collections.EMPTY_MAP);
			monitor.worked(1);

			if (file.exists()) {
				file.setContents(new ByteArrayInputStream(os.toByteArray()), false, true, new SubProgressMonitor(
						monitor, 1));
				monitor.worked(1);
			} else {
				file.create(new ByteArrayInputStream(os.toByteArray()), false, new SubProgressMonitor(monitor, 1));
				monitor.worked(1);
			}
			resource.save(os, Collections.EMPTY_MAP);
		} catch (IOException e) {
		} finally {
			monitor.done();
		}
	}

	@Override
	public boolean isEnabled() {
		if (!getSelection().isEmpty()) {
			Object obj = getSelection().getFirstElement();
			if (obj instanceof IOfsModelPackage) {
				setEnabled(true);
			}
		}
		return super.isEnabled();
	}

	/**
	 * Returns the configured selection. If no selection has been configured
	 * using {@link #setSelection(IStructuredSelection)}, the currently selected
	 * element of the active workbench is returned.
	 * 
	 * @return the configured selection
	 */
	protected IStructuredSelection getSelection() {
		if (selection == null) {
			return evaluateCurrentSelection();
		}
		return selection;
	}

	/**
	 * @return
	 */
	private IStructuredSelection evaluateCurrentSelection() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			ISelection selection = window.getSelectionService().getSelection();
			if (selection instanceof IStructuredSelection) {
				return (IStructuredSelection) selection;
			}
		}
		return StructuredSelection.EMPTY;
	}

	/**
	 * @param selection
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected IPath getSelectionFullPath() {
		Iterator it = getSelection().iterator();
		if (it.hasNext()) {
			Object obj = it.next();
			IResource selectedResource = null;
			if (obj instanceof IResource) {
				selectedResource = (IResource) obj;
			} else if (obj instanceof IAdaptable) {
				selectedResource = (IResource) ((IAdaptable) obj).getAdapter(IResource.class);
			}
			if (selectedResource != null) {
				if (!(selectedResource instanceof IFolder && OfsCore.isOfsModelPackage((IFolder) selectedResource))) {
					selectedResource = selectedResource.getProject().getFolder("eson");
				}
			}
			if (selectedResource != null) {
				return selectedResource.getFullPath();
			}
		}
		return null;
	}

}
