package com.odcgroup.mdf.editor.ui.dialogs.sql;

import org.eclipse.core.runtime.Status;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.DialogPage;
import com.odcgroup.mdf.ext.sql.SQLAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.validation.validator.JavaExtensionValidator;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini </a>
 * @version 1.0
 */
public class SQLExtensionsPage extends DialogPage implements ModifyListener {

	private static final int DEFAULT_MAX_NAME_LENGTH = 30;
    private final MdfModelElement element;
    private Text sqlNameText;

    public SQLExtensionsPage(MdfModelElement element) {
        super();
        setTitle("SQL");
        setDescription("Edits the SQL extensions of this element.");
        this.element = element;
    }

    /**
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent) {
        WidgetFactory factory = getWidgetFactory();

        Composite container = factory.createComposite(parent);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        container.setLayout(new GridLayout(2, false));

        String label = "SQL &name";

        factory.createLabel(container, label);
        sqlNameText = factory.createText(container, null);
        sqlNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        sqlNameText.setTextLimit(DEFAULT_MAX_NAME_LENGTH);

        initialize();
        updateStatus();

        sqlNameText.addModifyListener(this);

        setControl(container);
    }

    public void doSave(MdfModelElement model) {
        ModelFactory.INSTANCE.removeAnnotations(model, SQLAspect.NAMESPACE_URI);
        addAnnotation(model, SQLAspect.SQL_NAME, sqlNameText.getText().trim());
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
                    SQLAspect.NAMESPACE_URI, name, value);
            ModelFactory.INSTANCE.addAnnotation(model, annot);
        }
    }

    /**
     * Tests if the current workbench selection is a suitable projectCombo to
     * use.
     */
    private void initialize() {
        setText(sqlNameText, SQLAspect.getSqlName(element));
    }

    private void updateStatus() {
        String name = sqlNameText.getText().trim();
        if (name.length() > 0) {
            setStatus(JavaExtensionValidator.validate(name));
        } else {
            setStatus(Status.OK_STATUS);
        }
    }
}
