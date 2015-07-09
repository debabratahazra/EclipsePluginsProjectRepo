package com.odcgroup.t24.application.localrefapplication.importer;

import com.odcgroup.t24.application.localref.importer.StringMatch;
import com.odcgroup.t24.application.localrefapplication.ILocalRefApplicationFilter;
import com.odcgroup.t24.server.external.model.LocalRefApplicationDetail;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public class LocalRefApplicationFilter implements ILocalRefApplicationFilter {
	
	private static final String DEFAULT_CODE_PATTERN = "*";

	private String namePattern = DEFAULT_CODE_PATTERN;

	@Override
	public boolean accept(LocalRefApplicationDetail model) {
		if (!(StringMatch.match(model.getName(), getName()))) {
			return false;
		} 
		return true;
	}

	@Override
	public String getName() {
		return namePattern;
	}

	@Override
	public void setName(String name) {
		this.namePattern = name;
	}
}
