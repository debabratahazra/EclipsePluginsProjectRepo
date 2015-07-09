package com.odcgroup.mdf.generation.rules;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.ext.java.JavaAspect;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;

public class RuleHelper {

	public final static String ODC_RULE_PACKAGE = "rules";
	public final static String DEFAULT_RULE_NAME = "System";
	public final static String RULE_SUFFIX = "Rule";

	// For oAW extensions, cannot access constants directly...
	public static String getRulePackage() {
		return ODC_RULE_PACKAGE;
	}

	// For oAW extensions, cannot access constants directly...
	public static String getDefaultRuleName() {
		return DEFAULT_RULE_NAME;
	}

	// For oAW extensions, cannot access enums directly...
	public static String getValidationRulePackage() {
		return RuleCategory.VALIDATION.packageName();
	}

	// For oAW extensions, cannot access enums directly...
	public static String getCompletionRulePackage() {
		return RuleCategory.COMPLETION.packageName();
	}

	// For oAW extensions, cannot access constants directly...
	public static String getRuleSuffix() {
		return RULE_SUFFIX;
	}
	
	public static String getRuleName(MdfEntity entity, RuleCategory category, String ruleShortName) {
		return entity.getName() + ruleShortName + StringUtils.capitalize(category.packageName()) + getRuleSuffix();
	}

	public static String getDefaultRuleName(MdfEntity entity, RuleCategory category) {
		return getRuleName(entity, category, getDefaultRuleName());
	}
	
	public static String getValidationRuleName(MdfEntity entity, String ruleShortName) {
		return getRuleName(entity, RuleCategory.VALIDATION, ruleShortName);
	}
	
	public static String getDefaultValidationRuleName(MdfEntity entity) {
		return getDefaultRuleName(entity, RuleCategory.VALIDATION);
	}

	public static String getCompletionRuleName(MdfEntity entity, String ruleShortName) {
		return getRuleName(entity, RuleCategory.COMPLETION, ruleShortName);
	}

	public static String getDefaultCompletionRuleName(MdfEntity entity) {
		return getDefaultRuleName(entity, RuleCategory.COMPLETION);
	}
	
	public static String getJavaPackageName(MdfDomain domain) {
		return JavaAspect.getPackage(domain);
	}
}
