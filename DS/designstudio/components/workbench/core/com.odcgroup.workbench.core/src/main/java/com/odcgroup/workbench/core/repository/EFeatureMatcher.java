package com.odcgroup.workbench.core.repository;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;


public class EFeatureMatcher extends EClassifierMatcher {

    private final EStructuralFeature acceptFeature;
    private final Object expected;

    public EFeatureMatcher(EClass acceptedClassifier, Object expected) {
        this(acceptedClassifier, acceptedClassifier.getEIDAttribute(), expected);
    }

    public EFeatureMatcher(EStructuralFeature acceptFeature, Object expected) {
        this(acceptFeature.eClass(), acceptFeature, expected);
    }

    public EFeatureMatcher(EClass acceptedClassifier,
            EStructuralFeature acceptFeature, Object expected) {
        super(acceptedClassifier);
        this.acceptFeature = acceptFeature;
        this.expected = expected;
    }

    public boolean match(Object model) {
        if (super.match(model)) {
            EObject eObject = (EObject) model;
            Object actual = eObject.eGet(acceptFeature);

            if (actual == null || expected == null) {
                return (expected == actual);
            } else if (expected == actual) {
                return true;
            } else {
                return actual.equals(expected);
            }
        }

        return false;
    }

}
