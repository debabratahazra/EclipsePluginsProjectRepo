package com.odcgroup.page.docgen.cartridges.mapper;

import com.google.common.base.Objects;
import com.odcgroup.page.docgen.model.ModuleAttribute;
import com.odcgroup.page.docgen.utils.GenerationUtils;
import com.odcgroup.page.docgen.utils.ModuleUtils;
import com.odcgroup.page.metamodel.EventType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.TranslationSupport;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Translation;
import com.odcgroup.page.model.Widget;
import com.odcgroup.workbench.core.IOfsProject;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class ModuleModel2ModuleDocumentation {
  public CharSequence htmlOpening() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<!DOCTYPE html>");
    _builder.newLine();
    _builder.append("<html lang=\"en\">\t\t ");
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
  
  public CharSequence moduleDescription(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generalDescription = this.generalDescription(model, project, WidgetTypeConstants.MODULE);
    _builder.append(_generalDescription, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence fragmentDescription(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generalDescription = this.generalDescription(model, project, WidgetTypeConstants.FRAGMENT);
    _builder.append(_generalDescription, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence moduleIncludes(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generalIncludes = this.generalIncludes(model, WidgetTypeConstants.MODULE, project);
    _builder.append(_generalIncludes, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence fragmentIncludes(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generalIncludes = this.generalIncludes(model, WidgetTypeConstants.FRAGMENT, project);
    _builder.append(_generalIncludes, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence moduleContainers(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    Widget _widget = model.getWidget();
    String containmentValue = this.fetchModuleInformation(_widget, WidgetTypeConstants.MODULE, "containment");
    _builder.newLineIfNotEmpty();
    Widget _widget_1 = model.getWidget();
    CharSequence _tabCondContainer = this.tabCondContainer(_widget_1, project, containmentValue);
    _builder.append(_tabCondContainer, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence moduleXContainers(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    Widget _widget = model.getWidget();
    String containmentValue = this.fetchModuleInformation(_widget, WidgetTypeConstants.MODULE, "containment");
    _builder.newLineIfNotEmpty();
    Widget _widget_1 = model.getWidget();
    CharSequence _tabCondXContainer = this.tabCondXContainer(_widget_1, project, containmentValue);
    _builder.append(_tabCondXContainer, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence fragmentContainers(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    Widget _widget = model.getWidget();
    CharSequence _tabCondContainer = this.tabCondContainer(_widget, project, "");
    _builder.append(_tabCondContainer, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence fragmentXContainers(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    Widget _widget = model.getWidget();
    CharSequence _tabCondXContainer = this.tabCondXContainer(_widget, project, "");
    _builder.append(_tabCondXContainer, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence moduleAttributes(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> allAttributes = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    this.fetchAllAttributes(_widget, allAttributes);
    _builder.newLineIfNotEmpty();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(allAttributes);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        _builder.append("List of module attributes:");
        _builder.newLine();
        CharSequence _coreAttributes = this.coreAttributes(project, allAttributes);
        _builder.append(_coreAttributes, "");
        _builder.newLineIfNotEmpty();
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.newLineIfNotEmpty();
        allAttributes.clear();
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence fragmentAttributes(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> allAttributes = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    this.fetchAllAttributes(_widget, allAttributes);
    _builder.newLineIfNotEmpty();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(allAttributes);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        _builder.append("List of fragment attributes:");
        _builder.newLine();
        CharSequence _coreAttributes = this.coreAttributes(project, allAttributes);
        _builder.append(_coreAttributes, "");
        _builder.newLineIfNotEmpty();
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.newLineIfNotEmpty();
        allAttributes.clear();
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence moduleXAttributes(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> allAttributes = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    List<Widget> attributeList = this.fetchAllXAttributes(_widget, allAttributes);
    _builder.newLineIfNotEmpty();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(attributeList);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        _builder.append("Large List of module attributes:");
        _builder.newLine();
        CharSequence _coreXAttributes = this.coreXAttributes(project, attributeList);
        _builder.append(_coreXAttributes, "");
        _builder.newLineIfNotEmpty();
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.newLineIfNotEmpty();
        attributeList.clear();
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence fragmentXAttributes(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> allAttributes = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    List<Widget> attributeList = this.fetchAllXAttributes(_widget, allAttributes);
    _builder.newLineIfNotEmpty();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(attributeList);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        _builder.append("Large List of fragment attributes:");
        _builder.newLine();
        CharSequence _coreXAttributes = this.coreXAttributes(project, attributeList);
        _builder.append(_coreXAttributes, "");
        _builder.newLineIfNotEmpty();
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.newLineIfNotEmpty();
        attributeList.clear();
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public boolean checkActionExist(final List<Widget> actionList) {
    for (final Widget actWid : actionList) {
      EList<Event> _events = actWid.getEvents();
      for (final Event eve : _events) {
        boolean _notEquals = (!Objects.equal(eve, null));
        if (_notEquals) {
          return true;
        }
      }
    }
    return false;
  }
  
  public CharSequence moduleActions(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> actionWidgets = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    List<Widget> actionList = this.fetchActionWidgets(_widget, actionWidgets);
    _builder.newLineIfNotEmpty();
    {
      boolean _and = false;
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(actionList);
      boolean _not = (!_isNullOrEmpty);
      if (!_not) {
        _and = false;
      } else {
        boolean _checkActionExist = this.checkActionExist(actionList);
        _and = _checkActionExist;
      }
      if (_and) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        _builder.append("List of actions that can be triggered within the module:");
        _builder.newLine();
        _builder.append("<table border=\"1\">");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Widget</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Event</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Label</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Navigates To</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Target</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Contained In</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Description</th>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("</tr>");
        _builder.newLine();
        {
          for(final Widget actWid : actionList) {
            {
              EList<Event> _events = actWid.getEvents();
              for(final Event eve : _events) {
                _builder.append("<tr>");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("<td>");
                {
                  WidgetType _type = actWid.getType();
                  String _name = _type.getName();
                  boolean _equals = _name.equals("TableGroup");
                  if (_equals) {
                    _builder.append("Table group");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                  } else {
                    boolean _and_1 = false;
                    WidgetType _type_1 = actWid.getType();
                    String _name_1 = _type_1.getName();
                    boolean _equals_1 = _name_1.equals(WidgetTypeConstants.MATRIX_AXIS);
                    if (!_equals_1) {
                      _and_1 = false;
                    } else {
                      String _propertyValue = actWid.getPropertyValue("matrixAxis");
                      boolean _equals_2 = _propertyValue.equals("y-axis");
                      _and_1 = _equals_2;
                    }
                    if (_and_1) {
                      _builder.append("Matrix Axis Y");
                      _builder.newLineIfNotEmpty();
                      _builder.append("\t");
                    } else {
                      boolean _and_2 = false;
                      WidgetType _type_2 = actWid.getType();
                      String _name_2 = _type_2.getName();
                      boolean _equals_3 = _name_2.equals(WidgetTypeConstants.MATRIX_AXIS);
                      if (!_equals_3) {
                        _and_2 = false;
                      } else {
                        String _propertyValue_1 = actWid.getPropertyValue("matrixAxis");
                        boolean _equals_4 = _propertyValue_1.equals("x-axis");
                        _and_2 = _equals_4;
                      }
                      if (_and_2) {
                        _builder.append("Matrix Axis X");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                      } else {
                        WidgetType _type_3 = actWid.getType();
                        String _name_3 = _type_3.getName();
                        boolean _equals_5 = _name_3.equals(WidgetTypeConstants.MATRIX_CONTENTCELLITEM);
                        if (_equals_5) {
                          _builder.append("Matrix Cell Item");
                          _builder.newLineIfNotEmpty();
                          _builder.append("\t");
                        } else {
                          boolean _and_3 = false;
                          WidgetType _type_4 = actWid.getType();
                          String _name_4 = _type_4.getName();
                          boolean _equals_6 = _name_4.equals(WidgetTypeConstants.MATRIX_CELLITEM);
                          if (!_equals_6) {
                            _and_3 = false;
                          } else {
                            EObject _eContainer = actWid.eContainer();
                            String _propertyValue_2 = ((Widget) _eContainer).getPropertyValue("matrixCellType");
                            boolean _equals_7 = _propertyValue_2.equals("last-row");
                            _and_3 = _equals_7;
                          }
                          if (_and_3) {
                            _builder.append("Matrix Last Row Item");
                            _builder.newLineIfNotEmpty();
                            _builder.append("\t");
                          } else {
                            boolean _and_4 = false;
                            WidgetType _type_5 = actWid.getType();
                            String _name_5 = _type_5.getName();
                            boolean _equals_8 = _name_5.equals(WidgetTypeConstants.MATRIX_CELLITEM);
                            if (!_equals_8) {
                              _and_4 = false;
                            } else {
                              EObject _eContainer_1 = actWid.eContainer();
                              String _propertyValue_3 = ((Widget) _eContainer_1).getPropertyValue("matrixCellType");
                              boolean _equals_9 = _propertyValue_3.equals("last-column");
                              _and_4 = _equals_9;
                            }
                            if (_and_4) {
                              _builder.append("Matrix Last Column Item");
                              _builder.newLineIfNotEmpty();
                              _builder.append("\t");
                            } else {
                              boolean _and_5 = false;
                              WidgetType _type_6 = actWid.getType();
                              String _name_6 = _type_6.getName();
                              boolean _equals_10 = _name_6.equals(WidgetTypeConstants.MATRIX_CELLITEM);
                              if (!_equals_10) {
                                _and_5 = false;
                              } else {
                                EObject _eContainer_2 = actWid.eContainer();
                                String _propertyValue_4 = ((Widget) _eContainer_2).getPropertyValue("matrixCellType");
                                boolean _equals_11 = _propertyValue_4.equals("last-cell");
                                _and_5 = _equals_11;
                              }
                              if (_and_5) {
                                _builder.append("Matrix Last Cell Item");
                                _builder.newLineIfNotEmpty();
                                _builder.append("\t");
                              } else {
                                WidgetType _type_7 = actWid.getType();
                                String _name_7 = _type_7.getName();
                                boolean _equals_12 = _name_7.equals(WidgetTypeConstants.MATRIX_EXTRACOLUMNITEM);
                                if (_equals_12) {
                                  _builder.append("Matrix Additonial Column");
                                  _builder.newLineIfNotEmpty();
                                  _builder.append("\t");
                                } else {
                                  WidgetType _type_8 = actWid.getType();
                                  String _name_8 = _type_8.getName();
                                  boolean _equals_13 = _name_8.equals(WidgetTypeConstants.TABLE_COLUMN_ITEM);
                                  if (_equals_13) {
                                    _builder.append("Table Column Item");
                                    _builder.newLineIfNotEmpty();
                                    _builder.append("\t");
                                  } else {
                                    WidgetType _type_9 = actWid.getType();
                                    String _name_9 = _type_9.getName();
                                    _builder.append(_name_9, "\t");
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("<td>");
                EventType _eventType = eve.getEventType();
                String _name_10 = _eventType.getName();
                String _plus = (_name_10 + " (");
                String _functionName = eve.getFunctionName();
                String _plus_1 = (_plus + _functionName);
                String _plus_2 = (_plus_1 + ")");
                _builder.append(_plus_2, "\t");
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("<td>");
                {
                  WidgetType _type_10 = actWid.getType();
                  String _name_11 = _type_10.getName();
                  boolean _equals_14 = _name_11.equals("TableGroup");
                  if (_equals_14) {
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    String _propertyValue_5 = actWid.getPropertyValue("group-column-name");
                    _builder.append(_propertyValue_5, "\t");
                    _builder.newLineIfNotEmpty();
                  } else {
                    boolean _or = false;
                    WidgetType _type_11 = actWid.getType();
                    String _name_12 = _type_11.getName();
                    boolean _equals_15 = _name_12.equals(WidgetTypeConstants.MATRIX_CONTENTCELLITEM);
                    if (_equals_15) {
                      _or = true;
                    } else {
                      boolean _and_6 = false;
                      WidgetType _type_12 = actWid.getType();
                      String _name_13 = _type_12.getName();
                      boolean _equals_16 = _name_13.equals(WidgetTypeConstants.MATRIX_CELLITEM);
                      if (!_equals_16) {
                        _and_6 = false;
                      } else {
                        EObject _eContainer_3 = actWid.eContainer();
                        String _propertyValue_6 = ((Widget) _eContainer_3).getPropertyValue("matrixCellType");
                        boolean _equals_17 = _propertyValue_6.equals("last-row");
                        boolean _not_1 = (!_equals_17);
                        _and_6 = _not_1;
                      }
                      _or = _and_6;
                    }
                    if (_or) {
                      _builder.append("\t");
                      String _propertyValue_7 = actWid.getPropertyValue("column-name");
                      _builder.append(_propertyValue_7, "\t");
                      _builder.newLineIfNotEmpty();
                    } else {
                      WidgetType _type_13 = actWid.getType();
                      String _name_14 = _type_13.getName();
                      boolean _equals_18 = _name_14.equals(WidgetTypeConstants.MATRIX_EXTRACOLUMNITEM);
                      if (_equals_18) {
                        _builder.append("\t");
                        String _fetchTranslationValue = this.fetchTranslationValue(actWid, project);
                        _builder.append(_fetchTranslationValue, "\t");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                      } else {
                        WidgetType _type_14 = actWid.getType();
                        String _name_15 = _type_14.getName();
                        boolean _equals_19 = _name_15.equals(WidgetTypeConstants.ICON);
                        if (_equals_19) {
                          _builder.append(" ");
                          String _propertyValue_8 = actWid.getPropertyValue("icon");
                          _builder.append(_propertyValue_8, "\t");
                          _builder.newLineIfNotEmpty();
                        } else {
                          boolean _and_7 = false;
                          WidgetType _type_15 = actWid.getType();
                          String _name_16 = _type_15.getName();
                          boolean _equals_20 = _name_16.equals(WidgetTypeConstants.MATRIX_CELLITEM);
                          if (!_equals_20) {
                            _and_7 = false;
                          } else {
                            EObject _eContainer_4 = actWid.eContainer();
                            String _propertyValue_9 = ((Widget) _eContainer_4).getPropertyValue("matrixCellType");
                            boolean _equals_21 = _propertyValue_9.equals("last-row");
                            _and_7 = _equals_21;
                          }
                          if (_and_7) {
                            _builder.append("\t");
                            String _propertyValue_10 = actWid.getPropertyValue("domainAttribute");
                            _builder.append(_propertyValue_10, "\t");
                            _builder.newLineIfNotEmpty();
                            _builder.append("\t");
                          } else {
                            WidgetType _type_16 = actWid.getType();
                            String _name_17 = _type_16.getName();
                            boolean _equals_22 = _name_17.equals(WidgetTypeConstants.MATRIX_AXIS);
                            boolean _not_2 = (!_equals_22);
                            if (_not_2) {
                              String _fetchTranslationValue_1 = this.fetchTranslationValue(actWid, project);
                              _builder.append(_fetchTranslationValue_1, "\t");
                            }
                          }
                        }
                      }
                    }
                  }
                }
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("<td>");
                {
                  boolean _and_8 = false;
                  boolean _or_1 = false;
                  boolean _or_2 = false;
                  boolean _or_3 = false;
                  boolean _or_4 = false;
                  WidgetType _type_17 = actWid.getType();
                  String _name_18 = _type_17.getName();
                  boolean _equals_23 = _name_18.equals("TableGroup");
                  if (_equals_23) {
                    _or_4 = true;
                  } else {
                    WidgetType _type_18 = actWid.getType();
                    String _name_19 = _type_18.getName();
                    boolean _equals_24 = _name_19.equals(WidgetTypeConstants.MATRIX_AXIS);
                    _or_4 = _equals_24;
                  }
                  if (_or_4) {
                    _or_3 = true;
                  } else {
                    WidgetType _type_19 = actWid.getType();
                    String _name_20 = _type_19.getName();
                    boolean _equals_25 = _name_20.equals(WidgetTypeConstants.MATRIX_CONTENTCELLITEM);
                    _or_3 = _equals_25;
                  }
                  if (_or_3) {
                    _or_2 = true;
                  } else {
                    WidgetType _type_20 = actWid.getType();
                    String _name_21 = _type_20.getName();
                    boolean _equals_26 = _name_21.equals(WidgetTypeConstants.MATRIX_CELLITEM);
                    _or_2 = _equals_26;
                  }
                  if (_or_2) {
                    _or_1 = true;
                  } else {
                    WidgetType _type_21 = actWid.getType();
                    String _name_22 = _type_21.getName();
                    boolean _equals_27 = _name_22.equals(WidgetTypeConstants.MATRIX_EXTRACOLUMNITEM);
                    _or_1 = _equals_27;
                  }
                  if (!_or_1) {
                    _and_8 = false;
                  } else {
                    Parameter _findParameter = eve.findParameter("flow-action");
                    boolean _notEquals = (!Objects.equal(_findParameter, null));
                    _and_8 = _notEquals;
                  }
                  if (_and_8) {
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("Continuation on: ");
                    Parameter _findParameter_1 = eve.findParameter("flow-action");
                    String _value = _findParameter_1.getValue();
                    _builder.append(_value, "\t");
                    _builder.newLineIfNotEmpty();
                  } else {
                    boolean _and_9 = false;
                    boolean _and_10 = false;
                    boolean _and_11 = false;
                    Parameter _findParameter_2 = eve.findParameter("call-URI");
                    boolean _notEquals_1 = (!Objects.equal(_findParameter_2, null));
                    if (!_notEquals_1) {
                      _and_11 = false;
                    } else {
                      Parameter _findParameter_3 = eve.findParameter("call-URI");
                      String _value_1 = _findParameter_3.getValue();
                      boolean _equals_28 = _value_1.equals("<pageflow:continuation/>");
                      _and_11 = _equals_28;
                    }
                    if (!_and_11) {
                      _and_10 = false;
                    } else {
                      Parameter _findParameter_4 = eve.findParameter("param");
                      boolean _notEquals_2 = (!Objects.equal(_findParameter_4, null));
                      _and_10 = _notEquals_2;
                    }
                    if (!_and_10) {
                      _and_9 = false;
                    } else {
                      Parameter _findParameter_5 = eve.findParameter("param");
                      String _value_2 = _findParameter_5.getValue();
                      boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(_value_2);
                      boolean _not_3 = (!_isNullOrEmpty_1);
                      _and_9 = _not_3;
                    }
                    if (_and_9) {
                      _builder.append("\t");
                      _builder.append("Continuation on: ");
                      {
                        Parameter _findParameter_6 = eve.findParameter("param");
                        String _value_3 = _findParameter_6.getValue();
                        boolean _startsWith = _value_3.startsWith("flow-action=");
                        if (_startsWith) {
                          Parameter _findParameter_7 = eve.findParameter("param");
                          String _value_4 = _findParameter_7.getValue();
                          int _length = "flow-action=".length();
                          String _substring = _value_4.substring(_length);
                          _builder.append(_substring, "\t");
                        }
                      }
                      _builder.newLineIfNotEmpty();
                    } else {
                      boolean _or_5 = false;
                      boolean _and_12 = false;
                      boolean _and_13 = false;
                      Parameter _findParameter_8 = eve.findParameter("call-URI");
                      boolean _notEquals_3 = (!Objects.equal(_findParameter_8, null));
                      if (!_notEquals_3) {
                        _and_13 = false;
                      } else {
                        Parameter _findParameter_9 = eve.findParameter("call-URI");
                        String _value_5 = _findParameter_9.getValue();
                        boolean _isNullOrEmpty_2 = StringExtensions.isNullOrEmpty(_value_5);
                        boolean _not_4 = (!_isNullOrEmpty_2);
                        _and_13 = _not_4;
                      }
                      if (!_and_13) {
                        _and_12 = false;
                      } else {
                        Parameter _findParameter_10 = eve.findParameter("call-URI");
                        String _value_6 = _findParameter_10.getValue();
                        boolean _endsWith = _value_6.endsWith(".pageflow");
                        _and_12 = _endsWith;
                      }
                      if (_and_12) {
                        _or_5 = true;
                      } else {
                        WidgetType _type_22 = actWid.getType();
                        String _name_23 = _type_22.getName();
                        boolean _equals_29 = _name_23.equals(WidgetTypeConstants.ICON);
                        _or_5 = _equals_29;
                      }
                      if (_or_5) {
                        {
                          Parameter _findParameter_11 = eve.findParameter("call-URI");
                          boolean _notEquals_4 = (!Objects.equal(_findParameter_11, null));
                          if (_notEquals_4) {
                            {
                              Parameter _findParameter_12 = eve.findParameter("call-URI");
                              String _value_7 = _findParameter_12.getValue();
                              boolean _startsWith_1 = _value_7.startsWith("resource:///");
                              if (_startsWith_1) {
                                _builder.append("\t");
                                _builder.append("Pageflow: ");
                                Parameter _findParameter_13 = eve.findParameter("call-URI");
                                String _value_8 = _findParameter_13.getValue();
                                int _length_1 = "resource:///".length();
                                Parameter _findParameter_14 = eve.findParameter("call-URI");
                                String _value_9 = _findParameter_14.getValue();
                                int _lastIndexOf = _value_9.lastIndexOf(".");
                                String _substring_1 = _value_8.substring(_length_1, _lastIndexOf);
                                _builder.append(_substring_1, "\t");
                                _builder.newLineIfNotEmpty();
                              } else {
                                _builder.append("\t");
                                _builder.append("Pageflow: ");
                                Parameter _findParameter_15 = eve.findParameter("call-URI");
                                String _value_10 = _findParameter_15.getValue();
                                _builder.append(_value_10, "\t");
                                _builder.newLineIfNotEmpty();
                              }
                            }
                          }
                        }
                        _builder.append("\t");
                      } else {
                        WidgetType _type_23 = actWid.getType();
                        String _name_24 = _type_23.getName();
                        boolean _equals_30 = _name_24.equals(WidgetTypeConstants.ICON);
                        if (_equals_30) {
                          _builder.append(" ");
                          String _propertyValue_11 = actWid.getPropertyValue("icon");
                          _builder.append(_propertyValue_11, "\t");
                          _builder.newLineIfNotEmpty();
                          _builder.append("\t");
                        }
                      }
                    }
                  }
                }
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("<td>");
                {
                  Parameter _findParameter_16 = eve.findParameter("target");
                  boolean _notEquals_5 = (!Objects.equal(_findParameter_16, null));
                  if (_notEquals_5) {
                    Parameter _findParameter_17 = eve.findParameter("target");
                    String _value_11 = _findParameter_17.getValue();
                    _builder.append(_value_11, "\t");
                  }
                }
                _builder.append("</td> ");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("<td>");
                String _fetchParent = this.fetchParent(actWid, project);
                _builder.append(_fetchParent, "\t");
                _builder.append("</td> ");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("<td>");
                String _propertyValue_12 = actWid.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                _builder.append(_propertyValue_12, "\t");
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("</tr>");
                _builder.newLine();
              }
            }
            _builder.append("\t");
          }
        }
        actionWidgets.clear();
        _builder.newLineIfNotEmpty();
        _builder.append("</table>");
        _builder.newLine();
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence moduleActionFilters(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Event> actionEvents = CollectionLiterals.<Event>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    this.fetchActionEvents(_widget, actionEvents);
    _builder.newLineIfNotEmpty();
    {
      boolean _and = false;
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(actionEvents);
      boolean _not = (!_isNullOrEmpty);
      if (!_not) {
        _and = false;
      } else {
        boolean _checkSearchAction = this.checkSearchAction(actionEvents, "FilterSet");
        _and = _checkSearchAction;
      }
      if (_and) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        List<Snippet> searchSnipet = CollectionLiterals.<Snippet>newArrayList();
        _builder.newLineIfNotEmpty();
        {
          for(final Event eve : actionEvents) {
            this.fetchEventSnipet(eve, searchSnipet, "FilterSet");
            _builder.newLineIfNotEmpty();
            {
              boolean _isNullOrEmpty_1 = IterableExtensions.isNullOrEmpty(searchSnipet);
              boolean _not_1 = (!_isNullOrEmpty_1);
              if (_not_1) {
                _builder.append("List of related filters set on ");
                Widget _widget_1 = eve.getWidget();
                WidgetType _type = _widget_1.getType();
                String _name = _type.getName();
                _builder.append(_name, "");
                _builder.append(": ");
                {
                  Widget _widget_2 = eve.getWidget();
                  String _title = ModuleUtils.getTitle(project, _widget_2);
                  boolean _isNullOrEmpty_2 = StringExtensions.isNullOrEmpty(_title);
                  boolean _not_2 = (!_isNullOrEmpty_2);
                  if (_not_2) {
                    Widget _widget_3 = eve.getWidget();
                    String _title_1 = ModuleUtils.getTitle(project, _widget_3);
                    _builder.append(_title_1, "");
                  } else {
                    Widget _widget_4 = eve.getWidget();
                    String _typeName = _widget_4.getTypeName();
                    _builder.append(_typeName, "");
                  }
                }
                _builder.append(" <br></br>");
                _builder.newLineIfNotEmpty();
                {
                  for(final Snippet snpet : searchSnipet) {
                    _builder.append("This filter(s) applies on the dataset: ");
                    EList<Property> _properties = snpet.getProperties();
                    Property _get = _properties.get(2);
                    String _value = _get.getValue();
                    _builder.append(_value, "");
                    _builder.append(" (level: ");
                    EList<Property> _properties_1 = snpet.getProperties();
                    Property _get_1 = _properties_1.get(1);
                    String _value_1 = _get_1.getValue();
                    _builder.append(_value_1, "");
                    _builder.append(")<br></br>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("<table border=\"1\">");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<tr>");
                    _builder.newLine();
                    _builder.append("\t\t");
                    _builder.append("<th bgcolor=\"#6495ED\">Attribute</th>");
                    _builder.newLine();
                    _builder.append("\t\t");
                    _builder.append("<th bgcolor=\"#6495ED\">Operator</th>");
                    _builder.newLine();
                    _builder.append("\t\t");
                    _builder.append("<th bgcolor=\"#6495ED\">Value(1)</th>");
                    _builder.newLine();
                    _builder.append("\t\t");
                    _builder.append("<th bgcolor=\"#6495ED\">Value(2)</th>");
                    _builder.newLine();
                    _builder.append("\t\t");
                    _builder.append("<th bgcolor=\"#6495ED\">Mode</th>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("</tr>");
                    _builder.newLine();
                    {
                      EList<Snippet> _contents = snpet.getContents();
                      for(final Snippet childSnipet : _contents) {
                        _builder.append("\t");
                        _builder.append("<tr>");
                        _builder.newLine();
                        _builder.append("\t");
                        _builder.append("\t");
                        _builder.append("<td>");
                        EList<Property> _properties_2 = childSnipet.getProperties();
                        Property _get_2 = _properties_2.get(0);
                        String _value_2 = _get_2.getValue();
                        _builder.append(_value_2, "\t\t");
                        _builder.append("</td>");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                        _builder.append("\t");
                        _builder.append("<td>");
                        EList<Property> _properties_3 = childSnipet.getProperties();
                        Property _get_3 = _properties_3.get(2);
                        String _value_3 = _get_3.getValue();
                        _builder.append(_value_3, "\t\t");
                        _builder.append("</td>");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                        _builder.append("\t");
                        _builder.append("<td>");
                        EList<Property> _properties_4 = childSnipet.getProperties();
                        Property _get_4 = _properties_4.get(1);
                        String _value_4 = _get_4.getValue();
                        _builder.append(_value_4, "\t\t");
                        _builder.append("</td>");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                        _builder.append("\t");
                        _builder.append("<td>");
                        EList<Property> _properties_5 = childSnipet.getProperties();
                        Property _get_5 = _properties_5.get(3);
                        String _value_5 = _get_5.getValue();
                        _builder.append(_value_5, "\t\t");
                        _builder.append("</td>");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                        _builder.append("\t");
                        _builder.append("<td>");
                        EList<Property> _properties_6 = childSnipet.getProperties();
                        Property _get_6 = _properties_6.get(4);
                        String _value_6 = _get_6.getValue();
                        _builder.append(_value_6, "\t\t");
                        _builder.append("</td>");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                        _builder.append("</tr>");
                        _builder.newLine();
                      }
                    }
                    _builder.append("\t");
                    _builder.append("</table>");
                    _builder.newLine();
                  }
                }
                searchSnipet.clear();
                _builder.append("\t");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        actionEvents.clear();
        _builder.newLineIfNotEmpty();
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.append("\t");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public boolean checkSearchAction(final List<Event> actionEvents, final String type) {
    for (final Event eve : actionEvents) {
      {
        List<Snippet> searchSnipet = CollectionLiterals.<Snippet>newArrayList();
        this.fetchEventSnipet(eve, searchSnipet, type);
        boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(searchSnipet);
        boolean _not = (!_isNullOrEmpty);
        if (_not) {
          return true;
        }
      }
    }
    return false;
  }
  
  public CharSequence moduleActionSearch(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Event> actionEvents = CollectionLiterals.<Event>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    this.fetchActionEvents(_widget, actionEvents);
    _builder.newLineIfNotEmpty();
    {
      boolean _and = false;
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(actionEvents);
      boolean _not = (!_isNullOrEmpty);
      if (!_not) {
        _and = false;
      } else {
        boolean _checkSearchAction = this.checkSearchAction(actionEvents, "Query");
        _and = _checkSearchAction;
      }
      if (_and) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        List<Snippet> searchSnipet = CollectionLiterals.<Snippet>newArrayList();
        _builder.newLineIfNotEmpty();
        {
          for(final Event eve : actionEvents) {
            this.fetchEventSnipet(eve, searchSnipet, "Query");
            _builder.newLineIfNotEmpty();
            {
              boolean _isNullOrEmpty_1 = IterableExtensions.isNullOrEmpty(searchSnipet);
              boolean _not_1 = (!_isNullOrEmpty_1);
              if (_not_1) {
                {
                  for(final Snippet snpet : searchSnipet) {
                    _builder.append("Search set on ");
                    Widget _widget_1 = eve.getWidget();
                    WidgetType _type = _widget_1.getType();
                    String _name = _type.getName();
                    _builder.append(_name, "");
                    _builder.append(": ");
                    {
                      Widget _widget_2 = eve.getWidget();
                      String _title = ModuleUtils.getTitle(project, _widget_2);
                      boolean _isNullOrEmpty_2 = StringExtensions.isNullOrEmpty(_title);
                      boolean _not_2 = (!_isNullOrEmpty_2);
                      if (_not_2) {
                        Widget _widget_3 = eve.getWidget();
                        String _title_1 = ModuleUtils.getTitle(project, _widget_3);
                        _builder.append(_title_1, "");
                      } else {
                        Widget _widget_4 = eve.getWidget();
                        String _typeName = _widget_4.getTypeName();
                        _builder.append(_typeName, "");
                      }
                    }
                    _builder.append(" <br></br>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("<table border=\"1\">");
                    _builder.newLine();
                    _builder.append("<tr>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<th bgcolor=\"#6495ED\">Property name</th>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<th bgcolor=\"#6495ED\">Property value</th>");
                    _builder.newLine();
                    _builder.append("</tr>");
                    _builder.newLine();
                    _builder.append("<tr>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<td>Search module </td>");
                    _builder.newLine();
                    _builder.append("\t");
                    Property _findProperty = snpet.findProperty("queryOutputModule");
                    final URI uriPath = _findProperty.getModelURI();
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("<td> ");
                    {
                      boolean _notEquals = (!Objects.equal(uriPath, null));
                      if (_notEquals) {
                        _builder.append(" ");
                        String _path = uriPath.path();
                        String _path_1 = uriPath.path();
                        int _indexOf = _path_1.indexOf(".");
                        String _substring = _path.substring(0, _indexOf);
                        _builder.append(_substring, "\t");
                      }
                    }
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("</tr>");
                    _builder.newLine();
                    _builder.append("<tr>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<td>Attributes Selection </td>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<td>");
                    Property _findProperty_1 = snpet.findProperty("queryAttributes");
                    String _value = _findProperty_1.getValue();
                    _builder.append(_value, "\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("</tr>");
                    _builder.newLine();
                    _builder.append("<tr>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<td>Attribute mapping  </td>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<td>");
                    Property _findProperty_2 = snpet.findProperty("queryMappings");
                    String _value_1 = _findProperty_2.getValue();
                    _builder.append(_value_1, "\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("</tr>");
                    _builder.newLine();
                    _builder.append("<tr>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<td>Tab to display </td>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<td>");
                    EList<Property> _properties = snpet.getProperties();
                    Property _get = _properties.get(2);
                    String _value_2 = _get.getValue();
                    _builder.append(_value_2, "\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("</tr>");
                    _builder.newLine();
                    _builder.append("<tr>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<td>Selection mode </td>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<td>");
                    Property _findProperty_3 = snpet.findProperty("selectionMode");
                    String _value_3 = _findProperty_3.getValue();
                    _builder.append(_value_3, "\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("</tr>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<tr>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<td>Run at start </td>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<td>");
                    Property _findProperty_4 = snpet.findProperty("runAtStart");
                    String _value_4 = _findProperty_4.getValue();
                    _builder.append(_value_4, "\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("</tr>");
                    _builder.newLine();
                    _builder.append("</tr>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<tr>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<td>Max row count </td>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<td>");
                    Property _findProperty_5 = snpet.findProperty("max-rows");
                    String _value_5 = _findProperty_5.getValue();
                    _builder.append(_value_5, "\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("</tr>");
                    _builder.newLine();
                    _builder.append("</table> <br></br>");
                    _builder.newLine();
                  }
                }
                searchSnipet.clear();
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        actionEvents.clear();
        _builder.newLineIfNotEmpty();
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence moduleAutoCompleteDesign(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> autoComplDesgns = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    List<Widget> av = this.getListOfWidgetByType(_widget, autoComplDesgns, "AutoCompleteDesign");
    _builder.newLineIfNotEmpty();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(autoComplDesgns);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        {
          for(final Widget autoComplDes : autoComplDesgns) {
            {
              boolean _and = false;
              boolean _notEquals = (!Objects.equal(autoComplDes, null));
              if (!_notEquals) {
                _and = false;
              } else {
                String _propertyValue = autoComplDes.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(_propertyValue);
                boolean _not_1 = (!_isNullOrEmpty_1);
                _and = _not_1;
              }
              if (_and) {
                _builder.append("\t");
                _builder.append("Description: ");
                String _propertyValue_1 = autoComplDes.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                _builder.append(_propertyValue_1, "\t");
                _builder.append(" <br></br>");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.append("List of properties set on the auto-complete design: <br></br>");
            _builder.newLine();
            _builder.append("<table border=\"1\">");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("<th bgcolor=\"#6495ED\">Property name</th>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("<th bgcolor=\"#6495ED\">value</th>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("<td>Sorting Attribute</td>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("<td>");
            String _fetchModuleInformation = this.fetchModuleInformation(autoComplDes, "AutoCompleteDesign", "sortAttribute");
            _builder.append(_fetchModuleInformation, "\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("<td>Max Returned Rows</td>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("<td>");
            String _fetchModuleInformation_1 = this.fetchModuleInformation(autoComplDes, "AutoCompleteDesign", "max-returned");
            _builder.append(_fetchModuleInformation_1, "\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("<td>Dataset </td>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("<td>");
            String _datasetName = ModuleUtils.getDatasetName(project, autoComplDes);
            _builder.append(_datasetName, "\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("</table> <br></br>");
            _builder.newLine();
            _builder.append("List of attributes composing the drop down list of items: <br></br>");
            _builder.newLine();
            _builder.append("<table border=\"1\">");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("<th bgcolor=\"#6495ED\">Line</th>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("<th bgcolor=\"#6495ED\">Attribute 1</th>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("<th bgcolor=\"#6495ED\">Attribute 2</th>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("<th bgcolor=\"#6495ED\">Attribute 3</th>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("\t");
            String title = this.fetchModuleInformation(autoComplDes, "AutoCompleteDesign", "titleAttribute");
            _builder.newLineIfNotEmpty();
            _builder.append("\t ");
            _builder.newLine();
            {
              boolean _isNullOrEmpty_2 = StringExtensions.isNullOrEmpty(title);
              boolean _not_2 = (!_isNullOrEmpty_2);
              if (_not_2) {
                _builder.append("\t");
                _builder.append("<tr>");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td>Title</td>");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td>");
                _builder.append(title, "\t\t");
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td></td>");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td></td>");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("</tr>");
                _builder.newLine();
              }
            }
            _builder.append("\t");
            _builder.newLine();
            {
              EList<Snippet> _snippets = autoComplDes.getSnippets();
              for(final Snippet lineSnip : _snippets) {
                _builder.append("\t");
                _builder.append("<tr>");
                _builder.newLine();
                {
                  boolean _and_1 = false;
                  EList<Snippet> _contents = lineSnip.getContents();
                  boolean _isEmpty = _contents.isEmpty();
                  boolean _not_3 = (!_isEmpty);
                  if (!_not_3) {
                    _and_1 = false;
                  } else {
                    EList<Snippet> _contents_1 = lineSnip.getContents();
                    boolean _checkLineItemEnabled = this.checkLineItemEnabled(_contents_1);
                    _and_1 = _checkLineItemEnabled;
                  }
                  if (_and_1) {
                    _builder.append("\t");
                    _builder.append("<td>Attribute Line ");
                    EList<Snippet> _snippets_1 = autoComplDes.getSnippets();
                    int _indexOf = _snippets_1.indexOf(lineSnip);
                    int _plus = (_indexOf + 1);
                    _builder.append(_plus, "\t");
                    _builder.append("</td> ");
                    _builder.newLineIfNotEmpty();
                    {
                      EList<Snippet> _contents_2 = lineSnip.getContents();
                      for(final Snippet lineItemSnip : _contents_2) {
                        {
                          Property _findProperty = lineItemSnip.findProperty("translation");
                          String _value = _findProperty.getValue();
                          Boolean _valueOf = Boolean.valueOf(_value);
                          if ((_valueOf).booleanValue()) {
                            _builder.append("\t");
                            _builder.append("<td>Label: ");
                            Property _findProperty_1 = lineItemSnip.findProperty("lineAttribute");
                            String _value_1 = _findProperty_1.getValue();
                            _builder.append(_value_1, "\t");
                            _builder.append(" (style: ");
                            Property _findProperty_2 = lineItemSnip.findProperty("cssClass");
                            String _value_2 = _findProperty_2.getValue();
                            _builder.append(_value_2, "\t");
                            _builder.append(")</td>");
                            _builder.newLineIfNotEmpty();
                          } else {
                            _builder.append("\t");
                            _builder.append("<td></td>\t");
                            _builder.newLine();
                          }
                        }
                      }
                    }
                    {
                      EList<Snippet> _contents_3 = lineSnip.getContents();
                      int _size = _contents_3.size();
                      boolean _equals = (_size == 1);
                      if (_equals) {
                        _builder.append("\t");
                        _builder.append("<td></td>");
                        _builder.newLine();
                        _builder.append("\t");
                        _builder.append("<td></td>");
                        _builder.newLine();
                      } else {
                        EList<Snippet> _contents_4 = lineSnip.getContents();
                        int _size_1 = _contents_4.size();
                        boolean _equals_1 = (_size_1 == 2);
                        if (_equals_1) {
                          _builder.append("\t");
                          _builder.append("<td></td>");
                          _builder.newLine();
                        }
                      }
                    }
                  }
                }
                _builder.append("\t");
                _builder.append("</tr>");
                _builder.newLine();
              }
            }
            _builder.append("\t");
            _builder.newLine();
            _builder.append("</table> <br></br>\t");
            _builder.newLine();
          }
        }
        autoComplDesgns.clear();
        _builder.append("\t\t");
        _builder.newLineIfNotEmpty();
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public boolean checkLineItemEnabled(final List<Snippet> lineItems) {
    for (final Snippet lineItemSnipet : lineItems) {
      Property _findProperty = lineItemSnipet.findProperty("translation");
      String _value = _findProperty.getValue();
      Boolean _valueOf = Boolean.valueOf(_value);
      if ((_valueOf).booleanValue()) {
        return true;
      }
    }
    return false;
  }
  
  public boolean checkLabelExistInWidgets(final List<Widget> allWidgets) {
    for (final Widget wid : allWidgets) {
      EList<Translation> _labels = wid.getLabels();
      boolean _isEmpty = _labels.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        return true;
      }
    }
    return false;
  }
  
  public CharSequence moduleTranslations(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> allWidgets = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    this.fetchAllWidgets(_widget, allWidgets);
    _builder.newLineIfNotEmpty();
    {
      boolean _and = false;
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(allWidgets);
      boolean _not = (!_isNullOrEmpty);
      if (!_not) {
        _and = false;
      } else {
        boolean _checkLabelExistInWidgets = this.checkLabelExistInWidgets(allWidgets);
        _and = _checkLabelExistInWidgets;
      }
      if (_and) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        _builder.append("List of translations of the module:<br></br>");
        _builder.newLine();
        _builder.append("<table border=\"1\">");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Widget</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Kind</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Origin</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">English</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">French</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">German</th>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("</tr>");
        _builder.newLine();
        {
          for(final Widget wid : allWidgets) {
            {
              EList<Translation> _labels = wid.getLabels();
              boolean _isEmpty = _labels.isEmpty();
              boolean _not_1 = (!_isEmpty);
              if (_not_1) {
                _builder.append("\t");
                _builder.append("<tr>");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td>");
                {
                  WidgetType _type = wid.getType();
                  String _name = _type.getName();
                  boolean _equals = _name.equals(WidgetTypeConstants.LABEL_DOMAIN);
                  if (_equals) {
                    _builder.append("Domain attribute");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                  } else {
                    WidgetType _type_1 = wid.getType();
                    String _name_1 = _type_1.getName();
                    boolean _equals_1 = _name_1.equals(WidgetTypeConstants.BOX);
                    if (_equals_1) {
                      _builder.append("Box title");
                      _builder.newLineIfNotEmpty();
                      _builder.append("\t");
                      _builder.append("\t");
                    } else {
                      WidgetType _type_2 = wid.getType();
                      String _name_2 = _type_2.getName();
                      boolean _equals_2 = _name_2.equals(WidgetTypeConstants.MODULE);
                      if (_equals_2) {
                        _builder.append("Module title");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                        _builder.append("\t");
                      } else {
                        WidgetType _type_3 = wid.getType();
                        String _name_3 = _type_3.getName();
                        boolean _equals_3 = _name_3.equals(WidgetTypeConstants.TABLE_COLUMN_ITEM);
                        if (_equals_3) {
                          _builder.append("Table item");
                          _builder.newLineIfNotEmpty();
                          _builder.append("\t");
                          _builder.append("\t");
                        } else {
                          WidgetType _type_4 = wid.getType();
                          String _name_4 = _type_4.getName();
                          boolean _equals_4 = _name_4.equals("TableGroup");
                          if (_equals_4) {
                            _builder.append("Table group");
                            _builder.newLineIfNotEmpty();
                            _builder.append("\t");
                            _builder.append("\t");
                          } else {
                            boolean _and_1 = false;
                            WidgetType _type_5 = wid.getType();
                            String _name_5 = _type_5.getName();
                            boolean _equals_5 = _name_5.equals(WidgetTypeConstants.MATRIX_AXIS);
                            if (!_equals_5) {
                              _and_1 = false;
                            } else {
                              String _propertyValue = wid.getPropertyValue("matrixAxis");
                              boolean _equals_6 = _propertyValue.equals("y-axis");
                              _and_1 = _equals_6;
                            }
                            if (_and_1) {
                              _builder.append("Matrix Axis Y");
                              _builder.newLineIfNotEmpty();
                              _builder.append("\t");
                              _builder.append("\t");
                            } else {
                              boolean _and_2 = false;
                              WidgetType _type_6 = wid.getType();
                              String _name_6 = _type_6.getName();
                              boolean _equals_7 = _name_6.equals(WidgetTypeConstants.MATRIX_AXIS);
                              if (!_equals_7) {
                                _and_2 = false;
                              } else {
                                String _propertyValue_1 = wid.getPropertyValue("matrixAxis");
                                boolean _equals_8 = _propertyValue_1.equals("x-axis");
                                _and_2 = _equals_8;
                              }
                              if (_and_2) {
                                _builder.append("Matrix Axis X");
                                _builder.newLineIfNotEmpty();
                                _builder.append("\t");
                                _builder.append("\t");
                              } else {
                                WidgetType _type_7 = wid.getType();
                                String _name_7 = _type_7.getName();
                                boolean _equals_9 = _name_7.equals(WidgetTypeConstants.MATRIX_CONTENTCELLITEM);
                                if (_equals_9) {
                                  _builder.append("Matrix Cell Item");
                                  _builder.newLineIfNotEmpty();
                                  _builder.append("\t");
                                  _builder.append("\t");
                                } else {
                                  boolean _and_3 = false;
                                  WidgetType _type_8 = wid.getType();
                                  String _name_8 = _type_8.getName();
                                  boolean _equals_10 = _name_8.equals(WidgetTypeConstants.MATRIX_CELLITEM);
                                  if (!_equals_10) {
                                    _and_3 = false;
                                  } else {
                                    EObject _eContainer = wid.eContainer();
                                    String _propertyValue_2 = ((Widget) _eContainer).getPropertyValue("matrixCellType");
                                    boolean _equals_11 = _propertyValue_2.equals("last-row");
                                    _and_3 = _equals_11;
                                  }
                                  if (_and_3) {
                                    _builder.append("Matrix Last Row Item");
                                    _builder.newLineIfNotEmpty();
                                    _builder.append("\t");
                                    _builder.append("\t");
                                  } else {
                                    boolean _and_4 = false;
                                    WidgetType _type_9 = wid.getType();
                                    String _name_9 = _type_9.getName();
                                    boolean _equals_12 = _name_9.equals(WidgetTypeConstants.MATRIX_CELLITEM);
                                    if (!_equals_12) {
                                      _and_4 = false;
                                    } else {
                                      EObject _eContainer_1 = wid.eContainer();
                                      String _propertyValue_3 = ((Widget) _eContainer_1).getPropertyValue("matrixCellType");
                                      boolean _equals_13 = _propertyValue_3.equals("last-column");
                                      _and_4 = _equals_13;
                                    }
                                    if (_and_4) {
                                      _builder.append("Matrix Last Column Item");
                                      _builder.newLineIfNotEmpty();
                                      _builder.append("\t");
                                      _builder.append("\t");
                                    } else {
                                      boolean _and_5 = false;
                                      WidgetType _type_10 = wid.getType();
                                      String _name_10 = _type_10.getName();
                                      boolean _equals_14 = _name_10.equals(WidgetTypeConstants.MATRIX_CELLITEM);
                                      if (!_equals_14) {
                                        _and_5 = false;
                                      } else {
                                        EObject _eContainer_2 = wid.eContainer();
                                        String _propertyValue_4 = ((Widget) _eContainer_2).getPropertyValue("matrixCellType");
                                        boolean _equals_15 = _propertyValue_4.equals("last-cell");
                                        _and_5 = _equals_15;
                                      }
                                      if (_and_5) {
                                        _builder.append("Matrix Last Cell Item");
                                        _builder.newLineIfNotEmpty();
                                        _builder.append("\t");
                                        _builder.append("\t");
                                      } else {
                                        WidgetType _type_11 = wid.getType();
                                        String _name_11 = _type_11.getName();
                                        boolean _equals_16 = _name_11.equals(WidgetTypeConstants.MATRIX_EXTRACOLUMNITEM);
                                        if (_equals_16) {
                                          _builder.append("Matrix Additonial Column");
                                          _builder.newLineIfNotEmpty();
                                          _builder.append("\t");
                                          _builder.append("\t");
                                        } else {
                                          WidgetType _type_12 = wid.getType();
                                          String _name_12 = _type_12.getName();
                                          boolean _equals_17 = _name_12.equals(WidgetTypeConstants.TABLE_COLUMN);
                                          if (_equals_17) {
                                            _builder.append("Table Column");
                                            _builder.newLineIfNotEmpty();
                                            _builder.append("\t");
                                            _builder.append("\t");
                                          } else {
                                            String _typeName = wid.getTypeName();
                                            _builder.append(_typeName, "\t\t");
                                          }
                                        }
                                      }
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td>");
                {
                  WidgetType _type_13 = wid.getType();
                  boolean _translationSupported = _type_13.translationSupported();
                  if (_translationSupported) {
                    String _transValue = this.getTransValue(wid);
                    _builder.append(_transValue, "\t\t");
                  }
                }
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td>");
                {
                  boolean _isDomainWidget = wid.isDomainWidget();
                  if (_isDomainWidget) {
                    _builder.append("Domain");
                  } else {
                    _builder.append("Local");
                  }
                }
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td>");
                {
                  EList<Translation> _labels_1 = wid.getLabels();
                  String _respectiveTranslation = this.getRespectiveTranslation(_labels_1, "en");
                  boolean _notEquals = (!Objects.equal(_respectiveTranslation, null));
                  if (_notEquals) {
                    EList<Translation> _labels_2 = wid.getLabels();
                    String _respectiveTranslation_1 = this.getRespectiveTranslation(_labels_2, "en");
                    _builder.append(_respectiveTranslation_1, "\t\t");
                  } else {
                    String _fetchTranslationValue = this.fetchTranslationValue(wid, project);
                    _builder.append(_fetchTranslationValue, "\t\t");
                  }
                }
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td>");
                {
                  EList<Translation> _labels_3 = wid.getLabels();
                  String _respectiveTranslation_2 = this.getRespectiveTranslation(_labels_3, "fr");
                  boolean _notEquals_1 = (!Objects.equal(_respectiveTranslation_2, null));
                  if (_notEquals_1) {
                    EList<Translation> _labels_4 = wid.getLabels();
                    String _respectiveTranslation_3 = this.getRespectiveTranslation(_labels_4, "fr");
                    _builder.append(_respectiveTranslation_3, "\t\t");
                  } else {
                    String _fetchTranslationValue_1 = this.fetchTranslationValue(wid, project);
                    _builder.append(_fetchTranslationValue_1, "\t\t");
                  }
                }
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td>");
                {
                  EList<Translation> _labels_5 = wid.getLabels();
                  String _respectiveTranslation_4 = this.getRespectiveTranslation(_labels_5, "de");
                  boolean _notEquals_2 = (!Objects.equal(_respectiveTranslation_4, null));
                  if (_notEquals_2) {
                    EList<Translation> _labels_6 = wid.getLabels();
                    String _respectiveTranslation_5 = this.getRespectiveTranslation(_labels_6, "de");
                    _builder.append(_respectiveTranslation_5, "\t\t");
                  } else {
                    String _fetchTranslationValue_2 = this.fetchTranslationValue(wid, project);
                    _builder.append(_fetchTranslationValue_2, "\t\t");
                  }
                }
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("</tr>\t");
                _builder.newLine();
              }
            }
          }
        }
        _builder.append("</table> <br></br>\t");
        _builder.newLine();
        allWidgets.clear();
        _builder.newLineIfNotEmpty();
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public String getRespectiveTranslation(final List<Translation> translations, final String type) {
    for (final Translation transMessage : translations) {
      String _language = transMessage.getLanguage();
      boolean _equals = _language.equals(type);
      if (_equals) {
        return transMessage.getMessage();
      }
    }
    return null;
  }
  
  public void fetchEventSnipet(final Event actionEvent, final List<Snippet> snipet, final String type) {
    EList<Snippet> _snippets = actionEvent.getSnippets();
    for (final Snippet spi : _snippets) {
      String _typeName = spi.getTypeName();
      boolean _equals = _typeName.equals(type);
      if (_equals) {
        snipet.add(spi);
      }
    }
  }
  
  public CharSequence generalDescription(final Model model, final IOfsProject project, final String type) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Widget _widget = model.getWidget();
      String _propertyValue = _widget.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
      boolean _notEquals = (!Objects.equal(_propertyValue, null));
      if (_notEquals) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        Widget _widget_1 = model.getWidget();
        String _propertyValue_1 = _widget_1.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
        String docText = _propertyValue_1.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
        _builder.newLineIfNotEmpty();
        {
          boolean _and = false;
          boolean _notEquals_1 = (!Objects.equal(docText, null));
          if (!_notEquals_1) {
            _and = false;
          } else {
            boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(docText);
            boolean _not = (!_isNullOrEmpty);
            _and = _not;
          }
          if (_and) {
            _builder.append("Description: ");
            _builder.append(docText, "");
            _builder.append(" <br>");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("  <br>");
        _builder.newLineIfNotEmpty();
        {
          boolean _equals = type.equals(WidgetTypeConstants.MODULE);
          if (_equals) {
            _builder.append("Model Path: <I>");
            Resource _eResource = model.eResource();
            URI _uRI = _eResource.getURI();
            String _path = _uRI.path();
            Resource _eResource_1 = model.eResource();
            URI _uRI_1 = _eResource_1.getURI();
            String _path_1 = _uRI_1.path();
            int _length = _path_1.length();
            int _minus = (_length - 7);
            String _substring = _path.substring(0, _minus);
            _builder.append(_substring, "");
            _builder.append("</I>  <br>");
            _builder.newLineIfNotEmpty();
          } else {
            boolean _equals_1 = type.equals(WidgetTypeConstants.FRAGMENT);
            if (_equals_1) {
              _builder.append("Model Path: <I>");
              Resource _eResource_2 = model.eResource();
              URI _uRI_2 = _eResource_2.getURI();
              String _path_2 = _uRI_2.path();
              Resource _eResource_3 = model.eResource();
              URI _uRI_3 = _eResource_3.getURI();
              String _path_3 = _uRI_3.path();
              int _length_1 = _path_3.length();
              int _minus_1 = (_length_1 - 9);
              String _substring_1 = _path_2.substring(0, _minus_1);
              _builder.append(_substring_1, "");
              _builder.append("</I>  <br>");
              _builder.newLineIfNotEmpty();
            }
          }
        }
        _builder.append("<br>");
        _builder.newLineIfNotEmpty();
        _builder.append("List of properties set on the module: <br><br>");
        _builder.newLine();
        _builder.append("<table border=\"1\">");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Property Name</th>");
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
        _builder.append("<td>Dataset </td>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<td>");
        Widget _widget_2 = model.getWidget();
        String _datasetName = ModuleUtils.getDatasetName(project, _widget_2);
        _builder.append(_datasetName, "\t\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("</tr>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<td>Based on Class </td>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<td>");
        Widget _widget_3 = model.getWidget();
        String _datasetBaseClassName = ModuleUtils.getDatasetBaseClassName(project, _widget_3);
        _builder.append(_datasetBaseClassName, "\t\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("</tr>");
        _builder.newLine();
        {
          boolean _equals_2 = type.equals(WidgetTypeConstants.MODULE);
          if (_equals_2) {
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>Title </td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            Widget _widget_4 = model.getWidget();
            String _title = ModuleUtils.getTitle(project, _widget_4);
            _builder.append(_title, "\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>Collapse </td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            Widget _widget_5 = model.getWidget();
            String _fetchModuleInformation = this.fetchModuleInformation(_widget_5, WidgetTypeConstants.MODULE, "collapsed");
            _builder.append(_fetchModuleInformation, "\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>Collapsible </td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            Widget _widget_6 = model.getWidget();
            String _fetchModuleInformation_1 = this.fetchModuleInformation(_widget_6, WidgetTypeConstants.MODULE, "collapsible");
            _builder.append(_fetchModuleInformation_1, "\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence generalIncludes(final Model model, final String type, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Widget _widget = model.getWidget();
      WidgetType _type = _widget.getType();
      String _name = _type.getName();
      boolean _equals = _name.equals(type);
      if (_equals) {
        List<Widget> includeSources = CollectionLiterals.<Widget>newArrayList();
        _builder.append("\t");
        _builder.newLineIfNotEmpty();
        Widget _widget_1 = model.getWidget();
        Widget _widgetByType = this.getWidgetByType(_widget_1, type);
        this.fetchIncludeSources(_widgetByType, WidgetTypeConstants.INCLUDE, includeSources, type);
        _builder.newLineIfNotEmpty();
        List<Widget> xspInclPaths = CollectionLiterals.<Widget>newArrayList();
        _builder.append("\t");
        _builder.newLineIfNotEmpty();
        Widget _widget_2 = model.getWidget();
        Widget _widgetByType_1 = this.getWidgetByType(_widget_2, type);
        this.fetchIncludeSources(_widgetByType_1, "IncludeXSP", xspInclPaths, type);
        _builder.newLineIfNotEmpty();
        List<Widget> extPageOrImgUrls = CollectionLiterals.<Widget>newArrayList();
        _builder.append("\t");
        _builder.newLineIfNotEmpty();
        Widget _widget_3 = model.getWidget();
        Widget _widgetByType_2 = this.getWidgetByType(_widget_3, type);
        this.fetchIncludeSources(_widgetByType_2, WidgetTypeConstants.EXTERNAL_INCLUDE_WIDGET, extPageOrImgUrls, type);
        _builder.newLineIfNotEmpty();
        {
          boolean _or = false;
          boolean _or_1 = false;
          boolean _and = false;
          boolean _notEquals = (!Objects.equal(includeSources, null));
          if (!_notEquals) {
            _and = false;
          } else {
            boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(includeSources);
            boolean _not = (!_isNullOrEmpty);
            _and = _not;
          }
          if (_and) {
            _or_1 = true;
          } else {
            boolean _and_1 = false;
            boolean _notEquals_1 = (!Objects.equal(xspInclPaths, null));
            if (!_notEquals_1) {
              _and_1 = false;
            } else {
              boolean _isNullOrEmpty_1 = IterableExtensions.isNullOrEmpty(xspInclPaths);
              boolean _not_1 = (!_isNullOrEmpty_1);
              _and_1 = _not_1;
            }
            _or_1 = _and_1;
          }
          if (_or_1) {
            _or = true;
          } else {
            boolean _and_2 = false;
            boolean _notEquals_2 = (!Objects.equal(extPageOrImgUrls, null));
            if (!_notEquals_2) {
              _and_2 = false;
            } else {
              boolean _isNullOrEmpty_2 = IterableExtensions.isNullOrEmpty(extPageOrImgUrls);
              boolean _not_2 = (!_isNullOrEmpty_2);
              _and_2 = _not_2;
            }
            _or = _and_2;
          }
          if (_or) {
            CharSequence _htmlOpening = this.htmlOpening();
            _builder.append(_htmlOpening, "");
            _builder.newLineIfNotEmpty();
            _builder.append("List of Includes: <br><br>");
            _builder.newLine();
            _builder.append("<table border=\"1\">");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("<th bgcolor=\"#6495ED\">Include</th>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("<th bgcolor=\"#6495ED\">Contained In</th>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("<th bgcolor=\"#6495ED\">Description</th>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            {
              for(final Widget inclWid : includeSources) {
                _builder.append("\t");
                _builder.append("<tr>");
                _builder.newLine();
                _builder.append("\t");
                Property _findProperty = inclWid.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
                Model _model = _findProperty.getModel();
                Resource _eResource = _model.eResource();
                URI _uRI = _eResource.getURI();
                String widSrc = _uRI.path();
                _builder.newLineIfNotEmpty();
                {
                  boolean _and_3 = false;
                  boolean _equals_1 = type.equals(WidgetTypeConstants.MODULE);
                  if (!_equals_1) {
                    _and_3 = false;
                  } else {
                    boolean _endsWith = widSrc.endsWith("module");
                    _and_3 = _endsWith;
                  }
                  if (_and_3) {
                    _builder.append("\t");
                    _builder.append("<td>Module: ");
                    int _lastIndexOf = widSrc.lastIndexOf(".");
                    String _substring = widSrc.substring(0, _lastIndexOf);
                    _builder.append(_substring, "\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                  } else {
                    boolean _and_4 = false;
                    boolean _equals_2 = type.equals(WidgetTypeConstants.FRAGMENT);
                    if (!_equals_2) {
                      _and_4 = false;
                    } else {
                      boolean _endsWith_1 = widSrc.endsWith("fragment");
                      _and_4 = _endsWith_1;
                    }
                    if (_and_4) {
                      _builder.append("\t");
                      _builder.append("<td>Fragment: ");
                      int _lastIndexOf_1 = widSrc.lastIndexOf(".");
                      String _substring_1 = widSrc.substring(0, _lastIndexOf_1);
                      _builder.append(_substring_1, "\t");
                      _builder.append("</td>");
                      _builder.newLineIfNotEmpty();
                    }
                  }
                }
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td>");
                Widget _parent = inclWid.getParent();
                String _fetchParent = this.fetchParent(_parent, project);
                _builder.append(_fetchParent, "\t\t");
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td>");
                String _propertyValue = inclWid.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                _builder.append(_propertyValue, "\t\t");
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("</tr>");
                _builder.newLine();
              }
            }
            {
              for(final Widget xspInclude : xspInclPaths) {
                _builder.append("\t");
                _builder.append("<tr>\t\t\t");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td>XSP Page: ");
                String _propertyValue_1 = xspInclude.getPropertyValue("xsp-path");
                _builder.append(_propertyValue_1, "\t\t");
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td>");
                Widget _parent_1 = xspInclude.getParent();
                String _fetchParent_1 = this.fetchParent(_parent_1, project);
                _builder.append(_fetchParent_1, "\t\t");
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td>");
                String _propertyValue_2 = xspInclude.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                _builder.append(_propertyValue_2, "\t\t");
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("</tr>");
                _builder.newLine();
              }
            }
            {
              for(final Widget extOrImgUrl : extPageOrImgUrls) {
                _builder.append("\t");
                _builder.append("<tr>\t\t\t");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td>External Page/Image: ");
                String _propertyValue_3 = extOrImgUrl.getPropertyValue(PropertyTypeConstants.URL);
                _builder.append(_propertyValue_3, "\t\t");
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td>");
                Widget _parent_2 = extOrImgUrl.getParent();
                String _fetchParent_2 = this.fetchParent(_parent_2, project);
                _builder.append(_fetchParent_2, "\t\t");
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td>");
                String _propertyValue_4 = extOrImgUrl.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                _builder.append(_propertyValue_4, "\t\t");
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("</tr>");
                _builder.newLine();
              }
            }
            _builder.append("</table>");
            _builder.newLine();
            CharSequence _htmlClosing = this.htmlClosing();
            _builder.append(_htmlClosing, "");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  public void fetchIncludeSources(final Widget widget, final String widgetType, final List<Widget> includeSources, final String type) {
    WidgetType _type = widget.getType();
    String _name = _type.getName();
    boolean _equals = _name.equals(widgetType);
    if (_equals) {
      Model _model = widget.getModel();
      boolean _notEquals = (!Objects.equal(_model, null));
      if (_notEquals) {
        boolean _equals_1 = widgetType.equals(WidgetTypeConstants.INCLUDE);
        if (_equals_1) {
          Property _findProperty = widget.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
          Model mod = _findProperty.getModel();
          boolean _and = false;
          boolean _and_1 = false;
          boolean _and_2 = false;
          boolean _notEquals_1 = (!Objects.equal(mod, null));
          if (!_notEquals_1) {
            _and_2 = false;
          } else {
            Resource _eResource = mod.eResource();
            boolean _notEquals_2 = (!Objects.equal(_eResource, null));
            _and_2 = _notEquals_2;
          }
          if (!_and_2) {
            _and_1 = false;
          } else {
            Resource _eResource_1 = mod.eResource();
            URI _uRI = _eResource_1.getURI();
            boolean _notEquals_3 = (!Objects.equal(_uRI, null));
            _and_1 = _notEquals_3;
          }
          if (!_and_1) {
            _and = false;
          } else {
            Resource _eResource_2 = mod.eResource();
            URI _uRI_1 = _eResource_2.getURI();
            String _path = _uRI_1.path();
            boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(_path);
            boolean _not = (!_isNullOrEmpty);
            _and = _not;
          }
          if (_and) {
            Resource _eResource_3 = mod.eResource();
            URI _uRI_2 = _eResource_3.getURI();
            String widSrc = _uRI_2.path();
            String _lowerCase = type.toLowerCase();
            boolean _endsWith = widSrc.endsWith(_lowerCase);
            if (_endsWith) {
              includeSources.add(widget);
            }
          }
        } else {
          includeSources.add(widget);
        }
      }
    } else {
      EList<Widget> _contents = widget.getContents();
      boolean _isEmpty = _contents.isEmpty();
      boolean _not_1 = (!_isEmpty);
      if (_not_1) {
        EList<Widget> _contents_1 = widget.getContents();
        for (final Widget wid : _contents_1) {
          this.fetchIncludeSources(wid, widgetType, includeSources, type);
        }
      }
    }
  }
  
  public String fetchParent(final Widget widget, final IOfsProject project) {
    String _xifexpression = null;
    boolean _notEquals = (!Objects.equal(widget, null));
    if (_notEquals) {
      String _xifexpression_1 = null;
      String _typeName = widget.getTypeName();
      boolean _equals = _typeName.equals(WidgetTypeConstants.CONDITIONAL_BODY);
      if (_equals) {
        String cond = widget.getPropertyValue("technicalName");
        Widget _parent = widget.getParent();
        String conditional = _parent.getPropertyValue("technicalName");
        boolean _or = false;
        boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(conditional);
        boolean _not = (!_isNullOrEmpty);
        if (_not) {
          _or = true;
        } else {
          boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(cond);
          boolean _not_1 = (!_isNullOrEmpty_1);
          _or = _not_1;
        }
        if (_or) {
          String _xifexpression_2 = null;
          boolean _isNullOrEmpty_2 = StringExtensions.isNullOrEmpty(conditional);
          boolean _not_2 = (!_isNullOrEmpty_2);
          if (_not_2) {
            _xifexpression_2 = conditional;
          } else {
            _xifexpression_2 = "";
          }
          String _plus = ("Condition: " + _xifexpression_2);
          String _xifexpression_3 = null;
          boolean _and = false;
          boolean _isNullOrEmpty_3 = StringExtensions.isNullOrEmpty(conditional);
          boolean _not_3 = (!_isNullOrEmpty_3);
          if (!_not_3) {
            _and = false;
          } else {
            boolean _isNullOrEmpty_4 = StringExtensions.isNullOrEmpty(cond);
            boolean _not_4 = (!_isNullOrEmpty_4);
            _and = _not_4;
          }
          if (_and) {
            _xifexpression_3 = ".";
          } else {
            _xifexpression_3 = "";
          }
          String _plus_1 = (_plus + _xifexpression_3);
          String _xifexpression_4 = null;
          boolean _isNullOrEmpty_5 = StringExtensions.isNullOrEmpty(cond);
          boolean _not_5 = (!_isNullOrEmpty_5);
          if (_not_5) {
            _xifexpression_4 = cond;
          } else {
            _xifexpression_4 = "";
          }
          return (_plus_1 + _xifexpression_4);
        } else {
          return "";
        }
      } else {
        String _xifexpression_5 = null;
        String _typeName_1 = widget.getTypeName();
        boolean _equals_1 = _typeName_1.equals(WidgetTypeConstants.TAB);
        if (_equals_1) {
          String cond_1 = widget.getPropertyValue("tab-name");
          Widget _parent_1 = widget.getParent();
          String tabPaneName = _parent_1.getPropertyValue("name");
          boolean _or_1 = false;
          boolean _isNullOrEmpty_6 = StringExtensions.isNullOrEmpty(cond_1);
          boolean _not_6 = (!_isNullOrEmpty_6);
          if (_not_6) {
            _or_1 = true;
          } else {
            boolean _isNullOrEmpty_7 = StringExtensions.isNullOrEmpty(tabPaneName);
            boolean _not_7 = (!_isNullOrEmpty_7);
            _or_1 = _not_7;
          }
          if (_or_1) {
            String _xifexpression_6 = null;
            boolean _isNullOrEmpty_8 = StringExtensions.isNullOrEmpty(tabPaneName);
            boolean _not_8 = (!_isNullOrEmpty_8);
            if (_not_8) {
              _xifexpression_6 = tabPaneName;
            } else {
              _xifexpression_6 = "";
            }
            String _plus_2 = ("Tab: " + _xifexpression_6);
            String _xifexpression_7 = null;
            boolean _and_1 = false;
            boolean _isNullOrEmpty_9 = StringExtensions.isNullOrEmpty(tabPaneName);
            boolean _not_9 = (!_isNullOrEmpty_9);
            if (!_not_9) {
              _and_1 = false;
            } else {
              boolean _isNullOrEmpty_10 = StringExtensions.isNullOrEmpty(cond_1);
              boolean _not_10 = (!_isNullOrEmpty_10);
              _and_1 = _not_10;
            }
            if (_and_1) {
              _xifexpression_7 = ".";
            } else {
              _xifexpression_7 = "";
            }
            String _plus_3 = (_plus_2 + _xifexpression_7);
            String _xifexpression_8 = null;
            boolean _isNullOrEmpty_11 = StringExtensions.isNullOrEmpty(cond_1);
            boolean _not_11 = (!_isNullOrEmpty_11);
            if (_not_11) {
              _xifexpression_8 = cond_1;
            } else {
              _xifexpression_8 = "";
            }
            return (_plus_3 + _xifexpression_8);
          } else {
            String _fetchTranslationValue = this.fetchTranslationValue(widget, project);
            boolean _isNullOrEmpty_12 = StringExtensions.isNullOrEmpty(_fetchTranslationValue);
            boolean _not_12 = (!_isNullOrEmpty_12);
            if (_not_12) {
              return this.fetchTranslationValue(widget, project);
            } else {
              return "";
            }
          }
        } else {
          Widget _parent_2 = widget.getParent();
          _xifexpression_5 = this.fetchParent(_parent_2, project);
        }
        _xifexpression_1 = _xifexpression_5;
      }
      _xifexpression = _xifexpression_1;
    } else {
      return "";
    }
    return _xifexpression;
  }
  
  public List<Widget> getListOfWidgetByType(final Widget widget, final List<Widget> widgets, final String widgetType) {
    WidgetType _type = widget.getType();
    String _name = _type.getName();
    boolean _equals = _name.equals(widgetType);
    if (_equals) {
      Widget _parent = widget.getParent();
      EList<Widget> _contents = _parent.getContents();
      for (final Widget wid : _contents) {
        boolean _and = false;
        Model _model = wid.getModel();
        boolean _notEquals = (!Objects.equal(_model, null));
        if (!_notEquals) {
          _and = false;
        } else {
          boolean _contains = widgets.contains(wid);
          boolean _not = (!_contains);
          _and = _not;
        }
        if (_and) {
          widgets.add(wid);
        }
      }
      return widgets;
    } else {
      EList<Widget> _contents_1 = widget.getContents();
      boolean _isEmpty = _contents_1.isEmpty();
      boolean _not_1 = (!_isEmpty);
      if (_not_1) {
        EList<Widget> _contents_2 = widget.getContents();
        for (final Widget wid_1 : _contents_2) {
          this.getListOfWidgetByType(wid_1, widgets, widgetType);
        }
      }
    }
    return widgets;
  }
  
  public Widget getWidgetByType(final Widget widget, final String widgetType) {
    Object _xifexpression = null;
    WidgetType _type = widget.getType();
    String _name = _type.getName();
    boolean _equals = _name.equals(widgetType);
    if (_equals) {
      Model _model = widget.getModel();
      boolean _notEquals = (!Objects.equal(_model, null));
      if (_notEquals) {
        return widget;
      }
    } else {
      Object _xifexpression_1 = null;
      EList<Widget> _contents = widget.getContents();
      boolean _isEmpty = _contents.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        EList<Widget> _contents_1 = widget.getContents();
        for (final Widget wid : _contents_1) {
          return this.getWidgetByType(wid, widgetType);
        }
      }
      _xifexpression = _xifexpression_1;
    }
    return ((Widget)_xifexpression);
  }
  
  public List<ModuleAttribute> computeAttributes(final IOfsProject project, final List<Widget> attributeList) {
    List<ModuleAttribute> modAttributes = CollectionLiterals.<ModuleAttribute>newArrayList();
    List<Widget> labelDoms = this.fetchLabelDomains(attributeList);
    for (final Widget attR : attributeList) {
      {
        ModuleAttribute modAttr = new ModuleAttribute();
        String _fetchRespLabelDomains = this.fetchRespLabelDomains(attR, labelDoms, project);
        modAttr.setLabel(_fetchRespLabelDomains);
        WidgetType _type = attR.getType();
        String _name = _type.getName();
        boolean _equals = _name.equals(WidgetTypeConstants.INCLUDE);
        if (_equals) {
          Property _findProperty = attR.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
          Model _model = _findProperty.getModel();
          Resource _eResource = _model.eResource();
          URI _uRI = _eResource.getURI();
          String _path = _uRI.path();
          boolean _endsWith = _path.endsWith("fragment");
          if (_endsWith) {
            final String domAssociation = attR.getPropertyValue(PropertyTypeConstants.DOMAIN_ASSOCIATION);
            Widget includeModelAttr = this.fetchAttributeFromInclModel(attR);
            String _xifexpression = null;
            boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(domAssociation);
            boolean _not = (!_isNullOrEmpty);
            if (_not) {
              String _xifexpression_1 = null;
              boolean _and = false;
              boolean _notEquals = (!Objects.equal(includeModelAttr, null));
              if (!_notEquals) {
                _and = false;
              } else {
                String _propertyValue = includeModelAttr.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
                boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(_propertyValue);
                boolean _not_1 = (!_isNullOrEmpty_1);
                _and = _not_1;
              }
              if (_and) {
                _xifexpression_1 = includeModelAttr.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
              } else {
                _xifexpression_1 = "";
              }
              _xifexpression = ((domAssociation + ".") + _xifexpression_1);
            }
            modAttr.setDomainReference(_xifexpression);
          }
        } else {
          WidgetType _type_1 = attR.getType();
          String _name_1 = _type_1.getName();
          boolean _equals_1 = _name_1.equals(WidgetTypeConstants.TABLE_COLUMN);
          if (_equals_1) {
            modAttr.setModuleAttrTabletype("Table column");
            String _xifexpression_2 = null;
            String _propertyValue_1 = attR.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
            boolean _isNullOrEmpty_2 = StringExtensions.isNullOrEmpty(_propertyValue_1);
            boolean _not_2 = (!_isNullOrEmpty_2);
            if (_not_2) {
              _xifexpression_2 = attR.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
            } else {
              _xifexpression_2 = "";
            }
            modAttr.setDomainReference(_xifexpression_2);
          } else {
            WidgetType _type_2 = attR.getType();
            String _name_2 = _type_2.getName();
            boolean _equals_2 = _name_2.equals("TableGroup");
            if (_equals_2) {
              modAttr.setModuleAttrTabletype("Grouping");
              String _xifexpression_3 = null;
              String _propertyValue_2 = attR.getPropertyValue("group-column-name");
              boolean _isNullOrEmpty_3 = StringExtensions.isNullOrEmpty(_propertyValue_2);
              boolean _not_3 = (!_isNullOrEmpty_3);
              if (_not_3) {
                _xifexpression_3 = attR.getPropertyValue("group-column-name");
              } else {
                _xifexpression_3 = "";
              }
              modAttr.setDomainReference(_xifexpression_3);
              String _propertyValue_3 = attR.getPropertyValue("group-sorting-column-name");
              boolean _isNullOrEmpty_4 = StringExtensions.isNullOrEmpty(_propertyValue_3);
              if (_isNullOrEmpty_4) {
                ModuleAttribute modAttr1 = new ModuleAttribute();
                String _fetchRespLabelDomains_1 = this.fetchRespLabelDomains(attR, labelDoms, project);
                modAttr1.setLabel(_fetchRespLabelDomains_1);
                modAttr1.setModuleAttrTabletype("Group sorting");
                String _xifexpression_4 = null;
                String _propertyValue_4 = attR.getPropertyValue("group-sorting-column-name");
                boolean _isNullOrEmpty_5 = StringExtensions.isNullOrEmpty(_propertyValue_4);
                boolean _not_4 = (!_isNullOrEmpty_5);
                if (_not_4) {
                  _xifexpression_4 = attR.getPropertyValue("group-sorting-column-name");
                } else {
                  _xifexpression_4 = "";
                }
                modAttr1.setDomainReference(_xifexpression_4);
                String _fetchParent = this.fetchParent(attR, project);
                modAttr1.setContainedIn(_fetchParent);
                modAttributes.add(modAttr);
              }
            } else {
              WidgetType _type_3 = attR.getType();
              String _name_3 = _type_3.getName();
              boolean _equals_3 = _name_3.equals("TableSort");
              if (_equals_3) {
                modAttr.setModuleAttrTabletype("Sorting");
                String _xifexpression_5 = null;
                String _propertyValue_5 = attR.getPropertyValue("sort-column-name");
                boolean _isNullOrEmpty_6 = StringExtensions.isNullOrEmpty(_propertyValue_5);
                boolean _not_5 = (!_isNullOrEmpty_6);
                if (_not_5) {
                  _xifexpression_5 = attR.getPropertyValue("sort-column-name");
                } else {
                  _xifexpression_5 = "";
                }
                modAttr.setDomainReference(_xifexpression_5);
              } else {
                WidgetType _type_4 = attR.getType();
                String _name_4 = _type_4.getName();
                boolean _equals_4 = _name_4.equals("TableExtra");
                if (_equals_4) {
                  modAttr.setModuleAttrTabletype("Extra column");
                  String _xifexpression_6 = null;
                  String _propertyValue_6 = attR.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
                  boolean _isNullOrEmpty_7 = StringExtensions.isNullOrEmpty(_propertyValue_6);
                  boolean _not_6 = (!_isNullOrEmpty_7);
                  if (_not_6) {
                    _xifexpression_6 = attR.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
                  } else {
                    _xifexpression_6 = "";
                  }
                  modAttr.setDomainReference(_xifexpression_6);
                } else {
                  WidgetType _type_5 = attR.getType();
                  String _name_5 = _type_5.getName();
                  boolean _equals_5 = _name_5.equals("TableAggregate");
                  if (_equals_5) {
                    modAttr.setModuleAttrTabletype("Computed column");
                    String _propertyValue_7 = attR.getPropertyValue("aggregate-column-name");
                    modAttr.setDomainReference(_propertyValue_7);
                  } else {
                    WidgetType _type_6 = attR.getType();
                    String _name_6 = _type_6.getName();
                    boolean _equals_6 = _name_6.equals("TableKeepFilter");
                    if (_equals_6) {
                      modAttr.setModuleAttrTabletype("Keep Filter column");
                      String _propertyValue_8 = attR.getPropertyValue("keep-filter-column-name");
                      modAttr.setDomainReference(_propertyValue_8);
                    } else {
                      String _xifexpression_7 = null;
                      String _propertyValue_9 = attR.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
                      boolean _isNullOrEmpty_8 = StringExtensions.isNullOrEmpty(_propertyValue_9);
                      boolean _not_7 = (!_isNullOrEmpty_8);
                      if (_not_7) {
                        _xifexpression_7 = attR.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
                      } else {
                        _xifexpression_7 = "";
                      }
                      modAttr.setDomainReference(_xifexpression_7);
                    }
                  }
                }
              }
            }
          }
        }
        String _fetchParent_1 = this.fetchParent(attR, project);
        modAttr.setContainedIn(_fetchParent_1);
        modAttributes.add(modAttr);
      }
    }
    Iterable<ModuleAttribute> _filterNull = IterableExtensions.<ModuleAttribute>filterNull(modAttributes);
    final Function1<ModuleAttribute, String> _function = new Function1<ModuleAttribute, String>() {
      public String apply(final ModuleAttribute e) {
        String _xifexpression = null;
        String _domainReference = e.getDomainReference();
        boolean _notEquals = (!Objects.equal(_domainReference, null));
        if (_notEquals) {
          _xifexpression = e.getDomainReference();
        }
        return _xifexpression;
      }
    };
    return IterableExtensions.<ModuleAttribute, String>sortBy(_filterNull, _function);
  }
  
  public List<ModuleAttribute> computeXAttributes(final IOfsProject project, final List<Widget> attributeList) {
    List<ModuleAttribute> modAttributes = CollectionLiterals.<ModuleAttribute>newArrayList();
    List<Widget> labelDoms = this.fetchLabelDomains(attributeList);
    for (final Widget attR : attributeList) {
      {
        ModuleAttribute modAttr = new ModuleAttribute();
        String _fetchRespLabelDomains = this.fetchRespLabelDomains(attR, labelDoms, project);
        modAttr.setLabel(_fetchRespLabelDomains);
        WidgetType _type = attR.getType();
        String _name = _type.getName();
        boolean _equals = _name.equals(WidgetTypeConstants.INCLUDE);
        if (_equals) {
          Property _findProperty = attR.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
          Model _model = _findProperty.getModel();
          Resource _eResource = _model.eResource();
          URI _uRI = _eResource.getURI();
          String _path = _uRI.path();
          boolean _endsWith = _path.endsWith("fragment");
          if (_endsWith) {
            final String domAssociation = attR.getPropertyValue(PropertyTypeConstants.DOMAIN_ASSOCIATION);
            Widget includeModelAttr = this.fetchAttributeFromInclModel(attR);
            String _xifexpression = null;
            boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(domAssociation);
            boolean _not = (!_isNullOrEmpty);
            if (_not) {
              String _xifexpression_1 = null;
              boolean _notEquals = (!Objects.equal(includeModelAttr, null));
              if (_notEquals) {
                _xifexpression_1 = includeModelAttr.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
              }
              _xifexpression = ((domAssociation + ".") + _xifexpression_1);
            }
            modAttr.setDomainReference(_xifexpression);
          }
        } else {
          String _propertyValue = attR.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
          modAttr.setDomainReference(_propertyValue);
        }
        String _fetchParent = this.fetchParent(attR, project);
        modAttr.setContainedIn(_fetchParent);
        String _propertyValue_1 = attR.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
        modAttr.setDescription(_propertyValue_1);
        String _xifexpression_2 = null;
        EList<Translation> _labels = attR.getLabels();
        boolean _isNullOrEmpty_1 = IterableExtensions.isNullOrEmpty(_labels);
        boolean _not_1 = (!_isNullOrEmpty_1);
        if (_not_1) {
          EList<Translation> _labels_1 = attR.getLabels();
          _xifexpression_2 = this.getRespectiveTranslation(_labels_1, "en");
        }
        modAttr.setTranslation(_xifexpression_2);
        String _businessTypeName = ModuleUtils.getBusinessTypeName(project, attR);
        modAttr.setDataType(_businessTypeName);
        String _propertyValue_2 = attR.getPropertyValue(PropertyTypeConstants.TYPE);
        modAttr.setType(_propertyValue_2);
        String _propertyValue_3 = attR.getPropertyValue("displayFormat");
        modAttr.setDisplayFormat(_propertyValue_3);
        String _propertyValue_4 = attR.getPropertyValue(PropertyTypeConstants.EDITABLE);
        modAttr.setEditable(_propertyValue_4);
        String _propertyValue_5 = attR.getPropertyValue(PropertyTypeConstants.ENABLED);
        boolean _notEquals_1 = (!Objects.equal(_propertyValue_5, null));
        if (_notEquals_1) {
          String _xifexpression_3 = null;
          String _propertyValue_6 = attR.getPropertyValue(PropertyTypeConstants.ENABLED);
          Boolean _valueOf = Boolean.valueOf(_propertyValue_6);
          if ((_valueOf).booleanValue()) {
            _xifexpression_3 = "Yes";
          } else {
            _xifexpression_3 = "No";
          }
          modAttr.setEnable(_xifexpression_3);
        } else {
          String _propertyValue_7 = attR.getPropertyValue(PropertyTypeConstants.ENABLED_IS_BASED_ON);
          modAttr.setEnable(_propertyValue_7);
        }
        String _propertyValue_8 = attR.getPropertyValue(PropertyTypeConstants.REQUIRED);
        modAttr.setRequired(_propertyValue_8);
        String _propertyValue_9 = attR.getPropertyValue(PropertyTypeConstants.READABLE);
        modAttr.setReadable(_propertyValue_9);
        String _propertyValue_10 = attR.getPropertyValue(PropertyTypeConstants.MAX_CHARACTERS);
        modAttr.setMaximumCharacters(_propertyValue_10);
        String _propertyValue_11 = attR.getPropertyValue(PropertyTypeConstants.WIDGET_GROUP);
        modAttr.setWidgetGroup(_propertyValue_11);
        String _propertyValue_12 = attR.getPropertyValue(PropertyTypeConstants.BEAN_NAME);
        modAttr.setBeanName(_propertyValue_12);
        String _propertyValue_13 = attR.getPropertyValue(PropertyTypeConstants.BEAN_PROPERTY);
        modAttr.setBeanProperty(_propertyValue_13);
        String _propertyValue_14 = attR.getPropertyValue(PropertyTypeConstants.CSS_CLASS);
        modAttr.setCssClass(_propertyValue_14);
        String horAlign = attR.getPropertyValue(PropertyTypeConstants.HORIZONTAL_ALIGNMENT);
        String _xifexpression_4 = null;
        boolean _and = false;
        boolean _notEquals_2 = (!Objects.equal(horAlign, null));
        if (!_notEquals_2) {
          _and = false;
        } else {
          boolean _equals_1 = horAlign.equals("${corporatehalign}");
          _and = _equals_1;
        }
        if (_and) {
          _xifexpression_4 = "Corporate";
        } else {
          _xifexpression_4 = horAlign;
        }
        modAttr.setHorizontalAlignment(_xifexpression_4);
        String _propertyValue_15 = attR.getPropertyValue(PropertyTypeConstants.VERTICAL_ALIGNMENT);
        modAttr.setVerticalAlignment(_propertyValue_15);
        String _propertyValue_16 = attR.getPropertyValue(PropertyTypeConstants.WIDTH);
        modAttr.setWidth(_propertyValue_16);
        String _propertyValue_17 = attR.getPropertyValue(PropertyTypeConstants.PATTERN_TYPE);
        modAttr.setPattern(_propertyValue_17);
        String _propertyValue_18 = attR.getPropertyValue(PropertyTypeConstants.ACCESS_KEY);
        modAttr.setAccessKey(_propertyValue_18);
        String _propertyValue_19 = attR.getPropertyValue(PropertyTypeConstants.TAB_INDEX);
        modAttr.setTabIndex(_propertyValue_19);
        String _propertyValue_20 = attR.getPropertyValue(PropertyTypeConstants.ID);
        modAttr.setId(_propertyValue_20);
        WidgetType _type_1 = attR.getType();
        String _name_1 = _type_1.getName();
        boolean _equals_2 = _name_1.equals(WidgetTypeConstants.LABEL);
        if (_equals_2) {
          String _propertyValue_21 = attR.getPropertyValue(PropertyTypeConstants.HORIZONTAL_TEXT_POSITION);
          modAttr.setHorizontalTextPosition(_propertyValue_21);
          String _propertyValue_22 = attR.getPropertyValue(PropertyTypeConstants.VERTICAL_TEXT_POSITION);
          modAttr.setVerticalTextPosition(_propertyValue_22);
        }
        boolean _or = false;
        WidgetType _type_2 = attR.getType();
        String _name_2 = _type_2.getName();
        boolean _equals_3 = _name_2.equals(WidgetTypeConstants.LABEL);
        if (_equals_3) {
          _or = true;
        } else {
          WidgetType _type_3 = attR.getType();
          String _name_3 = _type_3.getName();
          boolean _equals_4 = _name_3.equals(WidgetTypeConstants.RADIO_BUTTON);
          _or = _equals_4;
        }
        if (_or) {
          String _propertyValue_23 = attR.getPropertyValue("wrapped");
          modAttr.setWrapped(_propertyValue_23);
        }
        WidgetType _type_4 = attR.getType();
        String _name_4 = _type_4.getName();
        boolean _equals_5 = _name_4.equals(WidgetTypeConstants.ICON);
        if (_equals_5) {
          String _propertyValue_24 = attR.getPropertyValue(PropertyTypeConstants.ICON);
          modAttr.setIcon(_propertyValue_24);
        }
        WidgetType _type_5 = attR.getType();
        String _name_5 = _type_5.getName();
        boolean _equals_6 = _name_5.equals(WidgetTypeConstants.TEXTAREA);
        if (_equals_6) {
          String _propertyValue_25 = attR.getPropertyValue(PropertyTypeConstants.HEIGHT);
          modAttr.setHeight(_propertyValue_25);
          String _propertyValue_26 = attR.getPropertyValue(PropertyTypeConstants.RICHTEXT);
          modAttr.setRichText(_propertyValue_26);
        }
        WidgetType _type_6 = attR.getType();
        String _name_6 = _type_6.getName();
        boolean _equals_7 = _name_6.equals(WidgetTypeConstants.SEARCH_FIELD);
        if (_equals_7) {
          String _propertyValue_27 = attR.getPropertyValue(PropertyTypeConstants.SEARCH_TYPE);
          modAttr.setSearchType(_propertyValue_27);
          String _propertyValue_28 = attR.getPropertyValue("designModuleName");
          modAttr.setAutoCompleteDesignModule(_propertyValue_28);
          String _propertyValue_29 = attR.getPropertyValue(PropertyTypeConstants.DELAY_TYPE);
          modAttr.setAutoCompleteDelay(_propertyValue_29);
          String _propertyValue_30 = attR.getPropertyValue(PropertyTypeConstants.NB_CHARS);
          modAttr.setAutoCompleteNumberOfChars(_propertyValue_30);
        }
        WidgetType _type_7 = attR.getType();
        String _name_7 = _type_7.getName();
        boolean _equals_8 = _name_7.equals("Calendar");
        if (_equals_8) {
          String _propertyValue_31 = attR.getPropertyValue(PropertyTypeConstants.NILLABLE_TYPE);
          modAttr.setNullValue(_propertyValue_31);
          String _propertyValue_32 = attR.getPropertyValue(PropertyTypeConstants.FREE_DATE_TYPE);
          modAttr.setFreeDate(_propertyValue_32);
        }
        boolean _or_1 = false;
        WidgetType _type_8 = attR.getType();
        String _name_8 = _type_8.getName();
        boolean _equals_9 = _name_8.equals(WidgetTypeConstants.CHECKBOX);
        if (_equals_9) {
          _or_1 = true;
        } else {
          WidgetType _type_9 = attR.getType();
          String _name_9 = _type_9.getName();
          boolean _equals_10 = _name_9.equals(WidgetTypeConstants.RADIO_BUTTON);
          _or_1 = _equals_10;
        }
        if (_or_1) {
          String _propertyValue_33 = attR.getPropertyValue(PropertyTypeConstants.DISPLAY_LABEL);
          modAttr.setDisplayLabel(_propertyValue_33);
          String _propertyValue_34 = attR.getPropertyValue(PropertyTypeConstants.SELECTED);
          modAttr.setSelected(_propertyValue_34);
          String _propertyValue_35 = attR.getPropertyValue(PropertyTypeConstants.GROUP);
          modAttr.setGroup(_propertyValue_35);
        }
        boolean _or_2 = false;
        WidgetType _type_10 = attR.getType();
        String _name_10 = _type_10.getName();
        boolean _equals_11 = _name_10.equals(WidgetTypeConstants.COMBOBOX);
        if (_equals_11) {
          _or_2 = true;
        } else {
          WidgetType _type_11 = attR.getType();
          String _name_11 = _type_11.getName();
          boolean _equals_12 = _name_11.equals(WidgetTypeConstants.LIST);
          _or_2 = _equals_12;
        }
        if (_or_2) {
          String _propertyValue_36 = attR.getPropertyValue(PropertyTypeConstants.SELECTION_TYPE);
          modAttr.setSelection(_propertyValue_36);
          String _propertyValue_37 = attR.getPropertyValue(PropertyTypeConstants.SORTBY_TYPE);
          modAttr.setSortBy(_propertyValue_37);
        }
        WidgetType _type_12 = attR.getType();
        String _name_12 = _type_12.getName();
        boolean _equals_13 = _name_12.equals(WidgetTypeConstants.LIST);
        if (_equals_13) {
          String _propertyValue_38 = attR.getPropertyValue("numberOfItems");
          modAttr.setNumberOfItems(_propertyValue_38);
        }
        modAttributes.add(modAttr);
      }
    }
    Iterable<ModuleAttribute> _filterNull = IterableExtensions.<ModuleAttribute>filterNull(modAttributes);
    final Function1<ModuleAttribute, String> _function = new Function1<ModuleAttribute, String>() {
      public String apply(final ModuleAttribute e) {
        String _xifexpression = null;
        String _label = e.getLabel();
        boolean _notEquals = (!Objects.equal(_label, null));
        if (_notEquals) {
          _xifexpression = e.getLabel();
        }
        return _xifexpression;
      }
    };
    return IterableExtensions.<ModuleAttribute, String>sortBy(_filterNull, _function);
  }
  
  public boolean checkEachColumn(final List<ModuleAttribute> modAttrs, final int index) {
    switch (index) {
      case 1:
        for (final ModuleAttribute mA : modAttrs) {
          String _label = mA.getLabel();
          boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(_label);
          boolean _not = (!_isNullOrEmpty);
          if (_not) {
            return true;
          }
        }
        break;
      case 2:
        for (final ModuleAttribute mA_1 : modAttrs) {
          String _domainReference = mA_1.getDomainReference();
          boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(_domainReference);
          boolean _not_1 = (!_isNullOrEmpty_1);
          if (_not_1) {
            return true;
          }
        }
        break;
      case 3:
        for (final ModuleAttribute mA_2 : modAttrs) {
          String _containedIn = mA_2.getContainedIn();
          boolean _isNullOrEmpty_2 = StringExtensions.isNullOrEmpty(_containedIn);
          boolean _not_2 = (!_isNullOrEmpty_2);
          if (_not_2) {
            return true;
          }
        }
        break;
      case 4:
        for (final ModuleAttribute mA_3 : modAttrs) {
          String _moduleAttrTabletype = mA_3.getModuleAttrTabletype();
          boolean _isNullOrEmpty_3 = StringExtensions.isNullOrEmpty(_moduleAttrTabletype);
          boolean _not_3 = (!_isNullOrEmpty_3);
          if (_not_3) {
            return true;
          }
        }
        break;
    }
    return false;
  }
  
  public CharSequence coreAttributes(final IOfsProject project, final List<Widget> attributeList) {
    StringConcatenation _builder = new StringConcatenation();
    List<ModuleAttribute> modAttributes = this.computeAttributes(project, attributeList);
    _builder.newLineIfNotEmpty();
    _builder.append("<table border=\"1\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<tr>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Label</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Domain Reference</th>");
    _builder.newLine();
    {
      boolean _checkEachColumn = this.checkEachColumn(modAttributes, 4);
      if (_checkEachColumn) {
        _builder.append("\t");
        _builder.append("<th bgcolor=\"#6495ED\">Type</th>");
        _builder.newLine();
      }
    }
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Contained In</th>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</tr>");
    _builder.newLine();
    {
      for(final ModuleAttribute modAttr : modAttributes) {
        {
          boolean _or = false;
          boolean _or_1 = false;
          String _label = modAttr.getLabel();
          boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(_label);
          boolean _not = (!_isNullOrEmpty);
          if (_not) {
            _or_1 = true;
          } else {
            String _domainReference = modAttr.getDomainReference();
            boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(_domainReference);
            boolean _not_1 = (!_isNullOrEmpty_1);
            _or_1 = _not_1;
          }
          if (_or_1) {
            _or = true;
          } else {
            String _containedIn = modAttr.getContainedIn();
            boolean _isNullOrEmpty_2 = StringExtensions.isNullOrEmpty(_containedIn);
            boolean _not_2 = (!_isNullOrEmpty_2);
            _or = _not_2;
          }
          if (_or) {
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<td>");
            String _label_1 = modAttr.getLabel();
            _builder.append(_label_1, "\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<td>");
            String _domainReference_1 = modAttr.getDomainReference();
            _builder.append(_domainReference_1, "\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            {
              boolean _checkEachColumn_1 = this.checkEachColumn(modAttributes, 4);
              if (_checkEachColumn_1) {
                _builder.append("<td>");
                String _moduleAttrTabletype = modAttr.getModuleAttrTabletype();
                _builder.append(_moduleAttrTabletype, "\t");
                _builder.append("</td>");
              }
            }
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<td>");
            String _containedIn_1 = modAttr.getContainedIn();
            _builder.append(_containedIn_1, "\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("</table>");
    _builder.newLine();
    modAttributes.clear();
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public List<Widget> fetchLabelDomains(final List<Widget> attributeList) {
    List<Widget> labeldoms = CollectionLiterals.<Widget>newArrayList();
    for (final Widget wid : attributeList) {
      WidgetType _type = wid.getType();
      String _name = _type.getName();
      boolean _equals = _name.equals(WidgetTypeConstants.LABEL_DOMAIN);
      if (_equals) {
        labeldoms.add(wid);
      }
    }
    attributeList.removeAll(labeldoms);
    return labeldoms;
  }
  
  public String fetchRespLabelDomains(final Widget domAttr, final List<Widget> labelDoms, final IOfsProject project) {
    WidgetType _type = domAttr.getType();
    String _name = _type.getName();
    boolean _equals = _name.equals(WidgetTypeConstants.INCLUDE);
    if (_equals) {
      Property _findProperty = domAttr.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
      Model _model = _findProperty.getModel();
      Resource _eResource = _model.eResource();
      URI _uRI = _eResource.getURI();
      String _path = _uRI.path();
      boolean _endsWith = _path.endsWith("fragment");
      if (_endsWith) {
        String domAssociation = domAttr.getPropertyValue(PropertyTypeConstants.DOMAIN_ASSOCIATION);
        boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(domAssociation);
        boolean _not = (!_isNullOrEmpty);
        if (_not) {
          for (final Widget wid : labelDoms) {
            {
              String lblDomAttrName = wid.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
              boolean _equalsIgnoreCase = domAssociation.equalsIgnoreCase(lblDomAttrName);
              if (_equalsIgnoreCase) {
                return this.fetchTranslationValue(wid, project);
              }
            }
          }
        }
      }
    } else {
      WidgetType _type_1 = domAttr.getType();
      String _name_1 = _type_1.getName();
      boolean _equals_1 = _name_1.equals(WidgetTypeConstants.TABLE_COLUMN);
      if (_equals_1) {
        Property _findProperty_1 = domAttr.findProperty("column-name");
        return _findProperty_1.getValue();
      } else {
        boolean _or = false;
        boolean _or_1 = false;
        boolean _or_2 = false;
        boolean _or_3 = false;
        WidgetType _type_2 = domAttr.getType();
        String _name_2 = _type_2.getName();
        boolean _equals_2 = _name_2.equals("TableGroup");
        if (_equals_2) {
          _or_3 = true;
        } else {
          WidgetType _type_3 = domAttr.getType();
          String _name_3 = _type_3.getName();
          boolean _equals_3 = _name_3.equals("TableSort");
          _or_3 = _equals_3;
        }
        if (_or_3) {
          _or_2 = true;
        } else {
          WidgetType _type_4 = domAttr.getType();
          String _name_4 = _type_4.getName();
          boolean _equals_4 = _name_4.equals("TableExtra");
          _or_2 = _equals_4;
        }
        if (_or_2) {
          _or_1 = true;
        } else {
          WidgetType _type_5 = domAttr.getType();
          String _name_5 = _type_5.getName();
          boolean _equals_5 = _name_5.equals("TableAggregate");
          _or_1 = _equals_5;
        }
        if (_or_1) {
          _or = true;
        } else {
          WidgetType _type_6 = domAttr.getType();
          String _name_6 = _type_6.getName();
          boolean _equals_6 = _name_6.equals("TableKeepFilter");
          _or = _equals_6;
        }
        if (_or) {
          EObject _eContainer = domAttr.eContainer();
          Widget tabColm = ((Widget) _eContainer);
          boolean _and = false;
          boolean _notEquals = (!Objects.equal(tabColm, null));
          if (!_notEquals) {
            _and = false;
          } else {
            String _propertyValue = domAttr.getPropertyValue("domainAttribute");
            Widget _containerWidget = this.getContainerWidget(_propertyValue, tabColm);
            boolean _notEquals_1 = (!Objects.equal(_containerWidget, null));
            _and = _notEquals_1;
          }
          if (_and) {
            String _propertyValue_1 = domAttr.getPropertyValue("domainAttribute");
            Widget _containerWidget_1 = this.getContainerWidget(_propertyValue_1, tabColm);
            EObject _eContainer_1 = _containerWidget_1.eContainer();
            Widget parCont = ((Widget) _eContainer_1);
            return this.fetchTranslationValue(parCont, project);
          }
        } else {
          for (final Widget wid_1 : labelDoms) {
            {
              String domAttrName = domAttr.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
              String lblDomAttrName = wid_1.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
              boolean _equals_7 = lblDomAttrName.equals(domAttrName);
              if (_equals_7) {
                return this.fetchTranslationValue(wid_1, project);
              } else {
                boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(domAttrName);
                if (_isNullOrEmpty_1) {
                  return this.fetchTranslationValue(domAttr, project);
                }
              }
            }
          }
        }
      }
    }
    return "";
  }
  
  public Widget getContainerWidget(final String targetValue, final Widget parent) {
    EList<Widget> _contents = parent.getContents();
    for (final Widget widget : _contents) {
      {
        String dtext = widget.getPropertyValue("domainAttribute");
        boolean _and = false;
        boolean _notEquals = (!Objects.equal(dtext, null));
        if (!_notEquals) {
          _and = false;
        } else {
          boolean _equals = dtext.equals(targetValue);
          _and = _equals;
        }
        if (_and) {
          return widget;
        } else {
          this.getContainerWidget(targetValue, widget);
        }
      }
    }
    return null;
  }
  
  public String fetchTranslationValue(final Widget wid, final IOfsProject project) {
    String _title = ModuleUtils.getTitle(project, wid);
    boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(_title);
    boolean _not = (!_isNullOrEmpty);
    if (_not) {
      return ModuleUtils.getTitle(project, wid);
    } else {
      String _translation = ModuleUtils.getTranslation(project, wid);
      boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(_translation);
      boolean _not_1 = (!_isNullOrEmpty_1);
      if (_not_1) {
        return ModuleUtils.getTranslation(project, wid);
      } else {
        return "";
      }
    }
  }
  
  public CharSequence coreXAttributes(final IOfsProject project, final List<Widget> attributeList) {
    StringConcatenation _builder = new StringConcatenation();
    List<ModuleAttribute> modXAttributes = this.computeXAttributes(project, attributeList);
    _builder.newLineIfNotEmpty();
    _builder.append("<table border=\"1\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<tr>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Label</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Domain Reference</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Contained In</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Description</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Translation (en)</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Data Type</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Type</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Display Format</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Editable</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Enable</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Required</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Readable</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Maximum Characters</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Widget Group</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Bean Name</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Bean Property</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">CSS Class</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Horizontal Allignment</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Vertical Alignment</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Width</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Pattern</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Access Key</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Tab Index</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">id</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Horizontal Text Position</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Vertical Text Position</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Wrapped</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Icon</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Height (in characters)</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Rich Text</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Search Type</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Auto-complete design module</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Auto-complete delay</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Auto-complete number of characters</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Null Value</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Free Date</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Display Label</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Selected</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Group</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Selection</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Sort By</th>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<th bgcolor=\"#6495ED\">Number of items</th>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</tr>");
    _builder.newLine();
    {
      for(final ModuleAttribute modAttr : modXAttributes) {
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<td>");
        String _label = modAttr.getLabel();
        _builder.append(_label, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _domainReference = modAttr.getDomainReference();
        _builder.append(_domainReference, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _containedIn = modAttr.getContainedIn();
        _builder.append(_containedIn, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _description = modAttr.getDescription();
        _builder.append(_description, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _translation = modAttr.getTranslation();
        _builder.append(_translation, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _dataType = modAttr.getDataType();
        _builder.append(_dataType, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _type = modAttr.getType();
        _builder.append(_type, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _displayFormat = modAttr.getDisplayFormat();
        _builder.append(_displayFormat, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _editable = modAttr.getEditable();
        _builder.append(_editable, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _enable = modAttr.getEnable();
        _builder.append(_enable, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _required = modAttr.getRequired();
        _builder.append(_required, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _readable = modAttr.getReadable();
        _builder.append(_readable, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _maximumCharacters = modAttr.getMaximumCharacters();
        _builder.append(_maximumCharacters, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _widgetGroup = modAttr.getWidgetGroup();
        _builder.append(_widgetGroup, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _beanName = modAttr.getBeanName();
        _builder.append(_beanName, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _beanProperty = modAttr.getBeanProperty();
        _builder.append(_beanProperty, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _cssClass = modAttr.getCssClass();
        _builder.append(_cssClass, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _horizontalAlignment = modAttr.getHorizontalAlignment();
        _builder.append(_horizontalAlignment, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _verticalAlignment = modAttr.getVerticalAlignment();
        _builder.append(_verticalAlignment, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _width = modAttr.getWidth();
        _builder.append(_width, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _pattern = modAttr.getPattern();
        _builder.append(_pattern, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _accessKey = modAttr.getAccessKey();
        _builder.append(_accessKey, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _tabIndex = modAttr.getTabIndex();
        _builder.append(_tabIndex, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _id = modAttr.getId();
        _builder.append(_id, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _horizontalTextPosition = modAttr.getHorizontalTextPosition();
        _builder.append(_horizontalTextPosition, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _verticalTextPosition = modAttr.getVerticalTextPosition();
        _builder.append(_verticalTextPosition, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _wrapped = modAttr.getWrapped();
        _builder.append(_wrapped, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _icon = modAttr.getIcon();
        _builder.append(_icon, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _height = modAttr.getHeight();
        _builder.append(_height, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _richText = modAttr.getRichText();
        _builder.append(_richText, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _searchType = modAttr.getSearchType();
        _builder.append(_searchType, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _autoCompleteDesignModule = modAttr.getAutoCompleteDesignModule();
        _builder.append(_autoCompleteDesignModule, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _autoCompleteDelay = modAttr.getAutoCompleteDelay();
        _builder.append(_autoCompleteDelay, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _autoCompleteNumberOfChars = modAttr.getAutoCompleteNumberOfChars();
        _builder.append(_autoCompleteNumberOfChars, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _nullValue = modAttr.getNullValue();
        _builder.append(_nullValue, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _freeDate = modAttr.getFreeDate();
        _builder.append(_freeDate, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _displayLabel = modAttr.getDisplayLabel();
        _builder.append(_displayLabel, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _selected = modAttr.getSelected();
        _builder.append(_selected, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _group = modAttr.getGroup();
        _builder.append(_group, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _selection = modAttr.getSelection();
        _builder.append(_selection, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _sortBy = modAttr.getSortBy();
        _builder.append(_sortBy, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        String _numberOfItems = modAttr.getNumberOfItems();
        _builder.append(_numberOfItems, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("</tr>");
        _builder.newLine();
      }
    }
    _builder.append("</table>");
    _builder.newLine();
    return _builder;
  }
  
  public String fetchRepAttributeContainer(final Widget widget, final List<Widget> widgets) {
    List<String> value = CollectionLiterals.<String>newArrayList();
    for (final Widget wid : widgets) {
      {
        String curentD = wid.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
        String reDoAt = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
        boolean _and = false;
        boolean _and_1 = false;
        boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(curentD);
        boolean _not = (!_isNullOrEmpty);
        if (!_not) {
          _and_1 = false;
        } else {
          boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(reDoAt);
          boolean _not_1 = (!_isNullOrEmpty_1);
          _and_1 = _not_1;
        }
        if (!_and_1) {
          _and = false;
        } else {
          boolean _equals = curentD.equals(reDoAt);
          _and = _equals;
        }
        if (_and) {
          String _fetchAttributeContainer = this.fetchAttributeContainer(wid);
          value.add(_fetchAttributeContainer);
        }
      }
    }
    String _string = value.toString();
    String _string_1 = value.toString();
    int _length = _string_1.length();
    int _minus = (_length - 1);
    return _string.substring(1, _minus);
  }
  
  public String fetchAttributeContainer(final Widget widget) {
    String _xifexpression = null;
    Widget _parent = widget.getParent();
    WidgetType _type = _parent.getType();
    String _name = _type.getName();
    boolean _equals = _name.equals(WidgetTypeConstants.TAB);
    if (_equals) {
      Model _model = widget.getModel();
      boolean _notEquals = (!Objects.equal(_model, null));
      if (_notEquals) {
        Widget _parent_1 = widget.getParent();
        String _propertyValue = _parent_1.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
        return ("TAB:" + _propertyValue);
      }
    } else {
      String _xifexpression_1 = null;
      boolean _and = false;
      Widget _parent_2 = widget.getParent();
      WidgetType _type_1 = _parent_2.getType();
      String _name_1 = _type_1.getName();
      boolean _equals_1 = _name_1.equals(WidgetTypeConstants.CONDITIONAL_BODY);
      if (!_equals_1) {
        _and = false;
      } else {
        Widget _parent_3 = widget.getParent();
        Widget _parent_4 = _parent_3.getParent();
        Widget _parent_5 = _parent_4.getParent();
        WidgetType _type_2 = _parent_5.getType();
        String _name_2 = _type_2.getName();
        boolean _equals_2 = _name_2.equals(WidgetTypeConstants.TAB);
        _and = _equals_2;
      }
      if (_and) {
        Widget _parent_6 = widget.getParent();
        Widget _parent_7 = _parent_6.getParent();
        Widget _parent_8 = _parent_7.getParent();
        String _propertyValue_1 = _parent_8.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
        String _plus = ("TAB:" + _propertyValue_1);
        String _plus_1 = (_plus + "->Condition:");
        Widget _parent_9 = widget.getParent();
        String _propertyValue_2 = _parent_9.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
        return (_plus_1 + _propertyValue_2);
      } else {
        String _xifexpression_2 = null;
        boolean _and_1 = false;
        Widget _parent_10 = widget.getParent();
        WidgetType _type_3 = _parent_10.getType();
        String _name_3 = _type_3.getName();
        boolean _equals_3 = _name_3.equals(WidgetTypeConstants.CONDITIONAL_BODY);
        if (!_equals_3) {
          _and_1 = false;
        } else {
          Widget _parent_11 = widget.getParent();
          Widget _parent_12 = _parent_11.getParent();
          Widget _parent_13 = _parent_12.getParent();
          WidgetType _type_4 = _parent_13.getType();
          String _name_4 = _type_4.getName();
          boolean _equals_4 = _name_4.equals(WidgetTypeConstants.TAB);
          boolean _not = (!_equals_4);
          _and_1 = _not;
        }
        if (_and_1) {
          Widget _parent_14 = widget.getParent();
          String _propertyValue_3 = _parent_14.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
          return ("Condition:" + _propertyValue_3);
        } else {
          String _xifexpression_3 = null;
          boolean _and_2 = false;
          boolean _or = false;
          boolean _or_1 = false;
          Widget _parent_15 = widget.getParent();
          WidgetType _type_5 = _parent_15.getType();
          String _name_5 = _type_5.getName();
          boolean _equals_5 = _name_5.equals(WidgetTypeConstants.GRID);
          if (_equals_5) {
            _or_1 = true;
          } else {
            Widget _parent_16 = widget.getParent();
            WidgetType _type_6 = _parent_16.getType();
            String _name_6 = _type_6.getName();
            boolean _equals_6 = _name_6.equals(WidgetTypeConstants.BOX);
            _or_1 = _equals_6;
          }
          if (_or_1) {
            _or = true;
          } else {
            Widget _parent_17 = widget.getParent();
            WidgetType _type_7 = _parent_17.getType();
            String _name_7 = _type_7.getName();
            boolean _equals_7 = _name_7.equals(WidgetTypeConstants.TABLE);
            _or = _equals_7;
          }
          if (!_or) {
            _and_2 = false;
          } else {
            Widget _parent_18 = widget.getParent();
            Widget _parent_19 = _parent_18.getParent();
            WidgetType _type_8 = _parent_19.getType();
            String _name_8 = _type_8.getName();
            boolean _equals_8 = _name_8.equals(WidgetTypeConstants.MODULE);
            _and_2 = _equals_8;
          }
          if (_and_2) {
            Widget _parent_20 = widget.getParent();
            Widget _parent_21 = _parent_20.getParent();
            String _propertyValue_4 = _parent_21.getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
            return ("Module:" + _propertyValue_4);
          } else {
            String _xifexpression_4 = null;
            boolean _and_3 = false;
            boolean _or_2 = false;
            boolean _or_3 = false;
            Widget _parent_22 = widget.getParent();
            WidgetType _type_9 = _parent_22.getType();
            String _name_9 = _type_9.getName();
            boolean _equals_9 = _name_9.equals(WidgetTypeConstants.GRID);
            if (_equals_9) {
              _or_3 = true;
            } else {
              Widget _parent_23 = widget.getParent();
              WidgetType _type_10 = _parent_23.getType();
              String _name_10 = _type_10.getName();
              boolean _equals_10 = _name_10.equals(WidgetTypeConstants.BOX);
              _or_3 = _equals_10;
            }
            if (_or_3) {
              _or_2 = true;
            } else {
              Widget _parent_24 = widget.getParent();
              WidgetType _type_11 = _parent_24.getType();
              String _name_11 = _type_11.getName();
              boolean _equals_11 = _name_11.equals(WidgetTypeConstants.TABLE);
              _or_2 = _equals_11;
            }
            if (!_or_2) {
              _and_3 = false;
            } else {
              Widget _parent_25 = widget.getParent();
              Widget _parent_26 = _parent_25.getParent();
              WidgetType _type_12 = _parent_26.getType();
              String _name_12 = _type_12.getName();
              boolean _equals_12 = _name_12.equals(WidgetTypeConstants.FRAGMENT);
              _and_3 = _equals_12;
            }
            if (_and_3) {
              Widget _parent_27 = widget.getParent();
              Widget _parent_28 = _parent_27.getParent();
              String _propertyValue_5 = _parent_28.getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
              return ("Fragment:" + _propertyValue_5);
            } else {
              Widget _parent_29 = widget.getParent();
              _xifexpression_4 = this.fetchAttributeContainer(_parent_29);
            }
            _xifexpression_3 = _xifexpression_4;
          }
          _xifexpression_2 = _xifexpression_3;
        }
        _xifexpression_1 = _xifexpression_2;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  public String fetchModuleInformation(final Widget widget, final String widgetType, final String propertyType) {
    WidgetType _type = widget.getType();
    String _name = _type.getName();
    boolean _equals = _name.equals(widgetType);
    if (_equals) {
      Model _model = widget.getModel();
      boolean _notEquals = (!Objects.equal(_model, null));
      if (_notEquals) {
        return widget.getPropertyValue(propertyType);
      }
    }
    return null;
  }
  
  public CharSequence tabCondContainer(final Widget widget, final IOfsProject project, final String containmentValue) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> tabedCondPanes = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    List<Widget> tabbedPanesList = this.fetchTabCond(widget, tabedCondPanes);
    _builder.newLineIfNotEmpty();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(tabbedPanesList);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        _builder.append("List of containers:<br>");
        _builder.newLine();
        {
          for(final Widget tabPane : tabbedPanesList) {
            {
              WidgetType _type = tabPane.getType();
              String _name = _type.getName();
              boolean _equals = _name.equals(WidgetTypeConstants.TABBED_PANE);
              if (_equals) {
                CharSequence _printForTab = this.printForTab(tabPane, project, containmentValue);
                _builder.append(_printForTab, "");
                _builder.newLineIfNotEmpty();
              } else {
                WidgetType _type_1 = tabPane.getType();
                String _name_1 = _type_1.getName();
                boolean _equals_1 = _name_1.equals(WidgetTypeConstants.CONDITIONAL);
                if (_equals_1) {
                  CharSequence _printForConditional = this.printForConditional(tabPane, project, containmentValue);
                  _builder.append(_printForConditional, "");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
        }
        tabedCondPanes.clear();
        _builder.newLineIfNotEmpty();
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence printForTab(final Widget tabPane, final IOfsProject project, final String containmentValue) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    _builder.append("<ul>");
    _builder.newLine();
    _builder.append("<li>");
    _builder.newLine();
    _builder.append("Tabbed Pane:");
    {
      String _propertyValue = tabPane.getPropertyValue("name");
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(_propertyValue);
      if (_isNullOrEmpty) {
        String _propertyValue_1 = tabPane.getPropertyValue("name");
        _builder.append(_propertyValue_1, "");
        _builder.append(",");
      }
    }
    _builder.append("\t");
    _builder.newLineIfNotEmpty();
    {
      String _propertyValue_2 = tabPane.getPropertyValue("tabs-filtered-attribute");
      boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(_propertyValue_2);
      if (_isNullOrEmpty_1) {
        _builder.append("\t");
        _builder.append("static");
        {
          boolean _and = false;
          boolean _notEquals = (!Objects.equal(containmentValue, null));
          if (!_notEquals) {
            _and = false;
          } else {
            boolean _equalsIgnoreCase = containmentValue.equalsIgnoreCase("Container");
            _and = _equalsIgnoreCase;
          }
          if (_and) {
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append(", with page reload on selection");
            _builder.newLine();
          } else {
            boolean _and_1 = false;
            boolean _notEquals_1 = (!Objects.equal(containmentValue, null));
            if (!_notEquals_1) {
              _and_1 = false;
            } else {
              boolean _equalsIgnoreCase_1 = containmentValue.equalsIgnoreCase("Stand-alone");
              _and_1 = _equalsIgnoreCase_1;
            }
            if (_and_1) {
              _builder.append("\t");
              _builder.append(",with no page reload on selection");
            } else {
              _builder.append(". ");
            }
          }
        }
        _builder.append(" ");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("dynamic, filtered on attribute: ");
        String _propertyValue_3 = tabPane.getPropertyValue("tabs-filtered-attribute");
        _builder.append(_propertyValue_3, "\t");
        _builder.append(", ");
        {
          boolean _and_2 = false;
          boolean _notEquals_2 = (!Objects.equal(containmentValue, null));
          if (!_notEquals_2) {
            _and_2 = false;
          } else {
            boolean _equalsIgnoreCase_2 = containmentValue.equalsIgnoreCase("Container");
            _and_2 = _equalsIgnoreCase_2;
          }
          if (_and_2) {
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("with page reload on selection");
            _builder.newLine();
          } else {
            boolean _and_3 = false;
            boolean _notEquals_3 = (!Objects.equal(containmentValue, null));
            if (!_notEquals_3) {
              _and_3 = false;
            } else {
              boolean _equalsIgnoreCase_3 = containmentValue.equalsIgnoreCase("Stand-alone");
              _and_3 = _equalsIgnoreCase_3;
            }
            if (_and_3) {
              _builder.append("\t");
              _builder.append("with no page reload on selection");
            } else {
              _builder.append(" ");
            }
          }
        }
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    {
      String _propertyValue_4 = tabPane.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
      boolean _isNullOrEmpty_2 = StringExtensions.isNullOrEmpty(_propertyValue_4);
      boolean _not = (!_isNullOrEmpty_2);
      if (_not) {
        _builder.append("description ");
        String _propertyValue_5 = tabPane.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
        _builder.append(_propertyValue_5, "\t");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    List<Widget> tabs = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    List<Widget> tabInTabedPane = this.fetchTabs(tabPane, WidgetTypeConstants.TAB, tabs);
    _builder.newLineIfNotEmpty();
    {
      for(final Widget taba : tabInTabedPane) {
        _builder.append("\t");
        _builder.append("<ul>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("<li> Tab: ");
        {
          String _title = ModuleUtils.getTitle(project, taba);
          boolean _isNullOrEmpty_3 = StringExtensions.isNullOrEmpty(_title);
          boolean _not_1 = (!_isNullOrEmpty_3);
          if (_not_1) {
            String _title_1 = ModuleUtils.getTitle(project, taba);
            _builder.append(_title_1, "\t\t");
            _builder.append(".");
          }
        }
        {
          String _propertyValue_6 = tabPane.getPropertyValue("tabs-filtered-attribute");
          boolean _isNullOrEmpty_4 = StringExtensions.isNullOrEmpty(_propertyValue_6);
          boolean _not_2 = (!_isNullOrEmpty_4);
          if (_not_2) {
            _builder.append(" Static/filtered on value.");
          }
        }
        _builder.append(" <br>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        {
          String _propertyValue_7 = taba.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
          boolean _isNullOrEmpty_5 = StringExtensions.isNullOrEmpty(_propertyValue_7);
          boolean _not_3 = (!_isNullOrEmpty_5);
          if (_not_3) {
            _builder.append("description ");
            String _propertyValue_8 = taba.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
            _builder.append(_propertyValue_8, "\t\t");
          }
        }
        _builder.append(" </li>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        List<Widget> tabedCondPanes = CollectionLiterals.<Widget>newArrayList();
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        List<Widget> tabCondMenu = this.fetchTabCond(taba, tabedCondPanes);
        _builder.newLineIfNotEmpty();
        {
          for(final Widget tabedCondtnlPane : tabCondMenu) {
            {
              WidgetType _type = tabedCondtnlPane.getType();
              String _name = _type.getName();
              boolean _equals = _name.equals(WidgetTypeConstants.TABBED_PANE);
              if (_equals) {
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<p>\t");
                Object _printForTab = this.printForTab(tabedCondtnlPane, project, containmentValue);
                _builder.append(_printForTab, "\t\t");
                _builder.append(" </p>");
                _builder.newLineIfNotEmpty();
              } else {
                WidgetType _type_1 = tabedCondtnlPane.getType();
                String _name_1 = _type_1.getName();
                boolean _equals_1 = _name_1.equals(WidgetTypeConstants.CONDITIONAL);
                if (_equals_1) {
                  _builder.append("\t");
                  _builder.append("\t");
                  _builder.append("<p>\t");
                  CharSequence _printForConditional = this.printForConditional(tabedCondtnlPane, project, containmentValue);
                  _builder.append(_printForConditional, "\t\t");
                  _builder.append(" </p>");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
        }
        _builder.append("\t");
        _builder.append("\t");
        tabedCondPanes.clear();
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("</ul>");
        _builder.newLine();
        {
          List<Widget> _collectNestedModuleConds = this.collectNestedModuleConds(taba, WidgetTypeConstants.TABBED_PANE);
          for(final Widget tabedPane : _collectNestedModuleConds) {
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<p>\t");
            Object _printForTab_1 = this.printForTab(tabedPane, project, containmentValue);
            _builder.append(_printForTab_1, "\t\t");
            _builder.append(" </p>");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    tabs.clear();
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("</li>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("</ul>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence printForConditional(final Widget tabPane, final IOfsProject project, final String containmentValue) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> conditions = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    List<Widget> condtn = this.fetchConditions(tabPane, WidgetTypeConstants.CONDITIONAL_BODY, conditions);
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<ul>");
    _builder.newLine();
    _builder.append("<li>Conditional: ");
    String _propertyValue = tabPane.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
    _builder.append(_propertyValue, "");
    _builder.newLineIfNotEmpty();
    {
      for(final Widget cndtnl : condtn) {
        CharSequence _moduleConditions = this.moduleConditions(cndtnl, project, containmentValue);
        _builder.append(_moduleConditions, "");
        _builder.newLineIfNotEmpty();
        List<Widget> conds = CollectionLiterals.<Widget>newArrayList();
        _builder.newLineIfNotEmpty();
        {
          List<Widget> _collectNestedModuleConds = this.collectNestedModuleConds(cndtnl, WidgetTypeConstants.CONDITIONAL);
          for(final Widget cndtnlWidget : _collectNestedModuleConds) {
            List<Widget> condts = this.fetchConditions(cndtnlWidget, WidgetTypeConstants.CONDITIONAL_BODY, conds);
            _builder.newLineIfNotEmpty();
            _builder.append("<ul>");
            _builder.newLine();
            _builder.append("<li>Conditional: ");
            String _propertyValue_1 = cndtnlWidget.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
            _builder.append(_propertyValue_1, "");
            _builder.newLineIfNotEmpty();
            _builder.append("<ul>");
            _builder.newLine();
            {
              for(final Widget subCndtn : condts) {
                _builder.append("  \t\t\t\t\t");
                _builder.append("<li>Condition: ");
                String _propertyValue_2 = subCndtn.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
                _builder.append(_propertyValue_2, "  \t\t\t\t\t");
                _builder.append(" ");
                _builder.newLineIfNotEmpty();
                {
                  boolean _and = false;
                  String _propertyValue_3 = subCndtn.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                  boolean _notEquals = (!Objects.equal(_propertyValue_3, null));
                  if (!_notEquals) {
                    _and = false;
                  } else {
                    String _propertyValue_4 = subCndtn.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                    boolean _isEmpty = _propertyValue_4.isEmpty();
                    boolean _not = (!_isEmpty);
                    _and = _not;
                  }
                  if (_and) {
                    _builder.append("<p>Description: ");
                    String _propertyValue_5 = subCndtn.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                    _builder.append(_propertyValue_5, "");
                    _builder.append("</p>");
                    _builder.newLineIfNotEmpty();
                  }
                }
                _builder.append("</li>\t\t\t");
                _builder.newLine();
              }
            }
            _builder.append("  \t\t\t\t");
            conds.clear();
            _builder.newLineIfNotEmpty();
            _builder.append("  \t\t\t\t");
            _builder.append("</ul>\t");
            _builder.newLine();
            _builder.append("  \t\t\t\t");
            _builder.append("</li>\t\t");
            _builder.newLine();
            _builder.append("  \t\t\t\t");
            _builder.append("</ul>\t\t");
            _builder.newLine();
          }
        }
      }
    }
    conditions.clear();
    _builder.newLineIfNotEmpty();
    _builder.append("</li>");
    _builder.newLine();
    _builder.append("</ul>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence printForXConditional(final Widget tabPane, final IOfsProject project, final String containmentValue) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> conditions = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    List<Widget> condtn = this.fetchConditions(tabPane, WidgetTypeConstants.CONDITIONAL_BODY, conditions);
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<ul>");
    _builder.newLine();
    _builder.append("<li>Conditional: ");
    String _propertyValue = tabPane.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
    _builder.append(_propertyValue, "");
    _builder.newLineIfNotEmpty();
    {
      for(final Widget cndtnl : condtn) {
        CharSequence _moduleXConditions = this.moduleXConditions(cndtnl, project, containmentValue);
        _builder.append(_moduleXConditions, "");
        _builder.newLineIfNotEmpty();
        List<Widget> conds = CollectionLiterals.<Widget>newArrayList();
        _builder.newLineIfNotEmpty();
        {
          List<Widget> _collectNestedModuleConds = this.collectNestedModuleConds(cndtnl, WidgetTypeConstants.CONDITIONAL);
          for(final Widget cndtnlWidget : _collectNestedModuleConds) {
            List<Widget> condts = this.fetchConditions(cndtnlWidget, WidgetTypeConstants.CONDITIONAL_BODY, conds);
            _builder.newLineIfNotEmpty();
            _builder.append("<ul>");
            _builder.newLine();
            _builder.append("<li>Conditional: ");
            String _propertyValue_1 = cndtnlWidget.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
            _builder.append(_propertyValue_1, "");
            _builder.newLineIfNotEmpty();
            _builder.append("<ul>");
            _builder.newLine();
            {
              for(final Widget subCndtn : condts) {
                _builder.append("<li>Condition: ");
                String _propertyValue_2 = subCndtn.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
                _builder.append(_propertyValue_2, "");
                _builder.newLineIfNotEmpty();
                _builder.append("  \t\t\t\t\t\t");
                {
                  String _propertyValue_3 = subCndtn.getPropertyValue(PropertyTypeConstants.JAVA_CODE);
                  boolean _notEquals = (!Objects.equal(_propertyValue_3, null));
                  if (_notEquals) {
                    _builder.append("<p>Code: ");
                    String _propertyValue_4 = subCndtn.getPropertyValue(PropertyTypeConstants.JAVA_CODE);
                    _builder.append(_propertyValue_4, "  \t\t\t\t\t\t");
                    _builder.append("</p>");
                  }
                }
                _builder.append(" ");
                _builder.newLineIfNotEmpty();
                {
                  boolean _and = false;
                  String _propertyValue_5 = subCndtn.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                  boolean _notEquals_1 = (!Objects.equal(_propertyValue_5, null));
                  if (!_notEquals_1) {
                    _and = false;
                  } else {
                    String _propertyValue_6 = subCndtn.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                    boolean _isEmpty = _propertyValue_6.isEmpty();
                    boolean _not = (!_isEmpty);
                    _and = _not;
                  }
                  if (_and) {
                    _builder.append("<p>Description: ");
                    String _propertyValue_7 = subCndtn.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                    _builder.append(_propertyValue_7, "");
                    _builder.append("</p>");
                    _builder.newLineIfNotEmpty();
                  }
                }
                _builder.append("</li>\t\t\t");
                _builder.newLine();
              }
            }
            _builder.append("  \t\t\t\t");
            _builder.append("</ul>\t");
            _builder.newLine();
            _builder.append("  \t\t\t\t");
            _builder.append("</li>\t\t");
            _builder.newLine();
            _builder.append("  \t\t\t\t");
            _builder.append("</ul>\t\t");
            _builder.newLine();
          }
        }
      }
    }
    conditions.clear();
    _builder.newLineIfNotEmpty();
    _builder.append("</li>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</ul>");
    _builder.newLine();
    return _builder;
  }
  
  public List<Widget> collectNestedModuleConds(final Widget condition, final String widgetType) {
    List<Widget> condtnls = CollectionLiterals.<Widget>newArrayList();
    EList<Widget> _contents = condition.getContents();
    for (final Widget widget : _contents) {
      WidgetType _type = widget.getType();
      String _name = _type.getName();
      boolean _equals = _name.equals(WidgetTypeConstants.INCLUDE);
      if (_equals) {
        Property _findProperty = widget.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
        Model mod = _findProperty.getModel();
        boolean _and = false;
        boolean _and_1 = false;
        boolean _and_2 = false;
        boolean _notEquals = (!Objects.equal(mod, null));
        if (!_notEquals) {
          _and_2 = false;
        } else {
          Resource _eResource = mod.eResource();
          boolean _notEquals_1 = (!Objects.equal(_eResource, null));
          _and_2 = _notEquals_1;
        }
        if (!_and_2) {
          _and_1 = false;
        } else {
          Resource _eResource_1 = mod.eResource();
          URI _uRI = _eResource_1.getURI();
          boolean _notEquals_2 = (!Objects.equal(_uRI, null));
          _and_1 = _notEquals_2;
        }
        if (!_and_1) {
          _and = false;
        } else {
          Resource _eResource_2 = mod.eResource();
          URI _uRI_1 = _eResource_2.getURI();
          String _path = _uRI_1.path();
          boolean _endsWith = _path.endsWith("fragment");
          _and = _endsWith;
        }
        if (_and) {
          Widget _widget = mod.getWidget();
          this.fetchNestedConditionals(_widget, widgetType, condtnls);
        }
      }
    }
    boolean _isEmpty = condtnls.isEmpty();
    if (_isEmpty) {
      this.fetchNestedConditionals(condition, widgetType, condtnls);
    }
    return condtnls;
  }
  
  public void fetchNestedConditionals(final Widget widget, final String widgetType, final List<Widget> condtnls) {
    WidgetType _type = widget.getType();
    String _name = _type.getName();
    boolean _equals = _name.equals(widgetType);
    if (_equals) {
      boolean _and = false;
      Model _model = widget.getModel();
      boolean _notEquals = (!Objects.equal(_model, null));
      if (!_notEquals) {
        _and = false;
      } else {
        boolean _contains = condtnls.contains(widget);
        boolean _not = (!_contains);
        _and = _not;
      }
      if (_and) {
        condtnls.add(widget);
      }
    } else {
      WidgetType _type_1 = widget.getType();
      String _name_1 = _type_1.getName();
      boolean _equals_1 = _name_1.equals(WidgetTypeConstants.INCLUDE);
      if (_equals_1) {
        Property _findProperty = widget.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
        Model mod = _findProperty.getModel();
        boolean _and_1 = false;
        boolean _and_2 = false;
        boolean _and_3 = false;
        boolean _notEquals_1 = (!Objects.equal(mod, null));
        if (!_notEquals_1) {
          _and_3 = false;
        } else {
          Resource _eResource = mod.eResource();
          boolean _notEquals_2 = (!Objects.equal(_eResource, null));
          _and_3 = _notEquals_2;
        }
        if (!_and_3) {
          _and_2 = false;
        } else {
          Resource _eResource_1 = mod.eResource();
          URI _uRI = _eResource_1.getURI();
          boolean _notEquals_3 = (!Objects.equal(_uRI, null));
          _and_2 = _notEquals_3;
        }
        if (!_and_2) {
          _and_1 = false;
        } else {
          Resource _eResource_2 = mod.eResource();
          URI _uRI_1 = _eResource_2.getURI();
          String _path = _uRI_1.path();
          boolean _endsWith = _path.endsWith("fragment");
          _and_1 = _endsWith;
        }
        if (_and_1) {
          Widget _widget = mod.getWidget();
          this.fetchNestedConditionals(_widget, widgetType, condtnls);
        }
      } else {
        EList<Widget> _contents = widget.getContents();
        boolean _isEmpty = _contents.isEmpty();
        boolean _not_1 = (!_isEmpty);
        if (_not_1) {
          EList<Widget> _contents_1 = widget.getContents();
          for (final Widget wid : _contents_1) {
            this.fetchNestedConditionals(wid, widgetType, condtnls);
          }
        }
      }
    }
  }
  
  public CharSequence tabCondXContainer(final Widget widget, final IOfsProject project, final String containmentValue) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> tabedCondPanes = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    List<Widget> tabbedPanesList = this.fetchTabCond(widget, tabedCondPanes);
    _builder.newLineIfNotEmpty();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(tabbedPanesList);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        _builder.append("List of containers:<br>");
        _builder.newLine();
        {
          for(final Widget tabPane : tabbedPanesList) {
            {
              WidgetType _type = tabPane.getType();
              String _name = _type.getName();
              boolean _equals = _name.equals(WidgetTypeConstants.TABBED_PANE);
              if (_equals) {
                CharSequence _printForTab = this.printForTab(tabPane, project, containmentValue);
                _builder.append(_printForTab, "");
                _builder.newLineIfNotEmpty();
              } else {
                WidgetType _type_1 = tabPane.getType();
                String _name_1 = _type_1.getName();
                boolean _equals_1 = _name_1.equals(WidgetTypeConstants.CONDITIONAL);
                if (_equals_1) {
                  CharSequence _printForXConditional = this.printForXConditional(tabPane, project, containmentValue);
                  _builder.append(_printForXConditional, "");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
        }
        tabedCondPanes.clear();
        _builder.newLineIfNotEmpty();
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public Widget fetchAttributeFromInclModel(final Widget widget) {
    Widget _xifexpression = null;
    boolean _or = false;
    WidgetType _type = widget.getType();
    String _name = _type.getName();
    boolean _equals = _name.equals(WidgetTypeConstants.ATTRIBUTE);
    if (_equals) {
      _or = true;
    } else {
      Property _findProperty = widget.findProperty(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
      boolean _notEquals = (!Objects.equal(_findProperty, null));
      _or = _notEquals;
    }
    if (_or) {
      Model _model = widget.getModel();
      boolean _notEquals_1 = (!Objects.equal(_model, null));
      if (_notEquals_1) {
        return widget;
      }
    } else {
      Widget _xifexpression_1 = null;
      WidgetType _type_1 = widget.getType();
      String _name_1 = _type_1.getName();
      boolean _equals_1 = _name_1.equals(WidgetTypeConstants.INCLUDE);
      if (_equals_1) {
        Widget _xifexpression_2 = null;
        Property _findProperty_1 = widget.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
        Model _model_1 = _findProperty_1.getModel();
        Resource _eResource = _model_1.eResource();
        URI _uRI = _eResource.getURI();
        String _path = _uRI.path();
        boolean _endsWith = _path.endsWith("fragment");
        if (_endsWith) {
          Widget _xblockexpression = null;
          {
            Property prop = widget.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
            Model _model_2 = prop.getModel();
            Widget _widget = _model_2.getWidget();
            _xblockexpression = this.fetchAttributeFromInclModel(_widget);
          }
          _xifexpression_2 = _xblockexpression;
        }
        _xifexpression_1 = _xifexpression_2;
      } else {
        Object _xifexpression_3 = null;
        EList<Widget> _contents = widget.getContents();
        boolean _isEmpty = _contents.isEmpty();
        boolean _not = (!_isEmpty);
        if (_not) {
          EList<Widget> _contents_1 = widget.getContents();
          for (final Widget wid : _contents_1) {
            return this.fetchAttributeFromInclModel(wid);
          }
        }
        _xifexpression_1 = ((Widget)_xifexpression_3);
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  public void fetchAllAttributes(final Widget widget, final List<Widget> allAttributes) {
    boolean _or = false;
    boolean _or_1 = false;
    boolean _or_2 = false;
    boolean _or_3 = false;
    boolean _or_4 = false;
    boolean _or_5 = false;
    WidgetType _type = widget.getType();
    String _name = _type.getName();
    boolean _equals = _name.equals(WidgetTypeConstants.ATTRIBUTE);
    if (_equals) {
      _or_5 = true;
    } else {
      Property _findProperty = widget.findProperty(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
      boolean _notEquals = (!Objects.equal(_findProperty, null));
      _or_5 = _notEquals;
    }
    if (_or_5) {
      _or_4 = true;
    } else {
      WidgetType _type_1 = widget.getType();
      String _name_1 = _type_1.getName();
      boolean _equals_1 = _name_1.equals(WidgetTypeConstants.INCLUDE);
      _or_4 = _equals_1;
    }
    if (_or_4) {
      _or_3 = true;
    } else {
      WidgetType _type_2 = widget.getType();
      String _name_2 = _type_2.getName();
      boolean _equals_2 = _name_2.equals("TableGroup");
      _or_3 = _equals_2;
    }
    if (_or_3) {
      _or_2 = true;
    } else {
      WidgetType _type_3 = widget.getType();
      String _name_3 = _type_3.getName();
      boolean _equals_3 = _name_3.equals("TableSort");
      _or_2 = _equals_3;
    }
    if (_or_2) {
      _or_1 = true;
    } else {
      WidgetType _type_4 = widget.getType();
      String _name_4 = _type_4.getName();
      boolean _equals_4 = _name_4.equals("TableAggregate");
      _or_1 = _equals_4;
    }
    if (_or_1) {
      _or = true;
    } else {
      WidgetType _type_5 = widget.getType();
      String _name_5 = _type_5.getName();
      boolean _equals_5 = _name_5.equals("TableKeepFilter");
      _or = _equals_5;
    }
    if (_or) {
      Widget _parent = widget.getParent();
      EList<Widget> _contents = _parent.getContents();
      for (final Widget wid : _contents) {
        boolean _or_6 = false;
        boolean _or_7 = false;
        boolean _or_8 = false;
        boolean _or_9 = false;
        boolean _or_10 = false;
        WidgetType _type_6 = wid.getType();
        String _name_6 = _type_6.getName();
        boolean _equals_6 = _name_6.equals(WidgetTypeConstants.ATTRIBUTE);
        if (_equals_6) {
          _or_10 = true;
        } else {
          Property _findProperty_1 = wid.findProperty(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
          boolean _notEquals_1 = (!Objects.equal(_findProperty_1, null));
          _or_10 = _notEquals_1;
        }
        if (_or_10) {
          _or_9 = true;
        } else {
          WidgetType _type_7 = widget.getType();
          String _name_7 = _type_7.getName();
          boolean _equals_7 = _name_7.equals("TableGroup");
          _or_9 = _equals_7;
        }
        if (_or_9) {
          _or_8 = true;
        } else {
          WidgetType _type_8 = widget.getType();
          String _name_8 = _type_8.getName();
          boolean _equals_8 = _name_8.equals("TableSort");
          _or_8 = _equals_8;
        }
        if (_or_8) {
          _or_7 = true;
        } else {
          WidgetType _type_9 = widget.getType();
          String _name_9 = _type_9.getName();
          boolean _equals_9 = _name_9.equals("TableAggregate");
          _or_7 = _equals_9;
        }
        if (_or_7) {
          _or_6 = true;
        } else {
          WidgetType _type_10 = widget.getType();
          String _name_10 = _type_10.getName();
          boolean _equals_10 = _name_10.equals("TableKeepFilter");
          _or_6 = _equals_10;
        }
        if (_or_6) {
          boolean _and = false;
          Model _model = wid.getModel();
          boolean _notEquals_2 = (!Objects.equal(_model, null));
          if (!_notEquals_2) {
            _and = false;
          } else {
            boolean _contains = allAttributes.contains(wid);
            boolean _not = (!_contains);
            _and = _not;
          }
          if (_and) {
            allAttributes.add(wid);
          }
        } else {
          WidgetType _type_11 = wid.getType();
          String _name_11 = _type_11.getName();
          boolean _equals_11 = _name_11.equals(WidgetTypeConstants.INCLUDE);
          if (_equals_11) {
            Property _findProperty_2 = wid.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
            Model mod = _findProperty_2.getModel();
            boolean _and_1 = false;
            boolean _and_2 = false;
            boolean _and_3 = false;
            boolean _notEquals_3 = (!Objects.equal(mod, null));
            if (!_notEquals_3) {
              _and_3 = false;
            } else {
              Resource _eResource = mod.eResource();
              boolean _notEquals_4 = (!Objects.equal(_eResource, null));
              _and_3 = _notEquals_4;
            }
            if (!_and_3) {
              _and_2 = false;
            } else {
              Resource _eResource_1 = mod.eResource();
              URI _uRI = _eResource_1.getURI();
              boolean _notEquals_5 = (!Objects.equal(_uRI, null));
              _and_2 = _notEquals_5;
            }
            if (!_and_2) {
              _and_1 = false;
            } else {
              Resource _eResource_2 = mod.eResource();
              URI _uRI_1 = _eResource_2.getURI();
              String _path = _uRI_1.path();
              boolean _endsWith = _path.endsWith("fragment");
              _and_1 = _endsWith;
            }
            if (_and_1) {
              Property _findProperty_3 = wid.findProperty(PropertyTypeConstants.DOMAIN_ASSOCIATION);
              String _value = _findProperty_3.getValue();
              boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(_value);
              boolean _not_1 = (!_isNullOrEmpty);
              if (_not_1) {
                allAttributes.add(wid);
              } else {
                Widget _widget = mod.getWidget();
                this.fetchAllAttributes(_widget, allAttributes);
              }
            }
          }
        }
      }
    } else {
      EList<Widget> _contents_1 = widget.getContents();
      boolean _isEmpty = _contents_1.isEmpty();
      boolean _not_2 = (!_isEmpty);
      if (_not_2) {
        EList<Widget> _contents_2 = widget.getContents();
        for (final Widget wid_1 : _contents_2) {
          this.fetchAllAttributes(wid_1, allAttributes);
        }
      }
    }
  }
  
  public List<Widget> fetchAllXAttributes(final Widget widget, final List<Widget> allAttributes) {
    boolean _or = false;
    boolean _checkAttrGrpWidget = this.checkAttrGrpWidget(widget);
    if (_checkAttrGrpWidget) {
      _or = true;
    } else {
      WidgetType _type = widget.getType();
      String _name = _type.getName();
      boolean _equals = _name.equals(WidgetTypeConstants.INCLUDE);
      _or = _equals;
    }
    if (_or) {
      Widget _parent = widget.getParent();
      EList<Widget> _contents = _parent.getContents();
      for (final Widget wid : _contents) {
        boolean _checkAttrGrpWidget_1 = this.checkAttrGrpWidget(wid);
        if (_checkAttrGrpWidget_1) {
          boolean _and = false;
          Model _model = wid.getModel();
          boolean _notEquals = (!Objects.equal(_model, null));
          if (!_notEquals) {
            _and = false;
          } else {
            boolean _contains = allAttributes.contains(wid);
            boolean _not = (!_contains);
            _and = _not;
          }
          if (_and) {
            allAttributes.add(wid);
          }
        } else {
          WidgetType _type_1 = wid.getType();
          String _name_1 = _type_1.getName();
          boolean _equals_1 = _name_1.equals(WidgetTypeConstants.INCLUDE);
          if (_equals_1) {
            Property _findProperty = wid.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
            Model mod = _findProperty.getModel();
            boolean _and_1 = false;
            boolean _and_2 = false;
            boolean _and_3 = false;
            boolean _notEquals_1 = (!Objects.equal(mod, null));
            if (!_notEquals_1) {
              _and_3 = false;
            } else {
              Resource _eResource = mod.eResource();
              boolean _notEquals_2 = (!Objects.equal(_eResource, null));
              _and_3 = _notEquals_2;
            }
            if (!_and_3) {
              _and_2 = false;
            } else {
              Resource _eResource_1 = mod.eResource();
              URI _uRI = _eResource_1.getURI();
              boolean _notEquals_3 = (!Objects.equal(_uRI, null));
              _and_2 = _notEquals_3;
            }
            if (!_and_2) {
              _and_1 = false;
            } else {
              Resource _eResource_2 = mod.eResource();
              URI _uRI_1 = _eResource_2.getURI();
              String _path = _uRI_1.path();
              boolean _endsWith = _path.endsWith("fragment");
              _and_1 = _endsWith;
            }
            if (_and_1) {
              Property _findProperty_1 = wid.findProperty(PropertyTypeConstants.DOMAIN_ASSOCIATION);
              String _value = _findProperty_1.getValue();
              boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(_value);
              boolean _not_1 = (!_isNullOrEmpty);
              if (_not_1) {
                allAttributes.add(wid);
              } else {
                Widget _widget = mod.getWidget();
                this.fetchAllXAttributes(_widget, allAttributes);
              }
            }
          }
        }
      }
      return allAttributes;
    } else {
      EList<Widget> _contents_1 = widget.getContents();
      boolean _isEmpty = _contents_1.isEmpty();
      boolean _not_2 = (!_isEmpty);
      if (_not_2) {
        EList<Widget> _contents_2 = widget.getContents();
        for (final Widget wid_1 : _contents_2) {
          this.fetchAllXAttributes(wid_1, allAttributes);
        }
      }
    }
    return allAttributes;
  }
  
  public List<Widget> fetchActionWidgets(final Widget widget, final List<Widget> actionWidgets) {
    boolean _checkActionWidgets = this.checkActionWidgets(widget);
    if (_checkActionWidgets) {
      Widget _parent = widget.getParent();
      EList<Widget> _contents = _parent.getContents();
      for (final Widget wid : _contents) {
        boolean _checkActionWidgets_1 = this.checkActionWidgets(wid);
        if (_checkActionWidgets_1) {
          boolean _and = false;
          Model _model = wid.getModel();
          boolean _notEquals = (!Objects.equal(_model, null));
          if (!_notEquals) {
            _and = false;
          } else {
            boolean _contains = actionWidgets.contains(wid);
            boolean _not = (!_contains);
            _and = _not;
          }
          if (_and) {
            actionWidgets.add(wid);
          }
          WidgetType _type = wid.getType();
          String _name = _type.getName();
          boolean _equals = _name.equals(WidgetTypeConstants.TAB);
          if (_equals) {
            EList<Widget> _contents_1 = widget.getContents();
            for (final Widget widw : _contents_1) {
              this.fetchActionWidgets(widw, actionWidgets);
            }
          }
        }
      }
      return actionWidgets;
    } else {
      EList<Widget> _contents_2 = widget.getContents();
      boolean _isEmpty = _contents_2.isEmpty();
      boolean _not_1 = (!_isEmpty);
      if (_not_1) {
        EList<Widget> _contents_3 = widget.getContents();
        for (final Widget wid_1 : _contents_3) {
          this.fetchActionWidgets(wid_1, actionWidgets);
        }
      }
    }
    return actionWidgets;
  }
  
  public void fetchActionEvents(final Widget widget, final List<Event> actionEve) {
    List<Widget> actionWidgets = CollectionLiterals.<Widget>newArrayList();
    this.fetchActionWidgets(widget, actionWidgets);
    for (final Widget actWid : actionWidgets) {
      EList<Event> _events = actWid.getEvents();
      for (final Event eve : _events) {
        actionEve.add(eve);
      }
    }
  }
  
  public boolean checkAttrGrpWidget(final Widget widget) {
    boolean _or = false;
    boolean _or_1 = false;
    boolean _or_2 = false;
    boolean _or_3 = false;
    boolean _or_4 = false;
    boolean _or_5 = false;
    boolean _or_6 = false;
    boolean _or_7 = false;
    WidgetType _type = widget.getType();
    String _name = _type.getName();
    boolean _equals = _name.equals(WidgetTypeConstants.ATTRIBUTE);
    if (_equals) {
      _or_7 = true;
    } else {
      WidgetType _type_1 = widget.getType();
      String _name_1 = _type_1.getName();
      boolean _equals_1 = _name_1.equals(WidgetTypeConstants.LABEL);
      _or_7 = _equals_1;
    }
    if (_or_7) {
      _or_6 = true;
    } else {
      WidgetType _type_2 = widget.getType();
      String _name_2 = _type_2.getName();
      boolean _equals_2 = _name_2.equals(WidgetTypeConstants.ICON);
      _or_6 = _equals_2;
    }
    if (_or_6) {
      _or_5 = true;
    } else {
      WidgetType _type_3 = widget.getType();
      String _name_3 = _type_3.getName();
      boolean _equals_3 = _name_3.equals(WidgetTypeConstants.CHECKBOX);
      _or_5 = _equals_3;
    }
    if (_or_5) {
      _or_4 = true;
    } else {
      WidgetType _type_4 = widget.getType();
      String _name_4 = _type_4.getName();
      boolean _equals_4 = _name_4.equals(WidgetTypeConstants.RADIO_BUTTON);
      _or_4 = _equals_4;
    }
    if (_or_4) {
      _or_3 = true;
    } else {
      WidgetType _type_5 = widget.getType();
      String _name_5 = _type_5.getName();
      boolean _equals_5 = _name_5.equals(WidgetTypeConstants.TEXTFIELD);
      _or_3 = _equals_5;
    }
    if (_or_3) {
      _or_2 = true;
    } else {
      WidgetType _type_6 = widget.getType();
      String _name_6 = _type_6.getName();
      boolean _equals_6 = _name_6.equals(WidgetTypeConstants.TEXTAREA);
      _or_2 = _equals_6;
    }
    if (_or_2) {
      _or_1 = true;
    } else {
      WidgetType _type_7 = widget.getType();
      String _name_7 = _type_7.getName();
      boolean _equals_7 = _name_7.equals(WidgetTypeConstants.SEARCH_FIELD);
      _or_1 = _equals_7;
    }
    if (_or_1) {
      _or = true;
    } else {
      Property _findProperty = widget.findProperty(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
      boolean _notEquals = (!Objects.equal(_findProperty, null));
      _or = _notEquals;
    }
    if (_or) {
      return true;
    }
    return false;
  }
  
  public boolean checkActionWidgets(final Widget widget) {
    boolean _or = false;
    boolean _or_1 = false;
    boolean _or_2 = false;
    boolean _or_3 = false;
    boolean _or_4 = false;
    boolean _or_5 = false;
    boolean _or_6 = false;
    boolean _or_7 = false;
    boolean _or_8 = false;
    boolean _or_9 = false;
    WidgetType _type = widget.getType();
    String _name = _type.getName();
    boolean _equals = _name.equals(WidgetTypeConstants.BUTTON);
    if (_equals) {
      _or_9 = true;
    } else {
      WidgetType _type_1 = widget.getType();
      String _name_1 = _type_1.getName();
      boolean _equals_1 = _name_1.equals(WidgetTypeConstants.LABEL_DOMAIN);
      _or_9 = _equals_1;
    }
    if (_or_9) {
      _or_8 = true;
    } else {
      WidgetType _type_2 = widget.getType();
      String _name_2 = _type_2.getName();
      boolean _equals_2 = _name_2.equals(WidgetTypeConstants.TABLE_COLUMN_ITEM);
      _or_8 = _equals_2;
    }
    if (_or_8) {
      _or_7 = true;
    } else {
      WidgetType _type_3 = widget.getType();
      String _name_3 = _type_3.getName();
      boolean _equals_3 = _name_3.equals("TableGroup");
      _or_7 = _equals_3;
    }
    if (_or_7) {
      _or_6 = true;
    } else {
      WidgetType _type_4 = widget.getType();
      String _name_4 = _type_4.getName();
      boolean _equals_4 = _name_4.equals(WidgetTypeConstants.TAB);
      _or_6 = _equals_4;
    }
    if (_or_6) {
      _or_5 = true;
    } else {
      WidgetType _type_5 = widget.getType();
      String _name_5 = _type_5.getName();
      boolean _equals_5 = _name_5.equals(WidgetTypeConstants.MATRIX_CONTENTCELLITEM);
      _or_5 = _equals_5;
    }
    if (_or_5) {
      _or_4 = true;
    } else {
      WidgetType _type_6 = widget.getType();
      String _name_6 = _type_6.getName();
      boolean _equals_6 = _name_6.equals(WidgetTypeConstants.MATRIX_CELLITEM);
      _or_4 = _equals_6;
    }
    if (_or_4) {
      _or_3 = true;
    } else {
      WidgetType _type_7 = widget.getType();
      String _name_7 = _type_7.getName();
      boolean _equals_7 = _name_7.equals(WidgetTypeConstants.MATRIX_AXIS);
      _or_3 = _equals_7;
    }
    if (_or_3) {
      _or_2 = true;
    } else {
      WidgetType _type_8 = widget.getType();
      String _name_8 = _type_8.getName();
      boolean _equals_8 = _name_8.equals(WidgetTypeConstants.MATRIX_EXTRACOLUMNITEM);
      _or_2 = _equals_8;
    }
    if (_or_2) {
      _or_1 = true;
    } else {
      WidgetType _type_9 = widget.getType();
      String _name_9 = _type_9.getName();
      boolean _equals_9 = _name_9.equals(WidgetTypeConstants.ICON);
      _or_1 = _equals_9;
    }
    if (_or_1) {
      _or = true;
    } else {
      WidgetType _type_10 = widget.getType();
      String _name_10 = _type_10.getName();
      boolean _equals_10 = _name_10.equals(WidgetTypeConstants.SEARCH_FIELD);
      _or = _equals_10;
    }
    if (_or) {
      return true;
    }
    return false;
  }
  
  public List<Widget> fetchTabCond(final Widget widget, final List<Widget> tabedCondPanes) {
    boolean _or = false;
    WidgetType _type = widget.getType();
    String _name = _type.getName();
    boolean _equals = _name.equals(WidgetTypeConstants.TABBED_PANE);
    if (_equals) {
      _or = true;
    } else {
      WidgetType _type_1 = widget.getType();
      String _name_1 = _type_1.getName();
      boolean _equals_1 = _name_1.equals(WidgetTypeConstants.CONDITIONAL);
      _or = _equals_1;
    }
    if (_or) {
      Widget _parent = widget.getParent();
      EList<Widget> _contents = _parent.getContents();
      for (final Widget wid : _contents) {
        boolean _or_1 = false;
        WidgetType _type_2 = wid.getType();
        String _name_2 = _type_2.getName();
        boolean _equals_2 = _name_2.equals(WidgetTypeConstants.TABBED_PANE);
        if (_equals_2) {
          _or_1 = true;
        } else {
          WidgetType _type_3 = wid.getType();
          String _name_3 = _type_3.getName();
          boolean _equals_3 = _name_3.equals(WidgetTypeConstants.CONDITIONAL);
          _or_1 = _equals_3;
        }
        if (_or_1) {
          boolean _and = false;
          Model _model = wid.getModel();
          boolean _notEquals = (!Objects.equal(_model, null));
          if (!_notEquals) {
            _and = false;
          } else {
            boolean _contains = tabedCondPanes.contains(wid);
            boolean _not = (!_contains);
            _and = _not;
          }
          if (_and) {
            tabedCondPanes.add(wid);
          }
        }
      }
      return tabedCondPanes;
    } else {
      WidgetType _type_4 = widget.getType();
      String _name_4 = _type_4.getName();
      boolean _equals_4 = _name_4.equals(WidgetTypeConstants.INCLUDE);
      if (_equals_4) {
        Property _findProperty = widget.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
        Model mod = _findProperty.getModel();
        boolean _and_1 = false;
        boolean _and_2 = false;
        boolean _and_3 = false;
        boolean _notEquals_1 = (!Objects.equal(mod, null));
        if (!_notEquals_1) {
          _and_3 = false;
        } else {
          Resource _eResource = mod.eResource();
          boolean _notEquals_2 = (!Objects.equal(_eResource, null));
          _and_3 = _notEquals_2;
        }
        if (!_and_3) {
          _and_2 = false;
        } else {
          Resource _eResource_1 = mod.eResource();
          URI _uRI = _eResource_1.getURI();
          boolean _notEquals_3 = (!Objects.equal(_uRI, null));
          _and_2 = _notEquals_3;
        }
        if (!_and_2) {
          _and_1 = false;
        } else {
          Resource _eResource_2 = mod.eResource();
          URI _uRI_1 = _eResource_2.getURI();
          String _path = _uRI_1.path();
          boolean _endsWith = _path.endsWith("fragment");
          _and_1 = _endsWith;
        }
        if (_and_1) {
          Widget _widget = mod.getWidget();
          this.fetchTabCond(_widget, tabedCondPanes);
        }
      } else {
        EList<Widget> _contents_1 = widget.getContents();
        boolean _isEmpty = _contents_1.isEmpty();
        boolean _not_1 = (!_isEmpty);
        if (_not_1) {
          EList<Widget> _contents_2 = widget.getContents();
          for (final Widget wid_1 : _contents_2) {
            this.fetchTabCond(wid_1, tabedCondPanes);
          }
        }
      }
    }
    return tabedCondPanes;
  }
  
  public List<Widget> fetchTabs(final Widget widget, final String widgetType, final List<Widget> tabs) {
    WidgetType _type = widget.getType();
    String _name = _type.getName();
    boolean _equals = _name.equals(widgetType);
    if (_equals) {
      Widget _parent = widget.getParent();
      EList<Widget> _contents = _parent.getContents();
      for (final Widget wid : _contents) {
        WidgetType _type_1 = wid.getType();
        String _name_1 = _type_1.getName();
        boolean _equals_1 = _name_1.equals(widgetType);
        if (_equals_1) {
          boolean _and = false;
          boolean _and_1 = false;
          Model _model = wid.getModel();
          boolean _notEquals = (!Objects.equal(_model, null));
          if (!_notEquals) {
            _and_1 = false;
          } else {
            String _propertyValue = wid.getPropertyValue(PropertyTypeConstants.ENABLED);
            boolean _equalsIgnoreCase = _propertyValue.equalsIgnoreCase("True");
            _and_1 = _equalsIgnoreCase;
          }
          if (!_and_1) {
            _and = false;
          } else {
            boolean _contains = tabs.contains(wid);
            boolean _not = (!_contains);
            _and = _not;
          }
          if (_and) {
            tabs.add(wid);
          }
        }
      }
      return tabs;
    } else {
      EList<Widget> _contents_1 = widget.getContents();
      boolean _isEmpty = _contents_1.isEmpty();
      boolean _not_1 = (!_isEmpty);
      if (_not_1) {
        EList<Widget> _contents_2 = widget.getContents();
        for (final Widget wid_1 : _contents_2) {
          this.fetchTabs(wid_1, widgetType, tabs);
        }
      }
    }
    return tabs;
  }
  
  public List<Widget> fetchConditions(final Widget widget, final String widgetType, final List<Widget> conditions) {
    WidgetType _type = widget.getType();
    String _name = _type.getName();
    boolean _equals = _name.equals(widgetType);
    if (_equals) {
      Widget _parent = widget.getParent();
      EList<Widget> _contents = _parent.getContents();
      for (final Widget wid : _contents) {
        WidgetType _type_1 = wid.getType();
        String _name_1 = _type_1.getName();
        boolean _equals_1 = _name_1.equals(widgetType);
        if (_equals_1) {
          boolean _and = false;
          Model _model = wid.getModel();
          boolean _notEquals = (!Objects.equal(_model, null));
          if (!_notEquals) {
            _and = false;
          } else {
            boolean _contains = conditions.contains(wid);
            boolean _not = (!_contains);
            _and = _not;
          }
          if (_and) {
            conditions.add(wid);
          }
        }
      }
      return conditions;
    } else {
      EList<Widget> _contents_1 = widget.getContents();
      boolean _isEmpty = _contents_1.isEmpty();
      boolean _not_1 = (!_isEmpty);
      if (_not_1) {
        EList<Widget> _contents_2 = widget.getContents();
        for (final Widget wid_1 : _contents_2) {
          this.fetchConditions(wid_1, widgetType, conditions);
        }
      }
    }
    return conditions;
  }
  
  public CharSequence moduleConditions(final Widget cndtnl, final IOfsProject project, final String containmentValue) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    _builder.append("<ul>");
    _builder.newLine();
    _builder.append("<li>Condition: ");
    String _propertyValue = cndtnl.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
    _builder.append(_propertyValue, "");
    _builder.append(" ");
    _builder.newLineIfNotEmpty();
    {
      String _propertyValue_1 = cndtnl.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(_propertyValue_1);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        _builder.append("  \t\t\t");
        _builder.append("<p> Description: ");
        String _propertyValue_2 = cndtnl.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
        String _replaceAll = _propertyValue_2.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
        _builder.append(_replaceAll, "  \t\t\t");
        _builder.append(" </p>");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("  \t\t\t");
    List<Widget> tabedCondPanes = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    _builder.append("  \t\t\t");
    List<Widget> tabCondMenu = this.fetchTabCond(cndtnl, tabedCondPanes);
    _builder.newLineIfNotEmpty();
    {
      for(final Widget tabedCondtnlPane : tabCondMenu) {
        {
          WidgetType _type = tabedCondtnlPane.getType();
          String _name = _type.getName();
          boolean _equals = _name.equals(WidgetTypeConstants.TABBED_PANE);
          if (_equals) {
            _builder.append("\t\t");
            _builder.append("<p>\t");
            Object _printForTab = this.printForTab(tabedCondtnlPane, project, containmentValue);
            _builder.append(_printForTab, "\t\t");
            _builder.append(" </p>");
            _builder.newLineIfNotEmpty();
          } else {
            WidgetType _type_1 = tabedCondtnlPane.getType();
            String _name_1 = _type_1.getName();
            boolean _equals_1 = _name_1.equals(WidgetTypeConstants.CONDITIONAL);
            if (_equals_1) {
              _builder.append("\t\t");
              _builder.append("<p>\t");
              Object _printForConditional = this.printForConditional(tabedCondtnlPane, project, containmentValue);
              _builder.append(_printForConditional, "\t\t");
              _builder.append(" </p>");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
    }
    _builder.append("\t\t");
    tabedCondPanes.clear();
    _builder.newLineIfNotEmpty();
    _builder.append("</li>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</ul>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence moduleXConditions(final Widget cndtnl, final IOfsProject project, final String containmentValue) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    _builder.append("<ul>");
    _builder.newLine();
    _builder.append("<li>Condition: ");
    String _propertyValue = cndtnl.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
    _builder.append(_propertyValue, "");
    _builder.append(" <br>");
    _builder.newLineIfNotEmpty();
    {
      String _propertyValue_1 = cndtnl.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(_propertyValue_1);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        _builder.append("<p>Description: ");
        String _propertyValue_2 = cndtnl.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
        String _replaceAll = _propertyValue_2.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
        _builder.append(_replaceAll, "");
        _builder.append("</p>");
        _builder.newLineIfNotEmpty();
        _builder.append("  \t\t\t");
      }
    }
    _builder.append("<br>");
    _builder.newLineIfNotEmpty();
    _builder.append("Code: ");
    String _propertyValue_3 = cndtnl.getPropertyValue(PropertyTypeConstants.JAVA_CODE);
    _builder.append(_propertyValue_3, "");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    List<Widget> tabedCondPanes = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    _builder.append("  \t\t\t");
    List<Widget> tabCondMenu = this.fetchTabCond(cndtnl, tabedCondPanes);
    _builder.newLineIfNotEmpty();
    {
      for(final Widget tabedCondtnlPane : tabCondMenu) {
        {
          WidgetType _type = tabedCondtnlPane.getType();
          String _name = _type.getName();
          boolean _equals = _name.equals(WidgetTypeConstants.TABBED_PANE);
          if (_equals) {
            _builder.append("\t\t");
            _builder.append("<p>\t");
            CharSequence _printForTab = this.printForTab(tabedCondtnlPane, project, containmentValue);
            _builder.append(_printForTab, "\t\t");
            _builder.append(" </p>");
            _builder.newLineIfNotEmpty();
          } else {
            WidgetType _type_1 = tabedCondtnlPane.getType();
            String _name_1 = _type_1.getName();
            boolean _equals_1 = _name_1.equals(WidgetTypeConstants.CONDITIONAL);
            if (_equals_1) {
              _builder.append("\t\t");
              _builder.append("<p>\t");
              CharSequence _printForConditional = this.printForConditional(tabedCondtnlPane, project, containmentValue);
              _builder.append(_printForConditional, "\t\t");
              _builder.append(" </p>");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
    }
    _builder.append("\t\t");
    tabedCondPanes.clear();
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("</li>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</ul>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence moduleIndex(final String path, final List<String> fileList) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _htmlOpening = this.htmlOpening();
    _builder.append(_htmlOpening, "");
    _builder.newLineIfNotEmpty();
    _builder.append("<ul>");
    _builder.newLine();
    {
      for(final String moduleFile : fileList) {
        _builder.append("\t");
        _builder.append("<li>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("<A HREF=\"");
        _builder.append((path + "\\"), "\t\t");
        _builder.append(moduleFile, "\t\t");
        _builder.append("\">");
        String _titleForFileName = GenerationUtils.getTitleForFileName(moduleFile);
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
  
  public void fetchAllWidgets(final Widget widget, final List<Widget> allWidgets) {
    boolean _and = false;
    boolean _notEquals = (!Objects.equal(widget, null));
    if (!_notEquals) {
      _and = false;
    } else {
      boolean _isValidWidget = this.isValidWidget(widget);
      _and = _isValidWidget;
    }
    if (_and) {
      allWidgets.add(widget);
      EList<Widget> _contents = widget.getContents();
      for (final Widget wid : _contents) {
        this.fetchAllWidgets(wid, allWidgets);
      }
    } else {
      WidgetType _type = widget.getType();
      String _name = _type.getName();
      boolean _equals = _name.equals(WidgetTypeConstants.INCLUDE);
      if (_equals) {
        Property _findProperty = widget.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
        Model mod = _findProperty.getModel();
        boolean _and_1 = false;
        boolean _and_2 = false;
        boolean _and_3 = false;
        boolean _notEquals_1 = (!Objects.equal(mod, null));
        if (!_notEquals_1) {
          _and_3 = false;
        } else {
          Resource _eResource = mod.eResource();
          boolean _notEquals_2 = (!Objects.equal(_eResource, null));
          _and_3 = _notEquals_2;
        }
        if (!_and_3) {
          _and_2 = false;
        } else {
          Resource _eResource_1 = mod.eResource();
          URI _uRI = _eResource_1.getURI();
          boolean _notEquals_3 = (!Objects.equal(_uRI, null));
          _and_2 = _notEquals_3;
        }
        if (!_and_2) {
          _and_1 = false;
        } else {
          Resource _eResource_2 = mod.eResource();
          URI _uRI_1 = _eResource_2.getURI();
          String _path = _uRI_1.path();
          boolean _endsWith = _path.endsWith("fragment");
          _and_1 = _endsWith;
        }
        if (_and_1) {
          Widget _widget = mod.getWidget();
          this.fetchAllWidgets(_widget, allWidgets);
        }
      } else {
        EList<Widget> _contents_1 = widget.getContents();
        boolean _isEmpty = _contents_1.isEmpty();
        boolean _not = (!_isEmpty);
        if (_not) {
          EList<Widget> _contents_2 = widget.getContents();
          for (final Widget wid_1 : _contents_2) {
            this.fetchAllWidgets(wid_1, allWidgets);
          }
        }
      }
    }
  }
  
  public boolean isValidWidget(final Widget widget) {
    boolean _or = false;
    boolean _or_1 = false;
    boolean _or_2 = false;
    boolean _or_3 = false;
    boolean _or_4 = false;
    boolean _or_5 = false;
    boolean _or_6 = false;
    boolean _or_7 = false;
    boolean _or_8 = false;
    boolean _or_9 = false;
    boolean _or_10 = false;
    boolean _or_11 = false;
    boolean _or_12 = false;
    WidgetType _type = widget.getType();
    String _name = _type.getName();
    boolean _equals = _name.equals(WidgetTypeConstants.BUTTON);
    if (_equals) {
      _or_12 = true;
    } else {
      WidgetType _type_1 = widget.getType();
      String _name_1 = _type_1.getName();
      boolean _equals_1 = _name_1.equals(WidgetTypeConstants.BOX);
      _or_12 = _equals_1;
    }
    if (_or_12) {
      _or_11 = true;
    } else {
      WidgetType _type_2 = widget.getType();
      String _name_2 = _type_2.getName();
      boolean _equals_2 = _name_2.equals(WidgetTypeConstants.TABLE_COLUMN);
      _or_11 = _equals_2;
    }
    if (_or_11) {
      _or_10 = true;
    } else {
      WidgetType _type_3 = widget.getType();
      String _name_3 = _type_3.getName();
      boolean _equals_3 = _name_3.equals(WidgetTypeConstants.TABLE_COLUMN_ITEM);
      _or_10 = _equals_3;
    }
    if (_or_10) {
      _or_9 = true;
    } else {
      boolean _isDomainAttrWidget = this.isDomainAttrWidget(widget);
      _or_9 = _isDomainAttrWidget;
    }
    if (_or_9) {
      _or_8 = true;
    } else {
      WidgetType _type_4 = widget.getType();
      String _name_4 = _type_4.getName();
      boolean _equals_4 = _name_4.equals("TableGroup");
      _or_8 = _equals_4;
    }
    if (_or_8) {
      _or_7 = true;
    } else {
      WidgetType _type_5 = widget.getType();
      String _name_5 = _type_5.getName();
      boolean _equals_5 = _name_5.equals(WidgetTypeConstants.TAB);
      _or_7 = _equals_5;
    }
    if (_or_7) {
      _or_6 = true;
    } else {
      WidgetType _type_6 = widget.getType();
      String _name_6 = _type_6.getName();
      boolean _equals_6 = _name_6.equals(WidgetTypeConstants.MATRIX_CELL);
      _or_6 = _equals_6;
    }
    if (_or_6) {
      _or_5 = true;
    } else {
      WidgetType _type_7 = widget.getType();
      String _name_7 = _type_7.getName();
      boolean _equals_7 = _name_7.equals(WidgetTypeConstants.MATRIX_AXIS);
      _or_5 = _equals_7;
    }
    if (_or_5) {
      _or_4 = true;
    } else {
      WidgetType _type_8 = widget.getType();
      String _name_8 = _type_8.getName();
      boolean _equals_8 = _name_8.equals(WidgetTypeConstants.MATRIX_CELLITEM);
      _or_4 = _equals_8;
    }
    if (_or_4) {
      _or_3 = true;
    } else {
      WidgetType _type_9 = widget.getType();
      String _name_9 = _type_9.getName();
      boolean _equals_9 = _name_9.equals(WidgetTypeConstants.MATRIX_EXTRACOLUMNITEM);
      _or_3 = _equals_9;
    }
    if (_or_3) {
      _or_2 = true;
    } else {
      WidgetType _type_10 = widget.getType();
      String _name_10 = _type_10.getName();
      boolean _equals_10 = _name_10.equals(WidgetTypeConstants.MATRIX_CONTENTCELLITEM);
      _or_2 = _equals_10;
    }
    if (_or_2) {
      _or_1 = true;
    } else {
      WidgetType _type_11 = widget.getType();
      String _name_11 = _type_11.getName();
      boolean _equals_11 = _name_11.equals(WidgetTypeConstants.ICON);
      _or_1 = _equals_11;
    }
    if (_or_1) {
      _or = true;
    } else {
      WidgetType _type_12 = widget.getType();
      String _name_12 = _type_12.getName();
      boolean _equals_12 = _name_12.equals(WidgetTypeConstants.MODULE);
      _or = _equals_12;
    }
    if (_or) {
      return true;
    }
    return false;
  }
  
  public boolean isDomainAttrWidget(final Widget widget) {
    boolean _or = false;
    boolean _or_1 = false;
    boolean _or_2 = false;
    boolean _or_3 = false;
    boolean _or_4 = false;
    boolean _or_5 = false;
    boolean _or_6 = false;
    boolean _or_7 = false;
    boolean _or_8 = false;
    boolean _or_9 = false;
    boolean _or_10 = false;
    boolean _or_11 = false;
    WidgetType _type = widget.getType();
    String _name = _type.getName();
    boolean _equals = _name.equals(WidgetTypeConstants.BUTTON);
    if (_equals) {
      _or_11 = true;
    } else {
      WidgetType _type_1 = widget.getType();
      String _name_1 = _type_1.getName();
      boolean _equals_1 = _name_1.equals("AbstractButton");
      _or_11 = _equals_1;
    }
    if (_or_11) {
      _or_10 = true;
    } else {
      WidgetType _type_2 = widget.getType();
      String _name_2 = _type_2.getName();
      boolean _equals_2 = _name_2.equals(WidgetTypeConstants.CHECKBOX);
      _or_10 = _equals_2;
    }
    if (_or_10) {
      _or_9 = true;
    } else {
      WidgetType _type_3 = widget.getType();
      String _name_3 = _type_3.getName();
      boolean _equals_3 = _name_3.equals(WidgetTypeConstants.TAB);
      _or_9 = _equals_3;
    }
    if (_or_9) {
      _or_8 = true;
    } else {
      WidgetType _type_4 = widget.getType();
      String _name_4 = _type_4.getName();
      boolean _equals_4 = _name_4.equals(WidgetTypeConstants.COLUMN);
      _or_8 = _equals_4;
    }
    if (_or_8) {
      _or_7 = true;
    } else {
      WidgetType _type_5 = widget.getType();
      String _name_5 = _type_5.getName();
      boolean _equals_5 = _name_5.equals(WidgetTypeConstants.COLUMN_HEADER);
      _or_7 = _equals_5;
    }
    if (_or_7) {
      _or_6 = true;
    } else {
      WidgetType _type_6 = widget.getType();
      String _name_6 = _type_6.getName();
      boolean _equals_6 = _name_6.equals(WidgetTypeConstants.HIDDEN_FIELD);
      _or_6 = _equals_6;
    }
    if (_or_6) {
      _or_5 = true;
    } else {
      WidgetType _type_7 = widget.getType();
      String _name_7 = _type_7.getName();
      boolean _equals_7 = _name_7.equals(WidgetTypeConstants.LABEL);
      _or_5 = _equals_7;
    }
    if (_or_5) {
      _or_4 = true;
    } else {
      WidgetType _type_8 = widget.getType();
      String _name_8 = _type_8.getName();
      boolean _equals_8 = _name_8.equals(WidgetTypeConstants.LABEL_DOMAIN);
      _or_4 = _equals_8;
    }
    if (_or_4) {
      _or_3 = true;
    } else {
      WidgetType _type_9 = widget.getType();
      String _name_9 = _type_9.getName();
      boolean _equals_9 = _name_9.equals(WidgetTypeConstants.PAGE);
      _or_3 = _equals_9;
    }
    if (_or_3) {
      _or_2 = true;
    } else {
      WidgetType _type_10 = widget.getType();
      String _name_10 = _type_10.getName();
      boolean _equals_10 = _name_10.equals(WidgetTypeConstants.RADIO_BUTTON);
      _or_2 = _equals_10;
    }
    if (_or_2) {
      _or_1 = true;
    } else {
      WidgetType _type_11 = widget.getType();
      String _name_11 = _type_11.getName();
      boolean _equals_11 = _name_11.equals(WidgetTypeConstants.TABLE_COLUMN);
      _or_1 = _equals_11;
    }
    if (_or_1) {
      _or = true;
    } else {
      WidgetType _type_12 = widget.getType();
      String _name_12 = _type_12.getName();
      boolean _equals_12 = _name_12.equals("TableExtra");
      _or = _equals_12;
    }
    if (_or) {
      return true;
    }
    return false;
  }
  
  public String getTransValue(final Widget widget) {
    String _switchResult = null;
    WidgetType _type = widget.getType();
    TranslationSupport _translationSupport = _type.getTranslationSupport();
    String _name = _translationSupport.name();
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(_name, "NAME")) {
        _matched=true;
        _switchResult = "Label";
      }
    }
    if (!_matched) {
      if (Objects.equal(_name, "TEXT")) {
        _matched=true;
        _switchResult = "ToolTip";
      }
    }
    return _switchResult;
  }
}
