package com.odcgroup.domain.ui.coloring;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class DomainDSLHighlightingConfiguration extends DefaultHighlightingConfiguration {

	public static final String MDF_MODEL_ELEMENT = "String_Value";
	public static final String MDF_MODEL_DOCUMENTATION = "ML_DOC";
	
	
	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		acceptor.acceptDefaultHighlighting(MDF_MODEL_ELEMENT, "Domain Entity", mdfTextStyle());
		acceptor.acceptDefaultHighlighting(MDF_MODEL_DOCUMENTATION, "Domain DSL Documentation", mdfDocStyle());
		super.configure(acceptor);
	}
	
	public TextStyle mdfTextStyle() {
		TextStyle textStyle = new TextStyle();
		textStyle.setColor(new RGB(0, 0, 125));
		return textStyle;
	}
	
	public TextStyle mdfDocStyle() {
		TextStyle textStyle = new TextStyle();
		textStyle.setColor(new RGB(63, 127, 95));
		return textStyle;
	}
}
