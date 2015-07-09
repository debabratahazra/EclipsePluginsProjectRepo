package com.odcgroup.t24.enquiry.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextViewerExtension;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.xtext.ui.editor.XtextEditor;

import com.google.inject.Injector;
import com.odcgroup.t24.enquiry.ui.internal.EnquiryActivator;
import com.odcgroup.translation.core.ITranslation;

/**
 *
 * @author phanikumark
 *
 */
public class EnquiryMultipageEditor extends MultiPageEditorPart implements VerifyKeyListener {

	private XtextEditor textEditor;
	private EnquiryEditor graphicalEditor;
	private boolean dirtytext = false;

	@Override
	protected void createPages() {
		createEnquiryGraphicalEditor();
		createEnquiryTextEditor();		
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		setPartName(input.getName());
		IContextService contextService = (IContextService) getSite().getService(IContextService.class);
		if (contextService != null) {
			contextService.activateContext("com.odcgroup.t24.enquiry.model.ui.context");
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		if (graphicalEditor.isDirty()) {
			graphicalEditor.doSave(monitor);
		} else if (textEditor.isDirty() && dirtytext) {
			dirtytext = false;
			graphicalEditor.changeFromOtherTab = true;
			/*
			//TODO: DS-8293 - commenting out the field sorting for time-being
			textEditor.getDocument().modify(new IUnitOfWork.Void<XtextResource>() {
				@Override
				public void process(XtextResource resource) throws Exception {
					EObject model = resource.getParseResult().getRootASTElement();
					if(model instanceof Enquiry){
						Enquiry enquiry = (Enquiry) model;
						EnquiryUtil.sortEnquiryFields(enquiry.getFields());
					}
				}
			});
			*/
			textEditor.doSave(monitor);
		}
	}

	@Override
	public void doSaveAs() {		
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void verifyKey(VerifyEvent event) {
		this.dirtytext = true;		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorPart#pageChange(int)
	 */
	protected void pageChange(int newPageIndex) {
		if ( (newPageIndex == 1 && graphicalEditor.isDirty()) 
				|| (newPageIndex == 0 && textEditor.isDirty())) {
			if (querySwitchTab()) {
				super.pageChange(newPageIndex);
				IEditorPart active = getActiveEditor();
				if (isDirty()) {
					doSave(new NullProgressMonitor());
					if (active == textEditor) {
						textEditor.setInput(getEditorInput());
					}
				}
			} else {
				if (newPageIndex == 0) {
					newPageIndex = 1;
				} else {
					newPageIndex = 0;
				}
				setActivePage(newPageIndex);
				super.pageChange(newPageIndex);
			}
		}
		else {
			super.pageChange(newPageIndex);
		}
	}
	
	/**
	 * @param index
	 */
	public void setActiveForm(int index) {
		setActivePage(index);
	}
	
	/**
	 * This method is called from the translation overview to put the focus
	 * on the designated page index that correspond to the version/field currently selected in
	 * the translation overview.
	 * @param formIndex the form to be activated
	 */
	public void setActiveForm(ITranslation translation) {
		setActivePage(0);
	}
	
	/**
	 * 
	 */
	private void createEnquiryTextEditor() {
		try {
			Injector injector = EnquiryActivator.getInstance().getInjector(EnquiryActivator.COM_ODCGROUP_T24_ENQUIRY_ENQUIRY);
			textEditor = injector.getInstance(XtextEditor.class);
			int index = addPage(textEditor, getEditorInput());
			setPageText(index, "Source");
			ISourceViewer sourceViewer = textEditor.getInternalSourceViewer();
			if (sourceViewer instanceof ITextViewerExtension) {
				IContextService contextService = (IContextService)getSite().getService(IContextService.class);
				if(contextService != null) {
					//contextService.activateContext(EnquiryActivator.CONTEXT_ID);
				}
				((ITextViewerExtension) sourceViewer).appendVerifyKeyListener(this);
			}
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(),
					"Error creating DSL editor", null, e.getStatus());
		}
		
	}

	/**
	 * 
	 */
	private void createEnquiryGraphicalEditor() {
		try {
			graphicalEditor = new EnquiryEditor();
			IEditorInput editorInput = getEditorInput();
			int index = addPage(graphicalEditor, editorInput);
			setPageText(index, "Enquiry");
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(),
					"Error creating Enquiry editor", null, e.getStatus());
		}
		
	}
	
	/**
	 * @return
	 */
	public EnquiryEditor getEnquiryEditor() {
		return graphicalEditor;
	}

	/**
	 * @return
	 */
	private boolean querySwitchTab() {
		DialogPrompter dialogPrompter = new DialogPrompter();
		Display.getDefault().syncExec(dialogPrompter);
		boolean yesResult = dialogPrompter.isYesResult();
		return yesResult;
	}

	/**
	 *
	 */
	private class DialogPrompter implements Runnable {

		private boolean isYesResult;

		public boolean isYesResult() {
			return isYesResult;
		}

		public void run() {
			Shell shell = getActiveEditor().getSite().getShell();
			boolean formeditor = false;
			if (graphicalEditor.isDirty()) {
				formeditor = true;
			}
			String editor = formeditor ? "Graphical Editor" : "Source Editor";
			String seditor = formeditor ? "Source Editor" : "Graphical Editor";
			String msg = editor+ " has a modified screen model. \n"
					+ "Your changes are saved automatically when switching editors.\n"
					+ "Would you like to switch to "+seditor+"?";
			isYesResult = MessageDialog.open(
							MessageDialog.CONFIRM,
							shell,
							"Enquiry Model Changes",
							msg, SWT.NONE);
		}
	}

}
