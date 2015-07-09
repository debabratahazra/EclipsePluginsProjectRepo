
package com.odcgroup.mdf.generation.legacy.java.jet;

import java.util.*;
import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.mdf.generation.util.MdfGenerationUtil;
import com.odcgroup.mdf.generation.legacy.java.SourceCodeGenerator;
import com.odcgroup.mdf.metamodel.*;

class ConfigurableObjectIdGenerator extends SourceCodeGenerator {
 
  protected static String nl;
  public static synchronized ConfigurableObjectIdGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    ConfigurableObjectIdGenerator result = new ConfigurableObjectIdGenerator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "/*" + NL + " * This file has been automatically generated, DO NOT MODIFY!" + NL + " * Date: ";
  protected final String TEXT_3 = NL + " * User: ";
  protected final String TEXT_4 = NL + " */" + NL + "" + NL + "" + NL + "package ";
  protected final String TEXT_5 = ";" + NL + "" + NL + "import java.io.*;" + NL + "" + NL + "import com.odcgroup.mdf.core.*;" + NL + "import com.odcgroup.mdf.core.factory.ObjectIdBeanFactorySingleton;" + NL + "" + NL + "/**" + NL + " * Object id for the ";
  protected final String TEXT_6 = " class." + NL + " * @see ";
  protected final String TEXT_7 = NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_8 = " extends ";
  protected final String TEXT_9 = NL + "\t";
  protected final String TEXT_10 = NL + "\tObjectIdBean";
  protected final String TEXT_11 = " implements Serializable" + NL + "" + NL + "{" + NL + "\tprivate static final long serialVersionUID = ";
  protected final String TEXT_12 = "L;" + NL + "\t" + NL + "\t/**" + NL + "     * Creates a new instance of <em>";
  protected final String TEXT_13 = "</em>.";
  protected final String TEXT_14 = NL + "\t * @param ";
  protected final String TEXT_15 = NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_16 = "(";
  protected final String TEXT_17 = NL + "\t\t\t";
  protected final String TEXT_18 = " ";
  protected final String TEXT_19 = ") {" + NL + "\t\tthis(\"";
  protected final String TEXT_20 = "\", " + NL + "\t\t\t\"";
  protected final String TEXT_21 = "\"";
  protected final String TEXT_22 = NL + "\t\t\t\t, ";
  protected final String TEXT_23 = ");" + NL + "\t}" + NL + "" + NL + "    protected ";
  protected final String TEXT_24 = "(String _domain, String _name ";
  protected final String TEXT_25 = NL + "\t\t\t\t, ";
  protected final String TEXT_26 = " ";
  protected final String TEXT_27 = "){" + NL + "        super(_domain, _name ";
  protected final String TEXT_28 = NL + "\t\t\t\t, ";
  protected final String TEXT_29 = ");" + NL + "    }" + NL + "" + NL + "\t/**" + NL + "     * Creates a new instance of <em>";
  protected final String TEXT_30 = "</em>.";
  protected final String TEXT_31 = NL + "\t * @param ";
  protected final String TEXT_32 = NL + "     * @return A new instance of <em>";
  protected final String TEXT_33 = "</em>." + NL + "\t * @deprecated use {@link #create";
  protected final String TEXT_34 = "} instead " + NL + "     */" + NL + "    public static ObjectId create(";
  protected final String TEXT_35 = NL + "\t\t\t";
  protected final String TEXT_36 = " ";
  protected final String TEXT_37 = ") {" + NL + "\t\treturn (";
  protected final String TEXT_38 = ")ObjectIdBeanFactorySingleton.getInstance().getObjectIdBean(" + NL + "\t\t\t";
  protected final String TEXT_39 = ".class, " + NL + "\t\t\t";
  protected final String TEXT_40 = NL + "\t\t\t\t";
  protected final String TEXT_41 = ");" + NL + "    }" + NL + "\t" + NL + "\t/**" + NL + "     * Creates a new instance of <em>";
  protected final String TEXT_42 = "</em>.";
  protected final String TEXT_43 = NL + "\t * @param ";
  protected final String TEXT_44 = NL + "     * @return A new instance of <em>";
  protected final String TEXT_45 = "</em>." + NL + "     */" + NL + "    public static ";
  protected final String TEXT_46 = " create";
  protected final String TEXT_47 = "(";
  protected final String TEXT_48 = NL + "\t\t\t";
  protected final String TEXT_49 = " ";
  protected final String TEXT_50 = ") {" + NL + "\t\treturn (";
  protected final String TEXT_51 = ")ObjectIdBeanFactorySingleton.getInstance().getObjectIdBean(" + NL + "\t\t\t";
  protected final String TEXT_52 = ".class, " + NL + "\t\t\t";
  protected final String TEXT_53 = NL + "\t\t\t\t";
  protected final String TEXT_54 = ");" + NL + "    }" + NL + "\t" + NL + "\t/**" + NL + "\t * Used for deserialiation of objects" + NL + "\t */" + NL + "    public Object readResolve() throws ObjectStreamException  {" + NL + "    \t// The method is public in order to be accessible during deserialiation of " + NL + "    \t// objects that are not in the same package" + NL + "\t\treturn ObjectIdBeanFactorySingleton.getInstance().getObjectIdBean(" + NL + "\t\t\t\tthis.getClass(), " + NL + "\t\t\t\t\tthis.getPrimaryKeyValue());" + NL + "    }" + NL + "    " + NL + "   " + NL + "}";

 	private MdfClass _model;
	
	private ConfigurableObjectIdGenerator() {
		throw new UnsupportedOperationException();
	}
	
	public ConfigurableObjectIdGenerator(MdfClass model) {
		this._model = model;
	}

	public String getClassName() {
		return JavaCore.getQualifiedRefClassName(_model);
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
    stringBuffer.append( JavaCore.getQualifiedModelClassName(_model) );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_8);
    
	MdfClass base = _model.getBaseClass();
    if ((base != null) && base.hasPrimaryKey(true)) {
    stringBuffer.append(TEXT_9);
    stringBuffer.append( JavaCore.getQualifiedRefClassName(base) );
    
	} else {
    stringBuffer.append(TEXT_10);
    
	} 
    stringBuffer.append(TEXT_11);
    stringBuffer.append( JavaCore.getSerialVersionUID(_model) );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_13);
    		Iterator keys = _model.getPrimaryKeys(true).iterator();
        while (keys.hasNext()) {
            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_14);
    stringBuffer.append( JavaCore.getFieldName(p));
     } 
    stringBuffer.append(TEXT_15);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_16);
    
        keys = _model.getPrimaryKeys(true).iterator();
        while (keys.hasNext()) {
            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_17);
    stringBuffer.append( MdfGenerationUtil.getTypeName(p) );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( JavaCore.getFieldName(p));
    
			if (keys.hasNext()) stringBuffer.append(", ");             
        }
	
    stringBuffer.append(TEXT_19);
    stringBuffer.append( _model.getQualifiedName().getDomain() );
    stringBuffer.append(TEXT_20);
    stringBuffer.append( _model.getQualifiedName().getLocalName() );
    stringBuffer.append(TEXT_21);
    
	        keys = _model.getPrimaryKeys(true).iterator();
	        while (keys.hasNext()) {
	            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_22);
    stringBuffer.append( JavaCore.getFieldName(p));
    
	        }
    stringBuffer.append(TEXT_23);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_24);
     
			keys = _model.getPrimaryKeys(true).iterator();
	        while (keys.hasNext()) {
	            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_25);
    stringBuffer.append( MdfGenerationUtil.getTypeName(p) );
    stringBuffer.append(TEXT_26);
    stringBuffer.append( JavaCore.getFieldName(p));
    
	        }
    stringBuffer.append(TEXT_27);
    
	        keys = _model.getPrimaryKeys(true).iterator();
	        while (keys.hasNext()) {
	            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_28);
    stringBuffer.append( JavaCore.getFieldName(p));
    
	        }
    stringBuffer.append(TEXT_29);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_30);
    		keys = _model.getPrimaryKeys(true).iterator();
        while (keys.hasNext()) {
            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_31);
    stringBuffer.append( JavaCore.getFieldName(p));
     } 
    stringBuffer.append(TEXT_32);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_33);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_34);
    
        keys = _model.getPrimaryKeys(true).iterator();
        while (keys.hasNext()) {
            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_35);
    stringBuffer.append( MdfGenerationUtil.getTypeName(p) );
    stringBuffer.append(TEXT_36);
    stringBuffer.append( JavaCore.getFieldName(p));
    
			if (keys.hasNext()) stringBuffer.append(", ");             
        }
	
    stringBuffer.append(TEXT_37);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_38);
    stringBuffer.append( JavaCore.getQualifiedRefClassName(_model) );
    stringBuffer.append(TEXT_39);
    
			keys = _model.getPrimaryKeys(true).iterator();
	        while (keys.hasNext()) {
	            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_40);
    stringBuffer.append( JavaCore.getFieldName(p));
    
				if (keys.hasNext()) stringBuffer.append(", ");             
	        }
    stringBuffer.append(TEXT_41);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_42);
    		keys = _model.getPrimaryKeys(true).iterator();
        while (keys.hasNext()) {
            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_43);
    stringBuffer.append( JavaCore.getFieldName(p));
     } 
    stringBuffer.append(TEXT_44);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_45);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_46);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_47);
    
        keys = _model.getPrimaryKeys(true).iterator();
        while (keys.hasNext()) {
            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_48);
    stringBuffer.append( MdfGenerationUtil.getTypeName(p) );
    stringBuffer.append(TEXT_49);
    stringBuffer.append( JavaCore.getFieldName(p));
    
			if (keys.hasNext()) stringBuffer.append(", ");             
        }
	
    stringBuffer.append(TEXT_50);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_51);
    stringBuffer.append( JavaCore.getQualifiedRefClassName(_model) );
    stringBuffer.append(TEXT_52);
    
			keys = _model.getPrimaryKeys(true).iterator();
	        while (keys.hasNext()) {
	            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_53);
    stringBuffer.append( JavaCore.getFieldName(p));
    
				if (keys.hasNext()) stringBuffer.append(", ");             
	        }
    stringBuffer.append(TEXT_54);
    return stringBuffer.toString();
  }
}
