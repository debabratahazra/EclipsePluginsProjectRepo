package com.odcgroup.mdf.generation.legacy.java;

import com.odcgroup.mdf.generation.legacy.java.jet.ModelSerializerGeneratorFactory;


/**
 * A MDF writer that generates Java source files.
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini </a>
 * @version 1.0
 */
public class JavaModelSerializerWriter extends AbstractSourceCodeWriter {
    /**
     * Constructor for ByteCodeWriter
     */
    public JavaModelSerializerWriter() {
        super("Java Model Code generator", new ModelSerializerGeneratorFactory());
    }
}
