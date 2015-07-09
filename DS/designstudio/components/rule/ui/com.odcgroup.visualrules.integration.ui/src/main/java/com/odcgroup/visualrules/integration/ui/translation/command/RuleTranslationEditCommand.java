package com.odcgroup.visualrules.integration.ui.translation.command;

import java.util.Locale;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.ui.command.TranslationEditCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.visualrules.integration.ui.RulesIntegrationUICore;

/**
 * @author atr
 */
public class RuleTranslationEditCommand extends TranslationEditCommand {
	
	@Override
	protected void initializeTexts() {
		setStandardName("Edit Translation");
		setStandardToolTip("Edit Translation");
	}
	
	@Override
	public void execute(String newText) throws CoreException {
		if(newText.isEmpty()){
			if (getModel().getText()!=null) {
				RuleTranslationRemoveCommand removeCmd = new RuleTranslationRemoveCommand(getModel());
				removeCmd.execute(newText);
			}
			return;
		}
		if (newText.equals(getModel().getText())) {
			// do nothing if text is the same.
			return;
		}

		ITranslationKind kind = getModel().getSelectedKind();
		Locale locale = getModel().getSelectedLocale();
		ITranslation translation = getModel().getTranslation();
		
		// TODO: We cannot use the GEF command stack here as it does not work with VR				
		try {
			translation.setText(kind, locale, newText);
		} catch (TranslationException ex) {
			IStatus status = new Status(IStatus.ERROR, RulesIntegrationUICore.PLUGIN_ID, 
					"Error while executing command to update a translation", ex);
			throw new CoreException(status);
		}

		
	}
	

	/**
	 * @param model
	 */
	public RuleTranslationEditCommand(ITranslationModel model) {
		super(model);
	}

}
