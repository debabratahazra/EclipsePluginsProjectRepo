package com.odcgroup.aaa.ui.internal.model.impl;

import com.odcgroup.aaa.ui.internal.model.AAAFormatCode;

/**
 * @author atr
 * @since DS 1.40.0
 */
class AAAFormatCodeAdapter implements AAAFormatCode {
	
	private String code;
	private String function;
	private String denom;

	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAFormatCode#getCode()
	 */
	public final String getCode() {
		return code;
	}

	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAFormatCode#getDescription()
	 */
	public final String getDenom() {
		return denom;
	}
	
	public final String getFunction() {
		return function;
	}
	
	/**
	 * @param code
	 * @param description
	 */
	public AAAFormatCodeAdapter(String code, String function, String denom) {
		this.code = code;
		this.function = function;
		this.denom = denom;
	}

}
