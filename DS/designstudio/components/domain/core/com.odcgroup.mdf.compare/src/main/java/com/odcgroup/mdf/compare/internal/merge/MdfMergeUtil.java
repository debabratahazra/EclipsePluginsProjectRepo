package com.odcgroup.mdf.compare.internal.merge;

import org.eclipse.emf.ecore.EObject;

import com.odcgroup.mdf.ecore.ECoreModelFactory;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;

/**
 * utility to handle merge of mdf elements
 *
 * @author pkk
 *
 */
public class MdfMergeUtil {
	
	/**
	 * @param origin
	 * @param element
	 */
	public static boolean mergeModelElements(EObject origin, EObject element) {
		
		boolean handled = true;
		ECoreModelFactory factory = ECoreModelFactory.INSTANCE;
		if (origin instanceof MdfDomain && element instanceof MdfEntity) {					
			factory.addEntity((MdfDomain) origin, (MdfEntity) element);
		} else if (origin instanceof MdfClass && element instanceof MdfProperty) {
			factory.addProperty((MdfClass) origin, (MdfProperty)element); 
		} else if (origin instanceof MdfModelElement && element instanceof MdfAnnotation) {
			factory.addAnnotation((MdfModelElement) origin, (MdfAnnotation)element);
		} else if (origin instanceof MdfAnnotation && element instanceof MdfAnnotationProperty) {
			factory.addAnnotationProperty((MdfAnnotation) origin, (MdfAnnotationProperty) element);
		} else if (origin instanceof MdfDataset && element instanceof MdfDatasetProperty) {					
			factory.addDatasetProperty((MdfDataset) origin, (MdfDatasetProperty) element);
		} else if (origin instanceof MdfEnumeration && element instanceof MdfEnumValue) {
			factory.addEnumValue((MdfEnumeration) origin, (MdfEnumValue) element);
		} else if (origin instanceof MdfAssociation && element instanceof MdfReverseAssociation) {
			((MdfAssociationImpl) origin).setReverse((MdfReverseAssociation)element);
		} else {
			handled = false;
		}
		return handled;
	}

}
