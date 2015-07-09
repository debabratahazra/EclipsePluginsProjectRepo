package com.odcgroup.visualrules.integration.ui.translation.command;

import java.util.Locale;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.ui.command.TranslationRemoveCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.visualrules.integration.ui.RulesIntegrationUICore;

/**
 * @author atr
 */
public class RuleTranslationRemoveCommand extends TranslationRemoveCommand {

	@Override
	protected void initializeTexts() {
		setStandardName("Remove Translation");
		setStandardToolTip("Remove Translation");
	}

	@Override
	public void execute(String newText) throws CoreException {
		
		ITranslationKind kind = getModel().getSelectedKind();
		Locale locale = getModel().getSelectedLocale();
		ITranslation translation = getModel().getTranslation();
		
		// TODO: We cannot use the GEF command stack here as it does not work with VR
		try {
			translation.delete(kind, locale);
		} catch (TranslationException ex) {
			IStatus status = new Status(IStatus.ERROR, RulesIntegrationUICore.PLUGIN_ID, 
					"Error while executing command to remove a translation", ex);
			throw new CoreException(status);
		}	
		
	}
	
	/**
	 * @param model
	 */
	public RuleTranslationRemoveCommand(ITranslationModel model) {
		super(model);
	}

}
