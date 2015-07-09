package com.odcgroup.cdm.generation.custo.generator;


import static com.odcgroup.cdm.generation.util.CdmConstants.CUSTOM_FIELD_ANNOTATION_NAME;
import static com.odcgroup.cdm.generation.util.CdmConstants.CUSTOM_FIELD_NAMESPACE;
import static com.odcgroup.cdm.generation.util.CdmConstants.CUSTOM_FIELD_TYPE_BINDINGS;
import static com.odcgroup.cdm.generation.util.CdmConstants.SQL_ANNOTATION_NAME;
import static com.odcgroup.cdm.generation.util.CdmConstants.SQL_NAMESPACE;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.odcgroup.cdm.generation.util.CdmConstants;
import com.odcgroup.cdm.generation.util.MdfUtils;
import com.odcgroup.cdm.generation.util.CdmConstants.TypeBinding;
import com.odcgroup.mdf.ecore.MdfAnnotationImpl;
import com.odcgroup.mdf.ecore.MdfAnnotationPropertyImpl;
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfEntity;
/**
 * Adds custom fields from the cfdef-cdm.conf file to the Domain Model.
 * </br>
 * Syntax:
 * 
 * <pre>
 * Table_Name:
 * Custom_Field_Name, Custom_Field_Type
 * Custom_Field_Name2, Custom_Field_Type2
 * 
 * Table_Name2: ...
 * </pre>
 * 
 * @author Gary Hayes
 */
public class CustomFieldsReader {

	/** The Reader. */
	private final BufferedReader reader;

	/** The list of messages. */
	private List<String> messages;	

	/**
	 * Creates a new CustomFieldsReader.
	 * 
	 * @param fileName
	 *            The fully-qualified name of the files
	 */
	public CustomFieldsReader(String fileName) {
		try {
			reader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException(fnfe);
		}
	}

	/**
	 * Adds the custom fields to the domain model.
	 */
	public List<String> importFields(MdfDomainImpl domain) {
		messages = new ArrayList<String>();
		try {
			MdfClass currentClazz = null;
			String s;
			while ((s = reader.readLine()) != null) {
				s = s.trim();

				if (s.endsWith(":")) {
					// New table
					String tableName = s.substring(0, s.length() - 1).toUpperCase();
					MdfEntity mdfe = findByTable(domain, tableName);

					if (mdfe == null) {
					    mdfe = addClass(domain, tableName);
					}

					String entityName = mdfe.getName();
					currentClazz = domain.getClass(entityName);
				} else if (s.length() == 0) {
					// Ignore
				} else {					
					// Custom field
					String[] ss = s.split(",");
					String fieldName = ss[0].trim();
					String fieldType = ss[1].trim();

					if (currentClazz == null) {
					    messages.add("Unable to add the custom field '" + fieldName + "' to a table");
						continue;
					}
					
					addCustomField(currentClazz, fieldName, fieldType);
				}
			}
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		return messages;
	}

	/**
	 * Adds the custom field to the domain class.
	 * 
	 * @param clazz
	 *            The MdfClass to add the custom field to
	 * @param fieldName
	 *            The name of the field
	 * @param fieldType
	 *            The field type
	 */
	@SuppressWarnings("unchecked")
	private void addCustomField(MdfClass clazz, String fieldName, String fieldType) {
		MdfAttributeImpl a = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute();
		a.setName(fieldName);

		clazz.getProperties().add(a);

		MdfAnnotation an = MdfUtils.createMdfAnnotation(CUSTOM_FIELD_NAMESPACE, CUSTOM_FIELD_ANNOTATION_NAME);
		a.getAnnotations().add(an);
		
		TypeBinding tb = CUSTOM_FIELD_TYPE_BINDINGS.get(fieldType);
		if (tb == null) {
		    messages.add("Class: " + clazz.getName() + ". Unable to create the attribute type for field: " + fieldName);
		} else {
		    a.setType(tb.mdfType);
		}
			
	}
	
	/**
	 * Adds a Class for an unknown entity.
	 * 
	 * @param tableName The name of the physical database table
	 * @return MdfClass The MdfClass
	 */
	@SuppressWarnings("unchecked")
	private MdfClass addClass(MdfDomainImpl domain, String tableName) {
	   // Remove the cdm_
        String entityName = tableName.substring(4);
        messages.add("Unknown table: " + tableName + ". Creating Class: " + entityName);
        MdfClassImpl clazz = (MdfClassImpl) MdfFactory.eINSTANCE.createMdfClass();
        clazz.setName(entityName);
        domain.getClasses().add(clazz);
        
        MdfAnnotationImpl an = (MdfAnnotationImpl) MdfFactory.eINSTANCE.createMdfAnnotation();
        an.setNamespace(CdmConstants.SQL_NAMESPACE);
        an.setName(CdmConstants.SQL_ANNOTATION_NAME);
        clazz.getAnnotations().add(an);
        
        MdfAnnotationPropertyImpl ap = (MdfAnnotationPropertyImpl) MdfFactory.eINSTANCE.createMdfAnnotationProperty();
        ap.setName("value");
        ap.setValue(tableName.toLowerCase()); 
        an.getProperties().add(ap);
       
        return clazz;
	}
	
	/**
	 * Finds the entity which matches the specified table name.
	 * 
	 * @param tableName The name of the Table
	 * @return MdfEntity or null if no entity could be found
	 */
	@SuppressWarnings( "rawtypes" )
	private MdfEntity findByTable(MdfDomainImpl domain, String tableName) {
		for (Iterator it = domain.getEntities().iterator(); it.hasNext(); ) {
			MdfEntity e = (MdfEntity) it.next();
			MdfAnnotation a = e.getAnnotation(SQL_NAMESPACE, SQL_ANNOTATION_NAME);
			if (a == null) {
				continue;
			}
			String value = a.getProperty("value").getValue();
			if (tableName.equalsIgnoreCase(value)) {
				return e;
			}
		}
		
		// Not found
		return null;
	}
}