package com.odcgroup.iris.rim.generation.mappers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.iris.generator.Resource.RESOURCE_TYPE;
import com.odcgroup.iris.rim.generation.ParameterParser;
import com.odcgroup.iris.rim.generation.ParameterParserResult;
import com.odcgroup.t24.enquiry.enquiry.CriteriaOperator;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FunctionKind;
import com.odcgroup.t24.enquiry.enquiry.SelectionCriteria;
import com.odcgroup.t24.enquiry.util.EMUtils;

/**
 * This class will attempt to resolve a dynamic drill down 
 * based on the fromField attribute of a drillDown in an Enquiry.
 * This could be done directly in xtend, but certainly easier in 
 * pure java.
 * 
 * @see Enquiry2ResourceMapper.xtend
 *
 * @author taubert
 *
 */
public class EnquiryDrillDownResolver {

	private static final String IAUTH_SUFFIX = "_IAuth";
	private static final String DELETE_SUFFIX = "_delete";

	private static final String VER_LINK_DEFAULT_PARAM_PREFIX = "VLD_";
	private static final String CURR_VAR_PARAM_PREFIX = "CVR_";

	private static final Logger logger = LoggerFactory.getLogger(EnquiryDrillDownResolver.class);
	
	private final List<Field> _fields;
	private String sTargetResourceName = null;
	
	protected EnquiryDrillDownResolver(Enquiry enquiry){
		this._fields = enquiry.getFields();
	}

	protected RESOURCE_TYPE getResourceType(){
		return RESOURCE_TYPE.unknown;
	}
	
	protected String getTarget(){
		return null;
	}
	
	protected List<Parameter> resolveParameters(RESOURCE_TYPE type, String T24Name, DrillDown drilldown){
		/*
		 * We will test if a parameter is part of the fields or not.
		 * If part of the fields -> put it in {}. Otherwise, this is just a value.
		 */
		
		StringBuilder filter = new StringBuilder();
		if (drilldown.getCriteria() != null){
			for (SelectionCriteria sc : drilldown.getCriteria()) {
				if (sc.getValues().iterator().hasNext()){
					String criteria = sc.getValues().iterator().next();
					if (filter.length() > 0) {
						filter.append(" , ");
					}
					
					CriteriaOperator dslOperator = sc.getOperand();
					
					if(dslOperator == CriteriaOperator.GREATER_THAN_SYMBOL) {
						String fieldName = sc.getField();
						
						if(fieldName.startsWith("CURRENT.")) {
							filter.append(CURR_VAR_PARAM_PREFIX);
						} else {
							filter.append(VER_LINK_DEFAULT_PARAM_PREFIX);
						}
						
						filter.append(fieldName.replaceAll("\\.", "_"));
					} else {
						filter.append(EMUtils.camelCaseName(sc.getField()));						
					}
					
					filter.append(" ").append(convertOperator(dslOperator)).append(" ");

					if (isField(criteria) || dslOperator == CriteriaOperator.GREATER_THAN_SYMBOL){
						criteria = EMUtils.camelCaseName(criteria);
					}else{
						/*
						 * If double quotes : remove them
						 */
						if (criteria.startsWith("\"") && criteria.endsWith("\"")){
							criteria = "'" + criteria.substring(1, criteria.length()-1) + "'";
						}
						 // make sure we have single quotes as this is a String literal
						if (!(criteria.startsWith("'") && criteria.endsWith("'"))){
							criteria = "'" + criteria + "'";
						}
					}
					filter.append(criteria);
				}
			}
		}
		if (drilldown.getParameters() != null){
			String function = convertFunction(drilldown.getParameters().getFunction());
			if (function == null){
				function = "I"; // default;
			}
			if (filter.length() > 0){
				filter.append(" , ");
			}
			filter.append(function);
			if (drilldown.getParameters().getFieldName() != null && drilldown.getParameters().getFieldName().size() > 0 && drilldown.getParameters().getFieldName().get(0).length() > 0){
				String id = drilldown.getParameters().getFieldName().get(0);
				filter.append(" ").append(id);
			}else if (drilldown.getParameters().isAuto()){
				filter.append(" F3");
			}else {
				String id = drilldown.getLabelField();
				if (id == null){
					id = "ID"; // Hardcoded :-(
				}
				filter.append(" ").append(id);
			}
		}
		
		if (type == RESOURCE_TYPE.dynamic && filter.toString().length() == 0){
			filter.append("id < ID");
		}
		
		ParameterParserResult result = ParameterParser.getResourceName(type, T24Name, filter.toString(), false);
		
		String targetResourceName = result.getResourceName();
		
		if(targetResourceName.endsWith(DELETE_SUFFIX)) {
			targetResourceName = targetResourceName.substring(0, targetResourceName.length() - 7) + IAUTH_SUFFIX;
		}
		
		this.sTargetResourceName = targetResourceName;
		
		ArrayList<Parameter> ret = new ArrayList<EnquiryDrillDownResolver.Parameter>();
		
		String[] parameters = ParameterParser.cleverSplit(result.getParameters(), ',');
		
		for (String oneParam : parameters){
			String[] aOneParam = ParameterParser.cleverSplit(oneParam, '=');
			if (aOneParam.length != 2){
				logger.error("Failed to parse parameter '" + oneParam + "'");
			}else{
				Parameter p;
				/*
				 * Only lowerCase the parameter if 'Dynamic'. Otherwise keep the case.
				 */
				if (type == RESOURCE_TYPE.dynamic && "id".equals(aOneParam[0].toLowerCase().trim())){
					p = new Parameter(aOneParam[0].toLowerCase().trim(), aOneParam[1].trim());
				}else{
					p = new Parameter(aOneParam[0].trim(), aOneParam[1].trim());
				}
				ret.add(p);
			}
		}
		
		return ret;
	}
	
	
	/*
	 * This is a shame to have to do that. If only we could have a equals method on the Field object ...
	 */
	private boolean isField(String name){
		if (this._fields == null){
			return false;
		}
		for (Field f : this._fields){
			if (f.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	
	/*
	 * See the class SelectionOperator. The 
	 */
	
	private static String convertFunction(FunctionKind dslFunction){
		switch (dslFunction){
		case AUTHORISE:
			return "A";
		case COPY:
			return "C";
		case DELETE:
			return "D";
		case INPUT:
			return "I";
		case REVERSE:
			return "R";
		case SEE:
			return "S";
		default:
			return null;
		}
	}
	
	private static String convertOperator(CriteriaOperator dslOperator){
		switch (dslOperator){
		case BEGINS_WITH:
			return "startswith";
		case BETWEEN:
			return "rg";
		case CONTAINS:
			return "substringof";
		case DOES_NOT_BEGIN_WITH:
			return "not_startswith";
		case DOES_NOT_CONTAIN:
			return "not_substringof";
		case DOES_NOT_END_WITH:
			return "not_endswith";
		case ENDS_WITH:
			return "endswith";
		case EQUALS:
			return "eq";
		case GREATER:
			return "gt";
		case GREATER_OR_EQUALS:
			return "ge";
		case LESS_OR_EQUALS:
			return "le";
		case LESS_THAN:
			return "le";
		case MATCHES:
			return ">";
		case NOT_BETWEEN:
			return null;
		case NOT_EQUALS:
			return "ne";
		case NOT_MATCHES:
			return null;
		case GREATER_THAN_SYMBOL:
			return ">";
		case SOUNDS_LIKE:
			return null;
		default :
		    return null;
		}

	}
	
	public String getTargetResourceName(){
		return this.sTargetResourceName;
	}
	
	
	public class Parameter{

		final String type; // eg "filter"
		final String value;
		
		Parameter(String type, String value){
			this.type = type;
			this.value = value;
		}
	}
	
	public static void main(String[] args){
		
		ParameterParser.cleverSplit("hello,", ',');
		ParameterParser.cleverSplit("hello", ',');
		ParameterParser.cleverSplit("hello,world", ',');
		ParameterParser.cleverSplit(",hello", ',');
		ParameterParser.cleverSplit("hello,", ',');
		ParameterParser.cleverSplit(",hello,world,", ',');		
		ParameterParser.cleverSplit(",hello,,,world,", ',');
		
		ParameterParser.cleverSplit("he{ll}o", ',');
		ParameterParser.cleverSplit("hel{lo,wo}rld", ',');
		ParameterParser.cleverSplit("{,hello", ',');
		ParameterParser.cleverSplit("hello,}", ',');
		ParameterParser.cleverSplit(",hello,world,", ',');		
	}
	
}
