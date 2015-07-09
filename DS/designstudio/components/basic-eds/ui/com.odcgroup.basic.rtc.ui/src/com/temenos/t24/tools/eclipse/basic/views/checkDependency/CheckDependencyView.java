package com.temenos.t24.tools.eclipse.basic.views.checkDependency;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.ibm.team.repository.client.ITeamRepository;
import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;
import com.temenos.t24.tools.eclipse.basic.remote.data.InstallableItemCollection;
import com.temenos.t24.tools.eclipse.basic.wizards.rtc.install.ChangeSetInstallManager;
import com.temenos.t24.tools.eclipse.basic.wizards.rtc.install.T24ChangeSet;


/**
 * View to simulate changeset installation 
 * 
 * @author yasar arafath
 * 
 */

public class CheckDependencyView extends ViewPart {

    public static final String VIEW_ID = "com.temenos.t24.tools.eclipse.basic.view.checkDependencyView";
    private CheckboxTableViewer checkBoxTableViewer;
    private TableViewer tableViewer;
    private Label workItemReferenceNameLable;
    private Label changeSetCountLable;
    private Label sourceItemsCountLable;
    private Label dataItemsCountLable;
    private Label componentsCountLable;
    private Label productsCountLable;
    private Label dependencyDetailsLable;
    private Label releaseLable;
    private Label totalUpdatesLable;
    private Text workItemReferenceNameText;
    private Button showDependencyButton;
    private Button clearViewButton;
    private Combo releaseCombo;
    private Button runSelectedButton;
    private int changeSetCount = 0;
    private List<T24ItemDetail> tableInput = null;
    private static List<T24UpdateDetails> updateInput = new ArrayList<T24UpdateDetails>();
    
    
    public static List<T24UpdateDetails> getUpdateInput() {
        return updateInput;
    }

    private Composite container;
    private String releaseNumber = "R10";

    @Override
    public void createPartControl(Composite parent) {
        createView(parent);
        addListeners(parent);
       updateButton();
    }

    @SuppressWarnings("deprecation")
	private void createView(Composite parent) {
        GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        parent.setLayout(layout);
        Group group1 = new Group(parent, SWT.HORIZONTAL);
        group1.setParent(parent);
        GridLayout layout1 = new GridLayout();
        layout1.numColumns = 7;
        group1.setLayout(layout1);
        group1.setLayoutData(new GridData());
        GridData data = new GridData();
        data.grabExcessHorizontalSpace = true;
        data.minimumWidth = 100;
        workItemReferenceNameLable = new Label(group1, SWT.NORMAL);
        workItemReferenceNameLable.setText("Change Set Reference:");
        workItemReferenceNameText = new Text(group1, SWT.BORDER);
        workItemReferenceNameText.setLayoutData(data);
        showDependencyButton = new Button(group1, SWT.PUSH);
        showDependencyButton.setText("Show Dependency");
        showDependencyButton.setToolTipText("Shows dependencies");
        runSelectedButton = new Button(group1, SWT.PUSH);
        runSelectedButton.setText(" Run Selected ");
        runSelectedButton.setToolTipText("Calculate dependencies for selected items");
        runSelectedButton.setEnabled(false);
        clearViewButton = new Button(group1, SWT.PUSH);
        clearViewButton.setText("  Clear view  ");
        clearViewButton.setToolTipText("Clears the view");
        clearViewButton.setEnabled(false);
        releaseLable = new Label(group1, SWT.NORMAL);
        releaseLable.setText("Choose release:");
        releaseCombo = new Combo(group1, SWT.DROP_DOWN | SWT.READ_ONLY);
        String[] items = new String[] { "R10", "R09" };
        releaseCombo.setItems(items);
        releaseCombo.setText("R10");
        releaseCombo.setEnabled(true);
        checkBoxTableViewer = new CheckboxTableViewer(parent, SWT.CHECK | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        Table table = checkBoxTableViewer.getTable();
        table.setEnabled(true);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        GridData tableData = new GridData();
        tableData.minimumHeight = 150;
        // tableData.minimumWidth = 300;
        tableData.grabExcessVerticalSpace = true;
        tableData.grabExcessHorizontalSpace = true;
        table.setLayoutData(tableData);
        String componentName = "Component";
        int columnAlignment = SWT.CENTER | SWT.BOLD;
        TableColumn componentColumn = new TableColumn(table, columnAlignment, 0);
        componentColumn.setText(componentName);
        componentColumn.setWidth(300);
        String productName = "Product";
        TableColumn productColumn = new TableColumn(table, columnAlignment, 0);
        productColumn.setText(productName);
        productColumn.setWidth(100);
        String typeColumnName = "Type";
        TableColumn typeColumn = new TableColumn(table, columnAlignment, 0);
        typeColumn.setText(typeColumnName);
        typeColumn.setWidth(100);
        String itemColumnName = "Item";
        TableColumn itemColumn = new TableColumn(table, columnAlignment, 0);
        itemColumn.setText(itemColumnName);
        itemColumn.setWidth(580);
        // Separator
        layout.verticalSpacing = 20;
        Label line = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.BOLD);
        GridData lineData = new GridData(GridData.FILL_HORIZONTAL);
        lineData.horizontalSpan = 2;
        line.setLayoutData(lineData);
        changeSetCountLable = new Label(group1, SWT.NONE);
        changeSetCountLable.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
        changeSetCountLable.setText("Change Sets: 0");
        sourceItemsCountLable = new Label(group1, SWT.NONE);
        sourceItemsCountLable.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
        sourceItemsCountLable.setText("Source items : 0");
        dataItemsCountLable = new Label(group1, SWT.NONE);
        dataItemsCountLable.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
        dataItemsCountLable.setText("Data items : 0");
        componentsCountLable = new Label(group1, SWT.NONE);
        componentsCountLable.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
        componentsCountLable.setText("Components : 0");
        productsCountLable = new Label(group1, SWT.NONE);
        productsCountLable.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
        productsCountLable.setText("Products : 0");
        checkBoxTableViewer.setContentProvider(new ArrayContentProvider());
        checkBoxTableViewer.setLabelProvider(new T24ItemDetailLabelProvider());
        checkBoxTableViewer.setInput(new ArrayList<T24ItemDetail>().toArray());
        dependencyDetailsLable = new Label(parent, SWT.NORMAL);
        dependencyDetailsLable.setText("Dependency Details");
        totalUpdatesLable = new Label(parent, SWT.NORMAL);
        totalUpdatesLable.setText("Total Updates :");
        tableViewer = new TableViewer(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        Table dependencyDetailsTable = tableViewer.getTable();
        dependencyDetailsTable.setEnabled(true);
        dependencyDetailsTable.setHeaderVisible(true);
        dependencyDetailsTable.setLinesVisible(true);
        dependencyDetailsTable.setLayoutData(tableData);
        String reservedField1 = "Dependency list";
        TableColumn reservedField1Column = new TableColumn(dependencyDetailsTable, columnAlignment, 0);
        reservedField1Column.setText(reservedField1);
        reservedField1Column.setWidth(560);
        String nod = "No of Dependencies";
        TableColumn nodColumn = new TableColumn(dependencyDetailsTable, columnAlignment, 0);
        nodColumn.setText(nod);
        nodColumn.setWidth(120);
        String updateName = "Update Name";
        TableColumn updateColumn = new TableColumn(dependencyDetailsTable, columnAlignment, 0);
        updateColumn.setText(updateName);
        updateColumn.setWidth(300);
        GridData twdata = new GridData();
        twdata.grabExcessHorizontalSpace = true;
        twdata.grabExcessVerticalSpace = true;
        twdata.horizontalAlignment = GridData.FILL;
        twdata.verticalAlignment = GridData.FILL;
        tableViewer.setContentProvider(new ArrayContentProvider());
        tableViewer.setLabelProvider(new T24UpdateDetailLabelProvider());
        tableViewer.setInput(new ArrayList<T24UpdateDetails>().toArray());
        
    }

    @Override
    public void setFocus() {
    }

    private void addListeners(Composite parent) {
        ViewListener listener = new ViewListener(parent);
        workItemReferenceNameText.addKeyListener(listener);
        checkBoxTableViewer.addCheckStateListener(new CheckBoxListener());
        showDependencyButton.addMouseListener(listener);
        runSelectedButton.addMouseListener(listener);
        clearViewButton.addMouseListener(listener);
        releaseCombo.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                releaseNumber = releaseCombo.getText();
            }
        });
    }

    private class CheckBoxListener implements ICheckStateListener {

        public void checkStateChanged(CheckStateChangedEvent arg0) {
            Object obj = arg0.getElement();
            if (obj instanceof T24ItemDetail) {
                boolean checked = arg0.getChecked();
                ((T24ItemDetail) obj).setSelected(checked);
            }
        }
    }

    public String getReleaseNumber() {
        return releaseNumber;
    }

    class ViewListener implements MouseListener, KeyListener {

        ViewListener(Composite parent) {
            container = parent;
        }

        public void mouseDoubleClick(MouseEvent arg0) {
        }

        public void mouseDown(MouseEvent arg0) {
        }

        public void mouseUp(MouseEvent e) {
            if (e.button == 1 && e.widget == showDependencyButton) {
                String repositoryUri = getDefaultRepositoryUri();
                if (repositoryUri == null) {
                    T24MessageDialog errorDialog = new T24MessageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getShell(), "Error Dialog",
                            "Not logged into Repository. Log into repository and try the operation again.", MessageDialog.ERROR);
                    errorDialog.open();
                    return;
                }
                int workItemReference = Integer.parseInt(workItemReferenceNameText.getText());
                showDependencyButton.setEnabled(false);
                String relNo = getReleaseNumber();
                if (relNo.equals("R09")) {
                    showMessage();
                    return;
                }
                T24DependencyDetail dependencyDetail = constructTable(workItemReference, relNo, repositoryUri);
                tableInput = dependencyDetail.getItemDetails();
                updateInput = dependencyDetail.getUpdateDetails();
                int sourceItems = CheckDependencyViewHelper.getInstance().getSourceItemCount();
                int dataItems = CheckDependencyViewHelper.getInstance().getDataItemCount();
                int components = CheckDependencyViewHelper.getInstance().getComponentsCount();
                int products = CheckDependencyViewHelper.getInstance().getProductsCount();
                int totUpdates = CheckDependencyViewHelper.getInstance().getTotalUpdates();
                changeSetCountLable.setSize(90, 20);
                changeSetCountLable.setText("Change Sets: " + Integer.toString(getChangeSetCount()));
                sourceItemsCountLable.setSize(90, 20);
                sourceItemsCountLable.setText("Source items : " + Integer.toString(sourceItems));
                dataItemsCountLable.setSize(90, 20);
                dataItemsCountLable.setText("Data items : " + Integer.toString(dataItems));
                componentsCountLable.setText("Components : " + components);
                productsCountLable.setSize(100, 20);
                productsCountLable.setText("Products :" + products);
                totalUpdatesLable.setSize(100, 20);
                totalUpdatesLable.setText("Total Updates : " + Integer.toString(totUpdates));
                checkBoxTableViewer.setInput(tableInput);
                tableViewer.setInput(updateInput);
                for (T24ItemDetail detail : tableInput) {
                    checkBoxTableViewer.setChecked(detail, true);
                }
                if (tableInput.size() > 0) {
                    // releaseCombo.setEnabled(false);
                    runSelectedButton.setEnabled(true);
                    clearViewButton.setEnabled(true);
                } else {
                    // releaseCombo.setEnabled(true);
                    runSelectedButton.setEnabled(false);
                    clearViewButton.setEnabled(false);
                    showDependencyButton.setEnabled(true);
                }
                container.pack();
             // Uncomment the below code if you want Zest view to be displayed .Also uncomment in plugin.xml file.  
             //   showZestView();
                            }
            if (e.button == 1 && e.widget == clearViewButton) {
                clearview();
            }
            if (e.button == 1 && e.widget == runSelectedButton) {
                String relNo = getReleaseNumber();
                if (relNo.equals("R09")) {
                    showMessage();
                    return;
                }
                updateInput = constructUpdateTable(tableInput, relNo);
                tableViewer.setInput(updateInput);
                int totUpdates = CheckDependencyViewHelper.getInstance().getTotalUpdates();
                totalUpdatesLable.setText("Total Updates : " + Integer.toString(totUpdates));
                container.pack();
              // Uncomment the below code if you want Zest view to be displayed .Also uncomment in plugin.xml file.  
                
              //  showZestView();
                                
            }
        }

        private void showMessage() {
            IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            String message = "Currently R09 release not supported ";
            String headerTitle = "Dependency Message Dialog";
            T24MessageDialog messageDialog = new T24MessageDialog(window.getShell(), headerTitle, message,
                    MessageDialog.INFORMATION);
            messageDialog.open();
        }

        public void keyPressed(KeyEvent arg0) {
        }

        public void keyReleased(KeyEvent arg0) {
            if (arg0.widget == workItemReferenceNameText) {
                updateButton();
            }
        }
    }

    private void updateButton() {
        if (isWorkItemReferenceValid()) {
            showDependencyButton.setEnabled(true);
            runSelectedButton.setEnabled(false);
            clearViewButton.setEnabled(false);
        } else {
            showDependencyButton.setEnabled(false);
        }
    }

    private boolean isWorkItemReferenceValid() {
        String workItemReference = workItemReferenceNameText.getText();
        if ((workItemReference.length() <= 0) || (workItemReference.length() > 0 & !isNumeric(workItemReference))) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isNumeric(String num) {
        try {
            Integer.parseInt(num);
        } catch (NumberFormatException nfe) {
            T24MessageDialog errorDialog = new T24MessageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                    "Error Dialog", "Work Item Reference should be numeric", MessageDialog.ERROR);
            errorDialog.open();
            return false;
        }
        return true;
    }

    private void clearview() {
        changeSetCountLable.setText("Change Sets: 0");
        sourceItemsCountLable.setText("Source items :  0");
        dataItemsCountLable.setText("Data items : 0");
        componentsCountLable.setText("Components : 0");
        productsCountLable.setText("Products : 0");
        totalUpdatesLable.setText("Total Updates : 0");
        runSelectedButton.setEnabled(false);
        releaseCombo.setEnabled(true);
        checkBoxTableViewer.setInput(new ArrayList<T24ItemDetail>().toArray());
        tableViewer.setInput(new ArrayList<T24UpdateDetails>().toArray());
    }

    /**
     * populates label names (method name) and defines which columns are to be
     * displayed in table viewer
     */
    private class T24ItemDetailLabelProvider extends LabelProvider implements ITableLabelProvider {

        /**
         * We return null, because we don't support images yet.
         * 
         * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object,
         *      int)
         */
        private Image image;

        public Image getColumnImage(Object element, int columnIndex) {
            if (columnIndex == 0) {
                if (image == null) {
                    image = getImage();
                }
                return image;
            } else {
                return null;
            }
        }

        /**
         * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,
         *      int)
         */
        public String getColumnText(Object element, int columnIndex) {
            if (element instanceof T24ItemDetail) {
                T24ItemDetail itemDetail = (T24ItemDetail) element;
                switch (columnIndex) {
                    case 0:
                        return itemDetail.getItemName();
                    case 1:
                        return itemDetail.getType();
                    case 2:
                        return itemDetail.getProductName();
                    case 3:
                        return itemDetail.getComponentName();
                }
            }
            return "";
        }

        private Image getImage() {
            String imageFile = "/icons/test.png";
            InputStream is = T24ItemDetailLabelProvider.class.getResourceAsStream(imageFile);
            Image image = new Image(null, is);
            return image;
        }
    }

    /**
     * populates label names (method name) and defines which columns are to be
     * displayed in table viewer
     */
    private class T24UpdateDetailLabelProvider extends LabelProvider implements ITableLabelProvider {

        /**
         * We return null, because we don't support images yet.
         * 
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
            if (element instanceof T24UpdateDetails) {
                T24UpdateDetails itemDetail = (T24UpdateDetails) element;
                switch (columnIndex) {
                    case 0:
                        return itemDetail.getUpdateName();
                    case 1:
                        return Integer.toString(itemDetail.getDependencyCount());
                    case 2:
                        return itemDetail.getDependencyList();
                }
            }
            return "";
        }
    }

    private List<T24UpdateDetails> constructUpdateTable(List<T24ItemDetail> input, String relNo) {
        final UpdateInputBuilder runnable = new UpdateInputBuilder(input, relNo);
        try {
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().run(true, true, new IRunnableWithProgress() {

                public void run(IProgressMonitor monitor) {
                    monitor.beginTask("Checking dependency...", IProgressMonitor.UNKNOWN);
                    Thread thread = new Thread(runnable);
                    thread.start();
                    while (!runnable.hasFinished()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (monitor.isCanceled()) {
                            thread.stop();
                            break;
                        }
                    }
                    monitor.done();
                }
            });
        } catch (OperationCanceledException e) {
        } catch (InvocationTargetException e) {
        } catch (InterruptedException e) {
        }
        if (runnable.hasFinished()) {
            return runnable.getInput();
        }
        return null;
    }

    class UpdateInputBuilder implements Runnable {

        private boolean isFinished = false;
        private List<T24ItemDetail> input = null;
        private String releaseNo = "";
        private List<T24UpdateDetails> updateInput = null;

        public UpdateInputBuilder(List<T24ItemDetail> input, String relNo) {
            this.input = input;
            this.releaseNo = relNo;
        }

        public void run() {
            updateInput = CheckDependencyViewHelper.getInstance().getUpdateInput(input, releaseNo);
            isFinished = true;
        }

        public boolean hasFinished() {
            return isFinished;
        }

        public List<T24UpdateDetails> getInput() {
            return updateInput;
        }
    }

    private T24DependencyDetail constructTable(int workItemReference, String relNo, String repositoryUri) {
        final TableInputBuilder runnable = new TableInputBuilder(workItemReference, relNo, repositoryUri);
        try {
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().run(true, true, new IRunnableWithProgress() {

                public void run(IProgressMonitor monitor) {
                    monitor.beginTask("Checking dependency...", IProgressMonitor.UNKNOWN);
                    Thread thread = new Thread(runnable);
                    thread.start();
                    while (!runnable.hasFinished()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (monitor.isCanceled()) {
                            thread.stop();
                            break;
                        }
                    }
                    monitor.done();
                }
            });
        } catch (OperationCanceledException e) {
        } catch (InvocationTargetException e) {
        } catch (InterruptedException e) {
        }
        if (runnable.hasFinished()) {
            return runnable.getInput();
        }
        return null;
    }

    class TableInputBuilder implements Runnable {

        private boolean isFinished = false;
        private int itemReference = 0;
        private String releaseNo = "";
        private String uri = "";
        private T24DependencyDetail input = null;

        public TableInputBuilder(int itemReference, String relNo, String repositoryUri) {
            this.itemReference = itemReference;
            this.releaseNo = relNo;
            this.uri = repositoryUri;
        }

        public void run() {
            ChangeSetInstallManager csInstallManager = new ChangeSetInstallManager(uri, itemReference);
            List<T24ChangeSet> changeSets = new ArrayList<T24ChangeSet>();
            changeSets = csInstallManager.getAllChangeSets();
            changeSetCount = changeSets.size();
            int changeSetCount = changeSets.size();
            InstallableItemCollection itemCollection = null;
            if (changeSetCount > 0) {
                itemCollection = csInstallManager.retriveItems(changeSets);
                input = CheckDependencyViewHelper.getInstance().getItemDetails(itemCollection, releaseNo);
            } else {
                CheckDependencyViewHelper.getInstance().resetCount();
                input = new T24DependencyDetail();
            }
            isFinished = true;
        }

        public boolean hasFinished() {
            return isFinished;
        }

        public T24DependencyDetail getInput() {
            return input;
        }
    }

    public int getChangeSetCount() {
        return changeSetCount;
    }

    /**
     * Returns the default repository uri.
     * 
     * @return default or null in case no repositories available.
     */
    public String getDefaultRepositoryUri() {
        ITeamRepository[] allRepositories = ChangeSetInstallManager.getRepositories();
        if (allRepositories != null && allRepositories.length > 0) {
            return allRepositories[0].getRepositoryURI();
        }
        return null;
    }
    
    
}
