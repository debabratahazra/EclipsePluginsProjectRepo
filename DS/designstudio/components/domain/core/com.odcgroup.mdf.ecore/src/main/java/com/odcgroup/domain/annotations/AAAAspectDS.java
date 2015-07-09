package com.odcgroup.domain.annotations;

import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;



public class AAAAspectDS extends AAAAspect {
	
//  protected static void setAnnotationProperty(MdfModelElement model, String namespace, String annotationName, String propertyName, String propertyValue, boolean isCDATA) {
//  	MDF_FACTORY_ADAPTER.setAnnotationProperty(model, namespace, annotationName, propertyName, propertyValue, isCDATA);
//  }
//  
//  protected static List<MdfAnnotationProperty> getAnnotationProperties(MdfModelElement model, String namespace, String annotationName) {
//  	return MDF_FACTORY_ADAPTER.getAnnotationProperties(model, namespace, annotationName);
//  }
//
//  protected static void setAnnotationValue(MdfModelElement model, String namespace, String annotationName, String annotationValue) {
//  	MDF_FACTORY_ADAPTER.setAnnotationValue(model, namespace, annotationName, annotationValue);
//  }
//  
//  protected static void removeAnnotation(MdfModelElement model, String namespace, String annotationName) {
//  	MDF_FACTORY_ADAPTER.removeAnnotation(model, namespace, annotationName);
//  }

  protected static final String AAA_PROPERTY_TIMESTAMP = "Timestamp";
  
  public static void setMMLSpecific(MdfModelElement model, boolean value) {
  	MdfAnnotationsUtil.setAnnotationProperty(model, SERVICES_NAMESPACE_URI, SERVICES_ANNOTATION, SERVICES_PROPERTY_MML_SPECIFIC, value+"", false);
  }

  public static void setLoadPermittedValues(MdfModelElement model, boolean load) {
  	MdfAnnotationsUtil.setAnnotationProperty(model, SERVICES_NAMESPACE_URI, SERVICES_ANNOTATION, SERVICES_PROPERTY_PERMITTED_VALUES, load+"", false);
  }

//
//  public static void setMappingAttribute(MdfModelElement model, String value) {
//      setAnnotationProperty(model, SERVICES_NAMESPACE_URI, SERVICES_ANNOTATION, SERVICES_PROPERTY_MAPPING_ATTRIBUTE, value, false);
//  }

  public static void addAAAParams(MdfProperty model, String param, String type) {
  	MdfAnnotationsUtil.setAnnotationProperty(model, AAA_NAMESPACE_URI, AAA_ANNOTATION_PARAM, AAA_PROPERTY_PARAM, param, false);
  	MdfAnnotationsUtil.setAnnotationProperty(model, AAA_NAMESPACE_URI, AAA_ANNOTATION_PARAM, AAA_PROPERTY_TYPE, type, false);
  }

//  /**
//   * Set the the dict_id of a T'A Entity imported from the meta dictionary for this model element.
//   * @param model The model element
//   * @param dictID dict_id
//   */
//  public static void addDictID(MdfEntity model, long dictID) {
//      setAnnotationValue(model, AAA_NAMESPACE_URI, ENTITY_DICT_ID, Long.toString(dictID));
//  }

  public static void setTripleAEntityDictID(MdfEntity model, long dictID) {
  	MdfAnnotationsUtil.setAnnotationProperty(model, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_ENTITY_DICT_ID, Long.toString(dictID), false);
  }

  public static void setTripleAAttributeDictID(MdfProperty model, long dictID) {
  	MdfAnnotationsUtil.setAnnotationProperty(model, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_ATTRIBUTE_DICT_ID, Long.toString(dictID), false);
  }

  // ----------------------------------------------------------------------
	// Entity SQL Name (Class, Enumeration, Format)
  // ----------------------------------------------------------------------

  /**
   * Sets the original T'A SQLName of the entity associated with this enumeration
   *
   * @param enumeration the enumeration
   * @param sqlName the sql name of the entity
   */
  public static void setTripleAEntitySQLName(MdfEntity entity, String sqlName) {
  	MdfAnnotationsUtil.setAnnotationProperty(entity, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_ENTITY_SQL_NAME, sqlName, false);
  }

  public static void setTripleAEntitySQLName(MdfEnumeration enumeration, String sqlName) {
  	MdfAnnotationsUtil.setAnnotationProperty(enumeration, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_ENTITY_SQL_NAME, sqlName, false);
  }

  /**
   * Sets the original T'A SQLName of the entity associated with this property
   *
   * @param attribute the property
   * @param sqlName the sql name of the property
   */
  public static void setTripleAAttributeSQLName(MdfProperty attribute, String sqlName) {
  	MdfAnnotationsUtil.setAnnotationProperty(attribute, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_ATTRIBUTE_SQL_NAME, sqlName, false);
  }

  /**
   * Sets the original T'A SQLName of the entity associated with this dataset property
   *
   * @param attribute the dataset property
   * @param sqlName the sql name of the dataset property
   */
  public static void setTripleAAttributeSQLName(MdfDatasetProperty attribute, String sqlName) {
  	MdfAnnotationsUtil.setAnnotationProperty(attribute, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_ATTRIBUTE_SQL_NAME, sqlName, false);
  }

  /**
   * Sets the original T'A SQLName of the entity associated with this enumeration
   *
   * @param enumeration the enumeration
   * @param sqlName the sql name of the attribute
   */
  public static void setTripleAAttributeSQLName(MdfEnumeration enumeration, String sqlName) {
  	MdfAnnotationsUtil.setAnnotationProperty(enumeration, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_ATTRIBUTE_SQL_NAME, sqlName, false);
  }

  public static void setTripleAAttributeName(MdfProperty model, String name) {
  	MdfAnnotationsUtil.setAnnotationProperty(model, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_ATTRIBUTE_NAME, name, false);
  }

	public static void setTripleAPermittedValueName(MdfEnumValue enumValue, String name) {
		MdfAnnotationsUtil.setAnnotationProperty(enumValue, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_PERMITTED_VALUE_NAME, name, false);
	}

	public static void setTripleAPermittedValueRank(MdfEnumValue enumValue, String name) {
		MdfAnnotationsUtil.setAnnotationProperty(enumValue, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_PERMITTED_VALUE_RANK, name, false);
	}
  
	/**
   * Sets the original T'A name for this model element
   *
   * @param model The model element
   * @param name name
   */
  public static void setTripleAEntityName(MdfEntity model, String name) {
  	MdfAnnotationsUtil.setAnnotationProperty(model, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_ENTITY_NAME, name, false);
  }

  public static void setTripleAEntityName(MdfProperty model, String name) {
  	MdfAnnotationsUtil.setAnnotationProperty(model, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_ENTITY_NAME, name, false);
  }


  public static void setTripleABusinessType(MdfBusinessType businessType, String value) {
  	MdfAnnotationsUtil.setAnnotationProperty(businessType, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_BUSINESS_TYPE, value, false);
  }

	public static void setTripleAFormatFunctionDictId(MdfEntity entity, long id) {
		MdfAnnotationsUtil.setAnnotationProperty(entity, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_FORMAT_FUNCTION_DICTID, ""+id, false);
	}

	public static void setTripleAFormatName(MdfEntity entity, String name) {
		MdfAnnotationsUtil.setAnnotationProperty(entity, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_FORMAT_NAME, name, false);
	}


	/**
	 * Annotate the entity with the format function
	 * @param entity
	 * @param name
	 */
	public static void setTripleAFormatFunction(MdfEntity entity, String name) {
		MdfAnnotationsUtil.setAnnotationProperty(entity, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_FORMAT_FINANCIAL_FUNCTION, name, false);
	}

	public static void setTripleAColumnFilter(MdfEntity entity, String filter) {
		MdfAnnotationsUtil.setAnnotationProperty(entity, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_FORMAT_COLUMN_FILTER, filter, false);
	}


	public static void setTripleAFormatElementName(MdfModelElement entity, String name) {
		MdfAnnotationsUtil.setAnnotationProperty(entity, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_FORMAT_ELEMENT_NAME, name, false);
	}


	public static void setTripleAFormatElementScript(MdfProperty formatProperty, String script) {
		MdfAnnotationsUtil.setAnnotationProperty(formatProperty, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_FORMAT_ELEMENT_SCRIPT_DEF, script, false);
	}

	public static void setTripleAFormatElementScript(MdfDatasetProperty formatProperty, String script) {
		MdfAnnotationsUtil.setAnnotationProperty(formatProperty, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_FORMAT_ELEMENT_SCRIPT_DEF, script, false);
	}

	public static void setTripleAFormatElementRank(MdfProperty formatProperty, Integer rank) {
		MdfAnnotationsUtil.setAnnotationProperty(formatProperty, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_FORMAT_ELEMENT_RANK, rank!=null?rank.toString():null, false);
	}

	public static void setTripleAFormatElementRank(MdfDatasetProperty formatProperty, Integer rank) {
		MdfAnnotationsUtil.setAnnotationProperty(formatProperty, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_FORMAT_ELEMENT_RANK, rank!=null?rank.toString():null, false);
	}

	public static void setTripleAFormatElementSortingRank(MdfProperty formatProperty, Integer sortingRank) {
		MdfAnnotationsUtil.setAnnotationProperty(formatProperty, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_FORMAT_ELEMENT_SORTING_RANK, sortingRank!=null?sortingRank.toString():null, false);
	}

	public static void setTripleAFormatElementSortingRank(MdfDatasetProperty formatProperty, Integer sortingRank) {
		MdfAnnotationsUtil.setAnnotationProperty(formatProperty, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_FORMAT_ELEMENT_SORTING_RANK, sortingRank!=null?sortingRank.toString():null, false);
	}

  /**
   * Sets the original T'A multi language for this model element
   *
   * @param model The model element
   * @param name name
   */
  public static void setTripleAAttrMultiLanguage(MdfEntity model, String name) {
  	MdfAnnotationsUtil.setAnnotationProperty(model, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_ATTR_MULTI_LANGUAGE, name, false);
  }

  public static void setTripleAAttrMultiLanguage(MdfProperty model, String name) {
  	MdfAnnotationsUtil.setAnnotationProperty(model, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_ATTR_MULTI_LANGUAGE, name, false);
  }

  public static void setTripleAUDEntities(MdfDomain model, boolean udEntities) {
  	MdfAnnotationsUtil.setAnnotationProperty(model, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_UD_ENTITIES, udEntities+"", false);
	}	

	public static void setLogical(MdfModelElement model, boolean isLogical) {
		MdfAnnotationsUtil.setAnnotationProperty(model, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_LOGICAL, isLogical+"", false);
	}	

	public static void setTripleAParentTypeEntity(MdfModelElement model, String name) {
		MdfAnnotationsUtil.setAnnotationProperty(model, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_PARENT_TYPE_ENTITY, name, false);
	}
	
	public static void setTripleAParentTypeAttr(MdfModelElement model, String name) {
		MdfAnnotationsUtil.setAnnotationProperty(model, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_PARENT_TYPE_ATTR, name, false);
	}

	public static void setTripleAFinFuncProcName(MdfModelElement model, String name) {
		MdfAnnotationsUtil.setAnnotationProperty(model, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_FORMAT_FIN_FUNC_PROC_NAME, name, false);
	}
	
	public static void setTripleAEntitySecured(MdfModelElement model, boolean value) {
		MdfAnnotationsUtil.setAnnotationProperty(model, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_SECURE, "" + value, false);
	}
	
	public static void setTripleAAttributeDataType(MdfProperty property, String value) {
		MdfAnnotationsUtil.setAnnotationProperty(property, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_ATTR_DATA_TYPE, value, false);
	}
	/** 
	 * Set the TripleA TimeStamp
	 * @param domain
	 * @param value
	 */
	public static void setTripleATimeStamp(MdfDomain domain, String value) {
	    MdfAnnotationsUtil.setAnnotationProperty(domain, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_TIMESTAMP, value, false);
	}
       /**
        * get the TripleA Timestamp
        * @param domain
        * @return timeStamp
        */
	public static String getTripleATimeStamp(MdfDomain domain) {
		return getAnnotationProperty(domain, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME, AAA_PROPERTY_TIMESTAMP);
	}
	
	
	/**
	 * set the AAAparam property type annotation
	 * @param model
	 * @param param
	 * @param type
	 */
	public static void setAAAParamsType(MdfModelElement model,  String type) {
	  	MdfAnnotationsUtil.setAnnotationProperty(model, AAA_NAMESPACE_URI, AAA_ANNOTATION_PARAM, AAA_PROPERTY_TYPE, type, false);
	  }
	
	/**
	 * remove the AAAparam property type annotation
	 * 
	 */
	public static void removeAAAParamsTypeAnnotation(MdfModelElement model) {
	  	 MdfAnnotationsUtil.removeAnnotation(model, AAA_NAMESPACE_URI, AAA_ANNOTATION_PARAM);
	  }
	
	/**
	 * remove the Tripple'A annotation
	 * 
	 */
	public static void removeTAPDomainAnnotation(MdfModelElement model) {
		MdfAnnotationsUtil.removeAnnotation(model, AAA_NAMESPACE_URI, AAA_ANNOTATION_NAME);	
	}
}
