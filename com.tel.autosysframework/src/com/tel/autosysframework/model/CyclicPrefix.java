package com.tel.autosysframework.model;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Image;


public class CyclicPrefix extends AutoModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Image LM_ICON = createImage(CyclicPrefix.class, "icons/cp.JPG");  //$NON-NLS-1$
	public static String TERMINAL_IP = "I/P";  //$NON-NLS-1$

	public CyclicPrefix() {
		setName("Cyclic Prefix");
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
		return "CyclicPrefix" /*+ " #" */+ getID(); //$NON-NLS-1$
	}
	
	

}
