package com.odcgroup.page.ui.command;

import org.eclipse.gef.commands.Command;

import com.odcgroup.page.log.Logger;

/**
 * Dummy Command which writes a message to the log when it is executed.
 * 
 * @author Gary Hayes
 */
public class LoggerCommand extends Command {
	
	/** The message. */
	private String message;
	
	/**
	 * Creates a new LoggerCommand.
	 * 
	 * @param message The message to add to the log
	 */
	public LoggerCommand(String message) {
		this.message = message;
	}

	/**
	 * Executes the command.
	 */
	public void execute() {
		Logger.info("Executing: " + message);
	}

	/**
	 * Undos the Command.
	 */
	public void undo() {
		Logger.info("Undoing: " + message);
	}
}
