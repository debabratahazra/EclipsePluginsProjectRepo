package com.zealcore.se.ui.editors;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.part.EditorActionBarContributor;

public class LogsetEditorActionContributor extends EditorActionBarContributor {
    public LogsetEditorActionContributor() {}

    @Override
    public void contributeToToolBar(final IToolBarManager toolBarManager) {
        toolBarManager.add(new Separator("com.zealcore.sd.navigate"));
    }
}
