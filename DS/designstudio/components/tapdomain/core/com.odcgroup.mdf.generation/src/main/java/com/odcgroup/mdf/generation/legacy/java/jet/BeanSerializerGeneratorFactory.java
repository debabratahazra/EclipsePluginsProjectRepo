package com.odcgroup.mdf.generation.legacy.java.jet;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.mdf.generation.legacy.java.SourceCodeGenerator;
import com.odcgroup.mdf.generation.legacy.java.SourceCodeGeneratorFactory;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfModelElement;


public class BeanSerializerGeneratorFactory implements SourceCodeGeneratorFactory {
    public BeanSerializerGeneratorFactory() {
        super();
    }

    public SourceCodeGenerator[] getGenerators(MdfModelElement element) {
        List generators = new ArrayList();

        if (element instanceof MdfClass) {
            generators.add(new BeanSerializerGenerator((MdfClass) element));
        }
        return (SourceCodeGenerator[]) generators.toArray(new SourceCodeGenerator[generators.size()]);
    }
}
