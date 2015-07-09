package com.odcgroup.domain.ui.editor;

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
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.ui.editor.XtextEditor;

import com.google.inject.Injector;
import com.odcgroup.domain.ui.internal.DomainActivator;
import com.odcgroup.mdf.editor.ui.editors.DomainModelEditor;

public class DomainMultiPageEditor extends DomainModelEditor implements VerifyKeyListener {	

	private XtextEditor textEditor;
	private boolean dirtytext = false;
	
	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.editor.ui.editors.DomainModelEditor#createOtherDomainEditor()
	 */
	protected  void createOtherDomainEditor() {
		try {
			Injector injector = DomainActivator.getInstance().getInjector(DomainActivator.COM_ODCGROUP_DOMAIN_DOMAIN);
			textEditor = injector.getInstance(XtextEditor.class);
			int index = addPage(textEditor, getEditorInput());
			setPageText(index, "Source");
			ISourceViewer sourceViewer = textEditor.getInternalSourceViewer();
			if (sourceViewer instanceof ITextViewerExtension) {
				((ITextViewerExtension) sourceViewer).appendVerifyKeyListener(this);
			}
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(),
					"Error creating DSL editor", null, e.getStatus());
		}				
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.editor.ui.editors.DomainModelEditor#getAdapter(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class key) {
		if (getActiveEditor() instanceof XtextEditor) {
            return textEditor.getAdapter(key);
		} else {
			return super.getAdapter(key);
		}
	}
	
	 /* (non-Javadoc)
	 * @see com.odcgroup.mdf.editor.ui.editors.DomainModelEditor#isDirty()
	 */
	public boolean isDirty() {
		 if (textEditor != null && textEditor.isDirty() && dirtytext) {
			 return true;
		 }
		 return super.isDirty();
	 }
	


	@Override
	public void doSave(IProgressMonitor monitor) {
		if (textEditor != null && textEditor.isDirty() && dirtytext) {
			textEditor.doSave(monitor);
			changeFromOtherTab = true;
			dirtytext = false;
		} else {
			super.doSave(monitor);
		}
	}
	
	 /**
     * This is used to track the active viewer.
     */
    protected void pageChange(int newPageIndex) {
    	if ((newPageIndex == 0 && (textEditor != null && textEditor.isDirty()) 
    			|| (newPageIndex == 1 && super.isDirty()))) {
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

	@Override
	public void verifyKey(VerifyEvent event) {
		this.dirtytext = true;		
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
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			boolean dslEditor = false;
			if (textEditor.isDirty()) {
				dslEditor = true;
			}
			String editor = dslEditor ? "Source Editor" : "Domain Editor";
			String seditor = dslEditor ? "Domain Editor" : "Source Editor";
			String msg = editor+ " has a modified domain model. \n"
					+ "Your changes are saved automatically when switching editors.\n"
					+ "Would you like to switch to "+seditor+"?";
			isYesResult = MessageDialog.open(
							MessageDialog.CONFIRM,
							shell,
							"Domain Model Changes",
							msg, SWT.NONE);
		}
	}

}
