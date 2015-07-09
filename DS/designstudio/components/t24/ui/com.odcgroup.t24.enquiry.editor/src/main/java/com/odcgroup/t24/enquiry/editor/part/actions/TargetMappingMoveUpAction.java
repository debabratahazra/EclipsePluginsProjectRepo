package com.odcgroup.t24.enquiry.editor.part.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.t24.enquiry.enquiry.Target;
import com.odcgroup.t24.enquiry.enquiry.TargetMapping;
import com.odcgroup.t24.enquiry.editor.part.TargetMappingEditPart;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public class TargetMappingMoveUpAction extends AbstractMoveAction {

	public static final String ACTION_ID = "TARGET_MAPPING_MOVE_UP";
	private Request request;

	/**
	 * @param part
	 */
	public TargetMappingMoveUpAction(IWorkbenchPart part) {
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
			if (obj instanceof TargetMappingEditPart) {
				TargetMappingEditPart editPart = (TargetMappingEditPart) obj;
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
			if (obj instanceof TargetMappingEditPart) {
				TargetMappingEditPart editPart = (TargetMappingEditPart) obj;
				Target target = (Target) editPart.getParent().getModel();
				int index = target.getMappings().indexOf((TargetMapping) editPart.getModel());
				return (target.getMappings().size() != 1) && (index != 0);
			}
		}
		return false;
	}
}
