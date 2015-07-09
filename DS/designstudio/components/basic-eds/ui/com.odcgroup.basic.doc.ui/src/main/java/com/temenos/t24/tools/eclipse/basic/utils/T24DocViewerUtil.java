package com.temenos.t24.tools.eclipse.basic.utils;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.document.xml.SubroutineDocumentLookup;
import com.temenos.t24.tools.eclipse.basic.document.xml.SubroutineInfo;
import com.temenos.t24.tools.eclipse.basic.views.document.IDocumentNode;
import com.temenos.t24.tools.eclipse.basic.views.document.T24ComponentDocNode;
import com.temenos.t24.tools.eclipse.basic.views.document.T24ComponentViewController;
import com.temenos.t24.tools.eclipse.basic.views.document.T24ProductDocNode;

/**
 * utility class which handles the loading component document view
 * 
 * @author sbharathraja
 */
public class T24DocViewerUtil {

    private static T24ComponentViewController viewController = T24ComponentViewController.getInstance();
    public static SubroutineInfo subRoutineInfo;

    /**
     * loading the component document view, whenever the subroutines/basic files
     * getting opened
     * 
     * @param basicFileName
     */
    public static void buildDocsView(String basicFileName) {
        Object obj = null;
        IDocumentNode selectedNode = null;
        subRoutineInfo = getSubRoutineInfo(basicFileName);
        obj = new T24ComponentDocNode(subRoutineInfo.getComponent(), new T24ProductDocNode(subRoutineInfo.getProduct()));
        if (obj instanceof IDocumentNode) {
            selectedNode = (IDocumentNode) obj;
            T24ComponentViewController.getInstance().setT24DocViewAsNeedToBeActivated(false);
            viewController.loadDocView(selectedNode);
        }
    }

    /**
     * get the current active page
     * 
     * @return IWorkbenchPage - current active page
     */
    public static IWorkbenchPage getActivePage() {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        return window.getActivePage();
    }

    /**
     * get the subroutines information from subroutine-lookup.xml file and
     * stored it in SubroutineInfo's instance
     * 
     * @param basicFileName - routine name
     * 
     * @return SubRoutineInfo contains product, component names
     */
    private static SubroutineInfo getSubRoutineInfo(String basicFileName) {
        String subRoutineName = StringUtil.removeBasicExtension(basicFileName);
        return SubroutineDocumentLookup.getInstance().getSubroutineInfo(subRoutineName);
    }

    /**
     * converting normal file to IFile, but the normal file should be in local
     * os path, not on any remote path
     * 
     * @param normalFile - java's io file
     * @return IFile equivalent to java's io file
     */
    public static IFile makeFileToIFile(File normalFile) {
        return ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(Path.fromOSString(normalFile.getAbsolutePath()));
    }

    /**
     * checking whether the current workbench page has contain the view with
     * given id active or not
     * 
     * @param viewID
     * @return true - if view is active, false- if view is inactive
     */
    public static boolean isViewThere(String viewID) {
        if (getViewReference(viewID) != null)
            return true;
        else
            return false;
    }

    /**
     * get the reference of view for given id
     * 
     * @param viewID
     * @return view reference
     */
    public static IViewReference getViewReference(String viewID) {
        IViewReference viewReference = null;
        IViewReference[] allViews = getActivePage().getViewReferences();
        for (IViewReference currentView : allViews) {
            if (currentView.getId().equalsIgnoreCase(viewID)) {
                viewReference = currentView;
                break;
            }
        }
        return viewReference;
    }

    /**
     * asynchronously activate a view respective to given viewInstance.
     * 
     * @param viewInstance - instance of the view which needs to activated
     *            asynchronously
     */
    public static boolean activateViewAsynchronously(final IViewPart viewInstance) {
        final IWorkbenchPage activePage = getActivePage();
        if (activePage == null)
            return false;
        IEditorPart activeEditor = activePage.getActiveEditor();
        if (activeEditor == null)
            return false;
        try {
            activeEditor.getSite().getShell().getDisplay().asyncExec(new Runnable() {

                public void run() {
                    activePage.activate(viewInstance);
                }
            });
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }
}
