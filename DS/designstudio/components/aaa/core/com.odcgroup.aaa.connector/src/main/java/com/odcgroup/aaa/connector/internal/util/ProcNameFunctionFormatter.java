package com.odcgroup.aaa.connector.internal.util;

import java.util.StringTokenizer;

/**
 * @author yan
 */
public class ProcNameFunctionFormatter {

	public static String getDashedProcNameFunction(String procNameFunction) {
		return procNameFunction.replace('_', '-');
	}
	
	public static String getCamelCaseProcNameFunction(String procNameFunction) {
		return getProcNameFunction(procNameFunction, true);
	}
	
	public static String getLowerCaseProcNameFunction(String procNameFunction) {
		return getProcNameFunction(procNameFunction, false);
	}
	
	private static String getProcNameFunction(String procNameFunction, boolean camelCase) {
		StringBuffer result = new StringBuffer();
		StringTokenizer st = new StringTokenizer(getDashedProcNameFunction(procNameFunction), "-");
		while (st.hasMoreTokens()) {
			if (camelCase) {
				result.append(toFirstUpper(st.nextToken()));
			} else {
				result.append(st.nextToken().toLowerCase());
			}
		}
		return result.toString();
	}
	
    private static String toFirstUpper(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    
    public static String getProcFileNameFunction(String procNameFunction) {
    	String procName = procNameFunction;
    	procName = procName.replaceAll("[\\\\/:*'\"<>|#]", "_");
    	return procName;
    }
}
