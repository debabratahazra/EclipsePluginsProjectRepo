package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PartInitException;

import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.EditionSupportFactory;
import com.odcgroup.mdf.editor.ui.providers.content.MdfStructuredContentProvider;
import com.odcgroup.mdf.editor.ui.sorters.MdfElementSorter;
import com.odcgroup.mdf.metamodel.MdfModelElement;


public class MdfModelElementTableViewer extends Viewer implements IOpenListener {
    private static final Logger LOGGER =
        Logger.getLogger(MdfModelElementTableViewer.class);
    private static final int NAME = 0;
    private static final int DOCUMENTATION = 1;
    private final static String[] COLUMN_PROPERTIES =
        new String[] { "name", "documentation" };
    private final static String[] COLUMN_NAMES =
        new String[] { "Name", "Documentation" };
    private final static int[] COLUMN_WIDTHS = new int[] { 30, 70 };
    private Control control = null;
    private MdfModelElement model = null;
    private TableViewer viewer = null;
    private WidgetFactory factory = null;

    public MdfModelElementTableViewer(
        WidgetFactory factory, Composite parent, MdfModelElement model) {
        super();
        this.factory = factory;
        this.model = model;
        createControl(parent, null);
    }

    public MdfModelElementTableViewer(
        WidgetFactory factory, MdfModelElementButtonViewer buttonStrategyViewer,
        Composite parent, MdfModelElement model) {
        super();
        this.factory = factory;
        this.model = model;
        createControl(parent, buttonStrategyViewer);
        viewer.addSelectionChangedListener(buttonStrategyViewer);
    }

    /**
     * @see org.eclipse.jface.viewers.Viewer#getControl()
     */
    public Control getControl() {
        return control;
    }

    /**
     * @see org.eclipse.jface.viewers.Viewer#setInput(java.lang.Object)
     */
    public void setInput(Object input) {
    }

    /**
     * @see org.eclipse.jface.viewers.Viewer#getInput()
     */
    public Object getInput() {
        return viewer.getInput();
    }

    /**
     * @see org.eclipse.jface.viewers.Viewer#setSelection(org.eclipse.jface.viewers.ISelection, boolean)
     */
    public void setSelection(ISelection selection, boolean reveal) {
        viewer.setSelection(selection, reveal);
    }

    /**
     * @see org.eclipse.jface.viewers.Viewer#getSelection()
     */
    public ISelection getSelection() {
        return viewer.getSelection();
    }

    /*
     * DS-1349
     */
    public void open(OpenEvent e) {
        IStructuredSelection selection =
            (IStructuredSelection) e.getSelection();
        MdfModelElement sel = (MdfModelElement) selection.getFirstElement();

        if (sel != null) {
            try {
            	EditionSupportFactory.INSTANCE(viewer.getControl().getShell()).openModelEditor(
                    sel);
            } catch (PartInitException ex) {
                LOGGER.error(ex, ex);
            }
        }
    }

    /**
     * @see org.eclipse.jface.viewers.Viewer#refresh()
     */
    public void refresh() {
        viewer.refresh();
    }

    /**
     * @param parent
     */
    private void createControl(
        Composite parent, MdfModelElementButtonViewer buttonStrategyViewer) {
        Composite container = factory.createComposite(parent);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        container.setLayout(new GridLayout(2, false));

        {
            Table table =
                factory.createTable(
                    container,
                    SWT.MULTI | SWT.FULL_SELECTION | SWT.H_SCROLL |
                    SWT.V_SCROLL);
            table.setLayoutData(new GridData(GridData.FILL_BOTH));
            table.setHeaderVisible(true);
            table.setLinesVisible(true);

            viewer = new TableViewer(table);

            TableLayout layout = new TableLayout();
            TableColumn col = new TableColumn(table, SWT.NONE, NAME);
            col.setText(COLUMN_NAMES[NAME]);

            //col.setWidth(COLUMN_WIDTHS[NAME]);
            layout.addColumnData(
                new ColumnWeightData(COLUMN_WIDTHS[NAME], true));

            col = new TableColumn(table, SWT.NONE, DOCUMENTATION);
            col.setText(COLUMN_NAMES[DOCUMENTATION]);

            //col.setWidth(COLUMN_WIDTHS[DOCUMENTATION]);
            layout.addColumnData(
                new ColumnWeightData(COLUMN_WIDTHS[DOCUMENTATION], true));

            table.setLayout(layout);
            viewer.setColumnProperties(COLUMN_PROPERTIES);

            viewer.setSorter(new MdfElementSorter());
            viewer.setContentProvider(new MdfStructuredContentProvider());
            viewer.setLabelProvider(new MyLabelProvider());
            viewer.setInput(model);

            if (null != buttonStrategyViewer) {
                buttonStrategyViewer.renderModelElementButtons(
                    factory, container);
            }
        }

        viewer.addOpenListener(this);

        control = container;
    }

    private class MyLabelProvider extends LabelProvider
        implements ITableLabelProvider {
        /**
         * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
         */
        public Image getColumnImage(Object item, int columnIndex) {
            Image image = null;

            if (columnIndex == 0) {
                image = MdfUtility.getImage(item);
            }

            return image;
        }

        /**
         * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
         */
        public String getColumnText(Object item, int columnIndex) {
            MdfModelElement model = (MdfModelElement) item;

            switch (columnIndex) {
            case NAME:
                return model.getName();

            case DOCUMENTATION:

                String doc = model.getDocumentation();
                return (doc == null) ? "" : doc;
            }

            return null;
        }

        /**
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
         */
        public boolean isLabelProperty(Object element, String property) {
            return true;
        }
    }
}
