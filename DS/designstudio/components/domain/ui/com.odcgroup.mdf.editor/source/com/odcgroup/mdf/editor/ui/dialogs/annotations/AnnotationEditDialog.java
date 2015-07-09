package com.odcgroup.mdf.editor.ui.dialogs.annotations;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.FormWidgetFactory;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfModelElement;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class AnnotationEditDialog extends Dialog {
    private static final String DEFAULT_WINDOW_TITLE = MdfPlugin
            .getResourceString("annotations.editDialog.defaultTitle");
    private String annotationName = "";
    private String annotationNameSpace = "";
    private String windowTitle = DEFAULT_WINDOW_TITLE;
    private Text annotationNameText = null;
    private Text annotationNamespaceText = null;
    
    private Composite body = null;
    private MdfModelElement element = null;

    /**
     * Constructor for AnnotationEditDialog
     */
    public AnnotationEditDialog(Shell parentShell, MdfModelElement element) {
        super(parentShell);
        this.element = element;
    }

    public void setAnnotationName(String annotationName) {
        this.annotationName = annotationName;
    }

    public String getAnnotationName() {
        return annotationName;
    }

    public void setAnnotationNameSpace(String annotationNameSpace) {
        this.annotationNameSpace = annotationNameSpace;
    }

    public String getAnnotationNameSpace() {
        return annotationNameSpace;
    }

    public void setWindowTitle(String title) {
        this.windowTitle = title;
        updateWindowTitle();
    }

    protected Point getInitialSize() {
        return new Point(400, 350);
    }

    protected Control createContents(Composite parent) {
        Control ctrl = super.createContents(parent);
        update();
        return ctrl;
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    protected Control createDialogArea(Composite parent) {
        final WidgetFactory factory = new FormWidgetFactory();

        body = factory.createComposite(parent);
        body.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 10;
		layout.marginWidth = 15;
		body.setLayout(layout);
        

        factory.createLabel(body, MdfPlugin
                .getResourceString("annotations.editDialog.nameLabel.text"));

        annotationNameText = factory.createText(body, null);

        GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
        annotationNameText.setLayoutData(layoutData);
        annotationNameText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                update();
            }
        });
        annotationNameText.setText(annotationName);

        Label label = factory.createLabel(body, MdfPlugin
                .getResourceString("annotations.editDialog.namespaceLabel.text"));
        label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

    	annotationNamespaceText = factory.createText(body, null);
    	layoutData = new GridData(GridData.FILL_HORIZONTAL);
        annotationNamespaceText.setLayoutData(layoutData);
        annotationNamespaceText.setText(annotationNameSpace);

        updateWindowTitle();
        factory.dispose();
        return body;
    }
    
    /**
     * @see org.eclipse.jface.dialogs.Dialog#okPressed()
     */
    protected void okPressed() {
        annotationName = annotationNameText.getText().trim();
        annotationNameSpace = annotationNamespaceText.getText();
        if (checkAnnotationExists(annotationNameSpace, annotationName)) {
        	String message = "Annotation with namespace URI["+annotationNameSpace+"] " +
				"and name["+annotationName+"] already exists!!!";
        	MessageDialog.openError(getParentShell(), windowTitle, message);
        	return;
        }
        super.okPressed();
    }
    
    private boolean checkAnnotationExists(String namespace, String name) {
    	MdfAnnotation annot = element.getAnnotation(namespace, name);
    	if (annot == null) {
    		return false;
    	}
    	return true;
    }

    protected void update() {
        Button okButton = getButton(OK);

        if (okButton != null) {
            String name = annotationNameText.getText().trim();
            okButton.setEnabled(!name.equals(""));
        }
    }

    protected void updateWindowTitle() {
        if (getShell() == null) {
            // Not created yet
            return;
        }

        if (windowTitle == null) {
            windowTitle = DEFAULT_WINDOW_TITLE;
        }

        getShell().setText(windowTitle);
    }
}
