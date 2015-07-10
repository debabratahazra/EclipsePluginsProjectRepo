package com.zealcore.se.ui.dialogs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.zealcore.se.ui.util.StringMatcher;

@SuppressWarnings("unchecked")
public class SelectOrderDialog<T> extends Dialog {

    private static final LabelProvider LABEL_PROVIDER = new LabelProvider();

    private final List<T> elements = new ArrayList<T>();

    private final List<T> selection = new ArrayList<T>();

    private ListViewer listParticipants;

    private ListViewer orderParticipants;

    private Button addButton;

    private Button top;

    private Button up;

    private Button down;

    private Button bottom;

    private Text filterText;

    private Button remove;

    private Button removeAll;

    private Button addAllButton;

    protected SelectOrderDialog(final Shell parentShell) {
        super(parentShell);
        setShellStyle(getShellStyle() | SWT.RESIZE);

    }

    public static <E> SelectOrderDialog<E> newInstance(final Shell parentShell) {
        return new SelectOrderDialog<E>(parentShell);
    }

    @Override
    protected Control createDialogArea(final Composite parent) {

        final Composite composite = (Composite) super.createDialogArea(parent);

        composite.setLayout(new FormLayout());
        Composite buttons;

        final Group selectGroup = new Group(composite, SWT.NONE);
        selectGroup.setText("Select");
        final FormData fdselectGroup = new FormData();
        fdselectGroup.right = new FormAttachment(100, -20);
        fdselectGroup.top = new FormAttachment(0, 10);
        fdselectGroup.left = new FormAttachment(0, 20);
        fdselectGroup.bottom = new FormAttachment(filterText, 206, SWT.TOP);
        selectGroup.setLayoutData(fdselectGroup);
        selectGroup.setLayout(new FormLayout());
        filterText = new Text(selectGroup, SWT.BORDER);
        final FormData fdfilterText = new FormData();
        fdfilterText.right = new FormAttachment(100, -85);
        fdfilterText.top = new FormAttachment(0, 5);
        filterText.setLayoutData(fdfilterText);
        filterText
                .addModifyListener(createFilterTextModifyListener(filterText));

        listParticipants = new ListViewer(selectGroup, SWT.V_SCROLL
                | SWT.BORDER);
        fdfilterText.left = new FormAttachment(listParticipants.getList(), 0,
                SWT.LEFT);
        final FormData fdlist = new FormData();
        fdlist.right = new FormAttachment(100, -85);
        fdlist.top = new FormAttachment(0, 35);
        fdlist.left = new FormAttachment(0, 35);
        fdlist.bottom = new FormAttachment(0, 175);
        listParticipants.getList().setLayoutData(fdlist);

        final Label filterLabel = new Label(selectGroup, SWT.RIGHT);
        final FormData fdfilterLabel = new FormData();
        fdfilterLabel.right = new FormAttachment(filterText, -5, SWT.LEFT);
        fdfilterLabel.bottom = new FormAttachment(filterText, 13, SWT.TOP);
        fdfilterLabel.top = new FormAttachment(filterText, 0, SWT.TOP);
        fdfilterLabel.left = new FormAttachment(0, 0);
        filterLabel.setLayoutData(fdfilterLabel);
        filterLabel.setText("Filter");

        final Composite topButtonGroup = new Composite(selectGroup, SWT.NONE);
        final RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
        rowLayout.fill = true;
        topButtonGroup.setLayout(rowLayout);
        final FormData fdaddButton = new FormData();
        fdaddButton.top = new FormAttachment(listParticipants.getList(), 0,
                SWT.TOP);
        fdaddButton.right = new FormAttachment(listParticipants.getList(), 77,
                SWT.RIGHT);
        fdaddButton.left = new FormAttachment(listParticipants.getList(), 5,
                SWT.RIGHT);
        topButtonGroup.setLayoutData(fdaddButton);

        addButton = new Button(topButtonGroup, SWT.NONE);
        addButton.setText("Add");

        addAllButton = new Button(topButtonGroup, SWT.NONE);
        addAllButton.setText("Add All");
        
        Button aremoveAll = new Button(topButtonGroup, SWT.NULL);
        aremoveAll.setText("Remove Ali");
        aremoveAll.setVisible(false);


        final Group orderGroup = new Group(composite, SWT.NONE);
        orderGroup.setText("Order");
        final FormData fdorderGroup = new FormData();
        fdorderGroup.left = new FormAttachment(0, 20);
        fdorderGroup.right = new FormAttachment(100, -20);
        fdorderGroup.bottom = new FormAttachment(100, -5);
        fdorderGroup.top = new FormAttachment(0, 220);
        orderGroup.setLayoutData(fdorderGroup);
        orderGroup.setLayout(new FormLayout());

        orderParticipants = new ListViewer(orderGroup, SWT.V_SCROLL
                | SWT.BORDER);
        final FormData fdlist1 = new FormData();
        fdlist1.top = new FormAttachment(0, 5);
        fdlist1.bottom = new FormAttachment(100, -5);
        fdlist1.left = new FormAttachment(0, 35);
        orderParticipants.getList().setLayoutData(fdlist1);
        buttons = new Composite(orderGroup, SWT.NULL);
        fdlist1.right = new FormAttachment(buttons, -6, SWT.LEFT);
        final FormData fdbuttons = new FormData();
        fdbuttons.right = new FormAttachment(100, -8);
        fdbuttons.top = new FormAttachment(orderParticipants.getList(), 0,
                SWT.TOP);
        buttons.setLayoutData(fdbuttons);
        buttons.setLayout(rowLayout);
        top = new Button(buttons, SWT.NULL);
        top.setText("Top");
        up = new Button(buttons, SWT.NULL);
        up.setText("Up");

        down = new Button(buttons, SWT.NULL);
        down.setText("Down");

        bottom = new Button(buttons, SWT.NULL);
        bottom.setText("Bottom");

        remove = new Button(buttons, SWT.NULL);
        remove.setText("Remove");

        removeAll = new Button(buttons, SWT.NULL);
        removeAll.setText("Remove All");

        top.addSelectionListener(createTopSelectionListener());
        up.addSelectionListener(createUpSelectionListener());
        down.addSelectionListener(createDownSelectionListener());
        bottom.addSelectionListener(createBottomSelectionListener());

        orderParticipants.setLabelProvider(SelectOrderDialog.LABEL_PROVIDER);
        orderParticipants.setContentProvider(new ArrayContentProvider());

        listParticipants.setLabelProvider(SelectOrderDialog.LABEL_PROVIDER);
        listParticipants.setContentProvider(new ArrayContentProvider());

        orderParticipants.setInput(selection);
        listParticipants.setInput(elements);

        addButton.setEnabled(false);
        disableOrderButtons();

        makeActions();
        return composite;
    }

    private void disableOrderButtons() {
        top.setEnabled(false);
        bottom.setEnabled(false);
        up.setEnabled(false);
        down.setEnabled(false);
        remove.setEnabled(false);
    }

    private void addToOrder() {
        final Object element = ((IStructuredSelection) listParticipants
                .getSelection()).getFirstElement();
        if (!selection.contains(element)) {
            selection.add((T) element);
            orderParticipants.refresh();
        }
    }

    private void addAllToOrder() {
        selection.clear();
        selection.addAll(elements);
        orderParticipants.refresh();
    }

    private void makeActions() {
        addButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                addToOrder();
            }

        });

        addAllButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                addAllToOrder();
            }
        });

        remove.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                selection.remove(((IStructuredSelection) orderParticipants
                        .getSelection()).getFirstElement());
                disableOrderButtons();
                orderParticipants.refresh();
            }
        });

        listParticipants
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(
                            final SelectionChangedEvent arg0) {
                        final Object element = ((IStructuredSelection) listParticipants
                                .getSelection()).getFirstElement();
                        addButton.setEnabled(element != null);
                    }
                });

        listParticipants.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(final DoubleClickEvent arg0) {
                addToOrder();
            }
        });

        orderParticipants
                .addSelectionChangedListener(new ISelectionChangedListener() {

                    public void selectionChanged(
                            final SelectionChangedEvent arg0) {
                        final Object element = ((IStructuredSelection) orderParticipants
                                .getSelection()).getFirstElement();
                        top.setEnabled(element != null);
                        bottom.setEnabled(element != null);
                        up.setEnabled(element != null);
                        down.setEnabled(element != null);
                        remove.setEnabled(element != null);
                    }

                });

        removeAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                selection.clear();
                disableOrderButtons();
                orderParticipants.refresh();
            }
        });
    }

    private ModifyListener createFilterTextModifyListener(final Text filterText) {
        return new ModifyListener() {
            public void modifyText(final ModifyEvent arg0) {
                listParticipants.getList().setRedraw(false);

                listParticipants.resetFilters();
                listParticipants.addFilter(new ViewerFilter() {
                    @Override
                    public boolean select(final Viewer pViewer,
                            final Object parentElement, final Object element) {
                        final String match = filterText.getText();
                        if (match != null && match.length() > 0) {

                            final StringMatcher matcher = new StringMatcher(
                                    match + "*", true, false);

                            final String objectText = ((ILabelProvider) listParticipants
                                    .getLabelProvider()).getText(element);

                            return matcher.match(objectText, 0, objectText
                                    .length());
                        }
                        return true;
                    }
                });
                listParticipants.getList().setRedraw(true);
            }
        };
    }

    @Override
    protected void okPressed() {
        super.okPressed();
    }

    private SelectionListener createBottomSelectionListener() {
        return new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                final Object element = ((IStructuredSelection) orderParticipants
                        .getSelection()).getFirstElement();
                selection.remove(element);
                selection.add((T) element);
                orderParticipants.refresh();
            }
        };
    }

    private SelectionListener createDownSelectionListener() {
        return new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                final Object element = ((IStructuredSelection) orderParticipants
                        .getSelection()).getFirstElement();
                final int i = selection.indexOf(element);
                Collections.swap(selection, i, Math.min(selection.size() - 1,
                        i + 1));
                orderParticipants.refresh();
            }
        };
    }

    private SelectionListener createUpSelectionListener() {
        return new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                final Object element = ((IStructuredSelection) orderParticipants
                        .getSelection()).getFirstElement();
                final int i = selection.indexOf(element);
                Collections.swap(selection, i, Math.max(0, i - 1));
                orderParticipants.refresh();
            }
        };
    }

    private SelectionListener createTopSelectionListener() {
        return new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent arg0) {
                final Object element = ((IStructuredSelection) orderParticipants
                        .getSelection()).getFirstElement();
                selection.remove(element);
                selection.add(0, (T) element);
                orderParticipants.refresh();
            }
        };
    }

    public void setInitialElements(final Collection<T> elements) {
        this.elements.addAll(elements);
    }

    public void setInitialSelection(final Collection<T> selection) {
        this.elements.removeAll(selection);
        this.elements.addAll(0, selection);
        this.selection.addAll(selection);

    }

    public List<T> getSelectedItems() {
        return new ArrayList<T>(this.selection);
    }

    @Override
    protected Point getInitialSize() {
        return new Point(518, 503);
    }

    @Override
    protected void configureShell(final Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Configure Order");
    }
}
