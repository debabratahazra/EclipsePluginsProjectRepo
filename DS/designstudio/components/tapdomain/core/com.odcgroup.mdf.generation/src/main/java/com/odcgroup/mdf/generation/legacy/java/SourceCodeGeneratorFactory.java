package com.odcgroup.mdf.generation.legacy.java;

import com.odcgroup.mdf.metamodel.MdfModelElement;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 2.0
 */
public interface SourceCodeGeneratorFactory {
    public SourceCodeGenerator[] getGenerators(MdfModelElement element);
}
