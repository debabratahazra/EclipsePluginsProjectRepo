package com.odcgroup.cdm.generation.util;

import java.util.HashMap;
import java.util.Map;

import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfPrimitive;

/**
 * Constants for CDM.
 * 
 * @author Gary Hayes
 */
public class CdmConstants {

    /** valid cdm domain names that we are building. */
    public static final String[] VALID_CDM_DOMAINS = {"CDMAPP"};

    /** The domain file extension. */
    public static final String DOMAIN_FILE_EXTENSION = "domain";

    /** The mml file extension. */
    public static final String MML_FILE_EXTENSION = "mml";

    // Start annotations related to the SQL name
    // **************************************
    /** The namespace containing the SQL name. */
    public static final String SQL_NAMESPACE = "http://www.odcgroup.com/mdf/sql";

    /**
     * The name of the annotation containing the sql table / column name
     * information.
     */
    public static final String SQL_ANNOTATION_NAME = "SQLName";
    // End annotations related to the SQL name
    // ****************************************

    // Start annotations related to Custom Fields
    // **************************************
    /** The namespace for custom fields. */
    public static final String CUSTOM_FIELD_NAMESPACE = "http://www.odcgroup.com/mdf/customization";

    /** The name of the annotation containing the custom field information. */
    public static final String CUSTOM_FIELD_ANNOTATION_NAME = "Custom";
    // End annotations related to Custom Fields
    // ****************************************

    // Start annotations related to GCL
    // **************************************
    /** The namespace for gcl. */
    public static final String GCL_NAMESPACE = "http://www.odcgroup.com/mdf/gcl";

    /** The name of the annotation containing the gcl information. */
    public static final String GCL_ANNOTATION_NAME = "CDMName";
    // End annotations related to GCL
    // ****************************************

    // Start annotations related to CDM back end
    // **************************************
    /** The namespace for CDM back end. */
    public static final String CDM_BACKEND_NAMESPACE = "http://www.odcgroup.com/mdf/back";

    /**
     * The name of the annotation containing the corresponding back end entity
     * name.
     */
    public static final String CDM_BACKEND_ENTITY_ANNOTATION_NAME = "BackEndEntity";

    /** The name of the annotation containing the getter instruction. */
    public static final String CDM_BACKEND_GETTER_ANNOTATION_NAME = "GetterInstruction";

    /** The name of the annotation containing the setter instruction. */
    public static final String CDM_BACKEND_SETTER_ANNOTATION_NAME = "SetterInstruction";
    // End annotations related to CDM back end
    // ****************************************

    // Start custom field enumeration annotation information
    // **************************
    /** The namespace for cmd configuration file information. */
    public static final String CDM_NAMESPACE = "http://www.odcgroup.com/mdf/cdm";

    /** The name of the annotation for CDM-specific information. */
    public static final String CDM_ANNOTATION_NAME = "CDM";

    /** The name of the annotation containing the Xml Element name. */
    public static final String CDM_XML_ANNOTATION_NAME = "xml";

    /** The name of the annotation containing the empty item. */
    public static final String CDM_EMPTY_ITEM_ANNOTATION_NAME = "emptyItem";

    /** The name of the annotation property containing the key. */
    public static final String CDM_KEY_ANNOTATION_NAME = "key";

    /** The name of the annotation property containing the text content. */
    public static final String CDM_TEXT_CONTENT_ANNOTATION_NAME = "text";

    /** The name of the annotation for CDM custom fields not MML standard type. */
    public static final String CDM_ANNOTATION_CUSTOMFIELDS_TYPE = "cdmcustomfieldstype";
    
    // End custom field enumeration annotation information
    // ****************************

    /** The custom field / mdf primitive type bindings. */
    public static Map<String, TypeBinding> MDF_TYPE_BINDINGS;

    /** The custom field / mdf primitive type bindings. */
    public static Map<String, TypeBinding> CUSTOM_FIELD_TYPE_BINDINGS;
    
    static {
		MDF_TYPE_BINDINGS = new HashMap<String, TypeBinding>();
		CUSTOM_FIELD_TYPE_BINDINGS = new HashMap<String, TypeBinding>();
	
		addBinding(new TypeBinding("java.lang.String",    PrimitivesDomain.DOMAIN.getPrimitive("string")));
		addBinding(new TypeBinding("java.util.Date",      PrimitivesDomain.DOMAIN.getPrimitive("date")));	
		addBinding(new TypeBinding("java.lang.Integer",   PrimitivesDomain.DOMAIN.getPrimitive("Integer")));
		addBinding(new TypeBinding("java.lang.Double",    PrimitivesDomain.DOMAIN.getPrimitive("Double")));
		addBinding(new TypeBinding("java.lang.Boolean",   PrimitivesDomain.DOMAIN.getPrimitive("Boolean")));
		addBinding(new TypeBinding("java.lang.Byte",      PrimitivesDomain.DOMAIN.getPrimitive("Byte")));
		addBinding(new TypeBinding("java.lang.Long",      PrimitivesDomain.DOMAIN.getPrimitive("Long")));
		addBinding(new TypeBinding("java.lang.Character", PrimitivesDomain.DOMAIN.getPrimitive("Character")));
		addBinding(new TypeBinding("java.lang.Float",     PrimitivesDomain.DOMAIN.getPrimitive("Float")));
		addBinding(new TypeBinding("java.lang.Short",     PrimitivesDomain.DOMAIN.getPrimitive("Short")));
		
		addBinding(new TypeBinding("int",      PrimitivesDomain.DOMAIN.getPrimitive("integer")));
		addBinding(new TypeBinding("double",   PrimitivesDomain.DOMAIN.getPrimitive("double")));
		addBinding(new TypeBinding("boolean",  PrimitivesDomain.DOMAIN.getPrimitive("boolean")));
		addBinding(new TypeBinding("byte",     PrimitivesDomain.DOMAIN.getPrimitive("byte")));
		addBinding(new TypeBinding("long",     PrimitivesDomain.DOMAIN.getPrimitive("long")));
		addBinding(new TypeBinding("char",     PrimitivesDomain.DOMAIN.getPrimitive("char")));
		addBinding(new TypeBinding("float",    PrimitivesDomain.DOMAIN.getPrimitive("float")));
		addBinding(new TypeBinding("short",    PrimitivesDomain.DOMAIN.getPrimitive("short")));
		addBinding(new TypeBinding("date",     PrimitivesDomain.DOMAIN.getPrimitive("date")));
		addBinding(new TypeBinding("dateTime", PrimitivesDomain.DOMAIN.getPrimitive("dateTime")));
		addBinding(new TypeBinding("string",   PrimitivesDomain.DOMAIN.getPrimitive("string")));
		addBinding(new TypeBinding("URI",      PrimitivesDomain.DOMAIN.getPrimitive("URI")));
		addBinding(new TypeBinding("uri",      PrimitivesDomain.DOMAIN.getPrimitive("URI")));
    }

    /**
     * Adds a TypeBinding to the bindings maps.
     * 
     * @param binding The TypeBinding to add
     */
    private static void addBinding(TypeBinding binding) {
		MDF_TYPE_BINDINGS.put(binding.mdfType.getName(), binding);
		CUSTOM_FIELD_TYPE_BINDINGS.put(binding.customFieldType, binding);
    }

    /**
     * The type binding.
     * 
     * @author Gary Hayes
     */
    public static class TypeBinding {

		/** The custom field type. */
		public String customFieldType;
	
		/** The MDF Type. */
		public MdfPrimitive mdfType;
	
		/**
		 * Creates a new TypeBinding.
		 * 
		 * @param customFieldType The custom field type
		 * @param mdfType The mdf type
		 */
		public TypeBinding(String customFieldType, MdfPrimitive mdfType) {
		    this.customFieldType = customFieldType;
		    this.mdfType = mdfType;
		}
    }

}