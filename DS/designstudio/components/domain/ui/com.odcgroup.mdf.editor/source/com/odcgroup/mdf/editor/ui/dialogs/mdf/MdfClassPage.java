package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import java.util.ArrayList;
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
import com.odcgroup.mdf.ecore.util.MdfEcoreUtil;
import com.odcgroup.mdf.editor.model.MdfProjectRepository;
import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.model.ModelChangeListener;
import com.odcgroup.mdf.editor.model.ModelChangedEvent;
import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.EntitySelector;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.IContentAssistProvider;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;


/**
 * @author <a href="mailto:daguirre@odyssey-group">David Aguirre</a>
 * @version 1.0
 */
public class MdfClassPage extends MdfModelPage implements ModelChangeListener {

    private Button abstractCheckbox;
    private EntitySelector superClassSelector;
    private final MdfClass initialClass;
    private Composite container;
    private final IContentAssistProvider contentAssistProvider = new IContentAssistProvider() {

        public MdfModelElement[] getCandidates() {
            return getCandidateSuperClasses();
        }

        public String getDefaultDomainName() {
            return MdfUtility.getDomain(initialClass).getName();
        }
    };

    private MdfModelElementTableViewer mdfModelElementTableViewer;
    private Text classNameText;

    /**
     * Constructor for SampleNewWizardPage.
     * 
     * @param klass
     */
    public MdfClassPage(MdfClass klass) {
        super(klass);
        setTitle("Class");
        setDescription("Edits the core properties of the class.");
        this.initialClass = klass;

        setWorkingCopy(new WorkingClass());
    }
    
    protected MdfClass getInitialClass() {
    	return initialClass;
    }

    /**
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent) {
        WidgetFactory factory = getWidgetFactory();

        container = factory.createComposite(parent, SWT.NULL);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        container.setLayout(new GridLayout(2, false));

        factory.createLabel(container, "&Name:");

        classNameText = factory.createText(container, null);
        classNameText.setEnabled(getEditMode() != MODE_EDIT);
        classNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        factory.createLabel(container, "&Superclass:");

        
        superClassSelector = new EntitySelector(container, factory,
                contentAssistProvider,
                initialClass.getQualifiedName().getDomain());
        superClassSelector.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        {
            Group modifiers = factory.createGroup(container, "Modifiers");
            GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
            layoutData.horizontalSpan = 2;

            modifiers.setLayoutData(layoutData);
            modifiers.setLayout(new GridLayout(1, false));

            abstractCheckbox = factory.createButton(modifiers, "&Abstract",
                    SWT.CHECK);
        }

        if (getEditMode() == MODE_EDIT) {
            Group entities = factory.createGroup(container, "Properties");
            GridData data = new GridData(GridData.FILL_BOTH);
            data.horizontalSpan = 3;
            entities.setLayoutData(data);
            entities.setLayout(new GridLayout(1, false));
            mdfModelElementTableViewer = new MdfModelElementTableViewer(
                    factory, new MdfClassButtonViewer(initialClass), entities,
                    initialClass);
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
        ModelFactory.INSTANCE.copy((MdfClass) workingCopy, (MdfClass) element);
    }

    /**
     * @see com.odcgroup.mdf.editor.model.ModelChangeListener#onModelChanged(com.odcgroup.mdf.editor.model.ModelChangedEvent)
     */
    public void onModelChanged(ModelChangedEvent event) {
        if (mdfModelElementTableViewer == null) return;

        Object changed = event.getChangedElement();

        if ((event.getSource() != this)
                && (changed instanceof MdfProperty)
                && ((MdfProperty) changed).getParentClass().getQualifiedName().equals(
                        this.initialClass.getQualifiedName())) {
            // If an attribute of the viewer class has been modified, we refresh
            this.mdfModelElementTableViewer.refresh();
        }
    }

    protected void initialize() {
        MdfClass baseClass = initialClass.getBaseClass();

        setText(classNameText, initialClass.getName());
        superClassSelector.setSelection(baseClass);
        abstractCheckbox.setSelection(initialClass.isAbstract());
    }

    protected void registerListeners() {
        classNameText.addModifyListener(this);
        superClassSelector.addModifyListener(this);
        abstractCheckbox.addSelectionListener(this);
    }

    protected MdfModelElement[] getCandidateSuperClasses() {
        List candidates = new ArrayList();
        Resource resource = ((EObject) initialClass).eResource();
        List<MdfEntity> entities = DomainRepositoryUtil.getMdfClasses(resource);
        for (MdfEntity entity : entities) {
            if (!isInHierarchy((MdfClass) entity)) {
                candidates.add(entity);
            }
        }
        return (MdfModelElement[]) candidates.toArray(new MdfModelElement[candidates.size()]);
    }

    protected boolean isInHierarchy(MdfClass klass) {
        if (klass == null) return false;

        if (initialClass.getQualifiedName().equals(klass.getQualifiedName())) {
            return true;
        } else {
            return isInHierarchy(klass.getBaseClass());
        }
    }
    
    protected Text getText() {
    	return classNameText;
    }
    
    protected EntitySelector getSuperClassSelector() {
    	return superClassSelector;
    }

    @SuppressWarnings("serial")
	private class WorkingClass extends WorkingModelElement implements MdfClass {

        public WorkingClass() {
            super(initialClass);
        }

        public boolean isAbstract() {
            return abstractCheckbox.getSelection();
        }

        public MdfClass getBaseClass() {
            return MdfEcoreUtil.getMdfClassProxy(superClassSelector.getSelection());
        }

        public String getName() {
            return classNameText.getText().trim();
        }

        public MdfDomain getParentDomain() {
            return initialClass.getParentDomain();
        }

        public List getPrimaryKeys(boolean includeSuperClasses) {
            return initialClass.getPrimaryKeys(includeSuperClasses);
        }

        public List getPrimaryKeys() {
            return initialClass.getPrimaryKeys();
        }

        public boolean isPrimitiveType() {
            return initialClass.isPrimitiveType();
        }

        public List getProperties() {
            return initialClass.getProperties();
        }

        public List getProperties(boolean includeSuperClasses) {
            return initialClass.getProperties(includeSuperClasses);
        }

        public MdfProperty getProperty(String name) {
            return initialClass.getProperty(name);
        }

        public boolean isReferenceable() {
            return initialClass.isReferenceable();
        }


        public boolean hasPrimaryKey(boolean includeSuperClasses) {
            return initialClass.hasPrimaryKey(includeSuperClasses);
        }

        public boolean hasPrimaryKey() {
            return initialClass.hasPrimaryKey();
        }

		public List getAttributesRef() {
			return initialClass.getAttributesRef();
		}

		public List getBusinessKeys(boolean includeSuperClasses) {
			return initialClass.getBusinessKeys(includeSuperClasses);
		}

		// TODO to be removed at the end of the mdf-api/core split
		@SuppressWarnings("unused")
		public boolean isDeprecated() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }
		@SuppressWarnings("unused")
		public EObject getDeprecationInfo() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }
    }
}
