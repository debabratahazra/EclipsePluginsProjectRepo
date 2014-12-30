package com.tel.autosysframework.model;


import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Image;



public class VideoAnalyser extends AutoModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Image LM_ICON = createImage(VideoAnalyser.class, "icons/monitor.jpg");  //$NON-NLS-1$
	public static String TERMINAL_IP = "I/P";  //$NON-NLS-1$

	public VideoAnalyser() {
		setName("VideoAnalyser");
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
		return "VideoAnalyser" /*+ " #" */+ getID(); //$NON-NLS-1$
	}

}
