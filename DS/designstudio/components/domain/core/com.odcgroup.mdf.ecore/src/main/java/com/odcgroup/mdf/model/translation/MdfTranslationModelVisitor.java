package com.odcgroup.mdf.model.translation;

import java.util.List;

import org.eclipse.core.resources.IProject;

import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.ITranslationModelVisitorHandler;
import com.odcgroup.translation.core.TranslationCore;

/**
 * @author atr
 */
public class MdfTranslationModelVisitor implements ITranslationModelVisitor {

	/** */
	private ITranslationManager manager;
	/** */
	private MdfDomain domain;
	
	private final void notify(ITranslationModelVisitorHandler handler, Object object) {
		ITranslation translation = manager.getTranslation(object);
		if (translation != null) {
			handler.notify(translation);
		}
	}
	
	@Override
	public void visit(ITranslationModelVisitorHandler handler) {
		
		notify(handler, domain);
		
		// Enumerations
		for (MdfEnumeration mdfEnum : (List<MdfEnumeration>)domain.getEnumerations()) {
			notify(handler, mdfEnum);
			for (MdfEnumValue value : (List<MdfEnumValue>)mdfEnum.getValues()) {
				notify(handler, value);
			}
		}

		// Business Types
		for (MdfBusinessType mdfBT : (List<MdfBusinessType>)domain.getBusinessTypes()) {
			notify(handler, mdfBT);
		}

		// Classes
		for (MdfClass clazz : (List<MdfClass>)domain.getClasses()) {
			notify(handler, clazz);
			for (MdfProperty prop : (List<MdfProperty>)clazz.getProperties()) {
				notify(handler, prop);
			}
		}
		
		// Datasets
		for (MdfDataset ds : (List<MdfDataset>)domain.getDatasets()) {
			notify(handler, ds);
			for (MdfDatasetProperty prop : (List<MdfDatasetProperty>)ds.getProperties()) {
				notify(handler, prop);
			}		
		}
		
	}

	
	/**
	 * @param project
	 * @param domain
	 */
	public MdfTranslationModelVisitor(IProject project, MdfDomain domain) {
		this.manager = TranslationCore.getTranslationManager(project);
		this.domain = domain;
	}
}
