package com.odcgroup.page.ui.action;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;

/**
 * Represent the multi page editor contributor
 *
 * @author Alexandre Jaquet
 */
public class PageDesignerEditorActionBarContributor extends MultiPageEditorActionBarContributor {
    
	/** The registered global action keys. */
    private List globalActionKeys = new ArrayList();
    
    /** The retarget actions. */
    private List retargetActions = new  ArrayList();
    
    /** The ActionRegistry. */
    private ActionRegistry registry = new ActionRegistry();
    
    /**
     * Initialization.
     * 
     * @param bars The action bars
     */
    public void init(IActionBars bars) {
          buildActions();
          declareGlobalActionKeys();
          super.init(bars);
     }
    /**
     * Builds the actions.
     * 
     */
    protected void buildActions() {
    	IWorkbenchWindow workbenchWindow = getPage().getWorkbenchWindow();

        addRetargetAction(new UndoRetargetAction());
        addRetargetAction(new RedoRetargetAction());
        addRetargetAction(new DeleteRetargetAction());
        addRetargetAction((RetargetAction)ActionFactory.CUT.create(workbenchWindow));
        addRetargetAction((RetargetAction)ActionFactory.COPY.create(workbenchWindow));
        addRetargetAction((RetargetAction)ActionFactory.PASTE.create(workbenchWindow));
        //addRetargetAction(new AddWidgetToPaletteRetargetAction());
    }
    /**
     * Adds the retarded actions.
     *
     * @param action
     *             The action to add
     */
    protected void addRetargetAction(RetargetAction action) {       
        addAction(action);
        retargetActions.add(action);
        getPage().addPartListener(action);
        addGlobalActionKey(action.getId());
    }
    /**
     * Adds global action key.
     *
     * @param key
     *             The key to add
     */
    protected void addGlobalActionKey(String key) {       
        globalActionKeys.add(key);
    }
    /**
     * Adds to action registry an action.
     *
     * @param action
     *             The action to add
     */
    protected void addAction(IAction action) {
        getActionRegistry().registerAction(action);
    }
    /**
     * Gets the registry.
     *
     * @return ActionRegistry
     *                 The registry
     */
    protected ActionRegistry getActionRegistry() {       
        return registry;   
    }
    /**
     * Declares the global action keys.
     */
     protected void declareGlobalActionKeys() {
          addGlobalActionKey(ActionFactory.UNDO.getId());
          addGlobalActionKey(ActionFactory.REDO.getId());
          addGlobalActionKey(ActionFactory.COPY.getId());
          addGlobalActionKey(ActionFactory.PASTE.getId());       
          addGlobalActionKey(ActionFactory.SELECT_ALL.getId());
          addGlobalActionKey(ActionFactory.DELETE.getId());
          
          addGlobalActionKey(ActionFactory.PRINT.getId());
    }
    
     /**
      * Gets the action.
      * 
      * @param id The id of the Action
      * @return Action The Action
      */
    protected IAction getAction(String id) {
         return getActionRegistry().getAction(id);
    }
    
    /**
     * Adds the undo and redo items to the toolbar.
     *
     * @param tbm The IToolBarManager
     * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToToolBar(IToolBarManager)
     */
    public void contributeToToolBar(IToolBarManager tbm) {
        tbm.add(getAction(ActionFactory.UNDO.getId()));
        tbm.add(getAction(ActionFactory.REDO.getId()));       
        //tbm.add(getAction(AddWidgetToPaletteAction.ID));
        tbm.add(getAction(ActionFactory.DELETE.getId()));
       
    }
    /**
     * Sets the page to active status.
     *
     * @param activeEditor
     *                 The active editor
     */
    public void setActivePage(IEditorPart activeEditor) {
        ActionRegistry registry = (ActionRegistry) activeEditor.getAdapter(ActionRegistry.class);
        if (registry == null) {
        	return;
        }
        IActionBars bars = getActionBars();
        for (int i = 0; i < globalActionKeys.size(); i++) {
            String id = (String) globalActionKeys.get(i);
            bars.setGlobalActionHandler(id, registry.getAction(id));
        }
        bars.updateActionBars();
    }     
} 