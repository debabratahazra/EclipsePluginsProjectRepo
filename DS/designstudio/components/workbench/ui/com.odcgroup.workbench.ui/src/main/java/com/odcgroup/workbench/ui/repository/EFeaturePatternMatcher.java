package com.odcgroup.workbench.ui.repository;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;



/**
 * Filter based on the classifier of an object and a pattern match on the value
 * of a given feature.
 */
public class EFeaturePatternMatcher implements IPatternMatcher {

    private static final char END_SYMBOL = '<';
    private static final char ANY_STRING = '*';

    private final EStructuralFeature matchedFeature;
    private StringMatcher _matcher;

    /**
     * Build a filter based on the eIDAttribute of the given classifier.
     * 
     * @param matchedFeature feature used to accept the filtered object.
     */
    public EFeaturePatternMatcher(EClass acceptClassifier) {
        this(acceptClassifier.getEIDAttribute());
    }

    /**
     * Build a filter based on the given feature of the given classifier.
     * 
     * @param matchedFeature feature used to accept the filtered object.
     */
    public EFeaturePatternMatcher(EStructuralFeature matchedFeature) {
        this.matchedFeature = matchedFeature;
        _matcher = new StringMatcher(Character.toString(ANY_STRING), true,
                false);
    }

    public EStructuralFeature getMatchedFeature() {
        return matchedFeature;
    }

    /**
     * The pattern used to match the value of the given feature.
     * 
     * @param pattern the pattern, can contain <tt>'*'</tt> to represent any
     *            string.
     * @param ignoreCase tells wether the matching should be case sensitive or
     *            not.
     * @param ignoreWildCards if <code>true</code> then wild cards will be
     *            treated like normal text.
     */
    public void setPattern(String pattern, boolean ignoreCase,
            boolean ignoreWildCards) {
        _matcher = new StringMatcher(adjustPattern(pattern), ignoreCase,
                ignoreWildCards);
    }

    public boolean match(Object object) {
        EObject eObject = (EObject) object;

        if (eObject.eIsSet(matchedFeature)) {
            Object value = eObject.eGet(matchedFeature, true);
            return _matcher.match(value.toString());
        }

        return false;
    }

    private String adjustPattern(String pattern) {
        int length = pattern.length();

        if (length > 0) {
            switch (pattern.charAt(length - 1)) {
                case END_SYMBOL:
                    pattern = pattern.substring(0, length - 1);
                    break;

                case ANY_STRING:
                    break;

                default:
                    pattern = pattern + ANY_STRING;
            }
        }

        return pattern;
    }

}
