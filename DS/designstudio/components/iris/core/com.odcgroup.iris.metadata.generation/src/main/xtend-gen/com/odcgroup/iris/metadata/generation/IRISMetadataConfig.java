package com.odcgroup.iris.metadata.generation;

import com.odcgroup.iris.generator.IIRISGenerator;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMEntityModel;
import com.odcgroup.t24.enquiry.util.EMProperty;
import com.odcgroup.t24.enquiry.util.EMTerm;
import java.util.HashMap;
import java.util.List;
import org.eclipse.xtend.expression.Variable;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess;

@SuppressWarnings("all")
public class IRISMetadataConfig implements IIRISGenerator {
  public void doGenerate(final EMEntityModel entityModel, final IFileSystemAccess fsa, final HashMap<String, Variable> globalVars) {
    Variable _get = globalVars.get("fileName");
    Object _value = _get.getValue();
    String filename = _value.toString();
    CharSequence _renderMetadata = this.renderMetadata(entityModel, globalVars);
    fsa.generateFile(filename, _renderMetadata);
  }
  
  public CharSequence renderMetadata(final EMEntityModel entityModel, final HashMap<String, Variable> globalVars) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    _builder.newLine();
    _builder.append("<Metadata ModelName=\"");
    String _name = entityModel.getName();
    _builder.append(_name, "");
    _builder.append("\" Version=\"1.0\"");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("xmlns=\"http://iris.temenos.com/metadata.xsd\"");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("xsi:schemaLocation=\"http://iris.temenos.com metadata.xsd\">");
    _builder.newLine();
    {
      List<EMEntity> _entities = entityModel.getEntities();
      for(final EMEntity entity : _entities) {
        _builder.append("\t");
        CharSequence _renderEntity = this.renderEntity(entity);
        _builder.append(_renderEntity, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("</Metadata>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence renderEntity(final EMEntity entity) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<Entity Name=\"");
    String _name = entity.getName();
    _builder.append(_name, "");
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    {
      List<EMTerm> _vocabularyTerms = entity.getVocabularyTerms();
      int _size = _vocabularyTerms.size();
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        _builder.append("\t");
        CharSequence _renderTerm = this.renderTerm(entity);
        _builder.append(_renderTerm, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      List<EMProperty> _properties = entity.getProperties();
      for(final EMProperty property : _properties) {
        _builder.append("\t");
        CharSequence _renderProperty = this.renderProperty(property);
        _builder.append(_renderProperty, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("</Entity>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence renderProperty(final EMProperty property) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _or = false;
      List<EMTerm> _vocabularyTerms = property.getVocabularyTerms();
      int _size = _vocabularyTerms.size();
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        _or = true;
      } else {
        List<EMProperty> _subProperties = property.getSubProperties();
        int _size_1 = _subProperties.size();
        boolean _greaterThan_1 = (_size_1 > 0);
        _or = _greaterThan_1;
      }
      if (_or) {
        _builder.append("<Property Name=\"");
        String _name = property.getName();
        _builder.append(_name, "");
        _builder.append("\">");
        _builder.newLineIfNotEmpty();
        {
          List<EMTerm> _vocabularyTerms_1 = property.getVocabularyTerms();
          int _size_2 = _vocabularyTerms_1.size();
          boolean _greaterThan_2 = (_size_2 > 0);
          if (_greaterThan_2) {
            _builder.append("\t");
            CharSequence _renderTerm = this.renderTerm(property);
            _builder.append(_renderTerm, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          List<EMProperty> _subProperties_1 = property.getSubProperties();
          int _size_3 = _subProperties_1.size();
          boolean _greaterThan_3 = (_size_3 > 0);
          if (_greaterThan_3) {
            _builder.append("\t");
            CharSequence _renderComplexProperty = this.renderComplexProperty(property);
            _builder.append(_renderComplexProperty, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("</Property>");
        _builder.newLine();
      } else {
        _builder.append("<Property Name=\"");
        String _name_1 = property.getName();
        _builder.append(_name_1, "");
        _builder.append("\" />");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence renderComplexProperty(final EMProperty property) {
    StringConcatenation _builder = new StringConcatenation();
    {
      List<EMProperty> _subProperties = property.getSubProperties();
      for(final EMProperty prop : _subProperties) {
        CharSequence _renderProperty = this.renderProperty(prop);
        _builder.append(_renderProperty, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence renderTerm(final EMProperty property) {
    StringConcatenation _builder = new StringConcatenation();
    {
      List<EMTerm> _vocabularyTerms = property.getVocabularyTerms();
      for(final EMTerm term : _vocabularyTerms) {
        _builder.append("<Term Name=\"");
        String _name = term.getName();
        _builder.append(_name, "");
        _builder.append("\">");
        String _value = term.getValue();
        _builder.append(_value, "");
        _builder.append("</Term>");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence renderTerm(final EMEntity entity) {
    StringConcatenation _builder = new StringConcatenation();
    {
      List<EMTerm> _vocabularyTerms = entity.getVocabularyTerms();
      for(final EMTerm term : _vocabularyTerms) {
        _builder.append("<Term Name=\"");
        String _name = term.getName();
        _builder.append(_name, "");
        _builder.append("\">");
        String _value = term.getValue();
        _builder.append(_value, "");
        _builder.append("</Term>");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
}
