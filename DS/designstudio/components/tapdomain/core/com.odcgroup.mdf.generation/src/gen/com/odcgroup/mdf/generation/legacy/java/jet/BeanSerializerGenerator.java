
package com.odcgroup.mdf.generation.legacy.java.jet;

import java.util.*;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.mdf.generation.legacy.java.SourceCodeGenerator;
import com.odcgroup.mdf.metamodel.*;

class BeanSerializerGenerator extends SourceCodeGenerator {
 
  protected static String nl;
  public static synchronized BeanSerializerGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    BeanSerializerGenerator result = new BeanSerializerGenerator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "/*" + NL + " * This file has been automatically generated, DO NOT MODIFY!" + NL + " * Date: ";
  protected final String TEXT_3 = NL + " * User: ";
  protected final String TEXT_4 = NL + " */" + NL + "" + NL + "" + NL + "package ";
  protected final String TEXT_5 = ".serializer;" + NL + "" + NL + "import java.io.ObjectInput;" + NL + "import java.io.ObjectOutput;" + NL + "import java.io.IOException;" + NL + "" + NL + "/*" + NL + "import ";
  protected final String TEXT_6 = ".*;" + NL + "*/" + NL + "import com.odcgroup.mdf.core.*;" + NL + "import com.odcgroup.mdf.serializer.*;" + NL + "" + NL + "" + NL + "/**" + NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_7 = "Serializer";
  protected final String TEXT_8 = " implements Serializer";
  protected final String TEXT_9 = " {" + NL + "\t public static final long CLASSID = ";
  protected final String TEXT_10 = "L;" + NL;
  protected final String TEXT_11 = "\t " + NL + "\t public void writeObject(Object obj, ObjectOutput out) throws IOException {" + NL + "\t \twrite((";
  protected final String TEXT_12 = ")obj , out);" + NL + "\t }" + NL + "" + NL + "\t public Object readObject(ObjectInput in) throws IOException {" + NL + "\t \t";
  protected final String TEXT_13 = " obj = new ";
  protected final String TEXT_14 = "();" + NL + "\t\t" + NL + "\t\tread(obj,in);" + NL + "" + NL + "\t\treturn obj;" + NL + "\t }";
  protected final String TEXT_15 = NL + "    /*" + NL + "     * serialization" + NL + "     */ " + NL + "\tstatic public void write(";
  protected final String TEXT_16 = " obj, ObjectOutput out) throws IOException {" + NL;
  protected final String TEXT_17 = NL + "\t\t";
  protected final String TEXT_18 = ".write(obj, out);";
  protected final String TEXT_19 = NL + "\t";
  protected final String TEXT_20 = NL + "        MdfSerializer.writeList((obj.";
  protected final String TEXT_21 = "().size() == 0 ? null : obj.";
  protected final String TEXT_22 = "()) , out);";
  protected final String TEXT_23 = NL + "\t\tMdfSerializer.write(obj.";
  protected final String TEXT_24 = "(), out);";
  protected final String TEXT_25 = NL + "\t\tBasicObjectSerializer.write";
  protected final String TEXT_26 = "(obj.";
  protected final String TEXT_27 = "(), out);";
  protected final String TEXT_28 = NL + "\t}" + NL + "\t\t" + NL + "\t\t" + NL + "\t/*" + NL + "     * serialization" + NL + "     */ " + NL + "\t" + NL + "\tstatic public ";
  protected final String TEXT_29 = " read(";
  protected final String TEXT_30 = " obj, ObjectInput in) throws IOException {";
  protected final String TEXT_31 = NL + "\t\t";
  protected final String TEXT_32 = ".read(obj, in);";
  protected final String TEXT_33 = NL + "\t";
  protected final String TEXT_34 = NL + "\t  \tobj.";
  protected final String TEXT_35 = "(MdfSerializer.readList(in));";
  protected final String TEXT_36 = NL + "\t\tobj.";
  protected final String TEXT_37 = "((";
  protected final String TEXT_38 = ")MdfSerializer.read(in));";
  protected final String TEXT_39 = NL + "\t\tobj.";
  protected final String TEXT_40 = "((";
  protected final String TEXT_41 = ")MdfSerializer.read(in));";
  protected final String TEXT_42 = NL + "\t\tobj.";
  protected final String TEXT_43 = "(BasicObjectSerializer.read";
  protected final String TEXT_44 = "(in));";
  protected final String TEXT_45 = NL + "\t\treturn obj;" + NL + "\t}" + NL + "\t\t" + NL + "" + NL + "}";
  protected final String TEXT_46 = NL;

 	private MdfClass _model;
	
	private BeanSerializerGenerator() {
		throw new UnsupportedOperationException();
	}
	
	public BeanSerializerGenerator(MdfClass model) {
		this._model = model;
	}

	public String getClassName() {
		return JavaCore.getBeanPackage(_model)+".serializer."+JavaCore.getBeanClassName(_model)+"Serializer";
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
    stringBuffer.append( JavaCore.getBeanPackage(_model) );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( JavaCore.getModelPackage(_model) );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( JavaCore.getBeanClassName(_model));
    stringBuffer.append(TEXT_7);
    if (!_model.isAbstract()){
    stringBuffer.append(TEXT_8);
    }
    stringBuffer.append(TEXT_9);
    stringBuffer.append(JavaCore.generateBeanSUID(_model));
    stringBuffer.append(TEXT_10);
    	 if (!_model.isAbstract()){
    stringBuffer.append(TEXT_11);
    stringBuffer.append(JavaCore.getQualifiedBeanClassName(_model));
    stringBuffer.append(TEXT_12);
    stringBuffer.append(JavaCore.getQualifiedBeanClassName(_model));
    stringBuffer.append(TEXT_13);
    stringBuffer.append(JavaCore.getQualifiedBeanClassName(_model));
    stringBuffer.append(TEXT_14);
    	 }
    stringBuffer.append(TEXT_15);
    stringBuffer.append(JavaCore.getQualifiedBeanClassName(_model));
    stringBuffer.append(TEXT_16);
     if (_model.getBaseClass() != null) { 
    stringBuffer.append(TEXT_17);
    stringBuffer.append(JavaCore.getQualifiedBeanClassSerializerName(_model.getBaseClass()));
    stringBuffer.append(TEXT_18);
     }
    stringBuffer.append(TEXT_19);
    		Iterator it = _model.getProperties().iterator();
		boolean cptDeclared = false;
        while (it.hasNext()) {
            MdfProperty p = (MdfProperty) it.next();
            
            MdfEntity type = p.getType();
				
			if (p instanceof MdfReverseAssociation) {
					continue;
			}

			
			
			if (p instanceof MdfAssociation){
				MdfAssociation asso = (MdfAssociation)p;
				if (asso.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY){
    stringBuffer.append(TEXT_20);
    stringBuffer.append(JavaCore.getGetterName(p));
    stringBuffer.append(TEXT_21);
    stringBuffer.append(JavaCore.getGetterName(p));
    stringBuffer.append(TEXT_22);
    					} else {
    stringBuffer.append(TEXT_23);
    stringBuffer.append(JavaCore.getGetterName(p));
    stringBuffer.append(TEXT_24);
    				}
			} else if (p instanceof MdfAttribute){
			  if (type instanceof MdfEnumeration) {
			  	 MdfEnumeration e = (MdfEnumeration) type;
				 type = e.getType();
			  } 
			  Class javaClass = PrimitivesDomain.getJavaType(type);
			  if (javaClass == null) {
			  	 throw new RuntimeException("Invalid Property Type!");
			  }
					
			  javaClass = PrimitivesDomain.getJavaType(type);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(JavaCore.getLastWord(javaClass.getName()));
    stringBuffer.append(TEXT_26);
    stringBuffer.append( JavaCore.getGetterName(p));
    stringBuffer.append(TEXT_27);
    			}
		}	 
    stringBuffer.append(TEXT_28);
    stringBuffer.append(JavaCore.getQualifiedBeanClassName(_model));
    stringBuffer.append(TEXT_29);
    stringBuffer.append(JavaCore.getQualifiedBeanClassName(_model));
    stringBuffer.append(TEXT_30);
     if (_model.getBaseClass() != null) { 
    stringBuffer.append(TEXT_31);
    stringBuffer.append(JavaCore.getQualifiedBeanClassSerializerName(_model.getBaseClass()));
    stringBuffer.append(TEXT_32);
     }
    stringBuffer.append(TEXT_33);
    		it = _model.getProperties().iterator();
		cptDeclared = false;
        while (it.hasNext()) {
            MdfProperty p = (MdfProperty) it.next();
            
            MdfEntity type = p.getType();
				
			if (p instanceof MdfReverseAssociation) {
					continue;
			}

			if (p instanceof MdfAssociation){
				MdfAssociation asso = (MdfAssociation)p;
				if (asso.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY){
    stringBuffer.append(TEXT_34);
    stringBuffer.append(JavaCore.getSetterName(p));
    stringBuffer.append(TEXT_35);
    				} else {
				  if (asso.getContainment()== MdfConstants.CONTAINMENT_BYREFERENCE) {
    stringBuffer.append(TEXT_36);
    stringBuffer.append(JavaCore.getSetterName(p));
    stringBuffer.append(TEXT_37);
    stringBuffer.append(JavaCore.getQualifiedRefClassName((MdfClass)type));
    stringBuffer.append(TEXT_38);
    				  } else { 
    stringBuffer.append(TEXT_39);
    stringBuffer.append(JavaCore.getSetterName(p));
    stringBuffer.append(TEXT_40);
    stringBuffer.append(JavaCore.getQualifiedBeanClassName((MdfClass)type));
    stringBuffer.append(TEXT_41);
    				  }
			    }
			} else if (p instanceof MdfAttribute){
			  if (type instanceof MdfEnumeration) {
			  	 MdfEnumeration e = (MdfEnumeration) type;
				 type = e.getType();
			  } 
			  Class javaClass = PrimitivesDomain.getJavaType(type);
			  if (javaClass == null) {
			  	 throw new RuntimeException("Invalid Property Type!");
			  }
					
			  javaClass = PrimitivesDomain.getJavaType(type);
    stringBuffer.append(TEXT_42);
    stringBuffer.append( JavaCore.getSetterName(p));
    stringBuffer.append(TEXT_43);
    stringBuffer.append(JavaCore.getLastWord(javaClass.getName()));
    stringBuffer.append(TEXT_44);
    			}
		}	 
    stringBuffer.append(TEXT_45);
    stringBuffer.append(TEXT_46);
    return stringBuffer.toString();
  }
}
