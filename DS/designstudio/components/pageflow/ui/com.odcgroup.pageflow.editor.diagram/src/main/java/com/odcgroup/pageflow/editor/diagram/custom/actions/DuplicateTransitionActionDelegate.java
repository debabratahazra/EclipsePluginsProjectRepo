package com.odcgroup.pageflow.editor.diagram.custom.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.action.AbstractActionDelegate;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DuplicateRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.odcgroup.pageflow.editor.diagram.custom.commands.OffsetEdgeCommand;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;

/**
 * 
 */
public class DuplicateTransitionActionDelegate extends AbstractActionDelegate implements
		IObjectActionDelegate, IWorkbenchWindowActionDelegate, IHandler {

	/**
	 * Runs this duplicate action delegate by executing a duplicate command on
	 * the selected model elements or views.
	 * 
	 * @see org.eclipse.gmf.runtime.common.ui.internal.action.AbstractActionDelegate#doRun(IProgressMonitor)
	 */
	@SuppressWarnings("rawtypes")
	protected void doRun(IProgressMonitor progressMonitor) {
		DuplicateRequest request = null;
		ICommand cmd = null;
		if (getWorkbenchPart() instanceof IDiagramWorkbenchPart) {
			IStructuredSelection selection = getStructuredSelection();
			request = new DuplicateRequest();
			cmd = getDuplicateViewCommand(selection, getWorkbenchPart(), request);
		} 
		if (cmd != null && cmd.canExecute()) {
			try {
				IStatus status = getActionManager().getOperationHistory().execute(cmd, progressMonitor, null);
				if (status.isOK()) {
					List list = ((DuplicateRequest) request).getDuplicatedViews();
					if (!list.isEmpty() && list.get(0) instanceof Edge) {
						selectTransition((Edge) list.get(0));
					}
				}
			} catch (ExecutionException e) {
				PageflowDiagramEditorPlugin.getInstance().logError("Error duplicating the transition", e);
			}
		}
	}

	/**
	 * Determines if the selection can be duplicated by trying to get a command
	 * to do so.
	 * 
	 * @param selection
	 * @param workbenchPart
	 * @return true if the selection can be duplicated; false otherwise.
	 */
	static boolean canDuplicate(IStructuredSelection selection,
			IWorkbenchPart workbenchPart) {		
		ICommand cmd = getDuplicateViewCommand(selection, workbenchPart, new DuplicateRequest());
		return (cmd != null && cmd.canExecute());
	}

	/**
	 * @param selection
	 * @param workbenchPart
	 * @param request
	 * @return
	 */
	private static ICommand getDuplicateViewCommand(
			IStructuredSelection selection, IWorkbenchPart workbenchPart, DuplicateRequest request) {
		ConnectionEditPart connection = null;
		Object obj = selection.getFirstElement();
		if (obj instanceof ConnectionEditPart) {
			connection = (ConnectionEditPart) obj;
		}
		if (connection != null) {
			request.setEditParts(connection);
			Command cmd = ((IDiagramWorkbenchPart) workbenchPart).getDiagramEditPart().getCommand(request);			
			if (cmd instanceof CompoundCommand) {
				OffsetEdgeCommand ecmd = new OffsetEdgeCommand(getEditingDomain(selection), 
						request.getDuplicatedViews());
				((CompoundCommand) cmd).add(new ICommandProxy(ecmd));
			}
			if (cmd != null && cmd.canExecute()) {
				return new CommandProxy(cmd);
			}
		}
		return null;
	}


	/**
	 * Selects the newly added views on the diagram.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void selectTransition(Edge edge) {
		IDiagramGraphicalViewer viewer = ((IDiagramWorkbenchPart) getWorkbenchPart())
				.getDiagramGraphicalViewer();
		if (viewer != null && edge != null ) {
			List editparts = new ArrayList();
			editparts.add(viewer.getEditPartRegistry().get(edge));
			if (!editparts.isEmpty()) {
				viewer.setSelection(new StructuredSelection(editparts));
			}
		}
	}
	
	

	@SuppressWarnings("rawtypes")
	public static TransactionalEditingDomain getEditingDomain(
			IStructuredSelection selection) {

		for (Iterator i = selection.iterator(); i.hasNext();) {
			EObject element = (EObject) ((IAdaptable) i.next())
					.getAdapter(EObject.class);

			if (element != null) {
				TransactionalEditingDomain editingDomain = TransactionUtil
						.getEditingDomain(element);

				if (editingDomain != null) {
					return editingDomain;
				}
			}
		}
		return null;
	}

	// Documentation copied from superclass
	protected TransactionalEditingDomain getEditingDomain() {
		return getEditingDomain(getStructuredSelection());
	}

	public void addHandlerListener(IHandlerListener handlerListener) {
		// nothing
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		return null;
	}

	public boolean isEnabled() {
		return true;
	}

	public boolean isHandled() {
		return true;
	}

	public void removeHandlerListener(IHandlerListener handlerListener) {
		// nothing

	}
	
	

}
