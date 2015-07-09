package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ecore.util.MdfEcoreUtil;
import com.odcgroup.mdf.editor.model.MdfProjectRepository;
import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.model.ModelChangeListener;
import com.odcgroup.mdf.editor.model.ModelChangedEvent;
import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.EntitySelector;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.IContentAssistProvider;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;


/**
 * @author <a href="mailto:daguirre@odyssey-group">David Aguirre</a>
 * @version 1.0
 */
public class MdfEnumerationPage extends MdfModelPage
    implements ModelChangeListener {
    private EntitySelector typeSelector;
    private final IContentAssistProvider contentAssistProvider =
        new IContentAssistProvider() {
            public MdfModelElement[] getCandidates() {
                return getCandidateTypes();
            }

            public String getDefaultDomainName() {
                return MdfUtility.getDomain(initialEnumeration).getName();
            }
        };

    private final MdfEnumeration initialEnumeration;
    private MdfModelElementTableViewer mdfModelElementTableViewer;
    private Text enumNameText;
    private Button allowNullBtn;
    

    /**
     * Constructor for SampleNewWizardPage.
     *
     * @param enumeration
     */
    public MdfEnumerationPage(MdfEnumeration enumeration) {
        super(enumeration);
        setTitle("Enumeration");
        setDescription("Edits the core properties of the enumeration.");
        this.initialEnumeration = enumeration;

        setWorkingCopy(new WorkingEnumeration());
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

        enumNameText = factory.createText(container, null);
        enumNameText.setEnabled(getEditMode() != MODE_EDIT);
        enumNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        factory.createLabel(container, "&Type:");
        typeSelector =
            new EntitySelector(
                container, factory, contentAssistProvider,
                MdfUtility.getDomain(initialEnumeration).getName());
        typeSelector.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        //DS-706
        factory.createLabel(container, "");
        allowNullBtn = factory.createButton(container, "&Allow NULL value", SWT.CHECK); 

        if (getEditMode() == MODE_EDIT) {
            Group entities = factory.createGroup(container, "Values");
            GridData data = new GridData(GridData.FILL_BOTH);
            data.horizontalSpan = 2;
            entities.setLayoutData(data);
            entities.setLayout(new GridLayout(1, false));
            mdfModelElementTableViewer =
                new MdfModelElementTableViewer(
                    factory,
                    new MdfEnumerationButtonViewer(initialEnumeration),
                    entities, initialEnumeration);
        }

        initialize();
        updateStatus();
        registerListeners();

        setControl(container);
        MdfProjectRepository.addModelChangeListener(this);
    }

    public void dispose() {
        MdfProjectRepository.removeModelChangeListener(this);
    }

    public void doSave(MdfModelElement element) {
        MdfEnumeration enumeration = (MdfEnumeration) element;
        ModelFactory.INSTANCE.copy((MdfEnumeration) workingCopy, enumeration);
    }

    /**
     * @see com.odcgroup.mdf.editor.model.ModelChangeListener#onModelChanged(com.odcgroup.mdf.editor.model.ModelChangedEvent)
     */
    public void onModelChanged(ModelChangedEvent event) {
        if (mdfModelElementTableViewer == null)
            return;

        Object changed = event.getChangedElement();
        if (
            (event.getSource() != this) && (changed instanceof MdfEnumValue) &&
                ((MdfEnumValue) changed).getParentEnumeration().getQualifiedName().equals(
                    this.initialEnumeration.getQualifiedName())) {
            // If an enumeration of the viewer enum has been modified, we
            // refresh
            this.mdfModelElementTableViewer.refresh();
        }
    }

    protected void registerListeners() {
        enumNameText.addModifyListener(this);
        typeSelector.addModifyListener(this);
        //DS-706
        allowNullBtn.addSelectionListener(this);
    }

    private MdfModelElement[] getCandidateTypes() {
        // -- Add primitives only
        List candidates = new ArrayList();
        Iterator it = PrimitivesDomain.DOMAIN.getEntities().iterator();

        while (it.hasNext()) {
            MdfEntity entity = (MdfEntity) it.next();
            // DS-2278 - disallow creating Enums on Date/DateTime/Double
            if ((entity instanceof MdfPrimitive)
            		&& !(entity.getQualifiedName().equals(PrimitivesDomain.DATE.getQualifiedName()) 
            				|| entity.getQualifiedName().equals(PrimitivesDomain.DATE_TIME.getQualifiedName()) 
            				|| entity.getQualifiedName().equals(PrimitivesDomain.DOUBLE.getQualifiedName())
            				|| entity.getQualifiedName().equals(PrimitivesDomain.DOUBLE_OBJ.getQualifiedName()) )) {
                candidates.add(entity);
            }
        }
        
        
        // -- Then all businesstypes in the domain & referenced domains
        Resource res = ((EObject) initialEnumeration).eResource();
        List<MdfEntity> entities = DomainRepositoryUtil.getAllBusinessTypes(res);
        candidates.addAll(entities);

        return (MdfModelElement[]) candidates.toArray(new MdfModelElement[candidates.size()]);
    }

    /**
     * Tests if the current workbench selection is a suitable projectCombo to
     * use.
     */
    private void initialize() {
        setText(enumNameText, initialEnumeration.getName());

        if (initialEnumeration.getType() == null) {
            typeSelector.setSelection(
                MdfUtility.DEFAULT_ENUM_TYPE.getQualifiedName());
        } else {
            typeSelector.setSelection(initialEnumeration.getType());
        }
        //DS-706
    	allowNullBtn.setSelection(initialEnumeration.isAcceptNullValue());
    }

    @SuppressWarnings("serial")
	private class WorkingEnumeration extends WorkingModelElement
        implements MdfEnumeration {
        public WorkingEnumeration() {
            super(initialEnumeration);
        }

        public String getName() {
            return enumNameText.getText().trim();
        }

        public MdfDomain getParentDomain() {
            return initialEnumeration.getParentDomain();
        }

        public boolean isPrimitiveType() {
            return initialEnumeration.isPrimitiveType();
        }

        public MdfPrimitive getType() {
            return MdfEcoreUtil.getMdfPrimitiveProxy(typeSelector.getSelection());
        }

        public boolean isValidValue(String value) {
            return initialEnumeration.isValidValue(value);
        }

        public MdfEnumValue getValue(String name) {
            return initialEnumeration.getValue(name);
        }

        public List getValues() {
            return initialEnumeration.getValues();
        }
        //DS-706
		public boolean isAcceptNullValue() {
			return allowNullBtn.getSelection();
		}

		// TODO to be removed at the end of the mdf-api/core split
		public boolean isDeprecated() { return false; }
		public EObject getDeprecationInfo() { return null; }
        
    }
}
