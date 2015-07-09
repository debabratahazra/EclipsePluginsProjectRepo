
package com.odcgroup.mdf.generation.legacy.java.jet;

import java.util.*;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.mdf.generation.util.MdfGenerationUtil;
import com.odcgroup.mdf.generation.legacy.java.SourceCodeGenerator;
import com.odcgroup.mdf.metamodel.*;

class EnumerationGenerator extends SourceCodeGenerator {
 
  protected static String nl;
  public static synchronized EnumerationGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    EnumerationGenerator result = new EnumerationGenerator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "/*" + NL + " * This file has been automatically generated, DO NOT MODIFY!" + NL + " * Date: ";
  protected final String TEXT_3 = NL + " * User: ";
  protected final String TEXT_4 = NL + " */" + NL + "" + NL + "" + NL + "package ";
  protected final String TEXT_5 = ";" + NL + "" + NL + "/**" + NL + " * @generated" + NL + " */" + NL + "public final class ";
  protected final String TEXT_6 = " {" + NL + "" + NL + "\t///CLOVER:OFF" + NL + "\tprivate ";
  protected final String TEXT_7 = "() {" + NL + "\t};" + NL + "\t///CLOVER:ON" + NL + "\t" + NL + "\t";
  protected final String TEXT_8 = NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "     public static final ";
  protected final String TEXT_9 = " ";
  protected final String TEXT_10 = ";" + NL + "     ";
  protected final String TEXT_11 = NL + "     " + NL + "     static {" + NL + "\t\ttry {";
  protected final String TEXT_12 = NL + "\t\t\t";
  protected final String TEXT_13 = " = ";
  protected final String TEXT_14 = ";\t\t";
  protected final String TEXT_15 = NL + "\t\t} catch (Exception e) {" + NL + "            throw (IllegalArgumentException) new IllegalArgumentException(e.getMessage()).initCause(e);" + NL + "\t\t}\t" + NL + "     }\t" + NL + "}";

 	private MdfEnumeration _model;
	
	private EnumerationGenerator() {
		throw new UnsupportedOperationException();
	}
	
	public EnumerationGenerator(MdfEnumeration e) {
		this._model = e;
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
    stringBuffer.append( JavaCore.getModelClassName(_model) );
    stringBuffer.append(TEXT_7);
    
		Class javaClass = PrimitivesDomain.getJavaType(_model.getType());
		if (javaClass == null) {
			throw new RuntimeException("Invalid Property Type (" + _model.getType() + ") for " + _model);
		}
        
		Iterator it = _model.getValues().iterator();
        while (it.hasNext()) {
            MdfEnumValue nv = (MdfEnumValue) it.next();
    stringBuffer.append(TEXT_8);
    stringBuffer.append( javaClass.getName() );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( nv.getName());
    stringBuffer.append(TEXT_10);
     }
    stringBuffer.append(TEXT_11);
    
			Iterator props = _model.getValues().iterator();
			
			while (props.hasNext()) {
				MdfEnumValue ev = (MdfEnumValue) props.next();				
				String initCode = MdfGenerationUtil.getEnumValueInitCode(ev);
				
				if (initCode != null) {
    stringBuffer.append(TEXT_12);
    stringBuffer.append( ev.getName() );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( initCode );
    stringBuffer.append(TEXT_14);
    				} 
			} 
    stringBuffer.append(TEXT_15);
    return stringBuffer.toString();
  }
}
