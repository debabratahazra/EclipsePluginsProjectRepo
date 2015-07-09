package com.odcgroup.translation.ui.editor.model;

import java.util.Locale;

/**
 *
 * @author pkk
 *
 */
public class TranslationTableColumn implements ITranslationTableColumn {
	
	private String text;
	private int style;
	private int weight;
	private boolean resizeable;
	private Locale locale = null;

	/**
	 * @param text
	 * @param style
	 * @param weight
	 * @param resizeable
	 */
	public TranslationTableColumn(String text, int style, int weight, boolean resizeable) {
		this.text = text;
		this.style = style;
		this.weight = weight;
		this.resizeable = resizeable;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationTableColumn#getWeight()
	 */
	@Override
	public int getWeight() {
		return weight;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationTableColumn#getText()
	 */
	@Override
	public String getText() {
		return text;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationTableColumn#isResizeable()
	 */
	@Override
	public boolean isResizeable() {
		return resizeable;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationTableColumn#getStyle()
	 */
	@Override
	public int getStyle() {
		return style;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationTableColumn#getLocale()
	 */
	@Override
	public Locale getLocale() {
		return locale;
	}

}
