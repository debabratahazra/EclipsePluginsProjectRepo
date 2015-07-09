package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.domain.validation.JavaKeywordChecker;
import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.ext.java.JavaAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.otf.utils.inet.MalformedUriException;
import com.odcgroup.workbench.core.resources.OfsModelResource;
import com.odcgroup.workbench.ui.OfsUICore;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

/**
 * @author phanikumark
 *
 */
public abstract class AbstractDomainCreationPage extends WizardPage {

	protected IPath containerFullPath;
	protected IWorkbench workbench;
	protected Text fileNameField;
	protected Text domainNameField;
    private Text modelNamespaceText;
    private Text packageNameText;
	private static final String FILE_EXTN = "domain";
	private MdfDomain workingCopy;
	private MdfDomain actualDomain = null;
	
	private MdfDomain initialDomain = null;
	private String fileName;
	
	private static final String DEFAULT_FILENAME = "MyName";
	private boolean copyPage = false;
	private OfsModelResource resource;

	/**
	 * @param pageName
	 * @param containerPath
	 */
	public AbstractDomainCreationPage(String pageName, IWorkbench workbench,
			IPath containerFullPath, MdfDomain initialDomain, boolean copyPage, OfsModelResource resource) {
		super(pageName);
		this.workbench = workbench;
		this.containerFullPath = containerFullPath;
		if(!copyPage) {
			setTitle("Create Domain");
			setDescription("creates a new Domain");
	    } else {
		    setTitle("Copy Domain");
		}
		this.initialDomain = initialDomain;
		this.workingCopy = new WorkingDomain();
		setPageComplete(false);
		this.copyPage = copyPage;
		this.resource = resource;
		this.actualDomain = initialDomain;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {

		Composite topLevel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		topLevel.setLayout(layout);
		topLevel.setLayoutData(new GridData(GridData.FILL_BOTH));
		topLevel.setFont(parent.getFont());
		
		Composite secondLevel = new Composite(topLevel, SWT.NONE);
		layout = new GridLayout(2, false);
		secondLevel.setLayout(layout);
		secondLevel.setLayoutData(new GridData(GridData.FILL_BOTH));
		secondLevel.setFont(parent.getFont());

		Label label = new Label(secondLevel, SWT.WRAP);
		label.setText("File Name :");
		label.setFont(this.getFont());
		
		fileNameField = new Text(secondLevel, SWT.SINGLE | SWT.BORDER);
		fileNameField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fileNameField.setFont(this.getFont());
		
		createCustomPropertiesControl(topLevel);
		
		Group mdfGroup = createGroup(topLevel, "Domain Properties", 2);
		Group javaExt = createGroup(topLevel, "Java Extensions", 2);
		
		label = new Label(mdfGroup, SWT.WRAP);
		label.setText("Domain Name :");
		label.setFont(this.getFont());

		domainNameField = new Text(mdfGroup, SWT.SINGLE | SWT.BORDER);
		domainNameField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		domainNameField.setFont(this.getFont());
		
		label = new Label(mdfGroup, SWT.WRAP);
		label.setText("Namespace :");
		label.setFont(this.getFont());

		modelNamespaceText = new Text(mdfGroup, SWT.SINGLE | SWT.BORDER);
		modelNamespaceText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		modelNamespaceText.setFont(this.getFont());
		modelNamespaceText.setEnabled(false);

		label = new Label(javaExt, SWT.WRAP);
		label.setText("Package name :");
		label.setFont(this.getFont());

		packageNameText = new Text(javaExt, SWT.SINGLE | SWT.BORDER);
		packageNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		packageNameText.setFont(this.getFont());
		packageNameText.setEditable(false);

        initialize();
        registerListeners();
		setPageComplete(validatePage());
		setControl(parent);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent.getShell(), IOfsHelpContextIds.NEW_DOMAIN);
	}
	
	/**
	 * @param topLevel
	 */
	protected abstract void createCustomPropertiesControl(Composite parent);

	/**
	 * @param parent
	 * @param groupName
	 * @param cols
	 * @return
	 */
	public Group createGroup(Composite parent, String groupName, int cols) {
		Group group = new Group(parent, SWT.NONE);
		group.setBackground(parent.getBackground());
		if (groupName != null)
		group.setText(groupName);
		GridLayout gridLayout = new GridLayout(cols, false);
		group.setLayout(gridLayout);
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
		group.setFont(parent.getFont());
		return group;
	}
	
	/**
	 * 
	 */
	private void registerListeners() {
		fileNameField.addModifyListener(validator);
		domainNameField.addModifyListener(validator);
		modelNamespaceText.addModifyListener(validator);
		packageNameText.addModifyListener(validator);
		
		// automatically determine the namespace from the domain name
		domainNameField.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				modelNamespaceText.setText(inferNamespaceName(domainNameField.getText()));
			}
		});
	}

    protected String inferNamespaceName(String text) {
    	if(StringUtils.isEmpty(text)) return "";
    	StringBuilder sb = new StringBuilder("http://www.odcgroup.com/");
    	sb.append(text.substring(0, 1).toLowerCase());
    	for(char c : text.substring(1).toCharArray()) {
    		if(Character.isUpperCase(c)) {
    			sb.append("-").append(Character.toLowerCase(c));
    		} else {
    			sb.append(c);
    		}
    	}
    	return sb.toString();
	}

	/**
     * Tests if the current workbench selection is a suitable
     * projectCombo to use.
     */
    private void initialize() {
    	if(!copyPage){
	    	fileNameField.setText(DEFAULT_FILENAME+"."+FILE_EXTN);
	    	domainNameField.setText("NewDomain");
	    	modelNamespaceText.setText("http://www.odcgroup.com/new-domain");
	        packageNameText.setText(getDefaultPackage());
    	} else {
    		String prefixName=getAutoPrefixNameFor(resource);
    		fileNameField.setText(prefixName+resource.getName());
    		domainNameField.setText(actualDomain.getName());
            modelNamespaceText.setText(actualDomain.getNamespace());
            packageNameText.setText(getDefaultPackage());
    	}
    }
    
    /**
     * @return
     */
    private String getDefaultPackage() {
    	String domainFolder  = "/domain/";
    	String path = getContainerFullPath().toString();
    	int ii = path.indexOf(domainFolder);
    	return path.substring(ii+domainFolder.length()).replaceAll("/", ".");
    }
		
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModifyListener validator =
		new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(validatePage());
			}
		};
		
		
	protected SelectionListener selectionListener = 
		new SelectionListener() {		
			public void widgetSelected(SelectionEvent e) {	
				setPageComplete(validatePage());		
			}		
			
			public void widgetDefaultSelected(SelectionEvent e) {			
			}
		};

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected boolean validatePage() {
		String fileName = fileNameField.getText();
		if (fileName == null)
			return false;
		IPath path = getContainerFullPath().append(fileName);
		if (ResourcesPlugin.getWorkspace().getRoot().exists(path)) {
			setErrorMessage(OfsUICore.getDefault().getString("wizard.modelresource.validator.fileExists"));
			return false;
		}
		URI fileURI = URI.createFileURI(path.toString());
		String ext = fileURI.fileExtension();
		if (ext == null || !FILE_EXTN.equals(ext)) {
            setErrorMessage("The file name must end in \"."+FILE_EXTN+"\"");
			return false;
		}
		String domainName = domainNameField.getText();
		if (domainName == null)
			return false;
		IStatus status = JavaConventions.validateJavaTypeName(domainName, JavaCore.VERSION_1_5, JavaCore.VERSION_1_5); 
		if(!status.isOK()) {
			setErrorMessage("'" + domainName + "' is not a valid domain name");
			return false;
		}	
		if (JavaKeywordChecker.getInstance().isKeyword(domainName)) {
			setErrorMessage("'" + domainName + "' is a reserved Java keyword");
			return false;
		}
		Resource res = ((EObject)initialDomain).eResource();
		if(DomainRepositoryUtil.checkIfDomainExists(domainName, res)) {
			setErrorMessage("A domain with this name already exists");
			return false;
		}
		String namespace = modelNamespaceText.getText();
		if (MdfUtility.isNullOrEmpty(namespace)) {
			setErrorMessage("A domain namespace must be specified");
			return false;
		}
		
		try {
            com.odcgroup.otf.utils.inet.URI.parse(namespace);
        } catch (MalformedUriException e) {
			setErrorMessage("'" + namespace  + "' is not a valid namespace");
			return false;
        }
        setErrorMessage(null);
        if(!copyPage){
        setMessage("Click finish to create domain");
        }else{
        	setMessage("Click finish to Copy domain");
        }
        
		return true;
	}
	
	/**
	 * @return
	 */
	public IPath getContainerFullPath() {
		return containerFullPath;
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#setPageComplete(boolean)
	 */
	public void setPageComplete(boolean complete) {
		super.setPageComplete(complete);
		if (complete) {
			this.fileName = fileNameField.getText();
			ModelFactory.INSTANCE.copy(workingCopy, initialDomain);
	        ModelFactory.INSTANCE.removeAnnotations(initialDomain, JavaAspect.NAMESPACE_URI);
	        addAnnotation(initialDomain, JavaAspect.PACKAGE, packageNameText.getText().trim());
	        setCustomProperties(initialDomain);
		}
	}
	
	/**
	 * @param domain
	 */
	protected abstract void setCustomProperties(MdfDomain domain);

	/**
	 * @param model
	 * @param name
	 * @param value
	 */
	private void addAnnotation(MdfModelElement model, String name, String value) {
        if (value.length() > 0) {
            MdfAnnotation annot = ModelFactory.INSTANCE.createMdfAnnotation(
                    JavaAspect.NAMESPACE_URI, name, value);
            ModelFactory.INSTANCE.addAnnotation(model, annot);
        }
    }

    /**
     *
     */
    private class WorkingDomain extends WorkingModelElement implements MdfDomain {

		private static final long serialVersionUID = 3532427355345390508L;

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
            return domainNameField.getText().trim();
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

	/**
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * return  the domain Name
	 * @return string;
	 */
	public String getDomainName(){
		return domainNameField.getText();
	}
	/**
	 * return the domain namespace
	 * @return namespace
	 */
	public String getDomainNameSpace(){
		return modelNamespaceText.getText();
	}
	
  private String getAutoPrefixNameFor(OfsModelResource  resource) {
		IPath originalName=resource.getFullPath();
		int counter = 1;
		String resourceName = originalName.lastSegment();
		
		while (true) {
			String nameSegment;
			if (counter > 1) {
				nameSegment = "Copy_"+counter+"_Of"; 
			} else {
				nameSegment = "CopyOf";
			}
			IPath path = getContainerFullPath().append(nameSegment+resourceName);
			if (!(ResourcesPlugin.getWorkspace().getRoot().exists(path))){
			   return nameSegment;
			}
			counter++;
		}
	}	

}
