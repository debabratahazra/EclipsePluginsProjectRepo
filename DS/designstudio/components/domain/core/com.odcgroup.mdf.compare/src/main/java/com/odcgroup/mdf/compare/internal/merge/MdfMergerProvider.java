package com.odcgroup.mdf.compare.internal.merge;

import java.util.Map;

import org.eclipse.emf.compare.diff.merge.IMerger;
import org.eclipse.emf.compare.diff.merge.IMergerProvider;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.ModelElementChangeLeftTarget;
import org.eclipse.emf.compare.diff.metamodel.ModelElementChangeRightTarget;
import org.eclipse.emf.compare.util.EMFCompareMap;

/**
 * MDF Merger Provider
 * 
 * @author pkk
 *
 */
public class MdfMergerProvider implements IMergerProvider {

	private Map<Class<? extends DiffElement>, Class<? extends IMerger>> mergerTypes;

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.diff.merge.IMergerProvider#getMergers()
	 */
	@Override
	public Map<Class<? extends DiffElement>, Class<? extends IMerger>> getMergers() {
		if (mergerTypes == null) {
			mergerTypes = new EMFCompareMap<Class<? extends DiffElement>, Class<? extends IMerger>>();
			mergerTypes.put(ModelElementChangeRightTarget.class, MdfElementChangeRightTargetMerger.class);
			mergerTypes.put(ModelElementChangeLeftTarget.class, MdfElementChangeLeftTargetMerger.class);
		}
		return mergerTypes;
	}

}
