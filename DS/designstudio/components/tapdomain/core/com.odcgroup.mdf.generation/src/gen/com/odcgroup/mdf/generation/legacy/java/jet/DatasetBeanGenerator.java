
package com.odcgroup.mdf.generation.legacy.java.jet;

import java.util.*;
import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.mdf.generation.util.MdfGenerationUtil;
import com.odcgroup.mdf.generation.legacy.java.SourceCodeGenerator;
import com.odcgroup.mdf.metamodel.*;

class DatasetBeanGenerator extends SourceCodeGenerator {
 
  protected static String nl;
  public static synchronized DatasetBeanGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    DatasetBeanGenerator result = new DatasetBeanGenerator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "/*" + NL + " * This file has been automatically generated, DO NOT MODIFY!" + NL + " * Date: ";
  protected final String TEXT_3 = NL + " * User: ";
  protected final String TEXT_4 = NL + " */" + NL + "" + NL + "" + NL + "package ";
  protected final String TEXT_5 = ";" + NL + "" + NL + "import java.io.*;" + NL + "" + NL + "import ";
  protected final String TEXT_6 = ".*;" + NL + "import ";
  protected final String TEXT_7 = ".*;" + NL + "" + NL + "import com.odcgroup.mdf.core.*;" + NL;
  protected final String TEXT_8 = NL + NL + "/**" + NL + " * @see ";
  protected final String TEXT_9 = NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_10 = " extends DomainObjectBean " + NL + "\timplements ";
  protected final String TEXT_11 = ", Serializable, Cloneable {" + NL + "" + NL + "\tprivate static final long serialVersionUID = ";
  protected final String TEXT_12 = "L;" + NL + "\t" + NL + "\tprivate ";
  protected final String TEXT_13 = " _objectId;" + NL + "" + NL + "\tprivate ";
  protected final String TEXT_14 = " _permvalues;" + NL + "" + NL + "   //-------------------------------------------------------------------------" + NL + "   // Fields and accessors";
  protected final String TEXT_15 = NL + "\t/**" + NL + "\t * The <em>";
  protected final String TEXT_16 = "</em> property." + NL + "\t * @generated" + NL + "\t */" + NL + "    protected ";
  protected final String TEXT_17 = " ";
  protected final String TEXT_18 = ";" + NL + "" + NL + "\t/**" + NL + "\t * Returns the <em>";
  protected final String TEXT_19 = "</em> property." + NL + "\t * @return the <em>";
  protected final String TEXT_20 = "</em> property." + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_21 = " ";
  protected final String TEXT_22 = "() {" + NL + "\t\treturn this.";
  protected final String TEXT_23 = ";" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Sets the <em>";
  protected final String TEXT_24 = "</em> property." + NL + "\t * @param ";
  protected final String TEXT_25 = " the new value of the <em>";
  protected final String TEXT_26 = "</em> property." + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic void ";
  protected final String TEXT_27 = "(";
  protected final String TEXT_28 = " ";
  protected final String TEXT_29 = ") {" + NL + "\t\tthis.";
  protected final String TEXT_30 = " = ";
  protected final String TEXT_31 = ";" + NL + "\t}";
  protected final String TEXT_32 = NL + NL + "    //-------------------------------------------------------------------------" + NL + "\t// Constructors" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_33 = "() {" + NL + "\t\tthis._objectId = null;" + NL;
  protected final String TEXT_34 = NL + "\t\ttry {";
  protected final String TEXT_35 = NL + "\t\t\tthis.";
  protected final String TEXT_36 = " = ";
  protected final String TEXT_37 = ";\t\t";
  protected final String TEXT_38 = NL + "\t\t} catch (Exception e) {" + NL + "            throw (IllegalArgumentException) new IllegalArgumentException(e.getMessage()).initCause(e);" + NL + "\t\t}\t";
  protected final String TEXT_39 = "\t\t" + NL + "\t}" + NL + "" + NL + "" + NL + "\t/**" + NL + "\t * Copy Constructor" + NL + "\t * @param copy The instance to be copied" + NL + "\t * @generated" + NL + "\t */" + NL + "    public ";
  protected final String TEXT_40 = "(";
  protected final String TEXT_41 = " copy) {" + NL + "\t\tthis._objectId = null;";
  protected final String TEXT_42 = NL + "\t\t\t\tif (copy.";
  protected final String TEXT_43 = "() != null) {" + NL + "\t\t\t\t\tthis.";
  protected final String TEXT_44 = " = new java.util.ArrayList(copy.";
  protected final String TEXT_45 = "());" + NL + "\t\t\t\t} else {" + NL + "\t\t\t\t\tthis.";
  protected final String TEXT_46 = " = null;" + NL + "\t\t\t\t}";
  protected final String TEXT_47 = NL + "\t\t\t\tthis.";
  protected final String TEXT_48 = " = copy.";
  protected final String TEXT_49 = "();";
  protected final String TEXT_50 = NL + "    }" + NL + "" + NL + "\t/**" + NL + "\t * @see com.odcgroup.mdf.core.DomainObjectBean#copyTo(DomainObjectBean)" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic void copyTo(DomainObject copy) {" + NL + "\t\tif (copy instanceof ";
  protected final String TEXT_51 = ") {" + NL + "\t\t\t";
  protected final String TEXT_52 = " other = (";
  protected final String TEXT_53 = ") copy;";
  protected final String TEXT_54 = NL + "\t\t\t\t\tif (this.";
  protected final String TEXT_55 = " != null) {" + NL + "\t\t\t\t\t\tother.";
  protected final String TEXT_56 = "(new java.util.ArrayList(this.";
  protected final String TEXT_57 = "));" + NL + "\t\t\t\t\t} else {" + NL + "\t\t\t\t\t\tother.";
  protected final String TEXT_58 = "(null);" + NL + "\t\t\t\t\t}";
  protected final String TEXT_59 = NL + "\t\t\t\t\tother.";
  protected final String TEXT_60 = "(this.";
  protected final String TEXT_61 = ");";
  protected final String TEXT_62 = NL + "\t\t}" + NL + "\t}" + NL + "" + NL + "   //-------------------------------------------------------------------------" + NL + "\t// com.odcgroup.mdf.core.DomainObject" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     **/" + NL + "    private static final com.odcgroup.mdf.metamodel.MdfName ENTITY_NAME =" + NL + "          com.odcgroup.mdf.metamodel.MdfNameFactory.createMdfName(\"";
  protected final String TEXT_63 = "\",\"";
  protected final String TEXT_64 = "\");" + NL + "" + NL + "    /**" + NL + "     * @see com.odcgroup.mdf.core.DomainObject#getEntityName()" + NL + "     * @generated" + NL + "     **/" + NL + "    public com.odcgroup.mdf.metamodel.MdfName getEntityName() {" + NL + "        return ENTITY_NAME;" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @param objectId this object's global identifier" + NL + "\t * @generated" + NL + "\t */" + NL + "    public ";
  protected final String TEXT_65 = "(";
  protected final String TEXT_66 = " objectId) {" + NL + "        this();" + NL + "        this._objectId = objectId;" + NL + "    }" + NL + "" + NL + "\t/**" + NL + "\t * Copy Constructor" + NL + "\t * @param globalId this object's global identifier" + NL + "\t * @param copy the instance to be copied" + NL + "\t * @generated" + NL + "\t */" + NL + "   public ";
  protected final String TEXT_67 = "(" + NL + "\t\t";
  protected final String TEXT_68 = " objectId," + NL + "\t\t";
  protected final String TEXT_69 = " copy) {" + NL + "\t\tthis(copy);" + NL + "\t\tthis._objectId = objectId;" + NL + "\t}" + NL + "" + NL + "    //-------------------------------------------------------------------------" + NL + "\t// com.odcgroup.mdf.core.ReferenceableDomainObject" + NL + "" + NL + "\t/**" + NL + "\t * @see com.odcgroup.mdf.core.ReferenceableDomainObject#getDomainObject()" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic DomainObject getDomainObject() {" + NL + "\t\treturn this;" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @see com.odcgroup.mdf.core.ReferenceableDomainObject#getObjectId()" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ObjectId getObjectId() {" + NL + "\t\treturn _objectId;" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * @see com.odcgroup.mdf.core.ReferenceableDomainObject#get_oid()" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic Object get_oid() {" + NL + "\t\tObjectId objectId = getObjectId();" + NL + "\t\tif (objectId != null) {" + NL + "\t\t\treturn objectId.getPrimaryKeyValue();" + NL + "\t\t} else {" + NL + "\t\t\treturn null;" + NL + "\t\t}" + NL + "\t}" + NL + "\t" + NL + "\t" + NL + "    //-------------------------------------------------------------------------" + NL + "\t// Perm Values accessors" + NL + "\t" + NL + "\t/**" + NL + "\t * @return the _permvalues" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_70 = " getPermValues() {" + NL + "\t\treturn _permvalues;" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @param _permvalues the _permvalues to set" + NL + "\t */" + NL + "\tpublic void setPermvalues(";
  protected final String TEXT_71 = " permvalues) {" + NL + "\t\tthis._permvalues = permvalues;" + NL + "\t}" + NL + "\t" + NL + "" + NL + "    //-------------------------------------------------------------------------" + NL + "\t// hashCode & equals" + NL + "" + NL + "\t/**" + NL + "\t * Returns a hashCode value for the object." + NL + "\t * It is the hashCode of its object id." + NL + "     *" + NL + "     * @return a hash code value for this object." + NL + "\t * @see java.lang.Object#hashCode()" + NL + "\t * @generated" + NL + "\t */" + NL + "    public int hashCode() {" + NL + "\t\tObjectId oid = getObjectId();" + NL + "\t\tif (oid != null) {" + NL + "\t\t\treturn oid.hashCode();" + NL + "\t\t}" + NL + "" + NL + "\t\treturn 0;" + NL + "    }" + NL + "" + NL + "\t/**" + NL + "\t * Indicates whether some other object is \"equal to\" this one." + NL + "\t * The global ids are compared." + NL + "     *" + NL + "\t * @param obj the reference object with which to compare." + NL + "     * @return <code>true</code> if this object is equals to" + NL + "     * \t\tthe obj argument; <code>false</code> otherwise." + NL + "\t * @see java.lang.Object#equals(java.lang.Object)" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic boolean equals(Object obj) {" + NL + "\t\tif (this == obj) return true;" + NL + "" + NL + "\t\tif (obj instanceof ";
  protected final String TEXT_72 = ") {" + NL + "         \t";
  protected final String TEXT_73 = " other = (";
  protected final String TEXT_74 = ") obj;" + NL + "" + NL + "\t\t\tObjectId oid = getObjectId();" + NL + "\t\t\tObjectId other_oid = other.getObjectId();" + NL + "" + NL + "\t\t\tif (oid == null) {" + NL + "\t\t\t\tif (other_oid != null) return false;" + NL + "\t\t\t} else {" + NL + "\t\t\t\tif (other_oid == null) {" + NL + "\t\t\t\t\treturn false;" + NL + "\t\t\t\t} else {" + NL + "\t\t\t\t\treturn oid.equals(other_oid);" + NL + "\t\t\t\t}" + NL + "\t\t\t}" + NL + "\t\t\t";
  protected final String TEXT_75 = "\t\t\t\t" + NL + "\t\t\t\t\tif (this.";
  protected final String TEXT_76 = " != other.";
  protected final String TEXT_77 = "()) return false;";
  protected final String TEXT_78 = "\t\t\t\t\t \t\t\t\t\t\t\t" + NL + "\t\t\t\t\tif (this.";
  protected final String TEXT_79 = " == null) {" + NL + "\t\t\t\t\t\tif (other.";
  protected final String TEXT_80 = "() != null) return false;" + NL + "\t\t\t\t\t} else {" + NL + "\t\t\t\t\t\tif (!this.";
  protected final String TEXT_81 = ".equals(other.";
  protected final String TEXT_82 = "())) return false;" + NL + "\t\t\t\t\t}";
  protected final String TEXT_83 = NL + NL + "\t\t\treturn true;" + NL + "\t\t}" + NL + "" + NL + "\t\treturn false;" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @see com.odcgroup.mdf.core.DomainObject#isSame(com.odcgroup.mdf.core.DomainObject)" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic boolean isSame(DomainObject obj) {" + NL + "\t\tif (this == obj) return true;" + NL + "" + NL + "\t\tif (obj instanceof ";
  protected final String TEXT_84 = ") {" + NL + "\t\t     \t";
  protected final String TEXT_85 = " other = (";
  protected final String TEXT_86 = ") obj;";
  protected final String TEXT_87 = "\t\t\t\t" + NL + "\t\t\t\t\tif (this.";
  protected final String TEXT_88 = " != other.";
  protected final String TEXT_89 = "()) return false;";
  protected final String TEXT_90 = "\t\t\t\t\t \t\t\t\t\t\t\t" + NL + "\t\t\t\tif (this.";
  protected final String TEXT_91 = " == null) {" + NL + "\t\t\t\t\tif (other.";
  protected final String TEXT_92 = "() != null) return false;" + NL + "\t\t\t\t} else {" + NL + "\t\t\t\t\tif (!this.";
  protected final String TEXT_93 = ".equals(other.";
  protected final String TEXT_94 = "())) return false;" + NL + "\t\t\t\t}";
  protected final String TEXT_95 = NL + NL + "\t\t\treturn true;" + NL + "\t\t}" + NL + "" + NL + "\t\treturn false;" + NL + "\t}" + NL + "" + NL + "\tpublic Object clone() {" + NL + "\t\treturn new ";
  protected final String TEXT_96 = "(_objectId, this);" + NL + "\t}" + NL + "}";

 	private MdfDataset _model;
	
	private DatasetBeanGenerator() {
		throw new UnsupportedOperationException();
	}
	
	public DatasetBeanGenerator(MdfDataset model) {
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
    stringBuffer.append( JavaCore.getModelPackage(_model.getBaseClass()) );
    stringBuffer.append(TEXT_7);
    
String beanClassName = JavaCore.getBeanClassName(_model);
String modelClassName = JavaCore.getModelClassName(_model);
List localProperties = _model.getProperties();
Iterator it = null;

    stringBuffer.append(TEXT_8);
    stringBuffer.append( JavaCore.getQualifiedModelClassName(_model) );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( beanClassName );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( modelClassName );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( JavaCore.getSerialVersionUID(_model) );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( JavaCore.getRefClassName(_model.getBaseClass()) );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( JavaCore.getQualifiedPermValuesClassName(_model) );
    stringBuffer.append(TEXT_14);
        it = localProperties.iterator();
      while (it.hasNext()) {
            MdfDatasetProperty p = (MdfDatasetProperty) it.next();
    stringBuffer.append(TEXT_15);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( JavaCore.getShortName(MdfGenerationUtil.getTypeName(p)) );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_18);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_19);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_20);
    stringBuffer.append( JavaCore.getShortName(MdfGenerationUtil.getTypeName(p)) );
    stringBuffer.append(TEXT_21);
    stringBuffer.append( JavaCore.getGetterName(p) );
    stringBuffer.append(TEXT_22);
    stringBuffer.append( JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_23);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_24);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_25);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_26);
    stringBuffer.append( JavaCore.getSetterName(p) );
    stringBuffer.append(TEXT_27);
    stringBuffer.append( JavaCore.getShortName(MdfGenerationUtil.getTypeName(p)) );
    stringBuffer.append(TEXT_28);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_29);
    stringBuffer.append( JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_30);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_31);
     }
    stringBuffer.append(TEXT_32);
    stringBuffer.append( beanClassName );
    stringBuffer.append(TEXT_33);
    	if (!localProperties.isEmpty()) { 
    stringBuffer.append(TEXT_34);
    
			it = localProperties.iterator();
			while (it.hasNext()) {
				MdfDatasetProperty p = (MdfDatasetProperty) it.next();
				if (p instanceof MdfDatasetDerivedProperty) {
					String initCode = MdfGenerationUtil.getDatasetPropertyInitCode(p);
					if (initCode != null) {
						String fieldName = JavaCore.getFieldName(p);
    stringBuffer.append(TEXT_35);
    stringBuffer.append( fieldName );
    stringBuffer.append(TEXT_36);
    stringBuffer.append( initCode );
    stringBuffer.append(TEXT_37);
    					}
				} 
			} 
    stringBuffer.append(TEXT_38);
     } 
    stringBuffer.append(TEXT_39);
    stringBuffer.append( beanClassName );
    stringBuffer.append(TEXT_40);
    stringBuffer.append( modelClassName );
    stringBuffer.append(TEXT_41);
        it = localProperties.iterator();
		while (it.hasNext()) {
			MdfDatasetProperty p = (MdfDatasetProperty) it.next();
			String fieldName = JavaCore.getFieldName(p);

			if (p.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) { 
    stringBuffer.append(TEXT_42);
    stringBuffer.append( JavaCore.getGetterName(p) );
    stringBuffer.append(TEXT_43);
    stringBuffer.append( fieldName );
    stringBuffer.append(TEXT_44);
    stringBuffer.append( JavaCore.getGetterName(p) );
    stringBuffer.append(TEXT_45);
    stringBuffer.append( fieldName );
    stringBuffer.append(TEXT_46);
    			} else { 
    stringBuffer.append(TEXT_47);
    stringBuffer.append( fieldName );
    stringBuffer.append(TEXT_48);
    stringBuffer.append( JavaCore.getGetterName(p) );
    stringBuffer.append(TEXT_49);
    			}
		} 
    stringBuffer.append(TEXT_50);
    stringBuffer.append( beanClassName );
    stringBuffer.append(TEXT_51);
    stringBuffer.append( beanClassName );
    stringBuffer.append(TEXT_52);
    stringBuffer.append( beanClassName );
    stringBuffer.append(TEXT_53);
           it = localProperties.iterator();
			while (it.hasNext()) {
				MdfDatasetProperty p = (MdfDatasetProperty) it.next();
				String fieldName = JavaCore.getFieldName(p);

			   if (p.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) { 
    stringBuffer.append(TEXT_54);
    stringBuffer.append( fieldName );
    stringBuffer.append(TEXT_55);
    stringBuffer.append( JavaCore.getSetterName(p) );
    stringBuffer.append(TEXT_56);
    stringBuffer.append( fieldName );
    stringBuffer.append(TEXT_57);
    stringBuffer.append( JavaCore.getSetterName(p) );
    stringBuffer.append(TEXT_58);
    				} else { 
    stringBuffer.append(TEXT_59);
    stringBuffer.append( JavaCore.getSetterName(p) );
    stringBuffer.append(TEXT_60);
    stringBuffer.append( fieldName );
    stringBuffer.append(TEXT_61);
    				}
			} 
    stringBuffer.append(TEXT_62);
    stringBuffer.append( _model.getQualifiedName().getDomain() );
    stringBuffer.append(TEXT_63);
    stringBuffer.append( _model.getQualifiedName().getLocalName() );
    stringBuffer.append(TEXT_64);
    stringBuffer.append( beanClassName );
    stringBuffer.append(TEXT_65);
    stringBuffer.append( JavaCore.getRefClassName(_model.getBaseClass()) );
    stringBuffer.append(TEXT_66);
    stringBuffer.append( beanClassName );
    stringBuffer.append(TEXT_67);
    stringBuffer.append( JavaCore.getRefClassName(_model.getBaseClass()) );
    stringBuffer.append(TEXT_68);
    stringBuffer.append( modelClassName );
    stringBuffer.append(TEXT_69);
    stringBuffer.append( JavaCore.getQualifiedPermValuesClassName(_model) );
    stringBuffer.append(TEXT_70);
    stringBuffer.append( JavaCore.getQualifiedPermValuesClassName(_model) );
    stringBuffer.append(TEXT_71);
    stringBuffer.append( modelClassName );
    stringBuffer.append(TEXT_72);
    stringBuffer.append( modelClassName );
    stringBuffer.append(TEXT_73);
    stringBuffer.append( modelClassName );
    stringBuffer.append(TEXT_74);
    			it = localProperties.iterator();
			while (it.hasNext()) {
				MdfDatasetProperty p = (MdfDatasetProperty) it.next();
				if (JavaCore.isJavaPrimitive(MdfGenerationUtil.getTypeName(p))) { 
    stringBuffer.append(TEXT_75);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_76);
    stringBuffer.append( JavaCore.getGetterName(p) );
    stringBuffer.append(TEXT_77);
    				} else { 
    stringBuffer.append(TEXT_78);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_79);
    stringBuffer.append( JavaCore.getGetterName(p) );
    stringBuffer.append(TEXT_80);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_81);
    stringBuffer.append( JavaCore.getGetterName(p) );
    stringBuffer.append(TEXT_82);
    				}
			} 
    stringBuffer.append(TEXT_83);
    stringBuffer.append( modelClassName );
    stringBuffer.append(TEXT_84);
    stringBuffer.append( modelClassName );
    stringBuffer.append(TEXT_85);
    stringBuffer.append( modelClassName );
    stringBuffer.append(TEXT_86);
    			it = localProperties.iterator();
			while (it.hasNext()) {
				MdfDatasetProperty p = (MdfDatasetProperty) it.next();
				if (JavaCore.isJavaPrimitive(MdfGenerationUtil.getTypeName(p))) { 
    stringBuffer.append(TEXT_87);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_88);
    stringBuffer.append( JavaCore.getGetterName(p) );
    stringBuffer.append(TEXT_89);
    				} else { 
    stringBuffer.append(TEXT_90);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_91);
    stringBuffer.append( JavaCore.getGetterName(p) );
    stringBuffer.append(TEXT_92);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_93);
    stringBuffer.append( JavaCore.getGetterName(p) );
    stringBuffer.append(TEXT_94);
    				}
			} 
    stringBuffer.append(TEXT_95);
    stringBuffer.append( beanClassName );
    stringBuffer.append(TEXT_96);
    return stringBuffer.toString();
  }
}
