package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.mdf.editor.ui.WidgetFactory;


/**
 * DOCUMENT ME!
 *
 * @author <a href="mailto:daguirre@odyssey-group">David Aguirre</a>
 */
public interface MdfModelElementButtonViewer extends ISelectionChangedListener{
    void renderModelElementButtons(WidgetFactory factory, Composite parent);
    
}
