package com.tel.autosysframework.actions.testvector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tel.autosysframework.configureclasses.LTEParameters;
import com.tel.autosysframework.editor.AutosysFrameworkEditor;
import com.tel.autosysframework.views.GeneralConfigure;
import com.tel.autosysframework.workspace.ProjectInformation;

public class TestVectorLibraryClass {
	private Shell sShell = null;
	private Label headingLabel = null;
	private Label subBlocksLabel = null;
	private Text subBlocksvalue = null;
	private Label dashLabel = null;
	private Button okbutton = null;
	private Button cancelbutton = null;
	private Group blocksGroup = null;
	private Label iteratorLabel = null;
	private Text blockVlaue = null;
	private Label errorlabel = null;
	private int numofblocks;
	protected boolean generate = false;

	static {
		System.loadLibrary("module_level_test_vec_gen_dll");
	}

	protected MessageBox msg;
	private VerifyListener listener;
	private SelectionListener oklistner = null;
	public native void jniTestVectorCRCOneChannel(String filepath,int numofbits);
	public native void jniTestVectorCRCTwoChannel(String filepath1,int numofbits1,int numofbits2);

	protected void SubBlockConfig(int channels) {	

		GridData gridData21 = new GridData();
		gridData21.horizontalAlignment = GridData.BEGINNING;
		gridData21.verticalAlignment = GridData.FILL;
		GridData gridData11 = new GridData();
		gridData11.horizontalAlignment = GridData.BEGINNING;
		gridData11.grabExcessHorizontalSpace = false;
		gridData11.horizontalIndent = 84;
		gridData11.verticalAlignment = GridData.FILL;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.verticalAlignment = GridData.FILL;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = GridData.FILL;
		GridData gridData1 = new GridData();
		gridData1.horizontalSpan = 2;
		gridData1.verticalAlignment = GridData.BEGINNING;
		gridData1.horizontalAlignment = GridData.FILL;
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessVerticalSpace = false;
		gridData.horizontalAlignment = GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		sShell = new Shell(AutosysFrameworkEditor.display);
		sShell.setText("TestCase Configuration");
		sShell.setLayout(gridLayout);
		sShell.setSize(new Point(300, 200));
		sShell.setImage(new Image(sShell.getDisplay(), this.getClass().getResourceAsStream("testcase.jpg")));
		headingLabel = new Label(sShell, SWT.NONE);
		headingLabel.setText("CRC Test Vector Genration Configuration");
		headingLabel.setLayoutData(gridData);
		dashLabel = new Label(sShell, SWT.NONE);
		dashLabel.setText("========================");
		dashLabel.setLayoutData(gridData1);
		subBlocksLabel = new Label(sShell, SWT.NONE);
		subBlocksLabel.setText("Number of Sub Blocks :");
		subBlocksLabel.setLayoutData(gridData2);
		subBlocksvalue = new Text(sShell, SWT.BORDER);
		subBlocksvalue.setLayoutData(gridData3);
		okbutton = new Button(sShell, SWT.NONE);
		okbutton.setText("OK");
		okbutton.setLayoutData(gridData11);
		cancelbutton = new Button(sShell, SWT.NONE);
		cancelbutton.setText("Cancel");
		cancelbutton.setLayoutData(gridData21);

		GridData gridData5 = new GridData();
		gridData5.horizontalSpan = 2;
		gridData5.verticalAlignment = GridData.FILL;
		gridData5.grabExcessVerticalSpace = true;
		gridData5.horizontalAlignment = GridData.FILL;
		errorlabel = new Label(sShell, SWT.ERROR);
		errorlabel.setForeground(new Color(sShell.getDisplay(), 255,0,0));
		errorlabel.setText("");
		errorlabel.setLayoutData(gridData5);
		
		subBlocksvalue.addVerifyListener(listener = new VerifyListener() {

			public void verifyText(VerifyEvent e) {

				//Validate the only number character.
				if(e.character == '!' || e.character == '@' || e.character == '#' ||
						e.character == '$' || e.character == '%' || e.character == '^' ||
						e.character == '&' || e.character == '*' || 
						e.character == '(' || e.character == ')'){
					e.doit = false;
					errorlabel.setText("");
					return;
				}
				if((e.keyCode >= 48 && e.keyCode <=57) 
						|| e.keyCode==127 || e.keyCode==8 
						||(e.keyCode >= 16777264 && e.keyCode <=16777273)){
					e.doit = true;					
					errorlabel.setText("");
				}else{
					e.doit = false;
				}
			}
		});

		okbutton.addSelectionListener(oklistner = new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {

				if(!subBlocksvalue.getText().equalsIgnoreCase("")
						&& (Integer.parseInt(subBlocksvalue.getText().trim()) < 21)) {
					createBlocksGroup(Integer.parseInt(subBlocksvalue.getText().trim()));
					errorlabel.setText("");
				}
				else {
					errorlabel.setText("Number of Blocks cannot be blank");
					sShell.layout(true);
					return;
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		cancelbutton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				cancelbutton.getShell().dispose();
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		sShell.open();
	}

	/**
	 * This method initializes blocksGroup	
	 *
	 */
	private void createBlocksGroup(int _numofblocks) {
		while(0 != sShell.getChildren().length)
		{
			sShell.getChildren()[0].dispose();		
		}
		numofblocks = _numofblocks;
		GridData gridData21 = new GridData();
		gridData21.horizontalAlignment = GridData.BEGINNING;
		gridData21.verticalAlignment = GridData.FILL;
		GridData gridData11 = new GridData();
		gridData11.horizontalAlignment = GridData.BEGINNING;
		gridData11.grabExcessHorizontalSpace = false;
		gridData11.horizontalIndent = 84;
		gridData11.verticalAlignment = GridData.FILL;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.verticalAlignment = GridData.FILL;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = GridData.FILL;
		GridData gridData1 = new GridData();
		gridData1.horizontalSpan = 2;
		gridData1.verticalAlignment = GridData.BEGINNING;
		gridData1.horizontalAlignment = GridData.FILL;
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessVerticalSpace = false;
		gridData.horizontalAlignment = GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		sShell.setLayout(gridLayout);
		sShell.setSize(new Point(300, 200));
		headingLabel = new Label(sShell, SWT.NONE);
		headingLabel.setText("CRC Test Vector Genration Configuration");
		headingLabel.setLayoutData(gridData);
		dashLabel = new Label(sShell, SWT.NONE);
		dashLabel.setText("========================");
		dashLabel.setLayoutData(gridData1);
		subBlocksLabel = new Label(sShell, SWT.NONE);
		subBlocksLabel.setText("Number of Sub Blocks :");
		subBlocksLabel.setLayoutData(gridData2);
		subBlocksvalue = new Text(sShell, SWT.BORDER);
		subBlocksvalue.setLayoutData(gridData3);
		subBlocksvalue.removeVerifyListener(listener);
		subBlocksvalue.setText(String.valueOf(numofblocks));
		subBlocksvalue.addVerifyListener(listener);

		GridData gridData4 = new GridData();
		gridData4.horizontalSpan = 2;
		gridData4.verticalAlignment = GridData.CENTER;
		gridData4.horizontalAlignment = GridData.FILL;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		blocksGroup = new Group(sShell, SWT.Expand);
		blocksGroup.setText("Block Values");
		blocksGroup.setLayout(gridLayout1);
		blocksGroup.setLayoutData(gridData4);
		for(int i = 0; i < numofblocks; i++){

			iteratorLabel = new Label(blocksGroup, SWT.NONE);
			iteratorLabel.setText(String.valueOf(i+1)+".");
			blockVlaue = new Text(blocksGroup, SWT.BORDER);
			blockVlaue.addVerifyListener(listener);
		}
		blocksGroup.layout(true);
		okbutton = new Button(sShell, SWT.NONE);
		okbutton.setText("Generate");
		okbutton.removeSelectionListener(oklistner);
		oklistner = null;
		okbutton.setLayoutData(gridData11);
		cancelbutton = new Button(sShell, SWT.NONE);
		cancelbutton.setText("Cancel");
		cancelbutton.setLayoutData(gridData21);

		GridData gridData5 = new GridData();
		gridData5.horizontalSpan = 2;
		gridData5.verticalAlignment = GridData.FILL;
		gridData5.grabExcessVerticalSpace = true;
		gridData5.horizontalAlignment = GridData.FILL;
		errorlabel = new Label(sShell, SWT.ERROR);
		errorlabel.setForeground(new Color(sShell.getDisplay(), 255,0,0));
		errorlabel.setText("");
		errorlabel.setLayoutData(gridData5);

		sShell.layout(true);
		
		okbutton.addSelectionListener(oklistner = new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
		subBlocksvalue.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				if(!subBlocksvalue.getText().equalsIgnoreCase("") 
						&& (Integer.parseInt(subBlocksvalue.getText().trim()) > numofblocks)
						&& (Integer.parseInt(subBlocksvalue.getText().trim()) < 21)) {
					for(int i = numofblocks; i < Integer.parseInt(subBlocksvalue.getText().trim()); i++){

						iteratorLabel = new Label(blocksGroup, SWT.NONE);
						iteratorLabel.setText(String.valueOf(i+1)+".");
						blockVlaue = new Text(blocksGroup, SWT.BORDER);
					}
					numofblocks = Integer.parseInt(subBlocksvalue.getText().trim());
					errorlabel.setText("");
				} else if(!subBlocksvalue.getText().equalsIgnoreCase("") 
						&& (Integer.parseInt(subBlocksvalue.getText().trim()) < numofblocks)) {
					while(2*(Integer.parseInt(subBlocksvalue.getText().trim())) != blocksGroup.getChildren().length)
					{
						blocksGroup.getChildren()[blocksGroup.getChildren().length - 1].dispose();
					}
					numofblocks = Integer.parseInt(subBlocksvalue.getText().trim());
					errorlabel.setText("");
				} else if(!subBlocksvalue.getText().equalsIgnoreCase("") 
						&& (Integer.parseInt(subBlocksvalue.getText().trim()) > numofblocks)
						&& (Integer.parseInt(subBlocksvalue.getText().trim()) > 20)) {
					errorlabel.setText("SubBlocks > 20 Not Supported");
					sShell.layout(true);
					return;
				}
				else {
					errorlabel.setText("Number of Blocks cannot be blank");
					sShell.layout(true);
					return;
				}
				errorlabel.setText("");
				blocksGroup.layout(true);
				sShell.layout(true);				
			}
		});	

	}
}
