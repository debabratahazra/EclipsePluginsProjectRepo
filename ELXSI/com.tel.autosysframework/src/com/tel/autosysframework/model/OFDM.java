package com.tel.autosysframework.model;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Image;


public class OFDM extends AutoModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Image LM_ICON = createImage(OFDM.class, "icons/ofdm.JPG");  //$NON-NLS-1$
	public static String TERMINAL_IP = "I/P";  //$NON-NLS-1$

	public OFDM() {
		setName("OFDM");
		setSize(new Dimension(70,30));
	}
	public Image getIconImage() {
		// TODO Auto-generated method stub
		return LM_ICON;
	}
	public boolean getResult() {
		return getInput(TERMINAL_IP);	
	}
	public String toString(){
		return "OFDM" /*+ " #" */+ getID(); //$NON-NLS-1$
	}
	
	

}
