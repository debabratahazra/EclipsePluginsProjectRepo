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
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EClassImpl;
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
    final EPackage ePkg = EcoreFactory.eINSTANCE.createEPackage();
    MdfDomain _parentDomain = mdfClass.getParentDomain();
    String _name = _parentDomain.getName();
    ePkg.setName(_name);
    eClass.setName(mdfClass.name);
    boolean _notEquals = (!Objects.equal(mdfClass.baseClass, null));
    if (_notEquals) {
      EList<EClass> _eSuperTypes = eClass.getESuperTypes();
      EClassifier _map = this.map(((MdfClassImpl) mdfClass.baseClass));
      _eSuperTypes.add(((EClass) _map));
    }
    EList<EAnnotation> _eAnnotations = eClass.getEAnnotations();
    List<EAnnotation> _elementAnnotations = this.getElementAnnotations(mdfClass);
    _eAnnotations.addAll(_elementAnnotations);
    this.setDocumentation(eClass, mdfClass.documentation);
    EList<EClassifier> _eClassifiers = ePkg.getEClassifiers();
    _eClassifiers.add(eClass);
    for (final Object property : mdfClass.properties) {
      {
        final MdfPropertyImpl mdfProperty = ((MdfPropertyImpl) property);
        final EStructuralFeature eFeature = this.mapProperty(mdfProperty);
        EList<EStructuralFeature> _eStructuralFeatures = eClass.getEStructuralFeatures();
        _eStructuralFeatures.add(eFeature);
      }
    }
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
    final EPackage ePkg = EcoreFactory.eINSTANCE.createEPackage();
    MdfDomain _parentDomain = mdfDataset.getParentDomain();
    String _name = _parentDomain.getName();
    ePkg.setName(_name);
    EList<EClassifier> _eClassifiers = ePkg.getEClassifiers();
    _eClassifiers.add(eClass);
    eClass.setName(mdfDataset.name);
    EList<EAnnotation> _eAnnotations = eClass.getEAnnotations();
    List<EAnnotation> _elementAnnotations = this.getElementAnnotations(mdfDataset);
    _eAnnotations.addAll(_elementAnnotations);
    this.setDocumentation(eClass, mdfDataset.documentation);
    final EClassImpl eClassImpl = ((EClassImpl) eClass);
    eClassImpl.setClassifierID(EcorePackage.ECLASS);
    for (final Object property : mdfDataset.properties) {
      EList<EStructuralFeature> _eStructuralFeatures = eClass.getEStructuralFeatures();
      EStructuralFeature _mapDatasetProperty = this.mapDatasetProperty(((MdfDatasetPropertyImpl) property));
      _eStructuralFeatures.add(_mapDatasetProperty);
    }
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
    final EPackage ePkg = EcoreFactory.eINSTANCE.createEPackage();
    MdfDomain _parentDomain = mdfBT.getParentDomain();
    String _name = _parentDomain.getName();
    ePkg.setName(_name);
    EList<EClassifier> _eClassifiers = ePkg.getEClassifiers();
    _eClassifiers.add(eDataType);
    eDataType.setName(mdfBT.name);
    EList<EAnnotation> _eAnnotations = eDataType.getEAnnotations();
    List<EAnnotation> _elementAnnotations = this.getElementAnnotations(mdfBT);
    _eAnnotations.addAll(_elementAnnotations);
    final MdfPrimitive type = mdfBT.getType();
    boolean _notEquals = (!Objects.equal(type, null));
    if (_notEquals) {
      final EClassifier baseType = this.map(((MdfPrimitiveImpl) type));
      String _instanceTypeName = baseType.getInstanceTypeName();
      eDataType.setInstanceTypeName(_instanceTypeName);
      final EAnnotation eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
      eAnnotation.setSource("http:///org/eclipse/emf/ecore/util/ExtendedMetaData");
      EMap<String, String> _details = eAnnotation.getDetails();
      String _name_1 = baseType.getName();
      _details.put("baseType", _name_1);
      EList<EAnnotation> _eAnnotations_1 = eDataType.getEAnnotations();
      _eAnnotations_1.add(eAnnotation);
    }
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
    final EPackage ePkg = EcoreFactory.eINSTANCE.createEPackage();
    MdfDomain _parentDomain = mdfEnum.getParentDomain();
    String _name = _parentDomain.getName();
    ePkg.setName(_name);
    EList<EClassifier> _eClassifiers = ePkg.getEClassifiers();
    _eClassifiers.add(eEnum);
    eEnum.setName(mdfEnum.name);
    MdfPrimitive _type = mdfEnum.getType();
    EClassifier _map = this.map(((MdfPrimitiveImpl) _type));
    String _name_1 = _map.getName();
    EcorePlusAspect.EnumBaseType.set(eEnum, _name_1);
    EList<EAnnotation> _eAnnotations = eEnum.getEAnnotations();
    List<EAnnotation> _elementAnnotations = this.getElementAnnotations(mdfEnum);
    _eAnnotations.addAll(_elementAnnotations);
    this.setDocumentation(eEnum, mdfEnum.documentation);
    int i = 0;
    for (final Object literalObj : mdfEnum.values) {
      {
        final MdfEnumValueImpl literal = ((MdfEnumValueImpl) literalObj);
        final EEnumLiteral eLiteral = this.mapEnumLiteral(literal, i);
        EList<EEnumLiteral> _eLiterals = eEnum.getELiterals();
        _eLiterals.add(eLiteral);
        i = (i + 1);
      }
    }
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
    this.setPropertyAttributes(eReference, mdfrevAssociation);
    final MdfAssociationImpl mdfOpposite = ((MdfAssociationImpl) mdfrevAssociation.reversedAssociation);
    EStructuralFeature _mapProperty = this.mapProperty(mdfOpposite);
    final EReference opposite = ((EReference) _mapProperty);
    MdfClass _parentClass = mdfOpposite.getParentClass();
    EClassifier _map = this.map(((MdfClassImpl) _parentClass));
    EList<EStructuralFeature> _eStructuralFeatures = ((EClass) _map).getEStructuralFeatures();
    _eStructuralFeatures.add(opposite);
    eReference.setEOpposite(opposite);
    opposite.setEOpposite(eReference);
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
    EStructuralFeature _xblockexpression = null;
    {
      EStructuralFeature eProperty = null;
      final MdfEntity mdfType = mdfDSMappedProperty.getType();
      boolean _notEquals = (!Objects.equal(mdfType, null));
      if (_notEquals) {
        final EClassifier type = this.map(((MdfEntityImpl) mdfType));
        if ((type instanceof EDataType)) {
          final EAttribute eAttr = EcoreFactory.eINSTANCE.createEAttribute();
          boolean _isPrimaryKey = mdfDSMappedProperty.isPrimaryKey();
          eAttr.setID(_isPrimaryKey);
          boolean _isPrimaryKey_1 = mdfDSMappedProperty.isPrimaryKey();
          eAttr.setUnique(_isPrimaryKey_1);
          int _xifexpression = (int) 0;
          boolean _isRequired = mdfDSMappedProperty.isRequired();
          if (_isRequired) {
            _xifexpression = 1;
          } else {
            _xifexpression = 0;
          }
          eAttr.setLowerBound(_xifexpression);
          boolean _isBusinessKey = mdfDSMappedProperty.isBusinessKey();
          if (_isBusinessKey) {
            EcorePlusAspect.BusinessKey.set(eAttr, Boolean.valueOf(true));
          }
          eProperty = eAttr;
        } else {
          EReference _createEReference = EcoreFactory.eINSTANCE.createEReference();
          eProperty = _createEReference;
        }
      } else {
        EAttribute _createEAttribute = EcoreFactory.eINSTANCE.createEAttribute();
        eProperty = _createEAttribute;
      }
      this.setPropertyAttributes(eProperty, mdfDSMappedProperty);
      _xblockexpression = eProperty;
    }
    return _xblockexpression;
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
  
  protected String _getPropertyName(final MdfProperty property) {
    return property.getName();
  }
  
  protected String _getPropertyName(final MdfDatasetProperty property) {
    return property.getName();
  }
  
  protected void _setPropertyAttributes(final EStructuralFeature eProperty, final MdfProperty mdfProperty) {
    String _propertyName = this.getPropertyName(mdfProperty);
    eProperty.setName(_propertyName);
    MdfEntity _type = mdfProperty.getType();
    EClassifier _map = this.map(((MdfEntityImpl) _type));
    eProperty.setEType(_map);
    EList<EAnnotation> _eAnnotations = eProperty.getEAnnotations();
    List<EAnnotation> _elementAnnotations = this.getElementAnnotations(mdfProperty);
    _eAnnotations.addAll(_elementAnnotations);
    String _documentation = mdfProperty.getDocumentation();
    this.setDocumentation(eProperty, _documentation);
    int _xifexpression = (int) 0;
    boolean _isRequired = mdfProperty.isRequired();
    if (_isRequired) {
      _xifexpression = 1;
    } else {
      _xifexpression = 0;
    }
    eProperty.setLowerBound(_xifexpression);
    int _xifexpression_1 = (int) 0;
    int _multiplicity = mdfProperty.getMultiplicity();
    boolean _equals = (_multiplicity == MdfMultiplicity.ONE);
    if (_equals) {
      _xifexpression_1 = 1;
    } else {
      _xifexpression_1 = (-1);
    }
    eProperty.setUpperBound(_xifexpression_1);
    boolean _isBusinessKey = mdfProperty.isBusinessKey();
    if (_isBusinessKey) {
      EcorePlusAspect.BusinessKey.set(eProperty, Boolean.valueOf(true));
    }
    boolean _isPrimaryKey = mdfProperty.isPrimaryKey();
    eProperty.setUnique(_isPrimaryKey);
  }
  
  protected void _setPropertyAttributes(final EStructuralFeature eProperty, final MdfDatasetProperty mdfProperty) {
    String _propertyName = this.getPropertyName(mdfProperty);
    eProperty.setName(_propertyName);
    final MdfEntity mdfType = mdfProperty.getType();
    boolean _notEquals = (!Objects.equal(mdfType, null));
    if (_notEquals) {
      EClassifier _map = this.map(((MdfEntityImpl) mdfType));
      eProperty.setEType(_map);
    }
    EList<EAnnotation> _eAnnotations = eProperty.getEAnnotations();
    List<EAnnotation> _elementAnnotations = this.getElementAnnotations(mdfProperty);
    _eAnnotations.addAll(_elementAnnotations);
    String _documentation = mdfProperty.getDocumentation();
    this.setDocumentation(eProperty, _documentation);
    int _xifexpression = (int) 0;
    int _multiplicity = mdfProperty.getMultiplicity();
    boolean _equals = (_multiplicity == MdfMultiplicity.ONE);
    if (_equals) {
      _xifexpression = 1;
    } else {
      _xifexpression = (-1);
    }
    eProperty.setUpperBound(_xifexpression);
  }
  
  public List<EAnnotation> getElementAnnotations(final MdfModelElement mdfElement) {
    List<EAnnotation> _xblockexpression = null;
    {
      final List<EAnnotation> eAnnotations = CollectionLiterals.<EAnnotation>newArrayList();
      List _annotations = mdfElement.getAnnotations();
      for (final Object obj : _annotations) {
        {
          final MdfAnnotation mdfAnnotation = ((MdfAnnotation) obj);
          final EAnnotation eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
          String _name = mdfAnnotation.getName();
          eAnnotation.setSource(_name);
          List _properties = mdfAnnotation.getProperties();
          for (final Object propObj : _properties) {
            {
              final MdfAnnotationProperty property = ((MdfAnnotationProperty) propObj);
              EMap<String, String> _details = eAnnotation.getDetails();
              String _name_1 = property.getName();
              String _value = property.getValue();
              _details.put(_name_1, _value);
            }
          }
          eAnnotations.add(eAnnotation);
        }
      }
      _xblockexpression = eAnnotations;
    }
    return _xblockexpression;
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
  
  public String getPropertyName(final MdfModelElement property) {
    if (property instanceof MdfDatasetProperty) {
      return _getPropertyName((MdfDatasetProperty)property);
    } else if (property instanceof MdfProperty) {
      return _getPropertyName((MdfProperty)property);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(property).toString());
    }
  }
  
  public void setPropertyAttributes(final EStructuralFeature eProperty, final MdfModelElement mdfProperty) {
    if (mdfProperty instanceof MdfDatasetProperty) {
      _setPropertyAttributes(eProperty, (MdfDatasetProperty)mdfProperty);
      return;
    } else if (mdfProperty instanceof MdfProperty) {
      _setPropertyAttributes(eProperty, (MdfProperty)mdfProperty);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(eProperty, mdfProperty).toString());
    }
  }
}