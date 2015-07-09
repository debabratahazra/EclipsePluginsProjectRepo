package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import com.odcgroup.domain.annotations.AAAAspectDS;
import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ecore.util.MdfEcoreUtil;
import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.EntitySelector;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.IContentAssistProvider;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.otf.utils.lang.ISO8601;


public class MdfAttributePage extends MdfModelPage {

    private Button primaryKeyCheck;
    private Button businessKeyCheck;
    private Button cardinalityManyCheck;
    private Button cardinalityOneCheck;
    private Button requiredCheck;
    private Combo defaultEnumValueCombo;
    private Composite defaultTextContainer;
    private EntitySelector typeSelector;
    private final IContentAssistProvider contentAssistProvider = new IContentAssistProvider() {

        public MdfModelElement[] getCandidates() {
            return getCandidateTypes();
        }

        public String getDefaultDomainName() {
            return MdfUtility.getDomain(initialAttribute).getName();
        }
    };

    private final MdfAttribute initialAttribute;
    private PageBook defaultPageBook;
    private Text attributeNameText;
    private Text defaultText;

    /**
     * Constructor for SampleNewWizardPage.
     * 
     * @param pageName
     */
    public MdfAttributePage(MdfAttribute attr) {
        super(attr);
        setTitle("Attribute");
        setDescription("Edits the core properties of the attribute.");
        this.initialAttribute = attr;

        setWorkingCopy(new WorkingAttribute());
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

        attributeNameText = factory.createText(container, null);
        attributeNameText.setEnabled(getEditMode() != MODE_EDIT);
        attributeNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        factory.createLabel(container, "&Type:");
        typeSelector = new EntitySelector(container, factory,
                contentAssistProvider,
                MdfUtility.getDomain(initialAttribute).getName());
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
        container2.setLayout(new GridLayout(2, true));

        {
            Group modifiers = factory.createGroup(container2, "Modifiers");
            modifiers.setLayoutData(new GridData(GridData.FILL_BOTH));
            modifiers.setLayout(new GridLayout(1, false));

            primaryKeyCheck = factory.createButton(modifiers, "&Primary Key",
                    SWT.CHECK);
            businessKeyCheck = factory.createButton(modifiers, "&Business Key", SWT.CHECK);
            requiredCheck = factory.createButton(modifiers, "Re&quired",
                    SWT.CHECK);
        }

        {
            Group cardinality = factory.createGroup(container2, "Multiplicity");
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
        ModelFactory.INSTANCE.copy((MdfAttribute) workingCopy,
                (MdfAttribute) element);
    }

    protected void registerListeners() {
        attributeNameText.addModifyListener(this);
        typeSelector.addModifyListener(this);
        defaultText.addModifyListener(this);
        primaryKeyCheck.addSelectionListener(this);
        businessKeyCheck.addSelectionListener(this);
        requiredCheck.addSelectionListener(this);
        cardinalityOneCheck.addSelectionListener(this);
        cardinalityManyCheck.addSelectionListener(this);

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

        cardinalityManyCheck.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
               // checkDefault();
            }
        });

        cardinalityOneCheck.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
               // checkDefault();
            }
        });
        
    }

    private MdfModelElement[] getCandidateTypes() {
        List candidates = new ArrayList();
        Resource resource = ((EObject) initialAttribute).eResource();
        List<MdfEntity> entities = DomainRepositoryUtil.getAllMdfPrimitives(resource);
        candidates.addAll(entities);
        return (MdfModelElement[]) candidates.toArray(new MdfModelElement[candidates.size()]);
    }

    private void checkDefault() {
        MdfName name = typeSelector.getSelection();
        Resource res = ((EObject) initialAttribute).eResource();
        MdfEntity entity = DomainRepositoryUtil.getMdfEntity(name, res);

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
            requiredCheck.setSelection(true);
            defaultPageBook.showPage(defaultEnumValueCombo);
        } else {
            defaultPageBook.showPage(defaultTextContainer);
            manageDateFormat(((MdfAttribute) workingCopy).getType());
        }
        
       	setAnnotaitons(entity);
    }

    /**
     * Tests if the current workbench selection is a suitable projectCombo to
     * use.
     */
    private void initialize() {
        setText(attributeNameText, initialAttribute.getName());
        MdfEntity type = initialAttribute.getType();
        typeSelector.setSelection(type);
        checkDefault();
        primaryKeyCheck.setSelection(initialAttribute.isPrimaryKey());
        businessKeyCheck.setSelection(initialAttribute.isBusinessKey());
        requiredCheck.setSelection(initialAttribute.isRequired());
        setText(defaultEnumValueCombo, initialAttribute.getDefault());

        String defaultValue = initialAttribute.getDefault();
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

        cardinalityOneCheck.setSelection(initialAttribute.getMultiplicity() == MdfConstants.MULTIPLICITY_ONE);
        cardinalityManyCheck.setSelection(initialAttribute.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY);
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
	private class WorkingAttribute extends WorkingModelElement implements
            MdfAttribute {

        public WorkingAttribute() {
            super(initialAttribute);
        }

        public boolean isPrimaryKey() {
            return primaryKeyCheck.getSelection();
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

        public int getMultiplicity() {
            if (cardinalityManyCheck.getSelection()) {
                return MdfConstants.MULTIPLICITY_MANY;
            }

            return MdfConstants.MULTIPLICITY_ONE;
        }

        public String getName() {
            return attributeNameText.getText().trim();
        }

        public MdfClass getParentClass() {
            return initialAttribute.getParentClass();
        }

        public boolean isRequired() {
            return requiredCheck.getSelection();
        }

        public MdfEntity getType() {
            return MdfEcoreUtil.getMdfPrimitiveProxy(typeSelector.getSelection());
        }


		public boolean isBusinessKey() {
			return businessKeyCheck.getSelection();
		}

		// TODO to be removed at the end of the mdf-api/core split
//		public boolean isDeprecated() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }
//		public MdfDeprecationInfo getDeprecationInfo() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }
//        public int getAccess() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }
//        public int getVisibility() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }
       
        
    }
    /**
     * set the annotation for the attribute based on the attribute type
     * @param entity
     */
    protected void setAnnotaitons(MdfEntity entity){
    	if(entity instanceof MdfBusinessType){
    		String typeValue = AAAAspectDS.getTripleABusinessType((MdfBusinessType)entity);
    		if(typeValue != null) {
    			AAAAspectDS.setAAAParamsType(((MdfAttribute) workingCopy), typeValue);
    		}
    	} else if(entity instanceof MdfEnumeration){
    		MdfEnumeration enumeration = (MdfEnumeration) entity;
    		if(enumeration.isPrimitiveType() && enumeration.getType().getName().equals("boolean")){
    			AAAAspectDS.setAAAParamsType(((MdfAttribute) workingCopy), "flag_t");
			}else if (enumeration.isPrimitiveType() && enumeration.getType().getName().equals("EnumMask")) {
				AAAAspectDS.setAAAParamsType(((MdfAttribute) workingCopy), "enummask_t");
			}else {
				AAAAspectDS.setAAAParamsType(((MdfAttribute) workingCopy), "enum_t");
			}
    	} else {
    		String parmType = AAAAspectDS.getAAAParamsType(((MdfAttribute) workingCopy));
    		if(parmType != null){
    			AAAAspectDS.removeAAAParamsTypeAnnotation(((MdfAttribute) workingCopy));
    		}
    	}
    }
}
