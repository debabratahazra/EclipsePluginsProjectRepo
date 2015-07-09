package com.odcgroup.mdf.ecore.lazy;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EClassImpl;

import com.odcgroup.mdf.ecore.MdfClassImpl;

/**
 * @author yandenmatten
 */
public class EClassImplLazy extends EClassImpl {

	private MdfClassImpl mdfClass;

	public EClassImplLazy(MdfClassImpl mdfClass) {
		this.mdfClass = mdfClass;
	}

	@Override
	public String getNameGen() {
		return mdfClass.getName();
	}

	@Override
	public EList<EStructuralFeature> getEStructuralFeatures() {
		if (eStructuralFeatures==null) {
			super.getEStructuralFeatures();
//			new Mdf2EcoreMapperLazy().mapProperties(mdfClass, this);
		}
		return super.getEStructuralFeatures();
	}
	
}
