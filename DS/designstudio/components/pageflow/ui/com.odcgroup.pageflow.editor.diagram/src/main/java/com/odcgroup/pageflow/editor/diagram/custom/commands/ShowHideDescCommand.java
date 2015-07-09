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
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;

import com.odcgroup.pageflow.editor.diagram.custom.dialog.ShowHideDescriptionSelectionDialog;
import com.odcgroup.pageflow.editor.diagram.custom.util.PageflowPreferenceConstants;
import com.odcgroup.pageflow.editor.diagram.edit.parts.SubPageflowStateDescEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.SubPageflowStateEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.ViewStateDescEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.ViewStateEditPart;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;

public class ShowHideDescCommand extends AbstractCommand {
	
	private List affectedFiles;

	public ShowHideDescCommand(String label, List affectedFiles) {
		super(label, null);
		this.affectedFiles = affectedFiles;
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor arg0,
			IAdaptable arg1) throws ExecutionException {
		
		ShowHideDescriptionSelectionDialog dialog = new ShowHideDescriptionSelectionDialog(Display.getCurrent().getActiveShell());
		dialog.open();
		
		if (dialog.getReturnCode() == ShowHideDescriptionSelectionDialog.OK){
			Iterator iter = affectedFiles.iterator();
			List children = new ArrayList();
			
	    	while(iter.hasNext()){
	    		EditPart ep = (EditPart)iter.next();
	    		if (ep instanceof ViewStateEditPart){
	    			ViewStateEditPart dp = (ViewStateEditPart)ep;
	        		children.addAll(dp.getChildren());
	    		} else if (ep instanceof SubPageflowStateEditPart){
	    			SubPageflowStateEditPart sep = (SubPageflowStateEditPart)ep;
	        		children.addAll(sep.getChildren()); 
	    		} 		
	    		
	    	}
	    	Iterator child = children.iterator();
			while(child.hasNext()){
				GraphicalEditPart gep = (GraphicalEditPart) child.next();	
				if (gep instanceof ViewStateDescEditPart || gep instanceof SubPageflowStateDescEditPart) {
					gep.refresh();
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
		return getPreferenceStore().getBoolean(PageflowPreferenceConstants.PREF_DESC_LABEL_DISPLAY);
	}
	
	/**
	 * @return
	 */
	private IPreferenceStore getPreferenceStore() {
		return PageflowDiagramEditorPlugin.getInstance().getPreferenceStore();
	}
	
	

}
