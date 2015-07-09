package com.odcgroup.workbench.generation.ui.internal.command;

import java.net.URI;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

import com.odcgroup.workbench.generation.ui.action.CodeGenerationAction;

/**
 * Parameterized command handler that trigger the generate code action 
 * @author yan
 */
public class GenerateCodeHandler extends AbstractHandler {

	/**
	 * Trigger the generate code action
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String uri = event.getParameter("locationURI");

		// Call the code generation action
		IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(URI.create(uri));
		IStructuredSelection selection = new StructuredSelection(files);
		new CodeGenerationAction().run(selection);
		return null;
	}
}
