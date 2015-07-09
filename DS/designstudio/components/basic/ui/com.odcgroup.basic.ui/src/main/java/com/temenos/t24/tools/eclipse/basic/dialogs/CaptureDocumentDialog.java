package com.temenos.t24.tools.eclipse.basic.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.temenos.t24.tools.eclipse.basic.document.capture.DocumentCreationSupport;

public class CaptureDocumentDialog extends TitleAreaDialog implements Listener {

    private String product;
    private String component;
    private String document;
    private Text documentText;
    protected Button saveButton;
    private String invalidDoc;
    private String errorMessage;
    Shell parentShell;

    public CaptureDocumentDialog(Shell parentShell, String product, String component, String doc, String errorMessage) {
        super(parentShell);
        this.parentShell = parentShell;
        this.product = product;
        this.component = component;
        this.invalidDoc = doc;
        this.errorMessage = errorMessage;
        setShellStyle(getShellStyle() | SWT.MIN);
    }

    protected void createButtonsForButtonBar(Composite parent) {
        super.createButtonsForButtonBar(parent);
        saveButton = super.getButton(IDialogConstants.OK_ID);
        if (saveButton != null) {
            saveButton.setText("Save");
            saveButton.setEnabled(false);
        }
    }

    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        GridLayout layout = new GridLayout();
        GridData gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;
        gridData.minimumHeight = 400;
        gridData.minimumWidth = 800;
        gridData.heightHint = 400;
        gridData.widthHint = 800;
        layout.numColumns = 1;
        documentText = new Text(container, SWT.H_SCROLL | SWT.V_SCROLL);
        documentText.setLayoutData(gridData);
        if (invalidDoc != null) {
            documentText.setText(invalidDoc);
            setErrorMessage(errorMessage);
        } else {
            setErrorMessage(null);
            String doc = DocumentCreationSupport.getInstance().getDocumentContent(product, component);
            if (doc != null) {
                documentText.setText(doc);
            } else {
                documentText.setText("");
            }
        }
        addListener();
        setTitle("Create Document for " + component);
        container.pack();
        return container;
    }

    private void addListener() {
        documentText.addListener(SWT.KeyUp, this);
    }

    public void handleEvent(Event event) {
        if (event.widget == documentText) {
            document = documentText.getText();
            // if (!isValidDocument(document)) {
            // setErrorMessage("Invalid character encountered in the document.");
            // saveButton.setEnabled(false);
            // } else {
            // saveButton.setEnabled(true);
            // setErrorMessage(null);
            // }
            saveButton.setEnabled(true);
            setErrorMessage(null);
        }
    }

    public String getDocument() {
        return document;
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Add Document Dialog");
    }

    private boolean isValidDocument(String docStr) {
        String allowedChars = "[A-Za-z0-9\\.\\s<>\\\\/\\\n\\-\\_\\[\\]\\(\\)\\;\\:\\?\\+\\=\\!\\&\\*\\@\\{\\}\\'\"\\$\\,]*";
        if (!docStr.matches(allowedChars)) {
            return false;
        }
        return true;
    }
}
