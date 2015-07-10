/*
 * 
 */
package com.zealcore.se.ui.internal.assertions;

public final class ModifyEvent {

    private final String errorMessage;

    private final IEditor component;

    public ModifyEvent(final String errorMessage, final IEditor component) {
        super();
        this.errorMessage = errorMessage;
        this.component = component;
    }

    /**
     * Get the error message. If the error message is null, the component is in
     * a okay state
     * 
     * @return the error message
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * Gets the editable component.
     * 
     * @return the component
     */
    public IEditor getComponent() {
        return this.component;
    }
}
