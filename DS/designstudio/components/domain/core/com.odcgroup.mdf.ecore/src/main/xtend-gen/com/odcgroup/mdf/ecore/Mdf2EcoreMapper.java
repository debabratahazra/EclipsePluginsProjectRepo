package com.odcgroup.mdf.ecore;

import com.google.common.base.Objects;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfBusinessTypeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDatasetDerivedPropertyImpl;
import com.odcgroup.mdf.ecore.MdfDatasetImpl;
import com.odcgroup.mdf.ecore.MdfDatasetMappedPropertyImpl;
import com.odcgroup.mdf.ecore.MdfDatasetPropertyImpl;
import com.odcgroup.mdf.ecore.MdfEntityImpl;
import com.odcgroup.mdf.ecore.MdfEnumValueImpl;
import com.odcgroup.mdf.ecore.MdfEnumerationImpl;
import com.odcgroup.mdf.ecore.MdfPrimitiveImpl;
import com.odcgroup.mdf.ecore.MdfPropertyImpl;
import com.odcgroup.mdf.ecore.MdfReverseAssociationImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ecore.annotation.EcorePlusAspect;
import com.odcgroup.mdf.ecore.lazy.MdfDocumentationAdapter;
import com.odcgroup.mdf.metamodel.MdfContainment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class Mdf2EcoreMapper {
  public EClass mapInternal(final MdfClassImpl mdfClass) {
    final ArrayList<?> _cacheKey = CollectionLiterals.newArrayList(mdfClass);
    final EClass _result;
    synchronized (_createCache_mapInternal) {
      if (_createCache_mapInternal.containsKey(_cacheKey)) {
        return _createCache_mapInternal.get(_cacheKey);
      }
      EClass _createEClass = EcoreFactory.eINSTANCE.createEClass();
      _result = _createEClass;
      _createCache_mapInternal.put(_cacheKey, _result);
    }
    _init_mapInternal(_result, mdfClass);
    return _result;
  }
  
  private final HashMap<ArrayList<?>, EClass> _createCache_mapInternal = CollectionLiterals.newHashMap();
  
  private void _init_mapInternal(final EClass eClass, final MdfClassImpl mdfClass) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method parentDomain is undefined for the type Mdf2EcoreMapper"
      + "\nThe method baseClass is undefined for the type Mdf2EcoreMapper"
      + "\nThe method baseClass is undefined for the type Mdf2EcoreMapper"
      + "\nname cannot be resolved"
      + "\n!= cannot be resolved");
  }
  
  protected EClassifier _map(final MdfClassImpl mdfClass) {
    return this.mapInternal(mdfClass);
  }
  
  public EClass mapInternal(final MdfDatasetImpl mdfDataset) {
    final ArrayList<?> _cacheKey = CollectionLiterals.newArrayList(mdfDataset);
    final EClass _result;
    synchronized (_createCache_mapInternal_1) {
      if (_createCache_mapInternal_1.containsKey(_cacheKey)) {
        return _createCache_mapInternal_1.get(_cacheKey);
      }
      EClass _createEClass = EcoreFactory.eINSTANCE.createEClass();
      _result = _createEClass;
      _createCache_mapInternal_1.put(_cacheKey, _result);
    }
    _init_mapInternal_1(_result, mdfDataset);
    return _result;
  }
  
  private final HashMap<ArrayList<?>, EClass> _createCache_mapInternal_1 = CollectionLiterals.newHashMap();
  
  private void _init_mapInternal_1(final EClass eClass, final MdfDatasetImpl mdfDataset) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method parentDomain is undefined for the type Mdf2EcoreMapper"
      + "\nname cannot be resolved");
  }
  
  protected EClassifier _map(final MdfDatasetImpl mdfDataset) {
    return this.mapInternal(mdfDataset);
  }
  
  public EDataType mapInternal(final MdfBusinessTypeImpl mdfBT) {
    final ArrayList<?> _cacheKey = CollectionLiterals.newArrayList(mdfBT);
    final EDataType _result;
    synchronized (_createCache_mapInternal_2) {
      if (_createCache_mapInternal_2.containsKey(_cacheKey)) {
        return _createCache_mapInternal_2.get(_cacheKey);
      }
      EDataType _createEDataType = EcoreFactory.eINSTANCE.createEDataType();
      _result = _createEDataType;
      _createCache_mapInternal_2.put(_cacheKey, _result);
    }
    _init_mapInternal_2(_result, mdfBT);
    return _result;
  }
  
  private final HashMap<ArrayList<?>, EDataType> _createCache_mapInternal_2 = CollectionLiterals.newHashMap();
  
  private void _init_mapInternal_2(final EDataType eDataType, final MdfBusinessTypeImpl mdfBT) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method parentDomain is undefined for the type Mdf2EcoreMapper"
      + "\nThe method getType is undefined for the type Mdf2EcoreMapper"
      + "\nname cannot be resolved"
      + "\n!= cannot be resolved");
  }
  
  protected EClassifier _map(final MdfBusinessTypeImpl mdfBT) {
    return this.mapInternal(mdfBT);
  }
  
  public EEnum mapInternal(final MdfEnumerationImpl mdfEnum) {
    final ArrayList<?> _cacheKey = CollectionLiterals.newArrayList(mdfEnum);
    final EEnum _result;
    synchronized (_createCache_mapInternal_3) {
      if (_createCache_mapInternal_3.containsKey(_cacheKey)) {
        return _createCache_mapInternal_3.get(_cacheKey);
      }
      EEnum _createEEnum = EcoreFactory.eINSTANCE.createEEnum();
      _result = _createEEnum;
      _createCache_mapInternal_3.put(_cacheKey, _result);
    }
    _init_mapInternal_3(_result, mdfEnum);
    return _result;
  }
  
  private final HashMap<ArrayList<?>, EEnum> _createCache_mapInternal_3 = CollectionLiterals.newHashMap();
  
  private void _init_mapInternal_3(final EEnum eEnum, final MdfEnumerationImpl mdfEnum) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method parentDomain is undefined for the type Mdf2EcoreMapper"
      + "\nThe method getType is undefined for the type Mdf2EcoreMapper"
      + "\nname cannot be resolved");
  }
  
  protected EClassifier _map(final MdfEnumerationImpl mdfEnum) {
    return this.mapInternal(mdfEnum);
  }
  
  protected EDataType _map(final MdfPrimitiveImpl mdfPrimitive) {
    EDataType _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.STRING)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getEString();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.DECIMAL)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getEBigDecimal();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.BOOLEAN)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getEBoolean();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.BOOLEAN_OBJ)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getEBooleanObject();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.FLOAT)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getEFloat();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.FLOAT_OBJ)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getEFloatObject();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.DOUBLE)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getEDouble();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.DOUBLE_OBJ)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getEDoubleObject();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.INTEGER)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getEInt();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.INTEGER_OBJ)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getEIntegerObject();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.SHORT)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getEShort();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.SHORT_OBJ)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getEShortObject();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.LONG)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getELong();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.LONG_OBJ)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getELongObject();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.DATE)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getEDate();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.DATE_TIME)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getEDate();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.BYTE)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getEByte();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.BYTE_OBJ)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getEByteObject();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.CHAR)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getEChar();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.CHAR_OBJ)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getECharacterObject();
      }
    }
    if (!_matched) {
      if (Objects.equal(mdfPrimitive, PrimitivesDomain.URI)) {
        _matched=true;
        _switchResult = EcorePackage.eINSTANCE.getEString();
      }
    }
    if (!_matched) {
      String _string = mdfPrimitive.toString();
      String _plus = ("Unknown MdfPrimitiveImpl: " + _string);
      throw new IllegalArgumentException(_plus);
    }
    return _switchResult;
  }
  
  protected EStructuralFeature _mapProperty(final MdfAttributeImpl mdfAttribute) {
    final EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
    this.setPropertyAttributes(eAttribute, mdfAttribute);
    eAttribute.setID(mdfAttribute.primaryKey);
    String _default = mdfAttribute.getDefault();
    eAttribute.setDefaultValue(_default);
    return eAttribute;
  }
  
  public EReference mapPropertyInternal(final MdfAssociationImpl mdfAssociation) {
    final ArrayList<?> _cacheKey = CollectionLiterals.newArrayList(mdfAssociation);
    final EReference _result;
    synchronized (_createCache_mapPropertyInternal) {
      if (_createCache_mapPropertyInternal.containsKey(_cacheKey)) {
        return _createCache_mapPropertyInternal.get(_cacheKey);
      }
      EReference _createEReference = EcoreFactory.eINSTANCE.createEReference();
      _result = _createEReference;
      _createCache_mapPropertyInternal.put(_cacheKey, _result);
    }
    _init_mapPropertyInternal(_result, mdfAssociation);
    return _result;
  }
  
  private final HashMap<ArrayList<?>, EReference> _createCache_mapPropertyInternal = CollectionLiterals.newHashMap();
  
  private void _init_mapPropertyInternal(final EReference eReference, final MdfAssociationImpl mdfAssociation) {
    this.setPropertyAttributes(eReference, mdfAssociation);
    eReference.setContainment((mdfAssociation.containment == MdfContainment.BY_VALUE));
  }
  
  protected EStructuralFeature _mapProperty(final MdfAssociationImpl mdfAssociation) {
    return this.mapPropertyInternal(mdfAssociation);
  }
  
  public EReference mapPropertyInternal(final MdfReverseAssociationImpl mdfrevAssociation) {
    final ArrayList<?> _cacheKey = CollectionLiterals.newArrayList(mdfrevAssociation);
    final EReference _result;
    synchronized (_createCache_mapPropertyInternal_1) {
      if (_createCache_mapPropertyInternal_1.containsKey(_cacheKey)) {
        return _createCache_mapPropertyInternal_1.get(_cacheKey);
      }
      EReference _createEReference = EcoreFactory.eINSTANCE.createEReference();
      _result = _createEReference;
      _createCache_mapPropertyInternal_1.put(_cacheKey, _result);
    }
    _init_mapPropertyInternal_1(_result, mdfrevAssociation);
    return _result;
  }
  
  private final HashMap<ArrayList<?>, EReference> _createCache_mapPropertyInternal_1 = CollectionLiterals.newHashMap();
  
  private void _init_mapPropertyInternal_1(final EReference eReference, final MdfReverseAssociationImpl mdfrevAssociation) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method reversedAssociation is undefined for the type Mdf2EcoreMapper"
      + "\nThe method parentClass is undefined for the type Mdf2EcoreMapper");
  }
  
  protected EStructuralFeature _mapProperty(final MdfReverseAssociationImpl mdfrevAssociation) {
    return this.mapPropertyInternal(mdfrevAssociation);
  }
  
  protected EStructuralFeature _mapDatasetProperty(final MdfDatasetDerivedPropertyImpl mdfDSDerivedProperty) {
    final EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
    this.setPropertyAttributes(eAttribute, mdfDSDerivedProperty);
    return eAttribute;
  }
  
  protected EStructuralFeature _mapDatasetProperty(final MdfDatasetMappedPropertyImpl mdfDSMappedProperty) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method getType is undefined for the type Mdf2EcoreMapper"
      + "\n!= cannot be resolved");
  }
  
  public EEnumLiteral mapEnumLiteral(final MdfEnumValueImpl mdfEnumValue, final int index) {
    final EEnumLiteral eEnumLiteral = EcoreFactory.eINSTANCE.createEEnumLiteral();
    eEnumLiteral.setName(mdfEnumValue.name);
    EcorePlusAspect.EnumLiteralValue.set(eEnumLiteral, mdfEnumValue.value);
    String value = mdfEnumValue.value;
    boolean _equals = Objects.equal(value, "true");
    if (_equals) {
      value = "1";
    }
    boolean _equals_1 = Objects.equal(value, "false");
    if (_equals_1) {
      value = "0";
    }
    try {
      int _parseInt = Integer.parseInt(value);
      eEnumLiteral.setValue(_parseInt);
    } catch (final Throwable _t) {
      if (_t instanceof NumberFormatException) {
        final NumberFormatException nfe = (NumberFormatException)_t;
        eEnumLiteral.setValue(index);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    this.setDocumentation(eEnumLiteral, mdfEnumValue.documentation);
    return eEnumLiteral;
  }
  
  public boolean setDocumentation(final EObject obj, final String doc) {
    boolean _xifexpression = false;
    boolean _notEquals = (!Objects.equal(doc, null));
    if (_notEquals) {
      boolean _xblockexpression = false;
      {
        final MdfDocumentationAdapter documentationAdapter = new MdfDocumentationAdapter();
        documentationAdapter.setDocumentation(doc);
        EList<Adapter> _eAdapters = obj.eAdapters();
        _xblockexpression = _eAdapters.add(documentationAdapter);
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }
  
  protected Object _getPropertyName(final /* MdfProperty */Object property) {
    throw new Error("Unresolved compilation problems:"
      + "\nname cannot be resolved");
  }
  
  protected Object _getPropertyName(final /* MdfDatasetProperty */Object property) {
    throw new Error("Unresolved compilation problems:"
      + "\nname cannot be resolved");
  }
  
  protected void _setPropertyAttributes(final EStructuralFeature eProperty, final /* MdfProperty */Object mdfProperty) {
    throw new Error("Unresolved compilation problems:"
      + "\ngetType cannot be resolved"
      + "\nelementAnnotations cannot be resolved"
      + "\ndocumentation cannot be resolved"
      + "\nrequired cannot be resolved"
      + "\nmultiplicity cannot be resolved"
      + "\n== cannot be resolved"
      + "\nbusinessKey cannot be resolved"
      + "\nprimaryKey cannot be resolved");
  }
  
  protected void _setPropertyAttributes(final EStructuralFeature eProperty, final /* MdfDatasetProperty */Object mdfProperty) {
    throw new Error("Unresolved compilation problems:"
      + "\ngetType cannot be resolved"
      + "\n!= cannot be resolved"
      + "\nelementAnnotations cannot be resolved"
      + "\ndocumentation cannot be resolved"
      + "\nmultiplicity cannot be resolved"
      + "\n== cannot be resolved");
  }
  
  public List<EAnnotation> getElementAnnotations(final /* MdfModelElement */Object mdfElement) {
    throw new Error("Unresolved compilation problems:"
      + "\nMdfAnnotation cannot be resolved to a type."
      + "\nMdfAnnotation cannot be resolved to a type."
      + "\nMdfAnnotationProperty cannot be resolved to a type."
      + "\nannotations cannot be resolved"
      + "\nname cannot be resolved"
      + "\nproperties cannot be resolved"
      + "\nname cannot be resolved"
      + "\nvalue cannot be resolved");
  }
  
  public EClassifier map(final MdfEntityImpl mdfBT) {
    if (mdfBT instanceof MdfBusinessTypeImpl) {
      return _map((MdfBusinessTypeImpl)mdfBT);
    } else if (mdfBT instanceof MdfEnumerationImpl) {
      return _map((MdfEnumerationImpl)mdfBT);
    } else if (mdfBT instanceof MdfClassImpl) {
      return _map((MdfClassImpl)mdfBT);
    } else if (mdfBT instanceof MdfDatasetImpl) {
      return _map((MdfDatasetImpl)mdfBT);
    } else if (mdfBT instanceof MdfPrimitiveImpl) {
      return _map((MdfPrimitiveImpl)mdfBT);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(mdfBT).toString());
    }
  }
  
  public EStructuralFeature mapProperty(final MdfPropertyImpl mdfAssociation) {
    if (mdfAssociation instanceof MdfAssociationImpl) {
      return _mapProperty((MdfAssociationImpl)mdfAssociation);
    } else if (mdfAssociation instanceof MdfAttributeImpl) {
      return _mapProperty((MdfAttributeImpl)mdfAssociation);
    } else if (mdfAssociation instanceof MdfReverseAssociationImpl) {
      return _mapProperty((MdfReverseAssociationImpl)mdfAssociation);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(mdfAssociation).toString());
    }
  }
  
  public EStructuralFeature mapDatasetProperty(final MdfDatasetPropertyImpl mdfDSDerivedProperty) {
    if (mdfDSDerivedProperty instanceof MdfDatasetDerivedPropertyImpl) {
      return _mapDatasetProperty((MdfDatasetDerivedPropertyImpl)mdfDSDerivedProperty);
    } else if (mdfDSDerivedProperty instanceof MdfDatasetMappedPropertyImpl) {
      return _mapDatasetProperty((MdfDatasetMappedPropertyImpl)mdfDSDerivedProperty);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(mdfDSDerivedProperty).toString());
    }
  }
  
  public Object getPropertyName(final MdfProperty property) {
    if (property != null) {
      return _getPropertyName(property); else {
        throw new IllegalArgumentException("Unhandled parameter types: " +
          Arrays.<Object>asList(property).toString());
      }
    }
    
    public void setPropertyAttributes(final EStructuralFeature eProperty, final MdfProperty mdfProperty) {
      if (mdfProperty != null) {
        _setPropertyAttributes(eProperty, mdfProperty);
        return; else {
          throw new IllegalArgumentException("Unhandled parameter types: " +
            Arrays.<Object>asList(eProperty, mdfProperty).toString());
        }
      }
    }
    