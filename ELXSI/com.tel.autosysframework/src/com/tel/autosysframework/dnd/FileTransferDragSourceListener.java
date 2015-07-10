package com.tel.autosysframework.dnd;

import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;

import org.eclipse.gef.EditPartViewer;



public class FileTransferDragSourceListener
	extends org.eclipse.gef.dnd.AbstractTransferDragSourceListener
{

public FileTransferDragSourceListener(EditPartViewer viewer) {
	super(viewer, TextTransfer.getInstance());
}

public FileTransferDragSourceListener(EditPartViewer viewer, Transfer xfer) {
	super(viewer, xfer);
}

public void dragSetData(DragSourceEvent event) {
	event.data = "Some text"; //$NON-NLS-1$
}

public void dragStart(DragSourceEvent event) {
	/*if (getViewer().getSelectedEditParts().get(0) instanceof AutosysLabelEditPart)
		return;*/
	event.doit = false;
}

}
