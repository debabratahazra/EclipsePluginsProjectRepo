
package com.odcgroup.mdf.generation.legacy.java.jet;

import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.mdf.generation.legacy.java.SourceCodeGenerator;
import com.odcgroup.mdf.metamodel.*;

class DomainBeanGenerator extends SourceCodeGenerator {

  protected static String nl;
  public static synchronized DomainBeanGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    DomainBeanGenerator result = new DomainBeanGenerator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "/*" + NL + " * This file has been automatically generated, DO NOT MODIFY!" + NL + " * Date: ";
  protected final String TEXT_3 = NL + " * User: ";
  protected final String TEXT_4 = NL + " */" + NL + "" + NL + "" + NL + "package ";
  protected final String TEXT_5 = ";" + NL + "" + NL + "import java.io.*;" + NL + "" + NL + "import com.odcgroup.mdf.core.*;" + NL + "" + NL + "/**" + NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_6 = " " + NL + "\textends DomainObjectExtentBean implements Serializable {" + NL + "\t" + NL + "\tprivate static final long serialVersionUID = ";
  protected final String TEXT_7 = "L;" + NL + "\t" + NL + "\t/**" + NL + "\t * Default constructor" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_8 = "() {" + NL + "\t\tsuper(\"";
  protected final String TEXT_9 = "\");" + NL + "\t} \t" + NL + "}";

 	private MdfDomain _model;
	
	private DomainBeanGenerator() {
		throw new UnsupportedOperationException();
	}
	
	public DomainBeanGenerator(MdfDomain model) {
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
    stringBuffer.append( JavaCore.getBeanClassName(_model) );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( JavaCore.getSerialVersionUID(_model) );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( JavaCore.getBeanClassName(_model) );
    stringBuffer.append(TEXT_8);
    stringBuffer.append(_model.getName());
    stringBuffer.append(TEXT_9);
    return stringBuffer.toString();
  }
}
