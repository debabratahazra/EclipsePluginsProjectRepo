package com.odcgroup.t24.enquiry.serializer;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.serializer.sequencer.TransientValueService;

/**
 * Workaround for a bug in Xtext 2.7.3 & 2.8.2/3? (used to worked in 2.5.3)
 *
 * @see DS-8732  (http://rd.oams.com/browse/DS-8732)
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=469806
 *
 * @author Michael Vorburger, based on discussions with Moritz Eysholdt
 */
@SuppressWarnings("restriction")
public class Bug469806TransientValueService extends TransientValueService {

	@Override
	protected boolean defaultValueIsSerializeable(EStructuralFeature feature) {
		if(feature instanceof EAttribute) {
			EClassifier featureType = feature.getEType();
			Object defaultValue = feature.getDefaultValue();
			return defaultValue != null || isJavaPrimitive(featureType) || isEnumLiteral(featureType);
		}
		return false;
	}

	private boolean isJavaPrimitive(EClassifier eType) {
		return eType.getInstanceClass().isPrimitive();
	}

	private boolean isEnumLiteral(EClassifier eType) {
		// TODO Michael to confirm with Moritz that this is correct:
		return eType instanceof EEnumLiteral;
	}

}
