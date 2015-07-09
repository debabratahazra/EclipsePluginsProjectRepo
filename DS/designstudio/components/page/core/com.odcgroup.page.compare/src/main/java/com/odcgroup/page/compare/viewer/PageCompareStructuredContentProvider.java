package com.odcgroup.page.compare.viewer;

import org.eclipse.emf.compare.match.metamodel.Match2Elements;
import org.eclipse.emf.compare.match.metamodel.Match3Elements;
import org.eclipse.emf.compare.match.metamodel.UnmatchElement;
import org.eclipse.emf.compare.ui.util.EMFCompareConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;

/**
 * The content provider for the Page-Compare functionality.
 * 
 * @author Gary Hayes
 */
public class PageCompareStructuredContentProvider implements IStructuredContentProvider {
	
	/** The EObject to be displayed. */
	private EObject inputEObject;
	
	/**
	 * This int represents the side of the viewer part this content provider feeds. Must be one
	 * of
	 * <ul>
	 * <li>{@link EMFCompareConstants#RIGHT}</li>
	 * <li>{@link EMFCompareConstants#LEFT}</li>
	 * <li>{@link EMFCompareConstants#ANCESTOR}</li>
	 * </ul>
	 */
	private int partSide;
	
	/**
	 * Creates a new PageCompareStructuredContentProvider.
	 * 
	 * @param partSide The part side
	 */
	public PageCompareStructuredContentProvider(int partSide) {
		this.partSide = partSide;
	}

	/**
	 * Disposes of the ContentProvider.
	 */
	public void dispose() {
		// Nothing needs to be disposed.
	}
	
	/**
	 * Gets the input EObject to be displayed.
	 * 
	 * @return EObject The input EObject to be displayed
	 */
	public EObject getInputEObject() {
		return inputEObject;
	}

	/**
	 * Gets the EObject from the Match2Elements or UnMatchElement.
	 * 
	 * @param inputElement The input Element
	 * @return Object[] A single EObject (Widget, Property...)
	 */
	public Object[] getElements(Object inputElement) {
		inputEObject = findEObject(inputElement);
		return new Object[] {inputEObject};
	}

	/**
	 * Called when the input is changed.
	 * 
	 * @param viewer The viewer
	 * @param oldInput The old input
	 * @param newInput The new input
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (viewer instanceof TableViewer) {
			TableViewer tv = (TableViewer) viewer;
			tv.getTable().clearAll();
		}
	}
	
	/**
	 * Gets the input EObject.
	 * 
	 * @param inputElement the input Element
	 * @return EObject The input EObject
	 */
	private EObject findEObject(Object inputElement) {
		if (inputElement instanceof Match2Elements) {
			Match2Elements match = (Match2Elements) inputElement;
			if (partSide == EMFCompareConstants.RIGHT) {
				return match.getLeftElement();
			} else if (partSide == EMFCompareConstants.LEFT) {
				return match.getRightElement();
			} else if (inputElement instanceof Match3Elements) {
				return ((Match3Elements) match).getOriginElement();
			}
		} else if (inputElement instanceof UnmatchElement) {
			return ((UnmatchElement) inputElement).getElement();
		}	
		return null;
	}
}