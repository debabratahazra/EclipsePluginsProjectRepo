package com.temenos.t24.tools.eclipse.basic.views.inlineDocs.listener;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

import com.temenos.t24.tools.eclipse.basic.document.util.DocumentTextConverter;
import com.temenos.t24.tools.eclipse.basic.views.inlineDocs.model.InLineLabelsNode;
import com.temenos.t24.tools.eclipse.basic.views.inlineDocs.model.InLineRootNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;
import com.temenos.t24.tools.eclipse.basic.wizards.docgeneration.file.GenerateDocConstants;

/**
 * listener class for listening tree-viewer of In-line doc's view
 * 
 * @author sbharathraja
 * 
 */
public class InLineDocsViewListener {

    /**
     * instance of {@link TreeViewer} which is presented in In-line doc's view
     */
    private TreeViewer inLineTreeViewer;
    /** pop-up window to show information about labels */
    private InfoPopup pop;
    /** instance of {@link InLineDocsHelpCache} which helps to get the help text */
    private InLineDocsHelpCache helpCacher;
    /** help text */
    private String helpText = "";
    /**  */
    private boolean isEligibleToMoreInfo;

    public InLineDocsViewListener(TreeViewer inLineTreeViewer) {
        this.inLineTreeViewer = inLineTreeViewer;
    }

    /**
     * adding hover listener to tree viewer of in-line documentation view
     * 
     */
    public void addHoverListener() {
        if (inLineTreeViewer != null) {
            inLineTreeViewer.getTree().addListener(SWT.MouseHover, new Listener() {

                public void handleEvent(Event hoverEvent) {
                    Point point = new Point(hoverEvent.x, hoverEvent.y);
                    String nodeName = getNodeName(point);
                    if (!nodeName.equalsIgnoreCase("") && !helpText.equalsIgnoreCase("")) {
                        Rectangle rect = new Rectangle(210, 320, 300, 210);
                        pop = new InfoPopup(new Shell(), rect, point, "Information About :  " + nodeName,
                                "Select and press ESC to close");
                        if (isEligibleToMoreInfo)
                            pop.setSubRoutineName(nodeName);
                        pop.setIsMoreInfoActionNeed(isEligibleToMoreInfo);
                        pop.setText(DocumentTextConverter.convertCarriageReturnToNewLineFeed(helpText));
                        pop.open();
                    }
                }
            });
        }
    }

    /**
     * adding mouse moving listener to tree viewer of in-line doc's view. This
     * helps to disposing the info pop-up's view when the user move the mouse
     * out of that.
     */
    public void addMouseMoveListener() {
        if (inLineTreeViewer != null) {
            inLineTreeViewer.getTree().addListener(SWT.MouseMove, new Listener() {

                public void handleEvent(Event mouseMoveEvent) {
                    if (pop != null)
                        pop.close();
                }
            });
        }
    }

    /**
     * get the node name from the in-line tree viewer with given point and with
     * the help of {@link InLineDocsHelpCache}
     * 
     * @param point - hovered point
     * @return node name if found, empty otherwise
     */
    private String getNodeName(Point point) {
        helpCacher = new InLineDocsHelpCache();
        TreeItem treeItem = inLineTreeViewer.getTree().getItem(point);
        if (treeItem == null)
            return "";
        if (treeItem.getData() instanceof IT24TreeViewNode) {
            IT24TreeViewNode treeNode = (IT24TreeViewNode) treeItem.getData();
            if (treeNode instanceof InLineRootNode) {
                String nodeName = ((InLineRootNode) treeNode).getLabel();
                isEligibleToMoreInfo = true;
                helpText = helpCacher.getRoutineHelpText(nodeName, GenerateDocConstants.NAME_OF_SMALL_COMMENT);
                return nodeName;
            }
            if (treeNode instanceof InLineLabelsNode) {
                String nodeName = ((InLineLabelsNode) treeNode).getLabel();
                String routineName = ((InLineLabelsNode) treeNode).getParent().getLabel();
                isEligibleToMoreInfo = false;
                helpText = helpCacher.getLabelHelpText(routineName, nodeName);
                return nodeName;
            }
        }
        return "";
    }
}
