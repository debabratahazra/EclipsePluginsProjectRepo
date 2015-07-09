package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.ecore.util.MdfEcoreUtil;
import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.EntitySelector;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.IContentAssistProvider;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;


public class MdfAssociationPage extends MdfModelPage {
	
	private Button byReferenceCheck;
    private Button primaryKeyCheck;
    private Button byValueCheck;
    private Button cardinalityManyCheck;
    private Button cardinalityOneCheck;
    private Button businessKeyCheck;
    private Button requiredCheck;
    private final MdfAssociation initialAssociation;
    private EntitySelector typeSelector;
    private final IContentAssistProvider contentAssistProvider =
        new IContentAssistProvider() {
            public MdfModelElement[] getCandidates() {
                return getCandidateTypes();
            }

            public String getDefaultDomainName() {
                return MdfUtility.getDomain(initialAssociation).getName();
            }
        };

    private Text associationNameText;

    /**
     * Constructor for SampleNewWizardPage.
     *
     * @param pageName
     */
    public MdfAssociationPage(MdfAssociation assoc) {
        super(assoc);
        setTitle("Association");
        setDescription("Edits the core properties of the association.");
        this.initialAssociation = assoc;
        setWorkingCopy(new WorkingAssociation());
    }

    /**
     * @throws CoreException
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent) {
        WidgetFactory factory = getWidgetFactory();

        Composite container = factory.createComposite(parent);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        container.setLayout(new GridLayout(2, false));

        factory.createLabel(container, "&Name:");

        associationNameText = factory.createText(container, null);
        associationNameText.setEnabled(getEditMode() != MODE_EDIT);
        associationNameText.setLayoutData(
            new GridData(GridData.FILL_HORIZONTAL));

        factory.createLabel(container, "&Type:");
        typeSelector =
            new EntitySelector(
                container, factory, contentAssistProvider,
                MdfUtility.getDomain(initialAssociation).getName());
        typeSelector.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Composite container2 = factory.createComposite(container);
        GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
        layoutData.horizontalSpan = 2;
        container2.setLayoutData(layoutData);
        container2.setLayout(new GridLayout(3, true));

        {
            Group modifiers = factory.createGroup(container2, "Modifiers");
            modifiers.setLayoutData(new GridData(GridData.FILL_BOTH));
            modifiers.setLayout(new GridLayout(1, false));

            primaryKeyCheck = factory.createButton(modifiers, "&Primary Key",
                    SWT.CHECK);
            businessKeyCheck =
                factory.createButton(modifiers, "&Business Key", SWT.CHECK);
            requiredCheck =
                factory.createButton(modifiers, "Re&quired", SWT.CHECK);
        }

        {
            Group containment = factory.createGroup(container2, "Containment");
            containment.setLayoutData(new GridData(GridData.FILL_BOTH));
            containment.setLayout(new GridLayout(1, false));

            byValueCheck =
                factory.createButton(containment, "By &Value", SWT.RADIO);
            byReferenceCheck =
                factory.createButton(containment, "By Re&ference", SWT.RADIO);
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
        ModelFactory.INSTANCE.copy(
            (MdfAssociation) workingCopy, (MdfAssociation) element);
    }

    protected void registerListeners() {
        associationNameText.addModifyListener(this);
        typeSelector.addModifyListener(this);
        byValueCheck.addSelectionListener(this);
        byReferenceCheck.addSelectionListener(this);
        cardinalityOneCheck.addSelectionListener(this);
        cardinalityManyCheck.addSelectionListener(this);
        requiredCheck.addSelectionListener(this);
        businessKeyCheck.addSelectionListener(this);
        primaryKeyCheck.addSelectionListener(this);
    }
   
    public void widgetSelected(SelectionEvent e) {
        super.widgetSelected(e);

        if (e.getSource() == businessKeyCheck) {
            if (businessKeyCheck.getSelection()) {
                requiredCheck.setSelection(true);
                requiredCheck.setEnabled(false);
            } else {
                requiredCheck.setEnabled(true);
            }
        }
    }

    private MdfModelElement[] getCandidateTypes() {
    	Resource resource = ((EObject) initialAssociation).eResource();
    	List<MdfEntity> candidates = DomainRepositoryUtil.getMdfClassesFromProxy(resource);
        return (MdfModelElement[]) candidates.toArray(new MdfModelElement[candidates.size()]);
    }

    private void initialize() {
        setText(associationNameText, initialAssociation.getName());

        typeSelector.setSelection(initialAssociation.getType());

        byReferenceCheck.setSelection(
            initialAssociation.getContainment() == MdfConstants.CONTAINMENT_BYREFERENCE);
        byValueCheck.setSelection(
            initialAssociation.getContainment() == MdfConstants.CONTAINMENT_BYVALUE);

        cardinalityOneCheck.setSelection(
            initialAssociation.getMultiplicity() == MdfConstants.MULTIPLICITY_ONE);
        cardinalityManyCheck.setSelection(
            initialAssociation.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY);

        requiredCheck.setSelection(initialAssociation.isRequired());
        businessKeyCheck.setSelection(initialAssociation.isBusinessKey());
        primaryKeyCheck.setSelection(initialAssociation.isPrimaryKey());
    }

    private class WorkingAssociation extends WorkingModelElement
        implements MdfAssociation {
        public WorkingAssociation() {
            super(initialAssociation);
        }

        public int getContainment() {
            if (byReferenceCheck.getSelection()) {
                return MdfConstants.CONTAINMENT_BYREFERENCE;
            } else {
                return MdfConstants.CONTAINMENT_BYVALUE;
            }
        }

        public int getMultiplicity() {
            if (cardinalityManyCheck.getSelection()) {
                return MdfConstants.MULTIPLICITY_MANY;
            } else {
                return MdfConstants.MULTIPLICITY_ONE;
            }
        }

        public String getName() {
            return associationNameText.getText().trim();
        }

        public MdfClass getParentClass() {
            return initialAssociation.getParentClass();
        }

        public boolean isPrimaryKey() {
            return primaryKeyCheck.getSelection();
        }

        public boolean isRequired() {
            return requiredCheck.getSelection();
        }

        public MdfReverseAssociation getReverse() {
            return initialAssociation.getReverse();
        }

        public MdfEntity getType() {
            return MdfEcoreUtil.getMdfClassProxy(typeSelector.getSelection());
        }

		public boolean isBusinessKey() {
			return businessKeyCheck.getSelection();
		}

		// TODO to be removed at the end of the mdf-api/core split
		public boolean isDeprecated() { return false; }
		public EObject getDeprecationInfo() { return null; }
        public int getVisibility() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }
        public int getAccess() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }

    }
}
