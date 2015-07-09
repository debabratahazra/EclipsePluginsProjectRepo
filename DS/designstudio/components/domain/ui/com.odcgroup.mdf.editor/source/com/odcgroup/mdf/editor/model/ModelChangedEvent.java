package com.odcgroup.mdf.editor.model;




/**
 * @version 1.0
 * @author <a href="cvedovini@odyssey-group.com">Claude Vedovini</a>
 */
public class ModelChangedEvent {
	public static final int ELEMENT_ADDED = 0;
	public static final int ELEMENT_REMOVED = 1;
	public static final int ELEMENT_UPDATED = 2;
	
	private final Object changedElement;
	private final int action;
	private final Object source;

    /**
     * Constructor for ModelChangedEvent
     */
    public ModelChangedEvent(Object source, Object element, int action) {
        super();
        this.source = source;
		this.changedElement = element;
		this.action = action;
    }

    public int getAction() {
        return action;
    }

    public Object getChangedElement() {
        return changedElement;
    }

    public Object getSource() {
        return source;
    }

}
