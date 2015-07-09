package com.odcgroup.page.transformmodel.cdm;

/**
 * Interface for the definition of the cdm constants.
 * 
 * @author Gary Hayes
 */
 public interface CdmConstants {

	/** The namespace Uri for CdmComp. */
	String  CDMCOMP_NAMESPACE_URI = "http://www.odcgroup.com/cdm/cdmcomp/0.1";
	
	/** The namespace Uri for CdmTypes. */
	String  CDMTYPES_NAMESPACE_URI = "http://www.odcgroup.com/cdm/cdmtypes/0.1";	
	
	/** The cdpcomp:generic element name. */
	String CDM_GENERIC_ELEMENT = "generic";	
	
	/** The name of the width property. */
	String CDM_CLASS_ATTRIBUTE = "class";	
	
	/** The name of the cType property. */
	String CDM_CTYPE_ATTRIBUTE = "ctype";
	
	/** The name of the format property. */
	String CDM_FORMAT_ATTRIBUTE = "format";

	/** The name of the height property. */
	String CDM_HEIGHT_ATTRIBUTE = "height";
	
	/** The name of the width property. */
	String CDM_WIDTH_ATTRIBUTE = "width";	

	/** The name of the translate key property. */
	String CDM_TRANSLATE_KEY_ATTRIBUTE = "translatekey";	
	
	/** The name of the label property. */
	String CDM_LABEL_ATTRIBUTE = "label";	
	
	/** The cdpcomp:get-Enum element name. */
	String CDM_GET_ENUM_ELEMENT = "get-Enum";
	
	/** The cdpcomp:get-Enum prefix attribute name. */
	String CDM_PREFIX_ATTRIBUTE = "prefix";
	
	/** The cdpcomp:get-Enum postfiy attribute name. */
	String CDM_POSTFIX_ATTRIBUTE = "postfix";
	
	/** The path attribute name. */
	String CDM_PATH_ATTRIBUTE = "path";	
	
	/** The name of the key property. */
	String CDM_KEY_ATTRIBUTE = "key";	
	
	/** The name of the name property. */
	String CDM_NAME_ATTRIBUTE = "name";	
	
    /** The name of the property property. */
    String CDM_PROPERTY_ATTRIBUTE = "property"; 	
	
	/** The name of param element. */
	String CDMCOMP_PARAM_ELEMENT = "param";    
	
	/** The cdmcomp table model tag. */
	String CDMCOMP_TABLE_MODEL = "table-model";
	
	/** The cdmcomp table column tag. */
	String CDMCOMP_TABLE_COLUMN = "table-column";
	
	/** The cdmcomp action tag. */
	String CDMCOMP_COLUMN_ACTION = "column-action"; 
	
	/** The cdmcomp column decorator tag. */
	String CDMCOMP_COLUMN_DECORATOR = "column-decorator";
	
	/** The cdmcomp column difference tag. */
	String CDMPCOMP_COLUMN_DIFFERENCE = "column-difference";
	
	/** The attribute actionToCheck. */
	String CDMCOMP_ACTION_TO_CHECK = "actionToCheck";
		
	/** The attribute property. */
	String CDM_PROPERTY = "property";
	
	/** The attribute decorator. */
	String CDM_DECORATOR_ATTRIBUTE = "decorator";
	
	/** The attribute trueResult. */
	String CDM_TRUE_RESULT = "trueResult";
	
	/** The attribute falseResult. */
	String CDM_FALSE_RESULT = "falseResult";
	
	/** The attribute onAdded. */
	String CDM_ON_ADDED = "onAdded";
	
	/** The attribute onRemoved. */
	String CDM_ON_REMOVED = "onRemoved";
	
	/** The attribute onCommon. */
	String CDM_ON_COMMON = "onCommon";
	
	/** The name of the enum type property. */
	String CDM_ENUM_TYPE = "enumType";
	
	/** The name of the decorator property PropertyType. */
	String CDM_DECORATOR_PROPERTY = "columnDecoratorProperty";
	
	/** The name of the decorator attribute. */
	String CDM_DECORATOR = "columnDecorator";
	
	/** The name of the column-property PropertyType. */
	String CDM_PROPERTY_PROPERTY_TYPE = "columnProperty";
	
	/** The name of the column-property name property. */
	String CDM_PROPERTY_NAME = "columnPropertyName";
	
	/** The name of the column-property format property. */
	String CDM_PROPERTY_FORMAT = "columnPropertyFormat";
	
	/** The name of the column-property property. */
	String CDM_PROPERTY_PROPERTY ="columnPropertyProperty";
	
	/** The name of the column-action property. */
	String CDM_COLUMN_ACTION_PROPERTY ="columnActionProperty";
	
	/** The name of the columnActionTrueResult PropertyType. */
	String CDM_TRUE_RESULT_PROPERTY_TYPE = "columnActionTrueResult";
	
	/** The name of the columnActionFalseResult PropertyType. */
	String CDM_FALSE_RESULT_PROPERTY_TYPE = "columnActionFalseResult";
	
	/** The name of column-difference onAdded property. */
	String CDM_COLUMN_DIFFERENCE_ON_ADDED_PROPERTY_TYPE = "columnDifferenceOnAdded";
	
	/** The name of column-difference onRemoved property. */
	String CDM_COLUMN_DIFFERENCE_ON_REMOVED_PROPERTY_TYPE = "columnDifferenceOnRemoved";
	
	/** The name of column-difference onCommon property. */
	String CDM_COLUMN_DIFFERENCE_ON_COMMON_PROPERTY_TYPE = "columnDifferenceOnCommon";

	/** The name of column-property tag. */
	String CDMCOMP_COLUMN_PROPERTY = "column-property";
 }
