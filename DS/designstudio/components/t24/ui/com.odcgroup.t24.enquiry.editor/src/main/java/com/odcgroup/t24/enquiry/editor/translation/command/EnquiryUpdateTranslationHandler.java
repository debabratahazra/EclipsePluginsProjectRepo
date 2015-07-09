package com.odcgroup.t24.enquiry.editor.translation.command;

import java.util.Locale;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.ui.command.GEFUpdateTranslationCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class EnquiryUpdateTranslationHandler extends AbstractHandler {

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
		// EditingDomain editingDomain = (EditingDomain)ctx.getVariable("editingDomain");

		ITranslationKind kind = model.getSelectedKind();
		Locale locale = model.getSelectedLocale();
		ITranslation translation = model.getTranslation();

		GEFUpdateTranslationCommand cmd = new GEFUpdateTranslationCommand(translation, kind, locale, newText);
		cs.execute(cmd);

		return null;
	
	}

}
