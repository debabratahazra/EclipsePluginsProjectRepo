package com.odcgroup.t24.mdf.editor.ui.dialogs;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.metamodel.MdfProperty;

/**
 * @author ssreekanth
 *
 */
class LocalRefFilter {

	private static final String DEFAULT_CODE_PATTERN = "*"; //$NON-NLS-1$

	private String namePattern = DEFAULT_CODE_PATTERN;
	
	public boolean accept(MdfProperty property) {

		String pattern = getName();
		pattern = pattern.toUpperCase();
		pattern = StringUtils.replace(pattern, "*", ".*"); //$NON-NLS-1$ $NON-NLS-2$
		
		String name = property.getName();

		if (!name.matches(pattern)) {
			return false;
		}
		return true;
	}
	
	public boolean accept(String property) {

		String pattern = getName();
		pattern = pattern.toUpperCase();
		pattern = StringUtils.replace(pattern, "*", ".*"); //$NON-NLS-1$ $NON-NLS-2$
		String name = property;

		if (!name.matches(pattern)) {
			return false;
		}
		return true;
	}

	public final String getName() {
		return namePattern;
	}

	public final void setName(String name) {
		if(name.contains("?")) {
			name = name.replaceAll("?", "*");
		}
		this.namePattern = name;
	}

	public LocalRefFilter() {
	}

}
