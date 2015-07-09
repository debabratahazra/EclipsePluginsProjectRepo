package com.odcgroup.translation.ui.internal.views.richtext;

interface ActionHandler {
	
	boolean isStyleSelected(boolean selected);
	
	void updateStyle(boolean selected);
	
	void updateStyle(String selection);

}
