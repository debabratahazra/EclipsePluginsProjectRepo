package com.odcgroup.workbench.compare.team.action;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * Action provider for SVN compare and replace menu specific actions.
 *
 * @author pkk
 *
 */
public class SvnCompareActionProvider {	

	private AbstractTeamAction remoteCompareAction = null;
	private AbstractTeamAction tagCompareAction = null;
	private AbstractTeamAction branchCompareAction = null;
	private AbstractTeamAction workingCopyAction = null;
	private AbstractTeamAction revisionAction = null;
	private AbstractTeamAction replaceBranchAction = null;
	private AbstractTeamAction replaceTagAction = null;
	private AbstractTeamAction replaceRevisionAction = null;
	private AbstractTeamAction replaceLatestAction = null;
	
	private boolean svnProviderAvailable = true;
	
	private SvnCompareActionProvider() {
	}
	
	/**
	 * @return
	 */
	public static SvnCompareActionProvider getInstance() {
		SvnCompareActionProvider provider = new SvnCompareActionProvider();
		provider.init();
		return provider;
	}
	
	/**
	 * 
	 */
	public void init() {	
		try {
			remoteCompareAction = new CompareLatestRevisionAction();
			tagCompareAction = new CompareTagAction();
			branchCompareAction = new CompareBranchAction();
			workingCopyAction = new CompareWorkingCopyAction();
			revisionAction = new CompareRevisionAction();
	
			replaceBranchAction = new ReplaceWithBranchRevisionAction();
			replaceTagAction = new ReplaceWithTagRevisionAction();
			replaceRevisionAction = new ReplaceByRevisionAction();
			replaceLatestAction = new ReplaceByLatestRevisionAction();	
		} catch(Throwable e) {
			svnProviderAvailable = false;
			// no svn team plug-ins available
		}
	}
	
	/**
	 * @param compareWith
	 */
	public void fillCompareContextMenu(IMenuManager compareWith) {
		if (!svnProviderAvailable) {
			return;
		}
		if (workingCopyAction.isEnabled())
    		compareWith.add(workingCopyAction);
    	if (remoteCompareAction.isEnabled())
    		compareWith.add(remoteCompareAction);
    	if (revisionAction.isEnabled())
    		compareWith.add(revisionAction);
    	if (branchCompareAction.isEnabled())
    		compareWith.add(branchCompareAction);
    	if (tagCompareAction.isEnabled())
    		compareWith.add(tagCompareAction);
	}
	
	/**
	 * @param replaceWith
	 */
	public void fillReplaceContextMenu(IMenuManager replaceWith) {
		if (!svnProviderAvailable) {
			return;
		}
		if (replaceLatestAction.isEnabled())
    		replaceWith.add(replaceLatestAction);
    	if (replaceRevisionAction.isEnabled())
    		replaceWith.add(replaceRevisionAction);
    	if (replaceBranchAction.isEnabled())
    		replaceWith.add(replaceBranchAction);
    	if (replaceTagAction.isEnabled())
    		replaceWith.add(replaceTagAction);
	}
	
	/**
	 * @param selection
	 */
	public void selectionChanged(IStructuredSelection selection) {
		if (!svnProviderAvailable) {
			return;
		}
        remoteCompareAction.selectionChanged(selection);
        tagCompareAction.selectionChanged(selection);
        branchCompareAction.selectionChanged(selection);
        workingCopyAction.selectionChanged(selection);
        revisionAction.selectionChanged(selection);
        replaceBranchAction.selectionChanged(selection);
        replaceTagAction.selectionChanged(selection);
        replaceRevisionAction.selectionChanged(selection);
        replaceLatestAction.selectionChanged(selection);
	}

	/**
	 * @param svnProviderAvailable the svnProviderAvailable to set
	 */
	public void setSvnProviderAvailable(boolean svnProviderAvailable) {
		this.svnProviderAvailable = svnProviderAvailable;
	}

}
