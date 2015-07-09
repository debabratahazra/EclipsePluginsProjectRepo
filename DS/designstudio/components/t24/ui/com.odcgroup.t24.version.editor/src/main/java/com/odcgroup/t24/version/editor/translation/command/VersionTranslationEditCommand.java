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
import com.odcgroup.translation.ui.command.TranslationEditCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * @author atr
 */
public class VersionTranslationEditCommand extends TranslationEditCommand {
	
	private static String EDIT_COMMAND_ID = "com.odcgroup.t24.version.editor.translation.update";

	@Override
	protected void initializeTexts() {
		setInheritedInformation("Translation inherited from the Domain");
		setInheritedName("Edit Local Translation");
		setInheritedToolTip("Override Domain Translation");
		setNotInheritedInformation("Local translation overriding the Domain one");
		setNotInheritedName("Edit Local Translation");
		setNotInheritedToolTip("Edit Local Translation");
		setStandardName("Edit Translation");
		setStandardToolTip("Edit Translation");
	}
	
	@Override
	public void execute(String newText) throws CoreException {
		if(newText.isEmpty()){
			if (getModel().getText()!=null) {
				VersionTranslationRemoveCommand removeCmd = new VersionTranslationRemoveCommand(getModel());
				removeCmd.execute(newText);
			}
			return;
		}
		if (newText.equals(getModel().getText())) {
			// do nothing if text is the same.
			return;
		}

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
					Command command = commandService.getCommand(EDIT_COMMAND_ID);
					Parameterization parameter = new Parameterization(command.getParameter("text"), newText);
					ParameterizedCommand parmCommand = new ParameterizedCommand(command,
							new Parameterization[] { parameter });
	
					// Prepare the evaluation context
					IEvaluationContext ctx = handlerService.getCurrentState();
					ctx.addVariable("model", getModel());
					ctx.addVariable("commandStack", commandStack);
					ctx.addVariable("editingDomain", editingDomain);
	
					// Execute the command
					handlerService.executeCommandInContext(parmCommand, null, ctx);
	
				} catch (Exception ex) {
					IStatus status = new Status(IStatus.ERROR, VersionEditorActivator.PLUGIN_ID, 
							"Error while executing command to update translation", ex);
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
	public VersionTranslationEditCommand(ITranslationModel model) {
		super(model);
	}

}
