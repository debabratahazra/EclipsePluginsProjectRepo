package com.odcgroup.process.diagram.custom.wizards;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.util.IDEEditorUtil;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.util.DiagramFileCreator;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;

import com.odcgroup.process.diagram.part.ProcessDiagramEditorPlugin;
import com.odcgroup.process.model.Process;
import com.odcgroup.process.model.ProcessFactory;

public class ProcessDiagramCustomEditorUtil extends IDEEditorUtil {

	private static String processName = null;
	private static String description = null;

	/**
	 * @generated NOT
	 */
	public static final IFile createAndOpenDiagram(
			DiagramFileCreator diagramFileCreator, IPath containerPath,
			String fileName, InputStream initialContents, String kind,
			IWorkbenchWindow window, IProgressMonitor progressMonitor,
			boolean openEditor, boolean saveDiagram, String processName, String description) {
		ProcessDiagramCustomEditorUtil.processName = processName;
		ProcessDiagramCustomEditorUtil.description = description;
		IFile diagramFile = ProcessDiagramCustomEditorUtil.createNewDiagramFile(
				diagramFileCreator, containerPath, fileName, initialContents,
				kind, window.getShell(), progressMonitor);
		if (diagramFile != null && openEditor) {
			IDEEditorUtil.openDiagram(diagramFile, window, saveDiagram,
					progressMonitor);
		}
		return diagramFile;
	}

	/**
	 * <p>
	 * This method should be called within a workspace modify operation since it creates resources.
	 * </p>
	 * @generated NOT
	 * @return the created file resource, or <code>null</code> if the file was not created
	 */
	public static final IFile createNewDiagramFile(
			DiagramFileCreator diagramFileCreator, IPath containerFullPath,
			String fileName, InputStream initialContents, String kind,
			Shell shell, IProgressMonitor progressMonitor) {

		TransactionalEditingDomain editingDomain = GMFEditingDomainFactory.INSTANCE
				.createEditingDomain();
		ResourceSet resourceSet = editingDomain.getResourceSet();
		progressMonitor.beginTask("Creating diagram and model files", 4); //$NON-NLS-1$
		final IProgressMonitor subProgressMonitor = new SubProgressMonitor(
				progressMonitor, 1);
		final IFile diagramFile = diagramFileCreator.createNewFile(
				containerFullPath, fileName, initialContents, shell,
				new IRunnableContext() {
					public void run(boolean fork, boolean cancelable,
							IRunnableWithProgress runnable)
							throws InvocationTargetException,
							InterruptedException {
						runnable.run(subProgressMonitor);
					}
				});
		final Resource diagramResource = resourceSet
				.createResource(URI.createPlatformResourceURI(diagramFile
						.getFullPath().toString()));
		List affectedFiles = new ArrayList();
		affectedFiles.add(diagramFile);

		final String kindParam = kind;
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(
				editingDomain, "Creating diagram and model", affectedFiles) { //$NON-NLS-1$
			protected CommandResult doExecuteWithResult(
					IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				com.odcgroup.process.model.Process model = createInitialModel();
				diagramResource.getContents().add(model);
				Diagram diagram = ViewService.createDiagram(model, kindParam,
						ProcessDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				if (diagram != null) {
					diagramResource.getContents().add(diagram);
					diagram.setName(diagramFile.getName());
					diagram.setElement(model);
				}
				try {
					diagramResource.save(Collections.EMPTY_MAP);
				} catch (IOException e) {

					ProcessDiagramEditorPlugin.getInstance().logError(
							"Unable to store model and diagram resources", e); //$NON-NLS-1$
				}
				return CommandResult.newOKCommandResult();
			}
		};

		try {
			OperationHistoryFactory.getOperationHistory().execute(command,
					new SubProgressMonitor(progressMonitor, 1), null);
		} catch (ExecutionException e) {
			ProcessDiagramEditorPlugin.getInstance().logError(
					"Unable to create model and diagram", e); //$NON-NLS-1$
		}

		try {
			diagramFile.setCharset(
					"UTF-8", new SubProgressMonitor(progressMonitor, 1)); //$NON-NLS-1$
		} catch (CoreException e) {
			ProcessDiagramEditorPlugin.getInstance().logError(
					"Unable to set charset for diagram file", e); //$NON-NLS-1$
		}

		return diagramFile;	
	}

	/**
	 * Create a new instance of domain element associated with canvas.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Process createInitialModel() {
		Process process = ProcessFactory.eINSTANCE.createProcess();
		process.setName(ProcessDiagramCustomEditorUtil.processName);
		process.setDescription(ProcessDiagramCustomEditorUtil.description);
		return process;
	}
	

}
