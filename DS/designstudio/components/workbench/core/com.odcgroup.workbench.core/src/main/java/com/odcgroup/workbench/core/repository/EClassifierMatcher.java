package com.odcgroup.workbench.core.repository;

import org.eclipse.emf.ecore.EClass;


/**
 * Matcher based on the classifier of an object, it will match all of the object
 * that are instances of this classifier.
 */
public class EClassifierMatcher implements ModelMatcher {

    private final EClass acceptClassifier;

    /**
     * Build a filter that will accept only model files with the given
     * extensions and objects with the given classifier.
     * 
     * @param acceptExtensions extensions of the model file to accept.
     * @param acceptClassifier classifier of the object to accept.
     */
    public EClassifierMatcher(EClass acceptClassifier) {
        this.acceptClassifier = acceptClassifier;
    }

    
    public EClass getAcceptClassifier() {
        return acceptClassifier;
    }

    public boolean match(Object object) {
        return acceptClassifier.isInstance(object);
    }

}
