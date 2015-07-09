package com.odcgroup.page.ui.command;

import org.eclipse.gef.commands.Command;

import com.odcgroup.page.ui.util.EclipseUtils;

/**
 * This is the base class for all compound command of the page designer<p>
 * 
 * The command do nothing if the OFS model resource attached to
 * the current active editor is read only.
 * 
 * @author atr
 */
public abstract class BaseCommand extends Command {
	
	@Override
	public boolean canExecute() {
		boolean ok = true;
		if (checkModelState()) {
			ok = ! EclipseUtils.modelIsReadonly();
		}
		return ok;
	}
	
	/**
	 * @return true is the OFS model's state must be checked
	 * before executing the command.
	 */
	protected boolean checkModelState() {
		return true;
	}
	
}
