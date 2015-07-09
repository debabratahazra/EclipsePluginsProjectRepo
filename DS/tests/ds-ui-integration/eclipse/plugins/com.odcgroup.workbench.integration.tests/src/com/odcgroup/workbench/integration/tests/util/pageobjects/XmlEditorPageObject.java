package com.odcgroup.workbench.integration.tests.util.pageobjects;

/**
 * TODO: Document me!
 *
 * @author amc
 *
 */
public class XmlEditorPageObject extends EditorPageObject {

	public XmlEditorPageObject(String fileName) {
		super(fileName);
	}
	
	public String getText() {
		return editor.toTextEditor().getText();
	}
}
