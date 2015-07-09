
package com.odcgroup.mdf.generation.legacy.java.jet;

import java.util.*;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.mdf.generation.legacy.java.SourceCodeGenerator;
import com.odcgroup.mdf.generation.util.MdfGenerationUtil;
import com.odcgroup.mdf.metamodel.*;

class BeanGenerator extends SourceCodeGenerator {
 
  protected static String nl;
  public static synchronized BeanGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    BeanGenerator result = new BeanGenerator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "/*" + NL + " * This file has been automatically generated, DO NOT MODIFY!" + NL + " * Date: ";
  protected final String TEXT_3 = NL + " * User: ";
  protected final String TEXT_4 = NL + " */" + NL + "" + NL + "" + NL + "package ";
  protected final String TEXT_5 = ";" + NL + "" + NL + "import java.io.*;" + NL + "" + NL + "import ";
  protected final String TEXT_6 = ".*;" + NL + "" + NL + "import com.odcgroup.mdf.core.*;" + NL + "" + NL + "/**" + NL + " * @see ";
  protected final String TEXT_7 = NL + " * @generated" + NL + " */" + NL + "public ";
  protected final String TEXT_8 = "class ";
  protected final String TEXT_9 = " " + NL;
  protected final String TEXT_10 = NL + "\textends ";
  protected final String TEXT_11 = NL + "\textends DomainObjectBean";
  protected final String TEXT_12 = " implements Serializable, ";
  protected final String TEXT_13 = " {" + NL + "" + NL + "\tprivate static final long serialVersionUID = ";
  protected final String TEXT_14 = "L;\t" + NL + "" + NL + "    //-------------------------------------------------------------------------" + NL + "\t// Fields and accessors" + NL + "\t";
  protected final String TEXT_15 = "            " + NL + "\t/**\t " + NL + "\t * The <em>JavaCore.getFieldName(p)</em> property." + NL + "\t * ";
  protected final String TEXT_16 = NL + "\t * @generated" + NL + "     * ";
  protected final String TEXT_17 = NL + "\t */" + NL + "    private ";
  protected final String TEXT_18 = " ";
  protected final String TEXT_19 = ";" + NL + "\t" + NL + "\t/**" + NL + "\t * Returns the <em>";
  protected final String TEXT_20 = "</em> property." + NL + "\t * @return the <em>";
  protected final String TEXT_21 = "</em> property." + NL + "\t * ";
  protected final String TEXT_22 = NL + "\t * @generated" + NL + "     * ";
  protected final String TEXT_23 = NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_24 = " ";
  protected final String TEXT_25 = "() {";
  protected final String TEXT_26 = NL + "\t\t \tif (null==this.";
  protected final String TEXT_27 = ") {" + NL + "\t\tthis.";
  protected final String TEXT_28 = " = new java.util.ArrayList(0);\t\t \t" + NL + "\t\t \t}" + NL + "\t\treturn this.";
  protected final String TEXT_29 = ";";
  protected final String TEXT_30 = NL + "\t\tif (this.";
  protected final String TEXT_31 = "==null) {" + NL + "\t\t\treturn null;" + NL + "\t\t} else {" + NL + "\t\t\t// This ensure immutability of ";
  protected final String TEXT_32 = NL + "\t\t\treturn new java.util.Date(this.";
  protected final String TEXT_33 = ".getTime()); " + NL + "\t\t}";
  protected final String TEXT_34 = " " + NL + "\t\treturn this.";
  protected final String TEXT_35 = ";";
  protected final String TEXT_36 = NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * Sets the <em>";
  protected final String TEXT_37 = "</em> property." + NL + "\t * @param ";
  protected final String TEXT_38 = " the new value of the <em>";
  protected final String TEXT_39 = "</em> property." + NL + "\t * ";
  protected final String TEXT_40 = NL + "\t * @generated" + NL + "     * ";
  protected final String TEXT_41 = NL + "\t */" + NL + "\tpublic void ";
  protected final String TEXT_42 = "(";
  protected final String TEXT_43 = " ";
  protected final String TEXT_44 = ") {";
  protected final String TEXT_45 = NL + "\t\t// This ensure immutability of ";
  protected final String TEXT_46 = NL + "\t\tif (";
  protected final String TEXT_47 = "==null) {" + NL + "\t\t\tthis.";
  protected final String TEXT_48 = " = null;" + NL + "\t\t} else {" + NL + "\t\t\tthis.";
  protected final String TEXT_49 = " = new java.util.Date(";
  protected final String TEXT_50 = ".getTime());" + NL + "\t\t}";
  protected final String TEXT_51 = NL + "\t\tthis.";
  protected final String TEXT_52 = " = ";
  protected final String TEXT_53 = ";";
  protected final String TEXT_54 = NL + "\t}";
  protected final String TEXT_55 = NL + NL + "    //-------------------------------------------------------------------------" + NL + "\t// Constructors" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_56 = "() {" + NL + "\t\tsuper();";
  protected final String TEXT_57 = NL + "\t\ttry {";
  protected final String TEXT_58 = NL + "\t\t\t\tthis.";
  protected final String TEXT_59 = " = ";
  protected final String TEXT_60 = ";\t\t";
  protected final String TEXT_61 = NL + "\t\t} catch (Exception e) {" + NL + "            throw (IllegalArgumentException) new IllegalArgumentException(e.getMessage()).initCause(e);" + NL + "\t\t}\t";
  protected final String TEXT_62 = "\t\t" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Copy Constructor" + NL + "\t * @param copy The instance to be copied";
  protected final String TEXT_63 = NL + "\t * @deprecated since MDF2.30, replaced by another copy constructor " + NL + "    *     that makes explicit the fact that you need to provide the key";
  protected final String TEXT_64 = NL + "\t * @generated" + NL + "\t */" + NL + "    public ";
  protected final String TEXT_65 = "(";
  protected final String TEXT_66 = " copy) {" + NL + "    \tthis();" + NL + "        copy.copyTo(this);" + NL + "    }" + NL;
  protected final String TEXT_67 = NL + "\t/**" + NL + "\t * Copy Constructor";
  protected final String TEXT_68 = NL + "\t * @param copy The instance to be copied" + NL + "\t * @generated" + NL + "\t */" + NL + "    public ";
  protected final String TEXT_69 = "(";
  protected final String TEXT_70 = NL + "\t\t\t";
  protected final String TEXT_71 = " ";
  protected final String TEXT_72 = ", ";
  protected final String TEXT_73 = " copy) {" + NL + "    \tthis();" + NL + "        copy.copyTo(this);";
  protected final String TEXT_74 = NL + "\t\tthis.";
  protected final String TEXT_75 = "(";
  protected final String TEXT_76 = ");";
  protected final String TEXT_77 = NL + "    }";
  protected final String TEXT_78 = NL + "    " + NL + "\t/**" + NL + "\t * @see com.odcgroup.mdf.core.DomainObjectBean#copyTo(DomainObjectBean)" + NL + "\t * @generated" + NL + "\t */" + NL + "    public void copyTo(DomainObjectBean copy) {" + NL + "        super.copyTo(copy);";
  protected final String TEXT_79 = NL + "        if (copy instanceof ";
  protected final String TEXT_80 = ") {";
  protected final String TEXT_81 = NL + "            ";
  protected final String TEXT_82 = " other = (";
  protected final String TEXT_83 = ") copy;" + NL;
  protected final String TEXT_84 = NL + "\t\t\tother.";
  protected final String TEXT_85 = " = this.";
  protected final String TEXT_86 = ";";
  protected final String TEXT_87 = NL + "\t\t\tif (this.";
  protected final String TEXT_88 = " != null) {" + NL + "\t\t\t\tother.";
  protected final String TEXT_89 = " = new java.util.ArrayList(this.";
  protected final String TEXT_90 = ");" + NL + "\t\t\t}";
  protected final String TEXT_91 = NL + "\t\t\tother.";
  protected final String TEXT_92 = " = this.";
  protected final String TEXT_93 = ";";
  protected final String TEXT_94 = NL + "        }";
  protected final String TEXT_95 = "        " + NL + "    }" + NL;
  protected final String TEXT_96 = NL + "    //-------------------------------------------------------------------------" + NL + "\t// com.odcgroup.mdf.core.DomainObject" + NL + "" + NL + "\tprivate static final com.odcgroup.mdf.metamodel.MdfName ENTITY_NAME = " + NL + "\t\t com.odcgroup.mdf.metamodel.MdfNameFactory.createMdfName(\"";
  protected final String TEXT_97 = "\",\"";
  protected final String TEXT_98 = "\");" + NL + "\t" + NL + "    public com.odcgroup.mdf.metamodel.MdfName getEntityName() {" + NL + "        return ENTITY_NAME;" + NL + "    }";
  protected final String TEXT_99 = NL + "   " + NL + "    //-------------------------------------------------------------------------" + NL + "\t// com.odcgroup.mdf.core.ReferenceableDomainObject" + NL + "" + NL + "\t/**" + NL + "\t * @see com.odcgroup.mdf.core.ReferenceableDomainObject#getDomainObject()" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic DomainObject getDomainObject() {" + NL + "\t\treturn this;" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @see com.odcgroup.mdf.core.ReferenceableDomainObject#getObjectId()" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ObjectId getObjectId() {" + NL + "\t\t";
  protected final String TEXT_100 = NL + "         \tif (";
  protected final String TEXT_101 = ")" + NL + "\t\t    {" + NL + "        \t  return null;" + NL + "\t\t    }" + NL + "\t\t  ";
  protected final String TEXT_102 = NL + "\t\t  " + NL + "\t" + NL + "\t\treturn ";
  protected final String TEXT_103 = ".create";
  protected final String TEXT_104 = "(";
  protected final String TEXT_105 = ");" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * @see com.odcgroup.mdf.core.ReferenceableDomainObject#get_oid()" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic Object get_oid() {" + NL + "\t\tObjectId objectId = getObjectId();" + NL + "\t\tif (objectId != null) {" + NL + "\t\t\treturn objectId.getPrimaryKeyValue();" + NL + "\t\t} else {" + NL + "\t\t\treturn null;" + NL + "\t\t}" + NL + "\t}";
  protected final String TEXT_106 = NL + "  " + NL + "    //-------------------------------------------------------------------------" + NL + "\t// hashCode & equals" + NL + "" + NL + "\t/**" + NL + "\t * Returns a hash code value for the object." + NL + "     *" + NL + "     * @return a hash code value for this object." + NL + "\t * @see java.lang.Object#hashCode()" + NL + "\t * @generated" + NL + "\t */" + NL + "    public int hashCode() {" + NL + "    \tObjectId objectId = getObjectId();" + NL + "    \tif (objectId == null) {" + NL + "   \t\t\treturn super.hashCode();" + NL + "    \t}" + NL + "        return objectId.hashCode();" + NL + "    }" + NL + "" + NL + "\t/**" + NL + "\t * Indicates whether some other object is \"equal to\" this one." + NL + "     *" + NL + "\t * @param obj the reference object with which to compare." + NL + "     * @return <code>true</code> if this object is the same as " + NL + "     * \t\tthe obj argument; <code>false</code> otherwise." + NL + "\t * @see java.lang.Object#equals(java.lang.Object)" + NL + "\t * @generated" + NL + "\t */" + NL + "    public boolean equals(Object obj) {" + NL + "        if (this == obj) return true;" + NL + "" + NL + "        if (obj instanceof ";
  protected final String TEXT_107 = ") {" + NL + "        \tObjectId objectId = getObjectId(); " + NL + "\t    \tif (objectId == null) {" + NL + "\t   \t\t\treturn super.equals(obj);" + NL + "\t    \t}";
  protected final String TEXT_108 = NL + "            ";
  protected final String TEXT_109 = " other = (";
  protected final String TEXT_110 = ") obj;" + NL + "            return objectId.equals(other.getObjectId());" + NL + "        }" + NL + "        " + NL + "        return false;" + NL + "    }";
  protected final String TEXT_111 = "     " + NL;
  protected final String TEXT_112 = NL + NL + "    //-------------------------------------------------------------------------" + NL + "\t// readObject & writeObject\t" + NL + "    private void readObject(ObjectInputStream in)" + NL + "        throws IOException, ClassNotFoundException {" + NL + "" + NL + "        ObjectInputStream.GetField getfield = in.readFields();" + NL + "        " + NL + "\t\ttry {" + NL + "\t\t";
  protected final String TEXT_113 = NL + "\t\t\tthis.";
  protected final String TEXT_114 = " = ";
  protected final String TEXT_115 = "getfield.get(\"";
  protected final String TEXT_116 = "\", ";
  protected final String TEXT_117 = ");\t\t\t\t";
  protected final String TEXT_118 = NL + "\t\t} catch (IOException e) {" + NL + "\t\t\tthrow e;" + NL + "\t\t} catch (Exception e) {" + NL + "            throw (IOException) new IOException(e.getMessage()).initCause(e);" + NL + "\t\t}" + NL + "    }" + NL + "\t\t    " + NL + "    private void writeObject(ObjectOutputStream out)" + NL + "        throws IOException {" + NL + "    " + NL + "        ObjectOutputStream.PutField putfield = out.putFields();" + NL + "        " + NL + "\t\tprops = _model.getProperties().iterator();" + NL + "\t\t" + NL + "\t\twhile (props.hasNext()) {" + NL + "\t\t\tMdfProperty p = (MdfProperty) props.next();" + NL + "" + NL + "\t\t\tif (p instanceof MdfReverseAssociation) {" + NL + "\t\t\t\tcontinue;" + NL + "\t\t\t}%>" + NL + "\t\tputfield.put(\"";
  protected final String TEXT_119 = "\", this.";
  protected final String TEXT_120 = ");\t\t\t" + NL + "\t\t";
  protected final String TEXT_121 = NL + "        out.writeFields();" + NL + "    }" + NL + "    \t";
  protected final String TEXT_122 = NL + NL + "}";

 	private MdfClass _model;
	
	private BeanGenerator() {
		throw new UnsupportedOperationException();
	}
	
	public BeanGenerator(MdfClass model) {
		this._model = model;
	}

	public String getClassName() {
		return JavaCore.getQualifiedBeanClassName(_model);
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
    stringBuffer.append( JavaCore.getQualifiedModelClassName(_model) );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( (_model.isAbstract() ? "abstract " : ""));
    stringBuffer.append(TEXT_8);
    stringBuffer.append( JavaCore.getBeanClassName(_model) );
    stringBuffer.append(TEXT_9);
     if (_model.getBaseClass() != null) { 
    stringBuffer.append(TEXT_10);
    stringBuffer.append( JavaCore.getQualifiedBeanClassName(_model.getBaseClass()) );
     } else { 
    stringBuffer.append(TEXT_11);
     } 
    stringBuffer.append(TEXT_12);
    stringBuffer.append( JavaCore.getModelClassName(_model) );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( JavaCore.getSerialVersionUID(_model) );
    stringBuffer.append(TEXT_14);
    
		Iterator it = _model.getProperties().iterator();
        while (it.hasNext()) {
            MdfProperty p = (MdfProperty) it.next();
    stringBuffer.append(TEXT_15);
    stringBuffer.append( (p.getType() instanceof MdfEnumeration) ? "@see " + JavaCore.getQualifiedModelClassName(p.getType()) : "" );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( (p.isPrimaryKey()) ? "@primaryKey" : "" );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( JavaCore.getShortName(MdfGenerationUtil.getTypeName(p)) );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_19);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_20);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_21);
    stringBuffer.append( (p.getType() instanceof MdfEnumeration) ? "@see " + JavaCore.getQualifiedModelClassName(p.getType()) : "" );
    stringBuffer.append(TEXT_22);
    stringBuffer.append( (p.isPrimaryKey()) ? "@primaryKey" : "" );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( JavaCore.getShortName(MdfGenerationUtil.getTypeName(p)) );
    stringBuffer.append(TEXT_24);
    stringBuffer.append( JavaCore.getGetterName(p) );
    stringBuffer.append(TEXT_25);
    		if (p instanceof MdfAssociation
				 && ((MdfAssociation) p).getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
    stringBuffer.append(TEXT_26);
    stringBuffer.append( JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_27);
    stringBuffer.append( JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_28);
    stringBuffer.append( JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_29);
    		} else {
    			if ("mml:date".equals(p.getType().getQualifiedName().getQualifiedName()) ||
				"mml:dateTime".equals(p.getType().getQualifiedName().getQualifiedName())) { 
    stringBuffer.append(TEXT_30);
    stringBuffer.append(JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_31);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_32);
    stringBuffer.append(JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_33);
     			} else { 
    stringBuffer.append(TEXT_34);
    stringBuffer.append(JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_35);
    			} 
    		}
    stringBuffer.append(TEXT_36);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_37);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_38);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_39);
    stringBuffer.append( (p.getType() instanceof MdfEnumeration) ? "@see " + JavaCore.getQualifiedModelClassName(p.getType()) : "" );
    stringBuffer.append(TEXT_40);
    stringBuffer.append( (p.isPrimaryKey()) ? "@primaryKey" : "" );
    stringBuffer.append(TEXT_41);
    stringBuffer.append( JavaCore.getSetterName(p) );
    stringBuffer.append(TEXT_42);
    stringBuffer.append( JavaCore.getShortName(MdfGenerationUtil.getTypeName(p)) );
    stringBuffer.append(TEXT_43);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_44);
    			if ("mml:date".equals(p.getType().getQualifiedName().getQualifiedName()) ||
				"mml:dateTime".equals(p.getType().getQualifiedName().getQualifiedName())) { 
    stringBuffer.append(TEXT_45);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_46);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_47);
    stringBuffer.append( JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_48);
    stringBuffer.append( JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_49);
    stringBuffer.append( JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_50);
    			} else { 
    stringBuffer.append(TEXT_51);
    stringBuffer.append( JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_52);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_53);
    			} 
    stringBuffer.append(TEXT_54);
     }
    stringBuffer.append(TEXT_55);
    stringBuffer.append( JavaCore.getBeanClassName(_model) );
    stringBuffer.append(TEXT_56);
      Iterator props = null;
	if (!_model.getProperties().isEmpty()) { 
    stringBuffer.append(TEXT_57);
    
			props = _model.getProperties().iterator();
			
			while (props.hasNext()) {
				MdfProperty property = (MdfProperty) props.next();
				if (false==property instanceof MdfAssociation
				 || ((MdfAssociation) property).getMultiplicity() == MdfConstants.MULTIPLICITY_ONE) {
					String initCode = MdfGenerationUtil.getPropertyInitCode(property);
					if (initCode != null) {
						String fieldName = JavaCore.getFieldName(property);
    stringBuffer.append(TEXT_58);
    stringBuffer.append( fieldName );
    stringBuffer.append(TEXT_59);
    stringBuffer.append( initCode );
    stringBuffer.append(TEXT_60);
    					}
				} 
			} 
    stringBuffer.append(TEXT_61);
     } 
    stringBuffer.append(TEXT_62);
     if (_model.hasPrimaryKey(true)) { 
    stringBuffer.append(TEXT_63);
     } 
    stringBuffer.append(TEXT_64);
    stringBuffer.append( JavaCore.getBeanClassName(_model) );
    stringBuffer.append(TEXT_65);
    stringBuffer.append( JavaCore.getBeanClassName(_model) );
    stringBuffer.append(TEXT_66);
     if (_model.hasPrimaryKey(true)) { 
    stringBuffer.append(TEXT_67);
    		Iterator keys = _model.getPrimaryKeys(true).iterator();
        while (keys.hasNext()) {
            MdfProperty p = (MdfProperty) keys.next();
     } 
    stringBuffer.append(TEXT_68);
    stringBuffer.append( JavaCore.getBeanClassName(_model) );
    stringBuffer.append(TEXT_69);
    
        keys = _model.getPrimaryKeys(true).iterator();
        while (keys.hasNext()) {
            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_70);
    stringBuffer.append( MdfGenerationUtil.getTypeName(p) );
    stringBuffer.append(TEXT_71);
    stringBuffer.append( JavaCore.getFieldName(p));
    
			if (keys.hasNext()) stringBuffer.append(", ");
        }
	
    stringBuffer.append(TEXT_72);
    stringBuffer.append( JavaCore.getBeanClassName(_model) );
    stringBuffer.append(TEXT_73);
     	keys = _model.getPrimaryKeys(true).iterator();
    	while (keys.hasNext()) {
            MdfProperty p = (MdfProperty) keys.next();
    stringBuffer.append(TEXT_74);
    stringBuffer.append( JavaCore.getSetterName(p) );
    stringBuffer.append(TEXT_75);
    stringBuffer.append( JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_76);
    
        }
    stringBuffer.append(TEXT_77);
     } 
    stringBuffer.append(TEXT_78);
     if (_model.getProperties().size() > _model.getPrimaryKeys().size()) { 
    stringBuffer.append(TEXT_79);
    stringBuffer.append( JavaCore.getBeanClassName(_model) );
    stringBuffer.append(TEXT_80);
    stringBuffer.append(TEXT_81);
    stringBuffer.append( JavaCore.getBeanClassName(_model) );
    stringBuffer.append(TEXT_82);
    stringBuffer.append( JavaCore.getBeanClassName(_model) );
    stringBuffer.append(TEXT_83);
     
            props = _model.getProperties().iterator();
			
			while (props.hasNext()) {
				MdfProperty property = (MdfProperty) props.next();

				if (property.isPrimaryKey()) {
					continue;
				}
				
				String fieldName = JavaCore.getFieldName(property);
				
				if (property instanceof MdfAttribute) {
    stringBuffer.append(TEXT_84);
    stringBuffer.append( fieldName );
    stringBuffer.append(TEXT_85);
    stringBuffer.append( fieldName );
    stringBuffer.append(TEXT_86);
    				} else if (property instanceof MdfAssociation) {
					MdfAssociation association = (MdfAssociation) property;
					
					if (association.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
    stringBuffer.append(TEXT_87);
    stringBuffer.append( fieldName );
    stringBuffer.append(TEXT_88);
    stringBuffer.append( fieldName );
    stringBuffer.append(TEXT_89);
    stringBuffer.append( fieldName );
    stringBuffer.append(TEXT_90);
    					} else {
    stringBuffer.append(TEXT_91);
    stringBuffer.append( fieldName );
    stringBuffer.append(TEXT_92);
    stringBuffer.append( fieldName );
    stringBuffer.append(TEXT_93);
    					}
				}
			}

    stringBuffer.append(TEXT_94);
     } 
    stringBuffer.append(TEXT_95);
     if (!_model.isAbstract()) { 
    stringBuffer.append(TEXT_96);
    stringBuffer.append( _model.getQualifiedName().getDomain() );
    stringBuffer.append(TEXT_97);
    stringBuffer.append( _model.getQualifiedName().getLocalName() );
    stringBuffer.append(TEXT_98);
     }

   if (_model.hasPrimaryKey(true)) { 
    stringBuffer.append(TEXT_99);
    Iterator keys = _model.getPrimaryKeys(true).iterator();
		  boolean hasPrimaryNotPrimitive = false;
          while (keys.hasNext()) { 
              MdfProperty p = (MdfProperty) keys.next();
              if (!PrimitivesDomain.getJavaType(p.getType()).isPrimitive()) {
              	  if (!hasPrimaryNotPrimitive) hasPrimaryNotPrimitive = true;
              	  else stringBuffer.append(" || ");
    stringBuffer.append(TEXT_100);
    
         		  stringBuffer.append("this.").append(JavaCore.getGetterName(p)).append("() == null");              		
              } // end of if isPrimitive
          } // end of while
          
          if (hasPrimaryNotPrimitive) { 
    stringBuffer.append(TEXT_101);
    }
    stringBuffer.append(TEXT_102);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_103);
    stringBuffer.append( JavaCore.getRefClassName(_model) );
    stringBuffer.append(TEXT_104);
    
	        keys = _model.getPrimaryKeys(true).iterator();
	        while (keys.hasNext()) {
	            MdfProperty p = (MdfProperty) keys.next();
	            stringBuffer.append("this.").append(JavaCore.getGetterName(p)).append("()");
	            if (keys.hasNext()) stringBuffer.append(", ");
	        }
    stringBuffer.append(TEXT_105);
     }

   if (_model.hasPrimaryKey()) { 
    stringBuffer.append(TEXT_106);
    stringBuffer.append( JavaCore.getBeanClassName(_model) );
    stringBuffer.append(TEXT_107);
    stringBuffer.append(TEXT_108);
    stringBuffer.append( JavaCore.getBeanClassName(_model) );
    stringBuffer.append(TEXT_109);
    stringBuffer.append( JavaCore.getBeanClassName(_model) );
    stringBuffer.append(TEXT_110);
      } 
    stringBuffer.append(TEXT_111);
     
	// Not compatible with WebSphere 4.x
	// And it seems not compatible with WebSphere 5.1 either
	/*
	if (!_model.getProperties().isEmpty()) { 
    stringBuffer.append(TEXT_112);
    
			props = _model.getProperties().iterator();
			
			while (props.hasNext()) {
				MdfProperty p = (MdfProperty) props.next();

				if (p instanceof MdfReverseAssociation) {
					continue;
				}
				
				String fieldName = JavaCore.getFieldName(p);
				String cast = "";
				MdfEntity type = p.getType();
				
				if (p instanceof MdfAttribute) {
					if (type instanceof MdfEnumeration) {
						MdfEnumeration e = (MdfEnumeration) type;
						type = e.getType();
					} 
					
					Class javaClass = PrimitivesDomain.getJavaType(type);
					if (javaClass == null) {
						throw new RuntimeException("Invalid Property Type!");
					}

					if (!javaClass.isPrimitive()) {
						cast = "(" + JavaCore.getShortName(javaClass.getName()) + ") ";
					}
				} else {
						cast = "(" + JavaCore.getShortName(MdfGenerationUtil.getTypeName(p)) + ") ";
				}
				
				String initCode = JavaCore.getPropertyInitCode(p);
    stringBuffer.append(TEXT_113);
    stringBuffer.append( fieldName );
    stringBuffer.append(TEXT_114);
    stringBuffer.append( cast );
    stringBuffer.append(TEXT_115);
    stringBuffer.append( fieldName );
    stringBuffer.append(TEXT_116);
    stringBuffer.append( initCode );
    stringBuffer.append(TEXT_117);
    			}

    stringBuffer.append(TEXT_118);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_119);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_120);
     }
    stringBuffer.append(TEXT_121);
      } 
	*/

    stringBuffer.append(TEXT_122);
    return stringBuffer.toString();
  }
}
