package com.odcgroup.pageflow.docgen.cartridges.mapper;

import com.google.common.base.Objects;
import com.odcgroup.pageflow.docgen.utils.GenerationUtils;
import com.odcgroup.pageflow.model.Action;
import com.odcgroup.pageflow.model.DecisionState;
import com.odcgroup.pageflow.model.EndState;
import com.odcgroup.pageflow.model.InitState;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.ProblemManagement;
import com.odcgroup.pageflow.model.Property;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.pageflow.model.TransitionAction;
import com.odcgroup.pageflow.model.View;
import com.odcgroup.pageflow.model.ViewState;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class PageflowDocumentation {
  public CharSequence htmlOpening() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<!DOCTYPE html>");
    _builder.newLine();
    _builder.append("<html lang=\"en\">\t\t");
    _builder.newLine();
    _builder.append("<body>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence htmlClosing() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("</body>");
    _builder.newLine();
    _builder.append("</html>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence pageflowDescription(final Pageflow pageflow) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _htmlOpening = this.htmlOpening();
    _builder.append(_htmlOpening, "");
    _builder.newLineIfNotEmpty();
    String _desc = pageflow.getDesc();
    _builder.append(_desc, "");
    _builder.append("<br><br>");
    _builder.newLineIfNotEmpty();
    _builder.append("Model Path: Pageflows<I>");
    Resource _eResource = pageflow.eResource();
    URI _uRI = _eResource.getURI();
    String _path = _uRI.path();
    Resource _eResource_1 = pageflow.eResource();
    URI _uRI_1 = _eResource_1.getURI();
    String _path_1 = _uRI_1.path();
    int _length = _path_1.length();
    int _minus = (_length - 9);
    String _substring = _path.substring(0, _minus);
    _builder.append(_substring, "");
    _builder.append("</I>");
    _builder.newLineIfNotEmpty();
    CharSequence _htmlClosing = this.htmlClosing();
    _builder.append(_htmlClosing, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence pageflowProperties(final Pageflow pageflow) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _htmlOpening = this.htmlOpening();
    _builder.append(_htmlOpening, "");
    _builder.append("\t");
    _builder.newLineIfNotEmpty();
    _builder.append("List of pageflow properties: <br>");
    _builder.newLine();
    _builder.append("<table border=\"1\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<tr>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Property</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Value</th>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</tr>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<tr>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<td>Pageflow Name</td>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<td>");
    String _name = pageflow.getName();
    _builder.append(_name, "\t\t");
    _builder.append("</td>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("</tr>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<tr>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<td>Error View URI</td>\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<td>");
    View _errorView = pageflow.getErrorView();
    String _url = _errorView.getUrl();
    _builder.append(_url, "\t\t");
    _builder.append("</td>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("</tr>\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<tr>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<td>Target File Name</td>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<td>");
    String _fileName = pageflow.getFileName();
    _builder.append(_fileName, "\t\t");
    _builder.append("</td>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("</tr>");
    _builder.newLine();
    {
      EList _property = pageflow.getProperty();
      List<Object> _list = IterableExtensions.<Object>toList(_property);
      for(final Object pObj : _list) {
        {
          if ((pObj instanceof Property)) {
            _builder.append("\t");
            Property prop = ((Property) pObj);
            _builder.newLineIfNotEmpty();
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<td>Property name: ");
            String _name_1 = prop.getName();
            _builder.append(_name_1, "\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<td>");
            String _value = prop.getValue();
            _builder.append(_value, "\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("</table> <br>");
    _builder.newLine();
    CharSequence _htmlClosing = this.htmlClosing();
    _builder.append(_htmlClosing, "");
    _builder.append("\t\t");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence pageflowStates(final Pageflow pageflow) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _htmlOpening = this.htmlOpening();
    _builder.append(_htmlOpening, "");
    _builder.append("\t");
    _builder.newLineIfNotEmpty();
    _builder.append("List of pageflow states: <br>");
    _builder.newLine();
    _builder.append("<table border=\"1\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<tr>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Name</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Type</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Description</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Reference</th>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</tr>");
    _builder.newLine();
    _builder.append("\t");
    EList _states = pageflow.getStates();
    List<State> _list = IterableExtensions.<State>toList(_states);
    InitState initState = this.fetchInitState(_list);
    _builder.newLineIfNotEmpty();
    {
      boolean _notEquals = (!Objects.equal(initState, null));
      if (_notEquals) {
        _builder.append("\t");
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("<td>");
        String _name = initState.getName();
        _builder.append(_name, "\t\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("<td>Init State</td>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("<td>");
        String _desc = initState.getDesc();
        _builder.append(_desc, "\t\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("<td>None</td>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("</tr>");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    EList _states_1 = pageflow.getStates();
    List<State> _list_1 = IterableExtensions.<State>toList(_states_1);
    List<ViewState> pageStates = this.fetchPageStates(_list_1);
    _builder.newLineIfNotEmpty();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(pageStates);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        {
          for(final ViewState page : pageStates) {
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            String _displayName = page.getDisplayName();
            _builder.append(_displayName, "\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>Page</td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            String _desc_1 = page.getDesc();
            _builder.append(_desc_1, "\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            {
              View _view = page.getView();
              String _url = _view.getUrl();
              boolean _startsWith = _url.startsWith("resource:");
              if (_startsWith) {
                _builder.append("Page: ");
                View _view_1 = page.getView();
                String _url_1 = _view_1.getUrl();
                String _substring = _url_1.substring(11);
                _builder.append(_substring, "\t\t");
              } else {
                _builder.append("Page: ");
                View _view_2 = page.getView();
                String _url_2 = _view_2.getUrl();
                _builder.append(_url_2, "\t\t");
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("\t");
    EList _states_2 = pageflow.getStates();
    List<State> _list_2 = IterableExtensions.<State>toList(_states_2);
    List<SubPageflowState> subPageflowStates = this.fetchSubPageflowStates(_list_2);
    _builder.newLineIfNotEmpty();
    {
      boolean _isNullOrEmpty_1 = IterableExtensions.isNullOrEmpty(subPageflowStates);
      boolean _not_1 = (!_isNullOrEmpty_1);
      if (_not_1) {
        {
          for(final SubPageflowState subPfState : subPageflowStates) {
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            String _displayName_1 = subPfState.getDisplayName();
            _builder.append(_displayName_1, "\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>Sub-Pageflow</td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            String _desc_2 = subPfState.getDesc();
            _builder.append(_desc_2, "\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>Pageflow: ");
            Pageflow _subPageflow = subPfState.getSubPageflow();
            Resource _eResource = _subPageflow.eResource();
            URI _uRI = _eResource.getURI();
            String _string = _uRI.toString();
            _builder.append(_string, "\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("\t");
    EList _states_3 = pageflow.getStates();
    List<State> _list_3 = IterableExtensions.<State>toList(_states_3);
    List<DecisionState> decisnStates = this.fetchDecisionStates(_list_3);
    _builder.newLineIfNotEmpty();
    {
      boolean _isNullOrEmpty_2 = IterableExtensions.isNullOrEmpty(decisnStates);
      boolean _not_2 = (!_isNullOrEmpty_2);
      if (_not_2) {
        {
          for(final DecisionState decideState : decisnStates) {
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            String _displayName_2 = decideState.getDisplayName();
            _builder.append(_displayName_2, "\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>Decision State</td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            String _desc_3 = decideState.getDesc();
            _builder.append(_desc_3, "\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>None</td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("\t");
    EList _states_4 = pageflow.getStates();
    List<State> _list_4 = IterableExtensions.<State>toList(_states_4);
    List<EndState> endStates = this.fetchEndStates(_list_4);
    _builder.newLineIfNotEmpty();
    {
      boolean _isNullOrEmpty_3 = IterableExtensions.isNullOrEmpty(endStates);
      boolean _not_3 = (!_isNullOrEmpty_3);
      if (_not_3) {
        {
          for(final EndState endState : endStates) {
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            String _displayName_3 = endState.getDisplayName();
            _builder.append(_displayName_3, "\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>End State</td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            String _desc_4 = endState.getDesc();
            _builder.append(_desc_4, "\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>Exit URI: ");
            String _exitUrl = endState.getExitUrl();
            _builder.append(_exitUrl, "\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("</table> <br>");
    _builder.newLine();
    CharSequence _htmlClosing = this.htmlClosing();
    _builder.append(_htmlClosing, "");
    _builder.append("\t\t");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence pageflowTransitions(final Pageflow pageflow) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _htmlOpening = this.htmlOpening();
    _builder.append(_htmlOpening, "");
    _builder.append("\t");
    _builder.newLineIfNotEmpty();
    _builder.append("List of pageflow transitions: <br>");
    _builder.newLine();
    _builder.append("<table border=\"1\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<tr>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">From state</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">To state</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Name</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Description</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Actions</th>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</tr>");
    _builder.newLine();
    _builder.append("\t");
    EList _transitions = pageflow.getTransitions();
    List<Object> allTrans = IterableExtensions.<Object>toList(_transitions);
    _builder.newLineIfNotEmpty();
    {
      boolean _and = false;
      boolean _notEquals = (!Objects.equal(allTrans, null));
      if (!_notEquals) {
        _and = false;
      } else {
        boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(allTrans);
        boolean _not = (!_isNullOrEmpty);
        _and = _not;
      }
      if (_and) {
        {
          for(final Object eachTransition : allTrans) {
            _builder.append("\t");
            Transition trans = ((Transition) eachTransition);
            _builder.newLineIfNotEmpty();
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<td><img src=");
            State _fromState = trans.getFromState();
            String _fetchStateImage = this.fetchStateImage(_fromState);
            _builder.append(_fetchStateImage, "\t");
            _builder.append("/>");
            State _fromState_1 = trans.getFromState();
            String _displayName = _fromState_1.getDisplayName();
            _builder.append(_displayName, "\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<td><img src=");
            State _toState = trans.getToState();
            String _fetchStateImage_1 = this.fetchStateImage(_toState);
            _builder.append(_fetchStateImage_1, "\t");
            _builder.append("/>");
            State _toState_1 = trans.getToState();
            String _displayName_1 = _toState_1.getDisplayName();
            _builder.append(_displayName_1, "\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<td>");
            String _displayName_2 = trans.getDisplayName();
            _builder.append(_displayName_2, "\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<td>");
            String _desc = trans.getDesc();
            _builder.append(_desc, "\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<td>");
            _builder.newLine();
            _builder.append("\t");
            EList actions = trans.getActions();
            _builder.newLineIfNotEmpty();
            {
              boolean _and_1 = false;
              boolean _notEquals_1 = (!Objects.equal(actions, null));
              if (!_notEquals_1) {
                _and_1 = false;
              } else {
                boolean _isNullOrEmpty_1 = IterableExtensions.isNullOrEmpty(actions);
                boolean _not_1 = (!_isNullOrEmpty_1);
                _and_1 = _not_1;
              }
              if (_and_1) {
                {
                  for(final Object actns : actions) {
                    _builder.append("\t");
                    Action action = ((Action) actns);
                    _builder.newLineIfNotEmpty();
                    String _name = action.getName();
                    _builder.append(_name, "");
                    _builder.append("<br>");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
            _builder.append("\t");
            _builder.append("</td>");
            _builder.newLine();
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("</table> <br>");
    _builder.newLine();
    CharSequence _htmlClosing = this.htmlClosing();
    _builder.append(_htmlClosing, "");
    _builder.append("\t\t");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence pageflowXTransitions(final Pageflow pageflow) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _htmlOpening = this.htmlOpening();
    _builder.append(_htmlOpening, "");
    _builder.append("\t");
    _builder.newLineIfNotEmpty();
    _builder.append("List of pageflow transitions: <br>");
    _builder.newLine();
    _builder.append("<table border=\"1\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<tr>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Name</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">From state</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">To state</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Description</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Action name</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Description</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">URI</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Properties Name</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Properties Value</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Validation Process</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">If error Reported</th>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</tr>");
    _builder.newLine();
    _builder.append("\t");
    EList _transitions = pageflow.getTransitions();
    List<Object> allTrans = IterableExtensions.<Object>toList(_transitions);
    _builder.newLineIfNotEmpty();
    {
      boolean _and = false;
      boolean _notEquals = (!Objects.equal(allTrans, null));
      if (!_notEquals) {
        _and = false;
      } else {
        boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(allTrans);
        boolean _not = (!_isNullOrEmpty);
        _and = _not;
      }
      if (_and) {
        {
          for(final Object eachTransition : allTrans) {
            _builder.append("\t");
            Transition trans = ((Transition) eachTransition);
            _builder.newLineIfNotEmpty();
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<td>");
            String _displayName = trans.getDisplayName();
            _builder.append(_displayName, "\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<td>");
            State _fromState = trans.getFromState();
            String _displayName_1 = _fromState.getDisplayName();
            _builder.append(_displayName_1, "\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<td>");
            State _toState = trans.getToState();
            String _displayName_2 = _toState.getDisplayName();
            _builder.append(_displayName_2, "\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<td>");
            String _desc = trans.getDesc();
            _builder.append(_desc, "\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            EList actions = trans.getActions();
            _builder.newLineIfNotEmpty();
            {
              boolean _and_1 = false;
              boolean _notEquals_1 = (!Objects.equal(actions, null));
              if (!_notEquals_1) {
                _and_1 = false;
              } else {
                boolean _isNullOrEmpty_1 = IterableExtensions.isNullOrEmpty(actions);
                boolean _not_1 = (!_isNullOrEmpty_1);
                _and_1 = _not_1;
              }
              if (_and_1) {
                {
                  for(final Object actns : actions) {
                    _builder.append("\t");
                    Action action = ((Action) actns);
                    _builder.newLineIfNotEmpty();
                    {
                      int _indexOf = actions.indexOf(action);
                      boolean _notEquals_2 = (_indexOf != 0);
                      if (_notEquals_2) {
                        _builder.append("\t\t");
                        _builder.append("<tr>");
                        _builder.newLine();
                        _builder.append("\t\t");
                        _builder.append("<td></td>");
                        _builder.newLine();
                        _builder.append("\t\t");
                        _builder.append("<td></td>");
                        _builder.newLine();
                        _builder.append("\t\t");
                        _builder.append("<td></td>");
                        _builder.newLine();
                        _builder.append("\t\t");
                        _builder.append("<td></td>");
                        _builder.newLine();
                      }
                    }
                    _builder.append("<td>");
                    String _name = action.getName();
                    _builder.append(_name, "");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("<td>");
                    String _desc_1 = action.getDesc();
                    _builder.append(_desc_1, "");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("<td>");
                    String _uri = action.getUri();
                    _builder.append(_uri, "");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("<td>\t\t");
                    {
                      EList _property = action.getProperty();
                      for(final Object acProp : _property) {
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t\t\t");
                        Property acProps = ((Property) acProp);
                        _builder.newLineIfNotEmpty();
                        String _name_1 = acProps.getName();
                        _builder.append(_name_1, "");
                        _builder.append("<br>");
                        _builder.newLineIfNotEmpty();
                      }
                    }
                    _builder.append("</td>");
                    _builder.newLine();
                    _builder.append("<td>\t\t");
                    {
                      EList _property_1 = action.getProperty();
                      for(final Object acProp_1 : _property_1) {
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t\t\t");
                        Property acProps_1 = ((Property) acProp_1);
                        _builder.newLineIfNotEmpty();
                        String _value = acProps_1.getValue();
                        _builder.append(_value, "");
                        _builder.append("<br>");
                        _builder.newLineIfNotEmpty();
                      }
                    }
                    _builder.append("</td>");
                    _builder.newLine();
                    {
                      if ((action instanceof TransitionAction)) {
                        _builder.append("\t\t\t");
                        TransitionAction transActn = ((TransitionAction) action);
                        _builder.newLineIfNotEmpty();
                        _builder.append("<td>");
                        {
                          ProblemManagement _problemManagement = transActn.getProblemManagement();
                          String _literal = _problemManagement.getLiteral();
                          boolean _equals = _literal.equals("Validation");
                          if (_equals) {
                            _builder.append("Yes");
                          } else {
                            _builder.append("No");
                          }
                        }
                        _builder.append("</td>");
                        _builder.newLineIfNotEmpty();
                        _builder.append("<td>");
                        {
                          ProblemManagement _problemManagement_1 = transActn.getProblemManagement();
                          String _literal_1 = _problemManagement_1.getLiteral();
                          boolean _equals_1 = _literal_1.equals("Continue");
                          if (_equals_1) {
                            _builder.append("Continue");
                          } else {
                            ProblemManagement _problemManagement_2 = transActn.getProblemManagement();
                            String _literal_2 = _problemManagement_2.getLiteral();
                            boolean _equals_2 = _literal_2.equals("Go back");
                            if (_equals_2) {
                              _builder.append("Go back");
                            }
                          }
                        }
                        _builder.append("</td>");
                        _builder.newLineIfNotEmpty();
                      }
                    }
                    _builder.append("\t\t");
                    _builder.newLine();
                  }
                }
              }
            }
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("</table> <br>");
    _builder.newLine();
    CharSequence _htmlClosing = this.htmlClosing();
    _builder.append(_htmlClosing, "");
    _builder.append("\t\t");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public String fetchStateImage(final State state) {
    String imgUrl = null;
    if ((state instanceof InitState)) {
      imgUrl = "\"InitState.jpg\"";
    } else {
      if ((state instanceof ViewState)) {
        imgUrl = "\"ViewState.jpg\"";
      } else {
        if ((state instanceof DecisionState)) {
          imgUrl = "\"DecisionState.jpg\"";
        } else {
          if ((state instanceof SubPageflowState)) {
            imgUrl = "\"SubPageflowState.jpg\"";
          } else {
            if ((state instanceof EndState)) {
              imgUrl = "\"EndState.jpg\"";
            }
          }
        }
      }
    }
    return imgUrl;
  }
  
  public InitState fetchInitState(final List<State> states) {
    for (final State state : states) {
      if ((state instanceof InitState)) {
        return ((InitState) state);
      }
    }
    return null;
  }
  
  public List<ViewState> fetchPageStates(final List<State> states) {
    List<ViewState> pageStates = CollectionLiterals.<ViewState>newArrayList();
    for (final State state : states) {
      if ((state instanceof ViewState)) {
        pageStates.add(((ViewState) state));
      }
    }
    return pageStates;
  }
  
  public List<SubPageflowState> fetchSubPageflowStates(final List<State> states) {
    List<SubPageflowState> subPageflowStates = CollectionLiterals.<SubPageflowState>newArrayList();
    for (final State state : states) {
      if ((state instanceof SubPageflowState)) {
        subPageflowStates.add(((SubPageflowState) state));
      }
    }
    return subPageflowStates;
  }
  
  public List<DecisionState> fetchDecisionStates(final List<State> states) {
    List<DecisionState> decisionStates = CollectionLiterals.<DecisionState>newArrayList();
    for (final State state : states) {
      if ((state instanceof DecisionState)) {
        decisionStates.add(((DecisionState) state));
      }
    }
    return decisionStates;
  }
  
  public List<EndState> fetchEndStates(final List<State> states) {
    List<EndState> endStates = CollectionLiterals.<EndState>newArrayList();
    for (final State state : states) {
      if ((state instanceof EndState)) {
        endStates.add(((EndState) state));
      }
    }
    return endStates;
  }
  
  public CharSequence pageflowIndex(final String path, final List<String> fileList) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _htmlOpening = this.htmlOpening();
    _builder.append(_htmlOpening, "");
    _builder.newLineIfNotEmpty();
    _builder.append("<ul>");
    _builder.newLine();
    {
      for(final String pageflowFile : fileList) {
        _builder.append("\t");
        _builder.append("<li>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("<A HREF=\"");
        _builder.append((path + "\\"), "\t\t");
        _builder.append(pageflowFile, "\t\t");
        _builder.append("\">");
        String _titleForFileName = GenerationUtils.getTitleForFileName(pageflowFile);
        _builder.append(_titleForFileName, "\t\t");
        _builder.append("</A>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<br></br>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("</li>");
        _builder.newLine();
      }
    }
    _builder.append("</ul>");
    _builder.newLine();
    CharSequence _htmlClosing = this.htmlClosing();
    _builder.append(_htmlClosing, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
}
