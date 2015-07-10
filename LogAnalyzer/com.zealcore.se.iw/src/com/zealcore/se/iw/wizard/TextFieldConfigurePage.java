package com.zealcore.se.iw.wizard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;

import com.swtdesigner.SWTResourceManager;
import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.services.IMementoService;
import com.zealcore.se.iw.FieldDescriptor;
import com.zealcore.se.iw.GenericImportRegistry;
import com.zealcore.se.iw.GenericTextImportData;
import com.zealcore.se.iw.ImportWizardPlugin;
import com.zealcore.se.iw.types.internal.FieldTypeFactory;
import com.zealcore.se.iw.types.internal.IFieldType;
import com.zealcore.se.iw.types.internal.ImportBehaviour;
import com.zealcore.se.iw.types.internal.StringType;

public class TextFieldConfigurePage extends WizardPage {

    private CTabFolder tabFolder;

    private Text txtFiltersRegexp;

    private Table tblFilters;

    private FormData fdTable;

    private static final int HUNDRED = 100;

    private static final int CONSTANT10 = 10;

    private static final int CONSTANT60 = 60;

    private static final int CONSTANT100 = TextFieldConfigurePage.HUNDRED;

    private static final int NO_OF_DESCRIPTORS = 20;

    private static final String COURIER_NEW = "Courier New";

    static final String TAG_DELIMITER = "Delimiter";

    static final String TAG_TYPE = "Type";

    static final String TAG_NAME = "Name";

    private static List<FieldDescriptor> descriptors = new ArrayList<FieldDescriptor>();

    private StyledText filePreview;

    private TableViewer fieldConfig;

    private final File logFile;

    private final Set<String> linesToFilterRegExps = new HashSet<String>();

    private GenericTextImportData importData;

    private DecoratingVisitor filePreviewDecorator;

    private Button btnFiltersRemove;

    private Button btnFiltersUpdate;
    
    private Button exportButton;
    
    private int selectedRow;

    // Unused code?
    // public void setLogFile(final File logfile) {
    // logFile = logfile;
    // }

    protected TextFieldConfigurePage(final String pageName, final File logFile,
            final GenericTextImportData importData) {
        super(pageName);
        this.logFile = logFile;
        this.importData = importData;

        setDescription("Describe the format for each row by setting delimiters and datatype.");
        setTitle("Logfile: " + logFile.getName());

        if (TextFieldConfigurePage.descriptors.size() < 1) {
            for (int i = 0; i < TextFieldConfigurePage.NO_OF_DESCRIPTORS; i++) {
                TextFieldConfigurePage.descriptors.add(new FieldDescriptor());
            }
        }

        // read descriptors from import data and populate UI
        final List<FieldDescriptor> fieldDescriptors = importData
                .getDescriptors();
        if (fieldDescriptors != null && fieldDescriptors.size() > 0) {
            int ct = 0;
            for (final FieldDescriptor descriptor : fieldDescriptors) {
                TextFieldConfigurePage.descriptors.set(ct++, descriptor);
            }
        }
    }

    public List<FieldDescriptor> getDescriptors() {
        final List<FieldDescriptor> descs = new ArrayList<FieldDescriptor>();
        if (TextFieldConfigurePage.descriptors == null) {
            return descs;
        }
        for (final FieldDescriptor descriptor : TextFieldConfigurePage.descriptors) {
            if (descriptor.isFull()) {
                descs.add(descriptor.copy());
            }
        }
        return descs;
    }

    private void resetDescriptors() {
        if (TextFieldConfigurePage.descriptors != null) {
            for (final FieldDescriptor descriptor : TextFieldConfigurePage.descriptors) {
                descriptor.setDelimiter("");
                descriptor.setName("");
                descriptor.setType(new StringType());
            }
        }
        this.fieldConfig.setInput(TextFieldConfigurePage.descriptors);
    }

    public void insertNewDescriptor(final int i) {
        int index = i;
        // If the user selected outside existing descriptors, one will be added
        // at the last row
        if (index == -1) {
            index = TextFieldConfigurePage.descriptors.size();
        }
        final FieldDescriptor element = new FieldDescriptor();
        TextFieldConfigurePage.descriptors.add(index, element);
        this.fieldConfig.setInput(TextFieldConfigurePage.descriptors);
    }

    public void removeDescriptor(final int index) {
        if (index > -1 && index <= TextFieldConfigurePage.descriptors.size()) {
            TextFieldConfigurePage.descriptors.remove(index);
            this.fieldConfig.setInput(TextFieldConfigurePage.descriptors);
        }
    }

    public Collection<String> getFilters() {
        return this.linesToFilterRegExps;
    }

    public ImportBehaviour getImportBehaviour() {
        throw new UnsupportedOperationException(
                "This method should not be used");
    }

    public void createControl(final Composite parent) {
        final WizardDialog dlg = (WizardDialog) getContainer();
        dlg.addPageChangedListener(new IPageChangedListener() {
            public void pageChanged(final PageChangedEvent event) {
                updateDecorations();
            }
        });

        final Composite container = new Composite(parent, SWT.NULL);
        container.setLayout(new FormLayout());
        setControl(container);

        Button clearButton;
        Button importButton;

        this.tabFolder = new CTabFolder(container, SWT.FLAT | SWT.MULTI);
        this.tabFolder.setMRUVisible(true);
        this.tabFolder.setSimple(false);
        final FormData fdtabFolder = new FormData();
        fdtabFolder.right = new FormAttachment(100, -5);
        fdtabFolder.top = new FormAttachment(0, 5);
        fdtabFolder.left = new FormAttachment(0, 5);
        this.tabFolder.setLayoutData(fdtabFolder);

        final CTabItem tabItemFields = new CTabItem(this.tabFolder, SWT.NONE);
        tabItemFields.setText("Fields");

        final Composite comConfigTab = new Composite(this.tabFolder, SWT.NONE);
        comConfigTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(final MouseEvent e) {}
        });
        comConfigTab.setLayout(new FormLayout());
        tabItemFields.setControl(comConfigTab);

        this.fieldConfig = new TableViewer(comConfigTab, SWT.FULL_SELECTION);
        this.fieldConfig
                .addPostSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(
                            final SelectionChangedEvent event) {
                        updateDecorations();
                    }
                });
        final Table table = this.fieldConfig.getTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(final MouseEvent e) {

                // This event is caught because the row that is selected must be
                // stored to be able to Insert or Delete the correct row when
                // the pop-up menu is shown. This method must be used, instead
                // of using "table.getSelectionIndex()" at the pop-up menu since
                // the items are not updated when right clicking outside valid
                // items
                final Point point = new Point(e.x, e.y);
                if (table.getItem(point) != null) {
                    TextFieldConfigurePage.this.selectedRow = table.getItem(
                            point).getParent().getSelectionIndex();
                } else {
                    // Insert new rows if the user clicks on the last row since
                    // it is missing a descriptor. The test to se if the user is
                    // clicking on the last row is to verify that he clicks on a
                    // row without an Item and that he clicks within the
                    // columns.
                    TextFieldConfigurePage.this.selectedRow = -1;
                    int width = 0;
                    for (final TableColumn c : table.getColumns()) {
                        width += c.getWidth();
                    }
                    if (width > e.x) {
                        insertNewDescriptor(table.getItemCount());
                    }
                }
            }
        });
        table.setSize(360, 100);
        this.fdTable = new FormData();
        this.fdTable.left = new FormAttachment(0, 5);
        this.fdTable.right = new FormAttachment(100, -5);
        this.fdTable.top = new FormAttachment(0, 5);
        table.setLayoutData(this.fdTable);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        final TableColumn nameColumn = new TableColumn(table, SWT.NORMAL);
        nameColumn.setWidth(124);
        nameColumn.setMoveable(true);
        nameColumn.setText(TextFieldConfigurePage.TAG_NAME);
        nameColumn.setWidth(140);

        final TableColumn typeColumn = new TableColumn(table, SWT.NORMAL);
        typeColumn.setMoveable(true);
        typeColumn.setText(TextFieldConfigurePage.TAG_TYPE);
        typeColumn.setWidth(112);

        final TableColumn delimiterColumn = new TableColumn(table, SWT.NORMAL);
        delimiterColumn.setMoveable(true);
        delimiterColumn.setText(TextFieldConfigurePage.TAG_DELIMITER);
        delimiterColumn.setWidth(103);

        this.fieldConfig.setContentProvider(new ArrayContentProvider());
        this.fieldConfig.setLabelProvider(new FieldConfigLabelProvider());

        this.fieldConfig.setInput(TextFieldConfigurePage.descriptors);
        createCellEditors(this.fieldConfig);
        clearButton = new Button(comConfigTab, SWT.NONE);
        this.fdTable.bottom = new FormAttachment(clearButton, -3, SWT.TOP);
        importButton = new Button(comConfigTab, SWT.NONE);
        this.fdTable.bottom = new FormAttachment(importButton, -3, SWT.TOP);
        exportButton = new Button(comConfigTab, SWT.NONE);
        this.fdTable.bottom = new FormAttachment(exportButton, -3, SWT.TOP);
        
        final Menu menu = new Menu(table);

        final MenuItem insertMenuItem = new MenuItem(menu, SWT.NONE);
        insertMenuItem.addSelectionListener(new SelectionAdapter() {
            // Called when Pop-up menu item "Insert" is selected. A row is
            // inserted before the selected row. The "selected row" is stored at
            // mouse down event.
            @Override
            public void widgetSelected(final SelectionEvent e) {
                if (TextFieldConfigurePage.this.selectedRow > -1) {
                    insertNewDescriptor(table.getSelectionIndex());
                }
            }
        });
        insertMenuItem.setText("Insert");
        final MenuItem deleteMenuItem = new MenuItem(menu, SWT.PUSH);
        deleteMenuItem.addSelectionListener(new SelectionAdapter() {
            // Called when Pop-up menu item "Delete" is selected. The selected
            // deleted. The "selected row" is stored at mouse down event.
            @Override
            public void widgetSelected(final SelectionEvent e) {
                if (TextFieldConfigurePage.this.selectedRow > -1) {
                    removeDescriptor(TextFieldConfigurePage.this.selectedRow);
                }
            }
        });
        deleteMenuItem.setText("Delete");
        menu.addMenuListener(new MenuAdapter() {
            @Override
            public void menuShown(final MenuEvent e) {}
        });
        table.setMenu(menu);
        
        exportButton.setEnabled(false);
        final FormData fdClearButton = new FormData();
        fdClearButton.top = new FormAttachment(100, -28);
        fdClearButton.bottom = new FormAttachment(100, -5);
        fdClearButton.left = new FormAttachment(100, -97);
        fdClearButton.right = new FormAttachment(100, -5);
        clearButton.setLayoutData(fdClearButton);
        clearButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {}
        });
        clearButton.addMouseListener(new MouseListener() {
            public void mouseDoubleClick(final MouseEvent e) {}

            public void mouseDown(final MouseEvent e) {
                resetDescriptors();
            }

            public void mouseUp(final MouseEvent e) {}
        });
        clearButton.setText("Clear");
        
        final FormData fdImportButton = new FormData();
        fdImportButton.top = new FormAttachment(100, -28);
        fdImportButton.bottom = new FormAttachment(100, -5);
        fdImportButton.left = new FormAttachment(75, -90);
        fdImportButton.right = new FormAttachment(75, 5);
        importButton.setLayoutData(fdImportButton);
        importButton.setText("Import");
        importButton.setToolTipText("Import with parsing format");
        importButton.addSelectionListener(new SelectionAdapter(){
            @Override
            public void widgetSelected(SelectionEvent e) {
                FileDialog openDialog = new FileDialog(getShell(), SWT.OPEN);
                openDialog.setText("Import...");
                // Display only .xml files in the open dialog.
                openDialog.setFilterExtensions(new String[] { "*.xml" });
                String selected = openDialog.open();
                if(selected == null || selected.equalsIgnoreCase("")) {
                    return;
                }
                IPath importFilePath = new Path(selected);
                // Warning message after selection of format file and before
                // replacing the content of table from the file
                String dialogTitle = "Import";
                String dialogMessage = "Are you sure you want to import with selected file?";
                final MessageDialog warnDialog = new MessageDialog(null,
                        dialogTitle, null, dialogMessage,
                        MessageDialog.QUESTION,
                        new String[] { "Yes", "No", }, 0);

                // Yes is the default
                if (warnDialog.open() == 0) {
                   importXMLData(importFilePath);
                }
                updateFieldDescriptors(importData);
            }
        });
        
        final FormData fdExportButton = new FormData();
        fdExportButton.top = new FormAttachment(100, -28);
        fdExportButton.bottom = new FormAttachment(100, -5);
        fdExportButton.left = new FormAttachment(50, -90);
        fdExportButton.right = new FormAttachment(50, 5);
        exportButton.setLayoutData(fdExportButton);
        exportButton.setText("Export");
        exportButton.setToolTipText("Export the parsing format");
        exportButton.addSelectionListener(new SelectionAdapter(){
            @Override            
            public void widgetSelected(SelectionEvent e) {
                FileDialog saveDialog = new FileDialog(getShell(), SWT.SAVE);
                saveDialog.setText("Export...");
               // Set the file with .xml extension.
                saveDialog.setFilterExtensions(new String[] { "*.xml" });
                String selected = saveDialog.open();
                if (selected == null || selected.equalsIgnoreCase("")) {
                    return;
                }
                IPath exportFilePath = new Path(selected);
                GenericImportRegistry registry = SeCorePlugin.getDefault()
                        .getService(GenericImportRegistry.class);
                // Update imported data that has been stored during import operation.
                registry.updateImportData(importData);
                // Save the exported data in XML file.
                registry.save(exportFilePath);
            }
        });
        
        final CTabItem tabItemFilter = new CTabItem(this.tabFolder, SWT.NONE);
        tabItemFilter.setText("Filters");

        final Composite composite1 = new Composite(this.tabFolder, SWT.NONE);
        composite1.setLayout(new FormLayout());
        tabItemFilter.setControl(composite1);

        this.tblFilters = new Table(composite1, SWT.BORDER);
        this.tblFilters.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                TextFieldConfigurePage.this.txtFiltersRegexp
                        .setText(TextFieldConfigurePage.this.tblFilters
                                .getItem(
                                        TextFieldConfigurePage.this.tblFilters
                                                .getSelectionIndex()).getText());
            }
        });
        final FormData fdtblFilters = new FormData();
        fdtblFilters.top = new FormAttachment(0, 5);
        fdtblFilters.right = new FormAttachment(100, -5);
        fdtblFilters.left = new FormAttachment(0, 0);
        this.tblFilters.setLayoutData(fdtblFilters);
        this.tblFilters.setLinesVisible(true);
        this.tblFilters.setHeaderVisible(true);

        Composite comFiltersEdit;
        comFiltersEdit = new Composite(composite1, SWT.NONE);
        fdtblFilters.bottom = new FormAttachment(comFiltersEdit, -5, SWT.TOP);

        final TableColumn colFiltersRegexp = new TableColumn(this.tblFilters,
                SWT.NONE);
        colFiltersRegexp.setWidth(392);
        colFiltersRegexp.setText("Regular expression");
        final GridLayout gridLayout1 = new GridLayout();
        gridLayout1.numColumns = 3;
        comFiltersEdit.setLayout(gridLayout1);
        final FormData fdcomFiltersEdit = new FormData();
        fdcomFiltersEdit.bottom = new FormAttachment(100, -5);
        fdcomFiltersEdit.right = new FormAttachment(this.tblFilters, 0,
                SWT.RIGHT);
        fdcomFiltersEdit.left = new FormAttachment(0, 4);
        comFiltersEdit.setLayoutData(fdcomFiltersEdit);

        final Label lblFiltersRegexp = new Label(comFiltersEdit, SWT.NONE);
        final GridData gdlblFiltersRegexp = new GridData(SWT.RIGHT, SWT.CENTER,
                false, false);
        lblFiltersRegexp.setLayoutData(gdlblFiltersRegexp);
        lblFiltersRegexp.setText("Regular expression:");

        this.txtFiltersRegexp = new Text(comFiltersEdit, SWT.BORDER);

        final GridData gdtxtFiltersRegexp = new GridData(SWT.FILL, SWT.CENTER,
                true, false);
        gdtxtFiltersRegexp.widthHint = 214;
        this.txtFiltersRegexp.setLayoutData(gdtxtFiltersRegexp);

        final Button btnFiltersAdd = new Button(comFiltersEdit, SWT.NONE);
        btnFiltersAdd.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                addNewFilter(TextFieldConfigurePage.this.txtFiltersRegexp
                        .getText());
            }
        });
        final GridData gdbtnFiltersAdd = new GridData(SWT.FILL, SWT.CENTER,
                false, false);
        btnFiltersAdd.setLayoutData(gdbtnFiltersAdd);
        btnFiltersAdd.setText("Add");
        new Label(comFiltersEdit, SWT.NONE);
        new Label(comFiltersEdit, SWT.NONE);

        this.btnFiltersUpdate = new Button(comFiltersEdit, SWT.NONE);
        this.btnFiltersUpdate.setEnabled(false);
        this.btnFiltersUpdate.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                updateSelectedFilter();
            }
        });
        final GridData gdbtnFiltersUpdate = new GridData(SWT.FILL, SWT.CENTER,
                false, false);
        this.btnFiltersUpdate.setLayoutData(gdbtnFiltersUpdate);
        this.btnFiltersUpdate.setText("Update");
        new Label(comFiltersEdit, SWT.NONE);
        new Label(comFiltersEdit, SWT.NONE);

        this.btnFiltersRemove = new Button(comFiltersEdit, SWT.NONE);
        this.btnFiltersRemove.setEnabled(false);
        this.btnFiltersRemove.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                removeSelectedFilter();
            }
        });
        final GridData gdbtnFiltersRemove = new GridData(SWT.FILL, SWT.CENTER,
                false, false);
        this.btnFiltersRemove.setLayoutData(gdbtnFiltersRemove);
        this.btnFiltersRemove.setText("Remove");

        this.filePreview = new StyledText(container, SWT.V_SCROLL
                | SWT.H_SCROLL | SWT.BORDER);
        FormData fdpreview;
        fdpreview = new FormData();
        fdpreview.top = new FormAttachment(0, 325);
        fdpreview.height = 200;
        fdpreview.width = 600;
        fdpreview.left = new FormAttachment(0, 5);
        fdpreview.bottom = new FormAttachment(100, -5);
        fdpreview.right = new FormAttachment(100, -12);
        this.filePreview.setLayoutData(fdpreview);
        this.filePreview.setFont(SWTResourceManager.getFont(
                TextFieldConfigurePage.COURIER_NEW,
                TextFieldConfigurePage.CONSTANT10, SWT.NONE));
        // preview.setFont(font);
        this.filePreview.setBackground(Display.getDefault().getSystemColor(
                SWT.COLOR_WHITE));

        int style = SWT.V_SCROLL | SWT.MULTI | SWT.READ_ONLY;
        style |= SWT.BORDER | SWT.H_SCROLL;
        createPreview(container);

        Label filePreviewLabel;
        filePreviewLabel = new Label(container, SWT.NONE);
        fdtabFolder.bottom = new FormAttachment(filePreviewLabel, -5, SWT.TOP);
        final FormData fdfilePreviewLabel = new FormData();
        fdfilePreviewLabel.top = new FormAttachment(0, 300);
        fdfilePreviewLabel.right = new FormAttachment(this.filePreview, 170,
                SWT.LEFT);
        fdfilePreviewLabel.left = new FormAttachment(this.filePreview, 0,
                SWT.LEFT);
        filePreviewLabel.setLayoutData(fdfilePreviewLabel);
        filePreviewLabel.setText("File preview");

        this.tabFolder.setSelection(tabItemFields);

        this.filePreviewDecorator = new DecoratingVisitor(this.filePreview);

        // Make Actions
        this.txtFiltersRegexp.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                try {
                    Pattern
                            .compile(TextFieldConfigurePage.this.txtFiltersRegexp
                                    .getText());
                    btnFiltersAdd.setEnabled(true);
                    TextFieldConfigurePage.this.btnFiltersUpdate
                            .setEnabled(true);
                    setErrorMessage(null);
                } catch (final PatternSyntaxException ex) {
                    btnFiltersAdd.setEnabled(false);
                    TextFieldConfigurePage.this.btnFiltersUpdate
                            .setEnabled(false);
                    setErrorMessage("Please enter a valid regular expression");
                }
            }
        });

        this.tblFilters.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                updateFilterButtons();
            }
        });

        // read filters from import data and populate UI
        final Collection<String> filters = this.importData.getFilters();
        if (filters != null && filters.size() > 0) {
            for (final String filter : filters) {
                addNewFilter(filter);
            }
        }
        updateDecorations();
    }
    
    /*
     * Read the content from the selected XML file. 
     */
    private void importXMLData(IPath filePath){
        try {
            if (filePath.toFile().exists()){
                final IMemento root = SeCorePlugin.getDefault().getService(IMementoService.class).createReadRoot(filePath);
                if (root.getChildren(GenericImportRegistry.TAG_CONFIGURATION).length > 0) {
                    IMemento instance = root.getChildren(GenericImportRegistry.TAG_CONFIGURATION)[0];
                    GenericTextImportData xmlData = GenericTextImportData.valueOf(instance);
                    // Used for export operation.
                    importData = xmlData;
                }
            }
        } catch (final IOException e) {
            ImportWizardPlugin.logError(e);
        } catch (final WorkbenchException e) {
            ImportWizardPlugin.logError(e);
        }
    }

    private void createCellEditors(final TableViewer fieldConfig) {
        final Table table = fieldConfig.getTable();
        final TextCellEditor nameEditor = new TextCellEditor(table);
        ((Text) nameEditor.getControl())
                .setTextLimit(TextFieldConfigurePage.CONSTANT60);
        ((Text) nameEditor.getControl()).setSize(
                TextFieldConfigurePage.CONSTANT100, SWT.DEFAULT);

        final List<String> names = new ArrayList<String>();
        final List<IFieldType> fieldTypes = FieldTypeFactory.getInstance()
                .getRegisteredFieldTypes();
        for (final IFieldType type : fieldTypes) {
            names.add(type.getLabel());
        }
        final CellEditor typeEditor = new ComboBoxCellEditor(table, names
                .toArray(new String[names.size()]), SWT.READ_ONLY);

        final TextCellEditor delimEditor = new TextCellEditor(table);

        fieldConfig.setCellEditors(new CellEditor[] { nameEditor, typeEditor,
                delimEditor, });
        fieldConfig.setCellModifier(new FieldDescriptorCellModifier(
                fieldConfig, fieldTypes));

        final List<String> columnNames = new ArrayList<String>();
        for (final TableColumn column : fieldConfig.getTable().getColumns()) {
            // column.pack();
            columnNames.add(column.getText());
        }

        fieldConfig.setColumnProperties(columnNames
                .toArray(new String[columnNames.size()]));

    }

    protected void updateDecorations() {
        final FieldDescriptor selected = (FieldDescriptor) ((IStructuredSelection) this.fieldConfig
                .getSelection()).getFirstElement();
        // Enabled only if data is entered in Filter's table.
        exportButton.setEnabled(isPageComplete());
        this.importData.setDescriptors(getDescriptors());
        this.importData.setFilters(getFilters());
        this.filePreviewDecorator.decorate(this.importData, selected);
        if (getContainer() != null && isCurrentPage()) {
            getContainer().updateButtons();
        }
    }

    private void createPreview(final Composite container) {
        this.filePreview.setText("");
        reloadLogfileToPreview();
        this.filePreview.setEditable(false);
    }

    private void removeSelectedFilter() {
        final int idx = this.tblFilters.getSelectionIndex();
        final String text = this.tblFilters.getItem(idx).getText();
        this.linesToFilterRegExps.remove(text);
        this.tblFilters.remove(this.tblFilters.getSelectionIndex());
        this.linesToFilterRegExps.remove(text);
        if (idx < 0) {
            return;
        }
        if (idx > 0) {
            this.tblFilters.setSelection(idx - 1);
        }
        updateFilterButtons();
        updateDecorations();
    }

    private void updateSelectedFilter() {
        final String oldText = this.tblFilters.getItem(
                this.tblFilters.getSelectionIndex()).getText();
        this.linesToFilterRegExps.remove(oldText);
        this.tblFilters.getItem(this.tblFilters.getSelectionIndex()).setText(
                this.txtFiltersRegexp.getText());
        this.linesToFilterRegExps.add(this.txtFiltersRegexp.getText());
        updateFilterButtons();
        updateDecorations();
    }

    private void addNewFilter(final String text) {
        if (text.length() == 0) {
            return;
        }
        if (this.linesToFilterRegExps.contains(text)) {
            return;
        }
        final TableItem item = new TableItem(this.tblFilters, SWT.DEFAULT);
        item.setText(text);
        this.linesToFilterRegExps.add(text);
        updateDecorations();
    }

    private static class FieldConfigLabelProvider extends LabelProvider
            implements ITableLabelProvider {

        private static final String UNKNOWN_ELEMENT = "UNKNOWN ELEMENT";

        private static final int COLUMN_TWO = 2;

        public Image getColumnImage(final Object element, final int columnIndex) {
            return null;
        }

        public String getColumnText(final Object element, final int columnIndex) {
            if (!(element instanceof FieldDescriptor)) {
                return FieldConfigLabelProvider.UNKNOWN_ELEMENT;
            }
            final FieldDescriptor field = (FieldDescriptor) element;
            switch (columnIndex) {
            case 0:
                return field.getName();
            case 1:
                return field.getType().getLabel();
            case COLUMN_TWO:
                return field.getDelimiter();
            default:
                return "UNKNOWN COLUMN";
            }
        }

        public void setColumnText(final Object element, final int columnIndex,
                final Object fieldValue) {
            if (element instanceof FieldDescriptor) {
                final FieldDescriptor field = (FieldDescriptor) element;
                switch (columnIndex) {
                case 0:
                    field.setName((String) fieldValue);
                    break;

                case 1:
                    field.setType((IFieldType) fieldValue);
                    break;

                case COLUMN_TWO:
                    field.setDelimiter(((String) fieldValue));
                    break;
                default:
                    throw new IllegalArgumentException(
                            "Unknown index argument :" + columnIndex);
                }
            }
        }
    }

    @Override
    public boolean isPageComplete() {
        return !getDescriptors().isEmpty();
    }

    private void updateFilterButtons() {
        boolean enabled = false;
        if (this.tblFilters.getSelectionCount() > 0) {
            enabled = true;
        }
        this.btnFiltersRemove.setEnabled(enabled);
        this.btnFiltersUpdate.setEnabled(enabled);
    }

    public void reloadLogfileToPreview() {
        ConfigureImportWizard.clearCach(this.logFile);
        this.filePreview.setText(ConfigureImportWizard
                .getLogfileText(this.logFile));
    }
    
    private void updateFieldDescriptors(final GenericTextImportData importData){
        // read descriptors from import data and populate UI
        final List<FieldDescriptor> fieldDescriptors = importData
                .getDescriptors();
        // Reset all the descriptor values to fill the new import data. 
        resetDescriptors();
        if (fieldDescriptors != null && fieldDescriptors.size() > 0) {
            int ct = 0;
            // Fill the descriptor values one by one.
            for (final FieldDescriptor descriptor : fieldDescriptors) {
                TextFieldConfigurePage.descriptors.set(ct++, descriptor);
            }
        }
        this.fieldConfig.setInput(TextFieldConfigurePage.descriptors);
        // read filters from import data and populate UI
        final Collection<String> filters = this.importData.getFilters();
        if (filters != null && filters.size() > 0) {
            for (final String filter : filters) {
                addNewFilter(filter);
            }
        }
        updateDecorations();
    }
}
