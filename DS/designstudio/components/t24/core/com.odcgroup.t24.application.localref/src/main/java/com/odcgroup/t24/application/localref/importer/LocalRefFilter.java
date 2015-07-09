package com.odcgroup.t24.application.localref.importer;

import com.odcgroup.t24.application.localref.ILocalRefFilter;
import com.odcgroup.t24.server.external.model.LocalRefDetail;

/**
 * @author ssreekanth
 *
 */
class LocalRefFilter implements ILocalRefFilter {

	private static final String DEFAULT_CODE_PATTERN = "*"; //$NON-NLS-1$

	private String namePattern = DEFAULT_CODE_PATTERN;
	
	@Override
	public boolean accept(LocalRefDetail localref) {

		if (!(StringMatch.match(localref.getName(), getName()))) {
			return false;
		}
		return true;
	}

	public final String getName() {
		return namePattern;
	}

	public final void setName(String name) {
		this.namePattern = name;
	}

	public LocalRefFilter() {
	}

}
