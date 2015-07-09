package com.odcgroup.pageflow.editor.diagram.custom.clipboard;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.gmf.runtime.emf.clipboard.core.ClipboardSupportUtil;
import org.eclipse.gmf.runtime.emf.clipboard.core.CopyOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.OverrideCopyOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.OverridePasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteAction;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteOption;
import org.eclipse.gmf.runtime.emf.core.clipboard.AbstractClipboardSupport;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.providers.internal.copypaste.ConnectorViewPasteOp;
import org.eclipse.gmf.runtime.notation.providers.internal.copypaste.PositionalGeneralViewPasteOp;

import com.odcgroup.pageflow.model.InitState;
import com.odcgroup.pageflow.model.Pageflow;


public class PageflowClipboardSupport extends AbstractClipboardSupport {
	


	public void destroy(EObject eObject) {
		DestroyElementCommand.destroy(eObject);
	}
	
	/**
	 * By default, there are no collisions in pasting.
	 * 
	 * @return the {@link PasteAction#ADD}action, always
	 */
	public PasteAction getPasteCollisionAction(EClass eClass) {
		return PasteAction.ADD;
	}

	/**
	 * By default, the following paste options are supported:
	 * <ul>
	 * <li>{@link PasteOption#NORMAL}: always</li>
	 * <li>{@link PasteOption#PARENT}: never</li>
	 * <li>{@link PasteOption#DISTANT}: if and only only if the
	 * <code>eStructuralFeature</code> is a
	 * {@link org.eclipse.gmf.runtime.notation.View}'s reference to its semantic
	 * {@linkplain org.eclipse.gmf.runtime.notation.View#getElement() element}</li>
	 * </ul>
	 */
	public boolean hasPasteOption(EObject contextEObject,
			EStructuralFeature eStructuralFeature, PasteOption pasteOption) {
		if (pasteOption.equals(PasteOption.NORMAL)) {
			return true;
		} else if (pasteOption.equals(PasteOption.PARENT)) {
			//disable the copy-parent functionality completely.
			return false;
		} else if (pasteOption.equals(PasteOption.DISTANT)) {
			if (eStructuralFeature == null) {
				return false;
			} else {
				return NotationPackage.eINSTANCE.getView_Element().equals(
					eStructuralFeature);
			}
		} else {
			return false;
		}
	}

	/**
	 * By default, transient and derived references are never copied, and
	 * containment references and the
	 * {@linkplain org.eclipse.gmf.runtime.notation.View#getElement() element}reference
	 * always are copied.
	 */
	public boolean isCopyAlways(EObject context, EReference eReference,
			Object value) {
		if ((eReference.isTransient()) || (eReference.isDerived())) {
			return false;
		} else if (eReference.equals(NotationPackage.eINSTANCE
			.getView_Element())) {
			return true;
		} else {
			return eReference.isContainment();
		}
	}

	/**
	 * By default, don't provide any child paste override behaviour.
	 */
	public boolean shouldOverrideChildPasteOperation(EObject parentElement,
			EObject childEObject) {
		return (childEObject.eClass().getEPackage() == NotationPackage.eINSTANCE);
	}

	/**
	 * By default, don't provide any copy override behaviour.
	 */
	public boolean shouldOverrideCopyOperation(Collection eObjects, Map hintMap) {
		return false;
	}

	private boolean shouldAllowPaste(
			PasteChildOperation overriddenChildPasteOperation) {
		EObject eObject = overriddenChildPasteOperation.getEObject();
		EObject parentEObject = overriddenChildPasteOperation
			.getParentEObject();
		if ((parentEObject instanceof Diagram) && (eObject instanceof View)) {
			EObject semanticChildElement = ((View) eObject).getElement();
			if (semanticChildElement == null) {
				return true;
			}
			if (semanticChildElement.eIsProxy()) {
				semanticChildElement = ClipboardSupportUtil.resolve(
					semanticChildElement, overriddenChildPasteOperation
						.getParentPasteProcess().getLoadedIDToEObjectMapCopy());
				if (semanticChildElement.eIsProxy()) {
					semanticChildElement = EcoreUtil.resolve(
						semanticChildElement, getResource(parentEObject));
				}
			}

			EPackage semanticChildEpackage = semanticChildElement.eClass()
				.getEPackage();
			EPackage diagramRootContainerEpackage = EcoreUtil.getRootContainer(
				parentEObject).eClass().getEPackage();
			EPackage sematicDiagramRootContainerEpackage = null;
			EObject sematicDiagramElement = ((View) parentEObject).getElement();
			
			if(sematicDiagramElement != null && sematicDiagramElement instanceof Pageflow){
				Pageflow pageflow = (Pageflow) sematicDiagramElement;
				List states = pageflow.getStates();
				boolean initExist = false;
				for(Iterator iter = states.iterator();iter.hasNext();){
					if (iter.next() instanceof InitState){
						initExist = true;
					}
				}
				if (initExist && semanticChildElement instanceof InitState){
					return false;
				}
			}
			
			if (sematicDiagramElement != null) {
				sematicDiagramRootContainerEpackage = EcoreUtil
					.getRootContainer(sematicDiagramElement).eClass()
					.getEPackage();
			}

			if (diagramRootContainerEpackage != NotationPackage.eINSTANCE) {
				if (semanticChildEpackage != diagramRootContainerEpackage) {
					return false;
				}
			}

			if ((sematicDiagramRootContainerEpackage != null)
				&& (sematicDiagramRootContainerEpackage != NotationPackage.eINSTANCE)) {
				if (semanticChildEpackage != sematicDiagramRootContainerEpackage) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * By default, don't provide any child paste override behaviour.
	 * 
	 * @return <code>null</code>, always
	 */
	public OverridePasteChildOperation getOverrideChildPasteOperation(
			PasteChildOperation overriddenChildPasteOperation) {
		if (shouldAllowPaste(overriddenChildPasteOperation)) {
			EObject eObject = overriddenChildPasteOperation.getEObject();
			if (eObject instanceof org.eclipse.gmf.runtime.notation.Node) {
				org.eclipse.gmf.runtime.notation.Node node = (org.eclipse.gmf.runtime.notation.Node) eObject;
				EObject element = node.getElement();
				if ((element != null)) {
					return new PositionalGeneralViewPasteOp(
						overriddenChildPasteOperation, true);
				} else {
					return new PositionalGeneralViewPasteOp(
						overriddenChildPasteOperation, false);
				}
			} else if (eObject instanceof Edge) {
				return new ConnectorViewPasteOp(
					overriddenChildPasteOperation);
			}
		}
		return null;
	}

	/**
	 * By default, don't provide any copy override behaviour.
	 * 
	 * @return <code>null</code>, always
	 */
	public OverrideCopyOperation getOverrideCopyOperation(
			CopyOperation overriddenCopyOperation) {
		return null;
	}

	/**
	 * By default, don't exclude any objects from the copy operation.
	 * 
	 * @return an empty collection
	 */
	public Collection getExcludedCopyObjects(Set eObjects) {
		return Collections.EMPTY_SET;
	}

	/**
	 * By default, just get the resource that contains the object.
	 */
	public XMLResource getResource(EObject eObject) {
		XMLResource eResource = (XMLResource) eObject.eResource();
		if (eResource == null) {
			if (eObject instanceof View) {
				EObject element = ((View) eObject).getElement();
				if ((element != null)) {
					return (XMLResource) element.eResource();
				}
			}
		}
		return eResource;
	}

	/**
	 * By default, we always copy all contents of an object.
	 * 
	 * @return <code>true</code>
	 */
	public boolean shouldSaveContainmentFeature(EObject eObj) {
		if (EcorePackage.eINSTANCE.getEClassifiers().contains(eObj.eClass())) {
			return false;
		}
		try {
			eObj.eResource().getURIFragment(eObj);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * By default, there is no post processing to be done.
	 */
	public void performPostPasteProcessing(Set pastedEObjects) {
		// nothing to do
	}

	static Diagram getContainingDiagram(View view) {
		EObject current = view;
		while (current != null) {
			if (current instanceof Diagram) {
				return (Diagram) current;
			}
			current = current.eContainer();
		}
		return null;
	}

	static EObject getSemanticPasteTarget(View view) {
		Diagram diagram = getContainingDiagram(view);
		if (diagram != null) {
			return diagram.getElement();
		}
		return null;
	}
}
