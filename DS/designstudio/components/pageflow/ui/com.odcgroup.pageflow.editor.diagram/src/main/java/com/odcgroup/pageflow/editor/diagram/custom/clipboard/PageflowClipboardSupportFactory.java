package com.odcgroup.pageflow.editor.diagram.custom.clipboard;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupport;
import org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupportFactory;

public class PageflowClipboardSupportFactory implements
		IClipboardSupportFactory {
	
	private final IClipboardSupport  notationClipHelper = new PageflowClipboardSupport();

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupportFactory#newClipboardSupport(org.eclipse.emf.ecore.EPackage)
	 */
	public IClipboardSupport newClipboardSupport(EPackage ePackage) {
		return notationClipHelper;
	}

}
