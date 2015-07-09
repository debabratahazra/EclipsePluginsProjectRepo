package com.odcgroup.mdf.generation;

import java.io.IOException;


public class ModelGenerator extends CompoundCodeGenerator {

    public ModelGenerator() throws IOException {
        super(new JavaModelGenerator(), new ModelCopyGenerator());
    }

}
