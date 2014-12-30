package com.tel.autosysframework.editor;

import org.eclipse.ui.editors.text.TextEditor;


public class AutosysINIEditor extends TextEditor {
	
	public AutosysINIEditor() {
		setSourceViewerConfiguration(new AutosysSourceViwerConfiguration());
	}
	
}
