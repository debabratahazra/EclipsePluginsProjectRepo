package com.odcgroup.t24.version.editor.ui;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
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
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.xtext.ui.editor.XtextEditor;

import com.google.inject.Injector;
import com.odcgroup.t24.version.editor.VersionEditorActivator;
import com.odcgroup.t24.version.ui.internal.VersionDSLActivator;
import com.odcgroup.translation.core.ITranslation;

/**
 * @author phanikumark
 *
 */
public class VersionMultiPageEditor extends MultiPageEditorPart implements VerifyKeyListener, IEditingDomainProvider {
	
	private XtextEditor textEditor;
	private VersionDesignerEditor formEditor;
	private boolean dirtytext = false;
	
	public static Injector injector = VersionDSLActivator.getInstance().getInjector(VersionDSLActivator.COM_ODCGROUP_T24_VERSION_VERSIONDSL);

	@Override
	protected void createPages() {
		createVersionFormEditor();
		createVersionTextEditor();		
		setPartName(getEditorInput().getName());
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		if (formEditor.isDirty()) {
			formEditor.doSave(monitor);
		} else if (textEditor.isDirty() && dirtytext) {
			dirtytext = false;
			formEditor.changeFromOtherTab = true;
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
		if ( (newPageIndex == 1 && formEditor.isDirty()) 
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
		} else {
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
		formEditor.setActiveForm(translation);
	}
	
	/**
	 * 
	 */
	private void createVersionTextEditor() {
		try {
			textEditor = injector.getInstance(XtextEditor.class);
			int index = addPage(textEditor, getEditorInput());
			setPageText(index, "Source");
			ISourceViewer sourceViewer = textEditor.getInternalSourceViewer();
			if (sourceViewer instanceof ITextViewerExtension) {
				IContextService contextService = (IContextService)getSite().getService(IContextService.class);
				if(contextService != null) {
					contextService.activateContext(VersionEditorActivator.CONTEXT_ID);
				}
				((ITextViewerExtension) sourceViewer).appendVerifyKeyListener(this);
			}
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(),
					"Error creating DSL editor", null, e.getStatus());
		}
		
	}

	private void createVersionFormEditor() {
		try {
			formEditor = new VersionDesignerEditor();
			IEditorInput editorInput = getEditorInput();
			int index = addPage(formEditor, editorInput);
			setPageText(index, "Form");
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(),
					"Error creating form editor", null, e.getStatus());
		}
		
	}
	
	/**
	 * @return
	 */
	public VersionDesignerEditor getVersionFormEditor() {
		return formEditor;
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
			if (formEditor.isDirty()) {
				formeditor = true;
			}
			String editor = formeditor ? "Form Editor" : "Source Editor";
			String seditor = formeditor ? "Source Editor" : "Form Editor";
			String msg = editor+ " has a modified screen model. \n"
					+ "Your changes are saved automatically when switching editors.\n"
					+ "Would you like to switch to "+seditor+"?";
			isYesResult = MessageDialog.open(
							MessageDialog.CONFIRM,
							shell,
							"Screen Model Changes",
							msg, SWT.NONE);
		}
	}

	@Override
	public EditingDomain getEditingDomain() {
		return formEditor.getEditingDomain();
	}
}
