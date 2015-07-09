package com.odcgroup.workbench.generation.ui.internal.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.Parameterization;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.CommandException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Handler of the command that does save and generate code
 * @author yan
 * @author amc
 * @author kkr
 */
public class SaveAndGenerateHandler extends AbstractHandler {

	/**
	 * Execute the page designer save and generate code
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = getEditor(event);
		saveEditor(editor);
		IFile file = getFile(editor);
		generateCode(editor, file);
		return null;
	}
	
	private IFile getFile(IEditorPart editor) {
		IEditorInput input = editor.getEditorInput();
		IFile file = null;
		if (input instanceof FileEditorInput) {
			FileEditorInput fileEditor = (FileEditorInput)input;
			file = fileEditor.getFile();
		}
		return file;
	}
	
	private IEditorPart getEditor(ExecutionEvent event) {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		IEditorPart editor = page.getActiveEditor();
		return editor;
	}

	private void saveEditor(final IEditorPart editor) {
		if (editor.isDirty()) {
			editor.doSave(new NullProgressMonitor());
		}
	}

	private void generateCode(IEditorPart editor, IFile file) throws ExecutionException {			
		IHandlerService handlerService = (IHandlerService) editor.getSite().getService(
				IHandlerService.class);
		ICommandService commandService = (ICommandService) editor.getSite().getService(
				ICommandService.class);
		try {
			Command command = commandService.getCommand("com.odcgroup.workbench.generation.ui.GenerateCodeHandler");
			Parameterization parameter = new Parameterization(command.getParameter("locationURI"), file.getLocationURI().toString());
	 		ParameterizedCommand parmCommand = new ParameterizedCommand(
	 				command, new Parameterization[] { parameter });
	 		handlerService.executeCommand(parmCommand, null);
		} catch (CommandException e) {
			throw new ExecutionException("Unable to invoke the code generation command", e);
		}
	}

}
