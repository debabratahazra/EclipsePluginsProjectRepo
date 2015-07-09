package com.odcgroup.mdf.migration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.slf4j.Logger;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.migration.ITranslationModelMigrator;
import com.odcgroup.translation.core.migration.TranslationMigrationException;
import com.odcgroup.translation.core.migration.TranslationVO;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * @author yan
 */
public class DomainTranslationMigrator implements ITranslationModelMigrator {

	private static final String TOOLTIP_SUFFIX = ".tooltip";

	private static final String TEXT_SUFFIX = ".text";

	private Set<Resource> modifiedElements = new HashSet<Resource>();

	private Map<String, String> saveOptions = Collections.emptyMap();
	
	private Logger migrationLogger;
	
	private IOfsProject ofsProject;

	public void startMigration(Logger migrationLogger, IOfsProject ofsProject) {
		if (null == migrationLogger || null == ofsProject) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
		this.migrationLogger = migrationLogger;
		this.ofsProject = ofsProject;
	}

	/**
	 * Accepts key of format domain.entityname.(text|tooltip) and
	 * domain.enumname.enumvalue.(text|tooltip)
	 */
	public boolean isKeyAccepted(String key) {
		if (!key.endsWith(TEXT_SUFFIX) && !key.endsWith(TOOLTIP_SUFFIX)) {
			return false;
		}
		int nbToken = new StringTokenizer(key, ".").countTokens();
		if (nbToken == 3 || nbToken == 4) {
			return true;
		} else {
			return false;
		}
	}

	public boolean process(TranslationVO vo) {
		String key = vo.key;
		ITranslationKind kind;
		if (vo.key.endsWith(TEXT_SUFFIX)) {
			kind = ITranslationKind.NAME;
			key = StringUtils.remove(key, TEXT_SUFFIX);
		} else if (vo.key.endsWith(TOOLTIP_SUFFIX)) {
			kind = ITranslationKind.TEXT;
			key = StringUtils.remove(key, TOOLTIP_SUFFIX);
		} else {
			vo.errorStatus = "Unable to define the translation kind by the suffix";
			return false;
		}
		
		EObject mdfModelElement = findMdfModelElement(key);
		
		if (mdfModelElement == null) {
			// Skip translation migration of not found models
			vo.errorStatus = "Unable to find the associated model";
			return false;
		}
		
		if (!pushTranslationInModel(mdfModelElement, kind, new Locale(vo.language), vo.text)) {
			vo.errorStatus = "Unable to define the translation in the model";
			return false;
		}
		return true;
	}

	/**
	 * @param ofsProject
	 * @param key (without .text or .tooltip)
	 * @return the corresponding eobject
	 */
	@SuppressWarnings("unchecked")
	private EObject findMdfModelElement(String key) {
		StringTokenizer st = new StringTokenizer(key, ".");
		int nbToken = st.countTokens();
		if (nbToken != 2 && nbToken != 3) {
			migrationLogger.trace("Unable to find the domain related to " + key);
			return null;
		}
		String domainNameLowerCase = st.nextToken();
		String entityNameLowerCase = (st.hasMoreTokens()?st.nextToken():"");
		String propertyNameLowerCase = (st.hasMoreTokens()?st.nextToken():"");
		
		MdfDomain domain = (MdfDomain)findMdfDomain(domainNameLowerCase);
		 
		if (domain == null) {
			return null;
		}
		
		if (entityNameLowerCase.equals("")) {
			return (EObject)domain;
		} else {
			for (MdfEntity entity : (List<MdfEntity>)domain.getEntities()) {
				if (entityNameLowerCase.equalsIgnoreCase(entity.getName())) {
					if (StringUtils.isEmpty(propertyNameLowerCase)) {
						return (EObject)entity;
					} else {
						if (entity instanceof MdfClass) {
							MdfClass clazz = (MdfClass)entity;
							for (MdfProperty property: (List<MdfProperty>)clazz.getProperties()) {
								if (propertyNameLowerCase.equalsIgnoreCase(property.getName())) {
									return (EObject)property;
								}
							}
							migrationLogger.trace("Unable to find the \"" + propertyNameLowerCase + "\" class attribute of from the \"" + entityNameLowerCase + "\" class");
							return null;
						} else if (entity instanceof MdfDataset) {
							MdfDataset dataset = (MdfDataset)entity;
							for (MdfDatasetProperty property: (List<MdfDatasetProperty>)dataset.getProperties()) {
								if (propertyNameLowerCase.equalsIgnoreCase(property.getName())) {
									return (EObject)property;
								}
							}
							migrationLogger.trace("Unable to find the \"" + propertyNameLowerCase + "\" dataset attribute of from the \"" + entityNameLowerCase + "\" dataset");
							return null;
						} else if (entity instanceof MdfEnumeration) {
							MdfEnumeration enumeration = (MdfEnumeration)entity;
							for (MdfEnumValue enumValue : (List<MdfEnumValue>)enumeration.getValues()) {
								if (propertyNameLowerCase.equals(enumValue.getValue())) {
									return (EObject)enumValue;
								}
							}
							migrationLogger.trace("Unable to find the \"" + propertyNameLowerCase + "\" enum value of from the \"" + entityNameLowerCase + "\" enum");
							return null;
						} else {
							migrationLogger.trace("The \"" + entityNameLowerCase + "\" is not an enumeration but have an enum value: \"" + propertyNameLowerCase + "\"");
							return null;
						}
					}
				}
			}
		}
		migrationLogger.trace("Unable to find the domain related to " + key);
		return null;
	}

	/**
	 * @param ofsProject
	 * @param key
	 * @return
	 */
	private MdfDomain findMdfDomain(String key) {
		MdfDomain result = null;
		DomainRepository repository = DomainRepository.getInstance(ofsProject);
		for (MdfDomain domain : repository.getDomains(IOfsProject.SCOPE_PROJECT)) {
			if (key.equalsIgnoreCase(domain.getName())) {
				result = domain;
				break;
			}
		}
//		for (Resource resource : ofsProject.getModelResourceSet().getResources()) {
//			IOfsModelResource ofsResource = ofsProject.getModelResourceSet().getOfsModelResource(resource.getURI());
//			try {
//				MdfDomain domain = (MdfDomain)ofsResource.getEMFModel().get(0);
//				if (key.equalsIgnoreCase(domain.getName())) {
//					return domain;
//				}
//			} catch (Exception e) {
//				migrationLogger.error("Unable to read " + ofsResource.getName(), e);
//				return null;
//			}
//		}
		if (null == result) {
			migrationLogger.trace("Unable to find the domain related to " + key);
		}
		return result;
	}

	/**
	 * @param ofsProject
	 * @param mdfModelElement
	 * @param kind
	 * @param locale
	 * @param text
	 * @return
	 */
	private boolean pushTranslationInModel(EObject mdfModelElement, ITranslationKind kind,
			Locale locale, String text) {
		if (mdfModelElement != null) {
			IProject project = OfsResourceHelper.getProject(((EObject) mdfModelElement).eResource());
			ITranslationManager manager = TranslationCore.getTranslationManager(project);
			ITranslation translation = manager.getTranslation(mdfModelElement);
			try {
				translation.setText(kind, locale, text);
				modifiedElements.add(mdfModelElement.eResource());
				return true;
			} catch (TranslationException e) {
				return false;
			}
		} else {
			return false;
		}
	}


	public List<TranslationVO> endMigration() throws TranslationMigrationException {
		for (Resource resource: modifiedElements) {
			try {
				migrationLogger.trace("Saving modifications of " + resource.getURI());
				resource.save(saveOptions);
			} catch (IOException e) {
				migrationLogger.error("Unable to save " + resource.getURI(), e);
				throw new TranslationMigrationException("Unable to save " + resource.getURI(), e);
			}
		}
		return new ArrayList<TranslationVO>();
	}

}
