package com.odcgroup.iris.t24mappings.generation;

import com.odcgroup.iris.generator.IIRISGenerator;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMEntityModel;
import com.odcgroup.t24.enquiry.util.EMProperty;
import java.util.HashMap;
import java.util.List;
import org.eclipse.xtend.expression.Variable;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class IRIST24MappingConfig implements IIRISGenerator {
  private String projectName = "";
  
  public void doGenerate(final EMEntityModel entityModel, final IFileSystemAccess fsa, final HashMap<String,Variable> globalVars) {
    Variable _get = globalVars.get("fileName");
    Object _value = _get.getValue();
    String filename = _value.toString();
    String _name = entityModel.getName();
    this.projectName = _name;
    CharSequence _renderT24Properties = this.renderT24Properties(entityModel, globalVars);
    fsa.generateFile(filename, _renderT24Properties);
  }
  
  public CharSequence renderT24Properties(final EMEntityModel entityModel, final HashMap<String,Variable> globalVars) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("# ----------------------------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("# T24 settings");
    _builder.newLine();
    _builder.append("#");
    _builder.newLine();
    _builder.append("# This file contains basic T24 descriptions such as mapping to");
    _builder.newLine();
    _builder.append("# T24 entities and other field attributes specific to T24.");
    _builder.newLine();
    _builder.append("# ----------------------------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# NO T24 credentials for deployments with disabled web authentication");
    _builder.newLine();
    _builder.append("# NOT t24Credentials = username: INPUTT|password: 123456|company:");
    _builder.newLine();
    _builder.append("# Having env. specific information in a gen. T24.properties does not make sense from a DS perspective.");
    _builder.newLine();
    _builder.append("# IRIS will be changed to just read this from another completely separate (not generated) connection properties file");
    _builder.newLine();
    _builder.append("t24Credentials = username: INPUTT|password: 123456|company:");
    _builder.newLine();
    _builder.newLine();
    CharSequence _renderEntities = this.renderEntities(entityModel);
    _builder.append(_renderEntities, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence renderEntities(final EMEntityModel entityModel) {
    StringConcatenation _builder = new StringConcatenation();
    {
      List<EMEntity> _entities = entityModel.getEntities();
      for(final EMEntity entity : _entities) {
        {
          String _t24Name = entity.getT24Name();
          boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(_t24Name);
          boolean _not = (!_isNullOrEmpty);
          if (_not) {
            CharSequence _renderEntity = this.renderEntity(entity);
            _builder.append(_renderEntity, "");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence renderEntity(final EMEntity entity) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("# ----------------------------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("# Resource : ");
    String _t24Name = entity.getT24Name();
    _builder.append(_t24Name, "");
    _builder.newLineIfNotEmpty();
    _builder.append("# ----------------------------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append(this.projectName, "");
    _builder.append(".");
    String _name = entity.getName();
    _builder.append(_name, "");
    _builder.append(" = name: ");
    String _t24Name_1 = entity.getT24Name();
    _builder.append(_t24Name_1, "");
    _builder.newLineIfNotEmpty();
    {
      List<EMProperty> _properties = entity.getProperties();
      for(final EMProperty property : _properties) {
        CharSequence _renderProperty = this.renderProperty(property, entity);
        _builder.append(_renderProperty, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence renderProperty(final EMProperty property, final EMEntity entity) {
    StringConcatenation _builder = new StringConcatenation();
    {
      String _t24Name = property.getT24Name();
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(_t24Name);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        {
          boolean _isAAPropertyClass = property.isAAPropertyClass();
          if (_isAAPropertyClass) {
            _builder.append(this.projectName, "");
            _builder.append(".");
            String _name = entity.getName();
            _builder.append(_name, "");
            _builder.append(".");
            String _name_1 = entity.getName();
            _builder.append(_name_1, "");
            _builder.append("_");
            String _name_2 = property.getName();
            _builder.append(_name_2, "");
            _builder.append(" = name: ");
            String _t24Name_1 = property.getT24Name();
            _builder.append(_t24Name_1, "");
            _builder.append("|entity: ");
            String _t24Name_2 = entity.getT24Name();
            _builder.append(_t24Name_2, "");
            CharSequence _renderSelectionTerms = this.renderSelectionTerms(property);
            _builder.append(_renderSelectionTerms, "");
            CharSequence _renderJoinedTo = this.renderJoinedTo(property);
            _builder.append(_renderJoinedTo, "");
            CharSequence _renderPropertyGroupTo = this.renderPropertyGroupTo(property);
            _builder.append(_renderPropertyGroupTo, "");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append(this.projectName, "");
            _builder.append(".");
            String _name_3 = entity.getName();
            _builder.append(_name_3, "");
            _builder.append(".");
            String _name_4 = property.getName();
            _builder.append(_name_4, "");
            _builder.append(" = name: ");
            String _t24Name_3 = property.getT24Name();
            _builder.append(_t24Name_3, "");
            _builder.append("|entity: ");
            String _t24Name_4 = entity.getT24Name();
            _builder.append(_t24Name_4, "");
            CharSequence _renderSelectionTerms_1 = this.renderSelectionTerms(property);
            _builder.append(_renderSelectionTerms_1, "");
            CharSequence _renderJoinedTo_1 = this.renderJoinedTo(property);
            _builder.append(_renderJoinedTo_1, "");
            CharSequence _renderPropertyGroupTo_1 = this.renderPropertyGroupTo(property);
            _builder.append(_renderPropertyGroupTo_1, "");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      List<EMProperty> _subProperties = property.getSubProperties();
      for(final EMProperty sproperty : _subProperties) {
        {
          String _t24Name_5 = sproperty.getT24Name();
          boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(_t24Name_5);
          boolean _not_1 = (!_isNullOrEmpty_1);
          if (_not_1) {
            _builder.append(this.projectName, "");
            _builder.append(".");
            String _name_5 = entity.getName();
            _builder.append(_name_5, "");
            _builder.append(".");
            String _name_6 = entity.getName();
            _builder.append(_name_6, "");
            _builder.append("_");
            String _name_7 = property.getName();
            _builder.append(_name_7, "");
            _builder.append(".");
            String _name_8 = sproperty.getName();
            _builder.append(_name_8, "");
            _builder.append(" = name: ");
            String _t24Name_6 = sproperty.getT24Name();
            _builder.append(_t24Name_6, "");
            _builder.append("|entity: ");
            String _t24Name_7 = entity.getT24Name();
            _builder.append(_t24Name_7, "");
            CharSequence _renderSelectionTerms_2 = this.renderSelectionTerms(sproperty);
            _builder.append(_renderSelectionTerms_2, "");
            CharSequence _renderJoinedTo_2 = this.renderJoinedTo(sproperty);
            _builder.append(_renderJoinedTo_2, "");
            CharSequence _renderPropertyGroupTo_2 = this.renderPropertyGroupTo(sproperty);
            _builder.append(_renderPropertyGroupTo_2, "");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          List<EMProperty> _subProperties_1 = sproperty.getSubProperties();
          for(final EMProperty sprop : _subProperties_1) {
            {
              String _t24Name_8 = sprop.getT24Name();
              boolean _isNullOrEmpty_2 = StringExtensions.isNullOrEmpty(_t24Name_8);
              boolean _not_2 = (!_isNullOrEmpty_2);
              if (_not_2) {
                _builder.append(this.projectName, "");
                _builder.append(".");
                String _name_9 = entity.getName();
                _builder.append(_name_9, "");
                _builder.append(".");
                String _name_10 = entity.getName();
                _builder.append(_name_10, "");
                _builder.append("_");
                String _name_11 = property.getName();
                _builder.append(_name_11, "");
                _builder.append(".");
                String _name_12 = entity.getName();
                _builder.append(_name_12, "");
                _builder.append("_");
                String _name_13 = sproperty.getName();
                _builder.append(_name_13, "");
                _builder.append(".");
                String _name_14 = sprop.getName();
                _builder.append(_name_14, "");
                _builder.append(" = name: ");
                String _t24Name_9 = sprop.getT24Name();
                _builder.append(_t24Name_9, "");
                _builder.append("|entity: ");
                String _t24Name_10 = entity.getT24Name();
                _builder.append(_t24Name_10, "");
                CharSequence _renderSelectionTerms_3 = this.renderSelectionTerms(sprop);
                _builder.append(_renderSelectionTerms_3, "");
                CharSequence _renderJoinedTo_3 = this.renderJoinedTo(sprop);
                _builder.append(_renderJoinedTo_3, "");
                CharSequence _renderPropertyGroupTo_3 = this.renderPropertyGroupTo(sprop);
                _builder.append(_renderPropertyGroupTo_3, "");
                _builder.newLineIfNotEmpty();
              }
            }
            {
              boolean _isAAPropertyClass_1 = property.isAAPropertyClass();
              if (_isAAPropertyClass_1) {
                {
                  List<EMProperty> _subProperties_2 = sprop.getSubProperties();
                  for(final EMProperty aaSubprop : _subProperties_2) {
                    {
                      String _t24Name_11 = aaSubprop.getT24Name();
                      boolean _isNullOrEmpty_3 = StringExtensions.isNullOrEmpty(_t24Name_11);
                      boolean _not_3 = (!_isNullOrEmpty_3);
                      if (_not_3) {
                        _builder.append(this.projectName, "");
                        _builder.append(".");
                        String _name_15 = entity.getName();
                        _builder.append(_name_15, "");
                        _builder.append(".");
                        String _name_16 = entity.getName();
                        _builder.append(_name_16, "");
                        _builder.append("_");
                        String _name_17 = property.getName();
                        _builder.append(_name_17, "");
                        _builder.append(".");
                        String _name_18 = entity.getName();
                        _builder.append(_name_18, "");
                        _builder.append("_");
                        String _name_19 = sproperty.getName();
                        _builder.append(_name_19, "");
                        _builder.append(".");
                        String _name_20 = entity.getName();
                        _builder.append(_name_20, "");
                        _builder.append("_");
                        String _name_21 = sprop.getName();
                        _builder.append(_name_21, "");
                        _builder.append(".");
                        String _name_22 = aaSubprop.getName();
                        _builder.append(_name_22, "");
                        _builder.append(" = name: ");
                        String _t24Name_12 = aaSubprop.getT24Name();
                        _builder.append(_t24Name_12, "");
                        _builder.append("|entity: ");
                        String _t24Name_13 = entity.getT24Name();
                        _builder.append(_t24Name_13, "");
                        CharSequence _renderSelectionTerms_4 = this.renderSelectionTerms(aaSubprop);
                        _builder.append(_renderSelectionTerms_4, "");
                        CharSequence _renderJoinedTo_4 = this.renderJoinedTo(aaSubprop);
                        _builder.append(_renderJoinedTo_4, "");
                        CharSequence _renderPropertyGroupTo_4 = this.renderPropertyGroupTo(aaSubprop);
                        _builder.append(_renderPropertyGroupTo_4, "");
                        _builder.newLineIfNotEmpty();
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
    return _builder;
  }
  
  public CharSequence renderSelectionTerms(final EMProperty property) {
    StringConcatenation _builder = new StringConcatenation();
    {
      String _selectionInfo = property.getSelectionInfo();
      boolean _isEmpty = _selectionInfo.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("|");
        String _selectionInfo_1 = property.getSelectionInfo();
        _builder.append(_selectionInfo_1, "");
      }
    }
    return _builder;
  }
  
  public CharSequence renderJoinedTo(final EMProperty property) {
    StringConcatenation _builder = new StringConcatenation();
    {
      String _joinedTo = property.getJoinedTo();
      boolean _isEmpty = _joinedTo.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("|");
        String _joinedTo_1 = property.getJoinedTo();
        _builder.append(_joinedTo_1, "");
      }
    }
    return _builder;
  }
  
  public CharSequence renderPropertyGroupTo(final EMProperty property) {
    StringConcatenation _builder = new StringConcatenation();
    {
      String _propertyGroup = property.getPropertyGroup();
      boolean _isEmpty = _propertyGroup.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("|");
        String _propertyGroup_1 = property.getPropertyGroup();
        _builder.append(_propertyGroup_1, "");
      }
    }
    return _builder;
  }
}
