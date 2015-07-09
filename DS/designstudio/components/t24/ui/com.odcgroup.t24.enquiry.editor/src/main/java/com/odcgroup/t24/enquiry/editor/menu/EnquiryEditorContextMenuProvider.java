package com.odcgroup.t24.enquiry.editor.menu;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionFactory;

import com.odcgroup.t24.enquiry.editor.part.actions.AddCustomSelectionAction;
import com.odcgroup.t24.enquiry.editor.part.actions.AddDrillDownAction;
import com.odcgroup.t24.enquiry.editor.part.actions.AddFieldAction;
import com.odcgroup.t24.enquiry.editor.part.actions.AddHeaderAction;
import com.odcgroup.t24.enquiry.editor.part.actions.AddItemAction;
import com.odcgroup.t24.enquiry.editor.part.actions.AddJBCRoutineAction;
import com.odcgroup.t24.enquiry.editor.part.actions.AddJavaRoutineAction;
import com.odcgroup.t24.enquiry.editor.part.actions.AddTargetAction;
import com.odcgroup.t24.enquiry.editor.part.actions.AddToolAction;
import com.odcgroup.t24.enquiry.editor.part.actions.AddWebServiceAction;
import com.odcgroup.t24.enquiry.editor.part.actions.ColumnFieldMoveLeftAction;
import com.odcgroup.t24.enquiry.editor.part.actions.ColumnFieldMoveRightAction;
import com.odcgroup.t24.enquiry.editor.part.actions.CopyCustomSelectionAction;
import com.odcgroup.t24.enquiry.editor.part.actions.CopyFieldAction;
import com.odcgroup.t24.enquiry.editor.part.actions.CopyFixedSelectionAction;
import com.odcgroup.t24.enquiry.editor.part.actions.FieldMoveDownAction;
import com.odcgroup.t24.enquiry.editor.part.actions.FieldMoveUpAction;
import com.odcgroup.t24.enquiry.editor.part.actions.PasteCustomSelectionAction;
import com.odcgroup.t24.enquiry.editor.part.actions.PasteFieldAction;
import com.odcgroup.t24.enquiry.editor.part.actions.PasteFixedSelectionAction;
import com.odcgroup.t24.enquiry.editor.part.actions.RoutineMoveDownAction;
import com.odcgroup.t24.enquiry.editor.part.actions.RoutineMoveUpAction;
import com.odcgroup.t24.enquiry.editor.part.actions.SelectionMoveDownAction;
import com.odcgroup.t24.enquiry.editor.part.actions.SelectionMoveUpAction;
import com.odcgroup.t24.enquiry.editor.part.actions.TargetMappingMoveDownAction;
import com.odcgroup.t24.enquiry.editor.part.actions.TargetMappingMoveUpAction;
import com.odcgroup.t24.enquiry.editor.part.actions.ToolMoveDownAction;
import com.odcgroup.t24.enquiry.editor.part.actions.ToolMoveUpAction;

/**
 *
 * @author mumesh
 *
 */
public class EnquiryEditorContextMenuProvider extends ContextMenuProvider {

	/** the workbench part */
    
    private Set<String> exclusionSet = new HashSet<String>();

	private ActionRegistry actionRegistry;
	/**
	 * @param viewer
	 */
	public EnquiryEditorContextMenuProvider(EditPartViewer viewer,ActionRegistry registry) {
		super(viewer);
		setActionRegistry(registry);
		addDefaultExclusions();
	}

	   /** the following items will be deleted from the context menus by default */
    private String[] defaultExclusionList = {
        "replaceWithMenu", 
        "compareWithMenu", 
        "ValidationAction", 
        "team.main", 
        "org.eclipse.jst.ws.atk.ui.webservice.category.popupMenu", 
        "org.eclipse.tptp.platform.analysis.core.ui.internal.actions.MultiAnalysisActionDelegate", 
        "org.eclipse.debug.ui.contextualLaunch.run.submenu", 
        "org.eclipse.debug.ui.contextualLaunch.debug.submenu", 
        "org.eclipse.debug.ui.contextualLaunch.profile.submenu",
        "org.apache.ivyde.eclipse.ivymenu",
        "com.temenos.t24.popupSubMenu",
        "org.jboss.ide.eclipse.as.ui.popups.makeDeployable.action",
        "Checkstyle.menu",
        "com.odcgroup.ocs"
    };

	@Override
	public void buildContextMenu(IMenuManager manager) {
		IAction action;
		GEFActionConstants.addStandardActionGroups(manager);
		
		action = getActionRegistry().getAction(ActionFactory.DELETE.getId());
		manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

		action = getActionRegistry().getAction(ActionFactory.UNDO.getId());
		manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

		action = getActionRegistry().getAction(ActionFactory.REDO.getId());
		manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

		updateMenu(manager,AddItemAction.ACTION_ID);
			
		updateMenu(manager,AddCustomSelectionAction.ACTION_ID);

 		updateMenu(manager,AddFieldAction.ACTION_ID);
 
 		updateMenu(manager,SelectionMoveDownAction.ACTION_ID);
 
 		updateMenu(manager,SelectionMoveUpAction.ACTION_ID);
 		
 		updateMenu(manager,ColumnFieldMoveLeftAction.ACTION_ID);

 		updateMenu(manager,ColumnFieldMoveRightAction.ACTION_ID);
 
 		updateMenu(manager,AddToolAction.ACTION_ID);

 		updateMenu(manager,AddJavaRoutineAction.ACTION_ID);

 		updateMenu(manager,AddJBCRoutineAction.ACTION_ID);
 	
 		updateMenu(manager,AddTargetAction.ACTION_ID);
	
 		updateMenu(manager,AddWebServiceAction.ACTION_ID);
		
 		updateMenu(manager,ToolMoveUpAction.ACTION_ID);

 		updateMenu(manager,ToolMoveDownAction.ACTION_ID);

 		updateMenu(manager,RoutineMoveUpAction.ACTION_ID);
 
 		updateMenu(manager,RoutineMoveDownAction.ACTION_ID);
 
 		updateMenu(manager,TargetMappingMoveUpAction.ACTION_ID);

 		updateMenu(manager,TargetMappingMoveDownAction.ACTION_ID);
 
 		updateMenu(manager,AddHeaderAction.ACTION_ID);
 
 		updateMenu(manager,AddDrillDownAction.ACTION_ID);
		
 		updateMenu(manager,CopyFixedSelectionAction.ACTION_ID);

  		updateMenu(manager,PasteFixedSelectionAction.ACTION_ID);

  		updateMenu(manager,CopyCustomSelectionAction.ACTION_ID);

  		updateMenu(manager,PasteCustomSelectionAction.ACTION_ID);

  		updateMenu(manager,CopyFieldAction.ACTION_ID);

  		updateMenu(manager,PasteFieldAction.ACTION_ID);
  		
  		updateMenu(manager, FieldMoveUpAction.ACTION_ID);
  		
  		updateMenu(manager, FieldMoveDownAction.ACTION_ID);

}

	/**
	 * @param manager
	 */
	private void updateMenu(IMenuManager manager, String actionId) {
		IAction action = getActionRegistry().getAction(actionId);
 		if(action.isEnabled())
		manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
	}

	private ActionRegistry getActionRegistry() {
		return actionRegistry;
	}

	private void setActionRegistry(ActionRegistry registry) {
		actionRegistry = registry;
	}

	
    /**
     * The exclusion <code>Set</code> allows clients to specify which contributed
     * menu items they do not want to include in their context menus.
     * @return <code>Set</code> of IDs
     */
    public Set<String> getExclusionSet() {
        return exclusionSet;
    }
    
    /**
     * set the exclusion <code>Set</code>. 
     * @see org.eclipse.gmf.runtime.diagram.ui.providers.DiagramContextMenuProvider#getExclusionSet
     * @param exclusionSet the <code>Set</code> of IDs of menu items that need to be 
     * excluded from the context menu
     */
    public void setExclusionSet(Set<String> exclusionSet) {
        this.exclusionSet = exclusionSet;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.action.ContributionManager#allowItem(org.eclipse.jface.action.IContributionItem)
     */
    protected boolean allowItem(IContributionItem itemToAdd) {
        if (itemToAdd.getId() != null && exclusionSet.contains(itemToAdd.getId())){
            //we don't want to return false, as other menu items may depend on it...
            itemToAdd.setVisible(false);
        }
        return super.allowItem(itemToAdd);
    }
	
    /**
     * Transfer the String array <code>defaultExclusionList</code>
     * into the <code>exclusionSet</code>
     *
     */
    protected void addDefaultExclusions() {
        for (int i=0; i < defaultExclusionList.length; i++)
            exclusionSet.add(defaultExclusionList[i]);
    }
	
}
