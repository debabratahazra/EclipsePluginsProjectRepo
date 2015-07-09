package com.odcgroup.t24.helptextimport.mapper;

import com.google.common.base.Objects;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfPropertyImpl;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.t24.applicationimport.mapper.MappingException;
import com.odcgroup.t24.helptext.schema.Menu;
import com.odcgroup.t24.helptext.schema.T24Help;
import java.util.ArrayList;
import java.util.List;
import org.apache.xerces.dom.ElementNSImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

@SuppressWarnings("all")
public class T24HelpText2MdfMapper {
  private static Logger LOGGER = LoggerFactory.getLogger(T24HelpText2MdfMapper.class);
  
  public void map(final T24Help application, final MdfClassImpl mdfClass) throws MappingException {
    ArrayList<MdfClassImpl> mulSubClasList = this.getMultiSubValueGroups(mdfClass);
    Menu _menu = application.getMenu();
    boolean _notEquals = (!Objects.equal(_menu, null));
    if (_notEquals) {
      Menu _menu_1 = application.getMenu();
      List<Menu.T> _t = _menu_1.getT();
      for (final Menu.T t : _t) {
        String _field = t.getField();
        boolean _notEquals_1 = (!Objects.equal(_field, null));
        if (_notEquals_1) {
          String _field_1 = t.getField();
          String _validName = this.toValidName(_field_1);
          MdfPropertyImpl attrType = this.getClassAttribute(_validName, mulSubClasList);
          boolean _and = false;
          boolean _notEquals_2 = (!Objects.equal(attrType, null));
          if (!_notEquals_2) {
            _and = false;
          } else {
            Menu.T.Desc _desc = t.getDesc();
            boolean _notEquals_3 = (!Objects.equal(_desc, null));
            _and = _notEquals_3;
          }
          if (_and) {
            Menu.T.Desc _desc_1 = t.getDesc();
            List<Object> _content = _desc_1.getContent();
            this.setFieldCommon(attrType, _content);
          }
        } else {
          final String msg = (application + " does not have a field to save to domain");
          this.throwMappingError(msg);
          T24HelpText2MdfMapper.LOGGER.error(msg);
        }
      }
    }
  }
  
  public ArrayList<MdfClassImpl> getMultiSubValueGroups(final MdfClassImpl mdfClass) {
    EObject _eContainer = mdfClass.eContainer();
    MdfDomain mdfDomain = ((MdfDomain) _eContainer);
    ArrayList<MdfClassImpl> subClasList = new ArrayList<MdfClassImpl>();
    subClasList.add(mdfClass);
    List _classes = mdfDomain.getClasses();
    for (final Object multiClass : _classes) {
      {
        MdfClassImpl multiValclass = ((MdfClassImpl) multiClass);
        String _name = multiValclass.getName();
        String _name_1 = mdfClass.getName();
        String _plus = (_name_1 + "__");
        boolean _startsWith = _name.startsWith(_plus);
        if (_startsWith) {
          subClasList.add(multiValclass);
        }
      }
    }
    return subClasList;
  }
  
  public MdfPropertyImpl getClassAttribute(final String fieldNames, final List<MdfClassImpl> mdfClasses) {
    for (final MdfClassImpl genMultiSubClass : mdfClasses) {
      {
        List _properties = genMultiSubClass.getProperties();
        final Function1<MdfPropertyImpl, Boolean> _function = new Function1<MdfPropertyImpl, Boolean>() {
          public Boolean apply(final MdfPropertyImpl e) {
            boolean _and = false;
            String _name = e.getName();
            boolean _equals = _name.equals(fieldNames);
            if (!_equals) {
              _and = false;
            } else {
              boolean _or = false;
              if ((e instanceof MdfAttributeImpl)) {
                _or = true;
              } else {
                boolean _and_1 = false;
                if (!(e instanceof MdfAssociationImpl)) {
                  _and_1 = false;
                } else {
                  int _containment = ((MdfAssociationImpl) e).getContainment();
                  boolean _equals_1 = (_containment == 1);
                  _and_1 = _equals_1;
                }
                _or = _and_1;
              }
              _and = _or;
            }
            return Boolean.valueOf(_and);
          }
        };
        MdfPropertyImpl _findFirst = IterableExtensions.<MdfPropertyImpl>findFirst(_properties, _function);
        MdfPropertyImpl attrType = ((MdfPropertyImpl) _findFirst);
        boolean _notEquals = (!Objects.equal(attrType, null));
        if (_notEquals) {
          return attrType;
        }
      }
    }
    return null;
  }
  
  public void setFieldCommon(final MdfPropertyImpl property, final List<?> fieldDesc) {
    StringBuffer p = new StringBuffer();
    for (final Object descText : fieldDesc) {
      boolean _notEquals = (!Objects.equal(descText, null));
      if (_notEquals) {
        if ((descText instanceof ElementNSImpl)) {
          ElementNSImpl node = ((ElementNSImpl) descText);
          String _nodeName = node.getNodeName();
          boolean _equals = _nodeName.equals("table");
          if (_equals) {
            Node tr = node.getFirstChild();
            while ((!Objects.equal(tr, null))) {
              {
                Node td = tr.getFirstChild();
                while ((!Objects.equal(td, null))) {
                  {
                    String _textContent = td.getTextContent();
                    String _trim = _textContent.trim();
                    StringBuffer _append = p.append(_trim);
                    _append.append("#");
                    Node _nextSibling = td.getNextSibling();
                    td = _nextSibling;
                  }
                }
                Node _nextSibling = tr.getNextSibling();
                boolean _notEquals_1 = (!Objects.equal(_nextSibling, null));
                if (_notEquals_1) {
                  p.append("\n");
                }
                Node _nextSibling_1 = tr.getNextSibling();
                tr = _nextSibling_1;
              }
            }
          } else {
            String _textContent = node.getTextContent();
            String _trim = _textContent.trim();
            StringBuffer _append = p.append(_trim);
            _append.append("\n");
          }
        } else {
          if ((descText instanceof String)) {
            String _string = ((String)descText).toString();
            String _trim_1 = _string.trim();
            p.append(_trim_1);
          }
        }
      }
    }
    String description = p.toString();
    boolean _contains = description.contains("Help Text for this field is unavailable.  Please refer to the T24 User Guides for further information.");
    boolean _not = (!_contains);
    if (_not) {
      property.setDocumentation(description);
    }
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
      boolean _and = false;
      boolean _isEmpty = cleanName.isEmpty();
      boolean _not = (!_isEmpty);
      if (!_not) {
        _and = false;
      } else {
        char _charAt = cleanName.charAt(0);
        boolean _isDigit = Character.isDigit(_charAt);
        _and = _isDigit;
      }
      if (_and) {
        cleanName = ("n" + cleanName);
      }
      _xblockexpression = cleanName;
    }
    return _xblockexpression;
  }
  
  public void throwMappingError(final String message) throws MappingException {
    throw new MappingException(message);
  }
}
