package com.odcgroup.t24.enquiry.editor.part.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Tool;
import com.odcgroup.t24.enquiry.editor.part.ToolEditPart;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public class ToolMoveUpAction extends AbstractMoveAction {

	public static final String ACTION_ID = "TOOL_MOVE_UP";
	private Request request;

	/**
	 * @param part
	 */
	public ToolMoveUpAction(IWorkbenchPart part) {
		super(part);
		setId(ACTION_ID);
		setText("Move Up");
		this.request = new Request(RequestConstants.REQ_MOVE_CHILDREN);
	}

	@Override
	protected Command getCommand() {
		List<?> selection = getSelectedObjects();
		if (selection != null && selection.size() == 1) {
			Object obj = selection.get(0);
			if (obj instanceof ToolEditPart) {
				ToolEditPart editPart = (ToolEditPart) obj;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(INCREMENT_FLAG, false);
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
			if (obj instanceof ToolEditPart) {
				ToolEditPart editPart = (ToolEditPart) obj;
				Enquiry enquiry = (Enquiry) editPart.getParent().getModel();
				int index = enquiry.getTools().indexOf((Tool) editPart.getModel());
				return (enquiry.getTools().size() != 1) && (index != 0);
			}
		}
		return false;
	}
}
