package com.tel.autosysframework.model;


import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Image;



public class VideoDisplay extends AutoModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Image LM_ICON = createImage(VideoDisplay.class, "icons/monitor.jpg");  //$NON-NLS-1$
	public static String TERMINAL_IP = "I/P";  //$NON-NLS-1$

	public VideoDisplay() {
		setName("VideoDisplay");
		setSize(new Dimension(98,30));
	}
	public Image getIconImage() {
		// TODO Auto-generated method stub
		return LM_ICON;
	}
	public boolean getResult() {
		return getInput(TERMINAL_IP);	
	}
	public String toString(){
		return "VideoDisplay" /*+ " #" */+ getID(); //$NON-NLS-1$
	}

}
