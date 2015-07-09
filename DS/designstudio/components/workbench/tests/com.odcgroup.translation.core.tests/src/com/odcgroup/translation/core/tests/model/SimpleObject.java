package com.odcgroup.translation.core.tests.model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * TODO: Document me!
 * 
 * @author atr
 * 
 */
public class SimpleObject {

	private Map<Locale, String> labels;
	private Map<Locale, String> toolTips;
	private boolean editable = false;

	public final void setLabel(Locale locale, String text) {
		labels.put(locale, text);
	}

	public final String getLabel(Locale locale) {
		return labels.get(locale);
	}

	public final void setToolTip(Locale locale, String text) {
		toolTips.put(locale, text);
	}

	public final String getToolTip(Locale locale) {
		return toolTips.get(locale);
	}

	public final void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	public boolean isEditable() {
		return editable;
	}

	public SimpleObject() {
		labels = new HashMap<Locale, String>();
		toolTips = new HashMap<Locale, String>();
	}

}
