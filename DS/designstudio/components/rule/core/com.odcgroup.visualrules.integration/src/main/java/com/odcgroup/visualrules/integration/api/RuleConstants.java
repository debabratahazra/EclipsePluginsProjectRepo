package com.odcgroup.visualrules.integration.api;

public class RuleConstants {

	public static final String VALIDATION_RULE_PACKAGE_NAME = "Validation";

	public static final String COMPLETION_RULE_PACKAGE_NAME = "Completion";

	// For oAW extensions, cannot access constants directly...
	public static String getValidationRuleVRPackageName() {
		return VALIDATION_RULE_PACKAGE_NAME;
	}

	// For oAW extensions, cannot access constants directly...
	public static String getCompletionRuleVRPackageName() {
		return COMPLETION_RULE_PACKAGE_NAME;
	}
}
