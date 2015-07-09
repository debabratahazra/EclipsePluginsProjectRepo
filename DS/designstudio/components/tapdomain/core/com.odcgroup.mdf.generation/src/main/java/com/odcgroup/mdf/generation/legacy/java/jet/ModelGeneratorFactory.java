package com.odcgroup.mdf.generation.legacy.java.jet;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.mdf.generation.legacy.java.SourceCodeGenerator;
import com.odcgroup.mdf.generation.legacy.java.SourceCodeGeneratorFactory;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;


public class ModelGeneratorFactory implements SourceCodeGeneratorFactory {
	public static final String USE_BASIC_OBJECTID_GEN = "useBasicIdGenerator";
    public ModelGeneratorFactory() {
        super();
    }

    public SourceCodeGenerator[] getGenerators(MdfModelElement element) {
        List generators = new ArrayList();

        if (element instanceof MdfDomain) {
            generators.add(new DomainModelGenerator((MdfDomain) element));
        } else if (element instanceof MdfClass) {
            generators.add(new ClassGenerator((MdfClass) element));

            if (((MdfClass) element).hasPrimaryKey(true)) {
            	if (Boolean.getBoolean(USE_BASIC_OBJECTID_GEN)) {
            		generators.add(new ObjectIdGenerator((MdfClass) element));
            	} else {
            		// By default we use the configurable id generator            		
            		generators.add(new ConfigurableObjectIdGenerator((MdfClass) element));            		
            	}
            }
        } else if (element instanceof MdfEnumeration) {
            generators.add(new EnumerationGenerator((MdfEnumeration) element));
        } else if (element instanceof MdfDataset) {
            generators.add(new DatasetGenerator((MdfDataset) element));
            generators.add(new PermValuesGenerator((MdfDataset) element));
        }

        return (SourceCodeGenerator[]) generators.toArray(new SourceCodeGenerator[generators.size()]);
    }
}
