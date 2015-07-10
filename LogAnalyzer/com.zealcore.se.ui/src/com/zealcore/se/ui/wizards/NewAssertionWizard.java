/*
 * 
 */
package com.zealcore.se.ui.wizards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.SearchAdapter;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.dl.TypeRegistry;
import com.zealcore.se.core.ifw.assertions.Assertion;
import com.zealcore.se.core.ifw.assertions.AssertionRegistry;
import com.zealcore.se.core.ifw.assertions.AssertionSet;
import com.zealcore.se.core.ifw.assertions.IAssertion;
import com.zealcore.se.core.ifw.assertions.IAssertionRegistry;
import com.zealcore.se.core.ifw.assertions.IAssertionSet;
import com.zealcore.se.core.services.IMementoService;
import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.internal.assertions.EditableAssertionAdapter;
import com.zealcore.se.ui.internal.assertions.IEditor;
import com.zealcore.se.ui.search.SearchFilterInput;

public class NewAssertionWizard extends Wizard implements INewWizard {

    public static final String HELP_ID = "com.zealcore.se.ui.wizard_NewAssertion";

    private static final String CREATE_NEW_ASSERTION = "Create New Assertion";

    private static final String ASSERTIONS = "Assertions";

    static final String NAME = "Name:";

    static final String DESCRIPTION = "Description:";

    static final String SEVERITY = "Severity:";

    private final IPath path;

    public NewAssertionWizard(final IPath path) {
        this.path = path;
        setDefaultPageImageDescriptor(IconManager
                .getImageDescriptor(IconManager.FIND_BUGS));
    }

    @Override
    public void createPageControls(final Composite pageContainer) {
        super.createPageControls(pageContainer);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(pageContainer,
                NewAssertionWizard.HELP_ID);
        setHelpAvailable(true);
        AssertionSetViewPage assertionsViewPage = new AssertionSetViewPage(
                "windowTitle", path);
        assertionsViewPage.createControl(pageContainer);
    }

    @Override
    public boolean performFinish() {
        return true;
    }

    public void init(final IWorkbench workbench,
            final IStructuredSelection selection) {}

    @Override
    public void addPages() {
        super.addPages();

        setWindowTitle(NewAssertionWizard.CREATE_NEW_ASSERTION);
        addPage(new AssertionSetViewPage(NewAssertionWizard.ASSERTIONS, path));
    }

    private static class AssertionSetViewPage extends WizardPage {

        private static final String EDIT = "Edit...";

        private static final String NEW = "New...";

        private static final String DELETE = "Delete";

        private static final int LIST_HEIGHT = 200;

        private static final int LIST_WIDTH = 400;

        private final IAssertionRegistry registry;

        private IAssertionSet currentSet;

        private StructuredViewer assertionSetsViewer;

        private ListViewer assertionListViewer;

        private Button newAssertion;

        private Button removeAssertionSetBtn;

        private Button newAssertionSet;

        private Button removeAssertion;

        private Button editAssertion;

        private IAssertion currentAssertion;

        // private Button newAdvancedAssertion;

        protected AssertionSetViewPage(final String pageName, final IPath path) {
            super(pageName);
            IMementoService mem = SeCorePlugin.getDefault().getService(
                    IMementoService.class);
            registry = new AssertionRegistry(mem, path, TypeRegistry
                    .getInstance());
            setImageDescriptor(IconManager
                    .getImageDescriptor(IconManager.FIND_BUGS));
            setDescription(NewAssertionWizard.CREATE_NEW_ASSERTION);
            setTitle(NewAssertionWizard.ASSERTIONS);
        }

        private void setCurrentAssertionSet(final IAssertionSet currentSet) {
            if (this.currentSet == currentSet) {
                return;
            }
            this.currentSet = currentSet;
            assertionListViewer.setInput(getCurrentSet());

            if (getCurrentSet() == null) {
                newAssertion.setEnabled(false);
                // this.newAdvancedAssertion.setEnabled(false);
            } else {
                assertionSetsViewer.setSelection(new StructuredSelection(
                        currentSet));
                newAssertion.setEnabled(true);
                // this.newAdvancedAssertion.setEnabled(true);
            }
        }

        public IAssertionSet getCurrentSet() {
            return currentSet;
        }

        private void setCurrentAssertion(final IAssertion assertion) {
            currentAssertion = assertion;
            editAssertion.setEnabled(true);
        }

        private IAssertion getCurrentAssertion() {
            return currentAssertion;
        }

        /**
         * {@inheritDoc}
         */
        public void createControl(final Composite container) {
            GridLayout gridLayout = new GridLayout();
            gridLayout.numColumns = 4;
            gridLayout.verticalSpacing = 5;
            container.setLayout(gridLayout);
            setControl(container);

            Label assertionSetLabel = new Label(container, SWT.LEFT);
            GridData gridData = new GridData(GridData.FILL, GridData.BEGINNING,
                    true, false);
            gridData.horizontalIndent = 5;
            gridData.horizontalSpan = 4;
            assertionSetLabel.setLayoutData(gridData);

            assertionSetsViewer = new ComboViewer(container);
            gridData = new GridData(GridData.FILL, GridData.BEGINNING, true,
                    false);
            gridData.horizontalIndent = 5;
            gridData.horizontalSpan = 3;
            gridData.verticalSpan = 2;
            gridData.widthHint = LIST_WIDTH;
            assertionSetsViewer.getControl().setLayoutData(gridData);

            newAssertionSet = new Button(container, SWT.PUSH);
            newAssertionSet.setText(NEW);
            gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
            newAssertionSet.setLayoutData(gridData);

            removeAssertionSetBtn = new Button(container, SWT.PUSH);
            removeAssertionSetBtn.setText(DELETE);
            gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
            removeAssertionSetBtn.setLayoutData(gridData);

            Label assertionlabel = new Label(container, SWT.LEFT);
            gridData = new GridData(GridData.FILL, GridData.BEGINNING, true,
                    false);
            gridData.horizontalIndent = 5;
            gridData.horizontalSpan = 4;
            assertionlabel.setLayoutData(gridData);

            assertionListViewer = new ListViewer(container);
            assertionSetLabel.setText("Assertion Set");
            assertionlabel.setText("Assertions in selected set");
            assertionSetsViewer.setLabelProvider(new LabelProvider());
            gridData = new GridData(GridData.FILL, GridData.BEGINNING, true,
                    false);
            gridData.horizontalIndent = 5;
            gridData.horizontalSpan = 3;
            gridData.verticalSpan = 4;
            gridData.heightHint = LIST_HEIGHT;
            gridData.widthHint = LIST_WIDTH;
            assertionListViewer.getList().setLayoutData(gridData);

            assertionSetsViewer.setContentProvider(new ArrayContentProvider() {
                @Override
                public Object[] getElements(final Object inputElement) {
                    final ArrayList<IAssertionSet> temp = new ArrayList<IAssertionSet>();
                    for (final IAssertionSet set : registry.getAssertionSets()) {
                        temp.add(set);
                    }
                    return temp.toArray();
                }
            }

            );
            assertionSetsViewer.setInput(registry);

            assertionListViewer.setContentProvider(new ArrayContentProvider() {
                @Override
                public Object[] getElements(final Object inputElement) {
                    if (inputElement instanceof IAssertionSet) {
                        final IAssertionSet set = (IAssertionSet) inputElement;
                        final ArrayList<IAssertion> assertions = new ArrayList<IAssertion>();
                        for (final IAssertion assertion : set.getAssertions()) {
                            assertions.add(assertion);

                        }
                        return assertions.toArray();
                    }
                    return Collections.EMPTY_LIST.toArray();
                }
            });

            assertionListViewer.setLabelProvider(new LabelProvider());

            newAssertion = new Button(container, SWT.PUSH);
            newAssertion.setText(NEW);
            gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
            newAssertion.setLayoutData(gridData);

            // this.newAdvancedAssertion = new Button(rightButtonBar, SWT.PUSH);
            // this.newAdvancedAssertion.setText("New Script Assertion...");
            // TODO This is no longer created such that no advanced assertions
            // can be created

            removeAssertion = new Button(container, SWT.PUSH);
            removeAssertion.setText(AssertionSetViewPage.DELETE);
            gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
            removeAssertion.setLayoutData(gridData);

            editAssertion = new Button(container, SWT.PUSH);
            editAssertion.setText(EDIT);
            editAssertion.setEnabled(false);
            makeActions();
            gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
            editAssertion.setLayoutData(gridData);

            // Set initial selection (to the first set available - if there is
            // one
            final Iterator<IAssertionSet> setsIter = registry
                    .getAssertionSets().iterator();

            if (setsIter.hasNext()) {
                setCurrentAssertionSet(setsIter.next());
            }

            Listener listener = new Listener() {
                public void handleEvent(final Event event) {
                    container.getShell().close();
                }
            };
            new Label(container, SWT.LEFT);
            new Label(container, SWT.LEFT);
            new Label(container, SWT.LEFT);
            new Label(container, SWT.LEFT);

            Button close = new Button(container, SWT.PUSH);
            close.setText("      Close      ");
            gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
            close.setLayoutData(gridData);
            close.addListener(SWT.Selection, listener);
            container.pack(true);
        }

        /**
         * 
         * Creates the mediator actions between UI elements.
         */
        private void makeActions() {
            newAssertion
                    .addSelectionListener(createNewAssertionSelectionlistener());
            newAssertion.setEnabled(false);
            // this.newAdvancedAssertion
            // .addSelectionListener(createNewAdvancedAssertionSelectionlistener());
            // this.newAdvancedAssertion.setEnabled(false);
            newAssertionSet
                    .addSelectionListener(createNewAssertionSetSelectionListener());
            assertionSetsViewer
                    .addSelectionChangedListener(createAssertionSetsViewerSelectionChangedListener());
            assertionListViewer
                    .addSelectionChangedListener(createAssertionListViewerSelectionChangedListener());
            removeAssertion
                    .addSelectionListener(createRemoveAssertionSelectionListener());
            removeAssertionSetBtn
                    .addSelectionListener(createRemoveAssertionSetBtnSelectionListener());
            editAssertion
                    .addSelectionListener(createEditAssertionSelectionListener());
        }

        private SelectionListener createEditAssertionSelectionListener() {
            return new SelectionAdapter() {
                @Override
                public void widgetSelected(final SelectionEvent arg0) {
                    super.widgetSelected(arg0);
                    openEditable();

                }

                private void openEditable() {
                    final Object adp = (new EditableAssertionAdapter(
                            SeCorePlugin.getDefault().getService(
                                    ITypeRegistry.class), registry))
                            .getAdapter(AssertionSetViewPage.this
                                    .getCurrentAssertion(), IEditor.class);
                    if (adp instanceof IEditor) {
                        final IEditor editor = (IEditor) adp;
                        final NewAssertionDialog dialog = new NewAssertionDialog(
                                AssertionSetViewPage.this.getShell(),
                                AssertionSetViewPage.this.getCurrentAssertion(),
                                editor);

                        if (Window.OK == dialog.open()) {
                            AssertionSetViewPage.this.getCurrentAssertion()
                                    .setName(dialog.getName());
                            AssertionSetViewPage.this.getCurrentAssertion()
                                    .setSeverity(dialog.getSeverity());
                            AssertionSetViewPage.this.getCurrentAssertion()
                                    .setDescription(dialog.getDescription());

                            editor.saveChanges();
                            assertionListViewer
                                    .refresh(AssertionSetViewPage.this
                                            .getCurrentAssertion());
                        }

                    }
                }

            };
        }

        private SelectionListener createRemoveAssertionSetBtnSelectionListener() {
            return new SelectionAdapter() {
                @Override
                public void widgetSelected(final SelectionEvent arg0) {
                    if (AssertionSetViewPage.this.getCurrentSet() != null) {
                        if (MessageDialog.openQuestion(
                                AssertionSetViewPage.this.getShell(),
                                AssertionSetViewPage.DELETE,
                                "Are you sure you wan't to delete the Assertion Set "
                                        + AssertionSetViewPage.this
                                                .getCurrentSet().getName()
                                        + "?")) {

                            registry
                                    .removeAssertionSet(AssertionSetViewPage.this
                                            .getCurrentSet());
                            assertionSetsViewer.refresh(true);
                        }
                    }
                }
            };
        }

        private SelectionListener createRemoveAssertionSelectionListener() {
            return new SelectionAdapter() {
                @Override
                public void widgetSelected(final SelectionEvent arg0) {
                    final IStructuredSelection selection = (IStructuredSelection) assertionListViewer
                            .getSelection();
                    final Object firstElement = selection.getFirstElement();
                    if (firstElement instanceof IAssertion) {

                        final IAssertion assertion = (IAssertion) firstElement;
                        if (MessageDialog.openQuestion(
                                AssertionSetViewPage.this.getShell(),
                                AssertionSetViewPage.DELETE,
                                "Are you sure you want to delete the Assertion "
                                        + assertion.getName() + '?')) {
                            AssertionSetViewPage.this.getCurrentSet()
                                    .removeAssertion(assertion);

                            assertionListViewer.refresh(true);
                        }

                    }
                }
            };
        }

        private ISelectionChangedListener createAssertionListViewerSelectionChangedListener() {
            return new ISelectionChangedListener() {
                public void selectionChanged(final SelectionChangedEvent event) {
                    final IStructuredSelection struct = (IStructuredSelection) event
                            .getSelection();
                    if (struct.getFirstElement() instanceof IAssertion) {
                        final IAssertion assertion = (IAssertion) struct
                                .getFirstElement();
                        AssertionSetViewPage.this
                                .setCurrentAssertion(assertion);
                    }
                }
            };
        }

        private ISelectionChangedListener createAssertionSetsViewerSelectionChangedListener() {
            return new ISelectionChangedListener() {
                public void selectionChanged(final SelectionChangedEvent event) {
                    // Disable the edit button, otherwise it will be enabled for
                    // a selected assertion in another Assertion set
                    editAssertion.setEnabled(false);
                    final IStructuredSelection selection = (IStructuredSelection) assertionSetsViewer
                            .getSelection();
                    if (selection.isEmpty()) {
                        AssertionSetViewPage.this.setCurrentAssertionSet(null);
                    }

                    AssertionSetViewPage.this
                            .setCurrentAssertionSet((IAssertionSet) selection
                                    .getFirstElement());
                }
            };
        }

        private SelectionListener createNewAssertionSetSelectionListener() {
            return new SelectionAdapter() {
                @Override
                public void widgetSelected(final SelectionEvent arg0) {
                    super.widgetSelected(arg0);
                    final NewAssertionSetDialog dialog = new NewAssertionSetDialog(
                            AssertionSetViewPage.this.getShell());

                    if (dialog.open() == Window.OK) {
                        final IAssertionSet set = AssertionSet.valueOf(
                                dialog.name, dialog.description);
                        registry.addAssertionSet(set);

                        assertionSetsViewer.refresh(true);
                        AssertionSetViewPage.this.setCurrentAssertionSet(set);
                    }
                }
            };
        }

        private SelectionListener createNewAssertionSelectionlistener() {
            return new SelectionAdapter() {
                @Override
                public void widgetSelected(final SelectionEvent arg0) {
                    super.widgetSelected(arg0);

                    final SearchFilterInput editor = new SearchFilterInput(
                            SearchFilterInput.SEARCHABLE_TYPES);
                    final NewAssertionDialog dialog = new NewAssertionDialog(
                            AssertionSetViewPage.this.getShell(), editor);

                    if (dialog.open() == Window.OK) {
                        final IAssertion assertion = Assertion.valueOf(dialog
                                .getName(), dialog.getDescription(), Integer
                                .valueOf(dialog.getSeverity()),
                                (SearchAdapter) editor.createAdapter());
                        AssertionSetViewPage.this.getCurrentSet().addAssertion(
                                assertion);
                        assertionListViewer.refresh(true);
                    }
                }
            };
        }

        // private SelectionListener
        // createNewAdvancedAssertionSelectionlistener() {
        // return new SelectionAdapter() {
        // @Override
        // public void widgetSelected(final SelectionEvent arg0) {
        // super.widgetSelected(arg0);
        //
        // // FIXME Use service provider to get IDataConnection
        //
        // final NewAdvancedAssertionDialog dialog = new
        // NewAdvancedAssertionDialog(
        // AssertionSetViewPage.this.getShell());
        // if (dialog.open() == Window.OK) {
        // final IAssertion assertion = dialog.getAssertion();
        // AssertionSetViewPage.this.getCurrentSet().addAssertion(
        // assertion);
        // AssertionSetViewPage.this.assertionListViewer
        // .refresh(true);
        // }
        // }
        // };
        // }

        /**
         * The Class NewAssertionSetDialog.
         */
        private static class NewAssertionSetDialog extends Dialog {

            public static final String HELP_ID = "com.zealcore.se.ui.dialog_NewAssertionSet";

            private static final int INPUT_WIDTH = 280;

            private static final int TWO_COLUMNS = 2;

            private Text nameEdit;

            private Text descriptionEdit;

            private String name = "";

            private String description = "";

            protected NewAssertionSetDialog(final Shell parentShell) {
                super(parentShell);
            }

            @Override
            public Control createDialogArea(final Composite parent) {

                getShell().setText("New Assertion Set");
                final Composite container = new Composite(parent, SWT.NULL);
                container.setLayout(new GridLayout(
                        NewAssertionSetDialog.TWO_COLUMNS, false));
                PlatformUI.getWorkbench().getHelpSystem().setHelp(getShell(),
                        NewAssertionSetDialog.HELP_ID);

                final Label lname = new Label(container, SWT.NULL);
                lname.setText(NewAssertionWizard.NAME);

                nameEdit = new Text(container, SWT.BORDER);
                GridData data = new GridData();
                data.widthHint = NewAssertionSetDialog.INPUT_WIDTH;
                nameEdit.setLayoutData(data);

                final Label ldescription = new Label(container, SWT.NULL);
                ldescription.setText(NewAssertionWizard.DESCRIPTION);
                descriptionEdit = new Text(container, SWT.BORDER | SWT.MULTI);

                data = new GridData();
                data.widthHint = NewAssertionSetDialog.INPUT_WIDTH;
                data.heightHint = NewAssertionSetDialog.INPUT_WIDTH / (1 + 1);
                descriptionEdit.setLayoutData(data);
                nameEdit.addModifyListener(new ModifyListener() {

                    public void modifyText(final ModifyEvent e) {
                        name = nameEdit.getText();
                        NewAssertionSetDialog.this.checkValidResult();
                    }
                });
                descriptionEdit.addModifyListener(new ModifyListener() {

                    public void modifyText(final ModifyEvent e) {
                        description = descriptionEdit.getText();
                        NewAssertionSetDialog.this.checkValidResult();
                    }
                });

                return container;
            }

            @Override
            protected Control createButtonBar(final Composite parent) {
                final Control control = super.createButtonBar(parent);
                getButton(IDialogConstants.OK_ID).setEnabled(false);
                return control;
            }

            private void checkValidResult() {
                final Button button = getButton(IDialogConstants.OK_ID);
                if (button == null) {
                    return;
                }
                if (nameEdit.getText().length() < 1) {
                    button.setEnabled(false);
                    return;
                }
                button.setEnabled(true);
            }
        }
    }
}
