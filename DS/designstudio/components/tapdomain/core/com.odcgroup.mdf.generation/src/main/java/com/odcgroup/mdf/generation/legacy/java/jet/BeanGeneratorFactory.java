package com.odcgroup.mdf.generation.legacy.java.jet;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.mdf.generation.legacy.java.SourceCodeGenerator;
import com.odcgroup.mdf.generation.legacy.java.SourceCodeGeneratorFactory;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;


public class BeanGeneratorFactory implements SourceCodeGeneratorFactory {
    public BeanGeneratorFactory() {
        super();
    }

    public SourceCodeGenerator[] getGenerators(MdfModelElement element) {
        List generators = new ArrayList();

        if (element instanceof MdfDomain) {
            generators.add(new DomainBeanGenerator((MdfDomain) element));
        } else if (element instanceof MdfClass) {
            generators.add(new BeanGenerator((MdfClass) element));
        } else if (element instanceof MdfDataset) {
            generators.add(new DatasetBeanGenerator((MdfDataset) element));
            generators.add(new DatasetXBeanGenerator((MdfDataset) element));
            generators.add(new PermValuesBeanGenerator((MdfDataset) element));
        }

        return (SourceCodeGenerator[]) generators.toArray(new SourceCodeGenerator[generators.size()]);
    }
}
