package com.odcgroup.page.transformmodel.snippet;

/**
 * constants used in the generation of parameters of a snippet
 * 
 * @author pkk
 */
public interface SnippetTransformerConstants {
	
	/** prefix for the snippet parameter name */
	String PARAM_PREFIX = "Query";
	
	/** FilterSet level parameter */
	String FILTERSET_LEVEL = "level";
	
	/** FilterSet logicalOperator parameter */
	String FILTERSET_LOGICAL_OPERATOR = "logicalOp";
	
	/** FilterSet target dataSet name*/
	String FILTERSET_TARGETDS = "TargetDS";
	
	/** Filter attribute name */
	String FILTER_ATTRIBUTE = "attribute";
	
	/** Filter operator parameter name */
	String FILTER_OPERATOR = "Op";
	
	/** Filter value 1 parameter name */
	String FILTER_VALUEONE = "value1";
	
	/** Filter value 2 parameter name */
	String FILTER_VALUETWO = "value2";
	
	/** Filter mode parameter name */
	String FILTER_MODE = "mode";

	/** Query output parameter name */
	String QUERY_OUTPUT = "DSOutput";
	
	/** Query table model reference parameter name */
	String QUERY_MODELREF = "tableModelRef";
	
	/** Query bean  parameter name */
	String QUERY_BEANNAME = "beanName";
	
	/** Query bean property parameter name */
	String QUERY_BEANPROPERTY = "beanProperty";

	/** Query selection mode parameter name */
	String QUERY_SELECTIONMODE = "selectionMode";

	/** Query run at start parameter name */
	String QUERY_RUNATSTART = "runAtStart";
	
	/** Query run at start parameter name */
	String QUERY_ATTR_INCLUDE = "attributeInclude";
	
	/** Query run at start parameter name */
	String QUERY_ATTR_EXCLUDE = "attributeExclude";

}
