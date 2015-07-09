package com.odcgroup.mdf.generation.legacy.java;

import com.odcgroup.mdf.generation.legacy.java.jet.BeanGeneratorFactory;


/**
 * A MDF writer that generates Java source files.
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini </a>
 * @version 1.0
 */
public class JavaBeanWriter extends AbstractSourceCodeWriter {
    /**
     * Constructor for ByteCodeWriter
     */
    public JavaBeanWriter() {
        super("Java Bean Code generator", new BeanGeneratorFactory());
    }
}
