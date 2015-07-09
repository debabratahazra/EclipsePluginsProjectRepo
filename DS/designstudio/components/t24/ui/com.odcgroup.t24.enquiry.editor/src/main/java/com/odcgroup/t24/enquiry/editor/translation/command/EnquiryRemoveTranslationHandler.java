package com.odcgroup.t24.enquiry.editor.translation.command;

import java.util.Locale;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.ui.command.GEFDeleteTranslationCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class EnquiryRemoveTranslationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {


		IEvaluationContext ctx = (IEvaluationContext) event.getApplicationContext();

		// Get the translation model
		ITranslationModel model = (ITranslationModel) ctx.getVariable("model");

		// Get the CommandStack
		CommandStack cs = (CommandStack) ctx.getVariable("commandStack");

		ITranslationKind kind = model.getSelectedKind();
		Locale locale = model.getSelectedLocale();
		ITranslation translation = model.getTranslation();

		GEFDeleteTranslationCommand cmd = new GEFDeleteTranslationCommand(translation, kind, locale);
		cs.execute(cmd);

		return null;
	
	}

}
