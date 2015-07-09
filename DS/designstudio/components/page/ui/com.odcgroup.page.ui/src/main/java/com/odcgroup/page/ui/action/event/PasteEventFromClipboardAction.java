package com.odcgroup.page.ui.action.event;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.page.model.Event;

/**
 *
 * @author pkk
 *
 */
public abstract class PasteEventFromClipboardAction extends Action {
	
	/** */
	private Event event;
	
	/**
	 * @param event
	 */
	protected abstract void doPasteEvent(Event event);

	/**
	 * @param event
	 */
	public PasteEventFromClipboardAction(Event event) {
		super("Paste Event");
		
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
		setActionDefinitionId(IWorkbenchCommandConstants.EDIT_PASTE);

		this.event = event;
	}
	
	/**
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {		
		long tid = event.getTranslationId();
		if (tid > 0) {
			event.setTranslationId(System.nanoTime());
		}
		doPasteEvent(event);
	}

}
