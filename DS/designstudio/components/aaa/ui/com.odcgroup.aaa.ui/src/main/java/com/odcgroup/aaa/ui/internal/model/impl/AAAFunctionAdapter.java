package com.odcgroup.aaa.ui.internal.model.impl;

import com.odcgroup.aaa.ui.internal.model.AAAFunction;

/**
 * @author yan
 */
public class AAAFunctionAdapter implements AAAFunction {

	private String displayName;
	private String procName;
	
	public AAAFunctionAdapter(String displayName, String procName) {
		this.displayName = displayName;
		this.procName = procName;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public String getProcName() {
		return procName;
	}

}
