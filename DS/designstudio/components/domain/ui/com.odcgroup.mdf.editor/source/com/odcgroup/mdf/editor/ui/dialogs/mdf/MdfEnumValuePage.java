package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.mdf.ecore.MdfEnumerationImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.otf.utils.lang.ISO8601;


public class MdfEnumValuePage extends MdfModelPage {
    private final MdfEnumValue initialValue;
    private Text valueNameText;
    private Text valueText;

    /**
     * Constructor for SampleNewWizardPage.
     * @param pageName
     */
    public MdfEnumValuePage(MdfEnumValue value) {
        super(value);
        setTitle("Enumerated Value");
        setDescription("Edits the core properties of the value.");
        this.initialValue = value;
        setWorkingCopy(new WorkingEnumValue());
    }

    /**
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent) {
        WidgetFactory factory = getWidgetFactory();

        Composite container = factory.createComposite(parent);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        container.setLayout(new GridLayout(2, false));

        factory.createLabel(container, "&Name:");

        valueNameText = factory.createText(container, null);
        valueNameText.setEnabled(getEditMode() != MODE_EDIT);
        valueNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        factory.createLabel(container, "&Value:");

        valueText = factory.createText(container, null);
        valueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        initialize();
        updateStatus();

        this.registerListeners();

        setControl(container);
    }

    public void doSave(MdfModelElement element) {
        MdfEnumValue eValue = (MdfEnumValue) element;

        ModelFactory.INSTANCE.copy((MdfEnumValue) workingCopy, eValue);

        if (getEditMode() == MODE_CREATE) {
            MdfEnumerationImpl e = (MdfEnumerationImpl) eValue.getParentEnumeration();
            e.getValues().add(eValue);
        }
    }

    protected void registerListeners() {
        valueNameText.addModifyListener(this);
        valueText.addModifyListener(this);
    }

    /**
     * Tests if the current workbench selection is a suitable
     * projectCombo to use.
     */
    private void initialize() {
        setText(valueNameText, initialValue.getName());

        MdfPrimitive type = initialValue.getParentEnumeration().getType();
        String value = initialValue.getValue();
        
        SimpleDateFormat format = manageDateFormat(type);
        
        if (format != null) {
            if (value != null) {
                try {
                    Date date = ISO8601.parse(value);
                    value = format.format(date);
                } catch (ParseException e) {
                    // Ignore
                }
            }
        }

        setText(valueText, value);
    }
    
    private SimpleDateFormat manageDateFormat(MdfEntity type) {
        if (type != null) {
            SimpleDateFormat format = null;
            StringBuffer tooltip = new StringBuffer();

            if (type.equals(PrimitivesDomain.DATE)) {
                format = (SimpleDateFormat) DateFormat.getDateInstance();
                tooltip.append(format.toLocalizedPattern());
            } else if (type.equals(PrimitivesDomain.DATE_TIME)) {
                format = (SimpleDateFormat) DateFormat.getDateTimeInstance();
                tooltip.append(format.toLocalizedPattern());
            }

            valueText.setToolTipText(tooltip.toString());
            return format;
        }
        
        return null;
    }

    @SuppressWarnings("serial")
	private class WorkingEnumValue extends WorkingModelElement
        implements MdfEnumValue {
        public WorkingEnumValue() {
            super(initialValue);
        }

        public String getName() {
            return valueNameText.getText().trim();
        }

        public MdfEnumeration getParentEnumeration() {
            return initialValue.getParentEnumeration();
        }

        public String getValue() {
            String defaultValue = valueText.getText().trim();

            if (defaultValue.length() == 0) {
                return null;
            }

            MdfEntity type = getParentEnumeration().getType();
            if (type != null) {
                try {
                    if (type.equals(PrimitivesDomain.DATE)) {
                        Date date = DateFormat.getDateInstance().parse(defaultValue);
                        defaultValue = ISO8601.format(date);
                    } else if (type.equals(PrimitivesDomain.DATE_TIME)) {
                        Date date = DateFormat.getDateTimeInstance().parse(defaultValue);
                        defaultValue = ISO8601.format(date);
                    }
                } catch (ParseException e) {
                }
            }

            return defaultValue;
        }

		// TODO to be removed at the end of the mdf-api/core split
		public boolean isDeprecated() { return false; }
		public EObject getDeprecationInfo() { return null; }
    }
}
