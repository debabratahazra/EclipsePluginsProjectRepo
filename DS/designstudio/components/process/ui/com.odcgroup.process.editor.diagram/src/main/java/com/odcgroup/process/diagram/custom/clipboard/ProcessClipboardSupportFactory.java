package com.odcgroup.process.diagram.custom.clipboard;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupport;
import org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupportFactory;

public class ProcessClipboardSupportFactory implements
		IClipboardSupportFactory {
	
	private final IClipboardSupport  notationClipHelper = new ProcessClipboardSupport();

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupportFactory#newClipboardSupport(org.eclipse.emf.ecore.EPackage)
	 */
	public IClipboardSupport newClipboardSupport(EPackage ePackage) {
		return notationClipHelper;
	}

}
