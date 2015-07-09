package com.odcgroup.service.gen.t24.internal.cartridges.mapper;

import com.google.common.base.Objects;
import com.odcgroup.mdf.ecore.MdfPrimitiveImpl;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.service.gen.t24.internal.data.AttributeDescriptor;
import com.odcgroup.service.gen.t24.internal.data.Cardinality;
import com.odcgroup.service.gen.t24.internal.data.ClassDefDescriptor;
import com.odcgroup.service.gen.t24.internal.data.Complexity;
import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ParamDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.model.component.AccessSpecifier;
import com.odcgroup.service.model.component.Argument;
import com.odcgroup.service.model.component.Component;
import com.odcgroup.service.model.component.Content;
import com.odcgroup.service.model.component.InOutType;
import com.odcgroup.service.model.component.Method;
import com.odcgroup.service.model.component.MethodAnnotation;
import com.odcgroup.service.model.component.Multiplicity;
import com.odcgroup.service.model.component.T24MethodStereotype;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

@SuppressWarnings("all")
public class ServiceModel2ServiceDescriptionMapper {
  public ServiceDescriptor map(final Component component) {
    final ArrayList<?> _cacheKey = CollectionLiterals.newArrayList(component);
    final ServiceDescriptor _result;
    synchronized (_createCache_map) {
      if (_createCache_map.containsKey(_cacheKey)) {
        return _createCache_map.get(_cacheKey);
      }
      String _name = component.getName();
      ArrayList<OperationDescriptor> _newArrayList = CollectionLiterals.<OperationDescriptor>newArrayList();
      ArrayList<ClassDefDescriptor> _newArrayList_1 = CollectionLiterals.<ClassDefDescriptor>newArrayList();
      String _metamodelVersion = component.getMetamodelVersion();
      ServiceDescriptor _serviceDescriptor = new ServiceDescriptor(_name, _newArrayList, _newArrayList_1, _metamodelVersion);
      _result = _serviceDescriptor;
      _createCache_map.put(_cacheKey, _result);
    }
    _init_map(_result, component);
    return _result;
  }

  private final HashMap<ArrayList<?>, ServiceDescriptor> _createCache_map = CollectionLiterals.newHashMap();

  private void _init_map(final ServiceDescriptor sDescriptor, final Component component) {
    EList<Content> _content = component.getContent();
    for (final Content tempContent : _content) {
      EList<Method> _method = tempContent.getMethod();
      for (final Method operation : _method) {
        {
          OperationDescriptor oDescriptor = this.mapOperation(operation);
          EList<MethodAnnotation> _annotations = operation.getAnnotations();
          for (final MethodAnnotation annotation : _annotations) {
            T24MethodStereotype _name = annotation.getName();
            oDescriptor.addStereotype(_name);
          }
          List<OperationDescriptor> _operations = sDescriptor.getOperations();
          _operations.add(oDescriptor);
          Resource _eResource = component.eResource();
          IOfsProject ofsProject = OfsResourceHelper.getOfsProject(_eResource);
          DomainRepository repo = DomainRepository.getInstance(ofsProject);
          Collection<MdfDomain> allDomains = repo.getDomains();
          for (final MdfDomain domain : allDomains) {
            {
              List classes = domain.getClasses();
              for (final Object klazz : classes) {
                {
                  ClassDefDescriptor defDecs = this.mapClassDefDescriptor(((MdfClass) klazz));
                  List<ClassDefDescriptor> _classDefDescriptors = sDescriptor.getClassDefDescriptors();
                  boolean _contains = _classDefDescriptors.contains(defDecs);
                  boolean _not = (!_contains);
                  if (_not) {
                    List<ClassDefDescriptor> _classDefDescriptors_1 = sDescriptor.getClassDefDescriptors();
                    _classDefDescriptors_1.add(defDecs);
                  }
                  this.mapAssociations(((MdfClass) klazz), sDescriptor);
                }
              }
            }
          }
        }
      }
    }
  }

  public void mapAssociations(final MdfClass klazz, final ServiceDescriptor descriptor) {
    List _properties = klazz.getProperties();
    for (final Object property : _properties) {
      if ((property instanceof MdfAssociation)) {
        MdfEntity entity = ((MdfAssociation) property).getType();
        MdfClass klazz1 = ((MdfClass) entity);
        ClassDefDescriptor assocDesc1 = this.mapClassDefDescriptor(klazz1);
        List<ClassDefDescriptor> _classDefDescriptors = descriptor.getClassDefDescriptors();
        boolean _contains = _classDefDescriptors.contains(assocDesc1);
        boolean _not = (!_contains);
        if (_not) {
          List<ClassDefDescriptor> _classDefDescriptors_1 = descriptor.getClassDefDescriptors();
          _classDefDescriptors_1.add(assocDesc1);
          this.mapAssociations(klazz1, descriptor);
        }
      }
    }
  }

  public ClassDefDescriptor mapClassDefDescriptor(final MdfClass klassObj) {
    ClassDefDescriptor _xblockexpression = null;
    {
      final List<AttributeDescriptor> attrList = CollectionLiterals.<AttributeDescriptor>newArrayList();
      List _properties = klassObj.getProperties();
      for (final Object attribute : _properties) {
        if ((attribute instanceof MdfProperty)) {
          AttributeDescriptor _mapAttributeDescriptor = this.mapAttributeDescriptor(((MdfProperty) attribute));
          attrList.add(_mapAttributeDescriptor);
        }
      }
      String _name = klassObj.getName();
      String _name_1 = klassObj.getName();
      _xblockexpression = new ClassDefDescriptor(_name, _name_1, attrList);
    }
    return _xblockexpression;
  }

  public AttributeDescriptor mapAttributeDescriptor(final MdfProperty property) {
    AttributeDescriptor _xblockexpression = null;
    {
      int _multiplicity = property.getMultiplicity();
      Multiplicity _get = Multiplicity.get(_multiplicity);
      Cardinality card = this.mapCardinality(_get);
      MdfEntity _type = property.getType();
      String typeName = _type.getName();
      boolean _equals = typeName.equals("string");
      if (_equals) {
        typeName = "String";
      }
      String _name = property.getName();
      _xblockexpression = new AttributeDescriptor(_name, typeName, card);
    }
    return _xblockexpression;
  }

  public AttributeDescriptor mapAttributeDescripor(final MdfProperty property) {
    AttributeDescriptor _xblockexpression = null;
    {
      int _multiplicity = property.getMultiplicity();
      Multiplicity _get = Multiplicity.get(_multiplicity);
      Cardinality card = this.mapCardinality(_get);
      MdfEntity _type = property.getType();
      String typeName = _type.getName();
      String _name = property.getName();
      _xblockexpression = new AttributeDescriptor(_name, typeName, card);
    }
    return _xblockexpression;
  }

  public OperationDescriptor mapOperation(final Method operation) {
    OperationDescriptor _xblockexpression = null;
    {
      EObject _eContainer = operation.eContainer();
      EObject _eContainer_1 = _eContainer.eContainer();
      final String serviceName = ((Component) _eContainer_1).getName();
      final String name = operation.getName();
      final List<ParamDescriptor> paramDescList = CollectionLiterals.<ParamDescriptor>newArrayList();
      EList<Argument> _arguments = operation.getArguments();
      for (final Argument argument : _arguments) {
        ParamDescriptor _mapArgument = this.mapArgument(argument);
        paramDescList.add(_mapArgument);
      }
      final AccessSpecifier scope = operation.getAccessSpecifier();
      final ParamDescriptor retParamDesc = new ParamDescriptor("argName", "void", null, Direction.OUT, null, true, null);
      _xblockexpression = new OperationDescriptor(serviceName, name, paramDescList, retParamDesc, scope);
    }
    return _xblockexpression;
  }

  public ParamDescriptor mapArgument(final Argument argument) {
    ParamDescriptor _xblockexpression = null;
    {
      final String argName = argument.getName();
      MdfEntity _type = argument.getType();
      String typeName = _type.getName();
      Multiplicity _multiplicity = argument.getMultiplicity();
      final Cardinality cardinality = this.mapCardinality(_multiplicity);
      boolean _equals = typeName.equals("string");
      if (_equals) {
        typeName = "String";
      }
      InOutType _inout = argument.getInout();
      final Direction direction = this.mapDirection(_inout);
      MdfEntity _type_1 = argument.getType();
      final Complexity complexity = this.mapComplexity(_type_1);
      boolean _or = false;
      Multiplicity _multiplicity_1 = argument.getMultiplicity();
      boolean _equals_1 = Objects.equal(_multiplicity_1, Multiplicity.MANDATORY);
      if (_equals_1) {
        _or = true;
      } else {
        Multiplicity _multiplicity_2 = argument.getMultiplicity();
        boolean _equals_2 = Objects.equal(_multiplicity_2, Multiplicity.ONETOMANY);
        _or = _equals_2;
      }
      final boolean mandatory = _or;
      final List<AttributeDescriptor> attrList = CollectionLiterals.<AttributeDescriptor>newArrayList();
      if ((!(argument instanceof MdfPrimitiveImpl))) {
        AttributeDescriptor attrDesc = this.mapAttributeDescriptor(argument);
        boolean _contains = attrList.contains(attrDesc);
        boolean _not = (!_contains);
        if (_not) {
          attrList.add(attrDesc);
        }
      }
      _xblockexpression = new ParamDescriptor(argName, typeName, cardinality, direction, complexity, mandatory, attrList);
    }
    return _xblockexpression;
  }

  public Cardinality mapCardinality(final Multiplicity multiplicityabc) {
    Cardinality _xifexpression = null;
    boolean _equals = Objects.equal(multiplicityabc, Multiplicity.MANY);
    if (_equals) {
      _xifexpression = Cardinality.MULTIPLE;
    } else {
      Cardinality _xifexpression_1 = null;
      boolean _equals_1 = Objects.equal(multiplicityabc, Multiplicity.ONETOMANY);
      if (_equals_1) {
        _xifexpression_1 = Cardinality.MULTIPLE;
      } else {
        Cardinality _xifexpression_2 = null;
        boolean _equals_2 = Objects.equal(multiplicityabc, Multiplicity.OPTIONAL);
        if (_equals_2) {
          _xifexpression_2 = Cardinality.SINGLE;
        } else {
          Cardinality _xifexpression_3 = null;
          boolean _equals_3 = Objects.equal(multiplicityabc, Multiplicity.MANDATORY);
          if (_equals_3) {
            _xifexpression_3 = Cardinality.SINGLE;
          }
          _xifexpression_2 = _xifexpression_3;
        }
        _xifexpression_1 = _xifexpression_2;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }

  public Direction mapDirection(final InOutType type) {
    Direction _xifexpression = null;
    boolean _equals = Objects.equal(type, InOutType.IN);
    if (_equals) {
      _xifexpression = Direction.IN;
    } else {
      Direction _xifexpression_1 = null;
      boolean _equals_1 = Objects.equal(type, InOutType.OUT);
      if (_equals_1) {
        _xifexpression_1 = Direction.OUT;
      } else {
        Direction _xifexpression_2 = null;
        boolean _equals_2 = Objects.equal(type, InOutType.INOUT);
        if (_equals_2) {
          _xifexpression_2 = Direction.INOUT;
        }
        _xifexpression_1 = _xifexpression_2;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }

  public Complexity mapComplexity(final MdfEntity entity) {
    Complexity _xifexpression = null;
    if ((entity instanceof MdfPrimitiveImpl)) {
      _xifexpression = Complexity.PRIMITIVE;
    } else {
      _xifexpression = Complexity.COMPLEX;
    }
    return _xifexpression;
  }

  public AttributeDescriptor mapAttributeDescriptor(final Argument entity) {
    AttributeDescriptor _xblockexpression = null;
    {
      Multiplicity _multiplicity = entity.getMultiplicity();
      int _value = _multiplicity.getValue();
      Multiplicity _get = Multiplicity.get(_value);
      Cardinality card = this.mapCardinality(_get);
      MdfEntity _type = entity.getType();
      String typeName = _type.getName();
      boolean _equals = typeName.equals("string");
      if (_equals) {
        typeName = "String";
      }
      String _name = entity.getName();
      _xblockexpression = new AttributeDescriptor(_name, typeName, card);
    }
    return _xblockexpression;
  }

  public AttributeDescriptor mapAttributeDescriptor(final MdfAttribute entity) {
    AttributeDescriptor _xblockexpression = null;
    {
      int _multiplicity = entity.getMultiplicity();
      Multiplicity _get = Multiplicity.get(_multiplicity);
      Cardinality card = this.mapCardinality(_get);
      MdfEntity _type = entity.getType();
      String typeName = _type.getName();
      boolean _equals = typeName.equals("string");
      if (_equals) {
        typeName = "String";
      }
      String _name = entity.getName();
      _xblockexpression = new AttributeDescriptor(_name, typeName, card);
    }
    return _xblockexpression;
  }
}
