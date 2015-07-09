package com.odcgroup.mdf.generation.legacy.java;

import com.odcgroup.mdf.generation.legacy.java.jet.BeanSerializerGeneratorFactory;


/**
 * A MDF writer that generates Java source files.
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini </a>
 * @version 1.0
 */
public class JavaBeanSerializerWriter extends AbstractSourceCodeWriter {
    /**
     * Constructor for ByteCodeWriter
     */
    public JavaBeanSerializerWriter() {
        super("Java Bean Serializer Code generator", new BeanSerializerGeneratorFactory());
    }
}
