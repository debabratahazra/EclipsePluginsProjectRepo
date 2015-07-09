package com.odcgroup.workbench.memoryanalyzer.ui.internal.properties;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PropertyPage;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.memoryanalyzer.MemoryAnalyzerCore;

public class MemoryPropertyPage extends PropertyPage {

	Text text;
	
	public MemoryPropertyPage() {
		super();
	}

	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		final IOfsProject ofsProject = (IOfsProject) getElement().getAdapter(IOfsProject.class);
		if(ofsProject!=null) {
			//Label for path field
			Label label = new Label(composite, SWT.NONE);
			label.setText("Memory analysis of model resource set of project " + ofsProject.getName() + ":");
	
			ResourceSet rs = getResourceSet();
			if(rs!=null) {
				text = new Text(composite, SWT.WRAP | SWT.READ_ONLY);
				text.setFont(JFaceResources.getFontRegistry().get(JFaceResources.TEXT_FONT));
				updateAnalysis();
				
				Button clearCacheButton = new Button(composite, SWT.PUSH);
				clearCacheButton.setText("Clear cache");
				clearCacheButton.addSelectionListener(new SelectionListener() {
					public void widgetSelected(SelectionEvent e) {
						BusyIndicator.showWhile(PlatformUI.getWorkbench().getDisplay(), new Runnable() {
							public void run() {
								ofsProject.clearCache();
								updateAnalysis();
							}
						});
					}
					
					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});
			}
		} else {
			Label label = new Label(composite, SWT.NONE);
			label.setText("Selected project is no valid model project!");
		}
		

		return composite;
	}
	
	private void updateAnalysis() {
		StringBuilder sb = new StringBuilder();
		for(String line : MemoryAnalyzerCore.getAnalysis(getResourceSet())) {
			sb.append(line);
			sb.append("\n");
		}
		text.setText(sb.toString());
	}
	
	private ResourceSet getResourceSet() {
		IOfsProject ofsProject = (IOfsProject) getElement().getAdapter(IOfsProject.class);
		if(ofsProject!=null) {
			return ofsProject.getModelResourceSet();
		} else {
			return null;
		}
	}
}