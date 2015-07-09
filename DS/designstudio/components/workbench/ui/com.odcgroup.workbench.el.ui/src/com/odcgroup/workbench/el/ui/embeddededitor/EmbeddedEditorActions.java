package com.odcgroup.workbench.el.ui.embeddededitor;

import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.IWorkbench;

import com.google.inject.Inject;

/**
 * Extension of Xtext class, due to bug in it which will be corrected in future version.
 * When we'll upgrade to v2.6.1, this can be removed.
 * 
 * @see org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorActions
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=435638
 * @see http://rd.oams.com/browse/DS-7436
 */
@SuppressWarnings("restriction")
public class EmbeddedEditorActions extends org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorActions {

	public static class Factory {
		
		@Inject(optional=true)
		protected IWorkbench workbench;
		
		protected EmbeddedEditorActions createActions(ISourceViewer viewer) {
			return new EmbeddedEditorActions(viewer, workbench);
		}
		
	}

	/**
	 * @param viewer
	 * @param workbench
	 */
	public EmbeddedEditorActions(ISourceViewer viewer, IWorkbench workbench) {
		super(viewer, workbench);
	}
	
	@Override
	protected void updateAction(String actionId) {
		super.updateAction(actionId);
	}
	
	@Override
	public void updateAllActions() {
		super.updateAllActions();
	}
	
	@Override
	protected void updateSelectionDependentActions() {
		super.updateSelectionDependentActions();
	}
	
	@Override
	protected void createActions() {
		super.createActions();
	}
	
}
