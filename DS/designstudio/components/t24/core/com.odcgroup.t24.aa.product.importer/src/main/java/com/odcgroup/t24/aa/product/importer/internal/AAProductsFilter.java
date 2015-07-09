package com.odcgroup.t24.aa.product.importer.internal;

import com.odcgroup.t24.aa.product.importer.IAAProductsFilter;
import com.odcgroup.t24.server.external.model.AAProductsDetails;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public class AAProductsFilter implements IAAProductsFilter {
	
	private static final String DEFAULT_CODE_PATTERN = "*";

	private String namePattern = null;

	@Override
	public boolean accept(AAProductsDetails model) {
		if (!(StringMatch.match(model.getName(), getName()))) {
			return false;
		} 
		return true;
	}

	@Override
	public String getName() {
		if(namePattern == null){
			return DEFAULT_CODE_PATTERN;
		}
		return namePattern;
	}

	@Override
	public void setName(String name) {
		this.namePattern = name;
	}
}
