package com.odcgroup.process.diagram.custom.clipboard;

import org.eclipse.gmf.runtime.emf.clipboard.core.ObjectInfo;
import org.eclipse.gmf.runtime.emf.clipboard.core.OverridePasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation;

public class ProcessPositionalGeneralViewPasteOperation extends	OverridePasteChildOperation {

	private boolean shouldPasteAlwaysCopyObject;

	/**
	 * @param overriddenChildPasteOperation
	 */
	public ProcessPositionalGeneralViewPasteOperation(
			PasteChildOperation overriddenChildPasteOperation,
			boolean shouldPasteAlwaysCopyObject) {
		super(overriddenChildPasteOperation);
		this.shouldPasteAlwaysCopyObject = shouldPasteAlwaysCopyObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation#paste()
	 */
	public void paste() throws Exception {
		// delay unsetting of connector refs
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation#shouldPasteAlwaysCopyObject(org.eclipse.gmf.runtime.emf.clipboard.core.ObjectInfo)
	 */
	protected boolean shouldPasteAlwaysCopyObject(
			ObjectInfo alwaysCopyObjectInfo) {
		return shouldPasteAlwaysCopyObject;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation#getPostPasteOperation()
	 */
	public PasteChildOperation getPostPasteOperation() {
		return new ProcessViewPostPasteChildOperation(this, shouldPasteAlwaysCopyObject);
	}

}
