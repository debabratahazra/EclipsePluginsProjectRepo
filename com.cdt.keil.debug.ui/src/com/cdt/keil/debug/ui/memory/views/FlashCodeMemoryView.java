package com.cdt.keil.debug.ui.memory.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;
import com.cdt.keil.debug.ui.simulator.sdk.SimulatorSDK;

public class FlashCodeMemoryView extends ViewPart {
	
	
	public static Table table=null;	
	private TableColumn address;
	private TableColumn[] Data;
	private TableColumn DataAll;
	private Label Address;
	private Text textbox;
	private Button buttonPrev;
	private Button buttonRefresh;
	private Button buttonClose;	
	private Button buttonNext;	
	public static TableItem[] TableItemValue;
	
	public static final int ROW=4096;

	public FlashCodeMemoryView() {	
	}

	@Override
	public void createPartControl(Composite parent) {
		
		GridData gridData11 = new GridData();
		gridData11.horizontalAlignment = GridData.FILL;
		gridData11.grabExcessHorizontalSpace = false;
		gridData11.horizontalSpan = 3;
		gridData11.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.verticalAlignment = GridData.CENTER;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalIndent = 0;
		gridData.heightHint = -1;
		gridData.widthHint = -1;
		gridData.horizontalSpan = 9;
		gridData.verticalAlignment = GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 9;
		gridLayout.verticalSpacing = 3;
		gridLayout.marginWidth = 3;
		gridLayout.marginHeight = 3;
		gridLayout.horizontalSpacing = 18;
		Address = new Label(parent, SWT.NONE);
		Address.setText("Address");
		textbox = new Text(parent, SWT.BORDER);		
		textbox.setText("0x0000");
		textbox.setToolTipText("Set the Address value in HEX");
		textbox.setLayoutData(gridData11);
		textbox.addMouseListener(new org.eclipse.swt.events.MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				textbox.clearSelection();
				textbox.forceFocus();
				textbox.setSelection(2, textbox.getText().length());			
			}
			@Override
			public void mouseDown(MouseEvent e) {				
			}
			@Override
			public void mouseUp(MouseEvent e) {				
			}			
		});
		
		textbox.addKeyListener(new org.eclipse.swt.events.KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(textbox.getText().length() == 6 && e.character==SWT.CR){					
					table.deselectAll();				
					table.forceFocus();
					table.setSelection(findRow(textbox.getText().substring(2, 6)));
				}
			}
			private int findRow(String string) {				
				return ((Integer.valueOf(string, 16))/16);				
			}			
		});
		
		textbox.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {				
				if(textbox.getText().length() > 6 )
					textbox.setText("0x" + textbox.getText().substring((textbox.getText().indexOf("x") + 1), 
							(textbox.getText().indexOf("x") + 5)));				
			}
		});
		
		buttonPrev = new Button(parent, SWT.NONE);
		buttonPrev.setText(" << ");
		buttonPrev.setToolTipText("Go Backward 0x100 Location");
		buttonPrev.addMouseListener(new org.eclipse.swt.events.MouseListener() {
			public void mouseUp(org.eclipse.swt.events.MouseEvent e) {
				int Value= (Integer.valueOf(textbox.getText().substring(2, 
						textbox.getText().length()), 16)) - 256;				
				if(Value<=0) textbox.setText("0x0000");
				else{		
					String str=new String();
		 			if(Value>=1 && Value<16) str="0x000";
		 			else if(Value>=16 && Value<256) str="0x00";
		 			else if(Value>=256 && Value<4096) str="0x0";
		 			else str="0x";
		 			str+=Integer.toHexString(Value).toUpperCase();
		 			textbox.setText(str);
				}
				
				if(textbox.getText().length() == 6){					
					table.deselectAll();				
					table.forceFocus();
					table.setSelection(findRow(textbox.getText().substring(2, 6)));
				}								
			}
			private int findRow(String string) {				
				return ((Integer.valueOf(string, 16))/16);				
			}
			public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
			}
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
			}
		});
		
		
		buttonNext = new Button(parent, SWT.NONE);
		buttonNext.setText(" >> ");
		buttonNext.setToolTipText("Go Forward 0x100 Location");
		buttonNext.addMouseListener(new org.eclipse.swt.events.MouseListener() {
			public void mouseUp(org.eclipse.swt.events.MouseEvent e) {
				int Value= (Integer.valueOf(textbox.getText().substring(2, 
						textbox.getText().length()), 16)) + 256;				
				if(Value>65535) textbox.setText("0xFF" + textbox.getText().substring(4,
						textbox.getText().length()));
				else{		
					String str=new String();
		 			if(Value>=1 && Value<16) str="0x000";
		 			else if(Value>=16 && Value<256) str="0x00";
		 			else if(Value>=256 && Value<4096) str="0x0";
		 			else str="0x";
		 			str+=Integer.toHexString(Value).toUpperCase();
		 			textbox.setText(str);
				}
				
				if(textbox.getText().length() == 6){					
					table.deselectAll();				
					table.forceFocus();
					table.setSelection(findRow(textbox.getText().substring(2, 6)));
				}								
			}
			private int findRow(String string) {				
				return ((Integer.valueOf(string, 16))/16);				
			}
			public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
			}
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
			}
		});		
		
		buttonRefresh = new Button(parent, SWT.NONE);
		buttonRefresh.setText("Refresh");
		buttonRefresh.setToolTipText("Refresh View from Selected Address");
		buttonRefresh.addMouseListener(new org.eclipse.swt.events.MouseListener() {
			private int[] Packet_ACK;
			SimulatorSDK SDK=new SimulatorSDK();
			public void mouseUp(org.eclipse.swt.events.MouseEvent e) {				
				//Update Flash Code Memory view in Simulator SDK.						
				short [] addressValue = getAddress(textbox.getText());
				if (addressValue==null) return;								
					Packet_ACK=IdebugInterface.readFlashCodeMemory(addressValue);					
					Packet_ACK[1]++;
					SDK.DataTransfer(Packet_ACK);									
					//table.redraw();								
			}
			
			private short[] getAddress(String text) {
				if(textbox.getText().length()==6){
					short[] packet=new short[3];
					int pack=Integer.valueOf(textbox.getText().substring(2, 4),16);
					packet[0]=(short)pack;
					pack=Integer.valueOf(textbox.getText().substring(4, textbox.
							getText().length()),16);
					packet[1]=(short)pack;
					int val=Integer.valueOf(textbox.getText().substring(2,textbox.
							getText().length()) ,16);
					if(val > 65407){
						packet[2]= (short)(0xFFFF - val + 1);
					}
					else
						packet[2]=0x80;			
					return packet;
				}
				return null;
			}
			
			public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
			}
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				
			}
		});		
				
		buttonClose = new Button(parent, SWT.NONE);
		buttonClose.setText("Close");
		buttonClose.setToolTipText("Close Flash Code Memory View");
		buttonClose.addMouseListener(new org.eclipse.swt.events.MouseListener() {
			public void mouseUp(org.eclipse.swt.events.MouseEvent e) {				
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
				hideView(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
				findView("com.cdt.keil.debug.ui.FlashCodeMemoryView"));
			}
			public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
			}
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
			}
		});
		
 		table = new Table(parent, SWT.VIRTUAL | SWT.NONE);
 		table.setHeaderVisible(true);
 		table.setLayoutData(gridData);
 		table.setLinesVisible(true);
 		table.setRedraw(true);
 		
 		
 		//Create first table column [Address]
 		address = new TableColumn(table, SWT.NONE);
 		address.setWidth(55);
 		address.setAlignment(SWT.CENTER);
 		address.setText("Address");
 		
 		//Create 16 Table Column [Data Bit]
 		Data=new TableColumn[16];  		
 		for(int i=0;i<16;i++){
 			Data[i] = new TableColumn(table, SWT.NONE);
 	 		Data[i].setWidth(30);
 	 		Data[i].setAlignment(SWT.CENTER);
 	 		Data[i].setText("[" + Integer.toHexString(i).toUpperCase() + "]"); 			
 		}		
 		
 		//Create last table column [Data]
 		DataAll = new TableColumn(table, SWT.NONE);
 		DataAll.setWidth(130);
 		DataAll.setAlignment(SWT.CENTER);
 		DataAll.setText("Data");
 		
 		/*Create first column value i.e. Address of the Memory location in this table 
 		 * and also put default value [00] in memory location.
 		 */
 		TableItemValue=new TableItem[ROW];
 		table.setItemCount(ROW);
		table.addListener(SWT.SetData, new Listener(){
			private int[] Packet_ACK;
			SimulatorSDK SDK=new SimulatorSDK();
			@Override
			public void handleEvent(Event event) {
				table.redraw();
				TableItem item=(TableItem)event.item;
				int index=event.index;			
				String str=new String();
				TableItemValue[index]=item;									 
	 			if(index==0) str="0x000";
	 			else if(index>=1 && index<16) str="0x00";
	 			else if(index>=16 && index<256) str="0x0";
	 			if(index>=256) str="0x";
	 			TableItemValue[index].setText(0, str + 
	 					Integer.toHexString(index*16).toUpperCase());
	 			table.redraw();
	 			short [] addressValue = stringToShort(str + 
	 					Integer.toHexString(index*16).toUpperCase());					
				Packet_ACK=IdebugInterface.readFlashCodeMemory(addressValue);					
				Packet_ACK[1]++;
				SDK.DataTransfer(Packet_ACK);					
				addressValue[1]+= 0x10;
				if(addressValue[1]%256==0){
					addressValue[0]+=0x01;
				} 			
				table.redraw();
			}
			private short[] stringToShort(String str) {
				short[] value=new short[3];
				value[0]=(short)Integer.parseInt(str.substring(2, 4), 16);
				value[1]=(short)Integer.parseInt(str.substring(4, str.length()), 16);
				value[2]=0x10;
				return value;
			}			
		});
		
		table.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem[] item=table.getSelection();				
				textbox.setText(item[0].toString());
			}							
		});
 		
 		parent.setLayout(gridLayout);

	}

	@Override
	public void setFocus() {}

}
