package com.odcgroup.t24.applicationimport.mapper;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.odcgroup.domain.annotations.JavaAspectDS;
import com.odcgroup.domain.resource.DomainURIEncoder;
import com.odcgroup.domain.validation.JavaKeywordChecker;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfEnumValueImpl;
import com.odcgroup.mdf.ecore.MdfEnumerationImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.ecore.MdfPrimitiveImpl;
import com.odcgroup.mdf.ecore.MdfPropertyImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.utils.StringUtils;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.applicationimport.mapper.MappingException;
import com.odcgroup.t24.applicationimport.schema.ApplicationEntity;
import com.odcgroup.t24.applicationimport.schema.ApplicationField;
import com.odcgroup.t24.applicationimport.schema.ApplicationLevel;
import com.odcgroup.t24.applicationimport.schema.ApplicationReference;
import com.odcgroup.t24.applicationimport.schema.ApplicationType;
import com.odcgroup.t24.applicationimport.schema.FieldAlignment;
import com.odcgroup.t24.applicationimport.schema.FieldType;
import com.odcgroup.t24.applicationimport.schema.FieldValidValue;
import com.odcgroup.t24.applicationimport.schema.InputBehaviour;
import com.odcgroup.t24.applicationimport.schema.MvSvExpansionAccess;
import com.odcgroup.t24.applicationimport.schema.Translation;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang.LocaleUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.xtext.linking.lazy.LazyLinker;
import org.eclipse.xtext.util.EcoreGenericsUtil;
import org.eclipse.xtext.util.SimpleCache;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Maps T24 Components/Modules/Applications into DS Domain.
 * 
 * Implementation note: This class "isn't" (as in OOP IS-A) a LazyLinker,
 * as in linkModel() never gets called on it; it only extends LazyLinker
 * so that its protected findInstantiableCompatible() helper util can be
 * used here.
 * 
 * @author kosyakov (for Michael Vorburger) - adapted to use new infra instead of using MdfEcoreUtil, @see http://rd.oams.com/browse/DS-7424
 */
@SuppressWarnings("all")
public class T24MetaModel2MdfMapper extends LazyLinker {
  private static Logger LOGGER = LoggerFactory.getLogger(T24MetaModel2MdfMapper.class);
  
  private final SimpleCache<EClass, EClass> instantiableSubTypes = new SimpleCache<EClass, EClass>(new Function<EClass, EClass>() {
    public EClass apply(final EClass it) {
      return T24MetaModel2MdfMapper.this.findInstantiableCompatible(it);
    }
  });
  
  private IProject project;
  
  @Inject
  private DomainURIEncoder domainURIEncoder;
  
  @Inject
  private EcoreGenericsUtil ecoreGenericsUtil;
  
  public MdfDomainImpl mapInternal(final String componentName, final String moduleName, final IProject project) throws MappingException {
    final ArrayList<?> _cacheKey = CollectionLiterals.newArrayList(componentName, moduleName, project);
    final MdfDomainImpl _result;
    synchronized (_createCache_mapInternal) {
      if (_createCache_mapInternal.containsKey(_cacheKey)) {
        return _createCache_mapInternal.get(_cacheKey);
      }
      MdfDomain _createMdfDomain = MdfFactory.eINSTANCE.createMdfDomain();
      _result = ((MdfDomainImpl) _createMdfDomain);
      _createCache_mapInternal.put(_cacheKey, _result);
    }
    _init_mapInternal(_result, componentName, moduleName, project);
    return _result;
  }
  
  private final HashMap<ArrayList<?>, MdfDomainImpl> _createCache_mapInternal = CollectionLiterals.newHashMap();
  
  private void _init_mapInternal(final MdfDomainImpl mdfDomain, final String componentName, final String moduleName, final IProject project) throws MappingException {
    this.project = project;
    final MdfDomainImpl mdfDomainImpl = ((MdfDomainImpl) mdfDomain);
    boolean _or = false;
    boolean _equals = Objects.equal(componentName, null);
    if (_equals) {
      _or = true;
    } else {
      String _trim = componentName.trim();
      boolean _isEmpty = _trim.isEmpty();
      _or = _isEmpty;
    }
    if (_or) {
      this.throwMappingError("No Component Name - BUG in T24 XML");
    }
    mdfDomainImpl.setName(componentName);
    mdfDomainImpl.setNamespace(("http://www.temenos.com/t24/" + componentName));
    T24Aspect.setModule(mdfDomainImpl, moduleName);
    JavaKeywordChecker _instance = JavaKeywordChecker.getInstance();
    String _lowerCase = moduleName.toLowerCase();
    boolean _isKeyword = _instance.isKeyword(_lowerCase);
    if (_isKeyword) {
      String _lowerCase_1 = moduleName.toLowerCase();
      String _plus = ("com.temenos.t24.datamodel." + _lowerCase_1);
      String _plus_1 = (_plus + "_.");
      String _lowerCase_2 = componentName.toLowerCase();
      final String javaPackage = (_plus_1 + _lowerCase_2);
      JavaAspectDS.setPackage(mdfDomainImpl, javaPackage);
    } else {
      String _lowerCase_3 = moduleName.toLowerCase();
      String _plus_2 = ("com.temenos.t24.datamodel." + _lowerCase_3);
      String _plus_3 = (_plus_2 + ".");
      String _lowerCase_4 = componentName.toLowerCase();
      final String javaPackage_1 = (_plus_3 + _lowerCase_4);
      JavaAspectDS.setPackage(mdfDomainImpl, javaPackage_1);
    }
  }
  
  public MdfDomainImpl map(final String componentName, final String moduleName, final IProject project) throws MappingException {
    return this.mapInternal(componentName, moduleName, project);
  }
  
  public void map(final List<ApplicationEntity> appEntityList, final MdfDomainImpl domain, final IProject project) throws MappingException {
    this.project = project;
    for (final ApplicationEntity application : appEntityList) {
      this.map(application, domain);
    }
  }
  
  public MdfClass mapInternal(final ApplicationEntity application, final MdfDomainImpl domain) throws MappingException {
    final ArrayList<?> _cacheKey = CollectionLiterals.newArrayList(application, domain);
    final MdfClass _result;
    synchronized (_createCache_mapInternal_1) {
      if (_createCache_mapInternal_1.containsKey(_cacheKey)) {
        return _createCache_mapInternal_1.get(_cacheKey);
      }
      MdfClass _createMdfClass = MdfFactory.eINSTANCE.createMdfClass();
      _result = _createMdfClass;
      _createCache_mapInternal_1.put(_cacheKey, _result);
    }
    _init_mapInternal(_result, application, domain);
    return _result;
  }
  
  private final HashMap<ArrayList<?>, MdfClass> _createCache_mapInternal_1 = CollectionLiterals.newHashMap();
  
  private void _init_mapInternal(final MdfClass mdfClass, final ApplicationEntity application, final MdfDomainImpl domain) throws MappingException {
    MdfClassImpl mdfClassImpl = ((MdfClassImpl) mdfClass);
    String _name = application.getName();
    String _validName = this.toValidName(_name);
    mdfClassImpl.setName(_validName);
    String _nameT24 = application.getNameT24();
    boolean _notEquals = (!Objects.equal(_nameT24, null));
    if (_notEquals) {
      String _nameT24_1 = application.getNameT24();
      T24Aspect.setT24Name(mdfClassImpl, _nameT24_1);
    } else {
      String _name_1 = application.getName();
      T24Aspect.setT24Name(mdfClassImpl, _name_1);
    }
    MdfClassImpl duplicateClazz = null;
    List _classes = domain.getClasses();
    for (final Object mdfClazz : _classes) {
      String _name_2 = ((MdfClass) mdfClazz).getName();
      String _name_3 = mdfClassImpl.getName();
      boolean _equals = _name_2.equals(_name_3);
      if (_equals) {
        duplicateClazz = ((MdfClassImpl) mdfClazz);
        String _t24Name = T24Aspect.getT24Name(mdfClassImpl);
        T24Aspect.setT24Name(duplicateClazz, _t24Name);
      }
    }
    boolean _notEquals_1 = (!Objects.equal(duplicateClazz, null));
    if (_notEquals_1) {
      List _properties = duplicateClazz.getProperties();
      _properties.clear();
      List _properties_1 = duplicateClazz.getProperties();
      List _properties_2 = mdfClassImpl.getProperties();
      _properties_1.addAll(_properties_2);
      mdfClassImpl = duplicateClazz;
    } else {
      List _classes_1 = domain.getClasses();
      _classes_1.add(mdfClassImpl);
    }
    String _documentation = application.getDocumentation();
    mdfClassImpl.setDocumentation(_documentation);
    boolean _isNonStop = application.isNonStop();
    T24Aspect.setNonStop(mdfClassImpl, _isNonStop);
    ApplicationLevel _level = application.getLevel();
    T24Aspect.setLevel(mdfClassImpl, _level);
    ApplicationType _type = application.getType();
    T24Aspect.setType(mdfClassImpl, _type);
    String _additionalInfo = application.getAdditionalInfo();
    T24Aspect.setAdditionalInfo(mdfClassImpl, _additionalInfo);
    String _allowedFunctions = application.getAllowedFunctions();
    T24Aspect.setAllowedFunctions(mdfClassImpl, _allowedFunctions);
    boolean localRefAllowed = false;
    List<ApplicationField> _fields = application.getFields();
    for (final ApplicationField field : _fields) {
      {
        MdfModelElement mdfModelelement = null;
        boolean _and = false;
        if (!(!localRefAllowed)) {
          _and = false;
        } else {
          String _name_4 = field.getName();
          String _validName_1 = this.toValidName(_name_4);
          boolean _equals_1 = _validName_1.equals("LOCAL_REF");
          _and = _equals_1;
        }
        if (_and) {
          localRefAllowed = true;
        }
        String _name_5 = field.getName();
        String _validName_2 = this.toValidName(_name_5);
        boolean _equals_2 = _validName_2.equals("LOCAL_REF");
        boolean _not = (!_equals_2);
        if (_not) {
          String _multiValueGroupName = field.getMultiValueGroupName();
          boolean _notEquals_2 = (!Objects.equal(_multiValueGroupName, null));
          if (_notEquals_2) {
            this.mapMultiValue(field, application, mdfClassImpl, domain);
          } else {
            boolean _and_1 = false;
            String _multiValueGroupName_1 = field.getMultiValueGroupName();
            boolean _equals_3 = Objects.equal(_multiValueGroupName_1, null);
            if (!_equals_3) {
              _and_1 = false;
            } else {
              String _subValueGroupName = field.getSubValueGroupName();
              boolean _notEquals_3 = (!Objects.equal(_subValueGroupName, null));
              _and_1 = _notEquals_3;
            }
            if (_and_1) {
              String _name_6 = application.getName();
              String _plus = (_name_6 + "#");
              String _name_7 = field.getName();
              String _plus_1 = (_plus + _name_7);
              String _plus_2 = (_plus_1 + 
                " has a subValueGroupName but no multiValueGroupName - BUG in T24 XML");
              this.throwMappingError(_plus_2);
            } else {
              MdfPropertyImpl _map = this.map(field, application, mdfClassImpl, domain);
              mdfModelelement = _map;
              boolean _and_2 = false;
              boolean _notEquals_4 = (!Objects.equal(mdfModelelement, null));
              if (!_notEquals_4) {
                _and_2 = false;
              } else {
                String _name_8 = mdfModelelement.getName();
                boolean _isDuplicateProperty = this.isDuplicateProperty(mdfClassImpl, _name_8);
                boolean _not_1 = (!_isDuplicateProperty);
                _and_2 = _not_1;
              }
              if (_and_2) {
                List _properties_3 = mdfClassImpl.getProperties();
                _properties_3.add(mdfModelelement);
                this.installProxies(field, mdfModelelement);
                this.mapTranslation(mdfModelelement, field);
              }
            }
          }
        }
      }
    }
    this.mapTranslation(mdfClassImpl, application);
    this.setLocalRefAllowed(mdfClassImpl, localRefAllowed);
  }
  
  public MdfClass map(final ApplicationEntity application, final MdfDomainImpl domain) throws MappingException {
    return this.mapInternal(application, domain);
  }
  
  public MdfClass mapMultiValue(final ApplicationField field, final ApplicationEntity application, final MdfClassImpl mdfMainClass, final MdfDomainImpl domain) {
    final ArrayList<?> _cacheKey = CollectionLiterals.newArrayList(field, application, mdfMainClass, domain);
    final MdfClass _result;
    synchronized (_createCache_mapMultiValue) {
      if (_createCache_mapMultiValue.containsKey(_cacheKey)) {
        return _createCache_mapMultiValue.get(_cacheKey);
      }
      MdfClass _createMdfClass = MdfFactory.eINSTANCE.createMdfClass();
      _result = _createMdfClass;
      _createCache_mapMultiValue.put(_cacheKey, _result);
    }
    _init_mapMultiValue(_result, field, application, mdfMainClass, domain);
    return _result;
  }
  
  private final HashMap<ArrayList<?>, MdfClass> _createCache_mapMultiValue = CollectionLiterals.newHashMap();
  
  private void _init_mapMultiValue(final MdfClass mdfProperty, final ApplicationField field, final ApplicationEntity application, final MdfClassImpl mdfMainClass, final MdfDomainImpl domain) {
    String _name = application.getName();
    String _plus = (_name + "__");
    String _multiValueGroupName = field.getMultiValueGroupName();
    String _plus_1 = (_plus + _multiValueGroupName);
    final String multiValueClassName = this.toValidName(_plus_1);
    String _multiValueGroupName_1 = field.getMultiValueGroupName();
    String _validName = this.toValidName(_multiValueGroupName_1);
    MdfClassImpl multiValueClass = this.mapMultiOrSubValueClass(multiValueClassName, _validName, mdfMainClass, domain);
    String _subValueGroupName = field.getSubValueGroupName();
    boolean _notEquals = (!Objects.equal(_subValueGroupName, null));
    if (_notEquals) {
      String _name_1 = application.getName();
      String _plus_2 = (_name_1 + "__");
      String _multiValueGroupName_2 = field.getMultiValueGroupName();
      String _plus_3 = (_plus_2 + _multiValueGroupName_2);
      String _plus_4 = (_plus_3 + "__");
      String _subValueGroupName_1 = field.getSubValueGroupName();
      String _plus_5 = (_plus_4 + _subValueGroupName_1);
      final String subValueClassName = this.toValidName(_plus_5);
      String _subValueGroupName_2 = field.getSubValueGroupName();
      String _validName_1 = this.toValidName(_subValueGroupName_2);
      MdfClassImpl subValueClass = this.mapMultiOrSubValueClass(subValueClassName, _validName_1, multiValueClass, domain);
      MdfPropertyImpl mdfModelElement = this.map(field, application, subValueClass, domain);
      boolean _and = false;
      boolean _notEquals_1 = (!Objects.equal(mdfModelElement, null));
      if (!_notEquals_1) {
        _and = false;
      } else {
        String _name_2 = mdfModelElement.getName();
        boolean _isDuplicateProperty = this.isDuplicateProperty(subValueClass, _name_2);
        boolean _not = (!_isDuplicateProperty);
        _and = _not;
      }
      if (_and) {
        List _properties = subValueClass.getProperties();
        _properties.add(mdfModelElement);
        this.installProxies(field, mdfModelElement);
      }
      this.mapTranslation(mdfModelElement, field);
    } else {
      MdfPropertyImpl mdfModelElement_1 = this.map(field, application, multiValueClass, domain);
      boolean _and_1 = false;
      boolean _notEquals_2 = (!Objects.equal(mdfModelElement_1, null));
      if (!_notEquals_2) {
        _and_1 = false;
      } else {
        String _name_3 = mdfModelElement_1.getName();
        boolean _isDuplicateProperty_1 = this.isDuplicateProperty(multiValueClass, _name_3);
        boolean _not_1 = (!_isDuplicateProperty_1);
        _and_1 = _not_1;
      }
      if (_and_1) {
        List _properties_1 = multiValueClass.getProperties();
        _properties_1.add(mdfModelElement_1);
        this.installProxies(field, mdfModelElement_1);
      }
      this.mapTranslation(mdfModelElement_1, field);
    }
  }
  
  public MdfClassImpl mapMultiOrSubValueClassInternal(final String embeddedMultiOrSubValueClassName, final String embeddedMultiOrSubValuePropertyName, final MdfClassImpl mdfMainClass, final MdfDomainImpl mdfDomain) {
    final ArrayList<?> _cacheKey = CollectionLiterals.newArrayList(embeddedMultiOrSubValueClassName, embeddedMultiOrSubValuePropertyName, mdfMainClass, mdfDomain);
    final MdfClassImpl _result;
    synchronized (_createCache_mapMultiOrSubValueClassInternal) {
      if (_createCache_mapMultiOrSubValueClassInternal.containsKey(_cacheKey)) {
        return _createCache_mapMultiOrSubValueClassInternal.get(_cacheKey);
      }
      MdfClass _createMdfClass = MdfFactory.eINSTANCE.createMdfClass();
      _result = ((MdfClassImpl) _createMdfClass);
      _createCache_mapMultiOrSubValueClassInternal.put(_cacheKey, _result);
    }
    _init_mapMultiOrSubValueClassInternal(_result, embeddedMultiOrSubValueClassName, embeddedMultiOrSubValuePropertyName, mdfMainClass, mdfDomain);
    return _result;
  }
  
  private final HashMap<ArrayList<?>, MdfClassImpl> _createCache_mapMultiOrSubValueClassInternal = CollectionLiterals.newHashMap();
  
  private void _init_mapMultiOrSubValueClassInternal(final MdfClassImpl embeddedClass, final String embeddedMultiOrSubValueClassName, final String embeddedMultiOrSubValuePropertyName, final MdfClassImpl mdfMainClass, final MdfDomainImpl mdfDomain) {
    final MdfClassImpl embeddedClassImpl = ((MdfClassImpl) embeddedClass);
    embeddedClassImpl.setName(embeddedMultiOrSubValueClassName);
    boolean _isDuplicateProperty = this.isDuplicateProperty(mdfMainClass, embeddedMultiOrSubValuePropertyName);
    boolean _not = (!_isDuplicateProperty);
    if (_not) {
      MdfAssociation _createMdfAssociation = MdfFactory.eINSTANCE.createMdfAssociation();
      MdfAssociationImpl mdfAssociation = ((MdfAssociationImpl) _createMdfAssociation);
      mdfAssociation.setName(embeddedMultiOrSubValuePropertyName);
      mdfAssociation.setType(embeddedClass);
      mdfAssociation.setContainment(MdfContainment.BY_VALUE);
      mdfAssociation.setMultiplicity(MdfMultiplicity.MANY_LITERAL);
      List _properties = mdfMainClass.getProperties();
      _properties.add(mdfAssociation);
    }
    MdfClassImpl duplicateClazz = null;
    List _classes = mdfDomain.getClasses();
    for (final Object mdfClass : _classes) {
      String _name = ((MdfClassImpl) mdfClass).getName();
      String _name_1 = embeddedClass.getName();
      boolean _equals = _name.equals(_name_1);
      if (_equals) {
        duplicateClazz = ((MdfClassImpl) mdfClass);
      }
    }
    boolean _notEquals = (!Objects.equal(duplicateClazz, null));
    if (_notEquals) {
      List _classes_1 = mdfDomain.getClasses();
      _classes_1.remove(duplicateClazz);
    }
    List _classes_2 = mdfDomain.getClasses();
    _classes_2.add(embeddedClass);
  }
  
  public MdfClassImpl mapMultiOrSubValueClass(final String embeddedMultiOrSubValueClassName, final String embeddedMultiOrSubValuePropertyName, final MdfClassImpl mdfMainClass, final MdfDomainImpl mdfDomain) {
    return this.mapMultiOrSubValueClassInternal(embeddedMultiOrSubValueClassName, embeddedMultiOrSubValuePropertyName, mdfMainClass, mdfDomain);
  }
  
  public MdfPropertyImpl map(final ApplicationField applicationField, final ApplicationEntity application, final MdfClass mdfClass, final MdfDomainImpl mdfDomain) {
    try {
      MdfPropertyImpl _xblockexpression = null;
      {
        MdfEnumerationImpl enumeration = null;
        List<FieldValidValue> validValues = applicationField.getValidValue();
        MdfProperty property = null;
        boolean _and = false;
        int _size = validValues.size();
        boolean _greaterThan = (_size > 0);
        if (!_greaterThan) {
          _and = false;
        } else {
          ApplicationReference _refApplication = applicationField.getRefApplication();
          boolean _equals = Objects.equal(_refApplication, null);
          _and = _equals;
        }
        if (_and) {
          String _name = mdfClass.getName();
          String _plus = (_name + "__");
          String _name_1 = applicationField.getName();
          String _plus_1 = (_plus + _name_1);
          String enumName = this.toValidName(_plus_1);
          MdfEnumerationImpl _map = this.map(validValues, enumName, applicationField, application);
          enumeration = _map;
          MdfEnumerationImpl duplicateEnum = null;
          List _enumerations = mdfDomain.getEnumerations();
          for (final Object mdfEnum : _enumerations) {
            String _name_2 = ((MdfEnumerationImpl) mdfEnum).getName();
            boolean _equals_1 = _name_2.equals(enumName);
            if (_equals_1) {
              duplicateEnum = ((MdfEnumerationImpl) mdfEnum);
            }
          }
          boolean _notEquals = (!Objects.equal(duplicateEnum, null));
          if (_notEquals) {
            String _name_3 = mdfClass.getName();
            MdfClass _class = mdfDomain.getClass(_name_3);
            boolean _notEquals_1 = (!Objects.equal(_class, null));
            if (_notEquals_1) {
              String _name_4 = mdfClass.getName();
              MdfClass _class_1 = mdfDomain.getClass(_name_4);
              String _name_5 = applicationField.getName();
              MdfProperty _property = _class_1.getProperty(_name_5);
              property = _property;
            }
            List _values = duplicateEnum.getValues();
            _values.clear();
            List _values_1 = duplicateEnum.getValues();
            List _values_2 = enumeration.getValues();
            _values_1.addAll(_values_2);
            enumeration = duplicateEnum;
          } else {
            List _enumerations_1 = mdfDomain.getEnumerations();
            _enumerations_1.add(enumeration);
          }
        }
        MdfPropertyImpl _xifexpression = null;
        boolean _notEquals_2 = (!Objects.equal(property, null));
        if (_notEquals_2) {
          ((MdfAttributeImpl) property).setType(enumeration);
        } else {
          MdfPropertyImpl _xifexpression_1 = null;
          ApplicationReference _refApplication_1 = applicationField.getRefApplication();
          boolean _equals_2 = Objects.equal(_refApplication_1, null);
          if (_equals_2) {
            MdfAttributeImpl _xblockexpression_1 = null;
            {
              MdfAttribute mdfAttribute = MdfFactory.eINSTANCE.createMdfAttribute();
              MdfAttributeImpl mdfAttributeImpl = ((MdfAttributeImpl) mdfAttribute);
              this.setFieldCommon(mdfAttributeImpl, applicationField);
              boolean _notEquals_3 = (!Objects.equal(enumeration, null));
              if (_notEquals_3) {
                mdfAttributeImpl.setType(enumeration);
              } else {
                MdfPrimitiveImpl _mapType = this.mapType(applicationField);
                mdfAttributeImpl.setType(_mapType);
              }
              this.mapT24Name(mdfAttributeImpl, applicationField);
              this.mapGenOperation(mdfAttributeImpl, applicationField);
              this.setAnnotations(mdfAttributeImpl, applicationField);
              _xblockexpression_1 = mdfAttributeImpl;
            }
            _xifexpression_1 = _xblockexpression_1;
          } else {
            MdfAssociationImpl _xifexpression_2 = null;
            ApplicationReference _refApplication_2 = applicationField.getRefApplication();
            String _component = _refApplication_2.getComponent();
            boolean _notEquals_3 = (!Objects.equal(_component, null));
            if (_notEquals_3) {
              MdfAssociationImpl _xblockexpression_2 = null;
              {
                MdfAssociation mdfAssociation = MdfFactory.eINSTANCE.createMdfAssociation();
                MdfAssociationImpl mdfAssociationImpl = ((MdfAssociationImpl) mdfAssociation);
                this.setFieldCommon(mdfAssociationImpl, applicationField);
                this.mapT24Name(mdfAssociationImpl, applicationField);
                this.mapGenOperation(mdfAssociationImpl, applicationField);
                this.setAnnotations(mdfAssociationImpl, applicationField);
                _xblockexpression_2 = mdfAssociationImpl;
              }
              _xifexpression_2 = _xblockexpression_2;
            } else {
              String _nameT24 = applicationField.getNameT24();
              final String msg = (_nameT24 + 
                " has a <t24:ref-application> without a component, skipping it because otherwise all hell breaks loose in DS");
              this.throwMappingError(msg);
              T24MetaModel2MdfMapper.LOGGER.error(msg);
            }
            _xifexpression_1 = _xifexpression_2;
          }
          _xifexpression = _xifexpression_1;
        }
        _xblockexpression = _xifexpression;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  protected void _installProxies(final ApplicationField applicationField, final Object object) {
  }
  
  protected void _installProxies(final ApplicationField applicationField, final MdfAssociationImpl mdfAssociationImpl) {
    ApplicationReference appRef = applicationField.getRefApplication();
    String _name = appRef.getName();
    final String typeName = this.toValidName(_name);
    String _component = appRef.getComponent();
    String _plus = (_component + ":");
    String _plus_1 = (_plus + typeName);
    this.createAndSetProxyOrObject(mdfAssociationImpl, MdfPackage.Literals.MDF_ASSOCIATION__TYPE, _plus_1);
    MdfClass _basicGetType = mdfAssociationImpl.basicGetType();
    final MdfClass type = _basicGetType;
    boolean _matched = false;
    if (!_matched) {
      if (type instanceof MdfClassImpl) {
        _matched=true;
        ((MdfClassImpl)type).setName(typeName);
      }
    }
  }
  
  @SuppressWarnings("unchecked")
  protected void createAndSetProxyOrObject(final EObject obj, final EReference eRef, final String crossRefNode) {
    final EObject proxyOrObject = this.createProxy(obj, eRef, crossRefNode);
    boolean _isMany = eRef.isMany();
    if (_isMany) {
      Object _eGet = obj.eGet(eRef, false);
      ((InternalEList<EObject>) _eGet).addUnique(proxyOrObject);
    } else {
      obj.eSet(eRef, proxyOrObject);
    }
  }
  
  protected EObject createProxy(final EObject obj, final EReference eRef, final String crossRefString) {
    EObject _xblockexpression = null;
    {
      final Resource resource = obj.eResource();
      boolean _equals = Objects.equal(resource, null);
      if (_equals) {
        throw new IllegalStateException("object must be contained in a resource");
      }
      final URI uri = resource.getURI();
      String _encodeBrokenLink = this.domainURIEncoder.encodeBrokenLink(crossRefString);
      final URI encodedLink = uri.appendFragment(_encodeBrokenLink);
      EClass _eClass = obj.eClass();
      final EClass referenceType = this.ecoreGenericsUtil.getReferenceType(eRef, _eClass);
      final EClass instantiableType = this.instantiableSubTypes.get(referenceType);
      final EObject proxy = EcoreUtil.create(instantiableType);
      ((InternalEObject) proxy).eSetProxyURI(encodedLink);
      _xblockexpression = proxy;
    }
    return _xblockexpression;
  }
  
  public void setAnnotations(final MdfPropertyImpl property, final ApplicationField field) {
    String _typeModifiers = field.getTypeModifiers();
    T24Aspect.setTypeModifiers(property, _typeModifiers);
    String _mask = field.getMask();
    T24Aspect.setMask(property, _mask);
    String _onchangeBehaviour = field.getOnchangeBehaviour();
    T24Aspect.setOnchangeBehaviour(property, _onchangeBehaviour);
    MvSvExpansionAccess _mvSvExpansionAccess = field.getMvSvExpansionAccess();
    boolean _notEquals = (!Objects.equal(_mvSvExpansionAccess, null));
    if (_notEquals) {
      MvSvExpansionAccess _mvSvExpansionAccess_1 = field.getMvSvExpansionAccess();
      String _name = _mvSvExpansionAccess_1.name();
      T24Aspect.setMvSvExpansionAccess(property, _name);
    }
    Boolean _isIsTextarea = field.isIsTextarea();
    boolean _notEquals_1 = (!Objects.equal(_isIsTextarea, null));
    if (_notEquals_1) {
      Boolean _isIsTextarea_1 = field.isIsTextarea();
      T24Aspect.setTextArea(property, (_isIsTextarea_1).booleanValue());
    }
    Boolean _isIsImage = field.isIsImage();
    boolean _notEquals_2 = (!Objects.equal(_isIsImage, null));
    if (_notEquals_2) {
      Boolean _isIsImage_1 = field.isIsImage();
      T24Aspect.setImage(property, (_isIsImage_1).booleanValue());
    }
  }
  
  public void setFieldCommon(final MdfPropertyImpl property, final ApplicationField field) {
    String _name = field.getName();
    String _validName = this.toValidName(_name);
    property.setName(_validName);
    Boolean _isPrimaryKey = field.isPrimaryKey();
    property.setPrimaryKey((_isPrimaryKey).booleanValue());
    Boolean _isMandatory = field.isMandatory();
    property.setRequired((_isMandatory).booleanValue());
    String _documentation = field.getDocumentation();
    property.setDocumentation(_documentation);
    FieldAlignment _alignment = field.getAlignment();
    T24Aspect.setAlignment(property, _alignment);
    InputBehaviour _inputBehaviour = field.getInputBehaviour();
    T24Aspect.setInputBehaviour(property, _inputBehaviour);
    BigInteger _maxLength = field.getMaxLength();
    boolean _notEquals = (!Objects.equal(_maxLength, null));
    if (_notEquals) {
      BigInteger _maxLength_1 = field.getMaxLength();
      int _intValue = _maxLength_1.intValue();
      T24Aspect.setMaxLength(property, Integer.valueOf(_intValue));
    }
    Boolean _isMultiLanguage = field.isMultiLanguage();
    T24Aspect.setMultiLanguage(property, (_isMultiLanguage).booleanValue());
    Double _sysNumber = field.getSysNumber();
    boolean _notEquals_1 = (!Objects.equal(_sysNumber, null));
    if (_notEquals_1) {
      Double _sysNumber_1 = field.getSysNumber();
      T24Aspect.setSysNumber(property, _sysNumber_1);
    }
    boolean _isCore = field.isCore();
    T24Aspect.setCore(property, Boolean.valueOf(_isCore));
    String _businessType = field.getBusinessType();
    T24Aspect.setBusinessType(property, _businessType);
  }
  
  public void mapT24Name(final MdfPropertyImpl property, final ApplicationField field) {
    String _nameT24 = field.getNameT24();
    T24Aspect.setT24Name(property, _nameT24);
  }
  
  public void mapGenOperation(final MdfPropertyImpl property, final ApplicationField field) {
    String _genOperation = field.getGenOperation();
    T24Aspect.setGenOperation(property, _genOperation);
  }
  
  public MdfEnumerationImpl map(final List<FieldValidValue> validValueList, final String enumName, final ApplicationField field, final ApplicationEntity entity) {
    MdfEnumerationImpl _xblockexpression = null;
    {
      HashSet<String> alreadySeenNames = new HashSet<String>();
      MdfEnumeration mdfEnumeration = MdfFactory.eINSTANCE.createMdfEnumeration();
      MdfEnumerationImpl mdfEnumerationImpl = ((MdfEnumerationImpl) mdfEnumeration);
      mdfEnumerationImpl.setName(enumName);
      for (final FieldValidValue validValue : validValueList) {
        boolean _and = false;
        String _value = validValue.getValue();
        boolean _notEquals = (!Objects.equal(_value, null));
        if (!_notEquals) {
          _and = false;
        } else {
          String _value_1 = validValue.getValue();
          String _trim = _value_1.trim();
          int _length = _trim.length();
          boolean _greaterThan = (_length > 0);
          _and = _greaterThan;
        }
        if (_and) {
          String _value_2 = validValue.getValue();
          final String validName = this.toValidEnumValueName(_value_2);
          boolean _contains = alreadySeenNames.contains(validName);
          if (_contains) {
            String _name = entity.getName();
            String _plus = ("Application " + _name);
            String _plus_1 = (_plus + "\'s Field ");
            String _name_1 = field.getName();
            String _plus_2 = (_plus_1 + _name_1);
            String _plus_3 = (_plus_2 + " has duplicate valid value ");
            String _value_3 = validValue.getValue();
            String _plus_4 = (_plus_3 + _value_3);
            T24MetaModel2MdfMapper.LOGGER.warn(_plus_4);
          } else {
            MdfEnumValue mdfEnumValue = MdfFactory.eINSTANCE.createMdfEnumValue();
            MdfEnumValueImpl mdfenumValueImpl = ((MdfEnumValueImpl) mdfEnumValue);
            mdfenumValueImpl.setName(validName);
            alreadySeenNames.add(validName);
            String _value_4 = validValue.getValue();
            mdfenumValueImpl.setValue(_value_4);
            List _values = mdfEnumerationImpl.getValues();
            _values.add(mdfEnumValue);
          }
        }
      }
      MdfPrimitiveImpl _mapType = this.mapType(field);
      mdfEnumerationImpl.setType(_mapType);
      _xblockexpression = mdfEnumerationImpl;
    }
    return _xblockexpression;
  }
  
  public MdfPrimitiveImpl mapType(final ApplicationField field) {
    MdfPrimitiveImpl _xblockexpression = null;
    {
      final FieldType type = field.getType();
      MdfPrimitiveImpl _switchResult = null;
      if (type != null) {
        switch (type) {
          case S:
            _switchResult = PrimitivesDomain.STRING;
            break;
          case L:
            _switchResult = PrimitivesDomain.LONG_OBJ;
            break;
          case D:
            _switchResult = PrimitivesDomain.DATE;
            break;
          case B:
            _switchResult = PrimitivesDomain.STRING;
            break;
          case C:
            _switchResult = PrimitivesDomain.CHAR_OBJ;
            break;
          case M:
            _switchResult = PrimitivesDomain.DOUBLE_OBJ;
            break;
          case N:
            _switchResult = PrimitivesDomain.INTEGER_OBJ;
            break;
          case T:
            _switchResult = PrimitivesDomain.DATE_TIME;
            break;
          default:
            String _name = type.name();
            String _plus = ("FieldType cannot be mapped: " + _name);
            throw new IllegalArgumentException(_plus);
        }
      } else {
        String _name = type.name();
        String _plus = ("FieldType cannot be mapped: " + _name);
        throw new IllegalArgumentException(_plus);
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public String toValidEnumValueName(final String name) {
    String _xblockexpression = null;
    {
      String cleanName = name.trim();
      String _replace = cleanName.replace("-", "__");
      cleanName = _replace;
      _xblockexpression = this.toValidName(cleanName);
    }
    return _xblockexpression;
  }
  
  public String toValidName(final String name) {
    String _xblockexpression = null;
    {
      String cleanName = name.trim();
      String _replace = cleanName.replace(".", "_");
      cleanName = _replace;
      String _replace_1 = cleanName.replace("\'AND\'", "AND");
      cleanName = _replace_1;
      String _replace_2 = cleanName.replace("\"", "");
      cleanName = _replace_2;
      String _replace_3 = cleanName.replace("@", "");
      cleanName = _replace_3;
      String _replace_4 = cleanName.replace(" ", "_");
      cleanName = _replace_4;
      String _replace_5 = cleanName.replace("-", "_");
      cleanName = _replace_5;
      String _replace_6 = cleanName.replace(":", "_");
      cleanName = _replace_6;
      String _replace_7 = cleanName.replace("/", "_");
      cleanName = _replace_7;
      String _replace_8 = cleanName.replace(">", "_GT");
      cleanName = _replace_8;
      String _replace_9 = cleanName.replace("<", "_LT");
      cleanName = _replace_9;
      String _replace_10 = cleanName.replace(",", "COMMA");
      cleanName = _replace_10;
      String _replace_11 = cleanName.replace("*", "STAR");
      cleanName = _replace_11;
      String _replace_12 = cleanName.replace("=", "EQUALS");
      cleanName = _replace_12;
      String _replace_13 = cleanName.replace("~", "TILDE");
      cleanName = _replace_13;
      String _replace_14 = cleanName.replace("%", "PERCENT");
      cleanName = _replace_14;
      String _replace_15 = cleanName.replace("$", "_DOLLAR_");
      cleanName = _replace_15;
      String _replace_16 = cleanName.replace("&", "_AND_");
      cleanName = _replace_16;
      String _replace_17 = cleanName.replace("#", "NO");
      cleanName = _replace_17;
      String _replace_18 = cleanName.replace("\'", "_");
      cleanName = _replace_18;
      String _replace_19 = cleanName.replace(":", "_");
      cleanName = _replace_19;
      String _replace_20 = cleanName.replace(";", "_");
      cleanName = _replace_20;
      String _replace_21 = cleanName.replace("!", "_");
      cleanName = _replace_21;
      String _replace_22 = cleanName.replace("+", "_");
      cleanName = _replace_22;
      String _replace_23 = cleanName.replace("§", "_");
      cleanName = _replace_23;
      String _replace_24 = cleanName.replace("£", "_");
      cleanName = _replace_24;
      String _replace_25 = cleanName.replace("¦", "_");
      cleanName = _replace_25;
      String _replace_26 = cleanName.replace("|", "_");
      cleanName = _replace_26;
      String _replace_27 = cleanName.replace("\\", "_");
      cleanName = _replace_27;
      String _replace_28 = cleanName.replace("(", "_");
      cleanName = _replace_28;
      String _replace_29 = cleanName.replace(")", "_");
      cleanName = _replace_29;
      String _replace_30 = cleanName.replace("[", "_");
      cleanName = _replace_30;
      String _replace_31 = cleanName.replace("]", "_");
      cleanName = _replace_31;
      String _replace_32 = cleanName.replace("{", "_");
      cleanName = _replace_32;
      String _replace_33 = cleanName.replace("}", "_");
      cleanName = _replace_33;
      _xblockexpression = cleanName;
    }
    return _xblockexpression;
  }
  
  public void throwMappingError(final String message) throws MappingException {
    throw new MappingException(message);
  }
  
  public void mapTranslation(final MdfModelElement modelElement, final Object xmlModel) {
    ITranslationManager translationManger = TranslationCore.getTranslationManager(this.project);
    ITranslation mdfTranslation = translationManger.getTranslation(modelElement);
    if ((xmlModel instanceof ApplicationEntity)) {
      ApplicationEntity applicationEntity = ((ApplicationEntity) xmlModel);
      List<Translation> _header1 = applicationEntity.getHeader1();
      this.setTranslationTex(_header1, ITranslationKind.HEADER1, mdfTranslation);
      List<Translation> _header2 = applicationEntity.getHeader2();
      this.setTranslationTex(_header2, ITranslationKind.HEADER2, mdfTranslation);
    }
    if ((xmlModel instanceof ApplicationField)) {
      ApplicationField applicationField = ((ApplicationField) xmlModel);
      List<Translation> _label = applicationField.getLabel();
      this.setTranslationTex(_label, ITranslationKind.NAME, mdfTranslation);
      List<Translation> _tooltip = applicationField.getTooltip();
      this.setTranslationTex(_tooltip, ITranslationKind.TEXT, mdfTranslation);
    }
  }
  
  public Locale toLocale(final String local) {
    return LocaleUtils.toLocale(local);
  }
  
  public void setTranslationTex(final List<Translation> translationList, final ITranslationKind kind, final ITranslation mdfTranslation) {
    try {
      for (final Translation translation : translationList) {
        {
          String localeStr = translation.getLocale();
          String _translation = translation.getTranslation();
          String translationStr = StringUtils.arrangeWhiteSpaces(_translation);
          Locale _locale = this.toLocale(localeStr);
          mdfTranslation.setText(kind, _locale, translationStr);
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void setLocalRefAllowed(final MdfClassImpl mdfclass, final boolean refAllowed) {
    T24Aspect.setLocalRefAllowed(mdfclass, refAllowed);
  }
  
  public boolean isDuplicateProperty(final MdfClassImpl mdfclass, final String propertyName) {
    boolean _xblockexpression = false;
    {
      boolean duplicate = false;
      MdfProperty _property = mdfclass.getProperty(propertyName);
      boolean _notEquals = (!Objects.equal(_property, null));
      if (_notEquals) {
        duplicate = true;
        String _name = mdfclass.getName();
        String _plus = ((("Property " + propertyName) + " with the same name already existing in ") + _name);
        T24MetaModel2MdfMapper.LOGGER.warn(_plus);
      }
      _xblockexpression = duplicate;
    }
    return _xblockexpression;
  }
  
  public void installProxies(final ApplicationField applicationField, final Object mdfAssociationImpl) {
    if (mdfAssociationImpl instanceof MdfAssociationImpl) {
      _installProxies(applicationField, (MdfAssociationImpl)mdfAssociationImpl);
      return;
    } else if (mdfAssociationImpl != null) {
      _installProxies(applicationField, mdfAssociationImpl);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(applicationField, mdfAssociationImpl).toString());
    }
  }
}
