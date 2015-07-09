package com.odcgroup.mdf.editor.ui.actions;

import org.eclipse.jface.action.Action;

import com.odcgroup.mdf.editor.MdfPlugin;

/**
 * @version 1.0
 * @author <a href="cvedovini@odyssey-group.com">Claude Vedovini </a>
 */
public class MdfAction extends Action {
    private final MdfActionListener listener;

    public MdfAction(MdfActionListener listener, String id, String icon) {
        super(MdfPlugin.getResourceString(id), MdfPlugin.getDefault()
                .getImageRegistry().getDescriptor(icon));

        setId(id);
        this.listener = listener;
    }

    public void run() {
        if (listener != null) {
            listener.onAction(this);
        }
    }
}