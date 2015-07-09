package com.odcgroup.page.ui.command;

import com.odcgroup.page.model.Widget;

/**
 * This Compound Command adds a Widget and sets the selected index
 * to that of the newly added Widget. It also sets the isVisible flag of the
 * previously selected Widget to false if the corresponding property exists.
 * 
 * @author Gary Hayes
 */
public class AddWidgetSetSelectedIndexCommand extends BaseCompoundCommand {
    
    /** The parent. */
    private Widget newParent;
    
    /** The child. */
    private Widget child;
    
    /** The old index of the selected child. */
    private int oldIndex;

	/**
	 * Creates a new AddWidgetSetSelectedIndexCommand.
	 * 
	 * @param newParent
	 *            The new parent Widget
	 * @param child
	 *            The child Widget
	 */
	public AddWidgetSetSelectedIndexCommand(Widget newParent, Widget child) {
	    this.newParent = newParent;
	    this.child = child;
	    
	    oldIndex = newParent.getIndexOfSelectedChild();
		
	    AddWidgetCommand c1 = new AddWidgetCommand(newParent, child);
		add(c1);
		
		/*int newIndex = newParent.getContents().size();
		Property p = newParent.findProperty(PropertyTypeConstants.INDEX_OF_SELECTED_CHILD);
		UpdatePropertyCommand c2 = new UpdatePropertyCommand(p, String.valueOf(newIndex));
		add(c2);*/
	}
	
    /**
     * Executes the Command.
     */
    public void execute() {
    	super.execute();
        int index = newParent.getContents().indexOf(child);
        newParent.setIndexOfSelectedChild(index);
    }

    /**
     * Undoes the Command.
     */
    public void undo() {
        super.undo();
        
        newParent.setIndexOfSelectedChild(oldIndex);
    }	
}
