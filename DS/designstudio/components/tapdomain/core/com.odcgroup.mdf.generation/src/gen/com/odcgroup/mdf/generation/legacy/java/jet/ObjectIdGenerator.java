
package com.odcgroup.mdf.generation.legacy.java.jet;

import java.util.*;
import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.mdf.generation.legacy.java.SourceCodeGenerator;
import com.odcgroup.mdf.generation.util.MdfGenerationUtil;
import com.odcgroup.mdf.metamodel.*;

class ObjectIdGenerator extends SourceCodeGenerator {
 
  protected static String nl;
  public static synchronized ObjectIdGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    ObjectIdGenerator result = new ObjectIdGenerator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "/*" + NL + " * This file has been automatically generated, DO NOT MODIFY!" + NL + " * Date: ";
  protected final String TEXT_3 = NL + " * User: ";
  protected final String TEXT_4 = NL + " */" + NL + "" + NL + "" + NL + "package ";
  protected final String TEXT_5 = ";" + NL + "" + NL + "import java.io.*;" + NL + "" + NL + "import com.odcgroup.mdf.core.*;" + NL + "" + NL + "/**" + NL + " * Object id for the ";
  protected final String TEXT_6 = " class." + NL + " * @see ";
  protected final String TEXT_7 = NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_8 = " extends ";
  protected final String TEXT_9 = NL + "\t";
  protected final String TEXT_10 = NL + "\tObjectIdBean";
  protected final String TEXT_11 = " implements Serializable" + NL + "" + NL + "{" + NL + "\tprivate static final long serialVersionUID = ";
  protected final String TEXT_12 = "L;" + NL + "\t" + NL + "\t/**" + NL + "     * Creates a new instance of <em>";
  protected final String TEXT_13 = "</em>.";
  protected final String TEXT_14 = NL + "\t * @param ";
  protected final String TEXT_15 = " ";
  protected final String TEXT_16 = NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_17 = "(";
  protected final String TEXT_18 = NL + "\t\t\t";
  protected final String TEXT_19 = " ";
  protected final String TEXT_20 = ") {" + NL + "\t\tthis(\"";
  protected final String TEXT_21 = "\", " + NL + "\t\t\t\"";
  protected final String TEXT_22 = "\"";
  protected final String TEXT_23 = NL + "\t\t\t\t, ";
  protected final String TEXT_24 = ");" + NL + "\t}" + NL + "" + NL + "    protected ";
  protected final String TEXT_25 = "(String _domain, String _name ";
  protected final String TEXT_26 = NL + "\t\t\t\t, ";
  protected final String TEXT_27 = " ";
  protected final String TEXT_28 = "){" + NL + "        super(_domain, _name ";
  protected final String TEXT_29 = NL + "\t\t\t\t, ";
  protected final String TEXT_30 = ");" + NL + "    }" + NL + "" + NL + "\t/**" + NL + "     * Creates a new instance of <em>";
  protected final String TEXT_31 = "</em>.";
  protected final String TEXT_32 = NL + "\t * @param ";
  protected final String TEXT_33 = " ";
  protected final String TEXT_34 = NL + "     * @return A new instance of <em>";
  protected final String TEXT_35 = "</em>." + NL + "\t * @deprecated use {@link #create";
  protected final String TEXT_36 = "} instead " + NL + "     */" + NL + "    public static ObjectId create(";
  protected final String TEXT_37 = NL + "\t\t\t";
  protected final String TEXT_38 = " ";
  protected final String TEXT_39 = ") {" + NL + "        return new ";
  protected final String TEXT_40 = "(";
  protected final String TEXT_41 = NL + "\t\t\t\t";
  protected final String TEXT_42 = ");" + NL + "    }" + NL + "\t" + NL + "\t/**" + NL + "     * Creates a new instance of <em>";
  protected final String TEXT_43 = "</em>.";
  protected final String TEXT_44 = NL + "\t * @param ";
  protected final String TEXT_45 = " ";
  protected final String TEXT_46 = NL + "     * @return A new instance of <em>";
  protected final String TEXT_47 = "</em>." + NL + "     */" + NL + "    public static ";
  protected final String TEXT_48 = " create";
  protected final String TEXT_49 = "(";
  protected final String TEXT_50 = NL + "\t\t\t";
  protected final String TEXT_51 = " ";
  protected final String TEXT_52 = ") {" + NL + "        return new ";
  protected final String TEXT_53 = "(";
  protected final String TEXT_54 = NL + "\t\t\t\t";
  protected final String TEXT_55 = ");" + NL + "    }" + NL + "\t" + NL + "}";

 	private MdfClass _model;
	
	private ObjectIdGenerator() {
		throw new UnsupportedOperationException();
	}
	
	public ObjectIdGenerator(MdfClass model) {
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
    stringBuffer.append(TEXT_15);
     } 
    stringBuffer.append(TEXT_16);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_17);
    
        keys = _model.getPrimaryKeys(true).iterator();
        while (keys.hasNext()) {
            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_18);
    stringBuffer.append( MdfGenerationUtil.getTypeName(p) );
    stringBuffer.append(TEXT_19);
    stringBuffer.append( JavaCore.getFieldName(p));
    
			if (keys.hasNext()) stringBuffer.append(", ");             
        }
	
    stringBuffer.append(TEXT_20);
    stringBuffer.append( _model.getQualifiedName().getDomain() );
    stringBuffer.append(TEXT_21);
    stringBuffer.append( _model.getQualifiedName().getLocalName() );
    stringBuffer.append(TEXT_22);
    
	        keys = _model.getPrimaryKeys(true).iterator();
	        while (keys.hasNext()) {
	            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_23);
    stringBuffer.append( JavaCore.getFieldName(p));
    
	        }
    stringBuffer.append(TEXT_24);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_25);
     
			keys = _model.getPrimaryKeys(true).iterator();
	        while (keys.hasNext()) {
	            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_26);
    stringBuffer.append( MdfGenerationUtil.getTypeName(p) );
    stringBuffer.append(TEXT_27);
    stringBuffer.append( JavaCore.getFieldName(p));
    
	        }
    stringBuffer.append(TEXT_28);
    
	        keys = _model.getPrimaryKeys(true).iterator();
	        while (keys.hasNext()) {
	            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_29);
    stringBuffer.append( JavaCore.getFieldName(p));
    
	        }
    stringBuffer.append(TEXT_30);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_31);
    		keys = _model.getPrimaryKeys(true).iterator();
        while (keys.hasNext()) {
            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_32);
    stringBuffer.append( JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_33);
     } 
    stringBuffer.append(TEXT_34);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_35);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_36);
    
        keys = _model.getPrimaryKeys(true).iterator();
        while (keys.hasNext()) {
            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_37);
    stringBuffer.append( MdfGenerationUtil.getTypeName(p) );
    stringBuffer.append(TEXT_38);
    stringBuffer.append( JavaCore.getFieldName(p));
    
			if (keys.hasNext()) stringBuffer.append(", ");             
        }
	
    stringBuffer.append(TEXT_39);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_40);
    
	        keys = _model.getPrimaryKeys(true).iterator();
	        while (keys.hasNext()) {
	            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_41);
    stringBuffer.append( JavaCore.getFieldName(p));
    
				if (keys.hasNext()) stringBuffer.append(", ");             
	        }
    stringBuffer.append(TEXT_42);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_43);
    		keys = _model.getPrimaryKeys(true).iterator();
        while (keys.hasNext()) {
            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_44);
    stringBuffer.append( JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_45);
     } 
    stringBuffer.append(TEXT_46);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_47);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_48);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_49);
    
        keys = _model.getPrimaryKeys(true).iterator();
        while (keys.hasNext()) {
            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_50);
    stringBuffer.append( MdfGenerationUtil.getTypeName(p) );
    stringBuffer.append(TEXT_51);
    stringBuffer.append( JavaCore.getFieldName(p));
    
			if (keys.hasNext()) stringBuffer.append(", ");             
        }
	
    stringBuffer.append(TEXT_52);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_53);
    
	        keys = _model.getPrimaryKeys(true).iterator();
	        while (keys.hasNext()) {
	            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_54);
    stringBuffer.append( JavaCore.getFieldName(p));
    
				if (keys.hasNext()) stringBuffer.append(", ");             
	        }
    stringBuffer.append(TEXT_55);
    return stringBuffer.toString();
  }
}
