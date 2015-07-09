
package com.odcgroup.mdf.generation.legacy.java.jet;

import java.util.*;
import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.mdf.generation.util.MdfGenerationUtil;
import com.odcgroup.mdf.generation.legacy.java.SourceCodeGenerator;
import com.odcgroup.mdf.metamodel.*;

class PermValuesGenerator extends SourceCodeGenerator {
 
  protected static String nl;
  public static synchronized PermValuesGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    PermValuesGenerator result = new PermValuesGenerator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "/*" + NL + " * This file has been automatically generated, DO NOT MODIFY!" + NL + " * Date: ";
  protected final String TEXT_3 = NL + " * User: ";
  protected final String TEXT_4 = NL + " */" + NL + "" + NL + "" + NL + "package ";
  protected final String TEXT_5 = ";" + NL + "" + NL + "/**" + NL + " * Constraints on admitted values displayed to the user." + NL + " * If null for an enumeration, means all enum values" + NL + " * permitted (non filtered) " + NL + " * " + NL + " * @generated" + NL + " */" + NL + "public interface ";
  protected final String TEXT_6 = " {" + NL;
  protected final String TEXT_7 = "\t    " + NL + "" + NL + "\t// Permitted values for ";
  protected final String TEXT_8 = ".";
  protected final String TEXT_9 = NL + NL + "\t/**" + NL + "\t * Return the permitted values for the ";
  protected final String TEXT_10 = ".";
  protected final String TEXT_11 = " attribute" + NL + "\t * @return the permitted values for attribute ";
  protected final String TEXT_12 = ".";
  protected final String TEXT_13 = NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_14 = " ";
  protected final String TEXT_15 = "();" + NL;
  protected final String TEXT_16 = "\t";
  protected final String TEXT_17 = "\t" + NL + "" + NL + "}";
  protected final String TEXT_18 = NL;

 	private MdfDataset _model;
	
	private PermValuesGenerator() {
		throw new UnsupportedOperationException();
	}
	
	public PermValuesGenerator(MdfDataset model) {
		this._model = model;
	}

	public String getClassName() {
		return JavaCore.getQualifiedPermValuesClassName(_model);
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
    stringBuffer.append( JavaCore.getPermValuesPackage(_model) );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( JavaCore.getPermValuesClassName(_model) );
    stringBuffer.append(TEXT_6);
    
	Iterator props = _model.getProperties().iterator();
	
	while (props.hasNext()) {
	    MdfDatasetProperty p = (MdfDatasetProperty) props.next();
	    
	    if (JavaCore.hasPermValues(p)) {

    stringBuffer.append(TEXT_7);
    stringBuffer.append( JavaCore.getModelClassName(_model) );
    stringBuffer.append(TEXT_8);
    stringBuffer.append(p.getName());
    stringBuffer.append(TEXT_9);
    stringBuffer.append( JavaCore.getModelClassName(_model) );
    stringBuffer.append(TEXT_10);
    stringBuffer.append(p.getName());
    stringBuffer.append(TEXT_11);
    stringBuffer.append( JavaCore.getModelClassName(_model) );
    stringBuffer.append(TEXT_12);
    stringBuffer.append(p.getName());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(MdfGenerationUtil.getPermValuesListType(p));
    stringBuffer.append(TEXT_14);
    stringBuffer.append( JavaCore.getPermValuesGetterName(p) );
    stringBuffer.append(TEXT_15);
     		}
    stringBuffer.append(TEXT_16);
    	}
    stringBuffer.append(TEXT_17);
    stringBuffer.append(TEXT_18);
    return stringBuffer.toString();
  }
}
