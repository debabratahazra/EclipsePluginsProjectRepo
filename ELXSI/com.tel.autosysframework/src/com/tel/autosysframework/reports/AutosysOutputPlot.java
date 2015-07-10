package com.tel.autosysframework.reports;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.tel.autosysframework.editor.AutosysFrameworkEditor;
import com.tel.autosysframework.run.JavaNativeClass;
import com.tel.autosysframework.views.GeneralConfigure;

public class AutosysOutputPlot extends JavaNativeClass implements IWorkbenchWindowActionDelegate {

	private Shell shell = null;
	private Label Headinglabel = null;
	private Label datasetpathlabel = null;
	private Text datasetpathtext = null;
	private Button datasetbrowse = null;
	private Button PlotButton = null;
	private String output =null;
	
	public void dispose() {
		shell = null;
		Headinglabel = null;
		datasetpathlabel = null;
		datasetpathtext = null;
		datasetbrowse = null;
		PlotButton = null;
		output =null;
	}

	public void init(IWorkbenchWindow window) {

	}

	public void run(IAction action) {
		
		Shell plotshell = new Shell();		
		plotshell.setText("Plot");
		plotshell.setImage(new Image(plotshell.getDisplay(), this.getClass().getResourceAsStream("graph.jpg")));
		plotshell.setLayout(new GridLayout());
		
		CTabFolder cFolder = new CTabFolder(plotshell, SWT.BOTTOM | SWT.H_SCROLL | SWT.V_SCROLL);					
		cFolder.setBorderVisible(true);
		cFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		CTabItem item1 = new CTabItem(cFolder, SWT.BOTTOM | SWT.H_SCROLL | SWT.V_SCROLL);
		item1.setText("Output Plot");
		item1.setControl((new com.tel.autosysframework.reports.SWTChartViewer(cFolder, output)));
		plotshell.open();
		plotshell.setFocus();

		/*shell = new Shell(AutosysFrameworkEditor.display);
		shell.setText("Graph Generation Information");
		shell.setImage(new Image(shell.getDisplay(), this.getClass().getResourceAsStream("graph.jpg")));
		
		GridLayout gridlayout = new GridLayout();
		gridlayout.numColumns = 3;
		
		GridData griddata3 = new GridData();		
		griddata3.horizontalSpan = 3;
		griddata3.grabExcessHorizontalSpace = false;
		griddata3.horizontalAlignment = GridData.CENTER;
		griddata3.verticalAlignment = GridData.CENTER;
		
		GridData griddata1 = new GridData();		
//		griddata1.horizontalSpan = 1;
		griddata1.grabExcessHorizontalSpace = false;
		griddata1.horizontalAlignment = GridData.BEGINNING;
		griddata1.verticalAlignment = GridData.CENTER;
		
		GridData griddata4 = new GridData();		
//		griddata4.horizontalSpan = 1;
		griddata4.grabExcessHorizontalSpace = true;
		griddata4.horizontalAlignment = GridData.BEGINNING;
		griddata4.verticalAlignment = GridData.CENTER;
		
		GridData griddata5 = new GridData();		
//		griddata5.horizontalSpan = 1;
		griddata5.grabExcessHorizontalSpace = false;
		griddata5.horizontalAlignment = GridData.BEGINNING;
		griddata5.verticalAlignment = GridData.CENTER;
		
		
		Headinglabel = new Label(shell, SWT.NONE);
		Headinglabel.setText("========= Set Graph Output Path ============");
		Headinglabel.setLayoutData(griddata3);
		
		datasetpathlabel = new Label(shell, SWT.NONE);
		datasetpathlabel.setText("Source dataset :");
		datasetpathlabel.setLayoutData(griddata1);
		
		datasetpathtext = new Text(shell, SWT.READ_ONLY | SWT.BORDER);
		datasetpathtext.setToolTipText("Set Graph Output Path");
		datasetpathtext.setData(griddata4);
		datasetpathtext.setBackground(new Color(shell.getDisplay(),255, 255, 230));
		
		datasetbrowse = new Button(shell, SWT.NONE);
		datasetbrowse.setText("...");
		datasetbrowse.setLayoutData(griddata5);
		
		GridData griddata2 = new GridData();		
		griddata2.horizontalSpan = 3;
		griddata2.grabExcessHorizontalSpace = false;
		griddata2.horizontalAlignment = GridData.CENTER;
		griddata2.verticalAlignment = GridData.CENTER;
		griddata2.horizontalIndent = 30;
		
		PlotButton = new Button(shell, SWT.NONE);
		PlotButton.setText("Plot");
		PlotButton.setToolTipText("Plots The Ouput Graph");
		PlotButton.setLayoutData(griddata2);
		PlotButton.setEnabled(false);
		
		PlotButton.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
//				jniPlotGrpah(GeneralConfigure.getOutputpath(), output);
				PlotButton.getParent().dispose();
				Shell plotshell = new Shell();		
				plotshell.setText("Plot");
				
				plotshell.setLayout(new GridLayout());
				
				CTabFolder cFolder = new CTabFolder(plotshell, SWT.BOTTOM);					
				cFolder.setBorderVisible(true);
				cFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
				
				CTabItem item1 = new CTabItem(cFolder, SWT.BOTTOM);
				item1.setText("Output Plot");
				item1.setControl((new com.tel.autosysframework.reports.SWTChartViewer(cFolder, output)));
				plotshell.open();
				plotshell.setFocus();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
		datasetbrowse.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileBrowser = new FileDialog(new Shell(shell.getDisplay()));
				fileBrowser.setFilterExtensions(new String[]{"*.txt"});	
				fileBrowser.setText("DataSet Source File");
				output = fileBrowser.open();
				try {	
					if (output != null) {
						datasetpathtext.setText(output.trim());
					}
				} catch(Exception e1) {e1.printStackTrace();
				}
				PlotButton.setEnabled(true);
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
		shell.setSize(400, 150);
//		shell.pack();
		shell.setLayout(gridlayout);
		shell.layout(true);
		shell.open();*/
	}

	public void selectionChanged(IAction action, ISelection selection) {

	}

}
