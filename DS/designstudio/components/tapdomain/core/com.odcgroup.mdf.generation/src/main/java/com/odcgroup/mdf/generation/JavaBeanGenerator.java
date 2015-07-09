package com.odcgroup.mdf.generation;

import com.odcgroup.common.mdf.generation.MDFGenerator;
import com.odcgroup.mdf.generation.legacy.java.JavaBeanWriter;


public class JavaBeanGenerator extends MDFGenerator {

    public JavaBeanGenerator() {
        super(new JavaBeanWriter());
    }

}
