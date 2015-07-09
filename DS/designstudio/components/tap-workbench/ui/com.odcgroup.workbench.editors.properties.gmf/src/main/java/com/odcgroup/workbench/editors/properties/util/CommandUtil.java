package com.odcgroup.workbench.editors.properties.util;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.clipboard.core.ClipboardUtil;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import com.odcgroup.workbench.core.OfsCore;

public class CommandUtil {
	
	public static final int CANCELLED = 0x01;

	/**
	 * @param domain
	 * @param owner
	 * @param feature
	 * @param value
	 * @return
	 */
	public static ICommand create(TransactionalEditingDomain domain,
			final EObject owner, final EStructuralFeature feature,
			final Object value) {
		ICommand command = createCommandInternal(domain, getCommandName(owner, feature), owner, 
				new Runnable() {
					public void run() {
						owner.eSet(feature, value);						
					}
			}			
		);
		return command;
	}
	
	public static ICommand createElementsFromStringCommand(TransactionalEditingDomain domain,
			final EObject owner, final EStructuralFeature feature,
			final String value) {
		ICommand command = createCommandInternal(domain, getCommandName(owner, feature), owner, 
				new Runnable() {
					public void run() {
						Collection pasteElementsFromString = ClipboardUtil.pasteElementsFromString(value, owner, null, new NullProgressMonitor());
						//Fix for DS-5704:Copy/paste of pageflow action is broken
						Object eGet = owner.eGet(feature);
						((EList)eGet).addAll(pasteElementsFromString);
						
					}
			}			
		);
		return command;
	}
	
	public static boolean isStatusCancelled(IStatus status) {
		if (status.getCode() == CANCELLED) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param owner
	 * @param domain
	 */
	public static boolean makeReadOnlyWriteable(EObject owner, TransactionalEditingDomain domain) {
		IFile file = WorkspaceSynchronizer.getFile(owner.eResource());
		if (file == null) {
			return false;
		}
		if (file.isReadOnly()) {
			return executeEmptyCommand(domain, owner);
		}
		return true;
	}

	/**
	 * @param domain
	 * @param owner
	 */
	private static boolean executeEmptyCommand(TransactionalEditingDomain domain,
			final EObject owner) {		
		ICommand command = createCommandInternal(domain,"MakeFileWriteable", owner, 
				new Runnable(){
					public void run() {
						// nothing
					}
				}
		);
		IStatus status = executeCommand(command);
		if (status.getCode() == CANCELLED) {
			return false;
		}
		return true;
	}

	/**
	 * @param domain
	 * @param name
	 * @param eObject
	 * @param runnable
	 * @return
	 */
	public static ICommand createCommandInternal(
			TransactionalEditingDomain domain, String name, EObject eObject,
			final Runnable runnable) {

		ICommand command = new AbstractTransactionalCommand(domain, name,
				Collections.singletonList(WorkspaceSynchronizer.getFile(eObject
						.eResource()))) {
			protected CommandResult doExecuteWithResult(
					IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {

				runnable.run();

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}

	/**
	 * @param domain
	 * @param owner
	 * @param feature
	 * @param value
	 */
	public static void createEReference(TransactionalEditingDomain domain,
			final EObject owner, final EStructuralFeature feature,
			final Object value) {
		ICommand command = create(domain, owner, feature, value);
		executeCommand(command);

	}

	/**
	 * @param command
	 */
	public static IStatus executeCommand(ICommand command) {
		IStatus status = null;
		IOperationHistory history = OperationHistoryFactory.getOperationHistory();
		try {
			status = history.execute(command, new NullProgressMonitor(), null);
		} catch (ExecutionException e) {
			OfsCore.getDefault().logError(e.getLocalizedMessage(), e);
		}
		return status;
	}

	/**
	 * @param domain
	 * @param name
	 * @param eObject
	 * @param runnable
	 * @return
	 */
	public static IStatus execute(TransactionalEditingDomain domain, String name,
			EObject eObject, final Runnable runnable) {
		ICommand command = createCommandInternal(domain, name, eObject, runnable);
		return executeCommand(command);
	}

	/**
	 * @param owner
	 * @param feature
	 * @return
	 */
	private static String getCommandName(EObject owner,
			EStructuralFeature feature) {
		StringBuffer sb = new StringBuffer();
		if (owner != null) {
			sb.append(owner.eClass().getName() + "_");
		}
		sb.append(feature.getName() + "_Change");
		return sb.toString();
	}

}
