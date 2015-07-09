package com.odcgroup.process.translation.command;

import java.util.Locale;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.ui.command.EMFUpdateTranslationCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * @author atr
 */
public class ProcessUpdateTranslationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get the new text
		String newText = event.getParameter("text");

		IEvaluationContext ctx = (IEvaluationContext) event.getApplicationContext();

		// Get the translation model
		ITranslationModel model = (ITranslationModel) ctx.getVariable("model");

		// Get the CommandStack
		CommandStack cs = (CommandStack) ctx.getVariable("commandStack");
		
		// Get the editing domain
		TransactionalEditingDomain editingDomain = (TransactionalEditingDomain)ctx.getVariable("editingDomain");

		ITranslationKind kind = model.getSelectedKind();
		Locale locale = model.getSelectedLocale();
		ITranslation translation = model.getTranslation();

		final EMFUpdateTranslationCommand cmd = new EMFUpdateTranslationCommand(translation, kind, locale, newText);
		RecordingCommand transactionalCommand = new RecordingCommand((TransactionalEditingDomain)editingDomain) {
			@Override
			protected void doExecute() {
				cmd.execute();
			}
		};
		
		cs.execute(transactionalCommand);

		return null;
	}

}
