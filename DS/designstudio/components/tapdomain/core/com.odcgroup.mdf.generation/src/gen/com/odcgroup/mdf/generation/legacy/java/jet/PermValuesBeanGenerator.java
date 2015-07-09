
package com.odcgroup.mdf.generation.legacy.java.jet;

import java.util.*;
import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.mdf.generation.util.MdfGenerationUtil;
import com.odcgroup.mdf.generation.legacy.java.SourceCodeGenerator;
import com.odcgroup.mdf.metamodel.*;

class PermValuesBeanGenerator extends SourceCodeGenerator {
 
  protected static String nl;
  public static synchronized PermValuesBeanGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    PermValuesBeanGenerator result = new PermValuesBeanGenerator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "/*" + NL + " * This file has been automatically generated, DO NOT MODIFY!" + NL + " * Date: ";
  protected final String TEXT_3 = NL + " * User: ";
  protected final String TEXT_4 = NL + " */" + NL + "" + NL + "" + NL + "package ";
  protected final String TEXT_5 = ";" + NL + "" + NL + "import java.io.Serializable;" + NL + "" + NL + "public class ";
  protected final String TEXT_6 = " implements ";
  protected final String TEXT_7 = ", Serializable {" + NL;
  protected final String TEXT_8 = "\t    " + NL + "" + NL + "\t// Permitted values for ";
  protected final String TEXT_9 = ".";
  protected final String TEXT_10 = NL + NL + "\tprivate ";
  protected final String TEXT_11 = " ";
  protected final String TEXT_12 = ";" + NL + "" + NL + "\t/**" + NL + "\t * Return the permitted values for the ";
  protected final String TEXT_13 = ".";
  protected final String TEXT_14 = " attribute" + NL + "\t * @return the permitted values for attribute ";
  protected final String TEXT_15 = ".";
  protected final String TEXT_16 = NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_17 = " ";
  protected final String TEXT_18 = "() {" + NL + "\t\tif (this.";
  protected final String TEXT_19 = " == null) {" + NL + "\t\t\treturn null;" + NL + "\t\t} else {" + NL + "\t\t\treturn new ";
  protected final String TEXT_20 = "(this.";
  protected final String TEXT_21 = ");" + NL + "\t\t}" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Return the permitted values for the ";
  protected final String TEXT_22 = ".";
  protected final String TEXT_23 = " attribute" + NL + "\t * @return the permitted values for attribute ";
  protected final String TEXT_24 = ".";
  protected final String TEXT_25 = NL + "\t */" + NL + "\tpublic void ";
  protected final String TEXT_26 = "(";
  protected final String TEXT_27 = " ";
  protected final String TEXT_28 = ") {" + NL + "\t\tif (";
  protected final String TEXT_29 = " == null) {" + NL + "\t\t\tthis.";
  protected final String TEXT_30 = " = null;" + NL + "\t\t} else {" + NL + "\t\t\tthis.";
  protected final String TEXT_31 = " = new ";
  protected final String TEXT_32 = "(";
  protected final String TEXT_33 = ");" + NL + "\t\t}" + NL + "" + NL + "\t}" + NL + NL;
  protected final String TEXT_34 = "\t";
  protected final String TEXT_35 = "\t" + NL + "\t" + NL + "}";
  protected final String TEXT_36 = NL;

 	private MdfDataset _model;
	
	private PermValuesBeanGenerator() {
		throw new UnsupportedOperationException();
	}
	
	public PermValuesBeanGenerator(MdfDataset model) {
		this._model = model;
	}

	public String getClassName() {
		return JavaCore.getQualifiedPermValuesBeanClassName(_model);
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
    stringBuffer.append( JavaCore.getPermValuesBeanPackage(_model) );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( JavaCore.getPermValuesBeanClassName(_model) );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( JavaCore.getQualifiedPermValuesClassName(_model) );
    stringBuffer.append(TEXT_7);
    
	Iterator props = _model.getProperties().iterator();
	
	while (props.hasNext()) {
	    MdfDatasetProperty p = (MdfDatasetProperty) props.next();
	    
	    if (JavaCore.hasPermValues(p)) {

    stringBuffer.append(TEXT_8);
    stringBuffer.append( JavaCore.getModelClassName(_model) );
    stringBuffer.append(TEXT_9);
    stringBuffer.append(p.getName());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(MdfGenerationUtil.getPermValuesListType(p));
    stringBuffer.append(TEXT_11);
    stringBuffer.append(JavaCore.getPermValuesAttributeName(p));
    stringBuffer.append(TEXT_12);
    stringBuffer.append( JavaCore.getModelClassName(_model) );
    stringBuffer.append(TEXT_13);
    stringBuffer.append(p.getName());
    stringBuffer.append(TEXT_14);
    stringBuffer.append( JavaCore.getModelClassName(_model) );
    stringBuffer.append(TEXT_15);
    stringBuffer.append(p.getName());
    stringBuffer.append(TEXT_16);
    stringBuffer.append(MdfGenerationUtil.getPermValuesListType(p));
    stringBuffer.append(TEXT_17);
    stringBuffer.append( JavaCore.getPermValuesGetterName(p) );
    stringBuffer.append(TEXT_18);
    stringBuffer.append(JavaCore.getPermValuesAttributeName(p));
    stringBuffer.append(TEXT_19);
    stringBuffer.append(MdfGenerationUtil.getPermValuesConcreteListType(p));
    stringBuffer.append(TEXT_20);
    stringBuffer.append(JavaCore.getPermValuesAttributeName(p));
    stringBuffer.append(TEXT_21);
    stringBuffer.append( JavaCore.getModelClassName(_model) );
    stringBuffer.append(TEXT_22);
    stringBuffer.append(p.getName());
    stringBuffer.append(TEXT_23);
    stringBuffer.append( JavaCore.getModelClassName(_model) );
    stringBuffer.append(TEXT_24);
    stringBuffer.append(p.getName());
    stringBuffer.append(TEXT_25);
    stringBuffer.append( JavaCore.getPermValuesSetterName(p) );
    stringBuffer.append(TEXT_26);
    stringBuffer.append(MdfGenerationUtil.getPermValuesListType(p));
    stringBuffer.append(TEXT_27);
    stringBuffer.append(JavaCore.getPermValuesAttributeName(p));
    stringBuffer.append(TEXT_28);
    stringBuffer.append(JavaCore.getPermValuesAttributeName(p));
    stringBuffer.append(TEXT_29);
    stringBuffer.append(JavaCore.getPermValuesAttributeName(p));
    stringBuffer.append(TEXT_30);
    stringBuffer.append(JavaCore.getPermValuesAttributeName(p));
    stringBuffer.append(TEXT_31);
    stringBuffer.append(MdfGenerationUtil.getPermValuesConcreteListType(p));
    stringBuffer.append(TEXT_32);
    stringBuffer.append(JavaCore.getPermValuesAttributeName(p));
    stringBuffer.append(TEXT_33);
     		}
    stringBuffer.append(TEXT_34);
    	}
    stringBuffer.append(TEXT_35);
    stringBuffer.append(TEXT_36);
    return stringBuffer.toString();
  }
}
