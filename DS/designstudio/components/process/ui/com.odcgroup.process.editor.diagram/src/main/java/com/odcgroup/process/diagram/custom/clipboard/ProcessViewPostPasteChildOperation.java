package com.odcgroup.process.diagram.custom.clipboard;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.clipboard.core.ObjectInfo;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PostPasteChildOperation;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

public class ProcessViewPostPasteChildOperation extends PostPasteChildOperation {
	
	private boolean shouldPasteAlwaysCopyObject;
	private PasteChildOperation overriddenChildPasteOperation;

	/**
	 * @param overriddenChildPasteOperation
	 */
	public ProcessViewPostPasteChildOperation(
			PasteChildOperation overriddenChildPasteOperation,
			boolean shouldPasteAlwaysCopyObject) {
		super(overriddenChildPasteOperation, EMPTY_ARRAY);
		this.overriddenChildPasteOperation = overriddenChildPasteOperation;
		this.shouldPasteAlwaysCopyObject = shouldPasteAlwaysCopyObject;
	}

	public void paste() throws Exception {
		// target diagram
		Node view = (Node) getEObject();
		// now paste view
		EObject pastedElement = doPasteInto(overriddenChildPasteOperation.getParentEObject());
		// did we succeed?
		if (pastedElement != null) {
			setPastedElement(pastedElement);
			addPastedElement(pastedElement);
		} else {
			addPasteFailuresObject(getEObject());
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation#shouldPasteAlwaysCopyObject(org.eclipse.gmf.runtime.emf.clipboard.core.ObjectInfo)
	 */
	protected boolean shouldPasteAlwaysCopyObject(
			ObjectInfo alwaysCopyObjectInfo) {
		return shouldPasteAlwaysCopyObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.emf.core.internal.copypaste.PasteChildOperation#makeAuxiliaryChildPasteProcess(org.eclipse.gmf.runtime.emf.core.internal.copypaste.ObjectInfo)
	 */
	protected PasteChildOperation makeAuxiliaryChildPasteProcess(
			ObjectInfo auxiliaryChildEObjectInfo) {
		EObject semanticPasteTarget = ((View)overriddenChildPasteOperation.getParentEObject()).getElement();
		if (semanticPasteTarget == null) {
			return null;
		}
		return new PasteChildOperation(getParentPasteProcess().clone(semanticPasteTarget), auxiliaryChildEObjectInfo);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.clipboard.core.PostPasteChildOperation#getPostPasteOperation()
	 */
	public PasteChildOperation getPostPasteOperation() {
		List operations = getAlwaysCopyObjectPasteOperations();
		return new PostPasteChildOperation(this, operations);
	}


}
