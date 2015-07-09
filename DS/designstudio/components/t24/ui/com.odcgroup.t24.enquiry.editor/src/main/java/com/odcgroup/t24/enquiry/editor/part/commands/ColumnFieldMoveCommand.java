package com.odcgroup.t24.enquiry.editor.part.commands;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.editor.part.EnquiryDiagramEditPart;
import com.odcgroup.t24.enquiry.editor.part.OutputFieldEditPart;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Field;

/**
 * Command used to move the fields in the enquiry.
 * 
 * @author mumesh
 * 
 */
public class ColumnFieldMoveCommand extends Command {

	private Field child;
	private Enquiry parent;
	private int newIndex;
	private int oldIndex;
	private EnquiryDiagramEditPart parentPart;
	

	public ColumnFieldMoveCommand(boolean isIncrement, Field child, EnquiryDiagramEditPart parentEditPart) {
		super();
		init(child, parentEditPart);
		this.newIndex = isIncrement? oldIndex+1: oldIndex-1;
	}
	
	public ColumnFieldMoveCommand(int index, Field child, EnquiryDiagramEditPart parentEditPart) {
		super();
		this.newIndex = index;
		init(child, parentEditPart);
	}
	
	private void init(Field child, EnquiryDiagramEditPart parentEditPart) {
		this.child = child;
		this.parentPart = parentEditPart;
		this.parent = (Enquiry) parentEditPart.getModel();
		this.oldIndex = parent.getFields().indexOf(child);
	}

	@Override
	public void execute() {
		redo();
	}

	@Override
	public void redo() {
		EList<Field> list = parent.getFields();
		list.remove(child);
		list.add(newIndex, child);
		highLightSelection();
	}
	
	private void highLightSelection() {
		if(parentPart != null){
			EditPartViewer viewer = parentPart.getViewer();
			viewer.deselectAll();
			OutputFieldEditPart part = getEditPart(parentPart, child);
			if (part != null) {
				part.setSelected(EditPart.SELECTED);
				viewer.setFocus(part);
				viewer.select(part);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private OutputFieldEditPart getEditPart(EnquiryDiagramEditPart parent, Field model) {
		List<EditPart> children = parent.getChildren();
		for (EditPart editPart : children) {
			if (editPart.getModel() instanceof Field && editPart.getModel() == model) {
				if (editPart instanceof OutputFieldEditPart) {
					return (OutputFieldEditPart) editPart;
				}
			}
		}
		return null;
	}

	@Override
	public void undo() {
		EList<Field> list = parent.getFields();
		list.remove(child);
		list.add(oldIndex, child);
	}


}
