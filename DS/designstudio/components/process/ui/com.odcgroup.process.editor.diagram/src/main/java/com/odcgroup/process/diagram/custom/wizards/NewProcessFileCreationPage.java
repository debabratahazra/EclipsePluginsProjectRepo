package com.odcgroup.process.diagram.custom.wizards;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.util.EditorUtil;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.odcgroup.process.diagram.edit.parts.ProcessEditPart;
import com.odcgroup.process.diagram.part.Messages;
import com.odcgroup.process.diagram.part.ProcessDiagramEditorPlugin;
import com.odcgroup.process.diagram.part.ProcessDiagramEditorUtil;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;
import com.odcgroup.workbench.ui.wizards.AbstractNewModelResourceCreationPage;

public class NewProcessFileCreationPage extends AbstractNewModelResourceCreationPage {


	protected Resource diagram;
	
	/**
	 * OCS-24647 (changed extn from process to workflow)
	 * 
	 * @param pageName
	 * @param containerPath
	 */
	protected NewProcessFileCreationPage(String pageName, IWorkbench workbench,
			IPath containerFullPath) {
		super(pageName, workbench, containerFullPath, "workflow");
	}

	/**
	 * @param monitor the <code>IProgressMonitor</code> to use to indicate progress and check for cancellation
	 * @return boolean indicating whether the creation and opening the Diagram was successful
	 */
	public boolean doFinish(IProgressMonitor monitor) {
		IRunnableWithProgress op = new WorkspaceModifyOperation(null) {

			protected void execute(IProgressMonitor monitor)
					throws CoreException, InterruptedException {
				
				URI diagramUri = URI.createPlatformResourceURI(getContainerFullPath().toOSString()+"/"+getFileName(), false);
				diagram = ProcessDiagramEditorUtil.createDiagram(diagramUri, monitor, fileNameField.getText().trim(), description.getText().trim());
				if (diagram != null) {
					try {
						ProcessDiagramEditorUtil.openDiagram(diagram);
					} catch (PartInitException e) {
						ErrorDialog.openError(getContainer().getShell(),
								Messages.ProcessCreationWizardOpenEditorError,
								null, e.getStatus());
					}
				}
			}
		};
		try {
			getContainer().run(false, true, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof CoreException) {
				ErrorDialog.openError(getContainer().getShell(),
						Messages.ProcessCreationWizardCreationError, null,
						((CoreException) e.getTargetException()).getStatus());
			} else {
				ProcessDiagramEditorPlugin.getInstance().logError(
						"Error creating diagram", e.getTargetException()); //$NON-NLS-1$
			}
			return false;
		}
		return diagram != null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#getInitialContents()
	 */
	protected InputStream getInitialContents() {
		return EditorUtil.getInitialContents();
	}
	
	/**
	 * @return
	 */
	protected String getDiagramKind() {
		return ProcessEditPart.MODEL_ID;
	}
	
	/**
	 * Setup help
	 * @param parent
	 */
	public void createControl(Composite parent) {
		super.createControl(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent.getShell(), IOfsHelpContextIds.NEW_WORKFLOW);
	}
	
}
