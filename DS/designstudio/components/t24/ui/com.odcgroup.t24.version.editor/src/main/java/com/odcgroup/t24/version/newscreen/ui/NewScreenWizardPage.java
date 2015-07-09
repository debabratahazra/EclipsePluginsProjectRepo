package com.odcgroup.t24.version.newscreen.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.IContentAssistProvider;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.MdfEntitySelectionDialog;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.core.xtext.XtextResourceSetProviderDS;
import com.odcgroup.workbench.ui.OfsUICore;

/**
 * New Screen Wizard Page
 * @author arajeshwari
 *
 */
public class NewScreenWizardPage extends WizardPage{	
	
	private Text apptext;
	
	private Text shortNametext;
	
	private Version version;
	
	private Text fileNametext;
	
	private Text moduleName;
	
	private IProject project;
	
	private IContentAssistProvider contentAssistProvider = new IContentAssistProvider() {

        public MdfModelElement[] getCandidates() {
            return getCandidateSuperClasses();
        }

        public String getDefaultDomainName() {
            return "";
        }
    };

	private IPath iPath;
    
	private MdfModelElement[] getCandidateSuperClasses() {
        List<MdfEntity> candidates = new ArrayList<MdfEntity>();
        ResourceSet rs = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, project);
        if (rs != null) {
        	List<MdfEntity> classes = DomainRepositoryUtil.getMdfClassesWithPK(rs);
        	candidates.addAll(classes);
        }
        return (MdfModelElement[]) candidates.toArray(new MdfModelElement[candidates.size()]);
    }

	/**
	 * Create the wizard.
	 * @param version 
	 * @param iPath 
	 * @param iPath 
	 */
	public NewScreenWizardPage(Version version, IPath iPath, IProject project) {
		super("wizardPage");
		setPageComplete(false);
		setTitle("Create New Screen");
		setDescription("Create a New T24 Screen");
		this.version = version;
		this.iPath = iPath;
		this.project = project;		
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridData gd_parentComp = new GridData(GridData.FILL_BOTH);
		setControl(container);
		container.setLayoutData(gd_parentComp);
		container.setLayout(new GridLayout(1, false));
		
		Group grpScreenDetails = new Group(container, SWT.NONE);
		grpScreenDetails.setText("Screen Details");
		GridData gd_grpScreenDetails = new GridData(GridData.FILL_BOTH);

		grpScreenDetails.setLayoutData(gd_grpScreenDetails);
		grpScreenDetails.setLayout(new GridLayout(1, false));
		
		Composite composite_2 = new Composite(grpScreenDetails, SWT.NONE);
		composite_2.setLayout(new GridLayout(3, false));
		GridData gd_composite_2 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_composite_2.heightHint = 107;
		composite_2.setLayoutData(gd_composite_2);
		
		// application
		Label lblApplication = new Label(composite_2, SWT.NONE);
		lblApplication.setText("Application:");
		
		apptext = new Text(composite_2, SWT.BORDER);
		apptext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		apptext.setEditable(false);
		
		Button btnNewButton = new Button(composite_2, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MdfEntitySelectionDialog dialog = MdfEntitySelectionDialog.createDialog(getShell(), contentAssistProvider, false, "version");
			    if(dialog.open() == Window.OK) {
					MdfEntity entity = (MdfEntity) dialog.getFirstResult();
					if (entity != null) {
						String names[] = entity.getName().split("\\.");
						moduleName.setText(names[0]);
						apptext.setText(names[1].replace("_", "."));
						version.setForApplication((MdfClass)entity);
						version.setT24Name(names[1].replace("_", ".") + ",");
					}
					validateControls();
				}
			}
		});
		btnNewButton.setText("Browse...");
		
		//Module/Component name
		
		Label lblModuleName = new Label(composite_2, SWT.NONE);
		lblModuleName.setText("Module/Component:");
		
		moduleName = new Text(composite_2, SWT.BORDER);
		moduleName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		moduleName.setEditable(false);
		
		// screen name
		Label lblShortName = new Label(composite_2, SWT.NONE);
		lblShortName.setText("Screen Name:");
		
		shortNametext = new Text(composite_2, SWT.BORDER);
		shortNametext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		shortNametext.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				Text text = (Text)e.widget;
				version.setShortName(text.getText());				
				validateControls();
			}
		});
		
		shortNametext.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				/*
				 * Thierry : Ensure that the name of the version is in upper case.
				 * 2 reasons for it. First, the way we are camel-casing the resource name
				 * is not working if the version is in lower case, then on windows, since the 
				 * file system is case insensitive, this can create conflicts.
				 */
				e.text = e.text.toUpperCase();
				
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					Integer integer = new Integer(chars[i]);
					int intValue = integer.intValue();
					//special handling: allow "."
					if(intValue == 46){
						setDescription("Screen name with . " +"will be converted to _");
						return;
					}
					if ((32 <= intValue && intValue <= 47) || (91 <= intValue && intValue <= 96)) {
						if ((58 >= intValue && intValue <= 64) || (123 >= intValue && intValue <= 126)) {
						e.doit = false;
						return;
						}
					}
				}
				if(chars.length == 0){
					setDescription("Create New Screen");
				}
			}
		});
		
		// file name
		Label lblFileName = new Label(composite_2, SWT.NONE);
		lblFileName.setText("Full Name:");
		
		fileNametext = new Text(composite_2, SWT.BORDER);
		fileNametext.setEditable(false);
		fileNametext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
	}

	/**
	 * 
	 */
	private void validateControls() {
		setPageComplete(isWidgetEmpty());
		canFlipToNextPage();
		getWizard().getContainer().updateButtons();
		if(!(apptext.getText().isEmpty())){
			fileNametext.setText(getScreenName());
			IPath path = iPath.append(getScreenName() + ".version");
			if(ResourcesPlugin.getWorkspace().getRoot().exists(path)){
				setErrorMessage(OfsUICore.getDefault().getString("wizard.modelresource.validator.fileExists"));
				setPageComplete(false);
			}else{
				setErrorMessage(null);
				setPageComplete(true);
			}
		}
	}
	
	/**
	 * @return
	 */
	private boolean isWidgetEmpty() {
		if (shortNametext.getText().isEmpty()) {
			setErrorMessage("Screen Name is required.");
			return false;
		} else if (apptext.getText().isEmpty()) {
			setErrorMessage("Application is required.");
			return false;
		}
		setErrorMessage(null);
		return true;
	}
	
	public String getScreenName() {
		if(!(apptext.getText().isEmpty() && shortNametext.getText().isEmpty())){
			 String text = shortNametext.getText().replace(".", "_");
			String applnText = apptext.getText().replace(".", "_");
			return applnText + "," + text;		 
		}
		return null;
	}

	public String getShortNametext() {
		return shortNametext.getText().replace(".", "_");
	}
}
