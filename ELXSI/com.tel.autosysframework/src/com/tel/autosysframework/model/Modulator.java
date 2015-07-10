package com.tel.autosysframework.model;


import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Image;



public class Modulator extends AutoModel {
	private static Image MOD_ICON = createImage(Modulator.class, "icons/lte_modulator.JPG");  //$NON-NLS-1$
	static final long serialVersionUID = 1;
	public static String TERMINAL_IP = "I/P";  //$NON-NLS-1$
	
	public Modulator() {
		setName("Modulator");
		setSize(new Dimension(100,40)); 
	}
	
	public Image getIconImage() {
		return MOD_ICON;
	}
	public boolean getResult() {
		return getInput(TERMINAL_IP);	
	}	

	public String toString(){
		return "Modulator" + getID(); //$NON-NLS-1$
	}
	
	
}
