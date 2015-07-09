package com.temenos.t24.tools.eclipse.basic.views.document;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

import com.temenos.t24.tools.eclipse.basic.document.util.DocumentFileUtil;
import com.temenos.t24.tools.eclipse.basic.utils.T24DocViewerUtil;

/**
 * act as a controller between component specific and general component
 * landscape view
 * 
 * @author sbharathraja
 */
public class ComponentFilterController {

    private TreeViewer treeViewer;
    private T24ComponentViewController viewController;
    private Action actionFilter;
    private String productName="";
    private String componentName="";
    private String subRoutineName="";

    public ComponentFilterController(TreeViewer treeViewer, T24ComponentViewController viewController, Action actionFilter) {
        this.treeViewer = treeViewer;
        this.viewController = viewController;
        this.actionFilter = actionFilter;
    }

    /**
     * show/open the component landscape view
     */
    public static void showLandscapeView() {
        final IWorkbenchPage activePage = T24DocViewerUtil.getActivePage();
        if (activePage != null) {
            if (T24DocViewerUtil.isViewThere(ComponentLandscapeView.VIEW_ID)) {
                activePage.findView(ComponentLandscapeView.VIEW_ID);
                if(ComponentLandscapeView.filterController != null)
                	ComponentLandscapeView.filterController.loadView();
            } else if (!DocumentFileUtil.getRoot().equalsIgnoreCase("")) {
                activePage.getActiveEditor().getSite().getShell().getDisplay().asyncExec(new Runnable() {

                    public void run() {
                        try {
                            activePage.showView(ComponentLandscapeView.VIEW_ID);
                            ComponentLandscapeView.filterController.loadView();
                        } catch (PartInitException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    /**
     * doing the refresh action of component landscape view
     */
    public void doRefresh() {
        if (viewController != null && treeViewer != null) {
            treeViewer.setInput(viewController.getTree(getValidXPath()));
            if (T24DocViewerUtil.subRoutineInfo != null && !DocumentFileUtil.getRoot().equalsIgnoreCase(""))
                doExpand();
        }
    }

    /**
     * load the content of component landscape view
     */
    public void loadView() {
        loadSubRoutineInfo();
        if (treeViewer != null) {
            treeViewer.setInput(viewController.getTree(getValidXPath()));
            if (T24DocViewerUtil.subRoutineInfo != null&&!DocumentFileUtil.getRoot().equalsIgnoreCase(""))
                doExpand();
        }
    }

    /**
     * get the valid xpath based on toggle button of component landscape view's
     * tool-bar.
     * 
     * @return - parent path, if the toggle button is off, if its on then
     *         returns product path
     */
    private String getValidXPath() {
        if (T24DocViewerUtil.subRoutineInfo == null)
            return DocumentViewConstants.PRODUCT_XPATH + "*";
        if (actionFilter.isChecked() && !T24DocViewerUtil.subRoutineInfo.getProduct().equalsIgnoreCase(""))
            return DocumentViewConstants.PRODUCT_XPATH + T24DocViewerUtil.subRoutineInfo.getProduct();
        else
            return DocumentViewConstants.PRODUCT_XPATH + "*";
    }

    /**
     * doing the component specific expand operation of tree viewer
     */
    private void doExpand() {
        if (!componentName.equalsIgnoreCase("")) {
            Object originalComponent = new T24ComponentDocNode(componentName, new T24ProductDocNode(productName));
            Object currentComponent = getParentComponent(originalComponent);
            treeViewer.expandToLevel(currentComponent, AbstractTreeViewer.ALL_LEVELS);
            doHighlight();
        }
    }

    /**
     * loading the subroutine info and save as local object
     */
    private void loadSubRoutineInfo() {
        if (T24DocViewerUtil.subRoutineInfo != null) {
            productName = T24DocViewerUtil.subRoutineInfo.getProduct();
            componentName = T24DocViewerUtil.subRoutineInfo.getComponent();
            subRoutineName = T24DocViewerUtil.subRoutineInfo.getSubroutineName();
        }
    }

    /**
     * doing the highlight operation of current accessed subroutine in sources
     * view
     */
    private void doHighlight() {
        Object currentRoutine = getCurrentAccesedRoutine();
        if (currentRoutine != null) {
            treeViewer.setSelection(new StructuredSelection(currentRoutine), true);
            treeViewer.update(currentRoutine, null);
        }
    }

    /**
     * get the component from tree viewer equivalent to currently accessed
     * subroutines component In the tree viewer product act as grand parent,
     * component act as parent, tables,batches,sources act as childrens, and
     * content of those tables, batches, sources act as grand childrens.
     * 
     * @param component - current accessed subroutine belonging component
     * @return component from tree viewer
     */
    private Object getParentComponent(Object component) {
        Object child = null;
        for (Object item : ((ITreeContentProvider) treeViewer.getContentProvider()).getElements(treeViewer.getInput())) {
            Object[] contents = ((ITreeContentProvider) treeViewer.getContentProvider()).getChildren(item);
            for (Object content : contents) {
                if (content.equals(component)) {
                    child = content;
                    break;
                }
            }
        }
        return child;
    }

    /**
     * get the object version of current accessed subroutine from tree viewer
     * Please see {@link getParentComponent} for more details
     * 
     * @return - current accessed subroutine
     */
    private Object getCurrentAccesedRoutine() {
        Object originalComponent = new T24ComponentDocNode(componentName, new T24ProductDocNode(productName));
        Object currentComponent = getParentComponent(originalComponent);
        Object currentSource = new T24SourceParentDocNode((T24ComponentDocNode) originalComponent);
        Object currentRoutine = new T24SourcesChildDocNOde(subRoutineName, (T24SourceParentDocNode) currentSource);
        return getChildSource(currentComponent, currentSource, currentRoutine);
    }

    /**
     * get the object version of current subroutine Please see
     * {@link getParentComponent} for more details
     * 
     * @param currentComponent
     * @param currentSource
     * @param currentRoutine
     * @return
     */
    private Object getChildSource(Object currentComponent, Object currentSource, Object currentRoutine) {
        Object currentSubRoutine = null;
        Object[] grandChildrens = ((ITreeContentProvider) treeViewer.getContentProvider()).getChildren(currentComponent);
        for (Object currentChildren : grandChildrens) {
            if (currentChildren.equals(currentSource)) {
                currentSubRoutine = getGrandChildSource(currentChildren, currentRoutine);
                break;
            }
        }
        return currentSubRoutine;
    }

    /**
     * get the source which act as grand child in tree viewer. Please see
     * {@link getParentComponent} for more details
     * 
     * @param currentChildren
     * @param currentRoutine
     * @return - current accessed subroutines object
     */
    private Object getGrandChildSource(Object currentChildren, Object currentRoutine) {
        Object currentAccessedRoutine = null;
        Object[] sub = ((ITreeContentProvider) treeViewer.getContentProvider()).getChildren(currentChildren);
        for (Object routinename : sub) {
            if (routinename.equals(currentRoutine)) {
                currentAccessedRoutine = routinename;
                break;
            }
        }
        return currentAccessedRoutine;
    }
}
