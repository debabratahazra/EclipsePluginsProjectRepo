package com.odcgroup.workbench.compare.ui.navigator;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.compare.team.action.SvnTeamActionProvider;
import com.odcgroup.workbench.compare.util.RepositoryConstants;
import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * Action provider for SVN & EGit specific actions
 *
 * @author pkk
 *
 */
public class TeamActionProvider extends CommonActionProvider {
	private final Logger logger = LoggerFactory.getLogger(TeamActionProvider.class);
	
	private SvnTeamActionProvider svnTeamProvider;
	
	private MenuManager teamMenu = null;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
	 */
	public void init(ICommonActionExtensionSite anActionSite) {
		super.init(anActionSite);		
		svnTeamProvider = SvnTeamActionProvider.getInstance();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
	 */
	public void fillContextMenu(IMenuManager menu) {
		updateActionBars();
		
		teamMenu = new MenuManager("&Team", "&Team");
		menu.appendToGroup(ICommonMenuConstants.GROUP_BUILD, teamMenu);
				
		svnTeamProvider.fillTeamMergeContextMenu(teamMenu);
		teamMenu.add(new Separator(ICommonMenuConstants.GROUP_ADDITIONS));
		svnTeamProvider.fillShareMenu(teamMenu);
		svnTeamProvider.fillTeamNavigateContextMenu(teamMenu);
		teamMenu.add(new Separator(ICommonMenuConstants.GROUP_ADDITIONS));
		svnTeamProvider.fillTeamRepositoryContextMenu(teamMenu);
		teamMenu.add(new Separator(ICommonMenuConstants.GROUP_ADDITIONS));
		svnTeamProvider.fillTeamHistoryContextMenu(teamMenu);
		teamMenu.add(new Separator(ICommonMenuConstants.GROUP_ADDITIONS));
		svnTeamProvider.fillTeamControlContextMenu(teamMenu);
		teamMenu.add(new Separator(ICommonMenuConstants.GROUP_ADDITIONS));
		svnTeamProvider.fillTeamPropertiesContextMenu(teamMenu);
		teamMenu.add(new Separator(ICommonMenuConstants.GROUP_ADDITIONS));
		svnTeamProvider.fillTeamEditContextMenu(teamMenu);
		teamMenu.add(new Separator(ICommonMenuConstants.GROUP_ADDITIONS));
		svnTeamProvider.fillTeamOtherContextMenu(teamMenu);    
        
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
	 */
	public void fillActionBars(IActionBars actionBars) {
        updateActionBars();
	}

    /* (non-Javadoc)
     * @see org.eclipse.ui.actions.ActionGroup#updateActionBars()
     */
    public void updateActionBars() {
        IStructuredSelection selection = (IStructuredSelection) getContext()
                .getSelection();
    	
        if(selection.getFirstElement() instanceof IResource) {
        	IResource res = (IResource)selection.getFirstElement();
        	updateMenu(res);
        } else if(selection.getFirstElement() instanceof IOfsElement) {
        	IOfsProject project = ((IOfsElement)selection.getFirstElement()).getOfsProject();
        	IResource res = project.getProject();
        	updateMenu(res);
        } else if(selection.getFirstElement() instanceof IAdaptable) {
        	IOfsElement resource = (IOfsElement) ((IAdaptable) selection.getFirstElement()).getAdapter(IOfsElement.class);
        	if(resource != null) {
	        	IOfsProject project = resource.getOfsProject();
	        	IResource res = project.getProject();
	        	updateMenu(res);
        	}
    	} 
        else {
        	svnTeamProvider.setIsSVNProviderAvailable(false);
        	svnTeamProvider.setIsNotProjectFlag(true);
        }
    }

	private void updateMenu(IResource res) {
		try {
			if (res.getProject() != null) {
				res = res.getProject();
			}

			String repository = (String) (res.getPersistentProperty(new QualifiedName(RepositoryConstants.TEAM_CORE,
					RepositoryConstants.REPOSITORY)));
			if (repository != null) {
				if (RepositoryConstants.EGIT_NATURE.equals(repository)) {
					svnTeamProvider.disableAll();
				} else if (RepositoryConstants.SVN_NATURE.equals(repository)) {
					svnTeamProvider.enableAll();
				}
			} else {
				svnTeamProvider.disableAll();
			}
		} catch (Exception e) {
			logger.error("The persistent property does not exist.",e.getMessage());
		}
	}

}
