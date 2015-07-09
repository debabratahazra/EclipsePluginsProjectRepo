package com.odcgroup.menu.generation.tap;

import com.google.common.base.Objects;
import com.odcgroup.menu.generation.tap.IMenuGenerator;
import com.odcgroup.menu.model.Enabled;
import com.odcgroup.menu.model.MenuItem;
import com.odcgroup.menu.model.MenuRoot;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.generation.CodeGenerationPreferences;
import java.util.Date;
import java.util.HashMap;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend.expression.Variable;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class MenuConfig implements IMenuGenerator {
  public void doGenerate(final MenuRoot root, final IFileSystemAccess fsa, final HashMap<String, Variable> globalVars) {
    MenuItem _rootItem = root.getRootItem();
    CharSequence _renderRootNode = this.renderRootNode(_rootItem, globalVars);
    fsa.generateFile("menu/Menu.xml", _renderRootNode);
  }
  
  public CharSequence renderRootNode(final MenuItem item, final HashMap<String, Variable> globalVars) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("<!-- ");
    _builder.newLine();
    _builder.append("*************************************************************************************");
    _builder.newLine();
    _builder.append("DO NOT MODIFY THIS FILE!");
    _builder.newLine();
    _builder.newLine();
    _builder.append("This file has been generated automatically  by Design Studio. ");
    _builder.newLine();
    _builder.append("Any change will be lost when regenerated.");
    _builder.newLine();
    _builder.append("*************************************************************************************\t\t");
    _builder.newLine();
    _builder.append("Design Studio information of menu model");
    _builder.newLine();
    _builder.append("Project name     : ");
    String _var = this.getVar(globalVars, "projectName");
    _builder.append(_var, "");
    _builder.newLineIfNotEmpty();
    _builder.append("Package          : ");
    String _var_1 = this.getVar(globalVars, "packageName");
    _builder.append(_var_1, "");
    _builder.newLineIfNotEmpty();
    _builder.append("Generation user  : ");
    String _var_2 = this.getVar(globalVars, "systemUser");
    _builder.append(_var_2, "");
    _builder.newLineIfNotEmpty();
    _builder.append("Generation date  : ");
    Date _currentDate = this.getCurrentDate();
    _builder.append(_currentDate, "");
    _builder.newLineIfNotEmpty();
    _builder.append("DS version       : ");
    String _dSBuildID = this.getDSBuildID();
    _builder.append(_dSBuildID, "");
    _builder.newLineIfNotEmpty();
    _builder.append("*************************************************************************************");
    _builder.newLine();
    _builder.append("-->");
    _builder.newLine();
    _builder.newLine();
    _builder.append("<menu xmlns:cinclude=\"http://apache.org/cocoon/include/1.0\" name=\"");
    String _name = item.getName();
    _builder.append(_name, "");
    _builder.append("\"");
    String _renderPageflow = this.renderPageflow(item);
    _builder.append(_renderPageflow, "");
    String _renderSecurityRole = this.renderSecurityRole(item);
    _builder.append(_renderSecurityRole, "");
    String _renderEnabled = this.renderEnabled(item);
    _builder.append(_renderEnabled, "");
    String _renderKeyboardShortcut = this.renderKeyboardShortcut(item);
    _builder.append(_renderKeyboardShortcut, "");
    String _renderChildTabs = this.renderChildTabs(item);
    _builder.append(_renderChildTabs, "");
    String _closetag = this.closetag(item);
    _builder.append(_closetag, "");
    _builder.newLineIfNotEmpty();
    {
      EList<MenuItem> _submenus = item.getSubmenus();
      for(final MenuItem submenu : _submenus) {
        _builder.append("\t");
        CharSequence _renderSubMenu = this.renderSubMenu(submenu);
        _builder.append(_renderSubMenu, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    String _renderEndMenu = this.renderEndMenu(item);
    _builder.append(_renderEndMenu, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public String renderEndMenu(final MenuItem item) {
    EList<MenuItem> _submenus = item.getSubmenus();
    boolean _isEmpty = _submenus.isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      return "</menu>";
    }
    return null;
  }
  
  public String renderPageflow(final MenuItem item) {
    String _pageflow = item.getPageflow();
    boolean _notEquals = (!Objects.equal(_pageflow, null));
    if (_notEquals) {
      String _resolveDSPageflowURL = this.resolveDSPageflowURL(item);
      String _plus = (" uri=\"" + _resolveDSPageflowURL);
      return (_plus + "\"");
    } else {
      return "";
    }
  }
  
  public String renderSecurityRole(final MenuItem item) {
    String _securityRole = item.getSecurityRole();
    boolean _notEquals = (!Objects.equal(_securityRole, null));
    if (_notEquals) {
      String _securityRole_1 = item.getSecurityRole();
      String _plus = (" acl=\"" + _securityRole_1);
      return (_plus + "\"");
    } else {
      return "";
    }
  }
  
  public String renderEnabled(final MenuItem item) {
    Enabled _enabled = item.getEnabled();
    String _string = _enabled.toString();
    boolean _equals = _string.equals("false");
    if (_equals) {
      return " always-enabled=\"false\" always-disabled=\"true\"";
    } else {
      Enabled _enabled_1 = item.getEnabled();
      String _string_1 = _enabled_1.toString();
      boolean _equals_1 = _string_1.equals("conditional");
      if (_equals_1) {
        return " always-enabled=\"false\" always-disabled=\"false\"";
      }
    }
    return null;
  }
  
  public String renderKeyboardShortcut(final MenuItem item) {
    boolean _and = false;
    String _shortcut = item.getShortcut();
    boolean _notEquals = (!Objects.equal(_shortcut, null));
    if (!_notEquals) {
      _and = false;
    } else {
      String _shortcut_1 = item.getShortcut();
      String _trim = _shortcut_1.trim();
      boolean _equals = _trim.equals("");
      boolean _not = (!_equals);
      _and = _not;
    }
    if (_and) {
      String _shortcut_2 = item.getShortcut();
      String _trim_1 = _shortcut_2.trim();
      String _plus = (" keyboard-shortcut=\"" + _trim_1);
      return (_plus + "\"");
    } else {
      return "";
    }
  }
  
  public CharSequence renderSubMenu(final MenuItem item) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<menu name=\"");
    String _name = item.getName();
    _builder.append(_name, "");
    _builder.append("\"");
    String _renderPageflow = this.renderPageflow(item);
    _builder.append(_renderPageflow, "");
    String _renderSecurityRole = this.renderSecurityRole(item);
    _builder.append(_renderSecurityRole, "");
    String _renderEnabled = this.renderEnabled(item);
    _builder.append(_renderEnabled, "");
    String _renderChildTabs = this.renderChildTabs(item);
    _builder.append(_renderChildTabs, "");
    String _renderKeyboardShortcut = this.renderKeyboardShortcut(item);
    _builder.append(_renderKeyboardShortcut, "");
    String _closetag = this.closetag(item);
    _builder.append(_closetag, "");
    _builder.newLineIfNotEmpty();
    {
      EList<MenuItem> _submenus = item.getSubmenus();
      for(final MenuItem submenu : _submenus) {
        _builder.append("\t");
        Object _renderSubMenu = this.renderSubMenu(submenu);
        _builder.append(_renderSubMenu, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    String _renderEndMenu = this.renderEndMenu(item);
    _builder.append(_renderEndMenu, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public String renderChildTabs(final MenuItem item) {
    int _menuLevel = this.getMenuLevel(item, 0);
    boolean _equals = (_menuLevel == 2);
    if (_equals) {
      boolean _isDisplayTabs = item.isDisplayTabs();
      String _plus = (" children-displayed-as-tabs=\"" + Boolean.valueOf(_isDisplayTabs));
      return (_plus + "\"");
    }
    return null;
  }
  
  public String closetag(final MenuItem item) {
    EList<MenuItem> _submenus = item.getSubmenus();
    boolean _isEmpty = _submenus.isEmpty();
    if (_isEmpty) {
      return "/>";
    } else {
      return ">";
    }
  }
  
  public String resolveDSPageflowURL(final MenuItem item) {
    final String url = item.getPageflow();
    String rurl = "";
    boolean _and = false;
    boolean _startsWith = url.startsWith("resource:///");
    if (!_startsWith) {
      _and = false;
    } else {
      boolean _endsWith = url.endsWith(".pageflow");
      _and = _endsWith;
    }
    if (_and) {
      try {
        Resource _eResource = item.eResource();
        final IOfsProject ofsProject = OfsResourceHelper.getOfsProject(_eResource);
        final IProject project = ofsProject.getProject();
        URI _createURI = URI.createURI(url);
        final IOfsModelResource mres = ofsProject.getOfsModelResource(_createURI);
        final EList<EObject> list = mres.getEMFModel();
        String targetFileName = "";
        for (final EObject model : list) {
          EClass _eClass = model.eClass();
          String _name = _eClass.getName();
          boolean _equals = "Pageflow".equals(_name);
          if (_equals) {
            EClass _eClass_1 = model.eClass();
            EStructuralFeature _eStructuralFeature = _eClass_1.getEStructuralFeature("fileName");
            Object _eGet = model.eGet(_eStructuralFeature);
            String _plus = ("" + _eGet);
            targetFileName = _plus;
          }
        }
        String activity = CodeGenerationPreferences.getPageflowActivityPreference(project);
        String _modelName = this.getModelName(url, "pageflow");
        String _plus_1 = ((((("/activity/" + activity) + "/pageflow/") + targetFileName) + "/") + _modelName);
        rurl = _plus_1;
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
          final Exception e = (Exception)_t;
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    } else {
      rurl = url;
    }
    return rurl;
  }
  
  public String getModelName(final String resourceURL, final String extn) {
    int index = resourceURL.lastIndexOf("/");
    int _length = resourceURL.length();
    int _length_1 = extn.length();
    int _minus = (_length - _length_1);
    int _minus_1 = (_minus - 1);
    return resourceURL.substring((index + 1), _minus_1);
  }
  
  /**
   * @return
   */
  public Date getCurrentDate() {
    long _currentTimeMillis = System.currentTimeMillis();
    return new Date(_currentTimeMillis);
  }
  
  public int getMenuLevel(final MenuItem item, final int lev) {
    int level = lev;
    EObject _eContainer = item.eContainer();
    if ((_eContainer instanceof MenuRoot)) {
      return level;
    } else {
      EObject _eContainer_1 = item.eContainer();
      if ((_eContainer_1 instanceof MenuItem)) {
        EObject _eContainer_2 = item.eContainer();
        MenuItem mitem = ((MenuItem) _eContainer_2);
        int _menuLevel = this.getMenuLevel(mitem, (level + 1));
        level = _menuLevel;
      }
    }
    return level;
  }
  
  /**
   * @return
   */
  public String getDSBuildID() {
    return OfsCore.getVersionNumber();
  }
  
  public String getVar(final HashMap<String, Variable> globalVars, final String str) {
    Variable _get = globalVars.get(str);
    Object _value = _get.getValue();
    return ("" + _value);
  }
}
