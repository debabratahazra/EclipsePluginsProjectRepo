package com.odcgroup.t24.menu.menu.presentation;

import java.util.EventObject;

import javax.inject.Inject;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.texteditor.TextOperationAction;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextModelListener;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.odcgroup.t24.menu.editor.commands.SwitchEMFResourceCommand;
import com.odcgroup.t24.menu.menu.MenuRoot;

public class SyncHelper {

	@Inject 
	private MenuComparator menuComparator;
	
	private MenuEmbeddedXtextEditor xeditor;
	private MenuEditor editor;

	/**
	 * If an update of the xtext editor is required.
	 */
	private boolean updateXtextRequired;

	/**
	 * Listener for Xtext changes.
	 */
	private IXtextModelListener xtextListener;

	/**
	 * Listener for EMF changes.
	 */
	private CommandStackListener emfListener;

	/**
	 * Block recursive calls during updating.
	 */
	private boolean updateInProgress;

	/**
	 * The constructor.
	 * 
	 * @param xeditor
	 */
	public SyncHelper() {
		
	}
	
	
	public void setEditor(MenuEditor editor) {
		this.editor = editor;
		this.xeditor = editor.getXtextEditor();
		updateInProgress = false;

		createListeners();

		// add sync listeners
		xeditor.getDocument().addModelListener(xtextListener);
		editor.getEditingDomain().getCommandStack().addCommandStackListener(emfListener);

		// initial sync emf->xtext
		updateEMFToXtext();
	}

	private void createListeners() {

		xtextListener = new IXtextModelListener() {
			@Override
			public void modelChanged(XtextResource resource) {
				updateXtextToEMF(resource);
			}
	    };

	    emfListener = new CommandStackListener() {
			@Override
			public void commandStackChanged(EventObject event) {
				Command mostRecentCommand = ((CommandStack) event.getSource()).getMostRecentCommand();
				
				if (mostRecentCommand != null) {
					if (editor.getActiveEditor() == xeditor) {
						// xtext visible -> update now
						updateEMFToXtext();
					} else {
						// xtext not visible -> update on pagechange
						updateXtextRequired = true;
					}
				} 
			}
		};
	}

	public void pageChange(int pageIndex) {
		
		if (editor.getActiveEditor() instanceof XtextEditor) {
			// TODO switch statusline&actions
			if (editor.getEditor(pageIndex) instanceof XtextEditor) {
				if (updateXtextRequired) {
					// emf model changed -> update xtext representation
					updateEMFToXtext();
					updateXtextRequired = false;
				} else {
					// if not in update mode, try to sync selection
					SelectionChangedEvent event = new SelectionChangedEvent(editor.getContentOutlinePage(), editor
							.getContentOutlinePage().getSelection());
//					editor.getContentOutlinePage().getOutlineSelectionChangedListener().selectOutlineEntry(event);
				}
			}
		} else {
			editor.getMenuTreeViewer().expandAll();
		}
	}

	/**
	 * Update EMF contents after document modification.
	 */
	public void updateXtextToEMF(XtextResource resource) {
		if (updateInProgress)
			return;

		try {
			// update only if parsing gave no errors
			if (resource.getContents().size() == 0 || resource.getErrors().size() > 0)
				return;

			// read
			final EList<EObject> contents = editor.getEditingDomain().getResourceSet().getResources().get(0)
					.getContents();
			final MenuRoot menuEMF = (MenuRoot) editor.getEditingDomain().getResourceSet()
					.getResources().get(0).getContents().get(0);
			final MenuRoot menuXtext = (MenuRoot) resource.getContents().get(0);

			// update only if models are different
			SaveOptions options = SaveOptions.newBuilder().format().getOptions();
			String menuEMFString= editor.getXtextSerializer().serialize(menuEMF,options);
			String menuXtextString= editor.getXtextSerializer().serialize(menuXtext,options);
			if(menuEMFString.equals(menuXtextString))
				return;

			Display.getDefault().asyncExec(new Runnable() {

				@Override
				public void run() {
					beginUpdate();

					// synchronized modify
					// transfer copy of xText resource to EMF editor
					SwitchEMFResourceCommand command = new SwitchEMFResourceCommand(editor, contents, menuEMF,
							menuXtext);
					editor.getEditingDomain().getCommandStack().execute(command);

					endUpdate();
				}
			});
		} catch (Exception ex) {
			//ErrorDisplay.displayException(e);
			ex.printStackTrace();
			MessageDialog.openError(null, "Error - An Exception was caught", ex.getMessage());

		}
	}

	/**
	 * Update Xtext contents after modification command.
	 */
	public void updateEMFToXtext() {
		if (updateInProgress) {
			return;
		} else {
			beginUpdate();
		}

		final XtextDocument doc = (XtextDocument) xeditor.getDocument();

		// synchronized modify
		doc.modify(new IUnitOfWork.Void<XtextResource>() {
			@Override
			public void process(XtextResource resource) throws Exception {
				try {
					MenuRoot menuRoot = (MenuRoot) editor
							.getEditingDomain()
							.getResourceSet()
							.getResources()
							.get(0)
							.getContents()
							.get(0);

					// set new serialized text
					doc.set(editor.getXtextSerializer().serialize(menuRoot));
				} catch (Exception ex) {
					ex.printStackTrace();
					MessageDialog.openError(null, "Error - An Exception was caught", ex.getMessage());

					//if (doc != null)
					//	doc.set(e.getMessage());
				}
			}
		});

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				((TextOperationAction) xeditor.getAction("Format")).run();
				endUpdate();
			}
		});
	}

	/**
	 * @return the xtextListener
	 */
	public void beginUpdate() {
		updateInProgress = true;
	}

	/**
	 * @return the xtextListener
	 */
	public void endUpdate() {
		updateInProgress = false;
	}

	public void dispose() {
		editor.getEditingDomain().getCommandStack().removeCommandStackListener(emfListener);
	}

}
