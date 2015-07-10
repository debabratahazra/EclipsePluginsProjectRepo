package com.tel.autosysframework.wizard;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectOutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;


import com.tel.autosysframework.internal.ArrayOperation;
import com.tel.autosysframework.model.AutosysDiagram;
import com.tel.autosysframework.workspace.ProjectInformation;

public class AutosysFrameworkWizardPage extends WizardNewFileCreationPage implements SelectionListener
{

	private IWorkbench	workbench;
	private Button ltemodel = null;
	private Button vlcdmodel = null;
	
	
	public static int designselection = 0;

	public AutosysFrameworkWizardPage(IWorkbench aWorkbench, IStructuredSelection selection) {
		super("sampleAutosysPage1", selection);  //$NON-NLS-1$	
		this.setFileExtension("afw");	
		this.setTitle("Autosys File Creation Wizard");
		this.setDescription("Create a new Autosys File Resource");
		this.setImageDescriptor(ImageDescriptor.createFromFile(getClass(),"tata_logo.jpg"));  
		this.workbench = aWorkbench;
	}

	
	public void createControl(Composite parent) {

		super.createControl(parent);		
		this.setFileName("lteModel" + getCountValue() + ".afw");  //$NON-NLS-2$//$NON-NLS-1$
		
		Composite composite = (Composite)getControl();

		// sample section generation group
		Group group = new Group(composite,SWT.NONE);
		group.setLayout(new GridLayout());
		group.setText("Autosys Model Samples"); 
		group.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

		// sample section generation checkboxes
		ltemodel = new Button(group,SWT.RADIO);
		ltemodel.setText("LTE System");
		ltemodel.setSelection(true);
		
		// sample section generation checkboxes
		vlcdmodel = new Button(group,SWT.RADIO);
		vlcdmodel.setText("VLCD System");
		vlcdmodel.setSelection(false);

		ltemodel.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				designselection = 0;
				setFileName("lteModel" + getCountValue() + ".afw");  
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		
		vlcdmodel.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				designselection = 1;
				setFileName("vlcdModel" + getCountValue() + ".afw");  
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		
		new Label(composite,SWT.NONE);

		setPageComplete(validatePage());
	}


	/**
	 * Get the New File name's Counter value
	 * @return Count Value
	 */
	private int getCountValue() {

		//Listed all files available in current project	
		File[] filesList = new File(new ProjectInformation().getProjectName(4)).listFiles();
		int[] fileCount = new int[1];
		for (int i = 0, j=0; i < filesList.length; i++) {
			if(filesList[i].getName().contains("Model")){
				int _index = filesList[i].getName().lastIndexOf('.');
				if(_index==-1) return 1;
				if(_index==10)continue;
				int value = 0;
				try {
					value = Integer.parseInt(filesList[i].getName().substring(10, _index), 10);
				} catch (NumberFormatException e) {
				}catch(Exception e){}
				if(value==0) return 1;
				if(j!=0)
					fileCount = (int[]) ArrayOperation.expand(fileCount, j+1);
				fileCount[j++] = value;				
			}
		}
		fileCount = ArrayOperation.sort(fileCount);
		for (int i = 0; i < fileCount.length; i++) {
			if(fileCount[i]!=i+1) return (i+1);
		}
		return (fileCount.length+1);
	}
	
	

	protected InputStream getInitialContents() {
		
		AutosysDiagram ld = new AutosysDiagram();		
		ByteArrayInputStream bais = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(ld);
			oos.flush();
			oos.close();
			baos.close();
			bais = new ByteArrayInputStream(baos.toByteArray());
			bais.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return bais;
	}

	public boolean finish() {

		IFile newFile = createNewFile();
		if (newFile == null) 
			return false;  // ie.- creation was unsuccessful

		// Since the file resource was created fine, open it for editing
		// iff requested by the user

		try {
			IWorkbenchWindow dwindow = workbench.getActiveWorkbenchWindow();
			IWorkbenchPage page = dwindow.getActivePage();
			if (page != null)
				IDE.openEditor(page, newFile, true);
		} 
		catch (org.eclipse.ui.PartInitException e) {
			e.printStackTrace();
			return false;
		}

		
		return true;
	}

	
	
	public void widgetSelected(SelectionEvent e) {
		if( e.getSource() == ltemodel ){			
			setFileName("lteModel" + getCountValue() + ".afw");  
		}
		if( e.getSource() == vlcdmodel ){			
			setFileName("vlcdModel" + getCountValue() + ".afw");  
		}
	}

	
	
	public void widgetDefaultSelected(SelectionEvent e) {
	}

}
