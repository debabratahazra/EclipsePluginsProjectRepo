package com.tel.autosysframework.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.tel.autosysframework.views.GeneralConfigure;
import com.tel.autosysframework.wizard.AutosysFrameworkCreationWizard;

public class DesignSelection implements IWorkbenchWindowActionDelegate {


	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="17,19"
	private Label systemDesignLabel = null;
	private Label emptyLabel = null;
	private Group systemGroup = null;
	private Button lteButton = null;
	private Button vlcdButton = null;
	private Button cancelButton = null;
	private Button okButton = null;
	protected IResource resource = null;
	static final String PROJECTS_VIEW = "org.eclipse.ui.views.ResourceNavigator";
	
	public static int designSelection = -1;		//0 -> LTE, 1 -> VLCD

	public void dispose() {

	}

	public void init(IWorkbenchWindow window) {

	}

	public void run(IAction action) {
		design();
	}
	
	public void design(){
		
		sShell = new Shell();
		sShell.setText("Design Selection");
		
		sShell.setImage(new Image(sShell.getDisplay(), this.getClass().getResourceAsStream("../../../../../icons/designSelction.jpg")));

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;

		sShell.setLayout(gridLayout);
		sShell.setSize(new Point(300, 200));

		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.BEGINNING;
		gridData3.grabExcessHorizontalSpace = true;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData2 = new GridData();
		gridData2.horizontalIndent = 70;
		gridData2.verticalAlignment = GridData.CENTER;
		gridData2.grabExcessHorizontalSpace = false;
		gridData2.horizontalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.horizontalSpan = 2;
		gridData1.grabExcessHorizontalSpace = true;

		systemDesignLabel = new Label(sShell, SWT.NONE);
		systemDesignLabel.setText("Select the Sytem For Genral Configuration :");
		systemDesignLabel.setLayoutData(gridData1);
		emptyLabel = new Label(sShell, SWT.NONE);
		emptyLabel.setText("");
		new Label(sShell, SWT.NONE);
		GridData gridData5 = new GridData();
		gridData5.grabExcessHorizontalSpace = true;
		GridData gridData4 = new GridData();
		gridData4.grabExcessHorizontalSpace = true;
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		systemGroup = new Group(sShell, SWT.NONE);
		systemGroup.setLayout(new GridLayout());
		systemGroup.setLayoutData(gridData);
		lteButton = new Button(systemGroup, SWT.RADIO);
		lteButton.setText("LTE System");
		lteButton.setLayoutData(gridData4);
		
		
		vlcdButton = new Button(systemGroup, SWT.RADIO);
		vlcdButton.setText("VLCD System");
		vlcdButton.setLayoutData(gridData5);
		
		if (designSelection==0){
			lteButton.setSelection(true);
			vlcdButton.setSelection(false);
		}
		else if(designSelection==1){
			lteButton.setSelection(false);
			vlcdButton.setSelection(true);			
		}else{
			lteButton.setSelection(false);
			vlcdButton.setSelection(false);
		}
		
		okButton = new Button(sShell, SWT.NONE);
		okButton.setText("OK");
		okButton.setFocus();
		okButton.setLayoutData(gridData2);
		cancelButton = new Button(sShell, SWT.NONE);
		cancelButton.setText("Cancel");
		cancelButton.setLayoutData(gridData3);

		okButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				
				IStructuredSelection iStructSelection = null;
				IWorkbenchWindow activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

				if (activeWindow != null) {
					ISelection iSelection = activeWindow.getSelectionService().getSelection(PROJECTS_VIEW);

					if (iSelection instanceof IStructuredSelection) {
						iStructSelection = (IStructuredSelection) iSelection;
					}
					Object obj = iStructSelection.getFirstElement();

					if (obj instanceof IResource)
						resource = (IResource) obj;			
					else if (obj instanceof IAdaptable) {
						IAdaptable iAdaptable = (IAdaptable) obj;
						resource  = (IResource) iAdaptable.getAdapter(IResource.class);
					}
				}

				
				if(lteButton.getSelection()) {
					
					
					//Select the project name.....
					if (resource != null) 
					{
						designSelection = 0;
						new GeneralConfigure().createLTEPartControl(GeneralConfigure.composite, true);
					}
					else {
						okButton.getShell().close();
						MessageBox msgbox = new MessageBox(new Shell(), SWT.ERROR);
						msgbox.setMessage("Open AutosysFrameworkEditor");
						msgbox.open();
					}
				}
				else if(vlcdButton.getSelection()) {
					if(resource==null){
						okButton.getShell().close();
						MessageBox msgbox = new MessageBox(new Shell(), SWT.ERROR);
						msgbox.setText("#Error");
						msgbox.setMessage("Select or Open AutosysFramework File.");
						msgbox.open();
						return;
					}
					
					designSelection = 1;
					new GeneralConfigure().createVLCDPartControl(GeneralConfigure.composite, true);
				}
				okButton.getShell().close();
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		cancelButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				cancelButton.getShell().dispose();
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		sShell.open();
		while (!sShell.isDisposed()) {
		      if (!sShell.getDisplay().readAndDispatch())
		    	  sShell.getDisplay().sleep();
		    }
		sShell.getDisplay().dispose();
	
	}

	public void selectionChanged(IAction action, ISelection selection) {

	}

}
