package com.tel.autosysframework.model;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Image;


public class OutputPort extends AutoModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Image OUT_ICON = createImage(OutputPort.class, "icons/outputport.gif");  //$NON-NLS-1$
	public static String TERMINAL_IP = "I/P";  //$NON-NLS-1$

	public OutputPort() {
		setName("OutputPort");
		setSize(new Dimension(35,20)); 
	}
	public boolean getResult() {
		// TODO Auto-generated method stub
		return getInput(TERMINAL_IP);
	}

	public Image getIconImage() {
		// TODO Auto-generated method stub
		return OUT_ICON;
	}
	
	
	
	public String toString(){
		return "OutputPort" + getID(); //$NON-NLS-1$
	}
	
	
	

}
