package com.odcgroup.page.ui.tests.util;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.junit.Assert;

/**
 * Helper methods for unit tests to perform Assert.assertions
 *
 * @author amc
 *
 */
public abstract class PageUiAssert {
	
	public static void assertInstanceOfMultiPageEditorPart(IEditorPart editorPart) {
		if(editorPart == null) {
			Assert.fail("EditorPart was null");
		}
		Assert.assertTrue("Expected instanceof MultiPageEditorPart, but was "+editorPart.getClass().getName(),
						editorPart instanceof MultiPageEditorPart);
	}
	
	public static void assertInstanceOfDesignEditor(IEditorPart editorPart) {
		if(editorPart == null) {
			Assert.fail("EditorPart was null");
		}
		Assert.assertTrue("Expected instanceof MultiPageEditorPart, but was "+editorPart.getClass().getName(),
						editorPart instanceof MultiPageEditorPart);
	}
}
