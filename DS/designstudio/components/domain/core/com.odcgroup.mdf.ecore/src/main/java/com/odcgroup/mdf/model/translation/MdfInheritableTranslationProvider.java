package com.odcgroup.mdf.model.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.provider.BaseTranslationProvider;
import com.odcgroup.translation.core.translation.BaseTranslation;

/**
 * This is the translation provider specific for datasets and datasets' attributes
 * @author yan, atr
 */
public class MdfInheritableTranslationProvider extends BaseTranslationProvider implements ITranslationProvider {

	public BaseTranslation newTranslation(IProject project, Object element) {
		if (element instanceof MdfDataset || element instanceof MdfDatasetMappedProperty) {
			return new MdfInheritableTranslation(this, project, (MdfModelElement)element);
		} else {
			throw new IllegalArgumentException("MdfInheritableTranslationProvider doesn't support this argument: " + element);
		}
	}

}
