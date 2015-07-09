package com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.listener;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.DirectedGraphLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalShift;
import org.eclipse.zest.layouts.algorithms.HorizontalTreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.VerticalLayoutAlgorithm;

import com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.ComponentInteractionViewController;
import com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.model.ComponentInteractiontNode;

/**
 * contains listener operation for Zest's graph viewer
 * 
 * @author sbharathraja
 * 
 */
public class ComponentInteractionViewListener {

    /** instance of class {@link GraphViewer} */
    private GraphViewer zestViewer;
    /** instance of class {@link ComponentInteractionViewController} */
    private ComponentInteractionViewController zestController = ComponentInteractionViewController.getInstance();
    /** layout for graph viewer */
    private LayoutAlgorithm layout;
    /** counting for setting the layout */
    private int layoutCount = 1;

    /**
     * constructor for class {@link ComponentInteractionViewListener}
     * 
     * @param zestViewer - zest's GraphViewer
     */
    public ComponentInteractionViewListener(GraphViewer zestViewer) {
        this.zestViewer = zestViewer;
    }

    /**
     * adding the double click listener to zest's graph viewer
     */
    public void addDoubleClickListener() {
        if (zestViewer != null) {
            zestViewer.addDoubleClickListener(new IDoubleClickListener() {

                public void doubleClick(DoubleClickEvent event) {
                    Object selectedNode = ((StructuredSelection) event.getSelection()).getFirstElement();
                    if (selectedNode instanceof ComponentInteractiontNode)
                        zestViewer.setInput(zestController.getReArrangedNodes((ComponentInteractiontNode) selectedNode));
                    if (selectedNode == null)
                        setLayout();
                }
            });
        }
    }

    /**
     * set the layout for zest viewer on the basis of changing the layout with
     * int variable
     */
    private void setLayout() {
        switch (layoutCount) {
            case 1: {
                layout = new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
                break;
            }
            case 2: {
                layout = new RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
                break;
            }
            case 3: {
                layout = new GridLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
                break;
            }
            case 4: {
                layout = new DirectedGraphLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
                break;
            }
            case 5: {
                layout = new org.eclipse.zest.layouts.algorithms.HorizontalLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
                break;
            }
            case 6: {
                layout = new HorizontalTreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
                break;
            }
            case 7: {
                layout = new HorizontalShift(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
                break;
            }
            case 8: {
                layout = new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
                break;
            }
            case 9: {
                layout = new VerticalLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
                break;
            }
        }
        zestViewer.setLayoutAlgorithm(layout, true);
        zestViewer.applyLayout();
        if (layoutCount++ > 9) {
            layoutCount = 1;
        }
    }
}
