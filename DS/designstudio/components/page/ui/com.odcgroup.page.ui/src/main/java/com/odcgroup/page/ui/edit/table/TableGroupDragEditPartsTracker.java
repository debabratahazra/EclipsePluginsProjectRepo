package com.odcgroup.page.ui.edit.table;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.tools.DragEditPartsTracker;

/**
 * Drag tracker edit part for the TableColumn widget.
 *
 * @author atr
 * @since DS 1.40.0
 *
 */
public class TableGroupDragEditPartsTracker extends DragEditPartsTracker {

	protected boolean movedPastThreshold() {
		return false;
	}

	/*
	 * @see org.eclipse.gef.tools.DragEditPartsTracker#handleDragInProgress()
	 */
	protected boolean handleDragInProgress() {
		// returning true will disabled the drag and drop
		return true;
	}

	/**
	 * @param sourceEditPart
	 */
	public TableGroupDragEditPartsTracker(EditPart sourceEditPart) {
		super(sourceEditPart);
	}

}
