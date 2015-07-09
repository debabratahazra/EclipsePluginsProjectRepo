package com.odcgroup.t24.mdf.model.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.model.translation.MdfInheritableTranslation;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.provider.BaseTranslationProvider;
import com.odcgroup.translation.core.translation.BaseTranslation;

public class T24MdfTranslationProvider extends BaseTranslationProvider implements ITranslationProvider {

    @Override
    protected BaseTranslation newTranslation(IProject project, Object element) {
        if (element instanceof MdfClass ) {
            return new T24MdfTranslation(this, project,(MdfModelElement) element);
        } else if (element instanceof MdfDataset || element instanceof MdfDatasetMappedProperty) {
            return new MdfInheritableTranslation(this, project, (MdfModelElement)element);
        } else if (element instanceof MdfModelElement ) {
            return new T24MdfModelEleTranslation(this, project,(MdfModelElement) element);
        } else {
            throw new IllegalArgumentException("T24MdfTranslationProvider doesn't support this argument: " + element);
        }
    }

}



