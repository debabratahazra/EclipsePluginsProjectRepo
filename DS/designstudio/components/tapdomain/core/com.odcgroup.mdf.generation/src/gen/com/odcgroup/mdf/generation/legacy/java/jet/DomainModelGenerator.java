
package com.odcgroup.mdf.generation.legacy.java.jet;

import java.util.*;
import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.mdf.generation.legacy.java.SourceCodeGenerator;
import com.odcgroup.mdf.metamodel.*;

class DomainModelGenerator extends SourceCodeGenerator {

  protected static String nl;
  public static synchronized DomainModelGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    DomainModelGenerator result = new DomainModelGenerator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "/*" + NL + " * This file has been automatically generated, DO NOT MODIFY!" + NL + " * Date: ";
  protected final String TEXT_3 = NL + " * User: ";
  protected final String TEXT_4 = NL + " */" + NL + "" + NL + "" + NL + "package ";
  protected final String TEXT_5 = ";" + NL + "" + NL + "import org.slf4j.Logger;" + NL + "import org.slf4j.LoggerFactory;" + NL + "" + NL + "import com.odcgroup.mdf.*;" + NL + "import com.odcgroup.mdf.metamodel.*;" + NL + "import com.odcgroup.mdf.utils.*;" + NL + "" + NL + "/**" + NL + " * @generated" + NL + " */" + NL + "public final class ";
  protected final String TEXT_6 = " extends MdfCoreRepository {" + NL + "" + NL + "\tprivate static final Logger LOGGER = LoggerFactory.getLogger(";
  protected final String TEXT_7 = ".class);" + NL + "" + NL + "\tpublic static final MdfDomain ";
  protected final String TEXT_8 = ";\t";
  protected final String TEXT_9 = NL + NL + "\tpublic static final MdfClass ";
  protected final String TEXT_10 = ";";
  protected final String TEXT_11 = NL + NL + "\tpublic static final MdfEnumeration ";
  protected final String TEXT_12 = ";";
  protected final String TEXT_13 = NL + NL + "\tpublic static final MdfDataset ";
  protected final String TEXT_14 = ";";
  protected final String TEXT_15 = NL + NL + "\tstatic {" + NL + "\t\ttry {" + NL + "\t\t\t";
  protected final String TEXT_16 = " = loadDomain(\"";
  protected final String TEXT_17 = "\", " + NL + "\t\t\t\t";
  protected final String TEXT_18 = ".class.getClassLoader(), " + NL + "\t\t\t\tnew Slf4jMessageHandler(LOGGER));" + NL + "\t\t\t";
  protected final String TEXT_19 = NL + "\t\t\t";
  protected final String TEXT_20 = " = (MdfClass) DOMAIN.getEntity(\"";
  protected final String TEXT_21 = "\");";
  protected final String TEXT_22 = NL + "\t\t\t";
  protected final String TEXT_23 = " = (MdfEnumeration) DOMAIN.getEntity(\"";
  protected final String TEXT_24 = "\");";
  protected final String TEXT_25 = " " + NL + "\t\t\t";
  protected final String TEXT_26 = " = (MdfDataset) DOMAIN.getDataset(\"";
  protected final String TEXT_27 = "\");";
  protected final String TEXT_28 = "  " + NL + "\t\t} catch (Exception e) {" + NL + "\t\t\tLOGGER.error(\"Could not load domain model ";
  protected final String TEXT_29 = "\", e);" + NL + "\t\t\tthrow new RuntimeException(\"Could not load domain model ";
  protected final String TEXT_30 = "\", e);" + NL + "\t\t}" + NL + "\t}" + NL + "\t" + NL + "\t///CLOVER:OFF" + NL + "\tprivate ";
  protected final String TEXT_31 = "() {" + NL + "\t\tsuper();" + NL + "\t} \t" + NL + "\t///CLOVER:ON" + NL + "\t" + NL + "}";

 	private MdfDomain _model;
	
	private DomainModelGenerator() {
		throw new UnsupportedOperationException();
	}
	
	public DomainModelGenerator(MdfDomain model) {
		this._model = model;
	}

	public String getClassName() {
		return JavaCore.getModelPackage(_model) + "." + JavaCore.getDomainModelClassName(_model);
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
    stringBuffer.append( JavaCore.getDomainModelClassName(_model) );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( JavaCore.getDomainModelClassName(_model) );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( JavaCore.getEntityModelInstanceName(_model) );
    stringBuffer.append(TEXT_8);
    	Iterator entities = _model.getEntities().iterator();
	while (entities.hasNext()) {
		MdfEntity entity = (MdfEntity) entities.next();
		if (entity instanceof MdfClass) {
    stringBuffer.append(TEXT_9);
    stringBuffer.append( JavaCore.getEntityModelInstanceName(entity) );
    stringBuffer.append(TEXT_10);
    		} else if (entity instanceof MdfEnumeration) {
    stringBuffer.append(TEXT_11);
    stringBuffer.append( JavaCore.getEntityModelInstanceName(entity) );
    stringBuffer.append(TEXT_12);
    		}
	}

	Iterator datasets = _model.getDatasets().iterator();
	while (datasets.hasNext()) {
		MdfDataset dataset = (MdfDataset) datasets.next(); 
    stringBuffer.append(TEXT_13);
    stringBuffer.append( JavaCore.getEntityModelInstanceName(dataset) );
    stringBuffer.append(TEXT_14);
    	} 
    stringBuffer.append(TEXT_15);
    stringBuffer.append( JavaCore.getEntityModelInstanceName(_model) );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( _model.getName() );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( JavaCore.getDomainModelClassName(_model) );
    stringBuffer.append(TEXT_18);
    
	entities = _model.getEntities().iterator();
	while (entities.hasNext()) {
		MdfEntity entity = (MdfEntity) entities.next();
		if (entity instanceof MdfClass) {
    stringBuffer.append(TEXT_19);
    stringBuffer.append( JavaCore.getEntityModelInstanceName(entity) );
    stringBuffer.append(TEXT_20);
    stringBuffer.append( entity.getName() );
    stringBuffer.append(TEXT_21);
    		} else if (entity instanceof MdfEnumeration) {
    stringBuffer.append(TEXT_22);
    stringBuffer.append( JavaCore.getEntityModelInstanceName(entity) );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( entity.getName() );
    stringBuffer.append(TEXT_24);
    		}
	} 

	datasets = _model.getDatasets().iterator();
	while (datasets.hasNext()) {
		MdfDataset dataset = (MdfDataset) datasets.next(); 
    stringBuffer.append(TEXT_25);
    stringBuffer.append( JavaCore.getEntityModelInstanceName(dataset) );
    stringBuffer.append(TEXT_26);
    stringBuffer.append( dataset.getName() );
    stringBuffer.append(TEXT_27);
    	} 
    stringBuffer.append(TEXT_28);
    stringBuffer.append( _model.getName() );
    stringBuffer.append(TEXT_29);
    stringBuffer.append( _model.getName() );
    stringBuffer.append(TEXT_30);
    stringBuffer.append( JavaCore.getDomainModelClassName(_model) );
    stringBuffer.append(TEXT_31);
    return stringBuffer.toString();
  }
}
