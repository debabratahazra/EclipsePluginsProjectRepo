package com.tel.autosysframework.views;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.tel.autosysframework.actions.DesignSelection;
import com.tel.autosysframework.configureclasses.LTEParameters;
import com.tel.autosysframework.wizard.AutosysFrameworkWizardPage;
import com.tel.autosysframework.workspace.ProjectInformation;

public class GeneralConfigure extends ViewPart {

	private static final int ZERO = 0;
	private File file = null;
	private FileInputStream in = null;
	private ObjectInputStream ois = null;
	private FileOutputStream fout = null;
	private ObjectOutputStream oos = null;
	private static boolean objectwritten = false;
	private LTEParameters par_obj = null;

	private Label Channellabel = null;
	private Combo Channelcombo = null;
	private Label TranportBlocklabel = null;
	private Group BlockSizegroup = null;
	private Button oneBlockButton = null;
	private Button twoBlockButton = null;
	private Label TransportBlocksizelabel1 = null;
	private Text Blocksize1 = null;
	private Label TransportBlocksizelabel2 = null;
	private Text Blocksize2 = null;
	private Button GenericSaveButton = null;
	private Label HeadingLabel;

	private Button testvectorcheckbox = null; 

	public static Composite composite = null;
	private static VerifyListener listener1;
	private static VerifyListener listener2;
	public static Composite reference = null;
	private static String projectpath = null;	
	public static boolean count = true;
	private static boolean twoChannel = false;
	private static boolean GenerateTestVector = false;
	private static int blocklength1;
	private static int blocklength2;


	private Label errorlabel;
	private Label FilepathLabel_1;
	private Text FilepathTextbox_1;
	private Button BrowseButton_1;
	private Label FilepathLabel_2;
	private Text FilepathTextbox_2;
	private Button BrowseButton_2;
	private Display display;

	private String folderPath ;
	private Label FilepathLabel_out;
	private Text FilepathTextbox_out;
	private Button BrowseButton_out;

	private static String inputpath1 = "";
	private static String inputpath2 = "null";
	private static String outputpath = "";

	public static String OS_Seperator = "";



	//VLCD Config GUI
	private Label MB_SIZE_Label = null;
	private Text MB_SIZE_textbox = null;
	private Label FRAME_WIDTH_Label = null;
	private Text FRAME_WIDTH_textbox;
	private Label FRAME_HEIGHT_Label = null;
	private Text FRAME_HEIGHT_textbox;
	private Label FRAME_WIDTHxHEIGHT_Label = null;
	private Text FRAME_WIDTHxHEIGHT_textbox;
	private Label InputYUVFilepath_Label = null;
	private Text InputYUVFilepath_textbox = null;
	private Button InputBrowseButton = null;
	private Label OutputYUVFilepath_Label = null;
	private Text OutputYUVFilepath_textbox = null;
	private Button OutputBrowseButton = null;
	private Button SaveButton = null;
	public static String inputVLCDFilepath = null;
	public static String outputVLCDFilepath = null;



	public GeneralConfigure() {
		setTwoChannel(false);
	}


	public void createLTEPartControl(Composite parent, boolean add) {		

		if(parent!=null) {
			while(ZERO != parent.getChildren().length)
			{
				parent.getChildren()[0].dispose();		
			}
		}else
			return;
		//		composite =  parent;
		if(Platform.getOS().equalsIgnoreCase("win32"))
			setOS_Seperator("\\");
		else if(Platform.getOS().equalsIgnoreCase("linux"))
			setOS_Seperator("/");

		projectpath = new ProjectInformation().getProjectName(4);
		file = new File(projectpath+getOS_Seperator()+".settings");

		try {
			if(!file.exists()) {
				fout = new FileOutputStream(file);
				oos = new ObjectOutputStream(fout);
				par_obj = new LTEParameters();
				par_obj.setDefault_param("DEFAULT");
				par_obj.setMod_written(String.valueOf(false));
				par_obj.setLm_written(String.valueOf(false));
				par_obj.setPrecoding_written(String.valueOf(false));
				par_obj.setOFDM_written(String.valueOf(false));
				par_obj.setCPI_written(String.valueOf(false));
				par_obj.setGeneralparam_written(String.valueOf(false));
				par_obj.setObject_written(String.valueOf(true));
				oos.writeObject(par_obj);
				setObjectwritten(false);
				oos.close();
				fout.close();
				par_obj = null;
			} else {
				in = new FileInputStream(file);
				ois = new ObjectInputStream(in);
				par_obj = (LTEParameters) ois.readObject();
				setObjectwritten(Boolean.parseBoolean(par_obj.getGeneralparam_written().trim()));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		GridData gridData7 = new GridData();
		gridData7.horizontalAlignment = GridData.BEGINNING;
		gridData7.verticalAlignment = GridData.FILL;
		gridData7.horizontalSpan = 3;
		GridData gridData6 = new GridData();
		gridData6.horizontalAlignment = GridData.BEGINNING;
		gridData6.grabExcessHorizontalSpace = true;
		gridData6.verticalAlignment = GridData.FILL;
		GridData gridData4 = new GridData();
		gridData4.grabExcessVerticalSpace = false;
		gridData4.verticalAlignment = GridData.FILL;
		gridData4.horizontalAlignment = GridData.BEGINNING;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.BEGINNING;
		gridData3.verticalAlignment = GridData.FILL;
		GridData gridData2 = new GridData();
		gridData2.grabExcessHorizontalSpace = true;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.BEGINNING;
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.verticalAlignment = GridData.END;

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		GridLayout gridLayout = new GridLayout();
		gridData.horizontalSpan = 3;

		gridLayout.numColumns = 3;

		HeadingLabel = new Label(parent, SWT.NONE);
		HeadingLabel.setText("============ General Configuration Properties =========");
		HeadingLabel.setLayoutData(gridData7);

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		Channellabel = new Label(parent, SWT.READ_ONLY);
		Channellabel.setText("ChannelType : ");
		Channellabel.setLayoutData(gridData6);

		parent.setLayout(gridLayout);

		createChannelcombo(parent);

		new Label(parent, SWT.NONE);
		TranportBlocklabel = new Label(parent, SWT.NONE);
		TranportBlocklabel.setText("Transport Block :");

		createBlockSizegroup(parent);

		new Label(parent, SWT.NONE);
		TransportBlocksizelabel1 = new Label(parent, SWT.NONE);
		TransportBlocksizelabel1.setText("Transport Block Size 1 :");
		TransportBlocksizelabel1.setLayoutData(gridData3);
		Blocksize1 = new Text(parent, SWT.BORDER);
		Blocksize1.setLayoutData(gridData2);

		new Label(parent, SWT.NONE);
		TransportBlocksizelabel2 = new Label(parent, SWT.NONE);
		TransportBlocksizelabel2.setText("Transport Block Size 2 :");
		TransportBlocksizelabel2.setLayoutData(gridData4);
		TransportBlocksizelabel2.setEnabled(false);
		Blocksize2 = new Text(parent, SWT.BORDER);
		Blocksize2.setLayoutData(gridData1);
		Blocksize2.setEnabled(false);
		new Label(parent, SWT.NONE);	

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		testvectorcheckbox = new Button(parent, SWT.CHECK);
		testvectorcheckbox.setText("Generate Test Vector");

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		createFilepathPartControl(parent, "System");

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		GenericSaveButton = new Button(parent, SWT.NONE);
		GenericSaveButton.setText("Save Config");

		new Label(parent, SWT.NONE);
		errorlabel = new Label(parent,SWT.ERROR_CANNOT_GET_ITEM | SWT.BALLOON);
		errorlabel.setForeground(new Color(parent.getDisplay(), 255, 0, 0));
		errorlabel.setLayoutData(gridData);

		Channelcombo.add("Broadcast Channel");
		Channelcombo.add("Downlink Shared Channel");
		Channelcombo.add("Paging Channel");
		Channelcombo.add("Multicast Channel");
		Channelcombo.select(1);
		display = parent.getDisplay();

		/*parent.setBackground(new Color(parent.getDisplay(), 255,245,238));
		for(int i = 0; i < parent.getChildren().length; i++){
			parent.getChildren()[i].setBackground(new Color(parent.getChildren()[i].getDisplay(), 255,245,238));
		}*/
		reference  = parent;
		parent.setLayout(gridLayout);
		parent.layout(true);

		testvectorcheckbox.addSelectionListener(new SelectionListener() {			

			public void widgetSelected(SelectionEvent e) {
				if(testvectorcheckbox.getSelection())
					setGenerateTestVector(true);
				else
					setGenerateTestVector(false);	
				FilepathTextbox_1.setText("");
				FilepathTextbox_2.setText("");
				GenericSaveButton.setEnabled(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		Channelcombo.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				GenericSaveButton.setEnabled(true);
				if(Channelcombo.getSelectionIndex() != 1){
					TransportBlocksizelabel2.setEnabled(false);
					Blocksize2.setEnabled(false);
					twoBlockButton.setSelection(false);
					twoBlockButton.setEnabled(false);
					oneBlockButton.setSelection(true);

					FilepathLabel_2.setEnabled(false);
					FilepathTextbox_2.setEnabled(false);
					FilepathTextbox_2.setBackground(new Color(display, 235, 235, 235));
					BrowseButton_2.setEnabled(false);
				} else {
					TransportBlocksizelabel2.setEnabled(false);
					Blocksize2.setEnabled(false);
					twoBlockButton.setSelection(false);
					twoBlockButton.setEnabled(true);
					oneBlockButton.setSelection(true);
				}

			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		oneBlockButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				setTwoChannel(false);
				GenericSaveButton.setEnabled(true);
				TransportBlocksizelabel2.setEnabled(false);
				Blocksize2.setEnabled(false);

				if(Blocksize1.getText().equalsIgnoreCase(""))
					BrowseButton_1.setEnabled(false);
				else
					BrowseButton_1.setEnabled(true);
				FilepathLabel_2.setEnabled(false);
				FilepathTextbox_2.setEnabled(false);
				FilepathTextbox_2.setText("");
				setInputpath2("");
				FilepathTextbox_2.setBackground(new Color(display, 235, 235, 235));
				BrowseButton_2.setEnabled(false);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		twoBlockButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				setTwoChannel(true);				
				GenericSaveButton.setEnabled(true);
				Blocksize2.setEnabled(true);
				TransportBlocksizelabel2.setEnabled(true);
				if(Blocksize2.getText().equalsIgnoreCase(""))
					BrowseButton_2.setEnabled(false);
				else
					BrowseButton_2.setEnabled(true);				

				FilepathLabel_2.setEnabled(true);
				FilepathTextbox_2.setEnabled(true);
				FilepathTextbox_2.setBackground(new Color(display, 255, 255, 230));
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		GenericSaveButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				if(Blocksize1.getText().equalsIgnoreCase("")){
					errorlabel.setText("#ERROR: TransPort Block Size1 Cannot Be Blank");
					if((twoBlockButton.getSelection() 
							&& Blocksize2.getText().equalsIgnoreCase(""))){
						errorlabel.setText("#ERROR: TransPort Block Size 1 , 2 Cannot Be Blank");
					}
					reference.layout(true);
					return;
				}else {
					errorlabel.setText("");
				}
				setBlocklength1(Integer.parseInt(Blocksize1.getText().trim()));
				errorlabel.setText("");
				if((twoBlockButton.getSelection() 
						&& Blocksize2.getText().equalsIgnoreCase(""))){
					errorlabel.setText("#ERROR: TransPort Block Size2 Cannot Be Blank");
					reference.layout(true);
					return;
				} 

				if(twoBlockButton.getSelection()){
					setBlocklength2(Integer.parseInt(Blocksize2.getText().trim()));
				}
				errorlabel.setText("");

				try {
					if(file.exists()){
						in = new FileInputStream(file);
						ois = new ObjectInputStream(in);
						par_obj = (LTEParameters) ois.readObject();
					}else
						par_obj = new LTEParameters();					
				} catch (IOException e3) {
					e3.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}

				if(oneBlockButton.getSelection()) {
					if(Integer.parseInt(Blocksize1.getText().trim()) < 75377)
						par_obj.setBlocksize1("Transport_Block0_Size: "+Blocksize1.getText());
					else{
						GenericSaveButton.setEnabled(true);
						errorlabel.setText("#ERROR: Data block size exceeding 75376");
						reference.layout(true);						
						return;
					}		
					par_obj.setTransportBlock("Transport_Block: "+"0");
					if(!FilepathTextbox_1.getText().equalsIgnoreCase("")) {
						par_obj.setInputpath1(FilepathTextbox_1.getText());
						setInputpath1(FilepathTextbox_1.getText());
					}else {
						errorlabel.setText("#ERROR: Input File Path 1 cannot be blank");
						reference.layout(true);
						return;
					}
					par_obj.setTwoblocks(String.valueOf(false));
					errorlabel.setText("");
				}
				if(twoBlockButton.getSelection()) {
					if(Integer.parseInt(Blocksize1.getText().trim()) < 75377)
						par_obj.setBlocksize1("Transport_Block0_Size: "+Blocksize1.getText());
					else{
						GenericSaveButton.setEnabled(true);
						errorlabel.setText("#ERROR: Data block size exceeding 75376");
						reference.layout(true);
						return;
					}
					if(!FilepathTextbox_1.getText().equalsIgnoreCase("")) {
						par_obj.setInputpath1(FilepathTextbox_1.getText());
					}else {
						errorlabel.setText("#ERROR: Input File Path 1 cannot be blank");
						reference.layout(true);
						return;
					}
					errorlabel.setText("");
					if(Integer.parseInt(Blocksize2.getText().trim()) < 75377)
						par_obj.setBlocksize2("Transport_Block1_Size: "+Blocksize2.getText());
					else{
						GenericSaveButton.setEnabled(true);
						errorlabel.setText("#ERROR: Data block size exceeding 75376");
						reference.layout(true);
						return;
					}
					if(!FilepathTextbox_2.getText().equalsIgnoreCase("")) {
						par_obj.setInputpath2(FilepathTextbox_2.getText());
					}else {
						errorlabel.setText("#ERROR: Input File Path 2 cannot be blank");
						reference.layout(true);
						return;
					}
					errorlabel.setText("");						
					par_obj.setTransportBlock("Transport_Block: "+ "1");
					par_obj.setTwoblocks(String.valueOf(true));
				}

				switch(Channelcombo.getSelectionIndex()) {
				case 0:
					par_obj.setChannelType("Channel: "+"Broadcast_Channel");
					break;
				case 1:
					par_obj.setChannelType("Channel: "+"Downlink_Shared_Channel");
					break;
				case 2:
					par_obj.setChannelType("Channel: "+"Paging_Channel");
					break;
				case 3:
					par_obj.setChannelType("Channel: "+"Multicast_Channel");
					break;
				}

				if(!FilepathTextbox_out.getText().equalsIgnoreCase("")) {
					par_obj.setOutputpath(FilepathTextbox_out.getText());
				}else {
					errorlabel.setText("#ERROR: Output File Path  cannot be blank");
					reference.layout(true);
					return;
				}
				errorlabel.setText("");	
				par_obj.setGenerateTestVector(String.valueOf(isGenerateTestVector()));
				par_obj.setGeneralparam_written(String.valueOf(true));

				try {
					fout = new FileOutputStream(file);
					oos = new ObjectOutputStream(fout);
					oos.writeObject(par_obj);
					oos.close();
					fout.close();
					setObjectwritten(true);
					par_obj = null;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				GenericSaveButton.setEnabled(false);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		Blocksize1.addKeyListener(new KeyListener() {

			public void keyReleased(KeyEvent e) {
				GenericSaveButton.setEnabled(true);	
				if(!Blocksize1.getText().equalsIgnoreCase("")){
					BrowseButton_1.setEnabled(true);
					FilepathTextbox_out.setEnabled(true);
					FilepathLabel_out.setEnabled(true);
					BrowseButton_out.setEnabled(true);
				} else 
					BrowseButton_1.setEnabled(false);
				FilepathTextbox_out.setEnabled(true);
				FilepathLabel_out.setEnabled(true);
				BrowseButton_out.setEnabled(true);
				if(isGenerateTestVector())
					refreshFilepath_1();
			}

			private void refreshFilepath_1() {
				long BitsNumber = 0 ;
				if(!Blocksize1.getText().equalsIgnoreCase("")){
					BitsNumber = Long.parseLong(Blocksize1.getText());
				}

				if(null!=folderPath || !FilepathTextbox_1.getText().equalsIgnoreCase("")){
					//Using Folder browser.
					if(null!=folderPath){
						if(folderPath.endsWith(":\\")  || folderPath.endsWith("/"))
							FilepathTextbox_1.setText(folderPath +  "System"  
									+ "_" + BitsNumber + "-1.ini");
						else
							FilepathTextbox_1.setText(folderPath + getOS_Seperator() + "System" 
									+ "_" + BitsNumber + "-1.ini");
					}else if(!FilepathTextbox_1.getText().equalsIgnoreCase("")){
						//Without using folder browser.
						int _index = FilepathTextbox_1.getText().lastIndexOf("_");
						String string = FilepathTextbox_1.getText().substring(0,_index + 1);
						string += BitsNumber + "-1.ini";
						FilepathTextbox_1.setText(string);						
					}
				}

			}

			public void keyPressed(KeyEvent e) {

			}
		});
		Blocksize1.addVerifyListener(listener1 = new VerifyListener() {

			public void verifyText(VerifyEvent e) {

				//Validate the only number character.
				if(e.character == '!' || e.character == '@' || e.character == '#' ||
						e.character == '$' || e.character == '%' || e.character == '^' ||
						e.character == '&' || e.character == '*' || 
						e.character == '(' || e.character == ')'){
					e.doit = false;
					GenericSaveButton.setEnabled(false);
					return;
				}
				if((e.keyCode >= 48 && e.keyCode <=57) 
						|| e.keyCode==127 || e.keyCode==8 
						||(e.keyCode >= 16777264 && e.keyCode <=16777273)){
					e.doit = true;					
					GenericSaveButton.setEnabled(true);	


				}else{
					e.doit = false;
				}
			}
		});

		Blocksize2.addKeyListener(new KeyListener() {

			public void keyReleased(KeyEvent e) {
				GenericSaveButton.setEnabled(true);

				if(!Blocksize2.getText().equalsIgnoreCase("")){
					BrowseButton_2.setEnabled(true);
				} else 
					BrowseButton_2.setEnabled(false);
			}

			public void keyPressed(KeyEvent e) {

			}
		});
		Blocksize2.addVerifyListener(listener2 = new VerifyListener() {

			public void verifyText(VerifyEvent e) {
				//Validate the only number character.
				if(e.character == '!' || e.character == '@' || e.character == '#' ||
						e.character == '$' || e.character == '%' || e.character == '^' ||
						e.character == '&' || e.character == '*' || 
						e.character == '(' || e.character == ')'){
					e.doit = false;
					GenericSaveButton.setEnabled(false);
					return;
				}
				if((e.keyCode >= 48 && e.keyCode <=57) 
						|| e.keyCode==127 || e.keyCode==8 
						||(e.keyCode >= 16777264 && e.keyCode <=16777273)){
					e.doit = true;
					GenericSaveButton.setEnabled(true);									
				}else{
					e.doit = false;
				}
			}
		});

		if(isObjectwritten()) {
			GenericSaveButton.setEnabled(false);
			try {
				in = new FileInputStream(file);
				ois = new ObjectInputStream(in);
				par_obj = (LTEParameters) ois.readObject();
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			int selection = 1;
			if(par_obj.getChannelType().substring(par_obj.getChannelType().indexOf(":")+2).equalsIgnoreCase("Broadcast_Channel"))
				selection = 0;
			else if(par_obj.getChannelType().substring(par_obj.getChannelType().indexOf(":")+2).equalsIgnoreCase("Downlink_Shared_Channel"))
				selection = 1;
			else if(par_obj.getChannelType().substring(par_obj.getChannelType().indexOf(":")+2).equalsIgnoreCase("Paging_Channel"))
				selection = 2;
			else if(par_obj.getChannelType().substring(par_obj.getChannelType().indexOf(":")+2).equalsIgnoreCase("Multicast_Channel"))
				selection = 3;
			Channelcombo.select(selection);
			if(selection != 1) {
				twoBlockButton.setSelection(false);
				twoBlockButton.setEnabled(false);
				TransportBlocksizelabel2.setEnabled(false);
				Blocksize2.setText("");
				Blocksize2.setEnabled(false);
			}
			boolean twoblocks = Boolean.parseBoolean(par_obj.getTwoblocks());
			if(twoblocks) {
				TransportBlocksizelabel2.setEnabled(true);
				Blocksize2.setEnabled(true);
				twoBlockButton.setSelection(true);
				twoBlockButton.setEnabled(true);
				oneBlockButton.setSelection(false);
				setTwoChannel(true);
				TransportBlocksizelabel2.setEnabled(true);
				Blocksize2.setEnabled(true);

				Blocksize2.removeVerifyListener(listener2);
				Blocksize2.setText(par_obj.getBlocksize2().substring(par_obj.getBlocksize2().indexOf(":")+2).trim());
				Blocksize2.addVerifyListener(listener2);
				setBlocklength2(Integer.parseInt(Blocksize2.getText().trim()));
				BrowseButton_2.setEnabled(true);
			} else {
				TransportBlocksizelabel2.setEnabled(false);
				Blocksize2.setEnabled(false);
				twoBlockButton.setSelection(false);
				oneBlockButton.setSelection(true);
				setTwoChannel(false);
			}
			Blocksize1.removeVerifyListener(listener1);
			Blocksize1.setText(par_obj.getBlocksize1().substring(par_obj.getBlocksize1().indexOf(":")+2).trim());
			Blocksize1.addVerifyListener(listener1);
			setBlocklength1(Integer.parseInt(Blocksize1.getText().trim()));
			BrowseButton_1.setEnabled(true);
			BrowseButton_out.setEnabled(true);

			testvectorcheckbox.setSelection(Boolean.parseBoolean(par_obj.getGenerateTestVector()));
			if(testvectorcheckbox.getSelection())
				setGenerateTestVector(true);
			else
				setGenerateTestVector(false);

			FilepathTextbox_1.setText(par_obj.getInputpath1());
			setInputpath1(par_obj.getInputpath1());

			BrowseButton_1.setEnabled(true);
			if(twoBlockButton.getSelection()) {				
				FilepathTextbox_2.setEnabled(true);
				FilepathTextbox_2.setBackground(new Color(parent.getDisplay(),255, 255, 230));
				FilepathLabel_2.setEnabled(true);
				BrowseButton_2.setEnabled(true);
				FilepathTextbox_2.setText(par_obj.getInputpath2());
				setInputpath2(par_obj.getInputpath2());

			}
			FilepathTextbox_out.setEnabled(true);
			FilepathTextbox_out.setBackground(new Color(parent.getDisplay(),255, 255, 230));
			FilepathLabel_out.setEnabled(true);
			BrowseButton_out.setEnabled(true);
			FilepathTextbox_out.setText(par_obj.getOutputpath());

			setOutputpath(par_obj.getOutputpath());

			try {
				in.close();
				ois.close();
				par_obj = null;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 

		if(GenericSaveButton.getEnabled())
			GenericSaveButton.setFocus();
	}


	/**
	 * Create the File path GUI in View.
	 * @param parent
	 * @param moduleName
	 */
	private void createFilepathPartControl(Composite parent, final String moduleName) {

		GridData gridData2 = new GridData();
		gridData2.horizontalIndent = 0;
		gridData2.verticalAlignment = GridData.CENTER;
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.horizontalAlignment = GridData.FILL;

		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.verticalAlignment = GridData.CENTER;

		FilepathLabel_1 = new Label(parent, SWT.NONE);
		FilepathLabel_1.setText("Generated Input Filepath 1:");

		FilepathTextbox_1 = new Text(parent, SWT.BORDER);
		FilepathTextbox_1.setLayoutData(gridData2);
		FilepathTextbox_1.setEditable(false);
		FilepathTextbox_1.setBackground(new Color(parent.getDisplay(),255, 255, 230));

		BrowseButton_1 = new Button(parent, SWT.NONE);
		BrowseButton_1.setText("Browse...");
		BrowseButton_1.setEnabled(false);

		composite = parent;
		BrowseButton_1.addMouseListener(new MouseListener() {

			private String folderPath_1;
			public void mouseUp(MouseEvent e) {
				String output = new String();
				long BitsNumber = 0 ;
				if(!Blocksize1.getText().equals("")){
					BitsNumber = Long.parseLong(Blocksize1.getText());
				}
				if(!FilepathTextbox_1.equals("")){	
					output = FilepathTextbox_1.getText();
				}
				if(GenerateTestVector) {

					DirectoryDialog dialog = new DirectoryDialog(new Shell(composite.getDisplay()));				
					folderPath_1 = dialog.open();
					if(null!=folderPath_1){
						if(folderPath_1.endsWith(":\\") || folderPath_1.endsWith("/")){
							FilepathTextbox_1.setText(folderPath_1 +  moduleName  
									+ "_" + BitsNumber + "-1.ini");
							folderPath_1 += "Output.txt";
						}
						else{
							FilepathTextbox_1.setText(folderPath_1 + getOS_Seperator() + moduleName 
									+ "_" + BitsNumber + "-1.ini");
							folderPath_1 += getOS_Seperator() + "Output.txt";
						}
						FilepathTextbox_out.setText(folderPath_1);
						if(oneBlockButton.getSelection()){
							GenericSaveButton.setEnabled(true);
						}else if(twoBlockButton.getSelection() && !FilepathTextbox_2.getText().equalsIgnoreCase("")){
							GenericSaveButton.setEnabled(true);
						}else if(twoBlockButton.getSelection() && FilepathTextbox_2.getText().equalsIgnoreCase("")){
							GenericSaveButton.setEnabled(false);
						}
					}else{
						FilepathTextbox_1.setText(output);
					}
				} else {
					FileDialog fileBrowser = new FileDialog(new Shell(composite.getDisplay()),SWT.MULTI);
					fileBrowser.setFilterExtensions(new String[]{"*.ini"});	
					fileBrowser.setText("Source File");
					String FileName = fileBrowser.open();
					System.out.println(FileName);
					try {	
						if (FileName != null) {
							FilepathTextbox_1.setText(FileName.trim());
							int _index = FileName.lastIndexOf(getOS_Seperator());
							FileName = FileName.substring(0, _index+1);
							FileName += "Output.txt";
							FilepathTextbox_out.setText(FileName);
						}
					} catch(Exception e2) {e2.printStackTrace();}
				}
				setInputpath1(FilepathTextbox_1.getText());
				setOutputpath(FilepathTextbox_out.getText());
				composite.setFocus();
				GenericSaveButton.setEnabled(true);
			}
			public void mouseDown(MouseEvent e) {
			}
			public void mouseDoubleClick(MouseEvent e) {
			}
		});


		FilepathLabel_2 = new Label(parent, SWT.NONE);
		FilepathLabel_2.setText("Generated Input Filepath 2:");
		FilepathLabel_2.setEnabled(false);

		FilepathTextbox_2 = new Text(parent, SWT.BORDER);
		FilepathTextbox_2.setEditable(false);
		FilepathTextbox_2.setBackground(new Color(parent.getDisplay(),235, 235, 235));
		FilepathTextbox_2.setEnabled(false);
		FilepathTextbox_2.setLayoutData(gridData3);
		BrowseButton_2 = new Button(parent, SWT.NONE);
		BrowseButton_2.setText("Browse...");
		BrowseButton_2.setEnabled(false);

		BrowseButton_2.addMouseListener(new MouseListener() {

			private String folderPath_1;
			public void mouseUp(MouseEvent e) {
				String output = new String();
				long BitsNumber = 0 ;
				if(!Blocksize1.getText().equals("")){
					BitsNumber = Long.parseLong(Blocksize1.getText());
				}
				if(!FilepathTextbox_2.equals("")){	
					output = FilepathTextbox_2.getText();
				}				
				if(isGenerateTestVector()){
					DirectoryDialog dialog = new DirectoryDialog(new Shell(composite.getDisplay()));				
					folderPath_1 = dialog.open();
					if(null!=folderPath_1){
						if(folderPath_1.endsWith(":\\")  || folderPath_1.endsWith("/"))
							FilepathTextbox_2.setText(folderPath_1 +  moduleName  
									+ "_" + BitsNumber + "-2.ini");
						else
							FilepathTextbox_2.setText(folderPath_1 + getOS_Seperator() + moduleName 
									+ "_" + BitsNumber + "-2.ini");
						if(oneBlockButton.getSelection()){
							GenericSaveButton.setEnabled(true);
						}else if(twoBlockButton.getSelection() && !FilepathTextbox_2.getText().equalsIgnoreCase("")){
							GenericSaveButton.setEnabled(true);
						}else if(twoBlockButton.getSelection() && FilepathTextbox_2.getText().equalsIgnoreCase("")){
							GenericSaveButton.setEnabled(false);
						}
					}else{
						FilepathTextbox_2.setText(output);
					}
				} else {
					FileDialog fileBrowser = new FileDialog(new Shell(composite.getDisplay()));
					fileBrowser.setFilterExtensions(new String[]{"*.ini"});	
					fileBrowser.setText("Source File");
					String FileName = fileBrowser.open();
					try {	
						if (FileName != null) {
							FilepathTextbox_2.setText(FileName.trim());
						}
					} catch(Exception e1) {e1.printStackTrace();
					}
				}
				setInputpath2(FilepathTextbox_2.getText());
				composite.setFocus();
				GenericSaveButton.setEnabled(true);
			}
			public void mouseDown(MouseEvent e) {
			}
			public void mouseDoubleClick(MouseEvent e) {
			}
		});		


		FilepathLabel_out = new Label(parent, SWT.NONE);
		FilepathLabel_out.setText("Generated Output Filepath :");
		FilepathLabel_out.setEnabled(true);

		FilepathTextbox_out = new Text(parent, SWT.BORDER);
		FilepathTextbox_out.setEditable(false);
		FilepathTextbox_out.setBackground(new Color(parent.getDisplay(),255, 255, 230));
		FilepathTextbox_out.setEnabled(true);
		FilepathTextbox_out.setLayoutData(gridData3);
		BrowseButton_out = new Button(parent, SWT.NONE);
		BrowseButton_out.setText("Browse...");
		BrowseButton_out.setEnabled(false);
		BrowseButton_out.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				String output = new String();
				if(FilepathTextbox_out.getText().equals("")){	
					output = "";
				}else{
					output = FilepathTextbox_out.getText();					
				}
				DirectoryDialog dialog = new DirectoryDialog(new Shell(composite.getDisplay()));				
				String folder = dialog.open();
				if(null!=folder){
					if(folder.endsWith(":\\") || folder.endsWith("/")){
						FilepathTextbox_out.setText(folder + "Output.txt");
					}
					else {
						FilepathTextbox_out.setText(folder + getOS_Seperator()+"Output.txt");
					}
				}else{
					FilepathTextbox_out.setText(output);
				}
				setOutputpath(FilepathTextbox_out.getText());
				composite.setFocus();
				GenericSaveButton.setEnabled(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

	}


	public void createPartControl(Composite parent){

		//Default View
		if(parent==null) {
			if(composite != null)
				parent = composite; 
			else {
				return;
			}
		}
		System.out.println(parent.getStyle());
		System.out.println(parent.getLayout());
		//Composite temp;
		if(parent!=null) {
			while(ZERO != parent.getChildren().length)
			{
				parent.getChildren()[0].dispose();		
			}
		}


		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;

		GridData gridData10 = new GridData();
		gridData10.grabExcessHorizontalSpace = true;
		gridData10.verticalAlignment = GridData.CENTER;
		gridData10.horizontalSpan = 4;
		gridData10.horizontalAlignment = GridData.FILL;
		HeadingLabel = new Label(parent, SWT.NONE);
		HeadingLabel.setText("========== Select Design ==========");
		HeadingLabel.setLayoutData(gridData10);

		////parent.setbackground(new Color(parent.getDisplay(), 255, 250, 250));
		parent.setLayout(new FillLayout(SWT.V_SCROLL | SWT.H_SCROLL | SWT.WRAP));
		composite = parent;
		//composite.setBackground(new Color(parent.getDisplay(), 255, 250, 250));
		composite.layout(true);
		parent.layout(true);


	}


	@Override
	public void dispose() {
		composite = null;
		super.dispose();
	}

	/**
	 * This method initializes Channelcombo	
	 *
	 */
	private void createChannelcombo(Composite parent) {
		Channelcombo = new Combo(parent, SWT.READ_ONLY);
		parent.layout(true);
	}

	/**
	 * This method initializes BlockSizegroup	
	 *
	 */
	private void createBlockSizegroup(Composite parent) {

		GridData gridData5 = new GridData();
		gridData5.grabExcessHorizontalSpace = true;
		gridData5.verticalAlignment = GridData.CENTER;
		gridData5.grabExcessVerticalSpace = false;
		gridData5.heightHint = -1;
		gridData5.horizontalAlignment = GridData.BEGINNING;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		BlockSizegroup = new Group(parent, SWT.NONE);
		BlockSizegroup.setLayout(gridLayout1);
		BlockSizegroup.setLayoutData(gridData5);
		oneBlockButton = new Button(BlockSizegroup, SWT.RADIO);
		oneBlockButton.setText("OneBlock");
		oneBlockButton.setSelection(true);
		twoBlockButton = new Button(BlockSizegroup, SWT.RADIO);
		twoBlockButton.setText("TwoBlocks");

		parent.layout(true);
	}

	@Override
	public void setFocus() {

	}


	public static void setGenerateTestVector(boolean generateTestVector) {
		GenerateTestVector = generateTestVector;
	}


	public static boolean isGenerateTestVector() {
		return GenerateTestVector;
	}


	public static void setInputpath1(String inputpath1) {
		GeneralConfigure.inputpath1 = inputpath1;
	}


	public static String getInputpath1() {
		return inputpath1;
	}


	public static void setInputpath2(String inputpath2) {
		GeneralConfigure.inputpath2 = inputpath2;
	}


	public static String getInputpath2() {
		return inputpath2;
	}


	public static void setOutputpath(String outputpath) {
		GeneralConfigure.outputpath = outputpath;
	}


	public static String getOutputpath() {
		return outputpath;
	}


	public static void setTwoChannel(boolean twoChannel) {
		GeneralConfigure.twoChannel = twoChannel;
	}


	public static boolean isTwoChannel() {
		return twoChannel;
	}


	public static void setBlocklength1(int blocklength1) {
		GeneralConfigure.blocklength1 = blocklength1;
	}


	public static int getBlocklength1() {
		return blocklength1;
	}


	public static void setBlocklength2(int blocklength2) {
		GeneralConfigure.blocklength2 = blocklength2;
	}


	public static int getBlocklength2() {
		return blocklength2;
	}


	public boolean isObjectwritten() {
		return objectwritten;
	}


	public void setObjectwritten(boolean objectwritten) {
		this.objectwritten = objectwritten;
	}


	public void setOS_Seperator(String oS_Seperator) {
		OS_Seperator = oS_Seperator;
	}


	public String getOS_Seperator() {
		return OS_Seperator;
	}


	public void createVLCDPartControl(Composite parent, boolean b) {
		//Default View
		if(parent==null) {
			if(composite != null)
				parent = composite; 
			else {
				return;
			}
		}
		System.out.println(parent.getStyle());
		System.out.println(parent.getLayout());
		//Composite temp;
		if(parent!=null) {
			while(ZERO != parent.getChildren().length)
			{
				parent.getChildren()[0].dispose();		
			}
		}




		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;

		GridData gridData10 = new GridData();
		gridData10.grabExcessHorizontalSpace = true;
		gridData10.verticalAlignment = GridData.CENTER;
		gridData10.horizontalSpan = 4;
		gridData10.horizontalAlignment = GridData.FILL;
		HeadingLabel = new Label(parent, SWT.NONE);
		HeadingLabel.setText("========== VLCD Configuration Properties ==========");
		HeadingLabel.setLayoutData(gridData10);

		if(AutosysFrameworkWizardPage.designselection != 1 && DesignSelection.designSelection == -1){
			parent.setLayout(gridLayout);


			////parent.setbackground(new Color(parent.getDisplay(), 255, 250, 250));
			//parent.setLayout(new FillLayout(SWT.V_SCROLL | SWT.H_SCROLL | SWT.WRAP));
			composite = parent;
			//composite.setBackground(new Color(parent.getDisplay(), 255, 250, 250));
			composite.layout(true);
			parent.layout(true);

			return;
		}


		if(Platform.getOS().equalsIgnoreCase("win32"))
			setOS_Seperator("\\");
		else if(Platform.getOS().equalsIgnoreCase("linux"))
			setOS_Seperator("/");





		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.verticalAlignment = GridData.CENTER;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = GridData.CENTER;
		/*GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;*/

		MB_SIZE_Label = new Label(parent, SWT.NONE);
		MB_SIZE_Label.setText("MB_SIZE : ");
		MB_SIZE_textbox = new Text(parent, SWT.BORDER);
		MB_SIZE_textbox.setText("8");
		MB_SIZE_textbox.setEditable(false);
		MB_SIZE_textbox.setBackground(new Color(parent.getDisplay(), 255,255,220));
		new Label(parent, SWT.NONE);

		FRAME_WIDTH_Label = new Label(parent, SWT.NONE);
		FRAME_WIDTH_Label.setText("FRAME_WIDTH : ");
		FRAME_WIDTH_textbox = new Text(parent, SWT.BORDER);
		FRAME_WIDTH_textbox.setText("352");
		FRAME_WIDTH_textbox.setEditable(false);
		FRAME_WIDTH_textbox.setBackground(new Color(parent.getDisplay(), 255,255,220));
		new Label(parent, SWT.NONE);

		FRAME_HEIGHT_Label = new Label(parent, SWT.NONE);
		FRAME_HEIGHT_Label.setText("FRAME_HEIGHT : ");
		FRAME_HEIGHT_textbox = new Text(parent, SWT.BORDER);
		FRAME_HEIGHT_textbox.setText("288");
		FRAME_HEIGHT_textbox.setEditable(false);
		FRAME_HEIGHT_textbox.setBackground(new Color(parent.getDisplay(), 255,255,220));
		new Label(parent, SWT.NONE);

		FRAME_WIDTHxHEIGHT_Label = new Label(parent, SWT.NONE);
		FRAME_WIDTHxHEIGHT_Label.setText("FRAME_WIDTH x HEIGHT : ");
		FRAME_WIDTHxHEIGHT_textbox = new Text(parent, SWT.BORDER);
		FRAME_WIDTHxHEIGHT_textbox.setText("101376");
		FRAME_WIDTHxHEIGHT_textbox.setEditable(false);
		FRAME_WIDTHxHEIGHT_textbox.setBackground(new Color(parent.getDisplay(), 255,255,220));
		new Label(parent, SWT.NONE);
		InputYUVFilepath_Label = new Label(parent, SWT.NONE);
		InputYUVFilepath_Label.setText("Input YUV File Path : ");
		InputYUVFilepath_textbox = new Text(parent, SWT.BORDER);
		InputYUVFilepath_textbox.setLayoutData(gridData1);
		InputYUVFilepath_textbox.setEditable(false);
		InputYUVFilepath_textbox.setBackground(new Color(parent.getDisplay(), 255,255,220));
		InputBrowseButton = new Button(parent, SWT.NONE);
		InputBrowseButton.setText("Browse...");

		InputBrowseButton.addMouseListener(new MouseListener() {
		
			public void mouseUp(MouseEvent e) {
				FileDialog fileBrowser = new FileDialog(new Shell(composite.getDisplay()),SWT.MULTI);
				fileBrowser.setFilterExtensions(new String[]{"*.YUV"});	
				fileBrowser.setText("Source File");
				String FileName = fileBrowser.open();
				try {	
					if (FileName != null) {
						InputYUVFilepath_textbox.setText(FileName.trim());
						int _index = FileName.lastIndexOf(getOS_Seperator());
						FileName = FileName.substring(0, _index+1);
						FileName += "DumpOutput.YUV";
						OutputYUVFilepath_textbox.setText(FileName);
						SaveButton.setEnabled(true);
						SaveButton.setFocus();
					}
				} catch(Exception e2) {e2.printStackTrace();}

			}

			public void mouseDown(MouseEvent e) {

			}

			public void mouseDoubleClick(MouseEvent e) {

			}
		});

		OutputYUVFilepath_Label = new Label(parent, SWT.NONE);
		OutputYUVFilepath_Label.setText("Output YUV File Path : ");
		OutputYUVFilepath_textbox = new Text(parent, SWT.BORDER);
		OutputYUVFilepath_textbox.setLayoutData(gridData);
		OutputYUVFilepath_textbox.setEditable(false);
		OutputYUVFilepath_textbox.setBackground(new Color(parent.getDisplay(), 255,255,220));
		OutputBrowseButton = new Button(parent, SWT.NONE);
		OutputBrowseButton.setText("Browse...");

		OutputBrowseButton.addMouseListener(new MouseListener() {

			public void mouseUp(MouseEvent e) {

				DirectoryDialog dialog = new DirectoryDialog(new Shell(composite.getDisplay()));				
				String folderPath = dialog.open();
				if(null!=folderPath){
					if(folderPath.endsWith(":\\") || folderPath.endsWith("/")){
						folderPath += "DumpOutput.YUV";
					}
					else{
						folderPath += "\\DumpOutput.YUV";
					}
					OutputYUVFilepath_textbox.setText(folderPath);
					SaveButton.setEnabled(true);
					SaveButton.setFocus();
				}
			}


			public void mouseDoubleClick(MouseEvent e) {

			}


			public void mouseDown(MouseEvent e) {
				
			}
		});

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		SaveButton = new Button(parent, SWT.NONE);
		SaveButton.setText("Save ");
		SaveButton.setLayoutData(gridData2);
		SaveButton.setFocus();
		SaveButton.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {

				if (!InputYUVFilepath_textbox.getText().equalsIgnoreCase("") ||
						!OutputYUVFilepath_textbox.getText().equalsIgnoreCase("")){
					//TODO
					OutputBrowseButton.setFocus();
					SaveButton.setEnabled(false);
					
					inputVLCDFilepath = InputYUVFilepath_textbox.getText();
					outputVLCDFilepath = OutputYUVFilepath_textbox.getText();
				}else{
					//Config not completed.
					
					SaveButton.setEnabled(true);
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});




		parent.setLayout(gridLayout);


		////parent.setbackground(new Color(parent.getDisplay(), 255, 250, 250));
		//parent.setLayout(new FillLayout(SWT.V_SCROLL | SWT.H_SCROLL | SWT.WRAP));
		composite = parent;
		//composite.setBackground(new Color(parent.getDisplay(), 255, 250, 250));
		composite.layout(true);
		parent.layout(true);
	}
}
