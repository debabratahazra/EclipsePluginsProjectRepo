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
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;

import com.odcgroup.pageflow.editor.diagram.custom.dialog.ToggleIDNameDialog;
import com.odcgroup.pageflow.editor.diagram.custom.parts.PageflowShapeEditPart;
import com.odcgroup.pageflow.editor.diagram.custom.util.PageflowPreferenceConstants;
import com.odcgroup.pageflow.editor.diagram.edit.parts.SubPageflowStateDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.ViewStateDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;

public class ToggleIDCommand extends AbstractCommand {
	
	private List affectedFiles;

	public ToggleIDCommand(String label, List affectedFiles) {
		super(label, null);
		this.affectedFiles = affectedFiles;
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.core.command.AbstractCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor arg0,
			IAdaptable arg1) throws ExecutionException {
		
		ToggleIDNameDialog dialog = new ToggleIDNameDialog(Display.getCurrent().getActiveShell());
		dialog.open();
		
		if (dialog.getReturnCode() == ToggleIDNameDialog.OK){
			Iterator iter = affectedFiles.iterator();
	    	while(iter.hasNext()){
	    		EditPart ep = (EditPart)iter.next();
	    		List children = new ArrayList();
	    		if (ep instanceof ConnectionNodeEditPart) {
	    			ConnectionNodeEditPart connection = (ConnectionNodeEditPart)ep;
	    			connection.refresh();
	        		children = connection.getChildren();
	    		} else if (ep instanceof PageflowShapeEditPart){
	    			PageflowShapeEditPart dp = (PageflowShapeEditPart)ep;
	        		children = dp.getChildren();
	    		}
	    		
	    		Iterator child = children.iterator();
	    		while(child.hasNext()){
	    			GraphicalEditPart gep = (GraphicalEditPart) child.next();	
	    			if (gep instanceof LabelEditPart) {
						LabelEditPart lep = (LabelEditPart)gep;
						lep.refresh();
					} else if (gep instanceof ViewStateDisplayNameEditPart){
						ViewStateDisplayNameEditPart vep = (ViewStateDisplayNameEditPart)gep;
						vep.refresh();
					} else if (gep instanceof SubPageflowStateDisplayNameEditPart){
						SubPageflowStateDisplayNameEditPart sep = (SubPageflowStateDisplayNameEditPart)gep;
						gep.refresh();
					}
	    		}
	    	}
		}
        return CommandResult.newOKCommandResult();
	}

	@Override
	protected CommandResult doRedoWithResult(IProgressMonitor arg0,
			IAdaptable arg1) throws ExecutionException {
		return null;
	}

	@Override
	protected CommandResult doUndoWithResult(IProgressMonitor arg0,
			IAdaptable arg1) throws ExecutionException {
		return null;
	}
	
	/**
	 * @return
	 */
	private boolean getPreferredValue() {
		return getPreferenceStore().getBoolean(PageflowPreferenceConstants.PREF_SHOWID_LABEL);
	}
	
	/**
	 * @return
	 */
	private IPreferenceStore getPreferenceStore() {
		return PageflowDiagramEditorPlugin.getInstance().getPreferenceStore();
	}
	
	

}
