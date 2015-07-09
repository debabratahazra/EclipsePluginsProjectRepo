package com.odcgroup.iris.rim.generation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.iris.generator.Resource.RESOURCE_TYPE;
import com.odcgroup.t24.enquiry.util.EMUtils;

/**
 * TODO: Document me!
 * 
 * @author taubert
 * 
 */
public class ParameterParser {
    public static final String CURRENT_VARIABLE_PREFIX = "CVR_";
    public static final String VERSION_DEFAULT_PREFIX = "VLD_";
    
	private static final Logger logger = LoggerFactory.getLogger(ParameterParser.class);

	enum SyntaxType {
		UNKNOWN, COMPARISON, ASSIGNMENT, ACTION;
	}

	final static String aOperators[] = new String[] { "eq", "ne", "gt", "ge", "lt", "le", "and", "or", "not" };
	final static String aFunctionsOperators[] = new String[] { "startswith", "substringof", "endswith", "not_startswith", "not_substringof", "not_endswith"  };
	
	private static final Map<String,String> actionToIntent = new HashMap<String,String>();
	
	static {
		actionToIntent.put("I", "Edit");
		actionToIntent.put("A", "Authorize");
		actionToIntent.put("R", "Reverse");
		actionToIntent.put("D", "Delete");
		actionToIntent.put("S", "View");
	}
	
	/*
	 * Transform a pageFlow (eg "REPO,CREPOINP I F3") in a resource Name (eg :
	 * RepoCrepoinp_new) This is used in the XText, but the lack of support of
	 * arrays makes it extremely difficult, so let's do it in java instead.
	 */
	public static ParameterParserResult getResourceName(RESOURCE_TYPE type, String T24Name, String parameters) {
		return getResourceName(type, T24Name, parameters, true);
	}
	
	public static ParameterParserResult getResourceName(RESOURCE_TYPE type, String T24Name, String parameters, boolean wrapParameters) {
		if (T24Name == null) {
			logger.error("Null T24 name passed. Cannot resolve resourceName.");
			return new ParameterParserResult("");
		}
		ParameterParserResult result = null;
		try {
			ParameterResolverResult resolverResult = new ParameterParser().resolveParameters(type, parameters, wrapParameters);
			result = new ParameterParserResult(type.toString() + EMUtils.camelCaseName(T24Name)
					+ (resolverResult.suffix != null ? resolverResult.suffix : ""));
			result.setParameters(resolverResult.params);
			// return result;
		} catch (Exception e) {
			e.printStackTrace();
			
			logger.error("Failed to parse parameters : '" + parameters + "'", e);
			return new ParameterParserResult("");
		}
		
		return result;
	}
	
	private ParameterResolverResult resolveParameters(RESOURCE_TYPE type, String sParams, boolean wrapParameters)
			throws Exception {
		// Syntax
		// (+ means can have multiple, seperated by a coma)
		// (| means NON exclusive or)
		//
		// Actions | Comparison+ |Assignment+
		//
		// Comparison :
		// FIELD.NAME Operand "value"
		// FIELD.NAME Operand VARIABLE
		//
		// Assignment :
		// FIELD.NAME < VALUE
		// VALUE > FIELD.NAME
		// >SIMPLE.FIELD
		//
		// Actions :
		// FUNCTION VARIABLE
		// FUNCTION "VALUE"
		// FUNCTION F3 <-- special case
		//
		//
		// Examples :
		//
		// Actions :
		// I F3
		// S "12345"
		// A ARR.ID
		//
		// Comparisons :
		// ARRANGEMENT eq ARR.ID, SIMULATION.REF eq "SIM"
		//
		// Assignments :
		// CURRENT.CUSTOMER -> CUSTOMER, CURRENT.PRODUCT -> PRODUCT

		ParameterResolverResult result = new ParameterResolverResult();

		SyntaxType syntaxType = SyntaxType.UNKNOWN;

		if (type == RESOURCE_TYPE.menu) {
			/*
			 * Certainly nothing to do. How could we have MENU I F3 :-)
			 */
			return result;
		}

		if (type == RESOURCE_TYPE.enquiry) {
			result.suffix = "s";
		}

		if (sParams == null || sParams.trim().length() == 0) {
			if (type == RESOURCE_TYPE.version) {
				result.suffix = "Entry";
			}
			return result;
		}

		ArrayList<String> criterias = new ArrayList<String>();
		ArrayList<String> assignments = new ArrayList<String>();

		String aParams[] = cleverSplit(sParams, ',');
		for (String sOneParam : aParams) {
			sOneParam = sOneParam.trim();
			/*
			 * First a special case : 'NONE'
			 */
			if ("NONE".equals(sOneParam)) {
				if (wrapParameters) {
					assignments.add("HideToolbar = \"true\"");
				} else {
					assignments.add("HideToolbar = true");
				}
				continue;
			}
			/*
			 * Detect the type of parameter it is.
			 */
			String[] split = cleverSplit(sOneParam, ' ');
			
			if (split.length == 3 && (findOperator(split[1]) != null || findFunctionOperator(split[1]) != null)) {
				syntaxType = SyntaxType.COMPARISON;
			} else if (sOneParam.indexOf(">") > 0 || sOneParam.indexOf("<") >= 0) {
				syntaxType = SyntaxType.ASSIGNMENT;
			} else if (split.length == 1 || split.length == 2) {
				syntaxType = SyntaxType.ACTION;
			}
			
			if (sOneParam.length() == 0) {
				continue;
			}

			switch (syntaxType) {
				case ACTION:
					assignments.addAll(resolveActionParameter(type, wrapParameters, result, split));
	
					break;
				case ASSIGNMENT:
					/*
					 * Here, we already know that there is a -> or a <- in the
					 * oneParam or we just have a single field which would mean
					 * FIELD -> FIELD
					 */
					resolveAssignmentParameter(wrapParameters, assignments, sOneParam);
	
					break;
				case COMPARISON:
					/*
					 * Here, we already know that there is an operator in the
					 * oneParam
					 */
					criterias.addAll(resolveComparisonParameter(sOneParam));
	
					break;
				default:
					logger.error("Failed to interpret '" + sOneParam + "' as parameter.");
			}
		}

		resolveOtherParameters(wrapParameters, result, criterias, assignments);

		return result;
	}

	/**
	 * @param wrapParameters
	 * @param result
	 * @param criterias
	 * @param assignments
	 */
	private void resolveOtherParameters(boolean wrapParameters, ParameterResolverResult result, List<String> criterias,
			List<String> assignments) {
		StringBuilder sb = new StringBuilder();

		if (wrapParameters && (!criterias.isEmpty() || !assignments.isEmpty() || actionToIntent.containsKey(result.action))) {
			/*
			 * So there is something !
			 */
			sb.append("parameters [ ");
		}

		boolean bFirst = true;

		if (criterias.size() > 0) {
			sb.append("filter=");

			if (wrapParameters) {
				sb.append("\"");
			}

			for (String oneCriteria : criterias) {
				if (!bFirst) {
					sb.append(" and ");
				}
				sb.append(oneCriteria);
				bFirst = false;
			}

			if (wrapParameters) {
				sb.append("\"");
			}
		}

		for (String sOneAssignment : assignments) {
			if (!bFirst) {
				sb.append(" , ");
			}

			sb.append(sOneAssignment);
			bFirst = false;
		}
				
		if(actionToIntent.containsKey(result.action)) {
			if (!bFirst) {
				sb.append(", ");
			}
			
			sb.append("t24Intent=");
			
			if (wrapParameters) {
				sb.append("\"");
			}
			
			sb.append(actionToIntent.get(result.action));
			
			if (wrapParameters) {
				sb.append("\"");
			}
			
			bFirst = false;			
		}

		if (wrapParameters && (!criterias.isEmpty() || !assignments.isEmpty() || actionToIntent.containsKey(result.action))) {		
			/*
			 * So there 'was' something :-)
			 */
			sb.append(" ]");
		}

		result.params = sb.toString();
	}

	/**
	 * @param sOneParam
	 */
	private List<String> resolveComparisonParameter(String sOneParam) {
		String sFunctionOperator = findFunctionOperator(sOneParam);
		
		ArrayList<String> result = new ArrayList<String>();
		
		if (sFunctionOperator != null) {
			String sLowerCaseOperator = sFunctionOperator.toLowerCase().replace("_", " ");
			int pos = sOneParam.indexOf(" " + sFunctionOperator + " ");
			String sLeft = sOneParam.substring(0, pos).trim();
			String sRight = sOneParam.substring(pos + sFunctionOperator.length() + 2).trim();
			sLeft = EMUtils.camelCaseName(sLeft);
			sRight = EMUtils.camelCaseName(sRight);
			result.add(sLowerCaseOperator + "(" + sLeft + ", " + sRight + ")");

		} else {
			String sOperator = findOperator(sOneParam);
			
			if (sOperator != null) {
				String sLowerCaseOperator = sOperator.toLowerCase();
				int pos = sOneParam.indexOf(" " + sOperator + " ");
				String sLeft = sOneParam.substring(0, pos).trim();
				String sRight = sOneParam.substring(pos + sOperator.length() + 2).trim();
				sLeft = EMUtils.camelCaseName(sLeft);
				sRight = StringOrParam(sRight);
				sRight = "'" + sRight + "'";
				result.add(sLeft + " " + sLowerCaseOperator + " " + sRight);
			}
		}
		
		return result;
	}

	/**
	 * @param wrapParameters
	 * @param assignments
	 * @param sOneParam
	 */
	private void resolveAssignmentParameter(boolean wrapParameters, List<String> assignments, String sOneParam) {
		int pos = sOneParam.indexOf(">");
		String sVariable = null;
		String sValue = null;

		if (pos > 0) {
			sVariable = sOneParam.substring(0, pos).trim();

			if (!sVariable.startsWith(CURRENT_VARIABLE_PREFIX) && !sVariable.startsWith(VERSION_DEFAULT_PREFIX)) {
				sVariable = EMUtils.camelCaseName(sVariable);
			}

			sValue = sOneParam.substring(pos + 2).trim();
			sValue = StringOrParam(sValue);
		} else {
			pos = sOneParam.indexOf("<");
			if (pos >= 0) {
				sVariable = sOneParam.substring(pos + 2).trim();
				sVariable = EMUtils.camelCaseName(sVariable);
				if (pos > 0) {
					sValue = sOneParam.substring(0, pos).trim();
					sValue = StringOrParam(sValue);
				} else {
					sValue = "{" + sVariable + "}";
				}
			}
		}

		if (sVariable != null) { // always the case.

			if (wrapParameters) {
				assignments.add(sVariable + " = \"" + sValue + "\"");
			} else {
				assignments.add(sVariable + " = " + sValue);
			}
		}
	}

	/**
	 * @param type
	 * @param wrapParameters
	 * @param result
	 * @param split
	 */
	private List<String> resolveActionParameter(RESOURCE_TYPE type, boolean wrapParameters, ParameterResolverResult result, String[] split) {
		String sFunction = "S"; // default
		String sID = null;

		List<String> assignments = new ArrayList<String>();
		
		if (split.length > 0) {
			sFunction = split[0];
			result.action = sFunction;
			
			if (split.length > 1) {
				sID = split[1];
				if (!"F3".equals(sID)) {
					if (sID.startsWith("\"") || sID.startsWith("'")) {
						sID = sID.substring(1, sID.length() - 1);
						if (wrapParameters) {
							assignments.add("id = \"" + sID + "\"");
						} else {
							assignments.add("id = " + sID + "");
						}
					} else {
						/*
						 * This is a variable.
						 */
						if (sID.startsWith(CURRENT_VARIABLE_PREFIX) || sID.startsWith(VERSION_DEFAULT_PREFIX)) {
							/*
							 * Special case for not touching current variable /
							 * version default references
							 */
							sID = sID.replace('.', '_');
							if (wrapParameters) {
								assignments.add("id = \"" + sID + "\"");
							} else {
								assignments.add("id = " + sID + "");
							}
						} else {
							sID = EMUtils.camelCaseName(sID);
							if (wrapParameters) {
								assignments.add("id = \"{" + sID + "}\"");
							} else {
								assignments.add("id = {" + sID + "}");
							}
						}
					}
				}
				if (sFunction.equalsIgnoreCase("I")) {					
					if (sID.equals("F3")) {
						result.suffix = "_new";
					}
				} else if (sFunction.equalsIgnoreCase("A")) {
					result.suffix = "_IAuth";
				} else if (sFunction.equalsIgnoreCase("R")) {
					result.suffix = "_RAuth";
				} else if (sFunction.equalsIgnoreCase("D")) {
					result.suffix = "_delete";
				} else if (sFunction.equalsIgnoreCase("S")) {
					result.suffix = "_see";
				}
			} else {
				// Only for versions ?
				if (type == RESOURCE_TYPE.version) {
					result.suffix = "s";
				}
			}
		}
		
		return assignments;
	}
	
	private String StringOrParam(String value){
		if ((value.startsWith("'") && value.endsWith("'")) || (value.startsWith("\"") && value.endsWith("\""))){
			return value.substring(1, value.length()-1);
		}

        if (value.startsWith(CURRENT_VARIABLE_PREFIX) || value.startsWith(VERSION_DEFAULT_PREFIX)){
            /*
             * Special case for not touching current variable / version default references
             */
            return value.replace('.', '_');
        }

        /*
		 * From here on, we assume this is a parameter.
		 * TODO : Will have to triple-check the complex commands.
		 */
		
		if (value.startsWith("{") && value.endsWith("}")){
			return value;
		}
		
		if (value.equals(value.toUpperCase())){
			value =  EMUtils.camelCaseName(value);
		}
		
		return "{" + value + "}";
	}
	
	/**
	 * return the operator (with the case as in the value) if any. Null
	 * otherwise Eq,Ne,Gt,Ge,Lt,Le,And,Or,Not Example :
	 * findOperator("Hello EQ World") -> "EQ" Example2 :
	 * findOperator("Hello and World") -> "and"
	 */
	private String findOperator(String sValue) {
		sValue = " " + sValue + " ";
		String sLowerValue = sValue.toLowerCase();
		for (String sOneOperator : aOperators) {
			int pos = sLowerValue.indexOf(" " + sOneOperator.toLowerCase() + " ");
			if (pos >= 0) {
				return sValue.substring(pos + 1, pos + 1+ sOneOperator.length());
			}
		}
		return null;
	}

	private String findFunctionOperator(String sValue) {
		sValue = " " + sValue + " ";
		String sLowerValue = sValue.toLowerCase();
		for (String sOneFunctionOperator : aFunctionsOperators) {
			int pos = sLowerValue.indexOf(" " + sOneFunctionOperator.toLowerCase() + " ");
			if (pos >= 0) {
				return sValue.substring(pos + 1, pos + 1+ sOneFunctionOperator.length());
			}
		}
		return null;
	}
	
	
	static char[] openingBlocks = new char[]{'\"', '\'', '{', '('};
	static char[] closingBlocks = new char[]{'\"', '\'', '}', ')'};
	
	public static String[] cleverSplit(String s, char c){
		return cleverSplit(s, c, openingBlocks, closingBlocks, true);
	}
	
	public static String[] cleverSplit(String s, char c, boolean removeEmpty){
		return cleverSplit(s, c, openingBlocks, closingBlocks, removeEmpty);
	}
	/**
	 * Do a split on the given char only if outsite of blocks defined by the last parameter
	 */
	public static String[] cleverSplit(String s, char c, char[] openingBlocks,char[] closingBlocks, boolean removeEmpty){
		if (s == null){
			return new String[]{};
		}
		ArrayList<String> alResult = new ArrayList<String>();
		int cursor = 0;
		int last = -1;
		while(cursor < s.length()){
			for (int i = 0; i<openingBlocks.length; i++){
				char current = s.charAt(cursor);
				if (current == openingBlocks[i]){
					current = closingBlocks[i];
					cursor++;
					while(cursor < s.length() && s.charAt(cursor) != current){
						cursor++;
					}
						
				}
			}
			if (cursor < s.length() && s.charAt(cursor) == c){
				alResult.add(s.substring(last+1, cursor));
				last = cursor;
			}
			cursor++;
		}
		if (last <= s.length()){
			alResult.add(s.substring(last+1));
		}
		
		if (removeEmpty){
			Iterator<String> iter = alResult.iterator();
			while(iter.hasNext()){
				if (iter.next().length() == 0){
					iter.remove();
				}
			}
		}
		
		return alResult.toArray(new String[]{});
	}

	class ParameterResolverResult {
		String suffix = null;
		String prefix = null;
		String params = null;
		String action = null;
	}
}
