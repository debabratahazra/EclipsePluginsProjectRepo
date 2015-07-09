package com.temenos.t24.tools.eclipse.basic.wizards.data;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.temenos.t24.tools.eclipse.basic.remote.data.UnsupportedCharConversion;

@SuppressWarnings("restriction")
public class T24DataFileCreationPage extends WizardPage implements Listener {

    private IStructuredSelection selection;
    private String location="";
    private String dataRecordKey="";
    private Text dataFileNameText;
    private Text dataFileKeyText;
    private Text convertedKeyText;
    private Composite container;
    private static String DATA_FILE_PREFIX = "F.";
    private static String DATA_FILE_SUEFIX = ".d";
    private static String DATA_FILE_DELIMITER = "!";

    public T24DataFileCreationPage(IStructuredSelection selection) {
        super("T24DataFileCreationWizard");
        super.setDescription("Create T24 Data file");
        this.selection = selection;
    }

    public void handleEvent(Event event) {
        if (event.widget == dataFileNameText || event.widget == dataFileKeyText) {
            String fileName = dataFileNameText.getText().toUpperCase();
            String key = dataFileKeyText.getText();
            if (fileName != "") {
                if (!fileName.startsWith(DATA_FILE_PREFIX.toUpperCase())) {
                    fileName = DATA_FILE_PREFIX + fileName;
                }
                String dataFileName = fileName + DATA_FILE_DELIMITER + key;
                dataRecordKey = UnsupportedCharConversion.convertToRTC(dataFileName) + DATA_FILE_SUEFIX ;
                convertedKeyText.setText(dataRecordKey);
            } else {
                convertedKeyText.setText("");
            }
        }
        setPageComplete(isPageComplete());
    }

    public void createControl(Composite parent) {
        container = parent;
        drawLayouts(parent);
    }

    private void drawLayouts(Composite parent) {
        final int TEXT_MIN_WIDTH = 350;
        final String DATA_FILE_TOOLTIP = "T24 Data file name e.g F.COMPANY";
        final String DATA_KEY_TOOLTIP = "T24 Data record id eg US-001*0001";
        GridLayout gridLayout0 = new GridLayout();
        gridLayout0.makeColumnsEqualWidth = true;
        GridData gridData0 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData0.grabExcessHorizontalSpace = true;
        container.setLayout(gridLayout0);
        container.setLayoutData(gridData0);
        //
        Composite child = new Composite(container, SWT.NORMAL);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        child.setLayout(gridLayout);
        //
        GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.grabExcessHorizontalSpace = true;
        gridData.minimumWidth = TEXT_MIN_WIDTH;
        gridData.horizontalIndent = 10;
        //
        Label dataFileNameLabel = new Label(child, SWT.NORMAL);
        dataFileNameLabel.setText("T24 Data file name");
        dataFileNameLabel.setToolTipText(DATA_FILE_TOOLTIP);
        dataFileNameText = new Text(child, SWT.BORDER);
        dataFileNameText.setLayoutData(gridData);
        //
        Label dataFileKeyLabel = new Label(child, SWT.NORMAL);
        dataFileKeyLabel.setText("T24 Data Record key");
        dataFileKeyLabel.setToolTipText(DATA_KEY_TOOLTIP);
        dataFileKeyText = new Text(child, SWT.BORDER);
        dataFileKeyText.setLayoutData(gridData);
        Label dataFileKeyDiaplayLabel = new Label(child, SWT.NORMAL);
        dataFileKeyDiaplayLabel.setText("Converted Data Record key");
        convertedKeyText = new Text(child, SWT.BORDER);
        convertedKeyText.setLayoutData(gridData);
        convertedKeyText.setEditable(false);
        container.pack();
        setControl(container);
        addListeners();
        initWizard();
    }

    private void addListeners() {
        dataFileNameText.addListener(SWT.KeyUp, this);
        dataFileKeyText.addListener(SWT.KeyUp, this);
    }

    private void initWizard() {
        if (selection == null || !(selection instanceof IStructuredSelection)) {
            return;
        }
        Object itemSelected = selection.getFirstElement();
        if (itemSelected instanceof IProject) {
            location = ((IProject) itemSelected).getLocation().toOSString();
        } else if (itemSelected instanceof IFolder) {
            location = ((IFolder) itemSelected).getLocation().toOSString();
        } else if (itemSelected instanceof PackageFragment) {
            location = ((IPackageFragment) itemSelected).getResource().getLocation().toOSString();
        } else if (itemSelected instanceof PackageFragmentRoot) {
            location = ((IPackageFragmentRoot) itemSelected).getResource().getLocation().toOSString();
        }
    }


    /**
     * If there is no error, set page complete as true
     */
    public boolean isPageComplete() {
        if (isInputComplete() && IsInputValid()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean IsInputValid() {
        // TODO check if its valid. for eg you should not have F.a!.d
        return true;
    }

    private boolean isInputComplete() {
        return (!dataFileKeyText.getText().equals("") && convertedKeyText.toString() != "" && location != "");
    }

    
    public String getLocation() {
        return location;
    }

    
    public String getDataRecordKey() {
        return dataRecordKey;
    }

}
