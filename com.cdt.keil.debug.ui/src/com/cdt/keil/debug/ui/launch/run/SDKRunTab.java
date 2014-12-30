package com.cdt.keil.debug.ui.launch.run;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SDKRunTab extends AbstractLaunchConfigurationTab {	

	private Label label;
	private Text text;
	private Button hexBrowseButton;

	@Override
	public void createControl(Composite parent) {
					
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		label = new Label(comp, SWT.NONE);
		label.setText("Hex Filename ");
		text = new Text(comp, SWT.BORDER);
		text.setLayoutData(gridData);
		
		hexBrowseButton = createPushButton(comp, "&Browse...", null);
		hexBrowseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				browseOutputFile(text, "Browse Hex File");
			}
			
			private void browseOutputFile(Text text, String string) {				
			       
				String[] extensions={"*.HEX"};
		        FileDialog fileBrowser = new FileDialog(getShell());
		        fileBrowser.setText(string);
		        fileBrowser.setFilterExtensions(extensions);
		        
		        String FileName = fileBrowser.open();
		        try {	
		            if (FileName != null) {
		                text.setText(FileName.trim());
		            }
		            
		        } catch(Exception e) {}
		        updateLaunchConfigurationDialog();
		    }
		});				
		
		comp.setLayout(gridLayout);	
	}

	@Override
	public String getName() {		
		return "SDK";
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration){}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {}

}
