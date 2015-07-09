package com.odcgroup.page.ui.editor;

import java.io.ByteArrayInputStream;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

import com.odcgroup.page.log.Logger;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * NOTE: This is not really an editor. It provides a read-only
 * view of the Xsp page.
 * We suppose that the EditorInput is an InputStreamEditorInput.
 * IMPROVE GHA Performance. Should we listen to model changes and only update the editor when necessary?
 * 
 * @author Gary Hayes
 */
public class XspTextEditor extends StructuredTextEditor {
	
	/** The Model being edited. */
	private DesignEditor modelEditor;
	
	public XspTextEditor(DesignEditor modelEditor) {
		Assert.isNotNull(modelEditor);
		this.modelEditor = modelEditor;
	}
	
    /**
     * Asserts that the EditorInput is an InputStreamEditorInput.
     * 
     * @param site The IEditorSite
     * @param input The InputStreamEditorInput
     * @throws PartInitException Thrown if an error occurs
     */
    public void init(IEditorSite site, IEditorInput input) throws PartInitException {
    	Assert.isTrue(input instanceof XSPInputStreamEditorInput);
    	super.init(site, input);
    }

	/**
	 * Null implementation.
	 * 
	 * @param progressMonitor IThe ProgressMonitor
	 */
	protected void performSaveAs(IProgressMonitor progressMonitor) {
		// Do nothing
	}

	/**
	 * Null implementation.
	 * 
	 * @return false
	 */
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * This editor is never dirty since nothing can be saved.
	 * 
	 * @return false
	 */
	public boolean isDirty() {
		return false;
	}

	/**
	 * This is not really an editor therefore we return false all the time.
	 * 
	 * @return false
	 */
	public boolean isEditable() {
		return false;
	}
	
	/**
	 * Called when the editor should be updated. This occurs when
	 * it becomes the active editor.
	 */
	public void activateEditor() {
		updateXspFile();
	}
	
	/**
	 * Updates (regnerates) the XSP file.
	 */
	private void updateXspFile() {
		try {
			XSPInputStreamEditorInput isei = (XSPInputStreamEditorInput) getEditorInput();
			
			IOfsProject ofsProject = isei.getOfsResource().getOfsProject();
			
			String xspXml = TransformUtils.transformWidgetToXmlString(ofsProject.getProject(), modelEditor.getModel().getWidget());
			
			// create a new InputStreamEditorInput
			XSPInputStreamEditorInput oldInput = (XSPInputStreamEditorInput) getEditorInput();
			XSPInputStreamEditorInput newInput = new XSPInputStreamEditorInput(new ByteArrayInputStream(xspXml.getBytes()), true, oldInput.getOfsResource(), "XspPage");
			
			// Finally update the user-interface
			setInput(newInput);
		} catch (Exception te) {
			Logger.error("Transformer Exception", te);
		}
	}	
}