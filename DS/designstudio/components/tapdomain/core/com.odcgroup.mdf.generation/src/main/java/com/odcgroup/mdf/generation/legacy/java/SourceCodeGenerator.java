package com.odcgroup.mdf.generation.legacy.java;



/**
 * Base class for Byte Code generators
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public abstract class SourceCodeGenerator {

    public abstract String getClassName();

    /**
     * Returns the generated Java source code.
     *
     * @return the generated Java source code.
     */
    public abstract String generate();

}
