package com.tel.autosysframework.model;


import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Image;



public class MonitorArea extends AutoModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Image LM_ICON = createImage(MonitorArea.class, "icons/monitor.jpg");  //$NON-NLS-1$
	public static String TERMINAL_IP = "I/P";  //$NON-NLS-1$

	public MonitorArea() {
		setName("MonitorArea");
		setSize(new Dimension(90,30));
	}
	public Image getIconImage() {
		// TODO Auto-generated method stub
		return LM_ICON;
	}
	public boolean getResult() {
		return getInput(TERMINAL_IP);	
	}
	public String toString(){
		return "MonitorArea" /*+ " #" */+ getID(); //$NON-NLS-1$
	}
	
	

}
