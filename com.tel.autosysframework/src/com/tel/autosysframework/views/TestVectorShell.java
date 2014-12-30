///*************************************************
// * Create Test Vector Pop-up Shell Window
// *************************************************/
//
//package com.tel.autosysframework.views;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.KeyEvent;
//import org.eclipse.swt.events.KeyListener;
//import org.eclipse.swt.events.MouseEvent;
//import org.eclipse.swt.events.MouseListener;
//import org.eclipse.swt.events.VerifyEvent;
//import org.eclipse.swt.events.VerifyListener;
//import org.eclipse.swt.graphics.Color;
//import org.eclipse.swt.graphics.Image;
//import org.eclipse.swt.layout.FillLayout;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.DirectoryDialog;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Group;
//import org.eclipse.swt.widgets.Label;
//import org.eclipse.swt.widgets.Shell;
//import org.eclipse.swt.widgets.Text;
//import org.eclipse.ui.PlatformUI;
//
//
//public class TestVectorShell {
//
//	private Label NOBitLabel ;
//	private Text NOBitTextbox;
//	private Button BrowseButton_1 ;
//	private Button BrowseButton_2 ;
//	private Label FilepathLabel_1 ;
//	private Label FilepathLabel_2 ;
//	private Text FilepathTextbox_1;
//	private Text FilepathTextbox_2;
//	private Button ExecuteButton ;
//	private String folderPath ;
//	private Group group;
//	private Button OneChRadioButton;
//	private Button TwoChRadioButton;
//	private boolean twoChannel;
//	private Display display;
//	private Shell shell;
//	private Button CancelButton;
//
//	public TestVectorShell() {
//		twoChannel = TestVector.twoChannel;
//	}
//
//	/**
//	 * Design the Shell
//	 * @param moduleName
//	 */
//	public void showForm(String moduleName){
//
//		/*display = PlatformUI.getWorkbench().getDisplay();
//		shell = new Shell(display);
//
//		//Create the new Shell
//		shell.setLayout(new FillLayout());		
//		shell.setText("Test Vector: " + moduleName );
//		shell.setImage(new Image(display,this.getClass().getResourceAsStream("icons/load_tv.jpg")));
//		shell.setSize(450, 250);
//
//		//Design the Shell
//		createPartControl(moduleName);
//
//		//Set the Shell size and open it.
//		shell.setMaximized(false);
//		shell.setModified(false);
//		shell.setLocation(280, 200);
//		shell.open();*/
//	}
//
//
//	/**
//	 * Create the Pop-up Shell for Test Vector
//	 * @param moduleName
//	 */
//	private void createPartControl(final String moduleName) {	
//
//		GridData gridData4 = new GridData();
//		gridData4.horizontalAlignment = GridData.END;
//		gridData4.verticalAlignment = GridData.CENTER;
//		GridData gridData = new GridData();
//		gridData.horizontalAlignment = GridData.BEGINNING;
//		gridData.grabExcessHorizontalSpace = true;
//		gridData.horizontalIndent = 9;
//		gridData.heightHint = -1;
//		gridData.widthHint = 81;
//		gridData.verticalAlignment = GridData.CENTER;
//		GridLayout gridLayout = new GridLayout();
//		gridLayout.numColumns = 3;
//		gridLayout.verticalSpacing = 15;
//		gridLayout.marginWidth = 7;
//		gridLayout.marginHeight = 5;
//		gridLayout.horizontalSpacing = 4;
//		
//		NOBitLabel = new Label(shell, SWT.NONE);
//		NOBitLabel.setText("Numner of Bits :");
//		NOBitTextbox = new Text(shell, SWT.BORDER);
//		NOBitTextbox.setBackground(new Color(shell.getDisplay(),255, 255, 230));
//		NOBitTextbox.setLayoutData(gridData);
//
//
//		NOBitTextbox.addVerifyListener(new VerifyListener() {
//
//			public void verifyText(VerifyEvent e) {
//				//Validate the only number character.
//				if(e.character == '!' || e.character == '@' || e.character == '#' ||
//						e.character == '$' || e.character == '%' || e.character == '^' ||
//						e.character == '&' || e.character == '*' || 
//						e.character == '(' || e.character == ')'){
//					e.doit = false;
//					return;
//				}
//				if((e.keyCode >= 48 && e.keyCode <=57) 
//						|| e.keyCode==127 || e.keyCode==8){
//					e.doit = true;
//					if(OneChRadioButton.getSelection()){
//						BrowseButton_1.setEnabled(true);
//					}else if(TwoChRadioButton.getSelection()){
//						BrowseButton_2.setEnabled(true);
//						BrowseButton_1.setEnabled(true);
//					}					
//
//				}else{
//					e.doit = false;
//				}
//			}
//		});
//
//
//		NOBitTextbox.addKeyListener(new KeyListener() {
//
//			public void keyReleased(KeyEvent e) {
//				refreshFilepathTextbox();
//				
//				if(twoChannel){
//					if(!FilepathTextbox_1.getText().equalsIgnoreCase("") && !FilepathTextbox_2.getText().equalsIgnoreCase("") &&
//							!NOBitTextbox.getText().equalsIgnoreCase("")){
//						ExecuteButton.setEnabled(true);
//					}
//				}else{
//					if(!FilepathTextbox_1.getText().equalsIgnoreCase("") && !NOBitTextbox.getText().equalsIgnoreCase("")){
//						ExecuteButton.setEnabled(true);
//					}
//				}
//				if(NOBitTextbox.getText().equalsIgnoreCase("")){
//					BrowseButton_1.setEnabled(false);
//					BrowseButton_2.setEnabled(false);
//					ExecuteButton.setEnabled(false);
//				}
//			}
//
//			public void keyPressed(KeyEvent e) {
//			}
//
//			private void refreshFilepathTextbox() {
//				long BitsNumber = 0 ;
//				if(!NOBitTextbox.getText().equalsIgnoreCase("")){
//					BitsNumber = Long.parseLong(NOBitTextbox.getText());
//				}
//				if(!twoChannel){
//					if(null!=folderPath || !FilepathTextbox_1.getText().equalsIgnoreCase("")){
//						//Using Folder browser.
//						if(null!=folderPath){
//							if(folderPath.endsWith(":\\"))
//								FilepathTextbox_1.setText(folderPath +  moduleName  
//										+ "_" + BitsNumber + "-1.ini");
//							else
//								FilepathTextbox_1.setText(folderPath + "\\" + moduleName 
//										+ "_" + BitsNumber + "-1.ini");
//						}else if(!FilepathTextbox_1.getText().equalsIgnoreCase("")){
//							//Without using folder browser.
//							int _index = FilepathTextbox_1.getText().lastIndexOf("_");
//							String string = FilepathTextbox_1.getText().substring(0,_index + 1);
//							string += BitsNumber + "-1.ini";
//							FilepathTextbox_1.setText(string);						
//						}
//					}
//				}else if(twoChannel){
//
//					if(null!=folderPath || !FilepathTextbox_1.getText().equalsIgnoreCase("")
//							|| !FilepathTextbox_2.getText().equalsIgnoreCase("")){
//						//Using Folder browser.
//						if(null!=folderPath){
//							if(folderPath.endsWith(":\\")){
//								FilepathTextbox_1.setText(folderPath +  moduleName  
//										+ "_" + BitsNumber + "-1.ini");
//								FilepathTextbox_2.setText(folderPath +  moduleName  
//										+ "_" + BitsNumber + "-2.ini");
//							}
//							else{
//								FilepathTextbox_1.setText(folderPath + "\\" + moduleName 
//										+ "_" + BitsNumber + "-1.ini");
//								FilepathTextbox_2.setText(folderPath + "\\" + moduleName 
//										+ "_" + BitsNumber + "-2.ini");
//							}
//						}else{
//							if(!FilepathTextbox_1.getText().equalsIgnoreCase("")){
//								//Without using folder browser.
//								int _index = FilepathTextbox_1.getText().lastIndexOf("_");
//								String string = FilepathTextbox_1.getText().substring(0,_index + 1);
//								string += BitsNumber + "-1.ini";
//								FilepathTextbox_1.setText(string);						
//							}
//							if(!FilepathTextbox_2.getText().equalsIgnoreCase("")){
//								//Without using folder browser.
//								int _index = FilepathTextbox_2.getText().lastIndexOf("_");
//								String string = FilepathTextbox_2.getText().substring(0,_index + 1);
//								string += BitsNumber + "-2.ini";
//								FilepathTextbox_2.setText(string);
//							}
//						}
//					}
//				}
//
//			}
//		});
//
//
//
//
//		shell.setLayout(gridLayout);
//		new Label(shell, SWT.NONE);
//
//		//Design the Group
//		createGroupComponent(moduleName);
//
//		new Label(shell, SWT.NONE);
//		CancelButton = new Button(shell, SWT.NONE);
//		CancelButton.setText("Cancel");
//		CancelButton.setLayoutData(gridData4);
//		
//		CancelButton.addMouseListener(new MouseListener() {
//			
//			public void mouseUp(MouseEvent e) {
//				shell.close();
//			}
//			
//			public void mouseDown(MouseEvent e) {
//			}
//			
//			public void mouseDoubleClick(MouseEvent e) {
//			}
//		});
//
//		ExecuteButton = new Button(shell, SWT.NONE);
//		ExecuteButton.setText("Execute");
//		ExecuteButton.setEnabled(false);
//		
//		ExecuteButton.addMouseListener(new MouseListener() {
//			
//			public void mouseUp(MouseEvent e) {
//				shell.close();
//			}
//			
//			public void mouseDown(MouseEvent e) {
//				//TODO
//			}
//			
//			public void mouseDoubleClick(MouseEvent e) {
//			}
//		});
//		
//		
//		/*try {
//			NOBitTextbox.removeVerifyListener(listener);
//			Object obj = new TestVector().setModuleTestVectorSetting(moduleName, null);
//			NOBitTextbox.addVerifyListener(listener);
//			if(obj != null){
//				//Settings saved already
//				ExecuteButton.setEnabled(true);
//			}
//		} catch (Exception e1) {
//		}*/
//
//		shell.layout(true);
//	}
//
//
//	/**
//	 * Design the Group Shell
//	 * @param moduleName
//	 */
//	private void createGroupComponent(final String moduleName) {
//
//		GridData gridData3 = new GridData();
//		gridData3.horizontalAlignment = GridData.FILL;
//		gridData3.verticalAlignment = GridData.CENTER;
//		GridData gridData2 = new GridData();
//		gridData2.horizontalIndent = 0;
//		gridData2.verticalAlignment = GridData.CENTER;
//		gridData2.grabExcessHorizontalSpace = true;
//		gridData2.horizontalAlignment = GridData.FILL;
//		GridData gridData1 = new GridData();
//		gridData1.horizontalSpan = 3;
//		gridData1.verticalAlignment = GridData.CENTER;
//		gridData1.grabExcessHorizontalSpace = true;
//		gridData1.grabExcessVerticalSpace = false;
//		gridData1.horizontalAlignment = GridData.FILL;
//		GridLayout gridLayout1 = new GridLayout();
//		gridLayout1.numColumns = 4;
//		gridLayout1.horizontalSpacing = 5;
//
//		group = new Group(shell, SWT.NONE);
//		group.setLayout(gridLayout1);
//		group.setLayoutData(gridData1);
//		
//		OneChRadioButton = new Button(group, SWT.RADIO);
//		OneChRadioButton.setText("One Channel");
//
//		OneChRadioButton.addMouseListener(new MouseListener() {
//
//			public void mouseUp(MouseEvent e) {
//				FilepathLabel_2.setEnabled(false);
//				FilepathTextbox_2.setEnabled(false);
//				FilepathTextbox_2.setBackground(new Color(shell.getDisplay(),235, 235, 235));
//				BrowseButton_2.setEnabled(false);
//			}
//
//			public void mouseDown(MouseEvent e) {
//				twoChannel = false;
//				if(!NOBitTextbox.getText().equalsIgnoreCase("") && !FilepathTextbox_1.getText().equalsIgnoreCase("")){
//					ExecuteButton.setEnabled(true);
//				}
//				if(NOBitTextbox.getText().equalsIgnoreCase("") || FilepathTextbox_1.getText().equalsIgnoreCase("")){
//					ExecuteButton.setEnabled(false);
//				}
//			}
//
//			public void mouseDoubleClick(MouseEvent e) {
//			}
//		});
//
//		new Label(group, SWT.NONE);
//		
//		TwoChRadioButton = new Button(group, SWT.RADIO);
//		TwoChRadioButton.setText("Two Channels");
//		TwoChRadioButton.setSelection(true);
//
//		TwoChRadioButton.addMouseListener(new MouseListener() {
//
//			public void mouseUp(MouseEvent e) {
//				if(!NOBitTextbox.getText().equalsIgnoreCase("")){
//					BrowseButton_2.setEnabled(true);
//				}	
//				FilepathLabel_2.setEnabled(true);
//				FilepathTextbox_2.setEnabled(true);
//				FilepathTextbox_2.setBackground(new Color(shell.getDisplay(),255, 255, 230));
//			}
//
//			public void mouseDown(MouseEvent e) {
//				twoChannel = true;
//				if(!NOBitTextbox.getText().equalsIgnoreCase("") && !FilepathTextbox_1.getText().equalsIgnoreCase("")
//						&& !FilepathTextbox_2.getText().equalsIgnoreCase("")){
//					ExecuteButton.setEnabled(true);
//				}
//				if(FilepathTextbox_2.getText().equalsIgnoreCase("") || FilepathTextbox_1.getText().equalsIgnoreCase("")
//						|| NOBitTextbox.getText().equalsIgnoreCase("")){
//					ExecuteButton.setEnabled(false);
//				}
//			}
//
//			public void mouseDoubleClick(MouseEvent e) {
//			}
//		});
//		
//		new Label(group, SWT.NONE);
//		new Label(group, SWT.NONE);
//		new Label(group, SWT.NONE);
//		new Label(group, SWT.NONE);
//		new Label(group, SWT.NONE);
//		FilepathLabel_1 = new Label(group, SWT.NONE);
//		FilepathLabel_1.setText("Generated Input_1 Filepath");
//		new Label(group, SWT.NONE);
//		FilepathTextbox_1 = new Text(group, SWT.BORDER);
//		FilepathTextbox_1.setLayoutData(gridData2);
//		FilepathTextbox_1.setEditable(false);
//		FilepathTextbox_1.setBackground(new Color(shell.getDisplay(),255, 255, 230));
//
//		BrowseButton_1 = new Button(group, SWT.NONE);
//		BrowseButton_1.setText("Browse...");
//		BrowseButton_1.setEnabled(false);
//
//		BrowseButton_1.addMouseListener(new MouseListener() {
//
//			private String folderPath_1;
//			public void mouseUp(MouseEvent e) {
//				String output = new String();
//				long BitsNumber = 0 ;
//				if(!NOBitTextbox.getText().equals("")){
//					BitsNumber = Long.parseLong(NOBitTextbox.getText());
//				}
//				if(!FilepathTextbox_1.equals("")){	
//					output = FilepathTextbox_1.getText();
//				}
//				DirectoryDialog dialog = new DirectoryDialog(new Shell());				
//				folderPath_1 = dialog.open();
//				if(null!=folderPath_1){
//					if(folderPath_1.endsWith(":\\"))
//						FilepathTextbox_1.setText(folderPath_1 +  moduleName  
//								+ "_" + BitsNumber + "-1.ini");
//					else
//						FilepathTextbox_1.setText(folderPath_1 + "\\" + moduleName 
//								+ "_" + BitsNumber + "-1.ini");
//					if(OneChRadioButton.getSelection()){
//						ExecuteButton.setEnabled(true);
//					}else if(TwoChRadioButton.getSelection() && !FilepathTextbox_2.getText().equalsIgnoreCase("")){
//						ExecuteButton.setEnabled(true);
//					}else if(TwoChRadioButton.getSelection() && FilepathTextbox_2.getText().equalsIgnoreCase("")){
//						ExecuteButton.setEnabled(false);
//					}
//				}else{
//					FilepathTextbox_1.setText(output);
//				}					
//			}
//			public void mouseDown(MouseEvent e) {
//			}
//			public void mouseDoubleClick(MouseEvent e) {
//			}
//		});
//
//
//		FilepathLabel_2 = new Label(group, SWT.NONE);
//		FilepathLabel_2.setText("Generated Input_2 Filepath");
//		new Label(group, SWT.NONE);
//		FilepathTextbox_2 = new Text(group, SWT.BORDER);
//		FilepathTextbox_2.setEditable(false);
//		FilepathTextbox_2.setBackground(new Color(shell.getDisplay(),255, 255, 230));
//		FilepathTextbox_2.setLayoutData(gridData3);
//		BrowseButton_2 = new Button(group, SWT.NONE);
//		BrowseButton_2.setText("Browse...");
//		BrowseButton_2.setEnabled(false);
//		
//		BrowseButton_2.addMouseListener(new MouseListener() {
//
//			private String folderPath_1;
//			public void mouseUp(MouseEvent e) {
//				String output = new String();
//				long BitsNumber = 0 ;
//				if(!NOBitTextbox.getText().equals("")){
//					BitsNumber = Long.parseLong(NOBitTextbox.getText());
//				}
//				if(!FilepathTextbox_2.equals("")){	
//					output = FilepathTextbox_2.getText();
//				}
//				DirectoryDialog dialog = new DirectoryDialog(new Shell());				
//				folderPath_1 = dialog.open();
//				if(null!=folderPath_1){
//					if(folderPath_1.endsWith(":\\"))
//						FilepathTextbox_2.setText(folderPath_1 +  moduleName  
//								+ "_" + BitsNumber + "-2.ini");
//					else
//						FilepathTextbox_2.setText(folderPath_1 + "\\" + moduleName 
//								+ "_" + BitsNumber + "-2.ini");
//					if(OneChRadioButton.getSelection()){
//						ExecuteButton.setEnabled(true);
//					}else if(TwoChRadioButton.getSelection() && !FilepathTextbox_2.getText().equalsIgnoreCase("")){
//						ExecuteButton.setEnabled(true);
//					}else if(TwoChRadioButton.getSelection() && FilepathTextbox_2.getText().equalsIgnoreCase("")){
//						ExecuteButton.setEnabled(false);
//					}
//				}else{
//					FilepathTextbox_2.setText(output);
//				}					
//			}
//			public void mouseDown(MouseEvent e) {
//			}
//			public void mouseDoubleClick(MouseEvent e) {
//			}
//		});
//	}
//}
