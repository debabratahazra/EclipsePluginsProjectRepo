package com.odcgroup.pageflow.editor.diagram.custom.commands;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

public class PropertyTransactionalCommand extends AbstractTransactionalCommand {
	
	private EObject propertyValue;

	/**
	 * @param editingDomain
	 * @param label
	 * @param affectedFiles
	 */
	public PropertyTransactionalCommand(TransactionalEditingDomain editingDomain, String label, List affectedFiles, EObject propertyValue) {
		super(editingDomain, label, affectedFiles);
		this.propertyValue = propertyValue;
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor,
			IAdaptable info) throws ExecutionException {
		return CommandResult.newOKCommandResult(getPropertyValue());
	}


	/**
	 * @return
	 */
	public EObject getPropertyValue() {
		return propertyValue;
	}

}
