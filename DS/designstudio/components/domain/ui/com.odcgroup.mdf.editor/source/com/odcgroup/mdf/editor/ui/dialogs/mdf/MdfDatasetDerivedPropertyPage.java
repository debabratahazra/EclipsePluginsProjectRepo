package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.PageBook;

import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ecore.util.MdfEcoreUtil;
import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.EntitySelector;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.IContentAssistProvider;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.otf.utils.lang.ISO8601;


public class MdfDatasetDerivedPropertyPage extends MdfModelPage {

	private Group cardinality;
    private Combo defaultEnumValueCombo;
    private Composite defaultTextContainer;
    private EntitySelector typeSelector;
    
    private IContentAssistProvider contentAssistProvider;

    private final MdfDatasetDerivedProperty initialProperty;
    private PageBook defaultPageBook;
    private Text propertyNameText;
    private Text defaultText;
    private Button cardinalityManyCheck;
    private Button cardinalityOneCheck;
    
    /**
     * Override this method if another provider is required.
     * @return a IContentAssistProvider used to select the type of a derived property.
     */
    protected IContentAssistProvider getContentAssistProvider(MdfDatasetDerivedProperty property) {
    	return new MdfDatasetDerivedPropertyTypeProvider(property);
    }

    /**
     * Constructor for MdfDatasetDerivedPropertyPage.
     * 
     * @param pageName
     */
    public MdfDatasetDerivedPropertyPage(MdfDatasetDerivedProperty property) {
        super(property);
        setTitle("Dataset Calculated Attribute");
        setDescription("Edits the core properties of the dataset calculated attribute.");
        this.initialProperty = property;
        this.contentAssistProvider = getContentAssistProvider(initialProperty);
        setWorkingCopy(new WorkingDatasetProperty());
    }

    /**
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent) {
        WidgetFactory factory = getWidgetFactory();

        Composite container = factory.createComposite(parent);
        container.setLayout(new GridLayout(2, false));
        container.setLayoutData(new GridData(GridData.FILL_BOTH));

        factory.createLabel(container, "&Name:");

        propertyNameText = factory.createText(container, null);
        propertyNameText.setEnabled(getEditMode() != MODE_EDIT);
        propertyNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        factory.createLabel(container, "&Type:");
        typeSelector = new EntitySelector(container, factory,
                contentAssistProvider,
                MdfUtility.getDomain(initialProperty).getName());
        typeSelector.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        {
            factory.createLabel(container, "&Default:");

            defaultPageBook = new PageBook(container, SWT.NULL);
            defaultPageBook.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            defaultTextContainer = factory.createComposite(defaultPageBook);
            defaultText = factory.createText(defaultTextContainer, null);
            defaultText.setLayoutData(new GridData(GridData.FILL_BOTH));

            GridLayout layout = new GridLayout(1, false);

            if ((defaultText.getStyle() & SWT.BORDER) == 0) {
                layout.marginHeight = 4;
                layout.marginWidth = 2;
            } else {
                layout.marginHeight = 2;
                layout.marginWidth = 0;
            }

            defaultTextContainer.setLayout(layout);

            defaultEnumValueCombo = factory.createCombo(defaultPageBook);
        }

        Composite container2 = factory.createComposite(container);
        GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
        layoutData.horizontalSpan = 2;
        container2.setLayoutData(layoutData);
        container2.setLayout(new GridLayout(1, true));

        // initialize cardinality group
        {
            cardinality = factory.createGroup(container2, "Multiplicity");
            cardinality.setLayoutData(new GridData(GridData.FILL_BOTH));
            cardinality.setLayout(new GridLayout(1, false));

            cardinalityOneCheck =
                factory.createButton(cardinality, "&One", SWT.RADIO);
            cardinalityManyCheck =
                factory.createButton(cardinality, "&Many", SWT.RADIO);
        }
        
        initialize();
        updateStatus();
        registerListeners();

        setControl(container);
    }

    public void doSave(MdfModelElement element) {
        ModelFactory.INSTANCE.copy((MdfDatasetDerivedProperty) workingCopy,
                (MdfDatasetDerivedProperty) element);
    }

    protected void registerListeners() {
        propertyNameText.addModifyListener(this);
        typeSelector.addModifyListener(this);
        defaultText.addModifyListener(this);
        typeSelector.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                checkDefault();
            }
        });

        defaultEnumValueCombo.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                defaultText.setText(defaultEnumValueCombo.getText());
            }
        });
        cardinalityOneCheck.addSelectionListener(this);
        cardinalityManyCheck.addSelectionListener(this);
    }
    
    private void hideShowMultiplicity(MdfEntity entity) {
    	boolean isDataset = entity instanceof MdfDataset;
    	cardinality.setVisible(isDataset);
    	if (!isDataset) {
    		cardinalityOneCheck.setSelection(true);
    		cardinalityManyCheck.setSelection(false);
    	}
    }

    private void checkDefault() {
        MdfName name = typeSelector.getSelection();
        Resource resource = ((EObject) initialProperty).eResource();
        MdfEntity entity = DomainRepositoryUtil.getMdfEntity(name, resource);

        hideShowMultiplicity(entity);

        defaultEnumValueCombo.clearSelection();
        defaultEnumValueCombo.removeAll();

        if (entity instanceof MdfEnumeration) {
            defaultText.setText("");

            MdfEnumeration enumeration = (MdfEnumeration) entity;
            defaultEnumValueCombo.add("");

            Iterator it = enumeration.getValues().iterator();

            while (it.hasNext()) {
                defaultEnumValueCombo.add(((MdfEnumValue) it.next()).getName());
            }

            defaultEnumValueCombo.select(0);
            defaultPageBook.showPage(defaultEnumValueCombo);
        } else {
            defaultPageBook.showPage(defaultTextContainer);
            manageDateFormat(((MdfDatasetDerivedProperty) workingCopy).getType());
        }
    }

    /**
     * Tests if the current workbench selection is a suitable projectCombo to
     * use.
     */
    private void initialize() {
        setText(propertyNameText, initialProperty.getName());

        MdfEntity type = initialProperty.getType();
        typeSelector.setSelection(type);
        checkDefault();
        setText(defaultEnumValueCombo, initialProperty.getDefault());
        String defaultValue = initialProperty.getDefault();
        SimpleDateFormat format = manageDateFormat(type);

        if (format != null) {
            if ((defaultValue != null) && !defaultValue.equals("today()")
                    && !defaultValue.equals("now()")) {
                try {
                    Date date = ISO8601.parse(defaultValue);
                    defaultValue = format.format(date);
                } catch (ParseException e) {
                    // Ignore
                }
            }
        }
        setText(defaultText, defaultValue);
        
        cardinalityOneCheck.setSelection(
                initialProperty.getMultiplicity() == MdfConstants.MULTIPLICITY_ONE);
        cardinalityManyCheck.setSelection(
                initialProperty.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY);
        
    }

    private SimpleDateFormat manageDateFormat(MdfEntity type) {
        if ((type instanceof EObject) && !((EObject) type).eIsProxy()) {
            SimpleDateFormat format = null;
            StringBuffer tooltip = new StringBuffer();

            if (type.equals(PrimitivesDomain.DATE)) {
                format = (SimpleDateFormat) DateFormat.getDateInstance();
                tooltip.append(format.toLocalizedPattern()).append(" | today()");
            } else if (type.equals(PrimitivesDomain.DATE_TIME)) {
                format = (SimpleDateFormat) DateFormat.getDateTimeInstance();
                tooltip.append(format.toLocalizedPattern()).append(" | now()");
            }

            defaultText.setToolTipText(tooltip.toString());
            return format;
        }

        return null;
    }

    @SuppressWarnings("serial")
	private class WorkingDatasetProperty extends WorkingModelElement implements
            MdfDatasetDerivedProperty {

        public WorkingDatasetProperty() {
            super(initialProperty);
        }

        public int getMultiplicity() {
            if (cardinalityManyCheck.getSelection()) {
                return MdfConstants.MULTIPLICITY_MANY;
            } else {
                return MdfConstants.MULTIPLICITY_ONE;
            }
//        	return MdfConstants.MULTIPLICITY_ONE;
        }

        public String getName() {
            return propertyNameText.getText().trim();
        }

        public MdfDataset getParentDataset() {
            return initialProperty.getParentDataset();
        }

        public MdfEntity getType() {
            return MdfEcoreUtil.getMdfPrimitiveProxy(typeSelector.getSelection());
        }

        public String getDefault() {
            String defaultValue = defaultText.getText().trim();

            if (defaultValue.length() == 0) {
                return null;
            }

            MdfEntity type = getType();

            if (type != null) {
                try {
                    if (type.equals(PrimitivesDomain.DATE)
                            && !defaultValue.equals("today()")) {
                        Date date = DateFormat.getDateInstance().parse(
                                defaultValue);
                        defaultValue = ISO8601.format(date);
                    } else if (type.equals(PrimitivesDomain.DATE_TIME)
                            && !defaultValue.equals("now()")) {
                        Date date = DateFormat.getDateTimeInstance().parse(
                                defaultValue);
                        defaultValue = ISO8601.format(date);
                    }
                } catch (ParseException e) {
                }
            }

            return defaultValue;
        }
    }
}
