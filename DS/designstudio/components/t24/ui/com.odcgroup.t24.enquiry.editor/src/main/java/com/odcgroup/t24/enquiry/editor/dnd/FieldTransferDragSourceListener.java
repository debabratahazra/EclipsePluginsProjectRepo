package com.odcgroup.t24.enquiry.editor.dnd;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;

/**
 * This class acts as a DragSourceListener.
 *
 * @author mumesh
 *
 */
public class FieldTransferDragSourceListener extends TemplateTransferDragSourceListener {

	/**
	 * @param viewer
	 */
	public FieldTransferDragSourceListener(EditPartViewer viewer) {
		super(viewer);
	}

}
