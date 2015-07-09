package com.odcgroup.tap.translation.ui.navigator;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.odcgroup.tap.translation.ui.TAPTranslationUICore;
import com.odcgroup.translation.ui.navigator.TranslationEditorInput;
import com.odcgroup.translation.ui.navigator.TranslationNode;

/**
 * TODO: Document me!
 *
 * @author atr
 */
public class TAPTranslationOpenEditor extends BaseSelectionListenerAction {
	
	public static final String ID = PlatformUI.PLUGIN_ID+ ".OpenTranslationEditorAction";
	
	private ISelectionProvider provider;
	
	private TranslationNode data = null;

	/**
	 * @param text
	 */
	protected TAPTranslationOpenEditor(IWorkbenchPage page, ISelectionProvider provider) {
		super("Open Translations");
		setToolTipText("Open Translations");
		setId(ID);
		this.provider = provider;
	}
	
	/**
	 * @return
	 */
	public String getTranslationEditorID() {
		return "com.odcgroup.translation.ui.editor.TranslationEditor";
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#isEnabled()
	 */
	public boolean isEnabled() {
		ISelection selection = provider.getSelection();
		if (!selection.isEmpty()) {
			IStructuredSelection sSelection = (IStructuredSelection)selection;
			if (sSelection.size() == 1 && sSelection.getFirstElement() instanceof TranslationNode) {
				data = (TranslationNode) sSelection.getFirstElement();
				return true;
			}
		}
		return false;
	}

	/**
	 * Runs the action.
	 */
	public void run() {
		run(this);
	}

	/**
	 * Runs the action.
	 * 
	 * @param action The action to run
	 */
	public void run(IAction action) {		
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (isEnabled()) {
			try {
				IEditorInput input = new TranslationEditorInput(data.getOfsProject()); 
				IEditorReference[] editors = window.getActivePage().getEditorReferences();
				for (IEditorReference editorReference : editors) {
					IEditorInput eInput = editorReference.getEditorInput();
					if (eInput instanceof TranslationEditorInput) {
						TranslationEditorInput mInput = (TranslationEditorInput) eInput;
						if (mInput.getOfsProject().equals(data.getOfsProject())) {
							input = mInput;
						}
					}
				}
				window.getActivePage().openEditor(input, getTranslationEditorID(), false);
			} catch (PartInitException e) {
				TAPTranslationUICore.getDefault().logError("Unable to open Translation Editor", e);
			}
		}
	}

}
