package com.odcgroup.translation.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

/**
 * IQualifiedNameProvider for (shared) Translation.
 *
 * @author Michael Vorburger
 */
public class TranslationNameProvider extends IQualifiedNameProvider.AbstractImpl {

    @Override
    public QualifiedName getFullyQualifiedName(EObject obj) {
//        if (obj instanceof SharedTranslation) {
//            SharedTranslation sharedTranslation = (SharedTranslation) obj;
//            return QualifiedName.create(sharedTranslation.getName());
//        } else
            return null;
    }

}
