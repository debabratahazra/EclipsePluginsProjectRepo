package com.zealcore.se.iw.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

class AddTypePage extends WizardPage {

    private static final String STR_UPDATE = "Update";

    private static final String STR_REMOVE = "Remove";

    private static final String STR_NAME = "Name:";

    private static final String STR_ADD = "Add";

    private Text txtFilterRegexp;

    private Text txtFieldDelimiter;

    private Text txtPropertyValue;

    private Text txtPropertyName;

    private Text txtFieldType;

    private Text txtFieldName;

    private Table tblFields;

    private List listFilters;

    private Composite container;

    private Table tblProperties;

    private StyledText txtPreview;

    /**
     * Create the wizard
     */
    public AddTypePage() {
        super("wizardPage");
        setTitle("Generic Import Wizard");
        setDescription("Use this wizard to define a new importer");
    }

    /**
     * Create contents of the wizard
     * 
     * @param parent
     */
    public void createControl(final Composite parent) {
        this.container = new Composite(parent, SWT.NULL);
        this.container.setLayout(new FormLayout());
        setControl(this.container);

        final Composite composite2 = new Composite(this.container, SWT.NONE);
        final FormData fdcomposite2 = new FormData();
        fdcomposite2.bottom = new FormAttachment(100, -5);
        fdcomposite2.right = new FormAttachment(100, 0);
        fdcomposite2.top = new FormAttachment(0, 0);
        fdcomposite2.left = new FormAttachment(0, 0);
        composite2.setLayoutData(fdcomposite2);
        composite2.setLayout(new FormLayout());

        final Composite compositeConfig = new Composite(composite2, SWT.NONE);
        final FormData fdcompositeConfig = new FormData();
        fdcompositeConfig.right = new FormAttachment(0, 480);
        fdcompositeConfig.bottom = new FormAttachment(100, 0);
        fdcompositeConfig.top = new FormAttachment(0, 0);
        fdcompositeConfig.left = new FormAttachment(0, 0);
        compositeConfig.setLayoutData(fdcompositeConfig);
        compositeConfig.setLayout(new FormLayout());

        final CTabFolder tabFolder = new CTabFolder(compositeConfig, SWT.NONE);
        tabFolder.setSimple(false);
        final FormData fdtabFolder = new FormData();
        fdtabFolder.bottom = new FormAttachment(100, -9);
        fdtabFolder.right = new FormAttachment(100, 0);
        fdtabFolder.top = new FormAttachment(0, 0);
        fdtabFolder.left = new FormAttachment(0, 0);
        tabFolder.setLayoutData(fdtabFolder);

        final CTabItem tabItemProperties = new CTabItem(tabFolder, SWT.NONE);
        tabItemProperties.setText("Properties");

        final CTabItem tabItemFields = new CTabItem(tabFolder, SWT.NONE);
        tabItemFields.setText("Fields");

        final Composite comFields = new Composite(tabFolder, SWT.NONE);
        comFields.setLayout(new FormLayout());
        tabItemFields.setControl(comFields);

        Composite comFieldEdit;

        this.tblFields = new Table(comFields, SWT.BORDER);
        final FormData fdtblFields = new FormData();
        fdtblFields.right = new FormAttachment(100, -5);
        fdtblFields.left = new FormAttachment(0, 5);
        fdtblFields.top = new FormAttachment(0, 5);
        this.tblFields.setLayoutData(fdtblFields);
        this.tblFields.setLinesVisible(true);
        this.tblFields.setHeaderVisible(true);

        final TableColumn colFieldName = new TableColumn(this.tblFields,
                SWT.NONE);
        colFieldName.setWidth(222);
        colFieldName.setText(AddTypePage.STR_NAME);

        final TableColumn colFieldType = new TableColumn(this.tblFields,
                SWT.NONE);
        colFieldType.setWidth(100);
        colFieldType.setText("Type");
        comFieldEdit = new Composite(comFields, SWT.NONE);
        fdtblFields.bottom = new FormAttachment(comFieldEdit, -5, SWT.TOP);

        final TableColumn colFieldDelimiter = new TableColumn(this.tblFields,
                SWT.NONE);
        colFieldDelimiter.setWidth(100);
        colFieldDelimiter.setText("Delimiter");
        final FormData fdcomFieldEdit = new FormData();
        fdcomFieldEdit.bottom = new FormAttachment(100, -5);
        fdcomFieldEdit.right = new FormAttachment(100, -5);
        comFieldEdit.setLayoutData(fdcomFieldEdit);
        final GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 3;
        gridLayout.horizontalSpacing = 10;
        comFieldEdit.setLayout(gridLayout);

        final Label lblFieldName = new Label(comFieldEdit, SWT.NONE);
        final GridData gdlblFieldName = new GridData(SWT.RIGHT, SWT.CENTER,
                false, false);
        lblFieldName.setLayoutData(gdlblFieldName);
        lblFieldName.setAlignment(SWT.RIGHT);
        lblFieldName.setText(AddTypePage.STR_NAME);

        this.txtFieldName = new Text(comFieldEdit, SWT.BORDER);
        this.txtFieldName.setTextLimit(30);
        final GridData gdtxtFieldName = new GridData(SWT.FILL, SWT.CENTER,
                false, false);
        gdtxtFieldName.widthHint = 217;
        this.txtFieldName.setLayoutData(gdtxtFieldName);

        final Button btnFieldRemove = new Button(comFieldEdit, SWT.NONE);
        final GridData gdbtnFieldRemove = new GridData(SWT.FILL, SWT.CENTER,
                false, false);
        btnFieldRemove.setLayoutData(gdbtnFieldRemove);
        btnFieldRemove.setText(AddTypePage.STR_REMOVE);

        final Label lblFieldType = new Label(comFieldEdit, SWT.NONE);
        final GridData gdlblFieldType = new GridData(SWT.RIGHT, SWT.CENTER,
                false, false);
        lblFieldType.setLayoutData(gdlblFieldType);
        lblFieldType.setAlignment(SWT.RIGHT);
        lblFieldType.setText("Type:");

        this.txtFieldType = new Text(comFieldEdit, SWT.BORDER);
        this.txtFieldType.setTextLimit(30);
        final GridData gdtxtFieldType = new GridData(SWT.FILL, SWT.CENTER,
                false, false);
        this.txtFieldType.setLayoutData(gdtxtFieldType);

        final Button btnFieldUpdate = new Button(comFieldEdit, SWT.NONE);
        final GridData gdbtnFieldUpdate = new GridData(SWT.FILL, SWT.CENTER,
                false, false);
        btnFieldUpdate.setLayoutData(gdbtnFieldUpdate);
        btnFieldUpdate.setText(AddTypePage.STR_UPDATE);

        final Label lblFieldDelimiter = new Label(comFieldEdit, SWT.NONE);
        final GridData gdlblFieldDelimiter = new GridData(SWT.RIGHT,
                SWT.CENTER, false, false);
        lblFieldDelimiter.setLayoutData(gdlblFieldDelimiter);
        lblFieldDelimiter.setText("Delimiter:");

        this.txtFieldDelimiter = new Text(comFieldEdit, SWT.BORDER);
        this.txtFieldDelimiter.setTextLimit(30);
        final GridData gdtxtFieldDelimiter = new GridData(SWT.FILL, SWT.CENTER,
                true, false);
        this.txtFieldDelimiter.setLayoutData(gdtxtFieldDelimiter);

        final Button btnFieldAdd = new Button(comFieldEdit, SWT.NONE);
        btnFieldAdd.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {}
        });
        final GridData gdbtnFieldAdd = new GridData(SWT.FILL, SWT.CENTER,
                false, false);
        btnFieldAdd.setLayoutData(gdbtnFieldAdd);
        btnFieldAdd.setText(AddTypePage.STR_ADD);

        final Composite comPropertiesRoot = new Composite(tabFolder, SWT.NONE);
        comPropertiesRoot.setLayout(new FillLayout());
        tabItemProperties.setControl(comPropertiesRoot);

        final Composite comProperties = new Composite(comPropertiesRoot,
                SWT.NONE);
        comProperties.setLayout(new FormLayout());

        this.tblProperties = new Table(comProperties, SWT.BORDER);
        this.tblProperties.setHeaderVisible(true);
        final FormData fdtblProperties = new FormData();
        fdtblProperties.right = new FormAttachment(100, -5);
        fdtblProperties.left = new FormAttachment(0, 5);
        fdtblProperties.top = new FormAttachment(0, 5);
        this.tblProperties.setLayoutData(fdtblProperties);
        this.tblProperties.setLinesVisible(true);

        final TableColumn colPropertyName = new TableColumn(this.tblProperties,
                SWT.NONE);
        colPropertyName.setText(AddTypePage.STR_NAME);
        colPropertyName.setWidth(222);

        final TableColumn colPropertyValue = new TableColumn(
                this.tblProperties, SWT.NONE);
        colPropertyValue.setWidth(100);
        colPropertyValue.setText("Value");

        final TableItem tblItemPropertyName = new TableItem(this.tblProperties,
                SWT.BORDER);
        tblItemPropertyName.setText(0, AddTypePage.STR_NAME);
        tblItemPropertyName.setText(1, "New Type");
        tblItemPropertyName.setText("Name");

        Composite comPropertyEdit;
        comPropertyEdit = new Composite(comProperties, SWT.NONE);
        fdtblProperties.bottom = new FormAttachment(comPropertyEdit, -5,
                SWT.TOP);
        final FormData fdcomPropertyEdit = new FormData();
        fdcomPropertyEdit.bottom = new FormAttachment(100, -5);
        fdcomPropertyEdit.right = new FormAttachment(100, -5);
        comPropertyEdit.setLayoutData(fdcomPropertyEdit);
        final GridLayout gridLayout1 = new GridLayout();
        gridLayout1.numColumns = 3;
        gridLayout1.horizontalSpacing = 10;
        comPropertyEdit.setLayout(gridLayout1);

        final Label lblPropertyName = new Label(comPropertyEdit, SWT.NONE);
        final GridData gdlblPropertyName = new GridData(SWT.RIGHT, SWT.CENTER,
                false, false);
        lblPropertyName.setLayoutData(gdlblPropertyName);
        lblPropertyName.setAlignment(SWT.RIGHT);
        lblPropertyName.setText(AddTypePage.STR_NAME);

        this.txtPropertyName = new Text(comPropertyEdit, SWT.BORDER);
        this.txtPropertyName.setTextLimit(30);
        final GridData gdtxtPropertyName = new GridData(SWT.FILL, SWT.CENTER,
                false, false);
        gdtxtPropertyName.widthHint = 217;
        this.txtPropertyName.setLayoutData(gdtxtPropertyName);

        final Button btnPropertyRemove = new Button(comPropertyEdit, SWT.NONE);
        final GridData gdbtnPropertyRemove = new GridData(SWT.FILL, SWT.CENTER,
                false, false);
        btnPropertyRemove.setLayoutData(gdbtnPropertyRemove);
        btnPropertyRemove.setText(AddTypePage.STR_REMOVE);

        final Label lblPropertyValue = new Label(comPropertyEdit, SWT.NONE);
        final GridData gdlblPropertyValue = new GridData(SWT.RIGHT, SWT.CENTER,
                false, false);
        lblPropertyValue.setLayoutData(gdlblPropertyValue);
        lblPropertyValue.setAlignment(SWT.RIGHT);
        lblPropertyValue.setText("Value:");

        this.txtPropertyValue = new Text(comPropertyEdit, SWT.BORDER);
        this.txtPropertyValue.setTextLimit(30);
        final GridData gdtxtPropertyValue = new GridData(SWT.FILL, SWT.CENTER,
                false, false);
        this.txtPropertyValue.setLayoutData(gdtxtPropertyValue);

        final Button btnPropertyUpdate = new Button(comPropertyEdit, SWT.NONE);
        final GridData gdbtnPropertyUpdate = new GridData(SWT.FILL, SWT.CENTER,
                false, false);
        btnPropertyUpdate.setLayoutData(gdbtnPropertyUpdate);
        btnPropertyUpdate.setText(AddTypePage.STR_UPDATE);
        new Label(comPropertyEdit, SWT.NONE);
        new Label(comPropertyEdit, SWT.NONE);

        final Button btnPropertyAdd = new Button(comPropertyEdit, SWT.NONE);
        final GridData gdbtnPropertyAdd = new GridData(SWT.FILL, SWT.CENTER,
                false, false);
        btnPropertyAdd.setLayoutData(gdbtnPropertyAdd);
        btnPropertyAdd.setText(AddTypePage.STR_ADD);

        final CTabItem tabItemFilter = new CTabItem(tabFolder, SWT.NONE);
        tabItemFilter.setText("Filter");

        final Composite comFilter = new Composite(tabFolder, SWT.NONE);
        comFilter.setLayout(new FormLayout());
        tabItemFilter.setControl(comFilter);

        this.listFilters = new List(comFilter, SWT.BORDER);
        this.listFilters.setItems(new String[] { "Test" });
        final FormData fdlistFilters = new FormData();
        fdlistFilters.right = new FormAttachment(100, -5);
        fdlistFilters.left = new FormAttachment(0, 5);
        fdlistFilters.top = new FormAttachment(0, 5);
        this.listFilters.setLayoutData(fdlistFilters);

        Composite comPropertyEdit1;
        comPropertyEdit1 = new Composite(comFilter, SWT.NONE);
        fdlistFilters.bottom = new FormAttachment(comPropertyEdit1, -5, SWT.TOP);
        final FormData fdcomPropertyEdit1 = new FormData();
        fdcomPropertyEdit1.right = new FormAttachment(100, -5);
        fdcomPropertyEdit1.bottom = new FormAttachment(100, -5);
        comPropertyEdit1.setLayoutData(fdcomPropertyEdit1);
        final GridLayout gridLayout2 = new GridLayout();
        gridLayout2.numColumns = 3;
        gridLayout2.horizontalSpacing = 10;
        comPropertyEdit1.setLayout(gridLayout2);

        final Label lblFilterRegexp = new Label(comPropertyEdit1, SWT.NONE);
        final GridData gdlblFilterRegexp = new GridData(SWT.RIGHT, SWT.CENTER,
                false, false);
        lblFilterRegexp.setLayoutData(gdlblFilterRegexp);
        lblFilterRegexp.setAlignment(SWT.RIGHT);
        lblFilterRegexp.setText("Regular expression:");

        this.txtFilterRegexp = new Text(comPropertyEdit1, SWT.BORDER);
        final GridData gdtxtFilterRegexp = new GridData(SWT.FILL, SWT.CENTER,
                false, false);
        gdtxtFilterRegexp.widthHint = 217;
        this.txtFilterRegexp.setLayoutData(gdtxtFilterRegexp);

        final Button btnFilterRemove = new Button(comPropertyEdit1, SWT.NONE);
        final GridData gdbtnFilterRemove = new GridData(SWT.FILL, SWT.CENTER,
                false, false);
        btnFilterRemove.setLayoutData(gdbtnFilterRemove);
        btnFilterRemove.setText(AddTypePage.STR_REMOVE);
        new Label(comPropertyEdit1, SWT.NONE);
        new Label(comPropertyEdit1, SWT.NONE);

        final Button btnFilterUpdate = new Button(comPropertyEdit1, SWT.NONE);
        final GridData gdbtnFilterUpdate = new GridData(SWT.FILL, SWT.CENTER,
                false, false);
        btnFilterUpdate.setLayoutData(gdbtnFilterUpdate);
        btnFilterUpdate.setText(AddTypePage.STR_UPDATE);
        new Label(comPropertyEdit1, SWT.NONE);
        new Label(comPropertyEdit1, SWT.NONE);

        final Button btnFilterAdd = new Button(comPropertyEdit1, SWT.NONE);
        final GridData gdbtnFilterAdd = new GridData(SWT.FILL, SWT.CENTER,
                false, false);
        btnFilterAdd.setLayoutData(gdbtnFilterAdd);
        btnFilterAdd.setText(AddTypePage.STR_ADD);

        Composite compositePreview;
        compositePreview = new Composite(composite2, SWT.NONE);
        final FormData fdcompositePreview = new FormData();
        fdcompositePreview.left = new FormAttachment(compositeConfig, 5,
                SWT.RIGHT);
        fdcompositePreview.bottom = new FormAttachment(100, -5);
        fdcompositePreview.right = new FormAttachment(100, -5);
        fdcompositePreview.top = new FormAttachment(0, 0);
        compositePreview.setLayoutData(fdcompositePreview);
        compositePreview.setLayout(new FormLayout());

        final Group grpPreview = new Group(compositePreview, SWT.NONE);
        final FormData fdgrpPreview = new FormData();
        fdgrpPreview.top = new FormAttachment(0, 15);
        fdgrpPreview.left = new FormAttachment(0, 5);
        fdgrpPreview.bottom = new FormAttachment(100, -5);
        fdgrpPreview.right = new FormAttachment(100, 0);
        grpPreview.setLayoutData(fdgrpPreview);
        grpPreview.setLayout(new FormLayout());
        grpPreview.setText("Preview");

        this.txtPreview = new StyledText(grpPreview, SWT.BORDER);
        final FormData fdtxtPreview = new FormData();
        fdtxtPreview.left = new FormAttachment(0, 5);
        fdtxtPreview.right = new FormAttachment(100, -5);
        fdtxtPreview.top = new FormAttachment(0, 5);
        fdtxtPreview.bottom = new FormAttachment(100, -5);
        this.txtPreview.setLayoutData(fdtxtPreview);
        this.txtPreview.setText("Nothing to show");
    }
}
