package com.odcgroup.mdf.generation;

import com.odcgroup.common.mdf.generation.MDFGenerator;
import com.odcgroup.mdf.generation.legacy.xsd.XSDModelWriter;

public class XMLSchemaGenerator extends MDFGenerator {

    public XMLSchemaGenerator() {
        super(new XSDModelWriter());
    }
}
