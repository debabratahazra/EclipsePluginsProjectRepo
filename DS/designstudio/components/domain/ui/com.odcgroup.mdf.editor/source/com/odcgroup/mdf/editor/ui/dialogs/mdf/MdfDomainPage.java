package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.mdf.editor.model.MdfProjectRepository;
import com.odcgroup.mdf.editor.model.ModelChangeListener;
import com.odcgroup.mdf.editor.model.ModelChangedEvent;
import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class MdfDomainPage extends MdfModelPage implements ModelChangeListener {
    private final MdfDomain initialDomain;
    private MdfModelElementTableViewer mdfModelElementTableViewer;
    private Text domainNameText;
    private Text modelNamespaceText;

    /**
     * Constructor for SampleNewWizardPage.
     * @param pageName
     */
    public MdfDomainPage(MdfDomain domain) {
        super(domain);

        setTitle("Domain");
        setDescription("Edits the core properties of the domain.");
        this.initialDomain = domain;
        setWorkingCopy(new WorkingDomain());
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

        domainNameText = factory.createText(container, null);
        domainNameText.setEnabled(getEditMode() != MODE_EDIT);
        domainNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        domainNameText.setEditable(false);

        factory.createLabel(container, "Name&space:");

        modelNamespaceText = factory.createText(container, null);
        modelNamespaceText.setEnabled(getEditMode() != MODE_EDIT);
        modelNamespaceText.setLayoutData(
            new GridData(GridData.FILL_HORIZONTAL));
        modelNamespaceText.setEditable(false);

        if (getEditMode() == MODE_EDIT) {
            Group entities = factory.createGroup(container, "Entities");
            GridData data = new GridData(GridData.FILL_BOTH);
            data.horizontalSpan = 2;
            entities.setLayoutData(data);
            entities.setLayout(new GridLayout(1, false));
            mdfModelElementTableViewer =
                new MdfModelElementTableViewer(
                    factory,
                    new MdfDomainButtonViewer(initialDomain),
                    entities, initialDomain);
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
            (MdfDomain) workingCopy, (MdfDomain) element);
    }

    /**
     * @see com.odcgroup.mdf.editor.model.ModelChangeListener#onModelChanged(com.odcgroup.mdf.editor.model.ModelChangedEvent)
     */
    public void onModelChanged(ModelChangedEvent event) {
        if (mdfModelElementTableViewer == null)
            return;

        Object changed = event.getChangedElement();

        if (
            (event.getSource() != this) && (changed instanceof MdfEntity) &&
                ((MdfEntity) changed).getParentDomain().getQualifiedName().equals(
                    this.initialDomain.getQualifiedName())) {
            // If an attribute of the viewer class has been modified, we refresh
            this.mdfModelElementTableViewer.refresh();
        }
    }

    protected void registerListeners() {
        domainNameText.addModifyListener(this);
        modelNamespaceText.addModifyListener(this);
    }

    /**
     * Tests if the current workbench selection is a suitable
     * projectCombo to use.
     */
    private void initialize() {
        setText(domainNameText, initialDomain.getName(), "NewDomain");
        setText(
            modelNamespaceText, initialDomain.getNamespace(),
            "http://www.odcgroup.com/new-domain");
    }

    @SuppressWarnings("serial")
	private class WorkingDomain extends WorkingModelElement implements MdfDomain {
        public WorkingDomain() {
            super(initialDomain);
        }

        public MdfDataset getDataset(String name) {
            return initialDomain.getDataset(name);
        }

        public List getDatasets() {
            return initialDomain.getDatasets();
        }

        public List getEntities() {
            return initialDomain.getEntities();
        }

        public MdfEntity getEntity(String name) {
            return initialDomain.getEntity(name);
        }

        public String getName() {
            return domainNameText.getText().trim();
        }

        public String getNamespace() {
            return modelNamespaceText.getText().trim();
        }

        public String getMetamodelVersion() {
        	return initialDomain.getMetamodelVersion();
        }

		public MdfBusinessType getBusinessType(String name) {
			return initialDomain.getBusinessType(name);
		}

		public List getBusinessTypes() {
			return initialDomain.getBusinessTypes();
		}

		public MdfClass getClass(String arg0) {
			return initialDomain.getClass(arg0);
		}

		public List getClasses() {
			return initialDomain.getClasses();
		}

		public MdfEnumeration getEnumeration(String arg0) {
			return initialDomain.getEnumeration(arg0);
		}

		public List getEnumerations() {
			return initialDomain.getEnumerations();
		}

		public MdfPrimitive getPrimitive(String arg0) {
			return initialDomain.getPrimitive(arg0);
		}

		public List getPrimitives() {
			return initialDomain.getPrimitives();
		}

		// TODO to be removed at the end of the mdf-api/core split
		public boolean isDeprecated() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }
		public EObject getDeprecationInfo() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }
		public List getImportedDomains() { return new LinkedList(); }
    }
}
