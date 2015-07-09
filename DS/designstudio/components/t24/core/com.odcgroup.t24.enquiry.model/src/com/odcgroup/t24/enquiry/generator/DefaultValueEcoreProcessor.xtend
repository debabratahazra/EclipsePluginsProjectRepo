package com.odcgroup.t24.enquiry.generator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.GeneratedMetamodel;
import org.eclipse.xtext.xtext.ecoreInference.IXtext2EcorePostProcessor;
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.EDataType

/**
 * This Class is helpful in modifying the Ecore model generated from Xtext file.
 * Any modification like adding default value or adding new method to eobject can
 *
 *
 * @author mumesh
 *
 */
class DefaultValueEcoreProcessor implements IXtext2EcorePostProcessor {

	override process(GeneratedMetamodel metamodel) {
		val p = metamodel.EPackage
		for (c : p.EClassifiers.filter(typeof(EClass))) {
			c.handle
		}
	}

	def handle(EClass c) {
		for (a : c.EAllAttributes.filter[EType.name == "EInt"]) {
			a.setDefaultValue(0);
		}
	}
}
