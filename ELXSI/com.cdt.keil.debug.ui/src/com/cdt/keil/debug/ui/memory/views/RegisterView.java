package com.cdt.keil.debug.ui.memory.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.cdt.keil.debug.ui.console.ConsoleDisplayMgr;
import com.cdt.keil.debug.ui.internal.UpdatePC;
import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;
import com.cdt.keil.debug.ui.simulator.sdk.SimulatorSDK;

public class RegisterView extends ViewPart {	
	
	public static Table table ;
	private TableColumn address;
	private Button buttonRefresh;
	private Button buttonClose;
	public static TableItem[] regAddressValue;	
	static SimulatorSDK SDK=new SimulatorSDK();
	public static Display display ;
	public static Color yellow ;
	public static Color normal ;
	public static boolean boolValue=false;
	public static boolean spChanged = false;
	
	static final int ROW=17;

	public RegisterView() {	
		super();				
	}

	@Override
	public void createPartControl(Composite parent) {
		
		display=parent.getDisplay();
		yellow = display.getSystemColor(SWT.COLOR_YELLOW);
		normal = display.getSystemColor(SWT.COLOR_WHITE);
		
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
		gridData.horizontalSpan = 10;
		gridData.verticalAlignment = GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 10;
		gridLayout.verticalSpacing = 3;
		gridLayout.marginWidth = 3;
		gridLayout.marginHeight = 3;
		gridLayout.horizontalSpacing = 18;
				
		buttonRefresh = new Button(parent, SWT.NONE);
		buttonRefresh.setText("Refresh");
		buttonRefresh.setToolTipText("Refresh Register View");
		buttonRefresh.addMouseListener(new org.eclipse.swt.events.MouseListener() {
			public void mouseUp(org.eclipse.swt.events.MouseEvent e) {		
				ConsoleDisplayMgr.getDefault().println("REGISTERS REFRESHED.", 1);
				updateRegister(false, false);
				new UpdatePC();				
			}
			public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
			}
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
			}
		});
		
	
		
		buttonClose = new Button(parent, SWT.NONE);
		buttonClose.setText("Close");
		buttonClose.setToolTipText("Close Register View");
		buttonClose.addMouseListener(new org.eclipse.swt.events.MouseListener() {
			public void mouseUp(org.eclipse.swt.events.MouseEvent e) {				
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
				hideView(PlatformUI.getWorkbench().getActiveWorkbenchWindow().
				getActivePage().findView("com.cdt.keil.debug.ui.RegisterView"));
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
 		
 		address = new TableColumn(table, SWT.NONE);
 		address.setWidth(100);
 		address.setAlignment(SWT.CENTER);
 		address.setText("Register");
 		
 		TableColumn Data = new TableColumn(table, SWT.NONE);
 		Data.setWidth(100);
 		Data.setAlignment(SWT.CENTER);
 		Data.setText("Data");
 		
 		
 		//Create first column value i.e. Address of the Memory location & Default data value also.
 		regAddressValue =new TableItem[ROW]; 		
 		for(int i=0;i<ROW;i++)
 			regAddressValue[i]=new TableItem(table,SWT.NONE,i); 
 		
 		regAddressValue[0].setText(0, "PC");
 		regAddressValue[0].setText(1, "0x0000");
 		
 		regAddressValue[1].setText(0, "ACC");
 		regAddressValue[1].setText(1, "0x00");
 		
 		regAddressValue[2].setText(0, "REG B");
 		regAddressValue[2].setText(1, "0x00");
 		
 		regAddressValue[3].setText(0, "SP");
 		regAddressValue[3].setText(1, "0x00");
 		
 		regAddressValue[4].setText(0, "DPTR");
 		regAddressValue[4].setText(1, "0x0000");
 		
 		regAddressValue[5].setText(0, "PSW");
 		regAddressValue[5].setText(1, "0x00");
 		
 		regAddressValue[6].setText(0, "P0");
 		regAddressValue[6].setText(1, "0x00");
 		
 		regAddressValue[7].setText(0, "IE");
 		regAddressValue[7].setText(1, "0x00");
 		
 		regAddressValue[8].setText(0, "R0");
 		regAddressValue[8].setText(1, "0x00");
 		
 		regAddressValue[9].setText(0, "R1");
 		regAddressValue[9].setText(1, "0x00");
 		
 		regAddressValue[10].setText(0, "R2");
 		regAddressValue[10].setText(1, "0x00");
 		
 		regAddressValue[11].setText(0, "R3");
 		regAddressValue[11].setText(1, "0x00");
 		
 		regAddressValue[12].setText(0, "R4");
 		regAddressValue[12].setText(1, "0x00");
 		
 		regAddressValue[13].setText(0, "R5");
 		regAddressValue[13].setText(1, "0x00");
 		
 		regAddressValue[14].setText(0, "R6");
 		regAddressValue[14].setText(1, "0x00");
 		
 		regAddressValue[15].setText(0, "R7");
 		regAddressValue[15].setText(1, "0x00"); 		
 		
 		parent.setLayout(gridLayout);
 		
	}	
	
	public static void updateRegSP(){
		//Update Stack Pointer only.
		
		int[] Packet_ACK=IdebugInterface.
		readSpecialFunctionRegisters(new short[] {0x00, 0x80, 0x0A});  	//from 0x80 location. 
		
		//SP
		String str=getStringValue(Packet_ACK, 6);		
		if(!regAddressValue[3].getText(1).equalsIgnoreCase(str) ){			
			spChanged=true;
		}
		else{			
			spChanged=false;
		}
		regAddressValue[3].setText(1,str);
		table.redraw();
	}
	
	
	public static void updateRegister(boolean pcRefresh, boolean highlight){	
		
		boolValue=highlight;		
				
		int[] Packet_ACK=IdebugInterface.
				readSpecialFunctionRegisters(new short[] {0x00, 0x80, 0x55});  	//80 to D0
		updateRegValue(Packet_ACK, highlight);
		table.redraw();
		
		if(pcRefresh){
			Packet_ACK=IdebugInterface.readProgramCounter();		
			Packet_ACK[1]++;   //Update Program Counter value in Simulator SDK.				
			SDK.DataTransfer(Packet_ACK);
			table.redraw();		
		}			
		
		Packet_ACK=IdebugInterface.readAccumulator();		
		Packet_ACK[1]++;   //Update ACC & Reg. B value in Simulator SDK.			
		SDK.DataTransfer(Packet_ACK);				
		table.redraw();		
	}

	private static void updateRegValue(int[] packet_ACK, boolean highlight) {
		String str=new String("");
		
		//P0
		str=getStringValue(packet_ACK, 5);		
		if(!regAddressValue[6].getText(1).equalsIgnoreCase(str) && highlight)
			regAddressValue[6].setBackground(yellow);
		else
			regAddressValue[6].setBackground(normal);
		regAddressValue[6].setText(1,str);
		
		//SP
		str=getStringValue(packet_ACK, 6);		
		if(!regAddressValue[3].getText(1).equalsIgnoreCase(str) && highlight){
			regAddressValue[3].setBackground(yellow);
			spChanged=true;
		}
		else{
			regAddressValue[3].setBackground(normal);
			spChanged=false;
		}
		regAddressValue[3].setText(1,str);
		
		//DPTR
		str=getStringValue(packet_ACK, 7);			//DPL		
		String value = getStringValue(packet_ACK, 8)	//DPH 
						+ str.substring(2, str.length());		
		if(!regAddressValue[4].getText(1).equalsIgnoreCase(value) && highlight)
			regAddressValue[4].setBackground(yellow);
		else
			regAddressValue[4].setBackground(normal);
		regAddressValue[4].setText(1,value);		
		
		//IE
		str=getStringValue(packet_ACK, 45);		
		if(!regAddressValue[7].getText(1).equalsIgnoreCase(str) && highlight)
			regAddressValue[7].setBackground(yellow);
		else
			regAddressValue[7].setBackground(normal);
		regAddressValue[7].setText(1,str);		
		
		
		//PSW
		str=getStringValue(packet_ACK, 85);		
		if(!regAddressValue[5].getText(1).equalsIgnoreCase(str) && highlight)
			regAddressValue[5].setBackground(yellow);
		else
			regAddressValue[5].setBackground(normal);
		regAddressValue[5].setText(1,str);
		
		updateRxValue(str, highlight);
	}	

	private static void updateRxValue(String str, boolean highlight) {
		int regBank = 0x00;
		int psw = Integer.valueOf(str.substring(2, str.length()), 16);
		psw = psw >> 3;		psw = psw & 3;
		switch(psw){
			case 0:
				regBank=0x00;
				break;
			case 1:
				regBank=0x08;
				break;
			case 2:
				regBank=0x10;
				break;
			case 3:
				regBank=0x18;
				break;
			default:
				regBank=0x00;
					break;			
		}
		int[] value = IdebugInterface.readScratchpadRAM(new short[]{0x00, (short)regBank,0x08});
			
		//Update R0 Register.		
		str=getStringValue(value, 5);		
		if(!regAddressValue[8].getText(1).equalsIgnoreCase(str) && highlight)
			regAddressValue[8].setBackground(yellow);
		else
			regAddressValue[8].setBackground(normal);
		regAddressValue[8].setText(1,str);	
		
		//Update R1 Register.		
		str=getStringValue(value, 6);		
		if(!regAddressValue[9].getText(1).equalsIgnoreCase(str) && highlight)
			regAddressValue[9].setBackground(yellow);
		else
			regAddressValue[9].setBackground(normal);
		regAddressValue[9].setText(1,str);
		
		//Update R2 Register.		
		str=getStringValue(value, 7);		
		if(!regAddressValue[10].getText(1).equalsIgnoreCase(str) && highlight)
			regAddressValue[10].setBackground(yellow);
		else
			regAddressValue[10].setBackground(normal);
		regAddressValue[10].setText(1,str);
		
		//Update R3 Register.		
		str=getStringValue(value, 8);		
		if(!regAddressValue[11].getText(1).equalsIgnoreCase(str) && highlight)
			regAddressValue[11].setBackground(yellow);
		else
			regAddressValue[11].setBackground(normal);
		regAddressValue[11].setText(1,str);
		
		//Update R4 Register.		
		str=getStringValue(value, 9);		
		if(!regAddressValue[12].getText(1).equalsIgnoreCase(str) && highlight)
			regAddressValue[12].setBackground(yellow);
		else
			regAddressValue[12].setBackground(normal);
		regAddressValue[12].setText(1,str);
		
		//Update R5 Register.		
		str=getStringValue(value, 10);		
		if(!regAddressValue[13].getText(1).equalsIgnoreCase(str) && highlight)
			regAddressValue[13].setBackground(yellow);
		else
			regAddressValue[13].setBackground(normal);
		regAddressValue[13].setText(1,str);
		
		//Update R6 Register.		
		str=getStringValue(value, 11);		
		if(!regAddressValue[14].getText(1).equalsIgnoreCase(str) && highlight)
			regAddressValue[14].setBackground(yellow);
		else
			regAddressValue[14].setBackground(normal);
		regAddressValue[14].setText(1,str);
		
		//Update R7 Register.		
		str=getStringValue(value, 12);		
		if(!regAddressValue[15].getText(1).equalsIgnoreCase(str) && highlight)
			regAddressValue[15].setBackground(yellow);
		else
			regAddressValue[15].setBackground(normal);
		regAddressValue[15].setText(1,str);	
	}

	private static String getStringValue(int[] packet_ACK, int index) {
		String str=new String("");		
		if(packet_ACK[index]>=0 && packet_ACK[index]<=15)  		
			str="0x0" + Integer.toHexString(packet_ACK[index]).toUpperCase();
		else
			str="0x" + Integer.toHexString(packet_ACK[index]).toUpperCase();		
		return str;
	}
	
	@Override
	public void setFocus() {		
	}

}
