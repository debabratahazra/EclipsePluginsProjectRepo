package com.odcgroup.page.ui.edit;

import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.jface.action.IAction;

/**
 * @author Alain Tripod
 * @version 1.0
 */
abstract class KeyStrokeOperation {

	/** */
	private KeyHandler keyHandler = null;
	
	/**
	 * 
	 * @return KeyHandler
	 */
	protected final KeyHandler getKeyHandler()  {
		return keyHandler;
	}
	
	/**
	 * 
	 * @param action
	 * @return KeyStroke
	 */
	protected KeyStroke getKeyPressed(IAction action) {
		return KeyStroke.getPressed(action.getAccelerator(), 0);
	}
	
	/**
	 * 
	 * @param keyHandler
	 */
	public KeyStrokeOperation(KeyHandler keyHandler) {
		this.keyHandler = keyHandler;
	}

}
