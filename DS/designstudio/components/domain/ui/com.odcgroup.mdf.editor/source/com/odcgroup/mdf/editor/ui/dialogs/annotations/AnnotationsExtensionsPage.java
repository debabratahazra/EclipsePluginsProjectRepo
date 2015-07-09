package com.odcgroup.mdf.editor.ui.dialogs.annotations;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.DialogPage;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfModelElement;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini </a>
 * @version 1.0
 */
public class AnnotationsExtensionsPage extends DialogPage
    implements SelectionListener {
	
	protected static final String AnnotationsAspect_NAMESPACE_URI = "http://www.odcgroup.com/mdf/ext/annotations";
	protected static final String AnnotationsAspect_GENERIC_ANNOTATIONS = "GenericAnnotations";
	
    private static final int NAME = 0;
    private static final int VALUE = 1;
    private static final int CDATA = 2;
    private final static String[] COLUMN_PROPERTIES =
        new String[] { "name", "value", "CDATA" };
    private final static String[] COLUMN_NAMES =
        new String[] { MdfPlugin.getResourceString(
                "annotations.columns.name.text"), MdfPlugin.getResourceString(
                "annotations.columns.value.text"), "CDATA" };
    private final static int[] COLUMN_WIDTHS =
        new int[] { Integer.parseInt(
                MdfPlugin.getResourceString(
                    "annotations.columns.name.width", "60")), Integer.parseInt(
                MdfPlugin.getResourceString(
                    "annotations.columns.value.width", "250")), 60 };
    private Button addButton = null;
    private Button editButton = null;
    private Button removeButton = null;
    private final Map annotations = new HashMap();
    private final MdfModelElement element;
    private TableViewer viewer = null;

    public AnnotationsExtensionsPage(MdfModelElement element) {
        super();
        setTitle(MdfPlugin.getResourceString("annotations.page.title"));
        setDescription(
            MdfPlugin.getResourceString("annotations.page.description"));
        this.element = element;
    }

    /**
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent) {
        WidgetFactory factory = getWidgetFactory();

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

            TableColumn col = new TableColumn(table, SWT.NONE, NAME);
            col.setText(COLUMN_NAMES[NAME]);
            col.setWidth(COLUMN_WIDTHS[NAME]);

            col = new TableColumn(table, SWT.NONE, VALUE);
            col.setText(COLUMN_NAMES[VALUE]);
            col.setWidth(COLUMN_WIDTHS[VALUE]);

            col = new TableColumn(table, SWT.NONE, CDATA);
            col.setText(COLUMN_NAMES[CDATA]);
            col.setWidth(COLUMN_WIDTHS[CDATA]);

            viewer = new TableViewer(table);
            viewer.setContentProvider(new AnnotationsProvider());
            viewer.setLabelProvider(new AnnotationsLabelProvider());
            viewer.setColumnProperties(COLUMN_PROPERTIES);
        }

        {
            Composite buttons = factory.createComposite(container);
            buttons.setLayoutData(
                new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
            buttons.setLayout(new GridLayout(1, true));

            addButton =
                factory.createButton(
                    buttons,
                    MdfPlugin.getResourceString("annotations.addButton.text"),
                    SWT.PUSH);
            addButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            editButton =
                factory.createButton(
                    buttons,
                    MdfPlugin.getResourceString("annotations.editButton.text"),
                    SWT.PUSH);
            editButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            editButton.setEnabled(false);

            removeButton =
                factory.createButton(
                    buttons,
                    MdfPlugin.getResourceString(
                        "annotations.removeButton.text"), SWT.PUSH);
            removeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            removeButton.setEnabled(false);
        }

        viewer.getTable().addSelectionListener(this);
        addButton.addSelectionListener(this);
        editButton.addSelectionListener(this);
        removeButton.addSelectionListener(this);

        viewer.addOpenListener(
            new IOpenListener() {
                public void open(OpenEvent e) {
                    onEdit();
                }
            });

        initialize();
        setControl(container);
    }

    public void doSave(MdfModelElement model) {
        ModelFactory.INSTANCE.removeAnnotations(model,
            AnnotationsAspect_NAMESPACE_URI);

        if (annotations.size() > 0) {
            MdfAnnotation annotation =
                ModelFactory.INSTANCE.createMdfAnnotation(
                    AnnotationsAspect_NAMESPACE_URI,
                    AnnotationsAspect_GENERIC_ANNOTATIONS);
            ModelFactory.INSTANCE.addAnnotation(model, annotation);

            Iterator it = annotations.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                AnnotationProperty p = (AnnotationProperty)entry.getValue();
                MdfAnnotationProperty property =
                    ModelFactory.INSTANCE.createMdfAnnotationProperty(
                        annotation, p.getName(), p.getValue(), p.isCData());
                ModelFactory.INSTANCE.addAnnotationProperty(annotation, property);
            }
        }
    }

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetDefaultSelected(SelectionEvent e) {
        widgetSelected(e);
    }

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetSelected(SelectionEvent e) {
        if (e.widget == addButton) {
            onAdd();
        } else if (e.widget == editButton) {
            onEdit();
        } else if (e.widget == removeButton) {
            onRemove();
        } else if (e.widget == viewer.getTable()) {
            boolean selected = !viewer.getSelection().isEmpty();
            removeButton.setEnabled(selected);
            editButton.setEnabled(selected);
        }
    }

    /**
     * Tests if the current workbench selection is a suitable projectCombo to
     * use.
     */
    private void initialize() {
        MdfAnnotation annot =
            element.getAnnotation(
                AnnotationsAspect_NAMESPACE_URI,
                AnnotationsAspect_GENERIC_ANNOTATIONS);

        if (annot != null) {
            Iterator it = annot.getProperties().iterator();
            AnnotationProperty p = null;
            while (it.hasNext()) {
                MdfAnnotationProperty property =
                    (MdfAnnotationProperty) it.next();
                p = new AnnotationProperty();
                p.setName(property.getName());
                p.setValue(property.getValue());
                p.setCData(property.isCDATA());
                annotations.put(property.getName(), p);
            }
        }

        viewer.setInput(annotations);
    }

    private void onAdd() {
        AnnotationPropertyEditDialog dialog = new AnnotationPropertyEditDialog(getShell());
        dialog.setWindowTitle(
            MdfPlugin.getResourceString("annotations.editDialog.addTitle"));

        if (dialog.open() == Dialog.OK) {
            String name = dialog.getAnnotationName();
            AnnotationProperty p = new AnnotationProperty();
            p.setName(name);
            p.setValue(dialog.getAnnotationValue());
            p.setCData(dialog.isCDataEnabled());
            annotations.put(name, p);
            viewer.refresh();
            setDirty(true);
        }
    }

    private void onEdit() {
        IStructuredSelection selection =
            (IStructuredSelection) viewer.getSelection();
        Map.Entry entry = (Map.Entry) selection.getFirstElement();
        AnnotationProperty pr = (AnnotationProperty) entry.getValue();
        AnnotationPropertyEditDialog dialog = new AnnotationPropertyEditDialog(getShell());
        dialog.setAnnotationName(pr.getName());
        dialog.setAnnotationValue(pr.getValue());
        dialog.setCDataEnabled(pr.isCData());

        dialog.setWindowTitle(
            MdfPlugin.getResourceString("annotations.editDialog.editTitle"));

        if (dialog.open() == Dialog.OK) {
            String name = dialog.getAnnotationName();
            AnnotationProperty p = new AnnotationProperty();
            p.setName(name);
            p.setValue(dialog.getAnnotationValue());
            p.setCData(dialog.isCDataEnabled());
            annotations.put(name, p);
            viewer.refresh();
            setDirty(true);
        }
    }

    private void onRemove() {
        IStructuredSelection selection =
            (IStructuredSelection) viewer.getSelection();
        Object[] annots = selection.toArray();

        if (annots.length > 0) {
            for (int i = 0; i < annots.length; i++) {
                annotations.remove(((Map.Entry) annots[i]).getKey());
            }

            viewer.refresh();
            setDirty(true);
        }
    }

    private class AnnotationsLabelProvider extends LabelProvider
        implements ITableLabelProvider {
        /**
         * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object,
         *      int)
         */
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

        /**
         * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,
         *      int)
         */
        public String getColumnText(Object element, int columnIndex) {
            Map.Entry he = (Map.Entry) element;
            AnnotationProperty p = (AnnotationProperty) he.getValue();
            switch (columnIndex) {
            case NAME:
                return p.getName();

            case VALUE:
                return p.getValue();
             
            case CDATA:
            	return p.isCData()+"";
            }

            return null;
        }

        /**
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
         *      java.lang.String)
         */
        public boolean isLabelProperty(Object element, String property) {
            return true;
        }
    }

    private class AnnotationsProvider extends ArrayContentProvider {
        /**
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         */
        public Object[] getElements(Object input) {
            if (input instanceof Map) {
                input = ((Map) input).entrySet();
            }

            return super.getElements(input);
        }
    }
    
    private class AnnotationProperty  {
    	private String name;
    	private String value;
    	private boolean cData;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public boolean isCData() {
			return cData;
		}
		public void setCData(boolean data) {
			cData = data;
		}
    }
}
