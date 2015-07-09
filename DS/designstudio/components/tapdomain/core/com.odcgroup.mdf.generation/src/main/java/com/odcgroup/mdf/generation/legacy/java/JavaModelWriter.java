package com.odcgroup.mdf.generation.legacy.java;

import com.odcgroup.mdf.generation.legacy.java.jet.ModelGeneratorFactory;


/**
 * A MDF writer that generates Java source files.
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini </a>
 * @version 1.0
 */
public class JavaModelWriter extends AbstractSourceCodeWriter {
    /**
     * Constructor for ByteCodeWriter
     */
    public JavaModelWriter() {
        super("Java Model Code generator", new ModelGeneratorFactory());
    }
}
