package com.odcgroup.domain.ui.actions;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.xtext.ui.editor.outline.actions.IOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.impl.OutlinePage;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.odcgroup.domain.ui.DomainUtils;


public class CollapseAllAction extends Action implements IOutlineContribution {
	  
	  static public Action action;
	  
	  TreeViewer treeViewer;

	  protected Action getAction() {
	    if (action == null) {
	      action = this;
	      configureAction(action);
	    }
	    return action;
	  }

	  protected void configureAction(Action action) {
		action.setId("CollapseAll");
	    action.setText("Collapse All");
	    action.setToolTipText("Collapse All");
	    action.setDescription("Collapse All");
	    action.setImageDescriptor(DomainUtils.getImageDescriptor("icons/collapse-all.png"));
	    action.setDisabledImageDescriptor(DomainUtils.getImageDescriptor("icons/collapse-all.png"));
	  }

	  @Override
	  public void run() {
	    treeViewer.collapseAll();
	    action.setEnabled(false);
	    ExpandAllAction.action.setEnabled(true);
	  }
	  
	  public void initialize(IPreferenceStoreAccess access) {
	  }
	  
	  public void register(OutlinePage outlinePage) {
	    IToolBarManager toolBarManager = outlinePage.getSite().getActionBars().getToolBarManager();
	    toolBarManager.add(getAction());
	    treeViewer = outlinePage.getTreeViewer();
	  }

	  public void deregister(OutlinePage outlinePage) {
	    treeViewer = null;
	  }  
}
