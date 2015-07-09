package com.odcgroup.page.ui.command;

import com.odcgroup.page.model.Widget;


/**
 * This Compound Command deletes a Widget and sets the selected index to 0 if the size, after deletion, is >= 1
 * otherwise it sets the selected index to -1. It also sets the isWidgetVisible property to true if this property exists
 * in the Widget.
 * 
 * @author Gary Hayes
 */
public class DeleteWidgetSetSelectedIndexCommand extends BaseCompoundCommand {
    
    /** The parent. */
    private Widget parent;
    
    /** The child. */
    private Widget child;

    /**
     * Creates a new DeleteWidgetSetSelectedIndexCommand.
     * 
     * @param parent The parent Widget
     * @param child The child Widget
     */
    public DeleteWidgetSetSelectedIndexCommand(Widget parent, Widget child) {
        this.parent = parent;
        this.child = child;
        
        DeleteWidgetCommand c1 = new DeleteWidgetCommand(parent, child);
        add(c1);   
    }
    
    /**
     * Executes the Command.
     */
    public void execute() {
    	// DS-1489 missing call to super.execute()
    	super.execute();
        if (parent.getContents().size() >= 1) {
            parent.setIndexOfSelectedChild(0);
        }
    }

    /**
     * Undoes the Command.
     */
    public void undo() {
        super.undo();
        
        int index = parent.getContents().indexOf(child);
        parent.setIndexOfSelectedChild(index);
    } 
}