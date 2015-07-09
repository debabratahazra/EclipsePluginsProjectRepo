package com.odcgroup.t24.enquiry.editor.part.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.editor.part.EnquiryDiagramEditPart;
import com.odcgroup.t24.enquiry.editor.part.FieldEditPart;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FieldPosition;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;

/**
 * Command used to sort the fields in the enquiry.
 * 
 * @author mumesh
 * 
 */
public class FieldColumnPositionSortCommand extends Command {

	private Field child;
	private Enquiry parent;
	private int newIndex;
	private int oldIndex;
	private EnquiryDiagramEditPart parentPart;
	private Map<Field, Integer> modifiedColumns = new HashMap<Field, Integer>();
	private Map<Field, Integer> modifiedLines = new HashMap<Field, Integer>();
	private Map<Field, Integer> modifiedRelCols = new HashMap<Field, Integer>();
	private List<Field> displayFields = new ArrayList<Field>();
	

	public FieldColumnPositionSortCommand(boolean isIncrement, Field child, EnquiryDiagramEditPart parentEditPart) {
		super();
		init(child, parentEditPart);
		this.newIndex = isIncrement? oldIndex+1: oldIndex-1;
	}
	
	public FieldColumnPositionSortCommand(int index, Field child, EnquiryDiagramEditPart parentEditPart) {
		super();
		this.newIndex = index;
		init(child, parentEditPart);
	}
	
	private void init(Field child, EnquiryDiagramEditPart parentEditPart) {
		this.child = child;
		this.parentPart = parentEditPart;
		this.parent = (Enquiry) parentEditPart.getModel();
		this.displayFields = EnquiryUtil.fetchDisplayFieldList(parent);
		this.oldIndex = displayFields.indexOf(child);
	}

	@Override
	public void execute() {
		redo();
	}

	@Override
	public void redo() {
		displayFields.remove(child);
		displayFields.add(newIndex, child);
		updateColumnPositions(displayFields, child);
		highLightSelection();
	}
	
	private void highLightSelection() {
		if(parentPart != null){
			EditPartViewer viewer = parentPart.getViewer();
			viewer.deselectAll();
			FieldEditPart part = getEditPart(parentPart, child);
			if (part != null) {
				part.setSelected(EditPart.SELECTED);
				viewer.setFocus(part);
				viewer.select(part);
			}
			part.refresh();
		}
	}

	@SuppressWarnings("unchecked")
	private FieldEditPart getEditPart(EnquiryDiagramEditPart parent, Field model) {
		List<EditPart> children = parent.getChildren();
		for (EditPart editPart : children) {
			if (editPart.getModel() instanceof Field && editPart.getModel() == model) {
				if (editPart instanceof FieldEditPart) {
					return (FieldEditPart) editPart;
				}
			}
		}
		return null;
	}

	@Override
	public void undo() {
		resetPostion(displayFields);
		FieldEditPart part = getEditPart(parentPart, child);
		part.refresh();
	}
	
	/**
	 * Method to undo position changes.
	 * 
	 * @param list 
	 * 
	 */
	private void resetPostion(List<Field> list) {
		for (int i = 0; i < list.size(); i++) {
			Field field = list.get(i);
			if (modifiedColumns.keySet().contains(field)) {
				field.getPosition().setColumn(modifiedColumns.get(field));
			}
			if (modifiedLines.keySet().contains(field)) {
				field.getPosition().setLine(modifiedLines.get(field));
			}
			if (modifiedRelCols.keySet().contains(field)) {
				field.getPosition().setColumn(modifiedRelCols.get(field));
			}
		}
	}
	
	/**
	 * update the column positions and line positions of the fields
	 * 
	 * @param list
	 * @param child
	 */
	private void updateColumnPositions(List<Field> list, Field child) {
		boolean isRelated = EnquiryUtil.isFieldRelative(child);
		int col = child.getPosition().getColumn();
		List<Field> relfields = new ArrayList<Field>();
		if (isRelated) {
			for(Field field: list) {
				if (field.getPosition() != null 
						&& field.getPosition().getColumn() == col) {
					relfields.add(field);
				}
			}
		}
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		boolean relParentMove = false;
		for (int j = 0; j < list.size(); j++) {
			Field field = list.get(j);
			int pos = list.indexOf(field);
			// is related field
			if (isRelated) {
				int line = relfields.indexOf(field);
				FieldPosition fpos = field.getPosition();
				if (fpos != null 
						&& fpos.getColumn() == col
						&& fpos.getLine() != null) {
					modifiedLines.put(field, fpos.getLine());
					field.getPosition().setLine(new Integer(line+1));
				}
			} else {
				if (!EnquiryUtil.isFieldRelative(field)) {
					int old = field.getPosition().getColumn();
					modifiedColumns.put(field, old);
					if (EnquiryUtil.isRelativeFieldParent(field, list)) {
						relParentMove = true;
						map.put(new Integer(old), new Integer(pos+1));
					}
					field.getPosition().setColumn(pos+1);
				}
			}
		}
		// handle relative field if parent moved
		if (relParentMove) {
			for (Field field : list) {
				if (EnquiryUtil.isFieldRelative(field)) {
					int fcol = field.getPosition().getColumn();
					if (map.containsKey(new Integer(fcol))) {
						int ncol = map.get(new Integer(fcol));
						modifiedRelCols.put(field, fcol);
						field.getPosition().setColumn(ncol);
					}
				}
			}
		}
	}


}
