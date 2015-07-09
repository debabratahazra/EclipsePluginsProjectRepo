package com.odcgroup.page.ui.action;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.swt.SWT;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import com.odcgroup.page.ui.edit.WidgetEditPart;

/**
 * CutWidget Action for the page designer
 * copies the selected widget to the clipboard and 
 * then delete the selected widgets from the diagram
 * 
 * @author pkk
 */
public class CutWidgetAction extends CopyWidgetAction {

	/**
	 * @param part
	 */
	public CutWidgetAction(IWorkbenchPart part) {
		super(part);
	}

	/** 
	* Inits the action. 
	*/ 
	protected void init() {
		setId(ActionFactory.CUT.getId());
		setText("Cut");
		setAccelerator(SWT.CTRL | 'X');
		setDescription("Cut");
		setToolTipText("Cut");
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED));
	}

	/*
	*/
	
	/**
	 * Runs the action.
	 */
	@SuppressWarnings("rawtypes")
	public void run() {
		List selection = getSelectedObjects(); 
		if (selection != null && selection.size() > 0) { 
			for (int i = 0; i < selection.size(); i++) {
				if (selection.get(i) instanceof WidgetEditPart) {
					Clipboard.getDefault().setContents(new CutSelection(selection)); 
					break; 
				}
			} 
		} 
		if(selection != null) {
			execute(createDeleteCommand(selection));
		}
	}
	
	/**
	 * @param objects
	 * @return Command The Command
	 */
	@SuppressWarnings("rawtypes")
	public Command createDeleteCommand(List objects) {
		if (objects.isEmpty())
			return null;
		if (!(objects.get(0) instanceof EditPart))
			return null;

		GroupRequest deleteReq = new GroupRequest(RequestConstants.REQ_DELETE);
		deleteReq.setEditParts(objects);

		CompoundCommand compoundCmd = new CompoundCommand("Delete Action");
		for (int i = 0; i < objects.size(); i++) {
			EditPart object = (EditPart) objects.get(i);
			Command cmd = object.getCommand(deleteReq);
			if (cmd != null)
				compoundCmd.add(cmd);
		}

		return compoundCmd;
	}

}
