package com.odcgroup.integrationfwk.ui.common;

import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;

import com.odcgroup.integrationfwk.ui.editors.EventsEditor;
import com.odcgroup.integrationfwk.ui.editors.FlowsEditor;

/**
 * Type which holding the common/global action id's. The respective Actions
 * should enabled whenever in the workbench whenever the editor (
 * {@link EventsEditor}/{@link FlowsEditor}) is active.
 * 
 * @author sbharathraja
 * 
 */
public enum WorkBenchActionID {
	BOOKMARK(IDEActionFactory.BOOKMARK.getId()), CLOSE(ActionFactory.CLOSE
			.getId()), CLOSE_ALL(ActionFactory.CLOSE_ALL.getId()), CLOSE_OTHERS(
			ActionFactory.CLOSE_OTHERS.getId()), COPY(ActionFactory.COPY
			.getId()), CUT(ActionFactory.CUT.getId()), DELETE(
			ActionFactory.DELETE.getId()), DELETE_NEXT(
			ITextEditorActionDefinitionIds.DELETE_NEXT), FIND(
			ActionFactory.FIND.getId()), PASTE(ActionFactory.PASTE.getId()), PRINT(
			ActionFactory.PRINT.getId()), REDO(ActionFactory.REDO.getId()), REFRESH(
			ActionFactory.REFRESH.getId()), RENAME(ActionFactory.RENAME.getId()), SAVE(
			ActionFactory.SAVE.getId()), SAVE_ALL(ActionFactory.SAVE_ALL
			.getId()), SAVE_AS(ActionFactory.SAVE_AS.getId()), SELECT_ALL(
			ActionFactory.SELECT_ALL.getId()), UNDO(ActionFactory.UNDO.getId());

	private final String id;

	private WorkBenchActionID(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
