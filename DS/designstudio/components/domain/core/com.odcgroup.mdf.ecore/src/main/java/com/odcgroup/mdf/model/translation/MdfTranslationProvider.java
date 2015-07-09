package com.odcgroup.mdf.model.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.provider.BaseTranslationProvider;
import com.odcgroup.translation.core.translation.BaseTranslation;

/**
 * This is the translation provider for all domain elements 
 *
 * @author yan
 */
public class MdfTranslationProvider extends BaseTranslationProvider implements ITranslationProvider {

	public BaseTranslation newTranslation(IProject project, Object element) {
		if (element instanceof MdfDataset) {
			throw new IllegalArgumentException("Do not use MdfTranslationProvider for MdfDataset, use MdfInheritableTranslationProvider instead");
		}

		if (element instanceof MdfDatasetMappedProperty) {
			throw new IllegalArgumentException("Do not use MdfTranslationProvider for MdfDatasetMappedProperty, use MdfInheritableTranslationProvider instead");
		}

		if (element instanceof MdfModelElement) {
			return new MdfTranslation(this, project, (MdfModelElement)element);
		} else {
			throw new IllegalArgumentException("MdfTranslationProvider doesn't support this argument: " + element);
		}
	}

}
