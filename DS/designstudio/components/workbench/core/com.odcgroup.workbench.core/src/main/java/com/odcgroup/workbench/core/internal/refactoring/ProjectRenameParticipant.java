package com.odcgroup.workbench.core.internal.refactoring;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;


/** This class hooks into the refactoring mechanism of Eclipse for renames. It is configured to be
 *  launched for all rename operations on projects that adapt to IOfsProject.
 * 
 * @author Kai Kreuzer
 *
 */
public class ProjectRenameParticipant extends RenameParticipant {

	public ProjectRenameParticipant() {
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		
		RefactoringStatus result = new RefactoringStatus();
		
		// According to OCS-23973, Odyssey projects must not contain spaces 
		String newName = getArguments().getNewName();
		if(newName.contains(" ")) {
			result.addFatalError("Design Studio project names must not contain spaces!");
		}
		return result;
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		return null;
	}

	@Override
	public String getName() {
		return "Renaming Design Studio Project";
	}

	@Override
	protected boolean initialize(Object element) {
		return true;
	}

}
