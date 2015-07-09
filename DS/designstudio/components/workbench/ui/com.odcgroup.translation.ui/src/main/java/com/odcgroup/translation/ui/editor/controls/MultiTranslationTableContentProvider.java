package com.odcgroup.translation.ui.editor.controls;

import org.eclipse.jface.viewers.deferred.DeferredContentProvider;
import org.eclipse.swt.widgets.Display;

/**
 *
 * @author pkk
 *
 */
public class MultiTranslationTableContentProvider extends DeferredContentProvider  {

	/**
	 * @param sortOrder
	 */
	public MultiTranslationTableContentProvider() {
		super(new TranslationComparator(null));
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.deferred.DeferredContentProvider#updateElement(int)
	 */
	public void updateElement(final int element) {
		//DS-4603 
		Display.getCurrent().asyncExec(new Runnable() {
			public void run() {
				doUpdateElement(element);				
			}
		});
	}
	
	/**
	 * @param element
	 */
	private void doUpdateElement(int element) {
		super.updateElement(element);
	}
	
}
