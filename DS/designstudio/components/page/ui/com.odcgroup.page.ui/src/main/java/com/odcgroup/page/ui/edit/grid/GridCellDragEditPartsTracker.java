package com.odcgroup.page.ui.edit.grid;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.tools.DragEditPartsTracker;

/**
 * TODO: Document me!
 *
 * @author atr
 * @since DS 1.40.0
 *
 */
public class GridCellDragEditPartsTracker extends DragEditPartsTracker {

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
	public GridCellDragEditPartsTracker(EditPart sourceEditPart) {
		super(sourceEditPart);
	}

}
