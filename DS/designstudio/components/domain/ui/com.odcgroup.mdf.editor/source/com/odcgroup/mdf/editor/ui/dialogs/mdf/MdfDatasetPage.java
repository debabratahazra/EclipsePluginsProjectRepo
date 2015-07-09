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
import org.eclipse.xtext.naming.QualifiedName;

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
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfName;


/**
 * @author <a href="mailto:daguirre@odyssey-group">David Aguirre</a>
 * @version 1.0
 */
public class MdfDatasetPage extends MdfModelPage implements ModelChangeListener {
    private EntitySelector baseClassSelector;
    private final MdfDataset initialDataset;
    private Composite container;
    private final IContentAssistProvider contentAssistProvider =
        new IContentAssistProvider() {
            public MdfModelElement[] getCandidates() {
                return getCandidateBaseClasses();
            }

            public String getDefaultDomainName() {
                return MdfUtility.getDomain(initialDataset).getName();
            }
        };

    private MdfModelElementTableViewer mdfModelElementTableViewer;
    private Text classNameText;
    private Button linkedCheckbox;
    private Button syncCheckbox;

    /**
     * Constructor for SampleNewWizardPage.
     *
     * @param dataset
     */
    public MdfDatasetPage(MdfDataset dataset) {
        super(dataset);
        setTitle("Dataset");
        setDescription("Edits the core properties of the dataset.");
        this.initialDataset = dataset;

        setWorkingCopy(new WorkingDataset());
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

        factory.createLabel(container, "&Based on class:");

        baseClassSelector =
            new EntitySelector(
                container, factory, contentAssistProvider,
                MdfUtility.getDomain(initialDataset).getName());
        baseClassSelector.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        

        factory.createLabel(container, "");
        linkedCheckbox = factory.createButton(container, "&Default linked dataset",
                SWT.CHECK);
        linkedCheckbox.setToolTipText("used by \'Copy base-class\' action for " +
        		"any association referencing this base-class");
        
        factory.createLabel(container, "");
        syncCheckbox = factory.createButton(container, "&Synchronized with base class",
                SWT.CHECK);
        syncCheckbox.setToolTipText("Allow synchronization with the referenced base class");

        if (getEditMode() == MODE_EDIT) {
            Group entities = factory.createGroup(container, "Properties");
            GridData data = new GridData(GridData.FILL_BOTH);
            data.horizontalSpan = 2;
            entities.setLayoutData(data);
            entities.setLayout(new GridLayout(1, false));
            mdfModelElementTableViewer =
                new MdfModelElementTableViewer(
                    factory,
                    new MdfDatasetButtonViewer(initialDataset),
                    entities, initialDataset);
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
        ModelFactory.INSTANCE.copy(
        	new SafeWorkingDataset((WorkingDataset)workingCopy), (MdfDataset) element);
    }

    /**
     * @see com.odcgroup.mdf.editor.model.ModelChangeListener#onModelChanged(com.odcgroup.mdf.editor.model.ModelChangedEvent)
     */
    public void onModelChanged(ModelChangedEvent event) {
        if (mdfModelElementTableViewer == null)
            return;

        Object changed = event.getChangedElement();

        if (
            (event.getSource() != this) &&
                (changed instanceof MdfDatasetProperty) &&
                ((MdfDatasetProperty) changed).getParentDataset().getQualifiedName().equals(
                    this.initialDataset.getQualifiedName())) {
            // If an attribute of the viewer class has been modified, we refresh
            this.mdfModelElementTableViewer.refresh();
        }
    }
    
    protected void initialize() {
        setText(classNameText, initialDataset.getName());
        baseClassSelector.setSelection(initialDataset.getBaseClass());
        linkedCheckbox.setSelection(initialDataset.isLinked());
        syncCheckbox.setSelection(initialDataset.isSync());
    }

    protected void registerListeners() {
        classNameText.addModifyListener(this);
        baseClassSelector.addModifyListener(this);
        linkedCheckbox.addSelectionListener(this);
        syncCheckbox.addSelectionListener(this);
    }

    private MdfModelElement[] getCandidateBaseClasses() {
        List candidates = new ArrayList();
        Resource resource = ((EObject)initialDataset).eResource();
        List<MdfEntity> entities = DomainRepositoryUtil.getMdfClasses(resource);
        for (MdfEntity entity : entities) {
        	 MdfClass klass = (MdfClass) entity;
             if (!klass.getAttributesRef().isEmpty() && !klass.isAbstract()) {
                 candidates.add(entity);
             }
        }

        return (MdfModelElement[]) candidates.toArray(
            new MdfModelElement[candidates.size()]);
    }
    
    private boolean exists(MdfName mdfName) {
    	if (mdfName == null) {
    		return false;
    	}
    	
        Resource resource = ((EObject)initialDataset).eResource();    	
        List<MdfEntity> entities = DomainRepositoryUtil.getAllMdfEntities(resource);
        for (MdfEntity entity : entities) {
        	if (entity.getQualifiedName().equals(mdfName)) {
        		return true;
        	}
        }
        return false;
    }

    private boolean isExistingClass(MdfName mdfName) {
    	if (mdfName == null) {
    		return false;
    	}
    	QualifiedName qname = QualifiedName.create(mdfName.getDomain(), mdfName.getLocalName());
    	return DomainRepositoryUtil.checkMdfClassExists(qname);
    }

    @SuppressWarnings("serial")
	class WorkingDataset extends WorkingModelElement
        implements MdfDataset {
    	
        public WorkingDataset() {
            super(initialDataset);
        }
        
        public MdfClass getBaseClass() {
        	MdfName baseClass = baseClassSelector.getSelection();
        	if(baseClass==null) return null;
        	if (exists(baseClass) && 
        			!isExistingClass(baseClass)) {
   				throw new ClassCastException();
        	}
        	
        	// Modified for DS-6376
        	MdfClass mdfBaseClass = MdfEcoreUtil.getMdfClassProxy(baseClass);
        	Resource resource = ((EObject) initialDataset).eResource();
        	MdfEntity entity = DomainRepositoryUtil.getMdfEntity(baseClass, resource);
        	if(entity instanceof MdfClass) {
        		mdfBaseClass = (MdfClass)entity;
        	}
       		return mdfBaseClass;
        }

        public String getName() {
            return classNameText.getText().trim();
        }

        public MdfDomain getParentDomain() {
            return initialDataset.getParentDomain();
        }

        public boolean isPrimitiveType() {
            return initialDataset.isPrimitiveType();
        }

        public List getProperties() {
            return initialDataset.getProperties();
        }

        public MdfDatasetProperty getProperty(String name) {
            return initialDataset.getProperty(name);
        }

		public boolean isLinked() {
			return linkedCheckbox.getSelection();
		}
		
		public boolean isSync() {
			return syncCheckbox.getSelection();
		}

    }

    // DS-2627
    @SuppressWarnings("serial")
	private class SafeWorkingDataset extends WorkingDataset {
    	WorkingDataset workingDatasetAdaptee;
    	
    	public SafeWorkingDataset(WorkingDataset workingDatasetAdaptee) {
    		this.workingDatasetAdaptee = workingDatasetAdaptee;
    	}

		/* (non-Javadoc)
		 * @see com.odcgroup.mdf.metamodel.MdfDataset#getBaseClass()
		 */
		@Override
		public MdfClass getBaseClass() {
			try {
				return this.workingDatasetAdaptee.getBaseClass();
			} catch (ClassCastException c) {
				return null;
			}
		}

		/* (non-Javadoc)
		 * @see com.odcgroup.mdf.metamodel.MdfDataset#getProperties()
		 */
		@Override
		public List getProperties() {
			return this.workingDatasetAdaptee.getProperties();
		}

		/* (non-Javadoc)
		 * @see com.odcgroup.mdf.metamodel.MdfDataset#getProperty(java.lang.String)
		 */
		@Override
		public MdfDatasetProperty getProperty(String name) {
			return this.workingDatasetAdaptee.getProperty(name);
		}

		/* (non-Javadoc)
		 * @see com.odcgroup.mdf.metamodel.MdfDataset#isLinked()
		 */
		@Override
		public boolean isLinked() {
			return this.workingDatasetAdaptee.isLinked();
		}

		/* (non-Javadoc)
		 * @see com.odcgroup.mdf.metamodel.MdfDataset#isSync()
		 */
		@Override
		public boolean isSync() {
			return this.workingDatasetAdaptee.isSync();
		}
    }
}
