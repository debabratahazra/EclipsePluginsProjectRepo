package com.odcgroup.t24.enquiry.ui.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.odcgroup.mdf.ecore.MdfEntityImpl;
import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.IContentAssistProvider;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.MdfEntitySelectionDialog;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.workbench.core.xtext.XtextResourceSetProviderDS;
import com.odcgroup.workbench.ui.wizards.AbstractNewModelCreationPage;


public class NewEnquiryWizardPage extends AbstractNewModelCreationPage{

	public static final String[] APPLICATION_FILE_TYPES = new String[] { "", "ARC", "DIM", "HIS", "NAU", };
	private static final String ENQUIRY_NAME_PATTERN = "[a-zA-Z_0-9\\.]*[^\\\\/:*?\"<>|]";
	
	private Text enquiryName;
	private Text applicationText;
	private Enquiry enquiry;
	private Text headertext;
	private MdfEntity application;
	private String appName;
	private Text moduleName;
	private Combo fileTypeList;


	public NewEnquiryWizardPage(Enquiry enquiry, IProject project) {
		this(enquiry);
		this.project = project;
	}

	/**
	 * @param enquiry2
	 * @param selection
	 */
	public NewEnquiryWizardPage(Enquiry enquiry) {
		super("wizardPage");
		setPageComplete(false);
		setTitle("Create New Enquiry");
		setDescription("Create a New T24 Enquiry");
		this.enquiry = enquiry;
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridData gd_parentComp = new GridData(GridData.FILL_BOTH);
		setControl(container);
		container.setLayoutData(gd_parentComp);
		container.setLayout(new GridLayout(1, false));

		Group grpScreenDetails = new Group(container, SWT.NONE);
		grpScreenDetails.setText("Enquiry Details");
		GridData gd_grpScreenDetails = new GridData(GridData.FILL_BOTH);

		grpScreenDetails.setLayoutData(gd_grpScreenDetails);
		grpScreenDetails.setLayout(new GridLayout(1, false));

		Composite composite_enquirydetails = new Composite(grpScreenDetails, SWT.NONE);
		composite_enquirydetails.setLayout(new GridLayout(3, false));
		GridData gd_composite_enquirydetails = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_composite_enquirydetails.heightHint = 107;
		composite_enquirydetails.setLayoutData(gd_composite_enquirydetails);
		
		Label enquiryLabel = new Label(composite_enquirydetails, SWT.NONE);
		enquiryLabel.setText("Enquiry Name:");
		//Enquiry name text
		enquiryName = new Text(composite_enquirydetails, SWT.BORDER);
		enquiryName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,2,1));
		enquiryName.setTextLimit(50);
		enquiryName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validateControls();
			}
		});
		
		Label applicationLabel = new Label(composite_enquirydetails, SWT.NONE);
		applicationLabel.setText("Application:");
		
		applicationText = new Text(composite_enquirydetails, SWT.BORDER);
		applicationText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		applicationText.setEditable(false);
        applicationText.addModifyListener(new ModifyListener() {
        	 @Override
        	 public void modifyText(ModifyEvent e) {
        		 validateControls();
        		 ResourceSet rs = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, project);
        		 IContentAssistProvider contentAssistProvider = MdfUtility.getContentAssistProviderForDomainClasses(rs);
        		 String entityName = moduleName.getText()+":"+applicationText.getText();
        		 for (MdfModelElement modelEle : contentAssistProvider.getCandidates()) {
        			 String names[] = modelEle.getName().split("\\.");
        			 String formatedApplicationName = names[0] + ":" + names[1].replace("_", ".");
        			 if(formatedApplicationName.equals(entityName)){
        				 final MdfEntity entity = (MdfEntity) modelEle;
        				 setApplication(entity);
        			 }
        		 }
        	 }
        });
        
		Button applicaitonButton = new Button(composite_enquirydetails, SWT.NONE);
		applicaitonButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ResourceSet rs = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, project);
			    IContentAssistProvider contentAssistProvider = MdfUtility.getContentAssistProviderForDomainClasses(rs);
				MdfEntitySelectionDialog dialog = MdfEntitySelectionDialog.createDialog(getShell(), contentAssistProvider, true, "enquiry");
			    if(dialog.open() == Window.OK) {
					MdfEntity entity = (MdfEntity) dialog.getFirstResult();
					if (entity != null) {
						String names[] = entity.getName().split("\\.");
						moduleName.setText(names[0]);
						entity = getResolvedMdfEntity((MdfEntityImpl) entity, rs, names[1]);
						appName = names[1].replace("_", ".");
						applicationText.setText(appName);
						String name = names[0] + ":" + appName;
						enquiry.setFileName("name:/" + name + "#");
						fileTypeList.setEnabled(true);
					}
					final MdfEntity mdfEntity = entity;
					validateControls();
					((WizardDialog)getContainer()).addPageChangingListener(new IPageChangingListener() {
						public void handlePageChanging(PageChangingEvent event) {
							if(event.getTargetPage() instanceof FieldSelectionWizardPage){
							     setApplication(mdfEntity);
							}
						}
					});
				}
			}
		});
		applicaitonButton.setText("Browse...");
		
		// File Type
		Label fileTypeLabel = new Label(composite_enquirydetails, SWT.NONE);
		fileTypeLabel.setText("File Type:");
		
		fileTypeList = new Combo(composite_enquirydetails, SWT.READ_ONLY);
		fileTypeList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		fileTypeList.setItems(APPLICATION_FILE_TYPES);
		fileTypeList.setEnabled(false);
		fileTypeList.select(0);
		fileTypeList.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (fileTypeList.getText().isEmpty()) {
					enquiry.setFileName("name:/" + moduleName.getText() + ":" + appName + "#");
				} else {
					enquiry.setFileName("name:/" + moduleName.getText() + ":" + appName + "$" + fileTypeList.getText()
							+ "#");
				}
			}
		});

		// Module/Component name
		Label moduleLabel = new Label(composite_enquirydetails, SWT.NONE);
		moduleLabel.setText("Module/Component:");

		moduleName = new Text(composite_enquirydetails, SWT.BORDER);
		moduleName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		moduleName.setEditable(false);
		
		// Header name
		Label headerLabel = new Label(composite_enquirydetails, SWT.NONE);
		headerLabel.setText("Header:");
		//header text
		headertext = new Text(composite_enquirydetails, SWT.BORDER);
		headertext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		headertext.setTextLimit(42);
	}
	
	private MdfEntity getResolvedMdfEntity(MdfEntityImpl entity, ResourceSet rs, String className) {
		if (entity.eIsProxy()) {
			InternalEObject eobj = (InternalEObject) entity;
			URI uri = eobj.eProxyURI().trimFragment();
			Resource res = rs.getResource(uri, true);
			if (res != null) {
				MdfDomain domain = (MdfDomain) res.getContents().get(0);
				return domain.getClass(className);
			}
			return null;
		} else {
			return entity;
		}
	}
	
	private void validateControls() {
		setPageComplete(isValidEnquiryName(enquiryName.getText()) && isWidgetEmpty());
	}
	
	public String getFormatedApplicationName(MdfEntity entity){
		return entity.getParentDomain().getName() + ":"+ T24Aspect.getT24Name(entity);
	}
	
	/**
	 * @return
	 */
	private boolean isWidgetEmpty() {
		if (applicationText.getText().isEmpty()) {
			setErrorMessage("Application is required.");
			return false;
		} else if (enquiryName.getText().isEmpty()) {
			setErrorMessage("Enquiry name is required.");
			return false;
		}
		setErrorMessage(null);
		return true;
	}	
	
	/**
	 * @return the enquiryName
	 */
	public String getEnquiryName() {
		return enquiryName.getText();
	}
	
	/**
	 * @return the applicationText
	 */
	public MdfEntity getApplication() {
		return application;
	}
	
	private void setApplication(MdfEntity entity){
		application = entity;
		if((FieldSelectionWizardPage)getNextPage() != null) {
			((FieldSelectionWizardPage)getNextPage()).setApplication(entity);
		}
	}
	
	private boolean isValidEnquiryName(String text) {
		if (!text.isEmpty()) {
			if (text.contains(" ")) {
				setErrorMessage("Enquiry name should not contain spaces");
				return false;
			}
			if (!text.matches(ENQUIRY_NAME_PATTERN)) {
				setErrorMessage("Enquiry name should not contain \\/:*?\"<>|");
				return false;
			}
		}
		return true;
	}
	
	public String getHeaderText(){
		return headertext.getText();
	}



}
