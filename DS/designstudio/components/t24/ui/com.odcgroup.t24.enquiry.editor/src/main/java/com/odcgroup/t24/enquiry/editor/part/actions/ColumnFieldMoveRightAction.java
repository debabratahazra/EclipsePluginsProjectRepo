package com.odcgroup.t24.enquiry.editor.part.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.t24.enquiry.editor.part.EnquiryDiagramEditPart;
import com.odcgroup.t24.enquiry.editor.part.FieldEditPart;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public class ColumnFieldMoveRightAction extends AbstractMoveAction {

	public static final String ACTION_ID = "MOVE_RIGHT";
	private Request request;

	/**
	 * @param part
	 */
	public ColumnFieldMoveRightAction(IWorkbenchPart part) {
		super(part);
		setId(ACTION_ID);
		setText("Move Right");
		this.request = new Request(RequestConstants.REQ_MOVE_CHILDREN);
	}

	@Override
	protected Command getCommand() {
		List<?> selection = getSelectedObjects();
		if (selection != null && selection.size() == 1) {
			Object obj = selection.get(0);
			if (obj instanceof FieldEditPart) {
				FieldEditPart editPart = (FieldEditPart) obj;
				EnquiryDiagramEditPart enquiryEditPart = (EnquiryDiagramEditPart) editPart.getParent();
				if (enquiryEditPart != null) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put(INCREMENT_FLAG, true);
					map.put(SELECTED_CHILD, editPart.getModel());
					map.put(EDIT_PART, editPart);
					request.setExtendedData(map);
					return enquiryEditPart.getCommand(request);
				}
			}
		}
		return null;

	}

	@Override
	protected boolean calculateEnabled() {
		List<?> selection = getSelectedObjects();
		if (selection != null && selection.size() == 1) {
			Object obj = selection.get(0);
			if (obj instanceof FieldEditPart) {
				FieldEditPart editPart = (FieldEditPart) obj;
				EnquiryDiagramEditPart enquiryEditPart = (EnquiryDiagramEditPart) editPart.getParent();
				Enquiry enquiry = (Enquiry) enquiryEditPart.getModel();
				List<Field> list = EnquiryUtil.fetchDisplayFieldList(enquiry);
				int index = list.indexOf(editPart.getModel());
				return list.size() - 1 > index;
			}
		}
		return false;
	}

}
