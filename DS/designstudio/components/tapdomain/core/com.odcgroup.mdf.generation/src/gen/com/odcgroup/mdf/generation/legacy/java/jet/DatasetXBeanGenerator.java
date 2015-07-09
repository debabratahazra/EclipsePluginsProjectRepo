
package com.odcgroup.mdf.generation.legacy.java.jet;

import java.util.*;
import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.mdf.generation.util.MdfGenerationUtil;
import com.odcgroup.mdf.generation.legacy.java.SourceCodeGenerator;
import com.odcgroup.mdf.metamodel.*;

class DatasetXBeanGenerator extends SourceCodeGenerator {
 
  protected static String nl;
  public static synchronized DatasetXBeanGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    DatasetXBeanGenerator result = new DatasetXBeanGenerator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "/*" + NL + " * This file has been automatically generated, DO NOT MODIFY!" + NL + " * Date: ";
  protected final String TEXT_3 = NL + " * User: ";
  protected final String TEXT_4 = NL + " */" + NL + "" + NL + "" + NL + "package ";
  protected final String TEXT_5 = ";" + NL + "" + NL + "import java.io.*;" + NL + "" + NL + "import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;" + NL + "" + NL + "import ";
  protected final String TEXT_6 = ".*;" + NL + "" + NL + "import com.odcgroup.mdf.transform.java.xpath.XPathDatasetSkeleton;" + NL;
  protected final String TEXT_7 = NL + NL + "/**" + NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_8 = " " + NL + "\textends XPathDatasetSkeleton implements ";
  protected final String TEXT_9 = ", Serializable {" + NL + "" + NL + "\tprivate static final long serialVersionUID = ";
  protected final String TEXT_10 = "L;\t" + NL + "\t" + NL + "\tprivate ";
  protected final String TEXT_11 = " _permvalues;" + NL + "" + NL + "\tpublic ";
  protected final String TEXT_12 = "(";
  protected final String TEXT_13 = " domainObject) {" + NL + "\t    super(domainObject);" + NL + "\t    ";
  protected final String TEXT_14 = NL + "\t\ttry {";
  protected final String TEXT_15 = NL + "\t\t\tthis.";
  protected final String TEXT_16 = " = ";
  protected final String TEXT_17 = ";\t\t";
  protected final String TEXT_18 = NL + "\t\t} catch (Exception e) {" + NL + "            throw (IllegalArgumentException) new IllegalArgumentException(e.getMessage()).initCause(e);" + NL + "\t\t}\t";
  protected final String TEXT_19 = "\t\t" + NL + "\t}" + NL + "\t" + NL + "   //-------------------------------------------------------------------------" + NL + "\t// com.odcgroup.mdf.core.DomainObject" + NL + "" + NL + "\tprivate static final com.odcgroup.mdf.metamodel.MdfName ENTITY_NAME = " + NL + "\t\t\tcom.odcgroup.mdf.metamodel.MdfNameFactory.createMdfName(\"";
  protected final String TEXT_20 = "\",\"";
  protected final String TEXT_21 = "\");" + NL + "\t" + NL + "\t/**" + NL + "\t * @see com.odcgroup.mdf.core.DomainObject.getEntityName()" + NL + "    */" + NL + "   public com.odcgroup.mdf.metamodel.MdfName getEntityName() {" + NL + "       return ENTITY_NAME;" + NL + "   }" + NL + "\t";
  protected final String TEXT_22 = "\t    " + NL + "" + NL + "\t/**" + NL + "     * @see ";
  protected final String TEXT_23 = ".";
  protected final String TEXT_24 = NL + "\t * @generated" + NL + "\t */" + NL + "    public ";
  protected final String TEXT_25 = " ";
  protected final String TEXT_26 = "() {" + NL + "        return (";
  protected final String TEXT_27 = ") getPropertyValue((MdfDatasetMappedProperty) ";
  protected final String TEXT_28 = ".";
  protected final String TEXT_29 = ".getProperty(\"";
  protected final String TEXT_30 = "\"));" + NL + "    }" + NL + "    " + NL + "\t/**" + NL + "\t * Sets the <em>";
  protected final String TEXT_31 = "</em> property." + NL + "\t * @param ";
  protected final String TEXT_32 = " the new value of the <em>";
  protected final String TEXT_33 = "</em> property." + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic void ";
  protected final String TEXT_34 = "(";
  protected final String TEXT_35 = " ";
  protected final String TEXT_36 = ") {" + NL + "\t\tthrow new UnsupportedOperationException(\"Setters are not supported by xpath beans\");" + NL + "\t}";
  protected final String TEXT_37 = NL + "\t/**" + NL + "\t * The <em>";
  protected final String TEXT_38 = "</em> property." + NL + "\t * @generated" + NL + "\t */" + NL + "    protected ";
  protected final String TEXT_39 = " ";
  protected final String TEXT_40 = ";" + NL + "" + NL + "\t/**" + NL + "\t * Returns the <em>";
  protected final String TEXT_41 = "</em> property." + NL + "\t * @return the <em>";
  protected final String TEXT_42 = "</em> property." + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_43 = " ";
  protected final String TEXT_44 = "() {" + NL + "\t\treturn this.";
  protected final String TEXT_45 = ";" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Sets the <em>";
  protected final String TEXT_46 = "</em> property." + NL + "\t * @param ";
  protected final String TEXT_47 = " the new value of the <em>";
  protected final String TEXT_48 = "</em> property." + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic void ";
  protected final String TEXT_49 = "(";
  protected final String TEXT_50 = " ";
  protected final String TEXT_51 = ") {" + NL + "\t\tthis.";
  protected final String TEXT_52 = " = ";
  protected final String TEXT_53 = ";" + NL + "\t}";
  protected final String TEXT_54 = "\t" + NL + "" + NL + "" + NL + "    //-------------------------------------------------------------------------" + NL + "\t// Perm Values accessors" + NL + "\t" + NL + "\t/**" + NL + "\t * @return the _permvalues" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_55 = " getPermValues() {" + NL + "\t\treturn _permvalues;" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @param _permvalues the _permvalues to set" + NL + "\t */" + NL + "\tpublic void setPermvalues(";
  protected final String TEXT_56 = " permvalues) {" + NL + "\t\tthis._permvalues = permvalues;" + NL + "\t}" + NL + "" + NL + "}";

 	private MdfDataset _model;
	
	private DatasetXBeanGenerator() {
		throw new UnsupportedOperationException();
	}
	
	public DatasetXBeanGenerator(MdfDataset model) {
		this._model = model;
	}

	public String getClassName() {
		return JavaCore.getQualifiedXBeanClassName(_model);
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
    stringBuffer.append( JavaCore.getXBeanPackage(_model) );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( JavaCore.getModelPackage(_model) );
    stringBuffer.append(TEXT_6);
    
String beanClassName = JavaCore.getBeanClassName(_model);
String modelClassName = JavaCore.getModelClassName(_model);
List localProperties = _model.getProperties();
Iterator it = null;

    stringBuffer.append(TEXT_7);
    stringBuffer.append( beanClassName );
    stringBuffer.append(TEXT_8);
    stringBuffer.append( modelClassName );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( JavaCore.getSerialVersionUID(_model) );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( JavaCore.getQualifiedPermValuesClassName(_model) );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( beanClassName );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( JavaCore.getQualifiedModelClassName(_model.getBaseClass()) );
    stringBuffer.append(TEXT_13);
    	if (!localProperties.isEmpty()) { 
    stringBuffer.append(TEXT_14);
    
			it = localProperties.iterator();
			while (it.hasNext()) {
				MdfDatasetProperty p = (MdfDatasetProperty) it.next();
				if (p instanceof MdfDatasetDerivedProperty) {
					String initCode = MdfGenerationUtil.getDatasetPropertyInitCode(p);
					if (initCode != null) {
						String fieldName = JavaCore.getFieldName(p);
    stringBuffer.append(TEXT_15);
    stringBuffer.append( fieldName );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( initCode );
    stringBuffer.append(TEXT_17);
    					}
				} 
			} 
    stringBuffer.append(TEXT_18);
     } 
    stringBuffer.append(TEXT_19);
    stringBuffer.append( _model.getQualifiedName().getDomain() );
    stringBuffer.append(TEXT_20);
    stringBuffer.append( _model.getQualifiedName().getLocalName() );
    stringBuffer.append(TEXT_21);
    
	Iterator props = _model.getProperties().iterator();
	
	while (props.hasNext()) {
	    MdfDatasetProperty p = (MdfDatasetProperty) props.next();
	    String typeName = MdfGenerationUtil.getTypeName(p);
	    
	    if (p instanceof MdfDatasetMappedProperty) {

    stringBuffer.append(TEXT_22);
    stringBuffer.append( JavaCore.getQualifiedModelClassName(p.getType()) );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( JavaCore.getGetterName(p) );
    stringBuffer.append(TEXT_24);
    stringBuffer.append( typeName );
    stringBuffer.append(TEXT_25);
    stringBuffer.append( JavaCore.getGetterName(p) );
    stringBuffer.append(TEXT_26);
    stringBuffer.append( typeName );
    stringBuffer.append(TEXT_27);
    stringBuffer.append( JavaCore.getDomainModelClassName(_model.getParentDomain()) );
    stringBuffer.append(TEXT_28);
    stringBuffer.append( JavaCore.getEntityModelInstanceName(_model) );
    stringBuffer.append(TEXT_29);
    stringBuffer.append( p.getName() );
    stringBuffer.append(TEXT_30);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_31);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_32);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_33);
    stringBuffer.append( JavaCore.getSetterName(p) );
    stringBuffer.append(TEXT_34);
    stringBuffer.append( JavaCore.getShortName(MdfGenerationUtil.getTypeName(p)) );
    stringBuffer.append(TEXT_35);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_36);
    
	     } else {

    stringBuffer.append(TEXT_37);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_38);
    stringBuffer.append( JavaCore.getShortName(MdfGenerationUtil.getTypeName(p)) );
    stringBuffer.append(TEXT_39);
    stringBuffer.append( JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_40);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_41);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_42);
    stringBuffer.append( JavaCore.getShortName(MdfGenerationUtil.getTypeName(p)) );
    stringBuffer.append(TEXT_43);
    stringBuffer.append( JavaCore.getGetterName(p) );
    stringBuffer.append(TEXT_44);
    stringBuffer.append( JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_45);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_46);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_47);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_48);
    stringBuffer.append( JavaCore.getSetterName(p) );
    stringBuffer.append(TEXT_49);
    stringBuffer.append( JavaCore.getShortName(MdfGenerationUtil.getTypeName(p)) );
    stringBuffer.append(TEXT_50);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_51);
    stringBuffer.append( JavaCore.getFieldName(p));
    stringBuffer.append(TEXT_52);
    stringBuffer.append( JavaCore.getFieldName(p) );
    stringBuffer.append(TEXT_53);
           }
   }
    stringBuffer.append(TEXT_54);
    stringBuffer.append( JavaCore.getQualifiedPermValuesClassName(_model) );
    stringBuffer.append(TEXT_55);
    stringBuffer.append( JavaCore.getQualifiedPermValuesClassName(_model) );
    stringBuffer.append(TEXT_56);
    return stringBuffer.toString();
  }
}
