package com.zealcore.se.ui.actions;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SearchPattern;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.zealcore.se.ui.editors.GanttChart.TaskFilter;

class FilteredListSelectionDialog extends SelectionDialog {
    // the root element to populate the viewer with
    private Object inputElement;

    // providers for populating this dialog
    private ILabelProvider labelProvider;

    private IStructuredContentProvider contentProvider;

    // the visual selection widget group
    CheckboxTableViewer listViewer;

    // sizing constants
    private final static int SIZING_SELECTION_WIDGET_HEIGHT = 250;

    private final static int SIZING_SELECTION_WIDGET_WIDTH = 300;

    private static final String INITIAL_FILTER_TEXT = "type filter text";

    private static final String SELECT_ALL = "&Select All";

    private static final String DESELECT_ALL = "&Deselect All";

    private TaskEntityFilter filterPattern;

    private Text entityFilterText;

    /**
     * Creates a list selection dialog.
     * 
     * @param parentShell
     *            the parent shell
     * @param input
     *            the root element to populate this dialog with
     * @param contentProvider
     *            the content provider for navigating the model
     * @param labelProvider
     *            the label provider for displaying model elements
     * @param message
     *            the message to be displayed at the top of this dialog, or
     *            <code>null</code> to display a default message
     */
    public FilteredListSelectionDialog(Shell parentShell, Object input,
            IStructuredContentProvider contentProvider,
            ILabelProvider labelProvider, String message) {
        super(parentShell);
        inputElement = input;
        this.contentProvider = contentProvider;
        this.labelProvider = labelProvider;
        if (message != null) {
            setMessage(message);
        }
    }

    /**
     * Add the selection and deselection buttons to the dialog.
     * 
     * @param composite
     *            org.eclipse.swt.widgets.Composite
     */
    private void addSelectionButtons(Composite composite) {
        Composite buttonComposite = new Composite(composite, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 0;
        layout.marginWidth = 0;
        layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
        buttonComposite.setLayout(layout);
        buttonComposite.setLayoutData(new GridData(SWT.END, SWT.TOP, true,
                false));

        Button selectButton = createButton(buttonComposite,
                IDialogConstants.SELECT_ALL_ID, SELECT_ALL, false);

        SelectionListener listener = new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                listViewer.setAllChecked(true);
                setAllCheckBoxChecked(true);

            }
        };
        selectButton.addSelectionListener(listener);

        Button deselectButton = createButton(buttonComposite,
                IDialogConstants.DESELECT_ALL_ID, DESELECT_ALL, false);

        listener = new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                listViewer.setAllChecked(false);
                setAllCheckBoxChecked(false);
            }
        };
        deselectButton.addSelectionListener(listener);
    }

    public void setAllCheckBoxChecked(boolean flag) {

        int size = listViewer.getTable().getItemCount();

        if (size > 0) {
            for (int i = 0; i < size; i++) {
                TaskFilter element = (TaskFilter) listViewer.getElementAt(i);
                element.setChecked(flag);
            }
        }

    }

    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        PlatformUI.getWorkbench().getHelpSystem()
                .setHelp(shell, "org.eclipse.ui.list_selection_dialog_context");
    }

    /*
     * (non-Javadoc) Method declared on Dialog.
     */
    protected Control createDialogArea(Composite parent) {
        // page group
        Composite composite = (Composite) super.createDialogArea(parent);

        initializeDialogUnits(composite);

        createMessageArea(composite);

        FormToolkit toolkit = new FormToolkit(parent.getDisplay());
        entityFilterText = toolkit.createText(composite, INITIAL_FILTER_TEXT);
        entityFilterText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        entityFilterText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent event) {
                filterPattern.setPattern(((Text) event.widget).getText());
                listViewer.refresh();
                Object[] array = contentProvider.getElements(inputElement);
                for (int i = 0; i < array.length; i++) {
                    TaskFilter element = (TaskFilter) array[i];
                    listViewer.setChecked(element,
                            ((TaskFilter) element).isChecked());
                }
            }
        });

        entityFilterText.addMouseListener(new MouseListener() {

            public void mouseUp(MouseEvent e) {
                entityFilterText.selectAll();
            }

            public void mouseDown(MouseEvent e) {

            }

            public void mouseDoubleClick(MouseEvent e) {
                entityFilterText.selectAll();
            }
        });

        listViewer = CheckboxTableViewer.newCheckList(composite, SWT.BORDER);
        GridData data = new GridData(GridData.FILL_BOTH);
        data.heightHint = SIZING_SELECTION_WIDGET_HEIGHT;
        data.widthHint = SIZING_SELECTION_WIDGET_WIDTH;
        listViewer.getTable().setLayoutData(data);

        listViewer.addCheckStateListener(new ICheckStateListener() {

            public void checkStateChanged(CheckStateChangedEvent event) {
                TaskFilter element = (TaskFilter) event.getElement();
                element.setChecked(event.getChecked());

            }
        });
        filterPattern = new TaskEntityFilter();

        listViewer.setLabelProvider(labelProvider);
        listViewer.setContentProvider(contentProvider);
        listViewer.addFilter(filterPattern);

        addSelectionButtons(composite);

        initializeViewer();

        Dialog.applyDialogFont(composite);

        return composite;
    }

    /**
     * Initializes this dialog's viewer after it has been laid out.
     */
    private void initializeViewer() {
        listViewer.setInput(inputElement);
        Object[] children = contentProvider.getElements(inputElement);
        if (children != null) {
            for (int i = 0; i < children.length; ++i) {
                TaskFilter element = (TaskFilter) children[i];
                listViewer.setChecked(element, element.isChecked());

            }
        }
    }

    /**
     * The <code>ListSelectionDialog</code> implementation of this
     * <code>Dialog</code> method builds a list of the selected elements for
     * later retrieval by the client and closes this dialog.
     */
    protected void okPressed() {

        // Get the input children.
        Object[] children = contentProvider.getElements(inputElement);

        // Build a list of selected children.
        if (children != null) {
            ArrayList<TaskFilter> list = new ArrayList<TaskFilter>();
            for (int i = 0; i < children.length; ++i) {
                TaskFilter element = (TaskFilter) children[i];
                if (element.isChecked()) {
                    list.add(element);
                }

            }
            setResult(list);
        }
        super.okPressed();
    }

    private class TaskEntityFilter extends ViewerFilter {
        private final SearchPattern patternMatcher;

        TaskEntityFilter() {
            patternMatcher = new SearchPattern();
            patternMatcher.setPattern("");
        }

        public void setPattern(String filter) {
            patternMatcher.setPattern(filter);
        }

        public boolean select(Viewer viewer, Object parent, Object element) {
            String name = "";
            if (element instanceof TaskFilter) {
                name = ((TaskFilter) element).toString();
            }
            return patternMatcher.matches(name);
        }
    }
}
