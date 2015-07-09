package com.odcgroup.workbench.tap.validation.internal.strategy;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.service.AbstractTraversalStrategy;

public class FlatTraversalStrategy extends AbstractTraversalStrategy {

	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.service.AbstractTraversalStrategy#countElements(java.util.Collection)
	 */
	@Override
	protected int countElements(Collection<? extends EObject> traversalRoots) {
		return traversalRoots.size();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.service.AbstractTraversalStrategy#createIterator(java.util.Collection)
	 */
	@Override
	protected Iterator<? extends EObject> createIterator(Collection<? extends EObject> traversalRoots) {
		return traversalRoots.iterator();
	}

}
