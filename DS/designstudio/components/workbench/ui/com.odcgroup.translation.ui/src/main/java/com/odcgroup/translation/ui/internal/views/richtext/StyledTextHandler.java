package com.odcgroup.translation.ui.internal.views.richtext;

import org.eclipse.swt.custom.StyledText;

abstract class StyledTextHandler implements ActionHandler {

	private StyledText text;

	protected final StyledText getText() {
		return this.text;
	}

	public StyledTextHandler(StyledText text) {
		this.text = text;
	}
	
	public void updateStyle(String selection) {
		
	}

}
