package com.odcgroup.cdm.generation.custo.generator;

import static com.odcgroup.cdm.generation.util.CdmConstants.CUSTOM_FIELD_ANNOTATION_NAME;
import static com.odcgroup.cdm.generation.util.CdmConstants.CUSTOM_FIELD_NAMESPACE;
import static com.odcgroup.cdm.generation.util.CdmConstants.SQL_ANNOTATION_NAME;
import static com.odcgroup.cdm.generation.util.CdmConstants.SQL_NAMESPACE;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.odcgroup.cdm.generation.CdmOawGeneratorHelper;
import com.odcgroup.cdm.generation.util.CdmConstants;
import com.odcgroup.cdm.generation.util.MdfUtils;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;

/**
 * Writes the custom fields file from the domain model.
 * 
 * @author Gary Hayes
 */
public class CustomFieldsWriter {

	protected static final String AnnotationsAspect_NAMESPACE_URI = "http://www.odcgroup.com/mdf/ext/annotations";
	protected static final String AnnotationsAspect_GENERIC_ANNOTATIONS = "GenericAnnotations";
	
	/** The domain model. */
	private final MdfDomain domain;

	/** The output file path. */
	private String outputFilePath;	

	/**
	 * Creates a new CustomFieldsWriter.
	 * 
	 * @param domain
	 *            The Domain Model
	 * @param outputFilePath The output file path
	 */
	public CustomFieldsWriter(MdfDomain domain, String outputFilePath) {
		this.domain = domain;
		this.outputFilePath = outputFilePath;
	}
	
	/**
	 * Writes the file.
	 */
	public void write() {
		try {
			String configFolder = "/META-INF/config/";
			FileUtils.forceMkdir(new File(outputFilePath + configFolder));
			Document doc = processDomain();
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(new File(outputFilePath + configFolder + "cfdef-cdm.xml")));
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Writes all the custom fields for all the classes in the domain.
	 */
	@SuppressWarnings("unchecked")
	private Document processDomain() {
		Namespace gclCfDefNamespace = Namespace.getNamespace("gclCfDef", "http://www.odcgroup.com/gclCfDef" );
		Element root = new Element("custom-fields-definition", gclCfDefNamespace);
		Namespace xsiNamespace = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.addNamespaceDeclaration(gclCfDefNamespace);
		root.addNamespaceDeclaration(xsiNamespace);
		root.setAttribute("schemaLocation", "http://www.odcgroup.com/gclCfDef gcl-cfdef.xsd ", xsiNamespace);

		Document doc = new Document(root);

		for (MdfClass clazz : (List<MdfClass>)domain.getClasses()) {
		    String tableName = MdfUtils.getAnnotationPropertyValue(clazz, SQL_NAMESPACE, SQL_ANNOTATION_NAME, "value");    
		    if (StringUtils.isEmpty(tableName)) {
		        // The class cannot have custom fields because it does not really exist in the database
		        continue;
		    }
		    
			List<MdfProperty> customProperties = getCustomProperties(clazz);
			if (customProperties.size() > 0) {
				Element entity = new Element("entity");
				entity.setAttribute("sqlName", tableName);
				root.addContent(entity);
				
				for (MdfProperty p : customProperties) {
					Element customField = new Element("custom-field");
					customField.setAttribute("name", p.getName());
					customField.setAttribute("type", getCustomFieldType(p));
					entity.addContent(customField);
				}
			}
		}
		
		return doc;
	}

	@SuppressWarnings("unchecked")
	private List<MdfProperty> getCustomProperties(MdfClass clazz) {
		List<MdfProperty> customProperties = new ArrayList<MdfProperty>();
		
		for (MdfProperty p : (List<MdfProperty>)clazz.getProperties()) {
			MdfAnnotation ca = p.getAnnotation(CUSTOM_FIELD_NAMESPACE, CUSTOM_FIELD_ANNOTATION_NAME);
			if (ca != null) {
				customProperties.add(p);
			}
		}
		return customProperties;
	}
	
	private String getCustomFieldType(MdfProperty property) {
		String type = null;
		boolean propertyTypeNotFound = true;
		
		// check if cdmcustomfieldstype annotation property is set on the field.
		// In this case, the value of this property will be used as type for the current CDM cf definition type 
		MdfAnnotation customAnnatation = property.getAnnotation(AnnotationsAspect_NAMESPACE_URI, AnnotationsAspect_GENERIC_ANNOTATIONS);
		if (customAnnatation !=null) {
			MdfAnnotationProperty cdmCustomFieldsTypeProperty = customAnnatation.getProperty(CdmConstants.CDM_ANNOTATION_CUSTOMFIELDS_TYPE);
			
			if (cdmCustomFieldsTypeProperty != null) {
				type = cdmCustomFieldsTypeProperty.getValue();
				if ((type != null) && (type.trim().length() >0)) {
					propertyTypeNotFound = false;
				}
			}
		}
		
		if (propertyTypeNotFound) {
			MdfPrimitive mdfPrimitive = null;
			if (property.getType().isPrimitiveType() && property.getType() instanceof MdfEnumeration){
				MdfEnumeration enumeration = (MdfEnumeration)property.getType();
				mdfPrimitive = enumeration.getType();
				type = getJavaType(mdfPrimitive);
			} else {
				if (property.getType() instanceof MdfPrimitive) {
					mdfPrimitive = (MdfPrimitive) property.getType();
					type = getJavaType(mdfPrimitive);
				} else {
					type = property.getType().getQualifiedName().getLocalName();
				}
			}		
		}

		return type;			
	}

	/**
	 * DS-1647 
	 * makes use of type translation from CdmOawGeneratorHelper
	 * @param primitive
	 * @return
	 */
	private String getJavaType(MdfPrimitive primitive) {
		String mmlType = primitive.getQualifiedName().toString();
		return CdmOawGeneratorHelper.translateMMLTypeInJavaType(mmlType);
	}

}
