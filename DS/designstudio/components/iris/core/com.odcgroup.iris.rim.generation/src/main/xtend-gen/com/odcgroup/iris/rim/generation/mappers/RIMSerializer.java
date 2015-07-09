package com.odcgroup.iris.rim.generation.mappers;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.temenos.interaction.rimdsl.rim.BasePath;
import com.temenos.interaction.rimdsl.rim.Command;
import com.temenos.interaction.rimdsl.rim.CommandProperty;
import com.temenos.interaction.rimdsl.rim.DomainDeclaration;
import com.temenos.interaction.rimdsl.rim.DomainModel;
import com.temenos.interaction.rimdsl.rim.Entity;
import com.temenos.interaction.rimdsl.rim.Event;
import com.temenos.interaction.rimdsl.rim.Expression;
import com.temenos.interaction.rimdsl.rim.Function;
import com.temenos.interaction.rimdsl.rim.ImplRef;
import com.temenos.interaction.rimdsl.rim.LinkId;
import com.temenos.interaction.rimdsl.rim.MethodRef;
import com.temenos.interaction.rimdsl.rim.NotFoundFunction;
import com.temenos.interaction.rimdsl.rim.OKFunction;
import com.temenos.interaction.rimdsl.rim.Path;
import com.temenos.interaction.rimdsl.rim.Ref;
import com.temenos.interaction.rimdsl.rim.Relation;
import com.temenos.interaction.rimdsl.rim.RelationConstant;
import com.temenos.interaction.rimdsl.rim.RelationRef;
import com.temenos.interaction.rimdsl.rim.ResourceCommand;
import com.temenos.interaction.rimdsl.rim.ResourceInteractionModel;
import com.temenos.interaction.rimdsl.rim.ResourceType;
import com.temenos.interaction.rimdsl.rim.State;
import com.temenos.interaction.rimdsl.rim.Title;
import com.temenos.interaction.rimdsl.rim.Transition;
import com.temenos.interaction.rimdsl.rim.TransitionAuto;
import com.temenos.interaction.rimdsl.rim.TransitionEmbedded;
import com.temenos.interaction.rimdsl.rim.TransitionForEach;
import com.temenos.interaction.rimdsl.rim.TransitionRef;
import com.temenos.interaction.rimdsl.rim.TransitionSpec;
import com.temenos.interaction.rimdsl.rim.UriLink;
import com.temenos.interaction.rimdsl.rim.UriLinkage;
import com.temenos.interaction.rimdsl.rim.Use;
import com.temenos.interaction.rimdsl.scoping.RIMDslQualifiedNameProvider;
import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class RIMSerializer {
  @Extension
  private RIMDslQualifiedNameProvider _rIMDslQualifiedNameProvider = new RIMDslQualifiedNameProvider();
  
  public void generate(final Resource resource, final IFileSystemAccess fsa, final String foldersAndFileName) {
    try {
      boolean _equals = Objects.equal(resource, null);
      if (_equals) {
        throw new RuntimeException("Generator called with null resource");
      }
      EList<EObject> _contents = resource.getContents();
      List<EObject> _list = IterableExtensions.<EObject>toList(_contents);
      Iterable<DomainModel> models = Iterables.<DomainModel>filter(_list, DomainModel.class);
      int _size = IterableExtensions.size(models);
      boolean _notEquals = (_size != 1);
      if (_notEquals) {
        throw new AssertionError("Expecting exactly one DomainModel");
      }
      Iterator<DomainModel> _iterator = models.iterator();
      DomainModel _next = _iterator.next();
      CharSequence _generate = this.generate(_next);
      fsa.generateFile(foldersAndFileName, _generate);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public CharSequence generate(final DomainModel model) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Ref> _rims = model.getRims();
      List<Ref> _list = IterableExtensions.<Ref>toList(_rims);
      Iterable<Ref> _filter = Iterables.<Ref>filter(_list, Ref.class);
      for(final Ref ref : _filter) {
        CharSequence _refDispatch = this.refDispatch(ref);
        _builder.append(_refDispatch, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence refDispatch(final Ref ref) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((ref instanceof DomainDeclaration)) {
        CharSequence _domain = this.domain(((DomainDeclaration) ref));
        _builder.append(_domain, "");
        _builder.newLineIfNotEmpty();
      } else {
        if ((ref instanceof ResourceInteractionModel)) {
          CharSequence _rim = this.rim(((ResourceInteractionModel) ref));
          _builder.append(_rim, "");
          _builder.newLineIfNotEmpty();
        } else {
          if ((ref instanceof Use)) {
            CharSequence _use = this.use(((Use) ref));
            _builder.append(_use, "");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("// invalid object seen");
            _builder.newLine();
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence domain(final DomainDeclaration domain) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("domain ");
    QualifiedName _fullyQualifiedName = this._rIMDslQualifiedNameProvider.getFullyQualifiedName(domain);
    _builder.append(_fullyQualifiedName, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    {
      EList<Ref> _rims = domain.getRims();
      for(final Ref ref : _rims) {
        _builder.append("\t");
        Object _refDispatch = this.refDispatch(ref);
        _builder.append(_refDispatch, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence rim(final ResourceInteractionModel rim) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("rim ");
    String _name = rim.getName();
    _builder.append(_name, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    {
      EList<Event> _events = rim.getEvents();
      for(final Event event : _events) {
        _builder.append("\t");
        CharSequence _event = this.event(event);
        _builder.append(_event, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Command> _commands = rim.getCommands();
      for(final Command command : _commands) {
        _builder.append("\t");
        CharSequence _command = this.command(command);
        _builder.append(_command, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      BasePath _basepath = rim.getBasepath();
      boolean _notEquals = (!Objects.equal(_basepath, null));
      if (_notEquals) {
        _builder.append("\t");
        _builder.append("basepath: \"");
        BasePath _basepath_1 = rim.getBasepath();
        String _name_1 = _basepath_1.getName();
        _builder.append(_name_1, "\t");
        _builder.append("\"");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      EList<State> _states = rim.getStates();
      for(final State state : _states) {
        _builder.append("\t");
        CharSequence _state = this.state(state);
        _builder.append(_state, "\t");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence use(final Use use) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("use ");
    String _importedNamespace = use.getImportedNamespace();
    _builder.append(_importedNamespace, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence event(final Event event) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("event ");
    String _name = event.getName();
    _builder.append(_name, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence command(final Command command) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("command ");
    String _name = command.getName();
    _builder.append(_name, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence state(final State state) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("resource ");
    String _name = state.getName();
    _builder.append(_name, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    {
      ResourceType _type = state.getType();
      boolean _isIsItem = _type.isIsItem();
      if (_isIsItem) {
        _builder.append("\t");
        _builder.append("type: item");
        _builder.newLine();
      } else {
        ResourceType _type_1 = state.getType();
        boolean _isIsCollection = _type_1.isIsCollection();
        if (_isIsCollection) {
          _builder.append("\t");
          _builder.append("type: collection");
          _builder.newLine();
        } else {
          _builder.append("\t");
          _builder.append("// invalid type object seen");
          _builder.newLine();
        }
      }
    }
    _builder.append("\t");
    _builder.append("entity: ");
    Entity _entity = state.getEntity();
    String _name_1 = _entity.getName();
    _builder.append(_name_1, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    ImplRef _impl = state.getImpl();
    CharSequence _impl_1 = this.impl(_impl);
    _builder.append(_impl_1, "\t");
    _builder.newLineIfNotEmpty();
    {
      boolean _and = false;
      EList<RelationRef> _relations = state.getRelations();
      boolean _notEquals = (!Objects.equal(_relations, null));
      if (!_notEquals) {
        _and = false;
      } else {
        EList<RelationRef> _relations_1 = state.getRelations();
        int _size = _relations_1.size();
        boolean _greaterThan = (_size > 0);
        _and = _greaterThan;
      }
      if (_and) {
        _builder.append("\t");
        EList<RelationRef> _relations_2 = state.getRelations();
        CharSequence _relations_3 = this.relations(_relations_2);
        _builder.append(_relations_3, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Path _path = state.getPath();
      boolean _notEquals_1 = (!Objects.equal(_path, null));
      if (_notEquals_1) {
        _builder.append("\t");
        _builder.append("path: \"");
        Path _path_1 = state.getPath();
        String _name_2 = _path_1.getName();
        _builder.append(_name_2, "\t");
        _builder.append("\"");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<TransitionRef> _transitions = state.getTransitions();
      for(final TransitionRef transition : _transitions) {
        _builder.append("\t");
        CharSequence _transitionRef = this.transitionRef(transition);
        _builder.append(_transitionRef, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      State _errorState = state.getErrorState();
      boolean _notEquals_2 = (!Objects.equal(_errorState, null));
      if (_notEquals_2) {
        _builder.append("\t");
        _builder.append("onerror --> ");
        State _errorState_1 = state.getErrorState();
        QualifiedName _fullyQualifiedName = this._rIMDslQualifiedNameProvider.getFullyQualifiedName(_errorState_1);
        _builder.append(_fullyQualifiedName, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence impl(final ImplRef impl) {
    StringConcatenation _builder = new StringConcatenation();
    {
      ResourceCommand _view = impl.getView();
      boolean _notEquals = (!Objects.equal(_view, null));
      if (_notEquals) {
        _builder.append("view: ");
        ResourceCommand _view_1 = impl.getView();
        Command _command = _view_1.getCommand();
        String _name = _command.getName();
        _builder.append(_name, "");
        {
          boolean _and = false;
          ResourceCommand _view_2 = impl.getView();
          EList<CommandProperty> _properties = _view_2.getProperties();
          boolean _notEquals_1 = (!Objects.equal(_properties, null));
          if (!_notEquals_1) {
            _and = false;
          } else {
            ResourceCommand _view_3 = impl.getView();
            EList<CommandProperty> _properties_1 = _view_3.getProperties();
            int _size = _properties_1.size();
            boolean _greaterThan = (_size > 0);
            _and = _greaterThan;
          }
          if (_and) {
            _builder.append(" {");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            ResourceCommand _view_4 = impl.getView();
            EList<CommandProperty> _properties_2 = _view_4.getProperties();
            CharSequence _commandProperties = this.commandProperties(_properties_2);
            _builder.append(_commandProperties, "\t");
            _builder.newLineIfNotEmpty();
            _builder.append("}");
          }
        }
        _builder.newLineIfNotEmpty();
      } else {
        EList<ResourceCommand> _actions = impl.getActions();
        boolean _notEquals_2 = (!Objects.equal(_actions, null));
        if (_notEquals_2) {
          _builder.append("commands [ ");
          {
            EList<MethodRef> _methods = impl.getMethods();
            boolean _hasElements = false;
            for(final MethodRef method : _methods) {
              if (!_hasElements) {
                _hasElements = true;
              } else {
                _builder.appendImmediate(", ", "");
              }
              Event _event = method.getEvent();
              String _name_1 = _event.getName();
              _builder.append(_name_1, "");
              _builder.append(" : ");
              ResourceCommand _command_1 = method.getCommand();
              Command _command_2 = _command_1.getCommand();
              String _name_2 = _command_2.getName();
              _builder.append(_name_2, "");
              {
                boolean _and_1 = false;
                ResourceCommand _command_3 = method.getCommand();
                EList<CommandProperty> _properties_3 = _command_3.getProperties();
                boolean _notEquals_3 = (!Objects.equal(_properties_3, null));
                if (!_notEquals_3) {
                  _and_1 = false;
                } else {
                  ResourceCommand _command_4 = method.getCommand();
                  EList<CommandProperty> _properties_4 = _command_4.getProperties();
                  int _size_1 = _properties_4.size();
                  boolean _greaterThan_1 = (_size_1 > 0);
                  _and_1 = _greaterThan_1;
                }
                if (_and_1) {
                  _builder.append(" {");
                  _builder.newLineIfNotEmpty();
                  _builder.append("\t");
                  ResourceCommand _command_5 = method.getCommand();
                  EList<CommandProperty> _properties_5 = _command_5.getProperties();
                  CharSequence _commandProperties_1 = this.commandProperties(_properties_5);
                  _builder.append(_commandProperties_1, "\t");
                  _builder.newLineIfNotEmpty();
                  _builder.append("\t");
                  _builder.append("}");
                }
              }
            }
          }
          _builder.append(" ]");
          _builder.newLineIfNotEmpty();
        } else {
          _builder.append("// invalid impl object seen");
          _builder.newLine();
        }
      }
    }
    return _builder;
  }
  
  public CharSequence commandProperties(final EList<CommandProperty> properties) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("properties [ ");
    {
      boolean _hasElements = false;
      for(final CommandProperty prop : properties) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "");
        }
        String _name = prop.getName();
        _builder.append(_name, "");
        _builder.append(" = \"");
        String _value = prop.getValue();
        _builder.append(_value, "");
      }
    }
    _builder.append("\" ]");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence transitionRef(final TransitionRef ref) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((ref instanceof Transition)) {
        CharSequence _transition = this.transition(((Transition) ref));
        _builder.append(_transition, "");
        _builder.newLineIfNotEmpty();
      } else {
        if ((ref instanceof TransitionForEach)) {
          CharSequence _transition_1 = this.transition(((TransitionForEach) ref));
          _builder.append(_transition_1, "");
          _builder.newLineIfNotEmpty();
        } else {
          if ((ref instanceof TransitionAuto)) {
            CharSequence _transition_2 = this.transition(((TransitionAuto) ref));
            _builder.append(_transition_2, "");
            _builder.newLineIfNotEmpty();
          } else {
            if ((ref instanceof TransitionEmbedded)) {
              CharSequence _transition_3 = this.transition(((TransitionEmbedded) ref));
              _builder.append(_transition_3, "");
              _builder.newLineIfNotEmpty();
            } else {
              _builder.append("// invalid transition type seen");
              _builder.newLine();
            }
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence transition(final Transition transition) {
    StringConcatenation _builder = new StringConcatenation();
    Event _event = transition.getEvent();
    String _name = _event.getName();
    _builder.append(_name, "");
    _builder.append(" -> ");
    String _xifexpression = null;
    boolean _and = false;
    State _state = transition.getState();
    boolean _notEquals = (!Objects.equal(_state, null));
    if (!_notEquals) {
      _and = false;
    } else {
      State _state_1 = transition.getState();
      String _name_1 = _state_1.getName();
      boolean _notEquals_1 = (!Objects.equal(_name_1, null));
      _and = _notEquals_1;
    }
    if (_and) {
      State _state_2 = transition.getState();
      _xifexpression = _state_2.getName();
    } else {
      _xifexpression = transition.getName();
    }
    _builder.append(_xifexpression, "");
    {
      TransitionSpec _spec = transition.getSpec();
      boolean _notEquals_2 = (!Objects.equal(_spec, null));
      if (_notEquals_2) {
        _builder.append(" {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        TransitionSpec _spec_1 = transition.getSpec();
        CharSequence _transitionSpec = this.transitionSpec(_spec_1);
        _builder.append(_transitionSpec, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
      }
    }
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence transition(final TransitionForEach transition) {
    StringConcatenation _builder = new StringConcatenation();
    Event _event = transition.getEvent();
    String _name = _event.getName();
    _builder.append(_name, "");
    _builder.append(" *-> ");
    String _xifexpression = null;
    boolean _and = false;
    State _state = transition.getState();
    boolean _notEquals = (!Objects.equal(_state, null));
    if (!_notEquals) {
      _and = false;
    } else {
      State _state_1 = transition.getState();
      String _name_1 = _state_1.getName();
      boolean _notEquals_1 = (!Objects.equal(_name_1, null));
      _and = _notEquals_1;
    }
    if (_and) {
      State _state_2 = transition.getState();
      _xifexpression = _state_2.getName();
    } else {
      _xifexpression = transition.getName();
    }
    _builder.append(_xifexpression, "");
    {
      TransitionSpec _spec = transition.getSpec();
      boolean _notEquals_2 = (!Objects.equal(_spec, null));
      if (_notEquals_2) {
        _builder.append(" {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        TransitionSpec _spec_1 = transition.getSpec();
        CharSequence _transitionSpec = this.transitionSpec(_spec_1);
        _builder.append(_transitionSpec, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
      }
    }
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence transition(final TransitionAuto transition) {
    StringConcatenation _builder = new StringConcatenation();
    Event _event = transition.getEvent();
    String _name = _event.getName();
    _builder.append(_name, "");
    _builder.append(" --> ");
    String _xifexpression = null;
    boolean _and = false;
    State _state = transition.getState();
    boolean _notEquals = (!Objects.equal(_state, null));
    if (!_notEquals) {
      _and = false;
    } else {
      State _state_1 = transition.getState();
      String _name_1 = _state_1.getName();
      boolean _notEquals_1 = (!Objects.equal(_name_1, null));
      _and = _notEquals_1;
    }
    if (_and) {
      State _state_2 = transition.getState();
      _xifexpression = _state_2.getName();
    } else {
      _xifexpression = transition.getName();
    }
    _builder.append(_xifexpression, "");
    {
      TransitionSpec _spec = transition.getSpec();
      boolean _notEquals_2 = (!Objects.equal(_spec, null));
      if (_notEquals_2) {
        _builder.append(" {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        TransitionSpec _spec_1 = transition.getSpec();
        CharSequence _transitionSpec = this.transitionSpec(_spec_1);
        _builder.append(_transitionSpec, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
      }
    }
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence transition(final TransitionEmbedded transition) {
    StringConcatenation _builder = new StringConcatenation();
    Event _event = transition.getEvent();
    String _name = _event.getName();
    _builder.append(_name, "");
    _builder.append(" +-> ");
    String _xifexpression = null;
    boolean _and = false;
    State _state = transition.getState();
    boolean _notEquals = (!Objects.equal(_state, null));
    if (!_notEquals) {
      _and = false;
    } else {
      State _state_1 = transition.getState();
      String _name_1 = _state_1.getName();
      boolean _notEquals_1 = (!Objects.equal(_name_1, null));
      _and = _notEquals_1;
    }
    if (_and) {
      State _state_2 = transition.getState();
      _xifexpression = _state_2.getName();
    } else {
      _xifexpression = transition.getName();
    }
    _builder.append(_xifexpression, "");
    {
      TransitionSpec _spec = transition.getSpec();
      boolean _notEquals_2 = (!Objects.equal(_spec, null));
      if (_notEquals_2) {
        _builder.append(" {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        TransitionSpec _spec_1 = transition.getSpec();
        CharSequence _transitionSpec = this.transitionSpec(_spec_1);
        _builder.append(_transitionSpec, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
      }
    }
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence transitionSpec(final TransitionSpec spec) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _and = false;
      EList<UriLink> _uriLinks = spec.getUriLinks();
      boolean _notEquals = (!Objects.equal(_uriLinks, null));
      if (!_notEquals) {
        _and = false;
      } else {
        EList<UriLink> _uriLinks_1 = spec.getUriLinks();
        int _size = _uriLinks_1.size();
        boolean _greaterThan = (_size > 0);
        _and = _greaterThan;
      }
      if (_and) {
        _builder.append("parameters [ ");
        {
          EList<UriLink> _uriLinks_2 = spec.getUriLinks();
          boolean _hasElements = false;
          for(final UriLink parameter : _uriLinks_2) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate(",", "");
            }
            String _templateProperty = parameter.getTemplateProperty();
            _builder.append(_templateProperty, "");
            _builder.append(" = \"");
            UriLinkage _entityProperty = parameter.getEntityProperty();
            String _name = _entityProperty.getName();
            _builder.append(_name, "");
            _builder.append("\"");
          }
        }
        _builder.append(" ]");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Title _title = spec.getTitle();
      boolean _notEquals_1 = (!Objects.equal(_title, null));
      if (_notEquals_1) {
        _builder.append("title: \"");
        Title _title_1 = spec.getTitle();
        String _name_1 = _title_1.getName();
        _builder.append(_name_1, "");
        _builder.append("\"");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      LinkId _id = spec.getId();
      boolean _notEquals_2 = (!Objects.equal(_id, null));
      if (_notEquals_2) {
        _builder.append("id: \"");
        LinkId _id_1 = spec.getId();
        String _name_2 = _id_1.getName();
        _builder.append(_name_2, "");
        _builder.append("\"");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Expression _eval = spec.getEval();
      boolean _notEquals_3 = (!Objects.equal(_eval, null));
      if (_notEquals_3) {
        _builder.append("condition: ");
        {
          Expression _eval_1 = spec.getEval();
          EList<Function> _expressions = _eval_1.getExpressions();
          boolean _hasElements_1 = false;
          for(final Function expression : _expressions) {
            if (!_hasElements_1) {
              _hasElements_1 = true;
            } else {
              _builder.appendImmediate("&&", "");
            }
            {
              if ((expression instanceof OKFunction)) {
                _builder.append("OK(");
                State _state = ((OKFunction) expression).getState();
                String _name_3 = _state.getName();
                _builder.append(_name_3, "");
                _builder.append(")");
              }
            }
            {
              if ((expression instanceof NotFoundFunction)) {
                _builder.append("NOT_FOUND(");
                State _state_1 = ((NotFoundFunction) expression).getState();
                String _name_4 = _state_1.getName();
                _builder.append(_name_4, "");
                _builder.append(")");
              }
            }
          }
        }
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence relations(final EList<RelationRef> relations) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("relations [ ");
    {
      boolean _hasElements = false;
      for(final RelationRef relation : relations) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "");
        }
        {
          if ((relation instanceof Relation)) {
            String _name = ((Relation) relation).getName();
            _builder.append(_name, "");
          }
        }
        {
          if ((relation instanceof RelationConstant)) {
            _builder.append("\"");
            String _name_1 = ((RelationConstant) relation).getName();
            _builder.append(_name_1, "");
            _builder.append("\"");
          }
        }
      }
    }
    _builder.append(" ]");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
}
