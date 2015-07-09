package com.odcgroup.page.docgen.cartridges.mapper;

import com.google.common.base.Objects;
import com.odcgroup.page.docgen.utils.GenerationUtils;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class PageModel2PageDocumentation {
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
  
  public CharSequence pageDescription(final Model model) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _htmlOpening = this.htmlOpening();
    _builder.append(_htmlOpening, "");
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    String _propertyValue = _widget.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
    String docText = _propertyValue.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
    _builder.newLineIfNotEmpty();
    {
      boolean _and = false;
      boolean _notEquals = (!Objects.equal(docText, null));
      if (!_notEquals) {
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
    _builder.append("Model Path: <I>");
    Resource _eResource = model.eResource();
    URI _uRI = _eResource.getURI();
    String _path = _uRI.path();
    Resource _eResource_1 = model.eResource();
    URI _uRI_1 = _eResource_1.getURI();
    String _path_1 = _uRI_1.path();
    int _length = _path_1.length();
    int _minus = (_length - 5);
    String _substring = _path.substring(0, _minus);
    _builder.append(_substring, "");
    _builder.append("</I>");
    _builder.newLineIfNotEmpty();
    CharSequence _htmlClosing = this.htmlClosing();
    _builder.append(_htmlClosing, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence pageIncludes(final Model model) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> includeSources = CollectionLiterals.<Widget>newArrayList();
    _builder.append("\t");
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    this.fetchIncludeSources(_widget, WidgetTypeConstants.INCLUDE, includeSources);
    _builder.newLineIfNotEmpty();
    List<Widget> xspInclPaths = CollectionLiterals.<Widget>newArrayList();
    _builder.append("\t");
    _builder.newLineIfNotEmpty();
    Widget _widget_1 = model.getWidget();
    this.fetchIncludeSources(_widget_1, "IncludeXSP", xspInclPaths);
    _builder.newLineIfNotEmpty();
    List<Widget> extPageOrImgUrls = CollectionLiterals.<Widget>newArrayList();
    _builder.append("\t");
    _builder.newLineIfNotEmpty();
    Widget _widget_2 = model.getWidget();
    this.fetchIncludeSources(_widget_2, WidgetTypeConstants.EXTERNAL_INCLUDE_WIDGET, extPageOrImgUrls);
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
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>Module: ");
            int _lastIndexOf = widSrc.lastIndexOf(".");
            String _substring = widSrc.substring(0, _lastIndexOf);
            _builder.append(_substring, "\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            Widget _parent = inclWid.getParent();
            String _fetchParent = this.fetchParent(_parent);
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
            String _fetchParent_1 = this.fetchParent(_parent_1);
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
            String _fetchParent_2 = this.fetchParent(_parent_2);
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
    return _builder;
  }
  
  public void fetchIncludeSources(final Widget widget, final String widgetType, final List<Widget> includeSources) {
    WidgetType _type = widget.getType();
    String _name = _type.getName();
    boolean _equals = _name.equals(widgetType);
    if (_equals) {
      Model _model = widget.getModel();
      boolean _notEquals = (!Objects.equal(_model, null));
      if (_notEquals) {
        includeSources.add(widget);
      }
    } else {
      EList<Widget> _contents = widget.getContents();
      boolean _isEmpty = _contents.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        EList<Widget> _contents_1 = widget.getContents();
        for (final Widget wid : _contents_1) {
          this.fetchIncludeSources(wid, widgetType, includeSources);
        }
      }
    }
  }
  
  public String fetchParent(final Widget widget) {
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
          Widget _parent_1 = widget.getParent();
          String cond_1 = _parent_1.getPropertyValue("tab-name");
          boolean _isNullOrEmpty_6 = StringExtensions.isNullOrEmpty(cond_1);
          boolean _not_6 = (!_isNullOrEmpty_6);
          if (_not_6) {
            return ("Tab: " + cond_1);
          } else {
            return "";
          }
        } else {
          Widget _parent_2 = widget.getParent();
          _xifexpression_5 = this.fetchParent(_parent_2);
        }
        _xifexpression_1 = _xifexpression_5;
      }
      _xifexpression = _xifexpression_1;
    } else {
      return "";
    }
    return _xifexpression;
  }
  
  public List<Widget> collectNestedModuleConds(final Widget condition) {
    List<Widget> condtnls = CollectionLiterals.<Widget>newArrayList();
    boolean _isEmpty = condtnls.isEmpty();
    if (_isEmpty) {
      this.fetchNestedConditionals(condition, WidgetTypeConstants.CONDITIONAL, condtnls);
    }
    return condtnls;
  }
  
  public CharSequence pageContainers(final Model model, final boolean isLarge) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> conditionals = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    this.fetchConditionals(_widget, WidgetTypeConstants.CONDITIONAL, conditionals);
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = conditionals.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        _builder.append("List of containers:<br>");
        _builder.newLine();
        List<Widget> conditions = CollectionLiterals.<Widget>newArrayList();
        _builder.newLineIfNotEmpty();
        {
          for(final Widget cndtnWidget : conditionals) {
            _builder.append("\t");
            this.fetchConditions(cndtnWidget, WidgetTypeConstants.CONDITIONAL_BODY, conditions);
            _builder.newLineIfNotEmpty();
            {
              if (isLarge) {
                _builder.append("\t");
                String _propertyValue = cndtnWidget.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
                CharSequence _pageXConditions = this.pageXConditions(_propertyValue, conditions);
                _builder.append(_pageXConditions, "\t");
                _builder.newLineIfNotEmpty();
              } else {
                _builder.append("\t");
                String _propertyValue_1 = cndtnWidget.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
                CharSequence _pageConditions = this.pageConditions(_propertyValue_1, conditions);
                _builder.append(_pageConditions, "\t");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.append("\t");
            conditions.clear();
            _builder.newLineIfNotEmpty();
          }
        }
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    conditionals.clear();
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public void fetchConditionals(final Widget widget, final String widgetType, final List<Widget> conditionals) {
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
        boolean _contains = conditionals.contains(widget);
        boolean _not = (!_contains);
        _and = _not;
      }
      if (_and) {
        conditionals.add(widget);
      }
    } else {
      EList<Widget> _contents = widget.getContents();
      boolean _isEmpty = _contents.isEmpty();
      boolean _not_1 = (!_isEmpty);
      if (_not_1) {
        EList<Widget> _contents_1 = widget.getContents();
        for (final Widget wid : _contents_1) {
          this.fetchConditionals(wid, widgetType, conditionals);
        }
      }
    }
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
  
  public void fetchConditions(final Widget widget, final String widgetType, final List<Widget> conditions) {
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
        boolean _contains = conditions.contains(widget);
        boolean _not = (!_contains);
        _and = _not;
      }
      if (_and) {
        conditions.add(widget);
      }
    } else {
      EList<Widget> _contents = widget.getContents();
      boolean _isEmpty = _contents.isEmpty();
      boolean _not_1 = (!_isEmpty);
      if (_not_1) {
        EList<Widget> _contents_1 = widget.getContents();
        for (final Widget wid : _contents_1) {
          this.fetchConditions(wid, widgetType, conditions);
        }
      }
    }
  }
  
  public CharSequence pageConditions(final String conditonalName, final List<Widget> conditions) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Conditional: ");
    _builder.append(conditonalName, "");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<ul>");
    _builder.newLine();
    {
      for(final Widget cndtn : conditions) {
        _builder.append("  \t\t\t");
        _builder.append("<li>Condition: ");
        String _propertyValue = cndtn.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
        _builder.append(_propertyValue, "  \t\t\t");
        _builder.append(" ");
        _builder.newLineIfNotEmpty();
        {
          boolean _and = false;
          String _propertyValue_1 = cndtn.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
          boolean _notEquals = (!Objects.equal(_propertyValue_1, null));
          if (!_notEquals) {
            _and = false;
          } else {
            String _propertyValue_2 = cndtn.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
            boolean _isEmpty = _propertyValue_2.isEmpty();
            boolean _not = (!_isEmpty);
            _and = _not;
          }
          if (_and) {
            _builder.append("<p>Description: ");
            String _propertyValue_3 = cndtn.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
            _builder.append(_propertyValue_3, "");
            _builder.append("</p>");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          List<Widget> _collectNestedModuleConds = this.collectNestedModuleConds(cndtn);
          for(final Widget cndtnlWidget : _collectNestedModuleConds) {
            List<Widget> conds = CollectionLiterals.<Widget>newArrayList();
            _builder.newLineIfNotEmpty();
            this.fetchConditions(cndtnlWidget, WidgetTypeConstants.CONDITIONAL_BODY, conds);
            _builder.newLineIfNotEmpty();
            _builder.append("<ul>");
            _builder.newLine();
            _builder.append("<li> Conditional: ");
            String _propertyValue_4 = cndtnlWidget.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
            _builder.append(_propertyValue_4, "");
            _builder.newLineIfNotEmpty();
            _builder.append("<ul>");
            _builder.newLine();
            {
              for(final Widget subCndtn : conds) {
                _builder.append("  \t\t\t\t\t");
                _builder.append("<li>Condition: ");
                String _propertyValue_5 = subCndtn.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
                _builder.append(_propertyValue_5, "  \t\t\t\t\t");
                _builder.append(" ");
                _builder.newLineIfNotEmpty();
                {
                  boolean _and_1 = false;
                  String _propertyValue_6 = subCndtn.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                  boolean _notEquals_1 = (!Objects.equal(_propertyValue_6, null));
                  if (!_notEquals_1) {
                    _and_1 = false;
                  } else {
                    String _propertyValue_7 = subCndtn.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                    boolean _isEmpty_1 = _propertyValue_7.isEmpty();
                    boolean _not_1 = (!_isEmpty_1);
                    _and_1 = _not_1;
                  }
                  if (_and_1) {
                    _builder.append("<p>Description: ");
                    String _propertyValue_8 = subCndtn.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                    _builder.append(_propertyValue_8, "");
                    _builder.append("</p>");
                    _builder.newLineIfNotEmpty();
                  }
                }
                _builder.append("</li>\t\t\t");
                _builder.newLine();
              }
            }
            _builder.append("  \t\t\t\t\t");
            conds.clear();
            _builder.newLineIfNotEmpty();
            _builder.append("  \t\t\t\t");
            _builder.append("</ul>\t");
            _builder.newLine();
            _builder.append("  \t\t\t\t");
            _builder.append("</li>\t\t");
            _builder.newLine();
            _builder.append("  \t\t\t\t");
            _builder.append("</ul>\t\t\t");
            _builder.newLine();
          }
        }
        _builder.append("  \t\t\t");
        _builder.append("</li>\t");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("</ul>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence pageXConditions(final String conditonalName, final List<Widget> conditions) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Conditional: ");
    _builder.append(conditonalName, "");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<ul>");
    _builder.newLine();
    {
      for(final Widget cndtn : conditions) {
        _builder.append("  \t\t\t");
        _builder.append("<li>Condition: ");
        String _propertyValue = cndtn.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
        _builder.append(_propertyValue, "  \t\t\t");
        _builder.append(" ");
        _builder.newLineIfNotEmpty();
        _builder.append("  \t\t\t");
        {
          String _propertyValue_1 = cndtn.getPropertyValue(PropertyTypeConstants.JAVA_CODE);
          boolean _notEquals = (!Objects.equal(_propertyValue_1, null));
          if (_notEquals) {
            _builder.append("<p>Code: ");
            String _propertyValue_2 = cndtn.getPropertyValue(PropertyTypeConstants.JAVA_CODE);
            _builder.append(_propertyValue_2, "  \t\t\t");
            _builder.append("</p>");
          }
        }
        _builder.newLineIfNotEmpty();
        {
          boolean _and = false;
          String _propertyValue_3 = cndtn.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
          boolean _notEquals_1 = (!Objects.equal(_propertyValue_3, null));
          if (!_notEquals_1) {
            _and = false;
          } else {
            String _propertyValue_4 = cndtn.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
            boolean _isEmpty = _propertyValue_4.isEmpty();
            boolean _not = (!_isEmpty);
            _and = _not;
          }
          if (_and) {
            _builder.append("  \t\t\t");
            _builder.append("<p>Description: ");
            String _propertyValue_5 = cndtn.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
            _builder.append(_propertyValue_5, "  \t\t\t");
            _builder.append("</p>");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("  \t\t\t\t");
        List<Widget> conds = CollectionLiterals.<Widget>newArrayList();
        _builder.newLineIfNotEmpty();
        {
          List<Widget> _collectNestedModuleConds = this.collectNestedModuleConds(cndtn);
          for(final Widget cndtnlWidget : _collectNestedModuleConds) {
            this.fetchConditions(cndtnlWidget, WidgetTypeConstants.CONDITIONAL_BODY, conds);
            _builder.newLineIfNotEmpty();
            _builder.append("<ul>");
            _builder.newLine();
            _builder.append("<li> Conditional: ");
            String _propertyValue_6 = cndtnlWidget.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
            _builder.append(_propertyValue_6, "");
            _builder.newLineIfNotEmpty();
            _builder.append("<ul>");
            _builder.newLine();
            {
              for(final Widget subCndtn : conds) {
                _builder.append("  \t\t\t\t\t");
                _builder.append("<li>Condition: ");
                String _propertyValue_7 = subCndtn.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
                _builder.append(_propertyValue_7, "  \t\t\t\t\t");
                _builder.append(" ");
                _builder.newLineIfNotEmpty();
                _builder.append("  \t\t\t\t\t\t");
                {
                  String _propertyValue_8 = subCndtn.getPropertyValue(PropertyTypeConstants.JAVA_CODE);
                  boolean _notEquals_2 = (!Objects.equal(_propertyValue_8, null));
                  if (_notEquals_2) {
                    _builder.append("<p>Code: ");
                    String _propertyValue_9 = subCndtn.getPropertyValue(PropertyTypeConstants.JAVA_CODE);
                    _builder.append(_propertyValue_9, "  \t\t\t\t\t\t");
                    _builder.append("</p>");
                  }
                }
                _builder.newLineIfNotEmpty();
                {
                  boolean _and_1 = false;
                  String _propertyValue_10 = subCndtn.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                  boolean _notEquals_3 = (!Objects.equal(_propertyValue_10, null));
                  if (!_notEquals_3) {
                    _and_1 = false;
                  } else {
                    String _propertyValue_11 = subCndtn.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                    boolean _isEmpty_1 = _propertyValue_11.isEmpty();
                    boolean _not_1 = (!_isEmpty_1);
                    _and_1 = _not_1;
                  }
                  if (_and_1) {
                    _builder.append("<p>Description: ");
                    String _propertyValue_12 = subCndtn.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                    _builder.append(_propertyValue_12, "");
                    _builder.append("</p>");
                    _builder.newLineIfNotEmpty();
                  }
                }
                _builder.append("</li>\t\t\t");
                _builder.newLine();
              }
            }
            _builder.append("  \t\t\t\t\t");
            conds.clear();
            _builder.newLineIfNotEmpty();
            _builder.append("  \t\t\t\t");
            _builder.append("</ul>\t");
            _builder.newLine();
            _builder.append("  \t\t\t\t");
            _builder.append("</li>\t\t");
            _builder.newLine();
            _builder.append("  \t\t\t\t");
            _builder.append("</ul>\t\t\t");
            _builder.newLine();
          }
        }
        _builder.append("  \t\t\t");
        _builder.append("</li>");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("</ul>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence pageIndex(final String path, final List<String> fileList) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _htmlOpening = this.htmlOpening();
    _builder.append(_htmlOpening, "");
    _builder.newLineIfNotEmpty();
    _builder.append("<ul>");
    _builder.newLine();
    {
      for(final String pageFile : fileList) {
        _builder.append("\t");
        _builder.append("<li>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("    ");
        _builder.append("<A HREF=\"");
        _builder.append((path + "\\"), "\t    ");
        _builder.append(pageFile, "\t    ");
        _builder.append("\">");
        String _titleForFileName = GenerationUtils.getTitleForFileName(pageFile);
        _builder.append(_titleForFileName, "\t    ");
        _builder.append("</A>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t    ");
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
