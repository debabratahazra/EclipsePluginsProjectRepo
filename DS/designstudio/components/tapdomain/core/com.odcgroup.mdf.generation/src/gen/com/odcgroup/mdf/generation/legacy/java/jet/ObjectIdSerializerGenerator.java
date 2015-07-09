
package com.odcgroup.mdf.generation.legacy.java.jet;

import java.util.*;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.mdf.generation.legacy.java.SourceCodeGenerator;
import com.odcgroup.mdf.metamodel.*;

class ObjectIdSerializerGenerator extends SourceCodeGenerator {
 
  protected static String nl;
  public static synchronized ObjectIdSerializerGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    ObjectIdSerializerGenerator result = new ObjectIdSerializerGenerator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "/*" + NL + " * This file has been automatically generated, DO NOT MODIFY!" + NL + " * Date: ";
  protected final String TEXT_3 = NL + " * User: ";
  protected final String TEXT_4 = NL + " */" + NL + "" + NL + "" + NL + "package ";
  protected final String TEXT_5 = ".serializer;" + NL + "" + NL + "import java.io.ObjectInput;" + NL + "import java.io.ObjectOutput;" + NL + "import java.io.IOException;" + NL + "" + NL + "import com.odcgroup.mdf.core.*;" + NL + "import com.odcgroup.mdf.serializer.*;" + NL + "" + NL + "/**" + NL + " * Object id for the ";
  protected final String TEXT_6 = " class." + NL + " * @see ";
  protected final String TEXT_7 = NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_8 = "Serializer implements Serializer {" + NL + "\t public static final long CLASSID = ";
  protected final String TEXT_9 = "L;" + NL + "\t " + NL + "\t " + NL + "\t public void writeObject(Object obj, ObjectOutput out) throws IOException {" + NL + "\t \twrite((";
  protected final String TEXT_10 = ")obj , out);" + NL + "\t }" + NL + "" + NL + "\t public Object readObject(ObjectInput in) throws IOException {" + NL + "\t \treturn read(in);" + NL + "\t }" + NL + "" + NL + "    /*" + NL + "     * serialization" + NL + "     */ " + NL + "\tstatic public void write(";
  protected final String TEXT_11 = " obj, ObjectOutput out) throws IOException {";
  protected final String TEXT_12 = NL + "\t    BasicObjectSerializer.write";
  protected final String TEXT_13 = "(" + NL + "\t    ";
  protected final String TEXT_14 = NL + "\t\t\t\t(";
  protected final String TEXT_15 = ")obj.getPrimaryKeyValue(), out);" + NL + "\t    ";
  protected final String TEXT_16 = NL + "\t    \t\t\t((";
  protected final String TEXT_17 = ")obj.getPrimaryKeyValue()).";
  protected final String TEXT_18 = "Value(), out);";
  protected final String TEXT_19 = NL + "\t}" + NL + "" + NL + "" + NL + "\tstatic ";
  protected final String TEXT_20 = " read(ObjectInput in)  throws IOException {";
  protected final String TEXT_21 = NL + "\t     return ";
  protected final String TEXT_22 = ".create";
  protected final String TEXT_23 = "(BasicObjectSerializer.read";
  protected final String TEXT_24 = "(in));";
  protected final String TEXT_25 = NL + NL + "\t}" + NL + "\t" + NL + "}";

 	private MdfClass _model;
	
	private ObjectIdSerializerGenerator() {
		throw new UnsupportedOperationException();
	}
	
	public ObjectIdSerializerGenerator(MdfClass model) {
		this._model = model;
	}

	public String getClassName() {
		return JavaCore.getModelPackage(_model)+".serializer."+JavaCore.getRefClassName(_model)+"Serializer";
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
    stringBuffer.append( _model.getName() );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( JavaCore.getQualifiedRefClassName(_model) );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_8);
    stringBuffer.append(JavaCore.generateRefSUID(_model));
    stringBuffer.append(TEXT_9);
    stringBuffer.append( JavaCore.getQualifiedRefClassName(_model) );
    stringBuffer.append(TEXT_10);
    stringBuffer.append(JavaCore.getQualifiedRefClassName(_model));
    stringBuffer.append(TEXT_11);
    		Iterator keys = _model.getPrimaryKeys(true).iterator();
        if (keys.hasNext()) {
            MdfProperty p = (MdfProperty) keys.next();
            MdfEntity type = p.getType();
            
            Class javaClass = PrimitivesDomain.getJavaType(type); 
    stringBuffer.append(TEXT_12);
    stringBuffer.append(JavaCore.getLastWord(javaClass.getName()));
    stringBuffer.append(TEXT_13);
     if (!javaClass.isPrimitive()){
    stringBuffer.append(TEXT_14);
    stringBuffer.append(javaClass.getName());
    stringBuffer.append(TEXT_15);
    } else {
    stringBuffer.append(TEXT_16);
    stringBuffer.append(PrimitivesDomain.getJavaType(type).getName());
    stringBuffer.append(TEXT_17);
    stringBuffer.append(javaClass.getName());
    stringBuffer.append(TEXT_18);
    	      }
	}
    stringBuffer.append(TEXT_19);
    stringBuffer.append( JavaCore.getQualifiedRefClassName(_model));
    stringBuffer.append(TEXT_20);
    		keys = _model.getPrimaryKeys(true).iterator();
        if (keys.hasNext()) {
            MdfProperty p = (MdfProperty) keys.next();
            MdfEntity type = p.getType();
            
            Class javaClass = PrimitivesDomain.getJavaType(type); 
    stringBuffer.append(TEXT_21);
    stringBuffer.append( JavaCore.getQualifiedRefClassName(_model));
    stringBuffer.append(TEXT_22);
    stringBuffer.append(JavaCore.getRefClassName(_model));
    stringBuffer.append(TEXT_23);
    stringBuffer.append(JavaCore.getLastWord(javaClass.getName()));
    stringBuffer.append(TEXT_24);
    	 } 
    stringBuffer.append(TEXT_25);
    return stringBuffer.toString();
  }
}
