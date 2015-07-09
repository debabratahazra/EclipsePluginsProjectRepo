package com.temenos.t24.tools.eclipse.basic.views.remote;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.IT24FTPClient;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;

public class RemoteSiteViewManager {

    private static RemoteSiteViewManager VIEW_MANAGER = new RemoteSiteViewManager();
    private Map<String, RemoteSiteView> views = new HashMap<String, RemoteSiteView>();
    private Map<String, RemoteSiteViewController> viewControllers = new HashMap<String, RemoteSiteViewController>();

    private RemoteSiteViewManager() {
    }

    public static RemoteSiteViewManager getInstance() {
        return VIEW_MANAGER;
    }

    public boolean launchView(RemoteSite site) {
        if (site == null) {
            return false;
        }
        String siteId = site.getSiteName();
        IT24FTPClient ftpClient = RemoteSitesManager.getInstance().getFTPClient(siteId);
        if (!RemoteSitesManager.getInstance().connect(siteId)) {
            return false;
        }
        RemoteSiteViewController viewController = new RemoteSiteViewController(ftpClient);
        RemoteSiteView view = buildView(site);
        if (view == null) {
            return false;
        }
        view.setViewController(viewController);
        viewControllers.put(siteId, viewController);
        showView(site);
        view.loadView();
        return true;
    }

    public void closeView(RemoteSite site) {
        if (site == null) {
            return;
        }
        String siteId = site.getSiteName();
        RemoteSitesManager.getInstance().disconnect(siteId);
        RemoteSiteView view = views.remove(siteId);
        getActivePage().hideView(view);
    }

    public void disposeAllViews() {
        Collection<RemoteSiteView> viewList = views.values();
        Object[] allViews = viewList.toArray();
        RemoteSiteView view = null;
        RemoteSiteViewController viewController = null;
        for (int viewCount = 0; viewCount < allViews.length; viewCount++) {
            if (allViews[viewCount] instanceof RemoteSiteView) {
                view = ((RemoteSiteView) allViews[viewCount]);
                viewController = view.getViewController();
                viewController.closeView();
                getActivePage().hideView(view);
            }
        }
    }

    public RemoteSiteViewController getViewController(RemoteSite site) {
        if (site == null) {
            return null;
        }
        String siteId = site.getSiteName();
        if (viewControllers.containsKey(siteId)) {
            return viewControllers.get(siteId);
        }
        return null;
    }

    private RemoteSiteView buildView(RemoteSite site) {
        if (views.containsKey(site.getSiteName())) {
            return views.get(site.getSiteName());
        }
        RemoteSiteViewID viewId = new RemoteSiteViewID(site.getSiteName());
        IWorkbenchPage activePage = getActivePage();
        if (activePage == null) {
            return null;
        }
        IViewPart currentViewPart = null;
        try {
            currentViewPart = activePage.showView(viewId.getPrimaryId(), viewId.getSecondaryId(), IWorkbenchPage.VIEW_CREATE);
            if (currentViewPart instanceof RemoteSiteView) {
                ((RemoteSiteView) currentViewPart).setViewId(viewId);
                views.put(site.getSiteName(), (RemoteSiteView) currentViewPart);
                return (RemoteSiteView) currentViewPart;
            }
        } catch (PartInitException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private void showView(RemoteSite site) {
        IWorkbenchPage activePage = getActivePage();
        if (activePage == null) {
            return;
        }
        RemoteSiteView view = views.get(site.getSiteName());
        try {
            activePage.showView(view.getViewId().getPrimaryId(), view.getViewId().getSecondaryId(), IWorkbenchPage.VIEW_ACTIVATE);
        } catch (PartInitException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private IWorkbenchPage getActivePage() {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        return window.getActivePage();
    }
}
