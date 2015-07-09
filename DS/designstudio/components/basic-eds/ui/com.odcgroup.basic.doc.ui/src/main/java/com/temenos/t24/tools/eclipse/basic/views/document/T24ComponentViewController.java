package com.temenos.t24.tools.eclipse.basic.views.document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

import com.temenos.t24.tools.eclipse.basic.doc.views.ComponentInteractionView;
import com.temenos.t24.tools.eclipse.basic.document.ComponentDocumentSupplier;
import com.temenos.t24.tools.eclipse.basic.document.IDocInput;
import com.temenos.t24.tools.eclipse.basic.document.JavaDocumentSupplier;
import com.temenos.t24.tools.eclipse.basic.document.SubroutineDocumentSupplier;
import com.temenos.t24.tools.eclipse.basic.document.util.DocumentFileUtil;
import com.temenos.t24.tools.eclipse.basic.utils.T24DocViewerUtil;
import com.temenos.t24.tools.eclipse.basic.views.inlineDocs.InLineDocsView;
import com.temenos.t24.tools.eclipse.basic.views.inlineDocs.InLineDocsViewInitializer;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewRoot;
import com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.file.ComponentInteractionFileManager;
import com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.file.FileManager;
import com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.model.ComponentInteractionNodeContainer;

public class T24ComponentViewController {

    /**
     * name of the .properites file which having relationship b/w all components
     */
    private static final String propertiesFileName = "componentinteraction-lookup";
    /** extension for the properties file */
    private static final String propertiesFileExtn = ".properties";
    /** instance of class {@link FileManager} */
    private ComponentInteractionFileManager compInteractionFileMGR = new ComponentInteractionFileManager();
    private static T24ComponentViewController viewController = new T24ComponentViewController();
    private DocumentTreeViewInitialiser viewInitialiser = DocumentTreeViewInitialiser.getInstance();
    /**
     * flag to checking whether the T24DocView need to be activated (in other
     * words force focusing)
     */
    private static boolean isT24DocViewNeedToBeActivated;
    /**
     * instance of {@link IViewPart} which receives the instance of a view (any
     * T24Basic related view) which we tried to open asynchronously
     */
    private static IViewPart asyncViewInstance;

    private T24ComponentViewController() {
    }

    public static T24ComponentViewController getInstance() {
        return viewController;
    }

    /**
     * get the tree content
     * 
     * @param productPath - xpath as component specific/general
     * @return
     */
    public IT24TreeViewRoot getTree(String productPath) {
        IT24TreeViewRoot tree = new T24ComponentTree();
        List<String> products = viewInitialiser.getProducts(productPath);
        for (String product : products) {
            tree.addParentNode(new T24ProductDocNode(product));
        }
        return tree;
    }

    public IT24TreeViewRoot getEmptyTree() {
        IT24TreeViewRoot tree = new T24ComponentTree();
        return tree;
    }

    public IT24TreeViewNode[] getComponents(T24ProductDocNode productNode) {
        String product = productNode.getLabel();
        String[] componentsArr = viewInitialiser.getComponents(product);
        List<T24ComponentDocNode> componentNodes = new ArrayList<T24ComponentDocNode>();
        for (String component : componentsArr) {
            componentNodes.add(new T24ComponentDocNode(component, productNode));
        }
        IT24TreeViewNode[] components = new IT24TreeViewNode[componentNodes.size()];
        componentNodes.toArray(components);
        return components;
    }

    public IT24TreeViewNode[] getComponentChildren(T24ComponentDocNode componentNode) {
        /**
         * Create[3]for generate a new child[2](Sources) folder
         */
        // Mahudesh
        IT24TreeViewNode[] children = new IT24TreeViewNode[3];
        children[0] = new T24TablesDocNode(componentNode);
        children[1] = new T24CobDocNode(componentNode);
        // Source inclusion is in phase 2
        // children[2] = new T24SourceDocNode(componentNode);
        /**
         * Create a Child[2]for generate the Sources folder in Component
         * Landscape View
         */
        // Mahudesh
        children[2] = new T24SourceParentDocNode(componentNode);
        return children;
    }

    public IT24TreeViewNode[] getCobChildren(T24CobDocNode cobNode) {
        List<IT24TreeViewNode> childrenNodes = new ArrayList<IT24TreeViewNode>();
        String product = cobNode.getParent().getParent().getLabel();
        String component = cobNode.getParent().getLabel();
        String[] batchArr = ComponentDocumentSupplier.getBatchFiles(product, component);
        for (String batch : batchArr) {
            childrenNodes.add(new T24BatchDocNode(batch, cobNode));
        }
        IT24TreeViewNode[] children = new IT24TreeViewNode[childrenNodes.size()];
        return childrenNodes.toArray(children);
    }

    public IT24TreeViewNode[] getDataChildren(T24TablesDocNode dataNode) {
        String product = dataNode.getParent().getParent().getLabel();
        String component = dataNode.getParent().getLabel();
        List<IT24TreeViewNode> childrenNodes = new ArrayList<IT24TreeViewNode>();
        // get - data - files
        String[] fileArr = ComponentDocumentSupplier.getDataFiles(product, component);
        for (String file : fileArr) {
            childrenNodes.add(new T24DataFileDocNode(file, dataNode));
        }
        IT24TreeViewNode[] children = new IT24TreeViewNode[childrenNodes.size()];
        childrenNodes.toArray(children);
        return children;
    }

    /**
     * method for access to get the Product & Component paths, get the List of
     * Source Names from that ComponentName_Sources.xml File generate the List
     * of Sources in SourcesChildDocNOde, & return Children to T24SourcesDocNode
     */
    // mahudesh
    public IT24TreeViewNode[] getSourcesChildren(T24SourceParentDocNode sourcesNode) {
        String product = sourcesNode.getParent().getParent().getLabel();
        String component = sourcesNode.getParent().getLabel();
        List<IT24TreeViewNode> childrenNodes = new ArrayList<IT24TreeViewNode>();
        // get source files
        String[] sourcesArr = ComponentDocumentSupplier.getSourceFiles(product, component);
        for (String sources : sourcesArr) {
            childrenNodes.add(new T24SourcesChildDocNOde(sources, sourcesNode));
        }
        IT24TreeViewNode[] children = new IT24TreeViewNode[childrenNodes.size()];
        childrenNodes.toArray(children);
        return children;
    }// @ Mahudesh

    public IT24TreeViewNode[] getSourceChildren(T24SourceDocNode sourceNode) {
        String product = sourceNode.getParent().getParent().getLabel();
        String component = sourceNode.getParent().getLabel();
        List<IT24TreeViewNode> childrenNodes = new ArrayList<IT24TreeViewNode>();
        // get - source - subroutines
        String[] subroutinesArr = ComponentDocumentSupplier.getSubroutines(product, component);
        for (String subroutine : subroutinesArr) {
            childrenNodes.add(new T24SubroutineDocNode(subroutine, sourceNode));
        }
        // get - source - java class
        String[] javaClasses = JavaDocumentSupplier.getInstance().getJavaClasses(product, component);
        for (String javaClass : javaClasses) {
            childrenNodes.add(new T24JavaClassDocNode(javaClass, sourceNode));
        }
        IT24TreeViewNode[] children = new IT24TreeViewNode[childrenNodes.size()];
        childrenNodes.toArray(children);
        return children;
    }

    public IT24TreeViewNode[] getGosubs(T24SubroutineDocNode subroutineNode) {
        String subroutine = subroutineNode.getLabel();
        String[] gosubArr = SubroutineDocumentSupplier.getGosubs(subroutine);
        List<T24GosubDocNode> gosubNodes = new ArrayList<T24GosubDocNode>();
        for (String gosub : gosubArr) {
            gosubNodes.add(new T24GosubDocNode(gosub, subroutineNode));
        }
        IT24TreeViewNode[] gosubs = new IT24TreeViewNode[gosubNodes.size()];
        gosubNodes.toArray(gosubs);
        return gosubs;
    }

    /**
     * load the T24Doc View
     * 
     * @param selectedNode - double clicked node from component landscape view
     */
    public void loadDocView(IDocumentNode selectedNode) {
        IViewPart view = showView();
        if (view == null || !(view instanceof T24DocumentView)) {
            return;
        }
        IDocInput selectedDocInput = selectedNode.getDocument();
        ((T24DocumentView) view).showText(selectedDocInput);
    }

    /**
     * load the component interaction view
     * 
     * @param selectedNode - double clicked node from component landscape view
     */
    public void loadComponentInteractionView(T24ComponentDocNode selectedNode) {
        String componentName = selectedNode.getLabel();
        // since the component interaction file located in T24 Comp Doc
        // path this check is necessary for avoiding exception
        if (!DocumentFileUtil.getRoot().equalsIgnoreCase("")) {
            // if the selected component not already read from file doing the
            // read
            if (!ComponentInteractionNodeContainer.getInstance().isSelectedComponentAlreadyRead(componentName))
                doReadFile(componentName);
            ComponentInteractionNodeContainer.getInstance().setSelectedComponent(componentName);
            showComponentInteractionView();
        }
    }

    /**
     * load the InLine Documentation View
     * 
     * @param selectedNode - double clicked node from component landscape view
     */
    public void loadInLineDocView(T24SourcesChildDocNOde selectedNode) {
        IWorkbenchPage activePage = T24DocViewerUtil.getActivePage();
        if (!InLineDocsViewInitializer.getInstance().isEligibleToShowInLineDoc() || activePage == null)
            return;
        String routineName = selectedNode.getLabel();
        InLineDocsViewInitializer.getInstance().setSubRoutineName(routineName);
        if (T24DocViewerUtil.isViewThere(InLineDocsView.viewID)) {
            IViewPart inLineView = activePage.findView(InLineDocsView.viewID);
            ((InLineDocsView)inLineView).loadTreeView();
            if (!T24DocViewerUtil.activateViewAsynchronously(inLineView))
                activePage.bringToTop(inLineView);
        } else
            showViewAsynchronously(InLineDocsView.viewID, activePage);
    }

    /**
     * show the component interaction view
     */
    private void showComponentInteractionView() {
        final IWorkbenchPage activePage = T24DocViewerUtil.getActivePage();
        if (activePage == null)
            return;
        if (T24DocViewerUtil.isViewThere(ComponentInteractionView.viewID)) {
            IViewPart compInteractView = activePage.findView(ComponentInteractionView.viewID);
            ((ComponentInteractionView) compInteractView).loadTreeView();
            if (!T24DocViewerUtil.activateViewAsynchronously(compInteractView))
                activePage.bringToTop(compInteractView);
        } else {
            IViewPart compInteractView = showViewAsynchronously(ComponentInteractionView.viewID, activePage);
            if (compInteractView != null && compInteractView instanceof ComponentInteractionView)
                ((ComponentInteractionView) compInteractView).loadTreeView();
        }
    }

    /**
     * read the .properties file and convert the line as model for component
     * interaction view and stored the model in map presented in
     * {@link ComponentInteractionNodeContainer}
     * 
     * @param componentName - name of the component
     */
    private void doReadFile(String componentName) {
        String propFilePath = DocumentFileUtil.getRoot() + DocumentFileUtil.SEP + propertiesFileName + propertiesFileExtn;
        compInteractionFileMGR.loadFile(new File(propFilePath));
        compInteractionFileMGR.convertAsModels(componentName);
    }

    /**
     * showing the T24 Doc View.
     * 
     * @return instance of T24 Doc View
     */
    private static IViewPart showView() {
        final IWorkbenchPage activePage = T24DocViewerUtil.getActivePage();
        if (activePage == null) {
            return null;
        }
        if (T24DocViewerUtil.isViewThere(T24DocumentView.VIEW_ID)) {
            IViewPart t24DocView = activePage.findView(T24DocumentView.VIEW_ID);
            if (isT24DocViewNeedToBeActivated)
                if (!T24DocViewerUtil.activateViewAsynchronously(t24DocView))
                    activePage.bringToTop(t24DocView);
            return t24DocView;
        } else if (!DocumentFileUtil.getRoot().equalsIgnoreCase("")) {
            return showViewAsynchronously(T24DocumentView.VIEW_ID, activePage);
        }
        return null;
    }

    /**
     * set whether the T24DocView need to be activated or not. Activated in
     * other words force the focus to T24DocView.
     * 
     * @param isT24DocViewNeedToBeActivated - true if need to be activated,
     *            false otherwise
     */
    public void setT24DocViewAsNeedToBeActivated(boolean isT24DocViewNeedToBeActivated) {
        T24ComponentViewController.isT24DocViewNeedToBeActivated = isT24DocViewNeedToBeActivated;
    }

    /**
     * show a view asynchronously with given viewID in given activePage.
     * Asynchronously means here the view opening process would not block the
     * any other thread say for example highlighting a current accessed source
     * in landscape view will not get disturbed because of opening this view.
     * 
     * @param viewID - id of the view which needs to open asynchronously
     * @param activePage - instance of {@link IWorkbenchPage}
     * @return instance of the view
     */
    private static IViewPart showViewAsynchronously(final String viewID, final IWorkbenchPage activePage) {
        asyncViewInstance = null;
        final IEditorPart activeEditor = activePage.getActiveEditor();
        try {
            if (activeEditor == null)
                return activePage.showView(viewID);
            activeEditor.getSite().getShell().getDisplay().asyncExec(new Runnable() {

                public void run() {
                    try {
                        asyncViewInstance = activePage.showView(viewID);
                    } catch (PartInitException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (PartInitException e) {
            e.printStackTrace();
        }
        return asyncViewInstance;
    }
}
