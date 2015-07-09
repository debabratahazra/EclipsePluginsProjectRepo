package com.odcgroup.page.docgen.utils;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Translation;
import com.odcgroup.page.model.Widget;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.workbench.core.IOfsProject;

public class ModuleUtils {

	
	/**
	 * return 
	 * @param ofsProject
	 * @param widget
	 * @return MdfDataset
	 */
	public static String getDatasetName(IOfsProject ofsProject, Widget widget) {
		return	getDataset(ofsProject,widget)!=null ? getDataset(ofsProject,widget).getQualifiedName().toString() : "";
	}

	/**
	 * return 
	 * @param ofsProject
	 * @param widget
	 * @return MdfDataset
	 */
	public static String getDatasetBaseClassName(IOfsProject ofsProject, Widget widget) {
		return getDatasetBaseClass(ofsProject,widget)!= null ? getDatasetBaseClass(ofsProject,widget).getQualifiedName().toString() : "";
	}
	
	/**
	 * @param ofsProject
	 * @param widget
	 * @return MdfDataset
	 */
	public static MdfDataset getDataset(IOfsProject ofsProject, Widget widget) {
		String datasetName = widget.getRootWidget().getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
		DomainRepository repository = DomainRepository.getInstance(ofsProject);
		if (StringUtils.isNotBlank(datasetName) && MdfNameFactory.createMdfName(datasetName) != null
				&& MdfNameFactory.createMdfName(datasetName).getDomain() != null
				&& !MdfNameFactory.createMdfName(datasetName).getDomain().isEmpty()) {
			return repository.getDataset(MdfNameFactory.createMdfName(datasetName));	
		}
		return null;
	}

	/**
	 * @param ofsProject
	 * @param widget
	 * @return MdfDataset
	 */
	public static MdfClass getDatasetBaseClass(IOfsProject ofsProject, Widget widget) {
		return getDataset(ofsProject,widget)!=null ? getDataset(ofsProject,widget).getBaseClass() : null;
	}

	/**
	 * @param ofsProject
	 * @param widget
	 * @return
	 */
	public static String getBusinessTypeName(IOfsProject ofsProject, Widget widget) {
		return getBusinessDataType(ofsProject,widget)!= null ? getBusinessDataType(ofsProject,widget).getQualifiedName().toString() : "";
	}
	
	/**
	 * @param ofsProject
	 * @param widget
	 * @return
	 */
	public static MdfBusinessType getBusinessDataType(IOfsProject ofsProject, Widget widget) {
		String datasetName = widget.getRootWidget().getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
		DomainRepository repository = DomainRepository.getInstance(ofsProject);
		return repository.getBusinessType(MdfNameFactory.createMdfName(datasetName));
	}
	
	/**
	 * @param ofsProject
	 * @param widget
	 * @return
	 */
	public static String getTitle(IOfsProject ofsProject, Widget widget) {
		String tiltle = null;
		Locale defLocale = TranslationCore.getTranslationManager(ofsProject.getProject())
				  .getPreferences()
				  .getDefaultLocale();
		for(Translation trans: widget.getLabels()){
			if(defLocale.getLanguage().equals(trans.getLanguage())){
				tiltle = trans.getMessage();
				break;
			}
		}
		return tiltle;
	}
	
	public static String getTranslation(IOfsProject ofsProject,Widget widget){
		String tiltle = null;
		ITranslationManager manager = TranslationCore.getTranslationManager(ofsProject.getProject());
		try {
			Locale defLocale = manager.getPreferences().getDefaultLocale();
			if (manager.getTranslation(widget)!=null && manager.getTranslation(widget).messagesFound(ITranslationKind.NAME)) {
				tiltle = manager.getTranslation(widget).getInheritedText(
						ITranslationKind.NAME, defLocale);
			}
		} catch (TranslationException e) {
			e.printStackTrace();
		} catch (Exception ute){
			
		}
		return tiltle;
		
	}
	
}
