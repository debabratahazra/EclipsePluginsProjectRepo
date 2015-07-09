package com.odcgroup.t24.version.editor.translation.command;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.Parameterization;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;

import com.odcgroup.t24.version.editor.VersionEditorActivator;
import com.odcgroup.t24.version.editor.ui.VersionMultiPageEditor;
import com.odcgroup.translation.ui.command.TranslationRemoveCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * @author atr
 */
public class VersionTranslationRemoveCommand extends TranslationRemoveCommand {

	private static String REMOVE_COMMAND_ID = "com.odcgroup.t24.version.editor.translation.remove";

	protected String getCommandId() {
		return VersionTranslationRemoveCommand.REMOVE_COMMAND_ID;
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

	@Override
	public void execute(String newText) throws CoreException {
		
		IEditorPart editorPart = getActiveEditorPart();
		if (editorPart != null) {

			// Extract useful parameters needed for the command
			IWorkbenchPartSite site = editorPart.getSite();

			// Invoke the command
			IHandlerService handlerService = (IHandlerService) site.getService(IHandlerService.class);
			ICommandService commandService = (ICommandService) site.getService(ICommandService.class);
			
			if (editorPart instanceof VersionMultiPageEditor) {
				VersionMultiPageEditor vEditor = (VersionMultiPageEditor) editorPart;
				EditingDomain editingDomain = vEditor.getVersionFormEditor().getEditingDomain();
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
					IStatus status = new Status(IStatus.ERROR, VersionEditorActivator.PLUGIN_ID, 
							"Error while executing command to remove translation", ex);
					throw new CoreException(status);
				}
			}
		} else {
			/** @TODO log warn no active editorPart, should never occur */
		}

		
	}
	
	/**
	 * @param model
	 */
	public VersionTranslationRemoveCommand(ITranslationModel model) {
		super(model);
	}

}
