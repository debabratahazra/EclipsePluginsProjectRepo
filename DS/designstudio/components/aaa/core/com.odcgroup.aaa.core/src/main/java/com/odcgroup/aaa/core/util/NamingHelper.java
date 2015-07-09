package com.odcgroup.aaa.core.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Helper with methods to transform a T'A SQLName into a "nice" Java name.
 *
 * @author Michael Vorburger
 */
public final class NamingHelper {
	private static final Logger LOG = LoggerFactory.getLogger(NamingHelper.class);

    private NamingHelper() {
    }
    
    public static String getMMLJavaClassNameFromTADictEntitySQLName(String sqlName) {
    	if (StringUtils.isBlank(sqlName))
    		return sqlName;

        String javaClassName = toFirstCharacterUpperCase(replaceUnderscoresByCamelCase(sqlName));
        
        if (javaClassName.equals("Domain"))  {
            javaClassName = "DomainAAA";
        }

        if (javaClassName.equals("List"))  {
            javaClassName = "ListAAA";
        }

        return dealWithReservedJavaKeywords(javaClassName);
    }

    public static String getMMLJavaClassNameFromTAFormatCode(String formatCode) {
    	if (StringUtils.isBlank(formatCode))
    		return formatCode;

        String javaClassName = toFirstCharacterUpperCase(formatCode);
        javaClassName = javaClassName.replaceAll(" ", "");
        return dealWithReservedJavaKeywords(javaClassName);
    }
    
    public static String getMMLJavaAttributeNameFromTAFormatElementSQLName(String sqlName) {
    	if (StringUtils.isBlank(sqlName))
    		return sqlName;

        String turnCamelCaseIntoSpaces = turnCamelCaseIntoSpaces(sqlName);
        String replaceUnderscoresByCamelCase = replaceUnderscoresByCamelCase(turnCamelCaseIntoSpaces);
        String firstCharacterLowerCase = toFirstCharacterLowerCase(replaceUnderscoresByCamelCase);
        String javaAttributeName = firstCharacterLowerCase.replaceAll(" ", "");

        if (javaAttributeName.equals("objId"))  {
            javaAttributeName = "objIdX";
        }
        
        return dealWithReservedJavaKeywords(javaAttributeName);
    }
    
    public static String getMMLJavaAttributeNameFromTADictAttributeSQLName(String sqlName) {
    	if (StringUtils.isBlank(sqlName))
    		return sqlName;

        String javaAttributeName;
        if (sqlName.endsWith("_dict_id")) {
            javaAttributeName = sqlName.substring(0, sqlName.length() - 8);
        }
        else if (sqlName.endsWith("_id")) {
            javaAttributeName = sqlName.substring(0, sqlName.length() - 3);
        }
//        else if (sqlName.charAt(sqlName.length() - 2) == '_') {
//            javaAttributeName = sqlName.substring(0, sqlName.length() - 2);
//        } 
        else {
            javaAttributeName = sqlName;
        }
        return getMMLJavaAttributeNameFromTAFormatElementSQLName(javaAttributeName);
    }

    private static String replaceUnderscoresByCamelCase(String javaAttributeName) {
        return WordUtils.capitalizeFully(javaAttributeName, new char[] {'_', ' '}).replaceAll("_", "");
    }

    private static String replaceSpacesByCamelCase(String javaAttributeName) {
        return WordUtils.capitalize(javaAttributeName).replaceAll(" ", "");
    }
    
    public static String turnCamelCaseIntoSpaces(String s) {
    	if (StringUtils.isBlank(s))
    		return s;
        StringBuffer sb = new StringBuffer(s.length() + 5);
        sb.append(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            char pc = s.charAt(i - 1);
            char nc = i != s.length()-1 ? s.charAt(i + 1) : 0;
            if (Character.isUpperCase(c) && Character.isLowerCase(pc)) {
                sb.append(' ');
            }
            else if (Character.isUpperCase(c) && Character.isLowerCase(nc)) {
                sb.append(' ');
            }
            sb.append(c);
        }
        return sb.toString();
    }
    
    private static final String[] RESERVED_JAVA_KEYWORDS_ARRAY = { "abstract", "assert", "boolean", "break", "byte",
			"case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum",
			"extends", "false", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof",
			"int", "interface", "long", "native", "new", "null", "package", "private", "protected", "public", "return",
			"short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient",
			"true", "try", "void", "volatile", "while" };
    private static final Set<String> RESERVED_JAVA_KEYWORDS = new HashSet<String>(Arrays.asList(RESERVED_JAVA_KEYWORDS_ARRAY));
    
    public static String dealWithReservedJavaKeywords(String taName) {
        if (RESERVED_JAVA_KEYWORDS.contains(taName)) {
        	String fixedJavaName = taName + "J";
			LOG.info("Imported '" + taName + "' as '" + fixedJavaName + "' (because that's a reserved Java keyword that would be invalid in domain (MML)");
            return fixedJavaName;
        } else {
        	return taName;        	
        }
    }
    
    
    public static String toFirstCharacterLowerCase(String name) {
    	if (StringUtils.isBlank(name))
    		return name;

    	return name.substring(0, 1).toLowerCase() + name.substring(1);
    }
    public static String toFirstCharacterUpperCase(String name) {
    	if (StringUtils.isBlank(name))
    		return name;

        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    

    public static String getMMLJavaNameFromTAEnumName(String taName) {
    	if (StringUtils.isBlank(taName))
    		return taName;

    	String javaName = taName;
        javaName = javaName.replaceAll("<All>", "All");
        javaName = javaName.replaceAll("<None>", "None");
        javaName = javaName.replaceAll("<Default>", "Default");
        javaName = javaName.replaceAll("<Compound>", "Compound");
        javaName = javaName.replaceAll(">=", "GreaterThanOrEqualTo");
        javaName = javaName.replaceAll("=<", "LessThanOrEqualTo");
        javaName = javaName.replaceAll(">", "GreaterThan");
        javaName = javaName.replaceAll("<", "LessThan");
        javaName = javaName.replaceAll("=", "EqualTo");
        javaName = javaName.replaceAll("<>", "Different");
        javaName = javaName.replaceAll("\\[]", "Between");
        javaName = javaName.replaceAll("%", "Percent");
        javaName = javaName.replaceAll("\\*", "Times");
        javaName = javaName.replaceAll("\\+", "Plus");
        javaName = javaName.replaceAll("-", "_");
        javaName = javaName.replaceAll("\\.", "_");
        javaName = javaName.replaceAll("\\(", "_");
        javaName = javaName.replaceAll(",", "_");
        javaName = javaName.replaceAll("/", "_");
        javaName = javaName.replaceAll("\\.\\)", "");
        javaName = javaName.replaceAll("\\)", "");
        javaName = javaName.replaceAll("!", "");

        javaName = javaName.replaceAll("/", "");        // Remove slashes
        javaName = javaName.replaceAll("-", "");        // Remove hyphens
        javaName = javaName.replaceAll("\\.", "");      // Remove dots
        javaName = javaName.replaceAll("\\&", "And");   // Replace ampersands by "And"
        javaName = javaName.replaceAll(" \\& ", "And"); // Replace ampersands by "And"

        if (Character.isDigit(javaName.codePointAt(0))) {
            javaName = "N" + javaName;
        }
        
        if (javaName.endsWith("_"))  {
            javaName = javaName.substring(0, javaName.length() - 1);
        }
        
        javaName = dealWithReservedJavaKeywords(javaName);
        
        javaName = turnCamelCaseIntoSpaces(javaName);
        javaName = replaceUnderscoresByCamelCase(javaName);
        javaName = replaceSpacesByCamelCase(javaName);

        if (javaName.trim().isEmpty()) {
            javaName = "EMPTY"; // Believe it or not, there actually is a DictPermValue that is EMPTY in T'A.. ;(
        }
        
        return javaName;
    }
    
    // Added for DS-6121 -- START
 	public static String getMetaDictPermName(String dictPermName) {
 		String metaDictPermName = dictPermName;
 		
 		if (StringUtils.isNotBlank(metaDictPermName)) {
 			metaDictPermName = metaDictPermName.trim().replaceAll("[^A-Za-z0-9 ]", "_");
 		}
 		
 		return metaDictPermName;
 	}
 	// Added for DS-6121 -- END
}
