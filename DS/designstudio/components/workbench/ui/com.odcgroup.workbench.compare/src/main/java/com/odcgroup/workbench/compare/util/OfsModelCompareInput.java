package com.odcgroup.workbench.compare.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.internal.CompareUIPlugin;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.ICompareInputChangeListener;
import org.eclipse.emf.compare.ui.util.EMFCompareConstants;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.workbench.core.IOfsProject;

/**
 *
 * @author pkk
 */
public class OfsModelCompareInput implements ICompareInput {		
	
	private final ITypedElement leftResourceNode;
	private final ITypedElement rightResourceNode;
	
	private IOfsProject ofsProject;
	
	private final List<ICompareInputChangeListener> inputChangeListeners = new ArrayList<ICompareInputChangeListener>();
	
	/**
	 * @param ofsProject
	 * @param leftResourceNode
	 * @param rightResourceNode
	 */
	public OfsModelCompareInput(IOfsProject ofsProject, ITypedElement leftResourceNode, ITypedElement rightResourceNode) {
		this.ofsProject = ofsProject;
		this.leftResourceNode = leftResourceNode;
		this.rightResourceNode = rightResourceNode;
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.compare.structuremergeviewer.ICompareInput#addCompareInputChangeListener(org.eclipse.compare.structuremergeviewer.ICompareInputChangeListener)
	 */
	public void addCompareInputChangeListener(
			ICompareInputChangeListener listener) {
		inputChangeListeners.add(listener);
	}

	
	public void copy(boolean leftToRight) {
		//donothing
	}

	
	public ITypedElement getAncestor() {
		return null;
	}

	
	public Image getImage() {
		return CompareUIPlugin.getImage(leftResourceNode.getType());
	}

	
	public int getKind() {
		return EMFCompareConstants.NO_CHANGE;
	}

	
	public ITypedElement getLeft() {
		return leftResourceNode;
	}

	
	public String getName() {
		return leftResourceNode.getName();
	}

	
	public ITypedElement getRight() {
		return rightResourceNode;
	}

	
	public void removeCompareInputChangeListener(
			ICompareInputChangeListener listener) {
		inputChangeListeners.remove(listener);

	}
	
	public IOfsProject getOfsProject() {
		return ofsProject;
	}

}
