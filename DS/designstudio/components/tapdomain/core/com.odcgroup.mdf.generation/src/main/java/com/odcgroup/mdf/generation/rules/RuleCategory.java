package com.odcgroup.mdf.generation.rules;


import com.odcgroup.workbench.core.helper.StringHelper;

/**
 * This enumeration defines the different categories of rules that we can handle
 * in the Design Studio
 * 
 * @author Kai Kreuzer
 */
public enum RuleCategory {
	VALIDATION, COMPLETION;

	/**
	 * @return the name of the RuleModel package corresponding to the
	 *         RuleCategory
	 */
	public String packageName() {
		return this.toString().toLowerCase();
	}
	
	public String toString() {
		return StringHelper.toFirstUpper(this.name().toLowerCase());		
	}
}
