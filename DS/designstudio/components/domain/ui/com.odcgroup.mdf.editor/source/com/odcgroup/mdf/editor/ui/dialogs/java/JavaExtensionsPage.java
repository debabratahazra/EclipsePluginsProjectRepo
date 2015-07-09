package com.odcgroup.mdf.editor.ui.dialogs.java;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.DialogPage;
import com.odcgroup.mdf.ext.java.JavaAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.validation.validator.JavaExtensionValidator;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini </a>
 * @version 1.0
 */
public class JavaExtensionsPage extends DialogPage implements ModifyListener {

    private final MdfDomain domain;
    private Text packageNameText;

    public JavaExtensionsPage(MdfDomain domain) {
        super();
        setTitle("Java");
        setDescription("Edits the Java extensions of this domain.");
        this.domain = domain;
    }

    /**
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent) {
        WidgetFactory factory = getWidgetFactory();

        Composite container = factory.createComposite(parent);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        container.setLayout(new GridLayout(2, false));

        factory.createLabel(container, "Package &name:");
        packageNameText = factory.createText(container, null);
        packageNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        initialize();
        updateStatus();

        packageNameText.addModifyListener(this);

        setControl(container);
    }

    public void doSave(MdfModelElement model) {
        ModelFactory.INSTANCE.removeAnnotations(model, JavaAspect.NAMESPACE_URI);
        addAnnotation(model, JavaAspect.PACKAGE,
                packageNameText.getText().trim());
    }

    /**
     * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
     */
    public void modifyText(ModifyEvent e) {
        setDirty(true);
        updateStatus();
    }

    private void addAnnotation(MdfModelElement model, String name, String value) {
        if (value.length() > 0) {
            MdfAnnotation annot = ModelFactory.INSTANCE.createMdfAnnotation(
                    JavaAspect.NAMESPACE_URI, name, value);
            ModelFactory.INSTANCE.addAnnotation(model, annot);
        }
    }

    /**
     * Tests if the current workbench selection is a suitable projectCombo to
     * use.
     */
    private void initialize() {
        setText(packageNameText, JavaAspect.getPackage(domain));
    }

    private void updateStatus() {
        String name = packageNameText.getText().trim();
        IStatus status = JavaExtensionValidator.validate(name);
        /* 
        // DS-1774 - Error about package mismatch should be removed
        // DS-370
        if (status.isOK()) {
        	status = JavaExtensionValidator.validatePackage(domain, name);
        }
        */
        setStatus(status);
    }
}
