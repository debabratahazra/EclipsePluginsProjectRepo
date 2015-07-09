package com.odcgroup.service.model.ui.coloring;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class ServiceDSLHighlightingConfiguration extends DefaultHighlightingConfiguration {

	public static final String SERVICE_MODEL_ELEMENT = "String_Value";
	public static final String SERVICE_MODEL_DOCUMENTATION = "ML_DOC";
	
	
	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		acceptor.acceptDefaultHighlighting(SERVICE_MODEL_ELEMENT, "Service Entity", serviceTextStyle());
		acceptor.acceptDefaultHighlighting(SERVICE_MODEL_DOCUMENTATION, "Service DSL Documentation", serviceDocStyle());
		super.configure(acceptor);
	}
	
	public TextStyle serviceTextStyle() {
		TextStyle textStyle = new TextStyle();
		textStyle.setColor(new RGB(0, 0, 125));
		return textStyle;
	}
	
	public TextStyle serviceDocStyle() {
		TextStyle textStyle = new TextStyle();
		textStyle.setColor(new RGB(63, 127, 95));
		return textStyle;
	}
}
