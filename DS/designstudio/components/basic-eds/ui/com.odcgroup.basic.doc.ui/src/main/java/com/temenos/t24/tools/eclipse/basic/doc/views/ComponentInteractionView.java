package com.temenos.t24.tools.eclipse.basic.doc.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

import com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.ComponentInteractionContentProvider;
import com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.ComponentInteractionLabelProvider;
import com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.ComponentInteractionViewController;
import com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.listener.ComponentInteractionViewListener;
import com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.model.ComponentInteractionNodeContainer;

public class ComponentInteractionView extends ViewPart {

    /** id of the zest view */
    public static final String viewID = "com.temenos.t24.tools.eclipse.basic.graphical.componentInteractionView";
    /** instance of {@link GraphViewer} */
    private GraphViewer zestViewer;
    /** instance of class {@link ComponentInteractionViewController} */
    private ComponentInteractionViewController zestController = ComponentInteractionViewController.getInstance();
    /** instance of class {@link ComponentInteractionViewListener} */
    private ComponentInteractionViewListener zestListener;

    @Override
    public void createPartControl(Composite parent) {
        createZestGraphView(parent);
        doListen();
        setLayout();
    }

    /**
     * create the zest view
     * 
     * @param parent - parent composite
     */
    private void createZestGraphView(Composite parent) {
        if (zestViewer == null)
            zestViewer = new GraphViewer(parent, SWT.BORDER);
        zestViewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
        zestViewer.setContentProvider(new ComponentInteractionContentProvider());
        zestViewer.setLabelProvider(new ComponentInteractionLabelProvider());
    }

    /**
     * set the layout for zest viewer
     */
    private void setLayout() {
        if (zestViewer != null) {
            zestViewer.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
            zestViewer.applyLayout();
        }
    }

    /**
     * loading the zest tree view for given component
     * 
     */
    public void loadTreeView() {
        // ready to collect new connection
        zestController.makeNewConnection();
        String selectedComponent = ComponentInteractionNodeContainer.getInstance().getSelectedComponent();
        if (zestViewer != null && !selectedComponent.equalsIgnoreCase(""))
            zestViewer.setInput(zestController.getNodes(selectedComponent));
    }

    /**
     * hand-over the listening responsibility to zest listener
     */
    private void doListen() {
        if (zestViewer != null) {
            zestListener = new ComponentInteractionViewListener(zestViewer);
            zestListener.addDoubleClickListener();
        }
    }

    @Override
    public void setFocus() {
        // do nothing
    }
}
