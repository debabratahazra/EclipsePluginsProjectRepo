package com.odcgroup.mdf.editor.ui.viewers;

import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.ui.dnd.MdfModelElementTransfer;
import com.odcgroup.mdf.editor.ui.providers.content.MdfTreeContentProvider;
import com.odcgroup.mdf.editor.ui.providers.label.MdfTreeLabelProvider;
import com.odcgroup.mdf.editor.ui.sorters.MdfElementSorter;


/**
 * TODO: DOCUMENT ME!
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class MdfTreeViewer extends TreeViewer implements ILabelProviderListener {
    /**
     * Constructor for MdfTreeViewer
     */
    public MdfTreeViewer(Composite parent) {
        this(parent, SWT.FLAT | SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
    }

    /**
     * Constructor for MdfTreeViewer
     */
    public MdfTreeViewer(Composite parent, int style) {
        super(parent, style);

        setUseHashlookup(true);

        setContentProvider(new MdfTreeContentProvider());
        setSorter(new MdfElementSorter());

        MdfTreeLabelProvider labelProvider = new MdfTreeLabelProvider();
        labelProvider.addListener(this);

        ILabelDecorator decorator = MdfPlugin.getDefault().getWorkbench()
                                             .getDecoratorManager()
                                             .getLabelDecorator();
        setLabelProvider(new DecoratingLabelProvider(labelProvider, decorator));

        DragSource ds = new DragSource(getTree(),
                DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK);
        ds.setTransfer(new Transfer[] { MdfModelElementTransfer.INSTANCE });
        ds.addDragListener(new DragSourceAdapter() {
                public void dragSetData(DragSourceEvent event) {
                    // Set the data to be the first selected item                	
                    event.data = ((StructuredSelection) getSelection()).getFirstElement();
                }
            });
    }

    /**
     * @see org.eclipse.jface.viewers.ILabelProviderListener#labelProviderChanged(org.eclipse.jface.viewers.LabelProviderChangedEvent)
     */
    public void labelProviderChanged(LabelProviderChangedEvent event) {
        this.refresh(true);
    }
}
