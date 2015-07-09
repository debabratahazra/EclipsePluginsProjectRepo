package com.odcgroup.domain.scoping;

import com.odcgroup.workbench.dsl.naming.MultiDelimiterQualifiedNameConverter;

/**
 * @author phanikumark
 *
 */
public class DomainQualifiedNameConverter extends
		MultiDelimiterQualifiedNameConverter {

	@Override
	public String[] getDelimiters() {
		return new String[] { ":", ":" };
	}

}
