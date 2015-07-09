package com.odcgroup.t24.enquiry.editor.part.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.t24.enquiry.editor.part.CustomSelectionEditPart;
import com.odcgroup.t24.enquiry.editor.part.SelectionEditPart;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class SelectionMoveDownAction extends AbstractMoveAction {

	public static final String ACTION_ID = "MOVE_DOWN";
	private Request request;

	/**
	 * @param part
	 */
	public SelectionMoveDownAction(IWorkbenchPart part) {
		super(part);
		setId(ACTION_ID);
		setText("Move Down");
		this.request = new Request(RequestConstants.REQ_MOVE_CHILDREN);
	}

	@Override
	protected boolean calculateEnabled() {
		List<?> selection = getSelectedObjects();
		if (selection != null && selection.size() == 1) {
			Object obj = selection.get(0);
			if (obj instanceof SelectionEditPart) {
				SelectionEditPart editPart = (SelectionEditPart) obj;
				CustomSelectionEditPart customEditPart = (CustomSelectionEditPart) editPart.getParent();
				int index = customEditPart.getChildren().indexOf(editPart);
				return customEditPart.getChildren().size()-1>index;
			}
		}
		return false;
	}

	@Override
	protected Command getCommand() {
		List<?> selection = getSelectedObjects();
		if (selection != null && selection.size() == 1) {
			Object obj = selection.get(0);
			if (obj instanceof SelectionEditPart) {
				SelectionEditPart editPart = (SelectionEditPart) obj;
				CustomSelectionEditPart customEditPart = (CustomSelectionEditPart) editPart.getParent();
				if(customEditPart !=null){
					Map map = new HashMap();
					map.put(INCREMENT_FLAG, true);
					map.put(SELECTED_CHILD, editPart.getModel());
					request.setExtendedData(map);
					return customEditPart.getCommand(request);
				}
			}
		}
		return null;
	}


}
