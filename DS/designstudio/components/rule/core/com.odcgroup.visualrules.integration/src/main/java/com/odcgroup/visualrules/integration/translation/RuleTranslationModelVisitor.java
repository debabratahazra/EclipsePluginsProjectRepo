package com.odcgroup.visualrules.integration.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.ITranslationModelVisitorHandler;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo;

/**
 * @author atr, kkr
 */
public class RuleTranslationModelVisitor implements ITranslationModelVisitor {
	
	/** */
	private ITranslationManager manager;
	
	/** */
	private RuleTranslationRepo translationModel;
	

	@Override
	public void visit(ITranslationModelVisitorHandler handler) {
		if(translationModel==null) return;

		for(RuleMessageProxy owner : translationModel.getCodeMap().values()) {
			ITranslation translation = manager.getTranslation(owner);
			handler.notify(translation);
		}		
	}
	
	
	/**
	 * @param project
	 * @param model
	 */
	public RuleTranslationModelVisitor(IProject project, RuleTranslationRepo model) {
		this.manager = TranslationCore.getTranslationManager(project);
		this.translationModel = model;
	}

}
