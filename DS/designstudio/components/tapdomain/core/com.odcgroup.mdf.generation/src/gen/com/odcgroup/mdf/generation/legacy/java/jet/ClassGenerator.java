
package com.odcgroup.mdf.generation.legacy.java.jet;

import java.util.*;
import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.mdf.generation.util.MdfGenerationUtil;
import com.odcgroup.mdf.generation.legacy.java.SourceCodeGenerator;
import com.odcgroup.mdf.metamodel.*;

class ClassGenerator extends SourceCodeGenerator {
 
  protected static String nl;
  public static synchronized ClassGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    ClassGenerator result = new ClassGenerator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "/*" + NL + " * This file has been automatically generated, DO NOT MODIFY!" + NL + " * Date: ";
  protected final String TEXT_3 = NL + " * User: ";
  protected final String TEXT_4 = NL + " */" + NL + "" + NL + "" + NL + "package ";
  protected final String TEXT_5 = ";" + NL + "" + NL + "/**" + NL + " * @generated" + NL + " */" + NL + "public interface ";
  protected final String TEXT_6 = " " + NL + "\textends ";
  protected final String TEXT_7 = NL + "\t\t";
  protected final String TEXT_8 = NL + "\t, com.odcgroup.mdf.core.ReferenceableDomainObject";
  protected final String TEXT_9 = NL + "{";
  protected final String TEXT_10 = " " + NL + "\t    " + NL + "\t\t/**" + NL + "\t\t * Returns the <em>";
  protected final String TEXT_11 = "</em> property." + NL + "\t\t * @return the <em>";
  protected final String TEXT_12 = "</em> property." + NL + "\t\t * ";
  protected final String TEXT_13 = NL + "\t\t * @generated" + NL + "         * ";
  protected final String TEXT_14 = NL + "\t\t */" + NL + "\t    ";
  protected final String TEXT_15 = " ";
  protected final String TEXT_16 = "();" + NL + "\t    " + NL + "\t\t/**" + NL + "\t\t * Sets the <em>";
  protected final String TEXT_17 = "</em> property." + NL + "\t\t * @param ";
  protected final String TEXT_18 = " the new value of the <em>";
  protected final String TEXT_19 = "</em> property." + NL + "\t\t * ";
  protected final String TEXT_20 = NL + "\t\t * @generated" + NL + "         * ";
  protected final String TEXT_21 = NL + "\t\t */" + NL + "\t    void ";
  protected final String TEXT_22 = "(";
  protected final String TEXT_23 = " ";
  protected final String TEXT_24 = ");";
  protected final String TEXT_25 = "\t" + NL + "}";

 	private MdfClass _model;
	
	private ClassGenerator() {
		throw new UnsupportedOperationException();
	}
	
	public ClassGenerator(MdfClass model) {
		this._model = model;
	}

	public String getClassName() {
		return JavaCore.getQualifiedModelClassName(_model);
	}

	public String generate()
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    stringBuffer.append( new java.util.Date() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( System.getProperty("user.name") );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( JavaCore.getModelPackage(_model) );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( JavaCore.getModelClassName(_model) );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( (_model.getBaseClass() != null) ? JavaCore.getQualifiedModelClassName(_model.getBaseClass()) :
		"com.odcgroup.mdf.core.DomainObject" );
    stringBuffer.append(TEXT_7);
     if (_model.hasPrimaryKey()) { 
    stringBuffer.append(TEXT_8);
     } 
    stringBuffer.append(TEXT_9);
    
	Iterator props = _model.getProperties().iterator();
	
	while (props.hasNext()) {
	    MdfProperty p = (MdfProperty) props.next();
	    
	    if (p instanceof MdfReverseAssociation) {
	    	// We dont implement reverse association in the interface
	    	continue;	
	    }
    stringBuffer.append(TEXT_10);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( (p.getType() instanceof MdfEnumeration) ? "@see " + JavaCore.getQualifiedModelClassName(p.getType()) : "" );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( (p.isPrimaryKey()) ? "@primaryKey" : "" );
    stringBuffer.append(TEXT_14);
    stringBuffer.append( MdfGenerationUtil.getTypeName(p) );
    stringBuffer.append(TEXT_15);
    stringBuffer.append( JavaCore.getGetterName(p) );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_19);
    stringBuffer.append( (p.getType() instanceof MdfEnumeration) ? "@see " + JavaCore.getQualifiedModelClassName(p.getType()) : "" );
    stringBuffer.append(TEXT_20);
    stringBuffer.append( (p.isPrimaryKey()) ? "@primaryKey" : "" );
    stringBuffer.append(TEXT_21);
    stringBuffer.append( JavaCore.getSetterName(p) );
    stringBuffer.append(TEXT_22);
    stringBuffer.append( MdfGenerationUtil.getTypeName(p) );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_24);
     }
    stringBuffer.append(TEXT_25);
    return stringBuffer.toString();
  }
}
