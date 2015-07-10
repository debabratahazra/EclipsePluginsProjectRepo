package com.zealcore.se.ui.editors;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;

import com.zealcore.se.ui.internal.LogViewInput;

public final class LogsetEditorInputFactory implements IElementFactory {

    private static final String ID_FACTORY = "com.zealcore.se.ui.editors.LogsetEditorInputFactory";

    public LogsetEditorInputFactory() {}

    public IAdaptable createElement(final IMemento memento) {
        return LogViewInput.valueOf(memento);
    }

    /**
     * Returns the element factory id for this class.
     * 
     * @return the element factory id
     */
    public static String getFactoryId() {
        return ID_FACTORY;
    }
}
