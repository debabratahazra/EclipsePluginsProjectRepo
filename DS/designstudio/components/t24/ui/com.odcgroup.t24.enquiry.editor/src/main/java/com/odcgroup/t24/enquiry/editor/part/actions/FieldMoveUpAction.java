package com.odcgroup.t24.enquiry.editor.part.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.t24.enquiry.editor.part.EnquiryDiagramEditPart;
import com.odcgroup.t24.enquiry.editor.part.OutputFieldEditPart;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Field;

/**
 *
 * @author mumesh
 *
 */
public class FieldMoveUpAction extends AbstractMoveAction {

	public static final String ACTION_ID = "FIELD_MOVE_UP";
	private Request request;

	/**
	 * @param part
	 */
	public FieldMoveUpAction(IWorkbenchPart part) {
		super(part);
		setId(ACTION_ID);
		setText("Move Up");
		this.request = new Request(RequestConstants.REQ_MOVE_CHILDREN);
	}

	@Override
	protected boolean calculateEnabled() {
		List<?> selection = getSelectedObjects();
		if (selection != null && selection.size() == 1) {
			Object obj = selection.get(0);
			if (obj instanceof OutputFieldEditPart) {
				OutputFieldEditPart editPart = (OutputFieldEditPart) obj;
				EnquiryDiagramEditPart customEditPart = (EnquiryDiagramEditPart) editPart.getParent();
				Enquiry enquiry = (Enquiry) customEditPart.getModel();
				Field field = (Field) editPart.getModel();
				int index = enquiry.getFields().indexOf(field);
				return (index > 0);
			}
		}
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Command getCommand() {
		List<?> selection = getSelectedObjects();
		if (selection != null && selection.size() == 1) {
			Object obj = selection.get(0);
			if (obj instanceof OutputFieldEditPart) {
				OutputFieldEditPart editPart = (OutputFieldEditPart) obj;
				EnquiryDiagramEditPart customEditPart = (EnquiryDiagramEditPart) editPart.getParent();
				if(customEditPart !=null){
					Map map = new HashMap();
					map.put(INCREMENT_FLAG, false);
					map.put(SELECTED_CHILD, editPart.getModel());
					map.put(EDIT_PART, editPart);
					request.setExtendedData(map);
					return customEditPart.getCommand(request);
				}
			}
		}
		return null;
	}


}
