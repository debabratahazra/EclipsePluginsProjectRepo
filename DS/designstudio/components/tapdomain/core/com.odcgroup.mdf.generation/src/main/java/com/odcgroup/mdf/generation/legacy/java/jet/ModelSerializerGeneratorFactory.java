package com.odcgroup.mdf.generation.legacy.java.jet;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.mdf.generation.legacy.java.SourceCodeGenerator;
import com.odcgroup.mdf.generation.legacy.java.SourceCodeGeneratorFactory;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfModelElement;


public class ModelSerializerGeneratorFactory implements SourceCodeGeneratorFactory {
	public static final String USE_CONFIGURABLE_OBJECTID_GEN = "useConfigurableObjectIdGenerator";
    public ModelSerializerGeneratorFactory() {
        super();
    }

    public SourceCodeGenerator[] getGenerators(MdfModelElement element) {
        List<SourceCodeGenerator> generators = new ArrayList<SourceCodeGenerator>();

        if (element instanceof MdfClass) {
            if (((MdfClass) element).hasPrimaryKey(true)) {
           		generators.add(new ObjectIdSerializerGenerator((MdfClass) element));
            }
        }

        return generators.toArray(new SourceCodeGenerator[generators.size()]);
    }
}
