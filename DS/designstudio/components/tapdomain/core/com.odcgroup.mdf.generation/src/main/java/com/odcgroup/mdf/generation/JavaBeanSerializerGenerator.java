package com.odcgroup.mdf.generation;

import com.odcgroup.common.mdf.generation.MDFGenerator;
import com.odcgroup.mdf.generation.legacy.java.JavaBeanSerializerWriter;


public class JavaBeanSerializerGenerator extends MDFGenerator {

    public JavaBeanSerializerGenerator() {
        super(new JavaBeanSerializerWriter());
    }

}
