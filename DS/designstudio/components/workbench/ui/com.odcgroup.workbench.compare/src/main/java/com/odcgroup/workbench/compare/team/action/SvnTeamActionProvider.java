package com.odcgroup.workbench.compare.team.action;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;

import com.odcgroup.workbench.compare.ui.navigator.TeamApplyPatchAction;

/**
 * Action provider for SVN team menu specific actions
 * 
 * @author pkk
 */
public class SvnTeamActionProvider {

	private AbstractTeamAction synchronizeAction = null;
	private AbstractTeamAction commitAction = null;
	private AbstractTeamAction updateAction = null;
	private AbstractTeamAction patchAction = null;
	
	private AbstractTeamAction revertAction = null;
	private AbstractTeamAction addToAction = null;
	private AbstractTeamAction addToIgnoreAction = null;
	private AbstractTeamAction editConflictsAction = null;
	private AbstractTeamAction editTreeConflictsAction = null;
	private AbstractTeamAction markAsMergedAction = null;
	private AbstractTeamAction branchAction = null;
	private AbstractTeamAction tagAction = null;
	private AbstractTeamAction mergeAction = null;
	private AbstractTeamAction switchAction = null;
	private AbstractTeamAction showHistoryAction = null;
	private AbstractTeamAction lockAction = null;
	private AbstractTeamAction unLockAction = null;
	private AbstractTeamAction showPropertiesaction = null;
	private AbstractTeamAction setPropertyAction = null;
	private AbstractTeamAction setKeywordsAction = null;
	private AbstractTeamAction copyToAction = null;
	private AbstractTeamAction exportAction = null;
	private AbstractTeamAction cleanUpAction = null;
	private AbstractTeamAction disconnectAction = null;
	private AbstractTeamAction shareProjectsAction = null;
	private AbstractTeamAction showLocalHistoryAction = null;
	private TeamApplyPatchAction applyPatchAction = null;
	
	private boolean svnProviderAvailable = false;
	private boolean isNotProject = false;
	
	/**
	 * 
	 */
	private SvnTeamActionProvider() {
	}
	
	/**
	 * @return
	 */
	public static SvnTeamActionProvider getInstance() {
		SvnTeamActionProvider provider = new SvnTeamActionProvider();
		provider.init();
		return provider;
	}
	

	public void init() {	
		try {
			synchronizeAction = new SvnSynchronizeAction();	
			commitAction = new SvnCommitAction();
			updateAction = new SvnUpdateAction();
			patchAction = new SvnCreatePatchAction();
			revertAction = new SvnRevertAction();
			addToAction = new SvnAddToAction();
			addToIgnoreAction = new SvnAddToIgnoreAction();
			editConflictsAction = new SvnEditConflictsAction();
			editTreeConflictsAction = new SvnEditTreeConflictsAction();
			markAsMergedAction = new SvnMarkAsMergedAction();
			branchAction = new SvnBranchAction();
			tagAction = new SvnTagAction();
			mergeAction = new SvnMergeAction();
			switchAction = new SvnSwitchAction();
			showHistoryAction = new SvnShowHistoryAction();
			lockAction = new SvnLockAction();
			unLockAction = new SvnUnLockAction();
			showPropertiesaction = new SvnEditPropertiesAction();
			setPropertyAction = new SvnSetPropertyAction();
			setKeywordsAction = new SvnSetKeywordsAction();
			copyToAction = new SvnCopyToAction();
			exportAction = new SvnExportAction();
			cleanUpAction = new SvnCleanUpAction();
			disconnectAction = new SvnDisconnectAction();
			shareProjectsAction = new SvnShareProjectsAction();
			showLocalHistoryAction = new SvnShowLocalHistoryAction();
			applyPatchAction = new TeamApplyPatchAction();
		} catch(Throwable e) {
			// no svn team plug-ins available
			svnProviderAvailable = false;
		}
	}
	
	/**
	 * @param teamMenu
	 */
	public void fillTeamMergeContextMenu(IMenuManager teamMenu) {
		if (!svnProviderAvailable) {
			return;
		}
		teamMenu.add(synchronizeAction);
		teamMenu.add(commitAction);
		teamMenu.add(updateAction);
		teamMenu.add(patchAction);
		teamMenu.add(applyPatchAction);
	}
	
	/**
	 * @param teamMenu
	 */
	public void fillTeamNavigateContextMenu(IMenuManager teamMenu) {
		if (!svnProviderAvailable) {
			return;
		}
		teamMenu.add(revertAction);
		teamMenu.add(addToAction);
		teamMenu.add(addToIgnoreAction);
		teamMenu.add(editConflictsAction);
		teamMenu.add(editTreeConflictsAction);
		teamMenu.add(markAsMergedAction);
		
	}
	
	public void fillTeamRepositoryContextMenu(IMenuManager teamMenu) {
		if (!svnProviderAvailable) {
			return;
		}
		teamMenu.add(branchAction);
		teamMenu.add(tagAction);
		teamMenu.add(mergeAction);
		teamMenu.add(switchAction);	
		
	}
	
	public void fillTeamHistoryContextMenu(IMenuManager teamMenu) {
		if (!svnProviderAvailable) {
			return;
		}
		teamMenu.add(showHistoryAction);	
	}
	
	public void fillTeamControlContextMenu(IMenuManager teamMenu) {
		if (!svnProviderAvailable) {
			return;
		}
		teamMenu.add(lockAction);	
		teamMenu.add(unLockAction);			
	}
	
	public void fillTeamPropertiesContextMenu(IMenuManager teamMenu) {
		if (!svnProviderAvailable) {
			return;
		}
		teamMenu.add(showPropertiesaction);	
		teamMenu.add(setPropertyAction);	
		teamMenu.add(setKeywordsAction);
	}
	
	public void fillTeamEditContextMenu(IMenuManager teamMenu) {
		if (!svnProviderAvailable) {
			return;
		}
		teamMenu.add(copyToAction);	
		teamMenu.add(exportAction);	
	}
	
	public void fillTeamOtherContextMenu(IMenuManager teamMenu) {
		if (!svnProviderAvailable) {
			return;
		}
		teamMenu.add(cleanUpAction);	
		teamMenu.add(disconnectAction);	
	}

	public void disableAll() {
		isNotProject = false;
		svnProviderAvailable = false;
	}

	public void enableAll() {
		isNotProject = false;
		svnProviderAvailable = true;
	}

	public void fillShareMenu(MenuManager teamMenu) {
		if(!svnProviderAvailable) {
			teamMenu.add(showLocalHistoryAction);
			if(!isNotProject) {
				teamMenu.add(shareProjectsAction);	
			}
		}
	}

	public void setIsNotProjectFlag(boolean status) {
		isNotProject  = status;
	}
	
	public void setIsSVNProviderAvailable(boolean status) {
		svnProviderAvailable  = status;
	}
	
}
