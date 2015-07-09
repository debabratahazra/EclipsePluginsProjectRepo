package com.odcgroup.mdf.generation;

import com.odcgroup.common.mdf.generation.MDFGenerator;
import com.odcgroup.mdf.generation.legacy.castor.CastorXMLMapWriter;


public class CastorXMLMapGenerator extends MDFGenerator {

    public CastorXMLMapGenerator() {
        super(new CastorXMLMapWriter());
    }

}
