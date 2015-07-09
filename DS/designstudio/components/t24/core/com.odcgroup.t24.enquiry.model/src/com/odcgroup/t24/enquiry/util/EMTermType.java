package com.odcgroup.t24.enquiry.util;

/**
 * Define the possible types of EMTerm and their values
 */
public enum EMTermType {

	ID_TERM("TERM_ID_FIELD"),
	REQ_TERM("TERM_MANDATORY"),
	TYPE_TERM("TERM_VALUE_TYPE"),
	RANGE_TERM("TERM_RANGE"),
	LIST_TERM("TERM_LIST_TYPE"),
	T24_AA_PRODUCT_GROUP_TERM("TERM_T24_AA_PRODUCT_GROUP"),
	TERM_T24_AA_PROPERTY("TERM_T24_AA_PROPERTY"),
	TERM_LANG_TYPE("TERM_LANG_TYPE"),
	FIELD_RESTRICTION_TERM("TERM_RESTRICTION"),
	BUSINESS_TYPE_TERM("TERM_SEMANTIC_TYPE");

	private String value;

	EMTermType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
