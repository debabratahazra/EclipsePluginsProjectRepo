package com.odcgroup.process.translation.command;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.Parameterization;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;

import com.odcgroup.process.diagram.part.ProcessDiagramEditor;
import com.odcgroup.process.diagram.part.ProcessDiagramEditorPlugin;
import com.odcgroup.translation.ui.command.TranslationRemoveCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * @author atr
 */
public class ProcessTranslationRemoveCommand extends TranslationRemoveCommand {

	private static String REMOVE_COMMAND_ID = "com.odcgroup.process.command.translation.remove";

	protected String getCommandId() {
		return ProcessTranslationRemoveCommand.REMOVE_COMMAND_ID;
	}

	@Override
	protected void initializeTexts() {
		setStandardName("Remove Translation");
		setStandardToolTip("Remove Translation");
	}

	@Override
	public void execute(String newText) throws CoreException {
		
		IEditorPart editorPart = getActiveEditorPart();
		if (editorPart != null) {

			// Extract useful parameters needed for the command
			IWorkbenchPartSite site = editorPart.getSite();

			// Invoke the command
			IHandlerService handlerService = (IHandlerService) site.getService(IHandlerService.class);
			ICommandService commandService = (ICommandService) site.getService(ICommandService.class);

			TransactionalEditingDomain editingDomain = ((ProcessDiagramEditor)editorPart).getEditingDomain();
			CommandStack commandStack = editingDomain.getCommandStack();
			
			try {

				// Get the command and assign values to parameters
				Command command = commandService.getCommand(getCommandId());
				ParameterizedCommand parmCommand = new ParameterizedCommand(command, new Parameterization[] {});

				// Prepare the evaluation context
				IEvaluationContext ctx = handlerService.getCurrentState();
				ctx.addVariable("model", getModel());
				ctx.addVariable("commandStack", commandStack);
				ctx.addVariable("editingDomain", editingDomain);

				// Execute the command
				handlerService.executeCommandInContext(parmCommand, null, ctx);

			} catch (Exception ex) {
				IStatus status = new Status(IStatus.ERROR, ProcessDiagramEditorPlugin.ID, 
						"Error while executing command to remove translation", ex);
				throw new CoreException(status);
			}
		} else {
			/** @TODO log warn no active editorPart, should never occur */
		}

		
	}
	
	/**
	 * @param model
	 */
	public ProcessTranslationRemoveCommand(ITranslationModel model) {
		super(model);
	}

}
