package com.odcgroup.t24.enquiry.generator;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.GeneratedMetamodel;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xtext.ecoreInference.IXtext2EcorePostProcessor;

/**
 * This Class is helpful in modifying the Ecore model generated from Xtext file.
 * Any modification like adding default value or adding new method to eobject can
 * 
 * 
 * @author mumesh
 */
@SuppressWarnings("all")
public class DefaultValueEcoreProcessor implements IXtext2EcorePostProcessor {
  public void process(final GeneratedMetamodel metamodel) {
    final EPackage p = metamodel.getEPackage();
    EList<EClassifier> _eClassifiers = p.getEClassifiers();
    Iterable<EClass> _filter = Iterables.<EClass>filter(_eClassifiers, EClass.class);
    for (final EClass c : _filter) {
      this.handle(c);
    }
  }
  
  public void handle(final EClass c) {
    EList<EAttribute> _eAllAttributes = c.getEAllAttributes();
    final Function1<EAttribute, Boolean> _function = new Function1<EAttribute, Boolean>() {
      public Boolean apply(final EAttribute it) {
        EClassifier _eType = it.getEType();
        String _name = _eType.getName();
        return Boolean.valueOf(Objects.equal(_name, "EInt"));
      }
    };
    Iterable<EAttribute> _filter = IterableExtensions.<EAttribute>filter(_eAllAttributes, _function);
    for (final EAttribute a : _filter) {
      a.setDefaultValue(Integer.valueOf(0));
    }
  }
}
