package com.odcgroup.jbpm.extension.flow.ruleflow.properties;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.drools.eclipse.flow.common.editor.core.ProcessWrapper;
import org.drools.eclipse.flow.ruleflow.core.HumanTaskNodeWrapper;
import org.jbpm.process.core.context.variable.Variable;

/**
 * @author gasampong
 *
 */
public class JbpmDialogHelper {
	
	//The following pattern is DD:HH:MM:SS (0-99:0-23:0-59:0-59)
	static final Pattern PATTERN = Pattern.compile("[0-9]?[0-9]:(0?[0-9]|1[0-9]|2[0-3]):(0?[0-9]|[12345][0-9]):(0?[0-9]|[12345][0-9])");
	static final Pattern SPECIAL_CHARS =  Pattern.compile("\\^|\\=");
	static final String INVALID_DURATION_ERROR_MSG = "DURATION must be of the format " +
			"DD:HH:MM:SS where DD denotes days (0-99), HH denotes hours (0-23), MM denotes minutes (0-59) and SS denotes seconds (0-59).  Only numbers allowed";
	static final String INVALID_CHAR_ERROR_MSG = "The following special characters are not allowed " +
			"'=' '^'";
	
	static final String delims = "[ ]+";
	
    public static boolean isDurationValid(String duration){
		Matcher m = PATTERN.matcher(duration);	 
		if(m.matches()){
			return true;
		} else {
			return false;
		}
    }
    
	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getProcessVariables(HumanTaskNodeWrapper wrapper,boolean format) {
		List<String> vnames = new ArrayList<String>();
		List<Variable> varList = (List<Variable>) wrapper.getParent().getProcessWrapper().getPropertyValue(ProcessWrapper.VARIABLES);
		if(varList!=null && varList.size()>0){
			for(Variable variable : varList) {
				if(format){
					vnames.add("#{"+variable.getName()+"}");				
				} else {
					vnames.add(variable.getName());
				}
			}
		}
		return vnames;
	}  
	
	/**
	 * 
	 * @param wrapper
	 * @return
	 */
	public static String getTaskAssignedGroupId(HumanTaskNodeWrapper wrapper){
		 String groupId = (String)wrapper.getPropertyValue("GroupId");
		 return groupId==null ? "" : groupId;	
	}
	
	public static boolean containsInValidCharacter(String value){
		Matcher m = SPECIAL_CHARS.matcher(value);
		if(m.find()) {
			return true;
		} else {		
			return false;
		}
	}
	
	/**
	 * 
	 * @param wrapper
	 * @return
	 */
	public static String[] getVersionAndMode(HumanTaskNodeWrapper wrapper){
		String comment = (String)wrapper.getPropertyValue("Comment");
		Pattern pattern = Pattern.compile(", [AI] ");
		Matcher m = pattern.matcher(comment);
		if(m.find()==false){		
			comment = comment.replace(", ", ",");
		}		
		return comment.split("[ ]+");		
	}	
	
	/**
	 * 
	 * @param values
	 * @return
	 */
	public static Map<String, String> getFieldMappings(String[] values){
		Map<String, String> fieldmappings = new LinkedHashMap<String, String>();
		if(values.length>3) {
			String key = "";
			for (int i = 3; i < values.length; i++) {
				String mapping = values[i];
				String[] split = mapping.split("[=]");
				int length = split.length;				
				if(length==1){
					//Term belongs to previous key value mapping (separated by space)
					//Spaces are valid e.g. NAME_1=James Smith
					fieldmappings.put(key, (String)fieldmappings.get(key)+" "+mapping);
				}else if(length==2){
					key = split[0];
					fieldmappings.put(key, split[1]);					
				}
			}
		}
		return fieldmappings;
	}
	
	/**
	 * Parses value into 2 strings, t24 target and enq vars
	 * @param comment
	 * @return
	 */
	public static String[] parseValue(String value) {
		Matcher m = null;
		String t24Target = value;
		String enqVars = "";
		if(value.contains("ENQ_VARS")){
			
			//if the value string ends with a closing bracket then it's the closing 
			//bracket for ENQ_VARS	
			if(value.charAt(value.length()-1)=='}'){
				//ENQ_VARS positioned at the end of the comment string
				Pattern pattern = Pattern.compile("ENQ_VARS=\\{.*\\}$");				
				m = pattern.matcher(value);	
			} else {
				//Somebody has manually altered the comment string and has added
				//extra information after ENQ_VARS
				Pattern pattern = Pattern.compile("ENQ_VARS=\\{.*\\}\\s");				
				m = pattern.matcher(value);					
			}
			
			if(m!=null && m.find()) {
				enqVars = m.group().trim();
				t24Target = t24Target.replace(enqVars,"").trim();
				
				//remove double spaces in t24 target (required when some field mappings are placed
				//after the enq vars map)
				t24Target = t24Target.replace("  ", " ");
				
				enqVars = enqVars.substring(10, enqVars.length()-1);
			} 						
		}		
		return new String[]{t24Target,enqVars};
	}
	
	/**
	 * 
	 * @param enqVarsValue
	 * @return
	 */
	public static LinkedHashMap<String,String> getEnqVarsFieldMappings(String enqVarsValue){
		LinkedHashMap<String,String> enqVarsFieldmappings = new LinkedHashMap<String,String>();
		if(enqVarsValue!=null && !enqVarsValue.equals("")) {
			String[] enqVarsMap = enqVarsValue.split("\\^");					
			for (int i = 0; i < enqVarsMap.length; i++) {
				String[]var = enqVarsMap[i].split("=");
				enqVarsFieldmappings.put(var[0],var[1]);
			}	
		}
		return enqVarsFieldmappings;
	}
}
