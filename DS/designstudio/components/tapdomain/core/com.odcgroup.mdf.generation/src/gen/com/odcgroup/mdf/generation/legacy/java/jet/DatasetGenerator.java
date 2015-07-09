
package com.odcgroup.mdf.generation.legacy.java.jet;

import java.util.*;
import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.mdf.generation.util.MdfGenerationUtil;
import com.odcgroup.mdf.generation.legacy.java.SourceCodeGenerator;
import com.odcgroup.mdf.metamodel.*;

class DatasetGenerator extends SourceCodeGenerator {
 
  protected static String nl;
  public static synchronized DatasetGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    DatasetGenerator result = new DatasetGenerator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "/*" + NL + " * This file has been automatically generated, DO NOT MODIFY!" + NL + " * Date: ";
  protected final String TEXT_3 = NL + " * User: ";
  protected final String TEXT_4 = NL + " */" + NL + "" + NL + "" + NL + "package ";
  protected final String TEXT_5 = ";" + NL + "" + NL + "/**" + NL + " * @generated" + NL + " */" + NL + "public interface ";
  protected final String TEXT_6 = " " + NL + "\textends com.odcgroup.mdf.core.Dataset {";
  protected final String TEXT_7 = "\t    " + NL + "" + NL + "\t\t/**" + NL + "\t\t * Returns the <em>";
  protected final String TEXT_8 = "</em> property." + NL + "\t\t * @return the <em>";
  protected final String TEXT_9 = "</em> property.";
  protected final String TEXT_10 = NL + "\t\t * @see ";
  protected final String TEXT_11 = NL + "\t\t * @generated" + NL + "\t\t */" + NL + "\t    ";
  protected final String TEXT_12 = " ";
  protected final String TEXT_13 = "();" + NL + "\t    " + NL + "\t\t/**" + NL + "\t\t * Sets the <em>";
  protected final String TEXT_14 = "</em> property." + NL + "\t\t * @param ";
  protected final String TEXT_15 = " the new value of the <em>";
  protected final String TEXT_16 = "</em> property.";
  protected final String TEXT_17 = NL + "\t\t * @see ";
  protected final String TEXT_18 = NL + "\t\t * @generated" + NL + "\t\t */" + NL + "\t    void ";
  protected final String TEXT_19 = "(";
  protected final String TEXT_20 = " ";
  protected final String TEXT_21 = ");";
  protected final String TEXT_22 = "\t" + NL + "}";

 	private MdfDataset _model;
	
	private DatasetGenerator() {
		throw new UnsupportedOperationException();
	}
	
	public DatasetGenerator(MdfDataset model) {
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
    
	Iterator props = _model.getProperties().iterator();
	
	while (props.hasNext()) {
	    MdfDatasetProperty p = (MdfDatasetProperty) props.next();

    stringBuffer.append(TEXT_7);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_8);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_9);
    
	if (p.getType() instanceof MdfEnumeration) { 
    stringBuffer.append(TEXT_10);
    stringBuffer.append( JavaCore.getQualifiedModelClassName(p.getType()) );
    	} 
    stringBuffer.append(TEXT_11);
    stringBuffer.append( MdfGenerationUtil.getTypeName(p) );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( JavaCore.getGetterName(p) );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_14);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_15);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_16);
    
	if (p.getType() instanceof MdfEnumeration) { 
    stringBuffer.append(TEXT_17);
    stringBuffer.append( JavaCore.getQualifiedModelClassName(p.getType()) );
    	} 
    stringBuffer.append(TEXT_18);
    stringBuffer.append( JavaCore.getSetterName(p) );
    stringBuffer.append(TEXT_19);
    stringBuffer.append( MdfGenerationUtil.getTypeName(p) );
    stringBuffer.append(TEXT_20);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_21);
     }
    stringBuffer.append(TEXT_22);
    return stringBuffer.toString();
  }
}
