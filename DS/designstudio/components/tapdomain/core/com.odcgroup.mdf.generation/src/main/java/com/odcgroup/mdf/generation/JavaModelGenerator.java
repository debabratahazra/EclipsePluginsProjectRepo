package com.odcgroup.mdf.generation;

import com.odcgroup.common.mdf.generation.MDFGenerator;
import com.odcgroup.mdf.generation.legacy.java.JavaModelWriter;


public class JavaModelGenerator extends MDFGenerator {

    public JavaModelGenerator() {
        super(new JavaModelWriter());
    }

}
