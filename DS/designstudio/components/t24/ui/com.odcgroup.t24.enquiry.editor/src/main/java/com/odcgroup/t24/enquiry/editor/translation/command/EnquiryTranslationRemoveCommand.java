package com.odcgroup.t24.enquiry.editor.translation.command;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.Parameterization;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;

import com.odcgroup.t24.enquiry.editor.Activator;
import com.odcgroup.translation.ui.command.TranslationRemoveCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class EnquiryTranslationRemoveCommand extends TranslationRemoveCommand {

	/**
	 * @param model
	 */
	public EnquiryTranslationRemoveCommand(ITranslationModel model) {
		super(model);
	}

	private static String REMOVE_COMMAND_ID = "com.odcgroup.t24.enquiry.editor.translation.command.remove";
	
	protected String getCommandId() {
		return EnquiryTranslationRemoveCommand.REMOVE_COMMAND_ID;
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

			CommandStack commandStack = (CommandStack)editorPart.getAdapter(CommandStack.class);
			
			try {

				// Get the command and assign values to parameters
				Command command = commandService.getCommand(getCommandId());
				ParameterizedCommand parmCommand = new ParameterizedCommand(command, new Parameterization[] {});

				// Prepare the evaluation context
				IEvaluationContext ctx = handlerService.getCurrentState();
				ctx.addVariable("model", getModel());
				ctx.addVariable("commandStack", commandStack);

				// Execute the command
				handlerService.executeCommandInContext(parmCommand, null, ctx);

			} catch (Exception ex) {
				IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, 
						"Error while executing command to remove translation", ex);
				throw new CoreException(status);
			}
		} else {
			/** @TODO log warn no active editorPart, should never occur */
		}

		
	}

	@Override
	protected void initializeTexts() {
		setInheritedInformation("Translation inherited from the Domain");
		setNotInheritedInformation("Local translation overriding the Domain one");
		setNotInheritedName("Remove Local Translation");
		setNotInheritedToolTip("Revert to Domain translation");
		setStandardName("Remove Translation");
		setStandardToolTip("Remove Translation");
	}

}
