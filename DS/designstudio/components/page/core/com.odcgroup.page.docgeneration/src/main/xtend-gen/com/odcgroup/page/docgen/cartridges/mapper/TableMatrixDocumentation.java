package com.odcgroup.page.docgen.cartridges.mapper;

import com.google.common.base.Objects;
import com.odcgroup.page.docgen.utils.GenerationUtils;
import com.odcgroup.page.docgen.utils.ModuleUtils;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Translation;
import com.odcgroup.page.model.Widget;
import com.odcgroup.workbench.core.IOfsProject;
import java.util.List;
import org.apache.commons.lang.WordUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class TableMatrixDocumentation {
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
  
  public CharSequence tableDescription(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generalDescription = this.generalDescription(model, project, WidgetTypeConstants.TABLE_TREE);
    _builder.append(_generalDescription, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence matrixDescription(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> matrixWidgets = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    List<Widget> matrixList = this.fetchWidgets(_widget, WidgetTypeConstants.MATRIX, matrixWidgets);
    _builder.newLineIfNotEmpty();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(matrixList);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        {
          for(final Widget matrxWid : matrixList) {
            {
              boolean _or = false;
              Widget _parent = matrxWid.getParent();
              WidgetType _type = _parent.getType();
              String _name = _type.getName();
              boolean _equals = _name.equals(WidgetTypeConstants.CONDITIONAL_BODY);
              if (_equals) {
                _or = true;
              } else {
                Widget _parent_1 = matrxWid.getParent();
                WidgetType _type_1 = _parent_1.getType();
                String _name_1 = _type_1.getName();
                boolean _equals_1 = _name_1.equals(WidgetTypeConstants.TAB);
                _or = _equals_1;
              }
              if (_or) {
                _builder.append("\t");
                _builder.append("This matrix belongs to the ");
                Widget _parent_2 = matrxWid.getParent();
                WidgetType _type_2 = _parent_2.getType();
                String _name_2 = _type_2.getName();
                _builder.append(_name_2, "\t");
                _builder.append(".<br>");
                _builder.newLineIfNotEmpty();
              } else {
                boolean _or_1 = false;
                Widget _rootWidget = matrxWid.getRootWidget();
                WidgetType _type_3 = _rootWidget.getType();
                String _name_3 = _type_3.getName();
                boolean _equals_2 = _name_3.equals(WidgetTypeConstants.MODULE);
                if (_equals_2) {
                  _or_1 = true;
                } else {
                  Widget _rootWidget_1 = matrxWid.getRootWidget();
                  WidgetType _type_4 = _rootWidget_1.getType();
                  String _name_4 = _type_4.getName();
                  boolean _equals_3 = _name_4.equals(WidgetTypeConstants.PAGE);
                  _or_1 = _equals_3;
                }
                if (_or_1) {
                  _builder.append("\t");
                  _builder.append("This matrix belongs to the ");
                  Widget _rootWidget_2 = matrxWid.getRootWidget();
                  WidgetType _type_5 = _rootWidget_2.getType();
                  String _name_5 = _type_5.getName();
                  _builder.append(_name_5, "\t");
                  _builder.append(".<br>");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
            {
              String _propertyValue = matrxWid.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
              boolean _notEquals = (!Objects.equal(_propertyValue, null));
              if (_notEquals) {
                _builder.append("\t");
                String _propertyValue_1 = matrxWid.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                String docText = _propertyValue_1.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
                _builder.newLineIfNotEmpty();
                {
                  boolean _and = false;
                  boolean _notEquals_1 = (!Objects.equal(docText, null));
                  if (!_notEquals_1) {
                    _and = false;
                  } else {
                    boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(docText);
                    boolean _not_1 = (!_isNullOrEmpty_1);
                    _and = _not_1;
                  }
                  if (_and) {
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("Description: ");
                    _builder.append(docText, "\t\t");
                    _builder.append(" <br>");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
            _builder.append("List of properties set on the matrix: <br>");
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
            _builder.append("<td>Dataset reference </td>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("<td>");
            String _datasetName = ModuleUtils.getDatasetName(project, matrxWid);
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
            String _datasetBaseClassName = ModuleUtils.getDatasetBaseClassName(project, matrxWid);
            _builder.append(_datasetBaseClassName, "\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("</table>\t");
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
  
  public CharSequence matrixAxis(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> matrixAxisWidgets = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    List<Widget> matrixAxisList = this.fetchWidgets(_widget, WidgetTypeConstants.MATRIX_AXIS, matrixAxisWidgets);
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = matrixAxisList.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        _builder.append("Matrix axis: <br><br>");
        _builder.newLine();
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<table border=\"1\">");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Axis</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Attribute</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Sorting</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Max Number Of Cells</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Description</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("</tr>");
        _builder.newLine();
        {
          for(final Widget matrxWid : matrixAxisList) {
            _builder.append("\t\t");
            String _propertyValue = matrxWid.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
            String docText = _propertyValue.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<td>");
            {
              String _propertyValue_1 = matrxWid.getPropertyValue("matrixAxis");
              boolean _equals = _propertyValue_1.equals("x-axis");
              if (_equals) {
                _builder.append("X-axis");
              } else {
                String _propertyValue_2 = matrxWid.getPropertyValue("matrixAxis");
                boolean _equals_1 = _propertyValue_2.equals("y-axis");
                if (_equals_1) {
                  _builder.append("Y-axis");
                }
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<td>");
            String _propertyValue_3 = matrxWid.getPropertyValue("domainAttribute");
            _builder.append(_propertyValue_3, "\t\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<td>");
            String _propertyValue_4 = matrxWid.getPropertyValue("group-sorting-column-name");
            _builder.append(_propertyValue_4, "\t\t\t");
            _builder.append(" (");
            String _propertyValue_5 = matrxWid.getPropertyValue("group-sorting-direction");
            _builder.append(_propertyValue_5, "\t\t\t");
            _builder.append(")</td>");
            _builder.newLineIfNotEmpty();
            {
              String _propertyValue_6 = matrxWid.getPropertyValue("matrixAxis");
              boolean _equals_2 = _propertyValue_6.equals("x-axis");
              if (_equals_2) {
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("<td>");
                String _propertyValue_7 = matrxWid.getPropertyValue("maxRows");
                _builder.append(_propertyValue_7, "\t\t\t");
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
              } else {
                String _propertyValue_8 = matrxWid.getPropertyValue("matrixAxis");
                boolean _equals_3 = _propertyValue_8.equals("y-axis");
                if (_equals_3) {
                  _builder.append("\t\t");
                  _builder.append("\t");
                  _builder.append("<td>");
                  String _propertyValue_9 = matrxWid.getPropertyValue("maxColumns");
                  _builder.append(_propertyValue_9, "\t\t\t");
                  _builder.append("</td>");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<td>");
            {
              boolean _and = false;
              boolean _notEquals = (!Objects.equal(docText, null));
              if (!_notEquals) {
                _and = false;
              } else {
                boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(docText);
                boolean _not_1 = (!_isNullOrEmpty);
                _and = _not_1;
              }
              if (_and) {
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("Description: ");
                _builder.append(docText, "\t\t\t");
                _builder.append(" ");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("\t");
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
        _builder.append("\t");
        _builder.append("</table>\t");
        _builder.newLine();
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.append("\t");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence matrixCell(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> matrixCellWidgets = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    List<Widget> matrixCellList = this.fetchWidgets(_widget, WidgetTypeConstants.MATRIX, matrixCellWidgets);
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = matrixCellList.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        _builder.append("Matrix cell content: <br><br>");
        _builder.newLine();
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<table border=\"1\">");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Position</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Item name</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Description</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Type</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Display Format</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Aggregation</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Applies On Row</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Applies On Column</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Applies On Matrix</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("</tr>");
        _builder.newLine();
        {
          for(final Widget matrxCell : matrixCellList) {
            _builder.append("\t\t");
            List<Widget> matrixContentCellWidgets = CollectionLiterals.<Widget>newArrayList();
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            List<Widget> matrixContentCellList = this.fetchWidgets(matrxCell, WidgetTypeConstants.MATRIX_CONTENTCELLITEM, matrixContentCellWidgets);
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            String docText = matrxCell.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
            _builder.newLineIfNotEmpty();
            {
              for(final Widget matrxContentCell : matrixContentCellList) {
                _builder.append("\t\t");
                _builder.append("<tr>");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("<td>");
                int _indexOf = matrixContentCellList.indexOf(matrxContentCell);
                int _plus = (_indexOf + 1);
                _builder.append(_plus, "\t\t\t");
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("<td>");
                {
                  String _propertyValue = matrxContentCell.getPropertyValue("matrixCellItemType");
                  boolean _equalsIgnoreCase = _propertyValue.equalsIgnoreCase("domain");
                  if (_equalsIgnoreCase) {
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t");
                    String _propertyValue_1 = matrxContentCell.getPropertyValue("domainAttribute");
                    _builder.append(_propertyValue_1, "\t\t\t");
                    _builder.newLineIfNotEmpty();
                  } else {
                    String _propertyValue_2 = matrxContentCell.getPropertyValue("matrixCellItemType");
                    boolean _equalsIgnoreCase_1 = _propertyValue_2.equalsIgnoreCase("Computed");
                    if (_equalsIgnoreCase_1) {
                      _builder.append("\t\t");
                      _builder.append("\t");
                      String _propertyValue_3 = matrxContentCell.getPropertyValue("column-name");
                      _builder.append(_propertyValue_3, "\t\t\t");
                      _builder.newLineIfNotEmpty();
                      _builder.append("\t\t");
                      _builder.append("\t");
                    }
                  }
                }
                _builder.append("</td>\t");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("<td>");
                {
                  boolean _and = false;
                  boolean _notEquals = (!Objects.equal(docText, null));
                  if (!_notEquals) {
                    _and = false;
                  } else {
                    boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(docText);
                    boolean _not_1 = (!_isNullOrEmpty);
                    _and = _not_1;
                  }
                  if (_and) {
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t");
                    _builder.append("Description: ");
                    String _replaceAll = docText.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
                    _builder.append(_replaceAll, "\t\t\t");
                    _builder.append(" ");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t");
                  }
                }
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("<td>");
                {
                  String _propertyValue_4 = matrxContentCell.getPropertyValue("matrixCellItemType");
                  boolean _equalsIgnoreCase_2 = _propertyValue_4.equalsIgnoreCase("domain");
                  if (_equalsIgnoreCase_2) {
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t");
                    _builder.append("Attribute");
                    _builder.newLine();
                  } else {
                    String _propertyValue_5 = matrxContentCell.getPropertyValue("matrixCellItemType");
                    boolean _equalsIgnoreCase_3 = _propertyValue_5.equalsIgnoreCase("Computed");
                    if (_equalsIgnoreCase_3) {
                      {
                        String _propertyValue_6 = matrxContentCell.getPropertyValue("column-computation");
                        boolean _equalsIgnoreCase_4 = _propertyValue_6.equalsIgnoreCase("make-amount");
                        if (_equalsIgnoreCase_4) {
                          _builder.append("\t\t");
                          _builder.append("\t");
                          String _propertyValue_7 = matrxContentCell.getPropertyValue("column-computation-parameters");
                          String[] strBrk = _propertyValue_7.split(",");
                          _builder.newLineIfNotEmpty();
                          _builder.append("\t\t");
                          _builder.append("\t");
                          _builder.append("Make Amount ");
                          {
                            final String[] _converted_strBrk = (String[])strBrk;
                            int _size = ((List<String>)Conversions.doWrapArray(_converted_strBrk)).size();
                            boolean _greaterThan = (_size > 1);
                            if (_greaterThan) {
                              _builder.append("(");
                              String _get = strBrk[1];
                              String _string = _get.toString();
                              _builder.append(_string, "\t\t\t");
                              _builder.append(")");
                            }
                          }
                          _builder.newLineIfNotEmpty();
                        } else {
                          String _propertyValue_8 = matrxContentCell.getPropertyValue("column-computation");
                          boolean _equalsIgnoreCase_5 = _propertyValue_8.equalsIgnoreCase("additional column");
                          if (_equalsIgnoreCase_5) {
                            _builder.append("\t\t");
                            _builder.append("\t");
                            _builder.append("Aditional Column");
                            _builder.newLine();
                          } else {
                            String _propertyValue_9 = matrxContentCell.getPropertyValue("column-computation");
                            boolean _equalsIgnoreCase_6 = _propertyValue_9.equalsIgnoreCase("compute-percentage");
                            if (_equalsIgnoreCase_6) {
                              _builder.append("\t\t");
                              _builder.append("\t");
                              _builder.append("Compute Percentage");
                              _builder.newLine();
                            } else {
                              _builder.append("\t\t");
                              _builder.append("\t");
                              String _propertyValue_10 = matrxContentCell.getPropertyValue("column-computation");
                              String _capitalize = WordUtils.capitalize(_propertyValue_10);
                              _builder.append(_capitalize, "\t\t\t");
                              _builder.newLineIfNotEmpty();
                            }
                          }
                        }
                      }
                      _builder.append("\t\t\t\t");
                    }
                  }
                }
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("<td>");
                String _propertyValue_11 = matrxContentCell.getPropertyValue("format");
                _builder.append(_propertyValue_11, "\t\t\t");
                _builder.append("</td>\t");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("<td>");
                _builder.newLine();
                {
                  String _propertyValue_12 = matrxContentCell.getPropertyValue("aggregationType");
                  boolean _equalsIgnoreCase_7 = _propertyValue_12.equalsIgnoreCase("Sum");
                  if (_equalsIgnoreCase_7) {
                    _builder.append("\t\t");
                    _builder.append("\t\t");
                    _builder.append("Sum");
                    _builder.newLine();
                  } else {
                    String _propertyValue_13 = matrxContentCell.getPropertyValue("aggregationType");
                    boolean _equalsIgnoreCase_8 = _propertyValue_13.equalsIgnoreCase("Max");
                    if (_equalsIgnoreCase_8) {
                      _builder.append("\t\t");
                      _builder.append("\t\t");
                      _builder.append("Max\t");
                      _builder.newLine();
                    } else {
                      String _propertyValue_14 = matrxContentCell.getPropertyValue("aggregationType");
                      boolean _equalsIgnoreCase_9 = _propertyValue_14.equalsIgnoreCase("weighted-mean");
                      if (_equalsIgnoreCase_9) {
                        _builder.append("\t\t");
                        _builder.append("\t\t");
                        _builder.append("Weighted Mean(weight: ");
                        String _propertyValue_15 = matrxContentCell.getPropertyValue("matrixMeanWeight");
                        _builder.append(_propertyValue_15, "\t\t\t\t");
                        _builder.append(")\t");
                        _builder.newLineIfNotEmpty();
                      }
                    }
                  }
                }
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("</td>");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("\t");
                Widget matrix = this.getParentWidgetByType(matrxContentCell, WidgetTypeConstants.MATRIX);
                _builder.append("\t");
                _builder.newLineIfNotEmpty();
                {
                  boolean _and_1 = false;
                  boolean _notEquals_1 = (!Objects.equal(matrix, null));
                  if (!_notEquals_1) {
                    _and_1 = false;
                  } else {
                    String _typeName = matrix.getTypeName();
                    boolean _equals = _typeName.equals(WidgetTypeConstants.MATRIX);
                    _and_1 = _equals;
                  }
                  if (_and_1) {
                    _builder.append("\t\t");
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      String _propertyValue_16 = matrix.getPropertyValue("displayLastRow");
                      boolean _notEquals_2 = (!Objects.equal(_propertyValue_16, null));
                      if (_notEquals_2) {
                        _builder.append("Yes");
                      } else {
                        _builder.append("No");
                      }
                    }
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      String _propertyValue_17 = matrix.getPropertyValue("displayLastColumn");
                      boolean _notEquals_3 = (!Objects.equal(_propertyValue_17, null));
                      if (_notEquals_3) {
                        _builder.append("Yes");
                      } else {
                        _builder.append("No");
                      }
                    }
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      String _propertyValue_18 = matrix.getPropertyValue("displayLastCell");
                      boolean _notEquals_4 = (!Objects.equal(_propertyValue_18, null));
                      if (_notEquals_4) {
                        _builder.append("Yes");
                      } else {
                        _builder.append("No");
                      }
                    }
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t\t");
                  } else {
                    _builder.append("<td></td><td></td><td></td>");
                    _builder.newLineIfNotEmpty();
                  }
                }
                _builder.append("\t\t");
                _builder.append("</tr>");
                _builder.newLine();
              }
            }
          }
        }
        _builder.append("\t");
        _builder.append("</table>\t");
        _builder.newLine();
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.append("\t");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence matrixAttributes(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> matrixAxisWidgets = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    List<Widget> matrixAxisList = this.fetchWidgets(_widget, WidgetTypeConstants.MATRIX_AXIS, matrixAxisWidgets);
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = matrixAxisList.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("<table border=\"1\">");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Attribute Name</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Type</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Description</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Applies On Row</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Applies On Column</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Applies On Matrix</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Tooltip</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Axis</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Axis Sorting</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Max Number Of Columns</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Computation Parameter1</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Computation Parameter2</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">CSS Class</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Display Format</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Enable</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Horizontal Alignment</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Container Description</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("</tr>");
        _builder.newLine();
        {
          for(final Widget matrxAxsWid : matrixAxisList) {
            String docText = matrxAxsWid.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
            _builder.newLineIfNotEmpty();
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<td>");
            String _propertyValue = matrxAxsWid.getPropertyValue("domainAttribute");
            _builder.append(_propertyValue, "\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<td>Axis</td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<td>");
            {
              boolean _and = false;
              boolean _notEquals = (!Objects.equal(docText, null));
              if (!_notEquals) {
                _and = false;
              } else {
                boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(docText);
                boolean _not_1 = (!_isNullOrEmpty);
                _and = _not_1;
              }
              if (_and) {
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("Description: ");
                String _replaceAll = docText.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
                _builder.append(_replaceAll, "\t");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<td></td><td></td><td></td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<td>");
            {
              boolean _and_1 = false;
              EList<Translation> _toolTips = matrxAxsWid.getToolTips();
              boolean _notEquals_1 = (!Objects.equal(_toolTips, null));
              if (!_notEquals_1) {
                _and_1 = false;
              } else {
                EList<Translation> _toolTips_1 = matrxAxsWid.getToolTips();
                int _size = _toolTips_1.size();
                boolean _greaterThan = (_size > 0);
                _and_1 = _greaterThan;
              }
              if (_and_1) {
                _builder.newLineIfNotEmpty();
                {
                  EList<Translation> _toolTips_2 = matrxAxsWid.getToolTips();
                  for(final Translation tr : _toolTips_2) {
                    {
                      String _language = tr.getLanguage();
                      boolean _equals = _language.equals("en");
                      if (_equals) {
                        _builder.append("\t");
                        String _message = tr.getMessage();
                        _builder.append(_message, "\t");
                        _builder.newLineIfNotEmpty();
                      }
                    }
                  }
                }
                _builder.append("\t\t\t\t");
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<td>");
            {
              String _propertyValue_1 = matrxAxsWid.getPropertyValue("matrixAxis");
              boolean _equals_1 = _propertyValue_1.equals("x-axis");
              if (_equals_1) {
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("Horizontal (X)");
                _builder.newLine();
              } else {
                String _propertyValue_2 = matrxAxsWid.getPropertyValue("matrixAxis");
                boolean _equals_2 = _propertyValue_2.equals("y-axis");
                if (_equals_2) {
                  _builder.append("\t");
                  _builder.append("Vertical (Y)");
                  _builder.newLine();
                  _builder.append("\t");
                }
              }
            }
            _builder.append("</td>\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            String _generateEmptyTags = GenerationUtils.generateEmptyTags(9);
            _builder.append(_generateEmptyTags, "\t");
            _builder.newLineIfNotEmpty();
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
        {
          for(final Widget matrxAxsWid_1 : matrixAxisList) {
            String docText_1 = matrxAxsWid_1.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
            _builder.newLineIfNotEmpty();
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<td>");
            String _propertyValue_3 = matrxAxsWid_1.getPropertyValue("group-sorting-column-name");
            _builder.append(_propertyValue_3, "\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<td>");
            {
              String _propertyValue_4 = matrxAxsWid_1.getPropertyValue("matrixAxis");
              boolean _equals_3 = _propertyValue_4.equals("x-axis");
              if (_equals_3) {
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("X-Sorting");
                _builder.newLine();
              } else {
                String _propertyValue_5 = matrxAxsWid_1.getPropertyValue("matrixAxis");
                boolean _equals_4 = _propertyValue_5.equals("y-axis");
                if (_equals_4) {
                  _builder.append("\t");
                  _builder.append("Y-Sorting");
                  _builder.newLine();
                  _builder.append("\t");
                }
              }
            }
            _builder.append("</td>\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<td>");
            {
              boolean _and_2 = false;
              boolean _notEquals_2 = (!Objects.equal(docText_1, null));
              if (!_notEquals_2) {
                _and_2 = false;
              } else {
                boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(docText_1);
                boolean _not_2 = (!_isNullOrEmpty_1);
                _and_2 = _not_2;
              }
              if (_and_2) {
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("Description: ");
                String _replaceAll_1 = docText_1.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
                _builder.append(_replaceAll_1, "\t");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<td></td><td></td><td></td>");
            _builder.newLine();
            _builder.append("\t\t\t\t\t");
            _builder.append("<td>");
            {
              boolean _and_3 = false;
              EList<Translation> _toolTips_3 = matrxAxsWid_1.getToolTips();
              boolean _notEquals_3 = (!Objects.equal(_toolTips_3, null));
              if (!_notEquals_3) {
                _and_3 = false;
              } else {
                EList<Translation> _toolTips_4 = matrxAxsWid_1.getToolTips();
                int _size_1 = _toolTips_4.size();
                boolean _greaterThan_1 = (_size_1 > 0);
                _and_3 = _greaterThan_1;
              }
              if (_and_3) {
                _builder.newLineIfNotEmpty();
                {
                  EList<Translation> _toolTips_5 = matrxAxsWid_1.getToolTips();
                  for(final Translation tr_1 : _toolTips_5) {
                    {
                      String _language_1 = tr_1.getLanguage();
                      boolean _equals_5 = _language_1.equals("en");
                      if (_equals_5) {
                        String _message_1 = tr_1.getMessage();
                        _builder.append(_message_1, "");
                        _builder.newLineIfNotEmpty();
                      }
                    }
                  }
                }
                _builder.append("\t\t\t\t");
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<td></td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<td>");
            String _propertyValue_6 = matrxAxsWid_1.getPropertyValue("group-sorting-direction");
            _builder.append(_propertyValue_6, "\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            String _generateEmptyTags_1 = GenerationUtils.generateEmptyTags(8);
            _builder.append(_generateEmptyTags_1, "\t");
            _builder.newLineIfNotEmpty();
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
        List<Widget> matrixCellWidgets = CollectionLiterals.<Widget>newArrayList();
        _builder.newLineIfNotEmpty();
        Widget _widget_1 = model.getWidget();
        List<Widget> matrixCellList = this.fetchWidgets(_widget_1, WidgetTypeConstants.MATRIX, matrixCellWidgets);
        _builder.newLineIfNotEmpty();
        {
          boolean _isEmpty_1 = matrixCellList.isEmpty();
          boolean _not_3 = (!_isEmpty_1);
          if (_not_3) {
            {
              for(final Widget matrxCell : matrixCellList) {
                List<Widget> matrixContentCellWidgets = CollectionLiterals.<Widget>newArrayList();
                _builder.newLineIfNotEmpty();
                List<Widget> matrixContentCellList = this.fetchWidgets(matrxCell, WidgetTypeConstants.MATRIX_CONTENTCELLITEM, matrixContentCellWidgets);
                _builder.newLineIfNotEmpty();
                String docText_2 = matrxCell.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                _builder.newLineIfNotEmpty();
                {
                  for(final Widget matrxContentCell : matrixContentCellList) {
                    _builder.append("<tr>");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      String _propertyValue_7 = matrxContentCell.getPropertyValue("matrixCellItemType");
                      boolean _equalsIgnoreCase = _propertyValue_7.equalsIgnoreCase("domain");
                      if (_equalsIgnoreCase) {
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                        String _propertyValue_8 = matrxContentCell.getPropertyValue("domainAttribute");
                        _builder.append(_propertyValue_8, "\t");
                        _builder.newLineIfNotEmpty();
                      } else {
                        String _propertyValue_9 = matrxContentCell.getPropertyValue("matrixCellItemType");
                        boolean _equalsIgnoreCase_1 = _propertyValue_9.equalsIgnoreCase("Computed");
                        if (_equalsIgnoreCase_1) {
                          _builder.append("\t");
                          String _propertyValue_10 = matrxContentCell.getPropertyValue("column-name");
                          _builder.append(_propertyValue_10, "\t");
                          _builder.newLineIfNotEmpty();
                          _builder.append("\t");
                        }
                      }
                    }
                    _builder.append("</td>\t");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      String _propertyValue_11 = matrxContentCell.getPropertyValue("matrixCellItemType");
                      boolean _equalsIgnoreCase_2 = _propertyValue_11.equalsIgnoreCase("domain");
                      if (_equalsIgnoreCase_2) {
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                        _builder.append("Attribute");
                        _builder.newLine();
                      } else {
                        String _propertyValue_12 = matrxContentCell.getPropertyValue("matrixCellItemType");
                        boolean _equalsIgnoreCase_3 = _propertyValue_12.equalsIgnoreCase("Computed");
                        if (_equalsIgnoreCase_3) {
                          {
                            String _propertyValue_13 = matrxContentCell.getPropertyValue("column-computation");
                            boolean _equalsIgnoreCase_4 = _propertyValue_13.equalsIgnoreCase("make-amount");
                            if (_equalsIgnoreCase_4) {
                              _builder.append("\t");
                              _builder.append("Make Amount");
                              _builder.newLine();
                            } else {
                              String _propertyValue_14 = matrxContentCell.getPropertyValue("column-computation");
                              boolean _equalsIgnoreCase_5 = _propertyValue_14.equalsIgnoreCase("Relative Percent");
                              if (_equalsIgnoreCase_5) {
                                _builder.append("\t");
                                _builder.append("Relative Percent\t");
                                _builder.newLine();
                              } else {
                                String _propertyValue_15 = matrxContentCell.getPropertyValue("column-computation");
                                boolean _equalsIgnoreCase_6 = _propertyValue_15.equalsIgnoreCase("additional column");
                                if (_equalsIgnoreCase_6) {
                                  _builder.append("\t");
                                  _builder.append("Aditional Column\t");
                                  _builder.newLine();
                                }
                              }
                            }
                          }
                          _builder.append("\t\t\t\t");
                        }
                      }
                    }
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      boolean _and_4 = false;
                      boolean _notEquals_4 = (!Objects.equal(docText_2, null));
                      if (!_notEquals_4) {
                        _and_4 = false;
                      } else {
                        boolean _isNullOrEmpty_2 = StringExtensions.isNullOrEmpty(docText_2);
                        boolean _not_4 = (!_isNullOrEmpty_2);
                        _and_4 = _not_4;
                      }
                      if (_and_4) {
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                        _builder.append("Description: ");
                        String _replaceAll_2 = docText_2.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
                        _builder.append(_replaceAll_2, "\t");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                      }
                    }
                    _builder.append("</td>\t");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    Widget matrix = this.getParentWidgetByType(matrxContentCell, WidgetTypeConstants.MATRIX);
                    _builder.append("\t");
                    _builder.newLineIfNotEmpty();
                    {
                      boolean _and_5 = false;
                      boolean _notEquals_5 = (!Objects.equal(matrix, null));
                      if (!_notEquals_5) {
                        _and_5 = false;
                      } else {
                        String _typeName = matrix.getTypeName();
                        boolean _equals_6 = _typeName.equals(WidgetTypeConstants.MATRIX);
                        _and_5 = _equals_6;
                      }
                      if (_and_5) {
                        _builder.append("\t");
                        _builder.append("<td>");
                        {
                          String _propertyValue_16 = matrix.getPropertyValue("displayLastRow");
                          boolean _notEquals_6 = (!Objects.equal(_propertyValue_16, null));
                          if (_notEquals_6) {
                            _builder.append("Yes");
                          } else {
                            _builder.append("No");
                          }
                        }
                        _builder.append("</td>");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                        _builder.append("<td>");
                        {
                          String _propertyValue_17 = matrix.getPropertyValue("displayLastColumn");
                          boolean _notEquals_7 = (!Objects.equal(_propertyValue_17, null));
                          if (_notEquals_7) {
                            _builder.append("Yes");
                          } else {
                            _builder.append("No");
                          }
                        }
                        _builder.append("</td>");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                        _builder.append("<td>");
                        {
                          String _propertyValue_18 = matrix.getPropertyValue("displayLastCell");
                          boolean _notEquals_8 = (!Objects.equal(_propertyValue_18, null));
                          if (_notEquals_8) {
                            _builder.append("Yes");
                          } else {
                            _builder.append("No");
                          }
                        }
                        _builder.append("</td>");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t\t\t\t");
                      } else {
                        _builder.append("<td></td><td></td><td></td>");
                        _builder.newLineIfNotEmpty();
                      }
                    }
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      boolean _and_6 = false;
                      EList<Translation> _toolTips_6 = matrxContentCell.getToolTips();
                      boolean _notEquals_9 = (!Objects.equal(_toolTips_6, null));
                      if (!_notEquals_9) {
                        _and_6 = false;
                      } else {
                        EList<Translation> _toolTips_7 = matrxContentCell.getToolTips();
                        int _size_2 = _toolTips_7.size();
                        boolean _greaterThan_2 = (_size_2 > 0);
                        _and_6 = _greaterThan_2;
                      }
                      if (_and_6) {
                        _builder.newLineIfNotEmpty();
                        {
                          EList<Translation> _toolTips_8 = matrxContentCell.getToolTips();
                          for(final Translation tr_2 : _toolTips_8) {
                            {
                              String _language_2 = tr_2.getLanguage();
                              boolean _equals_7 = _language_2.equals("en");
                              if (_equals_7) {
                                _builder.append("\t");
                                String _message_2 = tr_2.getMessage();
                                _builder.append(_message_2, "\t");
                                _builder.newLineIfNotEmpty();
                              }
                            }
                          }
                        }
                        _builder.append("\t\t\t\t");
                      }
                    }
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("<td></td>\t\t\t\t");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("<td></td>");
                    _builder.newLine();
                    _builder.append("\t");
                    Widget _rootWidget = matrxContentCell.getRootWidget();
                    Widget matrixOld = this.getWidgetByType(_rootWidget, WidgetTypeConstants.MATRIX_AXIS);
                    _builder.append("\t");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      boolean _and_7 = false;
                      boolean _notEquals_10 = (!Objects.equal(matrixOld, null));
                      if (!_notEquals_10) {
                        _and_7 = false;
                      } else {
                        String _propertyValue_19 = matrixOld.getPropertyValue("matrixAxis");
                        boolean _equals_8 = _propertyValue_19.equals("y-axis");
                        _and_7 = _equals_8;
                      }
                      if (_and_7) {
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                        String _propertyValue_20 = matrixOld.getPropertyValue("maxColumns");
                        _builder.append(_propertyValue_20, "\t");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                      }
                    }
                    _builder.append("</td>\t");
                    _builder.newLineIfNotEmpty();
                    {
                      String _propertyValue_21 = matrxContentCell.getPropertyValue("matrixCellItemType");
                      boolean _equalsIgnoreCase_7 = _propertyValue_21.equalsIgnoreCase("Computed");
                      if (_equalsIgnoreCase_7) {
                        {
                          String _propertyValue_22 = matrxContentCell.getPropertyValue("column-computation-parameters");
                          boolean _contains = _propertyValue_22.contains(",");
                          if (_contains) {
                            _builder.append("\t");
                            String _propertyValue_23 = matrxContentCell.getPropertyValue("column-computation-parameters");
                            String[] arry = _propertyValue_23.split(",");
                            _builder.newLineIfNotEmpty();
                            {
                              final String[] _converted_arry = (String[])arry;
                              boolean _isNullOrEmpty_3 = IterableExtensions.isNullOrEmpty(((Iterable<?>)Conversions.doWrapArray(_converted_arry)));
                              boolean _not_5 = (!_isNullOrEmpty_3);
                              if (_not_5) {
                                _builder.append("<td>");
                                {
                                  final String[] _converted_arry_1 = (String[])arry;
                                  int _size_3 = ((List<String>)Conversions.doWrapArray(_converted_arry_1)).size();
                                  boolean _greaterThan_3 = (_size_3 > 0);
                                  if (_greaterThan_3) {
                                    String _get = arry[0];
                                    _builder.append(_get, "");
                                  }
                                }
                                _builder.append("</td>");
                                _builder.newLineIfNotEmpty();
                                _builder.append("<td>");
                                {
                                  final String[] _converted_arry_2 = (String[])arry;
                                  int _size_4 = ((List<String>)Conversions.doWrapArray(_converted_arry_2)).size();
                                  boolean _greaterThan_4 = (_size_4 > 1);
                                  if (_greaterThan_4) {
                                    String _get_1 = arry[1];
                                    _builder.append(_get_1, "");
                                  }
                                }
                                _builder.append("</td>");
                                _builder.newLineIfNotEmpty();
                              } else {
                                _builder.append("\t");
                                _builder.append("\t");
                                _builder.append("<td></td><td></td>");
                                _builder.newLine();
                              }
                            }
                          } else {
                            _builder.append("\t");
                            _builder.append("<td>");
                            String _propertyValue_24 = matrxContentCell.getPropertyValue("column-computation-parameters");
                            _builder.append(_propertyValue_24, "\t");
                            _builder.append("</td>");
                            _builder.newLineIfNotEmpty();
                            _builder.append("\t");
                            _builder.append("<td></td>\t");
                            _builder.newLine();
                          }
                        }
                      } else {
                        String _propertyValue_25 = matrxContentCell.getPropertyValue("matrixCellItemType");
                        boolean _equalsIgnoreCase_8 = _propertyValue_25.equalsIgnoreCase("Domain");
                        if (_equalsIgnoreCase_8) {
                          _builder.append("\t");
                          _builder.append("<td></td><td></td>");
                          _builder.newLine();
                        }
                      }
                    }
                    _builder.append("\t");
                    _builder.append("<td>");
                    String _propertyValue_26 = matrxContentCell.getPropertyValue("cssClass");
                    _builder.append(_propertyValue_26, "\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("<td>");
                    String _propertyValue_27 = matrxContentCell.getPropertyValue("format");
                    _builder.append(_propertyValue_27, "\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      String _propertyValue_28 = matrxContentCell.getPropertyValue("enabledIsBasedOn");
                      boolean _notEquals_11 = (!Objects.equal(_propertyValue_28, null));
                      if (_notEquals_11) {
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                        _builder.append("Based on:");
                        String _propertyValue_29 = matrxContentCell.getPropertyValue("enabledIsBasedOn");
                        _builder.append(_propertyValue_29, "\t");
                        _builder.newLineIfNotEmpty();
                      } else {
                        _builder.append("\t");
                        String _propertyValue_30 = matrxContentCell.getPropertyValue("enabled");
                        _builder.append(_propertyValue_30, "\t");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                      }
                    }
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("<td>");
                    String _propertyValue_31 = matrxContentCell.getPropertyValue("horizontalAlignment");
                    _builder.append(_propertyValue_31, "\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      boolean _and_8 = false;
                      boolean _and_9 = false;
                      boolean _notEquals_12 = (!Objects.equal(matrixOld, null));
                      if (!_notEquals_12) {
                        _and_9 = false;
                      } else {
                        String _typeName_1 = matrixOld.getTypeName();
                        boolean _equals_9 = _typeName_1.equals(WidgetTypeConstants.MATRIX);
                        _and_9 = _equals_9;
                      }
                      if (!_and_9) {
                        _and_8 = false;
                      } else {
                        boolean _or = false;
                        Widget _parent = matrixOld.getParent();
                        WidgetType _type = _parent.getType();
                        String _name = _type.getName();
                        boolean _equals_10 = _name.equals(WidgetTypeConstants.CONDITIONAL_BODY);
                        if (_equals_10) {
                          _or = true;
                        } else {
                          Widget _parent_1 = matrixOld.getParent();
                          WidgetType _type_1 = _parent_1.getType();
                          String _name_1 = _type_1.getName();
                          boolean _equals_11 = _name_1.equals(WidgetTypeConstants.TAB);
                          _or = _equals_11;
                        }
                        _and_8 = _or;
                      }
                      if (_and_8) {
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                        Widget _parent_2 = matrixOld.getParent();
                        String _propertyValue_32 = _parent_2.getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
                        _builder.append(_propertyValue_32, "\t");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                      }
                    }
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("</tr>");
                    _builder.newLine();
                  }
                }
                _builder.append("\t");
                _builder.newLine();
              }
            }
          }
        }
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence tableGrouping(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> tableWidgets = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    List<Widget> tableGrpList = this.fetchWidgets(_widget, "TableGroup", tableWidgets);
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = tableGrpList.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        _builder.append("Table grouping: ");
        _builder.newLine();
        _builder.append("<table border=\"1\">");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Level</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Grouping</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Sorting</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Hierarchy</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Collapsed</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Used In Dynamic Column</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Raw Data</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("</tr>");
        _builder.newLine();
        {
          for(final Widget tabGrpWid : tableGrpList) {
            _builder.append("\t\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<td>");
            int _indexOf = tableGrpList.indexOf(tabGrpWid);
            int _plus = (_indexOf + 1);
            _builder.append(_plus, "\t\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<td>");
            String _propertyValue = tabGrpWid.getPropertyValue("group-column-name");
            _builder.append(_propertyValue, "\t\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<td>");
            String _propertyValue_1 = tabGrpWid.getPropertyValue("group-sorting-column-name");
            _builder.append(_propertyValue_1, "\t\t\t");
            _builder.append(" (");
            String _propertyValue_2 = tabGrpWid.getPropertyValue("group-sorting-direction");
            String _capitalize = WordUtils.capitalize(_propertyValue_2);
            _builder.append(_capitalize, "\t\t\t");
            _builder.append(")</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<td>");
            {
              String _propertyValue_3 = tabGrpWid.getPropertyValue("hierarchy");
              Boolean _valueOf = Boolean.valueOf(_propertyValue_3);
              if ((_valueOf).booleanValue()) {
                _builder.append("Yes");
              } else {
                _builder.append("No");
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<td>");
            {
              String _propertyValue_4 = tabGrpWid.getPropertyValue("collapsed");
              Boolean _valueOf_1 = Boolean.valueOf(_propertyValue_4);
              if ((_valueOf_1).booleanValue()) {
                _builder.append("Yes");
              } else {
                _builder.append("No");
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<td>");
            {
              String _propertyValue_5 = tabGrpWid.getPropertyValue("group-dynamic-column");
              Boolean _valueOf_2 = Boolean.valueOf(_propertyValue_5);
              if ((_valueOf_2).booleanValue()) {
                _builder.append("Yes");
              } else {
                _builder.append("No");
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            Widget tableCol = this.getParentWidgetByType(tabGrpWid, "TableColumn");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<td>");
            {
              boolean _and = false;
              boolean _notEquals = (!Objects.equal(tableCol, null));
              if (!_notEquals) {
                _and = false;
              } else {
                boolean _isMoreTableGroupAvailable = this.isMoreTableGroupAvailable(tableCol);
                _and = _isMoreTableGroupAvailable;
              }
              if (_and) {
                _builder.append("No");
              } else {
                String _propertyValue_6 = tabGrpWid.getPropertyValue("aggregateData");
                boolean _equalsIgnoreCase = _propertyValue_6.equalsIgnoreCase("Aggregate");
                if (_equalsIgnoreCase) {
                  _builder.append("No");
                } else {
                  String _propertyValue_7 = tabGrpWid.getPropertyValue("aggregateData");
                  boolean _equalsIgnoreCase_1 = _propertyValue_7.equalsIgnoreCase("Raw");
                  if (_equalsIgnoreCase_1) {
                    _builder.append("Yes");
                  }
                }
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
        _builder.append("</table>\t\t");
        _builder.newLine();
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence tableSorting(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> tableWidgets = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    List<Widget> tableSortList = this.fetchWidgets(_widget, "TableSort", tableWidgets);
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = tableSortList.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        _builder.append("Table sorting applying on rows (leafs for table tree):");
        _builder.newLine();
        _builder.append("<table border=\"1\">");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Rank</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Attribute</th>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<th bgcolor=\"#6495ED\">Direction</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("</tr>");
        _builder.newLine();
        {
          for(final Widget tabSortWid : tableSortList) {
            _builder.append("\t\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<td>");
            int _indexOf = tableSortList.indexOf(tabSortWid);
            int _plus = (_indexOf + 1);
            _builder.append(_plus, "\t\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<td>");
            String _propertyValue = tabSortWid.getPropertyValue("sort-column-name");
            _builder.append(_propertyValue, "\t\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<td>");
            String _propertyValue_1 = tabSortWid.getPropertyValue("sort-direction");
            String _capitalize = WordUtils.capitalize(_propertyValue_1);
            _builder.append(_capitalize, "\t\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
        _builder.append("</table>\t\t");
        _builder.newLine();
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence tableFilter(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> tableWidgets = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    List<Widget> tableList = this.fetchWidgets(_widget, WidgetTypeConstants.TABLE_TREE, tableWidgets);
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = tableList.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        CharSequence _commonTabMatFilter = this.commonTabMatFilter(tableList);
        _builder.append(_commonTabMatFilter, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence matrixFilter(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> matrixWidgets = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    List<Widget> matrxList = this.fetchWidgets(_widget, WidgetTypeConstants.MATRIX, matrixWidgets);
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = matrxList.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        CharSequence _commonTabMatFilter = this.commonTabMatFilter(matrxList);
        _builder.append(_commonTabMatFilter, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence commonTabMatFilter(final List<Widget> matrxList) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Widget tabWid : matrxList) {
        List<Widget> tableKeepFilterWidgets = CollectionLiterals.<Widget>newArrayList();
        _builder.newLineIfNotEmpty();
        List<Widget> tableFilterList = this.fetchWidgets(tabWid, "TableKeepFilter", tableKeepFilterWidgets);
        _builder.newLineIfNotEmpty();
        {
          boolean _isEmpty = tableFilterList.isEmpty();
          boolean _not = (!_isEmpty);
          if (_not) {
            CharSequence _htmlOpening = this.htmlOpening();
            _builder.append(_htmlOpening, "");
            _builder.newLineIfNotEmpty();
            {
              int _size = tableFilterList.size();
              boolean _greaterThan = (_size > 1);
              if (_greaterThan) {
                _builder.append("List of filters, linked with the logical operator: ");
                Widget _get = tableFilterList.get(0);
                Widget _parentWidgetByType = this.getParentWidgetByType(_get, WidgetTypeConstants.TABLE_TREE);
                String _propertyValue = _parentWidgetByType.getPropertyValue("table-keep-filter-logic");
                _builder.append(_propertyValue, "");
                _builder.append("<br>");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.append("<table border=\"1\">");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t\t\t");
            _builder.append("<th bgcolor=\"#6495ED\">Filter Column</th>");
            _builder.newLine();
            _builder.append("\t\t\t");
            _builder.append("<th bgcolor=\"#6495ED\">Operator</th>");
            _builder.newLine();
            _builder.append("\t\t\t");
            _builder.append("<th bgcolor=\"#6495ED\">Value</th>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("</tr>");
            _builder.newLine();
            {
              for(final Widget tabFilterWid : tableFilterList) {
                _builder.append("\t\t");
                _builder.append("<tr>");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("<td>");
                String _propertyValue_1 = tabFilterWid.getPropertyValue("keep-filter-column-name");
                _builder.append(_propertyValue_1, "\t\t\t");
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("<td>");
                String _propertyValue_2 = tabFilterWid.getPropertyValue("keep-filter-operator");
                _builder.append(_propertyValue_2, "\t\t\t");
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("<td>");
                String _propertyValue_3 = tabFilterWid.getPropertyValue("keep-filter-operand");
                _builder.append(_propertyValue_3, "\t\t\t");
                _builder.append("</td>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("</tr>");
                _builder.newLine();
              }
            }
            _builder.append("</table><br>\t\t");
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
  
  public CharSequence tableExtra(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> tableWidgets = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    List<Widget> tableList = this.fetchWidgets(_widget, WidgetTypeConstants.TABLE_TREE, tableWidgets);
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = tableList.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        {
          for(final Widget tabWid : tableList) {
            List<Widget> tableExtraWidgets = CollectionLiterals.<Widget>newArrayList();
            _builder.newLineIfNotEmpty();
            List<Widget> tableExtraList = this.fetchWidgets(tabWid, "TableExtra", tableExtraWidgets);
            _builder.newLineIfNotEmpty();
            List<Widget> tableAggregateWidgets = CollectionLiterals.<Widget>newArrayList();
            _builder.newLineIfNotEmpty();
            List<Widget> tableAggregateList = this.fetchWidgets(tabWid, "TableAggregate", tableAggregateWidgets);
            _builder.newLineIfNotEmpty();
            {
              boolean _isEmpty_1 = tableExtraList.isEmpty();
              boolean _not_1 = (!_isEmpty_1);
              if (_not_1) {
                CharSequence _htmlOpening = this.htmlOpening();
                _builder.append(_htmlOpening, "");
                _builder.newLineIfNotEmpty();
                {
                  int _size = tableExtraList.size();
                  boolean _greaterThan = (_size > 1);
                  if (_greaterThan) {
                    _builder.append("\t\t\t");
                    _builder.append("List of extra columns: <br>");
                    _builder.newLine();
                  }
                }
                _builder.append("<table border=\"1\">");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("<tr>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Attribute</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Aggregate</th>");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("</tr>");
                _builder.newLine();
                {
                  for(final Widget tabExtraWid : tableExtraList) {
                    _builder.append("\t\t\t");
                    _builder.append("<tr>");
                    _builder.newLine();
                    _builder.append("\t\t\t");
                    String domAtrName = tabExtraWid.getPropertyValue("domainAttribute");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t");
                    _builder.append("<td>");
                    _builder.append(domAtrName, "\t\t\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t");
                    _builder.append("<td>");
                    String _aggregateFunction = this.getAggregateFunction(domAtrName, tableAggregateList);
                    String _capitalize = WordUtils.capitalize(_aggregateFunction);
                    _builder.append(_capitalize, "\t\t\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t");
                    _builder.append("</tr>");
                    _builder.newLine();
                  }
                }
                _builder.append("</table><br>\t\t");
                _builder.newLine();
                CharSequence _htmlClosing = this.htmlClosing();
                _builder.append(_htmlClosing, "");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence matrixExtra(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> matrixWidgets = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    List<Widget> matrixList = this.fetchWidgets(_widget, WidgetTypeConstants.MATRIX, matrixWidgets);
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = matrixList.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        {
          for(final Widget tabWid : matrixList) {
            List<Widget> matrixExtraWidgets = CollectionLiterals.<Widget>newArrayList();
            _builder.newLineIfNotEmpty();
            List<Widget> matrixExtraList = this.fetchWidgets(tabWid, "MatrixExtra", matrixExtraWidgets);
            _builder.newLineIfNotEmpty();
            {
              boolean _isEmpty_1 = matrixExtraList.isEmpty();
              boolean _not_1 = (!_isEmpty_1);
              if (_not_1) {
                CharSequence _htmlOpening = this.htmlOpening();
                _builder.append(_htmlOpening, "");
                _builder.newLineIfNotEmpty();
                {
                  int _size = matrixExtraList.size();
                  boolean _greaterThan = (_size > 1);
                  if (_greaterThan) {
                    _builder.append("\t\t\t");
                    _builder.append("List of extra columns: <br>");
                    _builder.newLine();
                  }
                }
                _builder.append("<table border=\"1\">");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("<tr>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Attribute</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Aggregate</th>");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("</tr>");
                _builder.newLine();
                {
                  for(final Widget tabExtraWid : matrixExtraList) {
                    _builder.append("\t\t\t");
                    _builder.append("<tr>");
                    _builder.newLine();
                    _builder.append("\t\t\t");
                    String domAtrName = tabExtraWid.getPropertyValue("domainAttribute");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t");
                    _builder.append("<td>");
                    _builder.append(domAtrName, "\t\t\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t");
                    _builder.append("<td>");
                    String _propertyValue = tabExtraWid.getPropertyValue("aggregationType");
                    String _capitalize = WordUtils.capitalize(_propertyValue);
                    _builder.append(_capitalize, "\t\t\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t");
                    _builder.append("</tr>");
                    _builder.newLine();
                  }
                }
                _builder.append("</table><br>\t\t");
                _builder.newLine();
                CharSequence _htmlClosing = this.htmlClosing();
                _builder.append(_htmlClosing, "");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence tableColumns(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> tableWidgets = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    List<Widget> tableList = this.fetchWidgets(_widget, WidgetTypeConstants.TABLE_TREE, tableWidgets);
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = tableList.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        {
          for(final Widget tabWid : tableList) {
            List<Widget> tableColumnWidgets = CollectionLiterals.<Widget>newArrayList();
            _builder.newLineIfNotEmpty();
            List<Widget> tableColumnList = this.fetchWidgets(tabWid, "TableColumn", tableColumnWidgets);
            _builder.newLineIfNotEmpty();
            {
              boolean _isEmpty_1 = tableColumnList.isEmpty();
              boolean _not_1 = (!_isEmpty_1);
              if (_not_1) {
                _builder.append("\t");
                CharSequence _htmlOpening = this.htmlOpening();
                _builder.append(_htmlOpening, "\t");
                _builder.newLineIfNotEmpty();
                {
                  int _size = tableColumnList.size();
                  boolean _greaterThan = (_size > 1);
                  if (_greaterThan) {
                    _builder.append("\t\t\t");
                    _builder.append("List of columns set on the table: <br>");
                    _builder.newLine();
                  }
                }
                _builder.append("<table border=\"1\">");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("<tr>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Position</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Column name</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Label</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Description</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Column Type</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Format</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Is Visible</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Sortable</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Part of Filter</th>");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("</tr>");
                _builder.newLine();
                {
                  for(final Widget tabColumnWid : tableColumnList) {
                    _builder.append("\t\t\t");
                    _builder.append("<tr>");
                    _builder.newLine();
                    _builder.append("\t\t\t");
                    _builder.append("<td>");
                    int _indexOf = tableColumnList.indexOf(tabColumnWid);
                    int _plus = (_indexOf + 1);
                    _builder.append(_plus, "\t\t\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t");
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      String _propertyValue = tabColumnWid.getPropertyValue("column-type");
                      boolean _equalsIgnoreCase = _propertyValue.equalsIgnoreCase("domain");
                      if (_equalsIgnoreCase) {
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t\t\t");
                        _builder.append("\t");
                        String _propertyValue_1 = tabColumnWid.getPropertyValue("domainAttribute");
                        _builder.append(_propertyValue_1, "\t\t\t\t");
                        _builder.newLineIfNotEmpty();
                      } else {
                        boolean _or = false;
                        String _propertyValue_2 = tabColumnWid.getPropertyValue("column-type");
                        boolean _equalsIgnoreCase_1 = _propertyValue_2.equalsIgnoreCase("Placeholder");
                        if (_equalsIgnoreCase_1) {
                          _or = true;
                        } else {
                          String _propertyValue_3 = tabColumnWid.getPropertyValue("column-type");
                          boolean _equalsIgnoreCase_2 = _propertyValue_3.equalsIgnoreCase("Computed");
                          _or = _equalsIgnoreCase_2;
                        }
                        if (_or) {
                          _builder.append("\t\t\t");
                          _builder.append("\t");
                          String _propertyValue_4 = tabColumnWid.getPropertyValue("column-name");
                          _builder.append(_propertyValue_4, "\t\t\t\t");
                          _builder.newLineIfNotEmpty();
                        } else {
                          String _propertyValue_5 = tabColumnWid.getPropertyValue("column-type");
                          boolean _equalsIgnoreCase_3 = _propertyValue_5.equalsIgnoreCase("dynamic");
                          if (_equalsIgnoreCase_3) {
                            _builder.append("\t\t\t");
                            _builder.append("\t");
                            Widget par = this.getParentWidgetByType(tabColumnWid, WidgetTypeConstants.TABLE_TREE);
                            _builder.newLineIfNotEmpty();
                            _builder.append("\t\t\t");
                            _builder.append("\t");
                            _builder.append("Based on group ");
                            {
                              boolean _and = false;
                              boolean _notEquals = (!Objects.equal(par, null));
                              if (!_notEquals) {
                                _and = false;
                              } else {
                                Widget _dynamicGroupWidget = this.getDynamicGroupWidget(par);
                                boolean _notEquals_1 = (!Objects.equal(_dynamicGroupWidget, null));
                                _and = _notEquals_1;
                              }
                              if (_and) {
                                _builder.append("[");
                                Widget _dynamicGroupWidget_1 = this.getDynamicGroupWidget(par);
                                String _propertyValue_6 = _dynamicGroupWidget_1.getPropertyValue("group-column-name");
                                _builder.append(_propertyValue_6, "\t\t\t\t");
                                _builder.append("]");
                              }
                            }
                            _builder.append("\t");
                            _builder.newLineIfNotEmpty();
                          } else {
                            String _propertyValue_7 = tabColumnWid.getPropertyValue("column-display-grouping");
                            Boolean _valueOf = Boolean.valueOf(_propertyValue_7);
                            if ((_valueOf).booleanValue()) {
                              _builder.append("\t\t\t");
                              _builder.append("\t");
                              _builder.append("Display grouping ");
                              _builder.newLine();
                              _builder.append("\t\t\t");
                              _builder.append("\t");
                            }
                          }
                        }
                      }
                    }
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t");
                    _builder.append("<td>");
                    String _fetchTranslationValue = this.fetchTranslationValue(tabColumnWid, project);
                    _builder.append(_fetchTranslationValue, "\t\t\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t");
                    _builder.append("\t");
                    String docText = tabColumnWid.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t");
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      boolean _and_1 = false;
                      boolean _notEquals_2 = (!Objects.equal(docText, null));
                      if (!_notEquals_2) {
                        _and_1 = false;
                      } else {
                        boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(docText);
                        boolean _not_2 = (!_isNullOrEmpty);
                        _and_1 = _not_2;
                      }
                      if (_and_1) {
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t\t\t");
                        _builder.append("\t");
                        _builder.append("Description: ");
                        String _replaceAll = docText.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
                        _builder.append(_replaceAll, "\t\t\t\t");
                        _builder.append(" ");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t\t\t");
                        _builder.append("\t");
                      }
                    }
                    _builder.append("</td> ");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t");
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      boolean _and_2 = false;
                      String _propertyValue_8 = tabColumnWid.getPropertyValue("column-type");
                      boolean _equalsIgnoreCase_4 = _propertyValue_8.equalsIgnoreCase("domain");
                      if (!_equalsIgnoreCase_4) {
                        _and_2 = false;
                      } else {
                        Property _findProperty = tabColumnWid.findProperty(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
                        boolean _notEquals_3 = (!Objects.equal(_findProperty, null));
                        _and_2 = _notEquals_3;
                      }
                      if (_and_2) {
                        _builder.append(" Attribute");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t\t\t");
                        _builder.append("\t");
                      } else {
                        String _propertyValue_9 = tabColumnWid.getPropertyValue("column-type");
                        boolean _equalsIgnoreCase_5 = _propertyValue_9.equalsIgnoreCase("Placeholder");
                        if (_equalsIgnoreCase_5) {
                          _builder.append(" Placeholder");
                          _builder.newLineIfNotEmpty();
                        } else {
                          boolean _and_3 = false;
                          String _propertyValue_10 = tabColumnWid.getPropertyValue("column-type");
                          boolean _equalsIgnoreCase_6 = _propertyValue_10.equalsIgnoreCase("Computed");
                          if (!_equalsIgnoreCase_6) {
                            _and_3 = false;
                          } else {
                            String _propertyValue_11 = tabColumnWid.getPropertyValue("column-computation");
                            boolean _equalsIgnoreCase_7 = _propertyValue_11.equalsIgnoreCase("make-amount");
                            _and_3 = _equalsIgnoreCase_7;
                          }
                          if (_and_3) {
                            _builder.append("\t\t\t");
                            _builder.append("\t");
                            String _propertyValue_12 = tabColumnWid.getPropertyValue("column-computation-parameters");
                            String[] strBrk = _propertyValue_12.split(",");
                            _builder.newLineIfNotEmpty();
                            _builder.append("\t\t\t");
                            _builder.append("\t");
                            _builder.append("Make Amount ");
                            {
                              final String[] _converted_strBrk = (String[])strBrk;
                              int _size_1 = ((List<String>)Conversions.doWrapArray(_converted_strBrk)).size();
                              boolean _greaterThan_1 = (_size_1 > 1);
                              if (_greaterThan_1) {
                                _builder.append("(");
                                String _get = strBrk[1];
                                String _string = _get.toString();
                                _builder.append(_string, "\t\t\t\t");
                                _builder.append(")");
                              }
                            }
                            _builder.newLineIfNotEmpty();
                            _builder.append("\t\t\t");
                            _builder.append("\t");
                          } else {
                            boolean _and_4 = false;
                            String _propertyValue_13 = tabColumnWid.getPropertyValue("column-type");
                            boolean _equalsIgnoreCase_8 = _propertyValue_13.equalsIgnoreCase("Computed");
                            if (!_equalsIgnoreCase_8) {
                              _and_4 = false;
                            } else {
                              String _propertyValue_14 = tabColumnWid.getPropertyValue("column-computation");
                              boolean _equalsIgnoreCase_9 = _propertyValue_14.equalsIgnoreCase("Same");
                              _and_4 = _equalsIgnoreCase_9;
                            }
                            if (_and_4) {
                              _builder.append(" Same\t");
                              _builder.newLineIfNotEmpty();
                            } else {
                              String _propertyValue_15 = tabColumnWid.getPropertyValue("column-type");
                              boolean _equalsIgnoreCase_10 = _propertyValue_15.equalsIgnoreCase("dynamic");
                              if (_equalsIgnoreCase_10) {
                                _builder.append("\t\t\t");
                                _builder.append("\t");
                                Widget par_1 = this.getParentWidgetByType(tabColumnWid, WidgetTypeConstants.TABLE_TREE);
                                _builder.newLineIfNotEmpty();
                                _builder.append("\t\t\t");
                                _builder.append("\t");
                                _builder.append("Dynamic ");
                                {
                                  boolean _and_5 = false;
                                  boolean _notEquals_4 = (!Objects.equal(par_1, null));
                                  if (!_notEquals_4) {
                                    _and_5 = false;
                                  } else {
                                    Widget _dynamicGroupWidget_2 = this.getDynamicGroupWidget(par_1);
                                    boolean _notEquals_5 = (!Objects.equal(_dynamicGroupWidget_2, null));
                                    _and_5 = _notEquals_5;
                                  }
                                  if (_and_5) {
                                    _builder.append(" (");
                                    Widget _dynamicGroupWidget_3 = this.getDynamicGroupWidget(par_1);
                                    String _propertyValue_16 = _dynamicGroupWidget_3.getPropertyValue("group-column-name");
                                    _builder.append(_propertyValue_16, "\t\t\t\t");
                                    _builder.append(")");
                                  }
                                }
                                _builder.append("\t\t\t\t");
                                _builder.newLineIfNotEmpty();
                                _builder.append("\t\t\t");
                                _builder.append("\t");
                              }
                            }
                          }
                        }
                      }
                    }
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t");
                    _builder.append("\t");
                    List<Widget> tabColItemWids = CollectionLiterals.<Widget>newArrayList();
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t");
                    _builder.append("\t");
                    List<Widget> tabColItemWidList = this.fetchWidgets(tabColumnWid, "TableColumnItem", tabColItemWids);
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t");
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      int _size_2 = tabColItemWidList.size();
                      boolean _greaterThan_2 = (_size_2 > 0);
                      if (_greaterThan_2) {
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t\t\t");
                        _builder.append("\t");
                        {
                          Widget _get_1 = tabColItemWidList.get(0);
                          String _propertyValue_17 = _get_1.getPropertyValue("format");
                          boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(_propertyValue_17);
                          boolean _not_3 = (!_isNullOrEmpty_1);
                          if (_not_3) {
                            Widget _get_2 = tabColItemWidList.get(0);
                            String _propertyValue_18 = _get_2.getPropertyValue("format");
                            _builder.append(_propertyValue_18, "\t\t\t\t");
                          } else {
                          }
                        }
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t\t\t");
                        _builder.append("\t");
                      }
                    }
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t");
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      String _propertyValue_19 = tabColumnWid.getPropertyValue("column-locked");
                      Boolean _valueOf_1 = Boolean.valueOf(_propertyValue_19);
                      if ((_valueOf_1).booleanValue()) {
                        _builder.append(" Locked\t");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t\t\t");
                        _builder.append("\t");
                      } else {
                        String _propertyValue_20 = tabColumnWid.getPropertyValue("column-visibility");
                        boolean _equalsIgnoreCase_11 = _propertyValue_20.equalsIgnoreCase("Visible");
                        if (_equalsIgnoreCase_11) {
                          _builder.append(" By default");
                          _builder.newLineIfNotEmpty();
                          _builder.append("\t\t\t");
                          _builder.append("\t");
                        } else {
                          String _propertyValue_21 = tabColumnWid.getPropertyValue("column-visibility");
                          boolean _equalsIgnoreCase_12 = _propertyValue_21.equalsIgnoreCase("not-visible");
                          if (_equalsIgnoreCase_12) {
                            _builder.append(" Not by default");
                            _builder.newLineIfNotEmpty();
                            _builder.append("\t\t\t");
                            _builder.append("\t");
                          } else {
                            String _propertyValue_22 = tabColumnWid.getPropertyValue("column-visibility");
                            boolean _equalsIgnoreCase_13 = _propertyValue_22.equalsIgnoreCase("never-visible");
                            if (_equalsIgnoreCase_13) {
                              _builder.append(" Never\t");
                              _builder.newLineIfNotEmpty();
                              _builder.append("\t\t\t");
                              _builder.append("\t");
                            }
                          }
                        }
                      }
                    }
                    _builder.append("</td>\t");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t");
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      String _propertyValue_23 = tabColumnWid.getPropertyValue("column-sortable");
                      Boolean _valueOf_2 = Boolean.valueOf(_propertyValue_23);
                      if ((_valueOf_2).booleanValue()) {
                        _builder.append(" Yes");
                      } else {
                        _builder.append(" No");
                      }
                    }
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t");
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      boolean _or_1 = false;
                      String _propertyValue_24 = tabColumnWid.getPropertyValue("column-type");
                      boolean _equalsIgnoreCase_14 = _propertyValue_24.equalsIgnoreCase("Placeholder");
                      if (_equalsIgnoreCase_14) {
                        _or_1 = true;
                      } else {
                        String _propertyValue_25 = tabColumnWid.getPropertyValue("column-type");
                        boolean _equalsIgnoreCase_15 = _propertyValue_25.equalsIgnoreCase("Computed");
                        _or_1 = _equalsIgnoreCase_15;
                      }
                      if (_or_1) {
                        _builder.append("No");
                      } else {
                        String _propertyValue_26 = tabColumnWid.getPropertyValue("column-is-part-of-filter");
                        Boolean _valueOf_3 = Boolean.valueOf(_propertyValue_26);
                        if ((_valueOf_3).booleanValue()) {
                          _builder.append("Yes ");
                        } else {
                          _builder.append(" No ");
                        }
                      }
                    }
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t\t");
                    _builder.append("</tr>");
                    _builder.newLine();
                  }
                }
                _builder.append("</table><br>\t\t");
                _builder.newLine();
                CharSequence _htmlClosing = this.htmlClosing();
                _builder.append(_htmlClosing, "");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence tableXColumns(final Model model, final IOfsProject project) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> tableWidgets = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    List<Widget> tableList = this.fetchWidgets(_widget, WidgetTypeConstants.TABLE_TREE, tableWidgets);
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = tableList.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        {
          for(final Widget tabWid : tableList) {
            List<Widget> tableColumnWidgets = CollectionLiterals.<Widget>newArrayList();
            _builder.newLineIfNotEmpty();
            List<Widget> tableColumnList = this.fetchWidgets(tabWid, "TableColumn", tableColumnWidgets);
            _builder.newLineIfNotEmpty();
            {
              boolean _isEmpty_1 = tableColumnList.isEmpty();
              boolean _not_1 = (!_isEmpty_1);
              if (_not_1) {
                _builder.append("\t");
                CharSequence _htmlOpening = this.htmlOpening();
                _builder.append(_htmlOpening, "\t");
                _builder.newLineIfNotEmpty();
                {
                  int _size = tableColumnList.size();
                  boolean _greaterThan = (_size > 1);
                  if (_greaterThan) {
                    _builder.append("\t\t");
                    _builder.append("List of columns set on the table: <br>");
                    _builder.newLine();
                  }
                }
                _builder.append("<table border=\"1\">");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("<tr>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Translation</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Column name</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Description</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Type</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Is Visible</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Tooltip</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Sortable</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Filter</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Display Grouping</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Grouping</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Part of Filter</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Filter Logical Operator</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Max Char</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Wrapped</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Width</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Item Name</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Aggregation</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Tooltip</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">New Line</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Width</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">CSS Class</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Display Format</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Wrapped</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Enable</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Condition</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Condition Description</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Condition Java code</th>");
                _builder.newLine();
                _builder.append("\t\t\t");
                _builder.append("<th bgcolor=\"#6495ED\">Horizontal Alignment</th>");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("</tr>");
                _builder.newLine();
                {
                  for(final Widget tabColumnWid : tableColumnList) {
                    _builder.append("\t\t");
                    _builder.append("<tr>");
                    _builder.newLine();
                    _builder.append("\t\t");
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      String _title = ModuleUtils.getTitle(project, tabColumnWid);
                      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(_title);
                      boolean _not_2 = (!_isNullOrEmpty);
                      if (_not_2) {
                        String _title_1 = ModuleUtils.getTitle(project, tabColumnWid);
                        _builder.append(_title_1, "\t\t\t");
                      } else {
                        _builder.append("None", "\t\t\t");
                      }
                    }
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    {
                      String _propertyValue = tabColumnWid.getPropertyValue("column-type");
                      boolean _equalsIgnoreCase = _propertyValue.equalsIgnoreCase("domain");
                      if (_equalsIgnoreCase) {
                        _builder.append("\t\t");
                        _builder.append("\t");
                        _builder.append("<td>");
                        String _propertyValue_1 = tabColumnWid.getPropertyValue("domainAttribute");
                        _builder.append(_propertyValue_1, "\t\t\t");
                        _builder.append("</td>");
                        _builder.newLineIfNotEmpty();
                      } else {
                        boolean _or = false;
                        String _propertyValue_2 = tabColumnWid.getPropertyValue("column-type");
                        boolean _equalsIgnoreCase_1 = _propertyValue_2.equalsIgnoreCase("Placeholder");
                        if (_equalsIgnoreCase_1) {
                          _or = true;
                        } else {
                          String _propertyValue_3 = tabColumnWid.getPropertyValue("column-type");
                          boolean _equalsIgnoreCase_2 = _propertyValue_3.equalsIgnoreCase("Computed");
                          _or = _equalsIgnoreCase_2;
                        }
                        if (_or) {
                          _builder.append("\t\t");
                          _builder.append("\t");
                          _builder.append("<td>");
                          String _propertyValue_4 = tabColumnWid.getPropertyValue("column-name");
                          _builder.append(_propertyValue_4, "\t\t\t");
                          _builder.append("</td>");
                          _builder.newLineIfNotEmpty();
                        } else {
                          String _propertyValue_5 = tabColumnWid.getPropertyValue("column-type");
                          boolean _equalsIgnoreCase_3 = _propertyValue_5.equalsIgnoreCase("dynamic");
                          if (_equalsIgnoreCase_3) {
                            _builder.append("\t\t");
                            _builder.append("\t");
                            _builder.append("Based on group [");
                            String _propertyValue_6 = tabColumnWid.getPropertyValue("column-based-on-group");
                            _builder.append(_propertyValue_6, "\t\t\t");
                            _builder.append("]");
                            _builder.newLineIfNotEmpty();
                          } else {
                            String _propertyValue_7 = tabColumnWid.getPropertyValue("column-display-grouping");
                            Boolean _valueOf = Boolean.valueOf(_propertyValue_7);
                            if ((_valueOf).booleanValue()) {
                              _builder.append("\t\t");
                              _builder.append("\t");
                              _builder.append("<td>Display grouping </td>");
                              _builder.newLine();
                            } else {
                              _builder.append("\t\t");
                              _builder.append("\t");
                              _builder.append("<td></td>");
                              _builder.newLine();
                            }
                          }
                        }
                      }
                    }
                    _builder.append("\t\t");
                    _builder.append("\t");
                    String docText = tabColumnWid.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t");
                    _builder.append("<td>");
                    {
                      boolean _and = false;
                      boolean _notEquals = (!Objects.equal(docText, null));
                      if (!_notEquals) {
                        _and = false;
                      } else {
                        boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(docText);
                        boolean _not_3 = (!_isNullOrEmpty_1);
                        _and = _not_3;
                      }
                      if (_and) {
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t\t");
                        _builder.append("\t");
                        _builder.append("Description: ");
                        String _replaceAll = docText.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
                        _builder.append(_replaceAll, "\t\t\t");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t\t");
                        _builder.append("\t");
                      }
                    }
                    _builder.append(" </td> ");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t");
                    List<Widget> tableExtraWidgets = CollectionLiterals.<Widget>newArrayList();
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t");
                    List<Widget> tableExtraList = this.fetchWidgets(tabWid, "TableExtra", tableExtraWidgets);
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t\t");
                    _builder.newLine();
                    {
                      Property _findProperty = tabColumnWid.findProperty(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
                      boolean _notEquals_1 = (!Objects.equal(_findProperty, null));
                      if (_notEquals_1) {
                        {
                          Property _findProperty_1 = tabColumnWid.findProperty(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
                          boolean _isReadonly = _findProperty_1.isReadonly();
                          boolean _not_4 = (!_isReadonly);
                          if (_not_4) {
                            _builder.append("\t\t");
                            _builder.append("\t");
                            _builder.append("<td>Editable Attribute</td>");
                            _builder.newLine();
                          } else {
                            _builder.append("\t\t");
                            _builder.append("\t");
                            _builder.append("<td>Attribute</td>\t\t");
                            _builder.newLine();
                          }
                        }
                      } else {
                        String _propertyValue_8 = tabColumnWid.getPropertyValue("column-type");
                        boolean _equalsIgnoreCase_4 = _propertyValue_8.equalsIgnoreCase("Placeholder");
                        if (_equalsIgnoreCase_4) {
                          _builder.append("\t\t");
                          _builder.append("\t");
                          _builder.append("<td>Placeholder</td>");
                          _builder.newLine();
                        } else {
                          boolean _and_1 = false;
                          String _propertyValue_9 = tabColumnWid.getPropertyValue("column-type");
                          boolean _equalsIgnoreCase_5 = _propertyValue_9.equalsIgnoreCase("Computed");
                          if (!_equalsIgnoreCase_5) {
                            _and_1 = false;
                          } else {
                            String _propertyValue_10 = tabColumnWid.getPropertyValue("column-computation");
                            boolean _equalsIgnoreCase_6 = _propertyValue_10.equalsIgnoreCase("Make amount");
                            _and_1 = _equalsIgnoreCase_6;
                          }
                          if (_and_1) {
                            _builder.append("\t\t");
                            _builder.append("\t");
                            _builder.append("<td>Make Amount</td>");
                            _builder.newLine();
                          } else {
                            boolean _and_2 = false;
                            String _propertyValue_11 = tabColumnWid.getPropertyValue("column-type");
                            boolean _equalsIgnoreCase_7 = _propertyValue_11.equalsIgnoreCase("Computed");
                            if (!_equalsIgnoreCase_7) {
                              _and_2 = false;
                            } else {
                              String _propertyValue_12 = tabColumnWid.getPropertyValue("column-computation");
                              boolean _equalsIgnoreCase_8 = _propertyValue_12.equalsIgnoreCase("Same");
                              _and_2 = _equalsIgnoreCase_8;
                            }
                            if (_and_2) {
                              _builder.append("\t\t");
                              _builder.append("\t");
                              _builder.append("<td>Same</td>\t");
                              _builder.newLine();
                            } else {
                              String _propertyValue_13 = tabColumnWid.getPropertyValue("column-type");
                              boolean _equalsIgnoreCase_9 = _propertyValue_13.equalsIgnoreCase("dynamic");
                              if (_equalsIgnoreCase_9) {
                                _builder.append("\t\t");
                                _builder.append("\t");
                                _builder.append("<td>Dynamic</td>");
                                _builder.newLine();
                              } else {
                                int _size_1 = tableExtraList.size();
                                boolean _greaterThan_1 = (_size_1 > 0);
                                if (_greaterThan_1) {
                                  _builder.append("\t\t");
                                  _builder.append("\t");
                                  _builder.append("<td>");
                                  String _string = tableExtraList.toString();
                                  String _string_1 = tableExtraList.toString();
                                  int _length = _string_1.length();
                                  int _minus = (_length - 2);
                                  CharSequence _subSequence = _string.subSequence(1, _minus);
                                  _builder.append(_subSequence, "\t\t\t");
                                  _builder.append("</td>");
                                  _builder.newLineIfNotEmpty();
                                } else {
                                  _builder.append("<td></td>\t\t\t");
                                  _builder.newLine();
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                    _builder.append("\t\t");
                    _builder.append("\t");
                    List<Widget> tabColItemWids = CollectionLiterals.<Widget>newArrayList();
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t");
                    List<Widget> tabColItemWidList = this.fetchWidgets(tabColumnWid, "TableColumnItem", tabColItemWids);
                    _builder.newLineIfNotEmpty();
                    {
                      int _size_2 = tabColItemWidList.size();
                      boolean _greaterThan_2 = (_size_2 > 0);
                      if (_greaterThan_2) {
                        _builder.append("\t\t");
                        _builder.append("\t");
                        _builder.append("<td>");
                        Widget _get = tabColItemWidList.get(0);
                        String _propertyValue_14 = _get.getPropertyValue("format");
                        _builder.append(_propertyValue_14, "\t\t\t");
                        _builder.append("</td>\t");
                        _builder.newLineIfNotEmpty();
                      } else {
                        _builder.append("<td></td>\t\t");
                        _builder.newLine();
                      }
                    }
                    {
                      String _propertyValue_15 = tabColumnWid.getPropertyValue("column-locked");
                      Boolean _valueOf_1 = Boolean.valueOf(_propertyValue_15);
                      if ((_valueOf_1).booleanValue()) {
                        _builder.append("\t\t");
                        _builder.append("\t");
                        _builder.append("<td>Locked</td>\t");
                        _builder.newLine();
                      } else {
                        String _propertyValue_16 = tabColumnWid.getPropertyValue("column-visibility");
                        boolean _equalsIgnoreCase_10 = _propertyValue_16.equalsIgnoreCase("Visible");
                        if (_equalsIgnoreCase_10) {
                          _builder.append("\t\t");
                          _builder.append("\t");
                          _builder.append("<td>By default</td>\t");
                          _builder.newLine();
                        } else {
                          String _propertyValue_17 = tabColumnWid.getPropertyValue("column-visibility");
                          boolean _equalsIgnoreCase_11 = _propertyValue_17.equalsIgnoreCase("Not Visible");
                          if (_equalsIgnoreCase_11) {
                            _builder.append("\t\t");
                            _builder.append("\t");
                            _builder.append("<td>Not by default</td>\t");
                            _builder.newLine();
                          } else {
                            String _propertyValue_18 = tabColumnWid.getPropertyValue("column-visibility");
                            boolean _equalsIgnoreCase_12 = _propertyValue_18.equalsIgnoreCase("Never Visible");
                            if (_equalsIgnoreCase_12) {
                              _builder.append("\t\t");
                              _builder.append("\t");
                              _builder.append("<td>Never</td>");
                              _builder.newLine();
                            } else {
                              _builder.append("<td></td>\t\t");
                              _builder.newLine();
                            }
                          }
                        }
                      }
                    }
                    {
                      boolean _and_3 = false;
                      EList<Translation> _toolTips = tabColumnWid.getToolTips();
                      boolean _notEquals_2 = (!Objects.equal(_toolTips, null));
                      if (!_notEquals_2) {
                        _and_3 = false;
                      } else {
                        EList<Translation> _toolTips_1 = tabColumnWid.getToolTips();
                        int _size_3 = _toolTips_1.size();
                        boolean _greaterThan_3 = (_size_3 > 0);
                        _and_3 = _greaterThan_3;
                      }
                      if (_and_3) {
                        {
                          EList<Translation> _toolTips_2 = tabColumnWid.getToolTips();
                          for(final Translation tr : _toolTips_2) {
                            {
                              String _language = tr.getLanguage();
                              boolean _equals = _language.equals("en");
                              if (_equals) {
                                _builder.append("\t\t");
                                _builder.append("\t");
                                _builder.append("<td>");
                                String _message = tr.getMessage();
                                _builder.append(_message, "\t\t\t");
                                _builder.append("</td>");
                                _builder.newLineIfNotEmpty();
                              }
                            }
                          }
                        }
                      } else {
                        _builder.append("<td></td> ");
                        _builder.newLine();
                      }
                    }
                    {
                      String _propertyValue_19 = tabColumnWid.getPropertyValue("column-sortable");
                      Boolean _valueOf_2 = Boolean.valueOf(_propertyValue_19);
                      if ((_valueOf_2).booleanValue()) {
                        _builder.append("\t\t");
                        _builder.append("\t");
                        _builder.append("<td>Yes</td>");
                        _builder.newLine();
                      } else {
                        _builder.append("\t\t");
                        _builder.append("\t");
                        _builder.append("<td>No</td>\t\t\t\t\t");
                        _builder.newLine();
                      }
                    }
                    {
                      String _propertyValue_20 = tabWid.getPropertyValue("table-filter-layer");
                      Boolean _valueOf_3 = Boolean.valueOf(_propertyValue_20);
                      if ((_valueOf_3).booleanValue()) {
                        _builder.append("\t\t");
                        _builder.append("\t");
                        _builder.append("<td>Yes</td>");
                        _builder.newLine();
                      } else {
                        _builder.append("\t\t");
                        _builder.append("\t");
                        _builder.append("<td>No</td>\t\t\t\t\t");
                        _builder.newLine();
                      }
                    }
                    {
                      String _propertyValue_21 = tabColumnWid.getPropertyValue("column-display-grouping");
                      Boolean _valueOf_4 = Boolean.valueOf(_propertyValue_21);
                      if ((_valueOf_4).booleanValue()) {
                        _builder.append("\t\t");
                        _builder.append("\t");
                        _builder.append("<td>Yes</td>");
                        _builder.newLine();
                      } else {
                        _builder.append("\t\t");
                        _builder.append("\t");
                        _builder.append("<td>No</td>\t\t\t\t\t");
                        _builder.newLine();
                      }
                    }
                    _builder.append("\t\t");
                    _builder.append("\t");
                    List<Widget> tableGrpWidgets = CollectionLiterals.<Widget>newArrayList();
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t");
                    List<Widget> tableGrpList = this.fetchWidgets(tabWid, "TableGroup", tableGrpWidgets);
                    _builder.newLineIfNotEmpty();
                    {
                      boolean _isEmpty_2 = tableGrpList.isEmpty();
                      boolean _not_5 = (!_isEmpty_2);
                      if (_not_5) {
                        _builder.append("\t\t");
                        _builder.append("\t");
                        _builder.append("<td>Level: [number: ");
                        {
                          for(final Widget tabGrp : tableGrpList) {
                            String _propertyValue_22 = tabGrp.getPropertyValue("group-rank");
                            _builder.append(_propertyValue_22, "\t\t\t");
                            _builder.append(" ");
                            {
                              int _indexOf = tableGrpList.indexOf(tabGrp);
                              int _size_4 = tableGrpList.size();
                              int _minus_1 = (_size_4 - 1);
                              boolean _notEquals_3 = (_indexOf != _minus_1);
                              if (_notEquals_3) {
                                _builder.append(",");
                              }
                            }
                          }
                        }
                        _builder.append("]</td>\t");
                        _builder.newLineIfNotEmpty();
                      } else {
                        _builder.append("<td></td> \t\t");
                        _builder.newLine();
                      }
                    }
                    {
                      String _propertyValue_23 = tabColumnWid.getPropertyValue("column-is-part-of-filter");
                      Boolean _valueOf_5 = Boolean.valueOf(_propertyValue_23);
                      if ((_valueOf_5).booleanValue()) {
                        _builder.append("\t\t");
                        _builder.append("\t");
                        _builder.append("<td>Yes</td>");
                        _builder.newLine();
                      } else {
                        _builder.append("\t\t");
                        _builder.append("\t");
                        _builder.append("<td>No</td>\t\t\t\t\t");
                        _builder.newLine();
                      }
                    }
                    _builder.append("\t\t");
                    _builder.append("\t");
                    _builder.append("<td>");
                    String _propertyValue_24 = tabWid.getPropertyValue("table-keep-filter-logic");
                    _builder.append(_propertyValue_24, "\t\t\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t");
                    _builder.append("<td>");
                    String _propertyValue_25 = tabColumnWid.getPropertyValue("column-max-characters");
                    _builder.append(_propertyValue_25, "\t\t\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t");
                    _builder.append("<td>");
                    String _propertyValue_26 = tabColumnWid.getPropertyValue("column-wrapped");
                    _builder.append(_propertyValue_26, "\t\t\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t");
                    _builder.append("<td>");
                    String _propertyValue_27 = tabColumnWid.getPropertyValue("column-width");
                    _builder.append(_propertyValue_27, "\t\t\t");
                    _builder.append("</td>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("</tr>");
                    _builder.newLine();
                  }
                }
                _builder.append("</table><br>\t\t");
                _builder.newLine();
                CharSequence _htmlClosing = this.htmlClosing();
                _builder.append(_htmlClosing, "");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    return _builder;
  }
  
  public String getAggregateFunction(final String domainAtrName, final List<Widget> tableAggregateList) {
    for (final Widget wid : tableAggregateList) {
      String _propertyValue = wid.getPropertyValue("aggregate-column-name");
      boolean _equals = _propertyValue.equals(domainAtrName);
      if (_equals) {
        return wid.getPropertyValue("aggregate-computation");
      }
    }
    return "";
  }
  
  public CharSequence generalDescription(final Model model, final IOfsProject project, final String type) {
    StringConcatenation _builder = new StringConcatenation();
    List<Widget> tableWidgets = CollectionLiterals.<Widget>newArrayList();
    _builder.newLineIfNotEmpty();
    Widget _widget = model.getWidget();
    List<Widget> tableList = this.fetchWidgets(_widget, type, tableWidgets);
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = tableList.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        CharSequence _htmlOpening = this.htmlOpening();
        _builder.append(_htmlOpening, "");
        _builder.newLineIfNotEmpty();
        {
          for(final Widget tabWid : tableWidgets) {
            {
              boolean _or = false;
              Widget _parent = tabWid.getParent();
              WidgetType _type = _parent.getType();
              String _name = _type.getName();
              boolean _equals = _name.equals(WidgetTypeConstants.CONDITIONAL_BODY);
              if (_equals) {
                _or = true;
              } else {
                Widget _parent_1 = tabWid.getParent();
                WidgetType _type_1 = _parent_1.getType();
                String _name_1 = _type_1.getName();
                boolean _equals_1 = _name_1.equals(WidgetTypeConstants.TAB);
                _or = _equals_1;
              }
              if (_or) {
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("This table belongs to the ");
                Widget _parent_2 = tabWid.getParent();
                WidgetType _type_2 = _parent_2.getType();
                String _name_2 = _type_2.getName();
                _builder.append(_name_2, "\t\t");
                _builder.append(".<br><br>");
                _builder.newLineIfNotEmpty();
              } else {
                boolean _or_1 = false;
                Widget _rootWidget = tabWid.getRootWidget();
                WidgetType _type_3 = _rootWidget.getType();
                String _name_3 = _type_3.getName();
                boolean _equals_2 = _name_3.equals(WidgetTypeConstants.MODULE);
                if (_equals_2) {
                  _or_1 = true;
                } else {
                  Widget _rootWidget_1 = tabWid.getRootWidget();
                  WidgetType _type_4 = _rootWidget_1.getType();
                  String _name_4 = _type_4.getName();
                  boolean _equals_3 = _name_4.equals(WidgetTypeConstants.PAGE);
                  _or_1 = _equals_3;
                }
                if (_or_1) {
                  _builder.append("\t");
                  _builder.append("\t");
                  _builder.append("This table belongs to the ");
                  Widget _rootWidget_2 = tabWid.getRootWidget();
                  WidgetType _type_5 = _rootWidget_2.getType();
                  String _name_5 = _type_5.getName();
                  _builder.append(_name_5, "\t\t");
                  _builder.append(".<br><br>");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
            {
              String _propertyValue = tabWid.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
              boolean _notEquals = (!Objects.equal(_propertyValue, null));
              if (_notEquals) {
                _builder.append("\t");
                _builder.append("\t");
                String docText = tabWid.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
                _builder.newLineIfNotEmpty();
                {
                  boolean _and = false;
                  boolean _notEquals_1 = (!Objects.equal(docText, null));
                  if (!_notEquals_1) {
                    _and = false;
                  } else {
                    boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(docText);
                    boolean _not_1 = (!_isNullOrEmpty);
                    _and = _not_1;
                  }
                  if (_and) {
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("Description: ");
                    String _replaceAll = docText.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
                    _builder.append(_replaceAll, "\t\t\t");
                    _builder.append(" <br>");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
            _builder.append("\t");
            _builder.append("List of properties set on the table: <br>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<table border=\"1\">");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<th bgcolor=\"#6495ED\">Property Name</th>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<th bgcolor=\"#6495ED\">Value</th>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>Rendering mode </td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>");
            String _propertyValue_1 = tabWid.getPropertyValue("table-rendering-mode");
            String _capitalize = WordUtils.capitalize(_propertyValue_1);
            _builder.append(_capitalize, "\t\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>Make multiple checkboxes exclusive mode </td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>");
            {
              String _propertyValue_2 = tabWid.getPropertyValue("make-checkbox-exclusive");
              Boolean _valueOf = Boolean.valueOf(_propertyValue_2);
              if ((_valueOf).booleanValue()) {
                _builder.append("Yes");
              } else {
                _builder.append("No");
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>Display checkbox on tree nodes </td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>");
            {
              String _propertyValue_3 = tabWid.getPropertyValue("display-checkbox");
              Boolean _valueOf_1 = Boolean.valueOf(_propertyValue_3);
              if ((_valueOf_1).booleanValue()) {
                _builder.append("Yes");
              } else {
                _builder.append("No");
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>Show Column Selector</td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>");
            {
              String _propertyValue_4 = tabWid.getPropertyValue("show-column-selector");
              Boolean _valueOf_2 = Boolean.valueOf(_propertyValue_4);
              if ((_valueOf_2).booleanValue()) {
                _builder.append("Yes");
              } else {
                _builder.append("No");
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>Expand/Collapse all </td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>");
            {
              String _propertyValue_5 = tabWid.getPropertyValue("table-expand-all");
              Boolean _valueOf_3 = Boolean.valueOf(_propertyValue_5);
              if ((_valueOf_3).booleanValue()) {
                _builder.append("Yes");
              } else {
                _builder.append("No");
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>Filter </td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>");
            {
              String _propertyValue_6 = tabWid.getPropertyValue("table-filter-layer");
              Boolean _valueOf_4 = Boolean.valueOf(_propertyValue_6);
              if ((_valueOf_4).booleanValue()) {
                _builder.append("Yes");
              } else {
                _builder.append("No");
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>Select/De-select all </td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>");
            {
              String _propertyValue_7 = tabWid.getPropertyValue("table-select-all");
              Boolean _valueOf_5 = Boolean.valueOf(_propertyValue_7);
              if ((_valueOf_5).booleanValue()) {
                _builder.append("Yes");
              } else {
                _builder.append("No");
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>Highlight row </td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>");
            {
              String _propertyValue_8 = tabWid.getPropertyValue("highlightRow");
              Boolean _valueOf_6 = Boolean.valueOf(_propertyValue_8);
              if ((_valueOf_6).booleanValue()) {
                _builder.append("Yes");
              } else {
                _builder.append("No");
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            {
              boolean _and_1 = false;
              String _propertyValue_9 = tabWid.getPropertyValue("table-page-size");
              String _string = _propertyValue_9.toString();
              boolean _notEquals_2 = (!Objects.equal(_string, null));
              if (!_notEquals_2) {
                _and_1 = false;
              } else {
                String _propertyValue_10 = tabWid.getPropertyValue("table-page-size");
                String _string_1 = _propertyValue_10.toString();
                boolean _isEmpty_1 = _string_1.isEmpty();
                boolean _not_2 = (!_isEmpty_1);
                _and_1 = _not_2;
              }
              if (_and_1) {
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<tr>");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<td>Page size </td>");
                _builder.newLine();
                {
                  boolean _and_2 = false;
                  String _propertyValue_11 = tabWid.getPropertyValue("table-page-size");
                  boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(_propertyValue_11);
                  boolean _not_3 = (!_isNullOrEmpty_1);
                  if (!_not_3) {
                    _and_2 = false;
                  } else {
                    String _propertyValue_12 = tabWid.getPropertyValue("table-page-size");
                    int _length = _propertyValue_12.length();
                    boolean _greaterThan = (_length > 2);
                    _and_2 = _greaterThan;
                  }
                  if (_and_2) {
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("\t");
                    String _propertyValue_13 = tabWid.getPropertyValue("table-page-size");
                    String _string_2 = _propertyValue_13.toString();
                    String _propertyValue_14 = tabWid.getPropertyValue("table-page-size");
                    String _string_3 = _propertyValue_14.toString();
                    int _length_1 = _string_3.length();
                    int _minus = (_length_1 - 1);
                    String pageSize = _string_2.substring(2, _minus);
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("\t");
                    String[] strBrk = pageSize.split("-");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("<td>");
                    String _get = strBrk[0];
                    String _string_4 = _get.toString();
                    String _firstUpper = StringExtensions.toFirstUpper(_string_4);
                    _builder.append(_firstUpper, "\t\t\t");
                    _builder.append("(");
                    int _length_2 = pageSize.length();
                    _builder.append(_length_2, "\t\t\t");
                    _builder.append(")");
                    _builder.newLineIfNotEmpty();
                  }
                }
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("</tr>");
                _builder.newLine();
              }
            }
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>Dataset reference </td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>");
            String _datasetName = ModuleUtils.getDatasetName(project, tabWid);
            _builder.append(_datasetName, "\t\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>Based on Class </td>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t\t");
            _builder.append("<td>");
            String _datasetBaseClassName = ModuleUtils.getDatasetBaseClassName(project, tabWid);
            _builder.append(_datasetBaseClassName, "\t\t\t");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
        _builder.append("\t");
        _builder.append("</table>");
        _builder.newLine();
        CharSequence _htmlClosing = this.htmlClosing();
        _builder.append(_htmlClosing, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public boolean isCheckBoxAvailable(final Widget widget) {
    EList<Widget> _contents = widget.getContents();
    for (final Widget wid : _contents) {
      WidgetType _type = wid.getType();
      String _name = _type.getName();
      boolean _equals = _name.equals(WidgetTypeConstants.CHECKBOX);
      if (_equals) {
        return true;
      }
    }
    return false;
  }
  
  public boolean isMoreTableGroupAvailable(final Widget widget) {
    List<Widget> tableGrpWidgets = CollectionLiterals.<Widget>newArrayList();
    EList<Widget> _contents = widget.getContents();
    for (final Widget wid : _contents) {
      WidgetType _type = wid.getType();
      String _name = _type.getName();
      boolean _equals = _name.equals("TableGroup");
      if (_equals) {
        tableGrpWidgets.add(wid);
      }
    }
    int _size = tableGrpWidgets.size();
    boolean _greaterThan = (_size > 1);
    if (_greaterThan) {
      return true;
    }
    return false;
  }
  
  public Widget getDynamicGroupWidget(final Widget widget) {
    List<Widget> tableWidgets = CollectionLiterals.<Widget>newArrayList();
    this.fetchWidgets(widget, "TableGroup", tableWidgets);
    for (final Widget wid : tableWidgets) {
      boolean _and = false;
      WidgetType _type = wid.getType();
      String _name = _type.getName();
      boolean _equals = _name.equals("TableGroup");
      if (!_equals) {
        _and = false;
      } else {
        String _propertyValue = wid.getPropertyValue("group-dynamic-column");
        Boolean _valueOf = Boolean.valueOf(_propertyValue);
        _and = (_valueOf).booleanValue();
      }
      if (_and) {
        return wid;
      }
    }
    return null;
  }
  
  public List<Widget> fetchWidgets(final Widget widget, final String widgetType, final List<Widget> tableWidgets) {
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
            boolean _contains = tableWidgets.contains(wid);
            boolean _not = (!_contains);
            _and = _not;
          }
          if (_and) {
            tableWidgets.add(wid);
          }
        }
      }
      return tableWidgets;
    } else {
      EList<Widget> _contents_1 = widget.getContents();
      boolean _isEmpty = _contents_1.isEmpty();
      boolean _not_1 = (!_isEmpty);
      if (_not_1) {
        EList<Widget> _contents_2 = widget.getContents();
        for (final Widget wid_1 : _contents_2) {
          this.fetchWidgets(wid_1, widgetType, tableWidgets);
        }
      }
    }
    return tableWidgets;
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
  
  public Widget getParentWidgetByType(final Widget widget, final String widgetType) {
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
      boolean _and = false;
      boolean _notEquals_1 = (!Objects.equal(widget, null));
      if (!_notEquals_1) {
        _and = false;
      } else {
        EObject _eContainer = widget.eContainer();
        boolean _notEquals_2 = (!Objects.equal(_eContainer, null));
        _and = _notEquals_2;
      }
      if (_and) {
        EObject _eContainer_1 = widget.eContainer();
        return this.getParentWidgetByType(((Widget) _eContainer_1), widgetType);
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
  
  public CharSequence tableMatrixIndex(final String path, final List<String> fileList) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _htmlOpening = this.htmlOpening();
    _builder.append(_htmlOpening, "");
    _builder.newLineIfNotEmpty();
    _builder.append("<ul>");
    _builder.newLine();
    {
      for(final String tableMatrixFile : fileList) {
        _builder.append("\t");
        _builder.append("<li>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("<A HREF=\"");
        _builder.append((path + "\\"), "\t\t");
        _builder.append(tableMatrixFile, "\t\t");
        _builder.append("\">");
        String _titleForFileName = GenerationUtils.getTitleForFileName(tableMatrixFile);
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
