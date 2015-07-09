package com.odcgroup.mdf.generation;

import com.odcgroup.common.mdf.generation.MDFGenerator;
import com.odcgroup.mdf.generation.legacy.torque.TorqueWriter;

public class TorqueGenerator extends MDFGenerator {

    public TorqueGenerator() {
        super(new TorqueWriter());
    }
}
