package com.tel.autosysframework.model;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Image;


public class Precoding extends AutoModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Image LM_ICON = createImage(Precoding.class, "icons/pre_coding.JPG");  //$NON-NLS-1$
	public static String TERMINAL_IP = "I/P";  //$NON-NLS-1$

	public Precoding() {
		setName("Precoding");
		setSize(new Dimension(80,30));
	}
	public Image getIconImage() {
		// TODO Auto-generated method stub
		return LM_ICON;
	}
	public boolean getResult() {
		return getInput(TERMINAL_IP);	
	}
	public String toString(){
		return "Precoding" /*+ " #" */+ getID(); //$NON-NLS-1$
	}
	
	

}
