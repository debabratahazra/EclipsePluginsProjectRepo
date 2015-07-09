package com.odcgroup.page.ui.action;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.UpdateAction;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.MoveWidgetAndKeepSelectionCommand;
import com.odcgroup.page.ui.edit.WidgetEditPart;

/**
 * Base class action for moving tabs inside a tabbed pane.
 * 
 * @author Gary Hayes
 */
abstract public class AbstractMoveTabAction extends AbstractGenericAction implements UpdateAction {

    /** The selected editPart. */
    private WidgetEditPart oldEditPart;

    /** The active viewer. */
    private EditPartViewer viewer;
    
    /**
     * Abstract Constructor. 
     *  
     * @param parameters the set of parameters for the concrete action
     * @param oldEditPart
     *            the selected edit part. Note that this MUST not be used after the Command
     *            has been executed since it is no longer associated with the View
     */
    protected AbstractMoveTabAction(ActionParameters parameters, WidgetEditPart oldEditPart) {
        super(parameters);
        
        this.oldEditPart = oldEditPart;
        // Get the Viewer immediately since this EditPart will be stale afterwards
        this.viewer = oldEditPart.getViewer();
    }

    /**
     * Gets the flag indicating if we should increment the index.
     * 
     * @return boolean
     */
    abstract protected boolean isIncrement();
    
    /**
     * Runs the action
     */
    public void run() {     
        // Gets the table widget from the current edit part
        Widget widget = getParentWidget();
        int index = widget.getIndexOfSelectedChild();
        Widget child = widget.getContents().get(index);
        
        int increment = isIncrement() ? 1 : -1;
        execute(new MoveWidgetAndKeepSelectionCommand(getText(), widget, child,
                    index + increment));
        
        // Since the Command removes and then adds the Widget GEF removes the old 
        // edit part and then creates a new one. We need to find the new one 
        // and reselect it
        
		EditPart ep = (EditPart) viewer.getEditPartRegistry().get(child);
		viewer.deselectAll();
		if (ep!=null) {
			ep.setSelected(EditPart.SELECTED_PRIMARY);
			ep.setFocus(true);
			viewer.setFocus(ep);
			viewer.select(ep);
		}
    }   
    
    /**
     * Gets the index of the selected child.
     * 
     * @return int 
     */
    protected int getIndexOfSelectedChild() {
        Widget widget = getParentWidget();
        int index = widget.getIndexOfSelectedChild();
        return index;
    }   
    
    /**
     * Gets the parent Widget. This is the Widget, for example, the Table which 
     * contains the indexOfSelectedChild value.
     * 
     * @return Widget The parent Widget
     */
    protected Widget getParentWidget() {
        Widget w = getSelectedWidget();
        if (w.getTypeName().equals(WidgetTypeConstants.TABBED_PANE)) {
            return w;
        }
        return oldEditPart.getWidget().getParent();
    }   
}
