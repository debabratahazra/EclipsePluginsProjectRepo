package com.odcgroup.pageflow.editor.diagram.custom.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.LabelEditPart;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.pageflow.editor.diagram.custom.dialog.ActionLabelDisplaySelectionDialog;
import com.odcgroup.pageflow.editor.diagram.edit.parts.DecisionStateEditPart;

public class TransitionLabelDisplayCommand extends AbstractCommand {

	private Shell parentShell;
	private List content;

	/**
	 * @param label
	 * @param parentShell
	 */
	public TransitionLabelDisplayCommand(String label, Shell parentShell, List content) {
		super(label, null);
		setParentShell(parentShell);
		this.content = content;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.core.command.AbstractCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor,
			IAdaptable info) throws ExecutionException {
		ActionLabelDisplaySelectionDialog dialog = new ActionLabelDisplaySelectionDialog(parentShell);
        dialog.open();
        if (dialog.getReturnCode() == ActionLabelDisplaySelectionDialog.OK){
        	Iterator iter = content.iterator();
        	while(iter.hasNext()){
        		EditPart ep = (EditPart)iter.next();
        		List children = new ArrayList();
        		if (ep instanceof ConnectionNodeEditPart) {
        			ConnectionNodeEditPart connection = (ConnectionNodeEditPart)ep;
            		children = connection.getChildren();
        		} else if (ep instanceof DecisionStateEditPart){
        			DecisionStateEditPart dp = (DecisionStateEditPart)ep;
            		children = dp.getChildren();
        		}
        		
        		Iterator child = children.iterator();
        		while(child.hasNext()){
        			GraphicalEditPart gep = (GraphicalEditPart) child.next();	
    				if (gep instanceof LabelEditPart) {
    					LabelEditPart lep = (LabelEditPart)gep;
    					lep.refresh();
    				}
        		}
        	}
        }
        return CommandResult.newOKCommandResult(dialog.getReturnCode());
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.core.command.AbstractCommand#doRedoWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor,
			IAdaptable info) throws ExecutionException {
		return null;
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.core.command.AbstractCommand#doUndoWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor,
			IAdaptable info) throws ExecutionException {
		return null;
	}


	/**
	 * @return
	 */
	public Shell getParentShell() {
		return parentShell;
	}


	/**
	 * @param parentShell
	 */
	public void setParentShell(Shell parentShell) {
		this.parentShell = parentShell;
	}
	

}
