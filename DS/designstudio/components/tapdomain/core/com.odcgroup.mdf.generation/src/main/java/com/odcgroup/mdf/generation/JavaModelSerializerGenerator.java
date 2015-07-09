package com.odcgroup.mdf.generation;

import com.odcgroup.common.mdf.generation.MDFGenerator;
import com.odcgroup.mdf.generation.legacy.java.JavaModelSerializerWriter;


public class JavaModelSerializerGenerator extends MDFGenerator {

    public JavaModelSerializerGenerator() {
        super(new JavaModelSerializerWriter());
    }

}
