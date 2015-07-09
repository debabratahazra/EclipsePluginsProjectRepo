package com.odcgroup.page.ui.edit.matrix;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.tools.DragEditPartsTracker;

/**
 *
 * @author pkk
 *
 */
public class MatrixCellDragEditPartsTracker extends DragEditPartsTracker {

	/**
	 * @param sourceEditPart
	 */
	public MatrixCellDragEditPartsTracker(EditPart sourceEditPart) {
		super(sourceEditPart);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.tools.AbstractTool#movedPastThreshold()
	 */
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

}
