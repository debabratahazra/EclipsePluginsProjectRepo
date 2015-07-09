package com.odcgroup.page.ui.edit;

import java.util.List;

import org.eclipse.gef.KeyHandler;
import org.eclipse.jface.action.IAction;

/**
 * @author Alain Tripod
 * @author Gary Hayes
 */
class KeyStrokeRemover extends KeyStrokeOperation {
	
	/**
	 * @param keyHandler
	 */
	public KeyStrokeRemover(KeyHandler keyHandler) {
		super(keyHandler);
	}

	/**
	 * Adds the KeyStroke to the actions.
	 * 
	 * @param actions The actions to add the KeyStrokes to
	 */
	public void remove(List<IAction> actions) {
		for (IAction action : actions) {
			getKeyHandler().remove(getKeyPressed(action));
		}
	}
}
