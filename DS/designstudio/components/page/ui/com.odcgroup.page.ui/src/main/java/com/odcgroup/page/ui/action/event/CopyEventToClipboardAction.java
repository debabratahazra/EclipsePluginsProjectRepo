package com.odcgroup.page.ui.action.event;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.page.model.Event;

/**
 * @author atr
 */
public abstract class CopyEventToClipboardAction extends Action {
	
	/** */
	private Event event;
	
	/**
	 * @param event
	 */
	protected abstract void doCopyEvent(Event event);

	/**
	 * @param event 
	 */
	public CopyEventToClipboardAction(Event event) {
		super("Copy Event");

		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
		setActionDefinitionId(IWorkbenchCommandConstants.EDIT_COPY);
		
		this.event = event;
	}
	
	/**
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {		
		doCopyEvent(event);
	}
}
