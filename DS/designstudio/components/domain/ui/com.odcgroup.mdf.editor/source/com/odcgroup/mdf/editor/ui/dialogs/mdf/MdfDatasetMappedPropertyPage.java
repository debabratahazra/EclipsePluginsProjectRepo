package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.ecore.MdfDatasetImpl;
import com.odcgroup.mdf.ecore.util.MdfEcoreUtil;
import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.actions.DeriveBaseClassUtil;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.EntitySelector;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.IContentAssistProvider;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.MdfPropertySelectionDialog;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.PropertyPathContentAssistProcessor;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.mdf.utils.PropertiesStack;


public class MdfDatasetMappedPropertyPage extends MdfModelPage {

    private Button browseProperties;
    private Button uniqueCheck;
    private Button singleValuedCheck;
    private final IContentAssistProvider contentAssistProvider = new IContentAssistProvider() {

        public MdfModelElement[] getCandidates() {
            return getCandidateElements();
        }

        public String getDefaultDomainName() {
            return MdfUtility.getDomain(initialProperty).getName();
        }
    };

    private final MdfDatasetMappedProperty initialProperty;
    private Label linkDatasetLabel;
    private EntitySelector linkDatasetSelector;
    private Text attributeNameText;
    private Text pathText;
    private boolean pathChanged = false;
    

    /**
     * Constructor for SampleNewWizardPage.
     * 
     * @param pageName
     */
    public MdfDatasetMappedPropertyPage(MdfDatasetMappedProperty property) {
        super(property);
        setTitle("Dataset Class Attribute");
        setDescription("Edits the core properties of the dataset class attribute.");
        this.initialProperty = property;

        setWorkingCopy(new WorkingDatasetProperty());
    }

    /**
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent) {
        WidgetFactory factory = getWidgetFactory();

        Composite container = factory.createComposite(parent);
        container.setLayout(new GridLayout(3, false));
        container.setLayoutData(new GridData(GridData.FILL_BOTH));

        factory.createLabel(container, "&Name:");

        attributeNameText = factory.createText(container, null);
        attributeNameText.setEnabled(getEditMode() != MODE_EDIT);

        GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
        layoutData.horizontalSpan = 2;
        attributeNameText.setLayoutData(layoutData);

        factory.createLabel(container, "&Path:");

        pathText = factory.createText(container, null);
        pathText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        PropertyPathContentAssistProcessor.install(pathText,
                initialProperty.getParentDataset());

        browseProperties = factory.createButton(container, "&Browse...",
                SWT.NULL);

        linkDatasetLabel = factory.createLabel(container, "&Link dataset:");
        linkDatasetSelector = new EntitySelector(container, factory,
                contentAssistProvider,
                MdfUtility.getDomain(initialProperty).getName());
        layoutData = new GridData(GridData.FILL_HORIZONTAL);
        layoutData.horizontalSpan = 2;
        linkDatasetSelector.setLayoutData(layoutData);

        factory.createLabel(container, "");
        singleValuedCheck = factory.createButton(container, "&Single Valued",
                SWT.CHECK);
        layoutData = new GridData(GridData.FILL_HORIZONTAL);
        layoutData.horizontalSpan = 2;
        singleValuedCheck.setLayoutData(layoutData);

        factory.createLabel(container, "");
        uniqueCheck = factory.createButton(container, "&Unique", SWT.CHECK);
        layoutData = new GridData(GridData.FILL_HORIZONTAL);
        layoutData.horizontalSpan = 2;
        singleValuedCheck.setLayoutData(layoutData);

        initialize();
        updateCheckBoxesState();
        updateStatus();
        registerListeners();

        setControl(container);
    }

    public void doSave(MdfModelElement element) {
    	MdfDataset parentDataset = ((MdfDatasetMappedProperty) element).getParentDataset();
    	if(isPropertyChanged( (MdfDatasetMappedProperty) element) && parentDataset.isSync()) {
			if(MdfUtility.showDataSetSynchronizeMessage()){
			  ((MdfDatasetImpl)parentDataset).setSync(false);	
			}
    	}
    	
        ModelFactory.INSTANCE.copy((MdfDatasetMappedProperty) workingCopy,
                (MdfDatasetMappedProperty) element);
        
       
        MdfClass baseClass = parentDataset.getBaseClass();
        String path = pathText.getText();

        if (baseClass != null &&  path != null && pathChanged) {
        	
            Stack props = PropertiesStack.getStack(parentDataset, path);
            MdfProperty p = (MdfProperty) props.peek();
            // if already has service annotation remove it
            if (DeriveBaseClassUtil.serviceAnnotationExists(element)) {
        		MdfAnnotation annot = element.getAnnotation(AAAAspect.SERVICES_NAMESPACE_URI,
				AAAAspect.SERVICES_ANNOTATION);
        		element.getAnnotations().remove(annot);
        	}
            
            // check if the new path element has service annotation
            if (p != null && DeriveBaseClassUtil.serviceAnnotationExists(p)) {
        		DeriveBaseClassUtil.addServiceAnnotation((MdfDatasetMappedProperty)element, p);
        	}
        }
    }

    /**
	 * @param element
	 */
	private boolean isPropertyChanged(MdfDatasetMappedProperty element) {
		 if(!attributeNameText.getText().equals(element.getName())){
			 return true;
		 } else if(!pathText.getText().equals(element.getName())) {
			 return true;
		} else if (linkDatasetSelector.getSelection() != null
				&& !linkDatasetSelector.getSelection().equals(element.getType())) {
			  return true;
		 }
		return false;
	}

    protected void registerListeners() {
        attributeNameText.addModifyListener(this);
        pathText.addModifyListener(this);
        linkDatasetSelector.addModifyListener(this);
        uniqueCheck.addSelectionListener(this);
        singleValuedCheck.addSelectionListener(this);

        singleValuedCheck.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                updateCheckBoxesState();
            }
        });

        pathText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
        		attributeNameText.setText(getAttributeName(pathText.getText().trim()));
                updateCheckBoxesState();
            }
        });

        browseProperties.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                onBrowseProperties();
            }
        });
    }

    private MdfModelElement[] getCandidateElements() {
        String path = pathText.getText().trim();

        if (path.length() > 0) {
            Stack props = PropertiesStack.getStack(
                    initialProperty.getParentDataset(), path);

            if (!props.isEmpty()) {
                MdfProperty p = (MdfProperty) props.peek();
                
                // OCS-26136
                if (p instanceof MdfReverseAssociation) {
                	MdfReverseAssociation assoc = ((MdfReverseAssociation)p);
                	return getCandidateElements(assoc.getType());
                }

                if (p instanceof MdfAssociation) {
                    MdfAssociation assoc = (MdfAssociation) p;
                    if (isLinkEnabled(assoc)) {
                    	return getCandidateElements(assoc.getType());
                    }
                }
            }
        }

        return new MdfModelElement[0];
    }
    
    private MdfModelElement[] getCandidateElements(MdfEntity entity) {
        List datasets = new ArrayList();
        Resource res = ((EObject) initialProperty).eResource();
        List<MdfEntity> entities = DomainRepositoryUtil.getAllMdfDatasets(res);
        for (MdfEntity mdfEntity : entities) {
            MdfDataset ds = (MdfDataset) mdfEntity;
	        if (ds.getBaseClass().getQualifiedName().equals(entity.getQualifiedName())) {
	            datasets.add(ds);
	        }
        }
        return (MdfModelElement[]) datasets.toArray(new MdfModelElement[datasets.size()]);
    }

    private void initialize() {
        setText(attributeNameText, initialProperty.getName());
        setText(pathText, initialProperty.getPath());

        MdfEntity type = initialProperty.getType();

        if (type instanceof MdfDataset) {
            linkDatasetSelector.setSelection(type);
        }

        singleValuedCheck.setSelection(initialProperty.isSingleValued());
        uniqueCheck.setSelection(initialProperty.isUnique());
    }

    private void onBrowseProperties() {
        MdfPropertySelectionDialog dialog = new MdfPropertySelectionDialog(
                getShell(), initialProperty.getParentDataset());
        String value = pathText.getText();
        dialog.setPath(value);

        if (dialog.open() == Window.OK) {
        	String newValue = dialog.getPath();
        	if (!value.equals(newValue)) {
        		pathText.setText(newValue);
        		pathChanged = true;
        		attributeNameText.setText(getAttributeName(newValue));
        	}
        }
    } 
    
    private String getAttributeName(String path) { 
    	int i = path.indexOf(".");
    	if (i > 0) {
        	StringTokenizer st = new StringTokenizer(path, ".");
        	StringBuffer sb = new StringBuffer();
        	while(st.hasMoreTokens()) {
        		sb.append(changeFirstLetterCase(st.nextToken(), true));
        	}   
        	return changeFirstLetterCase(sb.toString(), false);
    	}
    	return path;
    }
    
    private String changeFirstLetterCase(String str, boolean upper) {
    	String suffix = null;
    	if (upper) {
    		suffix = str.substring(0,1).toUpperCase();
    	} else {
    		suffix = str.substring(0,1).toLowerCase();
    	}
    	return suffix + str.substring(1);
    }
    
    private boolean isLinkEnabled(MdfAssociation assoc) {
    	if ((assoc.getContainment() == MdfConstants.CONTAINMENT_BYREFERENCE)
                    	|| (assoc.getContainment() == MdfConstants.CONTAINMENT_BYVALUE)) {
    		return true;
    	}
    	return false;
    }

    private void updateCheckBoxesState() {
        String path = pathText.getText().trim();
        if (path.length() > 0) {
        	try {
            	Stack props = PropertiesStack.getStack(
                        initialProperty.getParentDataset(), path);

                if (!props.isEmpty()) {
                    MdfProperty p = (MdfProperty) props.peek();

                    // OCS-26136                
                    if (p instanceof MdfReverseAssociation) { 
                    	p  = ((MdfReverseAssociation)p).getAssociation();
                    }
                    
                    if (p instanceof MdfAssociation) {
                        MdfAssociation assoc = (MdfAssociation) p;
                        boolean datasetEnabled = isLinkEnabled(assoc);
                        linkDatasetLabel.setEnabled(datasetEnabled);
                        linkDatasetSelector.setEnabled(datasetEnabled);
                    } else {
                    	linkDatasetSelector.setSelection("");
                        linkDatasetLabel.setEnabled(false);
                        linkDatasetSelector.setEnabled(false);
                    }

                    Iterator it = props.iterator();
                    while (it.hasNext()) {
                        p = (MdfProperty) it.next();

                        if (p != null
                                && p.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
                            singleValuedCheck.setEnabled(true);

                            if (singleValuedCheck.getSelection()) {
                                uniqueCheck.setEnabled(false);
                                uniqueCheck.setSelection(true);
                            } else {
                                uniqueCheck.setEnabled(true);
                            }
                            return;
                        }
                    }

                    uniqueCheck.setSelection(true);
                    uniqueCheck.setEnabled(false);
                    singleValuedCheck.setSelection(false);
                    singleValuedCheck.setEnabled(false);
                }
        	} catch(RuntimeException e) {
        		// DS-4048: If linked property does not exist, we have to catch the RE
        	}
        } else {
            linkDatasetLabel.setEnabled(false);
            linkDatasetSelector.setEnabled(false);
            linkDatasetSelector.setSelection((MdfEntity) null);
            uniqueCheck.setSelection(true);
            uniqueCheck.setEnabled(false);
            singleValuedCheck.setSelection(false);
            singleValuedCheck.setEnabled(false);
        }
    }

    private class WorkingDatasetProperty extends WorkingModelElement implements
            MdfDatasetMappedProperty {

        public WorkingDatasetProperty() {
            super(initialProperty);
        }

        public int getMultiplicity() {
            return initialProperty.getMultiplicity();
        }

        public String getName() {
            return attributeNameText.getText().trim();
        }

        public MdfDataset getParentDataset() {
            return initialProperty.getParentDataset();
        }

        public String getPath() {
            return pathText.getText().trim();
        }

        public MdfEntity getType() {
            return MdfEcoreUtil.getMdfDatasetProxy(linkDatasetSelector.getSelection());
        }

        public boolean isTypeDataset() {
            return (getType() != null);
        }

        public boolean isUnique() {
            return uniqueCheck.getSelection();
        }

        public boolean isSingleValued() {
            return singleValuedCheck.getSelection();
        }
    }
}
