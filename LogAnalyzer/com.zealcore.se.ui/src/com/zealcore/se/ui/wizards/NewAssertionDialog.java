/**
 * 
 */
package com.zealcore.se.ui.wizards;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.zealcore.se.core.ifw.assertions.IAssertion;
import com.zealcore.se.ui.internal.assertions.IEditor;
import com.zealcore.se.ui.internal.assertions.IModifyListener;

class NewAssertionDialog extends Dialog {

    public static final String HELP_ID = "com.zealcore.se.ui.dialog_NewAssertion";

    private static final int MAX_LETTERS_IN_SEVERITY = 6;

    private static final int DESC_HEIGHT = 100;

    static final int NUM_COLUMNS = 2;

    private static final String DEFAULT_SEVERITY = "50";
    
    private static final int TEXT_HEIGHT = 20;

	private static final int MIN_ASSERTION_DIALOG_WIDTH = 450;

	private static final int MIN_ASSERTION_DIALOG_HEIGHT = 320;

	private static final int ASSERTION_DIALOG_WIDTH = 500;

	private static final int ASSERTION_DIALOG_HEIGHT = 460;

    private Text nameEdit;

    private Text severityEdit;

    private Text descriptionEdit;

    private String name;

    private int severity;

    private String description;

    private final IEditor editor;

    private IAssertion assertion;

    // Modified by the IEditor - if that is ok
    private boolean editorOk;

    // Modified by this editor (name,severity, desc edit)
    private boolean dialogOk;

    NewAssertionDialog(final Shell shell, final IEditor editor) {
        super(shell);        
        setShellStyle(SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MAX
                | SWT.APPLICATION_MODAL);
        this.editor = editor;

    }

    NewAssertionDialog(final Shell shell, final IAssertion assertion,
            final IEditor editor) {
        this(shell, editor);
        this.assertion = assertion;

    }

    @Override
    public Control createDialogArea(final Composite parent) {

        getShell().setText("Assertion");

        Composite composite = new Composite(parent, SWT.NONE);
        final GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
        composite.setLayoutData(gridData);
        final GridLayout gridLayout = new GridLayout();
        gridLayout.marginBottom = 0;
        gridLayout.marginTop = 0;
        gridLayout.marginLeft = 0;
        gridLayout.marginRight = 0;
        composite.setLayout(gridLayout);
        final ScrolledComposite scrolledComposite = new ScrolledComposite(
                composite, SWT.H_SCROLL | SWT.V_SCROLL);
        scrolledComposite.setLayout(gridLayout);
        scrolledComposite.setLayoutData(new GridData(GridData.FILL,
                GridData.FILL, true, true));
        scrolledComposite.setExpandHorizontal(true);
        scrolledComposite.setExpandVertical(true);
        scrolledComposite.setMinSize(MIN_ASSERTION_DIALOG_WIDTH, MIN_ASSERTION_DIALOG_HEIGHT);

        final Composite container = new Composite(scrolledComposite, SWT.NULL);
        if (PlatformUI.isWorkbenchRunning()) {
            PlatformUI.getWorkbench().getHelpSystem().setHelp(getShell(),
                    NewAssertionDialog.HELP_ID);
        }

        container.setLayout(new FillLayout(SWT.VERTICAL));
        container.setLayout(new GridLayout(NewAssertionDialog.NUM_COLUMNS,
                false));

        GridData data = new GridData();

        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.minimumHeight = NewAssertionDialog.TEXT_HEIGHT ;

        final Label lname = new Label(container, SWT.NULL);
        lname.setText(NewAssertionWizard.NAME);
        this.nameEdit = new Text(container, SWT.BORDER);
        this.nameEdit.setLayoutData(data);

        final Label lseverity = new Label(container, SWT.NULL);
        lseverity.setText(NewAssertionWizard.SEVERITY);
        this.severityEdit = new Text(container, SWT.BORDER);
        this.severityEdit.setText(NewAssertionDialog.DEFAULT_SEVERITY);

        this.severityEdit.setLayoutData(data);

        final Label ldescription = new Label(container, SWT.NULL);
        ldescription.setText(NewAssertionWizard.DESCRIPTION);
        this.descriptionEdit = new Text(container, SWT.BORDER | SWT.MULTI);
        data = new GridData();

        data.minimumHeight = NewAssertionDialog.DESC_HEIGHT;
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        this.descriptionEdit.setLayoutData(data);

        this.severityEdit.addVerifyListener(new VerifyListener() {

            public void verifyText(final VerifyEvent arg0) {
                final String result = arg0.text
                        + NewAssertionDialog.this.severityEdit.getText();
                arg0.doit = result.matches("[0-9]{1,}");
                if (result.length() > NewAssertionDialog.MAX_LETTERS_IN_SEVERITY) {
                    arg0.doit = false;
                }
            }

        });

        this.severityEdit.addModifyListener(new ModifyListener() {

            public void modifyText(final ModifyEvent e) {
                String text = NewAssertionDialog.this.severityEdit.getText();
                if (text.length() < 1) {
                    text = "0";
                }
                setSeverity(Integer.valueOf(text));
                checkValidResult();
            }
        });

        this.descriptionEdit.addModifyListener(new ModifyListener() {

            public void modifyText(final ModifyEvent e) {
                setDescription(descriptionEdit.getText());
                checkValidResult();
            }
        });

        nameEdit.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent arg0) {
                setName(nameEdit.getText());
                checkValidResult();
            }
        });

        if (this.assertion != null) {
            this.nameEdit.setText(this.assertion.getName());
            this.severityEdit.setText(String.valueOf(this.assertion
                    .getSeverity()));
            this.descriptionEdit.setText(this.assertion.getDescription());
        }
        createConfigArea(container);

        scrolledComposite.setContent(container);
        container.setSize(MIN_ASSERTION_DIALOG_WIDTH, MIN_ASSERTION_DIALOG_HEIGHT);
        return container;
    }

    @Override
    protected Control createContents(final Composite parent) {
        final Control control = super.createContents(parent);
        getShell().setSize(ASSERTION_DIALOG_WIDTH, ASSERTION_DIALOG_HEIGHT);
        getShell().layout(true);
        return control;
    }

    /**
     * Creates configuration area of the editor. This method may be overriden to
     * create specific content area
     * 
     * @param container
     *                the container
     */
    void createConfigArea(final Composite container) {

        final Composite composite = this.editor.createContents(container);

        final GridData data = new GridData();
        data.horizontalSpan = NewAssertionDialog.NUM_COLUMNS;
        data.grabExcessVerticalSpace = true;
        data.widthHint = SWT.DEFAULT;
        data.horizontalAlignment = SWT.CENTER;

        composite.setLayoutData(data);
        
        container.setSize(MIN_ASSERTION_DIALOG_WIDTH, MIN_ASSERTION_DIALOG_HEIGHT);
        container.layout(true);
    }

    @Override
    protected Control createButtonBar(final Composite parent) {
        final Control control = super.createButtonBar(parent);
        getButton(IDialogConstants.OK_ID).setEnabled(false);

        final IModifyListener listner = new IModifyListener() {

            public void componentModified(

            final com.zealcore.se.ui.internal.assertions.ModifyEvent event) {
                NewAssertionDialog.this.editorOk = event.getErrorMessage() == null;
                NewAssertionDialog.this.checkValidResult();
            }
        };

        this.editor.addModifyListener(listner);
        parent.addDisposeListener(new DisposeListener() {
            public void widgetDisposed(final DisposeEvent e) {
                NewAssertionDialog.this.editor.removeModifyListener(listner);
            }

        });
        parent.layout(true);
        return control;
    }

    private void checkValidResult() {

        final Button button = getButton(IDialogConstants.OK_ID);
        if (button == null) {
            return;
        }

        this.dialogOk = true;
        if (this.severityEdit.getText() == null
                || this.nameEdit.getText() == null
                || this.descriptionEdit.getText() == null) {

            this.dialogOk = false;

        }
        if (this.severityEdit.getText().trim().length() < 1
                || this.nameEdit.getText().trim().length() < 1
                || this.descriptionEdit.getText() == null) {
            // || descriptionEdit.getText().length() < 1) {

            this.dialogOk = false;
        }

        button.setEnabled(this.dialogOk && this.editorOk);

    }

    /**
     * @param name
     *                the name to set
     */
    void setName(final String name) {
        this.name = name == null ? "" : name.trim();
    }

    /**
     * @return the name
     */
    String getName() {
        return this.name;
    }

    /**
     * @param i
     *                the severity to set
     */
    void setSeverity(final int i) {
        this.severity = i;
    }

    /**
     * @return the severity
     */
    int getSeverity() {
        return this.severity;
    }

    /**
     * @param description
     *                the description to set
     */
    void setDescription(final String description) {
        this.description = description == null ? "" : description.trim();
    }

    /**
     * @return the description
     */
    String getDescription() {
        return this.description;
    }
}
