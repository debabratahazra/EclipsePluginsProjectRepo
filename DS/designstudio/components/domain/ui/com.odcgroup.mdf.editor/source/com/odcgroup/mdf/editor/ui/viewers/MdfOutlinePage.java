package com.odcgroup.mdf.editor.ui.viewers;

import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import com.odcgroup.mdf.editor.ui.providers.content.MdfTreeContentProvider;
import com.odcgroup.mdf.editor.ui.providers.label.MdfTreeLabelProvider;
import com.odcgroup.mdf.editor.ui.sorters.MdfElementSorter;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class MdfOutlinePage extends ContentOutlinePage
    implements ISelectionChangedListener {
    private ISelectionProvider source = null;

    /**
     * Constructor for MdfOutlinePage
     */
    public MdfOutlinePage(ISelectionProvider source) {
        super();
        this.source = source;
        source.addSelectionChangedListener(this);
    }

    /**
     * @see org.eclipse.ui.part.IPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        super.createControl(parent);

        TreeViewer viewer = getTreeViewer();

        viewer.setContentProvider(new MdfTreeContentProvider());
        viewer.setLabelProvider(new MdfTreeLabelProvider());
        viewer.setSorter(new MdfElementSorter());
        viewer.setAutoExpandLevel(2);

        Object selection = getSelection(source.getSelection());
        viewer.addOpenListener(
            new IOpenListener() {
                public void open(OpenEvent event) {
                    onOpen(event);
                }
            });

        if (selection != null) {
            viewer.setInput(new Object[] { selection });
        }
    }

    /**
     * @see org.eclipse.ui.part.IPage#dispose()
     */
    public void dispose() {
        source.removeSelectionChangedListener(this);
        super.dispose();
    }

    public void selectionChanged(SelectionChangedEvent event) {
        if (event.getSource() == source) {
            Object selection = getSelection(event.getSelection());
            TreeViewer viewer = getTreeViewer();

            if ((selection != null) && (selection != viewer.getInput())) {
                viewer.setInput(new Object[] { selection });
            }
        }
    }

    private Object getSelection(ISelection selection) {
        return ((IStructuredSelection) selection).getFirstElement();
    }

    private void onOpen(OpenEvent event) {
        Object obj = getSelection(event.getSelection());
        source.setSelection(new StructuredSelection(obj));
    }
}
