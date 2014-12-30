package com.tel.autosysframework.model;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Image;


public class InputPort  extends AutoModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Image IP_ICON = createImage(InputPort.class, "icons/inputport.gif");  //$NON-NLS-1$

	public InputPort() {
		setName("InputPort");
		setSize(new Dimension(35,20)); 
	}
	public Image getIconImage() {
		// TODO Auto-generated method stub
		return IP_ICON;
	}
	public String toString(){
		return "InputPort" + getID(); //$NON-NLS-1$
	}
	
	
	
	
	public boolean getResult() {
		return false;
	}
}
