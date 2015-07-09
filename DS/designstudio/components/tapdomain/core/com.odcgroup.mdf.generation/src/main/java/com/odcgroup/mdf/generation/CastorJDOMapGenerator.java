package com.odcgroup.mdf.generation;

import com.odcgroup.common.mdf.generation.MDFGenerator;
import com.odcgroup.mdf.generation.legacy.castor.CastorJDOMapWriter;


public class CastorJDOMapGenerator extends MDFGenerator {

    public CastorJDOMapGenerator() {
        super(new CastorJDOMapWriter());
    }

}
