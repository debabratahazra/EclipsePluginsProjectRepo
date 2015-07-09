package com.odcgroup.mdf.compare.internal.merge;

import java.util.Iterator;

import org.eclipse.emf.compare.EMFComparePlugin;
import org.eclipse.emf.compare.FactoryException;
import org.eclipse.emf.compare.diff.merge.DefaultMerger;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.ModelElementChangeRightTarget;
import org.eclipse.emf.compare.diff.metamodel.ReferenceChangeRightTarget;
import org.eclipse.emf.compare.diff.metamodel.ReferenceOrderChange;
import org.eclipse.emf.compare.util.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * right target merger for mdf elements
 * 
 * @author pkk
 */
public class MdfElementChangeRightTargetMerger extends DefaultMerger {/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.diff.merge.api.AbstractMerger#applyInOrigin()
	 */
	@Override
	public void applyInOrigin() {
		final ModelElementChangeRightTarget theDiff = (ModelElementChangeRightTarget)this.diff;
		final EObject origin = theDiff.getLeftParent();
		final EObject element = theDiff.getRightElement();
		EObject newOne = copy(element);
		final EReference ref = element.eContainmentFeature();
		if (ref != null) {
			try {
				 if (!MdfMergeUtil.mergeModelElements(origin, element)) {
					EFactory.eAdd(origin, ref.getName(), newOne);
					setXMIID(newOne, getXMIID(element));
				}
			} catch (final FactoryException e) {
				EMFComparePlugin.log(e, true);
			}
		} else {
			origin.eResource().getContents().add(newOne);
		}
		// we should now have a look for AddReferencesLinks needing this object
		final Iterator<EObject> siblings = getDiffModel().eAllContents();
		while (siblings.hasNext()) {
			final DiffElement op = (DiffElement)siblings.next();
			if (op instanceof ReferenceChangeRightTarget) {
				final ReferenceChangeRightTarget link = (ReferenceChangeRightTarget)op;
				// now if I'm in the target References I should put my copy in the origin
				if (link.getLeftTarget().equals(element)) {
					link.setRightTarget(newOne);
				}
			} else if (op instanceof ReferenceOrderChange) {
				final ReferenceOrderChange link = (ReferenceOrderChange)op;
				if (link.getReference().equals(ref)) {
					// FIXME respect ordering!
					link.getLeftTarget().add(newOne);
				}
			}
		}
		super.applyInOrigin();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.diff.merge.api.AbstractMerger#undoInTarget()
	 */
	@Override
	public void undoInTarget() {
		final ModelElementChangeRightTarget theDiff = (ModelElementChangeRightTarget)this.diff;
		final EObject element = theDiff.getRightElement();
		EcoreUtil.remove(element);
		super.undoInTarget();
	}

}
