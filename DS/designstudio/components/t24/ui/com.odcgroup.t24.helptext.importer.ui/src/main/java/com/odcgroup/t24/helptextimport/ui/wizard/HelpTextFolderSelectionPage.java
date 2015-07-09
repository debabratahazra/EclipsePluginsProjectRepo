package com.odcgroup.t24.helptextimport.ui.wizard;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.t24.helptext.ui.Messages;



public class HelpTextFolderSelectionPage extends WizardPage implements Listener{

	private String directoryPath=null;
	private List<File> files= new ArrayList<File>();
	
	protected HelpTextFolderSelectionPage(String pageName) {
		super(pageName);
	}
	
	private HelpTextSelectionGroup hsg;
	
	public void createControl(Composite parent) {
		 initializeDialogUnits(parent);
		 
		 Composite rootFolderArea = new Composite(parent, SWT.NONE);
		 rootFolderArea.setLayout(new GridLayout());
		 rootFolderArea.setLayoutData(new GridData(GridData.FILL_BOTH
					| GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
			
		 Composite browseCom=new Composite(rootFolderArea, SWT.NONE);
		 GridLayout browseComLay= new GridLayout();
		 browseComLay.numColumns=3;
		 browseComLay.makeColumnsEqualWidth=false;
		 browseCom.setLayout(browseComLay);
		 browseCom.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		 Label label=new Label(browseCom,SWT.NONE);
		 label.setText(Messages.FolderSelectionPage_message);
		 GridData labeldata =new GridData();
		 label.setData(labeldata);
		 
		 final Text filestext=new Text(browseCom, SWT.BORDER);
		 filestext.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		 filestext.setEditable(false);
		 final Button browseButton =new Button(browseCom,SWT.None);
		 browseButton.setText(Messages.FolderSelectionPage_browse);
		 browseButton.setLayoutData(new GridData());
		 
		 hsg = new HelpTextSelectionGroup(rootFolderArea, this, ""); //$NON-NLS-1$
		 
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(browseButton.getShell(), SWT.MULTI | SWT.OPEN);
				if (dialog.open() != null) {
					setSelectedFolder(dialog.getFilterPath());
					updateHelptextSelectionGroup();
				}
				getContainer().updateButtons();
				setPageComplete(validatePage());
				if (directoryPath != null && directoryPath.trim().length() != 0) {
					filestext.setText(directoryPath);
				}
			}
		});

	     setErrorMessage(null);
	     setMessage(Messages.FolderSelectionPage_description +  Messages.FolderSelectionPage_additional_desc);	     
	     // Set the control for the receiver.
	     setControl(rootFolderArea);
	     setPageComplete(validatePage());

	}
   
	private void setSelectedFolder(String path){
		if (path!=null && path.trim().length() != 0) {
			this.directoryPath = path;
		}
	}
	
	/**
	 * @param selectedFiles
	 */
	private void setSelectedFiles(List<File> selectedFiles){
		files.addAll(selectedFiles);
	}
	
	public List<File> getSelectedFiles() {
		return files;
	}

	private void updateHelptextSelectionGroup() {
		try {
			hsg.setInput(getInputFiles());
		} catch (Exception ex) {
			hsg.setInput(Collections.EMPTY_LIST);
			setMessage(ex.getMessage(), ERROR);
		}

	}
	
	private boolean validatePage() {
		if(directoryPath!=null && directoryPath.trim().length() != 0 && hsg.getSelectedFiles().size()!=0){
			return true;
		}
		return false;
	}

	
	private void validateControls() {
		setSelectedFiles(hsg.getSelectedFiles());
		setPageComplete(! hsg.getSelectedFiles().isEmpty());
		canFlipToNextPage();
		getWizard().getContainer().updateButtons();
	}
    
	public List<File> getInputFiles(){
		List<File> fileLsit = new ArrayList<File>();
		if (directoryPath != null) {
			Collection<File> xmls = FileUtils.listFiles(new File(directoryPath), new String[] { "xml" }, true);
			return (List<File>) xmls;
		}
		return fileLsit;
	}
	
	
	public boolean canFlipToNextPage() {
		return validatePage(); 
	}

	
	@Override
	public void handleEvent(Event event) {
		validateControls();
	}
}
