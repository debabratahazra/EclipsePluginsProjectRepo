package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;


public class MdfReverseAssociationPage extends MdfModelPage {

    private Button cardinalityManyCheck;
    private Button cardinalityOneCheck;
    private final MdfReverseAssociation initialReverseAssociation;
    private Text associationNameText;

    /**
     * Constructor for SampleNewWizardPage.
     * 
     * @param pageName
     */
    public MdfReverseAssociationPage(MdfReverseAssociation reverse) {
        super(reverse);
        setTitle("Reverse Association");
        setDescription("Edits the core properties of the reverse association.");
        this.initialReverseAssociation = reverse;

        setWorkingCopy(new WorkingReverseAssociation());
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

        associationNameText = factory.createText(container, null);
        associationNameText.setEnabled(getEditMode() != MODE_EDIT);
        associationNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Composite container2 = factory.createComposite(container);
        GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
        layoutData.horizontalSpan = 2;
        container2.setLayoutData(layoutData);

        GridLayout layout = new GridLayout(2, true);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.verticalSpacing = 10;
        container2.setLayout(layout);

        {
            Group cardinality = factory.createGroup(container2, "Cardinality");
            layoutData = new GridData(GridData.FILL_BOTH);
            layoutData.horizontalSpan = 2;
            cardinality.setLayoutData(layoutData);
            cardinality.setLayout(new GridLayout(1, false));

            cardinalityOneCheck = factory.createButton(cardinality, "&One",
                    SWT.RADIO);
            cardinalityManyCheck = factory.createButton(cardinality, "&Many",
                    SWT.RADIO);
        }

        initialize();
        updateStatus();

        associationNameText.addModifyListener(this);
        cardinalityOneCheck.addSelectionListener(this);
        cardinalityManyCheck.addSelectionListener(this);

        setControl(container);
    }

    public void doSave(MdfModelElement element) {
        ModelFactory.INSTANCE.copy((MdfReverseAssociation) workingCopy,
                (MdfReverseAssociation) element);
    }

    /**
     * Tests if the current workbench selection is a suitable projectCombo to
     * use.
     */
    private void initialize() {
        setText(associationNameText, initialReverseAssociation.getName());

        cardinalityOneCheck.setSelection(initialReverseAssociation.getMultiplicity() == MdfConstants.MULTIPLICITY_ONE);
        cardinalityManyCheck.setSelection(initialReverseAssociation.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY);
    }

    private class WorkingReverseAssociation extends WorkingModelElement
            implements MdfReverseAssociation {

        public WorkingReverseAssociation() {
            super(initialReverseAssociation);
        }

        public MdfAssociation getAssociation() {
            return initialReverseAssociation.getAssociation();
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
            return initialReverseAssociation.getParentClass();
        }

        public boolean isPrimaryKey() {
            return initialReverseAssociation.isPrimaryKey();
        }

        public boolean isRequired() {
            return initialReverseAssociation.isRequired();
        }

        public MdfEntity getType() {
            return initialReverseAssociation.getType();
        }

		public boolean isBusinessKey() {
			return initialReverseAssociation.isBusinessKey();
		}

		// TODO to be removed at the end of the mdf-api/core split
		public boolean isDeprecated() { return false; }
		public EObject getDeprecationInfo() { return null; }
    }
}
