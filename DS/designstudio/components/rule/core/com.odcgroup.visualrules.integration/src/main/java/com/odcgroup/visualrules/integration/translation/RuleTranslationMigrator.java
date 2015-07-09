package com.odcgroup.visualrules.integration.translation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.slf4j.Logger;

import com.odcgroup.translation.core.migration.ITranslationModelMigrator;
import com.odcgroup.translation.core.migration.TranslationMigrationException;
import com.odcgroup.translation.core.migration.TranslationVO;
import com.odcgroup.visualrules.integration.RulesIntegrationUtils;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationFactory;
import com.odcgroup.visualrules.integration.model.ruletranslation.Translation;
import com.odcgroup.visualrules.integration.model.ruletranslation.TranslationKind;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * @author atr, kkr
 */
public class RuleTranslationMigrator implements ITranslationModelMigrator {

	private static final String TRANSLATION_SUFFIX = ".message"; //$NON-NLS-1$
	
	private Logger migrationLogger;
	
	private RuleTranslationRepo ruleTranslationModel;
	
	@Override
	public void startMigration(Logger migrationLogger, IOfsProject ofsProject) {
		if (null == migrationLogger || null == ofsProject) {
			throw new IllegalArgumentException("Arguments cannot be null"); //$NON-NLS-1$
		}
		this.migrationLogger = migrationLogger;
		this.ruleTranslationModel = RulesIntegrationUtils.getRulesTranslationModel(ofsProject.getProject());
	}

	@Override
	public boolean isKeyAccepted(String key) {
		return StringUtils.isNotEmpty(key) 
			&& Character.isDigit(key.charAt(0))		
		    && key.endsWith(TRANSLATION_SUFFIX);
	}

	@Override
	public boolean process(TranslationVO vo) {
		if(ruleTranslationModel==null) return false;
		EMap<String, RuleMessageProxy> map = ruleTranslationModel.getCodeMap();
		RuleMessageProxy msg = map.get(vo.key);
		if(msg==null) {
			msg = RuletranslationFactory.eINSTANCE.createRuleMessageProxy();
			map.put(vo.key, msg);
		}
		Translation translation = RuletranslationFactory.eINSTANCE.createTranslation();
		translation.setKind(TranslationKind.NAME);
		translation.setLanguage(vo.language);
		translation.setMessage(vo.text);
		msg.getTranslations().add(translation);
		return true;
	}

	@Override
	public List<TranslationVO> endMigration() throws TranslationMigrationException {
		if(ruleTranslationModel != null) {
			Map<String, String> saveOptions = new HashMap<String, String>();
			saveOptions.put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
			Resource resource = ruleTranslationModel.eResource();
			try {
				migrationLogger.trace("Saving modifications of " + resource.getURI()); //$NON-NLS-1$
				resource.save(saveOptions);
			} catch (IOException e) {
				migrationLogger.error("Unable to save " + resource.getURI(), e); //$NON-NLS-1$
				throw new TranslationMigrationException("Unable to save " + resource.getURI(), e); //$NON-NLS-1$
			}
		} else {
			migrationLogger.trace("Project does not support rules, so no rule translation migration is performed."); //$NON-NLS-1$
		}
		return new ArrayList<TranslationVO>();
	}

	/** */
	public RuleTranslationMigrator() {
	}

}
