package com.ifx.dave.monitor.svd;

import java.util.HashMap;
import java.util.Map;

public class Peripheral {
    String peripheralName;

    public String getPeripheralName() {
        return peripheralName;
    }

    public void setPeripheralName(String peripheralName) {
        this.peripheralName = peripheralName;
    }

    public Map<String, Register> reisterMap = new HashMap<String, Register>();
}
