package com.odcgroup.page.ui.editor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.log.Logger;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.tap.validation.ValidationUtil;
import com.odcgroup.workbench.ui.ModelEditorInput;
import com.odcgroup.workbench.ui.OfsUIResourceHelper;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

/**
 * The PageDesignerEditor is the main editor for the PageDesigner. It is a tabbed editor which contains the different
 * views of the Model.
 * 
 * @author Alexandre Jaquet
 * @author Gary Hayes
 */
public class PageDesignerEditor extends MultiPageEditorPart {
	
	/** The DesignEditor. We keep a reference to this since this editor performs the save. */
	private DesignEditor designEditor;

	/** The DSL Editor. We keep a reference to this since this editor performs the save, if we do not have a valid model. */
	private TextEditor dslEditor;

	/** The current page index. */
	private int currentPageIndex;
	

	/**
	 * @see org.eclipse.ui.part.MultiPageEditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (input instanceof IFileEditorInput) {
			IFileEditorInput fei = (IFileEditorInput) input;
			IFile file = fei.getFile();
			try {
				// ensure the resource file is uptodate.
				file.refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (CoreException ex) {
				Logger.error("Unable to refesh local resource"+ ex.getMessage());
			}
		}
		super.init(site, input);
		//ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener, IResourceChangeEvent.POST_CHANGE);
	}

	/** 
	 * @see org.eclipse.ui.part.MultiPageEditorPart#dispose()
	 */
	public void dispose() {
		//ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
		super.dispose();
	}

	/**
	 * Override the base class version to return the EditPart from the DesignEditor
	 * if asked.
	 * 
	 * @param adapter The Class to adapt to
	 * @return Object The adapted Object
	 */
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		if(designEditor==null) return null;
		if (adapter == EditPart.class || adapter == WidgetEditPart.class) {
			return designEditor.getAdapter(adapter);
		} else if (adapter == GraphicalViewer.class) {
			return designEditor.getAdapter(adapter);
		}
		
		return super.getAdapter(adapter);
	}	

	/**
	 * Create a DesignEditor.
	 */
	private boolean createDesignPage() {
		boolean success = true;
		try {
			designEditor = new DesignEditor();
			int index = addPage(designEditor, getEditorInput());
			setPageText(index, "Design");
			IContextService contextService = (IContextService) getSite()
			  .getService(IContextService.class);
			if (contextService != null) {
				contextService.activateContext(PageUIPlugin.CONTEXT_ID);
			}
		} catch (PartInitException ex) {
			success = false;
			ErrorDialog.openError(getSite().getShell(), "Error creating Design Editor", ex.getMessage(), ex.getStatus());
		} catch (PageException ex) {
			success = false;
			ErrorDialog.openError(getSite().getShell(), "Error creating Design Editor", ex.getMessage(), ex.getStatus());
		} catch (Exception ex) {
			success = false;
			Logger.error("The Page Design Editor raised an exception ", ex);
		}
		return success;
	}

	/**
	 * Create an xsp editor.
	 */
	private void createXspPage() {
		try {
			IEditorPart xspTextEditor = new XspTextEditor(designEditor);
			XSPInputStreamEditorInput sie = createInputStreamEditorInput("XspPage");
			if (null == sie) {
				IStatus status = new Status(IStatus.ERROR, PageUIPlugin.PLUGIN_ID, 
						"Cannot create the XSP Page for the selected model");
				ErrorDialog.openError(getSite().getShell(), "Error creating Code Property Editor", null, status);
			}
			int index = addPage(xspTextEditor, sie);
			setPageText(index, "XSP Code");
		} catch (PartInitException ex) {
			ErrorDialog.openError(getSite().getShell(), "Error creating XSP Editor", null, ex.getStatus());
		} catch (ModelNotFoundException ex) {
			IStatus status = new Status(IStatus.ERROR, PageUIPlugin.PLUGIN_ID, 
					"Cannot create the XSP Page for the selected model", ex);
			ErrorDialog.openError(getSite().getShell(), "Error creating XSP Editor", null, status);
		} catch (CoreException ex) {
			ErrorDialog.openError(getSite().getShell(), "Error creating XSP Editor", null, ex.getStatus());
		}
	}
	
	private void createDSLTextPage() {
		try {
			dslEditor = new TextEditor();
			addPage(0, dslEditor, getEditorInput()); 
			setPageText(0, "DSL");
		} catch (CoreException ex) {
			ErrorDialog.openError(getSite().getShell(), "Error creating DSL Editor", null, ex.getStatus());
		}
	}

	/**
	 * Initialize the editors.
	 */
	protected void createPages() {
		if(!fileHasSyntaxErrors()) {
			boolean success = createDesignPage();
			if(success) {
				//Set selected file name to the editor
				setPartName(getEditorInput().getName());
				// create additional pages in case of file/model not remote resource
				if (getEditorInput() instanceof IFileEditorInput || getEditorInput() instanceof ModelEditorInput) {
					createXspPage();
				}
			}
			else {
				createDSLTextPage();
			}
		} else {
			createDSLTextPage();
		}
		// Set help
        PlatformUI.getWorkbench().getHelpSystem().setHelp(getContainer(), IOfsHelpContextIds.WORKBENCH);
	}

	private boolean fileHasSyntaxErrors() {
		try {
			IEditorInput editorInput = getEditorInput();
			if (editorInput instanceof IFileEditorInput) {
				IFileEditorInput fileEditorInput = (IFileEditorInput) editorInput;
				IOfsElement ofsElement = (IOfsElement) fileEditorInput.getFile().getAdapter(IOfsElement.class);
				if (ofsElement instanceof IOfsModelResource) {
					IOfsModelResource modelResource = (IOfsModelResource) ofsElement;
					Collection<Diagnostic> syntaxErrors = ValidationUtil.validateFileSyntax(modelResource, true, null);
					return syntaxErrors.size()>0;
				}
			}
		} catch(NoClassDefFoundError e) {
			// the com.odcgroup.workbench.validation plugin is optional, so we have to catch this exception
		}
		return false;
	}

	/**
	 * Perform a save.
	 * 
	 * @param monitor
	 */
	public void doSave(IProgressMonitor monitor) {
		if(designEditor!=null) {
			designEditor.doSave(monitor);
		} else {
			dslEditor.doSave(monitor);
		}		
	}

	/**
	 * Perform a save as.
	 */
	public void doSaveAs() {
		if(designEditor!=null) {
			designEditor.doSaveAs();
		} else {
			dslEditor.doSaveAs();
		}
	}

	/**
	 * Does the saves functionalities are allowed ?
	 * 
	 * @return boolean True if the saveAs is allwoed
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * Called when the page is changed. This method try everytime to save the current property value called "code". We
	 * do not know yet this property so we have to invoke the method each time we are at index 0, the PageDesigner.
	 * 
	 * @param newPageIndex
	 *            The new page index
	 */
	public void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		
		// This is a bit ugly but it works. When we disactivate the CodePropertyEditor
		// we need to try and transform the code into Widgets.
		IEditorPart oldEditor = getEditor(currentPageIndex);
		if (oldEditor instanceof CodePropertyEditor) {
			CodePropertyEditor cpe = (CodePropertyEditor) oldEditor;
			cpe.disactivateEditor();
		}
		
		// Activate / Disactivate the editable text
		IEditorPart newEditor = getEditor(newPageIndex);	
		if (newEditor instanceof CodePropertyEditor) {
			CodePropertyEditor cpe = (CodePropertyEditor) newEditor;
			cpe.activateEditor();
		}		
		
		// We also need to force the regeneration of the XSP page
		if (newEditor instanceof XspTextEditor) {
			XspTextEditor xspe = (XspTextEditor) newEditor;
			//InputStreamEditorInput sie = createInputStreamEditorInput("XspPage");	
			xspe.activateEditor();
		}
			
		currentPageIndex = newPageIndex;
	}
	
	/**
	 * Creates an InputStreamEditorInput.
	 * 
	 * @param id The id of the InputStreamEditorInput 
	 * @return InputStreamEditorInput
	 * @throws ModelNotFoundException 
	 * @throws CoreException 
	 */
	private XSPInputStreamEditorInput createInputStreamEditorInput(String id) throws ModelNotFoundException, CoreException {
		
		final boolean readOnly = true;
		InputStream is = new ByteArrayInputStream(new byte[]{});
		IEditorInput editorInput = getEditorInput();
		
		IOfsModelResource ofsModelResource = null;		

		IOfsProject ofsProject = OfsUIResourceHelper.getOfsProject(editorInput);
		if (ofsProject != null) {
			
			if (editorInput instanceof IFileEditorInput) {
				IFileEditorInput fei = (IFileEditorInput) editorInput;
				IFile file = fei.getFile();
				URI uri = ModelURIConverter.createModelURI((IResource)file);
				ofsModelResource = ofsProject.getOfsModelResource(uri);
				
			} else if (editorInput instanceof ModelEditorInput) {
				ModelEditorInput mei = (ModelEditorInput)editorInput;
				ofsModelResource = (IOfsModelResource)mei.getStorage();
			}
		}
		
		return new XSPInputStreamEditorInput(is, readOnly, ofsModelResource, id);

	}
	
}
