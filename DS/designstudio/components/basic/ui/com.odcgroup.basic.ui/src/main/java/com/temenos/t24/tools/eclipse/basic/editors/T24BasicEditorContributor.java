package com.temenos.t24.tools.eclipse.basic.editors;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.part.EditorActionBarContributor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;

import com.temenos.t24.tools.eclipse.basic.IDocViewProvider;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;

/**
 * A editor action bar contributor defines the actions for one or more editors.
 * Within the workbench there may be more than one editor open of a particular
 * type. For instance, there may be 1 or more open Basic Editors. To avoid the
 * creation of duplicate actions and action images the editor concept has been
 * split into two. An action contributor is responsible for the creation of
 * actions. The editor is responsible for action implementation. Furthermore,
 * the contributor is shared by each open editor. As a result of this design
 * there is only 1 set of actions for 1 or more open editors.
 */
public class T24BasicEditorContributor extends EditorActionBarContributor {

    /** Actions enabled whenever in the workbench whenever the editor is active. */
    private enum WORKBENCH_ACTION_ID {
        BOOKMARK(IDEActionFactory.BOOKMARK.getId()), CLOSE(ActionFactory.CLOSE.getId()), CLOSE_ALL(ActionFactory.CLOSE_ALL.getId()), CLOSE_OTHERS(
                ActionFactory.CLOSE_OTHERS.getId()), COPY(ActionFactory.COPY.getId()), CUT(ActionFactory.CUT.getId()), DELETE(
                ActionFactory.DELETE.getId()), DELETE_NEXT(ITextEditorActionDefinitionIds.DELETE_NEXT), FIND(ActionFactory.FIND
                .getId()), PASTE(ActionFactory.PASTE.getId()), PRINT(ActionFactory.PRINT.getId()), REDO(ActionFactory.REDO.getId()), REFRENSH(
                ActionFactory.REFRESH.getId()), SAVE(ActionFactory.SAVE.getId()), SAVE_ALL(ActionFactory.SAVE_ALL.getId()), SAVE_AS(
                ActionFactory.SAVE_AS.getId()), SELECT_ALL(ActionFactory.SELECT_ALL.getId()), UNDO(ActionFactory.UNDO.getId());

        private final String id;

        private WORKBENCH_ACTION_ID(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    /**
     * Initialises this contributor, which is expected to add contributions as
     * required to the given action bars and global action handlers.
     */
    public void init(IActionBars bars, IWorkbenchPage page) {
        super.init(bars, page);
    }

    /**
     * Contributes to the given tool bar.
     */
    public void contributeToToolBar(IToolBarManager manager) {
    }

    /**
     * Disposes this contributor.
     */
    public void dispose() {
        super.dispose();
    }

    /**
     * Sets the active editor for the contributor. Generally this involves
     * disconnecting from the old editor, connect to the new editor, and update
     * the actions to reflect the new editor. In our case, we are setting global
     * action hooks which are automatically disconnected the next time they are
     * set.
     */
    public void setActiveEditor(IEditorPart part) {
        T24BasicMultiPageEditor editor = (T24BasicMultiPageEditor) part;
        setActivePage(editor, editor.getActivePage());
        IDocViewProvider provider = T24BasicPlugin.getDefault().getProvider();
        if(provider != null) {
            provider.buildView(editor);
        }
    }

    public void setActivePage(T24BasicMultiPageEditor editor, int pageIndex) {
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

    private void hookGlobalTextActions(T24BasicMultiPageEditor editor, IActionBars actionBars) {
        // ITextEditor textEditor = editor.getSourceEditor();
        ITextEditor textEditor = editor.getSourceEditor();
        for (WORKBENCH_ACTION_ID wbId : WORKBENCH_ACTION_ID.values()) {
            String actionId = wbId.getId();
            IAction handler;
            if (ActionFactory.PRINT.getId().equals(actionId)) {
                handler = new PrintActionDecorator(textEditor.getAction(actionId));
            } else {
                handler = textEditor.getAction(actionId);
            }
            actionBars.setGlobalActionHandler(actionId, handler);
        }
    }
}
