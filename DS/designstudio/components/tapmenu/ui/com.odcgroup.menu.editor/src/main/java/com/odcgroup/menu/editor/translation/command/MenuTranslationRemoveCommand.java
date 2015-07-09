package com.odcgroup.menu.editor.translation.command;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.Parameterization;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;

import com.odcgroup.menu.editor.MenuEditorPlugin;
import com.odcgroup.translation.ui.command.TranslationRemoveCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * @author scn
 *
 */
public class MenuTranslationRemoveCommand extends TranslationRemoveCommand {

	private static String REMOVE_COMMAND_ID = "com.odcgroup.menu.command.translation.remove";

	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.command.TranslationCommand#initializeTexts()
	 */
	protected void initializeTexts() {
		setStandardName("Remove Translation");
		setStandardToolTip("Remove Translation");
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.command.ITranslationCommand#execute(java.lang.String)
	 */
	public void execute(String newText) throws CoreException {

		IEditorPart editorPart = getActiveEditorPart();
		if (editorPart != null) {

			// Extract useful parameters needed for the command
			IWorkbenchPartSite site = editorPart.getSite();

			// Invoke the command
			IHandlerService handlerService = (IHandlerService) site
					.getService(IHandlerService.class);
			ICommandService commandService = (ICommandService) site
					.getService(ICommandService.class);

			CommandStack commandStack = (CommandStack) editorPart
					.getAdapter(CommandStack.class);

			try {

				Command command = commandService.getCommand(REMOVE_COMMAND_ID);
				ParameterizedCommand parmCommand = new ParameterizedCommand(
						command, new Parameterization[] {});

				// Prepare the evaluation context
				IEvaluationContext ctx = handlerService.getCurrentState();
				ctx.addVariable("model", getModel());
				ctx.addVariable("commandStack", commandStack);

				// Execute the command
				handlerService.executeCommandInContext(parmCommand, null, ctx);

			} catch (Exception ex) {
				IStatus status = new Status(IStatus.ERROR,
						MenuEditorPlugin.PLUGIN_ID,
						"Error while executing command to remove translation",
						ex);
				throw new CoreException(status);
			}
		} else {
			/** @TODO log warn no active editorPart, should never occur */
		}
	}

	/**
	 * @param model
	 */
	public MenuTranslationRemoveCommand(ITranslationModel model) {
		super(model);
	}

}
