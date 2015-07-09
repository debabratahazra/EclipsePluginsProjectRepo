package com.temenos.t24.tools.eclipse.basic.views.inlineDocs;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.temenos.t24.tools.eclipse.basic.views.inlineDocs.listener.InLineDocsViewListener;
import com.temenos.t24.tools.eclipse.basic.views.tree.T24TreeViewContentProvider;
import com.temenos.t24.tools.eclipse.basic.views.tree.T24TreeViewLabelProvider;

/**
 * InLine Documentation view
 * 
 * @author sbharathraja
 * 
 */
public class InLineDocsView extends ViewPart {

    /** view id */
    public static final String viewID = "com.temenos.t24.tools.eclipse.basic.InLineDocView";
    /** instance of class {@link TreeViewer} */
    private TreeViewer inLineTreeViewer;
    /** controller */
    private InLineDocsViewController inLineViewController = InLineDocsViewController.getInstance();
    /** reference variable of {@link InLineDocsViewListener} */
    private InLineDocsViewListener docViewListener;

    @Override
    public void createPartControl(Composite parent) {
        createTreeView(parent);
        loadTreeView();
        addListeners();
    }

    /**
     * create the tree view with given composite
     * 
     * @param parent - composite
     */
    private void createTreeView(Composite parent) {
        GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        layout.verticalSpacing = 2;
        // setting layout for parent composite
        // parent.setLayout(layout);
        inLineTreeViewer = new TreeViewer(parent);
        // setting label provider
        inLineTreeViewer.setLabelProvider(new T24TreeViewLabelProvider());
        // setting content provider
        inLineTreeViewer.setContentProvider(new T24TreeViewContentProvider());
        GridData treeData = new GridData();
        treeData.grabExcessHorizontalSpace = true;
        treeData.grabExcessVerticalSpace = true;
        treeData.horizontalAlignment = GridData.FILL;
        treeData.verticalAlignment = GridData.FILL;
        // setting grid data for tree viewer
        inLineTreeViewer.getTree().setData(treeData);
    }

    /**
     * loading the tree view
     */
    public void loadTreeView() {
        String subRoutineName = InLineDocsViewInitializer.getInstance().getSubRoutineName();
        // setting input for tree viewer
        if (inLineTreeViewer != null && !subRoutineName.equalsIgnoreCase(""))
            inLineTreeViewer.setInput(inLineViewController.getInLineDocsTree(subRoutineName));
    }

    /**
     * adding listener to the tree viewer
     */
    private void addListeners() {
        docViewListener = new InLineDocsViewListener(inLineTreeViewer);
        docViewListener.addHoverListener();
        docViewListener.addMouseMoveListener();
    }

    @Override
    public void setFocus() {
    }
}
