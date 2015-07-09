package com.odcgroup.mdf.compare.internal.merge;

import java.util.Iterator;

import org.eclipse.emf.compare.EMFComparePlugin;
import org.eclipse.emf.compare.FactoryException;
import org.eclipse.emf.compare.diff.merge.DefaultMerger;
import org.eclipse.emf.compare.diff.metamodel.ModelElementChangeLeftTarget;
import org.eclipse.emf.compare.diff.metamodel.ReferenceChangeLeftTarget;
import org.eclipse.emf.compare.diff.metamodel.ReferenceOrderChange;
import org.eclipse.emf.compare.util.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * left target merger for mdf model elements
 * 
 * @author pkk
 *
 */
public class MdfElementChangeLeftTargetMerger extends DefaultMerger {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.diff.merge.api.AbstractMerger#applyInOrigin()
	 */
	@Override
	public void applyInOrigin() {
		final ModelElementChangeLeftTarget theDiff = (ModelElementChangeLeftTarget)this.diff;
		final EObject element = theDiff.getLeftElement();
		EcoreUtil.remove(element);
		super.applyInOrigin();
	}
	

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.diff.merge.api.AbstractMerger#undoInTarget()
	 */
	@Override
	public void undoInTarget() {
		final ModelElementChangeLeftTarget theDiff = (ModelElementChangeLeftTarget)this.diff;
		// we should copy the element to the Origin one.
		final EObject origin = theDiff.getRightParent();
		final EObject element = theDiff.getLeftElement();
		final EObject newOne = copy(element);
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
		// we should now have a look for RemovedReferencesLinks needing elements to apply
		final Iterator<EObject> siblings = getDiffModel().eAllContents();
		while (siblings.hasNext()) {
			final Object op = siblings.next();
			if (op instanceof ReferenceChangeLeftTarget) {
				final ReferenceChangeLeftTarget link = (ReferenceChangeLeftTarget)op;
				// now if I'm in the target References I should put my copy in the origin
				if (link.getRightTarget().equals(element)) {
					link.setLeftTarget(newOne);
				}
			} else if (op instanceof ReferenceOrderChange) {
				final ReferenceOrderChange link = (ReferenceOrderChange)op;
				if (link.getReference().equals(ref)) {
					// FIXME respect ordering!
					link.getRightTarget().add(newOne);
				}
			}
		}
		super.undoInTarget();
	}


}
