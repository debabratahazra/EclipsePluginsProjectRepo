package com.temenos.t24.tools.eclipse.basic.dialogs;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.temenos.t24.tools.eclipse.basic.document.capture.DocumentCharConverter;
import com.temenos.t24.tools.eclipse.basic.document.capture.DocumentCreationSupport;
import com.temenos.t24.tools.eclipse.basic.document.capture.DocumentHtmlValidator;
import com.temenos.t24.tools.eclipse.basic.document.capture.InvalidDocumentException;
import com.temenos.t24.tools.eclipse.basic.views.document.DocumentTreeViewInitialiser;
import com.temenos.t24.tools.eclipse.basic.views.document.DocumentViewConstants;

public class CreateDocumentDialog extends Dialog {

    private Shell parentShell;
    private Combo comboProducts;
    private Combo comboComponents;
    private Label labelProducts;
    private Label labelComponents;
    private String selectedComponent;
    private String selectedProduct;
    private String productsPath=DocumentViewConstants.PRODUCT_XPATH+"*";

    public CreateDocumentDialog(Shell parentShell, String selectedComponent, String selectedProduct) {
        super(parentShell);
        this.parentShell = parentShell;
        this.selectedComponent = selectedComponent;
        this.selectedProduct = selectedProduct;
        setShellStyle(getShellStyle());
    }

    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, "Done", true);
    }

    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        Label label = new Label(container, SWT.NONE);
        label.setText("Select the required product and component :");
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        labelProducts = new Label(container, SWT.NONE);
        labelProducts.setText("Product: ");
        comboProducts = new Combo(container, SWT.NONE | SWT.READ_ONLY);
        GridData gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;
        gridData.minimumWidth = 100;
        comboProducts.setLayoutData(gridData);
        List<String> products = DocumentTreeViewInitialiser.getInstance().getProducts(productsPath);
        String[] items = new String[products.size()];
        comboProducts.setItems(products.toArray(items));
        labelComponents = new Label(container, SWT.NONE);
        labelComponents.setText("Component: ");
        comboComponents = new Combo(container, SWT.NONE | SWT.READ_ONLY);
        comboComponents.setEnabled(false);
        gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;
        gridData.minimumWidth = 100;
        comboComponents.setLayoutData(gridData);
        addListener();
        if (selectedProduct != null) {
            comboProducts.setText(selectedProduct);
            String[] components = DocumentTreeViewInitialiser.getInstance().getComponents(selectedProduct);
            comboComponents.setItems(components);
            comboComponents.setEnabled(true);
            if (selectedComponent != null) {
                comboComponents.setText(selectedComponent);
            }
        }
        container.pack();
        return container;
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Choose Component");
    }

    private void addListener() {
        comboProducts.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                String selectedElement = comboProducts.getText();
                if (comboProducts.getSelectionIndex() != -1) {
                    comboComponents.setEnabled(true);
                    String[] components = DocumentTreeViewInitialiser.getInstance().getComponents(selectedElement);
                    comboComponents.setItems(components);
                }
            }
        });
        comboComponents.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                String product = comboProducts.getText();
                String component = comboComponents.getText();
                String doc = null;
                String errorMessage = null;
                if (product != null && component != null && product.length() > 0 && component.length() > 0) {
                    do {
                        CaptureDocumentDialog dialog = new CaptureDocumentDialog(parentShell, product, component, doc, errorMessage);
                        if (dialog.open() == InputDialog.OK) {
                            doc = dialog.getDocument();
                            boolean valid = true;
                            try {
                                doc = validateDoc(doc);
                            } catch (InvalidDocumentException ide) {
                                valid = false;
                                errorMessage = ide.getMessage();
                            }
                            if (valid) {
                                DocumentCreationSupport.getInstance().updateDocument(product, component, doc);
                                break;
                            }
                        } else {
                            // cancel / close of dialog
                            break;
                        }
                    } while (true);
                }
            }
        });
    }

    private String validateDoc(String document) throws InvalidDocumentException {
        String doc = DocumentCharConverter.newInstance().convert(document);
        DocumentHtmlValidator.newInstance().parse(doc);
        return doc;
    }
}
