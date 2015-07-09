package com.odcgroup.t24.enquiry.editor.part.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Routine;
import com.odcgroup.t24.enquiry.editor.part.RoutineEditPart;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public class RoutineMoveDownAction extends AbstractMoveAction {

	public static final String ACTION_ID = "ROUTINE_MOVE_DOWN";
	private Request request;

	/**
	 * @param part
	 */
	public RoutineMoveDownAction(IWorkbenchPart part) {
		super(part);
		setId(ACTION_ID);
		setText("Move Down");
		this.request = new Request(RequestConstants.REQ_MOVE_CHILDREN);
	}

	@Override
	protected Command getCommand() {
		List<?> selection = getSelectedObjects();
		if (selection != null && selection.size() == 1) {
			Object obj = selection.get(0);
			if (obj instanceof RoutineEditPart) {
				RoutineEditPart editPart = (RoutineEditPart) obj;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(INCREMENT_FLAG, true);
				map.put(SELECTED_CHILD, editPart.getModel());
				request.setExtendedData(map);
				return editPart.getParent().getCommand(request);
			}
		}
		return null;
	}

	@Override
	protected boolean calculateEnabled() {
		List<?> selection = getSelectedObjects();
		if (selection != null && selection.size() == 1) {
			Object obj = selection.get(0);
			if (obj instanceof RoutineEditPart) {
				RoutineEditPart editPart = (RoutineEditPart) obj;
				Enquiry enquiry = (Enquiry) editPart.getParent().getModel();
				int index = enquiry.getBuildRoutines().indexOf((Routine) editPart.getModel());
				return ((enquiry.getBuildRoutines().size() - 1) > index);
			}
		}
		return false;
	}

}
