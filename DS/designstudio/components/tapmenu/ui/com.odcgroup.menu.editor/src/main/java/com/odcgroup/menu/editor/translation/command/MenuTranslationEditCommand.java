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
import com.odcgroup.translation.ui.command.TranslationEditCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * @author snn
 *
 */
public class MenuTranslationEditCommand extends TranslationEditCommand {

	private static String EDIT_COMMAND_ID = "com.odcgroup.menu.command.translation.update";

	public MenuTranslationEditCommand(ITranslationModel model) {
		super(model);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.command.TranslationCommand#initializeTexts()
	 */
	protected void initializeTexts() {
		setStandardName("Edit Translation");
		setStandardToolTip("Edit Translation");
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.command.TranslationCommand#release()
	 */
	public void release() {
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.command.ITranslationCommand#execute(java.lang.String)
	 */
	public void execute(String newText) throws CoreException {

		if (newText.equals(getModel().getText())) {
			// do nothing if text is the same.
			return;
		}
		
		if(newText.isEmpty()){
			if (getModel().getText()!=null) {
				MenuTranslationRemoveCommand removeCmd = new MenuTranslationRemoveCommand(getModel());
				removeCmd.execute(newText);
			}
			return;
		}

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

				// Get the command and assign values to parameters
				Command command = commandService.getCommand(EDIT_COMMAND_ID);
				Parameterization parameter = new Parameterization(
						command.getParameter("text"), newText);
				ParameterizedCommand parmCommand = new ParameterizedCommand(
						command, new Parameterization[] { parameter });

				// Prepare the evaluation context
				IEvaluationContext ctx = handlerService.getCurrentState();
				ctx.addVariable("model", getModel());
				ctx.addVariable("commandStack", commandStack);

				// Execute the command
				handlerService.executeCommandInContext(parmCommand, null, ctx);

			} catch (Exception ex) {
				IStatus status = new Status(IStatus.ERROR,
						MenuEditorPlugin.PLUGIN_ID,
						"Error while executing command to update translation",
						ex);
				throw new CoreException(status);
			}
		} else {
			/** @TODO log warn no active editorPart, should never occur */
		}
	}
}
