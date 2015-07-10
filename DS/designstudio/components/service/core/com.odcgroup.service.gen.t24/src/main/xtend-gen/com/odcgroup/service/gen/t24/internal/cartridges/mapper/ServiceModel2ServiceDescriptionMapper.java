package com.odcgroup.service.gen.t24.internal.cartridges.mapper;

import com.google.common.base.Objects;
import com.odcgroup.mdf.ecore.MdfPrimitiveImpl;
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
import com.odcgroup.service.model.component.InOutType;
import com.odcgroup.service.model.component.Method;
import com.odcgroup.service.model.component.Multiplicity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
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
    throw new Error("Unresolved compilation problems:"
      + "\nMdfClass cannot be resolved to a type."
      + "\nMdfClass cannot be resolved to a type."
      + "\nThe method domains is undefined for the type ServiceModel2ServiceDescriptionMapper"
      + "\nclasses cannot be resolved"
      + "\nmapClassDefDescriptor cannot be resolved"
      + "\nmapAssociations cannot be resolved");
  }
  
  public void mapAssociations(final /* MdfClass */Object klazz, final ServiceDescriptor descriptor) {
    throw new Error("Unresolved compilation problems:"
      + "\nMdfAssociation cannot be resolved to a type."
      + "\nMdfAssociation cannot be resolved to a type."
      + "\nMdfClass cannot be resolved to a type."
      + "\nproperties cannot be resolved"
      + "\ntype cannot be resolved"
      + "\nmapClassDefDescriptor cannot be resolved"
      + "\nmapAssociations cannot be resolved");
  }
  
  public ClassDefDescriptor mapClassDefDescriptor(final /* MdfClass */Object klassObj) {
    throw new Error("Unresolved compilation problems:"
      + "\nMdfProperty cannot be resolved to a type."
      + "\nMdfProperty cannot be resolved to a type."
      + "\nproperties cannot be resolved"
      + "\nmapAttributeDescriptor cannot be resolved"
      + "\nname cannot be resolved"
      + "\nname cannot be resolved");
  }
  
  public AttributeDescriptor mapAttributeDescriptor(final /* MdfProperty */Object property) {
    throw new Error("Unresolved compilation problems:"
      + "\nmultiplicity cannot be resolved"
      + "\ntype cannot be resolved"
      + "\nname cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nname cannot be resolved");
  }
  
  public AttributeDescriptor mapAttributeDescripor(final /* MdfProperty */Object property) {
    throw new Error("Unresolved compilation problems:"
      + "\nmultiplicity cannot be resolved"
      + "\ntype cannot be resolved"
      + "\nname cannot be resolved"
      + "\nname cannot be resolved");
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
    throw new Error("Unresolved compilation problems:"
      + "\nThe method type is undefined for the type ServiceModel2ServiceDescriptionMapper"
      + "\nThe method type is undefined for the type ServiceModel2ServiceDescriptionMapper"
      + "\nname cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nmapComplexity cannot be resolved");
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
  
  public Complexity mapComplexity(final /* MdfEntity */Object entity) {
    Complexity _xifexpression = null;
    if ((entity instanceof MdfPrimitiveImpl)) {
      _xifexpression = Complexity.PRIMITIVE;
    } else {
      _xifexpression = Complexity.COMPLEX;
    }
    return _xifexpression;
  }
  
  public AttributeDescriptor mapAttributeDescriptor(final Argument entity) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method type is undefined for the type ServiceModel2ServiceDescriptionMapper"
      + "\nname cannot be resolved"
      + "\nequals cannot be resolved");
  }
  
  public AttributeDescriptor mapAttributeDescriptor(final /* MdfAttribute */Object entity) {
    throw new Error("Unresolved compilation problems:"
      + "\nmultiplicity cannot be resolved"
      + "\ntype cannot be resolved"
      + "\nname cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nname cannot be resolved");
  }
}
