package com.odcgroup.t24.enquiry.editor.dnd;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;

/**
 * This class acts as a listener for target into which the editpart is dropped.
 *
 * @author mumesh
 *
 */
public class FieldTransferDropTargetListener extends TemplateTransferDropTargetListener {

	/**
	 * @param viewer
	 */
	public FieldTransferDropTargetListener(EditPartViewer viewer) {
		super(viewer);
	}

}
