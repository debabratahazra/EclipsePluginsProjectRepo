package com.odcgroup.integrationfwk.ui.editors;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.part.EditorActionBarContributor;
import org.eclipse.ui.texteditor.ITextEditor;

import com.odcgroup.integrationfwk.ui.RenameHandler;
import com.odcgroup.integrationfwk.ui.common.WorkBenchActionID;

/**
 * Manages the installation/de-installation of global actions for Flows
 * multi-page editors. Responsible for the redirection of global actions to the
 * active editor. Multi-page contributor replaces the contributors for the
 * individual editors in the multi-page editor.
 * 
 * @author sbharathraja
 * 
 */
public class FlowsEditorContributor extends EditorActionBarContributor {
	/** holding the active editor */
	private IEditorPart activeEditorPart;

	public FlowsEditorContributor() {
		super();
	}

	/**
	 * Contributes to the given tool bar.
	 */
	@Override
	public void contributeToToolBar(IToolBarManager manager) {
	}

	/**
	 * Disposes this contributor.
	 */
	@Override
	public void dispose() {
		super.dispose();
	}

	/**
	 * Method which helps to add the global action handlers.
	 * 
	 * @param editor
	 * @param actionBars
	 */
	private void hookGlobalTextActions(FlowsEditor editor,
			IActionBars actionBars) {
		ITextEditor textEditor = editor.getSourceEditor();
		for (WorkBenchActionID wbId : WorkBenchActionID.values()) {
			String actionId = wbId.getId();
			IAction handler = null;
			if (actionId.equals(WorkBenchActionID.RENAME)) {
				handler = (IAction) new RenameHandler();
			} else {
				handler = textEditor.getAction(actionId);
			}
			actionBars.setGlobalActionHandler(actionId, handler);
		}
	}

	@Override
	public void init(IActionBars bars) {
		super.init(bars);
	}

	/**
	 * Initialises this contributor, which is expected to add contributions as
	 * required to the given action bars and global action handlers.
	 */
	@Override
	public void init(IActionBars bars, IWorkbenchPage page) {
		super.init(bars, page);
	}

	/**
	 * Sets the active editor for the contributor. Generally this involves
	 * disconnecting from the old editor, connect to the new editor, and update
	 * the actions to reflect the new editor. In our case, we are setting global
	 * action hooks which are automatically disconnected the next time they are
	 * set.
	 */
	@Override
	public void setActiveEditor(IEditorPart part) {
		if (activeEditorPart == part) {
			return;
		}
		activeEditorPart = part;
		setActivePage(part);
	}

	/**
	 * Method which helps to add/refresh the action bar.
	 * 
	 * @param part
	 *            - active editor part
	 */
	public void setActivePage(IEditorPart part) {
		FlowsEditor editor = (FlowsEditor) part;
		if (editor == null) {
			return;
		}
		int pageIndex = editor.getActivePage();
		IActionBars actionBars = getActionBars();
		if (actionBars != null) {
			switch (pageIndex) {
			case 0:
				hookGlobalTextActions(editor, actionBars);
				break;
			default:
				break;
			}
			actionBars.updateActionBars();
		}
	}

}
