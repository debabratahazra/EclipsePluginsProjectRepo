package com.odcgroup.t24.menu.menu.presentation;

import org.eclipse.xtext.ui.editor.XtextEditor;

public class MenuEmbeddedXtextEditor extends XtextEditor {

	@Override
	protected void handleEditorInputChanged() {
		/**
		 * do not call super.handleEditorInputChanged in order to avoid the
		 * message "The file XXX has been changed on the file system do you want
		 * to replace the editor contents with these changes"
		 */
	}

	public MenuEmbeddedXtextEditor() {
	}

}
