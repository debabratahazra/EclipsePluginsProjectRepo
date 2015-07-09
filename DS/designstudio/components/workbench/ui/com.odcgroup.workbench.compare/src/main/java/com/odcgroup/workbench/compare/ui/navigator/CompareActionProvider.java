package com.odcgroup.workbench.compare.ui.navigator;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.compare.team.action.SvnCompareActionProvider;
import com.odcgroup.workbench.compare.util.RepositoryConstants;
import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsProject;


/**
 * Compare & Replace actions provider for SVN & EGit
 *
 * @author pkk
 *
 */
public class CompareActionProvider extends CommonActionProvider {
	private final Logger logger = LoggerFactory.getLogger(CompareActionProvider.class);
	private OdsCompareAction compareAction;
	private OdsCompareEditionAction compareEditionAction;
	private OdsReplaceWithEditionAction replaceEditionAction;
	
	private SvnCompareActionProvider svnCompareProvider;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
	 */
	public void init(ICommonActionExtensionSite anActionSite) {
		super.init(anActionSite);
		compareAction = new OdsCompareAction();
		compareEditionAction = new OdsCompareEditionAction();
		replaceEditionAction = new OdsReplaceWithEditionAction();
				
		svnCompareProvider = SvnCompareActionProvider.getInstance();	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
	 */
	public void fillContextMenu(IMenuManager menu) {
		updateActionBars();		
		
        MenuManager compareWith = new MenuManager("&Compare With","CompareWith");
    	menu.appendToGroup(ICommonMenuConstants.GROUP_BUILD, compareWith);  
    	
    	svnCompareProvider.fillCompareContextMenu(compareWith);
    	compareWith.add(compareEditionAction);
    	compareWith.add(compareAction);
    	
    	MenuManager replaceWith = new MenuManager("&Replace With", "ReplaceWith");
    	menu.appendToGroup(ICommonMenuConstants.GROUP_BUILD, replaceWith);
    	svnCompareProvider.fillReplaceContextMenu(replaceWith);
    	replaceWith.add(replaceEditionAction);
        
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
    	} else {
    		svnCompareProvider.setSvnProviderAvailable(false);
    	}
    	
        if(!(selection.size()==1 && selection.iterator().next().equals(ResourcesPlugin.getWorkspace().getRoot()))) {
	        compareAction.selectionChanged(selection);
	        compareEditionAction.selectionChanged(selection);
	        replaceEditionAction.selectionChanged(selection);
	        svnCompareProvider.selectionChanged(selection);
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
					svnCompareProvider.setSvnProviderAvailable(false);
				} else if (RepositoryConstants.SVN_NATURE.equals(repository)) {
					svnCompareProvider.setSvnProviderAvailable(true);
				}
			} else {
				svnCompareProvider.setSvnProviderAvailable(false);
			}
		} catch (Exception e) {
			logger.error("The persistent property does not exist.",e.getMessage());
		}
	}
}
