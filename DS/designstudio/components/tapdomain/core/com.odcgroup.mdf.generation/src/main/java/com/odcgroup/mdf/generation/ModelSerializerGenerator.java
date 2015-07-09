package com.odcgroup.mdf.generation;

import java.io.IOException;

import com.odcgroup.common.mdf.generation.MDFGenerator;
import com.odcgroup.mdf.generation.legacy.java.ModelSerializerWriter;


public class ModelSerializerGenerator extends MDFGenerator {

    public ModelSerializerGenerator() throws IOException {
        super(new ModelSerializerWriter());
    }

}
